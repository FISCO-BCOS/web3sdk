package org.fisco.bcos.web3j.precompile.dag2;

import static java.lang.System.exit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize;
/*
contract DagTransfer{
    function userAdd(string user, uint256 balance) public returns(bool);
    function userSave(string user, uint256 balance) public returns(bool);
    function userDraw(string user, uint256 balance) public returns(bool);
    function userBalance(string user) public constant returns(bool,uint256);
    function userTransfer(string user_a, string user_b, uint256 amount) public returns(bool);
}
*/

public class DagTransferTools {

	private static Logger logger = LoggerFactory.getLogger(DagTransferTools.class);
	private DagTransferService dts;

	public void setDts(DagTransferService dts) {
		this.dts = dts;
	}

	public DagTransferService getDtf() {
		return this.dts;
	}

	public static void usage() {
		System.out.println(" DagTransferTools Usage: ");
		System.out.println("\t DagTransferTools userAdd  user amount");
		System.out.println("\t DagTransferTools userSave  user amount");
		System.out.println("\t DagTransferTools userDraw user amount");
		System.out.println("\t DagTransferTools userBalance user");
		System.out.println("\t DagTransferTools userTransfer from_user to_user amount");
	}

	public static void processDT(String[] args, DagTransferTools dtt)throws Exception {
		String command = args[0];

		switch (command) {
		case "userAdd": {
			if (args.length < 4) {
				System.out.println("userAdd invalid args length.");
				usage();
				return;
			}

			String user = args[2];
			BigInteger amount = new BigInteger(args[3]);
			dtt.getDtf().userAdd(user, amount);
			System.out.println(" userAdd end ");
			break;
		}

		case "userSave": {
			if (args.length < 4) {
				System.out.println("userSave invalid args length.");
				usage();
				return;
			}

			String user = args[2];
			BigInteger amount = new BigInteger(args[3]);
			dtt.getDtf().userSave(user, amount);
			System.out.println(" userSave end ");
			break;
		}

		case "userDraw": {
			if (args.length < 4) {
				System.out.println("userDraw invalid args length.");
				usage();
				return;
			}

			String user = args[2];
			BigInteger amount = new BigInteger(args[3]);
			dtt.getDtf().userDraw(user, amount);
			System.out.println(" userDraw end ");
			break;
		}
		case "userBalance": {
			if (args.length < 3) {
				System.out.println("userBalance invalid args length.");
				usage();
				return;
			}

			String user = args[2];
			BigInteger amount = new BigInteger(args[3]);
			dtt.getDtf().userDraw(user, amount);
			System.out.println(" userBalance end ");
			break;
		}

		case "userTransfer": {
			if (args.length < 5) {
				System.out.println("userTransfer invalid args length.");
				usage();
				return;
			}

			String from = args[2];
			String to = args[3];
			BigInteger amount = new BigInteger(args[4]);
			dtt.getDtf().userTransfer(from, to, amount);
			System.out.println(" userTransfer end ");
			break;
		}
		default:
			System.out.println(" unkown cmd. ");
			usage();
		}
	}

	public static void main(String[] args) throws Exception {

		String groupId = "1";

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.setGroupId(Integer.parseInt(groupId));
		service.run();

		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);

		Web3j web3 = Web3j.build(channelEthereumService, 15 * 100, Executors.newScheduledThreadPool(500),
				Integer.parseInt(groupId));

		DagTransferService dts = new DagTransferService(web3, Credentials.create(Keys.createEcKeyPair()));
		DagTransferTools dtt = new DagTransferTools();
		dtt.setDts(dts);
		processDT(args, dtt);

		exit(0);

	}

}
