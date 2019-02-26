package org.fisco.bcos.web3j.console;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.channel.ResponseExcepiton;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.jline.builtins.Completers.FilesCompleter;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

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
      completers.add(
          new ArgumentCompleter(new StringsCompleter("getTransactionByBlockHashAndIndex")));
      completers.add(
          new ArgumentCompleter(new StringsCompleter("getTransactionByBlockNumberAndIndex")));
      completers.add(new ArgumentCompleter(new StringsCompleter("getTransactionReceipt")));
      completers.add(new ArgumentCompleter(new StringsCompleter("getPendingTransactions")));
      completers.add(new ArgumentCompleter(new StringsCompleter("getPendingTxSize")));
      completers.add(new ArgumentCompleter(new StringsCompleter("getCode")));
      completers.add(new ArgumentCompleter(new StringsCompleter("getTotalTransactionCount")));
      Path path = FileSystems.getDefault().getPath("solidity/contracts/", "");
      completers.add(
          new ArgumentCompleter(new StringsCompleter("deploy"), new FilesCompleter(path)));
      completers.add(new ArgumentCompleter(new StringsCompleter("call"), new FilesCompleter(path)));
      completers.add(
          new ArgumentCompleter(new StringsCompleter("deployByCNS"), new FilesCompleter(path)));
      completers.add(
          new ArgumentCompleter(new StringsCompleter("callByCNS"), new FilesCompleter(path)));
      completers.add(
          new ArgumentCompleter(new StringsCompleter("queryCNS"), new FilesCompleter(path)));
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

      Terminal terminal = TerminalBuilder.terminal();
      lineReader =
          LineReaderBuilder.builder()
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
      if ("quit".equals(params[0])) {
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
            console.help(params);
            break;
          case "getBlockNumber":
            console.getBlockNumber(params);
            break;
          case "getPbftView":
            console.getPbftView(params);
            break;
          case "getSealerList":
            console.getSealerList(params);
            break;
          case "getObserverList":
            console.getObserverList(params);
            break;
          case "getConsensusStatus":
            console.getConsensusStatus(params);
            break;
          case "getSyncStatus":
            console.getSyncStatus(params);
            break;
          case "getClientVersion":
            console.getClientVersion(params);
            break;
          case "getPeers":
            console.getPeers(params);
            break;
          case "getNodeIDList":
            console.getNodeIDList(params);
            break;
          case "getGroupPeers":
            console.getGroupPeers(params);
            break;
          case "getGroupList":
            console.getGroupList(params);
            break;
          case "getBlockByHash":
            console.getBlockByHash(params);
            break;
          case "getBlockByNumber":
            console.getBlockByNumber(params);
            break;
          case "getBlockHashByNumber":
            console.getBlockHashByNumber(params);
            break;
          case "getTransactionByHash":
            console.getTransactionByHash(params);
            break;
          case "getTransactionByBlockHashAndIndex":
            console.getTransactionByBlockHashAndIndex(params);
            break;
          case "getTransactionByBlockNumberAndIndex":
            console.getTransactionByBlockNumberAndIndex(params);
            break;
          case "getTransactionReceipt":
            console.getTransactionReceipt(params);
            break;
          case "getPendingTransactions":
            console.getPendingTransactions(params);
            break;
          case "getPendingTxSize":
            console.getPendingTxSize(params);
            break;
          case "getCode":
            console.getCode(params);
            break;
          case "getTotalTransactionCount":
            console.getTotalTransactionCount(params);
            break;
          case "deploy":
            console.deploy(params);
            break;
          case "call":
            console.call(params);
            break;
          case "deployByCNS":
            console.deployByCNS(params);
            break;
          case "callByCNS":
            console.callByCNS(params);
            break;
          case "queryCNS":
            console.queryCNS(params);
            break;
          case "addSealer":
            console.addSealer(params);
            break;
          case "addObserver":
            console.addObserver(params);
            break;
          case "removeNode":
            console.removeNode(params);
            break;
          case "addUserTableManager":
            console.addUserTableManager(params);
            break;
          case "removeUserTableManager":
            console.removeUserTableManager(params);
            break;
          case "queryUserTableManager":
            console.queryUserTableManager(params);
            break;
          case "addDeployAndCreateManager":
            console.addDeployAndCreateManager(params);
            break;
          case "removeDeployAndCreateManager":
            console.removeDeployAndCreateManager(params);
            break;
          case "queryDeployAndCreateManager":
            console.queryDeployAndCreateManager(params);
            break;
          case "addAuthorityManager":
            console.addAuthorityManager(params);
            break;
          case "removeAuthorityManager":
            console.removeAuthorityManager(params);
            break;
          case "queryAuthorityManager":
            console.queryAuthorityManager(params);
            break;
          case "addNodeManager":
            console.addNodeManager(params);
            break;
          case "removeNodeManager":
            console.removeNodeManager(params);
            break;
          case "queryNodeManager":
            console.queryNodeManager(params);
            break;
          case "addCNSManager":
            console.addCNSManager(params);
            break;
          case "removeCNSManager":
            console.removeCNSManager(params);
            break;
          case "queryCNSManager":
            console.queryCNSManager(params);
            break;
          case "addSysConfigManager":
            console.addSysConfigManager(params);
            break;
          case "removeSysConfigManager":
          case "rsm":
            console.removeSysConfigManager(params);
            break;
          case "querySysConfigManager":
            console.querySysConfigManager(params);
            break;
          case "setSystemConfigByKey":
            console.setSystemConfigByKey(params);
            break;
          case "getSystemConfigByKey":
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
        System.out.println(e.getMessage() + " does not exist.");
        System.out.println();
      } catch (IOException e) {
        if (e.getMessage().startsWith("activeConnections")) {
          System.out.println("Please check the connection between sdk to node.");
        } else if (e.getMessage().startsWith("No value")) {
          System.out.println(
              "The groupID is not configured in dist/conf/applicationContext.xml file.");
        } else {
          System.out.println(e.getMessage());
        }
        System.out.println();
      } catch (RuntimeException e) {
        String message = e.getMessage();
        Response t = null;
        try {
          t =
              ObjectMapperFactory.getObjectMapper(true)
                  .readValue(
                      message.substring(message.indexOf("{"), message.lastIndexOf("}") + 1),
                      Response.class);
          if (t != null) {
            ConsoleUtils.printJson(
                "{\"code\":"
                    + t.getError().getCode()
                    + ", \"msg\":"
                    + "\""
                    + t.getError().getMessage()
                    + "\"}");
            System.out.println();
          }
        } catch (Exception e1) {
          System.out.println(e1.getMessage());
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println();
      }
    }
  }
}
