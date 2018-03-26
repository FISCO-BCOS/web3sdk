package org.bcos.contract.source;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class SystemProxy extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6107958061001e6000396000f300606060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631b5f03a614610067578063d1c4c20614610116578063d5aa1a261461019d578063e3e1dcb3146101c6575b600080fd5b341561007257600080fd5b6100c2600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610262565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183151515158152602001828152602001935050505060405180910390f35b341561012157600080fd5b61019b600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803515159060200190919050506103ed565b005b34156101a857600080fd5b6101b0610541565b6040518082815260200191505060405180910390f35b34156101d157600080fd5b6101e7600480803590602001909190505061054e565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561022757808201518184015260208101905061020c565b50505050905090810190601f1680156102545780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b600080600080846040518082805190602001908083835b60208310151561029e5780518252602082019150602081019050602083039250610279565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff166000856040518082805190602001908083835b60208310151561032d5780518252602082019150602081019050602083039250610308565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000160149054906101000a900460ff166000866040518082805190602001908083835b6020831015156103a95780518252602082019150602081019050602083039250610384565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600101549250925092509193909250565b6060604051908101604052808373ffffffffffffffffffffffffffffffffffffffff1681526020018215158152602001438152506000846040518082805190602001908083835b6020831015156104595780518252602082019150602081019050602083039250610434565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160000160146101000a81548160ff02191690831515021790555060408201518160010155905050600180548060010182816105149190610610565b91600052602060002090016000859091909150908051906020019061053a92919061063c565b5050505050565b6000600180549050905090565b6105566106bc565b60018281548110151561056557fe5b90600052602060002090018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106045780601f106105d957610100808354040283529160200191610604565b820191906000526020600020905b8154815290600101906020018083116105e757829003601f168201915b50505050509050919050565b8154818355818115116106375781836000526020600020918201910161063691906106d0565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061067d57805160ff19168380011785556106ab565b828001600101855582156106ab579182015b828111156106aa57825182559160200191906001019061068f565b5b5090506106b891906106fc565b5090565b602060405190810160405280600081525090565b6106f991905b808211156106f557600081816106ec9190610721565b506001016106d6565b5090565b90565b61071e91905b8082111561071a576000816000905550600101610702565b5090565b90565b50805460018160011615610100020316600290046000825580601f106107475750610766565b601f01602090049060005260206000209081019061076591906106fc565b5b505600a165627a7a72305820e83f02cdee8bb1fe8c10eb733540e59ebd78995e1dff68004c4f9afa8b393f850029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"key\",\"type\":\"string\"}],\"name\":\"getRoute\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"key\",\"type\":\"string\"},{\"name\":\"addr\",\"type\":\"address\"},{\"name\":\"cache\",\"type\":\"bool\"}],\"name\":\"setRoute\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getRouteSize\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"getRouteNameByIndex\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]";

    private SystemProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private SystemProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private SystemProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private SystemProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public Future<List<Type>> getRoute(Utf8String key) {
        Function function = new Function("getRoute", 
                Arrays.<Type>asList(key), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setRoute(Utf8String key, Address addr, Bool cache) {
        Function function = new Function("setRoute", Arrays.<Type>asList(key, addr, cache), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setRoute(Utf8String key, Address addr, Bool cache, TransactionSucCallback callback) {
        Function function = new Function("setRoute", Arrays.<Type>asList(key, addr, cache), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Uint256> getRouteSize() {
        Function function = new Function("getRouteSize", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getRouteNameByIndex(Uint256 index) {
        Function function = new Function("getRouteNameByIndex", 
                Arrays.<Type>asList(index), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<SystemProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(SystemProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<SystemProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(SystemProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static SystemProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SystemProxy(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static SystemProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SystemProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static SystemProxy loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SystemProxy(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static SystemProxy loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SystemProxy(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }
}
