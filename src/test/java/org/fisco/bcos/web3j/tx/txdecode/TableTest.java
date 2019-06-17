package org.fisco.bcos.web3j.tx.txdecode;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8;
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
@SuppressWarnings("unchecked")
public class TableTest extends Contract {
    private static final String BINARY =
            "608060405234801561001057600080fd5b50612520806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063487a5a1014610072578063c4f41ab31461013f578063ebf3b24f146101c6578063efc81a8c14610389578063fcd7e3c1146103b4575b600080fd5b34801561007e57600080fd5b50610129600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610502565b6040518082815260200191505060405180910390f35b34801561014b57600080fd5b506101b0600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610c31565b6040518082815260200191505060405180910390f35b3480156101d257600080fd5b5061027d600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061112a565b604051808681526020018060200185600019166000191681526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838103835287818151815260200191508051906020019080838360005b838110156103075780820151818401526020810190506102ec565b50505050905090810190601f1680156103345780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019060200280838360005b83811015610370578082015181840152602081019050610355565b5050505090500197505050505050505060405180910390f35b34801561039557600080fd5b5061039e61199d565b6040518082815260200191505060405180910390f35b3480156103c057600080fd5b5061041b600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050611b35565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b8381101561046657808201518184015260208101905061044b565b50505050905001848103835286818151815260200191508051906020019060200280838360005b838110156104a857808201518184015260208101905061048d565b50505050905001848103825285818151815260200191508051906020019060200280838360005b838110156104ea5780820151818401526020810190506104cf565b50505050905001965050505050505060405180910390f35b60008060008060008061100194508473ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156105b057600080fd5b505af11580156105c4573d6000803e3d6000fd5b505050506040513d60208110156105da57600080fd5b810190808051906020019092919050505093508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561065157600080fd5b505af1158015610665573d6000803e3d6000fd5b505050506040513d602081101561067b57600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b516886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561074e578082015181840152602081019050610733565b50505050905090810190601f16801561077b5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561079b57600080fd5b505af11580156107af573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561081757600080fd5b505af115801561082b573d6000803e3d6000fd5b505050506040513d602081101561084157600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d18a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156109145780820151818401526020810190506108f9565b50505050905090810190601f1680156109415780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561096157600080fd5b505af1158015610975573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610a2157600080fd5b505af1158015610a35573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18a85856040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b83811015610b27578082015181840152602081019050610b0c565b50505050905090810190601f168015610b545780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b158015610b7557600080fd5b505af1158015610b89573d6000803e3d6000fd5b505050506040513d6020811015610b9f57600080fd5b810190808051906020019092919050505090507fc64839fb54045aee80d8eb25314a07a53c06502ed16295c9dea594cefea4b757816040518082815260200180602001828103825260058152602001807f66727569740000000000000000000000000000000000000000000000000000008152506020019250505060405180910390a180955050505050509392505050565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610cde57600080fd5b505af1158015610cf2573d6000803e3d6000fd5b505050506040513d6020811015610d0857600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610d7f57600080fd5b505af1158015610d93573d6000803e3d6000fd5b505050506040513d6020811015610da957600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d1886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610e7c578082015181840152602081019050610e61565b50505050905090810190601f168015610ea95780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610ec957600080fd5b505af1158015610edd573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610f8957600080fd5b505af1158015610f9d573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166328bb211788846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b8381101561105c578082015181840152602081019050611041565b50505050905090810190601f1680156110895780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b1580156110a957600080fd5b505af11580156110bd573d6000803e3d6000fd5b505050506040513d60208110156110d357600080fd5b810190808051906020019092919050505090507f4b930e280fe29620bdff00c88155d46d6d82a39f45dd5c3ea114dc3157358112816040518082815260200191505060405180910390a18094505050505092915050565b600060606000806060600080600080606061100194508473ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156111e057600080fd5b505af11580156111f4573d6000803e3d6000fd5b505050506040513d602081101561120a57600080fd5b810190808051906020019092919050505093508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561128157600080fd5b505af1158015611295573d6000803e3d6000fd5b505050506040513d60208110156112ab57600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b5168e6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b8381101561137e578082015181840152602081019050611363565b50505050905090810190601f1680156113ab5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156113cb57600080fd5b505af11580156113df573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff16632ef8ba748d6040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260078152602001807f6974656d5f69640000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561148b57600080fd5b505af115801561149f573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff1663e942b5168c6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611563578082015181840152602081019050611548565b50505050905090810190601f1680156115905780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b1580156115b057600080fd5b505af11580156115c4573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff166331afac368e856040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611683578082015181840152602081019050611668565b50505050905090810190601f1680156116b05780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b1580156116d057600080fd5b505af11580156116e4573d6000803e3d6000fd5b505050506040513d60208110156116fa57600080fd5b81019080805190602001909291905050509150600260405190808252806020026020018201604052801561173d5781602001602082028038833980820191505090505b5090507f666973636f00000000000000000000000000000000000000000000000000000081600081518110151561177057fe5b9060200190602002019060001916908160001916815250507f62636f73000000000000000000000000000000000000000000000000000000008160018151811015156117b857fe5b9060200190602002019060001916908160001916815250507f431a036d6859d1e4575941e93c7ed24ff4a13c1f3db0f879becd1992f650451382826040518083815260200180602001807f6170706c6500000000000000000000000000000000000000000000000000000081525060200180602001838103835260058152602001807f6672756974000000000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019060200280838360005b8381101561189c578082015181840152602081019050611881565b5050505090500194505050505060405180910390a17fc64839fb54045aee80d8eb25314a07a53c06502ed16295c9dea594cefea4b757826040518082815260200180602001828103825260058152602001807f66727569740000000000000000000000000000000000000000000000000000008152506020019250505060405180910390a1816012826040805190810160405280600581526020017f68656c6c6f00000000000000000000000000000000000000000000000000000081525091907f776f726c640000000000000000000000000000000000000000000000000000009190819150995099509950995099505050505050939792965093509350565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001848103835260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001848103825260118152602001807f6974656d5f69642c6974656d5f6e616d650000000000000000000000000000008152506020019350505050602060405180830381600087803b158015611ab957600080fd5b505af1158015611acd573d6000803e3d6000fd5b505050506040513d6020811015611ae357600080fd5b810190808051906020019092919050505090507fb5636cd912a73dcdb5b570dbe331dfa3e6435c93e029e642def2c8e40dacf210816040518082815260200191505060405180910390a1809250505090565b6060806060600080600080606080606060008061100198508873ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260068152602001807f745f746573740000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015611bed57600080fd5b505af1158015611c01573d6000803e3d6000fd5b505050506040513d6020811015611c1757600080fd5b810190808051906020019092919050505097508773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611c8e57600080fd5b505af1158015611ca2573d6000803e3d6000fd5b505050506040513d6020811015611cb857600080fd5b810190808051906020019092919050505096508773ffffffffffffffffffffffffffffffffffffffff1663e8434e398e896040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015611d86578082015181840152602081019050611d6b565b50505050905090810190601f168015611db35780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015611dd357600080fd5b505af1158015611de7573d6000803e3d6000fd5b505050506040513d6020811015611dfd57600080fd5b810190808051906020019092919050505095508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611e7457600080fd5b505af1158015611e88573d6000803e3d6000fd5b505050506040513d6020811015611e9e57600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015611edd5781602001602082028038833980820191505090505b5094508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611f4457600080fd5b505af1158015611f58573d6000803e3d6000fd5b505050506040513d6020811015611f6e57600080fd5b8101908080519060200190929190505050604051908082528060200260200182016040528015611fad5781602001602082028038833980820191505090505b5093508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561201457600080fd5b505af1158015612028573d6000803e3d6000fd5b505050506040513d602081101561203e57600080fd5b810190808051906020019092919050505060405190808252806020026020018201604052801561207d5781602001602082028038833980820191505090505b509250600091505b8573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156120e957600080fd5b505af11580156120fd573d6000803e3d6000fd5b505050506040513d602081101561211357600080fd5b81019080805190602001909291905050508212156124db578573ffffffffffffffffffffffffffffffffffffffff1663846719e0836040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561219a57600080fd5b505af11580156121ae573d6000803e3d6000fd5b505050506040513d60208110156121c457600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6e616d6500000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561227757600080fd5b505af115801561228b573d6000803e3d6000fd5b505050506040513d60208110156122a157600080fd5b810190808051906020019092919050505085838151811015156122c057fe5b9060200190602002019060001916908160001916815250508073ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f6974656d5f696400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561237857600080fd5b505af115801561238c573d6000803e3d6000fd5b505050506040513d60208110156123a257600080fd5b810190808051906020019092919050505084838151811015156123c157fe5b90602001906020020181815250508073ffffffffffffffffffffffffffffffffffffffff166327314f796040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f6974656d5f6e616d650000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561246f57600080fd5b505af1158015612483573d6000803e3d6000fd5b505050506040513d602081101561249957600080fd5b810190808051906020019092919050505083838151811015156124b857fe5b906020019060200201906000191690816000191681525050816001019150612085565b8484849b509b509b5050505050505050505091939092505600a165627a7a723058206c7a11534a77a967c1f4028d86e31d459996fa0e63335529ad181e35af916aa10029";

