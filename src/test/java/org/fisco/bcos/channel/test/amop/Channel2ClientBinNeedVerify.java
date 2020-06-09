package org.fisco.bcos.channel.test.amop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.ChannelRequest;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Channel2ClientBinNeedVerify {
    private static Logger logger = LoggerFactory.getLogger(Channel2ClientBinNeedVerify.class);

    public static byte[] toByteArrFromFile(String path) throws Exception {

        FileInputStream fileInputStream = null;
        try {
            File inFile = new File(path);
            fileInputStream = new FileInputStream(inFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i;
            while ((i = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(i);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bytes;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] byteCat(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("param: target topic filename of request");
            return;
        }
        String topic = args[0];
        String filename = args[1];
        Integer count = 10;

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

            /*设置为-128表示为传输二进制*/
            int flag = -128;
            byte[] byteflag = intToByteArray(flag);
            int filelength = filename.length();
            byte[] bytelength = intToByteArray(filelength);
            byte[] bytefilename = filename.getBytes();
            byte[] contentfile = toByteArrFromFile(filename);
            byte[] content =
                    byteCat(byteCat(byteCat(byteflag, bytelength), bytefilename), contentfile);
            request.setContent(content);

            logger.info("msg:" + Arrays.toString(content));

            System.out.println(
                    df.format(LocalDateTime.now())
                            + " request seq:"
                            + String.valueOf(request.getMessageID())
                            + " content length:"
                            + content.length);

            ChannelResponse response = service.sendChannelMessageForVerifyTopic(request);

            System.out.println(
                    df.format(LocalDateTime.now())
                            + "response seq:"
                            + String.valueOf(response.getMessageID())
                            + ", ErrorCode:"
                            + response.getErrorCode()
                            + ", Content:"
                            + response.getContent());
            if (response.getErrorCode() != 0) {
                System.out.println("Error message" + response.getErrorMessage());
            }
        }
    }
}
