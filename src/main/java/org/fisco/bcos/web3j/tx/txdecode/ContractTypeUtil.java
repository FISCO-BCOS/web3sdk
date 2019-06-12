/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.fisco.bcos.web3j.tx.txdecode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Bytes;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.NumericType;
import org.fisco.bcos.web3j.abi.datatypes.Type;
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
import org.fisco.bcos.web3j.abi.datatypes.generated.Int104;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int112;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int120;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int128;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int136;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int144;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int152;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int16;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int160;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int168;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int176;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int184;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int192;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int200;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int208;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int216;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int224;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int232;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int24;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int240;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int248;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int40;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int48;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int56;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int64;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int72;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int8;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int80;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int88;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int96;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint104;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint112;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint120;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint128;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint136;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint144;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint152;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint16;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint160;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint168;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint176;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint184;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint192;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint200;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint208;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint216;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint224;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint232;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint24;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint240;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint248;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint40;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint48;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint56;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint64;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint72;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint80;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint88;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint96;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** ContractTypeUtil. */
@Slf4j
public class ContractTypeUtil {
    private static Logger logger = LoggerFactory.getLogger(ContractTypeUtil.class);
    /**
     * generateClassFromInput.
     *
     * @param input input
     * @param type type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Type> T encodeString(String input, Class<T> type)
            throws BaseException {
        try {
            if (Address.class.isAssignableFrom(type)) {
                return (T) new Address(input);
            } else if (NumericType.class.isAssignableFrom(type)) {
                return (T) encodeNumeric(input, (Class<NumericType>) type);
            } else if (Bool.class.isAssignableFrom(type)) {
                return (T) new Bool(Boolean.valueOf(input));
            } else if (Utf8String.class.isAssignableFrom(type)) {
                return (T) new Utf8String(input);
            } else if (Bytes.class.isAssignableFrom(type)) {
                return (T) encodeBytes(input, (Class<Bytes>) type);
            } else if (DynamicBytes.class.isAssignableFrom(type)) {
                return (T) new DynamicBytes(input.getBytes());
            } else {
                throw new BaseException(
                        201201, String.format("type:%s unsupported encoding", type.getName()));
            }
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            logger.error("generateClassFromInput failed input:{} type:{}", input, type.getName());
            throw new BaseException(ConstantCode.IN_FUNCPARAM_ERROR);
        }
    }

    /**
     * decodeResult.
     *
     * @param result result
     * @param type type
     * @return
     */
    public static <T> Object decodeResult(Type result, Class<T> type) throws BaseException {
        try {
            if (Address.class.isAssignableFrom(type)) {
                return result.toString();
            } else if (NumericType.class.isAssignableFrom(type)) {
                return result.getValue();
            } else if (Bool.class.isAssignableFrom(type)) {
                return result.getValue();
            } else if (Utf8String.class.isAssignableFrom(type)) {
                return result.getValue().toString();
            } else if (Bytes.class.isAssignableFrom(type)) {
                return decodeBytes((byte[]) result.getValue());
            } else if (DynamicBytes.class.isAssignableFrom(type)) {
                return decodeBytes((byte[]) result.getValue());
            } else {
                throw new BaseException(
                        201202, String.format("type:%s unsupported decoding", type.getName()));
            }
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            logger.error("decodeResult failed result:{} type:{}", result, type.getName());
            throw new BaseException(ConstantCode.IN_FUNCPARAM_ERROR);
        }
    }

    public static <T extends NumericType> T encodeNumeric(String input, Class<T> type)
            throws BaseException {
        try {
            BigInteger numericValue = new BigInteger(input);
            return type.getConstructor(BigInteger.class).newInstance(numericValue);
        } catch (NoSuchMethodException
                | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            logger.error("encodeNumeric failed.");
            throw new BaseException(
                    201203, String.format("unable to create instance of type:%s", type.getName()));
        }
    }

    public static <T extends Bytes> T encodeBytes(String input, Class<T> type)
            throws BaseException {
        try {
            String simpleName = type.getSimpleName();
            String[] splitName = simpleName.split(Bytes.class.getSimpleName());
            int length = Integer.parseInt(splitName[1]);

            byte[] byteValue = null;
            if (input.length() > length) {
                byteValue = input.substring(0, length).getBytes();
            } else {
                byteValue = input.getBytes();
            }
            byte[] byteValueLength = new byte[length];
            System.arraycopy(byteValue, 0, byteValueLength, 0, byteValue.length);

            return type.getConstructor(byte[].class).newInstance(byteValueLength);
        } catch (NoSuchMethodException
                | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            logger.error("encodeBytes failed.");
            throw new BaseException(
                    201203, String.format("unable to create instance of type:%s", type.getName()));
        }
    }

