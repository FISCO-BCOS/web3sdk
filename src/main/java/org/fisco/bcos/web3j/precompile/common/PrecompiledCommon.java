package org.fisco.bcos.web3j.precompile.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.solidity.Abi;

public class PrecompiledCommon {

  // system table for authority control
  public static final String SYSTABLE = "_sys_tables_";
  public static final String SYSTABLEACCESS = "_sys_table_access_";
  public static final String SYSCONSENSUS = "_sys_consensus_";
  public static final String SYSCNS = "_sys_cns_";
  public static final String SYSCONFIG = "_sys_config_";

  public static String transferToJson(int code) throws JsonProcessingException {
    String msg = "";
    switch (code) {
      case 0:
        msg = "success";
        break;
      case 50000:
        msg = "permission denied";
        break;
      case 51000:
        msg = "table name and address exist";
        break;
      case 51001:
        msg = "table name and address does not exist";
        break;
      case 51100:
        msg = "invalid nodeId";
        break;
      case 51101:
        msg = "the last sealer cannot be removed";
        break;
      case 51102:
        msg = "the node is not in p2p network";
        break;
      case 51103:
        msg = "the node is not in group peers";
        break;
      case 51104:
        msg = "the node is already in sealer list";
        break;
      case 51105:
        msg = "the node is already in observer list";
        break;
      case 51200:
        msg = "contract name and version exist";
        break;
      case 51201:
        msg = "version exceeds maximum(40) length";
        break;
      case 51300:
        msg = "invalid configuration key";
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
    	  code = 0;
      }
      if(code == 56)
      {
    	  code = 51000;
      }
      if(code == 57)
      {
    	  code = 51001;
      }
      if(code == 80)
      {
    	  code = 50000;
      }
      if(code == 100)
      {
    	  code = 51300;
      }
      if(code == 157)
      {
    	  code = 51101;
      }
      return transferToJson(code);
    } catch (NumberFormatException e) {
      return "The call function does not exist.";
    }
  }
}
