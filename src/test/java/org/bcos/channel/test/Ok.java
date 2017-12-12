package org.bcos.channel.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.StaticArray;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.bcos.web3j.abi.datatypes.generated.Int256;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.2.1.
 */
public final class Ok extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b6001600060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506402540be4006000600101819055506002600260000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060006002600101819055505b5b61055a806100c26000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806366c99139146100465780636d4ce63c14610066575bfe5b341561004e57fe5b610064600480803590602001909190505061008c565b005b341561006e57fe5b6100766104ca565b6040518082815260200191505060405180910390f35b6100946104d8565b61009c610501565b8260006001015403600060010181905550826002600101600082825401925050819055506001826000600c811015156100d157fe5b602002018181525050600b826001600c811015156100eb57fe5b602002018181525050600c826002600c8110151561010557fe5b602002018181525050600d826003600c8110151561011f57fe5b602002018181525050600e826004600c8110151561013957fe5b602002018181525050600f826005600c8110151561015357fe5b6020020181815250506010826006600c8110151561016d57fe5b6020020181815250506011826007600c8110151561018757fe5b6020020181815250506012826008600c811015156101a157fe5b6020020181815250506013826009600c811015156101bb57fe5b602002018181525050600a82600a600c811015156101d557fe5b602002018181525050600b82600b600c811015156101ef57fe5b6020020181815250507f617364000000000000000000000000000000000000000000000000000000000081600060088110151561022857fe5b602002019060001916908160001916815250507f626262000000000000000000000000000000000000000000000000000000000081600160088110151561026b57fe5b602002019060001916908160001916815250507f61736400000000000000000000000000000000000000000000000000000000008160026008811015156102ae57fe5b602002019060001916908160001916815250507f62626200000000000000000000000000000000000000000000000000000000008160036008811015156102f157fe5b602002019060001916908160001916815250507f617364000000000000000000000000000000000000000000000000000000000081600460088110151561033457fe5b602002019060001916908160001916815250507f626262000000000000000000000000000000000000000000000000000000000081600560088110151561037757fe5b602002019060001916908160001916815250507f61736400000000000000000000000000000000000000000000000000000000008160066008811015156103ba57fe5b602002019060001916908160001916815250507f6262e4b8ade696876200000000000000000000000000000000000000000000008160076008811015156103fd57fe5b602002019060001916908160001916815250507f3aa02e71b322fa7f5b57a4ffa4b1d2dea8f2b3eab44621dd716d8802fbf3312a8183600a604051808460086020028083836000831461046f575b80518252602083111561046f5760208201915060208101905060208303925061044b565b50505090500183600c602002808383600083146104ab575b8051825260208311156104ab57602082019150602081019050602083039250610487565b505050905001828152602001935050505060405180910390a15b505050565b600060026001015490505b90565b61018060405190810160405280600c905b60008152602001906001900390816104e95790505090565b610100604051908101604052806008905b60006000191681526020019060019003908161051257905050905600a165627a7a723058208240b9af43e88a8fd4e9e51b35e13b9a8fd64f9de2202c6582c2510521160d340029";

    private Ok(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Ok(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<EventOrderLogEventResponse> getEventOrderLogEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("eventOrderLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray<Bytes32>>() {}, new TypeReference<StaticArray<Int256>>() {}, new TypeReference<Int256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<EventOrderLogEventResponse> responses = new ArrayList<EventOrderLogEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            EventOrderLogEventResponse typedResponse = new EventOrderLogEventResponse();
            typedResponse.msg = (StaticArray<Bytes32>) eventValues.getNonIndexedValues().get(0);
            typedResponse.msg2 = (StaticArray<Int256>) eventValues.getNonIndexedValues().get(1);
            typedResponse.hello = (Int256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<EventOrderLogEventResponse> eventOrderLogEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("eventOrderLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray<Bytes32>>() {}, new TypeReference<StaticArray<Int256>>() {}, new TypeReference<Int256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, EventOrderLogEventResponse>() {
            @Override
            public EventOrderLogEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                EventOrderLogEventResponse typedResponse = new EventOrderLogEventResponse();
                typedResponse.msg = (StaticArray<Bytes32>) eventValues.getNonIndexedValues().get(0);
                typedResponse.msg2 = (StaticArray<Int256>) eventValues.getNonIndexedValues().get(1);
                typedResponse.hello = (Int256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Future<TransactionReceipt> trans(Uint256 num) {
        Function function = new Function("trans", Arrays.<Type>asList(num), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> get() {
        Function function = new Function("get", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<Ok> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Ok.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Ok> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Ok.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Ok load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ok(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Ok load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ok(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class EventOrderLogEventResponse {
        public StaticArray<Bytes32> msg;

        public StaticArray<Int256> msg2;

        public Int256 hello;
    }
}
