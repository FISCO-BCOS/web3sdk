package org.bcos.contract.tools;

import org.bcos.channel.client.Service;
import org.bcos.contract.source.*;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.abi.datatypes.generated.Uint8;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public class SystemContractTools {
	static Logger logger = LoggerFactory.getLogger(SystemContractTools.class);
	private static String LOGO = "\n"
			+ "命令输入参考如下！ \n"
			+ "Usage: java  -cp 'conf/:apps/*:lib/*'  org.bcos.contract.tools.SystemContractTools SystemProxy  \n"
			+ "Usage: java  -cp 'conf/:apps/*:lib/*'  org.bcos.contract.tools.SystemContractTools AuthorityFilter  \n"
			+ "Usage: java  -cp 'conf/:apps/*:lib/*'  org.bcos.contract.tools.SystemContractTools NodeAction all|registerNode|cancelNode \n"
			+ "Usage: java  -cp 'conf/:apps/*:lib/*'  org.bcos.contract.tools.SystemContractTools CAAction all|update|updateStatus \n"
			+ "Usage: java  -cp 'conf/:apps/*:lib/*'  org.bcos.contract.tools.SystemContractTools ConfigAction get|set \n";

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

		ToolConf toolConf = context.getBean(ToolConf.class);

		System.out.println(LOGO);
		System.out.println("===================================================================");

		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);

		Web3j web3 = Web3j.build(channelEthereumService);

		ECKeyPair keyPair = Keys.createEcKeyPair();
		Credentials credentials = Credentials.create(keyPair);

		BigInteger gasPrice = new BigInteger("30000000");
		BigInteger gasLimit = new BigInteger("30000000");

		SystemProxy systemProxy = SystemProxy.load(toolConf.getSystemProxyAddress(),web3,credentials,gasPrice,gasLimit);
		switch (args[0]) {
			case "SystemProxy":
				SystemProxyTools.processSystemProxy(systemProxy, web3, credentials, gasPrice, gasLimit);
				break;
			case "AuthorityFilter":
				AuthorityFilter authorityFilter  = AuthorityFilter.load(getAction(systemProxy,"AuthorityFilter"),
						web3, credentials, gasPrice, gasLimit);
				AuthorityFilterTools.processAuthorityFilter(authorityFilter, args);
				break;
			case "NodeAction":
				NodeAction nodeAction  = NodeAction.load(getAction(systemProxy,"NodeAction"),
						web3, credentials, gasPrice, gasLimit);
				NodeActionTools.processNodeAction(context, nodeAction, args);
				break;
			case "CAAction":
				CAAction caAction  = CAAction.load(getAction(systemProxy,"CAActions"),
						web3, credentials, gasPrice, gasLimit);
				CAActionTools.processCAAction(context, caAction, args);
				break;
			case "ConfigAction":
				ConfigAction configAction  = ConfigAction.load(getAction(systemProxy,"ConfigAction"),
						web3, credentials, gasPrice, gasLimit);
				ConfigActionTools.processConfigAction(configAction,args);
				break;
			default:
				break;
		}
		System.exit(0);
	}
}