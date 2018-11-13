package org.bcos.channel.test;

import org.bcos.channel.client.Service;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class BasicTest {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");

    public static void main(String[] args) throws Exception {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            Service service = context.getBean(Service.class);
            service.run();
            System.out.println("===================================================================");

            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            channelEthereumService.setChannelService(service);
            channelEthereumService.setTimeout(10000);
            Web3j web3j = Web3j.build(channelEthereumService,1);
            Thread.sleep(2000);
          //  ToolConf toolConf = context.getBean(ToolConf.class);
            Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
            if (credentials == null) {
                System.out.println("create Credentials failed");
                throw new Exception("create Credentials failed");
            }
            testDeployContract(web3j, credentials);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Execute basic test failed");
        }
        Thread.sleep(2000);
        exit(1);
    }


    private static void testDeployContract(Web3j web3j, Credentials credentials) throws Exception {
        Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).send();
        if (okDemo != null) {
            System.out.println("####get nonce from Block: " + web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("0")), true).send().getBlock().getNonce());
            System.out.println("####get block number by index from Block: " + web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("1")), true).send().getBlock().getNumber());

            System.out.println("####contract address is: " + okDemo.getContractAddress());
            TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
            System.out.println("###callback trans success");

            System.out.println("####get block number from TransactionReceipt: " + receipt.getBlockNumber());
            System.out.println("####get transaction index from TransactionReceipt: " + receipt.getTransactionIndex());
            System.out.println("####get gas used from TransactionReceipt: " + receipt.getGasUsed());
          //  System.out.println("####get cumulative gas used from TransactionReceipt: " + receipt.getCumulativeGasUsed());

            BigInteger toBalance = okDemo.get().send();
            System.out.println("============to balance:" + toBalance.intValue());
            Thread.sleep(1000);
            exit(1);
        }
    }
}
