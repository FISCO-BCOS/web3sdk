package org.fisco.bcos.web3j.precompile.cns;

/** ENS resolution exception. */
public class CnsResolutionException extends RuntimeException {
  public CnsResolutionException(String message) {
    super(message);
  }

  public CnsResolutionException(String message, Throwable cause) {
    super(message, cause);
  }
}
