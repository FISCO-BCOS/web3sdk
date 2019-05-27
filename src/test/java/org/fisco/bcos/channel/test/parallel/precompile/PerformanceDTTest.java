package org.fisco.bcos.channel.test.parallel.precompile;

import com.google.common.util.concurrent.RateLimiter;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PerformanceDTTest {
    private static Logger logger = LoggerFactory.getLogger(PerformanceDTTest.class);
    private static AtomicInteger sended = new AtomicInteger(0);
    private static final String dagTransferAddr = "0x0000000000000000000000000000000000005002";
    private static String groupId = "1";

    private Web3j web3;
    private DagTransfer dagTransfer;

    private Credentials credentials;
    private DagUserMgr dagUserMgr;
    private PerformanceDTCollector collector;

    private static CountDownLatch latch;
    private static AtomicInteger sent;
    private TransactionManager transactionManager;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
                Tuple2<BigInteger, BigInteger> result =
                        dagTransfer.userBalance(allUser.get(i).getUser()).send();

                String user = allUser.get(i).getUser();
                BigInteger local = allUser.get(i).getAmount();
                BigInteger remote = result.getValue2();

                if (result.getValue1().compareTo(new BigInteger("0")) != 0) {
                    logger.error(" query failed, user " + user + " ret code " + result.getValue1());
                    verify_failed++;
                    continue;
                }

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
        Web3j web3 =
                Web3j.build(
                        channelEthereumService,
                        15 * 100,
                        scheduledExecutorService,
                        Integer.parseInt(groupId));

        Credentials credentials =
                Credentials.create(
                        "b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

        dagTransfer =
                DagTransfer.load(
                        dagTransferAddr,
                        web3,
                        credentials,
                        new StaticGasProvider(
                                new BigInteger("30000000"), new BigInteger("30000000")));
        transactionManager = Contract.getTheTransactionManager(web3, credentials);
    }

    public void userAddTest(BigInteger count, BigInteger qps) {

        try {

            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(200);
            threadPool.setMaxPoolSize(500);
            threadPool.setQueueCapacity(count.intValue());

            threadPool.initialize();

            System.out.println("Start UserAdd test, count " + count);
            System.out.println(
                    "===================================================================");

            RateLimiter limiter = RateLimiter.create(qps.intValue());
            Integer area = count.intValue() / 10;

            long seconds = System.currentTimeMillis() / 1000l;

            this.collector.setStartTimestamp(System.currentTimeMillis());

            for (int i = 0; i < count.intValue(); ++i) {
                final int index = i;
                threadPool.execute(
                        new Runnable() {
                            @Override
                            public void run() {
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
                                callback.setCallBackType("add");

                                try {
                                    dagTransfer.userAdd(user, amount, callback);
                                } catch (Exception e) {
                                    TransactionReceipt receipt = new TransactionReceipt();
                                    receipt.setStatus("-1");

                                    callback.onResponse(receipt);
                                    logger.info(e.getMessage());
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

            dagUserMgr.writeDagTransferUser();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void userTransferTest(BigInteger count, BigInteger qps, BigInteger deci) {
        System.out.println("===================================================================");
        System.out.println("Start UserTransfer test...");
        System.out.println("===================================================================");

        Lock lock = new ReentrantLock();

        String dirName = "./.signed_transactions";
        File dir = new File(dirName);
        if (dir.exists()) {
            File[] fileList = dir.listFiles();
            for (File file : fileList) {
                if (!file.delete()) {
                    System.out.printf("Can't clean %s%n", dirName);
                    System.exit(0);
                }
            }
        } else {
            if (!dir.mkdir()) {
                System.out.printf("Can't create directory %s%n", dirName);
                System.exit(0);
            }
        }

        try {
            String percent = "0.00%";
            System.out.print(dateFormat.format(new Date()) + " Querying account state..." + percent);
            List<DagTransferUser> allUser = dagUserMgr.getUserList();
            for (int i = 0; i < allUser.size(); ++i) {
                Tuple2<BigInteger, BigInteger> result =
                        dagTransfer.userBalance(allUser.get(i).getUser()).send();

                if (result.getValue1().compareTo(new BigInteger("0")) == 0) {
                    allUser.get(i).setAmount(result.getValue2());
                } else {
                    System.out.println(" Query failed, user is " + allUser.get(i).getUser());
                    System.exit(0);
                }

                for (int p = 0; p < percent.length(); ++p) {
                    System.out.print('\b');
                }
                percent = String.format("%.2f%%", (i + 1) * 100 / (double) allUser.size());
                System.out.print(percent);
            }
            System.out.println("");

            int coreNum = Runtime.getRuntime().availableProcessors();
            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(coreNum);
            threadPool.setMaxPoolSize(2 * coreNum);
            threadPool.setQueueCapacity(count.intValue());
            threadPool.initialize();

            int segmentSize = 200000;
            int segmentCount = count.intValue() / segmentSize;
            if (count.intValue() % segmentSize != 0) {
                segmentCount++;
            }

            percent = "0.00%";
            System.out.print(dateFormat.format(new Date()) + " Creating signed transactions..." + percent);
            for (int i = 0; i < segmentCount; ++i) {
                int start = i * segmentSize;
                int end = start + segmentSize;
                if (end > count.intValue()) {
                    end = count.intValue();
                }

                String fileName = dirName + "/signed_transactions_" + i;

                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                latch = new CountDownLatch(end - start);

                for (int j = start; j < end; ++j) {
                    final int index = j;
                    threadPool.execute(
                            new Runnable() {
                                @Override
                                public void run() {
                                    DagTransferUser from = dagUserMgr.getFrom(index);
                                    DagTransferUser to = dagUserMgr.getTo(index);
                                    if ((deci.intValue() > 0)
                                            && (deci.intValue() >= (index % 10 + 1))) {
                                        to = dagUserMgr.getNext(index);
                                    }

                                    Random random = new Random();
                                    int r = random.nextInt(100) + 1;
                                    BigInteger amount = BigInteger.valueOf(r);

                                    try {
                                        String signedTransaction = dagTransfer.userTransferSeq(
                                                from.getUser(), to.getUser(), amount);
                                        String content = String.format("%s %d %d%n", signedTransaction, index, r);
                                        lock.lock();
                                        writer.write(content);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        lock.unlock();
                                    }
                                    latch.countDown();
                                }
                            });
                }

                long latchCount;
                while ((latchCount = latch.getCount()) != 0) {

                    for (int p = 0; p < percent.length(); ++p) {
                        System.out.print('\b');
                    }

                    percent = String.format("%.2f%%", (end - latchCount) / (double) count.intValue() * 100);
                    System.out.print(percent);
                    Thread.sleep(40);
                }

                writer.close();
            }

            for (int p = 0; p < percent.length(); ++p) {
                System.out.print('\b');
            }
            System.out.println("100.00%");

            System.out.println(dateFormat.format(new Date()) + " Sending transactions...");

            File[] fileList = dir.listFiles();

            long startTime = System.currentTimeMillis();
            collector.setStartTimestamp(startTime);

            sent = new AtomicInteger(0);

            for (int i = 0; i < fileList.length; ++i) {
                BufferedReader reader = new BufferedReader(new FileReader(fileList[i]));

                List<String> signedTransactions = new ArrayList<String>();
                List<PerformanceDTCallback> callbacks = new ArrayList<PerformanceDTCallback>();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(" ");
                    signedTransactions.add(fields[0]);

                    int index = Integer.parseInt(fields[1]);
                    BigInteger amount = new BigInteger(fields[2]);

                    DagTransferUser from = dagUserMgr.getFrom(index);
                    DagTransferUser to = dagUserMgr.getTo(index);

                    PerformanceDTCallback callback = new PerformanceDTCallback();
                    callback.setCallBackType("transfer");
                    callback.setCollector(collector);
                    callback.setDagUserMgr(getDagUserMgr());
                    callback.setFromUser(from);
                    callback.setToUser(to);
                    callback.setAmount(amount);
                    callbacks.add(callback);
                }

                threadPool = new ThreadPoolTaskExecutor();
                threadPool.setCorePoolSize(coreNum * 16);
                threadPool.setMaxPoolSize(coreNum * 32);
                threadPool.setQueueCapacity(count.intValue());
                threadPool.initialize();

                latch = new CountDownLatch(signedTransactions.size());
                int division = count.intValue() / 10;
                RateLimiter limiter = RateLimiter.create(qps.intValue());

                for (int j = 0; j < signedTransactions.size(); ++j) {
                    final int index = j;
                    threadPool.execute(
                            new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        limiter.acquire();
                                        transactionManager.sendTransaction(signedTransactions.get(index), callbacks.get(index));
                                    } catch (Exception e) {
                                        TransactionReceipt receipt = new TransactionReceipt();
                                        receipt.setStatus("-1");
                                        callbacks.get(index).onResponse(receipt);
                                    }

                                    int current = sent.addAndGet(1);
                                    if (current >= division && (current % division) == 0) {
                                        long elapsedTime = System.currentTimeMillis() - startTime;
                                        double sendSpeed = current / ((double) elapsedTime / 1000);
                                        System.out.println(
                                                "sent: " + (int) (current / (double) count.intValue() * 100) + "%"
                                                        + ", QPS: " + String.format("%.2f", sendSpeed));
                                    }
                                    latch.countDown();
                                }
                            });
                }

                latch.await();
            }

            while (!collector.isEnd()) {
                Thread.sleep(1000);
            }

            if (dir.exists()) {
                fileList = dir.listFiles();
                for (File file : fileList) {
                    if (!file.delete()) {
                        System.out.printf("Can't clean %s%n", dirName);
                    }
                }
            }

            System.out.println(dateFormat.format(new Date()) + " Verifying account state...");
            veryTransferData();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public DagTransfer getDagTransfer() {
        return dagTransfer;
    }

    public void setDagTransfer(DagTransfer dagTransfer) {
        this.dagTransfer = dagTransfer;
    }
}
