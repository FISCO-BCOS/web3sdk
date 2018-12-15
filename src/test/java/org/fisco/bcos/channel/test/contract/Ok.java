package org.fisco.bcos.channel.test.contract;

import io.reactivex.Flowable;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
public class Ok extends Contract {
    private static final String BINARY = "60606040525b6001600060005060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908302179055506402540be4006000600050600101600050819055506002600260005060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff0219169083021790555060006002600050600101600050819055505b6104168061009e6000396000f360606040526000357c01000000000000000000000000000000000000000000000000000000009004806366c99139146100475780636d4ce63c1461006457610042565b610002565b3461000257610062600480803590602001909190505061008c565b005b346100025761007660048050506103fe565b6040518082815260200191505060405180910390f35b80600060005060010160005054036000600050600101600050819055508060026000506001016000828282505401925050819055507fb797d73164cc7b1c119ca7507c18ac67eac964ca7eed3b0fbdd4e63caab2ca65816040518082815260200191505060405180910390a16004600050805480600101828181548183558181151161020c5760040281600402836000526020600020918201910161020b9190610131565b8082111561020757600060008201600050805460018160011615610100020316600290046000825580601f1061016757506101a4565b601f0160209004906000526020600020908101906101a39190610185565b8082111561019f5760008181506000905550600101610185565b5090565b5b506001820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690556002820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055600382016000506000905550600401610131565b5090565b5b5050509190906000526020600020906004020160005b608060405190810160405280604060405190810160405280600881526020017f32303137303431330000000000000000000000000000000000000000000000008152602001508152602001600060005060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168152602001600260005060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681526020018581526020015090919091506000820151816000016000509080519060200190828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061032a57805160ff191683800117855561035b565b8280016001018555821561035b579182015b8281111561035a57825182600050559160200191906001019061033c565b5b5090506103869190610368565b808211156103825760008181506000905550600101610368565b5090565b505060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff0219169083021790555060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff02191690830217905550606082015181600301600050555050505b50565b60006002600050600101600050549050610413565b9056\r\n";

    public static final String FUNC_TRANS = "trans";

    public static final String FUNC_GET = "get";

    public static final Event TRANSEVENT_EVENT = new Event("transEvent",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Ok(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ok(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Ok(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Ok(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> trans(BigInteger num) {
        final Function function = new Function(
                FUNC_TRANS,
                Arrays.<Type>asList(new Uint256(num)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> get() {
        final Function function = new Function(FUNC_GET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<TransEventEventResponse> getTransEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSEVENT_EVENT, transactionReceipt);
        ArrayList<TransEventEventResponse> responses = new ArrayList<TransEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransEventEventResponse typedResponse = new TransEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.num = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransEventEventResponse> transEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransEventEventResponse>() {
            @Override
            public TransEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSEVENT_EVENT, log);
                TransEventEventResponse typedResponse = new TransEventEventResponse();
                typedResponse.log = log;
                typedResponse.num = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransEventEventResponse> transEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSEVENT_EVENT));
        return transEventEventFlowable(filter);
    }

    @Deprecated
    public static Ok load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ok(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Ok load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ok(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Ok load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Ok(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Ok load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Ok(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Ok> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, credentials, contractGasProvider, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<Ok> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, transactionManager, contractGasProvider, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Ok> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Ok> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static class TransEventEventResponse {
        public Log log;

        public BigInteger num;
    }
}
