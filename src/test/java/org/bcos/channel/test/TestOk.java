package org.bcos.channel.test;

import org.bcos.channel.client.Service;
import org.bcos.solidity.Ok;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.RemoteCall;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class TestOk {
	public static void main(String[] args) throws Exception {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			Service service = context.getBean(Service.class);
			service.run();
			System.out.println("===================================================================");

			ChannelEthereumService channelEthereumService = new ChannelEthereumService();
			channelEthereumService.setChannelService(service);
			channelEthereumService.setTimeout(10000);
			Web3j web3 = Web3j.build(channelEthereumService);
			Thread.sleep(2000);
			//ToolConf toolConf=context.getBean(ToolConf.class);
			Credentials credentials = Credentials.create("");
			if (credentials != null) {
				System.out.println("####create credential succ, begin deploy contract");
				java.math.BigInteger gasPrice = new BigInteger("300000000");
				java.math.BigInteger gasLimit = new BigInteger("300000000");
				java.math.BigInteger initialWeiValue = new BigInteger("0");
				while (true){
					try {
						Ok okDemo = Ok.deploy(web3, credentials, gasPrice, gasLimit, initialWeiValue).sendAsync().get();
						if (okDemo != null) {
							while (true) {
								System.out.println("####contract address is: " + okDemo.getContractAddress());
								TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
								System.out.println("###callback trans success");
								BigInteger toBalance = okDemo.get().send();
								System.out.println("============to balance:" + toBalance.intValue());
								Thread.sleep(1000);
							}
						} else {
							System.out.println("deploy Ok contract failed");
							//exit(1);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						System.out.println("Execute testok failed");
					}
				}
			} else {
				System.out.println("create Credentials failed");
				exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Execute testok failed");
		}
	}

}
