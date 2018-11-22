package org.fisco.bcos.web3j.console;

import java.io.Console;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.methods.request.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthBlock.Block;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthSyncing;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthSyncing.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsoleClient {
    
    private static BigInteger transactionCount = BigInteger.valueOf(0);
    private static BigInteger bigBlockHeight = BigInteger.valueOf(0);
    private static BigInteger bigPbftView = BigInteger.valueOf(0);
    private static EthBlock.Block block = null;
    private static BigInteger transactionIndex = BigInteger.valueOf(0);
    private static Transaction transaction = null;
    private static String transactionHash = null;
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
            try {
                
                switch (params[0]) {
                
                case "help" :
                    help();
                    break;
                case "getBlockNumber" :
                    getBlockNumber(params);
                    break;
                case "getPbftView" :
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
                case "gbh" :
                	getBlockByHash(params);
                	break;
                case "getBlockByNumber" :
                case "gbn" :
                	getBlockByNumber(params);
                	break;
                case "sendRawTransaction" :
                    sendRawTransaction(params);
                    break;
                case "quit" :
                    System.exit(0);
                default:
                    System.out.println("Unknown command, enter 'help' for command list.\n");
                    break;  
            
                }
                
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
    }

    private static void welcomeInfo() {
        doubleLine();
        System.out.println("Welcome to the FISCO BCOS console!");
        System.out.println("Type 'help' for command list. Type 'quit' to quit the console.");
        System.out.println();
        doubleLine();
    }

    private static void getBlockNumber(String[] params) throws IOException {
        String blockNumber = web3j.ethBlockNumber().send().getResult();
        System.out.println(blockNumber);
            
        System.out.println();
    }
    

    private static void getPbftView(String[] params) throws IOException {
    	String pbftView = web3j.ethPbftView().send().getResult();
    	System.out.println(pbftView);
    	
    	System.out.println();
    }
    
    private static void getConsensusStatus(String[] params) throws IOException {
        String consensusStatus = web3j.consensusStatus().send().getNetVersion();
        System.out.println(consensusStatus);
            
        System.out.println();
    }
    
    private static void getSyncStatus(String[] params) throws IOException {
    	Result result = web3j.ethSyncing().send().getResult();
    	String syncStatus = mapper.writeValueAsString(result);
    	System.out.println(syncStatus);
    	
    	System.out.println();
    }
    
    private static void getClientVersion(String[] params) throws IOException {
    	String clientVersion = web3j.web3ClientVersion().send().getResult();
    	System.out.println(clientVersion);
    	
    	System.out.println();
    }
    
    private static void getPeers(String[] params) throws IOException {
    	String peers = web3j.netPeerCount().send().getResult();
    	System.out.println(peers);
    	
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
    	if(params.length < 3)
    	{
    		System.out.println("Please provide block hash and transaction flag.");
    		return;
    	}
    	Block block = web3j.ethGetBlockByHash(params[1], false).send().getResult();
    	System.out.println(block);
    	
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
    	Block block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), flag).send().getResult();
    	System.out.println(block);
    	
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
            String txHash = web3j.ethSendRawTransaction(params[2]).send().getResult();
            System.out.println(txHash);
            
        }
        System.out.println();
    }

    private static void help() {
        singleLine();
        StringBuilder sb = new StringBuilder();
        sb.append("help                      Provide help information for blockchain console.\n");
        sb.append("getBlockNumber            Query the number of most recent block.\n");
        sb.append("getPbftView               Query the pbft view of node.\n");
        sb.append("getConsensusStatus(gcs)   Query consensus status.\n");
        sb.append("getSyncStatus(gss)        Query sync status.\n");
        sb.append("getClientVersion          Query the current client version.\n");
        sb.append("getPeers                  Query peers currently connected to the client.\n");
        sb.append("getGroupPeers             Query peers currently connected to the client in the specified group.\n");
        sb.append("sendRawTransaction        Creates new message call transaction or a contract \n                          creation for signed transactions.\n");
        sb.append("quit                      Quit the blockchain console.");
        System.out.println(sb.toString());
        singleLine();
    }
    
    private static void singleLine() {
        System.out.println("--------------------------------------------------------------------------");
    }
    private static void doubleLine() {
        System.out.println("==========================================================================");
    }

}
