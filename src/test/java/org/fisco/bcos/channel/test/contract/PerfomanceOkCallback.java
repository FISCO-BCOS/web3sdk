package org.fisco.bcos.channel.test.contract;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfomanceOkCallback extends TransactionSucCallback {
  private static ObjectMapper objectMapper = new ObjectMapper();
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
  public void onResponse(TransactionReceipt receipt) {
    Long cost = System.currentTimeMillis() - startTime;

    try {
      collector.onMessage(receipt, cost);
    } catch (Exception e) {
      logger.error("onMessage error: ", e);
    }
  }
}
