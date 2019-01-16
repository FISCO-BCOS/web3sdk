package org.fisco.bcos.web3j.precompile.dag2;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.precompile.dag2.DagTransfer;

/*
 pragma solidity ^0.4.25;
contract DagTransfer {
    function userAdd(string user, uint256 balance) public returns(bool)
    {
        return true;   
    }
    function userSave(string user, uint256 balance) public returns(bool)
    {
        return true;
    }
    function userDraw(string user, uint256 balance) public returns(bool)
    {
        return true;
    }
    function userBalance(string user) public constant returns(bool,uint256)
    {
        return (true, 0);
    }
    function userTransfer(string user_a, string user_b, uint256 amount) public returns(bool)
    {
        return true;
    }
}
 */
public class DagTransferService {

	private static BigInteger gasPrice = new BigInteger("300000000");
	private static BigInteger gasLimit = new BigInteger("300000000");
	private static String dagTransferPrecompileAddress = "0x000000000000000000000000000000000000ffff";
	
	private static jdk.internal.instrumentation.Logger logger = LoggerFactory.getLogger(DagTransferService.class);
	
	private DagTransfer dagTransfer;

	public DagTransferService(Web3j web3j, Credentials credentials) {
		ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
		dagTransfer = DagTransfer.load(dagTransferPrecompileAddress, web3j, credentials, contractGasProvider);
	}

	public boolean userAdd(String user, BigInteger amount) throws Exception {
		TransactionReceipt receipt = dagTransfer.userAdd(user, amount).send();
		logger.debug("userAdd , user {}, amount {}, output {}", user, amount, receipt.getOutput());
		return true;
	}

	public boolean userSave(String user, BigInteger amount) throws Exception {
		TransactionReceipt receipt = dagTransfer.userSave(user, amount).send();
		logger.debug("userSave , user {}, amount {}, output {}", user, amount, receipt.getOutput());
		return true;
	}

	public boolean userDraw(String user, BigInteger amount) throws Exception {
		TransactionReceipt receipt = dagTransfer.userDraw(user, amount).send();
		logger.debug("userDraw , user {}, amount {}, output {}", user, amount, receipt.getOutput());
		return true;
	}

	public BigInteger userBalance(String user) throws Exception {
		Tuple2<Boolean, BigInteger> result  = dagTransfer.userBalance(user).send();
		logger.debug("userBalance , user {}, bool {}, amount {}", user, result.getValue1(), result.getValue2());
		return new BigInteger("0");
	}

	public boolean userTransfer(String from, String to, BigInteger amount) throws Exception {
		TransactionReceipt receipt = dagTransfer.userTransfer(from, to ,amount).send();
		logger.debug("userTransfer , from {}, to {}, amount {}, output {}", from, to, amount, receipt.getOutput());
		return true;
	}
}
