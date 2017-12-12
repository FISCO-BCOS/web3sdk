package org.bcos.channel.test;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.bcos.channel.client.Service;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;

import java.math.BigInteger;

public class Ethereum {
	static Logger logger = LoggerFactory.getLogger(Ethereum.class);
	
	public static void main(String[] args) throws Exception {
		
		//初始化Service
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();

		Thread.sleep(3000);
		System.out.println("开始测试...");
		System.out.println("===================================================================");
		
		logger.info("初始化AOMP的ChannelEthereumService");
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		
		//使用AMOP消息信道初始化web3j
		Web3j web3 = Web3j.build(channelEthereumService);

		logger.info("调用web3的getBlockNumber接口");
		EthBlockNumber ethBlockNumber = web3.ethBlockNumber().sendAsync().get();
		logger.info("获取ethBlockNumber:{}", ethBlockNumber);

		//初始化交易签名私钥
		ECKeyPair keyPair = Keys.createEcKeyPair();
		Credentials credentials = Credentials.create(keyPair);

		//初始化交易参数
		java.math.BigInteger gasPrice = new BigInteger("30000000");
		java.math.BigInteger gasLimit = new BigInteger("30000000");
		java.math.BigInteger initialWeiValue = new BigInteger("0");

		//部署合约
		Ok ok = Ok.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("Ok getContractAddress " + ok.getContractAddress());
		
		//调用合约接口
		java.math.BigInteger Num = new BigInteger("999");
		Uint256 num = new Uint256(Num);
		TransactionReceipt receipt = ok.trans(num).get();
		System.out.println("receipt transactionHash" + receipt.getTransactionHash());

		//查询合约数据
		num = ok.get().get();
		System.out.println("ok.get() " + num.getValue());
	}
}