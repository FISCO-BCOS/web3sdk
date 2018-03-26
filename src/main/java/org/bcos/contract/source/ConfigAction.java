package org.bcos.contract.source;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
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
import java.util.Collections;
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
public final class ConfigAction extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6107f08061001e6000396000f300606060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063693ec85e1461005c578063e942b51614610139578063ff68fa6e146101d9575b600080fd5b341561006757600080fd5b6100b7600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610275565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156100fd5780820151818401526020810190506100e2565b50505050905090810190601f16801561012a5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b341561014457600080fd5b6101d7600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610401565b005b34156101e457600080fd5b6101fa60048080359060200190919050506105af565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561023a57808201518184015260208101905061021f565b50505050905090810190601f1680156102675780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61027d61066b565b60006001836040518082805190602001908083835b6020831015156102b75780518252602082019150602081019050602083039250610292565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000016001846040518082805190602001908083835b6020831015156103255780518252602082019150602081019050602083039250610300565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010154818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103f15780601f106103c6576101008083540402835291602001916103f1565b820191906000526020600020905b8154815290600101906020018083116103d457829003601f168201915b5050505050915091509150915091565b60006001836040518082805190602001908083835b60208310151561043b5780518252602082019150602081019050602083039250610416565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001015414156104b5576000805480600101828161048c919061067f565b9160005260206000209001600084909190915090805190602001906104b29291906106ab565b50505b806001836040518082805190602001908083835b6020831015156104ee57805182526020820191506020810190506020830392506104c9565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000190805190602001906105379291906106ab565b50436001836040518082805190602001908083835b602083101515610571578051825260208201915060208101905060208303925061054c565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600101819055505050565b6000818154811015156105be57fe5b90600052602060002090016000915090508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106635780601f1061063857610100808354040283529160200191610663565b820191906000526020600020905b81548152906001019060200180831161064657829003601f168201915b505050505081565b602060405190810160405280600081525090565b8154818355818115116106a6578183600052602060002091820191016106a5919061072b565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106106ec57805160ff191683800117855561071a565b8280016001018555821561071a579182015b828111156107195782518255916020019190600101906106fe565b5b5090506107279190610757565b5090565b61075491905b808211156107505760008181610747919061077c565b50600101610731565b5090565b90565b61077991905b8082111561077557600081600090555060010161075d565b5090565b90565b50805460018160011615610100020316600290046000825580601f106107a257506107c1565b601f0160209004906000526020600020908101906107c09190610757565b5b505600a165627a7a7230582015102030c0fc3053b816120b2f03d99a1dc4a4b6ca6d6d7f2573d3fedd02b8d10029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"key\",\"type\":\"string\"}],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"key\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"allItems\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"code\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"LogMessage\",\"type\":\"event\"}]";

    private ConfigAction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private ConfigAction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private ConfigAction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private ConfigAction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public Future<List<Type>> get(Utf8String key) {
        Function function = new Function("get", 
                Arrays.<Type>asList(key), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> set(Utf8String key, Utf8String value) {
        Function function = new Function("set", Arrays.<Type>asList(key, value), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void set(Utf8String key, Utf8String value, TransactionSucCallback callback) {
        Function function = new Function("set", Arrays.<Type>asList(key, value), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Utf8String> allItems(Uint256 param0) {
        Function function = new Function("allItems", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<ConfigAction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(ConfigAction.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<ConfigAction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(ConfigAction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static ConfigAction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConfigAction(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static ConfigAction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConfigAction(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static ConfigAction loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConfigAction(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static ConfigAction loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConfigAction(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static class LogMessageEventResponse {
        public Address addr;

        public Uint256 code;

        public Utf8String msg;
    }
}
