package org.fisco.bcos.channel.test.dag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

import org.fisco.bcos.channel.test.dag.PerfomanceDTTest;
import org.fisco.bcos.channel.test.dag.DagUserMgr;

public class PerfomanceDT {
	private static Logger logger = LoggerFactory.getLogger(PerfomanceDT.class);

	public static void Usage() {
		System.out.println(" Usage:");
		System.out.println(
				" \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.dag.PerfomanceDT add count tps file.");
		System.out.println(
				" \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.dag.PerfomanceDT transfer count tps file strategy.");
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
		String strategy = null;
		if (args.length > 3) {
			file = args[3];
			if (args.length > 4) {
				strategy = args[4];
			}
		}

		logger.info(" dag transfer test begin, command is {}, count is {}, tps is {}, file is {}, strategy is {}",
				command, tps, file, strategy);

		DagUserMgr d = new DagUserMgr();
		d.setFile(file);
		d.setTransfer(command == "transfer");
		d.loadDagTransferUser();
		
		PerfomanceDTCollector collector = new PerfomanceDTCollector();
		collector.setTotal(count.intValue());
		collector.setDagUserMrg(d);
		
		PerfomanceDTTest p = new PerfomanceDTTest();
		p.setCollector(collector);
		p.setDagUserMgr(d);

		switch (command) {
		case "add":
			p.userAddTest(count, tps);
			break;
		case "transfer":
			p.userTransferTest(count, tps, strategy);
			break;
		default:
			Usage();
		}
	}
}
