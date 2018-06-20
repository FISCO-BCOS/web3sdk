package org.bcos.channel.test.cns.source;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.EncryptType;
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
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class CNSTest extends Contract {
    private static String BINARY = "6060604052341561000c57fe5b5b604060405190810160405280600781526020017f762d302e302e31000000000000000000000000000000000000000000000000008152505b806000908051906020019061005b9291906100bc565b505b50604060405190810160405280600b81526020017f48692c57656c636f6d6521000000000000000000000000000000000000000000815250600190805190602001906100aa9291906100bc565b5063423a35c76002819055505b610161565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100fd57805160ff191683800117855561012b565b8280016001018555821561012b579182015b8281111561012a57825182559160200191906001019061010f565b5b509050610138919061013c565b5090565b61015e91905b8082111561015a576000816000905550600101610142565b5090565b90565b6108aa806101706000396000f3006060604052361561008c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630d8e6e2c1461008e578063388ea3ef1461012757806362738998146101475780636d4ce63c1461016d578063788bc78c1461020d5780638a42ebe914610267578063b5fdeb23146102ca578063c4784fd414610363575bfe5b341561009657fe5b61009e6103bd565b60405180806020018281038252838181518152602001915080519060200190808383600083146100ed575b8051825260208311156100ed576020820191506020810190506020830392506100c9565b505050905090810190601f1680156101195780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561012f57fe5b6101456004808035906020019091905050610466565b005b341561014f57fe5b6101576104a8565b6040518082815260200191505060405180910390f35b341561017557fe5b61017d6104b3565b60405180806020018381526020018281038252848181518152602001915080519060200190808383600083146101d2575b8051825260208311156101d2576020820191506020810190506020830392506101ae565b505050905090810190601f1680156101fe5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b341561021557fe5b610265600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610567565b005b341561026f57fe5b6102c8600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091908035906020019091905050610582565b005b34156102d257fe5b6102da610657565b6040518080602001828103825283818151815260200191508051906020019080838360008314610329575b80518252602083111561032957602082019150602081019050602083039250610305565b505050905090810190601f1680156103555780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561036b57fe5b6103bb600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610700565b005b6103c56107c5565b60008054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561045b5780601f106104305761010080835404028352916020019161045b565b820191906000526020600020905b81548152906001019060200180831161043e57829003601f168201915b505050505090505b90565b7fc00a88effcf368426430fe97503f0ef5a812794222c022dd11c8fa14a2156faf816040518082815260200191505060405180910390a1806002819055505b50565b600060025490505b90565b6104bb6107c5565b60006001600254818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105575780601f1061052c57610100808354040283529160200191610557565b820191906000526020600020905b81548152906001019060200180831161053a57829003601f168201915b50505050509150915091505b9091565b806000908051906020019061057d9291906107d9565b505b50565b7f432af2d421e625915dad0f2771ad9cda633a5403d2ca4cfadd6477bbf7742d4c828260405180806020018381526020018281038252848181518152602001915080519060200190808383600083146105fa575b8051825260208311156105fa576020820191506020810190506020830392506105d6565b505050905090810190601f1680156106265780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1816001908051906020019061064a9291906107d9565b50806002819055505b5050565b61065f6107c5565b60018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106f55780601f106106ca576101008083540402835291602001916106f5565b820191906000526020600020905b8154815290600101906020018083116106d857829003601f168201915b505050505090505b90565b7ffa8d9be79768d448342b5dcd866c2161208faa3a844ca75ee77603680e3aa12d816040518080602001828103825283818151815260200191508051906020019080838360008314610771575b8051825260208311156107715760208201915060208101905060208303925061074d565b505050905090810190601f16801561079d5780820380516001836020036101000a031916815260200191505b509250505060405180910390a180600190805190602001906107c09291906107d9565b505b50565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061081a57805160ff1916838001178555610848565b82800160010185558215610848579182015b8281111561084757825182559160200191906001019061082c565b5b5090506108559190610859565b5090565b61087b91905b8082111561087757600081600090555060010161085f565b5090565b905600a165627a7a723058204e8a34f4bc0782ea030cf62d76abd0e2e2c294884bb82373728b3b66020acc970029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"getVersion\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"ui\",\"type\":\"uint256\"}],\"name\":\"setU\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getInt\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"version_para\",\"type\":\"string\"}],\"name\":\"setVersion\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"m\",\"type\":\"string\"},{\"name\":\"ui\",\"type\":\"uint256\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getMsg\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"m\",\"type\":\"string\"}],\"name\":\"setMsg\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"}],\"name\":\"SetMsg\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"u\",\"type\":\"uint256\"}],\"name\":\"SetU\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"u\",\"type\":\"uint256\"}],\"name\":\"Set\",\"type\":\"event\"}]";

    private static String GUOMI_BINARY = "6060604052341561000c57fe5b5b604060405190810160405280600781526020017f762d302e302e31000000000000000000000000000000000000000000000000008152505b806000908051906020019061005b9291906100bc565b505b50604060405190810160405280600b81526020017f48692c57656c636f6d6521000000000000000000000000000000000000000000815250600190805190602001906100aa9291906100bc565b5063423a35c76002819055505b610161565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100fd57805160ff191683800117855561012b565b8280016001018555821561012b579182015b8281111561012a57825182559160200191906001019061010f565b5b509050610138919061013c565b5090565b61015e91905b8082111561015a576000816000905550600101610142565b5090565b90565b6108aa806101706000396000f3006060604052361561008c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630da4f31c1461008e578063299f7f9d146100e857806395ce2ccc146101885780639e43bc49146101e2578063ca46547014610208578063d165031b14610228578063ecb905ca146102c1578063f2f4ee6d1461035a575bfe5b341561009657fe5b6100e6600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506103bd565b005b34156100f057fe5b6100f8610482565b604051808060200183815260200182810382528481815181526020019150805190602001908083836000831461014d575b80518252602083111561014d57602082019150602081019050602083039250610129565b505050905090810190601f1680156101795780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b341561019057fe5b6101e0600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610536565b005b34156101ea57fe5b6101f2610551565b6040518082815260200191505060405180910390f35b341561021057fe5b610226600480803590602001909190505061055c565b005b341561023057fe5b61023861059e565b6040518080602001828103825283818151815260200191508051906020019080838360008314610287575b80518252602083111561028757602082019150602081019050602083039250610263565b505050905090810190601f1680156102b35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156102c957fe5b6102d1610647565b6040518080602001828103825283818151815260200191508051906020019080838360008314610320575b805182526020831115610320576020820191506020810190506020830392506102fc565b505050905090810190601f16801561034c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561036257fe5b6103bb600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080359060200190919050506106f0565b005b7f8079293bffde3acc1ab782b8b6a4088823fb1b69ac3013dc45074ca062342c0c81604051808060200182810382528381815181526020019150805190602001908083836000831461042e575b80518252602083111561042e5760208201915060208101905060208303925061040a565b505050905090810190601f16801561045a5780820380516001836020036101000a031916815260200191505b509250505060405180910390a1806001908051906020019061047d9291906107c5565b505b50565b61048a610845565b60006001600254818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105265780601f106104fb57610100808354040283529160200191610526565b820191906000526020600020905b81548152906001019060200180831161050957829003601f168201915b50505050509150915091505b9091565b806000908051906020019061054c9291906107c5565b505b50565b600060025490505b90565b7f52a4a0b113f0a51c957ac6e7d8bc1db158c80da6d1f6f9cec3f282bfd261a6b4816040518082815260200191505060405180910390a1806002819055505b50565b6105a6610845565b60018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561063c5780601f106106115761010080835404028352916020019161063c565b820191906000526020600020905b81548152906001019060200180831161061f57829003601f168201915b505050505090505b90565b61064f610845565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106e55780601f106106ba576101008083540402835291602001916106e5565b820191906000526020600020905b8154815290600101906020018083116106c857829003601f168201915b505050505090505b90565b7f0d8a6e81c93fa9cf89caf31db80919cffeb12762265a307546bbf081a0067b6482826040518080602001838152602001828103825284818151815260200191508051906020019080838360008314610768575b80518252602083111561076857602082019150602081019050602083039250610744565b505050905090810190601f1680156107945780820380516001836020036101000a031916815260200191505b50935050505060405180910390a181600190805190602001906107b89291906107c5565b50806002819055505b5050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061080657805160ff1916838001178555610834565b82800160010185558215610834579182015b82811115610833578251825591602001919060010190610818565b5b5090506108419190610859565b5090565b602060405190810160405280600081525090565b61087b91905b8082111561087757600081600090555060010161085f565b5090565b905600a165627a7a7230582016010342e8aa12c44adfcd3fb71d6be0944eacffd6d886677cdb5f6e545392640029";

    private CNSTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private CNSTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private CNSTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private CNSTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    public static List<SetMsgEventResponse> getSetMsgEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SetMsg", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<SetMsgEventResponse> responses = new ArrayList<SetMsgEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            SetMsgEventResponse typedResponse = new SetMsgEventResponse();
            typedResponse.msg = (Utf8String) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SetMsgEventResponse> setMsgEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SetMsg", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SetMsgEventResponse>() {
            @Override
            public SetMsgEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SetMsgEventResponse typedResponse = new SetMsgEventResponse();
                typedResponse.msg = (Utf8String) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public static List<SetUEventResponse> getSetUEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SetU", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<SetUEventResponse> responses = new ArrayList<SetUEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            SetUEventResponse typedResponse = new SetUEventResponse();
            typedResponse.u = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SetUEventResponse> setUEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SetU", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SetUEventResponse>() {
            @Override
            public SetUEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SetUEventResponse typedResponse = new SetUEventResponse();
                typedResponse.u = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public static List<SetEventResponse> getSetEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Set", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<SetEventResponse> responses = new ArrayList<SetEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            SetEventResponse typedResponse = new SetEventResponse();
            typedResponse.msg = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse.u = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SetEventResponse> setEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Set", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SetEventResponse>() {
            @Override
            public SetEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SetEventResponse typedResponse = new SetEventResponse();
                typedResponse.msg = (Utf8String) eventValues.getNonIndexedValues().get(0);
                typedResponse.u = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Future<Utf8String> getVersion() {
        Function function = new Function("getVersion", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setU(Uint256 ui) {
        Function function = new Function("setU", Arrays.<Type>asList(ui), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setU(Uint256 ui, TransactionSucCallback callback) {
        Function function = new Function("setU", Arrays.<Type>asList(ui), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Uint256> getInt() {
        Function function = new Function("getInt", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<List<Type>> get() {
        Function function = new Function("get", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setVersion(Utf8String version_para) {
        Function function = new Function("setVersion", Arrays.<Type>asList(version_para), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setVersion(Utf8String version_para, TransactionSucCallback callback) {
        Function function = new Function("setVersion", Arrays.<Type>asList(version_para), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> set(Utf8String m, Uint256 ui) {
        Function function = new Function("set", Arrays.<Type>asList(m, ui), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void set(Utf8String m, Uint256 ui, TransactionSucCallback callback) {
        Function function = new Function("set", Arrays.<Type>asList(m, ui), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Utf8String> getMsg() {
        Function function = new Function("getMsg", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setMsg(Utf8String m) {
        Function function = new Function("setMsg", Arrays.<Type>asList(m), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setMsg(Utf8String m, TransactionSucCallback callback) {
        Function function = new Function("setMsg", Arrays.<Type>asList(m), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public static Future<CNSTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(CNSTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<CNSTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(CNSTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static CNSTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CNSTest(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static CNSTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CNSTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static CNSTest loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CNSTest(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static CNSTest loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CNSTest(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static void setBinary(String binary) {
        BINARY = binary;
    }

    public static String getGuomiBinary() {
        return GUOMI_BINARY;
    }

    public static class SetMsgEventResponse {
        public Utf8String msg;
    }

    public static class SetUEventResponse {
        public Uint256 u;
    }

    public static class SetEventResponse {
        public Utf8String msg;

        public Uint256 u;
    }
}
