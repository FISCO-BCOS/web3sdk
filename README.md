[![image for the blockchain contest](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/imgs/application_contest.png "点击图片报名")](https://con.geekbang.org/)

## 技术文档

1 #配置
# SDK编译

       #=== 创建并进入web3sdk源码放置目录（假设为~/mydata/）=====
       $ mkdir -p ~/mydata
       $ cd ~/mydata

       #==== 拉取git代码 ====
       $ git clone https://github.com/FISCO-BCOS/web3sdk

       #===编译we3bsdk源码，生成dist目录 ===
       $ cd web3sdk

       拷贝证书文件ca.crt和client.keystore到web3sdk/src/test/resources目录下；
       找到 web3sdk/src/test/resources/applicationContext.xml文件的channelService, 增加<property name="groupId" value="1" /> groupId配置。
      查询块高 gradle test --tests  org.fisco.bcos.channel.test.block.BlockTest
      部署OK合约 gradle test --tests  org.fisco.bcos.channel.test.contract.OkTest
      压力测试  gradle test --tests  org.fisco.bcos.channel.test.contract.PressureTest
      基本测试  gradle test --tests org.fisco.bcos.channel.test.BasicTest

      ## 通过SDK部署合约和发交易
  1 把自己编写的sol文件文件放到tools/contracts下。。
  2 确保本地安装了fisco-solc；
  3 切换到tools/bin 执行 ./compile fisco-solc out;
  4 生成的bin文件即在tools/contracts/out目录下。
  5 abi转成java类，可以参考项目中SolidityFunctionWrapperGeneratorTest 测试类可以帮你生成把abi文件转换成相应Java类。
