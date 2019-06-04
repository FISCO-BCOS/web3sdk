package org.fisco.bcos.channel.test.amop;

import static java.lang.System.exit;

import org.fisco.bcos.channel.proxy.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProxyServer {

    public static void main(String[] args) throws Exception {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Server server1 = (Server) context.getBean("proxyServer1");
        Server server2 = (Server) context.getBean("proxyServer2");
        System.out.println("start testing");
        System.out.println("===================================================================");

        server1.run();
        server2.run();

        Thread.sleep(500000);
        exit(1);
    }
}
