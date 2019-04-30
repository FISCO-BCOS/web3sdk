package org.fisco.bcos.channel.test.contract;

import com.google.common.util.concurrent.RateLimiter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class PerfomanceTableQuery {
  private static Logger logger = LoggerFactory.getLogger(PerfomanceTableQuery.class);
  private static AtomicInteger sended = new AtomicInteger(0);
  
  
  private static String getId(){
		 UUID uuid = UUID.randomUUID();
		 return uuid.toString().replace("-", "");
	}
  
  
  private static AtomicLong uniqeid = new AtomicLong(0);
  
	public static long getNextID() {
		return uniqeid.getAndIncrement();
	}


  public static void main(String[] args) throws Exception {
    try {
      String groupId = args[3];
      ApplicationContext context =
          new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
      Service service = context.getBean(Service.class);
      service.setGroupId(Integer.parseInt(groupId));
      service.run();

      System.out.println("Start test...");
      System.out.println("===================================================================");

      ChannelEthereumService channelEthereumService = new ChannelEthereumService();
      channelEthereumService.setChannelService(service);

      ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(500);
      Web3j web3 =
          Web3j.build(
              channelEthereumService,
              15 * 100,
              scheduledExecutorService,
              Integer.parseInt(groupId));

      Credentials credentials =
          Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

      BigInteger gasPrice = new BigInteger("30000000");
      BigInteger gasLimit = new BigInteger("30000000");

      String command = args[0];
      Integer count = 0;
      Integer qps = 0;

      switch (command) {
        case "trans":
          count = Integer.parseInt(args[1]);
          qps = Integer.parseInt(args[2]);
          break;
        default:
          System.out.println("Args: <trans> <Total> <QPS>");
      }

      ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
      threadPool.setCorePoolSize(200);
      threadPool.setMaxPoolSize(500);
      threadPool.setQueueCapacity(count);

      threadPool.initialize();

      System.out.println("Deploying contract...");
      TableTest tabletest = TableTest.deploy(web3, credentials, gasPrice, gasLimit).send();

      PerfomanceCollector collector = new PerfomanceCollector();
      collector.setTotal(count);

      RateLimiter limiter = RateLimiter.create(qps);
      Integer area = count / 10;
      final Integer total = count;

      Random random = new Random(System.currentTimeMillis());

      System.out.println("Start test，total：" + count);
      for (Integer i = 0; i < count; ++i) {
        threadPool.execute(
            new Runnable() {
              @Override
              public void run() {
                limiter.acquire();
                PerfomanceTableTestCallback callback = new PerfomanceTableTestCallback();
                callback.setCollector(collector);
                try {
                	
                	Long time_before = System.currentTimeMillis();
                	long _id = getNextID();
                	Random r=new Random();
                	long l1=r.nextLong();
                	Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>> lists = 
                				tabletest.select("fruit"+l1%TableTestClient.modevalue).send();
                	Long time_after = System.currentTimeMillis();
                	
                	TransactionReceipt receipt = new TransactionReceipt();
                    receipt.setStatus("0");
                    collector.onSelectMessage(receipt, time_after-time_before);
                    
          /*          
            		List<byte[]> value1 = lists.getValue1();
                    List<BigInteger> value2 = lists.getValue2();
                    List<byte[]> value3 = lists.getValue3();
                    logger.info("record numbers = " + value1.size());
                    System.out.println("record numbers = " + value1.size());
                    for (int i = 0; i < value1.size(); i++) {
                      String name = new String(value1.get(i));
                      logger.info("name = " + name);
                      System.out.println("name = " + name);
                      int item_id = value2.get(i).intValue();
                      logger.info("item_id = " + item_id);
                      System.out.println("item_id = " + item_id);
                      String item_name = new String(value3.get(i));
                      logger.info("item_name = " + item_name);
                      System.out.println("item_name = " + item_name);
                    }
                    
                    */
 
                } catch (Exception e) {
                  TransactionReceipt receipt = new TransactionReceipt();
                  receipt.setStatus("-1");

                  callback.onResponse(receipt);
                  logger.error("Error sending:", e);
                }

                int current = sended.incrementAndGet();

                if (current >= area && ((current % area) == 0)) {
                  System.out.println("Already sended: " + current + "/" + total + " transactions");
                }
              }
            });
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
