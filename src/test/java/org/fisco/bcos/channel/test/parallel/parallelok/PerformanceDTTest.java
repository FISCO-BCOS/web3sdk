package org.fisco.bcos.channel.test.parallel.parallelok;

import com.google.common.util.concurrent.RateLimiter;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
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
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class PerformanceDTTest {
    private static Logger logger = LoggerFactory.getLogger(PerformanceDTTest.class);

    private static String groupId = "1";

    private Web3j web3;
    private ParallelOk parallelok;
    private TransactionManager transactionManager;

    private Credentials credentials;
    private DagUserMgr dagUserMgr;
    private PerformanceDTCollector collector;
    private String parallelokAddr = "";

    private static AtomicInteger sent;
    private static CountDownLatch latch;

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
        System.out.println("===================================================================");
        System.out.println("Verify");
        System.out.println("===================================================================");

        String percent = "0.00%";
        System.out.print(dateFormat.format(new Date()) + " Verifying account state..." + percent);

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

                for (int p = 0; p < percent.length(); ++p) {
                    System.out.print('\b');
                }

                percent = String.format("%.2f%%", (i + 1) * 100 / (double) allUser.size());
                System.out.print(percent);
            }
            System.out.println("");

            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow(null, "Validation");
            at.addRule();
            at.addRow("User count", total_user);
            at.addRule();
            at.addRow("Verify success", verify_success);
            at.addRule();
            at.addRow("Verify failed", verify_failed);
            at.addRule();

            at.getContext().setWidth(40);
            at.setTextAlignment(TextAlignment.CENTER);

            String atRender = at.render();
            System.out.println(atRender);
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
        transactionManager = Contract.getTheTransactionManager(web3, credentials);
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

            System.out.println(
                    "===================================================================");
            System.out.println("Start UserAdd test, count " + count);

            long startTime = System.currentTimeMillis();
            this.collector.setStartTimestamp(startTime);

            sent = new AtomicInteger(0);

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
                                int current = sent.incrementAndGet();

                                if (current >= area && ((current % area) == 0)) {
                                    long elapsed = System.currentTimeMillis() - startTime;
                                    double sendSpeed = current / ((double) elapsed / 1000);
                                    System.out.println(
                                            "Already sent: "
                                                    + current
                                                    + "/"
                                                    + count
                                                    + " transactions"
                                                    + ",QPS="
                                                    + sendSpeed);
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

            long startTime = System.currentTimeMillis();
            this.collector.setStartTimestamp(startTime);

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
            parallelokAddr = dagUserMgr.getContractAddr();
            parallelok =
                    ParallelOk.load(
                            parallelokAddr,
                            web3,
                            credentials,
                            new StaticGasProvider(
                                    new BigInteger("30000000"), new BigInteger("30000000")));

            String percent = "0.00%";
            System.out.print(
                    dateFormat.format(new Date()) + " Querying account state..." + percent);
            List<DagTransferUser> allUser = dagUserMgr.getUserList();
            for (int i = 0; i < allUser.size(); ++i) {
                BigInteger result = parallelok.balanceOf(allUser.get(i).getUser()).send();
                allUser.get(i).setAmount(result);

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
            System.out.print(
                    dateFormat.format(new Date()) + " Creating signed transactions..." + percent);
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
                                    int r = random.nextInt(100);
                                    BigInteger amount = BigInteger.valueOf(r);

                                    try {
                                        String signedTransaction =
                                                parallelok.transferSeq(
                                                        from.getUser(), to.getUser(), amount);
                                        String content =
                                                String.format(
                                                        "%s %d %d%n", signedTransaction, index, r);
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

                    percent =
                            String.format(
                                    "%.2f%%", (end - latchCount) / (double) count.intValue() * 100);
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
                                        transactionManager.sendTransaction(
                                                signedTransactions.get(index),
                                                callbacks.get(index));
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
                                                "sent: "
                                                        + (int)
                                                                (current
                                                                        / (double) count.intValue()
                                                                        * 100)
                                                        + "%"
                                                        + ", QPS: "
                                                        + String.format("%.2f", sendSpeed));
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
    /*
    Lock lock = new ReentrantLock();
    List<String> signedTransactions = new ArrayList<String>();
    List<PerformanceDTCallback> callbacks = new ArrayList<PerformanceDTCallback>();

    try {


        System.out.println("Reading account state...");
        List<DagTransferUser> allUser = dagUserMgr.getUserList();
        for (int i = 0; i < allUser.size(); ++i) {
            BigInteger result = parallelok.balanceOf(allUser.get(i).getUser()).send();
            allUser.get(i).setAmount(result);
        }

        int coreNum = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(coreNum);
        threadPool.setMaxPoolSize(coreNum * 2);
        threadPool.setQueueCapacity(count.intValue());
        threadPool.initialize();

        latch = new CountDownLatch(count.intValue());

        // create signed transactions
        System.out.println("Creating signed transactions...");
        for (int i = 0; i < count.intValue(); ++i) {
            final int index = i;
            threadPool.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
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
                                    String signedTransaction =
                                            parallelok.transferSeq(
                                                    from.getUser(), to.getUser(), amount);
                                    lock.lock();
                                    signedTransactions.add(signedTransaction);
                                    callbacks.add(callback);
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    continue;
                                } finally {
                                    lock.unlock();
                                }
                            }
                            latch.countDown();
                        }
                    });
        }

        latch.await();

        // send signed transactions
        threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(coreNum * 16);
        threadPool.setMaxPoolSize(coreNum * 32);
        threadPool.setQueueCapacity(count.intValue());
        threadPool.initialize();

        latch = new CountDownLatch(count.intValue());

        long startTime = System.currentTimeMillis();
        collector.setStartTimestamp(startTime);
        AtomicInteger sent = new AtomicInteger(0);
        int division = count.intValue() / 10;

        System.out.println("Sending signed transactions...");
        for (int i = 0; i < count.intValue(); ++i) {
            final int index = i;
            threadPool.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    transactionManager.sendTransaction(
                                            signedTransactions.get(index),
                                            callbacks.get(index));
                                    break;
                                } catch (Exception e) {
                                    continue;
                                }
                            }

                            int current = sent.incrementAndGet();

                            if (current >= division && ((current % division) == 0)) {
                                long elapsed = System.currentTimeMillis() - startTime;
                                double sendSpeed = current / ((double) elapsed / 1000);
                                System.out.println(
                                        "Already sent: "
                                                + current
                                                + "/"
                                                + count
                                                + " transactions"
                                                + ",QPS="
                                                + sendSpeed);
                            }

                            latch.countDown();
                        }
                    });
        }

        latch.await();

        while (!collector.isEnd()) {
            Thread.sleep(3000);
        }

        veryTransferData();

        System.exit(0);
    } catch (Exception e) {
        e.printStackTrace();
        System.exit(0);
    }
    /*
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

        long startTime = System.currentTimeMillis();
        this.collector.setStartTimestamp(startTime);

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
                                long elapsed = System.currentTimeMillis() - startTime;
                                double sendSpeed = current / ((double)elapsed / 1000);
                                System.out.println(
                                        "Already sended: "
                                                + current
                                                + "/"
                                                + count
                                                + " transactions"
                                                + ",QPS="
                                                + sendSpeed);
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
    */

    public ParallelOk getDagTransfer() {
        return parallelok;
    }

    public void setDagTransfer(ParallelOk parallelok) {
        this.parallelok = parallelok;
    }
}
