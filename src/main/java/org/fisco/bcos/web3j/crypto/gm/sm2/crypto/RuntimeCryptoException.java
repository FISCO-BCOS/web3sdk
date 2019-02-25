package org.fisco.bcos.web3j.crypto.gm.sm2.crypto;

/** the foundation class for the exceptions thrown by the crypto packages. */
public class RuntimeCryptoException extends RuntimeException {

  private static final long serialVersionUID = -560419554349052616L;

  /** base constructor. */
  public RuntimeCryptoException() {}

  /**
   * create a RuntimeCryptoException with the given message.
   *
   * @param message the message to be carried with the exception.
   */
  public RuntimeCryptoException(String message) {
    super(message);
  }
}
