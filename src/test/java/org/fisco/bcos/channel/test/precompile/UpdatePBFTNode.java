package org.fisco.bcos.channel.test;

import org.fisco.bcos.channel.test.Miner;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
///import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
///import org.fisco.bcos.web3j.protocol.core.methods.response.*;
///import org.springframework.context.support.ClassPathXmlApplicationContext;
///import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;

import java.math.BigInteger;

public class UpdatePBFTNode
{
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");

    public void call(String[] args, Web3j web3j, Credentials credentials, int groupId)
    {
        /// get functions
        if(args.length < 1)
            Usage(args);
        String operation = args[2];
        if(args.length < 3)
            Usage(args);
        String nodeId = args[3];
        if(operation == "add")
        {
            System.out.println("==== add " + nodeId + " to PBFT leaders of " + groupId);
            AddPBFTNode(nodeId, web3j, credentials);
            System.out.println("==== add " + nodeId + " to PBFT leaders of " + groupId + " END ====");
            System.exit(0);
        }
        if(operation == "remove")
        {
            System.out.println("==== remove " + nodeId + " from PBFT leaders of " + groupId);
            RemovePBFTNode(nodeId, web3j, credentials);
            System.out.println("==== remove " + nodeId + " from PBFT leaders of " + groupId + " END ====");
            System.exit(0);
        }
    }

    private void Usage(String[] args)
    {
        System.out.println("Usage:");
        System.out.println(args[0] + "pbft add ${nodeId} : add given nodeId to PBFT leaders by calling precompile");
        System.out.println(args[0] + "pbft remove ${nodeId} : remove given nodeId from PBFT leaders by calling precompile");
        System.exit(0);
    }

    private void RemovePBFTNode(String nodeId, Web3j web3j, Credentials credentials)
    {
        RemoveNode("0x1003", web3j, credentials, nodeId);
    }

    private void AddPBFTNode(String nodeId, Web3j web3j, Credentials credentials)
    {
        AddNode("0x1003", web3j, credentials, nodeId);
    }

    /**
     * remove node id from the PBFT leaders
     * @param address: the contract address
     * @param web3j: the web3j object
     * @param credentials : the credential related to the private key to send transactions
     * @param nodeId : given node id to be removed from the PBFT leaders
     */
    private void RemoveNode(String address, Web3j web3j, Credentials credentials, String nodeId)
    {
        Miner miner = Miner.load(address, web3j, credentials, gasPrice, gasLimit);
        miner.remove(nodeId);
    }

    /**
     * add specified node to the PBFT leaders
     * @param address: the contract address
     * @param web3j: the web3j object
     * @param credentials: the credential related to the private key to send transactions
     * @param nodeId: given node id to be added to the PBFT leaders
     */
    private void AddNode(String address, Web3j web3j, Credentials credentials, String nodeId)
    {
        Miner miner = Miner.load(address, web3j, credentials, gasPrice, gasLimit);
        miner.add(nodeId);
    }
}

