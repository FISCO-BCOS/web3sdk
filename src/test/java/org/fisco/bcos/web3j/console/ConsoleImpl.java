package org.fisco.bcos.web3j.console;

import com.alibaba.fastjson.JSONObject;
import io.bretty.console.table.Alignment;
import io.bretty.console.table.ColumnFormatter;
import io.bretty.console.table.Table;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.precompile.authority.AuthorityInfo;
import org.fisco.bcos.web3j.precompile.authority.AuthorityService;
import org.fisco.bcos.web3j.precompile.cns.CnsInfo;
import org.fisco.bcos.web3j.precompile.cns.CnsService;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.precompile.config.SystemConfigSerivce;
import org.fisco.bcos.web3j.precompile.consensus.ConsensusService;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ConsoleImpl implements ConsoleFace {

	private Service service = null;
	private Web3j web3j = null;
	private java.math.BigInteger gasPrice = new BigInteger("1");
	private java.math.BigInteger gasLimit = new BigInteger("30000000");
	private ECKeyPair keyPair;
	private Credentials credentials;
	private String contractAddress;
	private String contractName;
	private String contractVersion;
	private Class<?> contractClass;
	private RemoteCall<?> remoteCall;
	private String privateKey = "";
	private ChannelEthereumService channelEthereumService = new ChannelEthereumService();
	private final String CONTRACT_PACKAGE = "org.fisco.bcos.temp.";
	
	public void init(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		service = context.getBean(Service.class);
		int groupID = 1;
		try {
			keyPair = Keys.createEcKeyPair();
			credentials = GenCredential.create(keyPair.getPrivateKey().toString(16));
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (args.length) {
		case 0:
			break;
		case 1:
			groupID = setGroupID(args, groupID);
			break;
		default:
			groupID = setGroupID(args, groupID);
			privateKey = args[1];
			try {
				credentials = GenCredential.create(privateKey);
			} catch (NumberFormatException e) {
				System.out.println("Please provide private key by hex format.");
				System.exit(0);
			}
			break;
		}
		service.setGroupId(groupID);
		try {
			service.run();
		} catch (Exception e) {
			System.out.println(
					"Failed to connect blockchain. Please check running status for blockchain and configruation for console.");
			System.exit(1);
		}
		channelEthereumService.setChannelService(service);
		web3j = Web3j.build(channelEthereumService, groupID);

	}

	private int setGroupID(String[] args, int groupID) {
		try {
			groupID = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("Please provide groupID by decimal format (default 1).");
			System.exit(0);
		}
		return groupID;
	}

	@Override
	public void welcome() {
		ConsoleUtils.doubleLine();
		System.out.println("Welcome to FISCO BCOS console!");
		System.out.println("Type 'help' or 'h' for help. Type 'quit' or 'q' to quit console.");
		String logo = " ________ ______  ______   ______   ______       _______   ______   ______   ______  \n" + 
				"|        |      \\/      \\ /      \\ /      \\     |       \\ /      \\ /      \\ /      \\ \n" + 
				"| $$$$$$$$\\$$$$$|  $$$$$$|  $$$$$$|  $$$$$$\\    | $$$$$$$|  $$$$$$|  $$$$$$|  $$$$$$\\\n" + 
				"| $$__     | $$ | $$___\\$| $$   \\$| $$  | $$    | $$__/ $| $$   \\$| $$  | $| $$___\\$$\n" + 
				"| $$  \\    | $$  \\$$    \\| $$     | $$  | $$    | $$    $| $$     | $$  | $$\\$$    \\ \n" + 
				"| $$$$$    | $$  _\\$$$$$$| $$   __| $$  | $$    | $$$$$$$| $$   __| $$  | $$_\\$$$$$$\\\n" + 
				"| $$      _| $$_|  \\__| $| $$__/  | $$__/ $$    | $$__/ $| $$__/  | $$__/ $|  \\__| $$\n" + 
				"| $$     |   $$ \\\\$$    $$\\$$    $$\\$$    $$    | $$    $$\\$$    $$\\$$    $$\\$$    $$\n" + 
				" \\$$      \\$$$$$$ \\$$$$$$  \\$$$$$$  \\$$$$$$      \\$$$$$$$  \\$$$$$$  \\$$$$$$  \\$$$$$$";
		System.out.println(logo);
		System.out.println();
		ConsoleUtils.doubleLine();
	}

	@Override
	public void help(String[] params) {
		if (promptNoParams(params, "h")) {
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("h");
			return;
		}
		ConsoleUtils.singleLine();
		StringBuilder sb = new StringBuilder();
		sb.append("help(h)                                       Provide help information.\n");
		sb.append("getBlockNumber(gbn)                           Query the number of most recent block.\n");
		sb.append("getPbftView(gpv)                              Query the pbft view of node.\n");
		sb.append("getMinerList(gml)                             Query nodeID list for miner nodes.\n");
		sb.append("getObserverList(gol)                          Query nodeID list for observer nodes.\n");
		sb.append("getNodeIDList(gnl)                            Query nodeID list for all connected nodes.\n");
		sb.append("getGroupPeers(ggp)                            Query nodeID list for miner and observer nodes.\n");
		sb.append("getPeers(gps)                                 Query peers currently connected to the client.\n");
		sb.append("getConsensusStatus(gcs)                       Query consensus status.\n");
		sb.append("getSyncStatus(gss)                            Query sync status.\n");
		sb.append("getClientVersion(gcv)                         Query the current client version.\n");
		sb.append("getGroupList(ggl)                             Query group list.\n");
		sb.append("getBlockByHash(gbbh)                          Query information about a block by hash.\n");
		sb.append("getBlockByNumber(gbbn)                        Query information about a block by block number.\n");
		sb.append("getBlockHashByNumber(ghbn)                    Query block hash by block number.\n");
		sb.append(
				"getTransactionByHash(gtbh)                    Query information about a transaction requested by transaction hash.\n");
		sb.append(
				"getTransactionByBlockHashAndIndex(gthi)       Query information about a transaction by block hash and transaction index position.\n");
		sb.append(
				"getTransactionByBlockNumberAndIndex(gtni)     Query information about a transaction by block number and transaction index position.\n");
		sb.append(
				"getTransactionReceipt(gtr)                    Query the receipt of a transaction by transaction hash.\n");
		sb.append("getPendingTransactions(gpt)                   Query pending transactions.\n");
		sb.append("getPendingTxSize(gpts)                        Query pending transactions size.\n");
		sb.append("getCode(gc)                                   Query code at a given address.\n");
		sb.append("getTotalTransactionCount(gtc)                 Query total transaction count.\n");
		sb.append("deploy(d)                                     Deploy a contract on blockchain.\n");
		sb.append("call(c)                                       Call a contract by a function and paramters.\n");
		sb.append("deployByCNS(dbc)                              Deploy a contract on blockchain by CNS.\n");
		sb.append(
				"callByCNS(cbc)                                Call a contract by a function and paramters by CNS.\n");
		sb.append(
				"queryCNS(qcs)                                 Query cns information by contract name and contract version.\n");
		sb.append("addMiner(am)                                  Add a miner node.\n");
		sb.append("addObserver(ao)                               Add an observer node.\n");
		sb.append("removeNode(rn)                                Remove a node.\n");
		sb.append("addUserTableManager(aum)                      Add authority for user table by table name and address.\n");
		sb.append("removeUserTableManager(rum)                   Remove authority for user table by table name and address.\n");
		sb.append("queryUserTableManager(qum)                    Query authority for user table information.\n");
		sb.append("addDeployAndCreateManager(adm)                Add authority for deploy contract and create user table by address.\n");
		sb.append("removeDeployAndCreateManager(rdm)             Remove authority for deploy contract and create user table by address.\n");
		sb.append("queryDeployAndCreateManager(qdm)              Query authority information for deploy contract and create user table.\n");
		sb.append("addAuthorityManager(aum)                      Add authority for authority configuration by address.\n");
		sb.append("removeAuthorityManager(rum)                   Remove authority for authority configuration by address.\n");
		sb.append("queryAuthorityManager(qum)                    Query authority information for authority configuration.\n");
		sb.append("addNodeManager(anm)                           Add authority for node configuration by address.\n");
		sb.append("removeNodeManager(rnm)                        Remove authority for node configuration by address.\n");
		sb.append("queryNodeManager(qnm)                         Query authority information for node configuration.\n");
		sb.append("addCNSManager(acm)                            Add authority for CNS by address.\n");
		sb.append("removeCNSManager(rcm)                         Remove authority for CNS by address.\n");
		sb.append("queryCNSManager(qcm)                          Query authority information for CNS.\n");
		sb.append("addSysConfigManager(asm)                      Add authority for system configuration by address.\n");
		sb.append("removeSysConfigManager(rsm)                   Remove authority for system configuration by address.\n");
		sb.append("querySysConfigManager(qsm)                    Query authority information for system configuration.\n");
		sb.append("setSystemConfigByKey(ssc)                     Set a system config.\n");
		sb.append("getSystemConfigByKey(gsc)                     Query a system config value by key.\n");
		sb.append("quit(q)                                       Quit console.");
		System.out.println(sb.toString());
		ConsoleUtils.singleLine();
		System.out.println();
	}

	private boolean promptNoParams(String[] params, String funcName) {
		if (params.length == 2) {
			if ("-h".equals(params[1]) || "--help".equals(params[1])) {
				HelpInfo.helpNoParams(funcName);
				return true;
			} else {
				HelpInfo.promptHelp(funcName);
				return true;
			}
		} else if (params.length > 2) {
			HelpInfo.promptHelp(funcName);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void getClientVersion(String[] params) throws IOException {
		if (promptNoParams(params, "gcv")) {
			return;
		}
		String clientVersion = web3j.getClientVersion().sendForReturnString();
		ConsoleUtils.printJson(clientVersion);
		System.out.println();
	}
	
	@Override
	public void getBlockNumber(String[] params) throws IOException {
		if (promptNoParams(params, "gbn")) {
			return;
		}
		String blockNumber = web3j.getBlockNumber().sendForReturnString();
		System.out.println(Numeric.decodeQuantity(blockNumber));
		System.out.println();
	}

	@Override
	public void getPbftView(String[] params) throws IOException {
		if (promptNoParams(params, "gpv")) {
			return;
		}
		String pbftView = web3j.getPbftView().sendForReturnString();
		System.out.println(Numeric.decodeQuantity(pbftView));
		System.out.println();
	}

	@Override
	public void getObserverList(String[] params) throws IOException {
		if (promptNoParams(params, "gol")) {
			return;
		}
		List<String> observerList = web3j.getObserverList().send().getResult();
		String observers = observerList.toString();
		if ("[]".equals(observers)) {
			System.out.println("[]");
		} else {
			ConsoleUtils.printJson(observers);
		}
		System.out.println();

	}

	@Override
	public void getMinerList(String[] params) throws IOException {
		if (promptNoParams(params, "gml")) {
			return;
		}
		List<String> minerList = web3j.getMinerList().send().getResult();
		String miners = minerList.toString();
		if ("[]".equals(miners)) {
			System.out.println("[]");
		} else {
			ConsoleUtils.printJson(miners);
		}
		System.out.println();

	}

	@Override
	public void getConsensusStatus(String[] params) throws IOException {
		if (promptNoParams(params, "gcs")) {
			return;
		}
		String consensusStatus = web3j.getConsensusStatus().sendForReturnString();
		ConsoleUtils.printJson(consensusStatus);
		System.out.println();
	}

	@Override
	public void getSyncStatus(String[] params) throws IOException {
		if (promptNoParams(params, "gss")) {
			return;
		}
		String syncStatus = web3j.getSyncStatus().sendForReturnString();
		ConsoleUtils.printJson(syncStatus);
		System.out.println();
	}
	
	@Override
	public void getPeers(String[] params) throws IOException {
		if (promptNoParams(params, "gps")) {
			return;
		}
		String peers = web3j.getPeers().sendForReturnString();
		ConsoleUtils.printJson(peers);
		System.out.println();
	}

	@Override
	public void getNodeIDList(String[] params) throws IOException {
		if (promptNoParams(params, "gnl")) {
			return;
		}
		List<String> nodeIDs = web3j.getNodeIDList().send().getResult();
		ConsoleUtils.printJson(nodeIDs.toString());
		System.out.println();
	}

	@Override
	public void getGroupPeers(String[] params) throws IOException {
		if (promptNoParams(params, "ggp")) {
			return;
		}
		List<String> groupPeers = web3j.getGroupPeers().send().getResult();
		ConsoleUtils.printJson(groupPeers.toString());
		System.out.println();
	}

	@Override
	public void getGroupList(String[] params) throws IOException {
		if (promptNoParams(params, "ggl")) {
			return;
		}
		List<String> groupList = web3j.getGroupList().send().getResult();
		System.out.println(groupList);
		System.out.println();
	}

	@Override
	public void getBlockByHash(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gbbh");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("gbbh");
			return;
		}
		String blockHash = params[1];
		if ("-h".equals(blockHash) || "--help".equals(blockHash)) {
			HelpInfo.getBlockByHashHelp();
			return;
		}
		if (ConsoleUtils.isInvalidHash(blockHash))
			return;
		boolean flag = false;
		if (params.length == 3) {
			if ("true".equals(params[2])) {
				flag = true;
			} else if ("false".equals(params[2])) {
				flag = false;
			} else {
				System.out.println("Please provide true or false for the second parameter.");
				System.out.println();
				return;
			}
		}
		String block = web3j.getBlockByHash(blockHash, flag).sendForReturnString();
		ConsoleUtils.printJson(block);
		System.out.println();

	}

	@Override
	public void getBlockByNumber(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gbbn");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("gbbn");
			return;
		}
		String blockNumberStr1 = params[1];
		if ("-h".equals(blockNumberStr1) || "--help".equals(blockNumberStr1)) {
			HelpInfo.getBlockByNumberHelp();
			return;
		}
		if (ConsoleUtils.isInvalidNumber(blockNumberStr1, 0))
			return;
		BigInteger blockNumber1 = new BigInteger(blockNumberStr1);
		String blockNumberStr2 = web3j.getBlockNumber().sendForReturnString();
		BigInteger blockNumber2 = Numeric.decodeQuantity(blockNumberStr2);
		if (blockNumber1.compareTo(blockNumber2) == 1) {
			System.out.println("BlockNumber does not exist.");
			System.out.println();
			return;
		}
		boolean flag = false;
		if (params.length == 3) {
			if ("true".equals(params[2])) {
				flag = true;
			} else if ("false".equals(params[2])) {
				flag = false;
			} else {
				System.out.println("Please provide true or false for the second parameter.");
				System.out.println();
				return;
			}
		}
		String block = web3j.getBlockByNumber(DefaultBlockParameter.valueOf(blockNumber1), flag)
				.sendForReturnString();
		ConsoleUtils.printJson(block);
		System.out.println();
	}

	@Override
	public void getBlockHashByNumber(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("ghbn");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("ghbn");
			return;
		}
		String blockNumberStr = params[1];
		if ("-h".equals(blockNumberStr) || "--help".equals(blockNumberStr)) {
			HelpInfo.getBlockHashByNumberHelp();
			return;
		}
		if (ConsoleUtils.isInvalidNumber(blockNumberStr, 0))
			return;
		BigInteger blockNumber = new BigInteger(blockNumberStr);
		BigInteger getBlockNumber = Numeric.decodeQuantity(web3j.getBlockNumber().sendForReturnString());
		if (blockNumber.compareTo(getBlockNumber) == 1) {
			System.out.println("This block number doesn't exsit.");
			System.out.println();
			return;
		}
		String blockHash = web3j.getBlockHashByNumber(DefaultBlockParameter.valueOf(blockNumber)).sendForReturnString();
		ConsoleUtils.printJson(blockHash);
		System.out.println();
	}

	@Override
	public void getTransactionByHash(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gtbh");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("gtbh");
			return;
		}
		String transactionHash = params[1];
		if ("-h".equals(transactionHash) || "--help".equals(transactionHash)) {
			HelpInfo.getTransactionByHashHelp();
			return;
		}
		if (ConsoleUtils.isInvalidHash(transactionHash))
			return;
		String transaction = web3j.getTransactionByHash(transactionHash).sendForReturnString();
		if ("null".equals(transaction)) {
			System.out.println("This transaction hash doesn't exist.");
			return;
		}
		ConsoleUtils.printJson(transaction);
		System.out.println();
	}

	@Override
	public void getTransactionByBlockHashAndIndex(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gthi");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("gthi");
			return;
		}
		String blockHash = params[1];
		if ("-h".equals(blockHash) || "--help".equals(blockHash)) {
			HelpInfo.getTransactionByBlockHashAndIndexHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("gthi");
			return;
		}
		if (ConsoleUtils.isInvalidHash(blockHash))
			return;
		String indexStr = params[2];
		if (ConsoleUtils.isInvalidNumber(indexStr, 1))
			return;
		BigInteger index = new BigInteger(indexStr);
		String transaction = web3j.getTransactionByBlockHashAndIndex(blockHash, index).sendForReturnString();
		ConsoleUtils.printJson(transaction);
		System.out.println();
	}

	@Override
	public void getTransactionByBlockNumberAndIndex(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gtni");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("gtni");
			return;
		}
		String blockNumberStr = params[1];
		if ("-h".equals(blockNumberStr) || "--help".equals(blockNumberStr)) {
			HelpInfo.getTransactionByBlockNumberAndIndexHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("gtni");
			return;
		}
		if (ConsoleUtils.isInvalidNumber(blockNumberStr, 0))
			return;
		BigInteger blockNumber = new BigInteger(blockNumberStr);
		String indexStr = params[2];
		if (ConsoleUtils.isInvalidNumber(indexStr, 1))
			return;
		BigInteger index = new BigInteger(indexStr);
		String transaction = web3j
				.getTransactionByBlockNumberAndIndex(DefaultBlockParameter.valueOf(blockNumber), index)
				.sendForReturnString();
		ConsoleUtils.printJson(transaction);
		System.out.println();
	}

	@Override
	public void getTransactionReceipt(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gtr");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("gtr");
			return;
		}
		String transactionHash = params[1];
		if ("-h".equals(transactionHash) || "--help".equals(transactionHash)) {
			HelpInfo.getTransactionReceiptHelp();
			return;
		}
		if (ConsoleUtils.isInvalidHash(transactionHash))
			return;
		String transactionReceipt = web3j.getTransactionReceipt(transactionHash).sendForReturnString();
		if ("null".equals(transactionReceipt)) {
			System.out.println("This transaction hash doesn't exist.");
			return;
		}
		ConsoleUtils.printJson(transactionReceipt);
		System.out.println();
	}

	@Override
	public void getPendingTxSize(String[] params) throws IOException {
		if (promptNoParams(params, "gpts")) {
			return;
		}
		String size = web3j.getPendingTxSize().sendForReturnString();
		System.out.println(Numeric.decodeQuantity(size));
		System.out.println();
	}

	@Override
	public void getPendingTransactions(String[] params) throws IOException {
		if (promptNoParams(params, "gpt")) {
			return;
		}
		String pendingTransactions = web3j.getPendingTransaction().sendForReturnString();
		if ("[]".equals(pendingTransactions))
			System.out.println(pendingTransactions);
		else
			ConsoleUtils.printJson(pendingTransactions);
		System.out.println();
	}

	@Override
	public void getCode(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gc");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("gc");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.getCodeHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		String code = web3j.getCode(address, DefaultBlockParameterName.LATEST).sendForReturnString();
		if ("0x".equals(code)) {
			System.out.println("This address doesn't exist.");
			System.out.println();
			return;
		}
		ConsoleUtils.printJson(code);
		System.out.println();
	}

	@Override
	public void getTotalTransactionCount(String[] params) throws IOException {
		if (promptNoParams(params, "gtc")) {
			return;
		}
		String transactionCount = web3j.getTotalTransactionCount().sendForReturnString();
		JSONObject jo = JSONObject.parseObject(transactionCount);
		jo.put("txSum", Numeric.decodeQuantity(jo.get("txSum").toString()));
		jo.put("blockNumber", Numeric.decodeQuantity(jo.get("blockNumber").toString()));
		ConsoleUtils.printJson(jo.toJSONString());
		System.out.println();
	}

	@Override
	public void deploy(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("d");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("d");
			return;
		}
		if ("-h".equals(params[1]) || "--help".equals(params[1])) {
			HelpInfo.deployHelp();
			return;
		}
		contractName = CONTRACT_PACKAGE + params[1];
		contractClass = ContractClassFactory.getContractClass(contractName);
		Method deploy = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		remoteCall = (RemoteCall<?>) deploy.invoke(null, web3j, credentials, gasPrice, gasLimit);
		Contract contract = (Contract) remoteCall.send();
		contractAddress = contract.getContractAddress();
		System.out.println(contractAddress);
		System.out.println();
	}

	@Override
	public void call(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("c");
			return;
		}
		if ("-h".equals(params[1]) || "--help".equals(params[1])) {
			HelpInfo.callHelp();
			return;
		}
		if (params.length < 4) {
			HelpInfo.promptHelp("c");
			return;
		}
		contractName = CONTRACT_PACKAGE + params[1];
		contractClass = ContractClassFactory.getContractClass(contractName);
		Method load = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		Object contractObject;

		contractAddress = params[2];
		if (ConsoleUtils.isInvalidAddress(contractAddress)) {
			return;
		}
		contractObject = load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);
		Method[] methods = contractClass.getMethods();
		String funcName = params[3];
		Class[] parameterType = ContractClassFactory.getParameterType(contractClass, funcName, params.length - 4);
		if(parameterType == null)
		{
			HelpInfo.promptNoFunc(params[1], funcName, params.length - 4);
			return;
		}
		Method func = contractClass.getMethod(funcName, parameterType);
		Object[] argobj = ContractClassFactory.getPrametersObject(funcName, parameterType, params);
		if (argobj == null) {
			return;
		}
		String returnType = ContractClassFactory.getReturnType(contractClass, funcName, parameterType);
		if(returnType == null)
		{
			HelpInfo.promptNoFunc(params[1], funcName, params.length - 4);
			return;
		}
		remoteCall = (RemoteCall<?>) func.invoke(contractObject, argobj);
		Object result;
		try {
			result = remoteCall.send();
		} catch (Exception e) {
			System.out.println("The contract "+ params[1] +" for address " + contractAddress + " doesn't exsit.");
			System.out.println();
			return;
		}
		String resultStr;
		resultStr = ContractClassFactory.getReturnObject(returnType, result);
		System.out.println(resultStr);
		System.out.println();

	}

	@Override
	public void deployByCNS(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("dbc");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("dbc");
			return;
		}
		if ("-h".equals(params[1]) || "--help".equals(params[1])) {
			HelpInfo.deployByCNSHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("dbc");
			return;
		}
		AuthorityService authorityTableService = new AuthorityService(web3j, credentials);
		List<AuthorityInfo> authoritys = authorityTableService.queryCNSManager();
		boolean flag = false;
		if (authoritys.size() == 0) {
			flag = true;
		} else {
			for (AuthorityInfo authority : authoritys) {
				if ((credentials.getAddress()).equals(authority.getAddress())) {
					flag = true;
					break;
				}
			}
		}
		if (!flag) {
			ConsoleUtils.printJson(PrecompiledCommon.transferToJson(-1));
			System.out.println();
			return;
		}
		CnsService cnsService = new CnsService(web3j, credentials);
		List<CnsInfo> qcns = cnsService.queryCnsByNameAndVersion(params[1], params[2]);
		if(qcns.size() != 0)
		{
			ConsoleUtils.printJson(PrecompiledCommon.transferToJson(-50));
			System.out.println();
			return;
		}
		contractName = CONTRACT_PACKAGE + params[1];
		contractClass = ContractClassFactory.getContractClass(contractName);
		Method deploy = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		remoteCall = (RemoteCall<?>) deploy.invoke(null, web3j, credentials, gasPrice, gasLimit);
		contractVersion = params[2];
        if(contractVersion.length() > CnsService.MAX_VERSION_LENGTH)
        {
        	ConsoleUtils.printJson(PrecompiledCommon.transferToJson(-51));
        	System.out.println();
        	return;
        }
		Contract contract = (Contract) remoteCall.send();
		contractAddress = contract.getContractAddress();
		// register cns
		String result = cnsService.registerCns(params[1], contractVersion, contractAddress, "");
		System.out.println(contractAddress);
		System.out.println();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void callByCNS(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("cbc");
			return;
		}
		if ("-h".equals(params[1]) || "--help".equals(params[1])) {
			HelpInfo.callByCNSHelp();
			return;
		}
		if (params.length < 4) {
			HelpInfo.promptHelp("cbc");
			return;
		}
		contractName = CONTRACT_PACKAGE + params[1];
		contractClass = ContractClassFactory.getContractClass(contractName);
		Method load = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		Object contractObject;

		// get address from cns
		contractName = params[1];
		contractVersion = params[2];
		CnsService cnsResolver = new CnsService(web3j, credentials);
		try {
			contractAddress = cnsResolver.getAddressByContractNameAndVersion(contractName + ":" + contractVersion);
		} catch (Exception e) {
			System.out.println("The contract "+ contractName +" for version " + contractVersion + " doesn't exsit.");
			System.out.println();
			return;
		}
		contractObject = load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);

		Method[] methods = contractClass.getMethods();
		String funcName = params[3];
		Class[] parameterType = ContractClassFactory.getParameterType(contractClass, funcName, params.length - 4);
		if(parameterType == null)
		{
			HelpInfo.promptNoFunc(params[1], funcName, params.length - 4);
			return;
		}
		Method func = contractClass.getMethod(funcName, parameterType);
		Object[] argobj = ContractClassFactory.getPrametersObject(funcName, parameterType, params);
		if (argobj == null) {
			return;
		}
		String returnType = ContractClassFactory.getReturnType(contractClass, funcName, parameterType);
		if(returnType == null)
		{
			HelpInfo.promptNoFunc(params[1], funcName, params.length - 4);
			return;
		}
		remoteCall = (RemoteCall<?>) func.invoke(contractObject, argobj);
		Object result;
		result = remoteCall.send();
		String resultStr;
		resultStr = ContractClassFactory.getReturnObject(returnType, result);
		System.out.println(resultStr);
		System.out.println();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void queryCNS(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("qcs");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("qcs");
			return;
		}
		if ("-h".equals(params[1]) || "--help".equals(params[1])) {
			HelpInfo.queryCNSHelp();
			return;
		}

		CnsService cnsService = new CnsService(web3j, credentials);
		List<CnsInfo> cnsInfos = new ArrayList<>();
		contractName = params[1];
		if (params.length == 3) {
			contractVersion = params[2];
			cnsInfos = cnsService.queryCnsByNameAndVersion(contractName, contractVersion);
		} else {
			cnsInfos = cnsService.queryCnsByName(contractName);
		}

		if (cnsInfos.isEmpty()) {
			System.out.println("Empty set.");
			System.out.println();
			return;
		}
		ConsoleUtils.singleLineForTable();
		String[] headers = { "version", "address" };
		int size = cnsInfos.size();
		String[][] data = new String[size][2];
		for (int i = 0; i < size; i++) {
			data[i][0] = cnsInfos.get(i).getVersion();
			data[i][1] = cnsInfos.get(i).getAddress();
		}
		ColumnFormatter<String> cf = ColumnFormatter.text(Alignment.CENTER, 45);
		Table table = Table.of(headers, data, cf);
		System.out.println(table);
		ConsoleUtils.singleLineForTable();
		System.out.println();
	}

	@Override
	public void addMiner(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("am");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("am");
			return;
		}
		String nodeID = params[1];
		if ("-h".equals(nodeID) || "--help".equals(nodeID)) {
			HelpInfo.addMinerHelp();
			return;
		}
		if (nodeID.length() != 128) {
			ConsoleUtils.printJson(PrecompiledCommon.transferToJson(-40));
		} else {
			ConsensusService consensusService = new ConsensusService(web3j, credentials);
			String result = consensusService.addMiner(nodeID);
			ConsoleUtils.printJson(result);
		}
		System.out.println();

	}

	@Override
	public void addObserver(String[] params) throws Exception {

		if (params.length < 2) {
			HelpInfo.promptHelp("ao");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("ao");
			return;
		}
		String nodeID = params[1];
		if ("-h".equals(nodeID) || "--help".equals(nodeID)) {
			HelpInfo.addObserverHelp();
			return;
		}
		if (nodeID.length() != 128) {
			ConsoleUtils.printJson(PrecompiledCommon.transferToJson(-40));
		} else {
			ConsensusService consensusService = new ConsensusService(web3j, credentials);
			String result = consensusService.addObserver(nodeID);
			ConsoleUtils.printJson(result);
		}
		System.out.println();

	}

	@Override
	public void removeNode(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("rn");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("rn");
			return;
		}
		String nodeID = params[1];
		if ("-h".equals(nodeID) || "--help".equals(nodeID)) {
			HelpInfo.removeNodeHelp();
			return;
		}
		if (nodeID.length() != 128) {
			ConsoleUtils.printJson(PrecompiledCommon.transferToJson(-40));
		} else {
			ConsensusService consensusService = new ConsensusService(web3j, credentials);
			String result = consensusService.removeNode(nodeID);
			ConsoleUtils.printJson(result);
		}
		System.out.println();

	}

	@Override
	public void addUserTableManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("aum");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("aum");
			return;
		}
		String tableName = params[1];
		if ("-h".equals(tableName) || "--help".equals(tableName)) {
			HelpInfo.addUserTableManagerHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("aum");
			return;
		}
		String addr = params[2];
		if (ConsoleUtils.isInvalidAddress(addr)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.addUserTableManager(tableName, addr);
		ConsoleUtils.printJson(result);
		System.out.println();

	}

	@Override
	public void removeUserTableManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("rum");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("rum");
			return;
		}
		String tableName = params[1];
		if ("-h".equals(tableName) || "--help".equals(tableName)) {
			HelpInfo.removeUserTableManagerHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("rum");
			return;
		}
		String addr = params[2];
		if (ConsoleUtils.isInvalidAddress(addr)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.removeUserTableManager(tableName, addr);
		ConsoleUtils.printJson(result);
		System.out.println();

	}

	@Override
	public void queryUserTableManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("qum");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("qum");
			return;
		}
		String tableName = params[1];
		if ("-h".equals(tableName) || "--help".equals(tableName)) {
			HelpInfo.queryUserTableManagerHelp();
			return;
		}
		AuthorityService authorityTableService = new AuthorityService(web3j, credentials);
		List<AuthorityInfo> authoritys = authorityTableService.queryUserTableManager(tableName);
		printAuthorityInfo(authoritys);
	}
	
	@Override
	public void addDeployAndCreateManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("adm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("adm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.addDeployAndCreateManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.addDeployAndCreateManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void removeDeployAndCreateManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("rdm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("rdm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.removeDeployAndCreateManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.removeDeployAndCreateManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void queryDeployAndCreateManager(String[] params) throws Exception {
		if (promptNoParams(params, "qdm")) {
			return;
		}
		AuthorityService authorityTableService = new AuthorityService(web3j, credentials);
		List<AuthorityInfo> authoritys = authorityTableService.queryDeployAndCreateManager();
		printAuthorityInfo(authoritys);
	}
	
	@Override
	public void addAuthorityManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("aam");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("aam");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.addAuthorityManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.addAuthorityManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void removeAuthorityManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("ram");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("ram");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.removeAuthorityManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.removeAuthorityManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void queryAuthorityManager(String[] params) throws Exception {
		if (promptNoParams(params, "qam")) {
			return;
		}
		AuthorityService authorityTableService = new AuthorityService(web3j, credentials);
		List<AuthorityInfo> authoritys = authorityTableService.queryAuthorityManager();
		printAuthorityInfo(authoritys);
	}
	
	@Override
	public void addNodeManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("anm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("anm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.addNodeManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.addNodeManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void removeNodeManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("rnm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("rnm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.removeNodeManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.removeNodeManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void queryNodeManager(String[] params) throws Exception {
		if (promptNoParams(params, "qnm")) {
			return;
		}
		AuthorityService authorityTableService = new AuthorityService(web3j, credentials);
		List<AuthorityInfo> authoritys = authorityTableService.queryNodeManager();
		printAuthorityInfo(authoritys);
	}
	
	@Override
	public void addCNSManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("acm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("acm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.addCNSManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.addCNSManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void removeCNSManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("rcm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("rcm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.removeCNSManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.removeCNSManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void queryCNSManager(String[] params) throws Exception {
		if (promptNoParams(params, "qcm")) {
			return;
		}
		AuthorityService authorityTableService = new AuthorityService(web3j, credentials);
		List<AuthorityInfo> authoritys = authorityTableService.queryCNSManager();
		printAuthorityInfo(authoritys);
	}
	
	@Override
	public void addSysConfigManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("asm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("asm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.addSysConfigManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.addSysConfigManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void removeSysConfigManager(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("rsm");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("rsm");
			return;
		}
		String address = params[1];
		if ("-h".equals(address) || "--help".equals(address)) {
			HelpInfo.removeSysConfigManagerHelp();
			return;
		}
		if (ConsoleUtils.isInvalidAddress(address)) {
			return;
		}
		AuthorityService authority = new AuthorityService(web3j, credentials);
		String result = authority.removeSysConfigManager(address);
		ConsoleUtils.printJson(result);
		System.out.println();
	}
	
	@Override
	public void querySysConfigManager(String[] params) throws Exception {
		if (promptNoParams(params, "qsm")) {
			return;
		}
		AuthorityService authorityTableService = new AuthorityService(web3j, credentials);
		List<AuthorityInfo> authoritys = authorityTableService.querySysConfigManager();
		printAuthorityInfo(authoritys);
	}
	
	@Override
	public void setSystemConfigByKey(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("ssc");
			return;
		} else if (params.length > 3) {
			HelpInfo.promptHelp("ssc");
			return;
		}
		String key = params[1];
		if ("-h".equals(key) || "--help".equals(key)) {
			HelpInfo.setSystemConfigByKeyHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("ssc");
			return;
		}
		String value = params[2];

		String[] args = { "setSystemConfig", key, value };
		SystemConfigSerivce systemConfigSerivce = new SystemConfigSerivce(web3j, credentials);
		String result = systemConfigSerivce.setValueByKey(key, value);
		ConsoleUtils.printJson(result);
		System.out.println();
	}

	@Override
	public void getSystemConfigByKey(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("gsc");
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("gsc");
			return;
		}
		String key = params[1];
		if ("-h".equals(key) || "--help".equals(key)) {
			HelpInfo.getSystemConfigByKeyHelp();
			return;
		}
		String[] args = { "getSystemConfigByKey", key };
		String value = web3j.getSystemConfigByKey(key).sendForReturnString();
		System.out.println(value);
		System.out.println();
	}

	@Override
	public void quit(String[] params) throws IOException {
		if (promptNoParams(params, "q")) {
			return;
		} else if (params.length > 2) {
			HelpInfo.promptHelp("q");
			return;
		}
		channelEthereumService.close();
		System.exit(0);
	}
	
	private void printAuthorityInfo(List<AuthorityInfo> authoritys) {
		if (authoritys.isEmpty()) {
			System.out.println("Empty set.");
			System.out.println();
			return;
		}
		ConsoleUtils.singleLineForTable();
		String[] headers = { "address", "enable_num" };
		int size = authoritys.size();
		String[][] data = new String[size][2];
		for (int i = 0; i < size; i++) {
			data[i][0] = authoritys.get(i).getAddress();
			data[i][1] = authoritys.get(i).getEnableNum();
		}
		ColumnFormatter<String> cf = ColumnFormatter.text(Alignment.CENTER, 45);
		Table table = Table.of(headers, data, cf);
		System.out.println(table);
		ConsoleUtils.singleLineForTable();
		System.out.println();
	}
}
