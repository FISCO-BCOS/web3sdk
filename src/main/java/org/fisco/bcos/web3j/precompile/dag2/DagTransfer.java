package org.fisco.bcos.web3j.precompile.dag2;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
public class DagTransfer extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506103dc806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b37617b146100725780631536aedd146101435780633fe8e3f5146101cb578063e555f3d914610256578063ff2b0127146102e1575b600080fd5b34801561007e57600080fd5b50610129600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061036c565b604051808215151515815260200191505060405180910390f35b34801561014f57600080fd5b506101aa600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610379565b60405180831515151581526020018281526020019250505060405180910390f35b3480156101d757600080fd5b5061023c600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061038c565b604051808215151515815260200191505060405180910390f35b34801561026257600080fd5b506102c7600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610398565b604051808215151515815260200191505060405180910390f35b3480156102ed57600080fd5b50610352600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506103a4565b604051808215151515815260200191505060405180910390f35b6000600190509392505050565b6000806001600080905091509150915091565b60006001905092915050565b60006001905092915050565b600060019050929150505600a165627a7a7230582028f16f1ba9806903f6d8db7cc987df1cfaf7567526c8631d292111511d73c73b0029";

    public static final String FUNC_USERTRANSFER = "userTransfer";

    public static final String FUNC_USERBALANCE = "userBalance";

    public static final String FUNC_USERADD = "userAdd";

    public static final String FUNC_USERSAVE = "userSave";

    public static final String FUNC_USERDRAW = "userDraw";

    @Deprecated
    protected DagTransfer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DagTransfer(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DagTransfer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DagTransfer(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> userTransfer(String user_a, String user_b, BigInteger amount) {
        final Function function = new Function(
                FUNC_USERTRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_a), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_b), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<Boolean, BigInteger>> userBalance(String user) {
        final Function function = new Function(FUNC_USERBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<Boolean, BigInteger>>(
                new Callable<Tuple2<Boolean, BigInteger>>() {
                    @Override
                    public Tuple2<Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> userAdd(String user, BigInteger balance) {
        final Function function = new Function(
                FUNC_USERADD, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> userSave(String user, BigInteger balance) {
        final Function function = new Function(
                FUNC_USERSAVE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> userDraw(String user, BigInteger balance) {
        final Function function = new Function(
                FUNC_USERDRAW, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static DagTransfer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DagTransfer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DagTransfer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DagTransfer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DagTransfer load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DagTransfer(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DagTransfer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DagTransfer(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DagTransfer> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DagTransfer.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DagTransfer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DagTransfer.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<DagTransfer> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DagTransfer.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DagTransfer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DagTransfer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
