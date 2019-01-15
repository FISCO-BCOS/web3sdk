package org.fisco.bcos.web3j.precompile.authority;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.List;

public class AuthorityService {
	private static BigInteger gasPrice = new BigInteger("300000000");
	private static BigInteger gasLimit = new BigInteger("300000000");
	private static String AuthorityPrecompileAddress = "0x0000000000000000000000000000000000001005";

	public String add(String tableName, String addr, Web3j web3j, Credentials credentials) throws Exception {
		return add(AuthorityPrecompileAddress, web3j, credentials, tableName, addr);
	}

	public String remove(String tableName, String addr, Web3j web3j, Credentials credentials) throws Exception {
		return remove(AuthorityPrecompileAddress, web3j, credentials, tableName, addr);
	}

	public List<Authority> query(String tableName, Web3j web3j, Credentials credentials) throws Exception {
		return query(AuthorityPrecompileAddress, web3j, credentials, tableName);
	}

	
	private String add(String address, Web3j web3j, Credentials credentials, String tableName, String addr)
			throws Exception {
		@SuppressWarnings("deprecation")
		AuthorityTable authority = AuthorityTable.load(address, web3j, credentials, gasPrice, gasLimit);
		TransactionReceipt receipt = authority.insert(tableName, addr).send();
		return PrecompiledCommon.getJsonStr(receipt.getOutput());
	}

	private String remove(String address, Web3j web3j, Credentials credentials, String tableName, String addr)
			throws Exception {
		@SuppressWarnings("deprecation")
		AuthorityTable authority = AuthorityTable.load(address, web3j, credentials, gasPrice, gasLimit);
		TransactionReceipt receipt = authority.remove(tableName, addr).send();
		return PrecompiledCommon.getJsonStr(receipt.getOutput());
	}

	private List<Authority> query(String address, Web3j web3j, Credentials credentials, String tableName)
			throws Exception {
		@SuppressWarnings("deprecation")
		AuthorityTable authority = AuthorityTable.load(address, web3j, credentials, gasPrice, gasLimit);
		String authorityInfo = authority.queryByName(tableName).send();
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		return objectMapper.readValue(authorityInfo,
				objectMapper.getTypeFactory().constructCollectionType(List.class, Authority.class));
	}
}