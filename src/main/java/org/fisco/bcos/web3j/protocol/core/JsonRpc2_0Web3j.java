package org.fisco.bcos.web3j.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.Web3jService;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockHash;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockNumber;
import org.fisco.bcos.web3j.protocol.core.methods.response.Call;
import org.fisco.bcos.web3j.protocol.core.methods.response.Code;
import org.fisco.bcos.web3j.protocol.core.methods.response.ConsensusStatus;
import org.fisco.bcos.web3j.protocol.core.methods.response.GroupList;
import org.fisco.bcos.web3j.protocol.core.methods.response.GroupPeers;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeIDList;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeVersion;
import org.fisco.bcos.web3j.protocol.core.methods.response.ObserverList;
import org.fisco.bcos.web3j.protocol.core.methods.response.PbftView;
import org.fisco.bcos.web3j.protocol.core.methods.response.Peers;
import org.fisco.bcos.web3j.protocol.core.methods.response.PendingTransactions;
import org.fisco.bcos.web3j.protocol.core.methods.response.PendingTxSize;
import org.fisco.bcos.web3j.protocol.core.methods.response.SealerList;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.SyncStatus;
import org.fisco.bcos.web3j.protocol.core.methods.response.SystemConfig;
import org.fisco.bcos.web3j.protocol.core.methods.response.TotalTransactionCount;
import org.fisco.bcos.web3j.utils.BlockLimit;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** JSON-RPC 2.0 factory implementation. */
public class JsonRpc2_0Web3j implements Web3j {
    static Logger logger = LoggerFactory.getLogger(JsonRpc2_0Web3j.class);
    protected static final long ID = 1;
    public static final int BLOCK_TIME = 15 * 100;
    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;
    protected final Web3jService web3jService;
    private final long blockTime;

    private int groupId = 1;

    public Web3jService web3jService() {
        return web3jService;
    }

    public BigInteger getLocalBlockNumber() {
        return ((ChannelEthereumService) web3jService).getChannelService().getNumber();
    }

    public synchronized void setBlockNumber(BigInteger blockNumber) {
        if (blockNumber.compareTo(
                        ((ChannelEthereumService) web3jService).getChannelService().getNumber())
                > 0) {
            ((ChannelEthereumService) web3jService).getChannelService().setNumber(blockNumber);
        }
    }

    public JsonRpc2_0Web3j(Web3jService web3jService) {
        this(web3jService, DEFAULT_BLOCK_TIME, 1);
    }

    public JsonRpc2_0Web3j(Web3jService web3jService, int groupId) {
        this(web3jService, DEFAULT_BLOCK_TIME, groupId);
        this.groupId = groupId;
    }

    public JsonRpc2_0Web3j(Web3jService web3jService, long pollingInterval, int groupId) {
        this.web3jService = web3jService;
        this.blockTime = pollingInterval;
        this.groupId = groupId;
    }

    @Override
    public Request<?, NodeVersion> getNodeVersion() {
        return new Request<>("getClientVersion", Arrays.asList(), web3jService, NodeVersion.class);
    }

    @Override
    public synchronized Request<?, BlockNumber> getBlockNumber() {
        return new Request<>(
                "getBlockNumber", Arrays.asList(groupId), web3jService, BlockNumber.class);
    }

    @Override
    public BigInteger getBlockNumberCache() {
        if (getLocalBlockNumber().intValue() == 1) {
            try {
                BlockNumber blockNumber = getBlockNumber().sendAsync().get();
                setBlockNumber(blockNumber.getBlockNumber());
            } catch (Exception e) {
                logger.error("Exception: " + e);
            }
        }
        return getLocalBlockNumber().add(new BigInteger(BlockLimit.blockLimit.toString()));
    }

    @Override
    public Request<?, GroupList> getGroupList() {
        return new Request<>("getGroupList", Arrays.asList(), web3jService, GroupList.class);
    }

    @Override
    public Request<?, SealerList> getSealerList() {
        return new Request<>(
                "getSealerList", Arrays.asList(groupId), web3jService, SealerList.class);
    }

    @Override
    public Request<?, ObserverList> getObserverList() {
        return new Request<>(
                "getObserverList", Arrays.asList(groupId), web3jService, ObserverList.class);
    }

    @Override
    public Request<?, Peers> getPeers() {
        return new Request<>("getPeers", Arrays.asList(groupId), web3jService, Peers.class);
    }

    @Override
    public Request<?, NodeIDList> getNodeIDList() {
        return new Request<>(
                "getNodeIDList", Arrays.asList(groupId), web3jService, NodeIDList.class);
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
                "getSyncStatus", Arrays.asList(groupId), web3jService, SyncStatus.class);
    }

    @Override
    public Request<?, PbftView> getPbftView() {
        return new Request<>("getPbftView", Arrays.asList(groupId), web3jService, PbftView.class);
    }

    @Override
    public Request<?, ConsensusStatus> getConsensusStatus() {
        return new Request<>(
                "getConsensusStatus",
                Arrays.asList(groupId),
                (ChannelEthereumService) web3jService,
                ConsensusStatus.class);
    }

    @Override
    public Request<?, Code> getCode(String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>("getCode", Arrays.asList(groupId, address), web3jService, Code.class);
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
            org.fisco.bcos.web3j.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>("call", Arrays.asList(groupId, transaction), web3jService, Call.class);
    }

    @Override
    public Request<?, BcosBlock> getBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "getBlockByHash",
                Arrays.asList(groupId, blockHash, returnFullTransactionObjects),
                web3jService,
                BcosBlock.class);
    }

    @Override
    public Request<?, BcosBlock> getBlockByNumber(
            DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects) {
        return new Request<>(
                "getBlockByNumber",
                Arrays.asList(
                        groupId, defaultBlockParameter.getValue(), returnFullTransactionObjects),
                web3jService,
                BcosBlock.class);
    }

    @Override
    public Request<?, BlockHash> getBlockHashByNumber(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "getBlockHashByNumber",
                Arrays.asList(groupId, defaultBlockParameter.getValue()),
                web3jService,
                BlockHash.class);
    }

    @Override
    public Request<?, BcosTransaction> getTransactionByHash(String transactionHash) {
        return new Request<>(
                "getTransactionByHash",
                Arrays.asList(groupId, transactionHash),
                web3jService,
                BcosTransaction.class);
    }

    @Override
    public Request<?, BcosTransaction> getTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "getTransactionByBlockHashAndIndex",
                Arrays.asList(groupId, blockHash, Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                BcosTransaction.class);
    }

    @Override
    public Request<?, BcosTransaction> getTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<>(
                "getTransactionByBlockNumberAndIndex",
                Arrays.asList(
                        groupId,
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                BcosTransaction.class);
    }

    @Override
    public Request<?, BcosTransactionReceipt> getTransactionReceipt(String transactionHash) {
        return new Request<>(
                "getTransactionReceipt",
                Arrays.asList(groupId, transactionHash),
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
                "getPendingTxSize", Arrays.asList(groupId), web3jService, PendingTxSize.class);
    }

    @Override
    public Request<?, SendTransaction> sendRawTransaction(String signedTransactionData) {
        return new Request<>(
                "sendRawTransaction",
                Arrays.asList(groupId, signedTransactionData),
                web3jService,
                SendTransaction.class);
    }

    @Override
    public Request<?, GroupPeers> getGroupPeers() {
        return new Request<>(
                "getGroupPeers", Arrays.asList(groupId), web3jService, GroupPeers.class);
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
