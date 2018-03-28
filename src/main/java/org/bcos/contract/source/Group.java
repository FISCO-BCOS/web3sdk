package org.bcos.contract.source;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.DynamicArray;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class Group extends Contract {
    private static final String BINARY = "60606040526000600160006101000a81548160ff02191690831515021790555060006001806101000a81548160ff021916908315150217905550341561004457600080fd5b610ba2806100536000396000f3006060604052600436106100a4576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063045894ab146100a95780632150c518146100d65780633a50f68b14610140578063644d8164146101e05780637dab61b61461026e5780638586b2f0146102935780639614c76914610327578063c878851d14610384578063e7d66cac146103a9578063ee6d84c514610473575b600080fd5b34156100b457600080fd5b6100bc6104a0565b604051808215151515815260200191505060405180910390f35b34156100e157600080fd5b6100e96104b7565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561012c578082015181840152602081019050610111565b505050509050019250505060405180910390f35b341561014b57600080fd5b610165600480803560001916906020019091905050610519565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101a557808201518184015260208101905061018a565b50505050905090810190601f1680156101d25780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156101eb57600080fd5b6101f3610622565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610233578082015181840152602081019050610218565b50505050905090810190601f1680156102605780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561027957600080fd5b610291600480803515159060200190919050506106ca565b005b341561029e57600080fd5b61030d600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506106e6565b604051808215151515815260200191505060405180910390f35b341561033257600080fd5b610382600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610830565b005b341561038f57600080fd5b6103a76004808035151590602001909190505061084a565b005b34156103b457600080fd5b610471600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080351515906020019091905050610867565b005b341561047e57600080fd5b610486610a42565b604051808215151515815260200191505060405180910390f35b6000600160009054906101000a900460ff16905090565b6104bf610a58565b600380548060200260200160405190810160405280929190818152602001828054801561050f57602002820191906000526020600020905b815460001916815260200190600101908083116104f7575b5050505050905090565b610521610a6c565b600080836000191660001916815260200190815260200160002060009054906101000a900460ff1615610609576004600083600019166000191681526020019081526020016000208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105fd5780601f106105d2576101008083540402835291602001916105fd565b820191906000526020600020905b8154815290600101906020018083116105e057829003601f168201915b5050505050905061061d565b602060405190810160405280600081525090505b919050565b61062a610a6c565b60028054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106c05780601f10610695576101008083540402835291602001916106c0565b820191906000526020600020905b8154815290600101906020018083116106a357829003601f168201915b5050505050905090565b806001806101000a81548160ff02191690831515021790555050565b60008060028484600060405160200152604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c0100000000000000000000000002815260140182805190602001908083835b60208310151561076c5780518252602082019150602081019050602083039250610747565b6001836020036101000a0380198251168184511680821785525050505050509050019250505060206040518083038160008661646e5a03f115156107af57600080fd5b50506040518051905090506001809054906101000a900460ff16156107fe57600080826000191660001916815260200190815260200160002060009054906101000a900460ff16159150610829565b600080826000191660001916815260200190815260200160002060009054906101000a900460ff1691505b5092915050565b8060029080519060200190610846929190610a80565b5050565b80600160006101000a81548160ff02191690831515021790555050565b600080600060028787600060405160200152604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c0100000000000000000000000002815260140182805190602001908083835b6020831015156108ef57805182526020820191506020810190506020830392506108ca565b6001836020036101000a0380198251168184511680821785525050505050509050019250505060206040518083038160008661646e5a03f1151561093257600080fd5b50506040518051905092508315610a075760009150600090505b60038054905081101561099957826000191660038281548110151561096d57fe5b90600052602060002090015460001916141561098c5760019150610999565b808060010191505061094c565b600015158215151415610a0657600380548060010182816109ba9190610b00565b916000526020600020900160008590919091509060001916905550846004600085600019166000191681526020019081526020016000209080519060200190610a04929190610a80565b505b5b83600080856000191660001916815260200190815260200160002060006101000a81548160ff02191690831515021790555050505050505050565b60006001809054906101000a900460ff16905090565b602060405190810160405280600081525090565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610ac157805160ff1916838001178555610aef565b82800160010185558215610aef579182015b82811115610aee578251825591602001919060010190610ad3565b5b509050610afc9190610b2c565b5090565b815481835581811511610b2757818360005260206000209182019101610b269190610b51565b5b505050565b610b4e91905b80821115610b4a576000816000905550600101610b32565b5090565b90565b610b7391905b80821115610b6f576000816000905550600101610b57565b5090565b905600a165627a7a723058205be74d70651e5dc32f4df52e553884b1aa31d47cb570f3a211f4d50f20aacc300029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"getCreate\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getKeys\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"key\",\"type\":\"bytes32\"}],\"name\":\"getFuncDescwithPermissionByKey\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getDesc\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"black\",\"type\":\"bool\"}],\"name\":\"setBlack\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"func\",\"type\":\"string\"}],\"name\":\"getPermission\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"desc\",\"type\":\"string\"}],\"name\":\"setDesc\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"create\",\"type\":\"bool\"}],\"name\":\"setCreate\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"func\",\"type\":\"string\"},{\"name\":\"funcDesc\",\"type\":\"string\"},{\"name\":\"permission\",\"type\":\"bool\"}],\"name\":\"setPermission\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getBlack\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";

    private Group(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private Group(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private Group(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private Group(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public Future<Bool> getCreate() {
        Function function = new Function("getCreate", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<DynamicArray<Bytes32>> getKeys() {
        Function function = new Function("getKeys", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getFuncDescwithPermissionByKey(Bytes32 key) {
        Function function = new Function("getFuncDescwithPermissionByKey", 
                Arrays.<Type>asList(key), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getDesc() {
        Function function = new Function("getDesc", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setBlack(Bool black) {
        Function function = new Function("setBlack", Arrays.<Type>asList(black), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setBlack(Bool black, TransactionSucCallback callback) {
        Function function = new Function("setBlack", Arrays.<Type>asList(black), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Bool> getPermission(Address to, Utf8String func) {
        Function function = new Function("getPermission", 
                Arrays.<Type>asList(to, func), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setDesc(Utf8String desc) {
        Function function = new Function("setDesc", Arrays.<Type>asList(desc), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setDesc(Utf8String desc, TransactionSucCallback callback) {
        Function function = new Function("setDesc", Arrays.<Type>asList(desc), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> setCreate(Bool create) {
        Function function = new Function("setCreate", Arrays.<Type>asList(create), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setCreate(Bool create, TransactionSucCallback callback) {
        Function function = new Function("setCreate", Arrays.<Type>asList(create), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> setPermission(Address to, Utf8String func, Utf8String funcDesc, Bool permission) {
        Function function = new Function("setPermission", Arrays.<Type>asList(to, func, funcDesc, permission), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setPermission(Address to, Utf8String func, Utf8String funcDesc, Bool permission, TransactionSucCallback callback) {
        Function function = new Function("setPermission", Arrays.<Type>asList(to, func, funcDesc, permission), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Bool> getBlack() {
        Function function = new Function("getBlack", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<Group> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Group.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Group> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Group.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Group load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Group(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static Group load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Group(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static Group loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Group(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static Group loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Group(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }
}
