package org.fisco.bcos.web3j.console;

public class HelpInfo {
	
	public static void promptHelp(String command) 
	{
		System.out.println("Try '"+ command +" -h or --help' for more information.");
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
	
	public static void helpNoParams(String func) 
	{
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
			queryDeployAndCreateManagerHelp();
			break;
		case "qam":
			queryAuthorityManagerHelp();
			break;
		case "qnm":
			queryNodeManagerHelp();
			break;
		case "qcm":
			queryCNSManagerHelp();
			break;
		case "qsm":
			querySysConfigManagerHelp();
			break;
		case "q":
			quitHelp();
			break;

		default:
			break;
		}
		System.out.println();
	}
	
	public static void help() 
	{
		System.out.println("Provide help information.");
		System.out.println("Usage: h");
	}
	
	public static void getBlockNumberHelp() 
	{
		System.out.println("Query the number of most recent block.");
		System.out.println("Usage: gbn");
	}
	
	public static void getPbftViewHelp() 
	{
		System.out.println("Query the pbft view of node.");
		System.out.println("Usage: gpv");
	}
	
	public static void getObserverListHelp() 
	{
		System.out.println("Query nodeID list for observer nodes.");
		System.out.println("Usage: gol");
	}
	
	public static void getSealerListHelp() 
	{
		System.out.println("Query nodeID list for sealer nodes.");
		System.out.println("Usage: gsl");
	}
	
	public static void getConsensusStatusHelp() 
	{
		System.out.println("Query consensus status.");
		System.out.println("Usage: gcs");
	}
	
	public static void getSyncStatusHelp() 
	{
		System.out.println("Query sync status.");
		System.out.println("Usage: gss");
	}
	
	public static void getClientVersionHelp() 
	{
		System.out.println("Query the current client version.");
		System.out.println("Usage: gcv");
	}
	
	public static void getPeersHelp() 
	{
		System.out.println("Query peers currently connected to the client.");
		System.out.println("Usage: gps");
	}
	
	public static void getNodeIDListHelp() 
	{
		System.out.println("Query nodeID list for all connected nodes.");
		System.out.println("Usage: gnl");
	}
	
	public static void getGroupPeersHelp() 
	{
		System.out.println("Query nodeID list for sealer and observer nodes.");
		System.out.println("Usage: ggp");
	}
	
	public static void getGroupListHelp() 
	{
		System.out.println("Query group list.");
		System.out.println("Usage: ggl");
	}
	
	public static void quitHelp() 
	{
		System.out.println("Quit console.");
		System.out.println("Usage: q");
	}
	
