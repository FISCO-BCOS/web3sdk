package org.fisco.bcos.channel.test.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple8;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

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
public class EvidenceVerify extends Contract {
    public static String BINARY =
            "608060405234801561001057600080fd5b50610c83806100206000396000f300608060405260043610610041576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635a53475114610046575b600080fd5b34801561005257600080fd5b50610190600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035600019169060200190929190803560ff169060200190929190803560001916906020019092919080356000191690602001909291905050506101d2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000808989896101e0610488565b80806020018060200180602001848103845287818151815260200191508051906020019080838360005b8381101561022557808201518184015260208101905061020a565b50505050905090810190601f1680156102525780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561028b578082015181840152602081019050610270565b50505050905090810190601f1680156102b85780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156102f15780820151818401526020810190506102d6565b50505050905090810190601f16801561031e5780820380516001836020036101000a031916815260200191505b509650505050505050604051809103906000f080158015610343573d6000803e3d6000fd5b5090507f8b94c7f6b3fadc764673ea85b4bfef3e17ce928d13e51b818ddfa891ad0f1fcc81604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a18673ffffffffffffffffffffffffffffffffffffffff166103cc878787876103fb565b73ffffffffffffffffffffffffffffffffffffffff161415156103ee57600080fd5b5098975050505050505050565b6000600185858585604051600081526020016040526040518085600019166000191681526020018460ff1660ff1681526020018360001916600019168152602001826000191660001916815260200194505050505060206040516020810390808403906000865af1158015610474573d6000803e3d6000fd5b505050602060405103519050949350505050565b6040516107bf80610499833901905600608060405234801561001057600080fd5b506040516107bf3803806107bf833981018060405281019080805182019291906020018051820192919060200180518201929190505050826000908051906020019061005d92919061020a565b50816001908051906020019061007492919061020a565b50806002908051906020019061008b92919061020a565b507faf1f4f8d84431b65de566a0a4f73763572c14edb25a1360312ff3c7b5386191183838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100f85780820151818401526020810190506100dd565b50505050905090810190601f1680156101255780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561015e578082015181840152602081019050610143565b50505050905090810190601f16801561018b5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156101c45780820151818401526020810190506101a9565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050506102af565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024b57805160ff1916838001178555610279565b82800160010185558215610279579182015b8281111561027857825182559160200191906001019061025d565b5b509050610286919061028a565b5090565b6102ac91905b808211156102a8576000816000905550600101610290565b5090565b90565b610501806102be6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063596f21f814610051578063c7eaf9b4146101b9575b600080fd5b34801561005d57600080fd5b50610066610249565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100ae578082015181840152602081019050610093565b50505050905090810190601f1680156100db5780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101145780820151818401526020810190506100f9565b50505050905090810190601f1680156101415780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561017a57808201518184015260208101905061015f565b50505050905090810190601f1680156101a75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101c557600080fd5b506101ce610433565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561020e5780820151818401526020810190506101f3565b50505050905090810190601f16801561023b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6060806060600060016002828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102e95780601f106102be576101008083540402835291602001916102e9565b820191906000526020600020905b8154815290600101906020018083116102cc57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103855780601f1061035a57610100808354040283529160200191610385565b820191906000526020600020905b81548152906001019060200180831161036857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104215780601f106103f657610100808354040283529160200191610421565b820191906000526020600020905b81548152906001019060200180831161040457829003601f168201915b50505050509050925092509250909192565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104cb5780601f106104a0576101008083540402835291602001916104cb565b820191906000526020600020905b8154815290600101906020018083116104ae57829003601f168201915b50505050509050905600a165627a7a72305820e37d9cf8278c7af44df27d74d13d0a08953c6e1d217baaa0f6e028b4fb64e0280029a165627a7a723058205166adb971a3dd0838616ba29cd3b5ba559a20b03140ccaaa4e073b3f6db3c490029";

