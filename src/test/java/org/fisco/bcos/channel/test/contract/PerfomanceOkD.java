package org.fisco.bcos.channel.test.contract;

import com.google.common.util.concurrent.RateLimiter;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class PerfomanceOkD {
	private static Logger logger = LoggerFactory.getLogger(PerfomanceOkD.class);
	private static AtomicInteger sended = new AtomicInteger(0);
	
	public static void main(String[] args) throws Exception {
		String groupId = args[3];
		//初始化Service
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.setGroupId(Integer.parseInt(groupId));
		service.run();

		System.out.println("开始测试...");
		System.out.println("===================================================================");
		
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);

		ScheduledExecutorService scheduledExecutorService =
				Executors.newScheduledThreadPool(500);
		Web3j web3 = Web3j.build(channelEthereumService,  15 * 100, scheduledExecutorService, Integer.parseInt(groupId));

		//初始化交易签名私钥
		Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

		//初始化交易参数
		BigInteger gasPrice = new BigInteger("30000000");
		BigInteger gasLimit = new BigInteger("30000000");
		
		//解析参数
		String command = args[0];
		Integer count = 0;
		Integer qps = 0;

		switch (command) {
			case "trans":
				count = Integer.parseInt(args[1]);
				qps = Integer.parseInt(args[2]);
				break;
			default:
				System.out.println("参数: <trans> <请求总数> <QPS>");
		}

		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(200);
		threadPool.setMaxPoolSize(500);
		threadPool.setQueueCapacity(count);
		
		threadPool.initialize();
		
		System.out.println("部署合约");
		OkD ok = OkD.deploy(web3, credentials, gasPrice, gasLimit).send();

		PerfomanceCollector collector = new PerfomanceCollector();
		collector.setTotal(count);

		RateLimiter limiter = RateLimiter.create(qps);
		Integer area = count / 10;
		final Integer total = count;

		System.out.println("开始压测，总交易量：" + count);
		for (Integer i = 0; i < count; ++i) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					limiter.acquire();
					try {
						PerfomanceOkCallback callback = new PerfomanceOkCallback();
						callback.setCollector(collector);
						ok.trans("0x1", new BigInteger("1"), callback);
					} catch (Exception e) {
						logger.info(e.getMessage());
					}

					int current = sended.incrementAndGet();

					if (current >= area && ((current % area) == 0)) {
						System.out.println("已发送: " + current + "/" + total + " 交易");
					}
				}
			});
		}
	}
}
