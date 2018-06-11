package org.bcos.channel.test.cns;

import java.math.BigInteger;
import java.util.List;

import org.bcos.channel.client.Service;
import org.bcos.contract.source.SystemProxy;
import org.bcos.contract.tools.ToolConf;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.GenCredential;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.contract.source.ContractAbiMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class Main {

	private static String LOGO = "CNS test\n" + "命令参数\n"
			+ "getCode                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getCode address\n"
			+ "getCodeCNS             : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getCodeCNS contractName\n"
			+ "getBalance             : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getBalance address\n"
			+ "getBalanceCNS          : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getBalanceCNS contractName\n"
			+ "getTransactionCount    : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getTransactionCount address\n"
			+ "getTransactionCountCNS : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getTransactionCountCNS contractName\n"
			+ "getStorageAt           : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getStorageAt    address pos\n"
			+ "getStorageAtCNS        : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getStorageAtCNS contractName pos\n\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main deploy\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main deployAddCNS\n\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getMsg address\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getMsgByCNS contractName\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getU address\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getUByCNS contractName\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main get address\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main getByCNS contractName\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main setMsg address string\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main setMsgByCNS contractName string\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main setU address uint\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main setUByCNS contractName uint\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main set address string uint\n"
			+ "CNSTest                : java -cp conf/:apps/*:lib/* org.bcos.channel.test.cns.Main setByCNS contractName string uint\n"
			;
	
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
	
	public static void main(String args[]) throws Exception {
		
		if (args.length == 0) {
			System.out.println(LOGO);
			System.exit(0);
		}
		
		BigInteger gasPrice = new BigInteger("99999999");
		BigInteger gasLimit = new BigInteger("99999999");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();
		System.out.println("CNSTest main ===================================================================");

		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		channelEthereumService.setTimeout(10000);
		Web3j web3 = Web3j.build(channelEthereumService);
		Thread.sleep(2000);
		ToolConf toolConf=context.getBean(ToolConf.class);
		Credentials credentials = GenCredential.create(toolConf.getPrivKey()); 
		
		Test test = new Test();
		test.setWeb3j(web3);
		test.setCredentials(credentials);
		
		// 初始化web3
		CNSRpc.getInstance().setWeb3j(web3);

		switch (args[0]) {
		case "getTransactionCountCNS": {
			CNSRpc.getInstance().getTransactionCountCNS(args[1]);
		}
			break;
		case "getTransactionCount": {
			CNSRpc.getInstance().getTransactionCount(args[1]);
		}
			break;
		case "getBalanceCNS": { 
			CNSRpc.getInstance().getBalanceCNS(args[1]);
		}
			break;
		case "getBalance": {
			CNSRpc.getInstance().getBalance(args[1]);
		}
			break;
		case "getCodeCNS": {
			CNSRpc.getInstance().getCodeCNS(args[1]);
		}
			break;
		case "getCode": {
			CNSRpc.getInstance().getCode(args[1]);
		}
			break;
		case "getStorageAtCNS": {
			CNSRpc.getInstance().getStorageAtCNS(args[1], new BigInteger(args[2]));
		}
			break;
		case "getStorageAt": {
			CNSRpc.getInstance().getStorageAt(args[1], new BigInteger(args[2]));
		}
			break;
		case "deploy":{
			test.deploy();
			break;
		}
		case "deployAddCNS":{
			SystemProxy systemProxy = SystemProxy.load(toolConf.getSystemProxyAddress(), web3, credentials, gasPrice,gasLimit);
			ContractAbiMgr abiMgr = ContractAbiMgr.load(getAction(systemProxy,"ContractAbiMgr"), web3, credentials, gasPrice, gasLimit);
			test.deployAdd2CNS(abiMgr);
			break;
		}
		case "getMsg": {
			test.getMsg(args[1]);
		}
		break;
		case "getMsgByCNS": {
			test.getMsgByCNS(args[1]);
		}
		break;
		case "getU": {
			test.getU(args[1]);
		}
		break;
		case "getUByCNS": {
			test.getUByCNS(args[1]);
		}
		break;
		case "get": {
			test.get(args[1]);
		}
		break;
		case "getByCNS": {
			test.getByCNS(args[1]);
		}
		break;
		case "setMsg": {
			test.setMsg(args[1],args[2]);
		}
		break;
		case "setMsgByCNS": {
			test.setMsgByCNS(args[1], args[2]);
		}
		break;
		case "setU": {
			test.setU(args[1],new BigInteger(args[2]));
		}
		break;
		case "setUByCNS": {
			test.setUByCNS(args[1], new BigInteger(args[2]));
		}
		break;
		case "set": {
			test.set(args[1],args[2],new BigInteger(args[3]));
		}
		break;
		case "setByCNS": {
			test.setByCNS(args[1],args[2],new BigInteger(args[3]));
		}
		break;
		default: {
			System.out.print("unkown opr.");
		}
		}

		System.exit(0);
	}
}
