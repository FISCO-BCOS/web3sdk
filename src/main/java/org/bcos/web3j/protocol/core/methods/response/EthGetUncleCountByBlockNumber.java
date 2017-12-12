package org.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.bcos.web3j.protocol.core.Response;
import org.bcos.web3j.utils.Numeric;

/**
 * eth_getUncleCountByBlockNumber.
 */
public class EthGetUncleCountByBlockNumber extends Response<String> {
    public BigInteger getUncleCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
