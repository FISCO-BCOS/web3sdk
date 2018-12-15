package org.fisco.bcos.channel.test.contract;

import com.google.common.util.concurrent.RateLimiter;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class PerfomanceOk {
	static Logger logger = LoggerFactory.getLogger(PerfomanceOk.class);
	private static AtomicInteger sended = new AtomicInteger(0);
	
	public static void main(String[] args) throws Exception {
		String groupId = args[3];
		//初始化Service
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();

		System.out.println("开始测试...");
		System.out.println("===================================================================");
		
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		
		Web3j web3 = Web3j.build(channelEthereumService,Integer.parseInt(groupId));

		//初始化交易签名私钥
		Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

		//ECKeyPair keyPair = Keys.createEcKeyPair();
		//Credentials credentials = Credentials.create(keyPair);

		//初始化交易参数
		BigInteger gasPrice = new BigInteger("30000000");
		BigInteger gasLimit = new BigInteger("30000000");
		BigInteger initialWeiValue = new BigInteger("0");
		
		//解析参数
		String command = args[0];
		Integer count = 0;
		Integer qps = 0;
		Integer startNum = 0;
		
		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(100);
		threadPool.setMaxPoolSize(500);
		threadPool.setQueueCapacity(1000000);
		
		threadPool.initialize();
		
		System.out.println("部署合约");
		Ok ok = Ok.deploy(web3, credentials, gasPrice, gasLimit, initialWeiValue).send();

		switch (command) {
		case "trans":
			count = Integer.parseInt(args[1]);
			qps = Integer.parseInt(args[2]);

			break;
		default:
			System.out.println("参数: <trans> <请求总数> <QPS>");
		}

		PerfomanceOkCallback callback = new PerfomanceOkCallback();
		callback.setTotal(count);
		
		RateLimiter limiter = RateLimiter.create(qps);
		Integer area = count / 10;

		System.out.println("开始压测，总交易量：" + count);
		for (Integer i = 0; i < count; ++i) {
			final Integer seq = i;
			final Integer total = count;
			final Integer start = startNum;

			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					limiter.acquire();

					Long currentTime = System.currentTimeMillis();
					switch (command) {
					case "trans":
						String userName = String.valueOf("User " + String.valueOf(seq + start));
						try {
							ok.trans(new BigInteger("4")).send();
							System.out.println("耗时 ms" + Long.toString(System.currentTimeMillis() - currentTime));
						} catch (Exception e) {
							logger.info(e.getMessage());
						}

						break;
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
