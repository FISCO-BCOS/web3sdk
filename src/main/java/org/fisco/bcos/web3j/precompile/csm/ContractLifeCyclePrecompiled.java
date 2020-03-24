package org.fisco.bcos.web3j.precompile.csm;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

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
public class ContractLifeCyclePrecompiled extends Contract {
    public static final String[] BINARY_ARRAY = {};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"constant\":true,\"inputs\":[{\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"getStatus\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"unfreeze\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"freeze\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"contractAddr\",\"type\":\"address\"},{\"name\":\"userAddr\",\"type\":\"address\"}],\"name\":\"grantManager\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"listManager\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"address[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"
    };

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_GETSTATUS = "getStatus";

    public static final String FUNC_UNFREEZE = "unfreeze";

    public static final String FUNC_FREEZE = "freeze";

    public static final String FUNC_GRANTMANAGER = "grantManager";

    public static final String FUNC_LISTMANAGER = "listManager";

    @Deprecated
    protected ContractLifeCyclePrecompiled(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractLifeCyclePrecompiled(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ContractLifeCyclePrecompiled(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ContractLifeCyclePrecompiled(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<Tuple2<BigInteger, String>> getStatus(String addr) {
        final Function function =
                new Function(
                        FUNC_GETSTATUS,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Int256>() {},
                                new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<BigInteger, String>>(
                new Callable<Tuple2<BigInteger, String>>() {
                    @Override
                    public Tuple2<BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, String>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> unfreeze(String addr) {
        final Function function =
                new Function(
                        FUNC_UNFREEZE,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void unfreeze(String addr, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_UNFREEZE,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String unfreezeSeq(String addr) {
        final Function function =
                new Function(
                        FUNC_UNFREEZE,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getUnfreezeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_UNFREEZE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getUnfreezeOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_UNFREEZE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> freeze(String addr) {
        final Function function =
                new Function(
                        FUNC_FREEZE,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void freeze(String addr, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_FREEZE,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String freezeSeq(String addr) {
        final Function function =
                new Function(
                        FUNC_FREEZE,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getFreezeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_FREEZE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getFreezeOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_FREEZE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> grantManager(String contractAddr, String userAddr) {
        final Function function =
                new Function(
                        FUNC_GRANTMANAGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(contractAddr),
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAddr)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void grantManager(
            String contractAddr, String userAddr, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTMANAGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(contractAddr),
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAddr)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String grantManagerSeq(String contractAddr, String userAddr) {
        final Function function =
                new Function(
                        FUNC_GRANTMANAGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(contractAddr),
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAddr)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, String> getGrantManagerInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTMANAGER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public Tuple1<BigInteger> getGrantManagerOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_GRANTMANAGER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<Tuple2<BigInteger, List<String>>> listManager(String addr) {
        final Function function =
                new Function(
                        FUNC_LISTMANAGER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(addr)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Int256>() {},
                                new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<Tuple2<BigInteger, List<String>>>(
                new Callable<Tuple2<BigInteger, List<String>>>() {
                    @Override
                    public Tuple2<BigInteger, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, List<String>>(
                                (BigInteger) results.get(0).getValue(),
                                convertToNative((List<Address>) results.get(1).getValue()));
                    }
                });
    }

    @Deprecated
    public static ContractLifeCyclePrecompiled load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new ContractLifeCyclePrecompiled(
                contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ContractLifeCyclePrecompiled load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new ContractLifeCyclePrecompiled(
                contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ContractLifeCyclePrecompiled load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new ContractLifeCyclePrecompiled(
                contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContractLifeCyclePrecompiled load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new ContractLifeCyclePrecompiled(
                contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ContractLifeCyclePrecompiled> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                ContractLifeCyclePrecompiled.class,
                web3j,
                credentials,
                contractGasProvider,
                BINARY,
                "");
    }

    @Deprecated
    public static RemoteCall<ContractLifeCyclePrecompiled> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                ContractLifeCyclePrecompiled.class,
                web3j,
                credentials,
                gasPrice,
                gasLimit,
                BINARY,
                "");
    }

    public static RemoteCall<ContractLifeCyclePrecompiled> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                ContractLifeCyclePrecompiled.class,
                web3j,
                transactionManager,
                contractGasProvider,
                BINARY,
                "");
    }

    @Deprecated
    public static RemoteCall<ContractLifeCyclePrecompiled> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                ContractLifeCyclePrecompiled.class,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                BINARY,
                "");
    }
}
