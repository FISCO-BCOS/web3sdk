package org.fisco.bcos.web3j.console;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.List;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.test.precompile.UpdatePBFTNode;
import org.fisco.bcos.web3j.cns.CnsResolver;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

public class ConsoleImpl implements ConsoleFace{
	
	private static Service service = null;
	private static Web3j web3j = null;
	private static java.math.BigInteger gasPrice = new BigInteger("1");
	private static java.math.BigInteger gasLimit = new BigInteger("30000000");
	private static ECKeyPair keyPair;
	private static Credentials credentials;
	private static String contractAddress;
	private static String contractName;
	private static String contractVersion;
	private static Class<?> contractClass;
	private static RemoteCall<?> remoteCall;

	@SuppressWarnings("resource")
	public ConsoleImpl(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		service = context.getBean(Service.class);
		try {
			service.run();
		} catch (Exception e) {
			System.out.println("Failed to initialize the client-side SSLContext, please checkout ca.crt File!");
			System.exit(1);
		}
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		try {
			keyPair = Keys.createEcKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		credentials = Credentials.create(keyPair);

		web3j = Web3j.build(channelEthereumService, service.getGroupId());
	}
	
	@Override
	public void welcome() {
		ConsoleUtils.doubleLine();
		System.out.println("Welcome to FISCO BCOS 2.0!");
		System.out.println("Type 'help' or 'h' for help. Type 'quit' or 'q' to quit console.");
		String logo = " ________  ______   ______    ______    ______         _______    ______    ______    ______  \n"
				+ "|        \\|      \\ /      \\  /      \\  /      \\       |       \\  /      \\  /      \\  /      \\ \n"
				+ "| $$$$$$$$ \\$$$$$$|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\      | $$$$$$$\\|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\\n"
				+ "| $$__      | $$  | $$___\\$$| $$   \\$$| $$  | $$      | $$__/ $$| $$   \\$$| $$  | $$| $$___\\$$\n"
				+ "| $$  \\     | $$   \\$$    \\ | $$      | $$  | $$      | $$    $$| $$      | $$  | $$ \\$$    \\ \n"
				+ "| $$$$$     | $$   _\\$$$$$$\\| $$   __ | $$  | $$      | $$$$$$$\\| $$   __ | $$  | $$ _\\$$$$$$\\\n"
				+ "| $$       _| $$_ |  \\__| $$| $$__/  \\| $$__/ $$      | $$__/ $$| $$__/  \\| $$__/ $$|  \\__| $$\n"
				+ "| $$      |   $$ \\ \\$$    $$ \\$$    $$ \\$$    $$      | $$    $$ \\$$    $$ \\$$    $$ \\$$    $$\n"
				+ " \\$$       \\$$$$$$  \\$$$$$$   \\$$$$$$   \\$$$$$$        \\$$$$$$$   \\$$$$$$   \\$$$$$$   \\$$$$$$";
		System.out.println(logo);
		System.out.println();
		ConsoleUtils.doubleLine();
	}
	
	@Override
	public void help() {
		ConsoleUtils.singleLine();
		StringBuilder sb = new StringBuilder();
		sb.append("help(h)                                       Provide help information.\n");
		sb.append("getBlockNumber(gbn)                           Query the number of most recent block.\n");
		sb.append("getPbftView(gpv)                              Query the pbft view of node.\n");
		sb.append("getMinerList(gml)                             Query the pbft miner nodes.\n");
		sb.append("getObserverList(gol)                          Query the pbft observer node.\n");
		sb.append("getConsensusStatus(gcs)                       Query consensus status.\n");
		sb.append("getSyncStatus(gss)                            Query sync status.\n");
		sb.append("getClientVersion(gcv)                         Query the current client version.\n");
		sb.append("getPeers(gps)                                 Query peers currently connected to the client.\n");
		sb.append(
				"getGroupPeers(ggp)                            Query peers currently connected to the client in the specified group.\n");
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
		sb.append("getCode(gc)                                   Query code at a given address.\n");
		sb.append("getTotalTransactionCount(gtc)                 Query total transaction count.\n");
		sb.append("deploy(d)                                     Deploy a contract on blockchain.\n");
		sb.append("call(c)                                       Call a contract by a function and paramters.\n");
		sb.append("addMiner(am)                                  Add a miner node.\n");
		sb.append("addObserver(ao)                               Add an observer node.\n");
		sb.append("removeNode(rn)                                Remove a node.\n");
		sb.append("quit(q)                                       Quit console.");
		System.out.println(sb.toString());
		ConsoleUtils.singleLine();
	}
	
	@Override
	public void getBlockNumber() throws IOException {
		String blockNumber = web3j.ethBlockNumber().sendForReturnString();
		System.out.println(Numeric.decodeQuantity(blockNumber));
		System.out.println();
	}
	
	@Override
	public void getPbftView() throws IOException {
		String pbftView = web3j.ethPbftView().sendForReturnString();
		System.out.println(Numeric.decodeQuantity(pbftView));
		System.out.println();
	}
	
	@Override
	public void getObserverList() throws IOException {
		List<String> observerList = web3j.getObserverList().send().getResult();
		String observers = observerList.toString();
		if("[]".equals(observers))
		{
			System.out.println("[]");
		}
		else
		{
			ConsoleUtils.printJson(observers);
		}
		System.out.println();

	}
	
	@Override
	public void getMinerList() throws IOException {
		List<String> minerList = web3j.getMinerList().send().getResult();
		String miners = minerList.toString();
		if("[]".equals(miners))
		{
			System.out.println("[]");
		}
		else
		{
			ConsoleUtils.printJson(miners);
		}
		System.out.println();

	}
	
	@Override
	public void getConsensusStatus() throws IOException {
		String consensusStatus = web3j.consensusStatus().sendForReturnString();
		ConsoleUtils.printJson(consensusStatus);
		System.out.println();
	}
	
	@Override
	public void getSyncStatus() throws IOException {
		String syncStatus = web3j.ethSyncing().sendForReturnString();
		ConsoleUtils.printJson(syncStatus);
		System.out.println();
	}
	
	@Override
	public void getClientVersion() throws IOException {
		String clientVersion = web3j.web3ClientVersion().sendForReturnString();
		System.out.println(clientVersion);
		System.out.println();
	}
	
	@Override
	public void getPeers() throws IOException {
		String peers = web3j.ethPeersInfo().sendForReturnString();
		ConsoleUtils.printJson(peers);
		System.out.println();
	}
	
	@Override
	public void getGroupPeers() throws IOException {
		List<String> groupPeers = web3j.ethGroupPeers().send().getResult();
		ConsoleUtils.printJson(groupPeers.toString());
		System.out.println();
	}
	
	@Override
	public void getGroupList() throws IOException {
		List<String> groupList = web3j.ethGroupList().send().getResult();
		System.out.println(groupList);
		System.out.println();
	}
	
	@Override
	public void getBlockByHash(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gbbh");
			return;
		}
		String blockHash = params[1];
		if("-h".equals(blockHash) || "--help".equals(blockHash))
		{
			HelpInfo.getBlockByHashHelp();
			return;
		}
		if (ConsoleUtils.isInvalidHash(blockHash))
			return;
		boolean flag = false;
		if (params.length == 3 && "true".equals(params[2]))
			flag = true;
		String block = web3j.ethGetBlockByHash(blockHash, flag).sendForReturnString();
		ConsoleUtils.printJson(block);
		System.out.println();

	}
	
