package org.bcos.contract.source;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
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
public final class Authority extends Contract {
    private static final String BINARY = "";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"_version\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"origin\",\"type\":\"address\"}],\"name\":\"deploy\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"_desc\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"version\",\"type\":\"string\"}],\"name\":\"setVersion\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"desc\",\"type\":\"string\"}],\"name\":\"setDesc\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"enable\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"newGroup\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"setName\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"},{\"name\":\"group\",\"type\":\"address\"}],\"name\":\"setUserGroup\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"_name\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"getUserGroup\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"origin\",\"type\":\"address\"},{\"name\":\"from\",\"type\":\"address\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"func\",\"type\":\"string\"},{\"name\":\"input\",\"type\":\"string\"}],\"name\":\"process\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]";

    private Authority(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private Authority(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private Authority(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private Authority(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public Future<Utf8String> _version() {
        Function function = new Function("_version", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> deploy(Address origin) {
        Function function = new Function("deploy", 
                Arrays.<Type>asList(origin), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> _desc() {
        Function function = new Function("_desc", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setVersion(Utf8String version) {
        Function function = new Function("setVersion", Arrays.<Type>asList(version), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setVersion(Utf8String version, TransactionSucCallback callback) {
        Function function = new Function("setVersion", Arrays.<Type>asList(version), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> setDesc(Utf8String desc) {
        Function function = new Function("setDesc", Arrays.<Type>asList(desc), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setDesc(Utf8String desc, TransactionSucCallback callback) {
        Function function = new Function("setDesc", Arrays.<Type>asList(desc), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> enable() {
        Function function = new Function("enable", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void enable(TransactionSucCallback callback) {
        Function function = new Function("enable", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> newGroup() {
        Function function = new Function("newGroup", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void newGroup(TransactionSucCallback callback) {
        Function function = new Function("newGroup", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> setName(Utf8String name) {
        Function function = new Function("setName", Arrays.<Type>asList(name), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setName(Utf8String name, TransactionSucCallback callback) {
        Function function = new Function("setName", Arrays.<Type>asList(name), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> setUserGroup(Address user, Address group) {
        Function function = new Function("setUserGroup", Arrays.<Type>asList(user, group), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setUserGroup(Address user, Address group, TransactionSucCallback callback) {
        Function function = new Function("setUserGroup", Arrays.<Type>asList(user, group), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Utf8String> _name() {
        Function function = new Function("_name", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> getUserGroup(Address user) {
        Function function = new Function("getUserGroup", 
                Arrays.<Type>asList(user), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> process(Address origin, Address from, Address to, Utf8String func, Utf8String input) {
        Function function = new Function("process", 
                Arrays.<Type>asList(origin, from, to, func, input), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<Authority> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Authority.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Authority> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Authority.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Authority load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Authority(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static Authority load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Authority(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static Authority loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Authority(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static Authority loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Authority(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }
}
