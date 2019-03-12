[English](../README.md) / 中文

# Web3SDK

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Build Status](https://travis-ci.org/FISCO-BCOS/web3sdk.svg?branch=release-2.0.1)](https://travis-ci.org/FISCO-BCOS/web3sdk)
[![CircleCI](https://circleci.com/gh/FISCO-BCOS/web3sdk/tree/release-2.0.1.svg?style=shield)](https://circleci.com/gh/FISCO-BCOS/web3sdk/tree/release-2.0.1)
[![CodeFactor](https://www.codefactor.io/repository/github/fisco-bcos/web3sdk/badge)](https://www.codefactor.io/repository/github/fisco-bcos/web3sdk)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c9769615cc364a408969b088e90d7912)](https://www.codacy.com/app/fisco/web3sdk?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=FISCO-BCOS/web3sdk&amp;utm_campaign=Badge_Grade)
---
Web3SDK为[FISCO BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/release-2.0.1)提供Java API。利用FISCO BCOS JAVA SDK可以简单快捷的基于FISCO-BCOS进行区块链应用开发。

<div align="center">
  <img src="../images/sdk.png" width = "600" height = "420"/>
</div>

## 关键特性

- 实现FISCO BCOS的[JSON-RPC](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/api.html)的Java API。
- 提供[Solidity](https://solidity.readthedocs.io/en/latest/)合约代码转换成Java合约代码的方法。
- 提供[Precompiled Service API](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html#precompiled-service-api)。
- 提供国密支持[Guomi support](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/manual/guomi_crypto.html)。

## 快速入门
提供基于SDK的[spring boot starter](https://github.com/FISCO-BCOS/spring-boot-starter)示例项目，示例项目使用了SDK的核心特性, 包括:

- 如何连接FISCO BCOS区块链节点
- 在区块链网络中部署合约
- 读取部署合约的变量值
- 更新部署合约的变量值
- 提供SDk API接口的测试案例

## 文档
- [English](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html)
- [中文](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html)

## 贡献代码
欢迎参与FISCO BCOS的社区建设：
- 点亮我们的小星星(点击项目左上方Star按钮)。
- 提交代码(Pull requests)，参考我们的[代码贡献流程](CONTRIBUTING_CN.md)。
- [提问和提交BUG](https://github.com/FISCO-BCOS/web3sdk/issues)。
- 在[微信群](images/WeChatQR.jpeg)和[Gitter](https://gitter.im/fisco-bcos/Lobby)参与讨论。

## 社区生态

**金链盟**开源工作组，获得金链盟成员机构的广泛认可，并由专注于区块链底层技术研发的成员机构及开发者牵头开展工作。其中首批成员包括以下单位(排名不分先后): 博彦科技、华为、深证通、神州数码、四方精创、腾讯、微众银行、越秀金科。

- 微信群：[![Scan](https://img.shields.io/badge/style-Scan_QR_Code-green.svg?logo=wechat&longCache=false&style=social&label=Group)](images/WeChatQR.jpeg) 

- Gitter：[![Gitter](https://img.shields.io/badge/style-on_gitter-green.svg?logo=gitter&longCache=false&style=social&label=Chat)](https://gitter.im/fisco-bcos/Lobby) 

- Twitter：[![](https://img.shields.io/twitter/url/http/shields.io.svg?style=social&label=Follow@FiscoBcos)](https://twitter.com/FiscoBcos)

- e-mail：[![](https://img.shields.io/twitter/url/http/shields.io.svg?logo=Gmail&style=social&label=service@fisco.com.cn)](mailto:service@fisco.com.cn)

## License
![license](http://img.shields.io/badge/license-Apache%20v2-blue.svg)

Web3SDK的开源协议为[Apache License 2.0](http://www.apache.org/licenses/). 详情参考[LICENSE](../LICENSE)。