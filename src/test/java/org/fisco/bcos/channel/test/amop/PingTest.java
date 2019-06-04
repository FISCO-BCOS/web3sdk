package org.fisco.bcos.channel.test.amop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.fisco.bcos.channel.client.ChannelPushCallback;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.ChannelPush;
import org.fisco.bcos.channel.dto.ChannelRequest;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PingTest {
  private static Logger logger = LoggerFactory.getLogger(PingTest.class);

  private List<String> localNodes;
  private List<String> checkNodes;

  private Map<String, Service> services = new HashMap<String, Service>();
  private Map<String, Summary> allSummary = new HashMap<String, Summary>();

  public void start() {
    for (String nodeDesc : localNodes) {
      ChannelConnections connections = new ChannelConnections();
      List<String> nodeArray = new ArrayList<String>();
      nodeArray.add(nodeDesc);

      connections.setConnectionsStr(nodeArray);
      ConcurrentHashMap<String, ChannelConnections> allConnections =
          new ConcurrentHashMap<String, ChannelConnections>();
      allConnections.put("PingTest", connections);
      GroupChannelConnectionsConfig groupChannelConnectionsConfig =
          new GroupChannelConnectionsConfig();
      List<ChannelConnections> ilist = new ArrayList<>();
      ilist.add(connections);
      groupChannelConnectionsConfig.setAllChannelConnections(ilist);

      Service service = new Service();
      service.setAllChannelConnections(groupChannelConnectionsConfig);

      String[] nodeSplit = nodeDesc.split("@");
      if (nodeSplit.length != 2) {
        System.out.println("Invalid node desc: " + nodeDesc);
        System.exit(1);
      }

      Set<String> topics = new HashSet<String>();
      topics.add(nodeSplit[0]);

      service.setTopics(topics);
      service.setOrgID("PingTest");

      service.setPushCallback(
          new ChannelPushCallback() {
            @Override
            public void onPush(ChannelPush push) {
              ChannelResponse response = new ChannelResponse();
              response.setErrorCode(0);
              response.setErrorMessage("");
              response.setMessageID(push.getMessageID());
              response.setContent("Pong");

              push.sendResponse(response);
            }
          });

      try {
        service.run();
      } catch (Exception e) {
        logger.error("ERROR while start", e);
        System.out.println("Connect to node: " + nodeDesc + " failed, exit");

        System.exit(1);
      }

      services.put(nodeSplit[0], service);
    }

    for (String check : checkNodes) {
      allSummary.put(check, new Summary());
    }

    System.out.println("Success, start monitor");
  }

  public void check() {
    for (String nodeDesc : services.keySet()) {
      Service service = services.get(nodeDesc);

      for (String check : checkNodes) {
        if (nodeDesc.equals(check)) {
          continue;
        }

        Summary summary = allSummary.get(check);
        ++summary.totalRequest;

        Long current = System.currentTimeMillis();

        ChannelRequest request = new ChannelRequest();
        request.setToTopic(check);
        request.setMessageID(service.newSeq());
        request.setTimeout(5000);
        request.setContent("Ping");

        ChannelResponse response = service.sendChannelMessage2(request);
        if (response.getErrorCode() != 0) {
          logger.error(
              "AMOP test error: {} {}", response.getErrorCode(), response.getErrorMessage());

          if (response.getErrorCode() == 102) {
            ++summary.totalTimeout;
          } else {
            ++summary.totalFatal;
          }
        } else {
          Long cost = System.currentTimeMillis() - current;

          ++summary.totalSuccess;
          summary.totalTime += cost;

          if (cost < 50) {
            ++summary.less50;
          } else if (cost < 100) {
            ++summary.less100;
          } else if (cost < 200) {
            ++summary.less200;
          } else if (cost < 400) {
            ++summary.less400;
          } else if (cost < 1000) {
            ++summary.less1000;
          } else if (cost < 2000) {
            ++summary.less2000;
          } else {
            ++summary.timeout2000;
          }
        }
      }
    }
  }

  public void outputSummary() {
    for (String check : allSummary.keySet()) {
      Summary summary = allSummary.get(check);

      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

      System.out.println("");
      System.out.println(
          df.format(LocalDateTime.now())
              + " Node: "
              + String.valueOf(check)
              + " --------------------------------------");
      System.out.println("Total request: " + String.valueOf(summary.totalRequest));
      System.out.println("Total success: " + String.valueOf(summary.totalSuccess));

      if (summary.totalRequest > 0) {
        System.out.println(
            "Success rate: "
                + String.valueOf(((double) summary.totalSuccess / summary.totalRequest) * 100)
                + "%");
      }

      if (summary.totalSuccess > 0) {
        System.out.println(
            "Avg time cost: "
                + String.valueOf(((double) summary.totalTime / summary.totalSuccess))
                + " ms");
      }

      System.out.println("Total fail: " + String.valueOf(summary.totalFatal));
      System.out.println("Total timeout: " + String.valueOf(summary.totalTimeout));

      System.out.println("");

      if (summary.totalSuccess > 0) {
        System.out.println("Time area:");
        System.out.println(
            "0    < time <  50ms   : "
                + String.valueOf(summary.less50)
                + "  : "
                + String.valueOf((double) summary.less50 / summary.totalSuccess * 100)
                + "%");
        System.out.println(
            "50   < time <  100ms  : "
                + String.valueOf(summary.less100)
                + "  : "
                + String.valueOf((double) summary.less100 / summary.totalSuccess * 100)
                + "%");
        System.out.println(
            "100  < time <  200ms  : "
                + String.valueOf(summary.less200)
                + "  : "
                + String.valueOf((double) summary.less200 / summary.totalSuccess * 100)
                + "%");
        System.out.println(
            "200  < time <  400ms  : "
                + String.valueOf(summary.less400)
                + "  : "
                + String.valueOf((double) summary.less400 / summary.totalSuccess * 100)
                + "%");
        System.out.println(
            "400  < time <  1000ms : "
                + String.valueOf(summary.less1000)
                + "  : "
                + String.valueOf((double) summary.less1000 / summary.totalSuccess * 100)
                + "%");
        System.out.println(
            "1000 < time <  2000ms : "
                + String.valueOf(summary.less2000)
                + "  : "
                + String.valueOf((double) summary.less2000 / summary.totalSuccess * 100)
                + "%");
        System.out.println(
            "2000 < time           : "
                + String.valueOf(summary.timeout2000)
                + "  : "
                + String.valueOf((double) summary.timeout2000 / summary.totalSuccess * 100)
                + "%");
      }

      System.out.println("");
    }
  }

  public static void main(String[] args) {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    PingTest pingTest = context.getBean(PingTest.class);

    pingTest.start();

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              public void run() {
                _pingTest.outputSummary();
              }

              private PingTest _pingTest = pingTest;
            });

    Integer count = 0;
    while (true) {
      try {
        Thread.sleep(5 * 1000L);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Thread exception");
        e.printStackTrace();

        System.exit(1);
      }

      pingTest.check();
      ++count;
      if (count == Integer.MAX_VALUE) {
        break;
      }

      pingTest.outputSummary();
    }
  }

  public List<String> getLocalNodes() {
    return localNodes;
  }

  public void setLocalNodes(List<String> localNodes) {
    this.localNodes = localNodes;
  }

  public List<String> getCheckNodes() {
    return checkNodes;
  }

  public void setCheckNodes(List<String> checkNodes) {
    this.checkNodes = checkNodes;
  }
}
