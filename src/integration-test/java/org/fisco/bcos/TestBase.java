package org.fisco.bcos;

import java.math.BigInteger;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.test.guomi.Ok;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase {
  public static ApplicationContext context = null;
  public static Credentials credentials =
      Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
  protected static Web3j web3j;
  protected static BigInteger gasPrice = new BigInteger("30000000");
  protected static BigInteger gasLimit = new BigInteger("30000000");
  protected static String address;
  protected static BigInteger blockNumber;
  protected static String blockHash;
  protected static String txHash;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

  	context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    Service service = context.getBean(Service.class);
    service.run();

    System.out.println("start...");
    System.out.println("===================================================================");

    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);

    web3j = Web3j.build(channelEthereumService, service.getGroupId());
   
    Ok ok = Ok.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
    address = ok.getContractAddress();
    blockNumber = ok.getTransactionReceipt().get().getBlockNumber();
    blockHash = ok.getTransactionReceipt().get().getBlockHash();
    txHash = ok.getTransactionReceipt().get().getTransactionHash();
  }

  @AfterClass
  public static void setUpAfterClass() throws Exception {
    ((ClassPathXmlApplicationContext) context).destroy();
  }
}
