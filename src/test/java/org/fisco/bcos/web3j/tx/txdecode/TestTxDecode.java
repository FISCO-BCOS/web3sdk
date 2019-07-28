package org.fisco.bcos.web3j.tx.txdecode;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestTxDecode {

    public static void main(String[] args) throws Exception {

        TransactionDecoder transactionDecoder =
                TransactionDecoderFactory.buildTransactionDecoder("TableTest");
        TransactionReceipt txReceipt = sentTx();

        // decode input
        System.out.println("===================decode input===================");
        String input = txReceipt.getInput();
        String inputResult1 = transactionDecoder.decodeInputReturnJson(input);
        InputAndOutputResult inputResult2 = transactionDecoder.decodeInputReturnObject(input);

        System.out.println(inputResult1);
        System.out.println(inputResult2);
        System.out.println();

        // decode output
        System.out.println("===================decode output===================");
        String output = txReceipt.getOutput();
        String outputResult1 = transactionDecoder.decodeOutputReturnJson(input, output);

        InputAndOutputResult outputResult2 =
                transactionDecoder.decodeOutputReturnObject(input, output);

        System.out.println(outputResult1);
        System.out.println(outputResult2);
        System.out.println();

        // decode event
        System.out.println("===================decode event===================");
        List<Log> logList = txReceipt.getLogs();
        String logJson = ObjectMapperFactory.getObjectMapper().writeValueAsString(logList);
        String eventResult1 = transactionDecoder.decodeEventReturnJson(logJson);
        Map<String, List<List<EventResultEntity>>> eventResult2 =
                transactionDecoder.decodeEventReturnObject(logList);

        System.out.println(eventResult1);
        System.out.println(eventResult2);

        System.exit(0);
    }

    public static TransactionReceipt sentTx() throws Exception {
        // init the Service
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();
        ECKeyPair keyPair = Keys.createEcKeyPair();
        Credentials credentials = Credentials.create(keyPair);
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        service.setGroupId(1);
        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());

        RemoteCall<TableTest> deploy =
                TableTest.deploy(
                        web3j,
                        credentials,
                        new StaticGasProvider(
                                new BigInteger("30000000"), new BigInteger("30000000")));
        TableTest tableTest = deploy.send();
        tableTest.create().send();

        String name = "fruit";
        int item_id = 1;
        String item_name = "apple";

        RemoteCall<TransactionReceipt> insert =
                tableTest.insert(name, BigInteger.valueOf(item_id), item_name);
        TransactionReceipt txReceipt = insert.send();

        return txReceipt;
    }
}
