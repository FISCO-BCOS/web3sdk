package org.fisco.bcos.channel.test.contract;

import com.google.common.util.concurrent.RateLimiter;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.exit;



public class PerfomanceOk {
	private static Logger logger = LoggerFactory.getLogger(PerfomanceOk.class);
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

		Web3AsyncThreadPoolSize.web3AsyncCorePoolSize = 3000;
		Web3AsyncThreadPoolSize.web3AsyncPoolSize = 2000;

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
		Integer startNum = 0;

		switch (command) {
			case "trans":
				count = Integer.parseInt(args[1]);
				qps = Integer.parseInt(args[2]);
				break;
			default:
				System.out.println("参数: <trans> <请求总数> <QPS>");
		}

		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
		threadPool.setCorePoolSize(3000);
		threadPool.setMaxPoolSize(6000);
		threadPool.setQueueCapacity(count);
		
		threadPool.initialize();
		
		System.out.println("部署合约");
		Ok ok = Ok.deploy(web3, credentials, gasPrice, gasLimit).send();



		PerfomanceOkCallback callback = new PerfomanceOkCallback();
		callback.setTotal(count);
		
		RateLimiter limiter = RateLimiter.create(qps);
		Integer area = count / 10;

		System.out.println("开始压测，总交易量：" + count);
		List<CompletableFuture> threadArray = Collections.synchronizedList(new ArrayList<CompletableFuture>());
		Long currentTime = System.currentTimeMillis();
		Long begintime = System.currentTimeMillis();

		for (Integer i = 0; i < count; ++i) {
			final Integer seq = i;
			final Integer total = count;
			final Integer start = startNum;

			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					limiter.acquire();

							try {
								CompletableFuture<TransactionReceipt> future = ok.trans(new BigInteger("4")).sendAsync();
								threadArray.add(future);
							} catch (Exception e) {
								logger.info(e.getMessage());
							}

							int current = sended.incrementAndGet();

							if (current >= area && ((current % area) == 0)) {
								System.out.println("已发送: " + current + "/" + total + " 交易");
								//	System.out.println("耗时 ms" + Long.toString(System.currentTimeMillis() - currentTime));
							}
					}


			});
		}
	int count1 = 1;
	int sum =0 ;
	int j=0;
	int previous;
		while(threadArray.size()>0) {
			for (int i = 0; i < threadArray.size(); i++) {
				previous = count1;
				if (threadArray.get(i).isDone()) {
					threadArray.remove(threadArray.get(i));
					count1++;
				}

			if(count1 % qps == 0 &&  previous != count1) {
				Long time = System.currentTimeMillis() - currentTime;

				double tps = qps/(time/1000.0);
				if(tps<2000 && tps>200) {
					sum+=tps;
					j=j+1;
					System.out.println(qps+"笔交易耗时 ms" + time.toString());
					System.out.println("此阶段tps 是" + (double)Math.round(tps*100)/100);
				}

				currentTime= System.currentTimeMillis();
			}

			}

		}
		System.out.println("已收到交易 " + (count1-1));
		Long time1 = System.currentTimeMillis() - begintime;
		double finaltps =  count/(time1/1000.0);
		System.out.println("***平均tps*** 是 " +(double)Math.round(finaltps*100)/100);
		System.out.println("***总耗时是***" + (System.currentTimeMillis()-begintime));
		exit(0);

	}
}
