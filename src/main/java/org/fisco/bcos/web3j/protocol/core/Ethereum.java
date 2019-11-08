package org.fisco.bcos.web3j.protocol.core;

import java.math.BigInteger;
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

    Request<?, Code> getCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, TotalTransactionCount> getTotalTransactionCount();

    Request<?, BcosBlock> getBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, BcosBlock> getBlockByNumber(
            DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects);

    Request<?, BlockHash> getBlockHashByNumber(DefaultBlockParameter defaultBlockParameter);

    Request<?, BcosTransaction> getTransactionByHash(String transactionHash);

    Request<?, BcosTransaction> getTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, BcosTransaction> getTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, BcosTransactionReceipt> getTransactionReceipt(String transactionHash);

    Request<?, PendingTransactions> getPendingTransaction();

    BigInteger getBlockNumberCache();

    Request<?, PendingTxSize> getPendingTxSize();

    Request<?, Call> call(
            org.fisco.bcos.web3j.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, SendTransaction> sendRawTransaction(String signedTransactionData);
}
