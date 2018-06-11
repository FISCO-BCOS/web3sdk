package org.bcos.channel.test.cns;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bcos.channel.test.cns.source.CNSTest;
import org.bcos.channel.test.cns.source.CNSTest.SetEventResponse;
import org.bcos.channel.test.cns.source.CNSTest.SetMsgEventResponse;
import org.bcos.channel.test.cns.source.CNSTest.SetUEventResponse;
import org.bcos.contract.source.ContractAbiMgr;
import org.bcos.contract.source.SystemProxy;
import org.bcos.contract.tools.CNSBaseOpr;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

public class Test {
	private Credentials credentials;
	private Web3j web3j;
	
	private BigInteger gasPrice = new BigInteger("99999999");
	private BigInteger gasLimit = new BigInteger("99999999");
	
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public Web3j getWeb3j() {
		return web3j;
	}
	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}
	
	static String getAction(SystemProxy systemProxy,String filename){
		String address = new String();
		try {
			List<Type> route = systemProxy.getRoute(new Utf8String(filename)).get();
			address = route.get(0).toString();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return address;
	}
	
	public void deploy() throws InterruptedException, ExecutionException {
		CNSTest obj = CNSTest.deploy(getWeb3j(), getCredentials(), gasPrice, gasLimit, new BigInteger("0")).get();
		Thread.sleep(3000);
		System.out.print("deploy CNSTest success");
		System.out.print("\t address = " + obj.getContractAddress());
		System.out.print("\t abi = " + obj.ABI);
	}
	
	public void deployAdd2CNS(ContractAbiMgr abiMgr) throws Exception {
		CNSTest obj = CNSTest.deploy(getWeb3j(), getCredentials(), gasPrice, gasLimit, new BigInteger("0")).get();
		System.out.print("deploy CNSTest success");
		System.out.print("\t address = " + obj.getContractAddress());
		System.out.print("\t abi = " + obj.ABI);
		
		String version = "v-0.0.1";
		boolean isExist = CNSBaseOpr.cnsIsExist(abiMgr, "CNSTest", version);
		if(isExist) {
			System.out.println("CNSTest already register, update it.");
			CNSBaseOpr.updateBase(abiMgr, "CNSTest", version, obj.getContractAddress(), obj.ABI);
		} else {
			System.out.println("CNSTest not register, add it.");
			CNSBaseOpr.addBase(abiMgr, "CNSTest", version, obj.getContractAddress(), obj.ABI);
		}
	}
	
	public void getMsg(String address) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.load(address, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<org.bcos.web3j.abi.datatypes.Utf8String> result = instance.getMsg();
		System.out.println("getMsg result = " + result.get().toString());
	}
	
	public void getMsgByCNS(String contractName) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.loadByName(contractName, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<Utf8String> result = instance.getMsg();
		System.out.println("getMsgByCNS result = " + result.get().toString());
	}
	
	public void getU(String address) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.load(address, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<Uint256> result = instance.getInt();
		System.out.println("getU result = " + result.get().getValue().toString());
	}
	
	public void getUByCNS(String contractName) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.loadByName(contractName, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<Uint256> result = instance.getInt();
		System.out.println("getUByCNS result = " + result.get().getValue().toString());
	}
	
	public void get(String address) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.load(address, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<List<Type>> result = instance.get();
		List<Type> result0 = result.get();
		System.out.println("get result = " + result0.get(0).toString() + " ," + result0.get(1).getValue().toString());
	}
	
	public void getByCNS(String contractName) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.loadByName(contractName, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<List<Type>> result = instance.get();
		List<Type> result0 = result.get();
		System.out.println("getByCNS result = " + result0.get(0).toString() + " ," + result0.get(1).getValue().toString());
	}
	
	public void setMsg(String address, String msg) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.load(address, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<TransactionReceipt> result = instance.setMsg(new Utf8String(msg));
		List<SetMsgEventResponse> response = instance.getSetMsgEvents(result.get());
		System.out.println("setMsg event msg = " + response.get(0).msg.toString());
	}
	
	public void setMsgByCNS(String contranctName, String msg) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.loadByName(contranctName, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<TransactionReceipt> result = instance.setMsg(new Utf8String(msg));
		List<SetMsgEventResponse> response = instance.getSetMsgEvents(result.get());
		System.out.println("setMsgByCNS event msg = " + response.get(0).msg.toString());
	}
	
	public void setU(String address, BigInteger b) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.load(address, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<TransactionReceipt> result = instance.setU(new Uint256(b));
		List<SetUEventResponse> response = instance.getSetUEvents(result.get());
		System.out.println("setUint event u = " + response.get(0).u.getValue().toString());
	}
	
	public void setUByCNS(String contranctName, BigInteger b) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.loadByName(contranctName, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<TransactionReceipt> result = instance.setU(new Uint256(b));
		List<SetUEventResponse> response = instance.getSetUEvents(result.get());
		System.out.println("setUIntByCNS event u = " + response.get(0).u.getValue().toString());
	}
	
	public void set(String address,String msg, BigInteger b) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.load(address, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<TransactionReceipt> result = instance.set(new Utf8String(msg),new Uint256(b));
		List<SetEventResponse> response = instance.getSetEvents(result.get());
		System.out.println("set event msg = " + response.get(0).msg.toString() + " ,u = " + response.get(0).u.getValue().toString());
	}
	
	public void setByCNS(String contranctName, String msg, BigInteger b) throws InterruptedException, ExecutionException {
		CNSTest instance = CNSTest.loadByName(contranctName, getWeb3j(), getCredentials(), gasPrice , gasLimit);
		Future<TransactionReceipt> result = instance.set(new Utf8String(msg),new Uint256(b));
		List<SetEventResponse> response = instance.getSetEvents(result.get());
		System.out.println("set event msg = " + response.get(0).msg.toString() + " ,u = " + response.get(0).u.getValue().toString());
	}
}
