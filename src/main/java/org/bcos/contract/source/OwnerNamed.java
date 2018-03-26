package org.bcos.contract.source;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Int256;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
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
public final class OwnerNamed extends Contract {
    private static final String BINARY = "60606040526000600155341561001457600080fd5b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550611450806100636000396000f3006060604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063180db1b4146100bf5780631bd95155146100e85780632ced7cef14610159578063319af333146102505780633ca6268e146102e05780633ffbd47f1461035a57806341304fac146103fa57806341c0e1b51461046b5780634b5c4277146104805780635e01eb5a14610534578063893d20e8146105c2578063b60e72cc14610650575b600080fd5b34156100ca57600080fd5b6100d26106ca565b6040518082815260200191505060405180910390f35b34156100f357600080fd5b610143600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506106d4565b6040518082815260200191505060405180910390f35b341561016457600080fd5b61023a600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506107ab565b6040518082815260200191505060405180910390f35b341561025b57600080fd5b6102ca600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610820565b6040518082815260200191505060405180910390f35b34156102eb57600080fd5b610344600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080359060200190919050506108a6565b6040518082815260200191505060405180910390f35b341561036557600080fd5b6103f8600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610916565b005b341561040557600080fd5b610455600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061091a565b6040518082815260200191505060405180910390f35b341561047657600080fd5b61047e61094e565b005b341561048b57600080fd5b61051e600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506109ac565b6040518082815260200191505060405180910390f35b341561053f57600080fd5b6105476109f1565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561058757808201518184015260208101905061056c565b50505050905090810190601f1680156105b45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156105cd57600080fd5b6105d5610a1d565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156106155780820151818401526020810190506105fa565b50505050905090810190601f1680156106425780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561065b57600080fd5b6106b4600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091908035906020019091905050610a6a565b6040518082815260200191505060405180910390f35b6000600154905090565b60008060008351915060208211156106eb57602091505b60009250600090505b60208110156107a4578181101561079157838181518110151561071357fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f0100000000000000000000000000000000000000000000000000000000000000027f0100000000000000000000000000000000000000000000000000000000000000900460ff166101008402019250610799565b610100830292505b8060010190506106f4565b5050919050565b60006107b56113fc565b602060405190810160405280600081525090506107dd858583610ad69092919063ffffffff16565b90506107e8836106d4565b600102600019166107f8826106d4565b60010260405180826000191660001916815260200191505060405180910390a1509392505050565b600061082a6113fc565b60206040519081016040528060008152509050610870846108608573ffffffffffffffffffffffffffffffffffffffff16610b76565b83610ad69092919063ffffffff16565b905061087b816106d4565b60010260405180826000191660001916815260200191505060405180910390a0600091505092915050565b60006108b06113fc565b602060405190810160405280600081525090506108e0846108d085610d84565b83610ad69092919063ffffffff16565b90506108eb816106d4565b60010260405180826000191660001916815260200191505060405180910390a0600091505092915050565b5050565b6000610925826106d4565b60010260405180826000191660001916815260200191505060405180910390a060009050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156109a9576109aa565b5b565b60006109b7826106d4565b600102600019166109c7846106d4565b60010260405180826000191660001916815260200191505060405180910390a16000905092915050565b6109f96113fc565b610a183373ffffffffffffffffffffffffffffffffffffffff16610f62565b905090565b610a256113fc565b610a656000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16610f62565b905090565b6000610a746113fc565b60206040519081016040528060008152509050610aa484610a9485611173565b83610ad69092919063ffffffff16565b9050610aaf816106d4565b60010260405180826000191660001916815260200191505060405180910390a05092915050565b610ade6113fc565b6000806000806000865188518a510101604051805910610afb5750595b9080825280601f01601f1916602001820160405250955060208901945060208801935060208701925060208601915060009050610b3c818301868b516112c1565b885181019050610b50818301858a516112c1565b875181019050610b648183018489516112c1565b86518101905050505050509392505050565b610b7e6113fc565b600080602a604051805910610b905750595b9080825280601f01601f191660200182016040525092507f3000000000000000000000000000000000000000000000000000000000000000836000815181101515610bd757fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053507f7800000000000000000000000000000000000000000000000000000000000000836001815181101515610c3757fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350602991505b60028260ff16101515610d7d57600f84169050601084811515610c8b57fe5b049350600a8160ff161015610d0657603081017f010000000000000000000000000000000000000000000000000000000000000002838360ff16815181101515610cd157fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350610d71565b6061600a8203017f010000000000000000000000000000000000000000000000000000000000000002838360ff16815181101515610d4057fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053505b81600190039150610c6c565b5050919050565b610d8c6113fc565b600080600080600080871415610dd9576040805190810160405280600181526020017f30000000000000000000000000000000000000000000000000000000000000008152509550610f58565b86945060019350600092506000871215610e00578660000394506000935082806001019350505b8491505b6000821115610e2957600a82811515610e1957fe5b0491508280600101935050610e04565b8260ff16604051805910610e3a5750595b9080825280601f01601f19166020018201604052509550831515610eb9577f2d00000000000000000000000000000000000000000000000000000000000000866000815181101515610e8857fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053505b6001830390505b6000851115610f57576030600a86811515610ed757fe5b06017f01000000000000000000000000000000000000000000000000000000000000000286828060019003935060ff16815181101515610f1357fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600a85811515610f4f57fe5b049450610ec0565b5b5050505050919050565b610f6a6113fc565b610f72611410565b6000806000806028604051805910610f875750595b9080825280601f01601f19166020018201604052509450600093505b6014841015611166578360130360080260020a8773ffffffffffffffffffffffffffffffffffffffff16811515610fd657fe5b047f01000000000000000000000000000000000000000000000000000000000000000292506010837f0100000000000000000000000000000000000000000000000000000000000000900460ff1681151561102d57fe5b047f0100000000000000000000000000000000000000000000000000000000000000029150817f01000000000000000000000000000000000000000000000000000000000000009004601002837f01000000000000000000000000000000000000000000000000000000000000009004037f01000000000000000000000000000000000000000000000000000000000000000290506110cb8261130c565b85856002028151811015156110dc57fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053506111158161130c565b856001866002020181518110151561112957fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053508380600101945050610fa3565b8495505050505050919050565b61117b6113fc565b6000806000808514156111c5576040805190810160405280600181526020017f300000000000000000000000000000000000000000000000000000000000000081525093506112b9565b600092508491505b60008211156111f257600a828115156111e257fe5b04915082806001019350506111cd565b8260ff166040518059106112035750595b9080825280601f01601f191660200182016040525093506001830390505b60008511156112b8576030600a8681151561123857fe5b06017f01000000000000000000000000000000000000000000000000000000000000000284828060019003935060ff1681518110151561127457fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600a858115156112b057fe5b049450611221565b5b505050919050565b60005b6020821015156112e957825184526020840193506020830192506020820391506112c4565b6001826020036101000a0390508019835116818551168181178652505050505050565b6000600a7f010000000000000000000000000000000000000000000000000000000000000002827effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191610156113ab576030827f01000000000000000000000000000000000000000000000000000000000000009004017f01000000000000000000000000000000000000000000000000000000000000000290506113f7565b6057827f01000000000000000000000000000000000000000000000000000000000000009004017f01000000000000000000000000000000000000000000000000000000000000000290505b919050565b602060405190810160405280600081525090565b6020604051908101604052806000815250905600a165627a7a7230582025fcc81574632f40f65b8c53217ff2f0a5aaaf51347fc0df2e9c6779cff7d26c0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"getErrno\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"}],\"name\":\"stringToUint\",\"outputs\":[{\"name\":\"_ret\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"},{\"name\":\"_str2\",\"type\":\"string\"},{\"name\":\"_topic\",\"type\":\"string\"}],\"name\":\"log\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"},{\"name\":\"_addr\",\"type\":\"address\"}],\"name\":\"log\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"},{\"name\":\"_i\",\"type\":\"int256\"}],\"name\":\"log\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_name\",\"type\":\"string\"},{\"name\":\"_version\",\"type\":\"string\"}],\"name\":\"register\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"}],\"name\":\"log\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"kill\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"},{\"name\":\"_topic\",\"type\":\"string\"}],\"name\":\"log\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getSender\",\"outputs\":[{\"name\":\"_ret\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getOwner\",\"outputs\":[{\"name\":\"_ret\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"},{\"name\":\"_ui\",\"type\":\"uint256\"}],\"name\":\"log\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";

    private OwnerNamed(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, isInitByName);
    }

    private OwnerNamed(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean isInitByName) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, isInitByName);
    }

    private OwnerNamed(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    private OwnerNamed(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public Future<Uint256> getErrno() {
        Function function = new Function("getErrno", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> stringToUint(Utf8String _str) {
        Function function = new Function("stringToUint", 
                Arrays.<Type>asList(_str), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> log(Utf8String _str, Utf8String _str2, Utf8String _topic) {
        Function function = new Function("log", 
                Arrays.<Type>asList(_str, _str2, _topic), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> log(Utf8String _str, Address _addr) {
        Function function = new Function("log", 
                Arrays.<Type>asList(_str, _addr), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> log(Utf8String _str, Int256 _i) {
        Function function = new Function("log", 
                Arrays.<Type>asList(_str, _i), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> register(Utf8String _name, Utf8String _version) {
        Function function = new Function("register", Arrays.<Type>asList(_name, _version), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void register(Utf8String _name, Utf8String _version, TransactionSucCallback callback) {
        Function function = new Function("register", Arrays.<Type>asList(_name, _version), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Uint256> log(Utf8String _str) {
        Function function = new Function("log", 
                Arrays.<Type>asList(_str), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> kill() {
        Function function = new Function("kill", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void kill(TransactionSucCallback callback) {
        Function function = new Function("kill", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Uint256> log(Utf8String _str, Utf8String _topic) {
        Function function = new Function("log", 
                Arrays.<Type>asList(_str, _topic), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getSender() {
        Function function = new Function("getSender", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getOwner() {
        Function function = new Function("getOwner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> log(Utf8String _str, Uint256 _ui) {
        Function function = new Function("log", 
                Arrays.<Type>asList(_str, _ui), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<OwnerNamed> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(OwnerNamed.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<OwnerNamed> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(OwnerNamed.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static OwnerNamed load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnerNamed(contractAddress, web3j, credentials, gasPrice, gasLimit, false);
    }

    public static OwnerNamed load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnerNamed(contractAddress, web3j, transactionManager, gasPrice, gasLimit, false);
    }

    public static OwnerNamed loadByName(String contractName, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnerNamed(contractName, web3j, credentials, gasPrice, gasLimit, true);
    }

    public static OwnerNamed loadByName(String contractName, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnerNamed(contractName, web3j, transactionManager, gasPrice, gasLimit, true);
    }
}
