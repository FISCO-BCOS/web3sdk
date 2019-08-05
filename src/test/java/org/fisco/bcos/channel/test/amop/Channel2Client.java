package org.fisco.bcos.channel.test.amop;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.ChannelRequest;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Channel2Client {
    static Logger logger = LoggerFactory.getLogger(Channel2Client.class);

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("param: target topic total number of request");
            return;
        }
        String topic = args[0];
        Integer count = Integer.parseInt(args[1]);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        logger.debug("init client");

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
		service.setTopics(topics);

		service.run();

        System.out.println("3s ...");
        Thread.sleep(1000);
        System.out.println("2s ...");
        Thread.sleep(1000);
        System.out.println("1s ...");
        Thread.sleep(1000);

        System.out.println("start test");
        System.out.println("===================================================================");

        for (Integer i = 0; i < count; ++i) {
            Thread.sleep(2000);
            ChannelRequest request = new ChannelRequest();
            request.setToTopic(topic);
            request.setMessageID(service.newSeq());
            request.setTimeout(5000);

            String content = "request seq:" + request.getMessageID();

            request.setContent(content.getBytes());

			System.out.println(df.format(LocalDateTime.now()) + " request seq:" + String.valueOf(request.getMessageID())
					+ ", Content:" + request.getContent() + " content:"
					+ Arrays.toString(request.getContentByteArray()));

            ChannelResponse response = service.sendChannelMessage2(request);

			System.out
					.println(df.format(LocalDateTime.now()) + "response seq:" + String.valueOf(response.getMessageID())
							+ ", ErrorCode:" + response.getErrorCode() + ", Content:" + response.getContent());
		}
	}
}
