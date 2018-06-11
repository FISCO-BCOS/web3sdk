package org.bcos.contract.source;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.DynamicArray;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
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
public final class ConsensusControlInterface extends Contract {
    private static String BINARY = "";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"agencyList\",\"type\":\"bytes32[]\"},{\"name\":\"num\",\"type\":\"uint256[]\"}],\"name\":\"control\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"agency\",\"type\":\"bytes32\"}],\"name\":\"addNode\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"agency\",\"type\":\"bytes32\"}],\"name\":\"delNode\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"type\":\"function\"}]";

    private static String GUOMI_BINARY = "";

    private ConsensusControlInterface(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private ConsensusControlInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private ConsensusControlInterface(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    private ConsensusControlInterface(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
        if(EncryptType.encryptType == 1) super.setContractBinary(GUOMI_BINARY);
    }

    public Future<Bool> control(DynamicArray<Bytes32> agencyList, DynamicArray<Uint256> num) {
        Function function = new Function("control", 
                Arrays.<Type>asList(agencyList, num), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> addNode(Bytes32 agency) {
        Function function = new Function("addNode", Arrays.<Type>asList(agency), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void addNode(Bytes32 agency, TransactionSucCallback callback) {
        Function function = new Function("addNode", Arrays.<Type>asList(agency), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<TransactionReceipt> delNode(Bytes32 agency) {
        Function function = new Function("delNode", Arrays.<Type>asList(agency), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void delNode(Bytes32 agency, TransactionSucCallback callback) {
        Function function = new Function("delNode", Arrays.<Type>asList(agency), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public static Future<ConsensusControlInterface> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(ConsensusControlInterface.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<ConsensusControlInterface> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        if(EncryptType.encryptType == 1) setBinary(getGuomiBinary());
        return deployAsync(ConsensusControlInterface.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static ConsensusControlInterface load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConsensusControlInterface(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static ConsensusControlInterface load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConsensusControlInterface(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static ConsensusControlInterface loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConsensusControlInterface(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static ConsensusControlInterface loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConsensusControlInterface(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }

    public static void setBinary(String binary) {
        BINARY = binary;
    }

    public static String getGuomiBinary() {
        return GUOMI_BINARY;
    }
}
