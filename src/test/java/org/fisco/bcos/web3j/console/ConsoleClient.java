package org.fisco.bcos.web3j.console;

import java.util.Scanner;

import org.fisco.bcos.web3j.protocol.channel.ResponseExcepiton;

public class ConsoleClient {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		ConsoleFace console = new ConsoleImpl();
		console.init(args);
		console.welcome();

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String request = sc.nextLine().trim().replaceAll(" +", " ");
			String[] params = request.split(" ");
			if (params.length < 1) {
				System.out.print("");
				continue;
			}
			if ("".equals(params[0].trim())) {
				System.out.print("");
				continue;
			}
			try {

				switch (params[0]) {

				case "help":
				case "h":
					console.help(params);
					break;
				case "getBlockNumber":
				case "gbn":
					console.getBlockNumber(params);
					break;
				case "getPbftView":
				case "gpv":
					console.getPbftView(params);
					break;
				case "getMinerList":
				case "gml":
					console.getMinerList(params);
					break;
				case "getObserverList":
				case "gol":
					console.getObserverList(params);
					break;
				case "getConsensusStatus":
				case "gcs":
					console.getConsensusStatus(params);
					break;
				case "getSyncStatus":
				case "gss":
					console.getSyncStatus(params);
					break;
				case "getClientVersion":
				case "gcv":
					console.getClientVersion(params);
					break;
				case "getPeers":
				case "gps":
					console.getPeers(params);
					break;
				case "getGroupPeers":
				case "ggp":
					console.getGroupPeers(params);
					break;
				case "getGroupList":
				case "ggl":
					console.getGroupList(params);
					break;
				case "getBlockByHash":
				case "gbbh":
					console.getBlockByHash(params);
					break;
				case "getBlockByNumber":
				case "gbbn":
					console.getBlockByNumber(params);
					break;
				case "getBlockHashByNumber":
				case "ghbn":
					console.getBlockHashByNumber(params);
					break;
				case "getTransactionByHash":
				case "gtbh":
					console.getTransactionByHash(params);
					break;
				case "getTransactionByBlockHashAndIndex":
				case "gthi":
					console.getTransactionByBlockHashAndIndex(params);
					break;
				case "getTransactionByBlockNumberAndIndex":
				case "gtni":
					console.getTransactionByBlockNumberAndIndex(params);
					break;
				case "getTransactionReceipt":
				case "gtr":
					console.getTransactionReceipt(params);
					break;
				case "getPendingTransactions":
				case "gpt":
					console.getPendingTransactions(params);
					break;
				case "getPendingTxSize":
				case "gpts":
					console.getPendingTxSize(params);
					break;
				case "getCode":
				case "gc":
					console.getCode(params);
					break;
				case "getTotalTransactionCount":
				case "gtc":
					console.getTotalTransactionCount(params);
					break;
				case "deploy":
				case "d":
					console.deploy(params);
					break;
				case "call":
				case "c":
					console.call(params);
					break;
				case "deployByCNS":
				case "dbc":
					console.deployByCNS(params);
					break;
				case "callByCNS":
				case "cbc":
					console.callByCNS(params);
					break;
				case "queryCNS":
				case "qcs":
					console.queryCNS(params);
					break;
				case "addMiner":
				case "am":
					console.addMiner(params);
					break;
				case "addObserver":
				case "ao":
					console.addObserver(params);
					break;
				case "removeNode":
				case "rn":
					console.removeNode(params);
					break;
				case "addAuthority":
				case "aa":
					console.addAuthority(params);
					break;
				case "removeAuthority":
				case "ra":
					console.removeAuthority(params);
					break;
				case "queryAuthority":
				case "qa":
					console.queryAuthority(params);
					break;
				case "setSystemConfigByKey":
				case "ssc":
					console.setSystemConfigByKey(params);
					break;
				case "getSystemConfigByKey":
				case "gsc":
					console.getSystemConfigByKey(params);
					break;
				case "quit":
				case "q":
					console.quit(params);
					break;
				default:
					System.out.println("Undefined command: \"" + params[0] + "\".  Try \"help\".\n");
					break;

				}

			} catch (ResponseExcepiton e) {
				ConsoleUtils.printJson(
						"{\"code\":" + e.getCode() + ", \"msg\":" + "\"" + e.getMessage() + "\"}");
				System.out.println();
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage()+" does not exist.");
				System.out.println();
			}catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
			}

		}
	}

}