package org.bcos.channel.dto;

import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

public class EthereumResponse {

    private TransactionReceipt transactionReceipt;

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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TransactionReceipt getTransactionReceipt() {
		return transactionReceipt;
	}

	public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
		this.transactionReceipt = transactionReceipt;
	}

	private Integer errorCode; //错误码
	private String errorMessage; //错误信息

	private String messageID; //消息唯一ID
	
	private String content; //响应包体
}
