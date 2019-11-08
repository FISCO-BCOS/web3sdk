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
public class MixContract extends Contract {
    private static final String BINARY =
            "608060405234801561001057600080fd5b506121de806100206000396000f300608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063487a5a101461007d578063488bdabc1461014a578063616ffe8314610175578063c4f41ab3146102c3578063ebf3b24f1461034a578063efc81a8c14610417575b600080fd5b34801561008957600080fd5b50610134600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061042e565b6040518082815260200191505060405180910390f35b34801561015657600080fd5b5061015f610b24565b6040518082815260200191505060405180910390f35b34801561018157600080fd5b506101dc600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610b2a565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b8381101561022757808201518184015260208101905061020c565b50505050905001848103835286818151815260200191508051906020019060200280838360005b8381101561026957808201518184015260208101905061024e565b50505050905001848103825285818151815260200191508051906020019060200280838360005b838110156102ab578082015181840152602081019050610290565b50505050905001965050505050505060405180910390f35b3480156102cf57600080fd5b50610334600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506114e9565b6040518082815260200191505060405180910390f35b34801561035657600080fd5b50610401600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506119f2565b6040518082815260200191505060405180910390f35b34801561042357600080fd5b5061042c612020565b005b60008060008060008061100194508473ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f64656d6f0000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156104dc57600080fd5b505af11580156104f0573d6000803e3d6000fd5b505050506040513d602081101561050657600080fd5b810190808051906020019092919050505093508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561057d57600080fd5b505af1158015610591573d6000803e3d6000fd5b505050506040513d60208110156105a757600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b516886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561067a57808201518184015260208101905061065f565b50505050905090810190601f1680156106a75780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156106c757600080fd5b505af11580156106db573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561074357600080fd5b505af1158015610757573d6000803e3d6000fd5b505050506040513d602081101561076d57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d18a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610840578082015181840152602081019050610825565b50505050905090810190601f16801561086d5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561088d57600080fd5b505af11580156108a1573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561094d57600080fd5b505af1158015610961573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18a85856040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b83811015610a53578082015181840152602081019050610a38565b50505050905090810190601f168015610a805780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b158015610aa157600080fd5b505af1158015610ab5573d6000803e3d6000fd5b505050506040513d6020811015610acb57600080fd5b810190808051906020019092919050505090507f0bdcb3b747cf033ae78b4b6e1576d2725709d03f68ad3d641b12cb72de614354816040518082815260200191505060405180910390a180955050505050509392505050565b60005481565b6060806060600080600080606080606060008061100198508873ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f64656d6f0000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610be257600080fd5b505af1158015610bf6573d6000803e3d6000fd5b505050506040513d6020811015610c0c57600080fd5b810190808051906020019092919050505097508773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610c8357600080fd5b505af1158015610c97573d6000803e3d6000fd5b505050506040513d6020811015610cad57600080fd5b810190808051906020019092919050505096508773ffffffffffffffffffffffffffffffffffffffff1663e8434e398e896040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610d7b578082015181840152602081019050610d60565b50505050905090810190601f168015610da85780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610dc857600080fd5b505af1158015610ddc573d6000803e3d6000fd5b505050506040513d6020811015610df257600080fd5b810190808051906020019092919050505095508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610e6957600080fd5b505af1158015610e7d573d6000803e3d6000fd5b505050506040513d6020811015610e9357600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015610ed25781602001602082028038833980820191505090505b5094508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610f3957600080fd5b505af1158015610f4d573d6000803e3d6000fd5b505050506040513d6020811015610f6357600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015610fa25781602001602082028038833980820191505090505b5093508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561100957600080fd5b505af115801561101d573d6000803e3d6000fd5b505050506040513d602081101561103357600080fd5b81019080805190602001909291905050506040519080825280602002602001820160405280156110725781602001602082028038833980820191505090505b509250600091505b8573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156110de57600080fd5b505af11580156110f2573d6000803e3d6000fd5b505050506040513d602081101561110857600080fd5b81019080805190602001909291905050508212156114d0578573ffffffffffffffffffffffffffffffffffffffff1663846719e0836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561118f57600080fd5b505af11580156111a3573d6000803e3d6000fd5b505050506040513d60208110156111b957600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561126c57600080fd5b505af1158015611280573d6000803e3d6000fd5b505050506040513d602081101561129657600080fd5b810190808051906020019092919050505085838151811015156112b557fe5b9060200190602002019060001916908160001916815250508073ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f6974656d5f696400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561136d57600080fd5b505af1158015611381573d6000803e3d6000fd5b505050506040513d602081101561139757600080fd5b810190808051906020019092919050505084838151811015156113b657fe5b90602001906020020181815250508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561146457600080fd5b505af1158015611478573d6000803e3d6000fd5b505050506040513d602081101561148e57600080fd5b810190808051906020019092919050505083838151811015156114ad57fe5b90602001906020020190600019169081600019168152505081600101915061107a565b8484849b509b509b505050505050505050509193909250565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f64656d6f0000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561159657600080fd5b505af11580156115aa573d6000803e3d6000fd5b505050506040513d60208110156115c057600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561163757600080fd5b505af115801561164b573d6000803e3d6000fd5b505050506040513d602081101561166157600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d1886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611734578082015181840152602081019050611719565b50505050905090810190601f1680156117615780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561178157600080fd5b505af1158015611795573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561184157600080fd5b505af1158015611855573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166328bb211788846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156119145780820151818401526020810190506118f9565b50505050905090810190601f1680156119415780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561196157600080fd5b505af1158015611975573d6000803e3d6000fd5b505050506040513d602081101561198b57600080fd5b81019080805190602001909291905050509050600080815460019003919050819055507f896358cb98e9e8e891ae04efd1bc177efbe5cffd7eca2e784b16ed7468553e08816040518082815260200191505060405180910390a18094505050505092915050565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f64656d6f0000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611a9f57600080fd5b505af1158015611ab3573d6000803e3d6000fd5b505050506040513d6020811015611ac957600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611b4057600080fd5b505af1158015611b54573d6000803e3d6000fd5b505050506040513d6020811015611b6a57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b516896040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611c3d578082015181840152602081019050611c22565b50505050905090810190601f168015611c6a5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015611c8a57600080fd5b505af1158015611c9e573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74886040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015611d4a57600080fd5b505af1158015611d5e573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611e22578082015181840152602081019050611e07565b50505050905090810190601f168015611e4f5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015611e6f57600080fd5b505af1158015611e83573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3689846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611f42578082015181840152602081019050611f27565b50505050905090810190601f168015611f6f5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015611f8f57600080fd5b505af1158015611fa3573d6000803e3d6000fd5b505050506040513d6020811015611fb957600080fd5b810190808051906020019092919050505090507f66f7705280112a4d1145399e0414adc43a2d6974b487710f417edcf7d4a39d71816040518082815260200191505060405180910390a1600080815460010191905081905550809450505050509392505050565b60008061100191508173ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260068152602001807f745f64656d6f0000000000000000000000000000000000000000000000000000815250602001848103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001848103825260118152602001807f6974656d5f69642c6974656d5f6e616d650000000000000000000000000000008152506020019350505050602060405180830381600087803b15801561213a57600080fd5b505af115801561214e573d6000803e3d6000fd5b505050506040513d602081101561216457600080fd5b810190808051906020019092919050505090507fcd4779437d9d027acc605a96427bfbd3787a1402cb53a5e64cd813d5391fbc2b816040518082815260200191505060405180910390a150505600a165627a7a72305820f74c6a35abc1f8fd480188a4b3caee48854d7cff40ded5821661707924b1809c0029";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_TOTALKEYS = "totalKeys";

