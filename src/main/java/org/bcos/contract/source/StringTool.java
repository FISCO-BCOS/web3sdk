package org.bcos.contract.source;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.StaticArray;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Bytes1;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class StringTool extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6107b18061001e6000396000f300606060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063045ccf7b1461005c5780633a96fdd71461011c57806346bdca9a146101d0575b600080fd5b341561006757600080fd5b6100a16004808061080001906040806020026040519081016040528092919082604060200280828437820191505050505091905050610288565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100e15780820151818401526020810190506100c6565b50505050905090810190601f16801561010e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561012757600080fd5b6101ba600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610483565b6040518082815260200191505060405180910390f35b34156101db57600080fd5b61026e600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610747565b604051808215151515815260200191505060405180910390f35b61029061075d565b610298610771565b6000806102a3610771565b604080518059106102b15750595b9080825280601f01601f1916602001820160405250935060009250600091505b60408210156103a15760007f010000000000000000000000000000000000000000000000000000000000000002868360408110151561030c57fe5b60200201517effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614151561039457858260408110151561034857fe5b6020020151848481518110151561035b57fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535082806001019350505b81806001019250506102d1565b826040518059106103af5750595b9080825280601f01601f19166020018201604052509050600091505b828210156104775783828151811015156103e157fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f010000000000000000000000000000000000000000000000000000000000000002818381518110151561043a57fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535081806001019250506103cb565b80945050505050919050565b600061048d610771565b610495610771565b6000808693508592508351915081835110156104b057825191505b600090505b818110156106f35782818151811015156104cb57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916848281518110151561054657fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191610156105e1577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff945061073d565b82818151811015156105ef57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916848281518110151561066a57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f0100000000000000000000000000000000000000000000000000000000000000027effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191611156106e6576001945061073d565b80806001019150506104b5565b825184511015610725577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff945061073d565b825184511115610738576001945061073d565b600094505b5050505092915050565b6000806107548484610483565b14905092915050565b602060405190810160405280600081525090565b6020604051908101604052806000815250905600a165627a7a7230582065009491e62a6186d95206f48599d4250cbd56f80ba362cea9549d2d844175980029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"x\",\"type\":\"bytes1[64]\"}],\"name\":\"byte64ToString\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_a\",\"type\":\"string\"},{\"name\":\"_b\",\"type\":\"string\"}],\"name\":\"compare\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_a\",\"type\":\"string\"},{\"name\":\"_b\",\"type\":\"string\"}],\"name\":\"equal\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";

    private StringTool(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private StringTool(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private StringTool(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private StringTool(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public Future<Utf8String> byte64ToString(StaticArray<Bytes1> x) {
        Function function = new Function("byte64ToString", 
                Arrays.<Type>asList(x), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> compare(Utf8String _a, Utf8String _b) {
        Function function = new Function("compare", Arrays.<Type>asList(_a, _b), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void compare(Utf8String _a, Utf8String _b, TransactionSucCallback callback) {
        Function function = new Function("compare", Arrays.<Type>asList(_a, _b), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> equal(Utf8String _a, Utf8String _b) {
        Function function = new Function("equal", Arrays.<Type>asList(_a, _b), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void equal(Utf8String _a, Utf8String _b, TransactionSucCallback callback) {
        Function function = new Function("equal", Arrays.<Type>asList(_a, _b), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public static Future<StringTool> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(StringTool.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<StringTool> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(StringTool.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static StringTool load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringTool(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static StringTool load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringTool(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static StringTool loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringTool(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static StringTool loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StringTool(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }
}
