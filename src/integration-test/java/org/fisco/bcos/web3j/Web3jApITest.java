package org.fisco.bcos.web3j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.methods.response.BlockNumber;
import org.fisco.bcos.web3j.protocol.core.methods.response.GroupList;
import org.fisco.bcos.web3j.protocol.core.methods.response.GroupPeers;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeVersion;
import org.fisco.bcos.web3j.protocol.core.methods.response.PbftView;
import org.fisco.bcos.web3j.protocol.core.methods.response.Peers;
import org.fisco.bcos.web3j.protocol.core.methods.response.Transaction;
import org.junit.Ignore;
import org.junit.Test;

public class Web3jApITest extends TestBase {
  
  @Test
  public void getNodeVersion() throws IOException {
  	NodeVersion nodeVersion = web3j.getNodeVersion().send();
    assertNotNull(nodeVersion.getNodeVersion());
  }
  
  @Test
  public void getBlockNumber() throws IOException {
  	BlockNumber blockNumber = web3j.getBlockNumber().send();
    assertTrue(blockNumber.getBlockNumber().compareTo(new BigInteger("0")) >= 0);
  }
  
  @Test
  public void pbftView() throws Exception {
    PbftView pbftView = web3j.getPbftView().send();
    assertTrue(pbftView.getPbftView().compareTo(new BigInteger("0")) >= 0);
  }

  @Test
  public void consensusStatus() throws Exception {
    System.out.println(web3j.getConsensusStatus().sendForReturnString());
    assertNotNull(web3j.getConsensusStatus().sendForReturnString());
  }

  @Test
  public void sync() throws Exception {
  	String syncStatus = web3j.getSyncStatus().sendForReturnString();
    assertNotNull(syncStatus);
  }

  @Test
  public void peers() throws Exception {
    Peers ethPeers = web3j.getPeers().send();
    System.out.println(ethPeers.getValue().get(0).getNodeID());
    assertNotNull(ethPeers);
  }

  @Test
  public void groupPeers() throws Exception {
    GroupPeers groupPeers = web3j.getGroupPeers().send();
    groupPeers.getGroupPeers().stream().forEach(System.out::println);
    assertNotNull(groupPeers.getResult());
  }

  @Test
  public void groupList() throws Exception {
    GroupList groupList = web3j.getGroupList().send();
    groupList.getGroupList().stream().forEach(System.out::println);
    assertTrue((groupList.getGroupList().size() > 0));
  }

  @Ignore
  @Test
  public void getTransactionByBlockNumberAndIndex() throws IOException {
    Transaction transaction =
        web3j
            .getTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(new BigInteger("1")), new BigInteger("0"))
            .send()
            .getTransaction()
            .get();
    assertTrue(transaction.getBlockNumber().intValue() == 1);
  }

}
