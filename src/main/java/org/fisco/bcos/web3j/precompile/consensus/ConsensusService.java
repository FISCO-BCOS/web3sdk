package org.fisco.bcos.web3j.precompile.consensus;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthPeers.Peer;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ConsensusService {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");
    private static String MinerPrecompileAddress = "0x0000000000000000000000000000000000001003";

    public void call(String[] args, Web3j web3j, Credentials credentials, int groupId) throws Exception {
        /// get functions
        if (args.length < 1)
            Usage(args);
        String operation = args[1];
        if (args.length < 3)
            Usage(args);
        String nodeId = args[2];
        if (operation.equals("addMiner")) {
            System.out.println("==== addMiner " + nodeId + " of " + groupId);
            addMiner(nodeId, web3j, credentials);
            System.out.println("==== addMiner " + nodeId + " of " + groupId + " END ====");
            System.exit(0);
        }
        if (operation.equals("addObserver")) {
            System.out.println("==== addObserver " + nodeId + " of " + groupId);
            addObserver(nodeId, web3j, credentials);
            System.out.println("==== addObserver " + nodeId + " of " + groupId + " END ====");
            System.exit(0);
        }
        if (operation.equals("remove")) {
            System.out.println("==== remove " + nodeId + " of " + groupId);
            removeNode(nodeId, web3j, credentials);
            System.out.println("==== remove " + nodeId + " of " + groupId + " END ====");
            System.exit(0);
        }
    }

    private void Usage(String[] args) {
        System.out.println("Usage:");
        System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager pbft addMiner ${nodeId}");
        System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager pbft addObserver ${nodeId}");
		System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager pbft remove ${nodeId}");
        System.exit(0);
    }

    public String addMiner(String nodeId, Web3j web3j, Credentials credentials) throws Exception {
    	return addMiner(MinerPrecompileAddress, web3j, credentials, nodeId);
    }

    public String addObserver(String nodeId, Web3j web3j, Credentials credentials) throws Exception {
        return addObserver(MinerPrecompileAddress, web3j, credentials, nodeId);
    }
	
	public String removeNode(String nodeId, Web3j web3j, Credentials credentials) throws Exception {
       return removeNode(MinerPrecompileAddress, web3j, credentials, nodeId);
    }

    private String addMiner(String address, Web3j web3j, Credentials credentials, String nodeId) throws Exception {
    	if(!isPeer(web3j, nodeId))
    	{
    		return PrecompiledCommon.transferToJson(-42);
    	}
    	List<String> minerList = web3j.getMinerList().send().getResult();
    	if(minerList.contains(nodeId))
    	{
    		return PrecompiledCommon.transferToJson(-44);
    	}
    	ConsensusTable consensus = ConsensusTable.load(address, web3j, credentials, gasPrice, gasLimit);
        TransactionReceipt receipt = consensus.addMiner(nodeId).send();
        return PrecompiledCommon.getJsonStr(receipt.getOutput());
    }

	private String addObserver(String address, Web3j web3j, Credentials credentials, String nodeId) throws Exception {
    	if(!isPeer(web3j, nodeId))
    	{
    		return PrecompiledCommon.transferToJson(-42);
    	}
    	List<String> observerList = web3j.getObserverList().send().getResult();
    	if(observerList.contains(nodeId))
    	{
    		return PrecompiledCommon.transferToJson(-45);
    	}
		ConsensusTable consensus = ConsensusTable.load(address, web3j, credentials, gasPrice, gasLimit);
        TransactionReceipt receipt = consensus.addObserver(nodeId).send();
        return PrecompiledCommon.getJsonStr(receipt.getOutput());
    }
	
	private String removeNode(String address, Web3j web3j, Credentials credentials, String nodeId) throws Exception {
		List<String> groupPeers = web3j.ethGroupPeers().send().getResult();
		if(!groupPeers.contains(nodeId))
		{
			return PrecompiledCommon.transferToJson(-43);
		}
		ConsensusTable consensus = ConsensusTable.load(address, web3j, credentials, gasPrice, gasLimit);
        TransactionReceipt receipt = consensus.remove(nodeId).send();
        return PrecompiledCommon.getJsonStr(receipt.getOutput());
    }
	
	private boolean isPeer(Web3j web3j, String nodeId) throws IOException, JsonProcessingException {
		boolean flag = false;
		List<Peer> peers = web3j.ethPeersInfo().send().getResult();
    	for(Peer peer : peers)
    	{
    		if(nodeId.equals(peer.getNodeID()))
    		{
    			flag = true;
    			break;
    		}
    	}
    	return flag;
	}
}