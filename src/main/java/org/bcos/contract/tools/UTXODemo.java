package org.bcos.contract.tools;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bcos.channel.client.Service;
import org.bcos.contract.source.Limitation;
import org.bcos.contract.source.UserCheckTemplate;
import org.bcos.contract.tools.UTXOTool;
import org.bcos.web3j.abi.FunctionEncoder;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.DynamicArray;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.GenCredential;
import org.bcos.web3j.abi.datatypes.*;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.tx.RawTransactionManager;
import org.bcos.web3j.tx.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UTXODemo {
	
	private static Logger logger = LoggerFactory.getLogger(Service.class);
	
	public static void main(String[] args) throws Exception{
		
		//String[] args={"InitTokens","1",null};
		
		if (args.length < 1) {
			System.out.println("Please input Params.");
			return;
		}
	  	
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		Web3j web3j = Web3j.build(channelEthereumService);

		ToolConf toolConf = context.getBean(ToolConf.class);
		String privKey = toolConf.getPrivKey();
		Credentials credentials = GenCredential.create(privKey);
		TransactionManager manager = new RawTransactionManager(web3j, credentials);
        
		UTXOTool utxoTool = new UTXOTool(web3j, toolConf, manager);
	  	
	  	switch (args[0]) {
	  		case "InitTokens":
	  		{
	  			if (args.length < 2) {
                    System.out.println("Please input: ./web3SDK InitTokens $(Type)");
                    break;
                }
	  			switch (args[1]) {
	  				case "1":
	  				{
	  					// 基础交易例子-铸币
	  					String accountHash = utxoTool.sha3("0x3ca576d469d7aa0244071d27eb33c5629753593e").toString();
	  					String initTokenStr1 = "{\"utxotype\":1,\"txout\":[{\"to\":\""+accountHash+"\",\"value\":\"100\",\"checktype\":\"P2PKH\"}]}";
	  					utxoTool.eth_sendTransaction(initTokenStr1);
	  					
	  					break;
	  				}
	  				case "2":
	  				{
	  			        // 限制Token的使用账号为特定账号-铸币
	  					String initContractAddr = "0x6494ddf84114fabf12abd1f4e63d29878eb118e1";
	  					List<Address> addrList = new ArrayList<Address>();
	  					addrList.add(new Address("0x3ca576d469d7aa0244071d27eb33c5629753593e"));
	  					DynamicArray<Address> array = new DynamicArray<Address>(addrList);
	  					Function function = new Function("newUserCheckContract", 
	  							Arrays.<Type>asList(array), 
	  							Collections.<TypeReference<?>>emptyList());
	  					String init_tx_data = FunctionEncoder.encode(function);
	  					String initTokenStr2 = "{\"utxotype\":1,\"txout\":[{\"to\":\"0x3ca576d469d7aa0244071d27eb33c5629753593e\",\"value\":\"100\",\"checktype\":\"P2PK\",\"initcontract\":\""+initContractAddr+"\",\"initfuncandparams\":\""+init_tx_data+"\",\"oridetail\":\"Only userd by config.account\"}]}";
	  					utxoTool.eth_sendTransaction(initTokenStr2);
	  			        
	  					break;
	  				}
	  				case "3":
	  				{
	  					// 限制某一账号的日转账限额-铸币
	  					String validationContractAddr = "0x541db082964c531b7005f4aa91c253a4a856152f";
	  					String initTokenStr3 = "{\"utxotype\":1,\"txout\":[{\"to\":\"0x3ca576d469d7aa0244071d27eb33c5629753593e\",\"value\":\"100\",\"checktype\":\"P2PK\",\"validationcontract\":\""+validationContractAddr+"\",\"oridetail\":\"Account with Limitation per day\"}]}";
	  					utxoTool.eth_sendTransaction(initTokenStr3);
	  			        
	  					break;
	  				}
	  				default:
	  				{
	  					System.out.println("The second param error.");
	  					break;
	  				}
	  			}
	  			break;
	  		}
	  		case "SendSelectedTokens":
	  		{
	  			if (args.length < 2) {
	  				System.out.println("Please input: ./web3SDK SendSelectedTokens $(Type)");
	  				break;
	  			}
	  			switch (args[1]) {
	  				case "1":
	  				{
	  					// 基础交易例子-转账
	  				  	String Token1 = "0x9327082b51dc5711967e37fe7ed8c7b9b91fad6ac599a1cde40dbc2dbd02d615_0";
	  				  	String shaSendTo = utxoTool.sha3("0x3ca576d469d7aa0244071d27eb33c5629753593e").toString();
	  				  	String sendTokenStr1 = "{\"utxotype\":2,\"txin\":[{\"tokenkey\":\""+Token1+"\"}],\"txout\":[{\"to\":\"0x64fa644d2a694681bd6addd6c5e36cccd8dcdde3\",\"value\":\"60\",\"checktype\":\"P2PK\"},{\"to\":\""+shaSendTo+"\",\"value\":\"40\",\"checktype\":\"P2PKH\"}]}";
	  				  	utxoTool.eth_sendTransaction(sendTokenStr1);
	  			        
	  				  	break;
	  				}
	  				case "2":
	  				{
	  				  	// 限制Token的使用账号为特定账号-转账
	  					String Token1 = "0x6b16bde0540c47f2166f0fae71b50692f264f5519cc7ca1aae29bb9ea7f4b32d_0";
	  				  	Address user = new Address("0x3ca576d469d7aa0244071d27eb33c5629753593e");
	  				  	Function function = new Function("check", 
	  				  			Arrays.<Type>asList(user), 
	  				  			Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
	  				  	String check_tx_data = FunctionEncoder.encode(function);
	  				  	String shaSendTo = utxoTool.sha3("0x3ca576d469d7aa0244071d27eb33c5629753593e").toString();
	  				  	String sendTokenStr2 = "{\"utxotype\":2,\"txin\":[{\"tokenkey\":\""+Token1+"\",\"callfuncandparams\":\""+check_tx_data+"\"}],\"txout\":[{\"to\":\"0x64fa644d2a694681bd6addd6c5e36cccd8dcdde3\",\"value\":\"60\",\"checktype\":\"P2PK\"},{\"to\":\""+shaSendTo+"\",\"value\":\"40\",\"checktype\":\"P2PKH\"}]}";
	  				  	utxoTool.eth_sendTransaction(sendTokenStr2);
	  			        
	  				  	break;
	  				}
	  				case "3":
	  				{
	  					// 限制某一账号的日转账限额-转账
	  					String Token1 = "0xb7ce3cf34d41a4880dae270682e78543e06411d455a78407eee96babe86c668c_0";
	  					Address account = new Address("0x3ca576d469d7aa0244071d27eb33c5629753593e");
	  					Uint256 limit = new Uint256(60);
	  					Function function1 = new Function("checkSpent", 
	  							Arrays.<Type>asList(account, limit), 
	  							Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
	  					String check_tx_data = FunctionEncoder.encode(function1);
	  					Function function2 = new Function("addSpent", 
	  							Arrays.<Type>asList(account, limit), 
	  							Collections.<TypeReference<?>>emptyList());
	  					String update_tx_data = FunctionEncoder.encode(function2);
	  					String shaSendTo = utxoTool.sha3("0x3ca576d469d7aa0244071d27eb33c5629753593e").toString();
	  					String sendTokenStr3 = "{\"utxotype\":2,\"txin\":[{\"tokenkey\":\""+Token1+"\",\"callfuncandparams\":\""+check_tx_data+"\",\"exefuncandparams\":\""+update_tx_data+"\"}],\"txout\":[{\"to\":\"0x64fa644d2a694681bd6addd6c5e36cccd8dcdde3\",\"value\":\"60\",\"checktype\":\"P2PK\"},{\"to\":\""+shaSendTo+"\",\"value\":\"40\",\"checktype\":\"P2PKH\"}]}";
	  					utxoTool.eth_sendTransaction(sendTokenStr3);
	  			        
	  					break;
	  				}
	  				default:
	  				{
	  					System.out.println("The second param error.");
	  					break;
	  				}
	  			}
	  			break;
	  		}
	  		case "RegisterAccount":
	  		{
	  			if (args.length < 2) {
	  				System.out.println("Please input: ./web3SDK RegisterAccount $(Account)");
	  				break;
	  			}
	  		  	String result = utxoTool.registerAccount(args[1]);
	  		  	System.out.println("result:\n"+result);
	  		  	break;
	  		}
	  		case "GetToken":
	  		{
	  			if (args.length < 2) {
	  				System.out.println("Please input: ./web3SDK GetToken $(TokenKey)");
	  				break;
	  			}
	  			String result = utxoTool.getToken(args[1]);
	  			System.out.println("result:\n"+result);
	  			break;
	  		}
	  		case "GetTx":
	  		{
	  			if (args.length < 2) {
	  				System.out.println("Please input: ./web3SDK GetTx $(TxKey)");
	  				break;
	  			}
	  			String result = utxoTool.getTx(args[1]);
	  			System.out.println("result:\n"+result);
	  			break;
	  		}
	  		case "GetVault":
	  		{
	  			if (args.length < 3) {
	  				System.out.println("Please input: ./web3SDK GetVault $(Account) $(TokenType)");
	  				break;
	  			}
	  			String result = utxoTool.getVault(args[1], args[2]);
	  			System.out.println("result:\n"+result);
	  			break;
	  		}
	  		case "SelectTokens":
	  		{
	  			if (args.length < 3) {
	  				System.out.println("Please input: ./web3SDK SelectTokens $(Account) $(Value)");
	  				break;
	  			}
	  			String result = utxoTool.selectTokens(args[1], args[2]);
	  			System.out.println("result:\n"+result);
	  			break;
	  		}
	  		case "TokenTracking":
	  		{
	  			if (args.length < 2) {
	  				System.out.println("Please input: ./web3SDK TokenTracking $(TokenKey)");
	  				break;
	  			}
	  			String result = utxoTool.tokenTracking(args[1]);
	  			System.out.println("result:\n"+result);
	  			break;
	  		}
	  		case "GetBalance":
	  		{
	  			if (args.length < 2) {
	  				System.out.println("Please input: ./web3SDK GetBalance $(Account)");
	  				break;
	  			}
	  			String result = utxoTool.getBalance(args[1]);
	  			System.out.println("result:\n"+result);
	  			break;
	  		}
	  		case "Preparation":
	  		{
	  			// 部署合约及日限额设置
	  		  	BigInteger gasPrice = new BigInteger("30000000");
	  			BigInteger gasLimit = new BigInteger("30000000");
	  			BigInteger initialWeiValue = new BigInteger("0");
	  			UserCheckTemplate userCheck = UserCheckTemplate.deploy(web3j,credentials,gasPrice,gasLimit,initialWeiValue).get();
	  		  	logger.info("userCheck address:"+userCheck.getContractAddress());
	  		  	System.out.println("userCheck address:"+userCheck.getContractAddress());
	  		  	Limitation limitation = Limitation.deploy(web3j,credentials,gasPrice,gasLimit,initialWeiValue).get();
	  		  	logger.info("limitation address:"+limitation.getContractAddress());
	  		  	System.out.println("limitation address:"+limitation.getContractAddress());
	  		  	limitation.setLimitation(new Address("0x3ca576d469d7aa0244071d27eb33c5629753593e"), new Uint256(2000000));
	  		  	Uint256 limit = limitation.getLimit(new Address("0x3ca576d469d7aa0244071d27eb33c5629753593e")).get();
	  		  	System.out.println("limit:"+limit.getValue());
	  			break;
	  		}
			default:
			{
				System.out.println("The first param error.");
				break;
			}
	  	}
        
	  	System.exit(0);
	}
}
