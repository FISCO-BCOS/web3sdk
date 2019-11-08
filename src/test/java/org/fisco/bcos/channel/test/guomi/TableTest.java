package org.fisco.bcos.channel.test.guomi;

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
    public static final String BINARY =
            "608060405234801561001057600080fd5b5061221f806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630fe1160f1461007257806345710fa5146100f957806349cc36b5146101105780635b325d78146101dd578063e020d4641461032b575b600080fd5b34801561007e57600080fd5b506100e3600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506103f8565b6040518082815260200191505060405180910390f35b34801561010557600080fd5b5061010e6108f1565b005b34801561011c57600080fd5b506101c7600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610a83565b6040518082815260200191505060405180910390f35b3480156101e957600080fd5b50610244600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050611179565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b8381101561028f578082015181840152602081019050610274565b50505050905001848103835286818151815260200191508051906020019060200280838360005b838110156102d15780820151818401526020810190506102b6565b50505050905001848103825285818151815260200191508051906020019060200280838360005b838110156103135780820151818401526020810190506102f8565b50505050905001965050505050505060405180910390f35b34801561033757600080fd5b506103e2600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050611bd4565b6040518082815260200191505060405180910390f35b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff166359a48b656040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156104a557600080fd5b505af11580156104b9573d6000803e3d6000fd5b505050506040513d60208110156104cf57600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561054657600080fd5b505af115801561055a573d6000803e3d6000fd5b505050506040513d602081101561057057600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663ae763db5886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610643578082015181840152602081019050610628565b50505050905090810190601f1680156106705780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561069057600080fd5b505af11580156106a4573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663d62b54b4876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561075057600080fd5b505af1158015610764573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166309ff42f088846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610823578082015181840152602081019050610808565b50505050905090810190601f1680156108505780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561087057600080fd5b505af1158015610884573d6000803e3d6000fd5b505050506040513d602081101561089a57600080fd5b810190808051906020019092919050505090507f809ffa7913d4c04a8785eea307a714cf83228bb7eded9cebd577c114e36c9967816040518082815260200191505060405180910390a18094505050505092915050565b60008061100191508173ffffffffffffffffffffffffffffffffffffffff1663c92a78016040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001848103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001848103825260118152602001807f6974656d5f69642c6974656d5f6e616d650000000000000000000000000000008152506020019350505050602060405180830381600087803b158015610a0b57600080fd5b505af1158015610a1f573d6000803e3d6000fd5b505050506040513d6020811015610a3557600080fd5b810190808051906020019092919050505090507f698cf490d4172e8c174ef6380602ab47c18d429938f9f778cc2c0f3b5498f2c6816040518082815260200191505060405180910390a15050565b60008060008060008061100194508473ffffffffffffffffffffffffffffffffffffffff166359a48b656040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610b3157600080fd5b505af1158015610b45573d6000803e3d6000fd5b505050506040513d6020811015610b5b57600080fd5b810190808051906020019092919050505093508373ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610bd257600080fd5b505af1158015610be6573d6000803e3d6000fd5b505050506040513d6020811015610bfc57600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff16631a391cb4886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610ccf578082015181840152602081019050610cb4565b50505050905090810190601f168015610cfc5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610d1c57600080fd5b505af1158015610d30573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610d9857600080fd5b505af1158015610dac573d6000803e3d6000fd5b505050506040513d6020811015610dc257600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663ae763db58a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610e95578082015181840152602081019050610e7a565b50505050905090810190601f168015610ec25780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610ee257600080fd5b505af1158015610ef6573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663d62b54b4896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610fa257600080fd5b505af1158015610fb6573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663664b37d68a85856040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b838110156110a857808201518184015260208101905061108d565b50505050905090810190601f1680156110d55780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b1580156110f657600080fd5b505af115801561110a573d6000803e3d6000fd5b505050506040513d602081101561112057600080fd5b810190808051906020019092919050505090507f21c0ede88315971cad0fa2aa5d177bf992894f6be25236454587141c48683046816040518082815260200191505060405180910390a180955050505050509392505050565b6060806060600080600080606080606060008061100198508873ffffffffffffffffffffffffffffffffffffffff166359a48b656040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561123157600080fd5b505af1158015611245573d6000803e3d6000fd5b505050506040513d602081101561125b57600080fd5b810190808051906020019092919050505097508773ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156112d257600080fd5b505af11580156112e6573d6000803e3d6000fd5b505050506040513d60208110156112fc57600080fd5b810190808051906020019092919050505096508773ffffffffffffffffffffffffffffffffffffffff1663d8ac59578e896040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156113ca5780820151818401526020810190506113af565b50505050905090810190601f1680156113f75780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561141757600080fd5b505af115801561142b573d6000803e3d6000fd5b505050506040513d602081101561144157600080fd5b810190808051906020019092919050505095508573ffffffffffffffffffffffffffffffffffffffff1663d3e9af5a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156114b857600080fd5b505af11580156114cc573d6000803e3d6000fd5b505050506040513d60208110156114e257600080fd5b81019080805190602001909291905050506040519080825280602002602001820160405280156115215781602001602082028038833980820191505090505b5094508573ffffffffffffffffffffffffffffffffffffffff1663d3e9af5a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561158857600080fd5b505af115801561159c573d6000803e3d6000fd5b505050506040513d60208110156115b257600080fd5b81019080805190602001909291905050506040519080825280602002602001820160405280156115f15781602001602082028038833980820191505090505b5093508573ffffffffffffffffffffffffffffffffffffffff1663d3e9af5a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561165857600080fd5b505af115801561166c573d6000803e3d6000fd5b505050506040513d602081101561168257600080fd5b81019080805190602001909291905050506040519080825280602002602001820160405280156116c15781602001602082028038833980820191505090505b509250600091505b8573ffffffffffffffffffffffffffffffffffffffff1663d3e9af5a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561172d57600080fd5b505af1158015611741573d6000803e3d6000fd5b505050506040513d602081101561175757600080fd5b8101908080519060200190929190505050821215611bbb578573ffffffffffffffffffffffffffffffffffffffff16633dd2b614836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b1580156117de57600080fd5b505af11580156117f2573d6000803e3d6000fd5b505050506040513d602081101561180857600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff1663fdebe4146040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156118bb57600080fd5b505af11580156118cf573d6000803e3d6000fd5b505050506040513d60208110156118e557600080fd5b8101908080519060200190929190505050858381518110151561190457fe5b9060200190602002019060001916908160001916815250508073ffffffffffffffffffffffffffffffffffffffff16634900862e6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f6974656d5f696400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156119bc57600080fd5b505af11580156119d0573d6000803e3d6000fd5b505050506040513d60208110156119e657600080fd5b81019080805190602001909291905050508483815181101515611a0557fe5b90602001906020020181815250508073ffffffffffffffffffffffffffffffffffffffff1663fdebe4146040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611ab357600080fd5b505af1158015611ac7573d6000803e3d6000fd5b505050506040513d6020811015611add57600080fd5b81019080805190602001909291905050508383815181101515611afc57fe5b9060200190602002019060001916908160001916815250507fef677d3bedeedc56f98504970ca9c69a69871a4cf5d7abee1012f075f3d064888583815181101515611b4357fe5b906020019060200201518584815181101515611b5b57fe5b906020019060200201518585815181101515611b7357fe5b906020019060200201516040518084600019166000191681526020018381526020018260001916600019168152602001935050505060405180910390a18160010191506116c9565b8484849b509b509b505050505050505050509193909250565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff166359a48b656040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611c8157600080fd5b505af1158015611c95573d6000803e3d6000fd5b505050506040513d6020811015611cab57600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611d2257600080fd5b505af1158015611d36573d6000803e3d6000fd5b505050506040513d6020811015611d4c57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff16631a391cb4896040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611e1f578082015181840152602081019050611e04565b50505050905090810190601f168015611e4c5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015611e6c57600080fd5b505af1158015611e80573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663def42698886040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015611f2c57600080fd5b505af1158015611f40573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16631a391cb4876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015612004578082015181840152602081019050611fe9565b50505050905090810190601f1680156120315780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561205157600080fd5b505af1158015612065573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff16634c6f30c089846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015612124578082015181840152602081019050612109565b50505050905090810190601f1680156121515780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561217157600080fd5b505af1158015612185573d6000803e3d6000fd5b505050506040513d602081101561219b57600080fd5b810190808051906020019092919050505090507f11edf97b45aa6c006853fb598a4a9be2e678d9498feb5e6c1f389b491e12bc4a816040518082815260200191505060405180910390a18094505050505093925050505600a165627a7a72305820d6b4b0b7c65f67babb5de4e65b764f12472c78217fe8de23a001f110c820c87f0029";

    public static final String ABI =
            "[{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"}],\"name\":\"remove\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"create\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"update\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"insert\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"createResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"name\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"item_id\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"item_name\",\"type\":\"bytes32\"}],\"name\":\"selectResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"insertResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"updateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"removeResult\",\"type\":\"event\"}]";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_CREATE = "create";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_SELECT = "select";

    public static final String FUNC_INSERT = "insert";

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

    public String removeSeq(String name, BigInteger item_id) {
        final Function function =
                new Function(
                        FUNC_REMOVE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String createSeq() {
        final Function function =
                new Function(
                        FUNC_CREATE,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String updateSeq(String name, BigInteger item_id, String item_name) {
        final Function function =
                new Function(
                        FUNC_UPDATE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(item_name)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public String insertSeq(String name, BigInteger item_id, String item_name) {
        final Function function =
                new Function(
                        FUNC_INSERT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(item_id),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(item_name)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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
