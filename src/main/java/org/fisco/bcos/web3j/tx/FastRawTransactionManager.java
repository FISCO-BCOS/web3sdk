package org.fisco.bcos.web3j.tx;

import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;

/**
 * Simple RawTransactionManager derivative that manages nonces to facilitate multiple transactions
 * per block.
 */
public class FastRawTransactionManager extends RawTransactionManager {

  private volatile BigInteger nonce = BigInteger.valueOf(-1);

  public FastRawTransactionManager(Web3j web3j, Credentials credentials, byte chainId) {
    super(web3j, credentials, chainId);
  }

  public FastRawTransactionManager(Web3j web3j, Credentials credentials) {
    super(web3j, credentials);
  }

  public BigInteger getCurrentNonce() {
    return nonce;
  }

  public synchronized void setNonce(BigInteger value) {
    nonce = value;
  }
}
