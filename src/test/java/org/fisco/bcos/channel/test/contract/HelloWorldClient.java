package org.fisco.bcos.channel.test.contract;

import java.math.BigInteger;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.ExtendedRawTransactonAndGetProofManager;
import org.fisco.bcos.web3j.tx.MerkleProofUtility;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldClient {

    public static Logger logger = LoggerFactory.getLogger(HelloWorldClient.class);
    public static BigInteger gasPrice = new BigInteger("30000000");
    public static BigInteger gasLimit = new BigInteger("30000000");

    public static void main(String[] args) throws Exception {

        // init the Service
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();
        Credentials credentials = GenCredential.create();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(5 * 1000);
        try {
            Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
            HelloWorld helloWorld =
                    HelloWorld.deploy(
                                    web3j,
                                    new ExtendedRawTransactonAndGetProofManager(
                                            web3j, credentials, BigInteger.ONE, BigInteger.ONE),
                                    new StaticGasProvider(gasPrice, gasLimit))
                            .send();

            TransactionReceipt transactionReceipt = helloWorld.set("HelloWorld Test").send();

            logger.info(" TransactionReceipt: {}", transactionReceipt);

            BcosBlock bcosBlock =
                    web3j.getBlockByNumber(
                                    DefaultBlockParameter.valueOf(
                                            transactionReceipt.getBlockNumber()),
                                    false)
                            .send();
            BcosBlock.Block block = bcosBlock.getBlock();
            boolean verifyTxReceipt =
                    MerkleProofUtility.verifyTransactionReceipt(
                            block.getReceiptsRoot(),
                            transactionReceipt,
                            transactionReceipt.getReceiptProof());

            if (!verifyTxReceipt) {
                System.out.println(" Transaction Receipt Verify Failed.");
            } else {
                System.out.println(" Transaction Receipt Verify Successfully.");
            }

            boolean verifyTx =
                    MerkleProofUtility.verifyTransaction(
                            transactionReceipt.getTransactionHash(),
                            transactionReceipt.getTransactionIndex(),
                            block.getTransactionsRoot(),
                            transactionReceipt.getTxProof());

            if (!verifyTx) {
                System.out.println(" Transaction Verify Failed.");
            } else {
                System.out.println(" Transaction Verify Successfully.");
            }

        } catch (Exception e) {
            System.out.println(" Exception: " + e);
        }
        System.exit(0);
    }
}