	public static void getBlockByHashHelp()
	{
		System.out.println("Query information about a block by hash.");
		System.out.println("Usage: gbbh blockHash [boolean]");
		System.out.println("blockHash -- 32 Bytes - Hash of a block.");
		System.out.println("boolean -- (optional) If true it returns the full transaction objects, if false only the hashes of the transactions.");
		System.out.println();
	}
	public static void getBlockByNumberHelp()
	{
		System.out.println("Query information about a block by block number.");
		System.out.println("Usage: gbbn blockNumber [boolean]");
		System.out.println("blockNumber -- Integer of a block number.");
		System.out.println("boolean -- (optional) If true it returns the full transaction objects, if false only the hashes of the transactions.");
		System.out.println();
	}
	public static void getBlockHashByNumberHelp() 
	{
		System.out.println("Query block hash by block number.");
		System.out.println("Usage: ghbn blockNumber");
		System.out.println("blockNumber -- Integer of a block number.");
		System.out.println();
	}
	public static void getTransactionByHashHelp() 
	{
		System.out.println("Query information about a transaction requested by transaction hash.");
		System.out.println("Usage: gtbh transactionHash");
		System.out.println("transactionHash -- 32 Bytes - Hash of a transaction.");
		System.out.println();
	}
	public static void getTransactionByBlockHashAndIndexHelp() 
	{
		System.out.println("Query information about a transaction by block hash and transaction index position.");
		System.out.println("Usage: gthi blockHash index");
		System.out.println("blockHash -- 32 Bytes - Hash of a block.");
		System.out.println("index -- Integer of a transaction index.");
		System.out.println();
	}
	public static void getTransactionByBlockNumberAndIndexHelp() 
	{
		System.out.println("Query information about a transaction by block number and transaction index position.");
		System.out.println("Usage: gtni blockNumber index");
		System.out.println("blockNumber -- Integer of a block number.");
		System.out.println("index -- Integer of a transaction index.");
		System.out.println();
	}
	public static void getTransactionReceiptHelp() 
	{
		System.out.println("Query the receipt of a transaction by transaction hash.");
		System.out.println("Usage: gtr transactionHash");
		System.out.println("transactionHash -- 32 Bytes - Hash of a transaction.");
		System.out.println();
	}
	public static void getPendingTransactionsHelp() 
	{
		System.out.println("Query pending transactions.");
		System.out.println("Usage: gpt");
	}
	public static void getPendingTxSizeHelp() 
	{
		System.out.println("Query pending transactions size.");
		System.out.println("Usage: gpts");
	}
	public static void getCodeHelp() 
	{
		System.out.println("Query code at a given address.");
		System.out.println("Usage: gc address");
		System.out.println("address -- 20 Bytes of a contract address.");
		System.out.println();
	}
	public static void getTotalTransactionCountHelp() 
	{
		System.out.println("Query total transaction count.");
		System.out.println("Usage: gtc");
	}
	public static void deployHelp() 
	{
		System.out.println("Deploy a contract on blockchain.");
		System.out.println("Usage: d contractName");
		System.out.println("contractName -- A name for a contract.");
		System.out.println();
	}
	public static void callHelp() 
	{
		System.out.println("Call a contract by a function and paramters.");
		System.out.println("Usage: c contractName contractAddress function parameters");
		System.out.println("contractName -- A name for a contract.");
		System.out.println("contractAddress -- An address for a contract.");
		System.out.println("function -- A function of a contract.");
		System.out.println("parameters -- Parameters(splited by a space) for a function.");
		System.out.println();
	}
	public static void deployByCNSHelp() 
	{
		System.out.println("Deploy a contract on blockchain by CNS.");
		System.out.println("Usage: dbc contractName contractVersion");
		System.out.println("contractName -- A name for a contract.");
		System.out.println("contractVersion -- A version for a contract.");
		System.out.println();
	}
	public static void callByCNSHelp() 
	{
		System.out.println("Call a contract by a function and paramters by CNS.");
		System.out.println("Usage: cbc contractName contractVersion function parameters");
		System.out.println("contractName -- A name for a contract.");
		System.out.println("contractVersion -- A version for a contract.");
		System.out.println("function -- A function of a contract.");
		System.out.println("parameters -- Parameters(splited by a space) for a function.");
		System.out.println();
	}
	public static void addObserverHelp() 
	{
		System.out.println("Add an observer node.");
		System.out.println("Usage: ao nodeID");
		System.out.println("nodeID -- nodeID of node.");
		System.out.println();
	}
	public static void addSealerHelp() 
	{
		System.out.println("Add a sealer node.");
		System.out.println("Usage: as nodeID");
		System.out.println("nodeID -- nodeID of a node.");
		System.out.println();
	}
	public static void removeNodeHelp() 
	{
		System.out.println("Remove a node.");
		System.out.println("Usage: rn nodeID");
		System.out.println("nodeID -- nodeID of a node.");
		System.out.println();
	}

