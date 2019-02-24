package org.fisco.bcos.web3j.precompile.cns;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
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
public class CNS extends Contract {
  private static final String BINARY = "";

  public static final String FUNC_SELECTBYNAME = "selectByName";

  public static final String FUNC_SELECTBYNAMEANDVERSION = "selectByNameAndVersion";

  public static final String FUNC_INSERT = "insert";

  @Deprecated
  protected CNS(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected CNS(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected CNS(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected CNS(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteCall<String> selectByName(String name) {
    final Function function =
        new Function(
            FUNC_SELECTBYNAME,
            Arrays.<Type>asList(new Utf8String(name)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<String> selectByNameAndVersion(String name, String version) {
    final Function function =
        new Function(
            FUNC_SELECTBYNAMEANDVERSION,
            Arrays.<Type>asList(new Utf8String(name), new Utf8String(version)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<TransactionReceipt> insert(
      String name, String version, String addr, String abi) {
    final Function function =
        new Function(
            FUNC_INSERT,
            Arrays.<Type>asList(
                new Utf8String(name),
                new Utf8String(version),
                new Utf8String(addr),
                new Utf8String(abi)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  @Deprecated
  public static CNS load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new CNS(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static CNS load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new CNS(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static CNS load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new CNS(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static CNS load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new CNS(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<CNS> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(CNS.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<CNS> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(CNS.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<CNS> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(CNS.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<CNS> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(CNS.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }
}