    public static final String ABI =
            "[{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"update\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"}],\"name\":\"remove\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"insert\",\"outputs\":[{\"name\":\"result\",\"type\":\"int256\"},{\"name\":\"result_name\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"addr\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32[]\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"create\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"CreateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"item_name\",\"type\":\"bytes8\"},{\"indexed\":false,\"name\":\"item_id\",\"type\":\"bytes32[]\"}],\"name\":\"InsertResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"UpdateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"RemoveResult\",\"type\":\"event\"}]";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_INSERT = "insert";

    public static final String FUNC_CREATE = "create";

    public static final String FUNC_SELECT = "select";

    public static final Event CREATERESULT_EVENT =
            new Event(
                    "CreateResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

    public static final Event INSERTRESULT_EVENT =
            new Event(
                    "InsertResult",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Int256>() {},
                            new TypeReference<Utf8String>() {},
                            new TypeReference<Bytes8>() {},
                            new TypeReference<DynamicArray<Bytes32>>() {}));;

    public static final Event UPDATERESULT_EVENT =
            new Event(
                    "UpdateResult",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}));;

    public static final Event REMOVERESULT_EVENT =
            new Event(
                    "RemoveResult",
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

    public Flowable<CreateResultEventResponse> createResultEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter)
                .map(
                        new io.reactivex.functions.Function<Log, CreateResultEventResponse>() {
                            @Override
                            public CreateResultEventResponse apply(Log log) {
                                Contract.EventValuesWithLog eventValues =
                                        extractEventParametersWithLog(CREATERESULT_EVENT, log);
                                CreateResultEventResponse typedResponse =
                                        new CreateResultEventResponse();
                                typedResponse.log = log;
                                typedResponse.count =
                                        (BigInteger)
                                                eventValues.getNonIndexedValues().get(0).getValue();
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
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.item_name = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.item_id =
                    (List<byte[]>) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<InsertResultEventResponse> insertResultEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter)
                .map(
                        new io.reactivex.functions.Function<Log, InsertResultEventResponse>() {
                            @Override
                            public InsertResultEventResponse apply(Log log) {
                                Contract.EventValuesWithLog eventValues =
                                        extractEventParametersWithLog(INSERTRESULT_EVENT, log);
                                InsertResultEventResponse typedResponse =
                                        new InsertResultEventResponse();
                                typedResponse.log = log;
                                typedResponse.count =
                                        (BigInteger)
                                                eventValues.getNonIndexedValues().get(0).getValue();
                                typedResponse.name =
                                        (String)
                                                eventValues.getNonIndexedValues().get(1).getValue();
                                typedResponse.item_name =
                                        (byte[])
                                                eventValues.getNonIndexedValues().get(2).getValue();
                                typedResponse.item_id =
                                        (List<byte[]>)
                                                eventValues.getNonIndexedValues().get(3).getValue();
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
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateResultEventResponse> updateResultEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter)
                .map(
                        new io.reactivex.functions.Function<Log, UpdateResultEventResponse>() {
                            @Override
                            public UpdateResultEventResponse apply(Log log) {
                                Contract.EventValuesWithLog eventValues =
                                        extractEventParametersWithLog(UPDATERESULT_EVENT, log);
                                UpdateResultEventResponse typedResponse =
                                        new UpdateResultEventResponse();
                                typedResponse.log = log;
                                typedResponse.count =
                                        (BigInteger)
                                                eventValues.getNonIndexedValues().get(0).getValue();
                                typedResponse.name =
                                        (String)
                                                eventValues.getNonIndexedValues().get(1).getValue();
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
        return web3j.logFlowable(filter)
                .map(
                        new io.reactivex.functions.Function<Log, RemoveResultEventResponse>() {
                            @Override
                            public RemoveResultEventResponse apply(Log log) {
                                Contract.EventValuesWithLog eventValues =
                                        extractEventParametersWithLog(REMOVERESULT_EVENT, log);
                                RemoveResultEventResponse typedResponse =
                                        new RemoveResultEventResponse();
                                typedResponse.log = log;
                                typedResponse.count =
                                        (BigInteger)
                                                eventValues.getNonIndexedValues().get(0).getValue();
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

    public static class InsertResultEventResponse {
        public Log log;

        public BigInteger count;

        public String name;

        public byte[] item_name;

        public List<byte[]> item_id;
    }

    public static class UpdateResultEventResponse {
        public Log log;

        public BigInteger count;

        public String name;
    }

    public static class RemoveResultEventResponse {
        public Log log;

        public BigInteger count;
    }
}
