package org.fisco.bcos.web3j.abi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes10;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes11;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes2;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int128;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int64;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray10;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray11;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray12;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray128;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray13;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray14;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray15;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray16;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray17;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray18;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray19;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray30;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray31;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray32;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray4;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray7;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray8;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray9;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint64;
import org.junit.Test;

public class UtilsTest {

    public static void main(String[] args) {
        // uint256[256][]
        String s =
                Utils.getTypeName(
                        new TypeReference<DynamicArray<StaticArray128<Uint256>>>() {}.getType());
        System.out.println(s);
    }

    @Test
    public void testGetTypeName() throws ClassNotFoundException {
        assertThat(Utils.getTypeName(new TypeReference<Uint>() {}), is("uint256"));
        assertThat(Utils.getTypeName(new TypeReference<Uint32>() {}), is("uint32"));
        assertThat(Utils.getTypeName(new TypeReference<Uint64>() {}), is("uint64"));
        assertThat(Utils.getTypeName(new TypeReference<Int>() {}), is("int256"));
        assertThat(Utils.getTypeName(new TypeReference<Int128>() {}), is("int128"));
        assertThat(Utils.getTypeName(new TypeReference<Int64>() {}), is("int64"));
        assertThat(Utils.getTypeName(new TypeReference<Ufixed>() {}), is("ufixed256"));
        assertThat(Utils.getTypeName(new TypeReference<Fixed>() {}), is("fixed256"));
        assertThat(Utils.getTypeName(new TypeReference<Address>() {}), is("address"));
        assertThat(Utils.getTypeName(new TypeReference<Bool>() {}), is("bool"));
        assertThat(Utils.getTypeName(new TypeReference<Bytes32>() {}), is("bytes32"));
        assertThat(Utils.getTypeName(new TypeReference<Bytes10>() {}), is("bytes10"));
        assertThat(Utils.getTypeName(new TypeReference<Bytes1>() {}), is("bytes1"));
        assertThat(Utils.getTypeName(new TypeReference<Utf8String>() {}), is("string"));
        assertThat(Utils.getTypeName(new TypeReference<DynamicBytes>() {}), is("bytes"));

        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray128<Uint>>() {}),
                is("uint256[128]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray128<Uint256>>() {}),
                is("uint256[128]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray32<Uint32>>() {}), is("uint32[32]"));
        assertThat(Utils.getTypeName(new TypeReference<StaticArray5<Uint>>() {}), is("uint256[5]"));
        assertThat(Utils.getTypeName(new TypeReference<DynamicArray<Uint>>() {}), is("uint256[]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray128<Bool>>() {}), is("bool[128]"));
        assertThat(Utils.getTypeName(new TypeReference<StaticArray31<Bool>>() {}), is("bool[31]"));
        assertThat(Utils.getTypeName(new TypeReference<DynamicArray<Bool>>() {}), is("bool[]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray128<Bytes32>>() {}),
                is("bytes32[128]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray11<Bytes11>>() {}),
                is("bytes11[11]"));
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Bytes32>>() {}), is("bytes32[]"));
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Bytes11>>() {}), is("bytes11[]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray5<Utf8String>>() {}),
                is("string[5]"));
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Utf8String>>() {}),
                is("string[]"));
        assertThat(Utils.getTypeName(new TypeReference<DynamicArray<Bytes>>() {}), is("bytes[]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray128<Bytes>>() {}), is("bytes[128]"));
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Address>>() {}), is("address[]"));
        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray128<Address>>() {}),
                is("address[128]"));

        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<DynamicArray<DynamicBytes>>>>>() {}),
                is("bytes[][1][1][1]"));
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<StaticArray1<DynamicBytes>>>>>() {}),
                is("bytes[1][1][1][1]"));

        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray10<Bool>>() {}.getType()),
                is("bool[10]")); // bool[10]
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Bool>>() {}.getType()),
                is("bool[]")); // bool[]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<DynamicArray<Bool>>>() {}.getType()),
                is("bool[][]")); // bool[][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray8<DynamicArray<Bool>>>() {}.getType()),
                is("bool[][8]")); // bool[][8]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<StaticArray7<Bool>>>() {}.getType()),
                is("bool[7][]")); // bool[7][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray3<StaticArray2<Bool>>>() {}.getType()),
                is("bool[2][3]")); // bool[2][3]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray3<StaticArray4<StaticArray5<Bool>>>>() {}.getType()),
                is("bool[5][4][3]")); // bool[5][4][3]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                DynamicArray<StaticArray12<StaticArray128<Bool>>>>() {}.getType()),
                is("bool[128][12][]")); // bool[128][12][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray8<
                                        StaticArray6<
                                                StaticArray4<StaticArray2<Bool>>>>>() {}.getType()),
                is("bool[2][4][6][8]")); // bool[2][4][6][8]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray9<
                                        StaticArray6<
                                                StaticArray3<DynamicArray<Bool>>>>>() {}.getType()),
                is("bool[][3][6][9]")); // bool[][3][6][9]

        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray31<Uint256>>() {}.getType()),
                is("uint256[31]")); // uint256[31]
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Uint256>>() {}.getType()),
                is("uint256[]")); // uint256[]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<DynamicArray<Uint256>>>() {}.getType()),
                is("uint256[][]")); // uint256[][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray2<DynamicArray<Uint256>>>() {}.getType()),
                is("uint256[][2]")); // uint256[][2]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<StaticArray11<Uint256>>>() {}.getType()),
                is("uint256[11][]")); // uint256[11][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray9<StaticArray11<Uint256>>>() {}.getType()),
                is("uint256[11][9]")); // uint256[11][9]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray10<StaticArray7<StaticArray4<Uint256>>>>() {}.getType()),
                is("uint256[4][7][10]")); // uint256[4][7][10]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                DynamicArray<StaticArray7<StaticArray7<Uint256>>>>() {}.getType()),
                is("uint256[7][7][]")); // uint256[7][7][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray5<
                                        StaticArray4<
                                                StaticArray3<
                                                        StaticArray2<Uint256>>>>>() {}.getType()),
                is("uint256[2][3][4][5]")); // uint256[2][3][4][5]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray11<
                                        StaticArray13<
                                                StaticArray15<
                                                        DynamicArray<Uint256>>>>>() {}.getType()),
                is("uint256[][15][13][11]")); // uint256[][15][13][11]

        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray30<Int256>>() {}.getType()),
                is("int256[30]")); // int256[30]
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Int256>>() {}.getType()),
                is("int256[]")); // int256[]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<DynamicArray<Int256>>>() {}.getType()),
                is("int256[][]")); // int256[][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray11<DynamicArray<Int256>>>() {}.getType()),
                is("int256[][11]")); // int256[][11]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<StaticArray12<Int256>>>() {}.getType()),
                is("int256[12][]")); // int256[12][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray11<StaticArray31<Int256>>>() {}.getType()),
                is("int256[31][11]")); // int256[31][11]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray3<StaticArray3<StaticArray3<Int256>>>>() {}.getType()),
                is("int256[3][3][3]")); // int256[3][3][3]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray5<StaticArray5<DynamicArray<Int256>>>>() {}.getType()),
                is("int256[][5][5]")); // int256[][5][5]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray2<
                                        StaticArray5<
                                                StaticArray6<
                                                        StaticArray4<Int256>>>>>() {}.getType()),
                is("int256[4][6][5][2]")); // int256[4][6][5][2]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray10<
                                        StaticArray9<
                                                StaticArray8<
                                                        DynamicArray<Int256>>>>>() {}.getType()),
                is("int256[][8][9][10]")); // int256[][8][9][10]

        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray17<Address>>() {}.getType()),
                is("address[17]")); // address[17]
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Address>>() {}.getType()),
                is("address[]")); // address[]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<DynamicArray<Address>>>() {}.getType()),
                is("address[][]")); // address[][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray19<DynamicArray<Address>>>() {}.getType()),
                is("address[][19]")); // address[][19]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<StaticArray15<Address>>>() {}.getType()),
                is("address[15][]")); // address[15][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray15<StaticArray15<Address>>>() {}.getType()),
                is("address[15][15]")); // address[15][15]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray13<
                                        StaticArray31<StaticArray31<Address>>>>() {}.getType()),
                is("address[31][31][13]")); // address[31][31][13]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray18<StaticArray10<Address>>>>() {}.getType()),
                is("address[10][18][]")); // address[10][18][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray128<
                                        StaticArray128<
                                                StaticArray128<
                                                        StaticArray128<Address>>>>>() {}.getType()),
                is("address[128][128][128][128]")); // address[128][128][128][128]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray128<
                                        StaticArray128<
                                                StaticArray128<
                                                        DynamicArray<Address>>>>>() {}.getType()),
                is("address[][128][128][128]")); // address[][128][128][128]

        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray5<Utf8String>>() {}.getType()),
                is("string[5]")); // string[5]
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<Utf8String>>() {}.getType()),
                is("string[]")); // string[]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<DynamicArray<Utf8String>>>() {}.getType()),
                is("string[][]")); // string[][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray13<DynamicArray<Utf8String>>>() {}.getType()),
                is("string[][13]")); // string[][13]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<StaticArray14<Utf8String>>>() {}.getType()),
                is("string[14][]")); // string[14][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray9<StaticArray9<Utf8String>>>() {}.getType()),
                is("string[9][9]")); // string[9][9]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray11<
                                        StaticArray11<StaticArray11<Utf8String>>>>() {}.getType()),
                is("string[11][11][11]")); // string[11][11][11]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray8<StaticArray8<Utf8String>>>>() {}.getType()),
                is("string[8][8][]")); // string[8][8][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray7<
                                        StaticArray6<
                                                StaticArray5<
                                                        StaticArray4<
                                                                Utf8String>>>>>() {}.getType()),
                is("string[4][5][6][7]")); // string[4][5][6][7]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray14<
                                        StaticArray13<
                                                StaticArray12<
                                                        DynamicArray<
                                                                Utf8String>>>>>() {}.getType()),
                is("string[][12][13][14]")); // string[][12][13][14]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray12<
                                        StaticArray16<
                                                DynamicArray<
                                                        DynamicArray<
                                                                Utf8String>>>>>() {}.getType()),
                is("string[][][16][12]")); // string[][][16][12]

        assertThat(
                Utils.getTypeName(new TypeReference<StaticArray1<DynamicBytes>>() {}.getType()),
                is("bytes[1]")); // bytes[1]
        assertThat(
                Utils.getTypeName(new TypeReference<DynamicArray<DynamicBytes>>() {}.getType()),
                is("bytes[]")); // bytes[]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<DynamicArray<DynamicBytes>>>() {}.getType()),
                is("bytes[][]")); // bytes[][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<DynamicArray<StaticArray7<DynamicBytes>>>() {}.getType()),
                is("bytes[7][]")); // bytes[7][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray9<DynamicArray<DynamicBytes>>>() {}.getType()),
                is("bytes[][9]")); // bytes[][9]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<StaticArray5<StaticArray5<DynamicBytes>>>() {}.getType()),
                is("bytes[5][5]")); // bytes[5][5]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray2<StaticArray3<DynamicBytes>>>>() {}.getType()),
                is("bytes[3][2][1]")); // bytes[3][2][1]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray9<StaticArray7<DynamicBytes>>>>() {}.getType()),
                is("bytes[7][9][]")); // bytes[7][9][]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        StaticArray1<
                                                                DynamicBytes>>>>>() {}.getType()),
                is("bytes[1][1][1][1]")); // bytes[1][1][1][1]
        assertThat(
                Utils.getTypeName(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        DynamicArray<
                                                                DynamicBytes>>>>>() {}.getType()),
                is("bytes[][1][1][1]")); // bytes[][1][1][1]
    }

    @Test
    public void testTypeMap() throws Exception {
        final List<BigInteger> input =
                Arrays.asList(BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN);

        assertThat(
                Utils.typeMap(input, Uint256.class),
                equalTo(
                        Arrays.asList(
                                new Uint256(BigInteger.ZERO),
                                new Uint256(BigInteger.ONE),
                                new Uint256(BigInteger.TEN))));
    }

    @Test
    public void testTypeMapNested() {
        List<BigInteger> innerList1 = Arrays.asList(BigInteger.valueOf(1), BigInteger.valueOf(2));
        List<BigInteger> innerList2 = Arrays.asList(BigInteger.valueOf(3), BigInteger.valueOf(4));
        final List<List<BigInteger>> input = Arrays.asList(innerList1, innerList2);

        StaticArray2<Uint256> staticArray1 = new StaticArray2<>(new Uint256(1), new Uint256(2));
        StaticArray2<Uint256> staticArray2 = new StaticArray2<>(new Uint256(3), new Uint256(4));
        List<StaticArray2<Uint256>> expectedList = Arrays.asList(staticArray1, staticArray2);
        assertThat(Utils.typeMap(input, StaticArray2.class, Uint256.class), equalTo(expectedList));
    }

    @Test
    public void testTypeMapEmpty() {
        assertThat(
                Utils.typeMap(new ArrayList<BigInteger>(), Uint256.class),
                equalTo(new ArrayList<Uint256>()));
    }

    @Test
    public void dynamicTypeRefTest() throws ClassNotFoundException {

        assertThat(Utils.dynamicType(new TypeReference<Bool>() {}.getType()), is(false)); // bool
        assertThat(
                Utils.dynamicType(new TypeReference<Address>() {}.getType()), is(false)); // address
        assertThat(
                Utils.dynamicType(new TypeReference<Utf8String>() {}.getType()),
                is(true)); // string
        assertThat(
                Utils.dynamicType(new TypeReference<Bytes10>() {}.getType()), is(false)); // bytes10
        assertThat(
                Utils.dynamicType(new TypeReference<Bytes32>() {}.getType()), is(false)); // bytes32
        assertThat(
                Utils.dynamicType(new TypeReference<Bytes1>() {}.getType()), is(false)); // bytes1
        assertThat(
                Utils.dynamicType(new TypeReference<Uint256>() {}.getType()), is(false)); // uint256
        assertThat(
                Utils.dynamicType(new TypeReference<Int256>() {}.getType()), is(false)); // int256
        assertThat(
                Utils.dynamicType(new TypeReference<DynamicBytes>() {}.getType()),
                is(true)); // bytes

        assertThat(
                Utils.dynamicType(new TypeReference<StaticArray1<Bool>>() {}.getType()),
                is(false)); // bool[1]
        assertThat(
                Utils.dynamicType(new TypeReference<DynamicArray<Bool>>() {}.getType()),
                is(true)); // bool[]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<DynamicArray<Bool>>>() {}.getType()),
                is(true)); // bool[][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<DynamicArray<Bool>>>() {}.getType()),
                is(true)); // bool[][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<StaticArray1<Bool>>>() {}.getType()),
                is(true)); // bool[1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<StaticArray1<Bool>>>() {}.getType()),
                is(false)); // bool[1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<StaticArray1<StaticArray1<Bool>>>>() {}.getType()),
                is(false)); // bool[1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                DynamicArray<StaticArray1<StaticArray1<Bool>>>>() {}.getType()),
                is(true)); // bool[1][1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<StaticArray1<Bool>>>>>() {}.getType()),
                is(false)); // bool[1][1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<DynamicArray<Bool>>>>>() {}.getType()),
                is(true)); // bool[][1][1][1]

        assertThat(
                Utils.dynamicType(new TypeReference<StaticArray1<Uint256>>() {}.getType()),
                is(false)); // uint256[1]
        assertThat(
                Utils.dynamicType(new TypeReference<DynamicArray<Uint256>>() {}.getType()),
                is(true)); // uint256[]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<DynamicArray<Uint256>>>() {}.getType()),
                is(true)); // uint256[][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<DynamicArray<Uint256>>>() {}.getType()),
                is(true)); // uint256[][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<StaticArray1<Uint256>>>() {}.getType()),
                is(true)); // uint256[1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<StaticArray1<Uint256>>>() {}.getType()),
                is(false)); // uint256[1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<StaticArray1<StaticArray1<Uint256>>>>() {}.getType()),
                is(false)); // uint256[1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                DynamicArray<StaticArray1<StaticArray1<Uint256>>>>() {}.getType()),
                is(true)); // uint256[1][1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        StaticArray1<Uint256>>>>>() {}.getType()),
                is(false)); // uint256[1][1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        DynamicArray<Uint256>>>>>() {}.getType()),
                is(true)); // uint256[][1][1][1]

        assertThat(
                Utils.dynamicType(new TypeReference<StaticArray1<Int256>>() {}.getType()),
                is(false)); // int256[1]
        assertThat(
                Utils.dynamicType(new TypeReference<DynamicArray<Int256>>() {}.getType()),
                is(true)); // int256[]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<DynamicArray<Int256>>>() {}.getType()),
                is(true)); // int256[][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<DynamicArray<Int256>>>() {}.getType()),
                is(true)); // int256[][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<StaticArray1<Int256>>>() {}.getType()),
                is(true)); // int256[1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<StaticArray1<Int256>>>() {}.getType()),
                is(false)); // int256[1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<StaticArray1<StaticArray1<Int256>>>>() {}.getType()),
                is(false)); // int256[1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<StaticArray1<DynamicArray<Int256>>>>() {}.getType()),
                is(true)); // int256[1][1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        StaticArray1<Int256>>>>>() {}.getType()),
                is(false)); // int256[1][1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        DynamicArray<Int256>>>>>() {}.getType()),
                is(true)); // int256[][1][1][1]

        assertThat(
                Utils.dynamicType(new TypeReference<StaticArray1<Address>>() {}.getType()),
                is(false)); // address[1]
        assertThat(
                Utils.dynamicType(new TypeReference<DynamicArray<Address>>() {}.getType()),
                is(true)); // address[]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<DynamicArray<Address>>>() {}.getType()),
                is(true)); // address[][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<DynamicArray<Address>>>() {}.getType()),
                is(true)); // address[][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<StaticArray1<Address>>>() {}.getType()),
                is(true)); // address[1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<StaticArray1<Address>>>() {}.getType()),
                is(false)); // address[1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<StaticArray1<StaticArray1<Address>>>>() {}.getType()),
                is(false)); // address[1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                DynamicArray<StaticArray1<StaticArray1<Address>>>>() {}.getType()),
                is(true)); // address[1][1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        StaticArray1<Address>>>>>() {}.getType()),
                is(false)); // address[1][1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        DynamicArray<Address>>>>>() {}.getType()),
                is(true)); // address[][1][1][1]

        assertThat(
                Utils.dynamicType(new TypeReference<StaticArray1<Utf8String>>() {}.getType()),
                is(true)); // string[1]
        assertThat(
                Utils.dynamicType(new TypeReference<DynamicArray<Utf8String>>() {}.getType()),
                is(true)); // string[]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<DynamicArray<Utf8String>>>() {}.getType()),
                is(true)); // string[][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<DynamicArray<Utf8String>>>() {}.getType()),
                is(true)); // string[][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<StaticArray1<Utf8String>>>() {}.getType()),
                is(true)); // string[1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<StaticArray1<Utf8String>>>() {}.getType()),
                is(true)); // string[1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<StaticArray1<Utf8String>>>>() {}.getType()),
                is(true)); // string[1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray1<StaticArray1<Utf8String>>>>() {}.getType()),
                is(true)); // string[1][1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        StaticArray1<
                                                                Utf8String>>>>>() {}.getType()),
                is(true)); // string[1][1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        DynamicArray<
                                                                Utf8String>>>>>() {}.getType()),
                is(true)); // string[][1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                DynamicArray<
                                                        DynamicArray<
                                                                Utf8String>>>>>() {}.getType()),
                is(true)); // string[1][1][][]

        assertThat(
                Utils.dynamicType(new TypeReference<StaticArray1<DynamicBytes>>() {}.getType()),
                is(true)); // bytes[1]
        assertThat(
                Utils.dynamicType(new TypeReference<DynamicArray<DynamicBytes>>() {}.getType()),
                is(true)); // bytes[]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<DynamicArray<DynamicBytes>>>() {}.getType()),
                is(true)); // bytes[][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<DynamicArray<StaticArray1<DynamicBytes>>>() {}.getType()),
                is(true)); // bytes[1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<DynamicArray<DynamicBytes>>>() {}.getType()),
                is(true)); // bytes[][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<StaticArray1<StaticArray1<DynamicBytes>>>() {}.getType()),
                is(true)); // bytes[1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<StaticArray1<DynamicBytes>>>>() {}.getType()),
                is(true)); // bytes[1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray1<StaticArray1<DynamicBytes>>>>() {}.getType()),
                is(true)); // bytes[1][1][]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        StaticArray1<
                                                                DynamicBytes>>>>>() {}.getType()),
                is(true)); // bytes[1][1][1][1]
        assertThat(
                Utils.dynamicType(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        DynamicArray<
                                                                DynamicBytes>>>>>() {}.getType()),
                is(true)); // bytes[][1][1][1]
    }

    @Test
    public void typeOffsetTest() throws ClassNotFoundException {

        assertThat(Utils.getOffset(new TypeReference<Bool>() {}.getType()), is(1)); // bool
        assertThat(Utils.getOffset(new TypeReference<Address>() {}.getType()), is(1)); // address
        assertThat(Utils.getOffset(new TypeReference<Utf8String>() {}.getType()), is(1)); // string
        assertThat(Utils.getOffset(new TypeReference<Bytes10>() {}.getType()), is(1)); // bytes10
        assertThat(Utils.getOffset(new TypeReference<Bytes32>() {}.getType()), is(1)); // bytes32
        assertThat(Utils.getOffset(new TypeReference<Bytes1>() {}.getType()), is(1)); // bytes1
        assertThat(Utils.getOffset(new TypeReference<Uint256>() {}.getType()), is(1)); // uint256
        assertThat(Utils.getOffset(new TypeReference<Int256>() {}.getType()), is(1)); // int256
        assertThat(Utils.getOffset(new TypeReference<DynamicBytes>() {}.getType()), is(1)); // bytes

        assertThat(
                Utils.getOffset(new TypeReference<StaticArray10<Bool>>() {}.getType()),
                is(10)); // bool[10]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<Bool>>() {}.getType()),
                is(1)); // bool[]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<DynamicArray<Bool>>>() {}.getType()),
                is(1)); // bool[][]
        assertThat(
                Utils.getOffset(new TypeReference<StaticArray8<DynamicArray<Bool>>>() {}.getType()),
                is(1)); // bool[][8]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<StaticArray7<Bool>>>() {}.getType()),
                is(1)); // bool[7][]
        assertThat(
                Utils.getOffset(new TypeReference<StaticArray3<StaticArray2<Bool>>>() {}.getType()),
                is(6)); // bool[2][3]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray3<StaticArray4<StaticArray5<Bool>>>>() {}.getType()),
                is(60)); // bool[5][4][3]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                DynamicArray<StaticArray12<StaticArray128<Bool>>>>() {}.getType()),
                is(1)); // bool[128][12][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray8<
                                        StaticArray6<
                                                StaticArray4<StaticArray2<Bool>>>>>() {}.getType()),
                is(384)); // bool[2][4][6][8]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray9<
                                        StaticArray6<
                                                StaticArray3<DynamicArray<Bool>>>>>() {}.getType()),
                is(1)); // bool[][3][6][9]

        assertThat(
                Utils.getOffset(new TypeReference<StaticArray31<Uint256>>() {}.getType()),
                is(31)); // uint256[31]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<Uint256>>() {}.getType()),
                is(1)); // uint256[]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<DynamicArray<Uint256>>>() {}.getType()),
                is(1)); // uint256[][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray2<DynamicArray<Uint256>>>() {}.getType()),
                is(1)); // uint256[][2]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<StaticArray11<Uint256>>>() {}.getType()),
                is(1)); // uint256[11][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray9<StaticArray11<Uint256>>>() {}.getType()),
                is(99)); // uint256[11][9]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray10<StaticArray7<StaticArray4<Uint256>>>>() {}.getType()),
                is(280)); // uint256[4][7][10]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                DynamicArray<StaticArray7<StaticArray7<Uint256>>>>() {}.getType()),
                is(1)); // uint256[7][7][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray5<
                                        StaticArray4<
                                                StaticArray3<
                                                        StaticArray2<Uint256>>>>>() {}.getType()),
                is(120)); // uint256[2][3][4][5]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray11<
                                        StaticArray13<
                                                StaticArray15<
                                                        DynamicArray<Uint256>>>>>() {}.getType()),
                is(1)); // uint256[][15][13][11]

        assertThat(
                Utils.getOffset(new TypeReference<StaticArray30<Int256>>() {}.getType()),
                is(30)); // int256[30]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<Int256>>() {}.getType()),
                is(1)); // int256[]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<DynamicArray<Int256>>>() {}.getType()),
                is(1)); // int256[][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray11<DynamicArray<Int256>>>() {}.getType()),
                is(1)); // int256[][11]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<StaticArray12<Int256>>>() {}.getType()),
                is(1)); // int256[12][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray11<StaticArray31<Int256>>>() {}.getType()),
                is(341)); // int256[31][11]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray3<StaticArray3<StaticArray3<Int256>>>>() {}.getType()),
                is(27)); // int256[3][3][3]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray5<StaticArray5<DynamicArray<Int256>>>>() {}.getType()),
                is(1)); // int256[5][5][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray2<
                                        StaticArray5<
                                                StaticArray6<
                                                        StaticArray4<Int256>>>>>() {}.getType()),
                is(240)); // int256[4][6][5][2]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray10<
                                        StaticArray9<
                                                StaticArray8<
                                                        DynamicArray<Int256>>>>>() {}.getType()),
                is(1)); // int256[][8][9][10]

        assertThat(
                Utils.getOffset(new TypeReference<StaticArray17<Address>>() {}.getType()),
                is(17)); // address[17]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<Address>>() {}.getType()),
                is(1)); // address[]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<DynamicArray<Address>>>() {}.getType()),
                is(1)); // address[][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray19<DynamicArray<Address>>>() {}.getType()),
                is(1)); // address[][19]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<StaticArray15<Address>>>() {}.getType()),
                is(1)); // address[15][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray15<StaticArray15<Address>>>() {}.getType()),
                is(225)); // address[15][15]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray13<
                                        StaticArray31<StaticArray31<Address>>>>() {}.getType()),
                is(12493)); // address[31][31][13]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray18<StaticArray10<Address>>>>() {}.getType()),
                is(1)); // address[10][18][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray128<
                                        StaticArray128<
                                                StaticArray128<
                                                        StaticArray128<Address>>>>>() {}.getType()),
                is(268435456)); // address[128][128][128][128]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray128<
                                        StaticArray128<
                                                StaticArray128<
                                                        DynamicArray<Address>>>>>() {}.getType()),
                is(1)); // address[][128][128][128]

        assertThat(
                Utils.getOffset(new TypeReference<StaticArray5<Utf8String>>() {}.getType()),
                is(1)); // string[5]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<Utf8String>>() {}.getType()),
                is(1)); // string[]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<DynamicArray<Utf8String>>>() {}.getType()),
                is(1)); // string[][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray13<DynamicArray<Utf8String>>>() {}.getType()),
                is(1)); // string[][13]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<StaticArray14<Utf8String>>>() {}.getType()),
                is(1)); // string[14][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray9<StaticArray9<Utf8String>>>() {}.getType()),
                is(1)); // string[9][9]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray11<
                                        StaticArray11<StaticArray11<Utf8String>>>>() {}.getType()),
                is(1)); // string[11][11][11]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray8<StaticArray8<Utf8String>>>>() {}.getType()),
                is(1)); // string[8][8][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray7<
                                        StaticArray6<
                                                StaticArray5<
                                                        StaticArray4<
                                                                Utf8String>>>>>() {}.getType()),
                is(1)); // string[4][5][6][7]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray14<
                                        StaticArray13<
                                                StaticArray12<
                                                        DynamicArray<
                                                                Utf8String>>>>>() {}.getType()),
                is(1)); // string[][12][13][14]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray12<
                                        StaticArray16<
                                                DynamicArray<
                                                        DynamicArray<
                                                                Utf8String>>>>>() {}.getType()),
                is(1)); // string[12][16][][]

        assertThat(
                Utils.getOffset(new TypeReference<StaticArray1<DynamicBytes>>() {}.getType()),
                is(1)); // bytes[1]
        assertThat(
                Utils.getOffset(new TypeReference<DynamicArray<DynamicBytes>>() {}.getType()),
                is(1)); // bytes[]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<DynamicArray<DynamicBytes>>>() {}.getType()),
                is(1)); // bytes[][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<DynamicArray<StaticArray7<DynamicBytes>>>() {}.getType()),
                is(1)); // bytes[7][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray9<DynamicArray<DynamicBytes>>>() {}.getType()),
                is(1)); // bytes[][9]
        assertThat(
                Utils.getOffset(
                        new TypeReference<StaticArray5<StaticArray5<DynamicBytes>>>() {}.getType()),
                is(1)); // bytes[5][5]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray2<StaticArray3<DynamicBytes>>>>() {}.getType()),
                is(1)); // bytes[3][2][1]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                DynamicArray<
                                        StaticArray9<StaticArray7<DynamicBytes>>>>() {}.getType()),
                is(1)); // bytes[7][9][]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        StaticArray1<
                                                                DynamicBytes>>>>>() {}.getType()),
                is(1)); // bytes[1][1][1][1]
        assertThat(
                Utils.getOffset(
                        new TypeReference<
                                StaticArray1<
                                        StaticArray1<
                                                StaticArray1<
                                                        DynamicArray<
                                                                DynamicBytes>>>>>() {}.getType()),
                is(1)); // bytes[][1][1][1]
    }

    @Test
    public void encodeGetLengthTest() {
        // uint256
        assertThat(Utils.getLength(Arrays.asList(new Uint256(1))), is(1));
        // int256
        assertThat(Utils.getLength(Arrays.asList(new Int256(1))), is(1));
        // bool
        assertThat(Utils.getLength(Arrays.asList(new Bool(true))), is(1));
        // address
        assertThat(
                Utils.getLength(
                        Arrays.asList(new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))),
                is(1));
        // bytes2
        assertThat(Utils.getLength(Arrays.asList(new Bytes2("ab".getBytes()))), is(1));
        // bytes32
        assertThat(
                Utils.getLength(
                        Arrays.asList(new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()))),
                is(1));
        // bytes
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicBytes("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()))),
                is(1));
        // string
        assertThat(
                Utils.getLength(Arrays.asList(new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"))),
                is(1));

        // uint256[4]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray4<Uint256>(
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1)))),
                is(4));
        // uint256[5]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray5<Uint256>(
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1)))),
                is(5));
        // uint256[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<Uint256>(
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1),
                                        new Uint256(1)))),
                is(1));
        // uint256[][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Uint256>(
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1)),
                                        new DynamicArray<Uint256>(
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1))))),
                is(1));
        // uint256[2][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Uint256>(new Uint256(1), new Uint256(1)),
                                        new StaticArray2<Uint256>(
                                                new Uint256(1), new Uint256(1))))),
                is(1));
        // uint256[][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Uint256>(new Uint256(1), new Uint256(1)),
                                        new DynamicArray<Uint256>(
                                                new Uint256(1), new Uint256(1))))),
                is(1));
        // uint256[2][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<StaticArray2>(
                                        new StaticArray2<Uint256>(new Uint256(1), new Uint256(1)),
                                        new StaticArray2<Uint256>(
                                                new Uint256(1), new Uint256(1))))),
                is(4));

        // int256[4]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray4<Int256>(
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1)))),
                is(4));
        // int256[5]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray5<Int256>(
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1)))),
                is(5));
        // int256[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<Int256>(
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1),
                                        new Int256(1)))),
                is(1));
        // int256[][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Int256>(
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1)),
                                        new DynamicArray<Int256>(
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1))))),
                is(1));
        // int256[][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Int256>(
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1)),
                                        new DynamicArray<Int256>(
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1))))),
                is(1));
        // int256[2][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1)),
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1))))),
                is(1));

        // int256[2][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<StaticArray2>(
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1)),
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1))))),
                is(4));

        // bool[4]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray4<Bool>(
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true)))),
                is(4));
        // bool[10]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray10<Bool>(
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true)))),
                is(10));
        // bool[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<Bool>(
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true),
                                        new Bool(true)))),
                is(1));
        // bool[][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Bool>(
                                                new Bool(true),
                                                new Bool(true),
                                                new Bool(true),
                                                new Bool(true),
                                                new Bool(true)),
                                        new DynamicArray<Bool>(
                                                new Bool(true),
                                                new Bool(true),
                                                new Bool(true),
                                                new Bool(true),
                                                new Bool(true))))),
                is(1));
        // bool[2][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Bool>(new Bool(true), new Bool(true)),
                                        new StaticArray2<Bool>(new Bool(true), new Bool(true))))),
                is(1));
        // bool[][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Bool>(new Bool(true), new Bool(true)),
                                        new DynamicArray<Bool>(new Bool(true), new Bool(true))))),
                is(1));
        // bool[2][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<StaticArray2>(
                                        new StaticArray2<Bool>(new Bool(true), new Bool(true)),
                                        new StaticArray2<Bool>(new Bool(true), new Bool(true))))),
                is(4));
        // bool[2][2][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<StaticArray2<StaticArray2>>(
                                        new StaticArray2<StaticArray2>(
                                                new StaticArray2<Bool>(
                                                        new Bool(true), new Bool(true)),
                                                new StaticArray2<Bool>(
                                                        new Bool(true), new Bool(true))),
                                        new StaticArray2<StaticArray2>(
                                                new StaticArray2<Bool>(
                                                        new Bool(true), new Bool(true)),
                                                new StaticArray2<Bool>(
                                                        new Bool(true), new Bool(true)))))),
                is(8));

        // address[4]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray4<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))),
                is(4));
        // address[5]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray5<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))),
                is(5));
        // address[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address(
                                                "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")))),
                is(1));
        // address[][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                        new DynamicArray<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))))),
                is(1));
        // address[2][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                        new StaticArray2<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))))),
                is(1));
        // address[][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                        new DynamicArray<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))))),
                is(1));
        // address[3][3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray3<StaticArray3>(
                                        new StaticArray3<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                        new StaticArray3<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                        new StaticArray3<Address>(
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                                new Address(
                                                        "0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))))),
                is(9));

        // bytes32[6]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray6<Bytes32>(
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes())))),
                is(6));
        // bytes32[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<Bytes32>(
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes())))),
                is(1));
        // bytes32[][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new DynamicArray<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(1));
        // bytes32[2][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray2<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(1));
        // bytes32[][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new DynamicArray<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(1));
        // bytes32[3][3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray3<StaticArray3>(
                                        new StaticArray3<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray3<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray3<Bytes32>(
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new Bytes32(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(9));

        // string[2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<Utf8String>(
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")))),
                is(1));
        // string[4]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray4<Utf8String>(
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")))),
                is(1));
        // string[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<Utf8String>(
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                        new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")))),
                is(1));
        // string[][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))))),
                is(1));
        // string[2][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new StaticArray2<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))))),
                is(1));
        // string[][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))))),
                is(1));
        // string[3][3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray3<StaticArray3>(
                                        new StaticArray3<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new StaticArray3<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new StaticArray3<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))))),
                is(1));

        // bytes[2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicBytes>(
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes())))),
                is(1));
        // bytes[3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray3<DynamicBytes>(
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes())))),
                is(1));
        // bytes[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new DynamicBytes(
                                                "b87213121fb89cbd8b877cb1bb3ff84d".getBytes())))),
                is(1));
        // bytes[][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(1));
        // bytes[2][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray2<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(1));
        // bytes[][]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(1));
        // bytes[3][3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray3<StaticArray3>(
                                        new StaticArray3<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray3<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray3<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(1));

        // int256
        assertThat(Utils.getLength(Arrays.asList(new Int256(1))), is(1));
        // uint256
        assertThat(Utils.getLength(Arrays.asList(new Uint256(1))), is(1));
        // int
        assertThat(Utils.getLength(Arrays.asList(new Int(new BigInteger("1")))), is(1));
        // uint
        assertThat(Utils.getLength(Arrays.asList(new Uint(new BigInteger("1")))), is(1));
        // address
        assertThat(
                Utils.getLength(
                        Arrays.asList(new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"))),
                is(1));

        // int,uint,bool,address,bytes32,bytes,string
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new Int256(1),
                                new Uint256(1),
                                new Bool(true),
                                new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                new DynamicBytes("aa".getBytes()),
                                new Utf8String("bb"))),
                is(7));
        // int[3],uint[3],bool[3],address[3],bytes32[3],bytes[3],string[3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray3<Int256>(
                                        new Int256(1), new Int256(1), new Int256(1)),
                                new StaticArray3<Uint256>(
                                        new Uint256(1), new Uint256(1), new Uint256(1)),
                                new StaticArray3<Bool>(
                                        new Bool(true), new Bool(false), new Bool(true)),
                                new StaticArray3<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new StaticArray3<Bytes32>(
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes())),
                                new StaticArray3<DynamicBytes>(
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes())),
                                new StaticArray3<Utf8String>(
                                        new Utf8String("bb"),
                                        new Utf8String("bb"),
                                        new Utf8String("bb")))),
                is(17));
        // int[],uint[],bool[],address[],bytes32[],bytes[],string[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new DynamicArray<Int256>(
                                        new Int256(1), new Int256(1), new Int256(1)),
                                new DynamicArray<Uint256>(
                                        new Uint256(1), new Uint256(1), new Uint256(1)),
                                new DynamicArray<Bool>(
                                        new Bool(true), new Bool(false), new Bool(true)),
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new DynamicArray<Bytes32>(
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes())),
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes())),
                                new DynamicArray<Utf8String>(
                                        new Utf8String("bb"),
                                        new Utf8String("bb"),
                                        new Utf8String("bb")))),
                is(7));
        // int,uint,bool,address,bytes32,bytes,string,int[3],uint[3],bool[3],address[3],bytes32[3],bytes[3],string[3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new Int256(1),
                                new Uint256(1),
                                new Bool(true),
                                new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                new DynamicBytes("aa".getBytes()),
                                new Utf8String("bb"),
                                new StaticArray3<Int256>(
                                        new Int256(1), new Int256(1), new Int256(1)),
                                new StaticArray3<Uint256>(
                                        new Uint256(1), new Uint256(1), new Uint256(1)),
                                new StaticArray3<Bool>(
                                        new Bool(true), new Bool(false), new Bool(true)),
                                new StaticArray3<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new StaticArray3<Bytes32>(
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes())),
                                new StaticArray3<DynamicBytes>(
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes())),
                                new StaticArray3<Utf8String>(
                                        new Utf8String("bb"),
                                        new Utf8String("bb"),
                                        new Utf8String("bb")))),
                is(24));
        // int,uint,bool,address,bytes32,bytes,string,int[],uint[],bool[],address[],bytes32[],bytes[],string[]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new Int256(1),
                                new Uint256(1),
                                new Bool(true),
                                new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                new DynamicBytes("aa".getBytes()),
                                new Utf8String("bb"),
                                new DynamicArray<Int256>(
                                        new Int256(1), new Int256(1), new Int256(1)),
                                new DynamicArray<Uint256>(
                                        new Uint256(1), new Uint256(1), new Uint256(1)),
                                new DynamicArray<Bool>(
                                        new Bool(true), new Bool(false), new Bool(true)),
                                new DynamicArray<Address>(
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa"),
                                        new Address("0xb87213121fb89cbd8b877cb1bb3ff84dd2869cfa")),
                                new DynamicArray<Bytes32>(
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes()),
                                        new Bytes32("b87213121fb89cbd8b877cb1bb3ff84d".getBytes())),
                                new DynamicArray<DynamicBytes>(
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes()),
                                        new DynamicBytes("aa".getBytes())),
                                new DynamicArray<Utf8String>(
                                        new Utf8String("bb"),
                                        new Utf8String("bb"),
                                        new Utf8String("bb")))),
                is(14));

        // uint256[][2],uint256[2][],uint256[][],uint256[2][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Uint256>(
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1)),
                                        new DynamicArray<Uint256>(
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1),
                                                new Uint256(1))),
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Uint256>(new Uint256(1), new Uint256(1)),
                                        new StaticArray2<Uint256>(new Uint256(1), new Uint256(1))),
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Uint256>(new Uint256(1), new Uint256(1)),
                                        new DynamicArray<Uint256>(new Uint256(1), new Uint256(1))),
                                new StaticArray2<StaticArray2>(
                                        new StaticArray2<Uint256>(new Uint256(1), new Uint256(1)),
                                        new StaticArray2<Uint256>(
                                                new Uint256(1), new Uint256(1))))),
                is(7));
        // int256[][2],int256[2][],int256[][],int256[2][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Int256>(
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1)),
                                        new DynamicArray<Int256>(
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1),
                                                new Int256(1))),
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1)),
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1))),
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Int256>(new Int256(1), new Int256(1)),
                                        new DynamicArray<Int256>(new Int256(1), new Int256(1))),
                                new StaticArray2<StaticArray2>(
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1)),
                                        new StaticArray2<Int256>(new Int256(1), new Int256(1))))),
                is(7));
        // string[][2],string[2][],string[][],string[3][3]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))),
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new StaticArray2<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))),
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new DynamicArray<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))),
                                new StaticArray3<StaticArray3>(
                                        new StaticArray3<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new StaticArray3<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d")),
                                        new StaticArray3<Utf8String>(
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String("b87213121fb89cbd8b877cb1bb3ff84d"),
                                                new Utf8String(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"))))),
                is(4));
        // bytes[][2],bytes[2][],bytes[][],bytes[2][2]
        assertThat(
                Utils.getLength(
                        Arrays.asList(
                                new StaticArray2<DynamicArray>(
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))),
                                new DynamicArray<StaticArray2>(
                                        new StaticArray2<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray2<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))),
                                new DynamicArray<DynamicArray>(
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new DynamicArray<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))),
                                new StaticArray3<StaticArray3>(
                                        new StaticArray3<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray3<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes())),
                                        new StaticArray3<DynamicBytes>(
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()),
                                                new DynamicBytes(
                                                        "b87213121fb89cbd8b877cb1bb3ff84d"
                                                                .getBytes()))))),
                is(4));
    }
}
