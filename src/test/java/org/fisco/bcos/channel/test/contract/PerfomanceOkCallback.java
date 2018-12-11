package org.fisco.bcos.channel.test.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.fisco.bcos.channel.dto.EthereumResponse;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PerfomanceOkCallback {
	private ObjectMapper objectMapper = new ObjectMapper();
	private AtomicInteger finished = new AtomicInteger(0);
	private Integer total = 0;
	private Long allCost = new Long(0);
	private Long startTime = System.currentTimeMillis();
	Map<Integer, Integer> timeArea = new HashMap<Integer, Integer>();
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	PerfomanceOkCallback() {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		timeArea.put(0, 0);
		timeArea.put(1, 0);
		timeArea.put(2, 0);
		timeArea.put(3, 0);
		timeArea.put(4, 0);
		timeArea.put(5, 0);
		timeArea.put(6, 0);
	}
	
	public void onResponse(Long cost, EthereumResponse response) {
		TransactionReceipt transactionReceipt;
		try {
			transactionReceipt = objectMapper.readValue(response.getContent(), TransactionReceipt.class);

			// 检查执行结果
			//List<ResultCodeEventResponse> code = SeekFunBook.getResultCodeEvents(transactionReceipt);
			//if (code.get(0)._result.getValue().intValue() != 1) {
			//	System.out.println("交易执行错误!: " + String.valueOf(code.get(0)._result.getValue().intValue()));
			//}
			
			if(cost <= 50) {
				timeArea.put(0, timeArea.get(0) + 1);
			}
			else if(cost > 50 && cost <= 200) {
				timeArea.put(1, timeArea.get(1) + 1);
			}
			else if(cost > 200 && cost <= 500) {
				timeArea.put(2, timeArea.get(2) + 1);
			}
			else if(cost > 500 && cost <= 1000) {
				timeArea.put(3, timeArea.get(3) + 1);
			}
			else if(cost > 1000 && cost <= 2000) {
				timeArea.put(4, timeArea.get(4) + 1);
			}
			else if(cost > 2000 && cost <= 5000) {
				timeArea.put(5, timeArea.get(5) + 1);
			}
			else if(cost > 5000) {
				timeArea.put(6, timeArea.get(6) + 1);
			}
			
			allCost += cost;
			
			Integer current = finished.incrementAndGet();
			Integer area = total / 10;
			
			if (current >= area && ((current % area) == 0)) {
				System.out.println("----------------------------------- 已完成: " + current + "/" + total + " 交易");
			}

			if (current >= total) {
				Long totalCost = System.currentTimeMillis() - startTime;

				System.out.println("全部 " + total + " 交易已执行完成，共耗时" + totalCost.toString() + "ms");
				System.out.println("TPS: " + (double) total / ((double) totalCost / 1000));
				System.out.println("平均耗时: " + (double)allCost / total + "ms");
				
				System.out.println("");
				System.out.println("         耗时 <= 50ms   : " + timeArea.get(0));
				System.out.println("50ms   < 耗时 <= 200ms  : " + timeArea.get(1));
				System.out.println("200ms  < 耗时 <= 500ms  : " + timeArea.get(2));
				System.out.println("500ms  < 耗时 <= 1000ms : " + timeArea.get(3));
				System.out.println("1000ms < 耗时 <= 2000ms : " + timeArea.get(4));
				System.out.println("2000ms < 耗时 <= 5000ms : " + timeArea.get(5));
				System.out.println("5000ms < 耗时           : " + timeArea.get(6));

				System.out.println("测试完成");

				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("错误:" + e.getMessage());
		}
	}
}
