package org.fisco.bcos.web3j.console;

import java.io.IOException;

public interface ConsoleFace {

  void welcome();

  void close();

  void help(String[] params);

  void switchGroupID(String[] params) throws IOException;
  
  void getBlockNumber(String[] params) throws IOException;

  void getPbftView(String[] params) throws IOException;

  void getObserverList(String[] params) throws IOException;

  void getSealerList(String[] params) throws IOException;

  void getConsensusStatus(String[] params) throws IOException;

  void getSyncStatus(String[] params) throws IOException;

  void getNodeVersion(String[] params) throws IOException;

  void getPeers(String[] params) throws IOException;

  void getNodeIDList(String[] params) throws IOException;

  void getGroupPeers(String[] params) throws IOException;

  void getGroupList(String[] params) throws IOException;

  void getBlockByHash(String[] params) throws IOException;

  void getBlockByNumber(String[] params) throws IOException;

  void getBlockHashByNumber(String[] params) throws IOException;

  void getTransactionByHash(String[] params) throws IOException;

  void getTransactionByBlockHashAndIndex(String[] params) throws IOException;

  void getTransactionByBlockNumberAndIndex(String[] params) throws IOException;

  void getTransactionReceipt(String[] params) throws IOException;

  void getPendingTxSize(String[] params) throws IOException;

  void getPendingTransactions(String[] params) throws IOException;

  void getCode(String[] params) throws IOException;

  void getTotalTransactionCount(String[] params) throws IOException;

  void deploy(String[] params) throws Exception;

  void call(String[] params) throws Exception;

  void deployByCNS(String[] params) throws Exception;

  void callByCNS(String[] params) throws Exception;

  void queryCNS(String[] params) throws Exception;

  void addSealer(String[] params) throws Exception;

  void addObserver(String[] params) throws Exception;

  void removeNode(String[] params) throws Exception;

  void grantUserTableManager(String[] params) throws Exception;

  void revokeUserTableManager(String[] params) throws Exception;

  void listUserTableManager(String[] params) throws Exception;

  void grantDeployAndCreateManager(String[] params) throws Exception;

  void revokeDeployAndCreateManager(String[] params) throws Exception;

  void listDeployAndCreateManager(String[] params) throws Exception;

  void grantPermissionManager(String[] params) throws Exception;

  void revokePermissionManager(String[] params) throws Exception;

  void listPermissionManager(String[] params) throws Exception;

  void grantNodeManager(String[] params) throws Exception;

  void revokeNodeManager(String[] params) throws Exception;

  void listNodeManager(String[] params) throws Exception;

  void grantCNSManager(String[] params) throws Exception;

  void revokeCNSManager(String[] params) throws Exception;

  void listCNSManager(String[] params) throws Exception;

  void grantSysConfigManager(String[] params) throws Exception;

  void revokeSysConfigManager(String[] params) throws Exception;

  void listSysConfigManager(String[] params) throws Exception;

  void setSystemConfigByKey(String[] params) throws Exception;

  void getSystemConfigByKey(String[] params) throws Exception;

  void init(String[] args);
}
