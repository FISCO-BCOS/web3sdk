package org.fisco.bcos.channel.test.guomi;

import java.math.BigInteger;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GMErc20Transaction {
  public static void main(String[] args) throws Exception {
    EncryptType encryptType = new EncryptType(1);
    System.out.println(encryptType.getEncryptType());
    String groupId = "1";
    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    Service service = context.getBean(Service.class);
    service.run();
    System.out.println("===================================================================");

    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);
    channelEthereumService.setTimeout(10000);
    Web3j web3 = Web3j.build(channelEthereumService, Integer.parseInt(groupId));
    BigInteger gasPrice = new BigInteger("300000000");
    BigInteger gasLimit = new BigInteger("300000000");

    Credentials credentials1 =
        GenCredential.create("a392604efc2fad9c0b3da43b5f698a2e3f270f170d859912be0d54742275c5f6");

    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    NewSolTest erc20 = NewSolTest.deploy(web3, credentials1, contractGasProvider).send();

    for (int i = 0; i < 1; i++) {
      System.out.println("####contract address is: " + erc20.getContractAddress());
      erc20.transfer("0x0f49a17d17f82da2a7d92ecf19268274150eaf5e", new BigInteger("100")).send();

      BigInteger oldBalance = erc20.balanceOf("0x0f49a17d17f82da2a7d92ecf19268274150eaf5e").send();
      System.out.println(
          "0x0f49a17d17f82da2a7d92ecf19268274150eaf5e balance" + oldBalance.intValue());
    }
    System.exit(0);
  }
}
