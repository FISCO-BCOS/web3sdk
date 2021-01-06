package org.fisco.bcos.web3j.precompile.gaschargemgr;

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
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
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
public class GasChargeManagePrecompiled extends Contract {
    public static final String[] BINARY_ARRAY = {
        "608060405234801561001057600080fd5b50610320806100206000396000f300608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063470553211461007d578063867bde5d146100e5578063a3ffa9cd1461013c578063a9f2b9a8146101a4578063caf39c5114610210578063f001f6a314610267575b600080fd5b34801561008957600080fd5b506100c8600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506102c5565b604051808381526020018281526020019250505060405180910390f35b3480156100f157600080fd5b50610126600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102cf565b6040518082815260200191505060405180910390f35b34801561014857600080fd5b50610187600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506102d6565b604051808381526020018281526020019250505060405180910390f35b3480156101b057600080fd5b506101b96102e0565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156101fc5780820151818401526020810190506101e1565b505050509050019250505060405180910390f35b34801561021c57600080fd5b50610251600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102e5565b6040518082815260200191505060405180910390f35b34801561027357600080fd5b506102a8600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102ec565b604051808381526020018281526020019250505060405180910390f35b6000809250929050565b6000919050565b6000809250929050565b606090565b6000919050565b6000809150915600a165627a7a723058207da4826d1fc97c388b06b4343c63fe33fac1be53c701dbdfc30b9bcdea6f89c60029"
    };

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"constant\":false,\"inputs\":[{\"name\":\"userAccount\",\"type\":\"address\"},{\"name\":\"gasValue\",\"type\":\"uint256\"}],\"name\":\"deduct\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"chargerAccount\",\"type\":\"address\"}],\"name\":\"revokeCharger\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"userAccount\",\"type\":\"address\"},{\"name\":\"gasValue\",\"type\":\"uint256\"}],\"name\":\"charge\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"listChargers\",\"outputs\":[{\"name\":\"\",\"type\":\"address[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"chargerAccount\",\"type\":\"address\"}],\"name\":\"grantCharger\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"userAccount\",\"type\":\"address\"}],\"name\":\"queryRemainGas\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"
    };

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String[] SM_BINARY_ARRAY = {
        "608060405234801561001057600080fd5b5061031f806100206000396000f300608060405260043610610077576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168062ba8f9b1461007c5780633009a33c146100da578063790162d714610131578063a06cc6ae14610199578063c74b68d914610205578063ec5975031461026d575b600080fd5b34801561008857600080fd5b506100bd600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102c4565b604051808381526020018281526020019250505060405180910390f35b3480156100e657600080fd5b5061011b600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102cc565b6040518082815260200191505060405180910390f35b34801561013d57600080fd5b5061017c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506102d3565b604051808381526020018281526020019250505060405180910390f35b3480156101a557600080fd5b506101ae6102dd565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156101f15780820151818401526020810190506101d6565b505050509050019250505060405180910390f35b34801561021157600080fd5b50610250600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506102e2565b604051808381526020018281526020019250505060405180910390f35b34801561027957600080fd5b506102ae600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102ec565b6040518082815260200191505060405180910390f35b600080915091565b6000919050565b6000809250929050565b606090565b6000809250929050565b60009190505600a165627a7a72305820ae1fe92ed3918d63631af60e34c33bb8f09575b75d9dec0f7018927c12ca03cf0029"
    };

    public static final String SM_BINARY = String.join("", SM_BINARY_ARRAY);

    public static final String FUNC_DEDUCT = "deduct";

    public static final String FUNC_REVOKECHARGER = "revokeCharger";

    public static final String FUNC_CHARGE = "charge";

    public static final String FUNC_LISTCHARGERS = "listChargers";

    public static final String FUNC_GRANTCHARGER = "grantCharger";

    public static final String FUNC_QUERYREMAINGAS = "queryRemainGas";

    @Deprecated
    protected GasChargeManagePrecompiled(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected GasChargeManagePrecompiled(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected GasChargeManagePrecompiled(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected GasChargeManagePrecompiled(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getBinary() {
        return (EncryptType.encryptType == EncryptType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<TransactionReceipt> deduct(String userAccount, BigInteger gasValue) {
        final Function function =
                new Function(
                        FUNC_DEDUCT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAccount),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(gasValue)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void deduct(String userAccount, BigInteger gasValue, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_DEDUCT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAccount),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(gasValue)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String deductSeq(String userAccount, BigInteger gasValue) {
        final Function function =
                new Function(
                        FUNC_DEDUCT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAccount),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(gasValue)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getDeductInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_DEDUCT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple2<String, BigInteger>(
                (String) results.get(0).getValue(), (BigInteger) results.get(1).getValue());
    }

    public Tuple2<BigInteger, BigInteger> getDeductOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_DEDUCT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple2<BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(), (BigInteger) results.get(1).getValue());
    }

    public RemoteCall<TransactionReceipt> revokeCharger(String chargerAccount) {
        final Function function =
                new Function(
                        FUNC_REVOKECHARGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(chargerAccount)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void revokeCharger(String chargerAccount, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_REVOKECHARGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(chargerAccount)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String revokeChargerSeq(String chargerAccount) {
        final Function function =
                new Function(
                        FUNC_REVOKECHARGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(chargerAccount)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getRevokeChargerInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_REVOKECHARGER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getRevokeChargerOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_REVOKECHARGER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> charge(String userAccount, BigInteger gasValue) {
        final Function function =
                new Function(
                        FUNC_CHARGE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAccount),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(gasValue)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void charge(String userAccount, BigInteger gasValue, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_CHARGE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAccount),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(gasValue)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String chargeSeq(String userAccount, BigInteger gasValue) {
        final Function function =
                new Function(
                        FUNC_CHARGE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAccount),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(gasValue)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getChargeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_CHARGE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple2<String, BigInteger>(
                (String) results.get(0).getValue(), (BigInteger) results.get(1).getValue());
    }

    public Tuple2<BigInteger, BigInteger> getChargeOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_CHARGE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple2<BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(), (BigInteger) results.get(1).getValue());
    }

    public RemoteCall<List> listChargers() {
        final Function function =
                new Function(
                        FUNC_LISTCHARGERS,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result =
                                (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<TransactionReceipt> grantCharger(String chargerAccount) {
        final Function function =
                new Function(
                        FUNC_GRANTCHARGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(chargerAccount)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void grantCharger(String chargerAccount, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTCHARGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(chargerAccount)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String grantChargerSeq(String chargerAccount) {
        final Function function =
                new Function(
                        FUNC_GRANTCHARGER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(chargerAccount)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getGrantChargerInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTCHARGER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getGrantChargerOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_GRANTCHARGER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> queryRemainGas(String userAccount) {
        final Function function =
                new Function(
                        FUNC_QUERYREMAINGAS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(userAccount)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    @Deprecated
    public static GasChargeManagePrecompiled load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new GasChargeManagePrecompiled(
                contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static GasChargeManagePrecompiled load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new GasChargeManagePrecompiled(
                contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static GasChargeManagePrecompiled load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new GasChargeManagePrecompiled(
                contractAddress, web3j, credentials, contractGasProvider);
    }

    public static GasChargeManagePrecompiled load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new GasChargeManagePrecompiled(
                contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<GasChargeManagePrecompiled> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                GasChargeManagePrecompiled.class,
                web3j,
                credentials,
                contractGasProvider,
                getBinary(),
                "");
    }

    @Deprecated
    public static RemoteCall<GasChargeManagePrecompiled> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                GasChargeManagePrecompiled.class,
                web3j,
                credentials,
                gasPrice,
                gasLimit,
                getBinary(),
                "");
    }

    public static RemoteCall<GasChargeManagePrecompiled> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                GasChargeManagePrecompiled.class,
                web3j,
                transactionManager,
                contractGasProvider,
                getBinary(),
                "");
    }

    @Deprecated
    public static RemoteCall<GasChargeManagePrecompiled> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                GasChargeManagePrecompiled.class,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                getBinary(),
                "");
    }
}
