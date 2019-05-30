package org.fisco.bcos.channel.test.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
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
public class DBTest extends Contract {
  private static final String BINARY =
      "608060405234801561001057600080fd5b50612183806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063487a5a1014610072578063616ffe831461013f578063c4f41ab31461028d578063ebf3b24f14610314578063efc81a8c146103e1575b600080fd5b34801561007e57600080fd5b50610129600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506103f8565b6040518082815260200191505060405180910390f35b34801561014b57600080fd5b506101a6600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610aee565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b838110156101f15780820151818401526020810190506101d6565b50505050905001848103835286818151815260200191508051906020019060200280838360005b83811015610233578082015181840152602081019050610218565b50505050905001848103825285818151815260200191508051906020019060200280838360005b8381101561027557808201518184015260208101905061025a565b50505050905001965050505050505060405180910390f35b34801561029957600080fd5b506102fe600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506114ad565b6040518082815260200191505060405180910390f35b34801561032057600080fd5b506103cb600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506119a6565b6040518082815260200191505060405180910390f35b3480156103ed57600080fd5b506103f6611fc5565b005b60008060008060008061100194508473ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156104a657600080fd5b505af11580156104ba573d6000803e3d6000fd5b505050506040513d60208110156104d057600080fd5b810190808051906020019092919050505093508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561054757600080fd5b505af115801561055b573d6000803e3d6000fd5b505050506040513d602081101561057157600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b516886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610644578082015181840152602081019050610629565b50505050905090810190601f1680156106715780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561069157600080fd5b505af11580156106a5573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561070d57600080fd5b505af1158015610721573d6000803e3d6000fd5b505050506040513d602081101561073757600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d18a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561080a5780820151818401526020810190506107ef565b50505050905090810190601f1680156108375780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561085757600080fd5b505af115801561086b573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561091757600080fd5b505af115801561092b573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18a85856040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b83811015610a1d578082015181840152602081019050610a02565b50505050905090810190601f168015610a4a5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b158015610a6b57600080fd5b505af1158015610a7f573d6000803e3d6000fd5b505050506040513d6020811015610a9557600080fd5b810190808051906020019092919050505090507f0bdcb3b747cf033ae78b4b6e1576d2725709d03f68ad3d641b12cb72de614354816040518082815260200191505060405180910390a180955050505050509392505050565b6060806060600080600080606080606060008061100198508873ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610ba657600080fd5b505af1158015610bba573d6000803e3d6000fd5b505050506040513d6020811015610bd057600080fd5b810190808051906020019092919050505097508773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610c4757600080fd5b505af1158015610c5b573d6000803e3d6000fd5b505050506040513d6020811015610c7157600080fd5b810190808051906020019092919050505096508773ffffffffffffffffffffffffffffffffffffffff1663e8434e398e896040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610d3f578082015181840152602081019050610d24565b50505050905090810190601f168015610d6c5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610d8c57600080fd5b505af1158015610da0573d6000803e3d6000fd5b505050506040513d6020811015610db657600080fd5b810190808051906020019092919050505095508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610e2d57600080fd5b505af1158015610e41573d6000803e3d6000fd5b505050506040513d6020811015610e5757600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015610e965781602001602082028038833980820191505090505b5094508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610efd57600080fd5b505af1158015610f11573d6000803e3d6000fd5b505050506040513d6020811015610f2757600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015610f665781602001602082028038833980820191505090505b5093508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610fcd57600080fd5b505af1158015610fe1573d6000803e3d6000fd5b505050506040513d6020811015610ff757600080fd5b81019080805190602001909291905050506040519080825280602002602001820160405280156110365781602001602082028038833980820191505090505b509250600091505b8573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156110a257600080fd5b505af11580156110b6573d6000803e3d6000fd5b505050506040513d60208110156110cc57600080fd5b8101908080519060200190929190505050821215611494578573ffffffffffffffffffffffffffffffffffffffff1663846719e0836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561115357600080fd5b505af1158015611167573d6000803e3d6000fd5b505050506040513d602081101561117d57600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561123057600080fd5b505af1158015611244573d6000803e3d6000fd5b505050506040513d602081101561125a57600080fd5b8101908080519060200190929190505050858381518110151561127957fe5b9060200190602002019060001916908160001916815250508073ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f6974656d5f696400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561133157600080fd5b505af1158015611345573d6000803e3d6000fd5b505050506040513d602081101561135b57600080fd5b8101908080519060200190929190505050848381518110151561137a57fe5b90602001906020020181815250508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561142857600080fd5b505af115801561143c573d6000803e3d6000fd5b505050506040513d602081101561145257600080fd5b8101908080519060200190929190505050838381518110151561147157fe5b90602001906020020190600019169081600019168152505081600101915061103e565b8484849b509b509b505050505050505050509193909250565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561155a57600080fd5b505af115801561156e573d6000803e3d6000fd5b505050506040513d602081101561158457600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156115fb57600080fd5b505af115801561160f573d6000803e3d6000fd5b505050506040513d602081101561162557600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d1886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156116f85780820151818401526020810190506116dd565b50505050905090810190601f1680156117255780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561174557600080fd5b505af1158015611759573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561180557600080fd5b505af1158015611819573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166328bb211788846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156118d85780820151818401526020810190506118bd565b50505050905090810190601f1680156119055780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561192557600080fd5b505af1158015611939573d6000803e3d6000fd5b505050506040513d602081101561194f57600080fd5b810190808051906020019092919050505090507f896358cb98e9e8e891ae04efd1bc177efbe5cffd7eca2e784b16ed7468553e08816040518082815260200191505060405180910390a18094505050505092915050565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611a5357600080fd5b505af1158015611a67573d6000803e3d6000fd5b505050506040513d6020811015611a7d57600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611af457600080fd5b505af1158015611b08573d6000803e3d6000fd5b505050506040513d6020811015611b1e57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b516896040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611bf1578082015181840152602081019050611bd6565b50505050905090810190601f168015611c1e5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015611c3e57600080fd5b505af1158015611c52573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74886040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015611cfe57600080fd5b505af1158015611d12573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611dd6578082015181840152602081019050611dbb565b50505050905090810190601f168015611e035780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015611e2357600080fd5b505af1158015611e37573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3689846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611ef6578082015181840152602081019050611edb565b50505050905090810190601f168015611f235780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015611f4357600080fd5b505af1158015611f57573d6000803e3d6000fd5b505050506040513d6020811015611f6d57600080fd5b810190808051906020019092919050505090507f66f7705280112a4d1145399e0414adc43a2d6974b487710f417edcf7d4a39d71816040518082815260200191505060405180910390a1809450505050509392505050565b60008061100191508173ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001848103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001848103825260118152602001807f6974656d5f69642c6974656d5f6e616d650000000000000000000000000000008152506020019350505050602060405180830381600087803b1580156120df57600080fd5b505af11580156120f3573d6000803e3d6000fd5b505050506040513d602081101561210957600080fd5b810190808051906020019092919050505090507fcd4779437d9d027acc605a96427bfbd3787a1402cb53a5e64cd813d5391fbc2b816040518082815260200191505060405180910390a150505600a165627a7a723058204a7dfc54315e9181f8a525c4747391179b0e12584ce4ef5deebc31d79404e8f20029";

