package org.fisco.bcos.web3j.tx.txdecode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class DynamicReferenceTest {

    @Test
    public void dynamicReferenceTest() throws BaseException, ClassNotFoundException {

        assertThat(
                DynamicArrayReference.create("int").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                DynamicArrayReference.create("int1").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                DynamicArrayReference.create("int128").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                DynamicArrayReference.create("int256").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                DynamicArrayReference.create("uint").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                DynamicArrayReference.create("uint1").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                DynamicArrayReference.create("uint128").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                DynamicArrayReference.create("uint256").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                DynamicArrayReference.create("bool").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                DynamicArrayReference.create("address").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                DynamicArrayReference.create("bytes").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                DynamicArrayReference.create("string").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                DynamicArrayReference.create("bytes32").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                DynamicArrayReference.create("bytes8").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                DynamicArrayReference.create("bytes1").getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.DynamicArray<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray1ReferenceTest() throws BaseException, ClassNotFoundException {

        assertThat(
                StaticArrayReference.create("int", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", 1).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray2ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 2;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray3ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 3;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray4ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 4;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray5ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 5;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray6ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 6;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray7ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 7;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray8ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 8;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray9ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 9;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray10ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 10;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray11ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 11;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray12ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 12;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray13ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 13;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray14ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 14;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray15ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 15;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray16ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 16;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray17ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 17;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray18ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 18;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray19ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 19;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray20ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 20;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray20<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray21ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 21;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray21<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray22ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 22;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray22<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray23ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 23;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray23<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray24ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 24;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray24<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray25ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 25;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray25<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray26ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 26;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray26<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray27ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 27;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray27<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray28ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 28;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray28<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray29ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 29;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray29<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray30ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 30;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray31ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 31;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }

    @Test
    public void staticArray32ReferenceTest() throws BaseException, ClassNotFoundException {
        int dec = 32;
        assertThat(
                StaticArrayReference.create("int", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("int256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Int256>"));
        assertThat(
                StaticArrayReference.create("uint", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint128", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));
        assertThat(
                StaticArrayReference.create("uint256", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>"));

        assertThat(
                StaticArrayReference.create("bool", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.Bool>"));
        assertThat(
                StaticArrayReference.create("address", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.Address>"));
        assertThat(
                StaticArrayReference.create("bytes", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.DynamicBytes>"));
        assertThat(
                StaticArrayReference.create("string", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.Utf8String>"));

        assertThat(
                StaticArrayReference.create("bytes32", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32>"));
        assertThat(
                StaticArrayReference.create("bytes8", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes8>"));
        assertThat(
                StaticArrayReference.create("bytes1", dec).getType().getTypeName(),
                is(
                        "org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32<org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1>"));
    }
}
