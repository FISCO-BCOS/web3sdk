package org.fisco.bcos.channel.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.ChannelRequest;
import org.fisco.bcos.channel.dto.ChannelResponse;

public class ChannelClient {
	static Logger logger = LoggerFactory.getLogger(ChannelClient.class);
	
	public static void main(String[] args) throws Exception {
		if(args.length < 3) {
			System.out.println("Param: request         response         total number of request");
			return;
		}
		String from = args[0];
		String to = args[1];
		Integer count = Integer.parseInt(args[2]);
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		logger.debug("init client");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		Service service = context.getBean(Service.class);
		service.run();
		
		//service.setPushCallback(new PushCallback());
		
		System.out.println("3s...");
		Thread.sleep(1000);
		System.out.println("2s...");
		Thread.sleep(1000);
		System.out.println("1s...");
		Thread.sleep(1000);

		System.out.println("start testing");
		System.out.println("===================================================================");
		
		for(Integer i = 0; i < count; ++i) {
			Thread.sleep(2000);
			ChannelRequest request = new ChannelRequest();
			request.setFromOrg(from);
			request.setToOrg(to);
			request.setMessageID(service.newSeq());
			
			request.setContent("request seq:" + request.getMessageID());
			
			System.out.println(df.format(LocalDateTime.now()) + " request seq:" + String.valueOf(request.getMessageID()) + ", Content:" + request.getContent());
			
			ChannelResponse response = service.sendChannelMessage(request);
			
			System.out.println(df.format(LocalDateTime.now()) + "response seq:" + String.valueOf(response.getMessageID()) + ", ErrorCode:" + response.getErrorCode() + ", Content:" + response.getContent());
		}
	}
}
