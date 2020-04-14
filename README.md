![](https://github.com/FISCO-BCOS/FISCO-BCOS/raw/master/docs/images/FISCO_BCOS_Logo.svg?sanitize=true)

English / [中文](doc/README_CN.md)

# Web3SDK

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Build Status](https://travis-ci.org/FISCO-BCOS/web3sdk.svg?branch=master)](https://travis-ci.org/FISCO-BCOS/web3sdk)
[![CodeFactor](https://www.codefactor.io/repository/github/fisco-bcos/web3sdk/badge)](https://www.codefactor.io/repository/github/fisco-bcos/web3sdk)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a2a6c2eb499e42739d066ff775d1b288)](https://www.codacy.com/app/fisco/console?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=FISCO-BCOS/console&amp;utm_campaign=Badge_Grade)
[![GitHub All Releases](https://img.shields.io/github/downloads/FISCO-BCOS/web3sdk/total.svg)](https://github.com/FISCO-BCOS/web3sdk)
---
Web3SDK provides the Java API for [FISCO-BCOS](https://github.com/FISCO-BCOS/FISCO-BCOS/tree/master). You can easily and quickly develop your blockchain applications based on the Web3SDK. **The version only supports** [FISCO BCOS 2.0+](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/introduction.html).

<div align="center">
  <img src="./images/sdk.png" width = "600" height = "420"/>
</div>

## Features

- Implement [JSON-RPC](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/api.html) of FISCO BCOS.
- Provide [precompiled contracts](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/manual/smart_contract.html#precompiled-contract-development) to manage the blockchain. 
- Implement [AMOP](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/manual/amop_protocol.html)(Advanced Messages Onchain Protocol) to provide a secure and efficient message channel for the consortium blockchain.
- Provide [OSCCA-approved cryptography](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/manual/guomi_crypto.html) to send transactions.

## Compile

**Environmental requirements**:
- [JDK8 or above](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/java_sdk.html#environment-requirements)
- Gradle 5.0 or above

**Compile web3sdk using the following command**:
```shell
$ cd web3sdk
$ ./gradlew build
```

**The artifact is located at**:
```shell
web3sdk/dist/apps/web3sdk.jar
```

## Documentation
  - [**English**](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/java_sdk.html)
  - [**Chinese**](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk.html)

## Quickstart
[spring boot starter](https://github.com/FISCO-BCOS/spring-boot-starter) for Web3SDK is available that demonstrates a number of core features, including:

- Connecting to a node on the FISCO BCOS network.
- Deploying a smart contract to the network.
- Reading a value from the deployed smart contract.
- Updating a value in the deployed smart contract.
- Providing integration tests demonstrating for the above features.

## Code Contribution
  - If this project is useful to you, please star us on GitHub project page.
  - Pull requests. See [CONTRIBUTING](CONTRIBUTING.md).
  - Report bugs, issues or feature requests. Using [GitHub issues](https://github.com/FISCO-BCOS/web3sdk/issues/new).

## Join Our Community

The FISCO BCOS community is one of the most active open-source blockchain communities in China. It provides long-term technical support for both institutional and individual developers and users of FISCO BCOS. Thousands of technical enthusiasts from numerous industry sectors have joined this community, studying and using FISCO BCOS platform. If you are also interested, you are most welcome to join us for more support and fun.

![](https://media.githubusercontent.com/media/FISCO-BCOS/LargeFiles/master/images/QR_image_en.png)
 

## License
![license](http://img.shields.io/badge/license-Apache%20v2-blue.svg)

All contributions are made under the [Apache License 2.0](http://www.apache.org/licenses/). See [LICENSE](LICENSE).