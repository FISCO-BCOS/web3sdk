package org.fisco.bcos.web3j.abi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes10;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes16;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint32;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.utils.Numeric;
import org.junit.Test;

public class FunctionReturnDecoderTest {

    @Test
    public void testSimpleFunctionDecode() {
        Function function =
                new Function(
                        "test",
                        Collections.<Type>emptyList(),
                        Collections.singletonList(new TypeReference<Uint>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0x0000000000000000000000000000000000000000000000000000000000000037",
                        function.getOutputParameters()),
                equalTo(Collections.singletonList(new Uint(BigInteger.valueOf(55)))));
    }

    @Test
    public void testSimpleFunctionStringResultDecode() {
        Function function =
                new Function(
                        "simple",
                        Arrays.asList(),
                        Collections.singletonList(new TypeReference<Utf8String>() {}));

        List<Type> utf8Strings =
                FunctionReturnDecoder.decode(
                        "0x0000000000000000000000000000000000000000000000000000000000000020"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "6f6e65206d6f72652074696d6500000000000000000000000000000000000000",
                        function.getOutputParameters());

        assertThat(utf8Strings.get(0).getValue(), is("one more time"));
    }

    @Test
    public void testFunctionEmptyStringResultDecode() {
        Function function =
                new Function(
                        "test",
                        Collections.emptyList(),
                        Collections.singletonList(new TypeReference<Utf8String>() {}));

        List<Type> utf8Strings =
                FunctionReturnDecoder.decode(
                        "0x0000000000000000000000000000000000000000000000000000000000000020"
                                + "0000000000000000000000000000000000000000000000000000000000000000",
                        function.getOutputParameters());

        assertThat(utf8Strings.get(0).getValue(), is(""));
    }

