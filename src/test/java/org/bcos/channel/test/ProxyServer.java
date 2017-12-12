package org.bcos.channel.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.bcos.channel.proxy.Server;

public class ProxyServer {
static Logger logger = LoggerFactory.getLogger(ProxyServer.class);
	
	public static void main(String[] args) throws Exception {
		logger.debug("初始化Server");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		Server server = context.getBean(Server.class);
		
		System.out.println("3s后开始测试...");
		Thread.sleep(1000);
		System.out.println("2s后开始测试...");
		Thread.sleep(1000);
		System.out.println("1s后开始测试...");
		Thread.sleep(1000);

		System.out.println("开始测试");
		System.out.println("===================================================================");
		
		server.run();
	}
}
