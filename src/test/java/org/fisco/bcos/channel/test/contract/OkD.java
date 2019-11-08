package org.fisco.bcos.channel.test.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
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
public class OkD extends Contract {
    private static final String BINARY =
            "608060405234801561001057600080fd5b50600080600060016000800160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506402540be40060006001018190555060028060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600060026001018190555061100192508273ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260048152602001807f745f6f6b000000000000000000000000000000000000000000000000000000008152506020018481038352600b8152602001807f66726f6d5f6163636f7574000000000000000000000000000000000000000000815250602001848103825260218152602001807f66726f6d5f62616c616e63652c746f5f6163636f75742c746f5f62616c616e6381526020017f65000000000000000000000000000000000000000000000000000000000000008152506040019350505050602060405180830381600087803b1580156101f557600080fd5b505af1158015610209573d6000803e3d6000fd5b505050506040513d602081101561021f57600080fd5b81019080805190602001909291905050505061100192508273ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f745f6f6b00000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156102d657600080fd5b505af11580156102ea573d6000803e3d6000fd5b505050506040513d602081101561030057600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561037757600080fd5b505af115801561038b573d6000803e3d6000fd5b505050506040513d60208110156103a157600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff1663e942b5166040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001806020018381038352600b8152602001807f66726f6d5f6163636f7574000000000000000000000000000000000000000000815250602001838103825260038152602001807f307831000000000000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561048d57600080fd5b505af11580156104a1573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff16632ef8ba746402540be4006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600c8152602001807f66726f6d5f62616c616e6365000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561055257600080fd5b505af1158015610566573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff1663e942b5166040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f746f5f6163636f75740000000000000000000000000000000000000000000000815250602001838103825260038152602001807f307832000000000000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561064357600080fd5b505af1158015610657573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff16632ef8ba7460006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600a8152602001807f746f5f62616c616e63650000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561070457600080fd5b505af1158015610718573d6000803e3d6000fd5b505050505050506107f98061072e6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680636d4ce63c14610051578063abe181b51461007c575b600080fd5b34801561005d57600080fd5b506100666100ef565b6040518082815260200191505060405180910390f35b34801561008857600080fd5b506100ed600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506100fc565b005b6000600260010154905090565b60008060008084600060010154036000600101819055508460026001016000828254019250508190555061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f745f6f6b00000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156101cb57600080fd5b505af11580156101df573d6000803e3d6000fd5b505050506040513d60208110156101f557600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561026c57600080fd5b505af1158015610280573d6000803e3d6000fd5b505050506040513d602081101561029657600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001806020018381038352600b8152602001807f66726f6d5f6163636f7574000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561036957808201518184015260208101905061034e565b50505050905090810190601f1680156103965780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156103b657600080fd5b505af11580156103ca573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba746000600101546040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600c8152602001807f66726f6d5f62616c616e6365000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561047b57600080fd5b505af115801561048f573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b5166040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f746f5f6163636f75740000000000000000000000000000000000000000000000815250602001838103825260038152602001807f307832000000000000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561056c57600080fd5b505af1158015610580573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba746002600101546040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600a8152602001807f746f5f62616c616e63650000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561063157600080fd5b505af1158015610645573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3687846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156107045780820151818401526020810190506106e9565b50505050905090810190601f1680156107315780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561075157600080fd5b505af1158015610765573d6000803e3d6000fd5b505050506040513d602081101561077b57600080fd5b810190808051906020019092919050505090507f66f7705280112a4d1145399e0414adc43a2d6974b487710f417edcf7d4a39d71816040518082815260200191505060405180910390a15050505050505600a165627a7a72305820766a85ac319262dcc91e61bbc2109bdc2369257905f4ca2b1751835597afff7b0029";

    public static final String FUNC_GET = "get";

    public static final String FUNC_TRANS = "trans";

    public static final Event INSERTRESULT_EVENT =
            new Event(
                    "insertResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

    @Deprecated
    protected OkD(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OkD(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OkD(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OkD(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> get() {
        final Function function =
                new Function(
                        FUNC_GET,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> trans(String from_accout, BigInteger num) {
        final Function function =
                new Function(
                        FUNC_TRANS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(from_accout),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(num)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void trans(String from_accout, BigInteger num, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_TRANS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(from_accout),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(num)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public List<InsertResultEventResponse> getInsertResultEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(INSERTRESULT_EVENT, transactionReceipt);
        ArrayList<InsertResultEventResponse> responses =
                new ArrayList<InsertResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InsertResultEventResponse typedResponse = new InsertResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    @Deprecated
    public static OkD load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new OkD(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OkD load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new OkD(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OkD load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new OkD(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OkD load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new OkD(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OkD> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OkD.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<OkD> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                OkD.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OkD> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OkD.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OkD> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                OkD.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class InsertResultEventResponse {
        public Log log;

        public BigInteger count;
    }
}
