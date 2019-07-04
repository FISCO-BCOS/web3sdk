### v2.0.4

(2019-07-04)

增加
1. 添加交易解析工具

* Compatibility

1. 兼容rc1，rc2, rc3, v2.0.0的节点

### v2.0.3

(2019-05-28)

增加
1. 提供CRUDService类，包含操作用户表的CRUD接口。
2. 提供加载账号文件的工具管理类PEMManager和P12Manager，可以分别加载PEM格式和PKCS12格式的账戶文件。
3. 增加集成测试，覆盖web3j api和precompile api。

更新
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