package org.fisco.bcos.temp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tuples.generated.Tuple6;
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
public class Game extends Contract {
    private static final String BINARY = "606060405260006000556000600155341561001657fe5b5b610d1e806100266000396000f30060606040523615610076576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631080caa21461007857806328c13ace1461022157806349b8d6f61461024757806353f4138e1461026d5780635882532f1461034d5780637ea26a2314610515575bfe5b341561008057fe5b61021f600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610878565b005b341561022957fe5b610231610996565b6040518082815260200191505060405180910390f35b341561024f57fe5b61025761099c565b6040518082815260200191505060405180910390f35b341561027557fe5b61034b600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506109a2565b005b341561035557fe5b61036b6004808035906020019091905050610a54565b604051808060200180602001806020018481038452878181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156103fc5780601f106103d1576101008083540402835291602001916103fc565b820191906000526020600020905b8154815290600101906020018083116103df57829003601f168201915b505084810383528681815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561047f5780601f106104545761010080835404028352916020019161047f565b820191906000526020600020905b81548152906001019060200180831161046257829003601f168201915b50508481038252858181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156105025780601f106104d757610100808354040283529160200191610502565b820191906000526020600020905b8154815290600101906020018083116104e557829003601f168201915b5050965050505050505060405180910390f35b341561051d57fe5b6105336004808035906020019091905050610a8b565b6040518080602001806020018060200180602001806020018060200187810387528d8181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156105d05780601f106105a5576101008083540402835291602001916105d0565b820191906000526020600020905b8154815290600101906020018083116105b357829003601f168201915b505087810386528c8181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156106535780601f1061062857610100808354040283529160200191610653565b820191906000526020600020905b81548152906001019060200180831161063657829003601f168201915b505087810385528b8181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156106d65780601f106106ab576101008083540402835291602001916106d6565b820191906000526020600020905b8154815290600101906020018083116106b957829003601f168201915b505087810384528a8181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156107595780601f1061072e57610100808354040283529160200191610759565b820191906000526020600020905b81548152906001019060200180831161073c57829003601f168201915b50508781038352898181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156107dc5780601f106107b1576101008083540402835291602001916107dc565b820191906000526020600020905b8154815290600101906020018083116107bf57829003601f168201915b505087810382528881815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561085f5780601f106108345761010080835404028352916020019161085f565b820191906000526020600020905b81548152906001019060200180831161084257829003601f168201915b50509c5050505050505050505050505060405180910390f35b6003805480600101828161088c9190610ad1565b916000526020600020906006020160005b60c0604051908101604052808a815260200189815260200188815260200187815260200186815260200185815250909190915060008201518160000190805190602001906108ec929190610b03565b506020820151816001019080519060200190610909929190610b03565b506040820151816002019080519060200190610926929190610b03565b506060820151816003019080519060200190610943929190610b03565b506080820151816004019080519060200190610960929190610b03565b5060a082015181600501908051906020019061097d929190610b03565b505050506001600154016001819055505b505050505050565b60005481565b60015481565b600280548060010182816109b69190610b83565b916000526020600020906003020160005b6060604051908101604052808781526020018681526020018581525090919091506000820151816000019080519060200190610a04929190610b03565b506020820151816001019080519060200190610a21929190610b03565b506040820151816002019080519060200190610a3e929190610b03565b505050506001600054016000819055505b505050565b600281815481101515610a6357fe5b906000526020600020906003020160005b915090508060000190806001019080600201905083565b600381815481101515610a9a57fe5b906000526020600020906006020160005b915090508060000190806001019080600201908060030190806004019080600501905086565b815481835581811511610afe57600602816006028360005260206000209182019101610afd9190610bb5565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b4457805160ff1916838001178555610b72565b82800160010185558215610b72579182015b82811115610b71578251825591602001919060010190610b56565b5b509050610b7f9190610c35565b5090565b815481835581811511610bb057600302816003028360005260206000209182019101610baf9190610c5a565b5b505050565b610c3291905b80821115610c2e576000600082016000610bd59190610caa565b600182016000610be59190610caa565b600282016000610bf59190610caa565b600382016000610c059190610caa565b600482016000610c159190610caa565b600582016000610c259190610caa565b50600601610bbb565b5090565b90565b610c5791905b80821115610c53576000816000905550600101610c3b565b5090565b90565b610ca791905b80821115610ca3576000600082016000610c7a9190610caa565b600182016000610c8a9190610caa565b600282016000610c9a9190610caa565b50600301610c60565b5090565b90565b50805460018160011615610100020316600290046000825580601f10610cd05750610cef565b601f016020900490600052602060002090810190610cee9190610c35565b5b505600a165627a7a7230582090a0bbe0dc8cb0615bd4e7f3d71272a76b4d6fd4bac876361d85a1601b064fc60029";

    public static final String FUNC_SAVERESULTRECORD = "saveResultRecord";

    public static final String FUNC_NUMGAMERECORD = "numGameRecord";

    public static final String FUNC_NUMRESULTRECORD = "numResultRecord";

    public static final String FUNC_SAVEGAMERECORD = "saveGameRecord";

    public static final String FUNC_GAMERECORDS = "gameRecords";

    public static final String FUNC_RESULTRECORDS = "resultRecords";

    @Deprecated
    protected Game(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Game(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Game(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Game(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> saveResultRecord(String _player1, String _player2, String _player1_vote, String _player2_vote, String _winer, String _datetime) {
        final Function function = new Function(
                FUNC_SAVERESULTRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_player1), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_player2), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_player1_vote), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_player2_vote), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_winer), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_datetime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> numGameRecord() {
        final Function function = new Function(FUNC_NUMGAMERECORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> numResultRecord() {
        final Function function = new Function(FUNC_NUMRESULTRECORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> saveGameRecord(String _player, String _vote, String _datetime) {
        final Function function = new Function(
                FUNC_SAVEGAMERECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_player), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_vote), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_datetime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, String, String>> gameRecords(BigInteger param0) {
        final Function function = new Function(FUNC_GAMERECORDS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<String, String, String>>(
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<Tuple6<String, String, String, String, String, String>> resultRecords(BigInteger param0) {
        final Function function = new Function(FUNC_RESULTRECORDS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple6<String, String, String, String, String, String>>(
                new Callable<Tuple6<String, String, String, String, String, String>>() {
                    @Override
                    public Tuple6<String, String, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    @Deprecated
    public static Game load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Game(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Game load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Game(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Game load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Game(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Game load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Game(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Game> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Game.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Game> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Game.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Game> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Game.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Game> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Game.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
