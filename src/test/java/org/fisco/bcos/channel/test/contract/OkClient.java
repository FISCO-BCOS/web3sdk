package org.fisco.bcos.channel.test.contract;

import java.math.BigInteger;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class OkClient {
  static Logger logger = LoggerFactory.getLogger(OkClient.class);
  public static Web3j web3j;
  // 初始化交易参数
  public static java.math.BigInteger gasPrice = new BigInteger("1");
  public static java.math.BigInteger gasLimit = new BigInteger("30000000");
  public static ECKeyPair keyPair;
  public static Credentials credentials;
  public static String contractAddress = "";

  /* deploy the contract,get address from blockchain */
  @SuppressWarnings("deprecation")
  public static void deployOk() {

    RemoteCall<Ok> deploy = Ok.deploy(web3j, credentials, gasPrice, gasLimit);
    Ok ok;
    try {
      ok = deploy.send();
      contractAddress = ok.getContractAddress();
      System.out.println("deploy contract address: " + contractAddress);
      logger.info("deploy contract address: " + contractAddress);
      final Resource contractResource = new ClassPathResource("contract.properties");
      PropertiesConfiguration prop = new PropertiesConfiguration(contractResource.getFile());
      prop.setProperty("ok_address", contractAddress);
      prop.save();

      System.out.println("deploy contract successful!");
    } catch (TransactionException e) {
      if ("0x19".equals(e.getStatus())) {
        System.out.println("non-authorized to deploy contracts!");
      } else {
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      System.out.println("deploy failed! " + e.getMessage());
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static void testOk(String[] args) throws Exception {

    final Resource contractResource = new ClassPathResource("contract.properties");
    PropertiesConfiguration prop = new PropertiesConfiguration(contractResource.getFile());
    Object addressObj = prop.getProperty("ok_address");
    if (addressObj != null) {
      contractAddress = (String) addressObj;
    } else {
      deployOk();
    }
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    ;
    Ok ok = Ok.load(contractAddress, web3j, credentials, contractGasProvider);
    // trans
    if ("trans".equals(args[0])) {
      if (args.length == 2) {
        String num = args[1];

        RemoteCall<TransactionReceipt> insert = ok.trans(new BigInteger(num));
        TransactionReceipt txReceipt = insert.send();
        System.out.println(txReceipt.getTransactionHash());
      } else {
        System.out.println("\nPlease enter as follow example:\n 1 trans 5");
      }
    }
    // get
    else if ("get".equals(args[0])) {
      BigInteger num = ok.get().send();
      System.out.println("num = " + num);

    } else {
      System.out.println("\nPlease choose follow commands:\n deploy, trans or get");
    }
  }

  public static void main(String[] args) throws Exception {

    // init the Service
    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    Service service = context.getBean(Service.class);
    service.run(); // run the daemon service
    // init the client keys
    keyPair = Keys.createEcKeyPair();
    credentials = Credentials.create(keyPair);

    logger.info("-----> start test !");
    logger.info("init AOMP ChannelEthereumService");
    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);
    try {
      web3j = Web3j.build(channelEthereumService, Integer.parseInt(args[0]));
    } catch (Exception e) {
      System.out.println("\nPlease provide groupID in the first paramters");
      System.exit(0);
    }

    if (args.length > 1) {
      if ("deploy".equals(args[1])) {
        deployOk();
      } else {
        String[] params = new String[args.length - 1];
        for (int i = 0; i < params.length; i++) params[i] = args[i + 1];
        testOk(params);
      }
    } else {
      System.out.println("\nPlease choose follow commands:\n deploy, trans or get");
    }
    System.exit(0);
  }
}
