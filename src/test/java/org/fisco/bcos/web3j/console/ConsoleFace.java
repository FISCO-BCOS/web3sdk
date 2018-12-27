package org.fisco.bcos.web3j.console;

import java.io.IOException;

public interface ConsoleFace {
	
	void welcome();

	void help();

	void getBlockNumber() throws IOException;

	void getPbftView() throws IOException;

	void getObserverList() throws IOException;

	void getMinerList() throws IOException;

	void getConsensusStatus() throws IOException;

	void getSyncStatus() throws IOException;

	void getClientVersion() throws IOException;

	void getPeers() throws IOException;

	void getGroupPeers() throws IOException;

	void getGroupList() throws IOException;

	void getBlockByHash(String[] params) throws IOException;

	void getBlockByNumber(String[] params) throws IOException;

	void getBlockHashByNumber(String[] params) throws IOException;

	void getTransactionByHash(String[] params) throws IOException;

	void getTransactionByBlockHashAndIndex(String[] params) throws IOException;

	void getTransactionByBlockNumberAndIndex(String[] params) throws IOException;

	void getTransactionReceipt(String[] params) throws IOException;

	void getPendingTransactions() throws IOException;

	void getCode(String[] params) throws IOException;

	void getTotalTransactionCount() throws IOException;

	void deploy(String[] params) throws Exception;
	
	void call(String[] params) throws Exception;
	
	void deployByCNS(String[] params) throws Exception;

	void callByCNS(String[] params) throws Exception;

	void addMiner(String[] params) throws Exception;

	void addObserver(String[] params) throws Exception;
	
	void removeNode(String[] params) throws Exception;
	
	void addAuthority(String[] params) throws Exception;
	
	void removeAuthority(String[] params) throws Exception;
	
	void queryAuthority(String[] params) throws Exception;

	void setSystemConfigByKey(String[] params) throws Exception;

	void getSystemConfigByKey(String[] params) throws Exception;

	void init(String[] args);
}
