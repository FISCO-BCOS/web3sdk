package org.bcos.contract.source;

import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class Base extends Contract {
    private static final String BINARY = "60606040523415600e57600080fd5b603580601b6000396000f3006060604052600080fd00a165627a7a723058207b5da91ae5fa1ecd0b9693f44172f9c1d93dff320a2b61be80e6c40e879dcb150029";

    public static final String ABI = "[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"code\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"LogMessage\",\"type\":\"event\"}]";

    private Base(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private Base(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private Base(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private Base(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static List<LogMessageEventResponse> getLogMessageEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("LogMessage", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<LogMessageEventResponse> responses = new ArrayList<LogMessageEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            LogMessageEventResponse typedResponse = new LogMessageEventResponse();
            typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.code = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.msg = (Utf8String) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogMessageEventResponse> logMessageEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("LogMessage", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogMessageEventResponse>() {
            @Override
            public LogMessageEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                LogMessageEventResponse typedResponse = new LogMessageEventResponse();
                typedResponse.addr = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.code = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse.msg = (Utf8String) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public static Future<Base> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Base.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Base> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Base.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Base load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Base(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static Base load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Base(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static Base loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Base(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static Base loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Base(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class LogMessageEventResponse {
        public Address addr;

        public Uint256 code;

        public Utf8String msg;
    }
}
