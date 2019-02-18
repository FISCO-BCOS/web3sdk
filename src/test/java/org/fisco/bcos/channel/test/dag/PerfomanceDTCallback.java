package org.fisco.bcos.channel.test.dag;


import java.math.BigInteger;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PerfomanceDTCallback extends TransactionSucCallback {
	static private ObjectMapper objectMapper = new ObjectMapper();
	private Long startTime = System.currentTimeMillis();
	
	private PerfomanceDTCollector collector = null;
	private DagUserMgr dagUserMgr = null;
	private DagTransferUser dagTransferUser = null;
	
	public DagUserMgr getDagUserMgr() {
		return dagUserMgr;
	}

	public void setDagUserMgr(DagUserMgr dagUserMgr) {
		this.dagUserMgr = dagUserMgr;
	}

	public DagTransferUser getDagTransferUser() {
		return dagTransferUser;
	}

	public void setDagTransferUser(DagTransferUser dagTransferUser) {
		this.dagTransferUser = dagTransferUser;
	}

	public PerfomanceDTCollector getCollector() {
		return collector;
	}

	public void setCollector(PerfomanceDTCollector collector) {
		this.collector = collector;
	}

	static Logger logger = LoggerFactory.getLogger(PerfomanceDTCallback.class);
	
	public PerfomanceDTCallback() {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Override
	public void onResponse(TransactionReceipt receipt) {
		Long cost = System.currentTimeMillis() - startTime;
		
		try {
			if(receipt.isStatusOK()) {
				String output = receipt.getOutput();
				if(!output.isEmpty()) {
					int code = new BigInteger(output.substring(2, output.length()), 16).intValue();
					logger.debug(" output is {}, code is {} ", output, code);
					if (0 == code && null != dagTransferUser) {
						dagUserMgr.addUser(dagTransferUser);
					}
				}
			}
			collector.onMessage(receipt, cost);
		} catch (Exception e) {
			logger.error("onMessage error: ", e);
		}
	}
}
