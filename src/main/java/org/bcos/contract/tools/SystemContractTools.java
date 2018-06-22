package org.bcos.contract.tools;

import org.bcos.channel.client.Service;
import org.bcos.contract.source.*;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.abi.datatypes.generated.Uint8;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.GenCredential;

import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.security.KeyPair;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public class SystemContractTools {
	static Logger logger = LoggerFactory.getLogger(SystemContractTools.class);

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

	public static void main(String[] args) throws Exception {
		

		//初始化Service
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();

		System.out.println("===================================================================");

		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);

		Web3j web3 = Web3j.build(channelEthereumService);
		//deploy system contract with privateKey 
		ToolConf toolConf = context.getBean(ToolConf.class);
		Credentials credentials = GenCredential.create(toolConf.getPrivKey());
	
		if (credentials != null) {
			BigInteger gasPrice = new BigInteger("30000000");
			BigInteger gasLimit = new BigInteger("30000000");

			SystemProxy systemProxy = SystemProxy.load(toolConf.getSystemProxyAddress(), web3, credentials, gasPrice,
					gasLimit);
			switch (args[0]) {
			case "SystemProxy":
				SystemProxyTools.processSystemProxy(systemProxy, web3, credentials, gasPrice, gasLimit);
				break;
			case "NodeAction":
				NodeAction nodeAction = NodeAction.load(getAction(systemProxy, "NodeAction"), web3, credentials,
						gasPrice, gasLimit);
				NodeActionTools.processNodeAction(context, nodeAction, args);
				break;
			case "CAAction":
				CAAction caAction = CAAction.load(getAction(systemProxy, "CAAction"), web3, credentials, gasPrice,
						gasLimit);
				CAActionTools.processCAAction(context, caAction, args);
				break;
			case "CNSAction":
				Object[] params = { web3, credentials, gasPrice, gasLimit };
				ContractAbiMgr abiMgr = ContractAbiMgr.load(getAction(systemProxy,"ContractAbiMgr"),
						web3, credentials, gasPrice, gasLimit);
				CNSCmdTool.processCns(abiMgr, args, params);
				break;
			case "ConfigAction":
				ConfigAction configAction = ConfigAction.load(getAction(systemProxy, "ConfigAction"), web3, credentials,
						gasPrice, gasLimit);
				ConfigActionTools.processConfigAction(configAction, args);
				break;
			case "ConsensusControl":
				ConsensusControlMgr mgr = ConsensusControlMgr.load(getAction(systemProxy, "ConsensusControlMgr"), web3,
						credentials, gasPrice, gasLimit);
				Address addr = null;
				try {
					addr = mgr.getAddr().get(); // get ConsensusContrl Addr
				} catch (Exception e) {
					System.out.println("ConsensusControlMgr not set in SystemProxy yet. We could do nothing.");
					break;
				}
				if (args[1].equals("turnoff")) {
					mgr.turnoff().get();
					System.out.println("turnoff the feature of ConsensusControl.");
					break;
				}
				java.math.BigInteger initialWeiValue = new BigInteger("0");
				Object[] deployParams = { web3, credentials, gasPrice, gasLimit, initialWeiValue };

				ConsensusControlTools.processConsensusControl(mgr, new Address(toolConf.getSystemProxyAddress()), args,
						deployParams);
				break;
			default:
				break;
			}
			System.exit(0);
		}
	}
}
