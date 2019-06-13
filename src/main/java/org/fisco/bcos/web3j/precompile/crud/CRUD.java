package org.fisco.bcos.web3j.precompile.crud;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class CRUD extends Contract {
    private static final String BINARY = "";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_SELECT = "select";

    public static final String FUNC_INSERT = "insert";

    public static final String FUNC_REMOVE = "remove";

    @Deprecated
    protected CRUD(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CRUD(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CRUD(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CRUD(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> update(
            String tableName, String key, String entry, String condition, String optional) {
        final Function function =
                new Function(
                        FUNC_UPDATE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(tableName),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(key),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(entry),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(condition),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(optional)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void update(
            String tableName,
            String key,
            String entry,
            String condition,
            String optional,
            TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_UPDATE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(tableName),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(key),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(entry),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(condition),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(optional)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<String> select(
            String tableName, String key, String condition, String optional) {
        final Function function =
                new Function(
                        FUNC_SELECT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(tableName),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(key),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(condition),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(optional)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> insert(
            String tableName, String key, String entry, String optional) {
        final Function function =
                new Function(
                        FUNC_INSERT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(tableName),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(key),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(entry),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(optional)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void insert(
            String tableName,
            String key,
            String entry,
            String optional,
            TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_INSERT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(tableName),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(key),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(entry),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(optional)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> remove(
            String tableName, String key, String condition, String optional) {
        final Function function =
                new Function(
                        FUNC_REMOVE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(tableName),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(key),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(condition),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(optional)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void remove(
            String tableName,
            String key,
            String condition,
            String optional,
            TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_REMOVE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(tableName),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(key),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(condition),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(optional)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    @Deprecated
    public static CRUD load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new CRUD(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CRUD load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new CRUD(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CRUD load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new CRUD(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CRUD load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new CRUD(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CRUD> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CRUD.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CRUD> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CRUD.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CRUD> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                CRUD.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CRUD> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                CRUD.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
