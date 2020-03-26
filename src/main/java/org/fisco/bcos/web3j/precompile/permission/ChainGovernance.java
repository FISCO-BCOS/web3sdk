package org.fisco.bcos.web3j.precompile.permission;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
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
    public static String BINARY =
            "608060405234801561001057600080fd5b506104b6806100206000396000f3006080604052600436106100a4576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063039a93ca146100a9578063246c337614610139578063281af27d1461019a5780636c147119146101c55780636f8f521f1461021c578063885a3a721461027357806397b0086114610303578063cafb4d1b14610344578063e348da131461039b578063fad8b32a146103f2575b600080fd5b3480156100b557600080fd5b506100be610449565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100fe5780820151818401526020810190506100e3565b50505050905090810190601f16801561012b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561014557600080fd5b50610184600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061044e565b6040518082815260200191505060405180910390f35b3480156101a657600080fd5b506101af610456565b6040518082815260200191505060405180910390f35b3480156101d157600080fd5b50610206600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061045b565b6040518082815260200191505060405180910390f35b34801561022857600080fd5b5061025d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610462565b6040518082815260200191505060405180910390f35b34801561027f57600080fd5b50610288610469565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102c85780820151818401526020810190506102ad565b50505050905090810190601f1680156102f55780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561030f57600080fd5b5061032e6004803603810190808035906020019092919050505061046e565b6040518082815260200191505060405180910390f35b34801561035057600080fd5b50610385600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610475565b6040518082815260200191505060405180910390f35b3480156103a757600080fd5b506103dc600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061047c565b6040518082815260200191505060405180910390f35b3480156103fe57600080fd5b50610433600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610483565b6040518082815260200191505060405180910390f35b606090565b600092915050565b600090565b6000919050565b6000919050565b606090565b6000919050565b6000919050565b6000919050565b60009190505600a165627a7a7230582038309cf64d0a48d9bf0177b33fbe798ff039dbe98086afff53b71cd51486e8da0029";
    public static String SM_BINARY =
            "608060405234801561001057600080fd5b506104b6806100206000396000f3006080604052600436106100a4576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806335365efb146100a9578063681362f3146100d457806377cb09941461012b578063931af204146101bb57806395e96f8f146101fc578063b90059a31461025d578063c784c982146102ed578063c9ab206914610344578063cbff03461461039b578063d1db6540146103f2575b600080fd5b3480156100b557600080fd5b506100be610449565b6040518082815260200191505060405180910390f35b3480156100e057600080fd5b50610115600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061044e565b6040518082815260200191505060405180910390f35b34801561013757600080fd5b50610140610455565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610180578082015181840152602081019050610165565b50505050905090810190601f1680156101ad5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101c757600080fd5b506101e66004803603810190808035906020019092919050505061045a565b6040518082815260200191505060405180910390f35b34801561020857600080fd5b50610247600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610461565b6040518082815260200191505060405180910390f35b34801561026957600080fd5b50610272610469565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102b2578082015181840152602081019050610297565b50505050905090810190601f1680156102df5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156102f957600080fd5b5061032e600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061046e565b6040518082815260200191505060405180910390f35b34801561035057600080fd5b50610385600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610475565b6040518082815260200191505060405180910390f35b3480156103a757600080fd5b506103dc600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061047c565b6040518082815260200191505060405180910390f35b3480156103fe57600080fd5b50610433600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610483565b6040518082815260200191505060405180910390f35b600090565b6000919050565b606090565b6000919050565b600092915050565b606090565b6000919050565b6000919050565b6000919050565b60009190505600a165627a7a7230582077695986c7901963310c1933f735fa511756ddbe89bd2116074f9cc91d2f34570029";

    public static final String ABI =
            "[{\"constant\":true,\"inputs\":[],\"name\":\"listOperators\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"},{\"name\":\"weight\",\"type\":\"int256\"}],\"name\":\"updateCommitteeMemberWeight\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"queryThreshold\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"queryCommitteeMemberWeight\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantCommitteeMember\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"listCommitteeMembers\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"threshold\",\"type\":\"int256\"}],\"name\":\"updateThreshold\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"revokeCommitteeMember\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantOperator\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"revokeOperator\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";

    public static final String FUNC_LISTOPERATORS = "listOperators";

    public static final String FUNC_UPDATECOMMITTEEMEMBERWEIGHT = "updateCommitteeMemberWeight";

    public static final String FUNC_QUERYTHRESHOLD = "queryThreshold";

    public static final String FUNC_QUERYCOMMITTEEMEMBERWEIGHT = "queryCommitteeMemberWeight";

    public static final String FUNC_GRANTCOMMITTEEMEMBER = "grantCommitteeMember";

    public static final String FUNC_LISTCOMMITTEEMEMBERS = "listCommitteeMembers";

    public static final String FUNC_UPDATETHRESHOLD = "updateThreshold";

    public static final String FUNC_REVOKECOMMITTEEMEMBER = "revokeCommitteeMember";

    public static final String FUNC_GRANTOPERATOR = "grantOperator";

    public static final String FUNC_REVOKEOPERATOR = "revokeOperator";

    @Deprecated
    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChainGovernance(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getBinary() {
        return (EncryptType.encryptType == EncryptType.ECDSA_TYPE ? BINARY : SM_BINARY);
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

    public RemoteCall<BigInteger> queryCommitteeMemberWeight(String user) {
        final Function function =
                new Function(
                        FUNC_QUERYCOMMITTEEMEMBERWEIGHT,
                        Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(user)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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
                ChainGovernance.class, web3j, credentials, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<ChainGovernance> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                ChainGovernance.class, web3j, credentials, gasPrice, gasLimit, getBinary(), "");
    }

    public static RemoteCall<ChainGovernance> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                ChainGovernance.class,
                web3j,
                transactionManager,
                contractGasProvider,
                getBinary(),
                "");
    }

    @Deprecated
    public static RemoteCall<ChainGovernance> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                ChainGovernance.class,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                getBinary(),
                "");
    }
}
