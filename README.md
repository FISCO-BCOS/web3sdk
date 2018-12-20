web3jsdk:  JAVA FISCO-BCOS API
==================================

Web3sdk provide the java API for fisco-bcos node, the project fork from web3j of Ethereum and add  the corresponding modification according to the FISCO-BCOS characteristics.
This allows you to work with the  FISCO-BCOS blockchain, without the additional overhead of having to write your own integration code for the platform.   

![sdk.png](./images/sdk.png)
Features
--------

- Complete implementation of fisco-bcos's [JSON-RPC]( https://github.com/ethereum/wiki/wiki/JSON-RPC)
- Auto-generation of Java smart contract wrappers to create, deploy, transact with and call smart
  contracts from native Java code
  [Solidity Introduce](https://solidity.readthedocs.io/en/v0.4.24)
  
- [Contract Name Service (CNS)](https://ens.domains)
- Comprehensive integration tests demonstrating a number of the above scenarios
- Command line tools
- [AMOP]()
- [Guomi support](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/guomi/index.html)


Getting started
---------------

Typically your application should depend on release versions of web3j.
| Add the relevant dependency to your project. recommend using Java 8 and gradle（4.10.1 and above）:

Maven
-----

    <dependency>
	    <groupId>org.fisco-bcos</groupId>
	    <artifactId>web3sdk</artifactId>
	    <version>2.0.0</version>
    </dependency>


Gradle
------

  	compile ('org.fisco-bcos:web3sdk:2.0.0')


 Build instructions
------------------

web3jsdk includes integration tests for running against a live fisco-bcos node.
 For Consortium blockchain, you should have the access permissions to connect the node. So copy the ca.crt file and keystore.p12 file to the resource directory of the project.
 For more details about how to config your sdk to connect to fisco-bcos node , see Resources [SDK CONFIG](http://wiki.weoa.com/books/fisco-bcos/page/sdk-%E4%BD%BF%E7%94%A8)

To run a full build (including integration tests):

```bash
$ ./gradlew build
```

To run the integration tests:

   - getBlockNumberTest:

```bash
  ./gradlew test --tests org.fisco.bcos.channel.test.block.BlockTest
```

   - DeployContractTest:
```bash
  ./gradlew test --tests org.fisco.bcos.channel.test.contract.OkTest
```
   - BasicTest:
```bash
  ./gradlew test --tests org.fisco.bcos.channel.test.BasicTest
```



## Community

Financial Blockchain Shenzhen Consortium (FISCO) has attracted more than 100 members including financial institutions and financial information service companies so far. The first members include the following organizations: Beyondsoft, Huawei, Shenzheng, Shenzhou Digital, Forms Syntron, Tencent, WeBank, Yuexiu Jinke.

- Join our WeChat [![Scan](https://img.shields.io/badge/style-Scan_QR_Code-green.svg?logo=wechat&longCache=false&style=social&label=Group)](docs/images/WeChatQR.jpeg) 


- Discuss in [![Gitter](https://img.shields.io/badge/style-on_gitter-green.svg?logo=gitter&longCache=false&style=social&label=Chat)](https://gitter.im/fisco-bcos/Lobby) 


- Read news by [![](https://img.shields.io/twitter/url/http/shields.io.svg?style=social&label=Follow@FiscoBcos)](https://twitter.com/FiscoBcos)


- Mail us at [![](https://img.shields.io/twitter/url/http/shields.io.svg?logo=Gmail&style=social&label=service@fisco.com.cn)](mailto:service@fisco.com.cn)
