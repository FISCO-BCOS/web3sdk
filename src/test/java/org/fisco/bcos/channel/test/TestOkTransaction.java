package org.fisco.bcos.channel.test;

import static java.lang.System.exit;

import java.math.BigInteger;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.test.contract.Ok;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestOkTransaction {
  public static void main(String[] args) throws Exception {

    String groupId = args[0];
    String method = args[1];
    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    Service service = context.getBean(Service.class);
    service.setGroupId(Integer.parseInt(groupId));
    service.run();
    System.out.println("===================================================================");

    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);
    channelEthereumService.setTimeout(10000);
    Web3j web3 = Web3j.build(channelEthereumService, service.getGroupId());
    BigInteger gasPrice = new BigInteger("300000000");
    BigInteger gasLimit = new BigInteger("300000000");
    BigInteger initialWeiValue = new BigInteger("0");
    Credentials credentials =
        Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

    if ("deploy".equals(method)) {
      System.out.println("####create credential succ, begin deploy contract");

      final Ok okDemo = Ok.deploy(web3, credentials, gasPrice, gasLimit).send();
      if (okDemo != null) {
        System.out.println("deploy success , contract address is: " + okDemo.getContractAddress());
      }
    }
    if ("transaction".equals(method)) {
      String address = args[2];
      Ok okDemo = Ok.load(address, web3, credentials, gasPrice, gasLimit);
      TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
      System.out.println("transaction success , receipt is " + receipt.getTransactionHash());
    }
    exit(0);
  }
}
