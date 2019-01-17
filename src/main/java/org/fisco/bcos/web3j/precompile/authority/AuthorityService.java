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

	public String add(String tableName, String addr) throws Exception {
		TransactionReceipt receipt = authority.insert(tableName, addr).send();
		return PrecompiledCommon.getJsonStr(receipt.getOutput());
	}

	public String remove(String tableName, String addr) throws Exception {
		TransactionReceipt receipt = authority.remove(tableName, addr).send();
		return PrecompiledCommon.getJsonStr(receipt.getOutput());
	}

	public List<AuthorityInfo> query(String tableName) throws Exception {
		String authorityInfo = authority.queryByName(tableName).send();
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		return objectMapper.readValue(authorityInfo,
				objectMapper.getTypeFactory().constructCollectionType(List.class, AuthorityInfo.class));
	}
}