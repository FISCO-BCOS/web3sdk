package org.fisco.bcos.channel.test;

import org.fisco.bcos.channel.test.Miner;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.methods.response.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;

import java.io.IOException;
import java.math.BigInteger;

public class UpdatePBFTNode
{
    //TODO: load from configuration
    public Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
    protected static Web3j web3j;

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");

    public static void main(String[] args) throws Exception
    {
        /// init application context from xml
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        /// init service
        Service service = context.getBean(Service.class);
        service.run();
        System.out.println("==== start ... ");
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        /// init web3j of specified group according to configuration file
        web3j = Web3j.build(channelEthereumService, service.getGroupId());

        /// get functions
        if(args.length < 1)
            Usage();
        String operation = args[1];
        if(args.length < 2)
            Usage();
        String nodeId = args[2];
        if(operation == "add")
        {
            System.out.println("==== add " + nodeId + " to PBFT leaders of " + service.getGroupId());
            AddPBFTNode(nodeId);
            System.out.println("==== add " + nodeId + " to PBFT leaders of " + service.getGroupId() + " END ====");
            System.exit(0);
        }
        if(operation == "remove")
        {
            System.out.println("==== remove " + nodeId + " from PBFT leaders of " + service.getGroupId());
            RemovePBFTNode(nodeId);
            System.out.println("==== remove " + nodeId + " from PBFT leaders of " + service.getGroupId() + " END ====");
            System.exit(0);
        }
    }
    private void Usage(String[] args)
    {
        System.out.println("Usage:");
        System.out.println(args[0] + " add ${nodeId} : add given nodeId to PBFT leaders by calling precompile");
        System.out.println(args[0] + " remove ${nodeId} : remove given nodeId from PBFT leaders by calling precompile");
        System.exit(0);
    }

    private void RemovePBFTNode(String nodeId)
    {
        removeNode("0x1003", web3j, credentials, nodeId);
    }

    private void AddPBFTNode(String nodeId)
    {
        AddNode("0x1003", nodeId);
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
        miner.removePBFTNode(nodeId);
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
        miner.addPBFTNode(nodeId);
    }
}

