package org.fisco.bcos.channel.test;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase {
  public static ApplicationContext context = null;
  // 初始化交易签名私钥
  public Credentials credentials =
      Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
  protected static Web3j web3j;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // 获取spring配置文件，生成上下文
    context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    //   ((ClassPathXmlApplicationContext) context).start();

    Service service = context.getBean(Service.class);
    service.run();

    System.out.println("start...");
    System.out.println("===================================================================");

    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);

    web3j = Web3j.build(channelEthereumService, service.getGroupId());
    // EthBlockNumber ethBlockNumber = web3.ethBlockNumber().send();
  }

  @AfterClass
  public static void setUpAfterClass() throws Exception {
    ((ClassPathXmlApplicationContext) context).destroy();
  }
}
