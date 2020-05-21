package org.fisco.bcos.channel.test.rpc;

import com.google.common.util.concurrent.RateLimiter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.protocol.core.Response.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class PerformanceRPC {
    private static Logger logger = LoggerFactory.getLogger(PerformanceRPC.class);
    private static AtomicInteger sended = new AtomicInteger(0);

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.PerformanceRPC groupID totalCount qps");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        try {
            if (args.length < 3) {
                Usage();
            }

            int groupId = Integer.valueOf(args[0]);
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            Service service = context.getBean(Service.class);
            service.setGroupId(groupId);
            service.run();

            System.out.println("Start test...");
            System.out.println(
                    "===================================================================");

            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            channelEthereumService.setChannelService(service);

            ScheduledExecutorService scheduledExecutorService =
                    Executors.newScheduledThreadPool(500);
            Web3j web3j =
                    Web3j.build(
                            channelEthereumService, 15 * 100, scheduledExecutorService, groupId);

            Integer count = Integer.parseInt(args[1]);
            Integer qps = Integer.parseInt(args[2]);

            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(200);
            threadPool.setMaxPoolSize(500);
            threadPool.setQueueCapacity(count);
            threadPool.initialize();

            PerformanceRpcCollector collector = new PerformanceRpcCollector();
            collector.setTotal(count);

            RateLimiter limiter = RateLimiter.create(qps);
            Integer area = count / 10;
            final Integer total = count;

            System.out.println("Start test，total：" + count);
            for (Integer i = 0; i < count; ++i) {
                threadPool.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                limiter.acquire();
                                Response response = new Response();
                                try {
                                    int random = new SecureRandom().nextInt(50000);
                                    int methodNum = 10;
                                    Long startTime = System.nanoTime();

                                    switch (random % methodNum) {
                                            // 1. call getPendingTxSize
                                        case 0:
                                            response = web3j.getPendingTxSize().send();
                                            break;
                                            // 2. call getBlockNumber
                                        case 1:
                                            response = web3j.getBlockNumber().send();
                                            break;
                                            // 3. call getSyncStatus
                                        case 2:
                                            response = web3j.getSyncStatus().send();
                                            break;
                                            // 4. call getConsensusStatus
                                            // case 3:
                                            //    response = web3j.getConsensusStatus().send();
                                            //    break;
                                            // 5. call getSealerList
                                        case 4:
                                            response = web3j.getSealerList().send();
                                            break;
                                            // 6. call getTotalTransactionCount
                                        case 5:
                                            response = web3j.getTotalTransactionCount().send();
                                            break;
                                            // 7. call getObserverList
                                        case 6:
                                            response = web3j.getObserverList().send();
                                            break;
                                            // 8. call getBlockHashByNumber
                                        case 7:
                                            BigInteger blockNumber =
                                                    web3j.getBlockNumber().send().getBlockNumber();
                                            DefaultBlockParameter blockParam =
                                                    DefaultBlockParameter.valueOf(blockNumber);

                                            response =
                                                    web3j.getBlockHashByNumber(blockParam).send();
                                            break;
                                            // 9. call getSystemConfigByKey
                                        case 8:
                                            response =
                                                    web3j.getSystemConfigByKey("tx_count_limit")
                                                            .send();
                                            break;
                                            // 10. call getPbftView
                                        case 9:
                                            response = web3j.getPbftView().send();
                                            break;
                                        default:
                                            // default call getPbftView
                                            response = web3j.getPbftView().send();
                                    }
                                    Long cost = System.nanoTime() - startTime;
                                    collector.onMessage(response, cost);

                                } catch (Exception e) {
                                    logger.error(
                                            "test rpc interface failed, error info: {}",
                                            e.getMessage());
                                    Error error = new Error();
                                    error.setCode(1);
                                    response.setError(error);
                                    collector.onMessage(response, 0L);
                                }

                                int current = sended.incrementAndGet();

                                if (current >= area && ((current % area) == 0)) {
                                    System.out.println(
                                            "Already sended: "
                                                    + current
                                                    + "/"
                                                    + total
                                                    + " RPC Requests");
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
