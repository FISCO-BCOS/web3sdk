package org.fisco.bcos.web3j.precompile.authority;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.List;

public class AuthorityService {
	private static BigInteger gasPrice = new BigInteger("300000000");
	private static BigInteger gasLimit = new BigInteger("300000000");
	private static String AuthorityPrecompileAddress = "0x0000000000000000000000000000000000001005";
	private Authority authority;

	public AuthorityService(Web3j web3j, Credentials credentials) {
		ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
		authority = Authority.load(AuthorityPrecompileAddress, web3j, credentials, contractGasProvider);
	}
	
	public String addUserTableManager(String tableName, String address) throws Exception {
		return add(tableName, address);
	}
	public String removeUserTableManager(String tableName, String address) throws Exception {
		return remove(tableName, address);
	}
	public List<AuthorityInfo> queryUserTableManager(String tableName) throws Exception {
		return query(tableName);
	}
	
	public String addDeployAndCreateManager(String address) throws Exception {
		return add(PrecompiledCommon.SYSTABLE, address);
	}
	public String removeDeployAndCreateManager(String address) throws Exception {
		return remove(PrecompiledCommon.SYSTABLE, address);
	}
	public List<AuthorityInfo> queryDeployAndCreateManager() throws Exception {
		return query(PrecompiledCommon.SYSTABLE);
	}
	
	public String addAuthorityManager(String address) throws Exception {
		return add(PrecompiledCommon.SYSTABLEACCESS, address);
	}
	public String removeAuthorityManager(String address) throws Exception {
		return remove(PrecompiledCommon.SYSTABLEACCESS, address);
	}
	public List<AuthorityInfo> queryAuthorityManager() throws Exception {
		return query(PrecompiledCommon.SYSTABLEACCESS);
	}
	
	public String addNodeManager(String address) throws Exception {
		return add(PrecompiledCommon.SYSCONSENSUS, address);
	}
	public String removeNodeManager(String address) throws Exception {
		return remove(PrecompiledCommon.SYSCONSENSUS, address);
	}
	public List<AuthorityInfo> queryNodeManager() throws Exception {
		return query(PrecompiledCommon.SYSCONSENSUS);
	}
	
	public String addCNSManager(String address) throws Exception {
		return add(PrecompiledCommon.SYSCNS, address);
	}
	public String removeCNSManager(String address) throws Exception {
		return remove(PrecompiledCommon.SYSCNS, address);
	}
	public List<AuthorityInfo> queryCNSManager() throws Exception {
		return query(PrecompiledCommon.SYSCNS);
	}
	
	public String addSysConfigManager(String address) throws Exception {
		return add(PrecompiledCommon.SYSCONFIG, address);
	}
	public String removeSysConfigManager(String address) throws Exception {
		return remove(PrecompiledCommon.SYSCONFIG, address);
	}
	public List<AuthorityInfo> querySysConfigManager() throws Exception {
		return query(PrecompiledCommon.SYSCONFIG);
	}
	
	private String add(String tableName, String address) throws Exception {
		TransactionReceipt receipt = authority.insert(tableName, address).send();
		return PrecompiledCommon.getJsonStr(receipt.getOutput());
	}
	private String remove(String tableName, String address) throws Exception {
		TransactionReceipt receipt = authority.remove(tableName, address).send();
		return PrecompiledCommon.getJsonStr(receipt.getOutput());
	}
	private List<AuthorityInfo> query(String tableName) throws Exception {
		String authorityInfo = authority.queryByName(tableName).send();
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		return objectMapper.readValue(authorityInfo,
				objectMapper.getTypeFactory().constructCollectionType(List.class, AuthorityInfo.class));
	}
	
}