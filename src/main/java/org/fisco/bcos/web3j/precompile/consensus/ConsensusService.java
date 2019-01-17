package org.fisco.bcos.web3j.precompile.consensus;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthPeers.Peer;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ConsensusService {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static String ConsensusPrecompileAddress = "0x0000000000000000000000000000000000001003";
    private Web3j web3j;
    private Consensus consensus;
    
	public ConsensusService(Web3j web3j, Credentials credentials) {
		ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
		this.web3j = web3j;
		consensus = Consensus.load(ConsensusPrecompileAddress, web3j, credentials, contractGasProvider);
	}

	public String addMiner(String nodeID) throws Exception {
    	if(!isValidNodeID(nodeID))
    	{
    		return PrecompiledCommon.transferToJson(-42);
    	}
    	List<String> minerList = web3j.getMinerList().send().getResult();
    	if(minerList.contains(nodeID))
    	{
    		return PrecompiledCommon.transferToJson(-44);
    	}
        TransactionReceipt receipt = consensus.addMiner(nodeID).send();
        return PrecompiledCommon.getJsonStr(receipt.getOutput());
    }

	public String addObserver(String nodeID) throws Exception {
    	if(!isValidNodeID(nodeID))
    	{
    		return PrecompiledCommon.transferToJson(-42);
    	}
    	List<String> observerList = web3j.getObserverList().send().getResult();
    	if(observerList.contains(nodeID))
    	{
    		return PrecompiledCommon.transferToJson(-45);
    	}
        TransactionReceipt receipt = consensus.addObserver(nodeID).send();
        return PrecompiledCommon.getJsonStr(receipt.getOutput());
    }
	
	public String removeNode(String nodeId) throws Exception {
		List<String> groupPeers = web3j.ethGroupPeers().send().getResult();
		if(!groupPeers.contains(nodeId))
		{
			return PrecompiledCommon.transferToJson(-43);
		}
        TransactionReceipt receipt = consensus.remove(nodeId).send();
        return PrecompiledCommon.getJsonStr(receipt.getOutput());
    }
	
	private boolean isValidNodeID(String _nodeID) throws IOException, JsonProcessingException {
		boolean flag = false;
		List<String> nodeIDs = web3j.getNodeIDList().send().getResult();
    	for(String nodeID : nodeIDs)
    	{
    		if(_nodeID.equals(nodeID))
    		{
    			flag = true;
    			break;
    		}
    	}
    	return flag;
	}
}