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
    public static final String[] BINARY_ARRAY = {};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"constant\":true,\"inputs\":[],\"name\":\"listOperators\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"},{\"name\":\"weight\",\"type\":\"int256\"}],\"name\":\"updateCommitteeMemberWeight\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"queryThreshold\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"member\",\"type\":\"address\"}],\"name\":\"queryVotesOfMember\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"queryCommitteeMemberWeight\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantCommitteeMember\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"unfreezeAccount\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"queryVotesOfThreshold\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"listCommitteeMembers\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"threshold\",\"type\":\"int256\"}],\"name\":\"updateThreshold\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"revokeCommitteeMember\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantOperator\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"freezeAccount\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"revokeOperator\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"getAccountStatus\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"
    };

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_LISTOPERATORS = "listOperators";

    public static final String FUNC_UPDATECOMMITTEEMEMBERWEIGHT = "updateCommitteeMemberWeight";

    public static final String FUNC_QUERYTHRESHOLD = "queryThreshold";

    public static final String FUNC_QUERYVOTESOFMEMBER = "queryVotesOfMember";

    public static final String FUNC_QUERYCOMMITTEEMEMBERWEIGHT = "queryCommitteeMemberWeight";

    public static final String FUNC_GRANTCOMMITTEEMEMBER = "grantCommitteeMember";

    public static final String FUNC_UNFREEZEACCOUNT = "unfreezeAccount";

    public static final String FUNC_QUERYVOTESOFTHRESHOLD = "queryVotesOfThreshold";

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

    public RemoteCall<String> queryVotesOfMember(String member) {
        final Function function =
                new Function(
                        FUNC_QUERYVOTESOFMEMBER,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(member)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<String> queryVotesOfThreshold() {
        final Function function =
                new Function(
                        FUNC_QUERYVOTESOFTHRESHOLD,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
