package org.fisco.bcos.channel.test.parallel.precompile;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
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
public class DagTransfer extends Contract {
    private static final String BINARY =
            "608060405234801561001057600080fd5b506103c6806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b37617b146100725780631536aedd1461013f5780633fe8e3f5146101c3578063e555f3d91461024a578063ff2b0127146102d1575b600080fd5b34801561007e57600080fd5b50610129600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610358565b6040518082815260200191505060405180910390f35b34801561014b57600080fd5b506101a6600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610364565b604051808381526020018281526020019250505060405180910390f35b3480156101cf57600080fd5b50610234600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610379565b6040518082815260200191505060405180910390f35b34801561025657600080fd5b506102bb600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610384565b6040518082815260200191505060405180910390f35b3480156102dd57600080fd5b50610342600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061038f565b6040518082815260200191505060405180910390f35b60008090509392505050565b60008060008081915080905091509150915091565b600080905092915050565b600080905092915050565b6000809050929150505600a165627a7a723058207ff2a508f6093d5b125faa8cb120d0157aa5384ad0b65abc9c8d9b9b7d1eaaca0029";
    private static final String BINARY_GM =
            "608060405234801561001057600080fd5b506103c6806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806301d04396146100725780631626c092146100f65780632f327550146101c3578063622793a51461024a578063ebceb511146102d1575b600080fd5b34801561007e57600080fd5b506100d9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610358565b604051808381526020018281526020019250505060405180910390f35b34801561010257600080fd5b506101ad600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061036d565b6040518082815260200191505060405180910390f35b3480156101cf57600080fd5b50610234600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610379565b6040518082815260200191505060405180910390f35b34801561025657600080fd5b506102bb600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610384565b6040518082815260200191505060405180910390f35b3480156102dd57600080fd5b50610342600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061038f565b6040518082815260200191505060405180910390f35b60008060008081915080905091509150915091565b60008090509392505050565b600080905092915050565b600080905092915050565b6000809050929150505600a165627a7a723058200d11c2df16dd0f16c96758ea0dc32a74ec0fe83a8b6f7d9871a807be5dd4a8580029";

    public static final String FUNC_USERTRANSFER = "userTransfer";

    public static final String FUNC_USERBALANCE = "userBalance";

    public static final String FUNC_USERADD = "userAdd";

    public static final String FUNC_USERSAVE = "userSave";

    public static final String FUNC_USERDRAW = "userDraw";

    public static final String getBinary() {
        return (EncryptType.encryptType == EncryptType.ECDSA_TYPE ? BINARY : BINARY_GM);
    }

    @Deprecated
    protected DagTransfer(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DagTransfer(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DagTransfer(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DagTransfer(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> userTransfer(
            String user_a, String user_b, BigInteger amount) {
        final Function function =
                new Function(
                        FUNC_USERTRANSFER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_a),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_b),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void userTransfer(
            String user_a, String user_b, BigInteger amount, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_USERTRANSFER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_a),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_b),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String userTransferSeq(String user_a, String user_b, BigInteger amount) {
        final Function function =
                new Function(
                        FUNC_USERTRANSFER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_a),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user_b),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> userBalance(String user) {
        final Function function =
                new Function(
                        FUNC_USERBALANCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
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

    public RemoteCall<TransactionReceipt> userAdd(String user, BigInteger balance) {
        final Function function =
                new Function(
                        FUNC_USERADD,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void userAdd(String user, BigInteger balance, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_USERADD,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String userAddSeq(String user, BigInteger balance) {
        final Function function =
                new Function(
                        FUNC_USERADD,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> userSave(String user, BigInteger balance) {
        final Function function =
                new Function(
                        FUNC_USERSAVE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void userSave(String user, BigInteger balance, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_USERSAVE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String userSaveSeq(String user, BigInteger balance) {
        final Function function =
                new Function(
                        FUNC_USERSAVE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> userDraw(String user, BigInteger balance) {
        final Function function =
                new Function(
                        FUNC_USERDRAW,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void userDraw(String user, BigInteger balance, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_USERDRAW,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String userDrawSeq(String user, BigInteger balance) {
        final Function function =
                new Function(
                        FUNC_USERDRAW,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balance)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    @Deprecated
    public static DagTransfer load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new DagTransfer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DagTransfer load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new DagTransfer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DagTransfer load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new DagTransfer(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DagTransfer load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new DagTransfer(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DagTransfer> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                DagTransfer.class, web3j, credentials, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DagTransfer> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                DagTransfer.class, web3j, credentials, gasPrice, gasLimit, getBinary(), "");
    }

    public static RemoteCall<DagTransfer> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                DagTransfer.class, web3j, transactionManager, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DagTransfer> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                DagTransfer.class, web3j, transactionManager, gasPrice, gasLimit, getBinary(), "");
    }
}
