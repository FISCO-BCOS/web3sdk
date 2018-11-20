[![image for the blockchain contest](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/doc/imgs/application_contest.png "点击图片报名")](https://con.geekbang.org/)

## 技术文档

1 #配置
# SDK编译

```eval_rst
.. admonition:: 安装依赖软件

   部署web3sdk之前需要安装git, dos2unix依赖软件:

   -  **git**：用于拉取最新代码
   -  **dos2unix**: 用于处理windows文件上传到linux服务器时，文件格式无法被linux正确解析的问题；

   **centos**:
    .. code-block:: bash

       $ sudo yum -y install git dos2unix

   **ubuntu**:
    .. code-block:: bash

       $ sudo apt install git tofrodos
       $ ln -s /usr/bin/todos /usr/bin/unxi2dos && ln -s /usr/bin/fromdos /usr/

.. admonition:: 编译源码

   执行如下命令拉取并编译源码：

    .. code-block:: bash

       #=== 创建并进入web3sdk源码放置目录（假设为~/mydata/）=====
       $ mkdir -p ~/mydata
       $ cd ~/mydata

       #==== 拉取git代码 ====
       $ git clone https://github.com/FISCO-BCOS/web3sdk

       #===编译we3bsdk源码，生成dist目录 ===
       $ cd web3sdk
       $ dos2unix *.sh

       拷贝证书文件ca.crt和client.keystore到web3sdk/src/test/resources目录下；
       找到 web3sdk/src/test/resources/applicationContext.xml文件的channelService, 增加<property name="groupId" value="1" /> groupId配置。

