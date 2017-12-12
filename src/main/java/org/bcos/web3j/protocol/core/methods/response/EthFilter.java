package org.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.bcos.web3j.protocol.core.Response;
import org.bcos.web3j.utils.Numeric;

/**
 * eth_newFilter.
 */
public class EthFilter extends Response<String> {
    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