    /**
     * decodeBytes.
     *
     * @param data data
     * @return
     */
    public static String decodeBytes(byte[] data) throws BaseException {
        try {
            int offset = searchByte(data, (byte) 0);
            if (offset != -1) {
                return new String(data, 0, offset, "UTF-8");
            } else {
                return new String(data, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("decodeBytes failed.");
            throw new BaseException(
                    201202, String.format("type:byte%s unsupported decoding", data.length));
        }
    }

    /**
     * searchByte.
     *
     * @param data data
     * @param value value
     * @return
     */
    public static int searchByte(byte[] data, byte value) {
        int size = data.length;
        for (int i = 0; i < size; ++i) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * getArrayType.
     *
     * @param type type
     * @return
     */
    public static TypeReference<?> getArrayType(String type) throws BaseException {
        switch (type) {
            case "address":
                return new TypeReference<DynamicArray<Address>>() {};
            case "bool":
                return new TypeReference<DynamicArray<Bool>>() {};
            case "string":
                return new TypeReference<DynamicArray<Utf8String>>() {};
            case "bytes":
                return new TypeReference<DynamicArray<DynamicBytes>>() {};
            case "uint8":
                return new TypeReference<DynamicArray<Uint8>>() {};
            case "int8":
                return new TypeReference<DynamicArray<Int8>>() {};
            case "uint16":
                return new TypeReference<DynamicArray<Uint16>>() {};
            case "int16":
                return new TypeReference<DynamicArray<Int16>>() {};
            case "uint24":
                return new TypeReference<DynamicArray<Uint24>>() {};
            case "int24":
                return new TypeReference<DynamicArray<Int24>>() {};
            case "uint32":
                return new TypeReference<DynamicArray<Uint32>>() {};
            case "int32":
                return new TypeReference<DynamicArray<Int32>>() {};
            case "uint40":
                return new TypeReference<DynamicArray<Uint40>>() {};
            case "int40":
                return new TypeReference<DynamicArray<Int40>>() {};
            case "uint48":
                return new TypeReference<DynamicArray<Uint48>>() {};
            case "int48":
                return new TypeReference<DynamicArray<Int48>>() {};
            case "uint56":
                return new TypeReference<DynamicArray<Uint56>>() {};
            case "int56":
                return new TypeReference<DynamicArray<Int56>>() {};
            case "uint64":
                return new TypeReference<DynamicArray<Uint64>>() {};
            case "int64":
                return new TypeReference<DynamicArray<Int64>>() {};
            case "uint72":
                return new TypeReference<DynamicArray<Uint72>>() {};
            case "int72":
                return new TypeReference<DynamicArray<Int72>>() {};
            case "uint80":
                return new TypeReference<DynamicArray<Uint80>>() {};
            case "int80":
                return new TypeReference<DynamicArray<Int80>>() {};
            case "uint88":
                return new TypeReference<DynamicArray<Uint88>>() {};
            case "int88":
                return new TypeReference<DynamicArray<Int88>>() {};
            case "uint96":
                return new TypeReference<DynamicArray<Uint96>>() {};
            case "int96":
                return new TypeReference<DynamicArray<Int96>>() {};
            case "uint104":
                return new TypeReference<DynamicArray<Uint104>>() {};
            case "int104":
                return new TypeReference<DynamicArray<Int104>>() {};
            case "uint112":
                return new TypeReference<DynamicArray<Uint112>>() {};
            case "int112":
                return new TypeReference<DynamicArray<Int112>>() {};
            case "uint120":
                return new TypeReference<DynamicArray<Uint120>>() {};
            case "int120":
                return new TypeReference<DynamicArray<Int120>>() {};
            case "uint128":
                return new TypeReference<DynamicArray<Uint128>>() {};
            case "int128":
                return new TypeReference<DynamicArray<Int128>>() {};
            case "uint136":
                return new TypeReference<DynamicArray<Uint136>>() {};
            case "int136":
                return new TypeReference<DynamicArray<Int136>>() {};
            case "uint144":
                return new TypeReference<DynamicArray<Uint144>>() {};
            case "int144":
                return new TypeReference<DynamicArray<Int144>>() {};
            case "uint152":
                return new TypeReference<DynamicArray<Uint152>>() {};
            case "uint160":
                return new TypeReference<DynamicArray<Uint160>>() {};
            case "int160":
                return new TypeReference<DynamicArray<Int160>>() {};
            case "uint168":
                return new TypeReference<DynamicArray<Uint168>>() {};
            case "int168":
                return new TypeReference<DynamicArray<Int168>>() {};
            case "uint176":
                return new TypeReference<DynamicArray<Uint176>>() {};
            case "int176":
                return new TypeReference<DynamicArray<Int176>>() {};
            case "uint184":
                return new TypeReference<DynamicArray<Uint184>>() {};
            case "int184":
                return new TypeReference<DynamicArray<Int184>>() {};
            case "uint192":
                return new TypeReference<DynamicArray<Uint192>>() {};
            case "int192":
                return new TypeReference<DynamicArray<Int192>>() {};
            case "uint200":
                return new TypeReference<DynamicArray<Uint200>>() {};
            case "int200":
                return new TypeReference<DynamicArray<Int200>>() {};
            case "uint208":
                return new TypeReference<DynamicArray<Uint208>>() {};
            case "int208":
                return new TypeReference<DynamicArray<Int208>>() {};
            case "uint216":
                return new TypeReference<DynamicArray<Uint216>>() {};
            case "int216":
                return new TypeReference<DynamicArray<Int216>>() {};
            case "uint224":
                return new TypeReference<DynamicArray<Uint224>>() {};
            case "int224":
                return new TypeReference<DynamicArray<Int224>>() {};
            case "uint232":
                return new TypeReference<DynamicArray<Uint232>>() {};
            case "int232":
                return new TypeReference<DynamicArray<Int232>>() {};
            case "uint240":
                return new TypeReference<DynamicArray<Uint240>>() {};
            case "int240":
                return new TypeReference<DynamicArray<Int240>>() {};
            case "uint248":
                return new TypeReference<DynamicArray<Uint248>>() {};
            case "int248":
                return new TypeReference<DynamicArray<Int248>>() {};
            case "uint256":
                return new TypeReference<DynamicArray<Uint256>>() {};
            case "int256":
                return new TypeReference<DynamicArray<Int256>>() {};
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
                        201201, String.format("type:%s array unsupported encoding", type));
        }
    }

    /**
     * getType.
     *
     * @param type type
     * @return
     */
    public static Class<? extends Type> getType(String type) throws BaseException {
        switch (type) {
            case "address":
                return Address.class;
            case "bool":
                return Bool.class;
            case "string":
                return Utf8String.class;
            case "bytes":
                return DynamicBytes.class;
            case "uint8":
                return Uint8.class;
            case "int8":
                return Int8.class;
            case "uint16":
                return Uint16.class;
            case "int16":
                return Int16.class;
            case "uint24":
                return Uint24.class;
            case "int24":
                return Int24.class;
            case "uint32":
                return Uint32.class;
            case "int32":
                return Int32.class;
            case "uint40":
                return Uint40.class;
            case "int40":
                return Int40.class;
            case "uint48":
                return Uint48.class;
            case "int48":
                return Int48.class;
            case "uint56":
                return Uint56.class;
            case "int56":
                return Int56.class;
            case "uint64":
                return Uint64.class;
            case "int64":
                return Int64.class;
            case "uint72":
                return Uint72.class;
            case "int72":
                return Int72.class;
            case "uint80":
                return Uint80.class;
            case "int80":
                return Int80.class;
            case "uint88":
                return Uint88.class;
            case "int88":
                return Int88.class;
            case "uint96":
                return Uint96.class;
            case "int96":
                return Int96.class;
            case "uint104":
                return Uint104.class;
            case "int104":
                return Int104.class;
            case "uint112":
                return Uint112.class;
            case "int112":
                return Int112.class;
            case "uint120":
                return Uint120.class;
            case "int120":
                return Int120.class;
            case "uint128":
                return Uint128.class;
            case "int128":
                return Int128.class;
            case "uint136":
                return Uint136.class;
            case "int136":
                return Int136.class;
            case "uint144":
                return Uint144.class;
            case "int144":
                return Int144.class;
            case "uint152":
                return Uint152.class;
            case "int152":
                return Int152.class;
            case "uint160":
                return Uint160.class;
            case "int160":
                return Int160.class;
            case "uint168":
                return Uint168.class;
            case "int168":
                return Int168.class;
            case "uint176":
                return Uint176.class;
            case "int176":
                return Int176.class;
            case "uint184":
                return Uint184.class;
            case "int184":
                return Int184.class;
            case "uint192":
                return Uint192.class;
            case "int192":
                return Int192.class;
            case "uint200":
                return Uint200.class;
            case "int200":
                return Int200.class;
            case "uint208":
                return Uint208.class;
            case "int208":
                return Int208.class;
            case "uint216":
                return Uint216.class;
            case "int216":
                return Int216.class;
            case "uint224":
                return Uint224.class;
            case "int224":
                return Int224.class;
            case "uint232":
                return Uint232.class;
            case "int232":
                return Int232.class;
            case "uint240":
                return Uint240.class;
            case "int240":
                return Int240.class;
            case "uint248":
                return Uint248.class;
            case "int248":
                return Int248.class;
            case "uint256":
                return Uint256.class;
            case "int256":
                return Int256.class;
            case "bytes1":
                return Bytes1.class;
            case "bytes2":
                return Bytes2.class;
            case "bytes3":
                return Bytes3.class;
            case "bytes4":
                return Bytes4.class;
            case "bytes5":
                return Bytes5.class;
            case "bytes6":
                return Bytes6.class;
            case "bytes7":
                return Bytes7.class;
            case "bytes8":
                return Bytes8.class;
            case "bytes9":
                return Bytes9.class;
            case "bytes10":
                return Bytes10.class;
            case "bytes11":
                return Bytes11.class;
            case "bytes12":
                return Bytes12.class;
            case "bytes13":
                return Bytes13.class;
            case "bytes14":
                return Bytes14.class;
            case "bytes15":
                return Bytes15.class;
            case "bytes16":
                return Bytes16.class;
            case "bytes17":
                return Bytes17.class;
            case "bytes18":
                return Bytes18.class;
            case "bytes19":
                return Bytes19.class;
            case "bytes20":
                return Bytes20.class;
            case "bytes21":
                return Bytes21.class;
            case "bytes22":
                return Bytes22.class;
            case "bytes23":
                return Bytes23.class;
            case "bytes24":
                return Bytes24.class;
            case "bytes25":
                return Bytes25.class;
            case "bytes26":
                return Bytes26.class;
            case "bytes27":
                return Bytes27.class;
            case "bytes28":
                return Bytes28.class;
            case "bytes29":
                return Bytes29.class;
            case "bytes30":
                return Bytes30.class;
            case "bytes31":
                return Bytes31.class;
            case "bytes32":
                return Bytes32.class;
            default:
                throw new BaseException(
                        201201, String.format("type:%s unsupported encoding", type));
        }
    }

    /**
     * parseByType.
     *
     * @param type type
     * @param value value
     * @return
     */
    public static Object parseByType(String type, String value) throws BaseException {
        try {
            switch (type) {
                case "address":
                    return value;
                case "bool":
                    return Boolean.valueOf(value);
                case "string":
                    return value;
                case "uint8":
                case "int8":
                case "uint16":
                case "int16":
                case "uint24":
                case "int24":
                case "uint32":
                case "int32":
                case "uint40":
                case "int40":
                case "uint48":
                case "int48":
                case "uint56":
                case "int56":
                case "uint64":
                case "int64":
                case "uint72":
                case "int72":
                case "uint80":
                case "int80":
                case "uint88":
                case "int88":
                case "uint96":
                case "int96":
                case "uint104":
                case "int104":
                case "uint112":
                case "int112":
                case "uint120":
                case "int120":
                case "uint128":
                case "int128":
                case "uint136":
                case "int136":
                case "uint144":
                case "int144":
                case "uint152":
                case "int152":
                case "uint160":
                case "int160":
                case "uint168":
                case "int168":
                case "uint176":
                case "int176":
                case "uint184":
                case "int184":
                case "uint192":
                case "int192":
                case "uint200":
                case "int200":
                case "uint208":
                case "int208":
                case "uint216":
                case "int216":
                case "uint224":
                case "int224":
                case "uint232":
                case "int232":
                case "uint240":
                case "int240":
                case "uint248":
                case "int248":
                case "uint256":
                case "int256":
                    return new BigInteger(value);
                case "bytes1":
                case "bytes2":
                case "bytes3":
                case "bytes4":
                case "bytes5":
                case "bytes6":
                case "bytes7":
                case "bytes8":
                case "bytes9":
                case "bytes10":
                case "bytes11":
                case "bytes12":
                case "bytes13":
                case "bytes14":
                case "bytes15":
                case "bytes16":
                case "bytes17":
                case "bytes18":
                case "bytes19":
                case "bytes20":
                case "bytes21":
                case "bytes22":
                case "bytes23":
                case "bytes24":
                case "bytes25":
                case "bytes26":
                case "bytes27":
                case "bytes28":
                case "bytes29":
                case "bytes30":
                case "bytes31":
                case "bytes32":
                case "bytes":
                    return value;
                default:
                    throw new BaseException(
                            201201, String.format("type:%s unsupported encoding", type));
            }
        } catch (Exception e) {
            logger.error("parseByType failed type:{} value:{}", type, value);
            throw new BaseException(ConstantCode.IN_FUNCPARAM_ERROR);
        }
    }
}
