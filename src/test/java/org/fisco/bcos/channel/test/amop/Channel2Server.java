package org.fisco.bcos.channel.test.amop;

import java.util.HashSet;
import java.util.Set;
import org.fisco.bcos.channel.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Channel2Server {
  static Logger logger = LoggerFactory.getLogger(Channel2Server.class);

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("Param: topic");
      return;
    }

    String topic = args[0];

    logger.debug("init Server");

    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    Service service = context.getBean(Service.class);

    Set<String> topics = new HashSet<String>();
    topics.add(topic);
    service.setTopics(topics);

    PushCallback cb = new PushCallback();

    service.setPushCallback(cb);

    System.out.println("3s...");
    Thread.sleep(1000);
    System.out.println("2s...");
    Thread.sleep(1000);
    System.out.println("1s...");
    Thread.sleep(1000);

    System.out.println("start test");
    System.out.println("===================================================================");

    service.run();
  }
}
