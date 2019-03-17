package org.fisco.bcos.web3j.precompile.common;

public class PrecompiledResponse {

  private int code;
  private String msg;

  public PrecompiledResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public PrecompiledResponse(int code, String msg) {
    super();
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
