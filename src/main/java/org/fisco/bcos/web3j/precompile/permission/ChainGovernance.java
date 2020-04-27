package org.fisco.bcos.web3j.precompile.permission;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
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
public class ChainGovernance extends Contract {
    public static final String[] BINARY_ARRAY = {
        "608060405234801561001057600080fd5b50610662806100206000396000f3006080604052600436106100c5576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063039a93ca146100ca578063246c33761461015a578063281af27d146101bb5780636c147119146101e65780636f8f521f14610248578063788649ea1461029f578063885a3a72146102f657806397b0086114610386578063cafb4d1b146103c7578063e348da131461041e578063f26c159f14610475578063fad8b32a146104cc578063fd4fa05a14610523575b600080fd5b3480156100d657600080fd5b506100df6105df565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561011f578082015181840152602081019050610104565b50505050905090810190601f16801561014c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561016657600080fd5b506101a5600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506105e4565b6040518082815260200191505060405180910390f35b3480156101c757600080fd5b506101d06105ec565b6040518082815260200191505060405180910390f35b3480156101f257600080fd5b50610227600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506105f1565b60405180831515151581526020018281526020019250505060405180910390f35b34801561025457600080fd5b50610289600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506105f9565b6040518082815260200191505060405180910390f35b3480156102ab57600080fd5b506102e0600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610600565b6040518082815260200191505060405180910390f35b34801561030257600080fd5b5061030b610607565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561034b578082015181840152602081019050610330565b50505050905090810190601f1680156103785780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561039257600080fd5b506103b16004803603810190808035906020019092919050505061060c565b6040518082815260200191505060405180910390f35b3480156103d357600080fd5b50610408600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610613565b6040518082815260200191505060405180910390f35b34801561042a57600080fd5b5061045f600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061061a565b6040518082815260200191505060405180910390f35b34801561048157600080fd5b506104b6600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610621565b6040518082815260200191505060405180910390f35b3480156104d857600080fd5b5061050d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610628565b6040518082815260200191505060405180910390f35b34801561052f57600080fd5b50610564600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061062f565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156105a4578082015181840152602081019050610589565b50505050905090810190601f1680156105d15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b606090565b600092915050565b600090565b600080915091565b6000919050565b6000919050565b606090565b6000919050565b6000919050565b6000919050565b6000919050565b6000919050565b60609190505600a165627a7a72305820a0ae9c325ed55ac24a87ece53995ff935662ff892b5a36213b0d09d926b46f960029"
    };

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"constant\":true,\"inputs\":[],\"name\":\"listOperators\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"},{\"name\":\"weight\",\"type\":\"int256\"}],\"name\":\"updateCommitteeMemberWeight\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"queryThreshold\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"queryCommitteeMemberWeight\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantCommitteeMember\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"unfreezeAccount\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"listCommitteeMembers\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"threshold\",\"type\":\"int256\"}],\"name\":\"updateThreshold\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"revokeCommitteeMember\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantOperator\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"freezeAccount\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"revokeOperator\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"getAccountStatus\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"
    };

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_LISTOPERATORS = "listOperators";

    public static final String FUNC_UPDATECOMMITTEEMEMBERWEIGHT = "updateCommitteeMemberWeight";

    public static final String FUNC_QUERYTHRESHOLD = "queryThreshold";

    public static final String FUNC_QUERYCOMMITTEEMEMBERWEIGHT = "queryCommitteeMemberWeight";

    public static final String FUNC_GRANTCOMMITTEEMEMBER = "grantCommitteeMember";

    public static final String FUNC_UNFREEZEACCOUNT = "unfreezeAccount";

    public static final String FUNC_LISTCOMMITTEEMEMBERS = "listCommitteeMembers";

    public static final String FUNC_UPDATETHRESHOLD = "updateThreshold";

    public static final String FUNC_REVOKECOMMITTEEMEMBER = "revokeCommitteeMember";

    public static final String FUNC_GRANTOPERATOR = "grantOperator";

    public static final String FUNC_FREEZEACCOUNT = "freezeAccount";

    public static final String FUNC_REVOKEOPERATOR = "revokeOperator";

    public static final String FUNC_GETACCOUNTSTATUS = "getAccountStatus";

    @Deprecated
    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<String> listOperators() {
        final Function function =
                new Function(
                        FUNC_LISTOPERATORS,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> updateCommitteeMemberWeight(
            String user, BigInteger weight) {
        final Function function =
                new Function(
                        FUNC_UPDATECOMMITTEEMEMBERWEIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(weight)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateCommitteeMemberWeight(
            String user, BigInteger weight, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_UPDATECOMMITTEEMEMBERWEIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(weight)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateCommitteeMemberWeightSeq(String user, BigInteger weight) {
        final Function function =
                new Function(
                        FUNC_UPDATECOMMITTEEMEMBERWEIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(user),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(weight)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getUpdateCommitteeMemberWeightInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_UPDATECOMMITTEEMEMBERWEIGHT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {}, new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple2<String, BigInteger>(
                (String) results.get(0).getValue(), (BigInteger) results.get(1).getValue());
    }

    public Tuple1<BigInteger> getUpdateCommitteeMemberWeightOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_UPDATECOMMITTEEMEMBERWEIGHT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<BigInteger> queryThreshold() {
        final Function function =
                new Function(
                        FUNC_QUERYTHRESHOLD,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple2<Boolean, BigInteger>> queryCommitteeMemberWeight(String user) {
        final Function function =
                new Function(
                        FUNC_QUERYCOMMITTEEMEMBERWEIGHT,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Bool>() {}, new TypeReference<Int256>() {}));
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

    public RemoteCall<TransactionReceipt> grantCommitteeMember(String user) {
        final Function function =
                new Function(
                        FUNC_GRANTCOMMITTEEMEMBER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void grantCommitteeMember(String user, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTCOMMITTEEMEMBER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String grantCommitteeMemberSeq(String user) {
        final Function function =
                new Function(
                        FUNC_GRANTCOMMITTEEMEMBER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getGrantCommitteeMemberInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTCOMMITTEEMEMBER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getGrantCommitteeMemberOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_GRANTCOMMITTEEMEMBER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> unfreezeAccount(String account) {
        final Function function =
                new Function(
                        FUNC_UNFREEZEACCOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void unfreezeAccount(String account, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_UNFREEZEACCOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String unfreezeAccountSeq(String account) {
        final Function function =
                new Function(
                        FUNC_UNFREEZEACCOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getUnfreezeAccountInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_UNFREEZEACCOUNT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getUnfreezeAccountOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_UNFREEZEACCOUNT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<String> listCommitteeMembers() {
        final Function function =
                new Function(
                        FUNC_LISTCOMMITTEEMEMBERS,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> updateThreshold(BigInteger threshold) {
        final Function function =
                new Function(
                        FUNC_UPDATETHRESHOLD,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(threshold)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateThreshold(BigInteger threshold, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_UPDATETHRESHOLD,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(threshold)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateThresholdSeq(BigInteger threshold) {
        final Function function =
                new Function(
                        FUNC_UPDATETHRESHOLD,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(threshold)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<BigInteger> getUpdateThresholdInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_UPDATETHRESHOLD,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getUpdateThresholdOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_UPDATETHRESHOLD,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> revokeCommitteeMember(String user) {
        final Function function =
                new Function(
                        FUNC_REVOKECOMMITTEEMEMBER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void revokeCommitteeMember(String user, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_REVOKECOMMITTEEMEMBER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String revokeCommitteeMemberSeq(String user) {
        final Function function =
                new Function(
                        FUNC_REVOKECOMMITTEEMEMBER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getRevokeCommitteeMemberInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_REVOKECOMMITTEEMEMBER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getRevokeCommitteeMemberOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_REVOKECOMMITTEEMEMBER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> grantOperator(String user) {
        final Function function =
                new Function(
                        FUNC_GRANTOPERATOR,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void grantOperator(String user, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTOPERATOR,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String grantOperatorSeq(String user) {
        final Function function =
                new Function(
                        FUNC_GRANTOPERATOR,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getGrantOperatorInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTOPERATOR,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getGrantOperatorOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_GRANTOPERATOR,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> freezeAccount(String account) {
        final Function function =
                new Function(
                        FUNC_FREEZEACCOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void freezeAccount(String account, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_FREEZEACCOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String freezeAccountSeq(String account) {
        final Function function =
                new Function(
                        FUNC_FREEZEACCOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getFreezeAccountInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_FREEZEACCOUNT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getFreezeAccountOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_FREEZEACCOUNT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<TransactionReceipt> revokeOperator(String user) {
        final Function function =
                new Function(
                        FUNC_REVOKEOPERATOR,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void revokeOperator(String user, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_REVOKEOPERATOR,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String revokeOperatorSeq(String user) {
        final Function function =
                new Function(
                        FUNC_REVOKEOPERATOR,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getRevokeOperatorInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_REVOKEOPERATOR,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Tuple1<BigInteger> getRevokeOperatorOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_REVOKEOPERATOR,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public RemoteCall<String> getAccountStatus(String account) {
        final Function function =
                new Function(
                        FUNC_GETACCOUNTSTATUS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Address(account)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static ChainGovernance load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new ChainGovernance(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChainGovernance load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new ChainGovernance(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChainGovernance load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new ChainGovernance(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChainGovernance load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new ChainGovernance(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChainGovernance> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                ChainGovernance.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ChainGovernance> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                ChainGovernance.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ChainGovernance> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                ChainGovernance.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ChainGovernance> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                ChainGovernance.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
