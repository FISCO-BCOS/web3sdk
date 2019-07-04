package org.fisco.bcos.web3j.tx.txdecode;

import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes10;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes11;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes12;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes13;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes14;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes15;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes16;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes17;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes18;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes19;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes2;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes20;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes21;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes22;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes23;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes24;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes25;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes26;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes27;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes28;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes29;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes30;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes31;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes4;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes5;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes6;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes7;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes9;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;

public class DynamicArrayReference {

    public static TypeReference<?> create(String type) throws BaseException {
        return create(type, false);
    }

    public static TypeReference<?> create(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<DynamicArray<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<DynamicArray<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<DynamicArray<Address>>(indexed) {};
            case "bool":
                return new TypeReference<DynamicArray<Bool>>(indexed) {};
            case "string":
                return new TypeReference<DynamicArray<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<DynamicArray<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<DynamicArray<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<DynamicArray<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<DynamicArray<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<DynamicArray<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<DynamicArray<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<DynamicArray<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<DynamicArray<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<DynamicArray<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<DynamicArray<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<DynamicArray<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<DynamicArray<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<DynamicArray<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<DynamicArray<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<DynamicArray<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<DynamicArray<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<DynamicArray<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<DynamicArray<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<DynamicArray<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<DynamicArray<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<DynamicArray<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<DynamicArray<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<DynamicArray<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<DynamicArray<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<DynamicArray<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<DynamicArray<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<DynamicArray<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<DynamicArray<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<DynamicArray<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<DynamicArray<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<DynamicArray<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<DynamicArray<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<DynamicArray<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201,
                        String.format(" %s[] unsupported encoding dynamic array type ", type));
        }
    }
}
