package org.bcos.solidity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.RemoteCall;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import org.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
public class Ok extends Contract {
    private static final String BINARY = "606060405260008054600160a060020a0319908116600190811783556402540be4009055600280549091168117905560035561024f8061003f6000396000f3606060405260e060020a600035046366c9913981146100295780636d4ce63c1461007d575b610002565b3461000257610097600435600180548290038155600380548301905560048054918201808255909190828183801582901161009957600402816004028360005260206000209182019101610099919061019b565b346100025760035460408051918252519081900360200190f35b005b505050600092835260208084206040805160c0810182526008608082019081527f323031373034313300000000000000000000000000000000000000000000000060a08301908152908252875473ffffffffffffffffffffffffffffffffffffffff908116838701526002805490911693830193909352606082018990526004969096029092018054818852968490209551601060ff1990911617815593959194849361020393610100600182161502600019011692909204601f01919091048101906101eb565b505060018101805473ffffffffffffffffffffffffffffffffffffffff199081169091556002820180549091169055600060038201556004015b808211156101ff57600060008201600050805460018160011615610100020316600290046000825580601f106101d15750610161565b601f01602090049060005260206000209081019061016191905b808211156101ff57600081556001016101eb565b5090565b5050602082015160018201805473ffffffffffffffffffffffffffffffffffffffff1990811690921790556040830151600283018054909216179055606090910151600390910155505056\r\n";

    public static final String FUNC_TRANS = "trans";

    public static final String FUNC_GET = "get";

    @Deprecated
    protected Ok(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ok(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Ok(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Ok(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> trans(BigInteger num) {
        final Function function = new Function(
                FUNC_TRANS, 
                Arrays.<Type>asList(new org.bcos.web3j.abi.datatypes.generated.Uint256(num)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> get() {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Ok load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ok(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Ok load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ok(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Ok load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Ok(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Ok load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Ok(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Ok> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, credentials, contractGasProvider, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<Ok> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, transactionManager, contractGasProvider, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Ok> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Ok> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Ok.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }
}
