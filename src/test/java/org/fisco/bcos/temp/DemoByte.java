package org.fisco.bcos.temp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
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
public class DemoByte extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b5b5b6101f18061001e6000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806310009562146100515780631d909276146100825780633bc5de30146100cd575bfe5b341561005957fe5b6100806004808035600019169060200190919080356000191690602001909190505061010a565b005b341561008a57fe5b6100a06004808035906020019091905050610164565b60405180836000191660001916815260200182600019166000191681526020019250505060405180910390f35b34156100d557fe5b6100dd610188565b60405180836000191660001916815260200182600019166000191681526020019250505060405180910390f35b604060405190810160405280836000191681526020018260001916815250600060006000815260200190815260200160002060008201518160000190600019169055602082015181600101906000191690559050505b5050565b60006020528060005260406000206000915090508060000154908060010154905082565b60006000600060006000815260200190815260200160002060000154600060006000815260200190815260200160002060010154915091505b90915600a165627a7a72305820a9b489bfe14ef8666fd570727ae2e739a9733832eedae7fb29fb08bbbf71527d0029";

    public static final String FUNC_ADDDATA = "addData";

    public static final String FUNC_METADATA = "metaData";

    public static final String FUNC_GETDATA = "getData";

    @Deprecated
    protected DemoByte(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DemoByte(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DemoByte(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DemoByte(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addData(byte[] data, byte[] data2) {
        final Function function = new Function(
                FUNC_ADDDATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(data), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(data2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<byte[], byte[]>> metaData(BigInteger param0) {
        final Function function = new Function(FUNC_METADATA, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple2<byte[], byte[]>>(
                new Callable<Tuple2<byte[], byte[]>>() {
                    @Override
                    public Tuple2<byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], byte[]>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> getData() {
        final Function function = new Function(
                FUNC_GETDATA, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static DemoByte load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DemoByte(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DemoByte load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DemoByte(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DemoByte load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DemoByte(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DemoByte load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DemoByte(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DemoByte> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DemoByte.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<DemoByte> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DemoByte.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DemoByte> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DemoByte.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DemoByte> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DemoByte.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
