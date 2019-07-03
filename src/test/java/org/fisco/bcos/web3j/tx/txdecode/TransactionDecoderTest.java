package org.fisco.bcos.web3j.tx.txdecode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.junit.Test;

/* test contract
pragma solidity ^0.4.24;
pragma experimental ABIEncoderV2;
contract TestContract
{
    event TestEventSimpleParams(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs);
	event TestEventDArrayParams(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32, string[] _s,bytes[] _bs);
	event TestEventSArrayParams(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32, string[4] _s,bytes[4] _bs);

	function test(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs) public constant returns (uint256,int256,bool,address,bytes32,string,bytes) {

	}

	function test(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32,string[] _s,bytes _bs[]) public constant returns (uint256[],int256[],bool[],address[],bytes32[],string[],bytes[]) {

	}

	function test(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32,string[4] _s,bytes[4] _bs) public constant returns (uint256[2],int256[2],bool[2],address[2],bytes32[2],string[2],bytes[2]) {

    }
}
*/
public class TransactionDecoderTest {

    public static List<Type> transEntitytoType(List<ResultEntity> entityList) {
        List<Type> listType = new ArrayList<>();
        for (ResultEntity resultEntity : entityList) {
            listType.add(resultEntity.getTypeObject());
        }
        return listType;
    }

    @Test
    public void testSimpleParams0()
            throws JsonProcessingException, TransactionException, BaseException {

        /*
        function test(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs) public constant returns (uint256,int256,bool,address,bytes32,string,bytes)
        */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<Type> test1Params =
                Arrays.asList(
                        new Uint256(111111),
                        new Int256(-1111111),
                        new Bool(false),
                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                        new DynamicBytes("sadfljkjkljkl".getBytes()));

        Function test1 =
                new Function("test", test1Params, Collections.<TypeReference<?>>emptyList());

        String resultInputJson = decode.decodeInputReturnJson(FunctionEncoder.encode(test1));
        List<ResultEntity> resultInputList =
                decode.decodeInputReturnObject(FunctionEncoder.encode(test1));
        List<Type> resultInputListType = transEntitytoType(resultInputList);
        assertThat(
                resultInputJson,
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"_i\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}],\"function\":\"test(uint256,int256,bool,address,bytes32,string,bytes)\",\"methodID\":\"0x58a12c20\"}"));
        assertThat(resultInputListType, is(test1Params));

