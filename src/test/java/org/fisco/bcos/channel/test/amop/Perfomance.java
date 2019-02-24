// package org.fisco.bcos.channel.test;
//
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
//
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.support.ClassPathXmlApplicationContext;
//
// import com.google.common.util.concurrent.RateLimiter;
//
// import org.fisco.bcos.channel.client.Service;
// import org.fisco.bcos.channel.dto.ChannelRequest;
//
// public class Perfomance {
//
//	public static void main(String[] args) throws Exception {
//		if(args.length < 6) {
//			System.out.println("Param: request         receive         total;          TPS     package size
//          timeout");
//			return;
//		}
//		String from = args[0];
//		String to = args[1];
//		Integer count = Integer.parseInt(args[2]);
//		Integer tps = Integer.parseInt(args[3]);
//		Integer packageSize = Integer.parseInt(args[4]);
//		Integer timeout = Integer.parseInt(args[5]);
//
//		ApplicationContext context = new
// ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//
//		Service service = context.getBean(Service.class);
//		service.setPushCallback(new PushCallback());
//		service.run();
//
//		System.out.println("3s...");
//		Thread.sleep(1000);
//		System.out.println("2s...");
//		Thread.sleep(1000);
//		System.out.println("1s...");
//		Thread.sleep(1000);
//
//		System.out.println("start");
//		System.out.println("===================================================================");
//
//		ChannelRequest request = new ChannelRequest();
//
//		request.setAppName("");
//		request.setBankNO("");
//
//		request.setFromOrg(from);
//		request.setOrgApp("");
//		request.setTimeout(0);
//		request.setToOrg(to);
//		request.setTimeout(timeout);
//		request.setVersion("");
//
//		String message = "";
//		for(Integer i = 0; i < packageSize; ++i) {
//			message += "z";
//		}
//
//		Map<Integer, RequestTimer> resultMap = new ConcurrentHashMap<Integer, RequestTimer>();
//
//		PerfomanceCollector collector = new PerfomanceCollector();
//		collector.total = count;
//		collector.resultMap = resultMap;
//		collector.startTimestamp = System.currentTimeMillis();
//		collector.tps = tps;
//		collector.packageSize = packageSize;
//
//		RateLimiter limiter = RateLimiter.create((double)tps);
//
//		for (Integer seq = 0; seq < count; ++seq) {
//			limiter.acquire();
//			if((seq + 1) % (count / 10) == 0) {
//				System.out.println("send:" + String.valueOf((seq + 1) * 100 / count) + "%");
//			}
//
//			request.setContent(message);
//			request.setMessageID(service.newSeq());
//
//			RequestTimer timer = new RequestTimer();
//			timer.sendTimestamp = System.currentTimeMillis();
//
//			resultMap.put(seq, timer);
//
//			PerfomanceCallback callback = new PerfomanceCallback();
//			callback.collector = collector;
//
//			service.asyncSendChannelMessage2(request);
//		}
//
//		System.out.println("total :" + String.valueOf(count) + " ");
//	}
// }
