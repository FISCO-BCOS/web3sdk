package org.bcos.channel.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.client.Service;
import org.bcos.channel.handler.ConnectionInfo;
import io.netty.channel.ChannelHandlerContext;

public class ChannelPush {
	static Logger logger = LoggerFactory.getLogger(ChannelPush.class);
	
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

	public String getToOrg() {
		return toOrg;
	}

	public void setToOrg(String toOrg) {
		this.toOrg = toOrg;
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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void sendResponse(ChannelResponse response) {
		logger.debug("发送回包 seq:{}", response.getMessageID());
		
		response.setMessageID(seq);
		
		service.sendResponseMessage(response, info, ctx, fromNode, toNode, seq);
	}

	private String keyID; //链ID
	private String orgApp; //来源标识
	private String version; //版本
	private String bankNO; //机构标识
	private String appName; //应用类型
	
	private String messageID; //消息的唯一标识id
	private String toOrg; //目标机构标识
	
	private Integer timeout; //超时时间（毫秒）
	private Integer ttl; //TTL
	
	private String content; //请求包体
	
	//回包用字段

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public ConnectionInfo getInfo() {
		return info;
	}

	public void setInfo(ConnectionInfo info) {
		this.info = info;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public String getFromNode() {
		return fromNode;
	}

	public void setFromNode(String fromNode) {
		this.fromNode = fromNode;
	}

	public String getToNode() {
		return toNode;
	}

	public void setToNode(String toNode) {
		this.toNode = toNode;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	private Service service;
	private ConnectionInfo info;
	private ChannelHandlerContext ctx;
	private String fromNode;
	private String toNode;
	private String seq;
}
