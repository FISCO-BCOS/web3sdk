package org.fisco.bcos.channel.test.guomi;

import java.math.BigInteger;
import java.util.List;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
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

public class GMTableTestClient {

    public static int modevalue = 100;
    public static Logger logger = LoggerFactory.getLogger(GMTableTestClient.class);
    public static Web3j web3j;

    public static BigInteger gasPrice = new BigInteger("30000000");
    public static BigInteger gasLimit = new BigInteger("30000000");
    public static ECKeyPair keyPair;
    public static Credentials credentials;
    public static String contractAddress = "";

    /* deploy the contract,get address from blockchain */
    @SuppressWarnings("deprecation")
    public static void deployTableTest() {

        RemoteCall<TableTest> deploy =
                TableTest.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TableTest tabletest;
        try {
            tabletest = deploy.send();
            contractAddress = tabletest.getContractAddress();
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
                System.out.println(
                        "deploy transaction is abnormal, please check the environment msg:"
                                + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("deploy transaction is abnormal, please check the environment");
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void testTableTest(String[] args) throws Exception {

        final Resource contractResource = new ClassPathResource("contract.properties");
        PropertiesConfiguration prop = new PropertiesConfiguration(contractResource.getFile());
        Object addressObj = prop.getProperty("crud_address");
        if (addressObj != null) {
            contractAddress = (String) addressObj;
        } else {
            deployTableTest();
        }
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        TableTest tabletest =
                TableTest.load(contractAddress, web3j, credentials, contractGasProvider);
        // create table
        if ("create".equals(args[0])) {
            create(tabletest);
        }
        // insert
        else if ("insert".equals(args[0])) {
            insert(args, tabletest);
        }
        // select
        else if ("select".equals(args[0])) {
            select(args, tabletest);
        }
        // update
        else if ("update".equals(args[0])) {
            update(args, tabletest);
        }
        // remove
        else if ("remove".equals(args[0])) {
            remove(args, tabletest);
        } else {
            System.out.println(
                    "\nPlease choose follow commands:\n deploy, create, insert, select, update or remove");
        }
    }

    private static void remove(String[] args, TableTest tabletest) {
        if (args.length == 3) {
            try {
                String name = args[1];
                int item_id = Integer.parseInt(args[2]);
                RemoteCall<TransactionReceipt> remove =
                        tabletest.remove(name, BigInteger.valueOf(item_id));
                TransactionReceipt transactionReceipt = remove.send();
                List<TableTest.RemoveResultEventResponse> removeResultEvents =
                        tabletest.getRemoveResultEvents(transactionReceipt);

                if (removeResultEvents.size() > 0) {
                    TableTest.RemoveResultEventResponse reomveResultEventResponse =
                            removeResultEvents.get(0);
                    logger.info("removeCount = " + reomveResultEventResponse.count.intValue());
                    System.out.println(
                            "removeCount = " + reomveResultEventResponse.count.intValue());
                } else {
                    System.out.println("t_test table does not exist.");
                }
            } catch (Exception e) {
                System.out.println("remove transaction is abnormal, please check the environment");
            }
        } else {
            System.out.println("\nPlease enter as follow example:\n 1 1 remove fruit 1");
        }
    }

    private static void update(String[] args, TableTest tabletest) {
        if (args.length == 4) {
            try {
                String name = args[1];
                int item_id = Integer.parseInt(args[2]);
                String item_name = args[3];
                RemoteCall<TransactionReceipt> update =
                        tabletest.update(name, BigInteger.valueOf(item_id), item_name);
                TransactionReceipt transactionReceipt = update.send();
                List<TableTest.UpdateResultEventResponse> updateResultEvents =
                        tabletest.getUpdateResultEvents(transactionReceipt);

                if (updateResultEvents.size() > 0) {
                    for (int i = 0; i < updateResultEvents.size(); i++) {
                        TableTest.UpdateResultEventResponse updateResultEventResponse =
                                updateResultEvents.get(i);
                        System.out.println(
                                "updateCount = " + updateResultEventResponse.count.intValue());
                        logger.info("updateCount = " + updateResultEventResponse.count.intValue());
                    }
                } else {
                    System.out.println("t_test table does not exist.");
                }
            } catch (Exception e) {
                System.out.println("update transaction is abnormal, please check the environment");
            }
        } else {
            System.out.println("\nPlease enter as follow example:\n 1 1 update fruit 1 orange");
        }
    }

    private static void select(String[] args, TableTest tabletest) {
        if (args.length == 2) {
            try {
                String keyName = args[1];
                Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>> lists =
                        tabletest.select(keyName).send();
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

    private static void insert(String[] args, TableTest tabletest) {
        if (args.length == 4) {
            try {
                String name = args[1];
                int item_id = Integer.parseInt(args[2]);
                String item_name = args[3];

                RemoteCall<TransactionReceipt> insert =
                        tabletest.insert(name, BigInteger.valueOf(item_id), item_name);
                TransactionReceipt txReceipt = insert.send();
                List<TableTest.InsertResultEventResponse> insertResultEvents =
                        tabletest.getInsertResultEvents(txReceipt);
                if (insertResultEvents.size() > 0) {
                    for (int i = 0; i < insertResultEvents.size(); i++) {
                        TableTest.InsertResultEventResponse insertResultEventResponse =
                                insertResultEvents.get(i);
                        logger.info("insertCount = " + insertResultEventResponse.count.intValue());
                        System.out.println(
                                "insertCount = " + insertResultEventResponse.count.intValue());
                    }
                } else {
                    System.out.println("t_test table does not exist.");
                }
            } catch (Exception e) {
                System.out.println("insert transaction is abnormal, please check the environment");
            }
        } else {
            System.out.println("\nPlease enter as follow example:\n 1 1 insert fruit 1 apple");
        }
    }

    private static void create(TableTest tabletest) throws Exception {
        TransactionReceipt receipt = tabletest.create().send();
        List<TableTest.CreateResultEventResponse> createResultEvents =
                tabletest.getCreateResultEvents(receipt);
        if (createResultEvents.size() == 0) {
            System.out.println("create t_test table failed.");
            return;
        }
        TableTest.CreateResultEventResponse createResultEventResponse = createResultEvents.get(0);
        int createCount = createResultEventResponse.count.intValue();
        System.out.println("create table ret:" + createCount);
        switch (createCount) {
            case PrecompiledCommon.PermissionDenied:
                System.out.println("non-authorized to create t_test table.");
                break;
            case PrecompiledCommon.PermissionDenied_RC3:
                System.out.println("non-authorized to create t_test table.");
                break;
            case PrecompiledCommon.TableExist:
                System.out.println("t_test table already exist.");
                break;
            case PrecompiledCommon.Success:
                System.out.println("create t_test table success.");
                break;
            default:
                System.out.println("unknown return value:" + createCount);
                break;
        }
    }

    public static void main(String[] args) throws Exception {

        // init the Service
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.setGroupId(Integer.parseInt(args[0]));
        service.run(); // run the daemon service
        // init the client keys
        keyPair = Keys.createEcKeyPair();
        credentials = GenCredential.create(keyPair.getPrivateKey().toString(16));

        logger.info("-----> start test !");
        logger.info("init AOMP ChannelEthereumService");
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(5 * 1000);
        try {
            web3j = Web3j.build(channelEthereumService, Integer.parseInt(args[0]));
        } catch (Exception e) {
            System.out.println("\nPlease provide groupID in the first parameters");
            System.exit(0);
        }

        if (args.length > 1) {
            if ("deploy".equals(args[1])) {
                deployTableTest();
            } else {
                String[] params = new String[args.length - 1];
                for (int i = 0; i < params.length; i++) params[i] = args[i + 1];
                testTableTest(params);
            }
        } else {
            System.out.println(
                    "\nPlease choose follow commands:\n deploy, create, insert, select, update or remove");
        }
        System.exit(0);
    }
}
