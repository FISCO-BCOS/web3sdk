package org.bcos.contract.source;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.FunctionEncoder;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
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
public final class ContractBase extends Contract {
    private static String BINARY = "6060604052341561000c57fe5b6040516103dd3803806103dd833981016040528080518201919050505b806000908051906020019061003f929190610047565b505b506100ec565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008857805160ff19168380011785556100b6565b828001600101855582156100b6579182015b828111156100b557825182559160200191906001019061009a565b5b5090506100c391906100c7565b5090565b6100e991905b808211156100e55760008160009055506001016100cd565b5090565b90565b6102e2806100fb6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630d8e6e2c14610046578063788bc78c146100df575bfe5b341561004e57fe5b610056610139565b60405180806020018281038252838181518152602001915080519060200190808383600083146100a5575b8051825260208311156100a557602082019150602081019050602083039250610081565b505050905090810190601f1680156100d15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156100e757fe5b610137600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506101e2565b005b6101416101fd565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156101d75780601f106101ac576101008083540402835291602001916101d7565b820191906000526020600020905b8154815290600101906020018083116101ba57829003601f168201915b505050505090505b90565b80600090805190602001906101f8929190610211565b505b50565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061025257805160ff1916838001178555610280565b82800160010185558215610280579182015b8281111561027f578251825591602001919060010190610264565b5b50905061028d9190610291565b5090565b6102b391905b808211156102af576000816000905550600101610297565b5090565b905600a165627a7a72305820c8eb2be90634f42092f93dbaa6a56a94c5c7f8469e8531bb75fdeee8af0a85bb0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"getVersion\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"version_para\",\"type\":\"string\"}],\"name\":\"setVersion\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"inputs\":[{\"name\":\"version_para\",\"type\":\"string\"}],\"payable\":false,\"type\":\"constructor\"}]";

    private static String GUOMI_BINARY = "6060604052341561000c57fe5b6040516103dd3803806103dd833981016040528080518201919050505b806000908051906020019061003f929190610047565b505b506100ec565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008857805160ff19168380011785556100b6565b828001600101855582156100b6579182015b828111156100b557825182559160200191906001019061009a565b5b5090506100c391906100c7565b5090565b6100e991905b808211156100e55760008160009055506001016100cd565b5090565b90565b6102e2806100fb6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806395ce2ccc14610046578063ecb905ca146100a0575bfe5b341561004e57fe5b61009e600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610139565b005b34156100a857fe5b6100b0610154565b60405180806020018281038252838181518152602001915080519060200190808383600083146100ff575b8051825260208311156100ff576020820191506020810190506020830392506100db565b505050905090810190601f16801561012b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b806000908051906020019061014f9291906101fd565b505b50565b61015c61027d565b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156101f25780601f106101c7576101008083540402835291602001916101f2565b820191906000526020600020905b8154815290600101906020018083116101d557829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061023e57805160ff191683800117855561026c565b8280016001018555821561026c579182015b8281111561026b578251825591602001919060010190610250565b5b5090506102799190610291565b5090565b602060405190810160405280600081525090565b6102b391905b808211156102af576000816000905550600101610297565b5090565b905600a165627a7a7230582045cd9363e0b7843d96dc80607451d2afdf503da1acbc26fde19723c51d606ab60029";

    private ContractBase(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private ContractBase(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private ContractBase(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private ContractBase(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    public Future<Utf8String> getVersion() {
        Function function = new Function("getVersion", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setVersion(Utf8String version_para) {
        Function function = new Function("setVersion", Arrays.<Type>asList(version_para), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setVersion(Utf8String version_para, TransactionSucCallback callback) {
        Function function = new Function("setVersion", Arrays.<Type>asList(version_para), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public static Future<ContractBase> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Utf8String version_para) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(version_para));
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(ContractBase.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Future<ContractBase> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Utf8String version_para) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(version_para));
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(ContractBase.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static ContractBase load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBase(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static ContractBase load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBase(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static ContractBase loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBase(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static ContractBase loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBase(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static void setBinary(String binary) {
        BINARY = binary;
    }

    public static String getGuomiBinary() {
        return GUOMI_BINARY;
    }
}
