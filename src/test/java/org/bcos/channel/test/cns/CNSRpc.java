package org.bcos.channel.test.cns;

import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.bcos.web3j.protocol.core.methods.response.EthGetBalance;
import org.bcos.web3j.protocol.core.methods.response.EthGetCode;
import org.bcos.web3j.protocol.core.methods.response.EthGetStorageAt;
import org.bcos.web3j.protocol.core.methods.response.EthGetTransactionCount;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * 封装CNS的rpc接口
 */
public class CNSRpc {
	
	private Web3j web3j;

	public Web3j getWeb3j() {
		return web3j;
	}

	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}

	private CNSRpc(){
		
	}
	
	private static CNSRpc instance = new CNSRpc();
	
	/**
	 * single instance
	 */
	public static CNSRpc getInstance() {
		return instance;
	}
	
	/**
	 * getTransactionCount
	 */
	public void getTransactionCount(String address) throws InterruptedException, ExecutionException {
	 	EthGetTransactionCount count = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();
	 	System.out.println("getTransactionCount opr, count = " + count.getTransactionCount());
	}
	
	/**
	 * getTransactionCountCNS
	 */
	public void getTransactionCountCNS(String contractName) throws InterruptedException, ExecutionException {
	 	EthGetTransactionCount count = web3j.ethGetTransactionCountCNS(contractName, DefaultBlockParameterName.LATEST).sendAsync().get();
	 	System.out.println("getTransactionCountCNS opr, count = " + count.getTransactionCount());
	}
	
	/**
	 * getBalance
	 */
	public void getBalance(String address) throws InterruptedException, ExecutionException {
		EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
		System.out.println("getBalance opr, balance = " + balance.getBalance());
	}

	
	/**
	 * getBalanceCNS
	 */
	public void getBalanceCNS(String contractName) throws InterruptedException, ExecutionException {
		EthGetBalance balance = web3j.ethGetBalanceCNS(contractName, DefaultBlockParameterName.LATEST).sendAsync().get();
		System.out.println("getBalanceCNS opr, balance = " + balance.getBalance());
	}
	
	/**
	 * getCode
	 */
	public void getCode(String address) throws InterruptedException, ExecutionException {
		EthGetCode code = web3j.ethGetCode(address, DefaultBlockParameterName.LATEST).sendAsync().get();
		System.out.println("getCode opr, code = " + code.getCode());
	}
	
	/**
	 * getCodeCNS
	 */
	public void getCodeCNS(String contractName) throws InterruptedException, ExecutionException {
		EthGetCode code = web3j.ethGetCodeCNS(contractName, DefaultBlockParameterName.LATEST).sendAsync().get();
		System.out.println("getCodeCNS opr, code = " + code.getCode());
	}
	
	/**
	 * getStorageAt
	 */
	public void getStorageAt(String address, BigInteger pos) throws InterruptedException, ExecutionException {
		EthGetStorageAt storage = web3j.ethGetStorageAt(address, pos, DefaultBlockParameterName.LATEST).sendAsync().get();
		System.out.println("getStorageAt opr, storage = " + storage.getData());
	}
	
	/**
	 * getStorageAtCNS
	 */
	public void getStorageAtCNS(String contractName, BigInteger pos) throws InterruptedException, ExecutionException {
		EthGetStorageAt storage = web3j.ethGetStorageAtCNS(contractName, pos, DefaultBlockParameterName.LATEST).sendAsync().get();
		System.out.println("getStorageAtCNS opr, storage = " + storage.getData());
	}
}
