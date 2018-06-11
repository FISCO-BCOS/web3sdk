package org.bcos.contract.tools;

import java.math.BigInteger;
import java.util.List;
import org.bcos.channel.client.Service;
import org.bcos.contract.source.ContractA;
import org.bcos.contract.source.AuthorityFilter;
import org.bcos.contract.source.Group;
import org.bcos.contract.source.SystemProxy;
import org.bcos.contract.source.TransactionFilterChain;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.GenCredential;
import org.bcos.web3j.crypto.Hash;

import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.utils.Numeric;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ARPI_Model {
	static ApplicationContext context;
	static TransactionReceipt receipt;
	public static BigInteger gasPrice = new BigInteger("99999999999");
	public static BigInteger gasLimited = new BigInteger("9999999999999");
	public static BigInteger initialValue = new BigInteger("0");
	
	public static void main(String[] args) throws Exception{
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		ToolConf toolConf=context.getBean(ToolConf.class);
		//getAccount
		String account=toolConf.getAccount();
		//getPrivKey
		String privKey=toolConf.getPrivKey();
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        Web3j web3j = Web3j.build(channelEthereumService);

	  	Credentials credentials = GenCredential.create(privKey);
        SystemProxy systemProxy = SystemProxy.load(toolConf.getSystemProxyAddress(), web3j,credentials , gasPrice,
        		gasLimited);
        //getAddress
	    String cAAction=SystemContractTools.getAction(systemProxy, "CAAction").toString();
	    String nodeAction=SystemContractTools.getAction(systemProxy, "NodeAction").toString();
	    String contractAbiMgr=SystemContractTools.getAction(systemProxy, "ContractAbiMgr").toString();
	    String configActionAddress=SystemContractTools.getAction(systemProxy, "ConfigAction");
	  	TransactionFilterChain transactionFilterChain=TransactionFilterChain.load(SystemContractTools.getAction(systemProxy, "TransactionFilterChain").toString(), web3j,  credentials, gasPrice, gasLimited);
	  	System.out.println("transactionFilterChain"+SystemContractTools.getAction(systemProxy, "TransactionFilterChain").toString());
	  	TransactionReceipt receipt=transactionFilterChain.addFilterAndInfo(new Utf8String("ARPI Filter"), new Utf8String("1.0"), new Utf8String("FISCO BCOS Permission management model")).get();
	  	int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	if(filtersLength==0){
	  		System.out.println("Add Filter operation fail");
	  		System.exit(0);
	  	}
	  	System.out.println("Add Filter operation completed, the current number of Filter："+filtersLength);
	  	AuthorityFilter authorityFilter=AuthorityFilter.load((transactionFilterChain.getFilter(new Uint256(0)).get()).toString(), web3j, credentials, gasLimited, gasLimited);
	  	for(int i=0;i<filtersLength;i++){
	  		Address filterAddress=transactionFilterChain.getFilter(new Uint256(i)).get();
	  		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
	  		System.out.println("Filter[" + i + "]："+authorityFilter.getContractAddress()+","+authorityFilter.getInfo().get());
	  	}
	  	receipt=authorityFilter.setUserToNewGroup(new Address(account)).get();
	  	Address groupAdress1=authorityFilter.getUserGroup(new Address(account)).get();
	  	Group group1=Group.load(groupAdress1.toString(), web3j,  credentials, gasPrice, gasLimited);
	  	receipt=group1.setDesc(new Utf8String("Manager Filter-1.0-chain/Filter administrator")).get();
	  	System.out.println("Group1 address："+group1.getContractAddress()+"，description："+group1.getDesc().get());
	  	System.out.println("Group1 add permissions");
	  	String desc = "Group1 set SystemProxy";
	  	// SystemProxy的getRoute、setRoute、getRouteNameByIndex、getRouteSize
	  	addPermission(group1, systemProxy.getContractAddress(),"registerNode(string,string,uint256,uint8,string,string,string,uint256)",desc);
	  	addPermission(group1, systemProxy.getContractAddress(), "cancelNode(string)", desc);
	  	addPermission(group1, systemProxy.getContractAddress(), "getNode(string)", desc);
	  	addPermission(group1, systemProxy.getContractAddress(), "getNodeIdx(string)", desc);
	  	addPermission(group1, systemProxy.getContractAddress(), "getNodeIdsLength()", desc);
	  	addPermission(group1, systemProxy.getContractAddress(), "getNodeId(uint)", desc);
	    // CAAction的update、updateStatus、get、getIp、getHashsLength、getHash
	  	desc = "Group1 set CAAction";
	  	addPermission(group1, cAAction, "update(string,string,string,uint256,uint256,uint8,string,string)", desc);
	  	addPermission(group1, cAAction, "updateStatus(string,uint8)", desc);
	  	addPermission(group1, cAAction, "get(string)", desc);
	  	addPermission(group1, cAAction, "getIp(string)", desc);
	  	addPermission(group1, cAAction, "getHashsLength()", desc);
	  	addPermission(group1, cAAction, "getHash(uint)", desc);
	    // NodeAction的registerNode、cancelNode、getNode、getNodeIdx、getNodeIdsLength、getNodeId
	  	desc = "Group1 set NodeAction";
	  	addPermission(group1, nodeAction, "registerNode(string,string,uint256,uint8,string,string,string,uint256)", desc);
	  	addPermission(group1, nodeAction, "cancelNode(string)", desc);
	  	addPermission(group1, nodeAction, "getNode(string)", desc);
	  	addPermission(group1, nodeAction, "getNodeIdx(string)", desc);
	  	addPermission(group1, nodeAction, "getNodeIdsLength()", desc);
	  	addPermission(group1, nodeAction, "getNodeId(uint)", desc);
	    // ContractAbiMgr的addAbi、updateAbi
	  	desc = "Group1 set ContractAbiMgr";
	  	addPermission(group1, contractAbiMgr, "addAbi(string,string,string,string,address)", desc);
	  	addPermission(group1, contractAbiMgr, "updateAbi(string,string,string,string,address)", desc);
	  	addPermission(group1, contractAbiMgr, "getAddr(string)", desc);
	  	addPermission(group1, contractAbiMgr, "getAbi(string)", desc);
	  	addPermission(group1, contractAbiMgr, "getAbi(string)", desc);
	  	addPermission(group1, contractAbiMgr, "getVersion(string)", desc);
	  	addPermission(group1, contractAbiMgr, "getBlockNumber(string)", desc);
	  	addPermission(group1, contractAbiMgr, "getTimeStamp(string)", desc);
	  	addPermission(group1, contractAbiMgr, "getAbiCount()", desc);
	  	addPermission(group1, contractAbiMgr, "getAll(string)", desc);
	  	addPermission(group1, contractAbiMgr, "getAllByIndex(uint256)", desc);
	  	System.out.println("Group1 has completed adding permissions and has the following permissions：");
	  	getPermissionList(group1);
	  	// Group2
	  	System.out.println("\n Create Group2 and Set Group Description");
	  	receipt=authorityFilter.setUserToNewGroup(new Address(account)).get();
	  	Address groupAdress2=authorityFilter.getUserGroup(new Address(account)).get();
		Group group2=Group.load(groupAdress2.toString(), web3j,  credentials, gasPrice, gasLimited);
		receipt=group2.setDesc(new Utf8String("Operations Filter-1.0-Operation and maintenance management")).get();
	  	System.out.println("Group2 address："+group2.getContractAddress()+"，description："+group2.getDesc().get());
	  	System.out.println("Enable this role to publish contract features");
	  	Bool bool2=new Bool(true);
	  	group2.setCreate(bool2).get();
	  	System.out.println("Group2 Release Contract Feature Enable Results："+(group2.getCreate().get().getValue()?"Success":"Fail"));
	  	System.out.println("Group1 add permissions");
	  	//SystemProxy的getRoute、setRoute、getRouteNameByIndex、getRouteSize
	  	desc="Group2 set SystemProxy";
	  	addPermission(group2, systemProxy.getContractAddress(), "getRoute(string)", desc);
	  	addPermission(group2, systemProxy.getContractAddress(), "setRoute(string)", desc);
	  	addPermission(group2, systemProxy.getContractAddress(), "getRouteNameByIndex(string)", desc);
	  	addPermission(group2, systemProxy.getContractAddress(), "getRouteSize()", desc);
	  	//ConfigAction的set、get
	  	desc="Group2 set ConfigAction";
	  	addPermission(group2, configActionAddress, "set(string,string)", desc);
	  	addPermission(group2, configActionAddress, "get(string)", desc);
	  	System.out.println("Group2 has added permissions and has the following permissions：");
	  	getPermissionList(group2);
	  	// Group3-1
	  	System.out.println("\n Create Group3-1 and Set Group Description");
	  	receipt=authorityFilter.setUserToNewGroup(new Address(account)).get();
	  	Address groupAdress3=authorityFilter.getUserGroup(new Address(account)).get();
		Group group3=Group.load(groupAdress3.toString(), web3j,  credentials, gasPrice, gasLimited);
		receipt=group3.setDesc(new Utf8String("Business Filter-1.0-Type of trader")).get();
		System.out.println("Group3-1 address："+group3.getContractAddress()+"，description："+group3.getDesc().get());
	  	System.out.println("Deployment Contract ContractA");
	  	ContractA contractA=ContractA.deploy(web3j, credentials, gasPrice, gasLimited, initialValue).get();
	  	System.out.println(contractA.getContractAddress());
	  	System.out.println("Group3-1 add permissions");
	  	addPermission(group3, contractA.getContractAddress(),"set1(string)", "Group3 set ContractA");
	  	System.out.println("Group3 has added permissions and has the following permissions：");
	  	getPermissionList(group3);
	  	// Group3-2
	  	System.out.println("\n Create Group3-2 and Set Group Description");
	  	receipt=authorityFilter.setUserToNewGroup(new Address(account)).get();
	  	Address groupAdress4=authorityFilter.getUserGroup(new Address(account)).get();
		Group group4=Group.load(groupAdress4.toString(), web3j,  credentials, gasPrice, gasLimited);
		receipt=group4.setDesc(new Utf8String("Business Filter-1.0-Type ii trader")).get();
		System.out.println("Group3-2 address："+group4.getContractAddress()+"，description："+group4.getDesc().get());
		System.out.println("Group3-2 add permissions");
		addPermission(group4, contractA.getContractAddress(), "set2(string)", "Group4 set ContractA");
		System.out.println("Group4 has added permissions and has the following permissions：");
		getPermissionList(group4);
		System.out.println("\n Start Filter permission control");
		Bool enable=new Bool(true);
		receipt=authorityFilter.setEnable(enable).get();
		System.out.println("The Filter permission control result starts："+(authorityFilter.getEnable().get().getValue()?"Success":"Fail"));
		System.out.println("........................ARPI_Model Init End........................");
		System.exit(0);
		
	}
	
	
	
	public static Boolean addPermission(Group group,String address,String func,String desc)throws Exception{
		String funcDesc="contract"+address+"function"+func;
		String funchash=sha3(func);
		Bool permission=new Bool(true);
		TransactionReceipt receipt=group.setPermission(new Address(address),new Utf8String(funchash),new Utf8String(funcDesc), permission).get();
		boolean flag=group.getPermission(new Address(address), new Utf8String(funchash)).get().getValue();
		System.out.println(desc+func+" permission result："+(flag?"Success":"Fail"));
		return flag;
	}
	
	public static void getPermissionList(Group group)throws Exception{
		int idx=0;
		List<Bytes32> rlist = group.getKeys().get().getValue();
		for (int i=0;i<rlist.size();i++){
			Utf8String desc=group.getFuncDescwithPermissionByKey(rlist.get(i)).get();
			if(!"".equals(desc)&&desc!=null){
				System.out.println("["+idx+"]："+desc);
				idx++;
			}
		}
	}
	
	//sha3
	public static String sha3(String methodSignature){
		byte[] input = methodSignature.getBytes();
        byte[] hash = Hash.sha3(input);
        String Signature=Numeric.toHexString(hash).substring(2, 10);
        return Signature;
	}
}
