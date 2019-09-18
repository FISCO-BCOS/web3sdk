package org.fisco.bcos.web3j.tx.txdecode;

import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.*;

public class StaticArrayReference {

    public static TypeReference<?> create(String type, int dimension) throws BaseException {
        return create(type, dimension, false);
    }

    public static TypeReference<?> create(String type, int dimension, boolean indexed)
            throws BaseException {

        switch (dimension) {
            case 1:
                return create1(type, indexed);
            case 2:
                return create2(type, indexed);
            case 3:
                return create3(type, indexed);
            case 4:
                return create4(type, indexed);
            case 5:
                return create5(type, indexed);
            case 6:
                return create6(type, indexed);
            case 7:
                return create7(type, indexed);
            case 8:
                return create8(type, indexed);
            case 9:
                return create9(type, indexed);
            case 10:
                return create10(type, indexed);
            case 11:
                return create11(type, indexed);
            case 12:
                return create12(type, indexed);
            case 13:
                return create13(type, indexed);
            case 14:
                return create14(type, indexed);
            case 15:
                return create15(type, indexed);
            case 16:
                return create16(type, indexed);
            case 17:
                return create17(type, indexed);
            case 18:
                return create18(type, indexed);
            case 19:
                return create19(type, indexed);
            case 20:
                return create20(type, indexed);
            case 21:
                return create21(type, indexed);
            case 22:
                return create22(type, indexed);
            case 23:
                return create23(type, indexed);
            case 24:
                return create24(type, indexed);
            case 25:
                return create25(type, indexed);
            case 26:
                return create26(type, indexed);
            case 27:
                return create27(type, indexed);
            case 28:
                return create28(type, indexed);
            case 29:
                return create29(type, indexed);
            case 30:
                return create30(type, indexed);
            case 31:
                return create31(type, indexed);
            case 32:
                return create32(type, indexed);
            case 128:
                return create128(type, indexed);

            default:
                throw new BaseException(
                        201201,
                        String.format(
                                "dimensions:%d unsupported encoding static array ", dimension));
        }
    }

