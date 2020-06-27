### v2.1.5

(2020-06-27)

* 删除
1. druid依赖

* 更新
1. netty-tcnative升级至2.0.30.Final版本
2. Java-WebSocket升级至1.5.1版本
3. commons-collections4升级至4.4版本

* 兼容

1. 兼容Channel Message v1协议
2. 兼容FISCO BCOS 2.1以下的sdk证书名node.crt和node.key

### v2.1.4

* 删除
1. fastjson依赖

* 更新
1. netty-all升级至4.1.50.Final版本
2. spring升级至4.3.27.RELEASE版本
3. slf4j升级至1.7.30版本
4. jackson-databind升级至2.11.0版本
4. guava至升级29.0-jre版本

* 兼容

1. 兼容Channel Message v1协议
2. 兼容FISCO BCOS 2.1以下的sdk证书名node.crt和node.key

### v2.1.3

(2020-03-23)
* 增加
1. 新增ECC加密/解密工具.

### v2.1.2

(2019-11-14)
* 修复
1. 修复国密服务无法使用的bug.


### v2.1.1

(2019-10-29)

* 增加
1. 生成`java`合约代码添加`input`和`output`的解析接口

* 更新
1. 修复`SDK`与节点握手协议的漏洞
2. 部署合约`SDK`时不再通过轮询方式判断是否成功
3. 修复`TransactionReceipt`类的`isStatusOK`接口抛出异常的问题

* 兼容

1. 兼容Channel Message v1协议
2. 兼容FISCO BCOS 2.1以下的sdk证书名node.crt和node.key

### v2.1.0

(2019-09-17)

* 增加

1. [合约事件通知](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk.html#id14)
2. 支持[Channel Message v2通信协议](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/design/protocol_description.html#channelmessage-v2)
3. 区块回调接口
4. [增加AMOP认证功能](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/manual/amop_protocol.html#topic)

* 更新

1. 支持手动配置证书名，默认为sdk.crt和sdk.key
2. 发交易时处理立即返回的RPC消息
3. 更新依赖库的版本号
4. 更详细的日志打印

* 修复

1. 修复了ABI空字符串处理的异常问题
2. 修复了发交易blocklimt的异常问题
3. 其它一些bug

* 兼容

1. 兼容Channel Message v1协议
2. 兼容FISCO BCOS 2.1以下的sdk证书名node.crt和node.key


### v2.0.5

(2019-07-10)

* 更新

1. 升级fastjson库至1.2.58
2. 修复AMOP传输字符串bug

* Compatibility

1. 兼容rc1，rc2, rc3, v2.0.0的节点

### v2.0.4

(2019-07-04)

* 增加

1. 添加交易解析工具

* Compatibility

1. 兼容rc1，rc2, rc3, v2.0.0的节点

### v2.0.3

(2019-05-28)

* 增加

1. 提供CRUDService类，包含操作用户表的CRUD接口。
2. 提供加载账号文件的工具管理类PEMManager和P12Manager，可以分别加载PEM格式和PKCS12格式的账戶文件。
3. 增加集成测试，覆盖web3j api和precompile api。

* 更新

1. 优化日志格式，调整日志输出内容，可以更详细显示交易流程信息。
2. 优化选择节点块高最大的节点发送交易。
3. 支持多群组区块链前置配置。
4. SDK配置文件中，机构属性字段修改为agencyName。

* Compatibility

1. 兼容rc1，rc2, rc3的节点


### v2.0.0-rc2

(2019-04-25)

* 增加

1. 可并行合约开发框架ParallelContract.sol
2. 并行预编译转账合约DagTransferPrecompiled的压测程序
3. 并行solidity转账合约parallelOk的压测程序
4. CRUD合约的压测程序
5. 回滚合约的压测程序

* 更新

1. 在交易编码中加入chainID和groupID，以支持rc2节点的交易格式
2. sol转java，在java文件中增加了abi字段。

* Compatibility

1. 兼容rc1，rc2的节点

### v2.0.0-rc1

(2019-03-18)

* 增加 

1. 提供多群组支持
2. 提供Spring Boot配置以及demo项目
3. 提供模块化的单元测试，新增使用示例
4. 增加Precompiled Service接口，实现对区块链相关配置的管理，及实现特定预编译合约的功能

* 更新

1. 同步以太坊最新代码，支持动态数组返回，支持最新0.5.x合约
2. 优化合约编译流程，无需下载solcj即可直接编译合约生成abi bin和java合约文件
3. 升级Web3j，修改Web3j接口名

* Compatibility

1. 兼容rc1的节点