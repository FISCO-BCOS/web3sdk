package org.fisco.bcos.web3j.precompile.common;

import java.math.BigInteger;

import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrecompiledCommon {

  // system table for authority control
  public static final String SYSTABLE = "_sys_tables_";
  public static final String SYSTABLEACCESS = "_sys_table_access_";
  public static final String SYSCONSENSUS = "_sys_consensus_";
  public static final String SYSCNS = "_sys_cns_";
  public static final String SYSCONFIG = "_sys_config_";
  
  public static final int Success = 0;
  public static final int PermissionDenied = 50000;
  public static final int TableNameAndAddressExist = 51000;
  public static final int TableNameAndAddressNotExist = 51001;
  public static final int InvalidNodeId = 51100;
  public static final int LastSealer = 51101;
  public static final int P2pNetwork = 51102;
  public static final int GroupPeers = 51103;
  public static final int SealerList = 51104;
  public static final int ObserverList = 51105;
  public static final int ContractNameAndVersionExist = 51200;
  public static final int VersionExceeds = 51201;
  public static final int InvalidKey = 51300;

  public static String transferToJson(int code) throws JsonProcessingException {
    String msg = "";
    switch (code) {
      case Success:
        msg = "success";
        break;
      case PermissionDenied:
        msg = "permission denied";
        break;
      case TableNameAndAddressExist:
        msg = "table name and address already exist";
        break;
      case TableNameAndAddressNotExist:
        msg = "table name and address does not exist";
        break;
      case InvalidNodeId:
        msg = "invalid node ID";
        break;
      case LastSealer:
        msg = "the last sealer cannot be removed";
        break;
      case P2pNetwork:
        msg = "the node is not reachable";
        break;
      case GroupPeers:
        msg = "the node is not a group peer";
        break;
      case SealerList:
        msg = "the node is already in the sealer list";
        break;
      case ObserverList:
        msg = "the node is already in the observer list";
        break;
      case ContractNameAndVersionExist:
        msg = "contract name and version already exist";
        break;
      case VersionExceeds:
        msg = "version string length exceeds the maximum limit";
        break;
      case InvalidKey:
        msg = "invalid configuration entry";
        break;
    }
    ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
    return mapper.writeValueAsString(new PrecompiledResponse(code, msg));
  }

  public static String getJsonStr(String output) throws JsonProcessingException {
    try {
      int code = 0;
      code = new BigInteger(output.substring(2, output.length()), 16).intValue();
      if(code == 1)
      {
    	  code = Success;
      }
      if(code == 56)
      {
    	  code = TableNameAndAddressExist;
      }
      if(code == 57)
      {
    	  code = TableNameAndAddressNotExist;
      }
      if(code == 80)
      {
    	  code = PermissionDenied;
      }
      if(code == 100)
      {
    	  code = InvalidKey;
      }
      if(code == 157)
      {
    	  code = LastSealer;
      }
      return transferToJson(code);
    } catch (NumberFormatException e) {
      return "The call function does not exist.";
    }
  }
  
	public static String handleTransactionReceipt(TransactionReceipt receipt)
			throws TransactionException, JsonProcessingException {
		if("Receipt timeout".equals(receipt.getStatus()))
    {
    	throw new TransactionException("Transaction receipt timeout.");
    }
    else 
    {
    	if(receipt.getOutput() != null)
    	{
    		return PrecompiledCommon.getJsonStr(receipt.getOutput());
    	}
    	else 
    	{
    		throw new TransactionException("Transaction is handled failure.");
			}
    }
	}
}