    @Test
    public void testMultipleResultFunctionDecode() {
        Function function =
                new Function(
                        "test",
                        Collections.<Type>emptyList(),
                        Arrays.asList(new TypeReference<Uint>() {}, new TypeReference<Uint>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0x0000000000000000000000000000000000000000000000000000000000000037"
                                + "0000000000000000000000000000000000000000000000000000000000000007",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Uint(BigInteger.valueOf(55)),
                                new Uint(BigInteger.valueOf(7)))));
    }

    @Test
    public void testDecodeMultipleStringValues() {
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0x0000000000000000000000000000000000000000000000000000000000000080"
                                + "00000000000000000000000000000000000000000000000000000000000000c0"
                                + "0000000000000000000000000000000000000000000000000000000000000100"
                                + "0000000000000000000000000000000000000000000000000000000000000140"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6465663100000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6768693100000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6a6b6c3100000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6d6e6f3200000000000000000000000000000000000000000000000000000000",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Utf8String("def1"), new Utf8String("ghi1"),
                                new Utf8String("jkl1"), new Utf8String("mno2"))));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDecodeStaticArrayValue() {
        List<TypeReference<Type>> outputParameters = new ArrayList<>(1);
        outputParameters.add((TypeReference) new TypeReference<StaticArray2<Uint256>>() {});
        outputParameters.add((TypeReference) new TypeReference<Uint256>() {});

        List<Type> decoded =
                FunctionReturnDecoder.decode(
                        "0x0000000000000000000000000000000000000000000000000000000000000037"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "000000000000000000000000000000000000000000000000000000000000000a",
                        outputParameters);

        List<Type> expected =
                Arrays.asList(
                        new StaticArray2<Uint256>(
                                new Uint256(BigInteger.valueOf(55)), new Uint256(BigInteger.ONE)),
                        new Uint256(BigInteger.TEN));
        assertThat(decoded, equalTo(expected));
    }

    @Test
    public void testVoidResultFunctionDecode() {
        Function function = new Function("test", Collections.emptyList(), Collections.emptyList());

        assertThat(
                FunctionReturnDecoder.decode("0x", function.getOutputParameters()),
                is(Collections.emptyList()));
    }

    @Test
    public void testEmptyResultFunctionDecode() {
        Function function =
                new Function(
                        "test",
                        Collections.emptyList(),
                        Collections.singletonList(new TypeReference<Uint>() {}));

        assertThat(
                FunctionReturnDecoder.decode("0x", function.getOutputParameters()),
                is(Collections.emptyList()));
    }

    @Test
    public void testDecodeIndexedUint256Value() {
        Uint256 value = new Uint256(BigInteger.TEN);
        String encoded = TypeEncoder.encodeNumeric(value);

        assertThat(
                FunctionReturnDecoder.decodeIndexedValue(encoded, new TypeReference<Uint256>() {}),
                equalTo(value));
    }

    @Test
    public void testDecodeIndexedStringValue() {
        Utf8String string = new Utf8String("some text");
        String encoded = TypeEncoder.encodeString(string);
        String hash = Hash.sha3(encoded);

        assertThat(
                FunctionReturnDecoder.decodeIndexedValue(hash, new TypeReference<Utf8String>() {}),
                equalTo(new Bytes32(Numeric.hexStringToByteArray(hash))));
    }

    @Test
    public void testDecodeIndexedBytes32Value() {
        String rawInput = "0x1234567890123456789012345678901234567890123456789012345678901234";
        byte[] rawInputBytes = Numeric.hexStringToByteArray(rawInput);

        assertThat(
                FunctionReturnDecoder.decodeIndexedValue(rawInput, new TypeReference<Bytes32>() {}),
                equalTo(new Bytes32(rawInputBytes)));
    }

    @Test
    public void testDecodeIndexedBytes16Value() {
        String rawInput = "0x1234567890123456789012345678901200000000000000000000000000000000";
        byte[] rawInputBytes = Numeric.hexStringToByteArray(rawInput.substring(0, 34));

        assertThat(
                FunctionReturnDecoder.decodeIndexedValue(rawInput, new TypeReference<Bytes16>() {}),
                equalTo(new Bytes16(rawInputBytes)));
    }

    @Test
    public void testDecodeIndexedDynamicBytesValue() {
        DynamicBytes bytes = new DynamicBytes(new byte[] {1, 2, 3, 4, 5});
        String encoded = TypeEncoder.encodeDynamicBytes(bytes);
        String hash = Hash.sha3(encoded);

        assertThat(
                FunctionReturnDecoder.decodeIndexedValue(
                        hash, new TypeReference<DynamicBytes>() {}),
                equalTo(new Bytes32(Numeric.hexStringToByteArray(hash))));
    }

    @Test
    public void testDecodeIndexedDynamicArrayValue() {
        DynamicArray<Uint256> array = new DynamicArray<>(new Uint256(BigInteger.TEN));
        String encoded = TypeEncoder.encodeDynamicArray(array);
        String hash = Hash.sha3(encoded);

        assertThat(
                FunctionReturnDecoder.decodeIndexedValue(
                        hash, new TypeReference<DynamicArray>() {}),
                equalTo(new Bytes32(Numeric.hexStringToByteArray(hash))));
    }

    @Test
    public void testMixTypeDecode0() {
        // function  function() constant returns(string,string)
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000020" // length
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000040"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "776f726c64212048656c6c6f2c00000000000000000000000000000000000000",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new DynamicArray<Utf8String>(
                                        new Utf8String("Hello, world!"),
                                        new Utf8String("world! Hello,")))));
    }

    @Test
    public void testMixTypeDecode1() {
        // function function() constant
        // returns(uint256,uint256[],uint256,bytes10,string)
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<DynamicArray<Uint256>>() {},
                                new TypeReference<Bytes10>() {},
                                new TypeReference<Utf8String>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000123"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "3132333435363738393000000000000000000000000000000000000000000000"
                                + "00000000000000000000000000000000000000000000000000000000000000e0"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000456"
                                + "0000000000000000000000000000000000000000000000000000000000000789"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Uint256(291),
                                new DynamicArray<Uint256>(new Uint256(1110), new Uint256(1929)),
                                new Bytes10("1234567890".getBytes()),
                                new Utf8String("Hello, world!"))));
    }

    @Test
    public void testMixTypeDecode2() {
        // function function() constant
        // returns(string,bool,uint256[])
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Bool>() {},
                                new TypeReference<DynamicArray<Uint256>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000060"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6461766500000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000003",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Utf8String("dave"),
                                new Bool(true),
                                new DynamicArray<>(
                                        new Uint256(1), new Uint256(2), new Uint256(3)))));
    }

    @Test
    public void testMixTypeDecode3() {
        // function function(string,uint256,uint256[6],uint256[],bool,address) constant
        // returns(string,uint256,uint256[6],uint256[],bool,address)
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Uint256>() {},
                                new TypeReference<StaticArray6<Uint256>>() {},
                                new TypeReference<DynamicArray<Uint256>>() {},
                                new TypeReference<Bool>() {},
                                new TypeReference<Address>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "00000000000000000000000000000000000000000000000000000000000001600000000000000000"
                                + "00000000000000000000000000000000000000000000045700000000000000000000000000000000"
                                + "00000000000000000000000000000001000000000000000000000000000000000000000000000000"
                                + "00000000000000020000000000000000000000000000000000000000000000000000000000000003"
                                + "00000000000000000000000000000000000000000000000000000000000000040000000000000000"
                                + "00000000000000000000000000000000000000000000000500000000000000000000000000000000"
                                + "00000000000000000000000000000006000000000000000000000000000000000000000000000000"
                                + "00000000000001a00000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a0000000000000000"
                                + "00000000000000000000000000000000000000000000000f6461736c666a616b6c666461736b6c00"
                                + "00000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "00000000000000060000000000000000000000000000000000000000000000000000000000000001"
                                + "00000000000000000000000000000000000000000000000000000000000000020000000000000000"
                                + "00000000000000000000000000000000000000000000000300000000000000000000000000000000"
                                + "00000000000000000000000000000004000000000000000000000000000000000000000000000000"
                                + "00000000000000050000000000000000000000000000000000000000000000000000000000000006",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Utf8String("daslfjaklfdaskl"),
                                new Uint256(1111),
                                new StaticArray6<>(
                                        new Uint256(1),
                                        new Uint256(2),
                                        new Uint256(3),
                                        new Uint256(4),
                                        new Uint256(5),
                                        new Uint256(6)),
                                new DynamicArray<>(
                                        new Uint256(1),
                                        new Uint256(2),
                                        new Uint256(3),
                                        new Uint256(4),
                                        new Uint256(5),
                                        new Uint256(6)),
                                new Bool(false),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"))));
    }

    @Test
    public void testMixTypeDecode4() {

        // function function(string,address,uint256,int256) constant
        // returns(string,address,uint256,int256)
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "00000000000000000000000000000000000000000000000000000000000000800000000"
                                + "0000000000000000035ef07393b57464e93deb59175ff72e6499450cf000000000000000000000000"
                                + "0000000000000000000000000000000000002b67fffffffffffffffffffffffffffffffffffffffff"
                                + "fffffffffffffffffffd4990000000000000000000000000000000000000000000000000000000000"
                                + "0000126161616661647366736661647366646173660000000000000000000000000000",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Utf8String("aaafadsfsfadsfdasf"),
                                new Address("0x35ef07393b57464e93deb59175ff72e6499450cf"),
                                new Uint256(11111),
                                new Int256(-11111))));
    }

    @Test
    public void testMixTypeDecode5() {

        // function function() constant
        // returns(uint256,address,string,uint256[],uint256[3],string[],string[3],uint256[][],uint256[3][])

        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Uint256>>() {},
                                new TypeReference<StaticArray3<Uint256>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<StaticArray3<Utf8String>>() {},
                                new TypeReference<DynamicArray<DynamicArray<Uint256>>>() {},
                                new TypeReference<DynamicArray<StaticArray3<Uint256>>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "000000000000000000000000000000000000000000000000000000000000007b00000000000000000000000069"
                                + "2a70d2e424a56d2c6c27aa97d1a86395877b3a0000000000000000000000000000000000000000000000000000"
                                + "00000000016000000000000000000000000000000000000000000000000000000000000001a000000000000000"
                                + "000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000"
                                + "000000000000000000000005000000000000000000000000000000000000000000000000000000000000000600"
                                + "000000000000000000000000000000000000000000000000000000000002200000000000000000000000000000"
                                + "000000000000000000000000000000000360000000000000000000000000000000000000000000000000000000"
                                + "000000048000000000000000000000000000000000000000000000000000000000000006800000000000000000"
                                + "000000000000000000000000000000000000000000000008737472696e67206300000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000030000"
                                + "000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000"
                                + "000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000"
                                + "000000030000000000000000000000000000000000000000000000000000000000000003000000000000000000"
                                + "000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000"
                                + "000000000000000000a000000000000000000000000000000000000000000000000000000000000000e0000000"
                                + "000000000000000000000000000000000000000000000000000000000361626300000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000003646566000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000036768690000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000006000000000"
                                + "000000000000000000000000000000000000000000000000000000a00000000000000000000000000000000000"
                                + "0000000000000000000000000000e0000000000000000000000000000000000000000000000000000000000000"
                                + "000361626300000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000003646566000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000036768690000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000300000000000000000000000000000000000000000000000000000000000000"
                                + "6000000000000000000000000000000000000000000000000000000000000000e0000000000000000000000000"
                                + "000000000000000000000000000000000000016000000000000000000000000000000000000000000000000000"
                                + "000000000000030000000000000000000000000000000000000000000000000000000000000001000000000000"
                                + "000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000"
                                + "000000000000000000000000010000000000000000000000000000000000000000000000000000000000000003"
                                + "000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000"
                                + "000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000"
                                + "000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000"
                                + "000000000000000000000000000000000000000000000000030000000000000000000000000000000000000000"
                                + "000000000000000000000003000000000000000000000000000000000000000000000000000000000000000300"
                                + "000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000"
                                + "000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000"
                                + "000000000400000000000000000000000000000000000000000000000000000000000000040000000000000000"
                                + "000000000000000000000000000000000000000000000005000000000000000000000000000000000000000000"
                                + "00000000000000000000050000000000000000000000000000000000000000000000000000000000000005",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Uint256(123),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Utf8String("string c"),
                                new DynamicArray<>(new Uint256(1), new Uint256(2), new Uint256(3)),
                                new StaticArray3<>(new Uint256(4), new Uint256(5), new Uint256(6)),
                                new DynamicArray<>(
                                        new Utf8String("abc"),
                                        new Utf8String("def"),
                                        new Utf8String("ghi")),
                                new StaticArray3<>(
                                        new Utf8String("abc"),
                                        new Utf8String("def"),
                                        new Utf8String("ghi")),
                                new DynamicArray<>(
                                        new DynamicArray<>(
                                                new Uint256(1), new Uint256(1), new Uint256(1)),
                                        new DynamicArray<>(
                                                new Uint256(2), new Uint256(2), new Uint256(2)),
                                        new DynamicArray<>(
                                                new Uint256(3), new Uint256(3), new Uint256(3))),
                                new DynamicArray<>(
                                        new StaticArray3<>(
                                                new Uint256(4), new Uint256(4), new Uint256(4)),
                                        new StaticArray3<>(
                                                new Uint256(5), new Uint256(5), new Uint256(5))))));
    }

    @Test
    public void testMixTypeDecode6() {
        // function function() constant returns(uint256[][3])
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(new TypeReference<StaticArray3<DynamicArray<Uint256>>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000"
                                + "000000000000000000000000000000000000600000000000000000000000000000000000000000000000000000"
                                + "0000000000a0000000000000000000000000000000000000000000000000000000000000010000000000000000"
                                + "000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000"
                                + "000000000000000000000001000000000000000000000000000000000000000000000000000000000000000200"
                                + "000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000"
                                + "000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000"
                                + "000000000300000000000000000000000000000000000000000000000000000000000000040000000000000000"
                                + "000000000000000000000000000000000000000000000005000000000000000000000000000000000000000000"
                                + "0000000000000000000006",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new StaticArray3<>(
                                        new DynamicArray<Type>(new Uint256(1)),
                                        new DynamicArray<Type>(new Uint256(2), new Uint256(3)),
                                        new DynamicArray<Type>(
                                                new Uint256(4), new Uint256(5), new Uint256(6))))));
    }

    @Test
    public void testMixTypeDecode7() {

        // function  set0(uint256[][3])
        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new Uint256(100),
                                new Int256(-100),
                                new Utf8String("abc"),
                                new DynamicArray<Type>(
                                        new Utf8String("abc"),
                                        new Utf8String("abc"),
                                        new Utf8String("abc")),
                                new StaticArray3<Type>(
                                        new Utf8String("abc"),
                                        new Utf8String("abc"),
                                        new Utf8String("abc"))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeConstructor(function.getInputParameters()),
                is(
                        "0000000000000000000000000000000000000000000000000000000000000064ffffffffffffffffffffffffff"
                                + "ffffffffffffffffffffffffffffffffffff9c0000000000000000000000000000000000000000000000000000"
                                + "0000000000a000000000000000000000000000000000000000000000000000000000000000e000000000000000"
                                + "000000000000000000000000000000000000000000000002200000000000000000000000000000000000000000"
                                + "000000000000000000000003616263000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000030000000000000000000000000000"
                                + "000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000"
                                + "00000000a000000000000000000000000000000000000000000000000000000000000000e00000000000000000"
                                + "000000000000000000000000000000000000000000000003616263000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000036162"
                                + "630000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000361626300000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000060000000000000000000"
                                + "00000000000000000000000000000000000000000000a000000000000000000000000000000000000000000000"
                                + "000000000000000000e00000000000000000000000000000000000000000000000000000000000000003616263"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000036162630000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000361626300000000000000"
                                + "00000000000000000000000000000000000000000000"));
    }

    @Test
    public void testMixTypeDecode8() {

        // function function(uint256[][3])
        Function function =
                new Function(
                        "function",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<StaticArray3<Utf8String>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000064ffffffffffffffffffffffffff"
                                + "ffffffffffffffffffffffffffffffffffff9c0000000000000000000000000000000000000000000000000000"
                                + "0000000000a000000000000000000000000000000000000000000000000000000000000000e000000000000000"
                                + "000000000000000000000000000000000000000000000002200000000000000000000000000000000000000000"
                                + "000000000000000000000003616263000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000030000000000000000000000000000"
                                + "000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000"
                                + "00000000a000000000000000000000000000000000000000000000000000000000000000e00000000000000000"
                                + "000000000000000000000000000000000000000000000003616263000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000036162"
                                + "630000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000361626300000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000060000000000000000000"
                                + "00000000000000000000000000000000000000000000a000000000000000000000000000000000000000000000"
                                + "000000000000000000e00000000000000000000000000000000000000000000000000000000000000003616263"
                                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000036162630000000000000000000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000000000361626300000000000000"
                                + "00000000000000000000000000000000000000000000",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Uint256(100),
                                new Int256(-100),
                                new Utf8String("abc"),
                                new DynamicArray<Type>(
                                        new Utf8String("abc"),
                                        new Utf8String("abc"),
                                        new Utf8String("abc")),
                                new StaticArray3<Type>(
                                        new Utf8String("abc"),
                                        new Utf8String("abc"),
                                        new Utf8String("abc")))));
    }

    @Test
    public void testMixTypeDecode9() {

        // function set0(string[][][][] s)
        Function function =
                new Function(
                        "set0",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<
                                        DynamicArray<
                                                DynamicArray<
                                                        DynamicArray<
                                                                DynamicArray<Utf8String>>>>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000020"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000020"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000020"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000020"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000060"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "00000000000000000000000000000000000000000000000000000000000000e0"
                                + "000000000000000000000000000000000000000000000000000000000000000b"
                                + "6164667361666a6c6a6b6c000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000016"
                                + "64736166686a6b3b6a6c6b3b6a6164666c3b6b6a6b6c00000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "6164610000000000000000000000000000000000000000000000000000000000",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new DynamicArray<
                                        DynamicArray<DynamicArray<DynamicArray<Utf8String>>>>(
                                        new DynamicArray(
                                                new DynamicArray(
                                                        new DynamicArray(
                                                                new Utf8String("adfsafjljkl"),
                                                                new Utf8String(
                                                                        "dsafhjk;jlk;jadfl;kjkl"),
                                                                new Utf8String("ada"))))))));
    }

    @Test
    public void testMixTypeDecode10() {

        // function baz(uint32,bool)
        Function function0 =
                new Function(
                        "baz",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Uint32>() {}, new TypeReference<Bool>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000045"
                                + "0000000000000000000000000000000000000000000000000000000000000001",
                        function0.getOutputParameters()),
                equalTo(Arrays.asList(new Uint32(69), new Bool(true))));

        // function baz(uint32[2])
        Function function1 =
                new Function(
                        "bar",
                        Collections.<Type>emptyList(),
                        Arrays.asList(new TypeReference<StaticArray2<Bytes3>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "6162630000000000000000000000000000000000000000000000000000000000"
                                + "6465660000000000000000000000000000000000000000000000000000000000",
                        function1.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new StaticArray2<Bytes3>(
                                        new Bytes3("abc".getBytes()),
                                        new Bytes3("def".getBytes())))));

        // function sam(bytes,bool,uint[])
        Function function2 =
                new Function(
                        "sam",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<DynamicBytes>() {},
                                new TypeReference<Bool>() {},
                                new TypeReference<DynamicArray<Uint256>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000060"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6461766500000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000003",
                        function2.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new DynamicBytes("dave".getBytes()),
                                new Bool(true),
                                new DynamicArray<Uint256>(
                                        new Uint256(1), new Uint256(2), new Uint256(3)))));

        // function f(uint,uint32[],bytes10,bytes)
        Function function3 =
                new Function(
                        "f",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<DynamicArray<Uint32>>() {},
                                new TypeReference<Bytes10>() {},
                                new TypeReference<DynamicBytes>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000123"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "3132333435363738393000000000000000000000000000000000000000000000"
                                + "00000000000000000000000000000000000000000000000000000000000000e0"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000456"
                                + "0000000000000000000000000000000000000000000000000000000000000789"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000",
                        function3.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new Uint256(0x123),
                                new DynamicArray<Uint32>(new Uint32(0x456), new Uint32(0x789)),
                                new Bytes10("1234567890".getBytes()),
                                new DynamicBytes("Hello, world!".getBytes()))));

        // function g(uint[][],string[])
        Function function4 =
                new Function(
                        "g",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<DynamicArray<DynamicArray<Uint256>>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0000000000000000000000000000000000000000000000000000000000000040"
                                + "0000000000000000000000000000000000000000000000000000000000000140"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000040"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000060"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "00000000000000000000000000000000000000000000000000000000000000e0"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "6f6e650000000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "74776f0000000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000005"
                                + "7468726565000000000000000000000000000000000000000000000000000000",
                        function4.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new DynamicArray<DynamicArray<Uint256>>(
                                        new DynamicArray<Uint256>(new Uint256(1), new Uint256(2)),
                                        new DynamicArray<Uint256>(new Uint256(3))),
                                new DynamicArray<Utf8String>(
                                        new Utf8String("one"),
                                        new Utf8String("two"),
                                        new Utf8String("three")))));
    }

    @Test
    public void testMixTypeDecod11() {

        // function set0(string[],uint256[],string[])
        Function function =
                new Function(
                        "set0",
                        Collections.<Type>emptyList(),
                        Arrays.asList(
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Uint256>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionReturnDecoder.decode(
                        "0x0000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000014000000000000000000000000000000000000000000000000000000000000001a000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000000076f63746f7075730000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000076f63746f707573000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000c000000000000000000000000000000000000000000000000000000000000000546b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b6b61616161616161616161616161616161616161616100000000000000000000000000000000000000000000000000000000000000000000000000000000000000036161610000000000000000000000000000000000000000000000000000000000",
                        function.getOutputParameters()),
                equalTo(
                        Arrays.asList(
                                new DynamicArray(
                                        new Utf8String("octopus"), new Utf8String("octopus")),
                                new DynamicArray(new Uint256(1), new Uint256(2)),
                                new DynamicArray(
                                        new Utf8String(
                                                "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkaaaaaaaaaaaaaaaaaaaaa"),
                                        new Utf8String("aaa")))));
    }
}
