package org.fisco.bcos.channel.test.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
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
public class Trans extends Contract {
  private static final String BINARY =
      "606060405234610000575b6001600060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff02191690836c010000000000000000000000009081020402179055506402540be4006000600101819055506002600260000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff02191690836c0100000000000000000000000090810204021790555060006002600101819055505b5b61011d806100b66000396000f360606040526000357c01000000000000000000000000000000000000000000000000000000009004806366c99139146100435780636d4ce63c14610060575b610000565b346100005761005e6004808035906020019091905050610083565b005b346100005761006d61010f565b6040518082815260200191505060405180910390f35b8060006001015410806100a157506002600101548160026001015401105b156100ab5761010c565b8060006001015403600060010181905550806002600101600082825401925050819055507f708194b465f22e1d7b3d3abcea9aa099715f27b82909295cc402b17dd6bd92796002600101546040518082815260200191505060405180910390a15b50565b600060026001015490505b9056";

  public static final String FUNC_TRANS = "trans";

  public static final String FUNC_GET = "get";

  public static final Event LOGADD_EVENT =
      new Event("LogAdd", Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));;

  @Deprecated
  protected Trans(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected Trans(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected Trans(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected Trans(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteCall<TransactionReceipt> trans(BigInteger num) {
    final Function function =
        new Function(
            FUNC_TRANS,
            Arrays.<Type>asList(new Uint256(num)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<BigInteger> get() {
    final Function function =
        new Function(
            FUNC_GET,
            Arrays.<Type>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public List<LogAddEventResponse> getLogAddEvents(TransactionReceipt transactionReceipt) {
    List<EventValuesWithLog> valueList =
        extractEventParametersWithLog(LOGADD_EVENT, transactionReceipt);
    ArrayList<LogAddEventResponse> responses = new ArrayList<LogAddEventResponse>(valueList.size());
    for (EventValuesWithLog eventValues : valueList) {
      LogAddEventResponse typedResponse = new LogAddEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<LogAddEventResponse> logAddEventFlowable(BcosFilter filter) {
    return web3j
        .logFlowable(filter)
        .map(
            new io.reactivex.functions.Function<Log, LogAddEventResponse>() {
              @Override
              public LogAddEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGADD_EVENT, log);
                LogAddEventResponse typedResponse = new LogAddEventResponse();
                typedResponse.log = log;
                typedResponse.index =
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<LogAddEventResponse> logAddEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(LOGADD_EVENT));
    return logAddEventFlowable(filter);
  }

  @Deprecated
  public static Trans load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new Trans(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static Trans load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new Trans(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static Trans load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new Trans(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static Trans load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new Trans(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<Trans> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(Trans.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  public static RemoteCall<Trans> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        Trans.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<Trans> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(Trans.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<Trans> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(Trans.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }

  public static class LogAddEventResponse {
    public Log log;

    public BigInteger index;
  }
}
