package org.fisco.bcos.channel.test.parallel.parallelok;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceDTCollector {

    static Logger logger = LoggerFactory.getLogger(PerformanceDTCollector.class);

    private Integer total = 0;
    private DagUserMgr dagUserMrg;
    private PerformanceDTTest PerformanceDTTest;

    public PerformanceDTTest getPerformanceDTTest() {
        return PerformanceDTTest;
    }

    public void setPerformanceDTTest(PerformanceDTTest PerformanceDTTest) {
        this.PerformanceDTTest = PerformanceDTTest;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public DagUserMgr getDagUserMrg() {
        return dagUserMrg;
    }

    public void setDagUserMrg(DagUserMgr dagUserMrg) {
        this.dagUserMrg = dagUserMrg;
    }

    public boolean isEnd() {
        return received.intValue() >= total;
    }

    public void onMessage(TransactionReceipt receipt, Long cost) {
        try {
            if (!receipt.isStatusOK()) {
                System.out.println("receipt error! status: " + receipt.getStatus());
                error.addAndGet(1);
            }

            received.incrementAndGet();

            if ((received.get() + 1) % (total / 10) == 0) {
                System.out.println(
                        "                                                       |received:"
                                + String.valueOf((received.get() + 1) * 100 / total)
                                + "%");
            }

            if (cost < 50) {
                less50.incrementAndGet();
            } else if (cost < 100) {
                less100.incrementAndGet();
                ;
            } else if (cost < 200) {
                less200.incrementAndGet();
                ;
            } else if (cost < 400) {
                less400.incrementAndGet();
                ;
            } else if (cost < 1000) {
                less1000.incrementAndGet();
                ;
            } else if (cost < 2000) {
                less2000.incrementAndGet();
                ;
            } else {
                timeout2000.incrementAndGet();
                ;
            }

            totalCost.addAndGet(cost);

            if (isEnd()) {
                Long totalTime = System.currentTimeMillis() - startTimestamp;

                System.out.println(
                        "===================================================================");
                System.out.println("Summary");
                System.out.println(
                        "===================================================================");

                AsciiTable at = new AsciiTable();
                at.addRule();
                at.addRow(null, "Performance");
                at.addRule();
                at.addRow("Total transactions", total);
                at.addRule();
                at.addRow("Total sent", total - error.get());
                at.addRule();
                at.addRow("Total error", error.get());
                at.addRule();
                at.addRow("Error rate", (error.get() / (double) received.get()) * 100 + "%");
                at.addRule();
                at.addRow(
                        "Return error rate",
                        (ret_error.get() / (double) received.get()) * 100 + "%");
                at.addRule();
                at.addRow("Elapsed time", totalTime + " ms");
                at.addRule();
                at.addRow(
                        "TPS",
                        String.format("%.2f", (total - error.get()) / ((double) totalTime / 1000)));
                at.addRule();

                at.getContext().setWidth(40);
                at.setTextAlignment(TextAlignment.CENTER);

                String atRender = at.render();
                System.out.println(atRender + "\n");

                at = new AsciiTable();
                at.addRule();
                at.addRow(null, null, "Time Area");
                at.addRule();
                at.addRow(
                        "0 < time < 50ms",
                        less50,
                        String.format("%.2f%%", (double) less50.get() / total * 100));
                at.addRule();
                at.addRow(
                        "50 < time < 100ms",
                        less100,
                        String.format("%.2f%%", (double) less100.get() / total * 100));
                at.addRule();
                at.addRow(
                        "100 < time < 200ms",
                        less200,
                        String.format("%.2f%%", (double) less200.get() / total * 100));
                at.addRule();
                at.addRow(
                        "200 < time < 400ms",
                        less400,
                        String.format("%.2f%%", (double) less400.get() / total * 100));
                at.addRule();
                at.addRow(
                        "400 < time < 1000ms",
                        less1000,
                        String.format("%.2f%%", (double) less1000.get() / total * 100));
                at.addRule();
                at.addRow(
                        "1000 < time < 2000ms",
                        less2000,
                        String.format("%.2f%%", (double) less2000.get() / total * 100));
                at.addRule();
                at.addRow(
                        "2000 < time",
                        timeout2000,
                        String.format("%.2f%%", (double) timeout2000.get() / total * 100));
                at.addRule();
                at.addRow("Average time cost", null, totalCost.get() / total + " ms");
                at.addRule();

                at.getContext().setWidth(80);
                at.setTextAlignment(TextAlignment.CENTER);

                atRender = at.render();
                System.out.println(atRender + "\n");
            }

        } catch (Exception e) {
            logger.error("error:", e);
            System.exit(0);
        }
    }

    private AtomicLong less50 = new AtomicLong(0);
    private AtomicLong less100 = new AtomicLong(0);
    private AtomicLong less200 = new AtomicLong(0);
    private AtomicLong less400 = new AtomicLong(0);
    private AtomicLong less1000 = new AtomicLong(0);
    private AtomicLong less2000 = new AtomicLong(0);
    private AtomicLong timeout2000 = new AtomicLong(0);
    private AtomicLong totalCost = new AtomicLong(0);

    private AtomicInteger received = new AtomicInteger(0);

    public AtomicInteger getReceived() {
        return received;
    }

    public void setReceived(AtomicInteger received) {
        this.received = received;
    }

    private AtomicInteger error = new AtomicInteger(0);
    private AtomicInteger ret_error = new AtomicInteger(0);
    private Long startTimestamp = System.currentTimeMillis();

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
}
