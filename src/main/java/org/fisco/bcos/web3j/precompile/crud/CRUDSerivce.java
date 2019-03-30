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

  public String createTable(Table table) throws Exception {
    
  	TransactionReceipt receipt = tableFactory.createTable(table.getTableName(), table.getKey(), table.getValueFields()).send();
    return PrecompiledCommon.handleTransactionReceipt(receipt);
  }
  
  public int insert(Table table, Entry entry) throws Exception{
  	
  	String entryJsonStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(entry.getFields());
  	TransactionReceipt receipt = crud.insert(table.getTableName(), table.getKey(), entryJsonStr, table.getOptional()).send();
  	return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
  }
  
  public int update(Table table, Entry entry, Condition condition) throws Exception {
  	
  	String entryJsonStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(entry.getFields());
  	String conditionStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(condition.getConditions());
  	TransactionReceipt receipt = crud.update(table.getTableName(), table.getKey(), entryJsonStr, conditionStr, table.getOptional()).send();
  	return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
  }
  
  public int remove(Table table, Condition condition) throws Exception {
  	
  	String conditionStr = ObjectMapperFactory.getObjectMapper().writeValueAsString(condition.getConditions());
  	TransactionReceipt receipt = crud.remove(table.getTableName(), table.getKey(), conditionStr, table.getOptional()).send();
  	return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
  }
  
  @SuppressWarnings("unchecked")
	public List<Map<String, String>> select(Table table, Condition condition) throws Exception {
  	
  	ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		String conditionJsonStr = objectMapper.writeValueAsString(condition.getConditions());
  	String resultStr = crud.select(table.getTableName(), table.getKey(), conditionJsonStr, table.getOptional()).send();
  	List<Map<String, String>> result = (List<Map<String, String>>)objectMapper.readValue(resultStr, objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
  	return result;
  }
}
