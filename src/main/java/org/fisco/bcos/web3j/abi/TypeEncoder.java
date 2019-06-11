package org.fisco.bcos.web3j.abi;

import static org.fisco.bcos.web3j.abi.datatypes.Type.MAX_BYTE_LENGTH;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.utils.Numeric;

/**
 * Ethereum Contract Application Binary Interface (ABI) encoding for types. Further details are
 * available <a href= "https://github.com/ethereum/wiki/wiki/Ethereum-Contract-ABI">here</a>.
 */
public class TypeEncoder {

    private TypeEncoder() {}

    @SuppressWarnings("unchecked")
    public static String encode(Type parameter) {
        if (parameter instanceof NumericType) {
            return encodeNumeric(((NumericType) parameter));
        } else if (parameter instanceof Address) {
            return encodeAddress((Address) parameter);
        } else if (parameter instanceof Bool) {
            return encodeBool((Bool) parameter);
        } else if (parameter instanceof Bytes) {
            return encodeBytes((Bytes) parameter);
        } else if (parameter instanceof DynamicBytes) {
            return encodeDynamicBytes((DynamicBytes) parameter);
        } else if (parameter instanceof Utf8String) {
            return encodeString((Utf8String) parameter);
        } else if (parameter instanceof StaticArray) {
            return encodeArrayValues((StaticArray) parameter);
        } else if (parameter instanceof DynamicArray) {
            return encodeDynamicArray((DynamicArray) parameter);
        } else {
            throw new UnsupportedOperationException(
                    "Type cannot be encoded: " + parameter.getClass());
        }
    }

    static String encodeAddress(Address address) {
        return encodeNumeric(address.toUint160());
    }

    static String encodeNumeric(NumericType numericType) {
        byte[] rawValue = toByteArray(numericType);
        byte paddingValue = getPaddingValue(numericType);
        byte[] paddedRawValue = new byte[MAX_BYTE_LENGTH];
        if (paddingValue != 0) {
            for (int i = 0; i < paddedRawValue.length; i++) {
                paddedRawValue[i] = paddingValue;
            }
        }

        System.arraycopy(
                rawValue, 0, paddedRawValue, MAX_BYTE_LENGTH - rawValue.length, rawValue.length);
        return Numeric.toHexStringNoPrefix(paddedRawValue);
    }

    private static byte getPaddingValue(NumericType numericType) {
        if (numericType.getValue().signum() == -1) {
            return (byte) 0xff;
        } else {
            return 0;
        }
    }

    private static byte[] toByteArray(NumericType numericType) {
        BigInteger value = numericType.getValue();
        if (numericType instanceof Ufixed || numericType instanceof Uint) {
            if (value.bitLength() == Type.MAX_BIT_LENGTH) {
                // As BigInteger is signed, if we have a 256 bit value, the resultant
                // byte array will contain a sign byte in it's MSB, which we should
                // ignore for this unsigned integer type.
                byte[] byteArray = new byte[MAX_BYTE_LENGTH];
                System.arraycopy(value.toByteArray(), 1, byteArray, 0, MAX_BYTE_LENGTH);
                return byteArray;
            }
        }
        return value.toByteArray();
    }

    static String encodeBool(Bool value) {
        byte[] rawValue = new byte[MAX_BYTE_LENGTH];
        if (value.getValue()) {
            rawValue[rawValue.length - 1] = 1;
        }
        return Numeric.toHexStringNoPrefix(rawValue);
    }

    static String encodeBytes(BytesType bytesType) {
        byte[] value = bytesType.getValue();
        int length = value.length;
        int mod = length % MAX_BYTE_LENGTH;

        byte[] dest;
        if (mod != 0) {
            int padding = MAX_BYTE_LENGTH - mod;
            dest = new byte[length + padding];
            System.arraycopy(value, 0, dest, 0, length);
        } else {
            dest = value;
        }
        return Numeric.toHexStringNoPrefix(dest);
    }

    static String encodeDynamicBytes(DynamicBytes dynamicBytes) {
        int size = dynamicBytes.getValue().length;
        String encodedLength = encode(new Uint(BigInteger.valueOf(size)));
        String encodedValue = encodeBytes(dynamicBytes);

        StringBuilder result = new StringBuilder();
        result.append(encodedLength);
        result.append(encodedValue);
        return result.toString();
    }

    static String encodeString(Utf8String string) {
        byte[] utfEncoded = string.getValue().getBytes(StandardCharsets.UTF_8);
        return encodeDynamicBytes(new DynamicBytes(utfEncoded));
    }

    static <T extends Type> String encodeArrayValues(Array<T> value) {

        StringBuilder encodedOffset = new StringBuilder();
        StringBuilder encodedValue = new StringBuilder();

        int offset = value.getValue().size() * MAX_BYTE_LENGTH;

        for (Type type : value.getValue()) {
            String r = encode(type);
            encodedValue.append(r);
            if (type.dynamicType()) {
                encodedOffset.append(encode(new Uint(BigInteger.valueOf(offset))));
                offset += (r.length() >> 1);
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(encodedOffset);
        result.append(encodedValue);

        return result.toString();
    }

    static <T extends Type> String encodeDynamicArray(DynamicArray<T> value) {

        StringBuilder encodedSize = new StringBuilder();
        StringBuilder encodedOffset = new StringBuilder();
        StringBuilder encodedValue = new StringBuilder();

        encodedSize.append(encode(new Uint(BigInteger.valueOf(value.getValue().size()))));

        int offset = value.getValue().size() * MAX_BYTE_LENGTH;

        for (Type type : value.getValue()) {
            String r = encode(type);
            encodedValue.append(r);

            if (type.dynamicType()) {
                encodedOffset.append(encode(new Uint(BigInteger.valueOf(offset))));
                offset += (r.length() >> 1);
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(encodedSize);
        result.append(encodedOffset);
        result.append(encodedValue);

        return result.toString();
    }
}
