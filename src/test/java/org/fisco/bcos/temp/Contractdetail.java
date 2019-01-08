package org.fisco.bcos.temp;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
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
public class Contractdetail extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50611880806100206000396000f300608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630ad1c2fa1461007d5780632637a4771461035c5780637156257d146103b35780639399869d146105ae5780639b534f18146105d9578063bde61cee146108a2575b600080fd5b34801561008957600080fd5b506100be600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506108fd565b6040518080602001806020018060200180602001806020018060200188815260200187810387528e818151815260200191508051906020019080838360005b838110156101185780820151818401526020810190506100fd565b50505050905090810190601f1680156101455780820380516001836020036101000a031916815260200191505b5087810386528d818151815260200191508051906020019080838360005b8381101561017e578082015181840152602081019050610163565b50505050905090810190601f1680156101ab5780820380516001836020036101000a031916815260200191505b5087810385528c818151815260200191508051906020019080838360005b838110156101e45780820151818401526020810190506101c9565b50505050905090810190601f1680156102115780820380516001836020036101000a031916815260200191505b5087810384528b818151815260200191508051906020019080838360005b8381101561024a57808201518184015260208101905061022f565b50505050905090810190601f1680156102775780820380516001836020036101000a031916815260200191505b5087810383528a818151815260200191508051906020019080838360005b838110156102b0578082015181840152602081019050610295565b50505050905090810190601f1680156102dd5780820380516001836020036101000a031916815260200191505b50878103825289818151815260200191508051906020019080838360005b838110156103165780820151818401526020810190506102fb565b50505050905090810190601f1680156103435780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b34801561036857600080fd5b5061039d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610ea5565b6040518082815260200191505060405180910390f35b3480156103bf57600080fd5b50610598600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050611050565b6040518082815260200191505060405180910390f35b3480156105ba57600080fd5b506105c3611647565b6040518082815260200191505060405180910390f35b3480156105e557600080fd5b5061060460048036038101908080359060200190929190505050611654565b6040518080602001806020018060200180602001806020018060200188815260200187810387528e818151815260200191508051906020019080838360005b8381101561065e578082015181840152602081019050610643565b50505050905090810190601f16801561068b5780820380516001836020036101000a031916815260200191505b5087810386528d818151815260200191508051906020019080838360005b838110156106c45780820151818401526020810190506106a9565b50505050905090810190601f1680156106f15780820380516001836020036101000a031916815260200191505b5087810385528c818151815260200191508051906020019080838360005b8381101561072a57808201518184015260208101905061070f565b50505050905090810190601f1680156107575780820380516001836020036101000a031916815260200191505b5087810384528b818151815260200191508051906020019080838360005b83811015610790578082015181840152602081019050610775565b50505050905090810190601f1680156107bd5780820380516001836020036101000a031916815260200191505b5087810383528a818151815260200191508051906020019080838360005b838110156107f65780820151818401526020810190506107db565b50505050905090810190601f1680156108235780820380516001836020036101000a031916815260200191505b50878103825289818151815260200191508051906020019080838360005b8381101561085c578082015181840152602081019050610841565b50505050905090810190601f1680156108895780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b3480156108ae57600080fd5b506108e3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506116ba565b604051808215151515815260200191505060405180910390f35b6060806060806060806000610911886116ba565b151561091c57600080fd5b6000808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000016000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001016000808b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206002016000808c73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206005016000808d73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206004016000808e73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000808f73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060060154868054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b795780601f10610b4e57610100808354040283529160200191610b79565b820191906000526020600020905b815481529060010190602001808311610b5c57829003601f168201915b50505050509650858054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c155780601f10610bea57610100808354040283529160200191610c15565b820191906000526020600020905b815481529060010190602001808311610bf857829003601f168201915b50505050509550848054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610cb15780601f10610c8657610100808354040283529160200191610cb1565b820191906000526020600020905b815481529060010190602001808311610c9457829003601f168201915b50505050509450838054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d4d5780601f10610d2257610100808354040283529160200191610d4d565b820191906000526020600020905b815481529060010190602001808311610d3057829003601f168201915b50505050509350828054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610de95780601f10610dbe57610100808354040283529160200191610de9565b820191906000526020600020905b815481529060010190602001808311610dcc57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e855780601f10610e5a57610100808354040283529160200191610e85565b820191906000526020600020905b815481529060010190602001808311610e6857829003601f168201915b505050505091509650965096509650965096509650919395979092949650565b6000806000610eb3846116ba565b1515610ebe57600080fd5b6000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600601549150600180808054905003815481101515610f1757fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905080600183815481101515610f5457fe5b9060005260206000200160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550816000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600601819055506001805480919060019003610ff79190611783565b508373ffffffffffffffffffffffffffffffffffffffff167f880d2658174af3f5b73b10333a61b5eb181cd60f5445a80cafe32146f8796630836040518082815260200191505060405180910390a28192505050919050565b600061105b886116ba565b1561106557600080fd5b866000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000190805190602001906110ba9291906117af565b50856000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010190805190602001906111109291906117af565b50846000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020190805190602001906111669291906117af565b50836000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050190805190602001906111bc9291906117af565b50826000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060040190805190602001906112129291906117af565b50816000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060030190805190602001906112689291906117af565b506001808990806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550036000808a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600601819055508773ffffffffffffffffffffffffffffffffffffffff167f448124917995f2902032c01bd74f139a5804f0309ffa3cc13797d67dd02fb1ca6000808b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600601548989898989896040518088815260200180602001806020018060200180602001806020018060200187810387528d818151815260200191508051906020019080838360005b838110156113ef5780820151818401526020810190506113d4565b50505050905090810190601f16801561141c5780820380516001836020036101000a031916815260200191505b5087810386528c818151815260200191508051906020019080838360005b8381101561145557808201518184015260208101905061143a565b50505050905090810190601f1680156114825780820380516001836020036101000a031916815260200191505b5087810385528b818151815260200191508051906020019080838360005b838110156114bb5780820151818401526020810190506114a0565b50505050905090810190601f1680156114e85780820380516001836020036101000a031916815260200191505b5087810384528a818151815260200191508051906020019080838360005b83811015611521578082015181840152602081019050611506565b50505050905090810190601f16801561154e5780820380516001836020036101000a031916815260200191505b50878103835289818151815260200191508051906020019080838360005b8381101561158757808201518184015260208101905061156c565b50505050905090810190601f1680156115b45780820380516001836020036101000a031916815260200191505b50878103825288818151815260200191508051906020019080838360005b838110156115ed5780820151818401526020810190506115d2565b50505050905090810190601f16801561161a5780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390a260018080549050039050979650505050505050565b6000600180549050905090565b60608060608060608060006116a160018981548110151561167157fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166108fd565b9650965096509650965096509650919395979092949650565b60008060018054905014156116d2576000905061177e565b8173ffffffffffffffffffffffffffffffffffffffff1660016000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206006015481548110151561173957fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161490505b919050565b8154818355818111156117aa578183600052602060002091820191016117a9919061182f565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106117f057805160ff191683800117855561181e565b8280016001018555821561181e579182015b8281111561181d578251825591602001919060010190611802565b5b50905061182b919061182f565b5090565b61185191905b8082111561184d576000816000905550600101611835565b5090565b905600a165627a7a7230582054ee67d46257d5f39c969dd5417d71b0bff26a1b031b1b0e04da08651d043f1e0029";

    public static final String FUNC_GETCONTRACT = "getContract";

    public static final String FUNC_DELETECONTRACT = "deleteContract";

    public static final String FUNC_INSERTCONTRACT = "insertContract";

    public static final String FUNC_GETCONTRACTCOUNT = "getContractCount";

    public static final String FUNC_GETCONTRACTATINDEX = "getContractAtIndex";

    public static final String FUNC_ISCONTRACTEXIST = "isContractExist";

    public static final Event LOGNEWCONTRACT_EVENT = new Event("LogNewContract", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event LOGDELETECONTRACT_EVENT = new Event("LogDeleteContract", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Contractdetail(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Contractdetail(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Contractdetail(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Contractdetail(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple7<String, String, String, String, String, String, BigInteger>> getContract(String contractAddress) {
        final Function function = new Function(FUNC_GETCONTRACT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(contractAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<String, String, String, String, String, String, BigInteger>>(
                new Callable<Tuple7<String, String, String, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple7<String, String, String, String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, String, String, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> deleteContract(String contractAddress) {
        final Function function = new Function(
                FUNC_DELETECONTRACT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> insertContract(String contractAddress, String contractName, String contractAbi, String contractBin, String networkId, String deployTime, String contractSource) {
        final Function function = new Function(
                FUNC_INSERTCONTRACT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(contractAddress), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(contractName), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(contractAbi), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(contractBin), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(networkId), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(deployTime), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(contractSource)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getContractCount() {
        final Function function = new Function(FUNC_GETCONTRACTCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> getContractAtIndex(BigInteger index) {
        final Function function = new Function(
                FUNC_GETCONTRACTATINDEX, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isContractExist(String contractAddress) {
        final Function function = new Function(FUNC_ISCONTRACTEXIST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(contractAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public List<LogNewContractEventResponse> getLogNewContractEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNEWCONTRACT_EVENT, transactionReceipt);
        ArrayList<LogNewContractEventResponse> responses = new ArrayList<LogNewContractEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogNewContractEventResponse typedResponse = new LogNewContractEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.contractName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.contractAbi = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.contractBin = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.networkId = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.deployTime = (String) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.contractSource = (String) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogNewContractEventResponse> logNewContractEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogNewContractEventResponse>() {
            @Override
            public LogNewContractEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGNEWCONTRACT_EVENT, log);
                LogNewContractEventResponse typedResponse = new LogNewContractEventResponse();
                typedResponse.log = log;
                typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.contractName = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.contractAbi = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.contractBin = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.networkId = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.deployTime = (String) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.contractSource = (String) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogNewContractEventResponse> logNewContractEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGNEWCONTRACT_EVENT));
        return logNewContractEventFlowable(filter);
    }

    public List<LogDeleteContractEventResponse> getLogDeleteContractEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDELETECONTRACT_EVENT, transactionReceipt);
        ArrayList<LogDeleteContractEventResponse> responses = new ArrayList<LogDeleteContractEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogDeleteContractEventResponse typedResponse = new LogDeleteContractEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDeleteContractEventResponse> logDeleteContractEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogDeleteContractEventResponse>() {
            @Override
            public LogDeleteContractEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDELETECONTRACT_EVENT, log);
                LogDeleteContractEventResponse typedResponse = new LogDeleteContractEventResponse();
                typedResponse.log = log;
                typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDeleteContractEventResponse> logDeleteContractEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDELETECONTRACT_EVENT));
        return logDeleteContractEventFlowable(filter);
    }

    @Deprecated
    public static Contractdetail load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contractdetail(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Contractdetail load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contractdetail(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Contractdetail load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Contractdetail(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Contractdetail load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Contractdetail(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Contractdetail> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Contractdetail.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Contractdetail> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contractdetail.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Contractdetail> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Contractdetail.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Contractdetail> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contractdetail.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class LogNewContractEventResponse {
        public Log log;

        public String contractAddress;

        public BigInteger index;

        public String contractName;

        public String contractAbi;

        public String contractBin;

        public String networkId;

        public String deployTime;

        public String contractSource;
    }

    public static class LogDeleteContractEventResponse {
        public Log log;

        public String contractAddress;

        public BigInteger index;
    }
}
