package org.fisco.bcos.web3j.console;

import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.channel.ResponseExcepiton;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.builtins.Completers.FileNameCompleter;
import org.jline.reader.Completer;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleClient {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ConsoleFace console = new ConsoleImpl();
		console.init(args);
		console.welcome();
		
		LineReader lineReader = null;
		try {
			List<Completer> completers = new ArrayList<Completer>();
			completers.add(new ArgumentCompleter(new StringsCompleter("help")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getBlockNumber")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getPbftView")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getSealerList")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getConsensusStatus")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getSyncStatus")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getClientVersion")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getPeers")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getNodeIDList")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getGroupPeers")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getGroupList")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getBlockByHash")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getBlockByNumber")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getBlockHashByNumber")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getTransactionByHash")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getTransactionByBlockHashAndIndex")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getTransactionByBlockNumberAndIndex")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getTransactionReceipt")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getPendingTransactions")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getPendingTxSize")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getCode")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getTotalTransactionCount")));
			completers.add(new ArgumentCompleter(new StringsCompleter("call")));
			completers.add(new ArgumentCompleter(new StringsCompleter("deploy"), new FileNameCompleter()));
			completers.add(new ArgumentCompleter(new StringsCompleter("deployByCNS"), new FileNameCompleter()));
			completers.add(new ArgumentCompleter(new StringsCompleter("callByCNS")));
			completers.add(new ArgumentCompleter(new StringsCompleter("queryCNS")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addSealer")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addObserver")));
			completers.add(new ArgumentCompleter(new StringsCompleter("removeNode")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addUserTableManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("removeUserTableManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("queryUserTableManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addDeployAndCreateManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("removeDeployAndCreateManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("queryDeployAndCreateManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addAuthorityManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("removeAuthorityManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("queryAuthorityManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addNodeManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("removeNodeManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("queryNodeManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addCNSManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("removeCNSManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("queryCNSManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("addSysConfigManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("removeSysConfigManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("querySysConfigManager")));
			completers.add(new ArgumentCompleter(new StringsCompleter("setSystemConfigByKey")));
			completers.add(new ArgumentCompleter(new StringsCompleter("getSystemConfigByKey")));
			completers.add(new ArgumentCompleter(new StringsCompleter("quit")));
			
			completers.add(new ArgumentCompleter(new StringsCompleter("deploy"), new FileNameCompleter()));
			
			Terminal terminal = TerminalBuilder.terminal();
			lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new AggregateCompleter(completers))
                    .build();
		} catch (IOException e2) {
			e2.printStackTrace();
			console.close();
		}
		
		while (true) {
			String request = lineReader.readLine(">").trim().replaceAll(" +", " ");
			String[] params = request.split(" ");
			if (params.length < 1) {
				System.out.print("");
				continue;
			}
			if ("".equals(params[0].trim())) {
				System.out.print("");
				continue;
			}
			if("quit".equals(params[0]) || "q".equals(params[0]))
			{	
				if (HelpInfo.promptNoParams(params, "q")) {
					continue;
				} else if (params.length > 2) {
					HelpInfo.promptHelp("q");
					continue;
				}
				console.close();
				break;
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
				case "getSealerList":
				case "gsl":
					console.getSealerList(params);
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
				case "getNodeIDList":
				case "gnl":
					console.getNodeIDList(params);
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
				case "addSealer":
				case "as":
					console.addSealer(params);
					break;
				case "addObserver":
				case "ao":
					console.addObserver(params);
					break;
				case "removeNode":
				case "rn":
					console.removeNode(params);
					break;
				case "addUserTableManager":
				case "aum":
					console.addUserTableManager(params);
					break;
				case "removeUserTableManager":
				case "rum":
					console.removeUserTableManager(params);
					break;
				case "queryUserTableManager":
				case "qum":
					console.queryUserTableManager(params);
					break;
				case "addDeployAndCreateManager":
				case "adm":
					console.addDeployAndCreateManager(params);
					break;
				case "removeDeployAndCreateManager":
				case "rdm":
					console.removeDeployAndCreateManager(params);
					break;
				case "queryDeployAndCreateManager":
				case "qdm":
					console.queryDeployAndCreateManager(params);
					break;
				case "addAuthorityManager":
				case "aam":
					console.addAuthorityManager(params);
					break;
				case "removeAuthorityManager":
				case "ram":
					console.removeAuthorityManager(params);
					break;
				case "queryAuthorityManager":
				case "qam":
					console.queryAuthorityManager(params);
					break;
				case "addNodeManager":
				case "anm":
					console.addNodeManager(params);
					break;
				case "removeNodeManager":
				case "rnm":
					console.removeNodeManager(params);
					break;
				case "queryNodeManager":
				case "qnm":
					console.queryNodeManager(params);
					break;
				case "addCNSManager":
				case "acm":
					console.addCNSManager(params);
					break;
				case "removeCNSManager":
				case "rcm":
					console.removeCNSManager(params);
					break;
				case "queryCNSManager":
				case "qcm":
					console.queryCNSManager(params);
					break;
				case "addSysConfigManager":
				case "asm":
					console.addSysConfigManager(params);
					break;
				case "removeSysConfigManager":
				case "rsm":
					console.removeSysConfigManager(params);
					break;
				case "querySysConfigManager":
				case "qsm":
					console.querySysConfigManager(params);
					break;
				case "setSystemConfigByKey":
				case "ssc":
					console.setSystemConfigByKey(params);
					break;
				case "getSystemConfigByKey":
				case "gsc":
					console.getSystemConfigByKey(params);
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
			} catch (IOException e) {
				if(e.getMessage().startsWith("activeConnections"))
				{
					System.out.println("Please check the connection between sdk to node.");
				}
				else if(e.getMessage().startsWith("No value"))
				{
					System.out.println("The groupID is not configured in dist/conf/applicationContext.xml file.");
				}
				else
				{
					System.out.println(e.getMessage());
				}
				System.out.println();
			}catch (RuntimeException e) {
				String message = e.getMessage();
				Response t = null;
				try {
					t = ObjectMapperFactory.getObjectMapper(true).readValue(message.substring(message.indexOf("{"), message.lastIndexOf("}") + 1), Response.class);
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				ConsoleUtils.printJson(
						"{\"code\":" + t.getError().getCode() + ", \"msg\":" + "\"" + t.getError().getMessage() + "\"}");
				System.out.println();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
			}

		}
	}
}