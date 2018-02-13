# web3sdk使用指南

## （一）介绍
web3sdk是用来访问fisco-bcos节点的java API。<br />
主要由两部分组成：<br />
1、AMOP（链上链下）系统旨在为联盟链提供一个安全高效的消息信道。<br />
2、web3j(Web3 Java Ethereum Ðapp API),这个部分来源于[web3j](https://github.com/web3j/web3j)，并针对bcos的做了相应改动。主要改动点为：1、交易结构中增加了randomid和blocklimit，这个改动对于sdk的使用者是透明的。2、为web3增加了AMOP消息信道。<br />
本文档主要说明如何使用AMOP消息信道调用web3j的API。AMOP（链上链下）的使用可以参考AMOP使用指南。web3j的使用可以参考[web3j](https://github.com/web3j/web3j)和[存证sample](https://github.com/FISCO-BCOS/evidenceSample)。

## （二）运行环境
1、需要首先搭建FISCO BCOS区块链环境（快速搭建可参考[FISCO BCOS一键快速安装部署](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/master/sample)）。<br />
2、JDK1.8。

## （三） 配置
以下为SDK的配置案例<br />
1、SDK配置（Spring Bean）：<br />
``` 

	<?xml version="1.0" encoding="UTF-8" ?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
		xmlns:tx="http://www.springframework.org/schema/tx" xmlns:aop="http://www.springframework.org/schema/aop"
		xmlns:context="http://www.springframework.org/schema/context"
		xsi:schemaLocation="http://www.springframework.org/schema/beans   
	    http://www.springframework.org/schema/beans/spring-beans-2.5.xsd  
	         http://www.springframework.org/schema/tx   
	    http://www.springframework.org/schema/tx/spring-tx-2.5.xsd  
	         http://www.springframework.org/schema/aop   
	    http://www.springframework.org/schema/aop/spring-aop-2.5.xsd">
	    
	<!-- AMOP消息处理线程池配置，根据实际需要配置 -->
	<bean id="pool" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
		<property name="corePoolSize" value="50" />
		<property name="maxPoolSize" value="100" />
		<property name="queueCapacity" value="500" />
		<property name="keepAliveSeconds" value="60" />
		<property name="rejectedExecutionHandler">
			<bean class="java.util.concurrent.ThreadPoolExecutor.AbortPolicy" />
		</property>
	</bean>
	
	<!-- 区块链节点信息配置 -->
	<bean id="channelService" class="org.bcos.channel.client.Service">
		<property name="orgID" value="WB" /> <!-- 配置本机构名称 -->
			<property name="allChannelConnections">
				<map>
					<entry key="WB"> <!-- 配置本机构的区块链节点列表（如有DMZ，则为区块链前置）-->
						<bean class="org.bcos.channel.handler.ChannelConnections">
							<property name="connectionsStr">
								<list>
									<value>NodeA@127.0.0.1:30333</value><!-- 格式：节点名@IP地址:channelport，节点名可以为任意名称 -->
								</list>
							</property>
						</bean>
					</entry>
				</map>
			</property>
		</bean>
	</bean>
```
2、ca.crt(用来验证节点或者前置的CA证书)：
```
和节点的ca.crt保持一致。
```
3、client.keystore(用来做sdk的ssl身份证书)：
```
里面需要包含一个由节点CA证书颁发的，别名为client的身份证书。
```
## （四）SDK使用

### 1、代码案例：

```

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

```
### 2、代码说明：
这个代码演示了web3sdk和fisco-bcos的主要特性。
#### 2.1、初始化web3j对象，连接到fisco-bcos节点：
web3sdk使用AMOP（链上链下）连接fisco-bcos节点。
##### 2.1.1、初始化AMOP的service，初始化函数详见配置文件，sleep(3000)是确保AMOP网络连接初始化完成。注意：org.bcos.channel.client.Service在Java Client端须为单实例，否则与链上节点连接会有问题。
```
    Service service = context.getBean(Service.class);
    service.run();
    Thread.sleep(3000);
```
##### 2.1.2、初始化AMOP的ChannelEthereumService，ChannelEthereumService通过AMOP的网络连接支持web3j的Ethereum JSON RPC协议。（JSON RPC 参考[JSON RPC API](https://github.com/ethereum/wiki/blob/master/JSON-RPC.md)）
```
    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);
```
##### 2.1.3、使用ChannelEthereumService初始化web3j对象。（web3sdk也支持通过http和ipc来初始化web3j对象，但在fisc-bcos中推荐使用AMOP）
```
    Web3j web3 = Web3j.build(channelEthereumService);
```
##### 2.1.4、调用web3j的rpc接口。样例给出的是获取块高例子，web3j还支持sendRawTransaction、getCode、getTransactionReceipt等等。（ 参考[JSON RPC API](https://github.com/ethereum/wiki/blob/master/JSON-RPC.md)）
```
    EthBlockNumber ethBlockNumber = web3.ethBlockNumber().sendAsync().get();
```
#### 2.2、初始化交易签名私钥（加载钱包）。样例给出的是新构建一个私钥文件。web3sdk也可以用证书来初始化交易签名私钥，只要是椭圆曲线加密算法(ECDSA-secp256k1)的私钥就能用来加载，对交易进行签可参考[存证sample](https://github.com/FISCO-BCOS/evidenceSample)。
```
    ECKeyPair keyPair = Keys.createEcKeyPair();
    Credentials credentials = Credentials.create(keyPair);
```
#### 2.3、部署合约。
##### 2.3.1、合约说明（Ok.sol）。
```
pragma solidity ^0.4.2;
contract Ok{
    
    struct Account{
        address account;
        uint balance;
    }
    
    struct  Translog {
        string time;
        address from;
        address to;
        uint amount;
    }
    
    Account from;
    Account to;
    
    Translog[] log;

    function Ok(){
        from.account=0x1;
        from.balance=10000000000;
        to.account=0x2;
        to.balance=0;

    }
    function get()constant returns(uint){
        return to.balance;
    }
    function trans(uint num){
    	from.balance=from.balance-num;
    	to.balance+=num;
    	log.push(Translog("20170413",from.account,to.account,num));
    }
}
```
##### 2.3.2、合约java类生产说明，详见（五）合约编译及java Wrap代码生成。
##### 2.3.3、初始化部署合约交易相关参数，正常情况采用demo给出值即可。
```
    java.math.BigInteger gasPrice = new BigInteger("30000000");
	java.math.BigInteger gasLimit = new BigInteger("30000000");
	java.math.BigInteger initialWeiValue = new BigInteger("0");
```
##### 2.3.3、部署合约。
```
    Ok ok = Ok.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();
```
#### 2.4、更新智能合约成员的值。
```
    TransactionReceipt receipt = ok.trans(num).get();
```
#### 2.5、查询智能合约成员的值。
```
    num = ok.get().get();
```
## （五）合约编译及java Wrap代码生成
* 智能合约语法及细节参考 <a href="https://solidity.readthedocs.io/en/develop/solidity-in-depth.html">solidity官方文档</a>。
* 安装fisco-solc,fisco-solc为solidity编译器。
	下载地址为：
	将下载下来的fisco-solc，将fisco-solc拷贝到/usr/bin目录下，执行命令chmod +x fisco-solc。如此fisco-solc即安装完成。
* 将自己的合约复制进/web3sdk/tools/contracts文件夹中（建议删除文件夹中其他无关的合约），SDK执行gradle build 之后生成工具包。
* 工具包中/dist/bin文件夹下为合约编译的执行脚本，/dist/contracts为合约存放文件夹，/dist/apps为sdk jar包，/dist/lib为sdk依赖jar包，/dist/output（不需要新建，脚本会生成）为编译后输出的abi、bin及java文件目录。
* 在bin文件夹下compile.sh为编译合约的脚本，执行命令sh compile.sh [参数1：java包名]执行成功后将在output目录生成所有合约对应的abi,bin,java文件，其文件名类似：合约名字.[abi|bin|java]。compile.sh脚本执行步骤实际分为两步，1.首先将sol源文件编译成abi和bin文件，依赖solc工具；2.将bin和abi文件编译java Wrap代码，依赖web3sdk.
* 例如：在以上工作都以完成,在/dist/bin文件夹下输入命令
    sh compile.sh com ,在/dist/output目录下回生成abi、bin文件，在/dist/output/com目录下生成java Wrap代码。

## （六）FAQ
### 6.1、使用工具包生成合约Java Wrap代码时会报错。
    从 https://github.com/FISCO-BCOS/web3sdk 下载代码（Download ZIP），解压之后，目录名称将为：web3sdk-master，运行gradle命令后生成的工具包中，/dist/apps目录下生成的jar包名称为web3sdk-master.jar，导致和/dist/bin/web3sdk中配置的CLASSPATH中的配置项$APP_HOME/apps/web3sdk.jar名称不一致，从而导致使用工具包生成合约Java Wrap代码时会报错。
### 6.2、工具目录下，dist/bin/web3sdk运行会出错。
可能是权限问题，请尝试手动执行命令：chmod +x  dist/bin/web3sdk。
### 6.3、java.util.concurrent.ExecutionException: com.fasterxml.jackson.databind.JsonMappingException: No content to map due to end-of-input
出现此问题请按顺序尝试来排查：<br />
1、检查配置是否连接的是channelPort，需要配置成channelPort。<br />
2、节点listen ip是否正确，最好直接监听0.0.0.0。<br />
3、检查channelPort是否能telnet通，需要能telnet通。如果不通检查网络策略，检查服务是否启动。<br />
4、服务端和客户端ca.crt是否一致，需要一致。<br />

