package org.fisco.bcos.web3j.tx.txdecode;

import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;

public class DynamicArrayReference {
    public static TypeReference<?> create(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<DynamicArray<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<DynamicArray<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<DynamicArray<Bytes32>>() {};
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
            default:
                throw new BaseException(
                        201201,
                        String.format(" %s[] unsupported encoding dynamic array type ", type));
        }
    }
}
