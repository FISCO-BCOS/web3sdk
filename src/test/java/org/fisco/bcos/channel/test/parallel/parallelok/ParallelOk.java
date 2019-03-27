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
    private static final String BINARY = "60806040526110066000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005257600080fd5b50610920806100626000396000f300608060405260043610610083576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806334a18dda1461008857806335ee5f87146100fb57806379fa913f146101785780638a42ebe9146101e15780639b80b05014610254578063bca926af1461030d578063d39f70bc14610324575b600080fd5b34801561009457600080fd5b506100f9600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061033b565b005b34801561010757600080fd5b50610162600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506104ac565b6040518082815260200191505060405180910390f35b34801561018457600080fd5b506101df600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610521565b005b3480156101ed57600080fd5b50610252600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610689565b005b34801561026057600080fd5b5061030b600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506106fd565b005b34801561031957600080fd5b506103226107f4565b005b34801561033057600080fd5b50610339610876565b005b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16630553904e3084846040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561041e578082015181840152602081019050610403565b50505050905090810190601f16801561044b5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561046c57600080fd5b505af1158015610480573d6000803e3d6000fd5b505050506040513d602081101561049657600080fd5b8101908080519060200190929190505050505050565b60006001826040518082805190602001908083835b6020831015156104e657805182526020820191506020810190506020830392506104c1565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166311e3f2af30836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156105fd5780820151818401526020810190506105e2565b50505050905090810190601f16801561062a5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561064a57600080fd5b505af115801561065e573d6000803e3d6000fd5b505050506040513d602081101561067457600080fd5b81019080805190602001909291905050505050565b806001836040518082805190602001908083835b6020831015156106c2578051825260208201915060208101905060208303925061069d565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055505050565b806001846040518082805190602001908083835b6020831015156107365780518252602082019150602081019050602083039250610711565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008282540392505081905550806001836040518082805190602001908083835b6020831015156107af578051825260208201915060208101905060208303925061078a565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008282540192505081905550505050565b6108346040805190810160405280601f81526020017f7472616e7366657228737472696e672c737472696e672c75696e743235362900815250600261033b565b6108746040805190810160405280601381526020017f73657428737472696e672c75696e743235362900000000000000000000000000815250600161033b565b565b6108b46040805190810160405280601f81526020017f7472616e7366657228737472696e672c737472696e672c75696e743235362900815250610521565b6108f26040805190810160405280601381526020017f73657428737472696e672c75696e743235362900000000000000000000000000815250610521565b5600a165627a7a7230582062cc089012d7df45ebf77073a15063fcfa0a9150f0d5d6329359b367cf3b5bbf0029";

    public static final String FUNC_REGISTERPARALLELFUNCTION = "registerParallelFunction";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_UNREGISTERPARALLELFUNCTION = "unregisterParallelFunction";

    public static final String FUNC_SET = "set";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_ENABLEPARALLEL = "enableParallel";

    public static final String FUNC_DISABLEPARALLEL = "disableParallel";

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
