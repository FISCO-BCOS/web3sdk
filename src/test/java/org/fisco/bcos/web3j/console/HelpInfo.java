package org.fisco.bcos.web3j.console;

public class HelpInfo {

  public static void promptHelp(String command) {
    System.out.println("Try '" + command + " -h or --help' for more information.");
    System.out.println();
  }

  public static boolean promptNoParams(String[] params, String funcName) {
    if (params.length == 2) {
      if ("-h".equals(params[1]) || "--help".equals(params[1])) {
        helpNoParams(funcName);
        return true;
      } else {
        promptHelp(funcName);
        return true;
      }
    } else if (params.length > 2) {
      promptHelp(funcName);
      return true;
    } else {
      return false;
    }
  }

  public static void helpNoParams(String func) {
    switch (func) {
      case "h":
        help();
        break;
      case "gbn":
        getBlockNumberHelp();
        break;
      case "gpv":
        getPbftViewHelp();
        break;
      case "gol":
        getObserverListHelp();
        break;
      case "gsl":
        getSealerListHelp();
        break;
      case "gcs":
        getConsensusStatusHelp();
        break;
      case "gss":
        getSyncStatusHelp();
        break;
      case "gcv":
        getClientVersionHelp();
        break;
      case "gps":
        getPeersHelp();
        break;
      case "gnl":
        getNodeIDListHelp();
        break;
      case "ggp":
        getGroupPeersHelp();
        break;
      case "ggl":
        getGroupListHelp();
        break;
      case "gpt":
        getPendingTransactionsHelp();
        break;
      case "gpts":
        getPendingTxSizeHelp();
        break;
      case "gtc":
        getTotalTransactionCountHelp();
        break;
      case "qdm":
        listDeployAndCreateManagerHelp();
        break;
      case "qpm":
    	listPermissionManagerHelp();
        break;
      case "qnm":
        listNodeManagerHelp();
        break;
      case "qcm":
        listCNSManagerHelp();
        break;
      case "qsm":
        listSysConfigManagerHelp();
        break;
      case "q":
        quitHelp();
        break;

      default:
        break;
    }
    System.out.println();
  }

  public static void help() {
    System.out.println("Provide help information.");
    System.out.println("Usage: help");
  }

  public static void getBlockNumberHelp() {
    System.out.println("Query the number of most recent block.");
    System.out.println("Usage: getBlockNumber");
  }

  public static void getPbftViewHelp() {
    System.out.println("Query the pbft view of node.");
    System.out.println("Usage: getPbftView");
  }

  public static void getObserverListHelp() {
    System.out.println("Query nodeID list for observer nodes.");
    System.out.println("Usage: getObserverList");
  }

  public static void getSealerListHelp() {
    System.out.println("Query nodeID list for sealer nodes.");
    System.out.println("Usage: getSealerList");
  }

  public static void getConsensusStatusHelp() {
    System.out.println("Query consensus status.");
    System.out.println("Usage: getConsensusStatus");
  }

  public static void getSyncStatusHelp() {
    System.out.println("Query sync status.");
    System.out.println("Usage: getSyncStatus");
  }

  public static void getClientVersionHelp() {
    System.out.println("Query the current client version.");
    System.out.println("Usage: getClientVersion");
  }

  public static void getPeersHelp() {
    System.out.println("Query peers currently connected to the client.");
    System.out.println("Usage: getPeers");
  }

  public static void getNodeIDListHelp() {
    System.out.println("Query nodeID list for all connected nodes.");
    System.out.println("Usage: getNodeIDList");
  }

  public static void getGroupPeersHelp() {
    System.out.println("Query nodeID list for sealer and observer nodes.");
    System.out.println("Usage: getGroupPeers");
  }

  public static void getGroupListHelp() {
    System.out.println("Query group list.");
    System.out.println("Usage: getGroupList");
  }

  public static void quitHelp() {
    System.out.println("Quit console.");
    System.out.println("Usage: quit");
  }

  public static void getBlockByHashHelp() {
    System.out.println("Query information about a block by hash.");
    System.out.println("Usage: getBlockByHash blockHash [boolean]");
    System.out.println("blockHash -- 32 Bytes - Hash of a block.");
    System.out.println(
        "boolean -- (optional) If true it returns the full transaction objects, if false only the hashes of the transactions.");
    System.out.println();
  }

  public static void getBlockByNumberHelp() {
    System.out.println("Query information about a block by block number.");
    System.out.println("Usage: getBlockByNumber blockNumber [boolean]");
    System.out.println("blockNumber -- Integer of a block number.");
    System.out.println(
        "boolean -- (optional) If true it returns the full transaction objects, if false only the hashes of the transactions.");
    System.out.println();
  }

