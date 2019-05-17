package org.fisco.bcos.channel.test.amop;

import static java.lang.System.exit;

import org.fisco.bcos.channel.proxy.Server;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProxyServer {

    public static void main(String[] args) throws Exception {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Server server = context.getBean(Server.class);
        System.out.println("start testing");
        System.out.println("===================================================================");

        server.run();
        Thread.sleep(500000);
        exit(1);
    }
}
