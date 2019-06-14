package org.fisco.bcos.web3j.tx.txdecode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.junit.Test;

public class TransactionDecoderTest {

    @Test
    public void testOK() throws JsonProcessingException, TransactionException, BaseException {

        /*contract TestOk
        {
        	function test(uint256 _u,int256 _i,bool _b,address _addr,string _s,bytes _bs,bytes32 _bs32) public constant returns (uint256,int256,bool,address,string,bytes,bytes32) {

        	}
        }*/

        // [{"constant":true,"inputs":[{"name":"_u","type":"uint256"},{"name":"_i","type":"int256"},{"name":"_b","type":"bool"},{"name":"_addr","type":"address"},{"name":"_s","type":"string"},{"name":"_bs","type":"bytes"},{"name":"_bs32","type":"bytes32"}],"name":"test","outputs":[{"name":"","type":"uint256"},{"name":"","type":"int256"},{"name":"","type":"bool"},{"name":"","type":"address"},{"name":"","type":"string"},{"name":"","type":"bytes"},{"name":"","type":"bytes32"}],"payable":false,"stateMutability":"view","type":"function"}]
        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"},{\"name\":\"\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]",
                        "");

        Function test =
                new Function(
                        "test",
                        Arrays.asList(
                                new Uint256(111111),
                                new Int256(-1111111),
                                new Bool(false),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicBytes>() {},
                                new TypeReference<Bytes32>() {}));

        String sr = decode.decodeInputReturnJson(FunctionEncoder.encode(test));
        List<Type> lr = decode.decodeInputReturnObject(FunctionEncoder.encode(test));