	@Override
	public void getBlockByNumber(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gbbn");
			return;
		}
		String blockNumberStr = params[1];
		if("-h".equals(blockNumberStr) || "--help".equals(blockNumberStr))
		{
			HelpInfo.getBlockByNumberHelp();
			return;
		}
		if(ConsoleUtils.isInvalidNumber(blockNumberStr, 0))
			return;
		BigInteger blockNumber = new BigInteger(blockNumberStr);
		boolean flag = false;
		if (params.length == 3 && "true".equals(params[2]))
			flag = true;
		String block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), flag)
				.sendForReturnString();
		ConsoleUtils.printJson(block);
		System.out.println();
	}
	
	@Override
	public void getBlockHashByNumber(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("ghbn");
			return;
		}
		String blockNumberStr = params[1];
		if("-h".equals(blockNumberStr) || "--help".equals(blockNumberStr))
		{
			HelpInfo.getBlockHashByNumberHelp();
			return;
		}
		if(ConsoleUtils.isInvalidNumber(blockNumberStr, 0))
			return;
		BigInteger blockNumber = new BigInteger(blockNumberStr);
		if (blockNumber.intValue() > Numeric.decodeQuantity(web3j.ethBlockNumber().sendForReturnString()).intValue()) {
			System.out.println("This block number doesn't exsit.");
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
		}
		String transactionHash = params[1];
		if("-h".equals(transactionHash) || "--help".equals(transactionHash))
		{
			HelpInfo.getTransactionByHashHelp();
			return;
		}
		if (ConsoleUtils.isInvalidHash(transactionHash))
			return;
		String transaction = web3j.ethGetTransactionByHash(transactionHash).sendForReturnString();
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
		}
		String blockHash = params[1];
		if("-h".equals(blockHash) || "--help".equals(blockHash))
		{
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
		if(ConsoleUtils.isInvalidNumber(indexStr, 1))
			return;
		BigInteger index = new BigInteger(indexStr);
		String transaction = web3j.ethGetTransactionByBlockHashAndIndex(blockHash, index).sendForReturnString();
		ConsoleUtils.printJson(transaction);
		System.out.println();
	}
	
	@Override
	public void getTransactionByBlockNumberAndIndex(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gtni");
			return;
		}
		String blockNumberStr = params[1];
		if("-h".equals(blockNumberStr) || "--help".equals(blockNumberStr))
		{
			HelpInfo.getTransactionByBlockNumberAndIndexHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("gtni");
			return;
		}
		if(ConsoleUtils.isInvalidNumber(blockNumberStr, 0))
			return;
		BigInteger blockNumber = new BigInteger(blockNumberStr);
		String indexStr = params[2];
		if(ConsoleUtils.isInvalidNumber(indexStr, 1))
			return;
		BigInteger index = new BigInteger(indexStr);
		String transaction = web3j
				.ethGetTransactionByBlockNumberAndIndex(DefaultBlockParameter.valueOf(blockNumber), index)
				.sendForReturnString();
		ConsoleUtils.printJson(transaction);
		System.out.println();
	}
	
	@Override
	public void getTransactionReceipt(String[] params) throws IOException {
		if (params.length < 2) {
			HelpInfo.promptHelp("gtr");
			return;
		}
		String transactionHash = params[1];
		if("-h".equals(transactionHash) || "--help".equals(transactionHash))
		{
			HelpInfo.getTransactionReceiptHelp();
			return;
		}
		if (ConsoleUtils.isInvalidHash(transactionHash))
			return;
		String transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).sendForReturnString();
		if ("null".equals(transactionReceipt)) {
			System.out.println("This transaction hash doesn't exist.");
			return;
		}
		ConsoleUtils.printJson(transactionReceipt);
		System.out.println();
	}
	
	@Override
	public void getPendingTransactions() throws IOException {
		String pendingTransactions = web3j.ethPendingTransaction().sendForReturnString();
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
		}
		String address = params[1];
		if("-h".equals(address) || "--help".equals(address))
		{
			HelpInfo.getCodeHelp();
			return;
		}
		if (!address.startsWith("0x") || !(address.length() == 42)) {
			System.out.println("This is an invalid address.");
			return;
		}
		String code = web3j.ethGetCode(address, DefaultBlockParameterName.LATEST).sendForReturnString();
		if ("0x".equals(code)) {
			System.out.println("This address doesn't exist.");
			return;
		}
		ConsoleUtils.printJson(code);
		System.out.println();
	}
	
	@Override
	public void getTotalTransactionCount() throws IOException {
		String transactionCount = web3j.getTotalTransactionCount().sendForReturnString();
		JSONObject jo = JSONObject.parseObject(transactionCount);
		jo.put("count", Numeric.decodeQuantity(jo.get("count").toString()));
		jo.put("number", Numeric.decodeQuantity(jo.get("number").toString()));
		ConsoleUtils.printJson(jo.toJSONString());
		System.out.println();
	}
	
	@Override
	public void deploy(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("d");
			return;
		}
		if("-h".equals(params[1]) || "--help".equals(params[1]))
		{
			HelpInfo.deployHelp();
			return;
		}
		contractName = "org.fisco.bcos.temp." + params[1];
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
		if("-h".equals(params[1]) || "--help".equals(params[1]))
		{
			HelpInfo.callHelp();
			return;
		}
		if (params.length < 4) {
			HelpInfo.promptHelp("c");
			return;
		}
		contractName = "org.fisco.bcos.temp." + params[1];
		contractClass = ContractClassFactory.getContractClass(contractName);
		Method load = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		Object contractObject;
		
		//get address from cns
		contractName = params[1];
		contractVersion = params[2];
		CnsResolver cnsResolver = new CnsResolver(web3j, credentials);
		contractAddress = cnsResolver.resolve(contractName+":"+contractVersion);
		
		contractObject = load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);
		Class[] parameterType = ContractClassFactory.getParameterType(contractClass, params[3]);
		String returnType = ContractClassFactory.getReturnType(contractClass, params[3]);
		Method func = contractClass.getMethod(params[3], parameterType);
		Object[] argobj = ContractClassFactory.getPrametersObject(parameterType, params);
		remoteCall = (RemoteCall<?>) func.invoke(contractObject, argobj);
		Object result;
		result = remoteCall.send();
		String resultStr;
		resultStr = ContractClassFactory.getReturnObject(returnType, result);
		System.out.println(resultStr);
		System.out.println();
	}
	
	@Override
	public void deployByCNS(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("d");
			return;
		}
		if("-h".equals(params[1]) || "--help".equals(params[1]))
		{
			HelpInfo.deployHelp();
			return;
		}
		if (params.length < 3) {
			HelpInfo.promptHelp("d");
			return;
		}
		contractName = "org.fisco.bcos.temp." + params[1];
		contractClass = ContractClassFactory.getContractClass(contractName);
		Method deploy = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		remoteCall = (RemoteCall<?>) deploy.invoke(null, web3j, credentials, gasPrice, gasLimit);
		Contract contract = (Contract) remoteCall.send();
		contractAddress = contract.getContractAddress();
		contractVersion = params[2];
		// register cns
		CnsResolver cnsResolver = new CnsResolver(web3j, credentials);
		TransactionReceipt registerCns = cnsResolver.registerCns(params[1], contractVersion, contractAddress, contract.getContractBinary());
		System.out.println(contractAddress);
		System.out.println();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void callByCNS(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("c");
			return;
		}
		if("-h".equals(params[1]) || "--help".equals(params[1]))
		{
			HelpInfo.callHelp();
			return;
		}
		if (params.length < 4) {
			HelpInfo.promptHelp("c");
			return;
		}
		contractName = "org.fisco.bcos.temp." + params[1];
		contractClass = ContractClassFactory.getContractClass(contractName);
		Method load = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		Object contractObject;
		
		//get address from cns
		contractName = params[1];
		contractVersion = params[2];
		CnsResolver cnsResolver = new CnsResolver(web3j, credentials);
		contractAddress = cnsResolver.resolve(contractName+":"+contractVersion);
		
		contractObject = load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);
		Class[] parameterType = ContractClassFactory.getParameterType(contractClass, params[3]);
		String returnType = ContractClassFactory.getReturnType(contractClass, params[3]);
		Method func = contractClass.getMethod(params[3], parameterType);
		Object[] argobj = ContractClassFactory.getPrametersObject(parameterType, params);
		remoteCall = (RemoteCall<?>) func.invoke(contractObject, argobj);
		Object result;
		result = remoteCall.send();
		String resultStr;
		resultStr = ContractClassFactory.getReturnObject(returnType, result);
		System.out.println(resultStr);
		System.out.println();
	}
	
	@Override
	public void addMiner(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("am");
			return;
		}
		String nodeID = params[1];
		if("-h".equals(nodeID) || "--help".equals(nodeID))
		{
			HelpInfo.addMinerHelp();
			return;
		}
		if (nodeID.length() != 128) {
			System.out.println("This is an invalid nodeID.");
		} 
		else {
			UpdatePBFTNode pbft = new UpdatePBFTNode();
			pbft.AddNodeToMiner(nodeID, web3j, credentials);
			System.out.println("Add " + nodeID.substring(0, 8) + "..." + " to a miner of group "
					+ service.getGroupId() + " successful.");
			System.out.println();
		}

	}
	
	@Override
	public void addObserver(String[] params) throws Exception {

		if (params.length < 2) {
			HelpInfo.promptHelp("ao");
			return;
		}
		String nodeID = params[1];
		if("-h".equals(nodeID) || "--help".equals(nodeID))
		{
			HelpInfo.addObserverHelp();
			return;
		}
		if (nodeID.length() != 128) {
			System.out.println("This is an invalid nodeID.");
		} 
		else {
			UpdatePBFTNode pbft = new UpdatePBFTNode();
			pbft.AddNodeToObserver(nodeID, web3j, credentials);
			System.out.println("Add " + nodeID.substring(0, 8) + "..." + " to an observer of group "
					+ service.getGroupId() + " successful.");
			System.out.println();
		}

	}
	
	@Override
	public void removeNode(String[] params) throws Exception {
		if (params.length < 2) {
			HelpInfo.promptHelp("rn");
			return;
		}
		String nodeID = params[1];
		if("-h".equals(nodeID) || "--help".equals(nodeID))
		{
			HelpInfo.removeNodeHelp();
			return;
		}
		if (nodeID.length() != 128) {
			System.out.println("This is an invalid nodeID.");
		} 
		else {
			UpdatePBFTNode pbft = new UpdatePBFTNode();
			pbft.RemoveNode(nodeID, web3j, credentials);
			System.out.println("Remove " + nodeID.substring(0, 8) + "..." + " in group "
					+ service.getGroupId() + " successful.");
			System.out.println();
		}
		
	}

}
