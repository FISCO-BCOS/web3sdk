package org.bcos.channel.test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.StaticArray;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.bcos.web3j.abi.datatypes.generated.Int256;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
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
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.3.1.
 */
public final class LoadTest extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b61035f8061001c6000396000f300606060405236156100755763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416634a74ffe181146100775780635b319572146100d157806361bc221a14610107578063cbd4946214610129578063daea656314610155578063f0edefe6146101bd575bfe5b341561007f57fe5b6100cf600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965050933593506101e992505050565b005b34156100d957fe5b6100e161029e565b60408051600160a060020a03938416815291909216602082015281519081900390910190f35b341561010f57fe5b6101176102a5565b60408051918252519081900360200190f35b341561013157fe5b6101396102ab565b60408051600160a060020a039092168252519081900360200190f35b341561015d57fe5b610117600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437509496506102ba95505050505050565b60408051918252519081900360200190f35b34156101c557fe5b610139610324565b60408051600160a060020a039092168252519081900360200190f35b600080548201905560405182518291600191859190819060208401908083835b602083106102285780518252601f199092019160209182019101610209565b51815160001960209485036101000a01908116901991909116179052920194855250604051938490030190922092909255505060028054600160a060020a0333811673ffffffffffffffffffffffffffffffffffffffff19928316179092556003805432909316929091169190911790555b5050565b33325b9091565b60005481565b600254600160a060020a031681565b60006001826040518082805190602001908083835b602083106102ee5780518252601f1990920191602091820191016102cf565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054925050505b919050565b600354600160a060020a0316815600a165627a7a7230582060911cb5096d303a45355bb04417c95c183cd617c5e6463b4fea62bc8df403160029";

    private LoadTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private LoadTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> addCounter(Utf8String id, Int256 val) {
        Function function = new Function("addCounter", Arrays.<Type>asList(id, val), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<List<Type>> constGetInf() {
        Function function = new Function("constGetInf", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<Int256> counter() {
        Function function = new Function("counter", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> _sender() {
        Function function = new Function("_sender", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Int256> history(Utf8String id) {
        Function function = new Function("history", 
                Arrays.<Type>asList(id), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> _origin() {
        Function function = new Function("_origin", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<LoadTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(LoadTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<LoadTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(LoadTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static LoadTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LoadTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static LoadTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LoadTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
