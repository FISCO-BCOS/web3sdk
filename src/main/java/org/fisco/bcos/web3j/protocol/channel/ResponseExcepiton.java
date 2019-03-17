package org.fisco.bcos.web3j.protocol.channel;

import java.io.IOException;

public class ResponseExcepiton extends IOException {

  private static final long serialVersionUID = 1L;

  public ResponseExcepiton(int code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

  private int code;
  private String message;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
