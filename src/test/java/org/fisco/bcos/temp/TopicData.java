package org.fisco.bcos.temp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
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
public class TopicData extends Contract {
    private static final String BINARY = "6060604052610778806100126000396000f360606040523615610074576000357c01000000000000000000000000000000000000000000000000000000009004806308463c4014610079578063161ce27c146100ac57806365788256146100f85780636d33e1c81461012d578063ac2976f614610172578063cbea41d1146101b957610074565b610002565b34610002576100946004808035906020019091905050610224565b60405180821515815260200191505060405180910390f35b34610002576100c7600480803590602001909190505061032e565b6040518082600a602002808383829060006004602084601f0104600302600f01f15090500191505060405180910390f35b346100025761011360048080359060200190919050506103e6565b604051808260001916815260200191505060405180910390f35b346100025761015a600480803590602001909190803590602001909190803590602001909190505061040b565b60405180821515815260200191505060405180910390f35b346100025761018d6004808035906020019091905050610582565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34610002576101d46004808035906020019091905050610671565b604051808473ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff168152602001828152602001935050505060405180910390f35b6000606060405190810160405280600081526020016000815260200160008152602001506000600050600084600019168152602001908152602001600020600050606060405190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160028201600050548152602001505090508060400151600014151561031e576001915061032856610327565b60009150610328565b5b50919050565b61014060405190810160405280600a905b600081526020019060019003908161033f57905050600060006000600a925084600a029150600090505b828110156103d6576001600050805490508210156103c857600160005082815481101561000257906000526020600020900160005b50548482600a81101561000257909060200201906000191690818152602001505081806001019250505b5b8080600101915050610369565b8393506103de565b505050919050565b600160005081815481101561000257906000526020600020900160005b915090505481565b60006060604051908101604052806000815260200160008152602001600081526020015060606040519081016040528085815260200132815260200184815260200150905080600060005060008760001916815260200190815260200160002060005060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff02191690836c0100000000000000000000000090810204021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff02191690836c010000000000000000000000009081020402179055506040820151816002016000505590505060016000508054806001018281815481835581811511610556578183600052602060002091820191016105559190610537565b808211156105515760008181506000905550600101610537565b5090565b5b5050509190906000526020600020900160005b87909190915055506001915061057a565b509392505050565b6000606060405190810160405280600081526020016000815260200160008152602001506000600050600084600019168152602001908152602001600020600050606060405190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160028201600050548152602001505090508060000151915081505b50919050565b600060006000606060405190810160405280600081526020016000815260200160008152602001506000600050600086600019168152602001908152602001600020600050606060405190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160028201600050548152602001505090508060000151935083508060200151925082508060400151915081505b50919390925056";

    public static final String FUNC_ISTOPICEXIST = "isTopicExist";

    public static final String FUNC_LISTTOPIC = "listTopic";

    public static final String FUNC_TOPICARRAY = "topicArray";

    public static final String FUNC_PUTTOPIC = "putTopic";

    public static final String FUNC_GETTOPICADDRESS = "getTopicAddress";

    public static final String FUNC_GETTOPIC = "getTopic";

    @Deprecated
    protected TopicData(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TopicData(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TopicData(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TopicData(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Boolean> isTopicExist(byte[] topicName) {
        final Function function = new Function(FUNC_ISTOPICEXIST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(topicName)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<List> listTopic(BigInteger pageIndex) {
        final Function function = new Function(FUNC_LISTTOPIC, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(pageIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray10<Bytes32>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<byte[]> topicArray(BigInteger param0) {
        final Function function = new Function(FUNC_TOPICARRAY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> putTopic(byte[] _topicName, String _topicAddress, BigInteger _createdTimestamp) {
        final Function function = new Function(
                FUNC_PUTTOPIC, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_topicName), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(_topicAddress), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_createdTimestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getTopicAddress(byte[] topicName) {
        final Function function = new Function(FUNC_GETTOPICADDRESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(topicName)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> getTopic(byte[] topicName) {
        final Function function = new Function(FUNC_GETTOPIC, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(topicName)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static TopicData load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TopicData(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TopicData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TopicData(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TopicData load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TopicData(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TopicData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TopicData(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TopicData> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TopicData.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TopicData> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TopicData.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TopicData> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TopicData.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TopicData> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TopicData.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