        String resultOutputJson =
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));

        List<ResultEntity> resultOutputList =
                decode.decodeOutputReturnObject(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));

        List<Type> resultOutputListType = transEntitytoType(resultOutputList);
        assertThat(
                resultOutputJson,
                is(
                        "[{\"name\":\"\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"\",\"type\":\"bool\",\"data\":false},{\"name\":\"\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"},{\"name\":\"\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}]"));
        assertThat(resultOutputListType, is(test1Params));
    }

    @Test
    public void testSimpleParams1()
            throws JsonProcessingException, TransactionException, BaseException {

        /*
        function test(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs) public constant returns (uint256,int256,bool,address,bytes32,string,bytes)
        */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<Type> test1Params =
                Arrays.asList(
                        new Uint256(0),
                        new Int256(0),
                        new Bool(false),
                        new Address("0x0"),
                        new Bytes32("                                ".getBytes()),
                        new Utf8String(""),
                        new DynamicBytes("".getBytes()));

        Function test1 =
                new Function("test", test1Params, Collections.<TypeReference<?>>emptyList());

        String resultInputJson = decode.decodeInputReturnJson(FunctionEncoder.encode(test1));
        List<ResultEntity> resultInputList =
                decode.decodeInputReturnObject(FunctionEncoder.encode(test1));
        List<Type> resultInputListType = transEntitytoType(resultInputList);
        assertThat(
                resultInputJson,
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":0},{\"name\":\"_i\",\"type\":\"int256\",\"data\":0},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x0000000000000000000000000000000000000000\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"\"}],\"function\":\"test(uint256,int256,bool,address,bytes32,string,bytes)\",\"methodID\":\"0x58a12c20\"}"));
        assertThat(resultInputListType, is(test1Params));

        String resultOutputJson =
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));

        List<ResultEntity> resultOutputList =
                decode.decodeOutputReturnObject(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));

        List<Type> resultOutputListType = transEntitytoType(resultOutputList);
        assertThat(
                resultOutputJson,
                is(
                        "[{\"name\":\"\",\"type\":\"uint256\",\"data\":0},{\"name\":\"\",\"type\":\"int256\",\"data\":0},{\"name\":\"\",\"type\":\"bool\",\"data\":false},{\"name\":\"\",\"type\":\"address\",\"data\":\"0x0000000000000000000000000000000000000000\"},{\"name\":\"\",\"type\":\"bytes32\",\"data\":\"\"},{\"name\":\"\",\"type\":\"string\",\"data\":\"\"},{\"name\":\"\",\"type\":\"bytes\",\"data\":\"\"}]"));
        assertThat(resultOutputListType, is(test1Params));
    }

    @Test
    public void testDynamicParams0()
            throws JsonProcessingException, TransactionException, BaseException {

        /*
        function test(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32,string[] _s,bytes[] _bs) public constant returns (uint256[],int256[],bool[],address[],bytes32[],string[],bytes[]) {

        }
           */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<Type> test1Params =
                Arrays.asList(
                        new DynamicArray<Uint256>(
                                new Uint256(11111), new Uint256(22222), new Uint256(33333)),
                        new DynamicArray<Int256>(
                                new Int256(-1111111), new Int256(-3333333), new Int256(-2222222)),
                        new DynamicArray<Bool>(new Bool(false), new Bool(true), new Bool(false)),
                        new DynamicArray<Address>(
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new DynamicArray<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                        new DynamicArray<Utf8String>(
                                new Utf8String(""),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl")),
                        new DynamicArray<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes())));

        Function test1 =
                new Function("test", test1Params, Collections.<TypeReference<?>>emptyList());

        String resultInputJson = decode.decodeInputReturnJson(FunctionEncoder.encode(test1));
        List<ResultEntity> resultInputList =
                decode.decodeInputReturnObject(FunctionEncoder.encode(test1));
        List<Type> resultInputListType = transEntitytoType(resultInputList);
        assertThat(
                resultInputJson,
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}],\"function\":\"test(uint256[],int256[],bool[],address[],bytes32[],string[],bytes[])\",\"methodID\":\"0x6dd9902a\"}"));
        assertThat(resultInputListType, is(test1Params));

        String resultOutputJson =
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));
        List<ResultEntity> lo =
                decode.decodeOutputReturnObject(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));
        assertThat(
                resultOutputJson,
                is(
                        "[{\"name\":\"\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"\",\"type\":\"string[]\",\"data\":[\"\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]},{\"name\":\"\",\"type\":\"bytes[]\",\"data\":[\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}]"));
        assertThat(transEntitytoType(lo), is(test1Params));
    }

    @Test
    public void testDynamicParams1()
            throws JsonProcessingException, TransactionException, BaseException {

        /*
        function test(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32,string[] _s,bytes[] _bs) public constant returns (uint256[],int256[],bool[],address[],bytes32[],string[],bytes[]) {

        }
           */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<Type> test1Params =
                Arrays.asList(
                        new DynamicArray<Uint256>(new Uint256(0), new Uint256(0), new Uint256(0)),
                        new DynamicArray<Int256>(new Int256(0), new Int256(0), new Int256(0)),
                        new DynamicArray<Bool>(new Bool(false), new Bool(true), new Bool(false)),
                        new DynamicArray<Address>(new Address("0x0"), new Address("0x0")),
                        new DynamicArray<Bytes32>(
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes())),
                        new DynamicArray<Utf8String>(
                                new Utf8String(""), new Utf8String(""), new Utf8String("")),
                        new DynamicArray<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes())));

        Function test1 =
                new Function("test", test1Params, Collections.<TypeReference<?>>emptyList());

        String resultInputJson = decode.decodeInputReturnJson(FunctionEncoder.encode(test1));
        List<ResultEntity> resultInputList =
                decode.decodeInputReturnObject(FunctionEncoder.encode(test1));
        List<Type> resultInputListType = transEntitytoType(resultInputList);
        assertThat(
                resultInputJson,
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[0,0,0]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[0,0,0]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"\",\"\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"\",\"\"]}],\"function\":\"test(uint256[],int256[],bool[],address[],bytes32[],string[],bytes[])\",\"methodID\":\"0x6dd9902a\"}"));
        assertThat(resultInputListType, is(test1Params));

        String resultOutputJson =
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));
        List<ResultEntity> lo =
                decode.decodeOutputReturnObject(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Params));
        assertThat(
                resultOutputJson,
                is(
                        "[{\"name\":\"\",\"type\":\"uint256[]\",\"data\":[0,0,0]},{\"name\":\"\",\"type\":\"int256[]\",\"data\":[0,0,0]},{\"name\":\"\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"\",\"type\":\"address[]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\"]},{\"name\":\"\",\"type\":\"bytes32[]\",\"data\":[\"\",\"\"]},{\"name\":\"\",\"type\":\"string[]\",\"data\":[\"\",\"\",\"\"]},{\"name\":\"\",\"type\":\"bytes[]\",\"data\":[\"\",\"\",\"\"]}]"));
        assertThat(transEntitytoType(lo), is(test1Params));
    }

    @Test
    public void testStaticParams0()
            throws JsonProcessingException, TransactionException, BaseException {

        /*
        function test(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32,string[4] _s,bytes[4] _bs) public constant returns (uint256[2],int256[2],bool[2],address[2],bytes32[2],string[2],bytes[2]) {

           }
           */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<Type> test1Params =
                Arrays.asList(
                        new StaticArray4<Uint256>(
                                new Uint256(11111),
                                new Uint256(22222),
                                new Uint256(33333),
                                new Uint256(44444)),
                        new StaticArray4<Int256>(
                                new Int256(-1111111),
                                new Int256(-2222222),
                                new Int256(-3333333),
                                new Int256(-4444444)),
                        new StaticArray4<Bool>(
                                new Bool(true), new Bool(false), new Bool(true), new Bool(false)),
                        new StaticArray4<Address>(
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x0"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new StaticArray4<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("00000000000000000000000000000000".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                        new StaticArray4<Utf8String>(
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("xxxfjlk"),
                                new Utf8String("fdajl;jkdsafjkljkadfjklf"),
                                new Utf8String("")),
                        new StaticArray4<DynamicBytes>(
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes())));

        Function test1 =
                new Function(
                        "test",
                        test1Params,
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicBytes>() {},
                                new TypeReference<Bytes32>() {}));

        assertThat(
                decode.decodeInputReturnJson(FunctionEncoder.encode(test1)),
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256[4]\",\"data\":[11111,22222,33333,44444]},{\"name\":\"_i\",\"type\":\"int256[4]\",\"data\":[-1111111,-2222222,-3333333,-4444444]},{\"name\":\"_b\",\"type\":\"bool[4]\",\"data\":[true,false,true,false]},{\"name\":\"_addr\",\"type\":\"address[4]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x0000000000000000000000000000000000000000\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\",\"00000000000000000000000000000000\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[4]\",\"data\":[\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"xxxfjlk\",\"fdajl;jkdsafjkljkadfjklf\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[4]\",\"data\":[\"sadfljkjkljkl\",\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}],\"function\":\"test(uint256[4],int256[4],bool[4],address[4],bytes32[4],string[4],bytes[4])\",\"methodID\":\"0x5682504e\"}"));

        assertThat(
                transEntitytoType(decode.decodeInputReturnObject(FunctionEncoder.encode(test1))),
                is(test1Params));

        List<Type> test1Output =
                Arrays.asList(
                        new StaticArray2<Uint256>(new Uint256(11111), new Uint256(33333)),
                        new StaticArray2<Int256>(new Int256(-1111111), new Int256(-2222222)),
                        new StaticArray2<Bool>(new Bool(true), new Bool(false)),
                        new StaticArray2<Address>(
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new StaticArray2<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("01234567890123456789012345678901".getBytes())),
                        new StaticArray2<Utf8String>(
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("dasfjklk;jlj")),
                        new StaticArray2<DynamicBytes>(
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes()),
                                new DynamicBytes("dasfjklk;jlj".getBytes())));

        assertThat(
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Output)),
                is(
                        "[{\"name\":\"\",\"type\":\"uint256[2]\",\"data\":[11111,33333]},{\"name\":\"\",\"type\":\"int256[2]\",\"data\":[-1111111,-2222222]},{\"name\":\"\",\"type\":\"bool[2]\",\"data\":[true,false]},{\"name\":\"\",\"type\":\"address[2]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"\",\"type\":\"bytes32[2]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"01234567890123456789012345678901\"]},{\"name\":\"\",\"type\":\"string[2]\",\"data\":[\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"dasfjklk;jlj\"]},{\"name\":\"\",\"type\":\"bytes[2]\",\"data\":[\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"dasfjklk;jlj\"]}]"));
        assertThat(
                transEntitytoType(
                        decode.decodeOutputReturnObject(
                                FunctionEncoder.encode(test1),
                                FunctionEncoder.encodeConstructor(test1Output))),
                is(test1Output));
    }

    @Test
    public void testStaticParams1()
            throws JsonProcessingException, TransactionException, BaseException {

        /*
        function test(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32,string[4] _s,bytes[4] _bs) public constant returns (uint256[2],int256[2],bool[2],address[2],bytes32[2],string[2],bytes[2]) {

           }
           */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<Type> test1Params =
                Arrays.asList(
                        new StaticArray4<Uint256>(
                                new Uint256(0), new Uint256(0), new Uint256(0), new Uint256(0)),
                        new StaticArray4<Int256>(
                                new Int256(0), new Int256(0), new Int256(0), new Int256(0)),
                        new StaticArray4<Bool>(
                                new Bool(true), new Bool(false), new Bool(true), new Bool(false)),
                        new StaticArray4<Address>(
                                new Address("0x0"),
                                new Address("0x0"),
                                new Address("0x0"),
                                new Address("0x0")),
                        new StaticArray4<Bytes32>(
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes())),
                        new StaticArray4<Utf8String>(
                                new Utf8String(""),
                                new Utf8String(""),
                                new Utf8String(""),
                                new Utf8String("")),
                        new StaticArray4<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes())));

        Function test1 =
                new Function(
                        "test",
                        test1Params,
                        Arrays.asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicBytes>() {},
                                new TypeReference<Bytes32>() {}));

        assertThat(
                decode.decodeInputReturnJson(FunctionEncoder.encode(test1)),
                is(
                        "{\"data\":[{\"name\":\"_u\",\"type\":\"uint256[4]\",\"data\":[0,0,0,0]},{\"name\":\"_i\",\"type\":\"int256[4]\",\"data\":[0,0,0,0]},{\"name\":\"_b\",\"type\":\"bool[4]\",\"data\":[true,false,true,false]},{\"name\":\"_addr\",\"type\":\"address[4]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\",\"data\":[\"\",\"\",\"\",\"\"]},{\"name\":\"_s\",\"type\":\"string[4]\",\"data\":[\"\",\"\",\"\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[4]\",\"data\":[\"\",\"\",\"\",\"\"]}],\"function\":\"test(uint256[4],int256[4],bool[4],address[4],bytes32[4],string[4],bytes[4])\",\"methodID\":\"0x5682504e\"}"));

        assertThat(
                transEntitytoType(decode.decodeInputReturnObject(FunctionEncoder.encode(test1))),
                is(test1Params));

        List<Type> test1Output =
                Arrays.asList(
                        new StaticArray2<Uint256>(new Uint256(0), new Uint256(0)),
                        new StaticArray2<Int256>(new Int256(0), new Int256(0)),
                        new StaticArray2<Bool>(new Bool(true), new Bool(false)),
                        new StaticArray2<Address>(new Address("0x0"), new Address("0x0")),
                        new StaticArray2<Bytes32>(
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes())),
                        new StaticArray2<Utf8String>(new Utf8String(""), new Utf8String("")),
                        new StaticArray2<DynamicBytes>(
                                new DynamicBytes("".getBytes()), new DynamicBytes("".getBytes())));

        assertThat(
                decode.decodeOutputReturnJson(
                        FunctionEncoder.encode(test1),
                        FunctionEncoder.encodeConstructor(test1Output)),
                is(
                        "[{\"name\":\"\",\"type\":\"uint256[2]\",\"data\":[0,0]},{\"name\":\"\",\"type\":\"int256[2]\",\"data\":[0,0]},{\"name\":\"\",\"type\":\"bool[2]\",\"data\":[true,false]},{\"name\":\"\",\"type\":\"address[2]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\"]},{\"name\":\"\",\"type\":\"bytes32[2]\",\"data\":[\"\",\"\"]},{\"name\":\"\",\"type\":\"string[2]\",\"data\":[\"\",\"\"]},{\"name\":\"\",\"type\":\"bytes[2]\",\"data\":[\"\",\"\"]}]"));
        assertThat(
                transEntitytoType(
                        decode.decodeOutputReturnObject(
                                FunctionEncoder.encode(test1),
                                FunctionEncoder.encodeConstructor(test1Output))),
                is(test1Output));
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

        List<ResultEntity> selectOR =
                decode.decodeInputReturnObject(FunctionEncoder.encode(select));
        assertThat(transEntitytoType(selectOR), is(Arrays.asList(new Utf8String("HelloWorld!"))));

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
        List<ResultEntity> selectOutOR =
                decode.decodeOutputReturnObject(FunctionEncoder.encode(select), output);
        assertThat(
                selectSOR,
                is(
                        "[{\"name\":\"\",\"type\":\"bytes32[]\",\"data\":[\"01234567890123456789012345678912\",\"a123456789012345f7890f2345678d12\",\"abcdefghijklmnopqrstuvwxyzadfljk\"]},{\"name\":\"\",\"type\":\"int256[]\",\"data\":[1234567,-1234567,98877]},{\"name\":\"\",\"type\":\"bytes32[]\",\"data\":[\"01234567890123456789012345678912\",\"a123456789012345f7890f2345678d12\",\"abcdefghijklmnopqrstuvwxyzadfljk\"]}]"));
        assertThat(
                transEntitytoType(selectOutOR),
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

        List<ResultEntity> updateOR =
                decode.decodeInputReturnObject(FunctionEncoder.encode(update));
        assertThat(
                transEntitytoType(updateOR),
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

        List<ResultEntity> removeOR =
                decode.decodeInputReturnObject(FunctionEncoder.encode(remove));
        assertThat(
                removeSR,
                is(
                        "{\"data\":[{\"name\":\"name\",\"type\":\"string\",\"data\":\"HelloWorld! My First Hello.\"},{\"name\":\"item_id\",\"type\":\"int256\",\"data\":-1111111}],\"function\":\"remove(string,int256)\",\"methodID\":\"0xc4f41ab3\"}"));
        assertThat(
                transEntitytoType(removeOR),
                is(
                        Arrays.asList(
                                new Utf8String("HelloWorld! My First Hello."),
                                new Int256(-1111111))));
    }

    private String decodeMethodSign(AbiDefinition abiDefinition) {
        List<String> inputTypes = ContractAbiUtil.getFuncInputType(abiDefinition);
        StringBuilder methodSign = new StringBuilder();
        methodSign.append(abiDefinition.getName());
        methodSign.append("(");
        String params = inputTypes.stream().map(String::toString).collect(Collectors.joining(","));
        methodSign.append(params);
        methodSign.append(")");
        return methodSign.toString();
    }

    /*
     * event TestEventSimpleParams(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs);
     * event TestEventDArrayParams(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32, string[] _s,bytes[] _bs);
     * event TestEventSArrayParams(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32, string[4] _s,bytes[4] _bs);
     */

    @Test
    public void testEventSimple() throws BaseException, IOException {
        /*
         	event TestEventSimpleParams(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs);
        */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<TypeReference<?>> eventTypeList =
                Arrays.asList(
                        new TypeReference<Uint256>() {},
                        new TypeReference<Int256>() {},
                        new TypeReference<Bool>() {},
                        new TypeReference<Address>() {},
                        new TypeReference<Bytes32>() {},
                        new TypeReference<Utf8String>() {},
                        new TypeReference<DynamicBytes>() {});

        Event event = new Event("TestEventSimpleParams", eventTypeList);

        List<Type> eventDataParams1 =
                Arrays.asList(
                        new Uint256(111111),
                        new Int256(-1111111),
                        new Bool(false),
                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                        new DynamicBytes("sadfljkjkljkl".getBytes()));

        List<Type> eventDataParams2 =
                Arrays.asList(
                        new Uint256(0),
                        new Int256(0),
                        new Bool(true),
                        new Address("0x0"),
                        new Bytes32("                                ".getBytes()),
                        new Utf8String(""),
                        new DynamicBytes("".getBytes()));

        List<Type> eventDataParams3 =
                Arrays.asList(
                        new Uint256(654321),
                        new Int256(123456),
                        new Bool(false),
                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                        new Bytes32("                                ".getBytes()),
                        new Utf8String("jlkjlkjasdfjlkj   fsadjkljlk j章鱼小丸子"),
                        new DynamicBytes("jlkjlkjasdfjlkj   fsadjkljlk j章鱼小丸子".getBytes()));

        List<String> topics = new ArrayList<String>();
        topics.add(EventEncoder.encode(event));

        Log log1 = new Log();
        log1.setData(FunctionEncoder.encodeConstructor(eventDataParams1));
        log1.setTopics(topics);

        Log log2 = new Log();
        log2.setData(FunctionEncoder.encodeConstructor(eventDataParams2));
        log2.setTopics(topics);

        Log log3 = new Log();
        log3.setData(FunctionEncoder.encodeConstructor(eventDataParams3));
        log3.setTopics(topics);

        AbiDefinition abiDefinition = null;

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult1 =
                decode.decodeEventReturnObject(log1);
        assertThat(transEntitytoType(tupleResult1.getValue2()), is(eventDataParams1));
        abiDefinition = tupleResult1.getValue1();

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult2 =
                decode.decodeEventReturnObject(log2);
        assertThat(transEntitytoType(tupleResult2.getValue2()), is(eventDataParams2));

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult3 =
                decode.decodeEventReturnObject(log3);
        assertThat(transEntitytoType(tupleResult3.getValue2()), is(eventDataParams3));

        List<Log> logList1 = new ArrayList<Log>();
        logList1.add(log1);
        Map<String, List<List<ResultEntity>>> mapResult1 = decode.decodeEventReturnObject(logList1);
        assertThat(
                transEntitytoType(mapResult1.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        assertThat(
                decode.decodeEventReturnJson(logList1),
                is(
                        "{\"TestEventSimpleParams(uint256,int256,bool,address,bytes32,string,bytes)\":[[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"_i\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}]]}"));

        List<Log> logList2 = new ArrayList<Log>();
        logList2.add(log1);
        logList2.add(log2);
        Map<String, List<List<ResultEntity>>> mapResult2 = decode.decodeEventReturnObject(logList2);
        assertThat(
                transEntitytoType(mapResult2.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        assertThat(
                transEntitytoType(mapResult2.get(decodeMethodSign(abiDefinition)).get(1)),
                is(eventDataParams2));
        assertThat(
                decode.decodeEventReturnJson(logList2),
                is(
                        "{\"TestEventSimpleParams(uint256,int256,bool,address,bytes32,string,bytes)\":[[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"_i\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}],[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":0},{\"name\":\"_i\",\"type\":\"int256\",\"data\":0},{\"name\":\"_b\",\"type\":\"bool\",\"data\":true},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x0000000000000000000000000000000000000000\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"\"}]]}"));

        List<Log> logList3 = new ArrayList<Log>();
        logList3.add(log1);
        logList3.add(log2);
        logList3.add(log3);
        Map<String, List<List<ResultEntity>>> mapResult3 = decode.decodeEventReturnObject(logList3);
        assertThat(
                transEntitytoType(mapResult3.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        assertThat(
                transEntitytoType(mapResult3.get(decodeMethodSign(abiDefinition)).get(1)),
                is(eventDataParams2));
        assertThat(
                transEntitytoType(mapResult3.get(decodeMethodSign(abiDefinition)).get(2)),
                is(eventDataParams3));
        assertThat(
                decode.decodeEventReturnJson(logList3),
                is(
                        "{\"TestEventSimpleParams(uint256,int256,bool,address,bytes32,string,bytes)\":[[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"_i\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}],[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":0},{\"name\":\"_i\",\"type\":\"int256\",\"data\":0},{\"name\":\"_b\",\"type\":\"bool\",\"data\":true},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x0000000000000000000000000000000000000000\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"\"}],[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":654321},{\"name\":\"_i\",\"type\":\"int256\",\"data\":123456},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"jlkjlkjasdfjlkj   fsadjkljlk j章鱼小丸子\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"jlkjlkjasdfjlkj   fsadjkljlk j章鱼小丸子\"}]]}"));
    }

    @Test
    public void testEventDArray() throws BaseException, IOException {
        /*
         	event TestEventDArrayParams(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32, string[] _s,bytes[] _bs);
        */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<TypeReference<?>> eventTypeList =
                Arrays.asList(
                        new TypeReference<DynamicArray<Uint256>>() {},
                        new TypeReference<DynamicArray<Int256>>() {},
                        new TypeReference<DynamicArray<Bool>>() {},
                        new TypeReference<DynamicArray<Address>>() {},
                        new TypeReference<DynamicArray<Bytes32>>() {},
                        new TypeReference<DynamicArray<Utf8String>>() {},
                        new TypeReference<DynamicArray<DynamicBytes>>() {});

        Event event = new Event("TestEventDArrayParams", eventTypeList);

        List<Type> eventDataParams1 =
                Arrays.asList(
                        new DynamicArray<Uint256>(
                                new Uint256(11111), new Uint256(22222), new Uint256(33333)),
                        new DynamicArray<Int256>(
                                new Int256(-1111111), new Int256(-3333333), new Int256(-2222222)),
                        new DynamicArray<Bool>(new Bool(false), new Bool(true), new Bool(false)),
                        new DynamicArray<Address>(
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new DynamicArray<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                        new DynamicArray<Utf8String>(
                                new Utf8String(""),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl")),
                        new DynamicArray<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes())));

        List<Type> eventDataParams2 =
                Arrays.asList(
                        new DynamicArray<Uint256>(new Uint256(0), new Uint256(0), new Uint256(0)),
                        new DynamicArray<Int256>(new Int256(0), new Int256(0), new Int256(0)),
                        new DynamicArray<Bool>(new Bool(false), new Bool(true), new Bool(false)),
                        new DynamicArray<Address>(new Address("0x0"), new Address("0x0")),
                        new DynamicArray<Bytes32>(
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes())),
                        new DynamicArray<Utf8String>(
                                new Utf8String(""), new Utf8String(""), new Utf8String("")),
                        new DynamicArray<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes())));

        List<Type> eventDataParams3 =
                Arrays.asList(
                        new DynamicArray<Uint256>(
                                new Uint256(0),
                                new Uint256(0),
                                new Uint256(0),
                                new Uint256(11111),
                                new Uint256(22222),
                                new Uint256(33333)),
                        new DynamicArray<Int256>(
                                new Int256(0),
                                new Int256(0),
                                new Int256(0),
                                new Int256(-1111111),
                                new Int256(-3333333),
                                new Int256(-2222222)),
                        new DynamicArray<Bool>(
                                new Bool(false), new Bool(true), new Bool(false), new Bool(false)),
                        new DynamicArray<Address>(
                                new Address("0x0"),
                                new Address("0x0"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new DynamicArray<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes())),
                        new DynamicArray<Utf8String>(
                                new Utf8String(""),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String(""),
                                new Utf8String(""),
                                new Utf8String("")),
                        new DynamicArray<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes())));

        List<String> topics = new ArrayList<String>();
        topics.add(EventEncoder.encode(event));

        Log log1 = new Log();
        log1.setData(FunctionEncoder.encodeConstructor(eventDataParams1));
        log1.setTopics(topics);

        Log log2 = new Log();
        log2.setData(FunctionEncoder.encodeConstructor(eventDataParams2));
        log2.setTopics(topics);

        Log log3 = new Log();
        log3.setData(FunctionEncoder.encodeConstructor(eventDataParams3));
        log3.setTopics(topics);

        AbiDefinition abiDefinition = null;

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult1 =
                decode.decodeEventReturnObject(log1);
        assertThat(transEntitytoType(tupleResult1.getValue2()), is(eventDataParams1));
        abiDefinition = tupleResult1.getValue1();

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult2 =
                decode.decodeEventReturnObject(log2);
        assertThat(transEntitytoType(tupleResult2.getValue2()), is(eventDataParams2));

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult3 =
                decode.decodeEventReturnObject(log3);
        assertThat(transEntitytoType(tupleResult3.getValue2()), is(eventDataParams3));

        List<Log> logList1 = new ArrayList<Log>();
        logList1.add(log1);
        Map<String, List<List<ResultEntity>>> mapResult1 = decode.decodeEventReturnObject(logList1);
        assertThat(
                transEntitytoType(mapResult1.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        // System.out.println("111 => " + decode.decodeEventReturnJson(logList1));
        assertThat(
                decode.decodeEventReturnJson(logList1),
                is(
                        "{\"TestEventDArrayParams(uint256[],int256[],bool[],address[],bytes32[],string[],bytes[])\":[[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}]]}"));

        List<Log> logList2 = new ArrayList<Log>();
        logList2.add(log1);
        logList2.add(log2);
        Map<String, List<List<ResultEntity>>> mapResult2 = decode.decodeEventReturnObject(logList2);
        assertThat(
                transEntitytoType(mapResult2.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        assertThat(
                transEntitytoType(mapResult2.get(decodeMethodSign(abiDefinition)).get(1)),
                is(eventDataParams2));
        // System.out.println("222 => " + decode.decodeEventReturnJson(logList2));
        assertThat(
                decode.decodeEventReturnJson(logList2),
                is(
                        "{\"TestEventDArrayParams(uint256[],int256[],bool[],address[],bytes32[],string[],bytes[])\":[[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}],[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[0,0,0]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[0,0,0]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"\",\"\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"\",\"\"]}]]}"));

        List<Log> logList3 = new ArrayList<Log>();
        logList3.add(log1);
        logList3.add(log2);
        logList3.add(log3);
        Map<String, List<List<ResultEntity>>> mapResult3 = decode.decodeEventReturnObject(logList3);
        assertThat(
                transEntitytoType(mapResult3.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        assertThat(
                transEntitytoType(mapResult3.get(decodeMethodSign(abiDefinition)).get(1)),
                is(eventDataParams2));
        assertThat(
                transEntitytoType(mapResult3.get(decodeMethodSign(abiDefinition)).get(2)),
                is(eventDataParams3));
        // System.out.println("333 => " + decode.decodeEventReturnJson(logList3));
        assertThat(
                decode.decodeEventReturnJson(logList3),
                is(
                        "{\"TestEventDArrayParams(uint256[],int256[],bool[],address[],bytes32[],string[],bytes[])\":[[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}],[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[0,0,0]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[0,0,0]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"\",\"\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"\",\"\"]}],[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[0,0,0,11111,22222,33333]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[0,0,0,-1111111,-3333333,-2222222]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\",\"\",\"\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"\",\"\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"\",\"\",\"\"]}]]}"));
    }

    @Test
    public void testEventSArray() throws BaseException, IOException {
        /*
        		event TestEventSArrayParams(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32, string[4] _s,bytes[4] _bs);
        */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<TypeReference<?>> eventTypeList =
                Arrays.asList(
                        new TypeReference<StaticArray4<Uint256>>() {},
                        new TypeReference<StaticArray4<Int256>>() {},
                        new TypeReference<StaticArray4<Bool>>() {},
                        new TypeReference<StaticArray4<Address>>() {},
                        new TypeReference<StaticArray4<Bytes32>>() {},
                        new TypeReference<StaticArray4<Utf8String>>() {},
                        new TypeReference<StaticArray4<DynamicBytes>>() {});

        Event event = new Event("TestEventSArrayParams", eventTypeList);

        List<Type> eventDataParams1 =
                Arrays.asList(
                        new StaticArray4<Uint256>(
                                new Uint256(11111),
                                new Uint256(22222),
                                new Uint256(33333),
                                new Uint256(44444)),
                        new StaticArray4<Int256>(
                                new Int256(-1111111),
                                new Int256(-2222222),
                                new Int256(-3333333),
                                new Int256(-4444444)),
                        new StaticArray4<Bool>(
                                new Bool(true), new Bool(false), new Bool(true), new Bool(false)),
                        new StaticArray4<Address>(
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x0"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new StaticArray4<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("00000000000000000000000000000000".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                        new StaticArray4<Utf8String>(
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("xxxfjlk"),
                                new Utf8String("fdajl;jkdsafjkljkadfjklf"),
                                new Utf8String("")),
                        new StaticArray4<DynamicBytes>(
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes())));

        List<Type> eventDataParams2 =
                Arrays.asList(
                        new StaticArray4<Uint256>(
                                new Uint256(0), new Uint256(0), new Uint256(0), new Uint256(0)),
                        new StaticArray4<Int256>(
                                new Int256(0), new Int256(0), new Int256(0), new Int256(0)),
                        new StaticArray4<Bool>(
                                new Bool(true), new Bool(false), new Bool(true), new Bool(false)),
                        new StaticArray4<Address>(
                                new Address("0x0"),
                                new Address("0x0"),
                                new Address("0x0"),
                                new Address("0x0")),
                        new StaticArray4<Bytes32>(
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes()),
                                new Bytes32("                                ".getBytes())),
                        new StaticArray4<Utf8String>(
                                new Utf8String(""),
                                new Utf8String(""),
                                new Utf8String(""),
                                new Utf8String("")),
                        new StaticArray4<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("".getBytes())));

        List<String> topics = new ArrayList<String>();
        topics.add(EventEncoder.encode(event));

        Log log1 = new Log();
        log1.setData(FunctionEncoder.encodeConstructor(eventDataParams1));
        log1.setTopics(topics);

        Log log2 = new Log();
        log2.setData(FunctionEncoder.encodeConstructor(eventDataParams2));
        log2.setTopics(topics);

        AbiDefinition abiDefinition = null;

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult1 =
                decode.decodeEventReturnObject(log1);
        assertThat(transEntitytoType(tupleResult1.getValue2()), is(eventDataParams1));
        abiDefinition = tupleResult1.getValue1();

        Tuple2<AbiDefinition, List<ResultEntity>> tupleResult2 =
                decode.decodeEventReturnObject(log2);
        assertThat(transEntitytoType(tupleResult2.getValue2()), is(eventDataParams2));

        List<Log> logList1 = new ArrayList<Log>();
        logList1.add(log1);
        Map<String, List<List<ResultEntity>>> mapResult1 = decode.decodeEventReturnObject(logList1);
        assertThat(
                transEntitytoType(mapResult1.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        // System.out.println("111 => " + decode.decodeEventReturnJson(logList1));
        assertThat(
                decode.decodeEventReturnJson(logList1),
                is(
                        "{\"TestEventSArrayParams(uint256[4],int256[4],bool[4],address[4],bytes32[4],string[4],bytes[4])\":[[{\"name\":\"_u\",\"type\":\"uint256[4]\",\"data\":[11111,22222,33333,44444]},{\"name\":\"_i\",\"type\":\"int256[4]\",\"data\":[-1111111,-2222222,-3333333,-4444444]},{\"name\":\"_b\",\"type\":\"bool[4]\",\"data\":[true,false,true,false]},{\"name\":\"_addr\",\"type\":\"address[4]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x0000000000000000000000000000000000000000\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\",\"00000000000000000000000000000000\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[4]\",\"data\":[\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"xxxfjlk\",\"fdajl;jkdsafjkljkadfjklf\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[4]\",\"data\":[\"sadfljkjkljkl\",\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}]]}"));

        List<Log> logList2 = new ArrayList<Log>();
        logList2.add(log1);
        logList2.add(log2);
        Map<String, List<List<ResultEntity>>> mapResult2 = decode.decodeEventReturnObject(logList2);
        assertThat(
                transEntitytoType(mapResult2.get(decodeMethodSign(abiDefinition)).get(0)),
                is(eventDataParams1));
        assertThat(
                transEntitytoType(mapResult2.get(decodeMethodSign(abiDefinition)).get(1)),
                is(eventDataParams2));
        // System.out.println("222 => " + decode.decodeEventReturnJson(logList2));
        assertThat(
                decode.decodeEventReturnJson(logList2),
                is(
                        "{\"TestEventSArrayParams(uint256[4],int256[4],bool[4],address[4],bytes32[4],string[4],bytes[4])\":[[{\"name\":\"_u\",\"type\":\"uint256[4]\",\"data\":[11111,22222,33333,44444]},{\"name\":\"_i\",\"type\":\"int256[4]\",\"data\":[-1111111,-2222222,-3333333,-4444444]},{\"name\":\"_b\",\"type\":\"bool[4]\",\"data\":[true,false,true,false]},{\"name\":\"_addr\",\"type\":\"address[4]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x0000000000000000000000000000000000000000\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\",\"00000000000000000000000000000000\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[4]\",\"data\":[\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"xxxfjlk\",\"fdajl;jkdsafjkljkadfjklf\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[4]\",\"data\":[\"sadfljkjkljkl\",\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}],[{\"name\":\"_u\",\"type\":\"uint256[4]\",\"data\":[0,0,0,0]},{\"name\":\"_i\",\"type\":\"int256[4]\",\"data\":[0,0,0,0]},{\"name\":\"_b\",\"type\":\"bool[4]\",\"data\":[true,false,true,false]},{\"name\":\"_addr\",\"type\":\"address[4]\",\"data\":[\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\",\"data\":[\"\",\"\",\"\",\"\"]},{\"name\":\"_s\",\"type\":\"string[4]\",\"data\":[\"\",\"\",\"\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[4]\",\"data\":[\"\",\"\",\"\",\"\"]}]]}"));
    }

    @Test
    public void testMixedEvent() throws BaseException, IOException {
        /*
         * event TestEventSimpleParams(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs);
         * event TestEventDArrayParams(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32, string[] _s,bytes[] _bs);
         * event TestEventSArrayParams(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32, string[4] _s,bytes[4] _bs);
         */

        TransactionDecoder decode =
                TransactionDecoderFactory.buildTransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"name\":\"_i\",\"type\":\"int256[4]\"},{\"name\":\"_b\",\"type\":\"bool[4]\"},{\"name\":\"_addr\",\"type\":\"address[4]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"name\":\"_s\",\"type\":\"string[4]\"},{\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[2]\"},{\"name\":\"\",\"type\":\"int256[2]\"},{\"name\":\"\",\"type\":\"bool[2]\"},{\"name\":\"\",\"type\":\"address[2]\"},{\"name\":\"\",\"type\":\"bytes32[2]\"},{\"name\":\"\",\"type\":\"string[2]\"},{\"name\":\"\",\"type\":\"bytes[2]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_b\",\"type\":\"bool\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"bytes\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256[]\"},{\"name\":\"_i\",\"type\":\"int256[]\"},{\"name\":\"_b\",\"type\":\"bool[]\"},{\"name\":\"_addr\",\"type\":\"address[]\"},{\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"name\":\"_s\",\"type\":\"string[]\"},{\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"test\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\"},{\"name\":\"\",\"type\":\"int256[]\"},{\"name\":\"\",\"type\":\"bool[]\"},{\"name\":\"\",\"type\":\"address[]\"},{\"name\":\"\",\"type\":\"bytes32[]\"},{\"name\":\"\",\"type\":\"string[]\"},{\"name\":\"\",\"type\":\"bytes[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes\"}],\"name\":\"TestEventSimpleParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[]\"}],\"name\":\"TestEventDArrayParams\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_u\",\"type\":\"uint256[4]\"},{\"indexed\":false,\"name\":\"_i\",\"type\":\"int256[4]\"},{\"indexed\":false,\"name\":\"_b\",\"type\":\"bool[4]\"},{\"indexed\":false,\"name\":\"_addr\",\"type\":\"address[4]\"},{\"indexed\":false,\"name\":\"_bs32\",\"type\":\"bytes32[4]\"},{\"indexed\":false,\"name\":\"_s\",\"type\":\"string[4]\"},{\"indexed\":false,\"name\":\"_bs\",\"type\":\"bytes[4]\"}],\"name\":\"TestEventSArrayParams\",\"type\":\"event\"}]",
                        "");

        List<TypeReference<?>> event1TypeList =
                Arrays.asList(
                        new TypeReference<Uint256>() {},
                        new TypeReference<Int256>() {},
                        new TypeReference<Bool>() {},
                        new TypeReference<Address>() {},
                        new TypeReference<Bytes32>() {},
                        new TypeReference<Utf8String>() {},
                        new TypeReference<DynamicBytes>() {});

        Event event1 = new Event("TestEventSimpleParams", event1TypeList);

        List<TypeReference<?>> event2TypeList =
                Arrays.asList(
                        new TypeReference<DynamicArray<Uint256>>() {},
                        new TypeReference<DynamicArray<Int256>>() {},
                        new TypeReference<DynamicArray<Bool>>() {},
                        new TypeReference<DynamicArray<Address>>() {},
                        new TypeReference<DynamicArray<Bytes32>>() {},
                        new TypeReference<DynamicArray<Utf8String>>() {},
                        new TypeReference<DynamicArray<DynamicBytes>>() {});

        Event event2 = new Event("TestEventDArrayParams", event2TypeList);

        List<TypeReference<?>> event3TypeList =
                Arrays.asList(
                        new TypeReference<StaticArray4<Uint256>>() {},
                        new TypeReference<StaticArray4<Int256>>() {},
                        new TypeReference<StaticArray4<Bool>>() {},
                        new TypeReference<StaticArray4<Address>>() {},
                        new TypeReference<StaticArray4<Bytes32>>() {},
                        new TypeReference<StaticArray4<Utf8String>>() {},
                        new TypeReference<StaticArray4<DynamicBytes>>() {});

        Event event3 = new Event("TestEventSArrayParams", event3TypeList);

        List<Type> eventDataParams1 =
                Arrays.asList(
                        new Uint256(111111),
                        new Int256(-1111111),
                        new Bool(false),
                        new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                        new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                        new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                        new DynamicBytes("sadfljkjkljkl".getBytes()));

        List<String> topics1 = new ArrayList<String>();
        topics1.add(EventEncoder.encode(event1));
        Log log1 = new Log();
        log1.setData(FunctionEncoder.encodeConstructor(eventDataParams1));
        log1.setTopics(topics1);

        List<Type> eventDataParams2 =
                Arrays.asList(
                        new DynamicArray<Uint256>(
                                new Uint256(11111), new Uint256(22222), new Uint256(33333)),
                        new DynamicArray<Int256>(
                                new Int256(-1111111), new Int256(-3333333), new Int256(-2222222)),
                        new DynamicArray<Bool>(new Bool(false), new Bool(true), new Bool(false)),
                        new DynamicArray<Address>(
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new DynamicArray<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                        new DynamicArray<Utf8String>(
                                new Utf8String(""),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl")),
                        new DynamicArray<DynamicBytes>(
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes())));

        List<String> topics2 = new ArrayList<String>();
        topics2.add(EventEncoder.encode(event2));

        Log log2 = new Log();
        log2.setData(FunctionEncoder.encodeConstructor(eventDataParams2));
        log2.setTopics(topics2);

        List<Type> eventDataParams3 =
                Arrays.asList(
                        new StaticArray4<Uint256>(
                                new Uint256(11111),
                                new Uint256(22222),
                                new Uint256(33333),
                                new Uint256(44444)),
                        new StaticArray4<Int256>(
                                new Int256(-1111111),
                                new Int256(-2222222),
                                new Int256(-3333333),
                                new Int256(-4444444)),
                        new StaticArray4<Bool>(
                                new Bool(true), new Bool(false), new Bool(true), new Bool(false)),
                        new StaticArray4<Address>(
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a"),
                                new Address("0x0"),
                                new Address("0x692a70d2e424a56d2c6c27aa97d1a86395877b3a")),
                        new StaticArray4<Bytes32>(
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes()),
                                new Bytes32("00000000000000000000000000000000".getBytes()),
                                new Bytes32("abcdefghiabcdefghiabcdefghiabhji".getBytes())),
                        new StaticArray4<Utf8String>(
                                new Utf8String("章鱼小丸子ljjkl;adjsfkljlkjl"),
                                new Utf8String("xxxfjlk"),
                                new Utf8String("fdajl;jkdsafjkljkadfjklf"),
                                new Utf8String("")),
                        new StaticArray4<DynamicBytes>(
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("".getBytes()),
                                new DynamicBytes("sadfljkjkljkl".getBytes()),
                                new DynamicBytes("章鱼小丸子ljjkl;adjsfkljlkjl".getBytes())));

        List<String> topics3 = new ArrayList<String>();
        topics3.add(EventEncoder.encode(event3));

        Log log3 = new Log();
        log3.setData(FunctionEncoder.encodeConstructor(eventDataParams3));
        log3.setTopics(topics3);

        List<Log> logList = new ArrayList<Log>();
        logList.add(log1);
        logList.add(log2);
        logList.add(log3);

        // System.out.println(" => " + decode.decodeEventReturnJson(logList));
        assertThat(
                decode.decodeEventReturnJson(logList),
                is(
                        "{\"TestEventDArrayParams(uint256[],int256[],bool[],address[],bytes32[],string[],bytes[])\":[[{\"name\":\"_u\",\"type\":\"uint256[]\",\"data\":[11111,22222,33333]},{\"name\":\"_i\",\"type\":\"int256[]\",\"data\":[-1111111,-3333333,-2222222]},{\"name\":\"_b\",\"type\":\"bool[]\",\"data\":[false,true,false]},{\"name\":\"_addr\",\"type\":\"address[]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[]\",\"data\":[\"\",\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]},{\"name\":\"_bs\",\"type\":\"bytes[]\",\"data\":[\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}]],\"TestEventSArrayParams(uint256[4],int256[4],bool[4],address[4],bytes32[4],string[4],bytes[4])\":[[{\"name\":\"_u\",\"type\":\"uint256[4]\",\"data\":[11111,22222,33333,44444]},{\"name\":\"_i\",\"type\":\"int256[4]\",\"data\":[-1111111,-2222222,-3333333,-4444444]},{\"name\":\"_b\",\"type\":\"bool[4]\",\"data\":[true,false,true,false]},{\"name\":\"_addr\",\"type\":\"address[4]\",\"data\":[\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\"0x0000000000000000000000000000000000000000\",\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"]},{\"name\":\"_bs32\",\"type\":\"bytes32[4]\",\"data\":[\"abcdefghiabcdefghiabcdefghiabhji\",\"abcdefghiabcdefghiabcdefghiabhji\",\"00000000000000000000000000000000\",\"abcdefghiabcdefghiabcdefghiabhji\"]},{\"name\":\"_s\",\"type\":\"string[4]\",\"data\":[\"章鱼小丸子ljjkl;adjsfkljlkjl\",\"xxxfjlk\",\"fdajl;jkdsafjkljkadfjklf\",\"\"]},{\"name\":\"_bs\",\"type\":\"bytes[4]\",\"data\":[\"sadfljkjkljkl\",\"\",\"sadfljkjkljkl\",\"章鱼小丸子ljjkl;adjsfkljlkjl\"]}]],\"TestEventSimpleParams(uint256,int256,bool,address,bytes32,string,bytes)\":[[{\"name\":\"_u\",\"type\":\"uint256\",\"data\":111111},{\"name\":\"_i\",\"type\":\"int256\",\"data\":-1111111},{\"name\":\"_b\",\"type\":\"bool\",\"data\":false},{\"name\":\"_addr\",\"type\":\"address\",\"data\":\"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"},{\"name\":\"_bs32\",\"type\":\"bytes32\",\"data\":\"abcdefghiabcdefghiabcdefghiabhji\"},{\"name\":\"_s\",\"type\":\"string\",\"data\":\"章鱼小丸子ljjkl;adjsfkljlkjl\"},{\"name\":\"_bs\",\"type\":\"bytes\",\"data\":\"sadfljkjkljkl\"}]]}"));
    }
}
