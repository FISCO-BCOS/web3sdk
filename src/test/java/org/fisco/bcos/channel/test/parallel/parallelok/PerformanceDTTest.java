package org.fisco.bcos.channel.test.parallel.parallelok;

import com.google.common.util.concurrent.RateLimiter;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class PerformanceDTTest {
    private static Logger logger = LoggerFactory.getLogger(PerformanceDTTest.class);
    private static AtomicInteger sended = new AtomicInteger(0);
    private static String groupId = "1";

    private Web3j web3;
    private ParallelOk parallelok;

    private Credentials credentials;
    private DagUserMgr dagUserMgr;
    private PerformanceDTCollector collector;
    private String parallelokAddr = "";

    public PerformanceDTTest(String groupID) throws Exception {
        groupId = groupID;
        initialize(groupId);
    }

    public DagUserMgr getDagUserMgr() {
        return dagUserMgr;
    }

    public void setDagUserMgr(DagUserMgr dagUserMgr) {
        this.dagUserMgr = dagUserMgr;
    }

    public Web3j getWeb3() {
        return web3;
    }

    public void setWeb3(Web3j web3) {
        this.web3 = web3;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public PerformanceDTCollector getCollector() {
        return collector;
    }

    public void setCollector(PerformanceDTCollector collector) {
        this.collector = collector;
    }

    public void veryTransferData() {
        // System.out.println(" data validation => ");
        List<DagTransferUser> allUser = dagUserMgr.getUserList();
        int total_user = allUser.size();

        int verify_success = 0;

        int verify_failed = 0;

        allUser = dagUserMgr.getUserList();

        try {
            for (int i = 0; i < allUser.size(); ++i) {
                BigInteger result = parallelok.balanceOf(allUser.get(i).getUser()).send();

                String user = allUser.get(i).getUser();
                BigInteger local = allUser.get(i).getAmount();
                BigInteger remote = result;

                logger.debug(
                        " user  " + user + " local amount  " + local + " remote amount " + remote);
                if (local.compareTo(remote) != 0) {
                    verify_failed++;
                    logger.error(
                            " local amount is not same as remote, user "
                                    + user
                                    + " local "
                                    + local
                                    + " remote "
                                    + remote);
                } else {
                    verify_success++;
                }
            }

            System.out.println("validation:");
            System.out.println(" \tuser count is " + total_user);
            System.out.println(" \tverify_success count is " + verify_success);
            System.out.println(" \tverify_failed count is " + verify_failed);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void initialize(String groupId) throws Exception {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.setGroupId(Integer.parseInt(groupId));
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        Web3AsyncThreadPoolSize.web3AsyncCorePoolSize = 3000;
        Web3AsyncThreadPoolSize.web3AsyncPoolSize = 2000;

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(500);
        web3 =
                Web3j.build(
                        channelEthereumService,
                        15 * 100,
                        scheduledExecutorService,
                        Integer.parseInt(groupId));

        credentials =
                Credentials.create(
                        "b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
    }

    public void userAddTest(BigInteger count, BigInteger qps) {

        try {

            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(200);
            threadPool.setMaxPoolSize(500);
            threadPool.setQueueCapacity(count.intValue());

            threadPool.initialize();

            System.out.println("Deploying contract ");
            System.out.println(
                    "===================================================================");
            credentials =
                    Credentials.create(
                            "b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
            parallelok =
                    ParallelOk.deploy(
                                    web3,
                                    credentials,
                                    new BigInteger("30000000"),
                                    new BigInteger("30000000"))
                            .send();

            // enable parallel transaction
            parallelok.enableParallel().send();

            parallelokAddr = parallelok.getContractAddress();

            System.out.println("ParallelOk address: " + parallelokAddr);

            RateLimiter limiter = RateLimiter.create(qps.intValue());
            Integer area = count.intValue() / 10;

            long seconds = System.currentTimeMillis() / 1000l;

            this.collector.setStartTimestamp(System.currentTimeMillis());

            System.out.println(
                    "===================================================================");
            System.out.println("Start UserAdd test, count " + count);

            for (Integer i = 0; i < count.intValue(); ++i) {
                final int index = i;
                threadPool.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                boolean success = false;
                                while (!success) {
                                    limiter.acquire();
                                    String user =
                                            Long.toHexString(seconds) + Integer.toHexString(index);
                                    BigInteger amount = new BigInteger("1000000000");
                                    DagTransferUser dtu = new DagTransferUser();
                                    dtu.setUser(user);
                                    dtu.setAmount(amount);

                                    PerformanceDTCallback callback = new PerformanceDTCallback();
                                    callback.setCollector(collector);
                                    callback.setDagTransferUser(dtu);
                                    callback.setDagUserMgr(getDagUserMgr());
                                    callback.setCallBackType("set");

                                    try {
                                        parallelok.set(user, amount, callback);
                                        success = true;
                                    } catch (Exception e) {
                                        success = false;
                                        continue;
                                    }
                                }
                                int current = sended.incrementAndGet();

                                if (current >= area && ((current % area) == 0)) {
                                    System.out.println(
                                            "Already sended: "
                                                    + current
                                                    + "/"
                                                    + count
                                                    + " transactions");
                                }
                            }
                        });
            }

            // end or not
            while (!collector.isEnd()) {
                Thread.sleep(3000);
            }

            dagUserMgr.setContractAddr(parallelokAddr);
            dagUserMgr.writeDagTransferUser();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void userTransferRevertTest(BigInteger count, BigInteger qps, BigInteger deci) {

        try {

            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(200);
            threadPool.setMaxPoolSize(500);
            threadPool.setQueueCapacity(count.intValue());

            threadPool.initialize();

            RateLimiter limiter = RateLimiter.create(qps.intValue());
            Integer area = count.intValue() / 10;

            parallelokAddr = dagUserMgr.getContractAddr();
            parallelok =
                    ParallelOk.load(
                            parallelokAddr,
                            web3,
                            credentials,
                            new StaticGasProvider(
                                    new BigInteger("30000000"), new BigInteger("30000000")));

            System.out.println("ParallelOk address: " + parallelokAddr);

            // query all account balance info
            List<DagTransferUser> allUser = dagUserMgr.getUserList();
            for (int i = 0; i < allUser.size(); ++i) {
                BigInteger result = parallelok.balanceOf(allUser.get(i).getUser()).send();

                allUser.get(i).setAmount(result);

                logger.debug(" query user " + allUser.get(i).getUser() + " amount " + result);
            }

            System.out.println("Start UserTransferRevert test...");
            System.out.println(
                    "===================================================================");

            this.collector.setStartTimestamp(System.currentTimeMillis());

            for (Integer i = 0; i < 2 * count.intValue(); i += 2) {
                final int index = i;
                threadPool.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                boolean success = false;
                                while (!success) {
                                    limiter.acquire();
                                    DagTransferUser from = dagUserMgr.getFrom(index);
                                    DagTransferUser to = dagUserMgr.getNext(index);

                                    Random random = new Random();
                                    int value = random.nextInt(101);
                                    int prob = random.nextInt(10);
                                    if (prob < deci.intValue()) {
                                        value += 101;
                                    }
                                    BigInteger amount = BigInteger.valueOf(value);

                                    PerformanceDTCallback callback = new PerformanceDTCallback();
                                    callback.setCallBackType("transferRevert");
                                    callback.setCollector(collector);
                                    callback.setDagUserMgr(getDagUserMgr());
                                    callback.setFromUser(from);
                                    callback.setToUser(to);
                                    callback.setAmount(amount);

                                    String info =
                                            "[RevertTest-SendTx]"
                                                    + "\t[From]="
                                                    + from.getUser()
                                                    + "\t[FromBalance]="
                                                    + from.getAmount()
                                                    + "\t[To]="
                                                    + to.getUser()
                                                    + "\t[ToBalance]="
                                                    + to.getAmount()
                                                    + "\t[Amount]="
                                                    + amount;
                                    System.out.println(info);

                                    try {
                                        parallelok.transferWithRevert(
                                                from.getUser(), to.getUser(), amount, callback);
                                        success = true;
                                    } catch (Exception e) {
                                        success = false;
                                        continue;
                                    }
                                }
                            }
                        });
            }

            // end or not
            while (!collector.isEnd()) {
                Thread.sleep(3000);
            }

            veryTransferData();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void userTransferTest(BigInteger count, BigInteger qps, BigInteger deci) {

        try {

            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(200);
            threadPool.setMaxPoolSize(500);
            threadPool.setQueueCapacity(count.intValue());

            threadPool.initialize();

            RateLimiter limiter = RateLimiter.create(qps.intValue());
            Integer area = count.intValue() / 10;

            parallelokAddr = dagUserMgr.getContractAddr();
            parallelok =
                    ParallelOk.load(
                            parallelokAddr,
                            web3,
                            credentials,
                            new StaticGasProvider(
                                    new BigInteger("30000000"), new BigInteger("30000000")));

            System.out.println("ParallelOk address: " + parallelokAddr);

            // query all account balance info
            List<DagTransferUser> allUser = dagUserMgr.getUserList();
            for (int i = 0; i < allUser.size(); ++i) {
                BigInteger result = parallelok.balanceOf(allUser.get(i).getUser()).send();

                allUser.get(i).setAmount(result);

                logger.debug(" query user " + allUser.get(i).getUser() + " amount " + result);
            }

            System.out.println("Start UserTransfer test...");
            System.out.println(
                    "===================================================================");

            this.collector.setStartTimestamp(System.currentTimeMillis());

            for (Integer i = 0; i < count.intValue(); ++i) {
                final int index = i;
                threadPool.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                boolean success = false;
                                while (!success) {
                                    limiter.acquire();
                                    DagTransferUser from = dagUserMgr.getFrom(index);
                                    DagTransferUser to = dagUserMgr.getTo(index);

                                    if ((deci.intValue() > 0)
                                            && (deci.intValue() >= (index % 10 + 1))) {
                                        to = dagUserMgr.getNext(index);
                                    }

                                    Random random = new Random();
                                    int r = random.nextInt(100);
                                    BigInteger amount = BigInteger.valueOf(r);

                                    PerformanceDTCallback callback = new PerformanceDTCallback();
                                    callback.setCallBackType("transfer");
                                    callback.setCollector(collector);
                                    callback.setDagUserMgr(getDagUserMgr());
                                    callback.setFromUser(from);
                                    callback.setToUser(to);
                                    callback.setAmount(amount);

                                    try {
                                        logger.debug(
                                                " transfer from is "
                                                        + from
                                                        + " to is "
                                                        + to
                                                        + " amount is "
                                                        + amount);
                                        parallelok.transfer(
                                                from.getUser(), to.getUser(), amount, callback);
                                        success = true;
                                    } catch (Exception e) {
                                        success = false;
                                        continue;
                                    }
                                }
                                int current = sended.incrementAndGet();

                                if (current >= area && ((current % area) == 0)) {
                                    System.out.println(
                                            "Already sended: "
                                                    + current
                                                    + "/"
                                                    + count
                                                    + " transactions");
                                }
                            }
                        });
            }

            // end or not
            while (!collector.isEnd()) {
                Thread.sleep(3000);
            }

            veryTransferData();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public ParallelOk getDagTransfer() {
        return parallelok;
    }

    public void setDagTransfer(ParallelOk parallelok) {
        this.parallelok = parallelok;
    }
}
