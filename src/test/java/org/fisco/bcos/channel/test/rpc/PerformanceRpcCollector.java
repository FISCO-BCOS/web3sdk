package org.fisco.bcos.channel.test.rpc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceRpcCollector {
    static Logger logger = LoggerFactory.getLogger(PerformanceRpcCollector.class);

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void onMessage(Response response, Long _cost) {
        try {
            if (response.getError() != null && response.getError().getCode() != 0) {
                // System.out.println("receipt error");
                error.addAndGet(1);
            }
            if ((received.get() + 1) % (total / 10) == 0) {
                System.out.println(
                        "                                                       |received:"
                                + String.valueOf((received.get() + 1) * 100 / total)
                                + "%");
            }
            // trans cost into ms
            Long cost = _cost / 1000000;
            if (cost < 1) {
                less1.incrementAndGet();
            } else if (cost < 5) {
                less5.incrementAndGet();
            } else if (cost < 10) {
                less10.incrementAndGet();

            } else if (cost < 20) {
                less20.incrementAndGet();

            } else if (cost < 50) {
                less50.incrementAndGet();

            } else if (cost < 100) {
                less100.incrementAndGet();

            } else if (cost < 500) {
                less500.incrementAndGet();
            } else {
                timeout500.incrementAndGet();
            }
            totalCost.addAndGet(cost);

            if (received.incrementAndGet() >= total) {
                System.out.println("total");

                // trans ns to ms
                Long totalTime = System.currentTimeMillis() - startTimestamp;

                System.out.println(
                        "===================================================================");

                System.out.println("Total RPC Requests:  " + String.valueOf(total));
                System.out.println("Total time: " + String.valueOf(totalTime) + "ms");
                System.out.println(
                        "TPS(include error requests): "
                                + String.valueOf(total / ((double) totalTime / 1000)));
                System.out.println(
                        "TPS(exclude error requests): "
                                + String.valueOf(
                                        (double) (total - error.get())
                                                / ((double) totalTime / 1000)));
                System.out.println(
                        "Avg time cost: " + String.valueOf(totalCost.get() / total) + "ms");
                System.out.println(
                        "Error rate: "
                                + String.valueOf((error.get() / received.get()) * 100)
                                + "%");

                System.out.println("Time area:");
                System.out.println(
                        "0    < time <  1ms   : "
                                + String.valueOf(less1)
                                + "  : "
                                + String.valueOf((double) less1.get() / total * 100)
                                + "%");
                System.out.println(
                        "1   < time <  5ms  : "
                                + String.valueOf(less5)
                                + "  : "
                                + String.valueOf((double) less5.get() / total * 100)
                                + "%");
                System.out.println(
                        "5  < time <  10ms  : "
                                + String.valueOf(less10)
                                + "  : "
                                + String.valueOf((double) less10.get() / total * 100)
                                + "%");
                System.out.println(
                        "10  < time <  20ms  : "
                                + String.valueOf(less20)
                                + "  : "
                                + String.valueOf((double) less20.get() / total * 100)
                                + "%");
                System.out.println(
                        "20  < time <  50ms : "
                                + String.valueOf(less50)
                                + "  : "
                                + String.valueOf((double) less50.get() / total * 100)
                                + "%");
                System.out.println(
                        "50 < time <  100ms : "
                                + String.valueOf(less100)
                                + "  : "
                                + String.valueOf((double) less100.get() / total * 100)
                                + "%");
                System.out.println(
                        "100 < time <  500ms : "
                                + String.valueOf(less500)
                                + "  : "
                                + String.valueOf((double) less500.get() / total * 100)
                                + "%");
                System.out.println(
                        "500 < time           : "
                                + String.valueOf(timeout500)
                                + "  : "
                                + String.valueOf((double) timeout500.get() / total * 100)
                                + "%");

                System.exit(0);
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
    }

    private AtomicLong less1 = new AtomicLong(0);
    private AtomicLong less5 = new AtomicLong(0);
    private AtomicLong less10 = new AtomicLong(0);
    private AtomicLong less20 = new AtomicLong(0);
    private AtomicLong less50 = new AtomicLong(0);
    private AtomicLong less100 = new AtomicLong(0);
    private AtomicLong less500 = new AtomicLong(0);
    private AtomicLong timeout500 = new AtomicLong(0);
    private AtomicLong totalCost = new AtomicLong(0);

    private Integer total = 0;
    private AtomicInteger received = new AtomicInteger(0);
    private AtomicInteger error = new AtomicInteger(0);
    private Long startTimestamp = System.currentTimeMillis();
}
