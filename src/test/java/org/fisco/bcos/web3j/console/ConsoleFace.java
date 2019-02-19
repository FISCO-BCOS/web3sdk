package org.fisco.bcos.web3j.console;

import java.io.IOException;

public interface ConsoleFace {
	
	void welcome();

	void help(String[] params);

	void getBlockNumber(String[] params) throws IOException;

	void getPbftView(String[] params) throws IOException;

	void getObserverList(String[] params) throws IOException;

	void getSealerList(String[] params) throws IOException;

	void getConsensusStatus(String[] params) throws IOException;

	void getSyncStatus(String[] params) throws IOException;

	void getClientVersion(String[] params) throws IOException;

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
	
	void addUserTableManager(String[] params) throws Exception;
	
	void removeUserTableManager(String[] params) throws Exception;
	
	void queryUserTableManager(String[] params) throws Exception;
	
	void addDeployAndCreateManager(String[] params) throws Exception;
	
	void removeDeployAndCreateManager(String[] params) throws Exception;
	
	void queryDeployAndCreateManager(String[] params) throws Exception;
	
	void addAuthorityManager(String[] params) throws Exception;
	
	void removeAuthorityManager(String[] params) throws Exception;
	
	void queryAuthorityManager(String[] params) throws Exception;
	
	void addNodeManager(String[] params) throws Exception;
	
	void removeNodeManager(String[] params) throws Exception;
	
	void queryNodeManager(String[] params) throws Exception;
	
	void addCNSManager(String[] params) throws Exception;
	
	void removeCNSManager(String[] params) throws Exception;
	
	void queryCNSManager(String[] params) throws Exception;
	
	void addSysConfigManager(String[] params) throws Exception;
	
	void removeSysConfigManager(String[] params) throws Exception;
	
	void querySysConfigManager(String[] params) throws Exception;

	void setSystemConfigByKey(String[] params) throws Exception;

	void getSystemConfigByKey(String[] params) throws Exception;
	
	void quit(String[] params) throws Exception;

	void init(String[] args);
}
