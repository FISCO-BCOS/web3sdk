package org.fisco.bcos.channel.test.contract;


import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.dto.EthereumResponse;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PerfomanceOkCallback extends TransactionSucCallback {
	static private ObjectMapper objectMapper = new ObjectMapper();
	private Long startTime = System.currentTimeMillis();
	
	private PerfomanceCollector collector;
	
	public PerfomanceCollector getCollector() {
		return collector;
	}

	public void setCollector(PerfomanceCollector collector) {
		this.collector = collector;
	}

	static Logger logger = LoggerFactory.getLogger(PerfomanceOkCallback.class);
	
	PerfomanceOkCallback() {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Override
	public void onResponse(EthereumResponse response) {
		Long cost = System.currentTimeMillis() - startTime;
		
		if(response.getErrorCode() != 0) {
			logger.error("transactionCallback error: {} {}", response.getErrorCode(), response.getErrorMessage());
			collector.onMessage(null, cost);
			return;
		}
		
		try {
			logger.trace("transactionCallback: {}", response.getContent());
			TransactionReceipt receipt = objectMapper.readValue(response.getContent().getBytes(), TransactionReceipt.class);
			collector.onMessage(receipt, cost);
		} catch (Exception e) {
			logger.error("decode receipt error: ", e);
			
			collector.onMessage(null, cost);
		}
	}
}
