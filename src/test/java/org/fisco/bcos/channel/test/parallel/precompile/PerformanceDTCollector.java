package org.fisco.bcos.channel.test.parallel.precompile;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.channel.test.parallel.precompile.DagUserMgr;

public class PerformanceDTCollector {

	static Logger logger = LoggerFactory.getLogger(PerformanceDTCollector.class);

	private Integer total = 0;
	private DagUserMgr dagUserMrg;
	private PerformanceDTTest PerformanceDTTest;

	public PerformanceDTTest getPerformanceDTTest() {
		return PerformanceDTTest;
	}

	public void setPerformanceDTTest(PerformanceDTTest PerformanceDTTest) {
		this.PerformanceDTTest = PerformanceDTTest;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public DagUserMgr getDagUserMrg() {
		return dagUserMrg;
	}

	public void setDagUserMrg(DagUserMgr dagUserMrg) {
		this.dagUserMrg = dagUserMrg;
	}

	public boolean isEnd() {
		return received.intValue() >= total;
	}

	public void onMessage(TransactionReceipt receipt, Long cost) {
		try {
			if (receipt.isStatusOK()) {
				String output = receipt.getOutput();
				if (!output.isEmpty()) {
					int code = new BigInteger(output.substring(2, output.length()), 16).intValue();
					if (0 != code) {
						error.addAndGet(1);
						ret_error.addAndGet(1);
					}
				} else {
					error.addAndGet(1);
				}
			} else {
				error.addAndGet(1);
			}

			received.incrementAndGet();

			if ((received.get() + 1) % (total / 10) == 0) {
				System.out.println("                                                       |received:"
						+ String.valueOf((received.get() + 1) * 100 / total) + "%");
			}

			if (cost < 50) {
				less50.incrementAndGet();
			} else if (cost < 100) {
				less100.incrementAndGet();
				;
			} else if (cost < 200) {
				less200.incrementAndGet();
				;
			} else if (cost < 400) {
				less400.incrementAndGet();
				;
			} else if (cost < 1000) {
				less1000.incrementAndGet();
				;
			} else if (cost < 2000) {
				less2000.incrementAndGet();
				;
			} else {
				timeout2000.incrementAndGet();
				;
			}

			totalCost.addAndGet(cost);

			if (isEnd()) {
				System.out.println("total");

				//
				Long totalTime = System.currentTimeMillis() - startTimestamp;

				System.out.println("===================================================================");

				System.out.println("Total transactions:  " + String.valueOf(total));
				System.out.println("Total time: " + String.valueOf(totalTime) + "ms");
				System.out.println("TPS: " + String.valueOf(total / ((double) totalTime / 1000)));
				System.out.println("Avg time cost: " + String.valueOf(totalCost.get() / total) + "ms");
				System.out.println("Error rate: " + String.valueOf((error.get() / received.get()) * 100) + "%");
				System.out.println(
						"Return Error rate: " + String.valueOf((ret_error.get() / received.get()) * 100) + "%");

				System.out.println("Time area:");
				System.out.println("0    < time <  50ms   : " + String.valueOf(less50) + "  : "
						+ String.valueOf((double) less50.get() / total * 100) + "%");
				System.out.println("50   < time <  100ms  : " + String.valueOf(less100) + "  : "
						+ String.valueOf((double) less100.get() / total * 100) + "%");
				System.out.println("100  < time <  200ms  : " + String.valueOf(less200) + "  : "
						+ String.valueOf((double) less200.get() / total * 100) + "%");
				System.out.println("200  < time <  400ms  : " + String.valueOf(less400) + "  : "
						+ String.valueOf((double) less400.get() / total * 100) + "%");
				System.out.println("400  < time <  1000ms : " + String.valueOf(less1000) + "  : "
						+ String.valueOf((double) less1000.get() / total * 100) + "%");
				System.out.println("1000 < time <  2000ms : " + String.valueOf(less2000) + "  : "
						+ String.valueOf((double) less2000.get() / total * 100) + "%");
				System.out.println("2000 < time           : " + String.valueOf(timeout2000) + "  : "
						+ String.valueOf((double) timeout2000.get() / total * 100) + "%");
			}

		} catch (Exception e) {
			logger.error("error:", e);
			System.exit(0);
		}
	}

	private AtomicLong less50 = new AtomicLong(0);
	private AtomicLong less100 = new AtomicLong(0);
	private AtomicLong less200 = new AtomicLong(0);
	private AtomicLong less400 = new AtomicLong(0);
	private AtomicLong less1000 = new AtomicLong(0);
	private AtomicLong less2000 = new AtomicLong(0);
	private AtomicLong timeout2000 = new AtomicLong(0);
	private AtomicLong totalCost = new AtomicLong(0);

	private AtomicInteger received = new AtomicInteger(0);

	public AtomicInteger getReceived() {
		return received;
	}

	public void setReceived(AtomicInteger received) {
		this.received = received;
	}

	private AtomicInteger error = new AtomicInteger(0);
	private AtomicInteger ret_error = new AtomicInteger(0);
	private Long startTimestamp = System.currentTimeMillis();

	public Long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(Long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
}
