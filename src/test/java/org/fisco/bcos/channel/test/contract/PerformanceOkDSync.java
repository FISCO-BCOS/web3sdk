package org.fisco.bcos.channel.test.contract;

import com.google.common.util.concurrent.RateLimiter;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.utils.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class PerformanceOkDSync {
    private static Logger logger = LoggerFactory.getLogger(PerformanceOkDSync.class);
    private static AtomicInteger sended = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        try {
            String groupId = args[3];
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            Service service = context.getBean(Service.class);
            service.setGroupId(Integer.parseInt(groupId));
            service.run();

            System.out.println("Start test...");
            System.out.println(
                    "===================================================================");

            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            channelEthereumService.setChannelService(service);

            if (args.length > 4) {
                Integer threadPoolSize = Integer.parseInt(args[4]);
                Async async = new Async(Executors.newFixedThreadPool(threadPoolSize));
                System.out.println(" === thread pool size = " + threadPoolSize);
            }

            Web3j web3 = Web3j.build(channelEthereumService, Integer.parseInt(groupId));

            Credentials credentials =
                    Credentials.create(
                            "b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

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
                    System.out.println("Args: <trans> <Total> <QPS> <GroupID> <ThreadPoolSize>");
            }

            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(200);
            threadPool.setMaxPoolSize(500);
            threadPool.setQueueCapacity(count);

            threadPool.initialize();

            System.out.println("Deploying contract...");
            OkD ok = OkD.deploy(web3, credentials, gasPrice, gasLimit).send();

            PerformanceCollector collector = new PerformanceCollector();
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
                                PerformanceOkCallback callback = new PerformanceOkCallback();
                                callback.setCollector(collector);
                                try {
                                    TransactionReceipt receipt =
                                            ok.trans(
                                                            String.valueOf(random.nextLong()),
                                                            new BigInteger("1"))
                                                    .sendAsync()
                                                    .get();
                                    callback.onResponse(receipt);
                                } catch (Exception e) {
                                    TransactionReceipt receipt = new TransactionReceipt();
                                    receipt.setStatus("-1");

                                    callback.onResponse(receipt);
                                    logger.error("Error sending:", e);
                                }

                                int current = sended.incrementAndGet();

                                if (current >= area && ((current % area) == 0)) {
                                    System.out.println(
                                            "Already sended: "
                                                    + current
                                                    + "/"
                                                    + total
                                                    + " transactions");
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
