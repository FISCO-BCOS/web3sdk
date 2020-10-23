package org.fisco.bcos.web3j.protocol.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlockHeader;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosLog;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockHash;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockNumber;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockTransactionReceipts;
import org.fisco.bcos.web3j.protocol.core.methods.response.Call;
import org.fisco.bcos.web3j.protocol.core.methods.response.Code;
import org.fisco.bcos.web3j.protocol.core.methods.response.ConsensusStatus;
import org.fisco.bcos.web3j.protocol.core.methods.response.GenerateGroup;
import org.fisco.bcos.web3j.protocol.core.methods.response.GroupList;
import org.fisco.bcos.web3j.protocol.core.methods.response.GroupPeers;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeIDList;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeVersion;
import org.fisco.bcos.web3j.protocol.core.methods.response.ObserverList;
import org.fisco.bcos.web3j.protocol.core.methods.response.PbftView;
import org.fisco.bcos.web3j.protocol.core.methods.response.Peers;
import org.fisco.bcos.web3j.protocol.core.methods.response.PendingTransactions;
import org.fisco.bcos.web3j.protocol.core.methods.response.PendingTxSize;
import org.fisco.bcos.web3j.protocol.core.methods.response.QueryGroupStatus;
import org.fisco.bcos.web3j.protocol.core.methods.response.RecoverGroup;
import org.fisco.bcos.web3j.protocol.core.methods.response.RemoveGroup;
import org.fisco.bcos.web3j.protocol.core.methods.response.SealerList;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.StartGroup;
import org.fisco.bcos.web3j.protocol.core.methods.response.StopGroup;
import org.fisco.bcos.web3j.protocol.core.methods.response.SyncStatus;
import org.fisco.bcos.web3j.protocol.core.methods.response.SystemConfig;
import org.fisco.bcos.web3j.protocol.core.methods.response.TotalTransactionCount;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceiptWithProof;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionWithProof;
import org.fisco.bcos.web3j.protocol.core.methods.response.UninstallFilter;

/** Core FISCO BCOS JSON-RPC API. */
public interface Ethereum {

    Request<?, NodeVersion> getNodeVersion();

    Request<?, BlockNumber> getBlockNumber();

    Request<?, PbftView> getPbftView();

    Request<?, SealerList> getSealerList();

    Request<?, ObserverList> getObserverList();

    Request<?, NodeIDList> getNodeIDList();

    Request<?, GroupList> getGroupList();

    Request<?, GroupPeers> getGroupPeers();

    Request<?, Peers> getPeers();

    Request<?, ConsensusStatus> getConsensusStatus();

    Request<?, SyncStatus> getSyncStatus();

    Request<?, SystemConfig> getSystemConfigByKey(String key);

    @Deprecated
    Request<?, Code> getCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, Code> getCode(String address);

    Request<?, TotalTransactionCount> getTotalTransactionCount();

    Request<?, BcosBlockHeader> getBlockHeaderByHash(String blockHash, boolean returnSealerList);

    Request<?, BcosBlockHeader> getBlockHeaderByNumber(
            BigInteger blockNumber, boolean returnSealerList);

    Request<?, BcosBlock> getBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    @Deprecated
    Request<?, BcosBlock> getBlockByNumber(
            DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects);

    Request<?, BcosBlock> getBlockByNumber(
            BigInteger blockNumber, boolean returnFullTransactionObjects);

    Request<?, BlockHash> getBlockHashByNumber(DefaultBlockParameter defaultBlockParameter);

    Request<?, BcosTransaction> getTransactionByHash(String transactionHash);

    Request<?, TransactionWithProof> getTransactionByHashWithProof(String transactionHash);

    Request<?, BcosTransaction> getTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, BcosTransaction> getTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, BcosTransactionReceipt> getTransactionReceipt(String transactionHash);

    /**
     * Get the total transaction receipts of the block by blocknumber
     *
     * @param blockNumber
     * @return
     */
    Request<?, BlockTransactionReceipts> getBlockTransactionReceipts(BigInteger blockNumber);
    /**
     * Get transaction receipts of the block by blockNumber、transaction receipt index and count
     *
     * @param blockNumber
     * @param offset
     * @param count
     * @return
     */
    Request<?, BlockTransactionReceipts> getBlockTransactionReceipts(
            BigInteger blockNumber, BigInteger offset, BigInteger count);
    /**
     * Get total transaction receipts of the block by block hash
     *
     * @param blockHash
     * @return
     */
    Request<?, BlockTransactionReceipts> getBlockTransactionReceiptsByHash(String blockHash);
    /**
     * Get the transaction receipts of the block by blockHash、transaction receipt index and count
     *
     * @param blockHash
     * @param offset
     * @param count
     * @return
     */
    Request<?, BlockTransactionReceipts> getBlockTransactionReceiptsByHash(
            String blockHash, BigInteger offset, BigInteger count);

    Request<?, TransactionReceiptWithProof> getTransactionReceiptByHashWithProof(
            String transactionHash);

    Request<?, PendingTransactions> getPendingTransaction();

    @Deprecated
    BigInteger getBlockNumberCache();

    BigInteger getBlockLimit();

    Request<?, PendingTxSize> getPendingTxSize();

    @Deprecated
    Request<?, Call> call(
            org.fisco.bcos.web3j.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, Call> call(
            org.fisco.bcos.web3j.protocol.core.methods.request.Transaction transaction);

    Request<?, SendTransaction> sendRawTransaction(String signedTransactionData);

    void sendRawTransaction(String signedTransactionData, TransactionSucCallback callback)
            throws IOException;

    Request<?, SendTransaction> sendRawTransactionAndGetProof(String signedTransactionData);

    void sendRawTransactionAndGetProof(
            String signedTransactionData, TransactionSucCallback callback) throws IOException;

    // generateGroup
    Request<?, GenerateGroup> generateGroup(
            int groupId, long timestamp, boolean enableFreeStorage, List<String> nodeList);

    Request<?, StartGroup> startGroup(int groupId);

    Request<?, StopGroup> stopGroup(int groupId);

    Request<?, RemoveGroup> removeGroup(int groupId);

    Request<?, RecoverGroup> recoverGroup(int groupId);

    Request<?, QueryGroupStatus> queryGroupStatus(int groupId);

    // TODO
    @Deprecated
    Request<?, BcosFilter> newPendingTransactionFilter();

    @Deprecated
    Request<?, BcosFilter> newBlockFilter();

    @Deprecated
    Request<?, BcosLog> getFilterChanges(BigInteger filterId);

    @Deprecated
    Request<?, UninstallFilter> getUninstallFilter(BigInteger filterId);

    @Deprecated
    Request<?, BcosFilter> newFilter(
            org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter ethFilter);
}
