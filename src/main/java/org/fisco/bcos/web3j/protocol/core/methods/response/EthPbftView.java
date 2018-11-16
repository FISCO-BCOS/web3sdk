package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

public class EthPbftView extends Response<String>{
	
	 public BigInteger getEthPbftView() {
	        return Numeric.decodeQuantity(getResult());
	    }

}
