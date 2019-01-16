package org.fisco.bcos.web3j.console;

import java.io.IOException;

public interface ConsoleFace {
	
	void welcome();

	void help(String[] params);

	void getBlockNumber(String[] params) throws IOException;

	void getPbftView(String[] params) throws IOException;

	void getObserverList(String[] params) throws IOException;

	void getMinerList(String[] params) throws IOException;

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

	void addMiner(String[] params) throws Exception;

	void addObserver(String[] params) throws Exception;
	
	void removeNode(String[] params) throws Exception;
	
	void addAuthority(String[] params) throws Exception;
	
	void removeAuthority(String[] params) throws Exception;
	
	void queryAuthority(String[] params) throws Exception;

	void setSystemConfigByKey(String[] params) throws Exception;

	void getSystemConfigByKey(String[] params) throws Exception;
	
	void quit(String[] params) throws Exception;

	void init(String[] args);
}
