package org.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.bcos.web3j.protocol.core.Response;
import org.bcos.web3j.utils.Numeric;

/**
 * eth_getBlockTransactionCountByNumber.
 */
public class EthGetBlockTransactionCountByNumber extends Response<String> {
    public BigInteger getTransactionCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
