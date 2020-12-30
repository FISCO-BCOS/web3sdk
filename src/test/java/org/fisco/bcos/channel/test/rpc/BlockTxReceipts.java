package org.fisco.bcos.channel.test.rpc;

import java.io.IOException;
import java.math.BigInteger;
import java.util.zip.DataFormatException;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockTransactionReceipts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BlockTxReceipts {

    private static final Logger logger = LoggerFactory.getLogger(GroupManager.class);

    public BlockTxReceipts() throws Exception {
        this.web3j = init();
    }

    private Web3j web3j;

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public Web3j init() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
        return web3j;
    }

    public BlockTransactionReceipts getBlockTransactionReceipts(BigInteger blockNumber)
            throws IOException, DataFormatException {
        BlockTransactionReceipts blockTransactionReceipts =
                web3j.getBlockTransactionReceipts(blockNumber).send();
        logger.info(
                "blockNumber: {}, result: {}",
                blockNumber,
                blockTransactionReceipts.getBlockTransactionReceipts());
        return blockTransactionReceipts;
    }

    public BlockTransactionReceipts getBlockTransactionReceipts(
            BigInteger blockNumber, BigInteger offset, BigInteger count)
            throws IOException, DataFormatException {
        BlockTransactionReceipts blockTransactionReceipts =
                web3j.getBlockTransactionReceipts(blockNumber, offset, count).send();
        logger.info(
                "blockNumber: {}, offset: {}, count: {}, result: {}",
                blockNumber,
                offset,
                count,
                blockTransactionReceipts.getBlockTransactionReceipts());
        return blockTransactionReceipts;
    }

    public BlockTransactionReceipts getBlockTransactionReceiptsByHash(String blockHash)
            throws IOException, DataFormatException {
        BlockTransactionReceipts blockTransactionReceipts =
                web3j.getBlockTransactionReceiptsByHash(blockHash).send();
        logger.info(
                "blockHash: {}, result: {}",
                blockHash,
                blockTransactionReceipts.getBlockTransactionReceipts());
        return blockTransactionReceipts;
    }

    public BlockTransactionReceipts getBlockTransactionReceiptsByHash(
            String blockHash, BigInteger offset, BigInteger count)
            throws IOException, DataFormatException {
        BlockTransactionReceipts blockTransactionReceipts =
                web3j.getBlockTransactionReceiptsByHash(blockHash, offset, count).send();
        logger.info(
                "blockHash: {}, offset: {}, count: {}, result: {}",
                blockHash,
                offset,
                count,
                blockTransactionReceipts.getBlockTransactionReceipts());
        return blockTransactionReceipts;
    }

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.BlockTxReceipts getBlockTransactionReceipts blockNumber [offset] [count]");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.BlockTxReceipts getBlockTransactionReceiptsByHash blockHash [offset] [count]");
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            Usage();
        }

        BlockTxReceipts blockTxReceipts = null;
        try {
            blockTxReceipts = new BlockTxReceipts();
            String command = args[0];
            String blockNumberOrBlockHash = args[1];

            BigInteger offset = BigInteger.ZERO;
            BigInteger count = BigInteger.valueOf(-1);
            if (args.length > 2) {
                offset = new BigInteger(args[2]);
            }

            if (args.length > 3) {
                count = new BigInteger(args[3]);
            }

            System.out.println(
                    " ## command: "
                            + command
                            + " ,blockNumberOrBlockHash: "
                            + blockNumberOrBlockHash
                            + " ,offset: "
                            + offset
                            + " ,count: "
                            + count);

            BlockTransactionReceipts blockTransactionReceipts = null;
            switch (command) {
                case "getBlockTransactionReceipts":
                    blockTransactionReceipts =
                            blockTxReceipts.getBlockTransactionReceipts(
                                    new BigInteger(blockNumberOrBlockHash), offset, count);
                    break;
                case "getBlockTransactionReceiptsByHash":
                    blockTransactionReceipts =
                            blockTxReceipts.getBlockTransactionReceiptsByHash(
                                    blockNumberOrBlockHash, offset, count);
                    break;
                default:
                    {
                        System.out.println(" unrecognized methods. ");
                    }
            }

            System.out.println(
                    " ## Result: " + blockTransactionReceipts.getBlockTransactionReceipts());

        } catch (Exception e) {
            System.out.println(" Failed => " + e.getMessage());
            logger.error("e: ", e);
        } finally {
            System.exit(0);
        }
    }
}
