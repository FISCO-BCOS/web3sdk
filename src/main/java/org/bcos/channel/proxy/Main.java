package org.bcos.channel.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		logger.debug("初始化ProxyServer");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		Server server = context.getBean(Server.class);
		
		System.out.println("3...");
		Thread.sleep(1000);
		System.out.println("2...");
		Thread.sleep(1000);
		System.out.println("1...");
		Thread.sleep(1000);

		System.out.println("启动Proxy服务");
		System.out.println("===================================================================");
		
		server.run();
	}
}
