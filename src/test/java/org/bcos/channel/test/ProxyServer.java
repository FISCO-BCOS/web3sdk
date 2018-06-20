package org.bcos.channel.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.bcos.channel.proxy.Server;

public class ProxyServer {
static Logger logger = LoggerFactory.getLogger(ProxyServer.class);
	
	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Server server = context.getBean(Server.class);
		System.out.println("start testing");
		System.out.println("===================================================================");
		
		server.run();
	}
}
