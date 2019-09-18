package org.fisco.bcos.channel.test.amop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.ChannelRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Channel2ClientMultiBin {
    private static Logger logger = LoggerFactory.getLogger(Channel2ClientMulti.class);
    private static final int parameterNum = 2;

    public static void main(String[] args) throws Exception {
        if (args.length < parameterNum) {
            System.out.println("param: target topic total number of request");
            return;
        }
        String topic = args[0];
        Integer count = 1;
        String filename = args[1];

        int flag = -128;
        byte[] byteflag = Channel2ClientBin.intToByteArray(flag);
        int filelength = filename.length();
        byte[] bytelength = Channel2ClientBin.intToByteArray(filelength);
        byte[] bytefilename = filename.getBytes();
        byte[] contentfile = Channel2ClientBin.toByteArrFromFile(filename);
        byte[] content =
                Channel2ClientBin.byteCat(
                        Channel2ClientBin.byteCat(
                                Channel2ClientBin.byteCat(byteflag, bytelength), bytefilename),
                        contentfile);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        logger.debug("init client");

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        Service service = context.getBean(Service.class);
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
            request.setContent(content);

            System.out.println(
                    df.format(LocalDateTime.now())
                            + " multicast request seq:"
                            + String.valueOf(request.getMessageID())
                            + ", filename:"
                            + filename);
            service.asyncMulticastChannelMessage2(request);
        }
    }
}
