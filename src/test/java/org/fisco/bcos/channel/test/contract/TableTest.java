package org.fisco.bcos.channel.test.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
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
@SuppressWarnings("unchecked")
public class TableTest extends Contract {
    private static final String BINARY =
            "608060405234801561001057600080fd5b50612239806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063487a5a1014610072578063c4f41ab31461013f578063ebf3b24f146101c6578063efc81a8c14610293578063fcd7e3c1146102be575b600080fd5b34801561007e57600080fd5b50610129600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061040c565b6040518082815260200191505060405180910390f35b34801561014b57600080fd5b506101b0600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610b02565b6040518082815260200191505060405180910390f35b3480156101d257600080fd5b5061027d600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610ffb565b6040518082815260200191505060405180910390f35b34801561029f57600080fd5b506102a861161a565b6040518082815260200191505060405180910390f35b3480156102ca57600080fd5b50610325600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506117b2565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b83811015610370578082015181840152602081019050610355565b50505050905001848103835286818151815260200191508051906020019060200280838360005b838110156103b2578082015181840152602081019050610397565b50505050905001848103825285818151815260200191508051906020019060200280838360005b838110156103f45780820151818401526020810190506103d9565b50505050905001965050505050505060405180910390f35b60008060008060008061100194508473ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156104ba57600080fd5b505af11580156104ce573d6000803e3d6000fd5b505050506040513d60208110156104e457600080fd5b810190808051906020019092919050505093508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561055b57600080fd5b505af115801561056f573d6000803e3d6000fd5b505050506040513d602081101561058557600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b516886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561065857808201518184015260208101905061063d565b50505050905090810190601f1680156106855780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156106a557600080fd5b505af11580156106b9573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561072157600080fd5b505af1158015610735573d6000803e3d6000fd5b505050506040513d602081101561074b57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d18a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561081e578082015181840152602081019050610803565b50505050905090810190601f16801561084b5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561086b57600080fd5b505af115801561087f573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561092b57600080fd5b505af115801561093f573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18a85856040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b83811015610a31578082015181840152602081019050610a16565b50505050905090810190601f168015610a5e5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b158015610a7f57600080fd5b505af1158015610a93573d6000803e3d6000fd5b505050506040513d6020811015610aa957600080fd5b810190808051906020019092919050505090507f0bdcb3b747cf033ae78b4b6e1576d2725709d03f68ad3d641b12cb72de614354816040518082815260200191505060405180910390a180955050505050509392505050565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610baf57600080fd5b505af1158015610bc3573d6000803e3d6000fd5b505050506040513d6020811015610bd957600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610c5057600080fd5b505af1158015610c64573d6000803e3d6000fd5b505050506040513d6020811015610c7a57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d1886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610d4d578082015181840152602081019050610d32565b50505050905090810190601f168015610d7a5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610d9a57600080fd5b505af1158015610dae573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610e5a57600080fd5b505af1158015610e6e573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166328bb211788846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610f2d578082015181840152602081019050610f12565b50505050905090810190601f168015610f5a5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610f7a57600080fd5b505af1158015610f8e573d6000803e3d6000fd5b505050506040513d6020811015610fa457600080fd5b810190808051906020019092919050505090507f896358cb98e9e8e891ae04efd1bc177efbe5cffd7eca2e784b16ed7468553e08816040518082815260200191505060405180910390a18094505050505092915050565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156110a857600080fd5b505af11580156110bc573d6000803e3d6000fd5b505050506040513d60208110156110d257600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561114957600080fd5b505af115801561115d573d6000803e3d6000fd5b505050506040513d602081101561117357600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b516896040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561124657808201518184015260208101905061122b565b50505050905090810190601f1680156112735780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561129357600080fd5b505af11580156112a7573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74886040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561135357600080fd5b505af1158015611367573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561142b578082015181840152602081019050611410565b50505050905090810190601f1680156114585780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561147857600080fd5b505af115801561148c573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3689846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b8381101561154b578082015181840152602081019050611530565b50505050905090810190601f1680156115785780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561159857600080fd5b505af11580156115ac573d6000803e3d6000fd5b505050506040513d60208110156115c257600080fd5b810190808051906020019092919050505090507f66f7705280112a4d1145399e0414adc43a2d6974b487710f417edcf7d4a39d71816040518082815260200191505060405180910390a1809450505050509392505050565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001848103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001848103825260118152602001807f6974656d5f69642c6974656d5f6e616d650000000000000000000000000000008152506020019350505050602060405180830381600087803b15801561173657600080fd5b505af115801561174a573d6000803e3d6000fd5b505050506040513d602081101561176057600080fd5b810190808051906020019092919050505090507fcd4779437d9d027acc605a96427bfbd3787a1402cb53a5e64cd813d5391fbc2b816040518082815260200191505060405180910390a1809250505090565b6060806060600080600080606080606060008061100198508873ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561186a57600080fd5b505af115801561187e573d6000803e3d6000fd5b505050506040513d602081101561189457600080fd5b810190808051906020019092919050505097508773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561190b57600080fd5b505af115801561191f573d6000803e3d6000fd5b505050506040513d602081101561193557600080fd5b810190808051906020019092919050505096508773ffffffffffffffffffffffffffffffffffffffff1663e8434e398e896040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611a035780820151818401526020810190506119e8565b50505050905090810190601f168015611a305780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015611a5057600080fd5b505af1158015611a64573d6000803e3d6000fd5b505050506040513d6020811015611a7a57600080fd5b810190808051906020019092919050505095508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611af157600080fd5b505af1158015611b05573d6000803e3d6000fd5b505050506040513d6020811015611b1b57600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015611b5a5781602001602082028038833980820191505090505b5094508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611bc157600080fd5b505af1158015611bd5573d6000803e3d6000fd5b505050506040513d6020811015611beb57600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015611c2a5781602001602082028038833980820191505090505b5093508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611c9157600080fd5b505af1158015611ca5573d6000803e3d6000fd5b505050506040513d6020811015611cbb57600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015611cfa5781602001602082028038833980820191505090505b509250600091505b8573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611d6657600080fd5b505af1158015611d7a573d6000803e3d6000fd5b505050506040513d6020811015611d9057600080fd5b81019080805190602001909291905050508212156121f4578573ffffffffffffffffffffffffffffffffffffffff1663846719e0836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b158015611e1757600080fd5b505af1158015611e2b573d6000803e3d6000fd5b505050506040513d6020811015611e4157600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611ef457600080fd5b505af1158015611f08573d6000803e3d6000fd5b505050506040513d6020811015611f1e57600080fd5b81019080805190602001909291905050508583815181101515611f3d57fe5b9060200190602002019060001916908160001916815250508073ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f6974656d5f696400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611ff557600080fd5b505af1158015612009573d6000803e3d6000fd5b505050506040513d602081101561201f57600080fd5b8101908080519060200190929190505050848381518110151561203e57fe5b90602001906020020181815250508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156120ec57600080fd5b505af1158015612100573d6000803e3d6000fd5b505050506040513d602081101561211657600080fd5b8101908080519060200190929190505050838381518110151561213557fe5b9060200190602002019060001916908160001916815250507fc65cd2adf133adee2ddcfab8b165c2f1f7b185c4389b0789a11112483efb1c84858381518110151561217c57fe5b90602001906020020151858481518110151561219457fe5b9060200190602002015185858151811015156121ac57fe5b906020019060200201516040518084600019166000191681526020018381526020018260001916600019168152602001935050505060405180910390a1816001019150611d02565b8484849b509b509b5050505050505050505091939092505600a165627a7a723058201ab472e3141e7db3e2203297190547ca2e10b6b55a96dc8278b4a88a2ff72a700029";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_INSERT = "insert";