    public static final String FUNC_READ = "read";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_INSERT = "insert";

    public static final String FUNC_CREATE = "create";

    public static final Event CREATERESULT_EVENT =
            new Event(
                    "createResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

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

    public static final Event READRESULT_EVENT =
            new Event(
                    "readResult",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Bytes32>() {},
                            new TypeReference<Int256>() {},
                            new TypeReference<Bytes32>() {}));;

    @Deprecated
    protected MixContract(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MixContract(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MixContract(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MixContract(
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

    public RemoteCall<BigInteger> totalKeys() {
        final Function function =
                new Function(
                        FUNC_TOTALKEYS,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>>> read(String name) {
        final Function function =
                new Function(
                        FUNC_READ,
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

    public List<ReadResultEventResponse> getReadResultEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(READRESULT_EVENT, transactionReceipt);
        ArrayList<ReadResultEventResponse> responses =
                new ArrayList<ReadResultEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReadResultEventResponse typedResponse = new ReadResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.item_id =
                    (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.item_name = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    @Deprecated
    public static MixContract load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new MixContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MixContract load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new MixContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MixContract load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new MixContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MixContract load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new MixContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MixContract> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                MixContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MixContract> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                MixContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<MixContract> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                MixContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MixContract> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                MixContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
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
