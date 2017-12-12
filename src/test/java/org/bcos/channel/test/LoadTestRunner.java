package org.bcos.channel.test;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.bcos.channel.client.Service;
import org.bcos.channel.dto.ChannelRequest;
import org.bcos.channel.dto.ChannelResponse;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Int256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.RawTransactionManager;

public class LoadTestRunner {
	static Logger logger = LoggerFactory.getLogger(LoadTestRunner.class);
	
	public static void main(String[] args) throws Exception {
		logger.debug("初始化");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		Service service = context.getBean(Service.class);
		service.run();

		System.out.println("3s后开始测试...");
		Thread.sleep(1000);
		System.out.println("2s后开始测试...");
		Thread.sleep(1000);
		System.out.println("1s后开始测试...");
		Thread.sleep(1000);

		System.out.println("开始测试");
		System.out.println("===================================================================");
		
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		channelEthereumService.setTimeout(10000);
		
		Web3j web3 = Web3j.build(channelEthereumService);
		
		Thread.sleep(2000);
		
		//先部署一个测试合约
		ECKeyPair keyPair = Keys.createEcKeyPair();
		Credentials credentials = Credentials.create(keyPair);
		RawTransactionManager rawTransactionManager = new RawTransactionManager(web3,credentials);

		java.math.BigInteger gasPrice = new BigInteger("30000000");
		java.math.BigInteger gasLimit = new BigInteger("30000000");
		java.math.BigInteger initialWeiValue = new BigInteger("0");

		LoadTest loadTest = LoadTest.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
		System.out.println("LoadTest getContractAddress " + loadTest.getContractAddress());
		
		//发送交易
		for(Integer i = 0; i< 10; ++i) {
			Thread txThread = new Thread() {
				public void run(){
					try {
						while(true) {
							Thread.sleep(1000);
							//TransactionReceipt receipt = loadTest.addCounter(new Utf8String(UUID.randomUUID().toString()), new Int256(1)).get();
							loadTest.addCounter(new Utf8String(UUID.randomUUID().toString()), new Int256(1));
						}
					} catch (Exception e) {
						logger.error("系统错误", e);
					}
				}
			};
			txThread.start();
		}
		
		//读取合约
		for(Integer i = 0; i< 10; ++i) {
			Thread callThread = new Thread() {
				public void run(){
					try {
						while(true) {
							Address origin = loadTest._origin().get();
							Address sender = loadTest._sender().get();
							logger.info("storage sender:{} origin:{}", sender.toString(), origin.toString());
						}
					} catch (Exception e) {
						logger.error("系统错误", e);
					}
				}
			};
			callThread.start();
		}
		
		//200个线程，发链上链下消息
		for (Integer i = 0; i < 10; ++i) {
			Thread messageThread = new Thread() {
				public void run() {
					try {
						while (true) {
							ChannelRequest request = new ChannelRequest();
							request.setToTopic("test");
							request.setFromOrg("WB");
							request.setToOrg("EB");
							request.setMessageID(service.newSeq());
							
							StringBuffer sb = new StringBuffer();
							for(int i=0; i<500; ++i) {
								sb.append("hello world!:");								
							}
							
							
							request.setContent(sb.toString() + request.getMessageID());
							request.setTimeout(2000);

							//ChannelResponse response = service.sendChannelMessage2(request);
							logger.info("发送链上链下消息:{}", request.getMessageID());
							ChannelResponse response = service.sendChannelMessage(request);
							logger.info("收到链上链下响应:{} {}", response.getMessageID(),response.getErrorCode());
						}
					} catch (Exception e) {
						logger.error("系统错误", e);
					}
				}
			};
			messageThread.start();
		}
		
		for(int i=0; i<10; ++i) {
			TransactionReceipt receipt = loadTest.addCounter(new Utf8String(UUID.randomUUID().toString()), new Int256(1)).get();
			Address origin = loadTest._origin().get();
			Address sender = loadTest._sender().get();
			
			List<Type> addresses = loadTest.constGetInf().get();
			
			logger.info("const sender:{} origin:{}", (((Address)addresses.get(0)).toString()), (((Address)addresses.get(1)).toString()));
			logger.info("storage sender:{} origin:{}", sender.toString(), origin.toString());
		}
	}
}
