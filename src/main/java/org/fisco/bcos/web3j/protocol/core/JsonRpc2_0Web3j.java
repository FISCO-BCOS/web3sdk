package org.fisco.bcos.web3j.protocol.core;

import io.reactivex.Flowable;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.Web3jService;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.*;
import org.fisco.bcos.web3j.protocol.rx.JsonRpc2_0Rx;
import org.fisco.bcos.web3j.protocol.websocket.events.LogNotification;
import org.fisco.bcos.web3j.protocol.websocket.events.NewHeadsNotification;
import org.fisco.bcos.web3j.utils.Async;
import org.fisco.bcos.web3j.utils.BlockLimit;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0Web3j implements Web3j {
    static Logger logger = LoggerFactory.getLogger(JsonRpc2_0Web3j.class);
    protected static final long ID = 1;
    public static final int BLOCK_TIME = 15 * 100;
    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;
    protected final Web3jService web3jService;
    private final JsonRpc2_0Rx web3jRx;
    private final long blockTime;
    private final ScheduledExecutorService scheduledExecutorService;
    private  int groupId = 1;
    
    public Web3jService web3jService() {
    	return web3jService;
    }

     public BigInteger getLocalBlockNumber() {
        return ((ChannelEthereumService)web3jService).getChannelService().getNumber();
    }

    synchronized public void setBlockNumber(BigInteger blockNumber) {
        if(blockNumber.compareTo(((ChannelEthereumService)web3jService).getChannelService().getNumber()) > 0) {
        	((ChannelEthereumService)web3jService).getChannelService().setNumber(blockNumber);
        }
    }

    public JsonRpc2_0Web3j(Web3jService web3jService) {
        this(web3jService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService(),1);
    }

    public JsonRpc2_0Web3j(Web3jService web3jService, int groupId) {
        this(web3jService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService(), groupId);
        this.groupId = groupId;
    }

    public JsonRpc2_0Web3j(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService, int groupId) {
        this.web3jService = web3jService;
        this.web3jRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
        this.scheduledExecutorService = scheduledExecutorService;
        this.groupId= groupId;

    }
    
    @Override
    public Request<?, ClientVersion> getClientVersion() {
        return new Request<>(
                "getClientVersion",
                Arrays.asList(),
                web3jService,
                ClientVersion.class);
    }
    
    @Override
    public Request<?, BlockNumber> getBlockNumber() {
        return new Request<>(
                "getBlockNumber",
               Arrays.asList(groupId),
                web3jService,
                BlockNumber.class);
    }
    
    @Override
    public BigInteger getBlockNumberCache() {
        if (getLocalBlockNumber().intValue() == 1)
        {
            try {
                BlockNumber ethBlockNumber = getBlockNumber().sendAsync().get();
                setBlockNumber(ethBlockNumber.getBlockNumber());
            } catch (Exception e) {
                logger.error("Exception: " + e);
            }
        }
        return getLocalBlockNumber().add(new BigInteger(BlockLimit.blockLimit.toString()));
    }

    @Override
    public Request<?, GroupList> getGroupList() {
    	return new Request<>(
    			"getGroupList",
    			Arrays.asList(),
    			web3jService,
    			GroupList.class);
    }
    
    @Override
    public Request<?, SealerList> getSealerList() {
    	return new Request<>(
    			"getSealerList",
    			Arrays.asList(groupId),
    			web3jService,
    			SealerList.class);
    }
    
    @Override
    public Request<?, ObserverList> getObserverList() {
        return new Request<>(
                "getObserverList",
               Arrays.asList(groupId),
                web3jService,
                ObserverList.class);
    }

    @Override
    public Request<?, Peers> getPeers() {
        return new Request<>(
                "getPeers",
               Arrays.asList(groupId),
                web3jService,
                Peers.class);
    }
    
    @Override
    public Request<?, NodeIDList> getNodeIDList() {
        return new Request<>(
                "getNodeIDList",
               Arrays.asList(groupId),
                web3jService,
                NodeIDList.class);
    }
    
    @Override
    public Request<?, SystemConfig> getSystemConfigByKey(String key) {
    	return new Request<>(
    			"getSystemConfigByKey",
    			Arrays.asList(groupId, key),
    			web3jService,
    			SystemConfig.class);
    }

    @Override
    public Request<?, SyncStatus> getSyncStatus() {
        return new Request<>(
                "getSyncStatus",
               Arrays.asList(groupId),
                web3jService,
                SyncStatus.class);
    }

    @Override
    public Request<?, PbftView> getPbftView() {
        return new Request<>(
                "getPbftView",
               Arrays.asList(groupId),
                web3jService,
                PbftView.class);
    }

    @Override
    public Request<?, ConsensusStatus> getConsensusStatus() {
        return new Request<>(
                "getConsensusStatus",
               Arrays.asList(groupId),
                (ChannelEthereumService)web3jService,
                ConsensusStatus.class);
    }

    @Override
    public Request<?, Code> getCode(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "getCode",
                Arrays.asList(groupId, address),
                web3jService,
                Code.class);
    }
    
    @Override
    public Request<?, TotalTransactionCount> getTotalTransactionCount() {
    	return new Request<>(
    			"getTotalTransactionCount",
    			Arrays.asList(groupId),
    			web3jService,
    			TotalTransactionCount.class);
    }

    @Override
    public Request<?, Call> call(
            org.fisco.bcos.web3j.protocol.core.methods.request.Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "call",
                Arrays.asList(groupId,transaction),
                web3jService,
                Call.class);
    }

    @Override
    public Request<?, BcosBlock> getBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "getBlockByHash",
                Arrays.asList(groupId,
                        blockHash,
                        returnFullTransactionObjects),
                web3jService,
                BcosBlock.class);
    }

    @Override
    public Request<?, BcosBlock> getBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) {
        return new Request<>(
                "getBlockByNumber",
                Arrays.asList(groupId,
                        defaultBlockParameter.getValue(),
                        returnFullTransactionObjects),
                web3jService,
                BcosBlock.class);
    }
    
    @Override
    public Request<?, BlockHash> getBlockHashByNumber(
    		DefaultBlockParameter defaultBlockParameter) {
    	return new Request<>(
    			"getBlockHashByNumber",
    			Arrays.asList(groupId,
    					defaultBlockParameter.getValue()),
    			web3jService,
    			BlockHash.class);
    }

    @Override
    public Request<?, BcosTransaction> getTransactionByHash(String transactionHash) {
        return new Request<>(
                "getTransactionByHash",
                Arrays.asList(groupId,transactionHash),
                web3jService,
                BcosTransaction.class);
    }

    @Override
    public Request<?, BcosTransaction> getTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "getTransactionByBlockHashAndIndex",
                Arrays.asList(groupId,
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                BcosTransaction.class);
    }

    @Override
    public Request<?, BcosTransaction> getTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<>(
                "getTransactionByBlockNumberAndIndex",
                Arrays.asList(groupId,
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                BcosTransaction.class);
    }

    @Override
    public Request<?, BcosTransactionReceipt> getTransactionReceipt(String transactionHash) {
        return new Request<>(
                "getTransactionReceipt",
                Arrays.asList(groupId,transactionHash),
                web3jService,
                BcosTransactionReceipt.class);
    }

    @Override
    public Request<?, PendingTransactions> getPendingTransaction() {
        return new Request<>(
                "getPendingTransactions",
               Arrays.asList(groupId),
                web3jService,
                PendingTransactions.class);
    }
    
    @Override
    public Request<?, PendingTxSize> getPendingTxSize() {
    	return new Request<>(
    			"getPendingTxSize",
    			Arrays.asList(groupId),
    			web3jService,
    			PendingTxSize.class);
    }
    
    @Override
    public Request<?, SendTransaction>
    sendRawTransaction(
            String signedTransactionData) {
        return new Request<>(
                "sendRawTransaction",
                Arrays.asList(groupId,signedTransactionData),
                web3jService,
                SendTransaction.class);
    }

    @Override
    public Request<?, GroupPeers> getGroupPeers() {
        return new Request<>(
                "getGroupPeers",
               Arrays.asList(groupId),
                web3jService,
                GroupPeers.class);
    }
    
    @Override
    public Request<?, BcosFilter> newPendingTransactionFilter() {
        return new Request<>(
                "newPendingTransactionFilter",
               Arrays.asList(groupId),
                web3jService,
                BcosFilter.class);
    }
    
    @Override
    public Request<?, BcosFilter> newBlockFilter() {
        return new Request<>(
                "newBlockFilter",
               Arrays.asList(groupId),
                web3jService,
                BcosFilter.class);
    }
    
    @Override
    public Request<?, BcosLog> getFilterChanges(BigInteger filterId) {
        return new Request<>(
                "getFilterChanges",
                Arrays.asList(groupId,Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                BcosLog.class);
    }
    
    @Override
    public Request<?, UninstallFilter> getUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "getUninstallFilter",
                Arrays.asList(groupId,Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                UninstallFilter.class);
    }
    
    @Override
    public Request<?, BcosFilter> newFilter(
            org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter filter) {
        return new Request<>(
                "newFilter",
                Arrays.asList(groupId,filter),
                web3jService,
                BcosFilter.class);
    }
    
    @Override
    public Flowable<NewHeadsNotification> newHeadsNotifications() {
        return web3jService.subscribe(
                new Request<>(
                        "subscribe",
                        Collections.singletonList("newHeads"),
                        web3jService,
                        BcosSubscribe.class),
                "unsubscribe",
                NewHeadsNotification.class
        );
    }

    @Override
    public Flowable<LogNotification> logsNotifications(
            List<String> addresses, List<String> topics) {

        Map<String, Object> params = createLogsParams(addresses, topics);

        return web3jService.subscribe(
                new Request<>(
                        "subscribe",
                        Arrays.asList(groupId,"logs", params),
                        web3jService,
                        BcosSubscribe.class),
                "unsubscribe",
                LogNotification.class
        );
    }

    private Map<String, Object> createLogsParams(List<String> addresses, List<String> topics) {
        Map<String, Object> params = new HashMap<>();
        if (!addresses.isEmpty()) {
            params.put("address", addresses);
        }
        if (!topics.isEmpty()) {
            params.put("topics", topics);
        }
        return params;
    }

    @Override
    public Flowable<String> blockHashFlowable() {
        return web3jRx.blockHashFlowable(blockTime);
    }

    @Override
    public Flowable<String> pendingTransactionHashFlowable() {
        return web3jRx.pendingTransactionHashFlowable(blockTime);
    }

    @Override
    public Flowable<Log> logFlowable(
            org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter filter) {
        return web3jRx.logFlowable(filter, blockTime);
    }

    @Override
    public Flowable<org.fisco.bcos.web3j.protocol.core.methods.response.Transaction>
    transactionFlowable() {
        return web3jRx.transactionFlowable(blockTime);
    }

    @Override
    public Flowable<org.fisco.bcos.web3j.protocol.core.methods.response.Transaction>
    pendingTransactionFlowable() {
        return web3jRx.pendingTransactionFlowable(blockTime);
    }

    @Override
    public Flowable<BcosBlock> blockFlowable(boolean fullTransactionObjects) {
        return web3jRx.blockFlowable(fullTransactionObjects, blockTime);
    }

    @Override
    public Flowable<BcosBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return web3jRx.replayBlocksFlowable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Flowable<BcosBlock> replayPastBlocksFlowable(DefaultBlockParameter startBlock,
                                                       DefaultBlockParameter endBlock,
                                                       boolean fullTransactionObjects,
                                                       boolean ascending) {
        return web3jRx.replayBlocksFlowable(startBlock, endBlock,
                fullTransactionObjects, ascending);
    }

    @Override
    public Flowable<BcosBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Flowable<BcosBlock> onCompleteFlowable) {
        return web3jRx.replayPastBlocksFlowable(
                startBlock, fullTransactionObjects, onCompleteFlowable);
    }

    @Override
    public Flowable<BcosBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.replayPastBlocksFlowable(startBlock, fullTransactionObjects);
    }

    @Override
    public Flowable<org.fisco.bcos.web3j.protocol.core.methods.response.Transaction>
    replayPastTransactionsFlowable(DefaultBlockParameter startBlock,
                                   DefaultBlockParameter endBlock) {
        return web3jRx.replayTransactionsFlowable(startBlock, endBlock);
    }

    @Override
    public Flowable<org.fisco.bcos.web3j.protocol.core.methods.response.Transaction>
    replayPastTransactionsFlowable(DefaultBlockParameter startBlock) {
        return web3jRx.replayPastTransactionsFlowable(startBlock);
    }

    @Override
    public Flowable<BcosBlock> replayPastAndFutureBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.replayPastAndFutureBlocksFlowable(
                startBlock, fullTransactionObjects, blockTime);
    }

    @Override
    public Flowable<org.fisco.bcos.web3j.protocol.core.methods.response.Transaction>
    replayPastAndFutureTransactionsFlowable(DefaultBlockParameter startBlock) {
        return web3jRx.replayPastAndFutureTransactionsFlowable(
                startBlock, blockTime);
    }


    public void shutdown() {
        scheduledExecutorService.shutdown();
        try {
            web3jService.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close web3j service", e);
        }
    }
}