  public static void getBlockHashByNumberHelp() {
    System.out.println("Query block hash by block number.");
    System.out.println("Usage: getBlockHashByNumber blockNumber");
    System.out.println("blockNumber -- Integer of a block number.");
    System.out.println();
  }

  public static void getTransactionByHashHelp() {
    System.out.println("Query information about a transaction requested by transaction hash.");
    System.out.println("Usage: getTransactionByHash transactionHash");
    System.out.println("transactionHash -- 32 Bytes - Hash of a transaction.");
    System.out.println();
  }

  public static void getTransactionByBlockHashAndIndexHelp() {
    System.out.println(
        "Query information about a transaction by block hash and transaction index position.");
    System.out.println("Usage: getTransactionByBlockHashAndIndex blockHash index");
    System.out.println("blockHash -- 32 Bytes - Hash of a block.");
    System.out.println("index -- Integer of a transaction index.");
    System.out.println();
  }

  public static void getTransactionByBlockNumberAndIndexHelp() {
    System.out.println(
        "Query information about a transaction by block number and transaction index position.");
    System.out.println("Usage: getTransactionByBlockNumberAndIndex blockNumber index");
    System.out.println("blockNumber -- Integer of a block number.");
    System.out.println("index -- Integer of a transaction index.");
    System.out.println();
  }

  public static void getTransactionReceiptHelp() {
    System.out.println("Query the receipt of a transaction by transaction hash.");
    System.out.println("Usage: getTransactionReceipt transactionHash");
    System.out.println("transactionHash -- 32 Bytes - Hash of a transaction.");
    System.out.println();
  }

  public static void getPendingTransactionsHelp() {
    System.out.println("Query pending transactions.");
    System.out.println("Usage: getPendingTransactions");
  }

  public static void getPendingTxSizeHelp() {
    System.out.println("Query pending transactions size.");
    System.out.println("Usage: getPendingTxSize");
  }

  public static void getCodeHelp() {
    System.out.println("Query code at a given address.");
    System.out.println("Usage: getCode address");
    System.out.println("address -- 20 Bytes of a contract address.");
    System.out.println();
  }

  public static void getTotalTransactionCountHelp() {
    System.out.println("Query total transaction count.");
    System.out.println("Usage: getTotalTransactionCount");
  }

  public static void deployHelp() {
    System.out.println("Deploy a contract on blockchain.");
    System.out.println("Usage: deploy contractName");
    System.out.println("contractName -- A name for a contract.");
    System.out.println();
  }

  public static void callHelp() {
    System.out.println("Call a contract by a function and paramters.");
    System.out.println("Usage: call contractName contractAddress function parameters");
    System.out.println("contractName -- A name for a contract.");
    System.out.println("contractAddress -- An address for a contract.");
    System.out.println("function -- A function of a contract.");
    System.out.println("parameters -- Parameters(splited by a space) for a function.");
    System.out.println();
  }

  public static void deployByCNSHelp() {
    System.out.println("Deploy a contract on blockchain by CNS.");
    System.out.println("Usage: deployByCNS contractName contractVersion");
    System.out.println("contractName -- A name for a contract.");
    System.out.println("contractVersion -- A version for a contract.");
    System.out.println();
  }

  public static void callByCNSHelp() {
    System.out.println("Call a contract by a function and paramters by CNS.");
    System.out.println("Usage: callByCNS contractName contractVersion function parameters");
    System.out.println("contractName -- A name for a contract.");
    System.out.println("contractVersion -- A version for a contract.");
    System.out.println("function -- A function of a contract.");
    System.out.println("parameters -- Parameters(splited by a space) for a function.");
    System.out.println();
  }

  public static void addObserverHelp() {
    System.out.println("Add an observer node.");
    System.out.println("Usage: addObserver nodeID");
    System.out.println("nodeID -- nodeID of node.");
    System.out.println();
  }

  public static void addSealerHelp() {
    System.out.println("Add a sealer node.");
    System.out.println("Usage: addSealer nodeID");
    System.out.println("nodeID -- nodeID of a node.");
    System.out.println();
  }

  public static void removeNodeHelp() {
    System.out.println("Remove a node.");
    System.out.println("Usage: removeNode nodeID");
    System.out.println("nodeID -- nodeID of a node.");
    System.out.println();
  }

