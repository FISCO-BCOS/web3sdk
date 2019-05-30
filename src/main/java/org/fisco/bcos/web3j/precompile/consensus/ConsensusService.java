package org.fisco.bcos.web3j.precompile.consensus;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ConsensusService {
  private static BigInteger gasPrice = new BigInteger("300000000");
  private static BigInteger gasLimit = new BigInteger("300000000");
  private static String ConsensusPrecompileAddress = "0x0000000000000000000000000000000000001003";
  private Web3j web3j;
  private Consensus consensus;

  public ConsensusService(Web3j web3j, Credentials credentials) {
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    this.web3j = web3j;
    consensus = Consensus.load(ConsensusPrecompileAddress, web3j, credentials, contractGasProvider);
  }

  public String addSealer(String nodeID) throws Exception {
    if (!isValidNodeID(nodeID)) {
      return PrecompiledCommon.transferToJson(PrecompiledCommon.P2pNetwork);
    }
    List<String> sealerList = web3j.getSealerList().send().getResult();
    if (sealerList.contains(nodeID)) {
      return PrecompiledCommon.transferToJson(PrecompiledCommon.SealerList);
    }
    TransactionReceipt receipt = consensus.addSealer(nodeID).send();
    return PrecompiledCommon.handleTransactionReceipt(receipt);
  }

  public String addObserver(String nodeID) throws Exception {
    if (!isValidNodeID(nodeID)) {
      return PrecompiledCommon.transferToJson(PrecompiledCommon.P2pNetwork);
    }
    List<String> observerList = web3j.getObserverList().send().getResult();
    if (observerList.contains(nodeID)) {
      return PrecompiledCommon.transferToJson(PrecompiledCommon.ObserverList);
    }
    TransactionReceipt receipt = consensus.addObserver(nodeID).send();
    return PrecompiledCommon.handleTransactionReceipt(receipt);
  }

  public String removeNode(String nodeId) throws Exception {
    List<String> groupPeers = web3j.getGroupPeers().send().getResult();
    if (!groupPeers.contains(nodeId)) {
      return PrecompiledCommon.transferToJson(PrecompiledCommon.GroupPeers);
    }
    TransactionReceipt receipt = new TransactionReceipt();
    try {
      receipt = consensus.remove(nodeId).send();
    } catch (RuntimeException e) {
      // firstly remove node that sdk connected to the node, return the request that present
      // susscces
      // because the exception is throwed by getTransactionReceipt, we need ignore it.
      if (e.getMessage()
          .contains("Don't send requests to this group")) {
        return PrecompiledCommon.transferToJson(0);
      } else {
        throw e;
      }
    }
    return PrecompiledCommon.handleTransactionReceipt(receipt);
  }

  private boolean isValidNodeID(String _nodeID) throws IOException, JsonProcessingException {
    boolean flag = false;
    List<String> nodeIDs = web3j.getNodeIDList().send().getResult();
    for (String nodeID : nodeIDs) {
      if (_nodeID.equals(nodeID)) {
        flag = true;
        break;
      }
    }
    return flag;
  }
}
