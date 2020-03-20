package org.fisco.bcos.web3j.tx;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.utils.Numeric;
import org.fisco.bcos.web3j.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
pragma solidity ^0.4.25;
contract Revert {
    function Error(string memory s) public {}
}

"08c379a0": "Error(string)" // Not SM Method
"c703cb12": "Error(string)" // SM Method
*/

public class RevertResolver {

    private static final Logger logger = LoggerFactory.getLogger(RevertResolver.class);

    public static final String RevertMethod = "08c379a0";
    public static final String RevertMethodWithHexPrefix = "0x08c379a0";

    public static final String SMRevertMethod = "c703cb12";
    public static final String SMRevertMethodWithHexPrefix = "0xc703cb12";

    // Error(String)
    public static final Function revertFunction =
            new Function(
                    "Error",
                    Collections.<Type>emptyList(),
                    Collections.singletonList(new TypeReference<Utf8String>() {}));

    /**
     * Does output start with the code of the Revert method, If so, the output may be error message
     *
     * @param output
     * @return
     */
    public static boolean isOutputStartWithRevertMethod(String output) {
        return output.startsWith(RevertMethodWithHexPrefix)
                || output.startsWith(SMRevertMethodWithHexPrefix)
                || (output.startsWith(RevertMethod) || output.startsWith(SMRevertMethod));
    }

    /**
     * @param status
     * @param output
     * @return
     */
    public static boolean hasRevertMessage(String status, String output) {
        if (Strings.isEmpty(status) || Strings.isEmpty(output)) {
            return false;
        }
        try {
            BigInteger statusQuantity = Numeric.decodeQuantity(status);
            return !BigInteger.ZERO.equals(statusQuantity) && isOutputStartWithRevertMethod(output);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param status
     * @param output
     * @return
     */
    public static Tuple2<Boolean, String> tryResolveRevertMessage(String status, String output) {
        if (!hasRevertMessage(status, output)) {
            return new Tuple2<>(false, null);
        }

        try {
            // 00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000030497373756572526f6c653a2063616c6c657220646f6573206e6f742068617665207468652049737375657220726f6c6500000000000000000000000000000000
            String rawOutput =
                    Numeric.containsHexPrefix(output)
                            ? output.substring(RevertMethodWithHexPrefix.length())
                            : output.substring(RevertMethod.length());
            List<Type> result =
                    FunctionReturnDecoder.decode(rawOutput, revertFunction.getOutputParameters());
            if (result.get(0) instanceof Utf8String) {
                String message = ((Utf8String) result.get(0)).getValue();
                if (logger.isDebugEnabled()) {
                    logger.debug(" ABI: {} , RevertMessage: {}", output, message);
                }
                return new Tuple2<>(true, message);
            }
        } catch (Exception e) {
            logger.warn(" ABI: {}, e: {}", output, e);
        }

        return new Tuple2<>(false, null);
    }

    /**
     * @param receipt
     * @return
     */
    public static Tuple2<Boolean, String> tryResolveRevertMessage(TransactionReceipt receipt) {
        return tryResolveRevertMessage(receipt.getStatus(), receipt.getOutput());
    }
}
