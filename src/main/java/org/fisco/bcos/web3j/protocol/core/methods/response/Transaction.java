package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import org.fisco.bcos.web3j.utils.Numeric;

/** Transaction object used by both {@link BcosTransaction} and {@link BcosBlock}. */
public class Transaction {
  private String hash;
  private String nonce;
  private String blockHash;
  private String blockNumber;
  private String transactionIndex;
  private String from;
  private String to;
  private String value;
  private String gasPrice;
  private String gas;
  private String input;
  private String creates;
  private String publicKey;
  private String raw;
  private String r;
  private String s;
  private int v; // see https://github.com/web3j/web3j/issues/44

  public Transaction() {}

  public Transaction(
      String hash,
      String nonce,
      String blockHash,
      String blockNumber,
      String transactionIndex,
      String from,
      String to,
      String value,
      String gas,
      String gasPrice,
      String input,
      String creates,
      String publicKey,
      String raw,
      String r,
      String s,
      int v) {
    this.hash = hash;
    this.nonce = nonce;
    this.blockHash = blockHash;
    this.blockNumber = blockNumber;
    this.transactionIndex = transactionIndex;
    this.from = from;
    this.to = to;
    this.value = value;
    this.gasPrice = gasPrice;
    this.gas = gas;
    this.input = input;
    this.creates = creates;
    this.publicKey = publicKey;
    this.raw = raw;
    this.r = r;
    this.s = s;
    this.v = v;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public BigInteger getNonce() {
    return Numeric.decodeQuantity(nonce);
  }

  public String getNonceRaw() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  public String getBlockHash() {
    return blockHash;
  }

  public void setBlockHash(String blockHash) {
    this.blockHash = blockHash;
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

  public BigInteger getTransactionIndex() {
    return Numeric.decodeQuantity(transactionIndex);
  }

  public String getTransactionIndexRaw() {
    return transactionIndex;
  }

  public void setTransactionIndex(String transactionIndex) {
    this.transactionIndex = transactionIndex;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public BigInteger getValue() {
    return Numeric.decodeQuantity(value);
  }

  public String getValueRaw() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public BigInteger getGasPrice() {
    return Numeric.decodeQuantity(gasPrice);
  }

  public String getGasPriceRaw() {
    return gasPrice;
  }

  public void setGasPrice(String gasPrice) {
    this.gasPrice = gasPrice;
  }

  public BigInteger getGas() {
    return Numeric.decodeQuantity(gas);
  }

  public String getGasRaw() {
    return gas;
  }

  public void setGas(String gas) {
    this.gas = gas;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getCreates() {
    return creates;
  }

  public void setCreates(String creates) {
    this.creates = creates;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public String getRaw() {
    return raw;
  }

  public void setRaw(String raw) {
    this.raw = raw;
  }

  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }

  public String getS() {
    return s;
  }

  public void setS(String s) {
    this.s = s;
  }

  public int getV() {
    return v;
  }

  // public void setV(byte v) {
  //     this.v = v;
  // }

  // Workaround until Geth & Parity return consistent values. At present
  // Parity returns a byte value, Geth returns a hex-encoded string
  // https://github.com/ethereum/go-ethereum/issues/3339
  public void setV(Object v) {
    if (v instanceof String) {
      this.v = Numeric.toBigInt((String) v).intValueExact();
    } else {
      this.v = ((Integer) v);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Transaction)) {
      return false;
    }

    Transaction that = (Transaction) o;

    if (getV() != that.getV()) {
      return false;
    }
    if (getHash() != null ? !getHash().equals(that.getHash()) : that.getHash() != null) {
      return false;
    }
    if (getNonceRaw() != null
        ? !getNonceRaw().equals(that.getNonceRaw())
        : that.getNonceRaw() != null) {
      return false;
    }
    if (getBlockHash() != null
        ? !getBlockHash().equals(that.getBlockHash())
        : that.getBlockHash() != null) {
      return false;
    }
    if (getBlockNumberRaw() != null
        ? !getBlockNumberRaw().equals(that.getBlockNumberRaw())
        : that.getBlockNumberRaw() != null) {
      return false;
    }
    if (getTransactionIndexRaw() != null
        ? !getTransactionIndexRaw().equals(that.getTransactionIndexRaw())
        : that.getTransactionIndexRaw() != null) {
      return false;
    }
    if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) {
      return false;
    }
    if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) {
      return false;
    }
    if (getValueRaw() != null
        ? !getValueRaw().equals(that.getValueRaw())
        : that.getValueRaw() != null) {
      return false;
    }
    if (getGasPriceRaw() != null
        ? !getGasPriceRaw().equals(that.getGasPriceRaw())
        : that.getGasPriceRaw() != null) {
      return false;
    }
    if (getGasRaw() != null ? !getGasRaw().equals(that.getGasRaw()) : that.getGasRaw() != null) {
      return false;
    }
    if (getInput() != null ? !getInput().equals(that.getInput()) : that.getInput() != null) {
      return false;
    }
    if (getCreates() != null
        ? !getCreates().equals(that.getCreates())
        : that.getCreates() != null) {
      return false;
    }
    if (getPublicKey() != null
        ? !getPublicKey().equals(that.getPublicKey())
        : that.getPublicKey() != null) {
      return false;
    }
    if (getRaw() != null ? !getRaw().equals(that.getRaw()) : that.getRaw() != null) {
      return false;
    }
    if (getR() != null ? !getR().equals(that.getR()) : that.getR() != null) {
      return false;
    }
    return getS() != null ? getS().equals(that.getS()) : that.getS() == null;
  }

  @Override
  public int hashCode() {
    int result = getHash() != null ? getHash().hashCode() : 0;
    result = 31 * result + (getNonceRaw() != null ? getNonceRaw().hashCode() : 0);
    result = 31 * result + (getBlockHash() != null ? getBlockHash().hashCode() : 0);
    result = 31 * result + (getBlockNumberRaw() != null ? getBlockNumberRaw().hashCode() : 0);
    result =
        31 * result + (getTransactionIndexRaw() != null ? getTransactionIndexRaw().hashCode() : 0);
    result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
    result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
    result = 31 * result + (getValueRaw() != null ? getValueRaw().hashCode() : 0);
    result = 31 * result + (getGasPriceRaw() != null ? getGasPriceRaw().hashCode() : 0);
    result = 31 * result + (getGasRaw() != null ? getGasRaw().hashCode() : 0);
    result = 31 * result + (getInput() != null ? getInput().hashCode() : 0);
    result = 31 * result + (getCreates() != null ? getCreates().hashCode() : 0);
    result = 31 * result + (getPublicKey() != null ? getPublicKey().hashCode() : 0);
    result = 31 * result + (getRaw() != null ? getRaw().hashCode() : 0);
    result = 31 * result + (getR() != null ? getR().hashCode() : 0);
    result = 31 * result + (getS() != null ? getS().hashCode() : 0);
    result = 31 * result + getV();
    return result;
  }
}
