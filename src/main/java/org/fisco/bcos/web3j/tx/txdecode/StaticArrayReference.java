package org.fisco.bcos.web3j.tx.txdecode;

import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.*;

public class StaticArrayReference {

    public static TypeReference<?> create(String type, int dimension) throws BaseException {

        switch (dimension) {
            case 1:
                return create1(type);
            case 2:
                return create2(type);
            case 3:
                return create3(type);
            case 4:
                return create4(type);
            case 5:
                return create5(type);
            case 6:
                return create6(type);
            case 7:
                return create7(type);
            case 8:
                return create8(type);
            case 9:
                return create9(type);
            case 10:
                return create10(type);
            case 11:
                return create11(type);
            case 12:
                return create12(type);
            case 13:
                return create13(type);
            case 14:
                return create14(type);
            case 15:
                return create15(type);
            case 16:
                return create16(type);
            case 17:
                return create17(type);
            case 18:
                return create18(type);
            case 19:
                return create19(type);
            case 20:
                return create20(type);
            case 21:
                return create21(type);
            case 22:
                return create22(type);
            case 23:
                return create23(type);
            case 24:
                return create24(type);
            case 25:
                return create25(type);
            case 26:
                return create26(type);
            case 27:
                return create27(type);
            case 28:
                return create28(type);
            case 29:
                return create29(type);
            case 30:
                return create30(type);
            case 31:
                return create31(type);
            case 32:
                return create32(type);

            default:
                throw new BaseException(
                        201201,
                        String.format(
                                "dimensions:%d unsupported encoding static array ", dimension));
        }
    }

    private static TypeReference<?> create1(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray1<Int256>>() {};
        }

        if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray1<Uint256>>() {};
        }

        if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray1<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray1<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray1<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray1<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray1<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[1] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create2(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray2<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray2<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray2<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray2<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray2<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray2<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray2<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[2] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create3(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray3<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray3<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray3<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray3<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray3<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray3<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray3<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[3] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create4(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray4<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray4<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray4<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray4<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray4<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray4<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray4<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[4] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create5(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray5<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray5<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray5<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray5<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray5<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray4<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray5<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[5] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create6(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray6<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray6<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray6<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray6<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray6<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray4<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray6<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[6] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create7(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray7<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray7<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray7<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray7<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray7<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray4<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray7<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[7] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create8(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray8<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray8<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray8<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray8<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray8<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray8<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray8<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[8] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create9(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray9<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray9<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray9<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray9<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray9<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray9<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray9<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[9] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create10(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray10<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray10<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray10<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray10<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray10<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray10<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray10<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[10] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create11(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray11<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray11<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray11<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray11<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray11<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray11<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray11<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[11] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create12(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray12<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray12<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray12<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray12<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray12<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray12<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray12<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[12] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create13(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray13<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray13<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray13<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray13<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray13<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray13<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray13<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[13] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create14(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray14<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray14<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray14<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray14<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray14<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray14<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray14<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[14] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create15(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray15<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray15<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray15<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray15<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray15<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray15<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray15<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[15] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create16(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray16<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray16<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray16<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray16<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray16<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray16<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray16<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[16] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create17(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray17<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray17<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray17<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray17<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray17<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray17<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray17<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[17] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create18(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray18<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray18<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray18<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray18<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray18<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray18<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray18<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[18] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create19(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray19<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray19<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray19<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray19<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray19<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray19<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray19<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[19] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create20(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray20<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray20<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray20<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray20<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray20<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray20<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray20<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[20] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create21(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray21<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray21<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray21<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray21<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray21<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray21<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray21<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[21] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create22(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray22<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray22<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray22<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray22<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray22<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray22<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray22<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[22] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create23(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray23<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray23<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray23<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray23<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray23<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray23<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray23<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[23] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create24(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray24<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray24<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray24<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray24<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray24<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray24<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray24<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[24] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create25(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray25<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray25<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray25<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray25<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray25<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray25<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray25<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format("%s[25] unsupported encoding array type", type));
        }
    }

    private static TypeReference<?> create26(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray26<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray26<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray26<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray26<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray26<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray26<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray26<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[26] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create27(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray27<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray27<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray27<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray27<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray27<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray27<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray27<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[27] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create28(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray28<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray28<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray28<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray28<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray28<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray28<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray28<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[28] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create29(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray29<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray29<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray29<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray29<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray29<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray29<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray29<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[29] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create30(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray30<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray30<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray30<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray30<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray30<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray30<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray30<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[30] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create31(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray31<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray31<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray31<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray31<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray31<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray31<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray31<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[31] unsupported encoding array type ", type));
        }
    }

    private static TypeReference<?> create32(String type) throws BaseException {

        if (ContractTypeUtil.invalidInt(type)) {
            return new TypeReference<StaticArray32<Int256>>() {};
        } else if (ContractTypeUtil.invalidUint(type)) {
            return new TypeReference<StaticArray32<Uint256>>() {};
        } else if (ContractTypeUtil.invalidBS(type)) {
            return new TypeReference<StaticArray32<Bytes32>>() {};
        }

        switch (type) {
            case "address":
                return new TypeReference<StaticArray32<Address>>() {};
            case "bool":
                return new TypeReference<StaticArray32<Bool>>() {};
            case "string":
                return new TypeReference<StaticArray32<Utf8String>>() {};
            case "bytes":
                return new TypeReference<StaticArray32<DynamicBytes>>() {};
            default:
                throw new BaseException(
                        201201, String.format(" %s[32] unsupported encoding array type ", type));
        }
    }
}