	public static void grantUserTableManagerHelp() {
	    System.out.println("Grant permisson for user table by table name and address.");
	    System.out.println("Usage: grantUserTableManager tableName address");
	    System.out.println("tableName -- name of a table.");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void revokeUserTableManagerHelp() {
	    System.out.println("Revoke permisson for user table by table name and address.");
	    System.out.println("Usage: revokeUserTableManager tableName address");
	    System.out.println("tableName -- name of a table.");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void listUserTableManagerHelp() {
	    System.out.println("Query permisson for user table information.");
	    System.out.println("Usage: listUserTableManager tableName");
	    System.out.println("tableName -- name of a table.");
	    System.out.println();
	  }

	  public static void grantDeployAndCreateManagerHelp() {
	    System.out.println("Grant permisson for deploy contract and create user table by address.");
	    System.out.println("Usage: grantDeployAndCreateManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void revokeDeployAndCreateManagerHelp() {
	    System.out.println("Revoke permisson for deploy contract and create user table by address.");
	    System.out.println("Usage: revokeDeployAndCreateManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void listDeployAndCreateManagerHelp() {
	    System.out.println("Query permisson information for deploy contract and create user table.");
	    System.out.println("Usage: listDeployAndCreateManager");
	    System.out.println();
	  }

	  public static void grantPermissionManagerHelp() {
	    System.out.println("Grant permisson for permisson configuration by address.");
	    System.out.println("Usage: grantPermissionManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void revokePermissionManagerHelp() {
	    System.out.println("Revoke permisson for permisson configuration by address.");
	    System.out.println("Usage: revokePermissionManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void listPermissionManagerHelp() {
	    System.out.println("Query permisson information for permisson configuration.");
	    System.out.println("Usage: listPermissionManager");
	    System.out.println();
	  }

	  public static void grantNodeManagerHelp() {
	    System.out.println("Grant permisson for node configuration by address.");
	    System.out.println("Usage: grantNodeManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void revokeNodeManagerHelp() {
	    System.out.println("Revoke permisson for node configuration by address.");
	    System.out.println("Usage: revokeNodeManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void listNodeManagerHelp() {
	    System.out.println("Query permisson information for node configuration.");
	    System.out.println("Usage: listNodeManager");
	    System.out.println();
	  }

	  public static void grantCNSManagerHelp() {
	    System.out.println("Grant permisson for CNS by address.");
	    System.out.println("Usage: grantCNSManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void revokeCNSManagerHelp() {
	    System.out.println("Revoke permisson for CNS by address.");
	    System.out.println("Usage: revokeCNSManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void listCNSManagerHelp() {
	    System.out.println("Query permisson information for CNS.");
	    System.out.println("Usage: listCNSManager");
	    System.out.println();
	  }

	  public static void grantSysConfigManagerHelp() {
	    System.out.println("Grant permisson for system configuration by address.");
	    System.out.println("Usage: grantSysConfigManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void revokeSysConfigManagerHelp() {
	    System.out.println("Revoke permisson for system configuration by address.");
	    System.out.println("Usage: revokeSysConfigManager address");
	    System.out.println("address -- address of tx.origin.");
	    System.out.println();
	  }

	  public static void listSysConfigManagerHelp() {
	    System.out.println("Query permisson information for system configuration.");
	    System.out.println("Usage: listSysConfigManager");
	    System.out.println();
	  }

  public static void setSystemConfigByKeyHelp() {
    System.out.println("Set a system config.");
    System.out.println("Usage: setSystemConfigByKey key value");
    System.out.println(
        "key -- the name of system config(tx_count_limit/tx_gas_limit supported currently).");
    System.out.println("value -- the value of system config to be set.");
    System.out.println();
  }

  public static void getSystemConfigByKeyHelp() {
    System.out.println("Query a system config value by key.");
    System.out.println("Usage: getSystemConfigByKey key");
    System.out.println(
        "key -- the name of system config(tx_count_limit/tx_gas_limit supported currently).");
    System.out.println();
  }

  public static void queryCNSHelp() {
    System.out.println("Query CNS information by contract name and contract version.");
    System.out.println("Usage: queryCNS contractName [contractVersion]");
    System.out.println("contractName -- A name for a contract.");
    System.out.println("contractVersion -- (optional) A version for a contract.");
    System.out.println();
  }

  public static void promptNoFunc(String contractName, String funcName, int lenParams) {
    if (lenParams <= 1) {
      System.out.println(
          "The method "
              + funcName
              + " with "
              + lenParams
              + " parameter"
              + " is undefined for the contract "
              + contractName
              + ".");
    } else {
      System.out.println(
          "The method "
              + funcName
              + " with "
              + lenParams
              + " parameters"
              + " is undefined for the contract "
              + contractName
              + ".");
    }
    System.out.println();
  }
}
