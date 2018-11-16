package org.bcos.channel.test;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.bcos.channel.proxy.Server;

import static java.lang.System.exit;

public class ProxyServer {

	@Ignore
	@Test
	public void testProxyServer() throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Server server = context.getBean(Server.class);
		System.out.println("start testing");
		System.out.println("===================================================================");
		
		server.run();
		Thread.sleep(5000);
		exit(1);

	}
}
