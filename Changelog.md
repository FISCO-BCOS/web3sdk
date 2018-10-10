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
