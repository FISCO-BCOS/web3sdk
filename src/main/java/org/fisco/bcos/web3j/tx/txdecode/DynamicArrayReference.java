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

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<DynamicArray<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<DynamicArray<Uint256>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<DynamicArray<Address>>() {};
            case "bool":
                return new TypeReference<DynamicArray<Bool>>() {};
            case "string":
                return new TypeReference<DynamicArray<Utf8String>>() {};
            case "bytes":
                return new TypeReference<DynamicArray<DynamicBytes>>() {};
            case "bytes1":
                return new TypeReference<DynamicArray<Bytes1>>() {};
            case "bytes2":
                return new TypeReference<DynamicArray<Bytes2>>() {};
            case "bytes3":
                return new TypeReference<DynamicArray<Bytes3>>() {};
            case "bytes4":
                return new TypeReference<DynamicArray<Bytes4>>() {};
            case "bytes5":
                return new TypeReference<DynamicArray<Bytes5>>() {};
            case "bytes6":
                return new TypeReference<DynamicArray<Bytes6>>() {};
            case "bytes7":
                return new TypeReference<DynamicArray<Bytes7>>() {};
            case "bytes8":
                return new TypeReference<DynamicArray<Bytes8>>() {};
            case "bytes9":
                return new TypeReference<DynamicArray<Bytes9>>() {};
            case "bytes10":
                return new TypeReference<DynamicArray<Bytes10>>() {};
            case "bytes11":
                return new TypeReference<DynamicArray<Bytes11>>() {};
            case "bytes12":
                return new TypeReference<DynamicArray<Bytes12>>() {};
            case "bytes13":
                return new TypeReference<DynamicArray<Bytes13>>() {};
            case "bytes14":
                return new TypeReference<DynamicArray<Bytes14>>() {};
            case "bytes15":
                return new TypeReference<DynamicArray<Bytes15>>() {};
            case "bytes16":
                return new TypeReference<DynamicArray<Bytes16>>() {};
            case "bytes17":
                return new TypeReference<DynamicArray<Bytes17>>() {};
            case "bytes18":
                return new TypeReference<DynamicArray<Bytes18>>() {};
            case "bytes19":
                return new TypeReference<DynamicArray<Bytes19>>() {};
            case "bytes20":
                return new TypeReference<DynamicArray<Bytes20>>() {};
            case "bytes21":
                return new TypeReference<DynamicArray<Bytes21>>() {};
            case "bytes22":
                return new TypeReference<DynamicArray<Bytes22>>() {};
            case "bytes23":
                return new TypeReference<DynamicArray<Bytes23>>() {};
            case "bytes24":
                return new TypeReference<DynamicArray<Bytes24>>() {};
            case "bytes25":
                return new TypeReference<DynamicArray<Bytes25>>() {};
            case "bytes26":
                return new TypeReference<DynamicArray<Bytes26>>() {};
            case "bytes27":
                return new TypeReference<DynamicArray<Bytes27>>() {};
            case "bytes28":
                return new TypeReference<DynamicArray<Bytes28>>() {};
            case "bytes29":
                return new TypeReference<DynamicArray<Bytes29>>() {};
            case "bytes30":
                return new TypeReference<DynamicArray<Bytes30>>() {};
            case "bytes31":
                return new TypeReference<DynamicArray<Bytes31>>() {};
            case "bytes32":
                return new TypeReference<DynamicArray<Bytes32>>() {};
            default:
                throw new BaseException(
                        201201,
                        String.format(" %s[] unsupported encoding dynamic array type ", type));
        }
    }
}
