English / [中文](doc/README_CN.md)

# Web3SDK

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Build Status](https://travis-ci.org/FISCO-BCOS/web3sdk.svg?branch=master)](https://travis-ci.org/FISCO-BCOS/web3sdk)
[![CircleCI](https://circleci.com/gh/FISCO-BCOS/web3sdk/tree/master.svg?style=shield)](https://circleci.com/gh/FISCO-BCOS/web3sdk/tree/master)
[![CodeFactor](https://www.codefactor.io/repository/github/fisco-bcos/web3sdk/badge)](https://www.codefactor.io/repository/github/fisco-bcos/web3sdk)
---
Web3SDK provides the Java API for [FISCO-BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/master). You can easily and quickly develop your blockchain applications based on the Web3SDK. **The version only supports** [FISCO BCOS 2.0](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/introduction.html).

<div align="center">
  <img src="./images/sdk.png" width = "600" height = "420"/>
</div>

## Features

- Implement [JSON-RPC](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/api.html) of FISCO BCOS.
- Provide [precompiled contracts](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/manual/smart_contract.html#id2) to manage the blockchain. 
- Implement [AMOP](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/manual/amop_protocol.html)(Advanced Messages Onchain Protocol) to provide a secure and efficient message channel for the consortium blockchain.
- Provide [Guomi algorithms](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/manual/guomi_crypto.html) to send transactions.

## Quickstart
A [spring boot starter](https://github.com/FISCO-BCOS/spring-boot-starter) for SDK is available that demonstrates a number of core features, including:

- Connecting to a node on the FISCO BCOS network.
- Deploying a smart contract to the network.
- Reading a value from the deployed smart contract.
- Updating a value in the deployed smart contract.
- Providing integration tests demonstrating for the above features.

## Documentation
- [**中文**](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html)

## Developing & Contributing
- Star our Github.
- Pull requests. See [CONTRIBUTING](CONTRIBUTING.md).
- [Ask questions](https://github.com/FISCO-BCOS/web3sdk/issues).
- Discuss in [WeChat group](doc/images/WeChatQR.jpeg)  or [Gitter](https://gitter.im/fisco-bcos/Lobby).

## Community

By the end of 2018, Financial Blockchain Shenzhen Consortium (FISCO) has attracted and admitted more than 100 members from 6 sectors including banking, fund management, securities brokerage, insurance, regional equity exchanges, and financial information service companies. The first members include the following organizations: Beyondsoft, Huawei, Shenzhen Securities Communications, Digital China, Forms Syntron, Tencent, WeBank, Yuexiu FinTech.

- Join our WeChat [![Scan](https://img.shields.io/badge/style-Scan_QR_Code-green.svg?logo=wechat&longCache=false&style=social&label=Group)](doc/images/WeChatQR.jpeg) 

- Discuss in [![Gitter](https://img.shields.io/badge/style-on_gitter-green.svg?logo=gitter&longCache=false&style=social&label=Chat)](https://gitter.im/fisco-bcos/Lobby) 

- Read news by [![](https://img.shields.io/twitter/url/http/shields.io.svg?style=social&label=Follow@FiscoBcos)](https://twitter.com/FiscoBcos)

- Mail us at [![](https://img.shields.io/twitter/url/http/shields.io.svg?logo=Gmail&style=social&label=service@fisco.com.cn)](mailto:service@fisco.com.cn)

## License
![license](http://img.shields.io/badge/license-Apache%20v2-blue.svg)

All contributions are made under the [Apache License 2.0](http://www.apache.org/licenses/). See [LICENSE](LICENSE).