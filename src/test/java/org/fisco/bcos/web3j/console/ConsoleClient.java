package org.fisco.bcos.web3j.console;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.channel.ResponseExcepiton;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.methods.request.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthBlock.Block;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsoleClient {
    
    private static  Service service = null;
    private static Web3j web3j = null;
    private static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        service = context.getBean(Service.class);
        try {
            service.run();
        } catch (Exception e) {
            System.out.println(e);
        }
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        web3j = Web3j.build(channelEthereumService, service.getGroupId());
        
        Scanner sc = new Scanner(System.in);
        welcomeInfo();
        
        while(true)
        {
            System.out.print("> ");
            String request = sc.nextLine();
            String[] params = request.split(" ");
            if(params.length < 1)
            {
            	System.out.print("");
            	continue;
            }
            if("".equals(params[0].trim()))
            {
            	System.out.print("");
            	continue;
            }
            try {
                
                switch (params[0]) {
                
                case "help" :
                case "h" :
                    help();
                    break;
                case "getBlockNumber" :
                case "gbn" :
                    getBlockNumber(params);
                    break;
                case "getPbftView" :
                case "gpv" :
                    getPbftView(params);
                    break;
                case "getConsensusStatus" :
                case "gcs" :
                	getConsensusStatus(params);
                	break;
                case "getSyncStatus" :
                case "gss" :
                	getSyncStatus(params);
                	break;
                case "getClientVersion" :
                case "gcv" :
                	getClientVersion(params);
                	break;
                case "getPeers" :
                case "gps" :
                	getPeers(params);
                	break;
                case "getGroupPeers" :
                case "ggp" :
                	getGroupPeers(params);
                	break;
                case "getGroupList" :
                case "ggl" :
                	getGroupList(params);
                	break;
                case "getBlockByHash" :
                case "gbbh" :
                	getBlockByHash(params);
                	break;
                case "getBlockByNumber" :
                case "gbbn" :
                	getBlockByNumber(params);
                	break;
                case "getBlockHashByNumber" :
                case "gbhbn" :
                	getBlockHashByNumber(params);
                	break;
                case "getTransactionByHash" :
                case "gtbh" :
                	getTransactionByHash(params);
                	break;
                case "getTransactionByBlockHashAndIndex" :
                case "gthi" :
                	getTransactionByBlockHashAndIndex(params);
                	break;
                case "getTransactionByBlockNumberAndIndex" :
                case "gtni" :
                	getTransactionByBlockNumberAndIndex(params);
                	break;
                case "getTransactionReceipt" :
                case "gtr" :
                	getTransactionReceipt(params);
                	break;
                case "getPendingTransactions" :
                case "gpt" :
                	getPendingTransactions(params);
                	break;
                case "getCode" :
                case "gc" :
                	getCode(params);
                	break;
                case "getTotalTransactionCount" :
                case "gtc" :
                	getTotalTransactionCount(params);
                	break;
                case "call" :
                	call(params);
                	break;
                case "sendRawTransaction" :
                case "srt" :
                    sendRawTransaction(params);
                    break;
                case "quit" :
                case "q" :
                    System.exit(0);
                default:
                    System.out.println("Unknown command, enter 'help' for command list.\n");
                    break;  
            
                }
                
            } 
            catch (ResponseExcepiton e) {
                System.out.println("{\"error\":{\"code\":"+e.getCode()+", \"message:\""+"\""+e.getMessage()+"\"}}");
                System.out.println();
            }
            catch (IOException e) {
            	System.out.println(e.getMessage());
            	System.out.println();
            }
            
        }
    }

    private static void welcomeInfo() {
        doubleLine();
        System.out.println("Welcome to FISCO BCOS 2.0!");
        System.out.println("Type 'help' for command list. Type 'quit' to quit the console.");
        String logo = " ________  ______   ______    ______    ______         _______    ______    ______    ______  \n" + 
        		"|        \\|      \\ /      \\  /      \\  /      \\       |       \\  /      \\  /      \\  /      \\ \n" + 
        		"| $$$$$$$$ \\$$$$$$|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\      | $$$$$$$\\|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\\n" + 
        		"| $$__      | $$  | $$___\\$$| $$   \\$$| $$  | $$      | $$__/ $$| $$   \\$$| $$  | $$| $$___\\$$\n" + 
        		"| $$  \\     | $$   \\$$    \\ | $$      | $$  | $$      | $$    $$| $$      | $$  | $$ \\$$    \\ \n" + 
        		"| $$$$$     | $$   _\\$$$$$$\\| $$   __ | $$  | $$      | $$$$$$$\\| $$   __ | $$  | $$ _\\$$$$$$\\\n" + 
        		"| $$       _| $$_ |  \\__| $$| $$__/  \\| $$__/ $$      | $$__/ $$| $$__/  \\| $$__/ $$|  \\__| $$\n" + 
        		"| $$      |   $$ \\ \\$$    $$ \\$$    $$ \\$$    $$      | $$    $$ \\$$    $$ \\$$    $$ \\$$    $$\n" + 
        		" \\$$       \\$$$$$$  \\$$$$$$   \\$$$$$$   \\$$$$$$        \\$$$$$$$   \\$$$$$$   \\$$$$$$   \\$$$$$$";
        System.out.println(logo);
        System.out.println();
        doubleLine();
    }

    private static void getBlockNumber(String[] params) throws IOException {
    	String blockNumber = web3j.ethBlockNumber().sendForReturnString();
        System.out.println(Numeric.decodeQuantity(blockNumber));
        System.out.println();
    }
    

    private static void getPbftView(String[] params) throws IOException {
    	String pbftView = web3j.ethPbftView().sendForReturnString();
    	System.out.println(Numeric.decodeQuantity(pbftView));
    	System.out.println();
    }
    
    private static void getConsensusStatus(String[] params) throws IOException {
        String consensusStatus = web3j.consensusStatus().sendForReturnString();
        JsonFormatUtil.printJson(consensusStatus);
        System.out.println();    
    }
    
    private static void getSyncStatus(String[] params) throws IOException {
    	String syncStatus = web3j.ethSyncing().sendForReturnString();
    	JsonFormatUtil.printJson(syncStatus);
    	System.out.println();
    }
    
    private static void getClientVersion(String[] params) throws IOException {
    	String clientVersion = web3j.web3ClientVersion().sendForReturnString();
    	System.out.println(clientVersion);
    	System.out.println();
    }
    
    private static void getPeers(String[] params) throws IOException {
    	String peers = web3j.ethPeersInfo().sendForReturnString();
    	JsonFormatUtil.printJson(peers);
    	System.out.println();
    }
    
    private static void getGroupPeers(String[] params) throws IOException {
    	List<String> groupPeers = web3j.ethGroupPeers().send().getResult();
    	System.out.println(groupPeers);
    	System.out.println();
    }
    
    private static void getGroupList(String[] params) throws IOException {
    	List<String> groupList = web3j.ethGroupList().send().getResult();
    	System.out.println(groupList);
    	System.out.println();
    }
    
    private static void getBlockByHash(String[] params) throws IOException {
    	if(params.length < 2)
    	{
    		System.out.println("Please provide block hash and transaction flag(optional).");
    		return;
    	}
    	boolean flag = false;
    	if(params.length == 3 && "true".equals(params[2]))
    		flag = true;
    	String block = web3j.ethGetBlockByHash(params[1], flag).sendForReturnString();
    	JsonFormatUtil.printJson(block);
    	System.out.println();
    }
    
    private static void getBlockByNumber(String[] params) throws IOException {
    	if(params.length < 2)
    	{
    		System.out.println("Please provide block number and transaction flag(optional).");
    		return;
    	}
    	if(!params[1].matches("^[0-9]*$"))
    	{
    		System.out.println("Please provide block number by decimal mode.");
    		return;
    	}
    	boolean flag = false;
    	if(params.length == 3 && "true".equals(params[2]))
    		flag = true;
    	BigInteger blockNumber = new BigInteger(params[1]);
    	String block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), flag).sendForReturnString();
    	JsonFormatUtil.printJson(block);
    	System.out.println();
    }
    
    private static void getBlockHashByNumber(String[] params) throws IOException {
    	if(params.length < 2)
    	{
    		System.out.println("Please provide block number.");
    		return;
    	}
    	if(!params[1].matches("^[0-9]*$"))
    	{
    		System.out.println("Please provide block number by decimal mode.");
    		return;
    	}
    	BigInteger blockNumber = new BigInteger(params[1]);
    	String blockHash = web3j.getBlockHashByNumber(DefaultBlockParameter.valueOf(blockNumber)).sendForReturnString();
    	JsonFormatUtil.printJson(blockHash);
    	System.out.println();
    }
    
    private static void getTransactionByHash(String[] params) throws IOException {
    	if(params.length < 2)
    	{
    		System.out.println("Please provide transactions hash.");
    		return;
    	}
    	String transaction = web3j.ethGetTransactionByHash(params[1]).sendForReturnString();
    	JsonFormatUtil.printJson(transaction);
    	System.out.println();
    }
    
    private static void getTransactionByBlockHashAndIndex(String[] params) throws IOException {
    	if(params.length < 3)
    	{
    		System.out.println("Please provide block hash and transaction index.");
    		return;
    	}
    	if(!params[2].matches("^[0-9]*$"))
    	{
    		System.out.println("Please provide transaction index by decimal mode.");
    		return;
    	}
    	BigInteger index = new BigInteger(params[2]);
    	String transaction = web3j.ethGetTransactionByBlockHashAndIndex(params[1], index).sendForReturnString();
    	JsonFormatUtil.printJson(transaction);
    	System.out.println();
    }
    
    private static void getTransactionByBlockNumberAndIndex(String[] params) throws IOException {
    	if(params.length < 3)
    	{
    		System.out.println("Please provide block hash and transaction index.");
    		return;
    	}
    	if(!params[1].matches("^[0-9]*$"))
    	{
    		System.out.println("Please provide block number by decimal mode.");
    		return;
    	}
    	BigInteger blockNumber = new BigInteger(params[1]);
    	if(!params[2].matches("^[0-9]*$"))
    	{
    		System.out.println("Please provide transaction index by decimal mode.");
    		return;
    	}
    	BigInteger index = new BigInteger(params[2]);
    	String transaction = web3j.ethGetTransactionByBlockNumberAndIndex(DefaultBlockParameter.valueOf(blockNumber), index).sendForReturnString();
    	JsonFormatUtil.printJson(transaction);
    	System.out.println();
    }
    
    private static void getTransactionReceipt(String[] params) throws IOException {
    	if(params.length < 2)
    	{
    		System.out.println("Please provide transaction hash.");
    		return;
    	}
    	String transactionReceipt = web3j.ethGetTransactionReceipt(params[1]).sendForReturnString();
    	JsonFormatUtil.printJson(transactionReceipt);
    	System.out.println();
    }
    
    private static void getPendingTransactions(String[] params) throws IOException {
    	String pendingTransactions = web3j.ethPendingTransaction().sendForReturnString();
    	if("[]".equals(pendingTransactions))
    		System.out.println(pendingTransactions);
    	else
    		JsonFormatUtil.printJson(pendingTransactions);
    	System.out.println();
    }
    
    private static void getCode(String[] params) throws IOException {
    	if(params.length < 2)
    	{
    		System.out.println("Please provide contract address.");
    		return;
    	}
    	String code = web3j.ethGetCode(params[1], DefaultBlockParameterName.LATEST).sendForReturnString();
    	JsonFormatUtil.printJson(code);
    	System.out.println();
    }
    
    private static void getTotalTransactionCount(String[] params) throws IOException {
    	String transactionCount = web3j.getTotalTransactionCount().sendForReturnString();
    	JSONObject jo = JSONObject.parseObject(transactionCount);
    	jo.put("count", Numeric.decodeQuantity(jo.get("count").toString()));
    	jo.put("number", Numeric.decodeQuantity(jo.get("number").toString()));
    	JsonFormatUtil.printJson(jo.toJSONString());
    	System.out.println();
    }
    
    private static void call(String[] params) throws IOException {
    	if(params.length < 4)
    	{
    		System.out.println("Please provide from, to and data to call.");
    		return;
    	}
    	Transaction tx = new Transaction(params[1], BigInteger.ZERO,  BigInteger.ZERO, BigInteger.ZERO, params[2],  BigInteger.ZERO, params[3]);
    	String result = web3j.ethCall(tx, DefaultBlockParameterName.LATEST).sendForReturnString();
    	JsonFormatUtil.printJson(result);
    	System.out.println();
    }
    
    private static void sendRawTransaction(String[] params) throws IOException {
        if(params.length < 2 || "".equals(params[1].trim()))
        {
            System.out.println("missing transactions rlp after 'sendRawTransaction', for example:");
            System.out.println("sendRawTransaction 0x123ed..");
        }
        else
        {
            String txHash = web3j.ethSendRawTransaction(params[2]).sendForReturnString();
            System.out.println(txHash);
            
        }
        System.out.println();
    }

    private static void help() {
        singleLine();
        StringBuilder sb = new StringBuilder();
        sb.append("help                                          Provide help information for blockchain console.\n");
        sb.append("getBlockNumber(gbn)                           Query the number of most recent block.\n");
        sb.append("getPbftView(gpv)                              Query the pbft view of node.\n");
        sb.append("getConsensusStatus(gcs)                       Query consensus status.\n");
        sb.append("getSyncStatus(gss)                            Query sync status.\n");
        sb.append("getClientVersion(gcv)                         Query the current client version.\n");
        sb.append("getPeers(gps)                                 Query peers currently connected to the client.\n");
        sb.append("getGroupPeers(ggp)                            Query peers currently connected to the client in the specified group.\n");
        sb.append("getGroupList(ggl)                             Query group list.\n");
        sb.append("getGroupPeers(ggp)                            Query peers currently connected to the client in the specified group.\n");
        sb.append("getBlockByHash(gbbh)                          Query information about a block by hash.\n");
        sb.append("getBlockByNumber(gbbn)                        Query information about a block by block number.\n");
        sb.append("getBlockHashByNumber(gbhbn)                   Query block hash by block number.\n");
        sb.append("getTransactionByHash(gtbh)                    Query information about a transaction requested by transaction hash.\n");
        sb.append("getTransactionByBlockHashAndIndex(gthi)       Query information about a transaction by block hash and transaction index position.\n");
        sb.append("getTransactionByBlockNumberAndIndex(gtni)     Query information about a transaction by block number and transaction index position.\n");
        sb.append("getTransactionReceipt(gtr)                    Query the receipt of a transaction by transaction hash.\n");
        sb.append("getPendingTransactions(gpt)                   Query pending transactions.\n");
        sb.append("getCode(gc)                                   Query code at a given address.\n");
        sb.append("getTotalTransactionCount(gtc)                 Query total transaction count.\n");
        sb.append("call                                          Executes a new message call immediately without creating a transaction on the block chain.\n");
        sb.append("sendRawTransaction(srt)                       Creates new message call transaction or a contract creation for signed transactions.\n");
        sb.append("quit                                          Quit the blockchain console.");
        System.out.println(sb.toString());
        singleLine();
    }
    
    private static void singleLine() {
        System.out.println("----------------------------------------------------------------------------------------------");
    }
    private static void doubleLine() {
        System.out.println("==============================================================================================");
    }

}
