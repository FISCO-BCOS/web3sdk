package org.bcos.channel.test;

import org.bcos.channel.client.Service;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;

public class Ethereum extends TestBase{

	@Test
	public  void ethereumTest() throws Exception {

		//初始化交易参数
		java.math.BigInteger gasPrice = new BigInteger("30000000");
		java.math.BigInteger gasLimit = new BigInteger("30000000");
		java.math.BigInteger initialWeiValue = new BigInteger("0");

		//部署合约
		Ok ok = Ok.deploy(web3j,credentials,gasPrice,gasLimit,initialWeiValue).send();
		System.out.println("Ok getContractAddress " + ok);

		//调用合约接口
		java.math.BigInteger num = new BigInteger("999");

		TransactionReceipt receipt = ok.trans(num).send();
		System.out.println("receipt transactionHash" + receipt.getTransactionHash());

		//查询合约数据
		num = ok.get().send();
		System.out.println("ok.get() " + num.intValue());
	}
}