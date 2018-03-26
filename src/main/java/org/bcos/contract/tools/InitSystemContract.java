package org.bcos.contract.tools;
import org.bcos.contract.source.*;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.bcos.channel.client.Service;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;

import java.io.BufferedWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InitSystemContract {
	static Logger logger = LoggerFactory.getLogger(InitSystemContract.class);
	static void writeAddress(String fileName, String content)
	{
		Path path = Paths.get(fileName);
		if (!Files.exists(path.getParent()))
		{
			try {
				Files.createDirectories(path.getParent());
			}
			catch ( Exception e)
			{
				System.out.println(e);
			}

		}
		//Use try-with-resource to get auto-closeable writer instance
		try (BufferedWriter writer = Files.newBufferedWriter(path))
		{
			writer.write(content);
		}
		catch ( Exception e)
		{
			System.out.println(e);
		}

	}
	public static void main(String[] args) throws Exception {
		
		//初始化Service
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();
		System.out.println("开始部署...");
		System.out.println("===================================================================");

		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		
		//init web3j
		Web3j web3 = Web3j.build(channelEthereumService);

		//初始化交易签名私钥
		ECKeyPair keyPair = Keys.createEcKeyPair();
		Credentials credentials = Credentials.create(keyPair);

		//初始化交易参数
		java.math.BigInteger gasPrice = new BigInteger("30000000");
		java.math.BigInteger gasLimit = new BigInteger("30000000");
		java.math.BigInteger initialWeiValue = new BigInteger("0");

		ToolConf toolConf = context.getBean(ToolConf.class);

		SystemProxy systemProxy = SystemProxy.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("systemProxy getContractAddress " + systemProxy.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"SystemProxy.address",systemProxy.getContractAddress());

		CAAction caAction = CAAction.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue,new Address(systemProxy.getContractAddress())).get();
		System.out.println("caAction getContractAddress " + caAction.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"CAAction.address",caAction.getContractAddress());

		NodeAction nodeAction = NodeAction.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("nodeAction getContractAddress " + nodeAction.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"NodeAction.address",nodeAction.getContractAddress());

		ConfigAction configAction = ConfigAction.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("configAction getContractAddress " + configAction.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"ConfigAction.address",configAction.getContractAddress());

		FileInfoManager fileInfoManager = FileInfoManager.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("fileInfoManager getContractAddress " + fileInfoManager.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"fileInfoManager.address",fileInfoManager.getContractAddress());

		FileServerManager fileServerManager = FileServerManager.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("fileServerManager getContractAddress " + fileServerManager.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"fileServerManager.address",fileServerManager.getContractAddress());


		ContractAbiMgr contractAbiMgr = ContractAbiMgr.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("contractAbiMgr getContractAddress " + contractAbiMgr.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"ContractAbiMgr.address",contractAbiMgr.getContractAddress());

		AuthorityFilter authorityFilter = AuthorityFilter.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("authorityFilter getContractAddress " + authorityFilter.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"AuthorityFilter.address",authorityFilter.getContractAddress());

		authorityFilter.setName(new Utf8String("AuthorityFilter"));
		authorityFilter.setVersion(new Utf8String("1.0"));

		Group group = Group.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("group getContractAddress " + group.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"Group.address",group.getContractAddress());

		TransactionFilterChain transactionFilterChain = TransactionFilterChain.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("transactionFilterChain getContractAddress " + transactionFilterChain.getContractAddress());
		writeAddress(toolConf.getOutPutpath()+"transactionFilterChain.address",transactionFilterChain.getContractAddress());


		contractAbiMgr.addAbi(new Utf8String("ContractAbiMgr"),new Utf8String("ContractAbiMgr"),
				new Utf8String(""),new Utf8String(ContractAbiMgr.ABI),new Address(contractAbiMgr.getContractAddress()));

		contractAbiMgr.addAbi(new Utf8String("SystemProxy"),new Utf8String("SystemProxy"),
				new Utf8String(""),new Utf8String(SystemProxy.ABI),new Address(contractAbiMgr.getContractAddress()));

		contractAbiMgr.addAbi(new Utf8String("TransactionFilterChain"),new Utf8String("TransactionFilterChain"),
				new Utf8String(""),new Utf8String(TransactionFilterChain.ABI),new Address(contractAbiMgr.getContractAddress()));

		contractAbiMgr.addAbi(new Utf8String("AuthorityFilter"),new Utf8String("AuthorityFilter"),
				new Utf8String(""),new Utf8String(AuthorityFilter.ABI),new Address(contractAbiMgr.getContractAddress()));

		contractAbiMgr.addAbi(new Utf8String("Group"),new Utf8String("Group"),
				new Utf8String(""),new Utf8String(Group.ABI),new Address(contractAbiMgr.getContractAddress()));

		contractAbiMgr.addAbi(new Utf8String("CAAction"),new Utf8String("CAAction"),
				new Utf8String(""),new Utf8String(CAAction.ABI),new Address(contractAbiMgr.getContractAddress()));

		contractAbiMgr.addAbi(new Utf8String("ConfigAction"),new Utf8String("ConfigAction"),
				new Utf8String(""),new Utf8String(ConfigAction.ABI),new Address(contractAbiMgr.getContractAddress()));

		contractAbiMgr.addAbi(new Utf8String("NodeAction"),new Utf8String("NodeAction"),
				new Utf8String(""),new Utf8String(NodeAction.ABI),new Address(contractAbiMgr.getContractAddress()));

		transactionFilterChain.addFilter(new Address(authorityFilter.getContractAddress()));

		systemProxy.setRoute(new Utf8String("TransactionFilterChain"),new Address(transactionFilterChain.getContractAddress()),new Bool(false)).get();
		systemProxy.setRoute(new Utf8String("ConfigAction"),new Address(configAction.getContractAddress()),new Bool(false)).get();
		systemProxy.setRoute(new Utf8String("NodeAction"),new Address(nodeAction.getContractAddress()),new Bool(false)).get();
		systemProxy.setRoute(new Utf8String("CAAction"),new Address(caAction.getContractAddress()),new Bool(false)).get();
		systemProxy.setRoute(new Utf8String("ContractAbiMgr"),new Address(contractAbiMgr.getContractAddress()),new Bool(false)).get();
		systemProxy.setRoute(new Utf8String("FileInfoManager"),new Address(fileInfoManager.getContractAddress()),new Bool(false)).get();
		systemProxy.setRoute(new Utf8String("FileServerManager"),new Address(fileServerManager.getContractAddress()),new Bool(false)).get();

		System.out.println("合约部署完成 系统代理合约:" + systemProxy.getContractAddress());

		System.out.println("-----------------系统路由表----------------------");
		Uint256 routelength = systemProxy.getRouteSize().get();
		for( int i=0;i<routelength.getValue().intValue();i++){
			Utf8String key = systemProxy.getRouteNameByIndex(new Uint256(i)).get();
			List<Type> route = systemProxy.getRoute(key).get();
			System.out.println(" " + i + ")" + key + "=>"+(route.get(0))
					+"," + route.get(1).getValue()
					+ "," + ((BigInteger)(route.get(2).getValue())).intValue());

			if( key.getValue().equals("TransactionFilterChain") ){
				TransactionFilterChain transactionFilterChain1 = TransactionFilterChain.load(route.get(0).toString(), web3, credentials, gasPrice, gasLimit);
				Uint256 filterlength = transactionFilterChain1.getFiltersLength().get();
				for( int j=0; j < filterlength.getValue().intValue(); j++){
					Address filter = transactionFilterChain1.getFilter(new Uint256(j)).get();
					TransactionFilterBase transactionFilterBase = TransactionFilterBase.load(filter.toString(),web3,credentials,gasPrice,gasLimit);

					Utf8String name = transactionFilterBase._name().get();
					Utf8String version = transactionFilterBase._version().get();
					System.out.println("       "+name+"=>"+version+","+filter);
				}
			}
		}
		System.exit(0);
	}
}