    public static final String ABI =
            "[{\"constant\":false,\"inputs\":[{\"name\":\"evi\",\"type\":\"string\"},{\"name\":\"info\",\"type\":\"string\"},{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"signAddr\",\"type\":\"address\"},{\"name\":\"message\",\"type\":\"bytes32\"},{\"name\":\"v\",\"type\":\"uint8\"},{\"name\":\"r\",\"type\":\"bytes32\"},{\"name\":\"s\",\"type\":\"bytes32\"}],\"name\":\"insertEvidence\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"newEvidenceEvent\",\"type\":\"event\"}]";

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static String SM_BINARY =
            "608060405234801561001057600080fd5b50610c83806100206000396000f300608060405260043610610041576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634547fd6414610046575b600080fd5b34801561005257600080fd5b50610190600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035600019169060200190929190803560ff169060200190929190803560001916906020019092919080356000191690602001909291905050506101d2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000808989896101e0610488565b80806020018060200180602001848103845287818151815260200191508051906020019080838360005b8381101561022557808201518184015260208101905061020a565b50505050905090810190601f1680156102525780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561028b578082015181840152602081019050610270565b50505050905090810190601f1680156102b85780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156102f15780820151818401526020810190506102d6565b50505050905090810190601f16801561031e5780820380516001836020036101000a031916815260200191505b509650505050505050604051809103906000f080158015610343573d6000803e3d6000fd5b5090507ffce723060091dd1452a91ae12d05541e3141b37fa27968bc557add04601f74d081604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a18673ffffffffffffffffffffffffffffffffffffffff166103cc878787876103fb565b73ffffffffffffffffffffffffffffffffffffffff161415156103ee57600080fd5b5098975050505050505050565b6000600185858585604051600081526020016040526040518085600019166000191681526020018460ff1660ff1681526020018360001916600019168152602001826000191660001916815260200194505050505060206040516020810390808403906000865af1158015610474573d6000803e3d6000fd5b505050602060405103519050949350505050565b6040516107bf80610499833901905600608060405234801561001057600080fd5b506040516107bf3803806107bf833981018060405281019080805182019291906020018051820192919060200180518201929190505050826000908051906020019061005d92919061020a565b50816001908051906020019061007492919061020a565b50806002908051906020019061008b92919061020a565b507f862dc616a336d85acf6c0a863ee6e67b03a19d22753b3455d915fddcdc414ea483838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100f85780820151818401526020810190506100dd565b50505050905090810190601f1680156101255780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561015e578082015181840152602081019050610143565b50505050905090810190601f16801561018b5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156101c45780820151818401526020810190506101a9565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050506102af565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024b57805160ff1916838001178555610279565b82800160010185558215610279579182015b8281111561027857825182559160200191906001019061025d565b5b509050610286919061028a565b5090565b6102ac91905b808211156102a8576000816000905550600101610290565b5090565b90565b610501806102be6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634ae70cef14610051578063995297f3146101b9575b600080fd5b34801561005d57600080fd5b50610066610249565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100ae578082015181840152602081019050610093565b50505050905090810190601f1680156100db5780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101145780820151818401526020810190506100f9565b50505050905090810190601f1680156101415780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561017a57808201518184015260208101905061015f565b50505050905090810190601f1680156101a75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101c557600080fd5b506101ce610433565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561020e5780820151818401526020810190506101f3565b50505050905090810190601f16801561023b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6060806060600060016002828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102e95780601f106102be576101008083540402835291602001916102e9565b820191906000526020600020905b8154815290600101906020018083116102cc57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103855780601f1061035a57610100808354040283529160200191610385565b820191906000526020600020905b81548152906001019060200180831161036857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104215780601f106103f657610100808354040283529160200191610421565b820191906000526020600020905b81548152906001019060200180831161040457829003601f168201915b50505050509050925092509250909192565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104cb5780601f106104a0576101008083540402835291602001916104cb565b820191906000526020600020905b8154815290600101906020018083116104ae57829003601f168201915b50505050509050905600a165627a7a723058205da1fe48a8f184bcc7f21eefc461343b81434aa3c0f24670126976afbb8e38e60029a165627a7a7230582055c9dc26eb8e24cc0eff5be3562371cfdf949079157dbf3b379ecfde71e41fb00029";

    public static final String FUNC_INSERTEVIDENCE = "insertEvidence";

