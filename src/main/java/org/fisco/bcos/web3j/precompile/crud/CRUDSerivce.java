package org.fisco.bcos.web3j.precompile.crud;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CRUDSerivce {
  private static BigInteger gasPrice = new BigInteger("300000000");
  private static BigInteger gasLimit = new BigInteger("300000000");
  private static final String TableFactoryPrecompileAddress = "0x0000000000000000000000000000000000001001";
  private static final String CRUDPrecompileAddress = "0x0000000000000000000000000000000000001002";
  private TableFactory tableFactory;
  private CRUD crud;

  public CRUDSerivce(Web3j web3j, Credentials credentials) {
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    tableFactory = TableFactory.load(TableFactoryPrecompileAddress, web3j, credentials, contractGasProvider);
    crud = CRUD.load(CRUDPrecompileAddress, web3j, credentials, contractGasProvider);
  }

  public String createTable(String tableName, String key, String valueField) throws Exception {
    
  	TransactionReceipt receipt = tableFactory.createTable(tableName, key, valueField).send();
    return PrecompiledCommon.handleTransactionReceipt(receipt);
  }
  
  public int insert(String tableName, String key, Map<String, String> entry, String optional) throws Exception{
  	
  	String entryJsonStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(entry);
  	TransactionReceipt receipt = crud.insert(tableName, key, entryJsonStr, optional).send();
  	return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
  }
  
  public int update(String tableName, String key, Map<String, String> entry, Condition condition, String optional) throws Exception {
  	
  	String entryJsonStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(entry);
  	String conditionStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(condition.getConditions());
  	TransactionReceipt receipt = crud.update(tableName, key, entryJsonStr, conditionStr, optional).send();
  	return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
  }
  
  public int remove(String tableName, String key, Condition condition, String optional) throws Exception {
  	
  	String conditionStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(condition.getConditions());
  	TransactionReceipt receipt = crud.remove(tableName, key, conditionStr, optional).send();
  	return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
  }
  
  @SuppressWarnings("unchecked")
	public List<Map<String, String>> select(String tableName, String key, Condition condition, String optional) throws Exception {
  	
  	ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		String conditionJsonStr = objectMapper.writeValueAsString(condition.getConditions());
  	String resultStr = crud.select(tableName, key, conditionJsonStr, optional).send();
  	List<Map<String, String>> result = (List<Map<String, String>>)objectMapper.readValue(resultStr, objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
  	return result;
  }
}
