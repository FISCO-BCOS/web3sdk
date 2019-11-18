package org.bcos.channel.test.performance;

import com.google.common.util.concurrent.RateLimiter;
import org.bcos.channel.client.Service;
import org.bcos.channel.test.Ok;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.GenCredential;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class PerfOK {

  public static void Usage() {
    System.out.println(" Usage:");
    System.out.println(
        " \t java -cp conf/:lib/*:apps/* org.bcos.channel.test.performance.PerfOK count tps.");
    System.exit(0);
  }

  public static void main(String[] args) throws Exception {
    try {

      if (args.length <= 1) {
        Usage();
      }

      // init web3j first
      ApplicationContext context =
          new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
      Service service = context.getBean(Service.class);
      service.run();

      ChannelEthereumService channelEthereumService = new ChannelEthereumService();
      channelEthereumService.setChannelService(service);
      channelEthereumService.setTimeout(10000);
      Web3j web3 = Web3j.build(channelEthereumService);
      Thread.sleep(3000);

      Credentials credentials = GenCredential.create();

      Integer count = Integer.valueOf(args[0]);
      Integer tps = Integer.valueOf(args[1]);
      System.out.println("===================================================================");
      System.out.printf(" Total count: %d, tps: %d \n", count, tps);

      // init thread pool
      ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
      threadPool.setCorePoolSize(tps);
      threadPool.setMaxPoolSize(tps);
      threadPool.setQueueCapacity(count.intValue());
      threadPool.initialize();

      BigInteger gasPrice = new BigInteger("300000000");
      BigInteger gasLimit = new BigInteger("300000000");
      BigInteger initialWeiValue = new BigInteger("0");

      final Ok ok = Ok.deploy(web3, credentials, gasPrice, gasLimit, initialWeiValue).get();
      if (null == ok) {
        System.out.println(" Deploy Ok failed ");
        System.exit(0);
      }

      System.out.printf(" Deploy Ok success, contract address: %s\n", ok.getContractAddress());

      PerformanceOKCollector collector = new PerformanceOKCollector();
      RateLimiter limiter = RateLimiter.create(tps.intValue());
      Integer area = count.intValue() / 10;

      AtomicInteger sended = new AtomicInteger(0);
      long startMS = System.currentTimeMillis();

      collector.setTotal(count);
      collector.setStartTimestamp(startMS);

      for (Integer i = 0; i < count.intValue(); ++i) {
        final int index = i;
        threadPool.execute(
            new Runnable() {
              @Override
              public void run() {
                limiter.acquire();

                PerformanceOKCallback callback = new PerformanceOKCallback();
                callback.setCollector(collector);

                ok.trans(new Uint256(index), callback);

                int current = sended.incrementAndGet();

                if (current >= area && ((current % area) == 0)) {
                  System.out.println("Already sended: " + current + "/" + count + " transactions");
                }
              }
            });
      }

      // end or not
      while (!collector.isEnd()) {
        Thread.sleep(100);
      }

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