  public static final String FUNC_UPDATE = "update";

  public static final String FUNC_READ = "read";

  public static final String FUNC_REMOVE = "remove";

  public static final String FUNC_INSERT = "insert";

  public static final String FUNC_CREATE = "create";

  public static final Event CREATERESULT_EVENT =
      new Event("createResult", Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

  public static final Event INSERTRESULT_EVENT =
      new Event("insertResult", Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

  public static final Event UPDATERESULT_EVENT =
      new Event("updateResult", Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

  public static final Event REMOVERESULT_EVENT =
      new Event("removeResult", Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

  public static final Event READRESULT_EVENT =
      new Event(
          "readResult",
          Arrays.<TypeReference<?>>asList(
              new TypeReference<Bytes32>() {},
              new TypeReference<Int256>() {},
              new TypeReference<Bytes32>() {}));;

  @Deprecated
  protected DBTest(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected DBTest(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected DBTest(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected DBTest(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteCall<TransactionReceipt> update(String name, BigInteger item_id, String item_name) {
    final Function function =
        new Function(
            FUNC_UPDATE,
            Arrays.<Type>asList(
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(item_name)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>> read(String name) {
    final Function function =
        new Function(
            FUNC_READ,
            Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name)),
            Arrays.<TypeReference<?>>asList(
                new TypeReference<DynamicArray<Bytes32>>() {},
                new TypeReference<DynamicArray<Int256>>() {},
                new TypeReference<DynamicArray<Bytes32>>() {}));
    return new RemoteCall<Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>>(
        new Callable<Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>>() {
          @Override
          public Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>> call() throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>(
                convertToNative((List<Bytes32>) results.get(0).getValue()),
                convertToNative((List<Int256>) results.get(1).getValue()),
                convertToNative((List<Bytes32>) results.get(2).getValue()));
          }
        });
  }

  public RemoteCall<TransactionReceipt> remove(String name, BigInteger item_id) {
    final Function function =
        new Function(
            FUNC_REMOVE,
            Arrays.<Type>asList(
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<TransactionReceipt> insert(String name, BigInteger item_id, String item_name) {
    final Function function =
        new Function(
            FUNC_INSERT,
            Arrays.<Type>asList(
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id),
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(item_name)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<TransactionReceipt> create() {
    final Function function =
        new Function(FUNC_CREATE, Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public List<CreateResultEventResponse> getCreateResultEvents(
      TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList =
        extractEventParametersWithLog(CREATERESULT_EVENT, transactionReceipt);
    ArrayList<CreateResultEventResponse> responses =
        new ArrayList<CreateResultEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      CreateResultEventResponse typedResponse = new CreateResultEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<CreateResultEventResponse> createResultEventFlowable(BcosFilter filter) {
    return web3j
        .logFlowable(filter)
        .map(
            new io.reactivex.functions.Function<Log, CreateResultEventResponse>() {
              @Override
              public CreateResultEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues =
                    extractEventParametersWithLog(CREATERESULT_EVENT, log);
                CreateResultEventResponse typedResponse = new CreateResultEventResponse();
                typedResponse.log = log;
                typedResponse.count =
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<CreateResultEventResponse> createResultEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(CREATERESULT_EVENT));
    return createResultEventFlowable(filter);
  }

  public List<InsertResultEventResponse> getInsertResultEvents(
      TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList =
        extractEventParametersWithLog(INSERTRESULT_EVENT, transactionReceipt);
    ArrayList<InsertResultEventResponse> responses =
        new ArrayList<InsertResultEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      InsertResultEventResponse typedResponse = new InsertResultEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<InsertResultEventResponse> insertResultEventFlowable(BcosFilter filter) {
    return web3j
        .logFlowable(filter)
        .map(
            new io.reactivex.functions.Function<Log, InsertResultEventResponse>() {
              @Override
              public InsertResultEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues =
                    extractEventParametersWithLog(INSERTRESULT_EVENT, log);
                InsertResultEventResponse typedResponse = new InsertResultEventResponse();
                typedResponse.log = log;
                typedResponse.count =
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<InsertResultEventResponse> insertResultEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(INSERTRESULT_EVENT));
    return insertResultEventFlowable(filter);
  }

  public List<UpdateResultEventResponse> getUpdateResultEvents(
      TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList =
        extractEventParametersWithLog(UPDATERESULT_EVENT, transactionReceipt);
    ArrayList<UpdateResultEventResponse> responses =
        new ArrayList<UpdateResultEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      UpdateResultEventResponse typedResponse = new UpdateResultEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<UpdateResultEventResponse> updateResultEventFlowable(BcosFilter filter) {
    return web3j
        .logFlowable(filter)
        .map(
            new io.reactivex.functions.Function<Log, UpdateResultEventResponse>() {
              @Override
              public UpdateResultEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues =
                    extractEventParametersWithLog(UPDATERESULT_EVENT, log);
                UpdateResultEventResponse typedResponse = new UpdateResultEventResponse();
                typedResponse.log = log;
                typedResponse.count =
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<UpdateResultEventResponse> updateResultEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(UPDATERESULT_EVENT));
    return updateResultEventFlowable(filter);
  }

  public List<RemoveResultEventResponse> getRemoveResultEvents(
      TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList =
        extractEventParametersWithLog(REMOVERESULT_EVENT, transactionReceipt);
    ArrayList<RemoveResultEventResponse> responses =
        new ArrayList<RemoveResultEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      RemoveResultEventResponse typedResponse = new RemoveResultEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<RemoveResultEventResponse> removeResultEventFlowable(BcosFilter filter) {
    return web3j
        .logFlowable(filter)
        .map(
            new io.reactivex.functions.Function<Log, RemoveResultEventResponse>() {
              @Override
              public RemoveResultEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues =
                    extractEventParametersWithLog(REMOVERESULT_EVENT, log);
                RemoveResultEventResponse typedResponse = new RemoveResultEventResponse();
                typedResponse.log = log;
                typedResponse.count =
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<RemoveResultEventResponse> removeResultEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(REMOVERESULT_EVENT));
    return removeResultEventFlowable(filter);
  }

  public List<ReadResultEventResponse> getReadResultEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList =
        extractEventParametersWithLog(READRESULT_EVENT, transactionReceipt);
    ArrayList<ReadResultEventResponse> responses =
        new ArrayList<ReadResultEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      ReadResultEventResponse typedResponse = new ReadResultEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.name = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.item_id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
      typedResponse.item_name = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public Flowable<ReadResultEventResponse> readResultEventFlowable(BcosFilter filter) {
    return web3j
        .logFlowable(filter)
        .map(
            new io.reactivex.functions.Function<Log, ReadResultEventResponse>() {
              @Override
              public ReadResultEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues =
                    extractEventParametersWithLog(READRESULT_EVENT, log);
                ReadResultEventResponse typedResponse = new ReadResultEventResponse();
                typedResponse.log = log;
                typedResponse.name = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.item_id =
                    (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.item_name =
                    (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
              }
            });
  }

  public Flowable<ReadResultEventResponse> readResultEventFlowable(
      DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(READRESULT_EVENT));
    return readResultEventFlowable(filter);
  }

  @Deprecated
  public static DBTest load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new DBTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static DBTest load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new DBTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static DBTest load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new DBTest(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static DBTest load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new DBTest(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<DBTest> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(DBTest.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<DBTest> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(DBTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<DBTest> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        DBTest.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<DBTest> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        DBTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }

  public static class CreateResultEventResponse {
    public Log log;

    public BigInteger count;
  }

  public static class InsertResultEventResponse {
    public Log log;

    public BigInteger count;
  }

  public static class UpdateResultEventResponse {
    public Log log;

    public BigInteger count;
  }

  public static class RemoveResultEventResponse {
    public Log log;

    public BigInteger count;
  }

  public static class ReadResultEventResponse {
    public Log log;

    public byte[] name;

    public BigInteger item_id;

    public byte[] item_name;
  }
}
