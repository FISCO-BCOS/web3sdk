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
      目前你也可以gradle build 之后到dist目录去执行java -cp命令:
      # 部署合约
      java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PerfOkTransaction 1 deploy
      # 发送交易
      java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PerfOkTransaction 1 transaction address
      # 增加节点（指定节点入网）
      java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager "pbft" "add" "e2970447090490552c32b9cb020e16d277ef21e285bb3f3b7b919057285e3ef0cee156975f9d1d105d9240e91e4e324e46bb3c897045b9278b69597714ad6b22"
      # 删除节点（指定节点出网）
      java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager "pbft" "remove" "e2970447090490552c32b9cb020e16d277ef21e285bb3f3b7b919057285e3ef0cee156975f9d1d105d9240e91e4e324e46bb3c897045b9278b69597714ad6b22"

      #===通过SDK部署合约和发交易
     1 把自己编写的sol文件文件放到src/test/resources/contracts下,确保合约名和文件名保持一致。
     2 在项目目录下执行
     gradle test --tests org.fisco.bcos.channel.test.solidity.SolidityFunctionWrapperGeneratorTest.compileSolFilesToJavaTest ;测试类sol文件转换成相应Java类,
     生成的类在src/test/java/org/fisco/bcos/temp文件夹下,并且生成的abi和bin在目录src/test/resources/solidity。
