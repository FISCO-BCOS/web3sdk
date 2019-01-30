package org.fisco.bcos.web3j.protocol.core;

import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.core.methods.response.*;

/**
 * Core FISCO BCOS JSON-RPC API.
 */
public interface Ethereum {
    Request<?, Web3ClientVersion> web3ClientVersion();

    Request<?, EthSyncing> ethSyncing();

    Request<?, EthBlockNumber> ethBlockNumber();
    
    Request<?, EthPbftView> ethPbftView();
    
    Request<?, MinerList> getMinerList();
    
    Request<?, ObserverList> getObserverList();

    Request<?,EthConsensusStatus> consensusStatus();

    Request<?, GroupList> ethGroupList();

    Request<?, EthPeerList> ethGroupPeers();

    Request<?, EthPeers> ethPeersInfo();
    
    Request<?, NodeIDList> getNodeIDList();
    
    Request<?, SystemConfig> getSystemConfigByKey(String key);

    Request<?, EthGetCode> ethGetCode(String address, DefaultBlockParameter defaultBlockParameter);
    
    Request<?, TotalTransactionCount> getTotalTransactionCount();

    Request<?, EthBlock> ethGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, EthBlock> ethGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects);
    
    Request<?, BlockHash> getBlockHashByNumber(
    		DefaultBlockParameter defaultBlockParameter);

    Request<?, EthTransaction> ethGetTransactionByHash(String transactionHash);

    Request<?, EthTransaction> ethGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, EthTransaction> ethGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, EthGetTransactionReceipt> ethGetTransactionReceipt(String transactionHash);

    Request<?,EthPendingTransactions> ethPendingTransaction();

    BigInteger getBlockNumberCache();

	Request<?, PendingTxSize> getPendingTxSize();
	
    Request<?, EthCall> ethCall(
            org.fisco.bcos.web3j.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);
    
    Request<?, EthSendTransaction> ethSendRawTransaction(
            String signedTransactionData);
	
	Request<?, EthFilter> ethNewPendingTransactionFilter();
	
	Request<?, EthGasPrice> ethGasPrice();
	
	Request<?, EthFilter> ethNewBlockFilter();
	
	Request<?, EthLog> ethGetFilterChanges(BigInteger filterId);
	
	Request<?, EthUninstallFilter> ethUninstallFilter(BigInteger filterId);
	
	Request<?, EthFilter> ethNewFilter(org.fisco.bcos.web3j.protocol.core.methods.request.EthFilter ethFilter);
}