        assertThat(
                sr,
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"_i\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"}],\"function\":\"test(uint256,int256,bool,address,string,bytes,bytes32)\",\"methodID\":\"0x0061b7bb\"}"));
        assertThat(
                lr,
                is(
                        Arrays.asList(
                                new Uint256(111111),
                                new Int256(-1111111),
                                new Bool(false),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()))));

        String or =
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test),
                        FunctionEncoder.encodeConstructor(
                                Arrays.asList(
                                        new Uint256(111111),
                                        new Int256(-1111111),
                                        new Bool(false),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                        new DynamicBytes("sadfljkjkljkl".getBytes()),
                                        new Bytes32(
                                                "abcdefghiabcdefghiabcdefghiabhji".getBytes()))));
        List<Type> lo =
                decode.decodeOutPutReturnObject(
                        FunctionEncoder.encode(test),
                        FunctionEncoder.encodeConstructor(
                                Arrays.asList(
                                        new Uint256(111111),
                                        new Int256(-1111111),
                                        new Bool(false),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                        new DynamicBytes("sadfljkjkljkl".getBytes()),
                                        new Bytes32(
                                                "abcdefghiabcdefghiabcdefghiabhji".getBytes()))));
        assertThat(
                or,
                is(
                        "[{\"name\":\"\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"\",\"type\":\"bool\",\"data\":false},{\"name\":\"\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"},{\"name\":\"\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"}]"));
        assertThat(
                lo,
                is(
                        Arrays.asList(
                                new Uint256(111111),
                                new Int256(-1111111),
                                new Bool(false),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()))));
    }

    @Test
    public void testOK1() throws JsonProcessingException, TransactionException, BaseException {

        /*contract TestOk
        {
        	function test(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32,string _s,bytes _bs) public constant returns (uint256[],int256[],bool[],address[],bytes32[],string,bytes) {

        	}
        }*/

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]",
                        "");

        Function test =
                new Function(
                        "test",
                        Arrays.asList(
                                new DynamicArray<Uint256>(
                                        new Uint256(11111), new Uint256(22222), new Uint256(33333)),
                                new DynamicArray<Int256>(
                                        new Int256(-1111111),
                                        new Int256(-3333333),
                                        new Int256(-2222222)),
                                new DynamicArray<Bool>(
                                        new Bool(false), new Bool(true), new Bool(false)),
                                new DynamicArray<Address>(
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                new DynamicArray<Bytes32>(
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes())),
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicBytes>() {},
                                new TypeReference<Bytes32>() {}));

        String sr = decode.decodeInputReturnJson(FunctionEncoder.encode(test));
        List<Type> lr = decode.decodeInputReturnObject(FunctionEncoder.encode(test));

        assertThat(
                sr,
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}],\"function\":\"test(uint256[],int256[],bool[],address[],bytes32[],string,bytes)\",\"methodID\":\"0x982d73e5\"}"));
        assertThat(
                lr,
                is(
                        Arrays.asList(
                                new DynamicArray<Uint256>(
                                        new Uint256(11111), new Uint256(22222), new Uint256(33333)),
                                new DynamicArray<Int256>(
                                        new Int256(-1111111),
                                        new Int256(-3333333),
                                        new Int256(-2222222)),
                                new DynamicArray<Bool>(
                                        new Bool(false), new Bool(true), new Bool(false)),
                                new DynamicArray<Address>(
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                new DynamicArray<Bytes32>(
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes()))));

        String or =
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test),
                        FunctionEncoder.encodeConstructor(
                                Arrays.asList(
                                        new DynamicArray<Uint256>(
                                                new Uint256(11111),
                                                new Uint256(22222),
                                                new Uint256(33333)),
                                        new DynamicArray<Int256>(
                                                new Int256(-1111111),
                                                new Int256(-3333333),
                                                new Int256(-2222222)),
                                        new DynamicArray<Bool>(
                                                new Bool(false), new Bool(true), new Bool(false)),
                                        new DynamicArray<Address>(
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                        new DynamicArray<Bytes32>(
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes())),
                                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                        new DynamicBytes("sadfljkjkljkl".getBytes()))));
        List<Type> lo =
                decode.decodeOutPutReturnObject(
                        FunctionEncoder.encode(test),
                        FunctionEncoder.encodeConstructor(
                                Arrays.asList(
                                        new DynamicArray<Uint256>(
                                                new Uint256(11111),
                                                new Uint256(22222),
                                                new Uint256(33333)),
                                        new DynamicArray<Int256>(
                                                new Int256(-1111111),
                                                new Int256(-3333333),
                                                new Int256(-2222222)),
                                        new DynamicArray<Bool>(
                                                new Bool(false), new Bool(true), new Bool(false)),
                                        new DynamicArray<Address>(
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                        new DynamicArray<Bytes32>(
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes())),
                                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                        new DynamicBytes("sadfljkjkljkl".getBytes()))));
        assertThat(
                or,
                is(
                        "[{\"name\":\"\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}]"));
        assertThat(
                lo,
                is(
                        Arrays.asList(
                                new DynamicArray<Uint256>(
                                        new Uint256(11111), new Uint256(22222), new Uint256(33333)),
                                new DynamicArray<Int256>(
                                        new Int256(-1111111),
                                        new Int256(-3333333),
                                        new Int256(-2222222)),
                                new DynamicArray<Bool>(
                                        new Bool(false), new Bool(true), new Bool(false)),
                                new DynamicArray<Address>(
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                new DynamicArray<Bytes32>(
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes()))));
    }

    @Test
    public void testOK2() throws JsonProcessingException, TransactionException, BaseException {

        /*contract TestOk
        {
        	function test(uint256[2] _u,int256[2] _i,bool[2] _b,address[2] _addr,bytes32[2] _bs32,string _s,bytes _bs) public constant returns (uint256[2],int256[2],bool[2],address[2],bytes32[2],string,bytes) {

        	}
        }*/

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[2]\"},{\"name\":\"_i\",\"type\":\"int256[2]\"},{\"name\":\"_b\",\"type\":\"bool[2]\"},{\"name\":\"_addr\",\"type\":\"address[2]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[2]\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]",
                        "");

        Function test =
                new Function(
                        "test",
                        Arrays.asList(
                                new StaticArray2<Uint256>(new Uint256(11111), new Uint256(33333)),
                                new StaticArray2<Int256>(
                                        new Int256(-1111111), new Int256(-2222222)),
                                new StaticArray2<Bool>(new Bool(true), new Bool(false)),
                                new StaticArray2<Address>(
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                new StaticArray2<Bytes32>(
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes())),
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicBytes>() {},
                                new TypeReference<Bytes32>() {}));

        String sr = decode.decodeInputReturnJson(FunctionEncoder.encode(test));
        List<Type> lr = decode.decodeInputReturnObject(FunctionEncoder.encode(test));

        assertThat(
                sr,
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256[2]\",\"data\":[11111,33333]},{\"name\":\"_i\",\"type\":\"int256[2]\",\"data\":[-1111111,-2222222]},{\"name\":\"_b\",\"type\":\"bool[2]\",\"data\":[true,false]},{\"name\":\"_addr\",\"type\":\"address[2]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[2]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}],\"function\":\"test(uint256[2],int256[2],bool[2],address[2],bytes32[2],string,bytes)\",\"methodID\":\"0xd7754ebf\"}"));
        assertThat(
                lr,
                is(
                        Arrays.asList(
                                new StaticArray2<Uint256>(new Uint256(11111), new Uint256(33333)),
                                new StaticArray2<Int256>(
                                        new Int256(-1111111), new Int256(-2222222)),
                                new StaticArray2<Bool>(new Bool(true), new Bool(false)),
                                new StaticArray2<Address>(
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                new StaticArray2<Bytes32>(
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes()))));

        String or =
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test),
                        FunctionEncoder.encodeConstructor(
                                Arrays.asList(
                                        new StaticArray2<Uint256>(
                                                new Uint256(11111), new Uint256(33333)),
                                        new StaticArray2<Int256>(
                                                new Int256(-1111111), new Int256(-2222222)),
                                        new StaticArray2<Bool>(new Bool(true), new Bool(false)),
                                        new StaticArray2<Address>(
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                        new StaticArray2<Bytes32>(
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes())),
                                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                        new DynamicBytes("sadfljkjkljkl".getBytes()))));
        List<Type> lo =
                decode.decodeOutPutReturnObject(
                        FunctionEncoder.encode(test),
                        FunctionEncoder.encodeConstructor(
                                Arrays.asList(
                                        new StaticArray2<Uint256>(
                                                new Uint256(11111), new Uint256(33333)),
                                        new StaticArray2<Int256>(
                                                new Int256(-1111111), new Int256(-2222222)),
                                        new StaticArray2<Bool>(new Bool(true), new Bool(false)),
                                        new StaticArray2<Address>(
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                                new Address(
                                                        "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                        new StaticArray2<Bytes32>(
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "abcdefghiabcdefghiabcdefghiabhji"
                                                                .getBytes())),
                                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                        new DynamicBytes("sadfljkjkljkl".getBytes()))));
        assertThat(
                or,
                is(
                        "[{\"name\":\"\",\"type\":\"uint256[2]\",\"data\":[11111,33333]},{\"name\":\"\",\"type\":\"int256[2]\",\"data\":[-1111111,-2222222]},{\"name\":\"\",\"type\":\"bool[2]\",\"data\":[true,false]},{\"name\":\"\",\"type\":\"address[2]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"\",\"type\":\"bytes32[2]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}]"));
        assertThat(
                lo,
                is(
                        Arrays.asList(
                                new StaticArray2<Uint256>(new Uint256(11111), new Uint256(33333)),
                                new StaticArray2<Int256>(
                                        new Int256(-1111111), new Int256(-2222222)),
                                new StaticArray2<Bool>(new Bool(true), new Bool(false)),
                                new StaticArray2<Address>(
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                                new StaticArray2<Bytes32>(
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new DynamicBytes("sadfljkjkljkl".getBytes()))));
    }

    @Test
    public void testTableTest()
            throws JsonProcessingException, TransactionException, BaseException {

        /*
         * contract TableTest {
         *
         * // function select(string name) public constant returns(bytes32[], int[], bytes32[])
         * // function insert(string name, int item_id, string item_name)
         * // function update(string name, int item_id, string item_name)
         * // function remove(string name, int item_id) public returns(int)
         * }
         * */

        String abi =
                "[{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"update\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"}],\"name\":\"remove\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"item_id\",\"type\":\"int256\"},{\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"insert\",\"outputs\":[{\"name\":\"result\",\"type\":\"int256\"},{\"name\":\"result_name\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"addr\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32[]\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"create\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"CreateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"item_name\",\"type\":\"bytes8\"},{\"indexed\":false,\"name\":\"item_id\",\"type\":\"bytes32[]\"}],\"name\":\"InsertResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"UpdateResult\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"RemoveResult\",\"type\":\"event\"}]";
        TransactionDecoder decode = TransactionDecoderFactory.buildTransactionDecoder(abi, "");

        // function select(string name) returns(bytes32[], int[], bytes32[])
        Function select =
                new Function(
                        "select",
                        Arrays.asList(new Utf8String("HelloWorld!")),
                        Arrays.asList(
                                new TypeReference<DynamicArray<Bytes32>>() {},
                                new TypeReference<DynamicArray<Int256>>() {},
                                new TypeReference<DynamicArray<Bytes32>>() {}));

        String selectSR = decode.decodeInputReturnJson(FunctionEncoder.encode(select));

        assertThat(
                selectSR,
                is(
                        "{\"data\":[{\"name\":\"name\",\"type\":\"string\",\"data\":\"HelloWorld!\"}],\"function\":\"select(string)\",\"methodID\":\"0xfcd7e3c1\"}"));

        List<Type> selectOR = decode.decodeInputReturnObject(FunctionEncoder.encode(select));
        assertThat(selectOR, is(Arrays.asList(new Utf8String("HelloWorld!"))));

        String output =
                FunctionEncoder.encodeConstructor(
                        Arrays.asList(
                                new DynamicArray<Bytes32>(
                                        new Bytes32("01234567890123456789012345678912".getBytes()),
                                        new Bytes32("a123456789012345f7890f2345678d12".getBytes()),
                                        new Bytes32("abcdefghijklmnopqrstuvwxyzadfljk".getBytes())),
                                new DynamicArray<Int256>(
                                        new Int256(1234567),
                                        new Int256(-1234567),
                                        new Int256(98877)),
                                new DynamicArray<Bytes32>(
                                        new Bytes32("01234567890123456789012345678912".getBytes()),
                                        new Bytes32("a123456789012345f7890f2345678d12".getBytes()),
                                        new Bytes32(
                                                "abcdefghijklmnopqrstuvwxyzadfljk".getBytes()))));

        String selectSOR = decode.decodeOutputReturnJson(FunctionEncoder.encode(select), output);
        List<Type> selectOutOR =
                decode.decodeOutPutReturnObject(FunctionEncoder.encode(select), output);
        assertThat(
                selectSOR,
                is(
                        "[{\"name\":\"\",\"type\":\"bytes32[]\",\"data\":[\"01234567890123456789012345678912\",\"a123456789012345f7890f2345678d12\",\"abcdefghijklmnopqrstuvwxyzadfljk\"]},{\"name\":\"\",\"type\":\"int256[]\",\"data\":[1234567,-1234567,98877]},{\"name\":\"\",\"type\":\"bytes32[]\",\"data\":[\"01234567890123456789012345678912\",\"a123456789012345f7890f2345678d12\",\"abcdefghijklmnopqrstuvwxyzadfljk\"]}]"));
        assertThat(
                selectOutOR,
                is(
                        Arrays.asList(
                                new DynamicArray<Bytes32>(
                                        new Bytes32("01234567890123456789012345678912".getBytes()),
                                        new Bytes32("a123456789012345f7890f2345678d12".getBytes()),
                                        new Bytes32("abcdefghijklmnopqrstuvwxyzadfljk".getBytes())),
                                new DynamicArray<Int256>(
                                        new Int256(1234567),
                                        new Int256(-1234567),
                                        new Int256(98877)),
                                new DynamicArray<Bytes32>(
                                        new Bytes32("01234567890123456789012345678912".getBytes()),
                                        new Bytes32("a123456789012345f7890f2345678d12".getBytes()),
                                        new Bytes32(
                                                "abcdefghijklmnopqrstuvwxyzadfljk".getBytes())))));

        // function update(string name, int item_id, string item_name)
        Function update =
                new Function(
                        "update",
                        Arrays.asList(
                                new Utf8String("HelloWorld! My First Hello."),
                                new Int256(5555),
                                new Utf8String("Good afternoon")),
                        Collections.<TypeReference<?>>emptyList());

        String updateSR = decode.decodeInputReturnJson(FunctionEncoder.encode(update));
        assertThat(
                updateSR,
                is(
                        "{\"data\":[{\"name\":\"name\",\"type\":\"string\",\"data\":\"HelloWorld! My First Hello.\"},{\"name\":\"item_id\",\"type\":\"int256\",\"data\":5555},{\"name\":\"item_name\",\"type\":\"string\",\"data\":\"Good afternoon\"}],\"function\":\"update(string,int256,string)\",\"methodID\":\"0x487a5a10\"}"));

        List<Type> updateOR = decode.decodeInputReturnObject(FunctionEncoder.encode(update));
        assertThat(
                updateOR,
                is(
                        Arrays.asList(
                                new Utf8String("HelloWorld! My First Hello."),
                                new Int256(5555),
                                new Utf8String("Good afternoon"))));

        // function remove(string name, int item_id) public returns(int)
        Function remove =
                new Function(
                        "remove",
                        Arrays.asList(
                                new Utf8String("HelloWorld! My First Hello."),
                                new Int256(-1111111)),
                        Collections.<TypeReference<?>>emptyList());

        String removeSR = decode.decodeInputReturnJson(FunctionEncoder.encode(remove));

        List<Type> removeOR = decode.decodeInputReturnObject(FunctionEncoder.encode(remove));
        assertThat(
                removeSR,
                is(
                        "{\"data\":[{\"name\":\"name\",\"type\":\"string\",\"data\":\"HelloWorld! My First Hello.\"},{\"name\":\"item_id\",\"type\":\"int256\",\"data\":-1111111}],\"function\":\"remove(string,int256)\",\"methodID\":\"0xc4f41ab3\"}"));
        assertThat(
                removeOR,
                is(
                        Arrays.asList(
                                new Utf8String("HelloWorld! My First Hello."),
                                new Int256(-1111111))));
    }
}
