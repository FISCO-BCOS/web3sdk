package org.fisco.bcos.web3j.console;

import java.util.Scanner;

import org.fisco.bcos.web3j.protocol.channel.ResponseExcepiton;


public class ConsoleClient {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		ConsoleFace console = new ConsoleImpl();
		console.welcome();
		
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String request = sc.nextLine();
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
					console.help();
					break;
				case "getBlockNumber":
				case "gbn":
					console.getBlockNumber();
					break;
				case "getPbftView":
				case "gpv":
					console.getPbftView();
					break;
				case "getMinerList":
				case "gml":
					console.getMinerList();
					break;
				case "getObserverList":
				case "gol":
					console.getObserverList();
					break;
				case "getConsensusStatus":
				case "gcs":
					console.getConsensusStatus();
					break;
				case "getSyncStatus":
				case "gss":
					console.getSyncStatus();
					break;
				case "getClientVersion":
				case "gcv":
					console.getClientVersion();
					break;
				case "getPeers":
				case "gps":
					console.getPeers();
					break;
				case "getGroupPeers":
				case "ggp":
					console.getGroupPeers();
					break;
				case "getGroupList":
				case "ggl":
					console.getGroupList();
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
					console.getPendingTransactions();
					break;
				case "getCode":
				case "gc":
					console.getCode(params);
					break;
				case "getTotalTransactionCount":
				case "gtc":
					console.getTotalTransactionCount();
					break;
				case "deployByCNS":
				case "dbc":
					console.deployByCNS(params);
					break;
				case "callByCNS":
				case "cbc":
					console.callByCNS(params);
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
				case "quit":
				case "q":
					System.exit(0);
				default:
					System.out.println("Undefined command: \""+params[0]+"\".  Try \"help\".\n");
					break;

				}

			} catch (ResponseExcepiton e) {
				System.out.println(
						"{\"error\":{\"code\":" + e.getCode() + ", \"message:\"" + "\"" + e.getMessage() + "\"}}");
				System.out.println();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
			}

		}
	}

}