    private static TypeReference<?> create1(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray1<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray1<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray1<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray1<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray1<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray1<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray1<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray1<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray1<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray1<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray1<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray1<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray1<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray1<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray1<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray1<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray1<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray1<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray1<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray1<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray1<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray1<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray1<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray1<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray1<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray1<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray1<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray1<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray1<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray1<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray1<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray1<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray1<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray1<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray1<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray1<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray1<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray1<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[1] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create2(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray2<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray2<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray2<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray2<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray2<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray2<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray2<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray2<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray2<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray2<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray2<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray2<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray2<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray2<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray2<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray2<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray2<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray2<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray2<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray2<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray2<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray2<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray2<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray2<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray2<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray2<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray2<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray2<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray2<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray2<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray2<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray2<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray2<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray2<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray2<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray2<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray2<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray2<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[2] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create3(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray3<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray3<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray3<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray3<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray3<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray3<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray3<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray3<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray3<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray3<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray3<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray3<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray3<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray3<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray3<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray3<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray3<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray3<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray3<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray3<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray3<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray3<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray3<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray3<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray3<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray3<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray3<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray3<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray3<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray3<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray3<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray3<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray3<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray3<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray3<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray3<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray3<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray3<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[3] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create4(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray4<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray4<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray4<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray4<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray4<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray4<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray4<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray4<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray4<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray4<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray4<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray4<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray4<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray4<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray4<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray4<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray4<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray4<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray4<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray4<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray4<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray4<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray4<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray4<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray4<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray4<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray4<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray4<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray4<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray4<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray4<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray4<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray4<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray4<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray4<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray4<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray4<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray4<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[4] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create5(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray5<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray5<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray5<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray5<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray5<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray5<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray5<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray5<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray5<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray5<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray5<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray5<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray5<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray5<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray5<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray5<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray5<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray5<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray5<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray5<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray5<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray5<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray5<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray5<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray5<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray5<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray5<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray5<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray5<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray5<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray5<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray5<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray5<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray5<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray5<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray5<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray5<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray5<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[5] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create6(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray6<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray6<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray6<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray6<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray6<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray6<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray6<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray6<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray6<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray6<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray6<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray6<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray6<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray6<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray6<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray6<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray6<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray6<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray6<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray6<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray6<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray6<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray6<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray6<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray6<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray6<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray6<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray6<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray6<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray6<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray6<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray6<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray6<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray6<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray6<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray6<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray6<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray6<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[6] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create7(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray7<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray7<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray7<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray7<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray7<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray7<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray7<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray7<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray7<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray7<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray7<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray7<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray7<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray7<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray7<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray7<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray7<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray7<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray7<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray7<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray7<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray7<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray7<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray7<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray7<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray7<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray7<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray7<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray7<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray7<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray7<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray7<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray7<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray7<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray7<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray7<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray7<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray7<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[7] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create8(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray8<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray8<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray8<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray8<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray8<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray8<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray8<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray8<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray8<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray8<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray8<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray8<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray8<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray8<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray8<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray8<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray8<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray8<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray8<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray8<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray8<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray8<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray8<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray8<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray8<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray8<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray8<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray8<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray8<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray8<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray8<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray8<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray8<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray8<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray8<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray8<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray8<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray8<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[8] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create9(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray9<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray9<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray9<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray9<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray9<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray9<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray9<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray9<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray9<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray9<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray9<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray9<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray9<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray9<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray9<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray9<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray9<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray9<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray9<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray9<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray9<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray9<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray9<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray9<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray9<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray9<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray9<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray9<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray9<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray9<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray9<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray9<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray9<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray9<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray9<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray9<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray9<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray9<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[9] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create10(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray10<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray10<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray10<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray10<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray10<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray10<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray10<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray10<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray10<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray10<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray10<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray10<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray10<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray10<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray10<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray10<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray10<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray10<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray10<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray10<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray10<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray10<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray10<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray10<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray10<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray10<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray10<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray10<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray10<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray10<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray10<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray10<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray10<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray10<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray10<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray10<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray10<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray10<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[10] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create11(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray11<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray11<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray11<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray11<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray11<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray11<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray11<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray11<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray11<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray11<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray11<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray11<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray11<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray11<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray11<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray11<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray11<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray11<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray11<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray11<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray11<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray11<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray11<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray11<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray11<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray11<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray11<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray11<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray11<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray11<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray11<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray11<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray11<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray11<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray11<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray11<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray11<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray11<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[11] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create12(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray12<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray12<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray12<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray12<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray12<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray12<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray12<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray12<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray12<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray12<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray12<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray12<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray12<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray12<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray12<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray12<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray12<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray12<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray12<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray12<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray12<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray12<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray12<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray12<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray12<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray12<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray12<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray12<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray12<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray12<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray12<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray12<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray12<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray12<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray12<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray12<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray12<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray12<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[12] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create13(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray13<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray13<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray13<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray13<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray13<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray13<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray13<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray13<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray13<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray13<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray13<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray13<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray13<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray13<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray13<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray13<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray13<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray13<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray13<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray13<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray13<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray13<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray13<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray13<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray13<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray13<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray13<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray13<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray13<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray13<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray13<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray13<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray13<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray13<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray13<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray13<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray13<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray13<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[13] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create14(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray14<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray14<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray14<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray14<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray14<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray14<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray14<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray14<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray14<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray14<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray14<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray14<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray14<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray14<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray14<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray14<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray14<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray14<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray14<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray14<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray14<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray14<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray14<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray14<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray14<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray14<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray14<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray14<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray14<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray14<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray14<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray14<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray14<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray14<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray14<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray14<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray14<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray14<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[14] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create15(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray15<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray15<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray15<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray15<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray15<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray15<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray15<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray15<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray15<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray15<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray15<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray15<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray15<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray15<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray15<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray15<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray15<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray15<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray15<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray15<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray15<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray15<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray15<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray15<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray15<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray15<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray15<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray15<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray15<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray15<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray15<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray15<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray15<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray15<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray15<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray15<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray15<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray15<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[15] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create16(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray16<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray16<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray16<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray16<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray16<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray16<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray16<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray16<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray16<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray16<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray16<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray16<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray16<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray16<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray16<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray16<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray16<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray16<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray16<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray16<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray16<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray16<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray16<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray16<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray16<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray16<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray16<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray16<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray16<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray16<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray16<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray16<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray16<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray16<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray16<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray16<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray16<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray16<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[16] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create17(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray17<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray17<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray17<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray17<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray17<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray17<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray17<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray17<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray17<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray17<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray17<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray17<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray17<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray17<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray17<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray17<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray17<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray17<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray17<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray17<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray17<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray17<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray17<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray17<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray17<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray17<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray17<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray17<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray17<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray17<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray17<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray17<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray17<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray17<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray17<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray17<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray17<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray17<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[17] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create18(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray18<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray18<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray18<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray18<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray18<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray18<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray18<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray18<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray18<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray18<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray18<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray18<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray18<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray18<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray18<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray18<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray18<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray18<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray18<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray18<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray18<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray18<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray18<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray18<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray18<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray18<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray18<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray18<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray18<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray18<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray18<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray18<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray18<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray18<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray18<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray18<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray18<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray18<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[18] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create19(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray19<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray19<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray19<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray19<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray19<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray19<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray19<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray19<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray19<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray19<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray19<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray19<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray19<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray19<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray19<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray19<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray19<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray19<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray19<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray19<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray19<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray19<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray19<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray19<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray19<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray19<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray19<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray19<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray19<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray19<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray19<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray19<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray19<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray19<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray19<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray19<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray19<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray19<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[19] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create20(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray20<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray20<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray20<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray20<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray20<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray20<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray20<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray20<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray20<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray20<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray20<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray20<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray20<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray20<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray20<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray20<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray20<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray20<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray20<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray20<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray20<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray20<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray20<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray20<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray20<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray20<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray20<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray20<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray20<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray20<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray20<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray20<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray20<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray20<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray20<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray20<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray20<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray20<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[20] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create21(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray21<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray21<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray21<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray21<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray21<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray21<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray21<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray21<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray21<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray21<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray21<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray21<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray21<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray21<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray21<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray21<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray21<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray21<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray21<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray21<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray21<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray21<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray21<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray21<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray21<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray21<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray21<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray21<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray21<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray21<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray21<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray21<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray21<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray21<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray21<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray21<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray21<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray21<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[21] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create22(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray22<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray22<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray22<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray22<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray22<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray22<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray22<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray22<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray22<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray22<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray22<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray22<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray22<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray22<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray22<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray22<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray22<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray22<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray22<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray22<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray22<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray22<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray22<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray22<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray22<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray22<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray22<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray22<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray22<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray22<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray22<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray22<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray22<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray22<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray22<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray22<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray22<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray22<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[22] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create23(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray23<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray23<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray23<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray23<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray23<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray23<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray23<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray23<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray23<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray23<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray23<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray23<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray23<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray23<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray23<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray23<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray23<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray23<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray23<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray23<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray23<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray23<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray23<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray23<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray23<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray23<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray23<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray23<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray23<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray23<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray23<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray23<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray23<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray23<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray23<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray23<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray23<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray23<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[23] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create24(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray24<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray24<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray24<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray24<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray24<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray24<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray24<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray24<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray24<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray24<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray24<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray24<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray24<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray24<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray24<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray24<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray24<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray24<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray24<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray24<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray24<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray24<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray24<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray24<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray24<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray24<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray24<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray24<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray24<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray24<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray24<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray24<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray24<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray24<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray24<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray24<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray24<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray24<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[24] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create25(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray25<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray25<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray25<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray25<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray25<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray25<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray25<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray25<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray25<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray25<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray25<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray25<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray25<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray25<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray25<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray25<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray25<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray25<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray25<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray25<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray25<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray25<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray25<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray25<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray25<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray25<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray25<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray25<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray25<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray25<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray25<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray25<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray25<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray25<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray25<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray25<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray25<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray25<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format("%s[25] unsupported encoding array type", type));
        }
    }

    private static TypeReference<?> create26(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray26<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray26<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray26<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray26<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray26<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray26<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray26<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray26<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray26<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray26<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray26<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray26<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray26<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray26<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray26<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray26<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray26<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray26<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray26<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray26<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray26<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray26<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray26<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray26<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray26<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray26<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray26<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray26<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray26<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray26<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray26<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray26<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray26<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray26<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray26<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray26<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray26<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray26<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[26] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create27(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray27<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray27<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray27<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray27<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray27<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray27<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray27<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray27<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray27<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray27<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray27<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray27<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray27<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray27<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray27<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray27<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray27<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray27<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray27<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray27<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray27<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray27<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray27<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray27<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray27<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray27<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray27<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray27<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray27<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray27<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray27<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray27<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray27<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray27<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray27<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray27<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray27<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray27<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[27] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create28(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray28<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray28<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray28<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray28<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray28<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray28<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray28<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray28<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray28<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray28<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray28<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray28<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray28<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray28<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray28<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray28<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray28<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray28<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray28<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray28<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray28<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray28<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray28<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray28<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray28<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray28<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray28<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray28<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray28<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray28<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray28<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray28<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray28<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray28<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray28<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray28<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray28<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray28<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[28] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create29(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray29<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray29<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray29<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray29<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray29<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray29<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray29<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray29<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray29<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray29<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray29<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray29<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray29<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray29<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray29<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray29<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray29<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray29<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray29<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray29<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray29<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray29<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray29<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray29<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray29<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray29<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray29<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray29<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray29<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray29<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray29<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray29<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray29<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray29<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray29<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray29<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray29<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray29<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[29] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create30(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray30<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray30<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray30<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray30<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray30<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray30<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray30<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray30<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray30<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray30<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray30<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray30<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray30<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray30<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray30<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray30<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray30<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray30<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray30<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray30<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray30<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray30<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray30<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray30<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray30<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray30<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray30<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray30<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray30<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray30<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray30<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray30<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray30<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray30<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray30<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray30<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray30<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray30<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[30] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create31(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray31<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray31<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray31<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray31<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray31<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray31<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray31<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray31<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray31<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray31<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray31<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray31<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray31<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray31<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray31<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray31<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray31<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray31<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray31<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray31<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray31<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray31<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray31<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray31<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray31<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray31<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray31<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray31<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray31<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray31<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray31<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray31<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray31<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray31<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray31<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray31<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray31<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray31<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[31] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create32(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray32<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray32<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray32<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray32<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray32<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray32<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray32<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray32<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray32<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray32<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray32<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray32<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray32<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray32<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray32<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray32<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray32<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray32<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray32<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray32<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray32<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray32<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray32<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray32<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray32<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray32<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray32<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray32<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray32<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray32<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray32<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray32<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray32<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray32<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray32<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray32<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray32<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray32<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[32] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create128(String type, boolean indexed) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray128<Int256>>(indexed) {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray128<Uint256>>(indexed) {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray128<Address>>(indexed) {};
            case "bool":
                return new TypeReference<StaticArray128<Bool>>(indexed) {};
            case "string":
                return new TypeReference<StaticArray128<Utf8String>>(indexed) {};
            case "bytes":
                return new TypeReference<StaticArray128<DynamicBytes>>(indexed) {};
            case "bytes1":
                return new TypeReference<StaticArray128<Bytes1>>(indexed) {};
            case "bytes2":
                return new TypeReference<StaticArray128<Bytes2>>(indexed) {};
            case "bytes3":
                return new TypeReference<StaticArray128<Bytes3>>(indexed) {};
            case "bytes4":
                return new TypeReference<StaticArray128<Bytes4>>(indexed) {};
            case "bytes5":
                return new TypeReference<StaticArray128<Bytes5>>(indexed) {};
            case "bytes6":
                return new TypeReference<StaticArray128<Bytes6>>(indexed) {};
            case "bytes7":
                return new TypeReference<StaticArray128<Bytes7>>(indexed) {};
            case "bytes8":
                return new TypeReference<StaticArray128<Bytes8>>(indexed) {};
            case "bytes9":
                return new TypeReference<StaticArray128<Bytes9>>(indexed) {};
            case "bytes10":
                return new TypeReference<StaticArray128<Bytes10>>(indexed) {};
            case "bytes11":
                return new TypeReference<StaticArray128<Bytes11>>(indexed) {};
            case "bytes12":
                return new TypeReference<StaticArray128<Bytes12>>(indexed) {};
            case "bytes13":
                return new TypeReference<StaticArray128<Bytes13>>(indexed) {};
            case "bytes14":
                return new TypeReference<StaticArray128<Bytes14>>(indexed) {};
            case "bytes15":
                return new TypeReference<StaticArray128<Bytes15>>(indexed) {};
            case "bytes16":
                return new TypeReference<StaticArray128<Bytes16>>(indexed) {};
            case "bytes17":
                return new TypeReference<StaticArray128<Bytes17>>(indexed) {};
            case "bytes18":
                return new TypeReference<StaticArray128<Bytes18>>(indexed) {};
            case "bytes19":
                return new TypeReference<StaticArray128<Bytes19>>(indexed) {};
            case "bytes20":
                return new TypeReference<StaticArray128<Bytes20>>(indexed) {};
            case "bytes21":
                return new TypeReference<StaticArray128<Bytes21>>(indexed) {};
            case "bytes22":
                return new TypeReference<StaticArray128<Bytes22>>(indexed) {};
            case "bytes23":
                return new TypeReference<StaticArray128<Bytes23>>(indexed) {};
            case "bytes24":
                return new TypeReference<StaticArray128<Bytes24>>(indexed) {};
            case "bytes25":
                return new TypeReference<StaticArray128<Bytes25>>(indexed) {};
            case "bytes26":
                return new TypeReference<StaticArray128<Bytes26>>(indexed) {};
            case "bytes27":
                return new TypeReference<StaticArray128<Bytes27>>(indexed) {};
            case "bytes28":
                return new TypeReference<StaticArray128<Bytes28>>(indexed) {};
            case "bytes29":
                return new TypeReference<StaticArray128<Bytes29>>(indexed) {};
            case "bytes30":
                return new TypeReference<StaticArray128<Bytes30>>(indexed) {};
            case "bytes31":
                return new TypeReference<StaticArray128<Bytes31>>(indexed) {};
            case "bytes32":
                return new TypeReference<StaticArray128<Bytes32>>(indexed) {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[128] unsupported encoding array type ", type));
        }
    }
}
