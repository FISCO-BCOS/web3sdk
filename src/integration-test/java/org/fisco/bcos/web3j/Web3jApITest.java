package org.fisco.bcos.web3j;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockHash;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockNumber;
import org.fisco.bcos.web3j.protocol.core.methods.response.Code;
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
import org.fisco.bcos.web3j.protocol.core.methods.response.SystemConfig;
import org.fisco.bcos.web3j.protocol.core.methods.response.TotalTransactionCount;
import org.fisco.bcos.web3j.protocol.core.methods.response.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;

public class Web3jApITest extends TestBase {

  @Test
  public void getNodeVersion() throws IOException {
      NodeVersion nodeVersion = web3j.getNodeVersion().send();
      assertNotNull(nodeVersion.getNodeVersion().getBuildTime());
      assertNotNull(nodeVersion.getNodeVersion().getBuildType());
      assertNotNull(nodeVersion.getNodeVersion().getGitBranch());
      assertNotNull(nodeVersion.getNodeVersion().getGitCommit());
      assertNotNull(nodeVersion.getNodeVersion().getVersion());
  }

  @Test
  public void getBlockNumber() throws IOException, InterruptedException, ExecutionException {
    BlockNumber blockNumber = web3j.getBlockNumber().send();
    assertNotNull(blockNumber.getBlockNumber());
    blockNumber = web3j.getBlockNumber().sendAsync().get();
    assertNotNull(blockNumber.getBlockNumber());
  }

  @Test
  public void getBlockNumberCache() throws IOException {
    BigInteger blockNumberCache = web3j.getBlockNumberCache();
    assertNotNull(blockNumberCache);
  }

  @Test
  public void pbftView() throws Exception {
    PbftView pbftView = web3j.getPbftView().send();
    assertNotNull(pbftView.getPbftView());
  }

  @Test
  public void getConsensusStatus() throws Exception {
    String consensusStatus = web3j.getConsensusStatus().sendForReturnString();
    assertNotNull(consensusStatus);
  }

  @Test
  public void getSyncStatus() throws Exception {
    String syncStatus = web3j.getSyncStatus().sendForReturnString();
    assertNotNull(syncStatus);
  }

  @Test
  public void peers() throws Exception {
    Peers peers = web3j.getPeers().send();
    assertNotNull(peers.getPeers());
  }

  @Test
  public void groupPeers() throws Exception {
    GroupPeers groupPeers = web3j.getGroupPeers().send();
    assertNotNull(groupPeers.getGroupPeers());
  }

  @Test
  public void groupList() throws Exception {
    GroupList groupList = web3j.getGroupList().send();
    assertNotNull(groupList.getGroupList());
  }

  @Test
  public void getSealerList() throws Exception {
    SealerList sealerList = web3j.getSealerList().send();
    assertNotNull(sealerList.getSealerList());
  }

  @Test
  public void getObserverList() throws Exception {
    ObserverList observerList = web3j.getObserverList().send();
    assertNotNull(observerList.getObserverList());
  }

  @Test
  public void getNodeIDList() throws Exception {
    NodeIDList nodeIDList = web3j.getNodeIDList().send();
    assertNotNull(nodeIDList.getNodeIDList());
  }

  @Test
  public void getSystemConfigByKey() throws Exception {
    SystemConfig txCountLimit = web3j.getSystemConfigByKey("tx_count_limit").send();
    SystemConfig txGasLimit = web3j.getSystemConfigByKey("tx_gas_limit").send();
    assertNotNull(txCountLimit.getSystemConfigByKey());
    assertNotNull(txGasLimit.getSystemConfigByKey());
  }

  /*
  @Test
  public void getCode() throws Exception {
    Code code = web3j.getCode(address).send();
    assertNotNull(code.getCode());
  }
  */

  @Test
  public void getTotalTransactionCount() throws Exception {
    TotalTransactionCount count = web3j.getTotalTransactionCount().send();
    assertNotNull(count.getTotalTransactionCount());
  }

  @Test
  public void getBlockByHash() throws Exception {
    BcosBlock bcosBlock = web3j.getBlockByHash(blockHash, true).send();
    assertNotNull(bcosBlock.getBlock());
  }

  @Test
  public void getBlockByNumber() throws Exception {
    BcosBlock bcosBlock =
        web3j.getBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send();
    assertNotNull(bcosBlock.getBlock());
  }

  @Test
  public void getBlockHashByNumber() throws Exception {
    BlockHash blockHash =
        web3j.getBlockHashByNumber(DefaultBlockParameter.valueOf(blockNumber)).send();
    assertNotNull(blockHash.getBlockHashByNumber());
  }

  @Test
  public void getTransactionByHash() throws Exception {
    BcosTransaction bcosTransaction = web3j.getTransactionByHash(blockHash).send();
    assertNotNull(bcosTransaction.getTransaction());
  }

  @Test
  public void getTransactionByBlockNumberAndIndex() throws IOException {
    BcosTransaction bcosTransaction =
        web3j
            .getTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(blockNumber), new BigInteger("0"))
            .send();
    Transaction transaction = bcosTransaction.getTransaction().get();
    assertNotNull(transaction);
  }

  @Test
  public void getTransactionByBlockHashAndIndex() throws IOException {
    BcosTransaction bcosTransaction =
        web3j.getTransactionByBlockHashAndIndex(blockHash, new BigInteger("0")).send();
    Transaction transaction = bcosTransaction.getTransaction().get();
    assertNotNull(transaction);
  }

  @Test
  public void getTransactionReceipt() throws IOException {
    BcosTransactionReceipt bcosTransactionReceipt = web3j.getTransactionReceipt(txHash).send();
    TransactionReceipt transactionReceipt = bcosTransactionReceipt.getTransactionReceipt().get();
    assertNotNull(transactionReceipt);
  }

  @Test
  public void getPendingTransaction() throws IOException {
    PendingTransactions pendingTransactions = web3j.getPendingTransaction().send();
    assertNotNull(pendingTransactions.getPendingTransactions());
  }

  @Test
  public void getPendingTxSize() throws IOException {
    PendingTxSize pendingTxSize = web3j.getPendingTxSize().send();
    assertNotNull(pendingTxSize.getPendingTxSize());
  }
}
