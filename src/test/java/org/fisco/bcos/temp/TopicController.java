package org.fisco.bcos.temp;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
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
public class TopicController extends Contract {
    private static final String BINARY = "6060604052604051602080610701833981016040528080519060200190919050505b80600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff02191690836c010000000000000000000000009081020402179055505b506106938061006e6000396000f360606040526000357c01000000000000000000000000000000000000000000000000000000009004806304fc45281461005d578063317ea4d5146100c2578063a10d4f2f14610107578063ac2976f61461017257610058565b610002565b346100025761007860048080359060200190919050506101b9565b60405180806020018281038252838181518152602001915080519060200190602002808383829060006004602084601f0104600302600f01f1509050019250505060405180910390f35b34610002576100ef600480803590602001909190803590602001909190803590602001909190505061033b565b60405180821515815260200191505060405180910390f35b34610002576101226004808035906020019091905050610511565b604051808473ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff168152602001828152602001935050505060405180910390f35b346100025761018d60048080359060200190919050506105df565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b602060405190810160405280600081526020015061014060405190810160405280600a905b60008152602001906001900390816101de5790505060206040519081016040528060008152602001506000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663161ce27c8660006040516101400152604051827c01000000000000000000000000000000000000000000000000000000000281526004018082815260200191505061014060405180830381600087803b156100025760325a03f1156100025750505060405180610140016040529250600a6040518059106102c15750595b9080825280602002602001820160405280156102d8575b509150600090505b600a81101561032b578281600a8110156100025790906020020151828281518110156100025790602001906020020190600019169081815260200150505b80806001019150506102e0565b819350610333565b505050919050565b6000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166308463c4085600060405160200152604051827c01000000000000000000000000000000000000000000000000000000000281526004018082600019168152602001915050602060405180830381600087803b156100025760325a03f115610002575050506040518051906020015015610431577f13c45be6a13a69b72e3af68ac93a23b440f116e2fca46ff32202473842c771066207a1846040518082815260200191505060405180910390a16000905061050a56610509565b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16636d33e1c8858585600060405160200152604051847c010000000000000000000000000000000000000000000000000000000002815260040180846000191681526020018373ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b156100025760325a03f1156100025750505060405180519060200150506001905061050a565b5b9392505050565b600060006000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663cbea41d185600060405160600152604051827c01000000000000000000000000000000000000000000000000000000000281526004018082600019168152602001915050606060405180830381600087803b156100025760325a03f11561000257505050604051805190602001805190602001805190602001508093508194508295505050505b9193909250565b6000600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663ac2976f683600060405160200152604051827c01000000000000000000000000000000000000000000000000000000000281526004018082600019168152602001915050602060405180830381600087803b156100025760325a03f1156100025750505060405180519060200150905080505b91905056";

    public static final String FUNC_LISTTOPICNAME = "listTopicName";

    public static final String FUNC_ADDTOPICINFO = "addTopicInfo";

    public static final String FUNC_GETTOPICINFO = "getTopicInfo";

    public static final String FUNC_GETTOPICADDRESS = "getTopicAddress";

    public static final Event LOGADDTOPICNAMEADDRESS_EVENT = new Event("LogAddTopicNameAddress", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected TopicController(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TopicController(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TopicController(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TopicController(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<List> listTopicName(BigInteger pageIndex) {
        final Function function = new Function(FUNC_LISTTOPICNAME, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(pageIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
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

    public RemoteCall<TransactionReceipt> addTopicInfo(byte[] topicName, String topicAddress, BigInteger createdTimestamp) {
        final Function function = new Function(
                FUNC_ADDTOPICINFO, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(topicName), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(topicAddress), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(createdTimestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> getTopicInfo(byte[] topicName) {
        final Function function = new Function(FUNC_GETTOPICINFO, 
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

    public RemoteCall<String> getTopicAddress(byte[] topicName) {
        final Function function = new Function(FUNC_GETTOPICADDRESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(topicName)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<LogAddTopicNameAddressEventResponse> getLogAddTopicNameAddressEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGADDTOPICNAMEADDRESS_EVENT, transactionReceipt);
        ArrayList<LogAddTopicNameAddressEventResponse> responses = new ArrayList<LogAddTopicNameAddressEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogAddTopicNameAddressEventResponse typedResponse = new LogAddTopicNameAddressEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.retCode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogAddTopicNameAddressEventResponse> logAddTopicNameAddressEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogAddTopicNameAddressEventResponse>() {
            @Override
            public LogAddTopicNameAddressEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGADDTOPICNAMEADDRESS_EVENT, log);
                LogAddTopicNameAddressEventResponse typedResponse = new LogAddTopicNameAddressEventResponse();
                typedResponse.log = log;
                typedResponse.retCode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogAddTopicNameAddressEventResponse> logAddTopicNameAddressEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGADDTOPICNAMEADDRESS_EVENT));
        return logAddTopicNameAddressEventFlowable(filter);
    }

    @Deprecated
    public static TopicController load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TopicController(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TopicController load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TopicController(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TopicController load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TopicController(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TopicController load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TopicController(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TopicController> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String topicDataAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(topicDataAddress)));
        return deployRemoteCall(TopicController.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<TopicController> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String topicDataAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(topicDataAddress)));
        return deployRemoteCall(TopicController.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<TopicController> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String topicDataAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(topicDataAddress)));
        return deployRemoteCall(TopicController.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<TopicController> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String topicDataAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(topicDataAddress)));
        return deployRemoteCall(TopicController.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static class LogAddTopicNameAddressEventResponse {
        public Log log;

        public BigInteger retCode;
    }
}
