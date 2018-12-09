package org.fisco.bcos.channel.test.precompile;

import org.fisco.bcos.channel.test.precompile.Miner;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.methods.response.*;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class UpdatePBFTNode {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");
    private static String MinerPrecompileAddress = "0x000000000000000000000000000000000001003";

    public void call(String[] args, Web3j web3j, Credentials credentials, int groupId) throws Exception {
        /// get functions
        if (args.length < 1)
            Usage(args);
        String operation = args[1];
        if (args.length < 3)
            Usage(args);
        String nodeId = args[2];
        if (operation.equals("add")) {
//            System.out.println("==== add " + nodeId + " to PBFT leaders of " + groupId);
            AddPBFTNode(nodeId, web3j, credentials);
//            System.out.println("==== add " + nodeId + " to PBFT leaders of " + groupId + " END ====");
//            System.exit(0);
        }
        if (operation.equals("remove")) {
//            System.out.println("==== remove " + nodeId + " from PBFT leaders of " + groupId);
            RemovePBFTNode(nodeId, web3j, credentials);
//            System.out.println("==== remove " + nodeId + " from PBFT leaders of " + groupId + " END ====");
//            System.exit(0);
        }
    }

    private void Usage(String[] args) {
        System.out.println("Usage:");
        System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager pbft add ${nodeId} : add given nodeId to PBFT leaders by calling precompile");
        System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager pbft remove ${nodeId} : remove given nodeId from PBFT leaders by calling precompile");
        System.exit(0);
    }

    private void RemovePBFTNode(String nodeId, Web3j web3j, Credentials credentials) throws Exception {
        RemoveNode(MinerPrecompileAddress, web3j, credentials, nodeId);
    }

    private void AddPBFTNode(String nodeId, Web3j web3j, Credentials credentials) throws Exception {
        AddNode(MinerPrecompileAddress, web3j, credentials, nodeId);
    }

    /**
     * remove node id from the PBFT leaders
     *
     * @param address:    the contract address
     * @param web3j:      the web3j object
     * @param credentials : the credential related to the private key to send transactions
     * @param nodeId      : given node id to be removed from the PBFT leaders
     */
    private void RemoveNode(String address, Web3j web3j, Credentials credentials, String nodeId) throws Exception {
        Miner miner = Miner.load(address, web3j, credentials, gasPrice, gasLimit);
        ///TransactionReceipt receipt = miner.remove(nodeId).sendAsync().get(60000, TimeUnit.MILLISECONDS);
        TransactionReceipt receipt = miner.remove(nodeId).send();
//        System.out.println("####get block number from TransactionReceipt: " + receipt.getBlockNumber());
//        System.out.println("####get transaction index from TransactionReceipt: " + receipt.getTransactionIndex());
//        System.out.println("####get gas used from TransactionReceipt: " + receipt.getGasUsed());
    }

    /**
     * add specified node to the PBFT leaders
     *
     * @param address:     the contract address
     * @param web3j:       the web3j object
     * @param credentials: the credential related to the private key to send transactions
     * @param nodeId:      given node id to be added to the PBFT leaders
     */
    private void AddNode(String address, Web3j web3j, Credentials credentials, String nodeId) throws Exception {
        Miner miner = Miner.load(address, web3j, credentials, gasPrice, gasLimit);
        ///TransactionReceipt receipt = miner.add(nodeId).sendAsync().get(60000, TimeUnit.MILLISECONDS);
        TransactionReceipt receipt = miner.add(nodeId).send();
//        System.out.println("####get block number from TransactionReceipt: " + receipt.getBlockNumber());
//        System.out.println("####get transaction index from TransactionReceipt: " + receipt.getTransactionIndex());
//        System.out.println("####get gas used from TransactionReceipt: " + receipt.getGasUsed());

    }
}

