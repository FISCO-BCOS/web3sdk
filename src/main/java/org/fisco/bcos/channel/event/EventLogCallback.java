package org.fisco.bcos.channel.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.BaseException;
import org.fisco.bcos.web3j.tx.txdecode.EventResultEntity;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class EventLogCallback {

	private static Logger logger = LoggerFactory.getLogger(EventLogCallback.class);

	public EventLogCallback() {
		this.decoder = null;
	}

	public EventLogCallback(String abi, String bin) {
		this.abi = abi;
		this.bin = bin;
		this.decoder = TransactionDecoderFactory.buildTransactionDecoder(abi, bin);
	}

	public EventLogCallback(TransactionDecoder decoder) {
		this.decoder = decoder;
	}

	private TransactionDecoder decoder;
	private String abi;
	private String bin;

	public TransactionDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(TransactionDecoder decoder) {
		this.decoder = decoder;
	}
	
	public List<Log> toLogList(String content) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		List<Log> logs = objectMapper.readValue(content,
				objectMapper.getTypeFactory().constructCollectionType(List.class, Log.class));

		return logs;
	}

	public List<List<List<EventResultEntity>>> decode(String content)
			throws JsonParseException, JsonMappingException, IOException, BaseException {
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		List<Log> logs = objectMapper.readValue(content,
				objectMapper.getTypeFactory().constructCollectionType(List.class, Log.class));

		return decode(logs);
	}

	public List<List<List<EventResultEntity>>> decode(List<Log> logs) throws BaseException, IOException {
		if (null == getDecoder()) {
			return null;
		}
		Map<String, List<List<EventResultEntity>>> mapEventLogs = this.getDecoder().decodeEventReturnObject(logs);
		List<List<List<EventResultEntity>>> r = new ArrayList<List<List<EventResultEntity>>>();
		for (List<List<EventResultEntity>> value : mapEventLogs.values()) {
			r.add(value);
		}
		logger.trace(" decode result, r: {}", r);
		return r;
	}

	public abstract void onResponse(List<Log> logs, List<List<List<EventResultEntity>>> result, int status);
}