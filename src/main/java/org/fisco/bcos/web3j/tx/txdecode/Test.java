package org.fisco.bcos.web3j.tx.txdecode;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;

public class Test {

    public static void testOK()
            throws JsonProcessingException, TransactionException, BaseException {
        /*
         * pragma solidity ^0.4.24;
         *
         * contract Ok { function set(uint256 _u,int256 _i,address _addr,string _s,
         * uint256[3] _us,uint256[] _ud) public constant returns (uint256 ,int256
         * ,address ,string , uint256[3],uint256[]){
         *
         * } }
         */

        // [{"constant":true,"inputs":[{"name":"_u","type":"uint256"},{"name":"_i","type":"int256"},{"name":"_addr","type":"address"},{"name":"_s","type":"string"},{"name":"_us","type":"uint256[3]"},{"name":"_ud","type":"uint256[]"}],"name":"set","outputs":[{"name":"","type":"uint256"},{"name":"","type":"int256"},{"name":"","type":"address"},{"name":"","type":"string"},{"name":"","type":"uint256[3]"},{"name":"","type":"uint256[]"}],"payable":false,"stateMutability":"view","type":"function"}]

        TransactionDecoder decode =
                new TransactionDecoder(
                        "[{\"constant\":true,\"inputs\":[{\"name\":\"_u\",\"type\":\"uint256\"},{\"name\":\"_i\",\"type\":\"int256\"},{\"name\":\"_addr\",\"type\":\"address\"},{\"name\":\"_s\",\"type\":\"string\"},{\"name\":\"_us\",\"type\":\"uint256[3]\"},{\"name\":\"_ud\",\"type\":\"uint256[]\"}],\"name\":\"set\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256[3]\"},{\"name\":\"\",\"type\":\"uint256[]\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]",
                        "");

        String sr =
                decode.decodeInputReturnJson(
                        "0x5ca6074a000000000000000000000000000000000000000000000000000000000000006f00000000000000000000000000000000000000000000000000000000000008ae000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000016617364666c6a6b6a6c6b6164666a6c6a6c6b6a6c6b660000000000000000000000000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000007");
        System.out.println("input string => " + sr);
        System.out.println();

        List<Type> lr =
                decode.decodeInputReturnObject(
                        "0x5ca6074a000000000000000000000000000000000000000000000000000000000000006f00000000000000000000000000000000000000000000000000000000000008ae000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000016617364666c6a6b6a6c6b6164666a6c6a6c6b6a6c6b660000000000000000000000000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000007");
        System.out.println("input object => " + lr);
        System.out.println();

        String osr =
                decode.decodeOutputReturnJson(
                        "0x5ca6074a000000000000000000000000000000000000000000000000000000000000006f00000000000000000000000000000000000000000000000000000000000008ae000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000016617364666c6a6b6a6c6b6164666a6c6a6c6b6a6c6b660000000000000000000000000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000007",
                        "0x000000000000000000000000000000000000000000000000000000000000006f00000000000000000000000000000000000000000000000000000000000008ae000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000016617364666c6a6b6a6c6b6164666a6c6a6c6b6a6c6b660000000000000000000000000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000007");
        System.out.println("output string => " + osr);
        System.out.println();

        List<Type> olr =
                decode.decodeOutPutReturnObject(
                        "0x5ca6074a000000000000000000000000000000000000000000000000000000000000006f00000000000000000000000000000000000000000000000000000000000008ae000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000016617364666c6a6b6a6c6b6164666a6c6a6c6b6a6c6b660000000000000000000000000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000007",
                        "0x000000000000000000000000000000000000000000000000000000000000006f00000000000000000000000000000000000000000000000000000000000008ae000000000000000000000000692a70d2e424a56d2c6c27aa97d1a86395877b3a000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000016617364666c6a6b6a6c6b6164666a6c6a6c6b6a6c6b660000000000000000000000000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000500000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000007");
        System.out.println("output object => " + olr);
        System.out.println();
    }

    public static void testTableTest()
            throws JsonProcessingException, TransactionException, BaseException {

        /*pragma solidity ^0.4.24;

        contract TableTest {

            //select records
            function select(string name) public constant returns(bytes32[], int[], bytes32[]){

            }
            //insert records
            function insert(string name, int item_id, string item_name) public {

            }
            //update records
            function update(string name, int item_id, string item_name) public {

            }
            //remove records
            function remove(string name, int item_id) public returns(int){

            }
        }*/
        TransactionDecoder decode = new TransactionDecoder(TableTest.ABI, "");

        Function select =
                new Function(
                        "select",
                        Arrays.asList(new Utf8String("HelloWorld!")),
                        Arrays.asList(
                                new TypeReference<DynamicArray<Bytes32>>() {},
                                new TypeReference<DynamicArray<Int256>>() {},
                                new TypeReference<DynamicArray<Bytes32>>() {}));

        String selectSR = decode.decodeInputReturnJson(FunctionEncoder.encode(select));
        System.out.println("select input string => " + selectSR);
        System.out.println();

        List<Type> selectOR = decode.decodeInputReturnObject(FunctionEncoder.encode(select));
        System.out.println("select input object => " + selectOR);
        System.out.println();

        Function update =
                new Function(
                        "update",
                        Arrays.asList(
                                new Utf8String("HelloWorld! My First Hello."),
                                new Int256(5555),
                                new Utf8String("Good afternoon")),
                        Collections.<TypeReference<?>>emptyList());

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
        System.out.println("select output string => " + selectSOR);
        System.out.println();

        String updateSR = decode.decodeInputReturnJson(FunctionEncoder.encode(update));
        System.out.println("update input string => " + updateSR);
        System.out.println();

        List<Type> updateOR = decode.decodeInputReturnObject(FunctionEncoder.encode(update));
        System.out.println("update input object => " + updateSR);
        System.out.println();

        Function remove =
                new Function(
                        "remove",
                        Arrays.asList(
                                new Utf8String("HelloWorld! My First Hello."),
                                new Int256(-1111111)),
                        Collections.<TypeReference<?>>emptyList());

        String removeSR = decode.decodeInputReturnJson(FunctionEncoder.encode(remove));
        System.out.println("remote input string => " + removeSR);
        System.out.println();

        List<Type> removeOR = decode.decodeInputReturnObject(FunctionEncoder.encode(remove));
        System.out.println("remote input object => " + removeOR);
        System.out.println();
    }

    public static void main(String[] args)
            throws BaseException, TransactionException, JsonProcessingException {

        testTableTest();
    }
}
