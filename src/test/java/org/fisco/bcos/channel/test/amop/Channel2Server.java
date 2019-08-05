package org.fisco.bcos.channel.test.amop;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.fisco.bcos.channel.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Channel2Server {
    private static Logger logger = LoggerFactory.getLogger(Channel2Server.class);

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Param: topic");
            return;
        }

        String topic = args[0];

        logger.debug("init Server");

		String currentPath = System.getProperty("user.dir");
		String pukPath = currentPath + "/conf/" + "puk.properties";
		String prkPath = currentPath + "/conf/" + "prk.properties";
		logger.info("pukPath:{} prkPath:{}", pukPath, prkPath);
		ConcurrentHashMap<String, Set<String>> topic2PublicKey = AmopCertCfgUtil.loadPukFromCfg(pukPath);
		ConcurrentHashMap<String, String> topic2PrivateKey = AmopCertCfgUtil.loadPrkFromCfg(prkPath);
			
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		Service service = context.getBean(Service.class);
		service.initPrivateAndPublicKey(topic2PublicKey, topic2PrivateKey);
		
		Set<String> topics = AmopCertCfgUtil.getTopicNeed2Send(topic2PrivateKey, topic2PublicKey);
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
