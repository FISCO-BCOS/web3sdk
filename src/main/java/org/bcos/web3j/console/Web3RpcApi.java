package org.bcos.web3j.console;

import java.math.BigInteger;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.fastjson.JSONObject;
import org.bcos.channel.client.Service;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.response.EthBlock;
import org.bcos.web3j.protocol.core.methods.response.Transaction;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

public class Web3RpcApi {
	
	private static BigInteger transactionCount = BigInteger.valueOf(0);
	private static BigInteger bigBlockHeight = BigInteger.valueOf(0);
	private static BigInteger bigPbftView= BigInteger.valueOf(0);
	private static EthBlock.Block block=null;
	private static BigInteger transactionIndex=BigInteger.valueOf(0);
	private static Transaction transaction=null;
	private static String transactionHash=null;

	public static void main(String[] args)  {
		System.out.println("==================================================================="+"\n"+"\n");
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
        try {
			service.run();
		} catch (Exception e) {
			System.out.println(e);
		}
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        Web3j web3j = Web3j.build(channelEthereumService);
        
        try {
        	switch (args[0]) {
        	
        	case "eth_accounts" :
        		
        		List<String> list=web3j.ethAccounts().send().getAccounts();
        		if(list.size()==0){
        			System.out.println("account is null");
        		}
        		if(list.size()>0){
        			for (int n=0;n<list.size();n++){
        				System.out.println(list.get(n));
        			}
        		}
        		break;
        		
			case "web3_clientVersion":
				
				String clientVersion=web3j.web3ClientVersion().send().getWeb3ClientVersion();
				System.out.println("clientVersion:"+clientVersion);
				break;
				
			case "eth_pbftView":
				
				bigPbftView=web3j.ethPbftView().send().getEthPbftView();
				System.out.println("PbftView:"+bigPbftView.intValue());
				break;
			
			case "eth_blockNumber":
				
				bigBlockHeight = web3j.ethBlockNumber().send().getBlockNumber();
				System.out.println("BlockHeight:"+bigBlockHeight.intValue());
				break;
			
			case "eth_getCode":
				if(args.length!=3){
        			System.out.println("please input address blockNumber");
        			break;
        		}
				bigBlockHeight = new BigInteger(args[2]);
				String code =web3j.ethGetCode(args[1], DefaultBlockParameter.valueOf(bigBlockHeight)).send().getCode();
				System.out.println("code:"+code);
				break;
			
			case "eth_getTransactionCount":	 
				if(args.length!=3){
        			System.out.println("please input address blockNumber");
        			break;
        		}
				bigBlockHeight = new BigInteger(args[2]);
				transactionCount=web3j.ethGetTransactionCount(args[1], DefaultBlockParameter.valueOf(bigBlockHeight)).send().getTransactionCount();
				System.out.println("transactionCount:"+transactionCount);
				break;
				
			case "eth_getBlockTransactionCountByHash":
				
				transactionCount=web3j.ethGetBlockTransactionCountByHash(args[1]).send().getTransactionCount();
				System.out.println("transactionCount:"+transactionCount);
				break;
			
			case "eth_getBlockTransactionCountByNumber":
				
				bigBlockHeight = new BigInteger(args[1]);
				transactionCount=web3j.ethGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(bigBlockHeight)).send().getTransactionCount();
				System.out.println("transactionCount:"+transactionCount);
				break;
				
			case "eth_sendRawTransaction":	
				
				transactionHash=web3j.ethSendRawTransaction(args[1]).send().getTransactionHash();
				System.out.println("transactionHash:"+transactionHash);
				break;
			
			case "eth_getBlockByHash":
				
				block=web3j.ethGetBlockByHash(args[1], false).send().getBlock();
				System.out.println("number: "+block.getNumber().intValue()+" hash: "+block.getHash()+" parentHash: "+block.getParentHash());
				break;
			
			case "eth_getBlockByNumber":
				
				bigBlockHeight = new BigInteger(args[1]);
				block=web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(bigBlockHeight), false).send().getBlock();
				System.out.println("number: "+block.getNumber().intValue()+" hash: "+block.getHash()+" parentHash: "+block.getParentHash());
				break;
				
			case "eth_getTransactionByBlockHashAndIndex":
				if(args.length!=3){
        			System.out.println("please input blockHash transactionPosition");
        			break;
        		}
				transactionIndex = new BigInteger(args[2]);
				transaction=web3j.ethGetTransactionByBlockHashAndIndex(args[1], transactionIndex).send().getResult();
				
				String transactionJson1=JSONObject.toJSONString(transaction);
				System.out.println(transactionJson1);
				break;
				
			case "eth_getTransactionByBlockNumberAndIndex":
				if(args.length!=3){
        			System.out.println("please input blockNumber transactionPosition");
        			break;
        		}
				bigBlockHeight = new BigInteger(args[1]);
				transactionIndex = new BigInteger(args[2]);
				transaction =web3j.ethGetTransactionByBlockNumberAndIndex(DefaultBlockParameter.valueOf(bigBlockHeight), transactionIndex).send().getResult();
				String transactionJson=JSONObject.toJSONString(transaction);
				System.out.println(transactionJson);
				break;
				
			case "eth_getTransactionReceipt":	
				
				TransactionReceipt receipt = web3j.ethGetTransactionReceipt(args[1]).send().getResult();
				String receiptJson=JSONObject.toJSONString(receipt);
				System.out.println(receiptJson);
				break;
				
			default:
				System.out.println("Parameter error, please re-enter！");
				break;	
		
        	}
			
		} catch (Exception e) {
			System.out.println(e);
		}
        
		System.exit(0);

	}

}