    public static final String FUNC_CREATE = "create";

    public static final String FUNC_SELECT = "select";

    public static final Event CREATERESULT_EVENT =
            new Event(
                    "createResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

    public static final Event SELECTRESULT_EVENT =
            new Event(
                    "selectResult",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Bytes32>() {},
                            new TypeReference<Int256>() {},
                            new TypeReference<Bytes32>() {}));;

    public static final Event INSERTRESULT_EVENT =
            new Event(
                    "insertResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

    public static final Event UPDATERESULT_EVENT =
            new Event(
                    "updateResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

    public static final Event REMOVERESULT_EVENT =
            new Event(
                    "removeResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

    @Deprecated
    protected TableTest(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TableTest(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TableTest(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TableTest(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> update(
            String name, BigInteger item_id, String item_name) {
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

    public void update(
            String name, BigInteger item_id, String item_name, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_UPDATE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(item_name)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
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

    public void remove(String name, BigInteger item_id, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_REMOVE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> insert(
            String name, BigInteger item_id, String item_name) {
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

    public void insert(
            String name, BigInteger item_id, String item_name, TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_INSERT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(item_name)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<TransactionReceipt> create() {
        final Function function =
                new Function(
                        FUNC_CREATE,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void create(TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_CREATE,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public RemoteCall<Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>> select(String name) {
        final Function function =
                new Function(
                        FUNC_SELECT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Bytes32>>() {},
                                new TypeReference<DynamicArray<Int256>>() {},
                                new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>>(
                new Callable<Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>>() {
                    @Override
                    public Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>(
                                convertToNative((List<Bytes32>) results.get(0).getValue()),
                                convertToNative((List<Int256>) results.get(1).getValue()),
                                convertToNative((List<Bytes32>) results.get(2).getValue()));
                    }
                });
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

    public List<SelectResultEventResponse> getSelectResultEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(SELECTRESULT_EVENT, transactionReceipt);
        ArrayList<SelectResultEventResponse> responses =
                new ArrayList<SelectResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SelectResultEventResponse typedResponse = new SelectResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.item_id =
                    (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.item_name = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
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

    @Deprecated
    public static TableTest load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new TableTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TableTest load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new TableTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TableTest load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new TableTest(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TableTest load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new TableTest(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TableTest> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                TableTest.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TableTest> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                TableTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TableTest> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                TableTest.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TableTest> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                TableTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CreateResultEventResponse {
        public Log log;

        public BigInteger count;
    }

    public static class SelectResultEventResponse {
        public Log log;

        public byte[] name;

        public BigInteger item_id;

        public byte[] item_name;
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
}
