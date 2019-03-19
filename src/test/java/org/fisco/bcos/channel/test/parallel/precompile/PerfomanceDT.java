package org.fisco.bcos.channel.test.parallel.precompile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

import org.fisco.bcos.channel.test.parallel.precompile.PerfomanceDTTest;
import org.fisco.bcos.channel.test.parallel.precompile.DagUserMgr;

public class PerfomanceDT {
	private static Logger logger = LoggerFactory.getLogger(PerfomanceDT.class);

	public static void Usage() {
		System.out.println(" Usage:");
		System.out.println(
				" \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.parallel.precompile.PerfomanceDT add count tps file.");
		System.out.println(
				" \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.parallel.precompile.PerfomanceDT transfer count tps file strategy.");
		System.exit(0);
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			Usage();
		}

		String command = args[0];
		BigInteger count = new BigInteger(args[1]);
		BigInteger tps = new BigInteger(args[2]);
		String file = null;
		BigInteger deci = new BigInteger("0");
		if (args.length > 3) {
			file = args[3];
			if (args.length > 4) {
				deci = new BigInteger(args[4]);
			}
		}

		// deci can not bigger than 10.
		if (deci.compareTo(new BigInteger("10")) > 0) {
			deci = new BigInteger("10");
		}

		logger.info(" dag transfer test begin, command is {}, count is {}, tps is {}, file is {}, deci is {}", command,
				count, tps, file, deci);

		DagUserMgr d = new DagUserMgr();
		d.setFile(file);

		PerfomanceDTCollector collector = new PerfomanceDTCollector();
		collector.setTotal(count.intValue());
		collector.setDagUserMrg(d);

		PerfomanceDTTest perfomanceDTTest = new PerfomanceDTTest();
		perfomanceDTTest.setCollector(collector);
		perfomanceDTTest.setDagUserMgr(d);
		collector.setPerfomanceDTTest(perfomanceDTTest);

		switch (command) {
		case "add":
			d.setTestType("add");
			perfomanceDTTest.userAddTest(count, tps);
			break;
		case "transfer":
			d.setTestType("transfer");
			d.loadDagTransferUser();
			perfomanceDTTest.userTransferTest(count, tps, deci);
			break;
		default:
			Usage();
		}
	}
}
