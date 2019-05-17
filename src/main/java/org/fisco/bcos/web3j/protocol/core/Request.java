package org.fisco.bcos.web3j.protocol.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.reactivex.Flowable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.protocol.Web3jService;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;

public class Request<S, T extends Response> {
    private static AtomicLong nextId = new AtomicLong(0);
    private String jsonrpc = "2.0";
    private String method;
    private List<S> params;
    private long id;
    private TransactionSucCallback callback;

    private Web3jService web3jService;

    // Unfortunately require an instance of the type too, see
    // http://stackoverflow.com/a/3437930/3211687
    private Class<T> responseType;

    // 添加属性支持交易回调
    @JsonIgnore
    private boolean needTransCallback;
    @JsonIgnore
    private TransactionSucCallback transactionSucCallback;

    public boolean isNeedTransCallback() {
	return needTransCallback;
    }

    public void setNeedTransCallback(boolean needTransCallback) {
	this.needTransCallback = needTransCallback;
    }

    public void setTransactionSucCallback(TransactionSucCallback transactionSucCallback) {
	this.transactionSucCallback = transactionSucCallback;
    }

    public TransactionSucCallback getTransactionSucCallback() {
	return transactionSucCallback;
    }

    public Request() {
    }

    public Request(String method, List<S> params, Web3jService web3jService, Class<T> type) {
	this.method = method;
	this.params = params;
	this.id = nextId.getAndIncrement();
	this.web3jService = web3jService;
	this.responseType = type;
    }

    public String getJsonrpc() {
	return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
	this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
	return method;
    }

    public void setMethod(String method) {
	this.method = method;
    }

    public List<S> getParams() {
	return params;
    }

    public void setParams(List<S> params) {
	this.params = params;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public T send() throws IOException {
	return web3jService.send(this, responseType);
    }

    public void sendOnly() throws IOException {
	web3jService.sendOnly(this);
    }

    public String sendForReturnString() throws IOException {
	ChannelEthereumService channelEthereumService = (ChannelEthereumService) web3jService;
	return channelEthereumService.sendSpecial(this);
    }

    public CompletableFuture<T> sendAsync() {
	return web3jService.sendAsync(this, responseType);
    }

    public Flowable<T> flowable() {
	return new RemoteCall<>(this::send).flowable();
    }

    public TransactionSucCallback getCallback() {
	return callback;
    }

    public void setCallback(TransactionSucCallback callback) {
	this.callback = callback;
    }
}
