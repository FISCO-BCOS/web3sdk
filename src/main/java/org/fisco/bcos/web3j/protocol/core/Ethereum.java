package org.fisco.bcos.web3j.protocol.core;

import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.core.methods.response.*;

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

  // TODO
  Request<?, BcosFilter> newPendingTransactionFilter();

  Request<?, BcosFilter> newBlockFilter();

  Request<?, BcosLog> getFilterChanges(BigInteger filterId);

  Request<?, UninstallFilter> getUninstallFilter(BigInteger filterId);

  Request<?, BcosFilter> newFilter(
      org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter ethFilter);
}
