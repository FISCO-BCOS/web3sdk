### V1.2.2 (2018-08-29)

* Updates

1. 增加错误提示。
2. 透传节点的json rpc错误。
3. 默认连接时间从3秒改到30秒。
4. TestOk中的Future设置超时时间。
5. blockLimit可以配置
```
 <bean id="blockLimit" class="org.bcos.web3j.utils.BlockLimit">
  <constructor-arg value="500"/>
 </bean>
```
6. jsonrpc线程池可以配置
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
