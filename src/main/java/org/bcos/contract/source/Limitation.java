package org.bcos.contract.source;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.EncryptType;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class Limitation extends Contract {
    private static String BINARY = "6060604052341561000f57600080fd5b6104398061001e6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631ce28e72146100695780633be8c7e4146100b657806376d9a04a1461011057806394e78a101461016a578063e14b441a146101ac57600080fd5b341561007457600080fd5b6100a0600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506101f9565b6040518082815260200191505060405180910390f35b34156100c157600080fd5b6100f6600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610242565b604051808215151515815260200191505060405180910390f35b341561011b57600080fd5b610150600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506102cf565b604051808215151515815260200191505060405180910390f35b341561017557600080fd5b6101aa600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190505061037d565b005b34156101b757600080fd5b6101e3600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506103c5565b6040518082815260200191505060405180910390f35b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054826000808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054011115905092915050565b60008015156102de8484610242565b151514156102ef5760009050610377565b816000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054016000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550600190505b92915050565b80600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505050565b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490509190505600a165627a7a723058200301e2e07a606eb12b90045c697ab8e6b53d8e7844fb10d4ddac6c01b48d00680029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"getLimit\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"},{\"name\":\"limit\",\"type\":\"uint256\"}],\"name\":\"checkSpent\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"},{\"name\":\"limit\",\"type\":\"uint256\"}],\"name\":\"addSpent\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"},{\"name\":\"limit\",\"type\":\"uint256\"}],\"name\":\"setLimitation\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"getSpent\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]";

    private static String GUOMI_BINARY = "6060604052341561000c57fe5b5b6104368061001c6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680636cb021c214610067578063a6f321ea146100be578063a9ac5bda14610108578063b43f3b941461015f578063d888d21d146101a9575bfe5b341561006f57fe5b6100a4600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506101e8565b604051808215151515815260200191505060405180910390f35b34156100c657fe5b6100f2600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610277565b6040518082815260200191505060405180910390f35b341561011057fe5b610145600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506102c1565b604051808215151515815260200191505060405180910390f35b341561016757fe5b610193600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610377565b6040518082815260200191505060405180910390f35b34156101b157fe5b6101e6600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506103c1565b005b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482600060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401111590505b92915050565b6000600060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b919050565b6000600015156102d184846101e8565b151514156102e25760009050610371565b81600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205401600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060019050610371565b5b92915050565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b919050565b80600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505b50505600a165627a7a7230582056e99b9bc7bf0a4f54968b1ebc7f034b10a3d3dd328cf0a0d3fdfd117444a8520029";

    private Limitation(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private Limitation(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private Limitation(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private Limitation(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    public Future<Uint256> getLimit(Address account) {
        Function function = new Function("getLimit", 
                Arrays.<Type>asList(account), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> checkSpent(Address account, Uint256 limit) {
        Function function = new Function("checkSpent", 
                Arrays.<Type>asList(account, limit), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> addSpent(Address account, Uint256 limit) {
        Function function = new Function("addSpent", Arrays.<Type>asList(account, limit), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void addSpent(Address account, Uint256 limit, TransactionSucCallback callback) {
        Function function = new Function("addSpent", Arrays.<Type>asList(account, limit), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> setLimitation(Address account, Uint256 limit) {
        Function function = new Function("setLimitation", Arrays.<Type>asList(account, limit), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setLimitation(Address account, Uint256 limit, TransactionSucCallback callback) {
        Function function = new Function("setLimitation", Arrays.<Type>asList(account, limit), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Uint256> getSpent(Address account) {
        Function function = new Function("getSpent", 
                Arrays.<Type>asList(account), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<Limitation> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(Limitation.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Limitation> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(Limitation.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Limitation load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Limitation(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static Limitation load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Limitation(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static Limitation loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Limitation(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static Limitation loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Limitation(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static void setBinary(String binary) {
        BINARY = binary;
    }

    public static String getGuomiBinary() {
        return GUOMI_BINARY;
    }
}
