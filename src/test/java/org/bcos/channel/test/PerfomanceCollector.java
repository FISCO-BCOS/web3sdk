package org.bcos.channel.test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bcos.channel.client.ChannelResponseCallback;
import org.bcos.channel.dto.ChannelResponse;

public class PerfomanceCollector {
	static Logger logger = LoggerFactory.getLogger(PerfomanceCollector.class);
	
	public void onMessage(ChannelResponse response) {
		try {
			Integer currentError = 0;
			
			if(response.getErrorCode() != 0) {
				System.out.println("接收错误:" + String.valueOf(response.getErrorCode()) + ", " + response.getErrorMessage());
				
				currentError = 1;
			}
			
			RequestTimer timer = null;
			if(response.getMessageID() != null) {
				timer = resultMap.get(response.getMessageID());
				if(timer == null) {
					System.out.println("返回错误，响应seq:" + String.valueOf(response.getMessageID()) + " 无对应请求");
					currentError = 1;
				}
			}
			else {
				currentError = 1;
			}
			
			error.addAndGet(currentError);
			
			if(timer != null) {
				timer.recvTimestamp = System.currentTimeMillis();
			}
			
			received.incrementAndGet();
			
			logger.debug("收到回包: {} {} {}, 总计:{}/{}", response.getErrorCode(), response.getMessageID(), response.getContent(), received, total);
			
			if((received.get() + 1) % (total / 10) == 0) {
				System.out.println("                                                       |已收到:" + String.valueOf((received.get() + 1) * 100 / total) + "%");
			}
			
			if(received.intValue() >= total) {
				System.out.println("已接收全部请求");
				
				//总耗时
				Long totalTime = System.currentTimeMillis() - startTimestamp;
				
				System.out.println("===================================================================");
				
				Integer less50 = 0;
				Integer less100 = 0;
				Integer less200 = 0;
				Integer less400 = 0;
				Integer less1000 = 0;
				Integer less2000 = 0;
				Integer timeout2000 = 0;
				
				Long totalCost = (long) 0;
				//汇总信息并输出
				for(RequestTimer v: resultMap.values()) {
					Long cost = (long)0;
	
					if(v.recvTimestamp > 0 && v.sendTimestamp > 0) {
						cost = v.recvTimestamp - v.sendTimestamp;
					}
					
					totalCost += cost;
					
					//耗时分段统计
					if(cost < 50) {
						++less50;
					} else if (cost < 100) {
						++less100;
					} else if (cost < 200) {
						++less200;
					} else if (cost < 400) {
						++less400;
					} else if (cost < 1000) {
						++less1000;
					} else if(cost < 2000) {
						++less2000;
					} else {
						++timeout2000;
					}
				}
	
				System.out.println("总请求量: " + String.valueOf(total));
				System.out.println("消息包大小: " + String.valueOf(packageSize) + " byte");
				System.out.println("请求TPS: " + String.valueOf(tps));
				System.out.println("响应TPS: " + String.valueOf(total / ((double)totalTime / 1000)));
				System.out.println("平均耗时:" + String.valueOf(totalCost / total) + "ms");
				System.out.println("错误率: " + String.valueOf((error.get() / received.get()) * 100) + "%");
				
				System.out.println("分段耗时统计:");
				System.out.println("0    < 耗时 <  50ms   : " + String.valueOf(less50) + " 占比: " + String.valueOf((double)less50 / total * 100) + "%");
				System.out.println("50   < 耗时 <  100ms  : " + String.valueOf(less100) + " 占比: " + String.valueOf((double)less100  / total * 100) + "%");
				System.out.println("100  < 耗时 <  200ms  : " + String.valueOf(less200) + " 占比: " + String.valueOf((double)less200 / total * 100) + "%");
				System.out.println("200  < 耗时 <  400ms  : " + String.valueOf(less400) + " 占比: " + String.valueOf((double)less400 / total * 100) + "%");
				System.out.println("400  < 耗时 <  1000ms : " + String.valueOf(less1000) + " 占比: " + String.valueOf((double)less1000 / total * 100) + "%");
				System.out.println("1000 < 耗时 <  2000ms : " + String.valueOf(less2000) + " 占比: " + String.valueOf((double)less2000 / total * 100) + "%");
				System.out.println("2000 < 耗时           : " + String.valueOf(timeout2000) + " 占比: " + String.valueOf((double)timeout2000 / total * 100) + "%");
			}
		}
		catch(Exception e) {
			logger.error("系统错误:", e);
		}
	}
	
	public Map<Integer, RequestTimer> resultMap;
	public Integer total;
	public AtomicInteger received = new AtomicInteger(0);
	public AtomicInteger error = new AtomicInteger(0);
	public Long startTimestamp = (long)0;
	public Integer tps = 0;
	public Integer packageSize = 0;
}