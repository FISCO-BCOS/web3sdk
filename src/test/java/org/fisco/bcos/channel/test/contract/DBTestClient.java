package org.fisco.bcos.channel.test.contract;

import java.math.BigInteger;
import java.util.List;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.test.contract.DBTest.CreateResultEventResponse;
import org.fisco.bcos.channel.test.contract.DBTest.InsertResultEventResponse;
import org.fisco.bcos.channel.test.contract.DBTest.RemoveResultEventResponse;
import org.fisco.bcos.channel.test.contract.DBTest.UpdateResultEventResponse;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DBTestClient {
  static Logger logger = LoggerFactory.getLogger(DBTestClient.class);
  public static Web3j web3j;
  // 初始化交易参数
  public static java.math.BigInteger gasPrice = new BigInteger("1");
  public static java.math.BigInteger gasLimit = new BigInteger("30000000");
  public static ECKeyPair keyPair;
  public static Credentials credentials;
  public static String contractAddress = "";

  /* deploy the contract,get address from blockchain */
  @SuppressWarnings("deprecation")
  public static void deployDBTest() {

    RemoteCall<DBTest> deploy = DBTest.deploy(web3j, credentials, gasPrice, gasLimit);
    DBTest dbtest;
    try {
      dbtest = deploy.send();
      contractAddress = dbtest.getContractAddress();
      System.out.println("deploy contract address: " + contractAddress);
      logger.info("deploy contract address: " + contractAddress);
      final Resource contractResource = new ClassPathResource("contract.properties");
      PropertiesConfiguration prop = new PropertiesConfiguration(contractResource.getFile());
      prop.setProperty("crud_address", contractAddress);
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
  public static void testDBTest(String[] args) throws Exception {

    final Resource contractResource = new ClassPathResource("contract.properties");
    PropertiesConfiguration prop = new PropertiesConfiguration(contractResource.getFile());
    Object addressObj = prop.getProperty("crud_address");
    if (addressObj != null) {
      contractAddress = (String) addressObj;
    } else {
      deployDBTest();
    }
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    ;
    DBTest dbtest = DBTest.load(contractAddress, web3j, credentials, contractGasProvider);
    // create table
    if ("create".equals(args[0])) {
      TransactionReceipt receipt = dbtest.create().send();
      List<CreateResultEventResponse> createResultEvents = dbtest.getCreateResultEvents(receipt);
      if (createResultEvents.size() == 0) {
        System.out.println("create t_test table failed.");
        return;
      }
      CreateResultEventResponse createResultEventResponse = createResultEvents.get(0);
      int createCount = createResultEventResponse.count.intValue();
      switch (createCount) {
        case 255:
          System.out.println("non-authorized to create t_test table.");
          break;
        case 0:
          System.out.println("t_test table already exist.");
          break;
        case 1:
          System.out.println("create t_test table completed.");
          break;
      }

    }
    // insert
    else if ("insert".equals(args[0])) {
      if (args.length == 4) {
        String name = args[1];
        int item_id = Integer.parseInt(args[2]);
        String item_name = args[3];

        RemoteCall<TransactionReceipt> insert =
            dbtest.insert(name, BigInteger.valueOf(item_id), item_name);
        TransactionReceipt txReceipt = insert.send();
        List<InsertResultEventResponse> insertResultEvents =
            dbtest.getInsertResultEvents(txReceipt);
        if (insertResultEvents.size() > 0) {
          for (int i = 0; i < insertResultEvents.size(); i++) {
            InsertResultEventResponse insertResultEventResponse = insertResultEvents.get(i);
            logger.info("insertCount = " + insertResultEventResponse.count.intValue());
            System.out.println("insertCount = " + insertResultEventResponse.count.intValue());
          }
        } else {
          System.out.println("t_test table does not exist.");
        }
      } else {
        System.out.println("\nPlease enter as follow example:\n 1 1 insert fruit 1 apple");
      }
    }
    // select
    else if ("select".equals(args[0])) {
      if (args.length == 2) {
        try {
          String keyName = args[1];
          Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>> lists = dbtest.read(keyName).send();
          List<byte[]> value1 = lists.getValue1();
          List<BigInteger> value2 = lists.getValue2();
          List<byte[]> value3 = lists.getValue3();
          logger.info("record numbers = " + value1.size());
          System.out.println("record numbers = " + value1.size());
          for (int i = 0; i < value1.size(); i++) {
            String name = new String(value1.get(i));
            logger.info("name = " + name);
            System.out.println("name = " + name);
            int item_id = value2.get(i).intValue();
            logger.info("item_id = " + item_id);
            System.out.println("item_id = " + item_id);
            String item_name = new String(value3.get(i));
            logger.info("item_name = " + item_name);
            System.out.println("item_name = " + item_name);
          }
        } catch (Exception e) {
          logger.info("record numbers = 0");
          System.out.println("record numbers = 0");
        }
      } else {
        System.out.println("\nPlease enter as follow example:\n 1 1 select fruit");
      }
    }
    // update
    else if ("update".equals(args[0])) {
      if (args.length == 4) {
        String name = args[1];
        int item_id = Integer.parseInt(args[2]);
        String item_name = args[3];
        RemoteCall<TransactionReceipt> update =
            dbtest.update(name, BigInteger.valueOf(item_id), item_name);
        TransactionReceipt transactionReceipt = update.send();
        List<UpdateResultEventResponse> updateResultEvents =
            dbtest.getUpdateResultEvents(transactionReceipt);

        if (updateResultEvents.size() > 0) {
          for (int i = 0; i < updateResultEvents.size(); i++) {
            UpdateResultEventResponse updateResultEventResponse = updateResultEvents.get(i);
            System.out.println("updateCount = " + updateResultEventResponse.count.intValue());
            logger.info("updateCount = " + updateResultEventResponse.count.intValue());
          }
        } else {
          System.out.println("t_test table does not exist.");
        }
      } else {
        System.out.println("\nPlease enter as follow example:\n 1 1 update fruit 1 orange");
      }
    }
    // remove
    else if ("remove".equals(args[0])) {
      if (args.length == 3) {
        String name = args[1];
        int item_id = Integer.parseInt(args[2]);
        RemoteCall<TransactionReceipt> remove = dbtest.remove(name, BigInteger.valueOf(item_id));
        TransactionReceipt transactionReceipt = remove.send();
        List<RemoveResultEventResponse> removeResultEvents =
            dbtest.getRemoveResultEvents(transactionReceipt);

        if (removeResultEvents.size() > 0) {
          RemoveResultEventResponse reomveResultEventResponse = removeResultEvents.get(0);
          logger.info("removeCount = " + reomveResultEventResponse.count.intValue());
          System.out.println("removeCount = " + reomveResultEventResponse.count.intValue());
        } else {
          System.out.println("t_test table does not exist.");
        }
      } else {
        System.out.println("\nPlease enter as follow example:\n 1 1 remove fruit 1");
      }
    } else {
      System.out.println(
          "\nPlease choose follow commands:\n deploy, create, insert, select, update or remove");
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
        deployDBTest();
      } else {
        String[] params = new String[args.length - 1];
        for (int i = 0; i < params.length; i++) params[i] = args[i + 1];
        testDBTest(params);
      }
    } else {
      System.out.println(
          "\nPlease choose follow commands:\n deploy, create, insert, select, update or remove");
    }
    System.exit(0);
  }
}
