package org.fisco.bcos.channel.dto;

public class FiscoRequest {
  private String keyID; // chain ID
  private String orgApp; // org identification
  private String version;
  private String bankNO; // institution identification
  private String appName; // application kinds

  private String messageID;
  private Integer timeout = 0; // ms

  private String content;

  public String getKeyID() {
    return keyID;
  }

  public void setKeyID(String keyID) {
    this.keyID = keyID;
  }

  public String getOrgApp() {
    return orgApp;
  }

  public void setOrgApp(String orgApp) {
    this.orgApp = orgApp;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getBankNO() {
    return bankNO;
  }

  public void setBankNO(String bankNO) {
    this.bankNO = bankNO;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getMessageID() {
    return messageID;
  }

  public void setMessageID(String messageID) {
    this.messageID = messageID;
  }

  public Integer getTimeout() {
    return timeout;
  }

  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


}
