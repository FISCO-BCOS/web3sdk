package org.bcos.contract.tools;

import java.math.BigInteger;
import java.util.*;
import org.bcos.channel.client.Service;
import org.bcos.contract.source.AuthorityFilter;
import org.bcos.contract.source.Group;
import org.bcos.contract.source.SystemProxy;
import org.bcos.contract.source.TransactionFilterChain;
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
import org.bcos.web3j.abi.datatypes.*;

public class AuthorityManagerTools {
	
	private TransactionFilterChain transactionFilterChain;
	private AuthorityFilter authorityFilter;
	private Web3j web3j;
	private Credentials credentials;
	private SystemProxy systemProxy;
	ApplicationContext context;
	
	public static BigInteger gasPrice = new BigInteger("99999999999");
	public static BigInteger gasLimited = new BigInteger("9999999999999");
	public static BigInteger initialValue = new BigInteger("0");
	
	public AuthorityManagerTools() {
		authorityFilter=null;
		transactionFilterChain =null;
		credentials=null;
		systemProxy=null;
		web3j = null;
		context=null;
	}
	
	//loadConfig
	public boolean loadConfig() throws Exception{
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		ToolConf toolConf=context.getBean(ToolConf.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        web3j = Web3j.build(channelEthereumService);
        boolean flag=false;
        if(web3j!=null){
        	flag=true;
        }
	    String privKey=toolConf.getPrivKey();
	    credentials = GenCredential.create(privKey);
        systemProxy = SystemProxy.load(toolConf.getSystemProxyAddress(), web3j,credentials, gasPrice,
        		gasLimited);
	  	transactionFilterChain=TransactionFilterChain.load(SystemContractTools.getAction(systemProxy, "TransactionFilterChain").toString(), web3j,  credentials, gasPrice, gasLimited);
        return flag;
	}
	
	//info
	public int getInfo() throws Exception{
		Uint256  filtersLength=transactionFilterChain.getFiltersLength().get();
		return filtersLength.getValue().intValue();
	}
	
	//addFilter
	public Boolean addFilter(String name1,String version1,String desc1)throws Exception{
	  	try {
	  		transactionFilterChain.addFilterAndInfo(new Utf8String(name1), new Utf8String(version1), new Utf8String(desc1)).get();
	  		System.out.println(transactionFilterChain.getContractAddress());
	  		Uint256  filtersLength=transactionFilterChain.getFiltersLength().get();
	  		System.out.println("The number of filters on the chain:" + filtersLength.getValue().intValue());
	  		Address filterAddress=transactionFilterChain.getFilter(new Uint256(filtersLength.getValue().intValue()-1)).get();
	  		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
	  		Bool authorityFlag=authorityFilter.getEnable().get();
	  		return authorityFlag.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//delFilter
	public int delFilter(String num)throws Exception{
	  	int filterNum=Integer.parseInt(num);
	  	int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	int delNum=0;
	  	if(filtersLength<= filterNum||filterNum<0){
	  		System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain "+filtersLength);
	  		return 0;
	  	}else{
	  		System.out.println("Start the delFilter operation：");
	  		try {
	  			transactionFilterChain.delFilter(new Uint256(filterNum)).get();
	  			int filtersLengthNow=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  			delNum=filtersLength-filtersLengthNow;
			} catch (Exception e) {
				e.printStackTrace();
			}
	  	}
	  	return delNum;
	}
	
	//showFilter
	public void showFilter()throws Exception{
	  	int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	System.out.println("The number of filters on the chain:" + filtersLength);
	  	for(int i=0; i<filtersLength;i++){
	  		Address filterAddress=transactionFilterChain.getFilter(new Uint256(i)).get();
	  		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
	  		List<Type> result2=authorityFilter.getInfo().get();
	  		Bool authorityFlag=authorityFilter.getEnable().get();
	  		if(authorityFlag.getValue()){
	  			System.out.println("Filter[" + i+ "]information： "+((Utf8String) result2.get(0)).getValue()+" "+((Utf8String) result2.get(1)).getValue()+" "+((Utf8String) result2.get(2)).getValue()+" The Filter permission control has started");
	  		}else{
	  			System.out.println("Filter[" + i+ "]information: "+((Utf8String) result2.get(0)).getValue()+" "+((Utf8String) result2.get(1)).getValue()+" "+((Utf8String) result2.get(2)).getValue()+" The Filter permission controls the off state");
	  		}
	  	}
	}
	
	//resetFilter
	public void resetFilter()throws Exception{
		System.out.println("Start restoring the chain operation：");
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
		for(int i=0; i<filtersLength;i++){
	  		Address filterAddress=transactionFilterChain.getFilter(new Uint256(i)).get();
	  		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
	  		Bool enable=new Bool(false);
	  		authorityFilter.setEnable(enable).get();
		}
		for (int j=filtersLength-1;;j--){
			transactionFilterChain.delFilter(new Uint256(j)).get();
			if(0==j){
				break;
			}
		}
		System.out.println("The number of filters on the chain:"+transactionFilterChain.getFiltersLength().get().getValue().intValue());
	}
	
	//getFilterStatus
	public Boolean getFilterStatus(String num)throws Exception{
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	if(filtersLength<= (Integer.parseInt(num))||(Integer.parseInt(num))<0){
	  		System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain "+filtersLength);
	  		return false;
	  	}
		Address filterAddress=transactionFilterChain.getFilter(new Uint256(Integer.parseInt(num))).get();
		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
		Bool authorityFlag=authorityFilter.getEnable().get();
		return authorityFlag.getValue();
	}
	
	//enableFilter
	public Boolean enableFilter(String num)throws Exception{
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	if(filtersLength<= (Integer.parseInt(num))||(Integer.parseInt(num))<0){
	  		System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain "+filtersLength);
	  		return false;
	  	}
		Address filterAddress=transactionFilterChain.getFilter(new Uint256(Integer.parseInt(num))).get();
		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
		Bool enable=new Bool(true);
		authorityFilter.setEnable(enable).get();
		Bool authorityFlag=authorityFilter.getEnable().get();
		return authorityFlag.getValue();
	}
	
	//disableFilter
	public Boolean disableFilter(String num)throws Exception{
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	if(filtersLength<= (Integer.parseInt(num))||(Integer.parseInt(num))<0){
	  		System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain "+filtersLength);
	  		return false;
	  	}
		Address filterAddress=transactionFilterChain.getFilter(new Uint256(Integer.parseInt(num))).get();
		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
		Bool enable=new Bool(false);
		authorityFilter.setEnable(enable).get();
		Bool authorityFlag=authorityFilter.getEnable().get();
		return authorityFlag.getValue();
	}
	
	//setUsertoNewGroup
	public void setUsertoNewGroup(String num,String account)throws Exception{
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	if(filtersLength<= (Integer.parseInt(num))||(Integer.parseInt(num))<0){
	  		System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain "+filtersLength);
	  		return;
	  	}
		Address filterAddress=transactionFilterChain.getFilter(new Uint256(Integer.parseInt(num))).get();
		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
		authorityFilter.setUserToNewGroup(new Address(account)).get();
		Address user=authorityFilter.getUserGroup(new Address(account)).get();
		System.out.println("Account assigned to the new role operation is completed, the role of："+user.toString());
	}
	
	//setUsertoExistingGroup
	public void setUsertoExistingGroup(String num,String account,String group)throws Exception{
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	if(filtersLength<= (Integer.parseInt(num))||(Integer.parseInt(num))<0){
	  		System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain "+filtersLength);
	  		return;
	  	}
		Address filterAddress=transactionFilterChain.getFilter(new Uint256(Integer.parseInt(num))).get();
		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
		TransactionReceipt receipt=authorityFilter.setUserToGroup(new Address(account), new Address(group)).get();
		System.out.println("Account assigned to the existing role operation completed");
	}
	
	//listUserGroup
	public String listUserGroup(String num,String account)throws Exception{
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue();
	  	if(filtersLength<= (Integer.parseInt(num))||(Integer.parseInt(num))<0){
	  		System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain "+filtersLength);
	  		return "undefined";
	  	}
		Address filterAddress=transactionFilterChain.getFilter(new Uint256(Integer.parseInt(num))).get();
		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
		Address user=authorityFilter.getUserGroup(new Address(account)).get();
		return user.toString();
	}
	
	//getBlackStatus
	public boolean getBlackStatus(String num,String account)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return false;
		}
		Bool blackFlag=group.getBlack().get();
		return blackFlag.getValue();
	}
	
	//enableBlack
	public boolean enableBlack(String num,String account)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return false;
		}
		Bool black=new Bool(true);
		TransactionReceipt receipt=group.setBlack(black).get();
		Bool blackFlag=group.getBlack().get();
		return blackFlag.getValue();
	}
	
	//disableBlack
	public boolean disableBlack(String num,String account)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return false;
		}
		Bool black=new Bool(false);
		TransactionReceipt receipt=group.setBlack(black).get();
		Bool blackFlag=group.getBlack().get();
		return blackFlag.getValue();
	}
	
	//getDeployStatus
	public boolean getDeployStatus(String num,String account)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return false;
		}
		Bool groupCreate=group.getCreate().get();
		return groupCreate.getValue();
	}
	
	//enableDeploy
	public boolean enableDeploy(String num,String account)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return false;
		}
		Bool bool=new Bool(true);
		TransactionReceipt receipt=group.setCreate(bool).get();
		Bool groupCreate=group.getCreate().get();
		return groupCreate.getValue();
	}
	
	//disableDeploy
	public boolean disableDeploy(String num,String account)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return false;
		}
		Bool bool=new Bool(false);
		TransactionReceipt receipt=group.setCreate(bool).get();
		Bool groupCreate=group.getCreate().get();
		return groupCreate.getValue();
	}
	
	//addPermission
	public void addPermission(String num,String account,String address,String func)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return;
		}
		System.out.println("Start adding permission operation：");
		String funcDesc="contract: "+address+"function: "+func;
		Bool permission=new Bool(true);
		String funchash=sha3(func);
		TransactionReceipt receipt=group.setPermission(new Address(address),new Utf8String(funchash),new Utf8String(funcDesc), permission).get();
		System.out.println("Add permission operation completed");
	}
	
	//delPermission
	public void delPermission(String num,String account,String address,String func)throws Exception{
		Group group=getGroup(num, account);
		if(group==null){
			return;
		}
		System.out.println("Start deleting permission operation：");
		String funchash=sha3(func);
		boolean flag=group.getPermission(new Address(address), new Utf8String(funchash)).get().getValue();
		if(flag){
			String funcDesc="contract: "+address+"function: "+func;
			Bool permission=new Bool(false);
			TransactionReceipt receipt=group.setPermission(new Address(address),new Utf8String(funchash),new Utf8String(funcDesc), permission).get();
			System.out.println("Delete permission operation completed");
		}else{
			System.out.println("Check without this permission");
		}
	}
	
	//checkPermission
	public Boolean checkPermission(String num,String account,String address,String func)throws Exception{
		boolean flag=false;
		try {
			Group group=getGroup(num, account);
			if(group==null){
				return false;
			}
			String funchash=sha3(func);
			flag=group.getPermission(new Address(address), new Utf8String(funchash)).get().getValue();
			return flag;
			
		} catch (Exception e) {
			System.out.println("account is wrong");
			return flag;
		}
		
	}
	
	//listPermission
	public void listPermission(String num,String account)throws Exception{
		try {
			Group group=getGroup(num, account);
			if(group==null){
				return;
			}
			int idx=0;
			List<Bytes32> rlist = group.getKeys().get().getValue();
			if(rlist.size()!=0){
				for (int i=0;i<rlist.size();i++){
					Utf8String desc=group.getFuncDescwithPermissionByKey(rlist.get(i)).get();
					if(!"".equals(desc)&&desc!=null){
						System.out.println("["+idx+"]："+desc);
						idx++;
					}
				}
			}else{
				System.out.println("listPermission is null");
			}
		} catch (Exception e) {
			System.out.println("account is wrong");
		}
		
	}
	
	//getGroup
	public Group getGroup(String num,String account)throws Exception{
		Address filterAddress=transactionFilterChain.getFilter(new Uint256(Integer.parseInt(num))).get();
		int filtersLength=transactionFilterChain.getFiltersLength().get().getValue().intValue(); 
		if(filtersLength<=(Integer.parseInt(num))){
			System.out.println("The number parameter is required to be non-negative and less than the length of the FilterChain"+filtersLength);
			return null;
		}
		authorityFilter=AuthorityFilter.load(filterAddress.toString(), web3j,  credentials, gasPrice, gasLimited);
		Address groupAdress=authorityFilter.getUserGroup(new Address(account)).get();
		Group group=Group.load(groupAdress.toString(), web3j,  credentials, gasPrice, gasLimited);
		return group;
	}
	
	//sha3
	public String sha3(String methodSignature){
		byte[] input = methodSignature.getBytes();
        byte[] hash = Hash.sha3(input);
        String Signature=Numeric.toHexString(hash).substring(2, 10);
        return Signature;
	}

}
