package org.fisco.bcos.web3j.abi;

import java.util.List;
import java.util.stream.Collectors;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.SmHash;
import org.fisco.bcos.web3j.utils.Numeric;

/**
 * Ethereum filter encoding. Further limited details are available <a
 * href="https://github.com/ethereum/wiki/wiki/Ethereum-Contract-ABI#events">here</a>.
 */
public class EventEncoder {

    private EventEncoder() {}

    public static String encode(Event event) {

        String methodSignature = buildMethodSignature(event.getName(), event.getParameters());

        return buildEventSignature(methodSignature);
    }

    static <T extends Type> String buildMethodSignature(
            String methodName, List<TypeReference<T>> parameters) {

        StringBuilder result = new StringBuilder();
        result.append(methodName);
        result.append("(");
        String params =
                parameters.stream().map(p -> Utils.getTypeName(p)).collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        return result.toString();
    }

    public static String buildEventSignature(String methodSignature, Boolean isSm) {
        byte[] input = methodSignature.getBytes();
        if (Boolean.TRUE.equals(isSm)) {
            // 国密
            byte[] hash = SmHash.sha3(input);
            return Numeric.toHexString(hash);
        } else {
            // keccak256
            byte[] hash = Hash.sha3(input);
            return Numeric.toHexString(hash);
        }
    }
}
