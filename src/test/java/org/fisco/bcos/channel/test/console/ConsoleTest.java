package org.fisco.bcos.channel.test.console;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.fisco.bcos.web3j.console.ConsoleFace;
import org.fisco.bcos.web3j.console.ConsoleImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class ConsoleTest{
	
    private ConsoleFace console = new ConsoleImpl();
    {
    	console.init(new String[0]);
    }
    
	@Rule
	public final SystemOutRule log = new SystemOutRule().enableLog();
	
    @Test
    public void helpTest() {
    	String[] params1 = {};
        console.help(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.help(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.help(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.help(params4);
        
        assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getBlockNumberTest() throws IOException {
    	String[] params1 = {};
        console.getBlockNumber(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getBlockNumber(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getBlockNumber(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getBlockNumber(params4);
        
    	assertTrue(!"".equals(log.getLog()));
    }

    @Test
    public void getSealerListTest() throws IOException {
    	String[] params1 = {};
        console.getSealerList(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getSealerList(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getSealerList(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getSealerList(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getObserverListTest() throws IOException {
    	String[] params1 = {};
        console.getObserverList(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getObserverList(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getObserverList(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getObserverList(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getConsensusStatusTest() throws IOException {
    	String[] params1 = {};
        console.getConsensusStatus(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getConsensusStatus(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getConsensusStatus(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getConsensusStatus(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getSyncStatusTest() throws IOException {
    	String[] params1 = {};
        console.getSyncStatus(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getSyncStatus(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getSyncStatus(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getSyncStatus(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getClientVersionTest() throws IOException {
    	String[] params1 = {};
        console.getClientVersion(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getClientVersion(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getClientVersion(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getClientVersion(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getPeersTest() throws IOException {
    	String[] params1 = {};
        console.getPeers(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getPeers(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getPeers(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getPeers(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getGroupPeersTest() throws IOException {
    	String[] params1 = {};
        console.getGroupPeers(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getGroupPeers(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getGroupPeers(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getGroupPeers(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getGroupListTest() throws IOException {
    	String[] params1 = {};
        console.getGroupList(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getGroupList(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getGroupList(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getGroupList(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getBlockByHash() throws IOException {
    	String[] params1 = {};
    	console.getBlockByHash(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gbbh", "-h"};
    	console.getBlockByHash(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gbbh", "0x2"};
    	console.getBlockByHash(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    }
    
    @Test
    public void getBlockByNumberTest() throws IOException {
    	String[] params1 = {};
    	console.getBlockByNumber(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gbbn", "-h"};
    	console.getBlockByNumber(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gbbn", "ae"};
    	console.getBlockByNumber(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params4 = {"gbbn", "0"};
    	console.getBlockByNumber(params4);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params5 = {"gbbn", "0", "true"};
    	console.getBlockByNumber(params5);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getBlockHashByNumberTest() throws IOException {
    	String[] params1 = {};
    	console.getBlockHashByNumber(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"ghbn", "-h"};
    	console.getBlockHashByNumber(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"ghbn", "ae"};
    	console.getBlockHashByNumber(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params4 = {"ghbn", "0"};
    	console.getBlockHashByNumber(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getTransactionByHashTest() throws IOException {
    	String[] params1 = {};
    	console.getTransactionByHash(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gtbh", "-h"};
    	console.getTransactionByHash(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gtbh", "ae"};
    	console.getTransactionByHash(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params4 = {"gtbh", "0xa1bab4089cccf4a30437babc6a43b9ab4ec30225f60630ac7d766f16037d7042"};
    	console.getTransactionByHash(params4);
    	assertTrue(!"".equals(log.getLog()));
    }

    @Test
    public void getTransactionByBlockHashAndIndexTest() throws IOException {
    	String[] params1 = {};
    	console.getTransactionByBlockHashAndIndex(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gthi", "-h"};
    	console.getTransactionByBlockHashAndIndex(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gthi", "ae"};
    	console.getTransactionByBlockHashAndIndex(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params4 = {"gthi", "0xca0577fa37367e89628e2db0a7adfccdd4b53e5fe781d7e34a3d5edbe29d1961", "a"};
    	console.getTransactionByBlockHashAndIndex(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getTransactionByBlockNumberAndIndexTest() throws IOException {
    	String[] params1 = {};
    	console.getTransactionByBlockNumberAndIndex(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gtni", "-h"};
    	console.getTransactionByBlockNumberAndIndex(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gtni", "ae"};
    	console.getTransactionByBlockNumberAndIndex(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params4 = {"gtni", "1", "a"};
    	console.getTransactionByBlockNumberAndIndex(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getTransactionReceiptTest() throws IOException {
    	String[] params1 = {};
    	console.getTransactionReceipt(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gtr", "-h"};
    	console.getTransactionReceipt(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gtr", "ae"};
    	console.getTransactionReceipt(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params4 = {"gtr", "0xa1bab4089cccf4a30437babc6a43b9ab4ec30225f60630ac7d766f16037d7042"};
    	console.getTransactionReceipt(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getPendingTransactionsTest() throws IOException {
    	String[] params1 = {};
        console.getPendingTransactions(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getPendingTransactions(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getPendingTransactions(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getPendingTransactions(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getCodeTest() throws IOException {
    	String[] params1 = {};
    	console.getCode(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gc", "-h"};
    	console.getCode(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gc", "ae"};
    	console.getCode(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params4 = {"gc", "0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b"};
    	console.getCode(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void getTotalTransactionCountTest() throws IOException {
    	String[] params1 = {};
        console.getTotalTransactionCount(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getTotalTransactionCount(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getTotalTransactionCount(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getTotalTransactionCount(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test(expected= ClassNotFoundException.class) 
    public void deployTestException() throws Exception {
    	String[] params = {"d", "ae"};
    	console.deploy(params);
    }
    @Test
    public void deployTest() throws Exception {
    	String[] params1 = {};
    	console.deploy(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"d", "-h"};
    	console.deploy(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"d", "Ok"};
    	console.deploy(params3);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void callTest() throws Exception {
    	String[] params1 = {};
    	console.call(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"c", "-h"};
    	console.call(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"c", "Ok"};
    	console.call(params3);
    	assertTrue(!"".equals(log.getLog()));
    	
    	String[] params4 = {"c", "Ok", "0x1"};
    	console.call(params4);
    	assertTrue(!"".equals(log.getLog()));
    	
    }

    @Test
    public void deployByCNSTest() throws Exception {
    	String[] params1 = {};
    	console.deployByCNS(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"dbc", "-h"};
    	console.deployByCNS(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        	
    	String[] params3 = {"dbc", "Ok"};
    	console.deployByCNS(params3);
    	assertTrue(!"".equals(log.getLog()));
    }
    @Test
    public void callByCNSTest() throws Exception {
    	String[] params1 = {};
    	console.callByCNS(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"cbc", "-h"};
    	console.callByCNS(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"cbc", "Ok"};
    	console.callByCNS(params3);
    	assertTrue(!"".equals(log.getLog()));
    	
    	String[] params4 = {"cbc", "Ok", "1.0"};
    	console.callByCNS(params4);
    	assertTrue(!"".equals(log.getLog()));
    	
    }
    @Test
    public void addSealerTest() throws Exception {
    	String[] params1 = {};
    	console.addSealer(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"as", "-h"};
    	console.addSealer(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"as", "ae"};
    	console.addSealer(params3);
    	assertTrue(!"".equals(log.getLog()));
    	
    }
    @Test
    public void addObserverTest() throws Exception {
    	String[] params1 = {};
    	console.addObserver(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"ao", "-h"};
    	console.addObserver(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"ao", "ae"};
    	console.addObserver(params3);
    	assertTrue(!"".equals(log.getLog()));
    	
    }
    @Test
    public void removeNodeTest() throws Exception {
    	String[] params1 = {};
    	console.removeNode(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"rn", "-h"};
    	console.removeNode(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"rn", "ae"};
    	console.removeNode(params3);
    	assertTrue(!"".equals(log.getLog()));
    	
    }
    @Test
    public void addUserTableManagerTest() throws Exception {
    	String[] params1 = {};
    	console.addUserTableManager(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"aa", "-h"};
    	console.addUserTableManager(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    }
    @Test
    public void removeUserTableManagerTest() throws Exception {
    	String[] params1 = {};
    	console.removeUserTableManager(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"ra", "-h"};
    	console.removeUserTableManager(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    }
    @Test
    public void queryAuthorityTest() throws Exception {
    	String[] params1 = {};
    	console.queryUserTableManager(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"qa", "-h"};
    	console.queryUserTableManager(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"qa", "t_test"};
    	console.queryUserTableManager(params3);
    	assertTrue(!"".equals(log.getLog()));
    }
    
    @Test
    public void setSystemConfigByKeyTest() throws Exception {
    	String[] params1 = {};
    	console.setSystemConfigByKey(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"ssc", "-h"};
    	console.setSystemConfigByKey(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"ssc", "tx_count_limit", "1000"};
    	console.setSystemConfigByKey(params3);
    	assertTrue(!"".equals(log.getLog()));
    }
    
    @Test
    public void getSystemConfigByKeyTest() throws Exception {
    	String[] params1 = {};
    	console.getSystemConfigByKey(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gsc", "-h"};
    	console.getSystemConfigByKey(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gsc", "tx_gas_limit"};
    	console.getSystemConfigByKey(params3);
    	assertTrue(!"".equals(log.getLog()));
    }
    
    @Test
    public void getPendingTxSizeTest() throws Exception {
    	String[] params1 = {};
        console.getPendingTxSize(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"-h"};
        console.getPendingTxSize(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
        
        String[] params3 = {"--help"};
        console.getPendingTxSize(params3);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
        String[] params4 = {"k"};
        console.getPendingTxSize(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    
    @Test
    public void queryCNSTest() throws Exception {
    	String[] params1 = {};
    	console.queryCNS(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"qcs", "-h"};
    	console.queryCNS(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"qcs", "Ok"};
    	console.queryCNS(params3);
    	assertTrue(!"".equals(log.getLog()));
    	
    	String[] params4 = {"qcs", "Ok", "1.0"};
    	console.queryCNS(params4);
    	assertTrue(!"".equals(log.getLog()));
    }
    
    @Test
    public void getNodeIDListTest() throws Exception {
    	String[] params1 = {};
    	console.getNodeIDList(params1);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params2 = {"gnl", "-h"};
    	console.getNodeIDList(params2);
    	assertTrue(!"".equals(log.getLog()));
    	log.clearLog();
    	
    	String[] params3 = {"gnl"};
    	console.getNodeIDList(params3);
    	assertTrue(!"".equals(log.getLog()));
    	
    }
    
}
