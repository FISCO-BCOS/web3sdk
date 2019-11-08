package org.fisco.bcos.web3j.tx.txdecode;

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
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
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
            "608060405234801561001057600080fd5b5061205f806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063487a5a1014610072578063c4f41ab3146100af578063ebf3b24f146100ec578063efc81a8c14610129578063fcd7e3c114610154575b600080fd5b34801561007e57600080fd5b5061009960048036036100949190810190611922565b610193565b6040516100a69190611c62565b60405180910390f35b3480156100bb57600080fd5b506100d660048036036100d191908101906118ce565b61060c565b6040516100e39190611c62565b60405180910390f35b3480156100f857600080fd5b50610113600480360361010e9190810190611922565b61095b565b6040516101209190611c62565b60405180910390f35b34801561013557600080fd5b5061013e610d32565b60405161014b9190611c62565b60405180910390f35b34801561016057600080fd5b5061017b6004803603610176919081019061184c565b610e22565b60405161018a93929190611c16565b60405180910390f35b60008060008060008061100194508473ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016101f490611d70565b602060405180830381600087803b15801561020e57600080fd5b505af1158015610222573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061024691908101906117fa565b93508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156102ac57600080fd5b505af11580156102c0573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506102e491908101906117d1565b92508273ffffffffffffffffffffffffffffffffffffffff1663e942b516886040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161033b9190611df6565b600060405180830381600087803b15801561035557600080fd5b505af1158015610369573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156103d157600080fd5b505af11580156103e5573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610409919081019061177f565b91508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d18a6040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016104609190611d3b565b600060405180830381600087803b15801561047a57600080fd5b505af115801561048e573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016104e79190611e4b565b600060405180830381600087803b15801561050157600080fd5b505af1158015610515573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18a85856040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161057293929190611cdd565b602060405180830381600087803b15801561058c57600080fd5b505af11580156105a0573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506105c49190810190611823565b90507f8e5890af40fc24a059396aca2f83d6ce41fcef086876548fa4fb8ec27e9d292a816040516105f59190611c62565b60405180910390a180955050505050509392505050565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161066c90611d70565b602060405180830381600087803b15801561068657600080fd5b505af115801561069a573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506106be91908101906117fa565b92508273ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561072457600080fd5b505af1158015610738573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061075c919081019061177f565b91508173ffffffffffffffffffffffffffffffffffffffff1663cd30a1d1886040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016107b39190611d3b565b600060405180830381600087803b1580156107cd57600080fd5b505af11580156107e1573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e44594b9876040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161083a9190611e4b565b600060405180830381600087803b15801561085457600080fd5b505af1158015610868573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166328bb211788846040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016108c3929190611c7d565b602060405180830381600087803b1580156108dd57600080fd5b505af11580156108f1573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506109159190810190611823565b90507f4b930e280fe29620bdff00c88155d46d6d82a39f45dd5c3ea114dc3157358112816040516109469190611c62565b60405180910390a18094505050505092915050565b600080600080600061100193508373ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016109bb90611d70565b602060405180830381600087803b1580156109d557600080fd5b505af11580156109e9573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610a0d91908101906117fa565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610a7357600080fd5b505af1158015610a87573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610aab91908101906117d1565b91508173ffffffffffffffffffffffffffffffffffffffff1663e942b516896040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610b029190611d3b565b600060405180830381600087803b158015610b1c57600080fd5b505af1158015610b30573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610b899190611e4b565b600060405180830381600087803b158015610ba357600080fd5b505af1158015610bb7573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610c109190611df6565b600060405180830381600087803b158015610c2a57600080fd5b505af1158015610c3e573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3689846040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610c99929190611cad565b602060405180830381600087803b158015610cb357600080fd5b505af1158015610cc7573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610ceb9190810190611823565b90507fc57b01fa77f41df77eaab79a0e2623fab2e7ae3e9530d9b1cab225ad65f2b7ce81604051610d1c9190611c62565b60405180910390a1809450505050509392505050565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610d8f90611d90565b602060405180830381600087803b158015610da957600080fd5b505af1158015610dbd573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610de19190810190611823565b90507fb5636cd912a73dcdb5b570dbe331dfa3e6435c93e029e642def2c8e40dacf21081604051610e129190611c62565b60405180910390a1809250505090565b6060806060600080600080606080606060008061100198508873ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610e8d90611d70565b602060405180830381600087803b158015610ea757600080fd5b505af1158015610ebb573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610edf91908101906117fa565b97508773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610f4557600080fd5b505af1158015610f59573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610f7d919081019061177f565b96508773ffffffffffffffffffffffffffffffffffffffff1663e8434e398e896040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610fd6929190611c7d565b602060405180830381600087803b158015610ff057600080fd5b505af1158015611004573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061102891908101906117a8565b95508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561108e57600080fd5b505af11580156110a2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506110c69190810190611823565b6040519080825280602002602001820160405280156110f957816020015b60608152602001906001900390816110e45790505b5094508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561116057600080fd5b505af1158015611174573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506111989190810190611823565b6040519080825280602002602001820160405280156111c65781602001602082028038833980820191505090505b5093508573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561122d57600080fd5b505af1158015611241573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506112659190810190611823565b60405190808252806020026020018201604052801561129857816020015b60608152602001906001900390816112835790505b509250600091505b8573ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561130457600080fd5b505af1158015611318573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061133c9190810190611823565b821215611642578573ffffffffffffffffffffffffffffffffffffffff1663846719e0836040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016113989190611c62565b602060405180830381600087803b1580156113b257600080fd5b505af11580156113c6573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506113ea91908101906117d1565b90508073ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161143f90611d1b565b600060405180830381600087803b15801561145957600080fd5b505af115801561146d573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f82011682018060405250611496919081019061188d565b85838151811015156114a457fe5b906020019060200201819052508073ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161150490611e2b565b602060405180830381600087803b15801561151e57600080fd5b505af1158015611532573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506115569190810190611823565b848381518110151561156457fe5b90602001906020020181815250508073ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016115c590611dd6565b600060405180830381600087803b1580156115df57600080fd5b505af11580156115f3573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525061161c919081019061188d565b838381518110151561162a57fe5b906020019060200201819052508160010191506112a0565b8484849b509b509b505050505050505050509193909250565b60006116678251611f5c565b905092915050565b600061167b8251611f6e565b905092915050565b600061168f8251611f80565b905092915050565b60006116a38251611f92565b905092915050565b60006116b78235611fa4565b905092915050565b60006116cb8251611fa4565b905092915050565b600082601f83011215156116e657600080fd5b81356116f96116f482611ea6565b611e79565b9150808252602083016020830185838301111561171557600080fd5b611720838284611fd2565b50505092915050565b600082601f830112151561173c57600080fd5b815161174f61174a82611ea6565b611e79565b9150808252602083016020830185838301111561176b57600080fd5b611776838284611fe1565b50505092915050565b60006020828403121561179157600080fd5b600061179f8482850161165b565b91505092915050565b6000602082840312156117ba57600080fd5b60006117c88482850161166f565b91505092915050565b6000602082840312156117e357600080fd5b60006117f184828501611683565b91505092915050565b60006020828403121561180c57600080fd5b600061181a84828501611697565b91505092915050565b60006020828403121561183557600080fd5b6000611843848285016116bf565b91505092915050565b60006020828403121561185e57600080fd5b600082013567ffffffffffffffff81111561187857600080fd5b611884848285016116d3565b91505092915050565b60006020828403121561189f57600080fd5b600082015167ffffffffffffffff8111156118b957600080fd5b6118c584828501611729565b91505092915050565b600080604083850312156118e157600080fd5b600083013567ffffffffffffffff8111156118fb57600080fd5b611907858286016116d3565b9250506020611918858286016116ab565b9150509250929050565b60008060006060848603121561193757600080fd5b600084013567ffffffffffffffff81111561195157600080fd5b61195d868287016116d3565b935050602061196e868287016116ab565b925050604084013567ffffffffffffffff81111561198b57600080fd5b611997868287016116d3565b9150509250925092565b60006119ac82611eec565b8084526020840193506119be83611ed2565b60005b828110156119f0576119d4868351611a88565b6119dd82611f18565b91506020860195506001810190506119c1565b50849250505092915050565b6000611a0782611ef7565b80845260208401935083602082028501611a2085611edf565b60005b84811015611a59578383038852611a3b838351611acd565b9250611a4682611f25565b9150602088019750600181019050611a23565b508196508694505050505092915050565b611a7381611fae565b82525050565b611a8281611fc0565b82525050565b611a9181611f52565b82525050565b6000611aa282611f0d565b808452611ab6816020860160208601611fe1565b611abf81612014565b602085010191505092915050565b6000611ad882611f02565b808452611aec816020860160208601611fe1565b611af581612014565b602085010191505092915050565b6000600482527f6e616d65000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000600682527f745f7465737400000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000600982527f6974656d5f6e616d6500000000000000000000000000000000000000000000006020830152604082019050919050565b6000600782527f6974656d5f6964000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000601182527f6974656d5f69642c6974656d5f6e616d650000000000000000000000000000006020830152604082019050919050565b60006060820190508181036000830152611c3081866119fc565b90508181036020830152611c4481856119a1565b90508181036040830152611c5881846119fc565b9050949350505050565b6000602082019050611c776000830184611a88565b92915050565b60006040820190508181036000830152611c978185611a97565b9050611ca66020830184611a6a565b9392505050565b60006040820190508181036000830152611cc78185611a97565b9050611cd66020830184611a79565b9392505050565b60006060820190508181036000830152611cf78186611a97565b9050611d066020830185611a79565b611d136040830184611a6a565b949350505050565b60006020820190508181036000830152611d3481611b03565b9050919050565b60006040820190508181036000830152611d5481611b03565b90508181036020830152611d688184611a97565b905092915050565b60006020820190508181036000830152611d8981611b3a565b9050919050565b60006060820190508181036000830152611da981611b3a565b90508181036020830152611dbc81611b03565b90508181036040830152611dcf81611bdf565b9050919050565b60006020820190508181036000830152611def81611b71565b9050919050565b60006040820190508181036000830152611e0f81611b71565b90508181036020830152611e238184611a97565b905092915050565b60006020820190508181036000830152611e4481611ba8565b9050919050565b60006040820190508181036000830152611e6481611ba8565b9050611e736020830184611a88565b92915050565b6000604051905081810181811067ffffffffffffffff82111715611e9c57600080fd5b8060405250919050565b600067ffffffffffffffff821115611ebd57600080fd5b601f19601f8301169050602081019050919050565b6000602082019050919050565b6000602082019050919050565b600081519050919050565b600081519050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b6000611f6782611f32565b9050919050565b6000611f7982611f32565b9050919050565b6000611f8b82611f32565b9050919050565b6000611f9d82611f32565b9050919050565b6000819050919050565b6000611fb982611f32565b9050919050565b6000611fcb82611f32565b9050919050565b82818337600083830152505050565b60005b83811015611fff578082015181840152602081019050611fe4565b8381111561200e576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820a321e40431af4f01c4101f4d0576934115c5b4d9c0efd7c34b6e5683012067ea6c6578706572696d656e74616cf50037";

    public static final String ABI =
            "[{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"update\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"}],\"name\":\"remove\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"insert\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"create\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"string[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"CreateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"InsertResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"UpdateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"RemoveResult\",\"type\":\"event\"}]";

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
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

    public static final Event UPDATERESULT_EVENT =
            new Event(
                    "UpdateResult",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));;

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

    public RemoteCall<Tuple3<List<String>, List<BigInteger>, List<String>>> select(String name) {
        final Function function =
                new Function(
                        FUNC_SELECT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Int256>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        return new RemoteCall<Tuple3<List<String>, List<BigInteger>, List<String>>>(
                new Callable<Tuple3<List<String>, List<BigInteger>, List<String>>>() {
                    @Override
                    public Tuple3<List<String>, List<BigInteger>, List<String>> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<String>, List<BigInteger>, List<String>>(
                                convertToNative((List<Utf8String>) results.get(0).getValue()),
                                convertToNative((List<Int256>) results.get(1).getValue()),
                                convertToNative((List<Utf8String>) results.get(2).getValue()));
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
