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
    private static String MinerPrecompileAddress = "0x0000000000000000000000000000000000001003";


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