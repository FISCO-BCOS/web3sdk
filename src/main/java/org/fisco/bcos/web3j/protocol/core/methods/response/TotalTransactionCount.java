package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

/** getTotalTransactionCount */
public class TotalTransactionCount extends Response<TotalTransactionCount.TransactionCount> {
  public TransactionCount getTotalTransactionCount() {
    return getResult();
  }

  public class TransactionCount {
    private String txSum;
    private String blockNumber;

    public TransactionCount() {}

    public TransactionCount(String txSum, String blockNumber) {
      this.txSum = txSum;
      this.blockNumber = blockNumber;
    }

    public BigInteger getTxSum() {
      return Numeric.decodeQuantity(txSum);
    }

    public String getTxSumRaw() {
      return txSum;
    }

    public void setTxSum(String txSum) {
      this.txSum = txSum;
    }

    public BigInteger getBlockNumber() {
      return Numeric.decodeQuantity(blockNumber);
    }

    public String getBlockNumberRaw() {
      return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
      this.blockNumber = blockNumber;
    }
  }
}
