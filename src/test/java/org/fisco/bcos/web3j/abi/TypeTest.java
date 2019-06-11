package org.fisco.bcos.web3j.abi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes10;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.junit.Test;

public class TypeTest {

    @Test
    public void dynamicTest() {
        assertThat(new Bool(true).dynamicType(), is(false)); // bool
        assertThat(new Bool(false).dynamicType(), is(false)); // bool

        assertThat(
                new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa").dynamicType(),
                is(false)); // address

        assertThat(new Utf8String("").dynamicType(), is(true)); // string
        assertThat(new Utf8String("abcdef").dynamicType(), is(true)); // string

        assertThat(new Bytes10("0123456789".getBytes()).dynamicType(), is(false)); // bytes10
        assertThat(new Bytes3("abc".getBytes()).dynamicType(), is(false)); // bytes3

        assertThat(new Bytes1("a".getBytes()).dynamicType(), is(false)); // bytes1

        assertThat(new Uint256(1).dynamicType(), is(false)); // uint256
        assertThat(new Int256(1).dynamicType(), is(false)); // int256

        assertThat(new DynamicBytes("".getBytes()).dynamicType(), is(true)); // bytes
        assertThat(new DynamicBytes("abc".getBytes()).dynamicType(), is(true)); // bytes

        assertThat(new StaticArray1<Bool>(new Bool(true)).dynamicType(), is(false)); // bool[1]
        assertThat(new DynamicArray<Bool>(new Bool(true)).dynamicType(), is(true)); // bool[]
        assertThat(
                new DynamicArray<DynamicArray<Bool>>(new DynamicArray<Bool>(new Bool(true)))
                        .dynamicType(),
                is(true)); // bool[][]
        assertThat(
                new StaticArray1<DynamicArray<Bool>>(new DynamicArray<Bool>(new Bool(true)))
                        .dynamicType(),
                is(true)); // bool[][1]
        assertThat(
                new DynamicArray<StaticArray1<Bool>>(new StaticArray1<Bool>(new Bool(true)))
                        .dynamicType(),
                is(true)); // bool[1][]
        assertThat(
                new StaticArray1<StaticArray1<Bool>>(new StaticArray1<Bool>(new Bool(true)))
                        .dynamicType(),
                is(false)); // bool[1][1]

        assertThat(
                new StaticArray1<Uint256>(new Uint256(1)).dynamicType(), is(false)); // uint256[1]
        assertThat(
                new DynamicArray<Uint256>(new Uint256(1), new Uint256(1)).dynamicType(),
                is(true)); // uint256[]
        assertThat(
                new DynamicArray<DynamicArray<Uint256>>(new DynamicArray<Uint256>(new Uint256(1)))
                        .dynamicType(),
                is(true)); // uint256[][]
        assertThat(
                new StaticArray1<DynamicArray<Uint256>>(new DynamicArray<Uint256>(new Uint256(1)))
                        .dynamicType(),
                is(true)); // uint256[][1]
        assertThat(
                new DynamicArray<StaticArray1<Uint256>>(new StaticArray1<Uint256>(new Uint256(1)))
                        .dynamicType(),
                is(true)); // uint256[1][]
        assertThat(
                new StaticArray1<StaticArray1<Uint256>>(new StaticArray1<Uint256>(new Uint256(1)))
                        .dynamicType(),
                is(false)); // uint256[1][1]

        assertThat(new StaticArray1<Int256>(new Int256(1)).dynamicType(), is(false)); // int256[1]
        assertThat(new DynamicArray<Int256>(new Int256(1)).dynamicType(), is(true)); // int256[]
        assertThat(
                new DynamicArray<DynamicArray<Int256>>(new DynamicArray<Int256>(new Int256(1)))
                        .dynamicType(),
                is(true)); // int256[][]
        assertThat(
                new StaticArray1<DynamicArray<Int256>>(new DynamicArray<Int256>(new Int256(1)))
                        .dynamicType(),
                is(true)); // int256[][1]
        assertThat(
                new DynamicArray<StaticArray1<Int256>>(new StaticArray1<Int256>(new Int256(1)))
                        .dynamicType(),
                is(true)); // int256[1][]
        assertThat(
                new StaticArray1<StaticArray1<Int256>>(new StaticArray1<Int256>(new Int256(1)))
                        .dynamicType(),
                is(false)); // int256[1][1]

        assertThat(
                new StaticArray1<Address>(new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))
                        .dynamicType(),
                is(false)); // address[1]
        assertThat(
                new DynamicArray<Address>(new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))
                        .dynamicType(),
                is(true)); // address[]
        assertThat(
                new DynamicArray<DynamicArray<Address>>(
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(true)); // address[][]
        assertThat(
                new StaticArray1<DynamicArray<Address>>(
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(true)); // address[][1]
        assertThat(
                new DynamicArray<StaticArray1<Address>>(
                                new StaticArray1<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(true)); // address[1][]
        assertThat(
                new StaticArray1<StaticArray1<Address>>(
                                new StaticArray1<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(false)); // address[1][1]

        assertThat(
                new StaticArray1<Utf8String>(new Utf8String("")).dynamicType(),
                is(true)); // string[1]
        assertThat(
                new DynamicArray<Utf8String>(new Utf8String("")).dynamicType(),
                is(true)); // string[]
        assertThat(
                new DynamicArray<DynamicArray<Utf8String>>(
                                new DynamicArray<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(true)); // string[][]
        assertThat(
                new StaticArray1<DynamicArray<Utf8String>>(
                                new DynamicArray<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(true)); // string[][1]
        assertThat(
                new DynamicArray<StaticArray1<Utf8String>>(
                                new StaticArray1<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(true)); // string[1][]
        assertThat(
                new StaticArray1<StaticArray1<Utf8String>>(
                                new StaticArray1<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .dynamicType(),
                is(true)); // string[1][1]

        assertThat(
                new StaticArray1<DynamicBytes>(new DynamicBytes("aa".getBytes())).dynamicType(),
                is(true)); // bytes[1]
        assertThat(
                new DynamicArray<DynamicBytes>(new DynamicBytes("".getBytes())).dynamicType(),
                is(true)); // bytes[]
        assertThat(
                new DynamicArray<DynamicArray<DynamicBytes>>(
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .dynamicType(),
                is(true)); // bytes[][]
        assertThat(
                new StaticArray1<DynamicArray<DynamicBytes>>(
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .dynamicType(),
                is(true)); // bytes[][1]
        assertThat(
                new DynamicArray<StaticArray1<DynamicBytes>>(
                                new StaticArray1<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .dynamicType(),
                is(true)); // bytes[1][]
        assertThat(
                new StaticArray1<StaticArray1<DynamicBytes>>(
                                new StaticArray1<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .dynamicType(),
                is(true)); // bytes[1][1]
    }

    @Test
    public void dynamicOffsetTest() {
        assertThat(new Bool(true).offset(), is(1)); // bool
        assertThat(new Bool(false).offset(), is(1)); // bool

        assertThat(
                new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa").offset(),
                is(1)); // address

        assertThat(new Utf8String("").offset(), is(1)); // string
        assertThat(new Utf8String("abcdef").offset(), is(1)); // string

        assertThat(new Bytes10("0123456789".getBytes()).offset(), is(1)); // bytes10
        assertThat(new Bytes3("abc".getBytes()).offset(), is(1)); // bytes3

        assertThat(new Bytes1("a".getBytes()).offset(), is(1)); // bytes1

        assertThat(new Uint256(1).offset(), is(1)); // uint256
        assertThat(new Int256(1).offset(), is(1)); // int256

        assertThat(new DynamicBytes("".getBytes()).offset(), is(1)); // bytes
        assertThat(new DynamicBytes("abc".getBytes()).offset(), is(1)); // bytes

        assertThat(new StaticArray1<Bool>(new Bool(true)).offset(), is(1)); // bool[1]
        assertThat(
                new StaticArray3<Bool>(new Bool(true), new Bool(true), new Bool(true)).offset(),
                is(3)); // bool[3]
        assertThat(
                new DynamicArray<Bool>(
                                new Bool(true), new Bool(true), new Bool(true), new Bool(true))
                        .offset(),
                is(1)); // bool[]
        assertThat(
                new DynamicArray<DynamicArray<Bool>>(
                                new DynamicArray<Bool>(
                                        new Bool(true), new Bool(true), new Bool(true)))
                        .offset(),
                is(1)); // bool[][]
        assertThat(
                new StaticArray2<DynamicArray<Bool>>(
                                new DynamicArray<Bool>(new Bool(true)),
                                new DynamicArray<Bool>(new Bool(true)))
                        .offset(),
                is(1)); // bool[][2]
        assertThat(
                new DynamicArray<StaticArray2<Bool>>(
                                new StaticArray2<Bool>(new Bool(true), new Bool(true)),
                                new StaticArray2<Bool>(new Bool(true), new Bool(true)))
                        .offset(),
                is(1)); // bool[1][]
        assertThat(
                new StaticArray2<StaticArray2<Bool>>(
                                new StaticArray2<Bool>(new Bool(true), new Bool(true)),
                                new StaticArray2<Bool>(new Bool(true), new Bool(true)))
                        .offset(),
                is(4)); // bool[2][2]

        assertThat(new StaticArray1<Uint256>(new Uint256(1)).offset(), is(1)); // uint256[1]
        assertThat(
                new DynamicArray<Uint256>(new Uint256(1), new Uint256(1)).offset(),
                is(1)); // uint256[]
        assertThat(
                new DynamicArray<DynamicArray<Uint256>>(
                                new DynamicArray<Uint256>(new Uint256(1), new Uint256(1)),
                                new DynamicArray<Uint256>(new Uint256(1), new Uint256(1)))
                        .offset(),
                is(1)); // uint256[][]
        assertThat(
                new StaticArray2<DynamicArray<Uint256>>(
                                new DynamicArray<Uint256>(new Uint256(1)),
                                new DynamicArray<Uint256>(new Uint256(1)))
                        .offset(),
                is(1)); // uint256[][2]
        assertThat(
                new DynamicArray<StaticArray2<Uint256>>(
                                new StaticArray2<Uint256>(new Uint256(1), new Uint256(1)))
                        .offset(),
                is(1)); // uint256[2][]
        assertThat(
                new StaticArray2<StaticArray2<Uint256>>(
                                new StaticArray2<Uint256>(new Uint256(1), new Uint256(1)),
                                new StaticArray2<Uint256>(new Uint256(1), new Uint256(1)))
                        .offset(),
                is(4)); // uint256[2][2]

        assertThat(new StaticArray1<Int256>(new Int256(1)).offset(), is(1)); // int256[1]
        assertThat(new DynamicArray<Int256>(new Int256(1)).offset(), is(1)); // int256[]
        assertThat(
                new DynamicArray<DynamicArray<Int256>>(
                                new DynamicArray<Int256>(new Int256(1), new Int256(1)),
                                new DynamicArray<Int256>(new Int256(1), new Int256(1)))
                        .offset(),
                is(1)); // int256[][]
        assertThat(
                new StaticArray2<DynamicArray<Int256>>(
                                new DynamicArray<Int256>(new Int256(1)),
                                new DynamicArray<Int256>(new Int256(1)))
                        .offset(),
                is(1)); // int256[][2]
        assertThat(
                new DynamicArray<StaticArray2<Int256>>(
                                new StaticArray2<Int256>(new Int256(1), new Int256(1)))
                        .offset(),
                is(1)); // int256[2][]
        assertThat(
                new StaticArray2<StaticArray2<Int256>>(
                                new StaticArray2<Int256>(new Int256(1), new Int256(1)),
                                new StaticArray2<Int256>(new Int256(1), new Int256(1)))
                        .offset(),
                is(4)); // int256[2][2]

        assertThat(
                new StaticArray1<Address>(new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))
                        .offset(),
                is(1)); // address[1]
        assertThat(
                new DynamicArray<Address>(new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))
                        .offset(),
                is(1)); // address[]
        assertThat(
                new DynamicArray<DynamicArray<Address>>(
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(1)); // address[][]
        assertThat(
                new StaticArray2<DynamicArray<Address>>(
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(1)); // address[][2]
        assertThat(
                new DynamicArray<StaticArray2<Address>>(
                                new StaticArray2<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(1)); // address[2][]
        assertThat(
                new StaticArray2<StaticArray2<Address>>(
                                new StaticArray2<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new StaticArray2<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(4)); // address[2][2]

        assertThat(new StaticArray1<Utf8String>(new Utf8String("")).offset(), is(1)); // string[1]
        assertThat(new DynamicArray<Utf8String>(new Utf8String("")).offset(), is(1)); // string[]
        assertThat(
                new DynamicArray<DynamicArray<Utf8String>>(
                                new DynamicArray<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(1)); // string[][]
        assertThat(
                new StaticArray2<DynamicArray<Utf8String>>(
                                new DynamicArray<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new DynamicArray<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(1)); // string[][2]
        assertThat(
                new DynamicArray<StaticArray2<Utf8String>>(
                                new StaticArray2<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(1)); // string[2][]
        assertThat(
                new StaticArray2<StaticArray2<Utf8String>>(
                                new StaticArray2<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new StaticArray2<Utf8String>(
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Utf8String(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))
                        .offset(),
                is(1)); // string[2][2]

        assertThat(
                new StaticArray1<DynamicBytes>(new DynamicBytes("aa".getBytes())).offset(),
                is(1)); // bytes[1]
        assertThat(
                new DynamicArray<DynamicBytes>(new DynamicBytes("".getBytes())).offset(),
                is(1)); // bytes[]
        assertThat(
                new DynamicArray<DynamicArray<DynamicBytes>>(
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .offset(),
                is(1)); // bytes[][]
        assertThat(
                new StaticArray2<DynamicArray<DynamicBytes>>(
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes()),
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())),
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes()),
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .offset(),
                is(1)); // bytes[][2]
        assertThat(
                new DynamicArray<StaticArray2<DynamicBytes>>(
                                new StaticArray2<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes()),
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())),
                                new StaticArray2<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes()),
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .offset(),
                is(1)); // bytes21][]
        assertThat(
                new StaticArray2<StaticArray2<DynamicBytes>>(
                                new StaticArray2<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes()),
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())),
                                new StaticArray2<DynamicBytes>(
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes()),
                                        new DynamicBytes(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"
                                                        .getBytes())))
                        .offset(),
                is(1)); // bytes[2][2]
    }
}
