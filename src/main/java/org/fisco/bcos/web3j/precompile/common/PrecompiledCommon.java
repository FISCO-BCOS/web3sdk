package org.fisco.bcos.web3j.precompile.common;

import java.math.BigInteger;

import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrecompiledCommon {
	
    public static String transferToJson(int code) throws JsonProcessingException{
    	String msg = "";
    	switch (code)
    	{
    		case -1:
    			msg = "non-authorized";
    			break;
    		case -30:
    			msg = "table name and address exist";
    			break;
    		case -31:
    			msg = "table name and address does not exist";
    			break;
    		case -40:
    			msg = "invalid nodeID";
    			break;
    		case -41:
    			msg = "last miner cannot be removed";
    			break;
    		case -42:
    			msg = "nodeID is not in peers";
    			break;
    		case -43:
    			msg = "nodeID is not in group peers";
    			break;
    		case -44:
    			msg = "nodeID is already in miner list";
    			break;
    		case -45:
    			msg = "nodeID is already in observer list";
    			break;
    		case -50:
    			msg = "address and version exist";
    			break;
    		case -60:
    			msg = "invalid configuration value";
    			break;
    		default:
    			msg = "success";
    			break;	
    	}
    	ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
        return mapper.writeValueAsString(new PrecompiledResponse(code, msg));
    }
    
	public static String getJsonStr(String output) throws JsonProcessingException {
		try {
			int code = 0;
			if ("f".equals(output.substring(2, 3))) {
				code = -1;
			} else {
				code = new BigInteger(output.substring(2, output.length()), 16).intValue() - 256;
				if(code < -200)
				{
					code = Integer.valueOf(output.substring(2), 16).intValue();
				}
			}
			return transferToJson(code);
		} catch (NumberFormatException e) {
			return "The call function does not exist.";
		}
	}
}
