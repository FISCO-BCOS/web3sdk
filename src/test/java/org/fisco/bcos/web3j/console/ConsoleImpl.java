package org.fisco.bcos.web3j.console;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.List;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.test.precompile.UpdatePBFTNode;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
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
	private static Class<?> contractClass;
	private static RemoteCall<?> remoteCall;

	@SuppressWarnings("resource")
	public ConsoleImpl() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		service = context.getBean(Service.class);
		try {
			service.run();
		} catch (Exception e) {
			System.out.println(e);
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
		sb.append("deploy(d)                                     Deploy contract on blockchain.\n");
		sb.append("call(c)                                       Call contract by method and paramters.\n");
		sb.append("addPbft(ap)                                   Add Pbft nodes.\n");
		sb.append("removePbft(rp)                                Remove Pbft nodes.\n");
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
		ConsoleUtils.printJson(observerList.toString());
		System.out.println();

	}
	
	@Override
	public void getMinerList() throws IOException {
		List<String> minerList = web3j.getMinerList().send().getResult();
		ConsoleUtils.printJson(minerList.toString());
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
	public void getGroupPeers(String[] params) throws IOException {
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
			System.out.println(
					"getBlockByHash(gbbh): missing block hash and transaction detail flag(optional, false by default)");
			System.out.println("example: getBlockByHash 0x5e743a... true");
			return;
		}
		String blockHash = params[1];
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
			System.out.println(
					"getBlockByNumber(gbbn): missing block number and transaction detail flag(optional, false by default)");
			System.out.println("example: getBlockByNumber 1 true");
			return;
		}
		String blockNumberStr = params[1];
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
			System.out.println("getBlockHashByNumber(ghbn): missing block number");
			System.out.println("example: ghbn 1");
			return;
		}
		String blockNumberStr = params[1];
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
			System.out.println("getTransactionByHash(gtbh): missing transactions hash");
			System.out.println("example: gtbh 0x0x7536cf...");
			return;
		}
		String transactionHash = params[1];
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
		if (params.length < 3) {
			System.out.println("getTransactionByBlockHashAndIndex(gthi): missing block hash and transaction index");
			System.out.println("example: gthi 0x5e743a... 0");
			return;
		}
		String blockHash = params[1];
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
		if (params.length < 3) {
			System.out.println("getTransactionByBlockNumberAndIndex(gtni): missing block number and transaction index");
			System.out.println("example: gtni 1 0");
			return;
		}
		String blockNumberStr = params[1];
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
			System.out.println("getTransactionReceipt(gtr): missing transaction hash");
			System.out.println("example: gtr 0x7536cf...");
			return;
		}
		String transactionHash = params[1];
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
			System.out.println("getCode(gc): missing contract address");
			System.out.println("example: gc 0xa94f53...");
			return;
		}
		String address = params[1];
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
	public void getTotalTransactionCount(String[] params) throws IOException {
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
			System.out.println("deploy: missing contract name");
			System.out.println("example: deploy Ok");
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public void call(String[] params) throws Exception {
		if (params.length < 3) {
			System.out.println("call(c): missing method name");
			System.out.println("example: call get");
			return;
		}
		Method load = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class,
				BigInteger.class);
		Object contractObject;
		contractObject = load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);
		Class[] parameterType = ContractClassFactory.getParameterType(contractClass, params[1]);
		String returnType = ContractClassFactory.getReturnType(contractClass, params[1]);
		Method func = contractClass.getMethod(params[1], parameterType);
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
	public void removePbft(String[] params) throws Exception {

		if (params.length < 2) {
			System.out.println("removePbft(rp): missing nodeID");
			System.out.println("example: removePbft 10b3a2d4b...");
			return;
		}
		UpdatePBFTNode pbft = new UpdatePBFTNode();
		String nodeID = params[1];
		List<String> minerList = web3j.getMinerList().send().getResult();
		List<String> observerList = web3j.getObserverList().send().getResult();
		if (nodeID.length() != 128) {
			System.out.println("This is an invalid nodeID.");
		} else if (observerList.contains(nodeID)) {
			System.out.println("The node is already a pbft observer node.");
		} else if (!minerList.contains(nodeID)) {
			System.out.println("This is not a pbft sealer node.");
		} else {
			String[] args = { "pbft", "remove", nodeID };
			pbft.call(args, web3j, credentials, service.getGroupId());
			System.out.println("Remove " + nodeID.substring(0, 8) + "..." + " to a pbft sealer of group "
					+ service.getGroupId() + " successful.");
		}

	}
	
	@Override
	public void addPbft(String[] params) throws Exception {
		if (params.length < 2) {
			System.out.println("addPbft(ap): missing nodeID");
			System.out.println("example: addPbft 10b3a2d4b...");
			return;
		}
		UpdatePBFTNode pbft = new UpdatePBFTNode();
		String nodeID = params[1];
		List<String> minerList = web3j.getMinerList().send().getResult();
		List<String> observerList = web3j.getObserverList().send().getResult();
		if (nodeID.length() != 128) {
			System.out.println("This is an invalid nodeID.");
		} else if (minerList.contains(nodeID)) {
			System.out.println("The node is already a pbft sealer node.");
		} else if (!observerList.contains(nodeID)) {
			System.out.println("This is not a pbft observer node.");
		} else {
			String[] args = { "pbft", "add", nodeID };
			pbft.call(args, web3j, credentials, service.getGroupId());
			System.out.println("Add " + nodeID.substring(0, 8) + "..." + " to a pbft sealer of group "
					+ service.getGroupId() + " successful.");
		}

	}

}
