package org.fisco.bcos.web3j.console;

public class HelpInfo {
	
	public static void promptHelp(String command) 
	{
		System.out.println("Try '"+ command +" --help' for more information.");
	}
	
	public static void getBlockByHashHelp()
	{
		System.out.println("Query information about a block by hash.");
		System.out.println("Usage: gbbh blockHash [boolean]");
		System.out.println("blockHash -- 32 Bytes - Hash of a block.");
		System.out.println("boolean -- If true it returns the full transaction objects, if false only the hashes of the transactions.");
		System.out.println();
	}
	public static void getBlockByNumberHelp()
	{
		System.out.println("Query information about a block by block number.");
		System.out.println("Usage: gbbn blockNumber [boolean]");
		System.out.println("blockNumber -- Integer of a block number.");
		System.out.println("boolean -- If true it returns the full transaction objects, if false only the hashes of the transactions.");
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
		System.out.println();
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
		System.out.println();
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
		System.out.println("Usage: c contractName function parameters");
		System.out.println("function -- A function of a contract.");
		System.out.println("parameters -- Parameters(splited by a space) for a function.");
		System.out.println();
	}
	public static void addPbftHelp() 
	{
		System.out.println("Add a pbft node.");
		System.out.println("Usage: ap nodeID");
		System.out.println("nodeID -- nodeID of a pbft observer node.");
		System.out.println();
	}
	public static void removePbftHelp() 
	{
		System.out.println("Remove a pbft node.");
		System.out.println("Usage: rp nodeID");
		System.out.println("nodeID -- nodeID of a pbft sealer node.");
		System.out.println();
	}

}
