package org.fisco.bcos.channel.dto;

import java.nio.charset.StandardCharsets;

public class ChannelRequest {

    private String fromOrg; // 链ID
    private String orgApp; // 来源标识
    private String version; // 版本
    private String bankNO; // 机构标识
    private String appName; // 应用类型

    private String messageID; // 消息的唯一标识id
    private String toOrg; // 目标机构标识
    private String toTopic; // 目标topic

    private Integer timeout = 0; // 超时时间（毫秒）
    private Integer ttl; // TTL 限制重试的次数

    private byte[] content; // 请求包体

    private short type = 0; // 请求类型

    public String getFromOrg() {
        return fromOrg;
    }

    public void setFromOrg(String keyID) {
        this.fromOrg = keyID;
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

    public String getToOrg() {
        return toOrg;
    }

    public void setToOrg(String toOrg) {
        this.toOrg = toOrg;
    }

    public String getToTopic() {
        return toTopic;
    }

    public void setToTopic(String toTopic) {
        this.toTopic = toTopic;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public String getContent() {

        if (this.content == null) {
            return null;
        }
        String _content = new String(this.content, StandardCharsets.UTF_8);
        return _content;
    }

    public byte[] getContentByteArray() {
        return this.content;
    }

    public void setContent(String content) {
        if (content == null) {
            this.content = null;
        } else {
            this.content = content.getBytes();
        }
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
