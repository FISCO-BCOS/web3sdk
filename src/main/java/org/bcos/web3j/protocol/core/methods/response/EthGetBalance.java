package org.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.bcos.web3j.protocol.core.Response;
import org.bcos.web3j.utils.Numeric;

/**
 * eth_getBalance.
 */
public class EthGetBalance extends Response<String> {
    public BigInteger getBalance() {
        return Numeric.decodeQuantity(getResult());
    }
}