    public static final Event NEWEVIDENCEEVENT_EVENT =
            new Event(
                    "newEvidenceEvent",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));;

    @Deprecated
    protected EvidenceVerify(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EvidenceVerify(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EvidenceVerify(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EvidenceVerify(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getBinary() {
        return (EncryptType.encryptType == EncryptType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<TransactionReceipt> insertEvidence(
            String evi,
            String info,
            String id,
            String signAddr,
            byte[] message,
            BigInteger v,
            byte[] r,
            byte[] s) {
        final Function function =
                new Function(
                        FUNC_INSERTEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(evi),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(info),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(id),
                                new org.fisco.bcos.web3j.abi.datatypes.Address(signAddr),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(message),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void insertEvidence(
            String evi,
            String info,
            String id,
            String signAddr,
            byte[] message,
            BigInteger v,
            byte[] r,
            byte[] s,
            TransactionSucCallback callback) {
        final Function function =
                new Function(
                        FUNC_INSERTEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(evi),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(info),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(id),
                                new org.fisco.bcos.web3j.abi.datatypes.Address(signAddr),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(message),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                        Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String insertEvidenceSeq(
            String evi,
            String info,
            String id,
            String signAddr,
            byte[] message,
            BigInteger v,
            byte[] r,
            byte[] s) {
        final Function function =
                new Function(
                        FUNC_INSERTEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(evi),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(info),
                                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(id),
                                new org.fisco.bcos.web3j.abi.datatypes.Address(signAddr),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(message),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint8(v),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(r),
                                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(s)),
                        Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple8<String, String, String, String, byte[], BigInteger, byte[], byte[]>
            getInsertEvidenceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_INSERTEVIDENCE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Bytes32>() {},
                                new TypeReference<Uint8>() {},
                                new TypeReference<Bytes32>() {},
                                new TypeReference<Bytes32>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple8<String, String, String, String, byte[], BigInteger, byte[], byte[]>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (String) results.get(2).getValue(),
                (String) results.get(3).getValue(),
                (byte[]) results.get(4).getValue(),
                (BigInteger) results.get(5).getValue(),
                (byte[]) results.get(6).getValue(),
                (byte[]) results.get(7).getValue());
    }

    public Tuple1<String> getInsertEvidenceOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function =
                new Function(
                        FUNC_INSERTEVIDENCE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        ;
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public List<NewEvidenceEventEventResponse> getNewEvidenceEventEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(NEWEVIDENCEEVENT_EVENT, transactionReceipt);
        ArrayList<NewEvidenceEventEventResponse> responses =
                new ArrayList<NewEvidenceEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewEvidenceEventEventResponse typedResponse = new NewEvidenceEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registernewEvidenceEventEventLogFilter(
            String fromBlock,
            String toBlock,
            List<String> otherTopcs,
            EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(NEWEVIDENCEEVENT_EVENT);
        registerEventLogPushFilter(ABI, BINARY, topic0, fromBlock, toBlock, otherTopcs, callback);
    }

    public void registernewEvidenceEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(NEWEVIDENCEEVENT_EVENT);
        registerEventLogPushFilter(ABI, BINARY, topic0, callback);
    }

    @Deprecated
    public static EvidenceVerify load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new EvidenceVerify(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EvidenceVerify load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new EvidenceVerify(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EvidenceVerify load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new EvidenceVerify(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EvidenceVerify load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new EvidenceVerify(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EvidenceVerify> deploy(
            Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                EvidenceVerify.class, web3j, credentials, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<EvidenceVerify> deploy(
            Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(
                EvidenceVerify.class, web3j, credentials, gasPrice, gasLimit, getBinary(), "");
    }

    public static RemoteCall<EvidenceVerify> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(
                EvidenceVerify.class,
                web3j,
                transactionManager,
                contractGasProvider,
                getBinary(),
                "");
    }

    @Deprecated
    public static RemoteCall<EvidenceVerify> deploy(
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(
                EvidenceVerify.class,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                getBinary(),
                "");
    }

    public static class NewEvidenceEventEventResponse {
        public Log log;

        public String addr;
    }
}
