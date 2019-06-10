package org.fisco.bcos.web3j.abi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes10;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint32;
import org.junit.Test;

public class FunctionEncoderTest {

    @Test
    public void testBuildMethodId() {
        assertThat(FunctionEncoder.buildMethodId("baz(uint32,bool)"), is("0xcdcd77c0"));
        assertThat(FunctionEncoder.buildMethodId("set(int256,bytes)"), is("0x1f381957"));
        assertThat(FunctionEncoder.buildMethodId("getInt()"), is("0x62738998"));
        assertThat(FunctionEncoder.buildMethodId("setString(string)"), is("0x7fcaf666"));
        assertThat(
                FunctionEncoder.buildMethodId(
                        "set(string,uint256,uint256[6],uint256[],bool,address)"),
                is("0x7a851dd4"));
        assertThat(
                FunctionEncoder.buildMethodId(
                        "set(uint256,address,string,uint256[],uint256[3],string[],string[3],uint256[][],uint256[3][])"),
                is("0x445cc37f"));
    }

    @Test
    public void testBuildMessageSignature() {
        assertThat(
                FunctionEncoder.buildMethodSignature("empty", Collections.emptyList()),
                is("empty()"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "baz", Arrays.asList(new Uint32(BigInteger.valueOf(69)), new Bool(true))),
                is("baz(uint32,bool)"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "test",
                        Arrays.asList(
                                new DynamicArray<DynamicArray<Uint256>>(
                                        new DynamicArray<Uint256>(new Uint256(1), new Uint256(2)),
                                        new DynamicArray<Uint256>(new Uint256(3))),
                                new DynamicArray<Utf8String>(
                                        new Utf8String("one"),
                                        new Utf8String("two"),
                                        new Utf8String("three")))),
                is("test(uint256[][],string[])"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "test",
                        Arrays.asList(
                                new Uint256(0x123),
                                new DynamicArray<Uint32>(new Uint32(0x456), new Uint32(0x789)),
                                new Bytes10("1234567890".getBytes()),
                                new DynamicBytes("Hello, world!".getBytes()))),
                is("test(uint256,uint32[],bytes10,bytes)"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "sam",
                        Arrays.asList(
                                new DynamicBytes("dave".getBytes()),
                                new Bool(true),
                                new DynamicArray<Uint256>(
                                        new Uint256(1), new Uint256(2), new Uint256(3)))),
                is("sam(bytes,bool,uint256[])"));

        Arrays.asList(
                new DynamicArray<DynamicArray<DynamicArray<DynamicArray<Utf8String>>>>(
                        new DynamicArray(
                                new DynamicArray(
                                        new DynamicArray(
                                                new Utf8String("adfsafjljkl"),
                                                new Utf8String("dsafhjk;jlk;jadfl;kjkl"),
                                                new Utf8String("ada"))))));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "dave",
                        Arrays.asList(
                                new DynamicArray<
                                        DynamicArray<DynamicArray<DynamicArray<Utf8String>>>>(
                                        new DynamicArray(
                                                new DynamicArray(
                                                        new DynamicArray(
                                                                new Utf8String("adfsafjljkl"),
                                                                new Utf8String(
                                                                        "dsafhjk;jlk;jadfl;kjkl"),
                                                                new Utf8String("ada"))))))),
                is("dave(string[][][][])"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "test",
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
                                        new Utf8String("abc")))),
                is("test(uint256,int256,string,string[],string[3])"));

        Arrays.asList(
                new StaticArray3<>(
                        new DynamicArray<Type>(new Uint256(1)),
                        new DynamicArray<Type>(new Uint256(2), new Uint256(3)),
                        new DynamicArray<Type>(new Uint256(4), new Uint256(5), new Uint256(6))));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "test",
                        Arrays.asList(
                                new StaticArray3<>(
                                        new DynamicArray<Type>(new Uint256(1)),
                                        new DynamicArray<Type>(new Uint256(2), new Uint256(3)),
                                        new DynamicArray<Type>(
                                                new Uint256(4), new Uint256(5), new Uint256(6))))),
                is("test(uint256[][3])"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "set",
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
                                                new Uint256(5), new Uint256(5), new Uint256(5))))),
                is(
                        "set(uint256,address,string,uint256[],uint256[3],string[],string[3],uint256[][],uint256[3][])"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "set",
                        Arrays.asList(
                                new Utf8String("aaafadsfsfadsfdasf"),
                                new Address("0x35ef07393b57464e93deb59175ff72e6499450cf"),
                                new Uint256(11111),
                                new Int256(-11111))),
                is("set(string,address,uint256,int256)"));

        assertThat(
                FunctionEncoder.buildMethodSignature(
                        "set",
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
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"))),
                is("set(string,uint256,uint256[6],uint256[],bool,address)"));
    }

    @Test
    public void testEncodeConstructorEmpty() {
        assertThat(FunctionEncoder.encodeConstructor(Collections.emptyList()), is(""));
    }

    @Test
    public void testEncodeConstructorString() {
        assertThat(
                FunctionEncoder.encodeConstructor(
                        Collections.singletonList(new Utf8String("Greetings!"))),
                is(
                        "0000000000000000000000000000000000000000000000000000000000000020"
                                + "000000000000000000000000000000000000000000000000000000000000000a"
                                + "4772656574696e67732100000000000000000000000000000000000000000000"));
    }

    @Test
    public void testEncodeConstructorUint() {
        assertThat(
                FunctionEncoder.encodeConstructor(
                        Arrays.asList(
                                new Uint(BigInteger.ONE), new Uint(BigInteger.valueOf(0x20)))),
                is(
                        "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000020"));
    }

    @Test
    public void testFunctionSimpleEncode() {
        Function function =
                new Function(
                        "baz",
                        Arrays.asList(new Uint32(BigInteger.valueOf(69)), new Bool(true)),
                        Collections.<TypeReference<?>>emptyList());

        assertThat(
                FunctionEncoder.encode(function),
                is(
                        "0xcdcd77c0"
                                + "0000000000000000000000000000000000000000000000000000000000000045"
                                + "0000000000000000000000000000000000000000000000000000000000000001"));
    }

    @Test
    public void testFunctionMDynamicArrayEncode1() {
        Function function =
                new Function(
                        "sam",
                        Arrays.asList(
                                new DynamicBytes("dave".getBytes()),
                                new Bool(true),
                                new DynamicArray<>(
                                        new Uint(BigInteger.ONE),
                                        new Uint(BigInteger.valueOf(2)),
                                        new Uint(BigInteger.valueOf(3)))),
                        Collections.<TypeReference<?>>emptyList());

        assertThat(
                FunctionEncoder.encode(function),
                is(
                        "0xa5643bf2"
                                + "0000000000000000000000000000000000000000000000000000000000000060"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6461766500000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000003"));
    }

    @Test
    public void testFunctionMDynamicArrayEncode2() {
        Function function =
                new Function(
                        "f",
                        Arrays.asList(
                                new Uint(BigInteger.valueOf(0x123)),
                                new DynamicArray<>(
                                        new Uint32(BigInteger.valueOf(0x456)),
                                        new Uint32(BigInteger.valueOf(0x789))),
                                new Bytes10("1234567890".getBytes()),
                                new DynamicBytes("Hello, world!".getBytes())),
                        Collections.<TypeReference<?>>emptyList());

        assertThat(
                FunctionEncoder.encode(function),
                is(
                        "0x8be65246"
                                + "0000000000000000000000000000000000000000000000000000000000000123"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "3132333435363738393000000000000000000000000000000000000000000000"
                                + "00000000000000000000000000000000000000000000000000000000000000e0"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000456"
                                + "0000000000000000000000000000000000000000000000000000000000000789"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"));
    }

    @Test
    public void testStringDynamicEncode() {
        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new DynamicArray<>(
                                        new Utf8String("Hello, world!"),
                                        new Utf8String("world! Hello,"))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encode(function),
                is(
                        "0x8812dfcb"
                                + "0000000000000000000000000000000000000000000000000000000000000020" // length
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000040"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "776f726c64212048656c6c6f2c00000000000000000000000000000000000000"));
    }

    @Test
    public void testEncodeUint() {
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Uint256(0)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000000000000000000000"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Uint256(1111)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000000000000000000457"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(
                                new Uint256(
                                        new BigInteger(
                                                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                                                16))),
                        new StringBuilder()),
                is("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Uint256(Long.MAX_VALUE)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000007fffffffffffffff"));
    }

    @Test
    public void testEncodeInt() {
        assertThat(
                FunctionEncoder.encodeParameters(Arrays.asList(new Int256(0)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000000000000000000000"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Int256(1111)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000000000000000000457"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Int256(new BigInteger("-1"))), new StringBuilder()),
                is("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Uint256(Long.MAX_VALUE)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000007fffffffffffffff"));
    }

    @Test
    public void testEncodeBool() {
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Bool(true)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000000000000000000001"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Bool(false)), new StringBuilder()),
                is("0000000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void testEncodelAddress() {
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new StringBuilder()),
                is("000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a"));
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Address("0x0000000000000000000000000000000000000000")),
                        new StringBuilder()),
                is("0000000000000000000000000000000000000000000000000000000000000000"));
    }

    /*
    @Test
    public void testEncodel_Bytes1() {
    	assertThat(FunctionEncoder.encodeParameters(Arrays.asList(new Bytes1("1".getBytes())), new StringBuilder()),
    			is("3100000000000000000000000000000000000000000000000000000000000000")
    			);
    	assertThat(FunctionEncoder.encodeParameters(Arrays.asList(new Bytes1("3".getBytes())), new StringBuilder()),
    			is("0300000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void testEncodel_Bytes32() {
    	assertThat(FunctionEncoder.encodeParameters(Arrays.asList(new Bytes32("1".getBytes())), new StringBuilder()),
    			is("0100000000000000000000000000000000000000000000000000000000000000")
    			);
    	assertThat(FunctionEncoder.encodeParameters(Arrays.asList(new Bytes1("3".getBytes())), new StringBuilder()),
    			is("0300000000000000000000000000000000000000000000000000000000000000"));
    }*/

    @Test
    public void testEncodelString() {
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Utf8String("asfdjhljfadksjf;sdajf")),
                        new StringBuilder()),
                is(
                        "00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000015617366646a686c6a6661646b736a663b7364616a660000000000000000000000"));

        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Utf8String("ljadfkj;kn;';'.';.;';.l")),
                        new StringBuilder()),
                is(
                        "000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000176c6a6164666b6a3b6b6e3b273b272e273b2e3b273b2e6c000000000000000000"));

        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new Utf8String("")), new StringBuilder()),
                is(
                        "00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void testEncodelBytes() {
        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new DynamicBytes("asfdjhljfadksjf;sdajf".getBytes())),
                        new StringBuilder()),
                is(
                        "00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000015617366646a686c6a6661646b736a663b7364616a660000000000000000000000"));

        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new DynamicBytes("ljadfkj;kn;';'.';.;';.l".getBytes())),
                        new StringBuilder()),
                is(
                        "000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000176c6a6164666b6a3b6b6e3b273b272e273b2e3b273b2e6c000000000000000000"));

        assertThat(
                FunctionEncoder.encodeParameters(
                        Arrays.asList(new DynamicBytes("".getBytes())), new StringBuilder()),
                is(
                        "00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void testMixTypeEncode0() {

        // function  set0(string[])
        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new DynamicArray<>(
                                        new Utf8String("Hello, world!"),
                                        new Utf8String("world! Hello,"))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encode(function),
                is(
                        "0x8812dfcb"
                                + "0000000000000000000000000000000000000000000000000000000000000020" // length
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000040"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "776f726c64212048656c6c6f2c00000000000000000000000000000000000000"));
    }

    @Test
    public void testMixTypeEncode1() {
        // function  set0(uint256,uint256[],uint256,bytes10,string)
        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new Uint256(291),
                                new DynamicArray<Uint256>(new Uint256(1110), new Uint256(1929)),
                                new Bytes10("1234567890".getBytes()),
                                new Utf8String("Hello, world!")),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
                is(
                        "0000000000000000000000000000000000000000000000000000000000000123"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "3132333435363738393000000000000000000000000000000000000000000000"
                                + "00000000000000000000000000000000000000000000000000000000000000e0"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000456"
                                + "0000000000000000000000000000000000000000000000000000000000000789"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"));
    }

    @Test
    public void testMixTypeEncode2() {

        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new Utf8String("dave"),
                                new Bool(true),
                                new DynamicArray<>(new Uint256(1), new Uint256(2), new Uint256(3))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
                is(
                        "0000000000000000000000000000000000000000000000000000000000000060"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6461766500000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000003"));
    }

    @Test
    public void testMixTypeEncode3() {
        // function  set0(string,uint256,uint256[6],uint256[],bool,address)
        Function function =
                new Function(
                        "set0",
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
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
                is(
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
                                + "00000000000000050000000000000000000000000000000000000000000000000000000000000006"));
    }

    @Test
    public void testMixTypeEncode4() {

        // function  set0(string,address,uint256,int256)
        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new Utf8String("aaafadsfsfadsfdasf"),
                                new Address("0x35ef07393b57464e93deb59175ff72e6499450cf"),
                                new Uint256(11111),
                                new Int256(-11111)),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
                is(
                        "00000000000000000000000000000000000000000000000000000000000000800000000"
                                + "0000000000000000035ef07393b57464e93deb59175ff72e6499450cf000000000000000000000000"
                                + "0000000000000000000000000000000000002b67fffffffffffffffffffffffffffffffffffffffff"
                                + "fffffffffffffffffffd4990000000000000000000000000000000000000000000000000000000000"
                                + "0000126161616661647366736661647366646173660000000000000000000000000000"));
    }

    @Test
    public void testMixTypeEncode5() {

        // function
        // set0(uint256,address,string,uint256[],uint256[3],string[],string[3],uint256[][],uint256[3][])

        Function function =
                new Function(
                        "set0",
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
                                                new Uint256(5), new Uint256(5), new Uint256(5)))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
                is(
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
                                + "00000000000000000000050000000000000000000000000000000000000000000000000000000000000005"));
    }

    @Test
    public void testMixTypeEncode6() {
        // function  set0(uint256[][3])
        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new StaticArray3<>(
                                        new DynamicArray<Type>(new Uint256(1)),
                                        new DynamicArray<Type>(new Uint256(2), new Uint256(3)),
                                        new DynamicArray<Type>(
                                                new Uint256(4), new Uint256(5), new Uint256(6)))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
                is(
                        "000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000"
                                + "000000000000000000000000000000000000600000000000000000000000000000000000000000000000000000"
                                + "0000000000a0000000000000000000000000000000000000000000000000000000000000010000000000000000"
                                + "000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000"
                                + "000000000000000000000001000000000000000000000000000000000000000000000000000000000000000200"
                                + "000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000"
                                + "000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000"
                                + "000000000300000000000000000000000000000000000000000000000000000000000000040000000000000000"
                                + "000000000000000000000000000000000000000000000005000000000000000000000000000000000000000000"
                                + "0000000000000000000006"));
    }

    @Test
    public void testMixTypeEncode7() {

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
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
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
    public void testMixTypeEncode8() {

        // function  set0(string[][][][] s)
        Function function =
                new Function(
                        "set0",
                        Arrays.asList(
                                new DynamicArray<
                                        DynamicArray<DynamicArray<DynamicArray<Utf8String>>>>(
                                        new DynamicArray(
                                                new DynamicArray(
                                                        new DynamicArray(
                                                                new Utf8String("adfsafjljkl"),
                                                                new Utf8String(
                                                                        "dsafhjk;jlk;jadfl;kjkl"),
                                                                new Utf8String("ada")))))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encodeParameters(
                        function.getInputParameters(), new StringBuilder()),
                is(
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
                                + "6164610000000000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void testMixTypeEncode9() {

        // function baz(uint32,bool)
        Function function0 =
                new Function(
                        "baz",
                        Arrays.asList(new Uint32(69), new Bool(true)),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encode(function0),
                is(
                        "0xcdcd77c0"
                                + "0000000000000000000000000000000000000000000000000000000000000045"
                                + "0000000000000000000000000000000000000000000000000000000000000001"));

        // function baz(bytes3[2])
        Function function1 =
                new Function(
                        "bar",
                        Arrays.asList(
                                new StaticArray2<Bytes3>(
                                        new Bytes3("abc".getBytes()),
                                        new Bytes3("def".getBytes()))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encode(function1),
                is(
                        "0xfce353f6"
                                + "6162630000000000000000000000000000000000000000000000000000000000"
                                + "6465660000000000000000000000000000000000000000000000000000000000"));

        // function sam(bytes,bool,uint[])
        Function function2 =
                new Function(
                        "sam",
                        Arrays.asList(
                                new DynamicBytes("dave".getBytes()),
                                new Bool(true),
                                new DynamicArray<Uint256>(
                                        new Uint256(1), new Uint256(2), new Uint256(3))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encode(function2),
                is(
                        "0xa5643bf2"
                                + "0000000000000000000000000000000000000000000000000000000000000060"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "00000000000000000000000000000000000000000000000000000000000000a0"
                                + "0000000000000000000000000000000000000000000000000000000000000004"
                                + "6461766500000000000000000000000000000000000000000000000000000000"
                                + "0000000000000000000000000000000000000000000000000000000000000003"
                                + "0000000000000000000000000000000000000000000000000000000000000001"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000003"));

        // function f(uint,uint32[],bytes10,bytes)
        Function function3 =
                new Function(
                        "f",
                        Arrays.asList(
                                new Uint256(0x123),
                                new DynamicArray<Uint32>(new Uint32(0x456), new Uint32(0x789)),
                                new Bytes10("1234567890".getBytes()),
                                new DynamicBytes("Hello, world!".getBytes())),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encode(function3),
                is(
                        "0x8be65246"
                                + "0000000000000000000000000000000000000000000000000000000000000123"
                                + "0000000000000000000000000000000000000000000000000000000000000080"
                                + "3132333435363738393000000000000000000000000000000000000000000000"
                                + "00000000000000000000000000000000000000000000000000000000000000e0"
                                + "0000000000000000000000000000000000000000000000000000000000000002"
                                + "0000000000000000000000000000000000000000000000000000000000000456"
                                + "0000000000000000000000000000000000000000000000000000000000000789"
                                + "000000000000000000000000000000000000000000000000000000000000000d"
                                + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"));

        // function g(uint[][],string[])
        Function function4 =
                new Function(
                        "g",
                        Arrays.asList(
                                new DynamicArray<DynamicArray<Uint256>>(
                                        new DynamicArray<Uint256>(new Uint256(1), new Uint256(2)),
                                        new DynamicArray<Uint256>(new Uint256(3))),
                                new DynamicArray<Utf8String>(
                                        new Utf8String("one"),
                                        new Utf8String("two"),
                                        new Utf8String("three"))),
                        Collections.singletonList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                FunctionEncoder.encode(function4),
                is(
                        "0x2289b18c"
                                + "0000000000000000000000000000000000000000000000000000000000000040"
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
                                + "7468726565000000000000000000000000000000000000000000000000000000"));
    }
}
