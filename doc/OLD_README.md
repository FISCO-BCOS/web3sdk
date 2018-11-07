# web3sdk使用指南
# 目录
<!-- TOC -->
- [1. 介绍](#1-介绍)
	- [1.1 功能介绍](#11-功能介绍)
	- [1.2 特性介绍](#12-特性介绍)
- [2. 运行环境](#2-运行环境)
- [3. web3sdk环境部署](#3-web3sdk环境部署)
	- [3.1 安装依赖软件](#31-安装依赖软件)
	- [3.2 部署web3sdk](#32-部署web3sdk)
		- [3.2.1 编译web3sdk源码](#321-编译web3sdk源码)
		- [3.2.2 生成客户端证书](#322-生成客户端证书)
		- [3.2.3 配置链上节点信息和证书](#323-配置链上节点信息和证书)
- [4. FISCO BCOS系统合约部署](#4-fisco-bcos系统合约部署)
- [5. 系统合约工具和测试工具的使用](#5-系统合约工具和测试工具的使用)
	- [5.1 系统合约介绍](#51-系统合约介绍)
	- [5.2 系统合约工具使用方法](#52-系统合约工具使用方法)
	- [5.3 测试工具使用方法](#53-测试工具使用方法)
	- [5.4 web3j API说明](#54-web3j-api说明)
- [6. 权限控制说明](#6-权限控制说明)
- [7. 合约编译及java Wrap代码生成](#7-合约编译及java-wrap代码生成)
- [8. SDK使用](#8-sdk使用)
- [9. FAQ](#9-faq)
	- [使用工具包生成合约Java Wrap代码时会报错](#91-使用工具包生成合约java-wrap代码时会报错)
	- [工具目录下，dist/bin/web3sdk运行会出错](#92-工具目录下distbinweb3sdk运行会出错)
	- [java.util.concurrent.ExecutionException: com.fasterxml.jackson.databind.JsonMappingException: No content to map due to end-of-input](#93-javautilconcurrentexecutionexception-comfasterxmljacksondatabindjsonmappingexception-no-content-to-map-due-to-end-of-input)

<!-- /TOC -->

## 1. 介绍
#### 1.1 功能介绍

web3sdk是用来访问fisco-bcos节点的java API。<br />
主要由两部分组成：<br />
(1) AMOP（链上链下）系统旨在为联盟链提供一个安全高效的消息信道。<br />
(2) web3j(Web3 Java Ethereum Ðapp API),这个部分来源于[web3j](https://github.com/web3j/web3j)，并针对bcos的做了相应改动。主要改动点为：

① 交易结构中增加了randomid和blocklimit，这个改动对于sdk的使用者是透明的。<br /> ②为web3增加了AMOP消息信道。<br />

本文档主要说明如何使用AMOP消息信道调用web3j的API。AMOP（链上链下）的使用可以参考AMOP使用指南。web3j的使用可以参考[web3j](https://github.com/web3j/web3j)和[存证sample](https://github.com/FISCO-BCOS/evidenceSample)。



#### 1.2 特性介绍

- **[系统合约工具和测试工具的使用](#5-系统合约工具和测试工具的使用)**
- **[提供将合约代码转换成java代码的工具](#7-合约编译及java-wrap代码生成)**
- **[提供系统合约部署和使用工具](##4-fisco-bcos系统合约部署)**
- **[使用国密算法发交易](doc/guomi_support_manual.md)**：[国密版FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md) 使用国密算法验证交易，客户端相应地需使用国密算法对交易进行签名，web3sdk客户端为国密版FISCO BCOS提供了支持，具体使用方法可参考文档[《web3sdk对国密版FISCO BCOS的支持》](doc/guomi_support_manual.md)

<br>

[返回目录](#目录)

<br>

## 2. 运行环境

1、需要首先搭建FISCO BCOS区块链环境（快速搭建可参考[FISCO BCOS一键快速安装部署](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/master/sample)）。<br />
2、[JDK1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

<br>

[返回目录](#目录)

<br>

## 3. web3sdk环境部署

## 3.1 安装依赖软件 

部署web3sdk之前需要安装git, dos2unix, lsof依赖软件

-  git：用于拉取最新代码
-  dos2unix && lsof: 用于处理windows文件上传到linux服务器时，文件格式无法被linux正确解析的问题；

可用如下命令安装这些软件：

```shell
[centos]
sudo yum -y install git
sudo yum -y install dos2unix
sudo yum -y install lsof

[ubuntu]
sudo apt install git
sudo apt install lsof
sudo apt install tofrodos
ln -s /usr/bin/todos /usr/bin/unxi2dos
ln -s /usr/bin/fromdos /usr/bin/dos2unix
```

<br>

[返回目录](#目录)

<br>

## 3.2 部署web3sdk

<br>

依赖部署完毕后，可拉取web3sdk代码，编译客户端代码，配置链上节点信息，主要包括如下过程：

### 3.2.1 编译web3sdk源码

<br>

- 从git上拉取代码
- 编译web3sdk源码，生成jar包

```bash
#=== 进入web3sdk源码放置目录（假设为/mydata/）=====
$ mkdir -p /mydata
$ cd /mydata

#==== 拉取git代码 ====
$ git clone https://github.com/FISCO-BCOS/web3sdk

#===编译we3bsdk源码，生成dist目录 ===
$ cd web3sdk
$ gradle build

```

<br>

编译成功后，会在web3sdk目录下生成dist目录，该目录主要包括如下内容：


| 目录             | 说明                                       |
| -------------- | ---------------------------------------- |
| dist/apps      | web3sdk项目编译生成的jar包web3sdk.jar             |
| dist/bin       | - web3sdk: 可执行脚本，调用web3sdk.jar执行web3sdk内方法(如部署系统合约、调用合约工具方法等) <br>  - compile.sh: 调用该脚本可将dist/contracts目录下的合约代码转换成java代码，该功能便于用户基于web3sdk开发更多应用 |
| dist/conf      | 配置目录, 用于配置节点信息、证书信息、日志目录等，详细信息会在下节叙述     |
| dist/contracts | 合约存放目录，调用compile.sh脚本可将存放于该目录下的.sol格式合约代码转换成java代码 |
| dist/lib       | 存放web3sdk依赖库的jar包                         |

<br>

[返回目录](#目录)

<br>


### 3.2.2 生成客户端证书

web3sdk客户端证书ca.crt, client.keystore生成方法请参考[FISCO-BCOS区块链操作手册的生成sdk证书](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/master/doc/manual#24-生成sdk证书)一节。<br>
具体步骤可以参考[sdk.sh](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/cert/sdk.sh),详细解释如下：<br>
(1)将链的根ca证书ca.crt和次级的机构ca证书agency.crt合成证书链ca证书ca.crt。此证书用来验证sdk连接节点的节点证书的合法性。具体步骤为：<br>

```shell
cp ca.crt ca-agency.crt
more agency.crt | cat >>ca-agency.crt
mv ca-agency.crt ca.crt
```

(2)生成client.keystore。其中的client证书有三种用途：1、用作和节点连接是sdk的身份证书，由节点的ca.crt和agency.crt来验证合法性。2、用作和其他sdk（前置）连接的身份证书，由其他sdk的ca.crt来验证合法性。3、用作sdk发交易的私钥证书。<br>
先用openssl生成一张secp256k1的证书sdk.crt。<br>

```shell
    openssl ecparam -out sdk.param -name secp256k1
    openssl ecparam -out sdk.param -name secp256k1
    openssl genpkey -paramfile sdk.param -out sdk.key
    openssl pkey -in sdk.key -pubout -out sdk.pubkey
    openssl req -new -key sdk.key -config cert.cnf  -out sdk.csr
    openssl x509 -req -days 3650 -in sdk.csr -CAkey agency.key -CA agency.crt -force_pubkey sdk.pubkey -out sdk.crt -CAcreateserial -extensions v3_req -extfile cert.cnf
```

再将生成的sdk证书导入到client.keystore中。下面步骤中的第一步是中间步骤，用于生成导入keystore的p12文件。<br>

```shell
    openssl pkcs12 -export -name client -in sdk.crt -inkey sdk.key -out keystore.p12
    keytool -importkeystore -destkeystore client.keystore -srckeystore keystore.p12 -srcstoretype pkcs12 -alias client
```

(3)加载client.keystore中私钥作为交易私钥的示例代码<br>

```
   KeyStore ks = KeyStore.getInstance("JKS");
   ksInputStream =  Ethereum.class.getClassLoader().getResourceAsStream(keyStoreFileName);
   ks.load(ksInputStream, keyStorePassword.toCharArray());
   Key key = ks.getKey("client", keyPassword.toCharArray());
   ECKeyPair keyPair = ECKeyPair.create(((ECPrivateKey) key).getS());
   Credentials credentials = Credentials.create(keyPair);
```

<br>

### 3.2.3 配置链上节点信息和证书

<br>

编译成功后, web3sdk主要配置文件存放于dist/conf目录下，主要包括如下设置： 

<br>

| 配置文件                   | 详细说明                                     |
| ---------------------- | ---------------------------------------- |
| applicationContext.xml | 主要包括三项配置: <br> - encryptType: 配置国密算法开启/关闭开关，0表示不使用国密算法发交易，1表示开启国密算法发交易，默认为0(即不使用国密算法发交易，web3sdk支持国密算法的具体方法可参考文档[web3sdk对国密版FISCO BCOS的支持](https://github.com/FISCO-BCOS/web3sdk/blob/master/doc/guomi_support_manual.md));  <br>  - systemProxyAddress: 配置FISCO BCOS系统合约地址, 部署系统合约成功后，要将systemProxyAddress对应的值改为部署的系统合约地址;  <br> - privKey: 向FISCO BCOS节点发交易或发消息的账户私钥，使用默认配置即可 <br> - ChannelConnections：配置FISCO BCOS节点信息和证书，证书相关配置如下:  <br>  (1) caCertPath: CA证书路径，默认为dist/conf/ca.crt; <br> (2) clientKeystorePath: 客户端证书路径，默认为dist/conf/client.keystore; <br> (3)keystorePassWord: 客户端证书文件访问口令, 默认为123456; <br> (4) clientCertPassWord: 客户端证书验证口令, 默认为123456 |
| ca.crt                 | 用来验证节点或者前置的CA证书，必须与链上FISCO BCOS节点CA证书保持一致           |
| client.keystore        | 用来做sdk的ssl身份证书， 里面需要包含一个由节点CA证书颁发的，别名为client的身份证书，默认访问口令和验证口令均为123456  |
| 日志配置文件                 | - commons-logging.properties： 配置日志类, 默认为org.apache.commons.logging.impl.SimpleLog;  <br>  - log4j2.xml：日志常见配置，包括路径、格式、缓存大小等; <br> - simplelog.properties: 日志级别设置，默认为WARN |

以下为SDK的配置案例<br />

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
	    
	    <bean id="encryptType" class="org.bcos.web3j.crypto.EncryptType">
                <constructor-arg value="0"/>
        </bean>
	
	<!-- 系统合约地址配置，在使用./web3sdk SystemProxy|AuthorityFilter等系统合约工具时需要配置 -->
	<bean id="toolConf" class="org.bcos.contract.tools.ToolConf">
		<property name="systemProxyAddress" value="0x919868496524eedc26dbb81915fa1547a20f8998" />
		<!--GOD账户的私钥-->（注意去掉“0x”）
		<property name="privKey" value="bcec428d5205abe0f0cc8a734083908d9eb8563e31f943d760786edf42ad67dd" />
		<!--GOD账户-->
		<property name="account" value="0x776bd5cf9a88e9437dc783d6414bccc603015cf0" />
		<property name="outPutpath" value="./output/" />
	</bean>

	<!-- 区块链节点信息配置 -->
	<bean id="channelService" class="org.bcos.channel.client.Service">
		<property name="orgID" value="WB" /> <!-- 配置本机构名称 -->
			<property name="allChannelConnections">
				<map>
					<entry key="WB"> <!-- 配置本机构的区块链节点列表（如有DMZ，则为区块链前置）-->
						<bean class="org.bcos.channel.handler.ChannelConnections">
						    <property name="caCertPath" value="classpath:ca.crt" />
						    <property name="clientKeystorePath" value="classpath:client.keystore" />
						    <property name="keystorePassWord" value="123456" />
						    <property name="clientCertPassWord" value="123456" />
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

<br>

[返回目录](#目录)

<br>

# 4. FISCO BCOS系统合约部署

部署完适配于[FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/master/doc/manual)的web3sdk后，可调用InitSystemContract部署系统合约，系统合约部署工具InitSystemContract由`src/main/java/org/bcos/contract/tools/InitSystemContract.java`调用合约生成的java代码实现，使用如下命令部署系统合约：

```bash
##进入dist目录(设web3sdk存放于/mydata/目录)
$ cd /mydata/web3sdk/dist/bin
####执行部署工具InitSystemContract部署系统合约:
$ ./web3sdk InitSystemContract
===================================================================
Start deployment...
===================================================================
systemProxy getContractAddress 0xc9ed60a2ebdf22936fdc920133af2a77dd553e13
caAction getContractAddress 0x014bf33e022f78f7c4bb8dbfe1d22df5168fc9bc
nodeAction getContractAddress 0x51b25952b01a42e6f84666ed091571c7836eda34
consensusControlMgr getContractAddress 0xffda2977b8bd529dd187d226ea6600ff3c8fb716
configAction getContractAddress 0xf6677fa9594c823abf39ac67f1f34866e2843399
fileInfoManager getContractAddress 0x0f49a17d17f82da2a7d92ecf19268274150eaf5e
fileServerManager getContractAddress 0xfbe0184fe09a3554103c5a541ba052f7fa45283b
contractAbiMgr getContractAddress 0x66ec295357750ce442227a6419ada7fdf9207be2
authorityFilter getContractAddress 0x2fa1ec76f3e31d2c42d21b62960625f326a044e6
group getContractAddress 0xa172b92c85a98167d96b9fde10792eb2fd4d584c
transactionFilterChain getContractAddress 0x0b78d9be55f047fb32d6fbc2c79013c0eca5d09d
Contract Deployment Completed System Agency Contract:0xc9ed60a2ebdf22936fdc920133af2a77dd553e13
-----------------System routing table----------------------
 0)TransactionFilterChain=>0x0b78d9be55f047fb32d6fbc2c79013c0eca5d09d,false,35
       AuthorityFilter=>1.0,0x2fa1ec76f3e31d2c42d21b62960625f326a044e6
 1)ConfigAction=>0xf6677fa9594c823abf39ac67f1f34866e2843399,false,36
 2)NodeAction=>0x51b25952b01a42e6f84666ed091571c7836eda34,false,37
 3)ConsensusControlMgr=>0xffda2977b8bd529dd187d226ea6600ff3c8fb716,false,38
 4)CAAction=>0x014bf33e022f78f7c4bb8dbfe1d22df5168fc9bc,false,39
 5)ContractAbiMgr=>0x66ec295357750ce442227a6419ada7fdf9207be2,false,40
 6)FileInfoManager=>0x0f49a17d17f82da2a7d92ecf19268274150eaf5e,false,41
 7)FileServerManager=>0xfbe0184fe09a3554103c5a541ba052f7fa45283b,false,42
```

部署完毕的系统合约地址是0xc9ed60a2ebdf22936fdc920133af2a77dd553e13, 还需要进行如下两步操作：

**设置配置文件的系统合约地址：将/mydata/web3sdk/dist/conf/applicationContext.xml的systemProxyAddress字段更新为输出的系统合约地址**

执行完上述两步操作后，按照[FISCO BCOS区块链操作手册](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/master/doc/manual#第七章-多记账节点组网) **多记账节点组网**一节进行加入新节点等操作。

> 注：InitSystemContract用来部署一套系统合约（用来做链的初始化和测试，生产环境请谨慎操作）。部署完成后需要将系统合约地址替换到各个节点的config.json和web3sdk工具的applicationContext.xml配置中，并重启节点。

<br>

[返回目录](#目录)

<br>

# 5. 系统合约工具和测试工具的使用

在SDK工具包/mydata/web3sdk/dist/bin（设web3sdk存放于/mydata目录下）目录下，compile.sh为合约编译脚本，web3sdk为SDK的的执行脚本。web3sdk脚本中将系统合约接口进行暴露。系统合约介绍文档参考[FISCO BCOS系统合约介绍](https://github.com/FISCO-BCOS/Wiki/tree/master/FISCO-BCOS%E7%B3%BB%E7%BB%9F%E5%90%88%E7%BA%A6%E4%BB%8B%E7%BB%8D)

## 5.1 系统合约介绍

web3sdk将系统合约部署于链上，并可通过工具调用这些系统合约，部署的系统合约如下：

| 系统合约                   | 详细说明                                     |
| ---------------------- | ---------------------------------------- |
| SystemProxy            | 系统合约代理合约                                 |
| TransactionFilterChain | 设置transaction过滤器                         |
| ConfigAction           | 设置/获取区块链系统参数，可参考[系统参数说明文档](https://github.com/FISCO-BCOS/Wiki/tree/master/%E7%B3%BB%E7%BB%9F%E5%8F%82%E6%95%B0%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3) |
| ConsensusControlMg     | 联盟控制合约，可参考[联盟控制模板参考文档](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/%E5%BC%B9%E6%80%A7%E8%81%94%E7%9B%9F%E9%93%BE%E5%85%B1%E8%AF%86%E6%A1%86%E6%9E%B6%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3.md)             |
| CAAction               | 证书列表黑名单管理: 包括将证书加入黑名单列表，将制定证书从黑名单列表删除，获取证书黑名单列表功能             |
| ContractAbiMgr         | ABI相关合约                                  |

<br>

[返回目录](#目录)

<br>

## 5.2 系统合约工具使用方法

web3sdk使用SystemContractTools调用系统合约，主要功能如下：

**(1) 系统合约代理合约SystemProxy**

**调用方法**:

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk SystemProxy
```
**功能**：遍历系统代理合约路由表，输出所有系统合约地址

<br>

[返回目录](#目录)

<br>

**(2) 节点管理合约NodeAction**

**① 加入节点**

**调用方法**: 

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk NodeAction registerNode ${node_json_path}
```

**功能**：将${node_json_path}中指定的节点加入到FISCO BCOS区块链网络中(注: ${node_json_path}是节点配置文件相对于dist/conf的路径)

节点配置文件主要包括如下配置项：

| 配置项        | 详细说明          |
| ---------- | ------------- |
| id         | 节点的node id    |
| ip         | 节点IP          |
| port       | 节点P2P连接端口     |
| desc       | 节点描述          |
| agencyinfo | 节点所属机构信息      |
| idx        | 节点序号，按照加入顺序排序 |

一个简单的节点配置文件node.json示例如下:

```json
{
    "id":"2cd7a7cadf8533e5859e1de0e2ae830017a25c3295fb09bad3fae4cdf2edacc9324a4fd89cfee174b21546f93397e5ee0fb4969ec5eba654dcc9e4b8ae39a878",
    "ip":"127.0.0.1",
    "port":30501,
    "desc":"node1",
    "CAhash":"",
    "agencyinfo":"node1",
    "idx":0
}
```

<br>

**② 退出节点**

**调用方法**：

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk NodeAction cancelNode ${node_json_path}
```
**功能**：将${node_json_path}指定的节点从FISCO BCOS区块链网络中退出(注: ${node_json_path}是节点配置文件相对于/mydata/web3sdk/dist/conf的路径，节点配置文件说明同上)

<br>

**③ 显示节点连接信息**

**调用方法**:
```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk NodeAction all
```

**功能**：显示FISCO BCOS区块链网络中所有节点信息，输出示例如下：

```bash
$ cd /mydata/web3sdk/dist/bin
$ ./web3sdk NodeAction all
===================================================================
=====INIT ECDSA KEYPAIR From private key===
NodeIdsLength= 1
----------node 0---------
id=28f815c7222118adaca6dfdefdda76906a491ae4ef9de4d311f3f23bd2371ee9d15e2f26646d1641bf6391b1c1489c8a1708e35012903f041d1841f58c63e674
nodeA
agencyA
E2746FDF0B29F8A8
org.bcos.web3j.abi.datatypes.generated.Uint256@ee871267
```

<br>

[返回目录](#目录)

<br>


**(3) 节点证书管理合约CAAction**

**① 将指定节点证书加入黑名单列表**

**调用方法**: 

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk CAAction add ${node_ca_path}
```

**功能**：通过系统合约CAAction，将路径${node_ca_path}指定的节点证书信息添加到黑名单列表，其他节点将拒绝与此节点连接(注: ${node_ca_path}是节点配置文件相对于dist/conf的路径)

<br>

**② 从黑名单列表中删除指定节点证书信息**

**调用方法**:

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk CAAction remove ${node_ca_path}
```

**功能**：通过系统合约CAAction，将${node_ca_path}指定的节点证书信息从黑名单列表中删除，其他节点恢复与该节点的连接(注: ${node_ca_path}是节点配置文件相对于dist/conf的路径)

<br>

**③ 显示区块链黑名单节点证书信息**

**调用方法**: 

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk CAAction all
```

**功能**: 显示记录在系统合约CAAction中的所有黑名单节点证书信息

<br>

[返回目录](#目录)

<br>


**(4) 系统参数配置合约ConfigAction**


**FISCO BCOS系统合约主要配置如下:**

<br>

| 配置项        | 详细说明          |
| ---------- | ------------- |
| maxBlockTransactions| 控制一个块内允许打包的最大交易数量上限 <br>  设置范围: [0, 2000) <br> 默认值:1000|
| intervalBlockTime| 设置出块间隔时间 <br> 设置范围：大于等于1000 <br> 默认值: 1000 |
| maxBlockHeadGas| 控制一个块允许最大Gas消耗上限 <br> 取值范围: 大于等于2000,000,000 <br> 默认值: 2000,000,000|
| maxTransactionGas | 设置一笔交易允许消耗的最大gas <br> 取值范围: 大于等于30,000,000 <br> 默认值: 30,000,000 |
| maxNonceCheckBlock| 控制Nonce排重覆盖的块范围 <br> 取值范围： 大于等于1000 <br>  缺省值: 1000 |
| maxBlockLimit| 控制允许交易上链延迟的最大块范围 <br> 取值范围：大于等于1000 <br> 缺省值：1000|
| CAVerify| 控制是否打开CA验证 <br> 取值：true或者false  <br>  缺省值: false|
| omitEmptyBlock| 控制是否忽略空块 <br> 取值：true或者false  <br> 缺省值：false |

<br>

**① 获取系统参数**

**调用方法**: 在/mydata/web3sdk/dist/bin目录下执行./web3sdk ConfigAction get ${配置项}

**功能**: 从系统合约ConfigAction读取${key}对应的值(ConfigAction中记录的系统参数说明参考[系统参数说明文档](https://github.com/FISCO-BCOS/Wiki/tree/master/%E7%B3%BB%E7%BB%9F%E5%8F%82%E6%95%B0%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3))

<br>

**② 设置系统参数信息**

**调用方法**: 在/mydata/web3sdk/dist/bin目录下执行./web3sdk ConfigAction set ${配置项} ${配置项的值}

**功能**: 将记录在系统合约ConfigAction中${key}对应的值设置为${setted_value}(ConfigAction中记录的系统参数说明参考[系统参数说明文档](https://github.com/FISCO-BCOS/Wiki/tree/master/%E7%B3%BB%E7%BB%9F%E5%8F%82%E6%95%B0%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3))


**通过ConfigAction配置系统参数的例子如下:**

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
$ cd /mydata/web3sdk/dist/bin
$ chmod a+x web3sdk

# =====更改出块时间为2s====
$ ./web3sdk ConfigAction set intervalBlockTime 2000

# =====允许空块落盘=====
$ ./web3sdk ConfigAction set omitEmptyBlock false

# ====调整一笔交易允许消耗的最大交易gas为40,000,000
$ ./web3sdk ConfigAction set maxTransactionGas 40000000

# ====调整一个块允许消耗的最大交易gas为3000,000,000
$ ./web3sdk ConfigAction set maxBlockHeadGas 3000000000

# ==== 打开CA认证开关====
$ ./web3sdk ConfigAction set CAVerify true

# ......
```

<br>

[返回目录](#目录)

<br>

**(5) 联盟控制合约ConsensusControl**

**① 部署联盟共识模板**

**调用方法**: ./web3sdk ConsensusControl deploy

<br>

**② 获取联盟共识模板合约地址**

**调用方法**: ./web3sdk ConsensusControl list

<br>

**③ 关闭联盟共识特性**

**调用方法**:  ./web3sdk ConsensusControl list turnoff

<br>
[返回目录](#目录)

<br>


## 5.3 测试工具使用方法

web3sdk提供了一些测试工具，方便确定web3sdk与[FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS)通信是否正常，本节简要介绍这些测试工具使用方法：

**(1) Ok合约测试工具**

**调用方法:** java -cp 'conf/:apps/\*:lib/\*' org.bcos.channel.test.TestOk
**说明:** 向链上部署Ok合约，并调用Ok合约的trans接口(Ok合约可参考[Ok.sol](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/tool/Ok.sol))

Ok合约调用示例如下:

```bash
#进入dist目录
$ cd /mydata/web3sdk/dist
#调用测试合约TestOk
$ java -cp 'conf/:apps/*:lib/*' org.bcos.channel.test.TestOk
===================================================================
=====INIT ECDSA KEYPAIR From private key===
contract address is: 0xecf79838dc5e0b4c2834f27b3dd2706d77d5f548
callback trans success
============to balance: 2000
....
```

<br>
<br>

**(2) Ethereum测试工具**

**调用方法:** java -cp 'conf/:apps/\*:lib/\*' org.bcos.channel.test.Ethereum
**说明:** Ethereum功能与Ok合约测试工具类似，也是向链上部署Ok合约，并调用相关接口(Ok合约可参考[Ok.sol](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/tool/Ok.sol))

Ethereum测试工具调用示例如下：

```bash
#进入dist目录
$ cd /mydata/web3sdk/dist
#调用测试合约Ethereum
$ java -cp 'conf/:apps/*:lib/*' org.bcos.channel.test.Ethereum
start...
===================================================================
=====INIT ECDSA KEYPAIR From private key===
Ok getContractAddress 0xa5db78544f7970ff04be172f03b0b236e4e3befb
receipt transactionHash0xf46894ad8e6a22eb06e99d9a6f471d12c9a3158a1c0605a2473b2e9f97e2fa19
ok.get() 999
```
<br>

[返回目录](#目录)

<br>

## 5.4 web3j API说明

> web3j API接口命令参考如下，--后为参数说明：

```bash
./web3sdk web3_clientVersion 
./web3sdk eth_accounts
./web3sdk eth_blockNumber
./web3sdk eth_pbftView
./web3sdk eth_getCode address blockNumber  --地址 存储位置整数
./web3sdk eth_getBlockTransactionCountByHash blockHash   --区块hash
./web3sdk eth_getTransactionCount address blockNumber   --区块号
./web3sdk eth_getBlockTransactionCountByNumber blockNumber  --区块号
./web3sdk eth_sendRawTransaction signTransactionData  --签名的交易数据
./web3sdk eth_getBlockByHash blockHash true|false   --区块hash true|false
./web3sdk eth_getBlockByNumber blockNumber  --区块号
./web3sdk eth_getTransactionByBlockNumberAndIndex blockNumber transactionPosition  --区块号 交易位置
./web3sdk eth_getTransactionByBlockHashAndIndex blockHash transactionPosition  --区块hash 交易位置
./web3sdk eth_getTransactionReceipt transactionHash  --交易hash
```

web3j API接口代码参照：

org.bcos.web3j.console.Web3RpcApi

<br>

[返回目录](#目录)

<br>

# 6. 权限控制说明

在SDK工具包/dist/bin目录下，compile.sh为合约编译脚本，web3sdk为SDK的的执行脚本。web3sdk脚本中将权限相关的接口进行暴露，使用相关命令执行即可。权限控制介绍文档请参看[FISCO BCOS权限模型（ARPI）介绍](https://github.com/FISCO-BCOS/Wiki/tree/master/FISCO%20BCOS%E6%9D%83%E9%99%90%E6%A8%A1%E5%9E%8B) 、[联盟链的权限体系](https://github.com/FISCO-BCOS/Wiki/tree/master/%E5%8C%BA%E5%9D%97%E9%93%BE%E7%9A%84%E6%9D%83%E9%99%90%E4%BD%93%E7%B3%BB) 。

```java
org.bcos.contract.tools.ARPI_Model    #一键执行类
org.bcos.contract.tools.AuthorityManagerTools
```

在使用时，需要在applicationContext.xml文件中配置相关参数：

```xml
	<!-- 系统合约地址配置置-->
	<bean id="toolConf" class="org.bcos.contract.tools.ToolConf">
		<!--系统合约-->
		<property name="systemProxyAddress" value="0x919868496524eedc26dbb81915fa1547a20f8998" />
		<!--GOD账户的私钥-->（注意去掉“0x”）
		<property name="privKey" value="bcec428d5205abe0f0cc8a734083908d9eb8563e31f943d760786edf42ad67dd" />
		<!--God账户-->
		<property name="account" value="0x776bd5cf9a88e9437dc783d6414bccc603015cf0" />
		<property name="outPutpath" value="./output/" />
	</bean>
```

> 权限控制接口命令参考如下

```bash
./web3sdk ARPI_Model 
./web3sdk PermissionInfo 
./web3sdk FilterChain addFilter name1 version1 desc1 
./web3sdk FilterChain delFilter num 
./web3sdk FilterChain showFilter 
./web3sdk FilterChain resetFilter 
./web3sdk Filter getFilterStatus num 
./web3sdk Filter enableFilter num 
./web3sdk Filter disableFilter num 
./web3sdk Filter setUsertoNewGroup num account 
./web3sdk Filter setUsertoExistingGroup num account group 
./web3sdk Filter listUserGroup num account 
./web3sdk Group getBlackStatus num account 
./web3sdk Group enableBlack num account 
./web3sdk Group disableBlack num account 
./web3sdk Group getDeployStatus num account 
./web3sdk Group enableDeploy num account 
./web3sdk Group disableDeploy num account 
./web3sdk Group addPermission num account A.address fun(string) 
./web3sdk Group delPermission num account A.address fun(string) 
./web3sdk Group checkPermission num account A.address fun(string) 
./web3sdk Group listPermission num account 
```

<br>

[返回目录](#目录)

<br>

# 7. 合约编译及java Wrap代码生成

(1) 智能合约语法及细节参考 <a href="https://solidity.readthedocs.io/en/develop/solidity-in-depth.html">solidity官方文档</a>。

(2) 安装fisco-solc,fisco-solc为solidity编译器,[下载fisco-solc](https://github.com/FISCO-BCOS/fisco-solc)。

  将下载下来的fisco-solc，将fisco-solc拷贝到/usr/bin目录下，执行命令chmod +x fisco-solc。如此fisco-solc即安装完成。

(3) 设web3sdk存放于/mydata路径下，则工具包中/mydata/web3sdk/dist/bin文件夹下为合约编译的执行脚本，/mydata/web3sdk/dist/contracts为合约存放文件夹,将自己的合约复制进/mydata/web3sdk/dist/contracts文件夹中（建议删除文件夹中其他无关的合约)，/mydata/web3sdk/dist/apps为sdk jar包，/mydata/web3sdk/dist/lib为sdk依赖jar包，/mydata/web3sdk/dist/output（不需要新建，脚本会创建）为编译后输出的abi、bin及java文件目录。

(4) 在/mydata/web3sdk/dist/bin文件夹下compile.sh为编译合约的脚本，执行命令sh compile.sh [参数1：java包名]：

- 执行成功后将在output目录生成所有合约对应的abi,bin,java文件，其文件名类似：合约名字.[abi|bin|java]
	
- compile.sh首先将sol源文件编译成abi和bin文件，依赖solc工具；然后将bin和abi文件编译java Wrap代码，依赖web3sdk.
	
- 例如：在以上工作都以完成,在/dist/bin文件夹下输入命令`sh compile.sh com`,会在/dist/output目录下生成abi、bin文件，在/dist/output/com目录下生成java Wrap代码
	
- 生成兼容国密版FISCO-BCOS的java代码具体方法可参考[web3sdk对国密版FISCO BCOS的支持的生成支持国密算法的java代码](https://github.com/FISCO-BCOS/web3sdk/blob/master/doc/guomi_support_manual.md#6-%E7%94%9F%E6%88%90%E6%94%AF%E6%8C%81%E5%9B%BD%E5%AF%86%E7%AE%97%E6%B3%95%E7%9A%84java%E4%BB%A3%E7%A0%81)一节

<br>

[返回目录](#目录)

<br>

# 8. SDK使用

下面代码演示了通过web3sdk调用合约向FISCO-BCOS发交易的主要流程：

```java
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
			
			System.out.println("开始测试...");			System.out.println("===================================================================");
			
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

以上代码主要包括如下流程：

**(1) 初始化web3j对象，连接到FISCO-BCOS节点**

web3sdk使用AMOP（链上链下）连接fisco-bcos节点：

```java
	//初始化AMOP的service，初始化函数详见配置文件，sleep(3000)是确保AMOP网络连接初始化完成。注意：org.bcos.channel.client.Service在Java Client端须为单实例，否则与链上节点连接会有问题
    Service service = context.getBean(Service.class);
    service.run();
    Thread.sleep(3000);
```

**(2) 初始化AMOP的ChannelEthereumService**

ChannelEthereumService通过AMOP的网络连接支持web3j的Ethereum JSON RPC协议。（JSON RPC 参考[JSON RPC API](https://github.com/ethereum/wiki/blob/master/JSON-RPC.md)

```java
    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);
```

**(3) 使用ChannelEthereumService初始化web3j对象**

> web3sdk也支持通过http和ipc来初始化web3j对象，但在fisc-bcos中推荐使用AMOP

```java
    Web3j web3 = Web3j.build(channelEthereumService);
```

**(4) 调用web3j的rpc接口**

> 样例给出的是获取块高例子，web3j还支持sendRawTransaction、getCode、getTransactionReceipt等等。部分API参看5.3 web3j API说明（ 参考[JSON RPC API](https://github.com/ethereum/wiki/blob/master/JSON-RPC.md)）

```java
    EthBlockNumber ethBlockNumber = web3.ethBlockNumber().sendAsync().get();
```
**(5)初始化交易签名私钥（加载钱包）**

> 样例给出的是新构建一个私钥文件。web3sdk也可以用证书来初始化交易签名私钥，对交易进行签可参考[存证sample](https://github.com/FISCO-BCOS/evidenceSample)。

```java
import org.bcos.web3j.crypto.GenCredential;

//...省略若干行...

BigInteger bigPrivKey = new BigInteger(privKey, 16);
ECKeyPair keyPair = ECKeyPair.create(bigPrivKey);
if (keyPair == null)
    return null;
Credentials credentials = Credentials.create(keyPair);
```

**(6) 部署合约**
 
Ok合约代码如下：

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

在部署合约前，首先要将合约代码转换成java代码，可参考（5.1）合约编译及java Wrap代码生成。
web3sdk部署合约的代码如下：

```java
	//初始化部署合约交易相关参数，正常情况采用demo给出值即可
    java.math.BigInteger gasPrice = new BigInteger("30000000");
	java.math.BigInteger gasLimit = new BigInteger("30000000");
	java.math.BigInteger initialWeiValue = new BigInteger("0");

	//部署合约
	Ok ok = Ok.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue).get();

	//更新智能合约成员的值
	TransactionReceipt receipt = ok.trans(num).get();

	//查询智能合约成员的值
	num = ok.get().get();

	//交易回调通知:
	//交易回调通知是指针对合约的交易接口（非constant function和构造函数），通过web3j生成的java代码后，会在原来的java函数基础上，新增了同名的重载函数，与原来的区别在于多了一个TransactionSucCallback的参数。原有接口web3j在底层实现的机制是通过轮训机制来获取交易回执结果的，而TransactionSucCallback是通过服务端，也就是区块链节点来主动push通知，相比之下，会更有效率和时效性。当触发到onResponse的时候，代表这笔交易已经成功上链。使用例子：
	ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
	Uint256 num = new Uint256(Num);
	ok.trans(num, new TransactionSucCallback() {
	@Override
    public void onResponse(EthereumResponse response) {
              TransactionReceipt transactionReceipt = objectMapper.readValue(ethereumResponse.getContent(), TransactionReceipt.class);
              //解析event log
   	}
});
```

<br>

[返回目录](#目录)

<br>

# 9. FAQ

## 9.1 使用工具包生成合约Java Wrap代码时会报错

从 https://github.com/FISCO-BCOS/web3sdk 下载代码（Download ZIP），解压之后，目录名称将为：web3sdk-master，运行gradle命令后生成的工具包中，/dist/apps目录下生成的jar包名称为web3sdk-master.jar，导致和/dist/bin/web3sdk中配置的CLASSPATH中的配置项$APP_HOME/apps/web3sdk.jar名称不一致，从而导致使用工具包生成合约Java Wrap代码时会报错。

## 9.2 工具目录下，dist/bin/web3sdk运行会出错

可能是权限问题，请尝试手动执行命令：chmod +x  dist/bin/web3sdk。

## 9.3 java.util.concurrent.ExecutionException: com.fasterxml.jackson.databind.JsonMappingException: No content to map due to end-of-input

> 出现此问题请按顺序尝试来排查：

(1) 检查配置是否连接的是channelPort，需要配置成channelPort。<br />
(2) 节点listen ip是否正确，最好直接监听0.0.0.0。<br />
(3) 检查channelPort是否能telnet通，需要能telnet通。如果不通检查网络策略，检查服务是否启动。<br />
(4) 服务端和客户端ca.crt是否一致，需要一致。<br />
(5) [FISCO-BCOS中client.keystore 的生成方法](https://github.com/FISCO-BCOS/web3sdk/issues/20)

<br>

[返回目录](#目录)

<br>
