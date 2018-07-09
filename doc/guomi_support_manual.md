# web3sdk对国密版FISCO BCOS的支持

# 目录
<!-- TOC -->

- [1. 基本介绍](#1-基本介绍)
- [2. 适配于国密版FISCO BCOS的web3sdk环境部署](#2-适配于国密版fisco-bcos的web3sdk环境部署)
    - [2.1 依赖部署](#21-依赖部署) 
        - [2.1.1 安装依赖软件](#211-安装依赖软件)
        - [2.1.2 部署国密版FISCO BCOS](#212-部署国密版fisco-bcos)
    - [2.2 部署web3sdk](#22-部署web3sdk)
        - [2.2.1 编译web3sdk源码](#221-编译web3sdk源码)
        - [2.2.2 生成客户端证书](#222-生成客户端证书)
        - [2.2.3 开启国密算法，配置链上节点信息和证书](#223-开启国密算法配置链上节点信息和证书)
- [3. 国密版FISCO BCOS系统合约部署](#3-国密版fisco-bcos系统合约部署)
- [4. 生成国密秘钥对和账户](#4-生成国密秘钥对和账户)
- [5. 系统合约工具和测试工具的使用](#5-系统合约工具和测试工具的使用)
    - [5.1 系统合约介绍](#51-系统合约介绍)
    - [5.2 系统合约工具使用方法](#52-系统合约工具使用方法)
    - [5.3 测试工具使用方法](#53-测试工具使用方法)
- [6. 生成支持国密算法的java代码](#6-生成支持国密算法的java代码)
- [7. 注意事项（关于Credentials对象初始化）](#7-注意事项关于credentials对象初始化)

<!-- /TOC -->

<br>

# 1. 基本介绍
<br>
FISCO BCOS支持国密算法，具体包括：


- P2P连接使用国密SSL
- 支持GM CA证书链验证
- 支持节点共识使用国密密钥对进行签名验签操作
- 支持web3sdk使用国密密钥对发送交易,eth端验证交易
- 支持数据落盘加密使用国密SM4算法进行加密

<br>

详细信息可参考[FISCO BCOS 国密特性文档](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md#12-国密版fisco-bcos特性列表). 本文档主要介绍如何在开启国密验证算法的FISCO BCOS环境下，使用web3sdk客户端部署系统合约、发交易以及使用系统合约工具。
主要包括如下内容：

<br>

- 适配于国密版FISCO BCOS的web3sdk环境配置
- 为开启国密验证算法的FISCO BCOS部署系统合约
- 系统合约工具的使用
- 如何使用web3sdk工具将合约代码转换成支持国密算法的java代码，并调用该代码发交易

<br>

[返回目录](#目录)

<br>
<br>

# 2. 适配于国密版FISCO BCOS的web3sdk环境部署

## 2.1 依赖部署

### 2.1.1 安装依赖软件

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

### 2.1.2 部署国密版FISCO BCOS

在FISCO BCOS基础上**开启国密验证算法**，可参考[FISCO BCOS 国密特性文档](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md).


<br>

[返回目录](#目录)

<br>

## 2.2 部署web3sdk

<br>

依赖部署完毕后，可拉取web3sdk代码，编译客户端代码，配置链上节点信息，主要包括如下过程：

### 2.2.1 编译web3sdk源码

<br>

- 从git上拉取代码
- 编译web3sdk源码，生成jar包

```bash
#=== 进入web3sdk源码放置目录（假设为/mydata/）=====
$ mkdir -p /mydata
$ cd /mydata

#==== 拉取git代码 ====
$ git clone https://github.com/FISCO-BCOS/web3sdk

#===编译web3sdk源码，生成dist目录 ===
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


### 2.2.2 生成客户端证书

<br>

> 国密版客户端证书ca.crt, client.keystore生成方法请参考[国密操作文档的生成客户端证书](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/%E5%9B%BD%E5%AF%86%E6%93%8D%E4%BD%9C%E6%96%87%E6%A1%A3.md#32-%E7%94%9F%E6%88%90%E5%AE%A2%E6%88%B7%E7%AB%AF%E8%AF%81%E4%B9%A6)一节

<br>

### 2.2.3 开启国密算法，配置链上节点信息和证书

<br>

编译成功后, web3sdk主要配置文件存放于dist/conf目录下，主要包括如下设置（详细配置方法可参考[web3sdk使用指南]( https://github.com/FISCO-BCOS/web3sdk) 第(三)部分）：<br> 

<br>

| 配置文件                   | 详细说明                                     |
| ---------------------- | ---------------------------------------- |
| applicationContext.xml | 主要包括三项配置: <br> - encryptType: 配置国密算法开启/关闭开关，0表示不使用国密算法发交易，1表示开启国密算法发交易，默认为0(即不使用国密算法发交易) ;  <br>  - systemProxyAddress: 配置FISCO BCOS系统合约地址, 部署系统合约成功后，要将systemProxyAddress对应的值改为部署的系统合约地址;  <br> - privKey: 向FISCO BCOS节点发交易或发消息的账户私钥，**国密版用户公私钥对和账户的生成方法请参考[生成国密秘钥对和账户一章](#4-生成国密秘钥对和账户)** <br> - ChannelConnections：配置FISCO BCOS节点信息和证书，证书相关配置如下:  <br>  (1) caCertPath: CA证书路径，默认为dist/conf/ca.crt; <br> (2) clientKeystorePath: 客户端证书路径，默认为dist/conf/client.keystore; <br> (3)keystorePassWord: 客户端证书文件访问口令, 默认为123456; <br> (4) clientCertPassWord: 客户端证书验证口令, 默认为123456 |
| ca.crt                 | CA证书，必须与链上FISCO BCOS节点CA证书保持一致           |
| client.keystore        | 客户端证书(默认访问口令和验证口令均为123456)               |
| 日志配置文件                 | - commons-logging.properties： 配置日志类, 默认为org.apache.commons.logging.impl.SimpleLog;  <br>  - log4j2.xml：日志常见配置，包括路径、格式、缓存大小等; <br> - simplelog.properties: 日志级别设置，默认为WARN |

开启国密算法时，applicationContext.xml配置如下(将encryptType设置为1)：(注：web3sdk端开启国密算法用于发交易时，其连接的链上节点必须是国密版本的FISCO BCOS，否则交易无法被验证) <br>

```xml
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
                <constructor-arg value="1"/> <!--### encyptType设置为1，web3sdk端开启国密验证，使用国密签名算法和hash算法向FISCO BCOS发交易-->
        </bean>

        <bean id="toolConf" class="org.bcos.contract.tools.ToolConf"> <!--===系统合约部署成功后，将systemProxyAddress设置为系统合约地址====-->
                <property name="systemProxyAddress" value="0x919868496524eedc26dbb81915fa1547a20f8998" />
                <property name="privKey" value="204851937051ba3192100417a79fe3b2fe88d99aff8c861b86a5fbd6fa8a108d" /> <!--====向FISCO BCOS节点发消息或交易的账户私钥===-->
                <property name="account" value="0xe519346a02b88cac6f91b52acf7c3951ed6cdb1e" /> <!--权限控制部分配置-->
                <property name="outPutpath" value="./output/" />
        </bean>

        <bean id="channelService" class="org.bcos.channel.client.Service">
                <property name="orgID" value="WB" /> <!--机构名称-->
                <property name="connectSeconds" value="10" />
                <property name="connectSleepPerMillis" value="10" />
                <property name="allChannelConnections">
                        <map>
                                <entry key="WB"> <!--机构节点配置，key与"orgID"配置值一致-->
                                        <bean class="org.bcos.channel.handler.ChannelConnections">
                                                <property name="caCertPath" value="classpath:ca.crt" />  <!--###CA证书路径, 默认是dist/conf/ca.crt, 必须与连接节点CA证书保持一致-->
                                                <property name="clientKeystorePath" value="classpath:client.keystore" /> <!--客户端证书路径, 默认是dist/conf/client.keystore-->
                                                <property name="keystorePassWord" value="123456" /> <!--访问客户端keystore证书的口令,默认是123456-->
                                                <property name="clientCertPassWord" value="123456" /> <!--客户端证书验证口令, 默认是123456-->
                        <property name="connectionsStr">
                                                        <list> <!--##连接信息，包括要连接节点的IP和channelPort, 节点名称可任意填，无限制##-->
                                                                <value>node1@10.107.105.81:7703</value>  
                                                        </list>
                                                </property>
                    </bean>
                                </entry>
                        </map>
                </property>
        </bean>
</beans>
```

<br>

[返回目录](#目录)

<br>



# 3. 国密版FISCO BCOS系统合约部署

部署完适配于[国密版FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md)的web3sdk后，可调用InitSystemContract部署系统合约，系统合约部署工具InitSystemContract由`src/main/java/org/bcos/contract/tools/InitSystemContract.java`调用合约生成的java代码实现，使用如下命令部署系统合约：

```bash
##进入bin目录(设web3sdk位于/mydata目录下)
cd /mydata/web3sdk/dist/bin && chmod a+x web3sdk
####执行部署工具InitSystemContract部署系统合约: ./web3sdk InitSystemContract
[app@VM_105_81_centos bin]$ ./web3sdk InitSystemContract
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

**(1) 设置配置文件的系统合约地址：将dist/conf/applicationContext.xml的systemProxyAddress字段更新为输出的系统合约地址**

**(2) 设置私钥地址：参考 [4.生成国密秘钥对和账户](#4-生成国密秘钥对和账户) ，生成国密秘钥对和账户，将dist/conf/applicationContext.xml的privKey更新为生成的国密秘钥privateKey**

执行完上述两步操作后，按照[FISCO BCOS 国密特性文档](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md#7-多记账节点组网) **多记账节点组网**一节进行加入新节点等操作。

<br>
<br>

[返回目录](#目录)

<br>

# 4. 生成国密秘钥对和账户

web3sdk提供了国密版秘钥对和账户生成工具GenGmAccount, 该工具使用方法如下：

**(1) 生成国密版秘钥对和账户**

**调用方法**: java -cp 'conf/:apps/\*:lib/\*' org.bcos.contract.tools.GenGmAccount genkey ${keyFile}

**功能和参数说明**: 调用国密算法，生成国密版秘钥对和账户，并将结果存于${keyFile}指定的文件中，**其中${keyFile}参数可选**，当没有传入该参数时，默认将结果存储于文件key.info中

GenGmAccount genkey工具一个调用示例如下：

```bash
[app@VM_105_81_centos dist]$ java -cp 'conf/:apps/*:lib/*' org.bcos.contract.tools.GenGmAccount genkey
-------------------------------------------------------------------------------
==========Generate (private key, public key, account) For Guomi randomly =======
=====INIT GUOMI KEYPAIR ====
===public:0ef2a3aef6f51574c62330ba99cf89b005e966f54b016cd62510e51c5d9765af9d928f166bc5c5bb9115c496d6dda8dbd4bac2d025f9b1229d1e0305b4bf8aeb
===private:b888fcc5cf685230ebdd2705493bebec5826d3bc8a2b4d332d34ef7b06135c6b
===GEN COUNT :[B@1c3a4799
DeduceAccountFromPublic failed, error message:exception decoding Hex string: String index out of range: 127
==== generate private/public key with GuoMi algorithm failed ====
[app@VM_105_81_centos dist]$ cat key.info 
{"privateKey":"d8d4e29b18252d7415ab0dfcf3fa1f0abc11bac1de254bf1d91c4a8866e1282a","publicKey":"6de57330ec3d4360834af935fef512bc4b785b66772c02afe2148b68da9c7d900b3ddd773f28595481f83cd69ec9de6ebb287762727cb5db9f08a031d89af1c9","account":"0xc874bcb663c2fbbe9aa66f12d10953e60d9d3cd9"}
[app@VM_105_81_centos dist]$ 
```

>**注：请妥善保存生成的私钥，该私钥用于向FISCO BCOS节点发交易或消息；发交易前，请先将web3sdk/dist/conf/applicationContext.xml的privKey字段设置为privateKey的值**

<br>
<br>

**(2) 加载国密版秘钥对和账户**

**调用方法**: java -cp 'conf/:apps/\*:lib/\*' org.bcos.contract.tools.GenGmAccount load ${keyFile}

**功能和参数说明**: 从${keyFile}指定的文件加载国密版秘钥对和账户到内存，**其中${keyFile}参数必选**

GenGmAccount load工具一个调用示例如下：

```bash
[app@VM_105_81_centos dist]$ java -cp 'conf/:apps/*:lib/*' org.bcos.contract.tools.GenGmAccount load key.info 
read file key.info, result:{"privateKey":"d8d4e29b18252d7415ab0dfcf3fa1f0abc11bac1de254bf1d91c4a8866e1282a","publicKey":"6de57330ec3d4360834af935fef512bc4b785b66772c02afe2148b68da9c7d900b3ddd773f28595481f83cd69ec9de6ebb287762727cb5db9f08a031d89af1c9","account":"0xc874bcb663c2fbbe9aa66f12d10953e60d9d3cd9"}

===key info:{"privateKey":"d8d4e29b18252d7415ab0dfcf3fa1f0abc11bac1de254bf1d91c4a8866e1282a","publicKey":"6de57330ec3d4360834af935fef512bc4b785b66772c02afe2148b68da9c7d900b3ddd773f28595481f83cd69ec9de6ebb287762727cb5db9f08a031d89af1c9","account":"0xc874bcb663c2fbbe9aa66f12d10953e60d9d3cd9"}

====LOADED KEY INFO ===
* private key:d8d4e29b18252d7415ab0dfcf3fa1f0abc11bac1de254bf1d91c4a8866e1282a
* public key :6de57330ec3d4360834af935fef512bc4b785b66772c02afe2148b68da9c7d900b3ddd773f28595481f83cd69ec9de6ebb287762727cb5db9f08a031d89af1c9
* account: 0xc874bcb663c2fbbe9aa66f12d10953e60d9d3cd9
=== LOAD GUOMI KEY INFO FROM key.info SUCCESS ===
```

<br>

[返回目录](#目录)

<br>
<br>

# 5. 系统合约工具和测试工具的使用


## 5.1 系统合约介绍

web3sdk将系统合约部署于链上，并可通过工具调用这些系统合约，部署的系统合约如下：

| 系统合约                   | 详细说明                                     |
| ---------------------- | ---------------------------------------- |
| SystemProxy            | 系统合约代理合约                                 |
| TransactionFilterChain | 设置transaction过滤器                         |
| ConfigAction           | 设置/获取区块链系统参数，可参考[系统参数说明文档](https://github.com/FISCO-BCOS/Wiki/tree/master/%E7%B3%BB%E7%BB%9F%E5%8F%82%E6%95%B0%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3) |
| ConsensusControlMg     | 联盟控制合约，可参考[联盟控制模板参考文档](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/%E5%BC%B9%E6%80%A7%E8%81%94%E7%9B%9F%E9%93%BE%E5%85%B1%E8%AF%86%E6%A1%86%E6%9E%B6%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3.md)             |
| CAAction               | 证书列表黑名单管理: 包括将证书加入黑名单列表，将制定证书从黑名单列表删除，获取证书黑名单列表功能              |
| ContractAbiMgr         | ABI相关合约                                  |

<br>

[返回目录](#目录)
<br>
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


**(2) 权限控制合约AuthorityFilter**

**调用方法**：

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


**(3) 节点管理合约NodeAction**

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

**② 退出节点**

**调用方法**

```bash
##设web3sdk代码位于/mydata/目录下
#----进入bin目录---
cd /mydata/web3sdk/dist/bin
chmod a+x web3sdk
./web3sdk NodeAction cancelNode ${node_json_path}
```

**功能**：将${node_json_path}指定的节点从FISCO BCOS区块链网络中退出(注: ${node_json_path}是节点配置文件相对于dist/conf的路径，节点配置文件说明同上)

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
$  ./web3sdk NodeAction all
===================================================================
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


**(4) 节点证书管理合约CAAction**

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


**(5) 系统参数配置合约ConfigAction**


**FISCO BCOS系统合约主要配置如下:**

<br>

| 配置项        | 详细说明          |
| ---------- | ------------- |
| maxBlockTransactions| 控制一个块内允许打包的最大交易数量上限 <br>  设置范围: [0, 2000) <br> 默认值:1000|
| intervalBlockTime| 设置出块间隔时间 <br> 设置范围：大于等于1000 <br> 默认值: 1000 |
| maxBlockHeadGas| 控制一个块允许最大Gas消耗上限 <br> 取值范围: 大于等于2000,000,000 <br> 默认值: 2000,000,000|
| maxTransactionGas| 设置一笔交易允许消耗的最大gas <br> 取值范围: 大于等于30,000,000 <br> 默认值: 30,000,000 |
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

**调用方法**: 在/mydata/web3sdk/dist/bin目录下执行./web3sdk ConfigAction set ${配置项} ${配置项的值}"

**功能**: 将记录在系统合约ConfigAction中${key}对应的值设置为${setted_value}(ConfigAction中记录的系统参数说明参考[系统参数说明文档](https://github.com/FISCO-BCOS/Wiki/tree/master/%E7%B3%BB%E7%BB%9F%E5%8F%82%E6%95%B0%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3))

<br>

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
<br>

**(6) 联盟控制合约ConsensusControl**

**① 部署联盟共识模板合约**

**调用方法**: ./web3sdk ConsensusControl deploy

<br>

**② 列出所有联盟共识合约地址**

**调用方法**: ./web3sdk ConsensusControl list

<br>

**③ 关闭联盟共识特性**

**调用方法**:  ./web3sdk ConsensusControl list turnoff

<br>



[返回目录](#目录)

<br>
<br>

## 5.3 测试工具使用方法

web3sdk提供了一些测试工具，方便确定web3sdk与[FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md)通信是否正常，本节简要介绍这些测试工具使用方法：

**(1) Ok合约测试工具**

**调用方法:** java -cp 'conf/:apps/\*:lib/\*' org.bcos.channel.test.TestOk
**说明:** 向链上部署Ok合约，并调用Ok合约的trans接口(Ok合约可参考[Ok.sol](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/tool/Ok.sol))

Ok合约调用示例如下:

```bash
[app@VM_105_81_centos dist]$ java -cp 'conf/:apps/*:lib/*' org.bcos.channel.test.TestOk
===================================================================
=====INIT GUOMI KEYPAIR from Private Key
====generate kepair from priv key:204851937051ba3192100417a79fe3b2fe88d99aff8c861b86a5fbd6fa8a108d
generate keypair data succeed
####create credential succ, begin deploy contract
####contract address is: 0xecf79838dc5e0b4c2834f27b3dd2706d77d5f548
###callback trans success
============to balance:org.bcos.web3j.abi.datatypes.generated.Uint256@ee87126b
```

<br>
<br>

**(2) Ethereum测试工具**

**调用方法:** java -cp 'conf/:apps/\*:lib/\*' org.bcos.channel.test.Ethereum
**说明:** Ethereum功能与Ok合约测试工具类似，也是向链上部署Ok合约，并调用相关接口(Ok合约可参考[Ok.sol](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/tool/Ok.sol))

Ethereum测试工具调用示例如下：

```bash
[app@VM_105_81_centos dist]$ java -cp 'conf/:apps/*:lib/*' org.bcos.channel.test.Ethereum
start...
===================================================================
=====INIT GUOMI KEYPAIR from Private Key
====generate kepair from priv key:204851937051ba3192100417a79fe3b2fe88d99aff8c861b86a5fbd6fa8a108d
generate keypair data succeed
Ok getContractAddress 0xa5db78544f7970ff04be172f03b0b236e4e3befb
receipt transactionHash0xf46894ad8e6a22eb06e99d9a6f471d12c9a3158a1c0605a2473b2e9f97e2fa19
ok.get() 999
```

<br>

[返回目录](#目录)

<br>
<br>

# 6. 生成支持国密算法的java代码

`dist/bin/compile.sh`脚本调用`src/main/java/org/bcos/web3j/codegen/SolidityFunctionWrapperGenerator.java`将合约代码转换成java代码，便于开发者基于web3sdk和智能合约开发新应用。本章主要介绍了如何使用compile.sh脚本生成java代码。

compile.sh脚本将放置于`dist/contracts`目录下的sol合约转换成java代码(dist/contracts目录下合约是编译web3sdk时，从tools/contracts目录下拷贝获取的)，主要用法如下：

**(1) 生成不支持国密特性的java代码**

**调用方法**: `bash compile.sh "${package_name}"`

**说明**: 

- 使用默认fisco-solc编译器编译dist/contracts/目录下所有合约代码，并将合约代码转换成java代码，java代码包名由${package_name}指定;
- 执行成功后，在dist/output/目录下生成相应的java代码(java代码相对dist/output路径由包名${package_name}决定);
- 使用该方法生成的java代码不支持使用国密算法发交易;
- 若系统没有安装合约编译器fisco-solc，请参考[fisco-solc](https://github.com/FISCO-BCOS/fisco-solc)编译安装编译器



**(2) 生成支持国密特性的java代码**

**调用方法**: `bash compile.sh "${package_name}" "${enable_guomi}" "${fisco_solc_guomi_path}"`

**参数说明**:

- ${package_name}: 生成的java代码import的包名;
- ${enable_guomi}: 表明生成的java代码是否要求支持使用国密算法发交易；0表示不支持国密算法，1表示支持国密算法;
- ${fisco_solc_guomi_path}: [国密版本fisco-solc编译器](https://github.com/FISCO-BCOS/fisco-solc) 路径，默认在\`which fisco-solc\`-guomi路径下，如何编译国密版本编译器可参考[编译国密版fisco-solc](https://github.com/FISCO-BCOS/fisco-solc/tree/master#312-编译国密版fisco-solc);

**说明**: 

- 使用默认fisco-solc编译器编译dist/contracts目录下所有合约代码，使用[国密版本fisco-solc编译器](https://github.com/FISCO-BCOS/fisco-solc/tree/master#312-编译国密版fisco-solc)编译dist/contacts目录下所有合约代码，并将其转换成支持国密算法的java代码，java代码包名由${package_name}指定;
- 执行成功后，在dist/output/目录下生成相应的java代码(java代码相对dist/output路径由包名${package_name}决定);
- 使用该方法生成的java代码支持使用国密算法发交易;
- 开启国密算法方法可参考[2.2.3 开启国密算法，配置链上节点信息和证书](#223-开启国密算法配置链上节点信息和证书)

<br>

[返回目录](#目录)

<br>
<br>

# 7. 注意事项（关于Credentials对象初始化）

web3sdk向链上发交易时，必须初始化Crendentials对象，为了便于用户调用，web3sdk在`package org.bcos.web3j.crypto`内抽象了`GenCredential`对象，用户可调用`public static Credentials create(String privKey)`接口初始化Credential对象：

使用示例如下（摘选自src/test/java/org/bcos/channel/test/TestOk.java）：

<br>

```java
import org.bcos.web3j.crypto.GenCredential;

//...省略若干行...

ToolConf toolConf=context.getBean(ToolConf.class);
//调用GenCredential的create接口初始化Crendentials对象
Credentials credentials = GenCredential.create(toolConf.getPrivKey()); 
if (credentials != null) {
	System.out.println("####create credential succ, begin deploy contract");
	java.math.BigInteger gasPrice = new BigInteger("300000000");
	java.math.BigInteger gasLimit = new BigInteger("300000000");
	java.math.BigInteger initialWeiValue = new BigInteger("0");
	Ok okDemo = Ok.deploy(web3, credentials, gasPrice, gasLimit, initialWeiValue).get();
	if (okDemo != null) {
		System.out.println("####contract address is: " + okDemo.getContractAddress());
		TransactionReceipt receipt = okDemo.trans(new Uint256(4)).get();
		System.out.println("###callback trans success");
		String toBalance = okDemo.get().get().toString();
		System.out.println("============to balance:" + toBalance);
	} else {
		System.out.println("deploy Ok contract failed");
		System.exit(1);
	}
	} else {
		System.out.println("create Credentials failed");
		System.exit(1);
	}
```

<br>

**当web3sdk与[国密版本的FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md)节点通信时，必须使用上述方法初始化Credentials对象**

老版web3sdk使用如下方法初始化Credentials对象，**但用该方法初始化的web3sdk不能与[国密版本的FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/国密操作文档.md)通信**：

<br>

```java
BigInteger bigPrivKey = new BigInteger(privKey, 16);
ECKeyPair keyPair = ECKeyPair.create(bigPrivKey);
if (keyPair == null)
    return null;
Credentials credentials = Credentials.create(keyPair);
```

<br>

[返回目录](#目录)

<br>
<br>

