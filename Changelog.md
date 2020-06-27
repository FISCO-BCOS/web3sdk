### V1.2.7 (2020-06-27)

* 删除
1. druid依赖

* 更新
1. netty-tcnative升级至2.0.30.Final版本

### V1.2.6 (2020-06-09)

* 删除
1. fastjson依赖

* 更新
1. netty-all升级至4.1.50.Final版本
2. spring升级至4.3.27.RELEASE版本
3. slf4j升级至1.7.30版本
4. jackson-databind升级至2.11.0版本
4. guava至升级29.0-jre版本

### V1.2.5 (2019-01-17)
* Updates

1. 安全考虑，删除InfuraHttpService。
2. 使用安全随机数。
3. 增加rpc接口getAdminPeers。
4. 修复web3sdk作为代理时出现未知消息的日志的问题。

### V1.2.4 (2018-10-17)
* Updates

1. 安全考虑，升级依赖库。
2. 部分代码优化。
3. 配合后端解决blocknumber不是统一16进制的问题。
4. 修改EthBlock getnonce() 函数。
5. 修改日志配置文件，简化日志配置。
6. 修改compile.sh脚本，增加 .gradlew脚本。可以直接执行./gradlew build。
```
删除Async 中的@Component注解

```

### V1.2.3 (2018-10-10)
* Updates

1. 增加web3sdk编译脚本compile.sh(脚本带有安装gradle功能)
2. tools/bin目录下增加counter_compile.sh脚本, 从https://raw.githubusercontent.com/FISCO-BCOS/FISCO-BCOS-DOC/master/docs/web3sdk/codes/Counter.sol下载示例solidity源码并编译
3. 修改tools/bin/web3sdk脚本，增加脚本执行异常判断，异常后终止脚本，并输出错误信息
4. applicationContext.xml增加必要的解释说明

### V1.2.2 (2018-08-29)

* Updates

1. 增加错误提示。
2. 默认连接时间从3秒改到30秒。
3. TestOk中的Future设置超时时间。
4. 交易轮询线程池可以配置
```
 <bean id="async" class="org.bcos.web3j.utils.Async">
   <constructor-arg type="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor" ref="pool" />
 </bean>

 <bean id="pool" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
   <property name="corePoolSize" value="50" />
   <property name="maxPoolSize" value="100" />
   <property name="queueCapacity" value="500" />
   <property name="keepAliveSeconds" value="60" />
   <property name="rejectedExecutionHandler">
    <bean class="java.util.concurrent.ThreadPoolExecutor.AbortPolicy" />
   </property>
 </bean>
```

### V1.2.1 (2018-07-02)

* Added

1. UTXO支持web3sdk调用

### V1.2.0 (2018-06-11)  

* Added
1. web3sdk增加系统合约部署和调用工具。
2. web3sdk增加命令行调用取块高，视图等rpc接口。
3. 增加web3sdk使用SM3交易HASH运算功能
4. 增加web3sdk使用SM2发送国密交易功能。
### V1.1.0 (2018-03-28)  
**本版本增加了对CNS（合约命名服务）的修改**
* Updates  
1. 增加ca.crt和client.keystore 的配置。
2. 增加证书密码的配置
3. service run 的sleep时间修改
4. 支持使用java部署系统合约
* Added:
1. 增加cns的支持。
