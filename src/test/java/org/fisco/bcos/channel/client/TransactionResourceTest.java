package org.fisco.bcos.channel.client;

import java.math.BigInteger;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.MerkleProofUnit;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceiptWithProof;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionWithProof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransactionResourceTest {
    static Logger logger = LoggerFactory.getLogger(TransactionResourceTest.class);
    public static Web3j web3j;

    public static ECKeyPair keyPair;
    public static Credentials credentials;

    public static void main(String[] args) throws Exception {

        // init the Service
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.setGroupId(Integer.parseInt(args[0]));
        service.run(); // run the daemon service
        // init the client keys
        keyPair = Keys.createEcKeyPair();
        credentials = Credentials.create(keyPair);

        logger.info("-----> start TransactionResourceTest !");
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        try {
            web3j = Web3j.build(channelEthereumService, Integer.parseInt(args[0]));
        } catch (Exception e) {
            System.out.println("Please provide groupID in the first paramters");
            System.exit(1);
        }

        try {
            if (args.length == 4) {
                BigInteger blockNumber = new BigInteger(args[2]);
                BigInteger transactionIndex = new BigInteger(args[3]);
                DefaultBlockParameter defaultBlockParameter =
                        DefaultBlockParameter.valueOf(blockNumber);

                BcosTransaction bcosTransaction =
                        web3j.getTransactionByBlockNumberAndIndex(
                                        defaultBlockParameter, transactionIndex)
                                .send();
                String transactionHash = bcosTransaction.getTransaction().get().getHash();

                BcosBlock block = web3j.getBlockByNumber(defaultBlockParameter, true).send();
                String transactionsRootHash = block.getBlock().getTransactionsRoot();
                System.out.println("transactionsRoot: " + transactionsRootHash);
                String receiptRootHash = block.getBlock().getReceiptsRoot();
                System.out.println("receiptRootHash : " + receiptRootHash);

                TransactionResource transactionResource = new TransactionResource(web3j);

                if ("getTrans".equals(args[1])) {
                    TransactionWithProof transactionWithProof =
                            web3j.getTransactionByHashWithProof(transactionHash).send();
                    if (transactionWithProof == null) {
                        System.out.println("transactionWithProof == null");
                        System.exit(1);
                    }

                    System.out.println("***********Test getTransactionByHashWithProof************");
                    List<MerkleProofUnit> transactionProof =
                            transactionWithProof.getTransactionWithProof().getTxProof();
                    System.out.println("transactionProof:" + transactionProof);

                    TransactionWithProof newTransactionWithProof =
                            transactionResource.getTransactionWithProof(
                                    transactionHash, transactionsRootHash);
                    if (newTransactionWithProof == null) {
                        System.out.println("Test getTransactionByHashWithProof failed!");

                    } else {
                        System.out.println(
                                newTransactionWithProof.getTransactionWithProof().toString());
                        System.out.println("Test getTransactionByHashWithProof successfully!");
                    }

                } else if ("getReceipt".equals(args[1])) {
                    TransactionReceiptWithProof transactionReceiptWithProof =
                            web3j.getTransactionReceiptByHashWithProof(transactionHash).send();
                    if (transactionReceiptWithProof == null) {
                        System.out.println("transactionReceiptWithProof == null");
                        System.exit(1);
                    }

                    List<MerkleProofUnit> transactionReceiptProof =
                            transactionReceiptWithProof
                                    .getTransactionReceiptWithProof()
                                    .getReceiptProof();
                    System.out.println("receiptProof:" + transactionReceiptProof);

                    System.out.println("***********Test getReceiptByHashWithProof************");
                    TransactionReceiptWithProof newTransactionReceiptWithProof =
                            transactionResource.getTransactionReceiptWithProof(
                                    transactionHash, receiptRootHash);
                    if (newTransactionReceiptWithProof == null) {
                        System.out.println("Test getReceiptByHashWithProof failed!");

                    } else {
                        System.out.println(
                                newTransactionReceiptWithProof
                                        .getTransactionReceiptWithProof()
                                        .toString());
                        System.out.println("Test getReceiptByHashWithProof successfully!");
                    }
                } else if ("getAll".equals(args[1])) {
                    System.out.println(
                            "***********Test getTransactionAndReceiptWithProof************");
                    ImmutablePair<TransactionWithProof, TransactionReceiptWithProof> pair =
                            transactionResource.getTransactionAndReceiptWithProof(
                                    transactionHash, transactionsRootHash, receiptRootHash);
                    if (pair == null) {
                        System.out.println("Test getAll failed!");
                    } else {
                        System.out.println("Test getAll successful!");
                    }
                } else {
                    System.out.println("Command not found!");
                }
            } else {
                System.out.println("Please choose follow commands:\n getTrans or getReceipt!");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }

        System.exit(0);
    }
}
