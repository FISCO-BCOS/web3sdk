package org.bcos.channel.test.performance;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.channel.dto.EthereumResponse;

public class PerformanceOKCallback extends TransactionSucCallback {

    private Long startTime = System.currentTimeMillis();

    private PerformanceOKCollector collector = null;

    @Override
    public void onResponse(EthereumResponse response) {
        Long cost = System.currentTimeMillis() - startTime;
        collector.onMessage(response, cost);
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public PerformanceOKCollector getCollector() {
        return collector;
    }

    public void setCollector(PerformanceOKCollector collector) {
        this.collector = collector;
    }
}
