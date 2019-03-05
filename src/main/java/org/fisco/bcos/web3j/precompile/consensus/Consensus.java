package org.fisco.bcos.web3j.precompile.consensus;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
public class Consensus extends Contract {
  private static final String BINARY = "";

  public static final String FUNC_ADDOBSERVER = "addObserver";

  public static final String FUNC_REMOVE = "remove";

  public static final String FUNC_ADDSEALER = "addSealer";

  @Deprecated
  protected Consensus(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected Consensus(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected Consensus(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected Consensus(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteCall<TransactionReceipt> addObserver(String nodeID) {
    final Function function =
        new Function(
            FUNC_ADDOBSERVER,
            Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(nodeID)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public void addObserver(String nodeID, TransactionSucCallback callback) {
    final Function function =
        new Function(
            FUNC_ADDOBSERVER,
            Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(nodeID)),
            Collections.<TypeReference<?>>emptyList());
    asyncExecuteTransaction(function, callback);
  }

  public RemoteCall<TransactionReceipt> remove(String nodeID) {
    final Function function =
        new Function(
            FUNC_REMOVE,
            Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(nodeID)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public void remove(String nodeID, TransactionSucCallback callback) {
    final Function function =
        new Function(
            FUNC_REMOVE,
            Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(nodeID)),
            Collections.<TypeReference<?>>emptyList());
    asyncExecuteTransaction(function, callback);
  }

  public RemoteCall<TransactionReceipt> addSealer(String nodeID) {
    final Function function =
        new Function(
            FUNC_ADDSEALER,
            Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(nodeID)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public void addSealer(String nodeID, TransactionSucCallback callback) {
    final Function function =
        new Function(
            FUNC_ADDSEALER,
            Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(nodeID)),
            Collections.<TypeReference<?>>emptyList());
    asyncExecuteTransaction(function, callback);
  }

  @Deprecated
  public static Consensus load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new Consensus(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static Consensus load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new Consensus(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static Consensus load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new Consensus(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static Consensus load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new Consensus(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<Consensus> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(Consensus.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<Consensus> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(Consensus.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<Consensus> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        Consensus.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<Consensus> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        Consensus.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }
}
