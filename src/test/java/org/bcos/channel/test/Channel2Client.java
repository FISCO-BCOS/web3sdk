package org.bcos.channel.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.bcos.channel.client.Service;
import org.bcos.channel.dto.ChannelRequest;
import org.bcos.channel.dto.ChannelResponse;

public class Channel2Client {
	static Logger logger = LoggerFactory.getLogger(Channel2Client.class);
	
	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
			System.out.println("参数: 目标topic         总请求量");
			return;
		}
		String topic = args[0];
		Integer count = Integer.parseInt(args[1]);
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		logger.debug("初始化链上链下客户端");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		Service service = context.getBean(Service.class);
		service.run(); //阻塞到连接成功，或通知
		
		System.out.println("3s后开始测试...");
		Thread.sleep(1000);
		System.out.println("2s后开始测试...");
		Thread.sleep(1000);
		System.out.println("1s后开始测试...");
		Thread.sleep(1000);

		System.out.println("开始测试");
		System.out.println("===================================================================");
		
		for(Integer i = 0; i < count; ++i) {
			Thread.sleep(2000);
			ChannelRequest request = new ChannelRequest();
			request.setToTopic(topic);
			request.setMessageID(service.newSeq());
			request.setTimeout(5000);
			
			request.setContent("request seq:" + request.getMessageID());
			
			System.out.println(df.format(LocalDateTime.now()) + " 发送请求 seq:" + String.valueOf(request.getMessageID()) + ", 内容:" + request.getContent());
			
			ChannelResponse response = service.sendChannelMessage2(request);
			
			System.out.println(df.format(LocalDateTime.now()) + "收到回包 seq:" + String.valueOf(response.getMessageID()) + ", 错误码:" + response.getErrorCode() + ", 内容:" + response.getContent());
		}
	}
}