	public static void addUserTableManagerHelp() 
	{
		System.out.println("Add authority for user table by table name and address.");
		System.out.println("Usage: aum tableName address");
		System.out.println("tableName -- name of a table.");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void removeUserTableManagerHelp() 
	{
		System.out.println("Remove authority for user table by table name and address.");
		System.out.println("Usage: rum tableName address");
		System.out.println("tableName -- name of a table.");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void queryUserTableManagerHelp() 
	{
		System.out.println("Query authority for user table information.");
		System.out.println("Usage: qum tableName");
		System.out.println("tableName -- name of a table.");
		System.out.println();
	}
	
	public static void addDeployAndCreateManagerHelp() 
	{
		System.out.println("Add authority for deploy contract and create user table by address.");
		System.out.println("Usage: adm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void removeDeployAndCreateManagerHelp() 
	{
		System.out.println("Remove authority for deploy contract and create user table by address.");
		System.out.println("Usage: rdm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void queryDeployAndCreateManagerHelp() 
	{
		System.out.println("Query authority information for deploy contract and create user table.");
		System.out.println("Usage: qdm");
		System.out.println();
	}
	
	public static void addAuthorityManagerHelp() 
	{
		System.out.println("Add authority for authority configuration by address.");
		System.out.println("Usage: aam address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void removeAuthorityManagerHelp() 
	{
		System.out.println("Remove authority for authority configuration by address.");
		System.out.println("Usage: ram address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void queryAuthorityManagerHelp() 
	{
		System.out.println("Query authority information for authority configuration.");
		System.out.println("Usage: qam");
		System.out.println();
	}
	
	public static void addNodeManagerHelp() 
	{
		System.out.println("Add authority for node configuration by address.");
		System.out.println("Usage: anm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void removeNodeManagerHelp() 
	{
		System.out.println("Remove authority for node configuration by address.");
		System.out.println("Usage: rnm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void queryNodeManagerHelp() 
	{
		System.out.println("Query authority information for node configuration.");
		System.out.println("Usage: qnm");
		System.out.println();
	}
	
	public static void addCNSManagerHelp() 
	{
		System.out.println("Add authority for CNS by address.");
		System.out.println("Usage: acm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void removeCNSManagerHelp() 
	{
		System.out.println("Remove authority for CNS by address.");
		System.out.println("Usage: rcm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void queryCNSManagerHelp() 
	{
		System.out.println("Query authority information for CNS.");
		System.out.println("Usage: qcm");
		System.out.println();
	}
	
	public static void addSysConfigManagerHelp() 
	{
		System.out.println("Add authority for system configuration by address.");
		System.out.println("Usage: asm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void removeSysConfigManagerHelp() 
	{
		System.out.println("Remove authority for system configuration by address.");
		System.out.println("Usage: rsm address");
		System.out.println("address -- address of tx.origin.");
		System.out.println();
	}
	
	public static void querySysConfigManagerHelp() 
	{
		System.out.println("Query authority information for system configuration.");
		System.out.println("Usage: qsm");
		System.out.println();
	}
	
	public static void setSystemConfigByKeyHelp()
	{
		System.out.println("Set a system config.");
		System.out.println("Usage: ssc key value");
		System.out.println("key -- the name of system config(tx_count_limit/tx_gas_limit supported currently).");
		System.out.println("value -- the value of system config to be set.");
		System.out.println();
	}
	public static void getSystemConfigByKeyHelp()
	{
		System.out.println("Query a system config value by key.");
		System.out.println("Usage: gsc key");
		System.out.println("key -- the name of system config(tx_count_limit/tx_gas_limit supported currently).");
		System.out.println();
	}

	public static void queryCNSHelp() 
	{
		System.out.println("Query cns information by contract name and contract version.");
		System.out.println("Usage: qcs contractName [contractVersion]");
		System.out.println("contractName -- A name for a contract.");
		System.out.println("contractVersion -- (optional) A version for a contract.");
		System.out.println();
	}

	public static void promptNoFunc(String contractName, String funcName, int lenParams) {
		if(lenParams <= 1)
		{
			System.out.println("The method "+ funcName + " with " + lenParams + " parameter" + " is undefined for the contract "+ contractName+".");
		}
		else
		{
			System.out.println("The method "+ funcName + " with " + lenParams + " parameters" + " is undefined for the contract "+ contractName+".");
		}
		System.out.println();
	}

}
