package org.fisco.bcos.channel.test.amop;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfomanceCollector {
  static Logger logger = LoggerFactory.getLogger(PerfomanceCollector.class);
  public Map<Integer, RequestTimer> resultMap;
  public Integer total;
  public AtomicInteger received = new AtomicInteger(0);
  public AtomicInteger error = new AtomicInteger(0);
  public Long startTimestamp = (long) 0;
  public Integer tps = 0;
  public Integer packageSize = 0;

  public void onMessage(ChannelResponse response) {
    try {
      Integer currentError = 0;

      if (response.getErrorCode() != 0) {
        System.out.println(
            "response error:"
                + String.valueOf(response.getErrorCode())
                + ", "
                + response.getErrorMessage());

        currentError = 1;
      }

      RequestTimer timer = null;
      if (response.getMessageID() != null) {
        timer = resultMap.get(response.getMessageID());
        if (timer == null) {
          System.out.println(
              "response error，seq:" + String.valueOf(response.getMessageID()) + " not found");
          currentError = 1;
        }
      } else {
        currentError = 1;
      }

      error.addAndGet(currentError);

      if (timer != null) {
        timer.recvTimestamp = System.currentTimeMillis();
      }

      received.incrementAndGet();

      logger.debug(
          "response: {} {} {}, total:{}/{}",
          response.getErrorCode(),
          response.getMessageID(),
          response.getContent(),
          received,
          total);

      if ((received.get() + 1) % (total / 10) == 0) {
        System.out.println(
            "                                                       |received:"
                + String.valueOf((received.get() + 1) * 100 / total)
                + "%");
      }

      if (received.intValue() >= total) {
        System.out.println("total");

        // 总耗时
        Long totalTime = System.currentTimeMillis() - startTimestamp;

        System.out.println("===================================================================");

        Integer less50 = 0;
        Integer less100 = 0;
        Integer less200 = 0;
        Integer less400 = 0;
        Integer less1000 = 0;
        Integer less2000 = 0;
        Integer timeout2000 = 0;

        Long totalCost = (long) 0;
        // 汇总信息并输出
        for (RequestTimer v : resultMap.values()) {
          Long cost = (long) 0;

          if (v.recvTimestamp > 0 && v.sendTimestamp > 0) {
            cost = v.recvTimestamp - v.sendTimestamp;
          }

          totalCost += cost;

          // 耗时分段统计
          if (cost < 50) {
            ++less50;
          } else if (cost < 100) {
            ++less100;
          } else if (cost < 200) {
            ++less200;
          } else if (cost < 400) {
            ++less400;
          } else if (cost < 1000) {
            ++less1000;
          } else if (cost < 2000) {
            ++less2000;
          } else {
            ++timeout2000;
          }
        }

        System.out.println("total: " + String.valueOf(total));
        System.out.println("packageSize: " + String.valueOf(packageSize) + " byte");
        System.out.println("tps: " + String.valueOf(tps));
        System.out.println(
            "total/totalTime: " + String.valueOf(total / ((double) totalTime / 1000)));
        System.out.println("totalCost/total:" + String.valueOf(totalCost / total) + "ms");
        System.out.println(
            "error/received: " + String.valueOf((error.get() / received.get()) * 100) + "%");

        System.out.println("time:");
        System.out.println(
            "0    < time <  50ms   : "
                + String.valueOf(less50)
                + "  : "
                + String.valueOf((double) less50 / total * 100)
                + "%");
        System.out.println(
            "50   < time <  100ms  : "
                + String.valueOf(less100)
                + "  : "
                + String.valueOf((double) less100 / total * 100)
                + "%");
        System.out.println(
            "100  < time <  200ms  : "
                + String.valueOf(less200)
                + "  : "
                + String.valueOf((double) less200 / total * 100)
                + "%");
        System.out.println(
            "200  < time <  400ms  : "
                + String.valueOf(less400)
                + "  : "
                + String.valueOf((double) less400 / total * 100)
                + "%");
        System.out.println(
            "400  < time <  1000ms : "
                + String.valueOf(less1000)
                + "  : "
                + String.valueOf((double) less1000 / total * 100)
                + "%");
        System.out.println(
            "1000 < time <  2000ms : "
                + String.valueOf(less2000)
                + "  : "
                + String.valueOf((double) less2000 / total * 100)
                + "%");
        System.out.println(
            "2000 < time           : "
                + String.valueOf(timeout2000)
                + "  : "
                + String.valueOf((double) timeout2000 / total * 100)
                + "%");
      }
    } catch (Exception e) {
      logger.error("error:", e);
    }
  }
}
