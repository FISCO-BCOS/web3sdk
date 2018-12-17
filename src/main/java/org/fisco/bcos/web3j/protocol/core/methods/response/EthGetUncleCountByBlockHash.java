package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

/**
 * eth_getUncleCountByBlockHash.
 */
public class EthGetUncleCountByBlockHash extends Response<String> {
    public BigInteger getUncleCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
