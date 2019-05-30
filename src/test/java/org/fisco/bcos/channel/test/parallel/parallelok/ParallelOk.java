package org.fisco.bcos.channel.test.parallel.parallelok;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>
 * Auto generated code.
 * <p>
 * <strong>Do not modify!</strong>
 * <p>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j
 * command line tools</a>, or the
 * org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen
 * module</a> to update.
 *
 * <p>
 * Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class ParallelOk extends Contract {
    private static final String BINARY = "60806040526110066000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005257600080fd5b50610bb7806100626000396000f30060806040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806334a18dda1461009357806335ee5f871461010657806379fa913f146101835780638a42ebe9146101ec5780639b80b0501461025f578063bca926af14610318578063d39f70bc1461032f578063fad42f8714610346575b600080fd5b34801561009f57600080fd5b50610104600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506103ff565b005b34801561011257600080fd5b5061016d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610570565b6040518082815260200191505060405180910390f35b34801561018f57600080fd5b506101ea600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506105e5565b005b3480156101f857600080fd5b5061025d600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061074d565b005b34801561026b57600080fd5b50610316600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506107c1565b005b34801561032457600080fd5b5061032d6108b8565b005b34801561033b57600080fd5b506103446109a1565b005b34801561035257600080fd5b506103fd600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610a84565b005b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16630553904e3084846040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b838110156104e25780820151818401526020810190506104c7565b50505050905090810190601f16801561050f5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561053057600080fd5b505af1158015610544573d6000803e3d6000fd5b505050506040513d602081101561055a57600080fd5b8101908080519060200190929190505050505050565b60006001826040518082805190602001908083835b6020831015156105aa5780518252602082019150602081019050602083039250610585565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166311e3f2af30836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156106c15780820151818401526020810190506106a6565b50505050905090810190601f1680156106ee5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561070e57600080fd5b505af1158015610722573d6000803e3d6000fd5b505050506040513d602081101561073857600080fd5b81019080805190602001909291905050505050565b806001836040518082805190602001908083835b6020831015156107865780518252602082019150602081019050602083039250610761565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055505050565b806001846040518082805190602001908083835b6020831015156107fa57805182526020820191506020810190506020830392506107d5565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008282540392505081905550806001836040518082805190602001908083835b602083101515610873578051825260208201915060208101905060208303925061084e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008282540192505081905550505050565b6108f86040805190810160405280601f81526020017f7472616e7366657228737472696e672c737472696e672c75696e74323536290081525060026103ff565b6109386040805190810160405280601381526020017f73657428737472696e672c75696e74323536290000000000000000000000000081525060016103ff565b61099f606060405190810160405280602981526020017f7472616e736665725769746852657665727428737472696e672c737472696e6781526020017f2c75696e7432353629000000000000000000000000000000000000000000000081525060026103ff565b565b6109df6040805190810160405280601f81526020017f7472616e7366657228737472696e672c737472696e672c75696e7432353629008152506105e5565b610a1d6040805190810160405280601381526020017f73657428737472696e672c75696e7432353629000000000000000000000000008152506105e5565b610a82606060405190810160405280602981526020017f7472616e736665725769746852657665727428737472696e672c737472696e6781526020017f2c75696e743235362900000000000000000000000000000000000000000000008152506105e5565b565b806001846040518082805190602001908083835b602083101515610abd5780518252602082019150602081019050602083039250610a98565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008282540392505081905550806001836040518082805190602001908083835b602083101515610b365780518252602082019150602081019050602083039250610b11565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000828254019250508190555060648111151515610b8657600080fd5b5050505600a165627a7a723058206c64dc79155185b3154a84b9b328acf23858657ed29a9cf028fb8166b86b5a200029";

    public static final String FUNC_REGISTERPARALLELFUNCTION = "registerParallelFunction";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_UNREGISTERPARALLELFUNCTION = "unregisterParallelFunction";

    public static final String FUNC_SET = "set";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_ENABLEPARALLEL = "enableParallel";

    public static final String FUNC_DISABLEPARALLEL = "disableParallel";

    public static final String FUNC_TRANSFERWITHREVERT = "transferWithRevert";

    @Deprecated
    protected ParallelOk(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ParallelOk(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ParallelOk(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ParallelOk(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> registerParallelFunction(String functionName, BigInteger criticalSize) {
        final Function function = new Function(FUNC_REGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(criticalSize)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void registerParallelFunction(String functionName, BigInteger criticalSize,
            TransactionSucCallback callback) {
        final Function function = new Function(FUNC_REGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(criticalSize)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<BigInteger> balanceOf(String name) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> unregisterParallelFunction(String functionName) {
        final Function function = new Function(FUNC_UNREGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void unregisterParallelFunction(String functionName, TransactionSucCallback callback) {
        final Function function = new Function(FUNC_UNREGISTERPARALLELFUNCTION,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> set(String name, BigInteger num) {
        final Function function = new Function(FUNC_SET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(num)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void set(String name, BigInteger num, TransactionSucCallback callback) {
        final Function function = new Function(FUNC_SET,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(num)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> transfer(String from, String to, BigInteger num) {
        final Function function = new Function(FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(from),
                        new org.fisco.bcos.web3j.abi.datatypes.Utf8String(to),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(num)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transfer(String from, String to, BigInteger num, TransactionSucCallback callback) {
        final Function function = new Function(FUNC_TRANSFER,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(from),
                        new org.fisco.bcos.web3j.abi.datatypes.Utf8String(to),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(num)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> enableParallel() {
        final Function function = new Function(FUNC_ENABLEPARALLEL, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void enableParallel(TransactionSucCallback callback) {
        final Function function = new Function(FUNC_ENABLEPARALLEL, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> disableParallel() {
        final Function function = new Function(FUNC_DISABLEPARALLEL, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void disableParallel(TransactionSucCallback callback) {
        final Function function = new Function(FUNC_DISABLEPARALLEL, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> transferWithRevert(String from, String to, BigInteger num) {
        final Function function = new Function(FUNC_TRANSFERWITHREVERT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(from),
                        new org.fisco.bcos.web3j.abi.datatypes.Utf8String(to),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(num)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transferWithRevert(String from, String to, BigInteger num, TransactionSucCallback callback) {
        final Function function = new Function(FUNC_TRANSFERWITHREVERT,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(from),
                        new org.fisco.bcos.web3j.abi.datatypes.Utf8String(to),
                        new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(num)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    @Deprecated
    public static ParallelOk load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return new ParallelOk(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ParallelOk load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new ParallelOk(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ParallelOk load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new ParallelOk(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ParallelOk load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new ParallelOk(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ParallelOk> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ParallelOk.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ParallelOk> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(ParallelOk.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ParallelOk> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ParallelOk.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ParallelOk> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(ParallelOk.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
