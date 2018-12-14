package org.fisco.bcos.temp;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class Topic extends Contract {
    private static final String BINARY = "606060405260016000600050556101848061001a6000396000f360606040526000357c010000000000000000000000000000000000000000000000000000000090048063635a47ff1461003c57610037565b610002565b346100025761009e6004808035906020019091908035906020019082018035906020019191908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509090919050506100b6565b60405180821515815260200191505060405180910390f35b60007fad6f1fe737d306b775970e326922a0deda069ac7d50a095a2a39453f6085da78836000600081815054809291906001019190505543856040518085600019168152602001848152602001838152602001806020018281038252838181518152602001915080519060200190808383829060006004602084601f0104600302600f01f150905090810190601f1680156101655780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a16001905061017e565b9291505056";

    public static final String FUNC_PUBLISHWEEVENT = "publishWeEvent";

    public static final Event LOGWEEVENT_EVENT = new Event("LogWeEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Topic(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Topic(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Topic(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Topic(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> publishWeEvent(byte[] topicName, String eventContent) {
        final Function function = new Function(
                FUNC_PUBLISHWEEVENT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(topicName), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(eventContent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<LogWeEventEventResponse> getLogWeEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGWEEVENT_EVENT, transactionReceipt);
        ArrayList<LogWeEventEventResponse> responses = new ArrayList<LogWeEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogWeEventEventResponse typedResponse = new LogWeEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.topicName = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.eventSeq = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.eventBlockNumer = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.eventContent = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogWeEventEventResponse> logWeEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogWeEventEventResponse>() {
            @Override
            public LogWeEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGWEEVENT_EVENT, log);
                LogWeEventEventResponse typedResponse = new LogWeEventEventResponse();
                typedResponse.log = log;
                typedResponse.topicName = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.eventSeq = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.eventBlockNumer = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.eventContent = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogWeEventEventResponse> logWeEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGWEEVENT_EVENT));
        return logWeEventEventFlowable(filter);
    }

    @Deprecated
    public static Topic load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Topic(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Topic load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Topic(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Topic load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Topic(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Topic load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Topic(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Topic> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Topic.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Topic> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Topic.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Topic> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Topic.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Topic> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Topic.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class LogWeEventEventResponse {
        public Log log;

        public byte[] topicName;

        public BigInteger eventSeq;

        public BigInteger eventBlockNumer;

        public String eventContent;
    }
}
