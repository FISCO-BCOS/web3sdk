package org.fisco.bcos.channel.dto;

import java.nio.charset.StandardCharsets;

public class ChannelResponse {

    private Integer errorCode; // 错误码
    private String errorMessage; // 错误信息
    private String messageID; // 消息唯一ID
    private byte[] content; // 响应包体

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
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
}
