package org.bcos.contract.source;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.DynamicArray;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.EncryptType;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class UserCheck extends Contract {
    private static String BINARY = "6060604052341561000f57600080fd5b6104f18061001e6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063c23697a814610053578063cdfca7f2146100a4578063ed815f621461015357600080fd5b341561005e57600080fd5b61008a600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506101bd565b604051808215151515815260200191505060405180910390f35b34156100af57600080fd5b6100fc60048080359060200190820180359060200190808060200260200160405190810160405280939291908181526020018383602002808284378201915050505050509190505061029f565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561013f578082015181840152602081019050610124565b505050509050019250505060405180910390f35b341561015e57600080fd5b6101666103cc565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156101a957808201518184015260208101905061018e565b505050509050019250505060405180910390f35b6000808273ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415156101fe5760009150610299565b600090505b6000805490508110156102945760008181548110151561021f57fe5b906000526020600020900160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614156102875760019150610299565b8080600101915050610203565b600091505b50919050565b6102a7610460565b60008090505b825181101561033d57600080548060010182816102ca9190610474565b9160005260206000209001600085848151811015156102e557fe5b90602001906020020151909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505080806001019150506102ad565b60008054806020026020016040519081016040528092919081815260200182805480156103bf57602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311610375575b5050505050915050919050565b6103d4610460565b600080548060200260200160405190810160405280929190818152602001828054801561045657602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001906001019080831161040c575b5050505050905090565b602060405190810160405280600081525090565b81548183558181151161049b5781836000526020600020918201910161049a91906104a0565b5b505050565b6104c291905b808211156104be5760008160009055506001016104a6565b5090565b905600a165627a7a72305820b17fda6e22b7bbd8d11f3079b26de36de20aed839b5a6a7c11a5e5de364dd0da0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"user\",\"type\":\"address\"}],\"name\":\"check\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"userList\",\"type\":\"address[]\"}],\"name\":\"addUser\",\"outputs\":[{\"name\":\"\",\"type\":\"address[]\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"listUser\",\"outputs\":[{\"name\":\"\",\"type\":\"address[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";

    private static String GUOMI_BINARY = "6060604052341561000c57fe5b5b5b5b61050a8061001e6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806355e8b20114610051578063a0e3449e1461010b578063f024051d14610180575bfe5b341561005957fe5b6100a66004808035906020019082018035906020019080806020026020016040519081016040528093929190818152602001838360200280828437820191505050505050919050506101ce565b60405180806020018281038252838181518152602001915080519060200190602002808383600083146100f8575b8051825260208311156100f8576020820191506020810190506020830392506100d4565b5050509050019250505060405180910390f35b341561011357fe5b61011b6102ff565b604051808060200182810382528381815181526020019150805190602001906020028083836000831461016d575b80518252602083111561016d57602082019150602081019050602083039250610149565b5050509050019250505060405180910390f35b341561018857fe5b6101b4600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610394565b604051808215151515815260200191505060405180910390f35b6101d6610479565b6000600090505b825181101561026f57600080548060010182816101fa919061048d565b916000526020600020900160005b858481518110151561021657fe5b90602001906020020151909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505b80806001019150506101dd565b60008054806020026020016040519081016040528092919081815260200182805480156102f157602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190600101908083116102a7575b505050505091505b50919050565b610307610479565b600080548060200260200160405190810160405280929190818152602001828054801561038957602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001906001019080831161033f575b505050505090505b90565b600060008273ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415156103d65760009150610473565b600090505b60008054905081101561046e576000818154811015156103f757fe5b906000526020600020900160005b9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614156104605760019150610473565b5b80806001019150506103db565b600091505b50919050565b602060405190810160405280600081525090565b8154818355818115116104b4578183600052602060002091820191016104b391906104b9565b5b505050565b6104db91905b808211156104d75760008160009055506001016104bf565b5090565b905600a165627a7a7230582017f8ee58a00d6c9c1e19976755c7d9dcac0ac5c54352c4fa374f12d2ff233ff10029";

    private UserCheck(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private UserCheck(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private UserCheck(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private UserCheck(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    public Future<Bool> check(Address user) {
        Function function = new Function("check", 
                Arrays.<Type>asList(user), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> addUser(DynamicArray<Address> userList) {
        Function function = new Function("addUser", Arrays.<Type>asList(userList), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void addUser(DynamicArray<Address> userList, TransactionSucCallback callback) {
        Function function = new Function("addUser", Arrays.<Type>asList(userList), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<DynamicArray<Address>> listUser() {
        Function function = new Function("listUser", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<UserCheck> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(UserCheck.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<UserCheck> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(UserCheck.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static UserCheck load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserCheck(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static UserCheck load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserCheck(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static UserCheck loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserCheck(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static UserCheck loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UserCheck(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static void setBinary(String binary) {
        BINARY = binary;
    }

    public static String getGuomiBinary() {
        return GUOMI_BINARY;
    }
}
