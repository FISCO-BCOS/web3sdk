package org.fisco.bcos.web3j.tx.txdecode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigInteger;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes7;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.junit.Test;

public class ResultEntityTest {

    @Test
    public void resultEntityTestTest() {
        ResultEntity r = new ResultEntity("name", "type", new Uint256(2));
        assertThat(r.getName(), is("name"));
        assertThat(r.getType(), is("type"));
    }

    @Test
    public void typeToObjectTest0() {

        assertThat(ResultEntity.typeToObject(new Uint256(2)), is(new BigInteger("2")));
        assertThat(ResultEntity.typeToObject(new Int256(-1)), is(new BigInteger("-1")));
        assertThat(ResultEntity.typeToObject(new Utf8String("adsfjkl")), is("adsfjkl"));
        assertThat(ResultEntity.typeToObject(new Bool(true)), is(true));
        assertThat(ResultEntity.typeToObject(new DynamicBytes("0x111".getBytes())), is("0x111"));
        assertThat(
                ResultEntity.typeToObject(new Address("0x111")),
                is("0x0000000000000000000000000000000000000111"));
        assertThat(
                ResultEntity.typeToObject(
                        new Bytes32("01234567890123456789012345678912".getBytes())),
                is("01234567890123456789012345678912"));
    }

    @Test
    public void typeToObjectArrayTest() throws JsonProcessingException {
        ResultEntity r0 = new ResultEntity("string", "string", new Utf8String("章鱼丸子"));
        assertThat(r0.toJson(), is("{\"name\":\"string\",\"type\":\"string\",\"data\":\"章鱼丸子\"}"));
        ResultEntity r1 = new ResultEntity("uint256", "uint256", new Uint256(247809787));
        assertThat(
                r1.toJson(), is("{\"name\":\"uint256\",\"type\":\"uint256\",\"data\":247809787}"));
        ResultEntity r2 = new ResultEntity("int256", "int256", new Int256(-247809787));
        assertThat(
                r2.toJson(), is("{\"name\":\"int256\",\"type\":\"int256\",\"data\":-247809787}"));
        ResultEntity r3 = new ResultEntity("bool", "bool", new Bool(true));
        assertThat(r3.toJson(), is("{\"name\":\"bool\",\"type\":\"bool\",\"data\":true}"));
        ResultEntity r4 =
                new ResultEntity("bytes", "bytes", new DynamicBytes("dasfjl;kljadfkl".getBytes()));
        assertThat(
                r4.toJson(),
                is("{\"name\":\"bytes\",\"type\":\"bytes\",\"data\":\"dasfjl;kljadfkl\"}"));

        ResultEntity r5 =
                new ResultEntity(
                        "StaticArray1", "StaticArray1", new StaticArray1<Uint256>(new Uint256(22)));
        assertThat(
                r5.toJson(),
                is("{\"name\":\"StaticArray1\",\"type\":\"StaticArray1\",\"data\":[22]}"));
        ResultEntity r6 =
                new ResultEntity(
                        "StaticArray3",
                        "StaticArray3",
                        new StaticArray3<Uint256>(new Uint256(1), new Uint256(2), new Uint256(3)));
        assertThat(
                r6.toJson(),
                is("{\"name\":\"StaticArray3\",\"type\":\"StaticArray3\",\"data\":[1,2,3]}"));
        ResultEntity r7 =
                new ResultEntity(
                        "DynamicArray",
                        "DynamicArray",
                        new DynamicArray<Bool>(new Bool(true), new Bool(false), new Bool(true)));
        assertThat(
                r7.toJson(),
                is(
                        "{\"name\":\"DynamicArray\",\"type\":\"DynamicArray\",\"data\":[true,false,true]}"));
        ResultEntity r8 =
                new ResultEntity(
                        "DynamicArray",
                        "DynamicArray",
                        new DynamicArray<Bytes7>(
                                new Bytes7("sdafljk".getBytes()),
                                new Bytes7("sdafljk".getBytes()),
                                new Bytes7("sdafljk".getBytes())));
        assertThat(
                r8.toJson(),
                is(
                        "{\"name\":\"DynamicArray\",\"type\":\"DynamicArray\",\"data\":[\"sdafljk\",\"sdafljk\",\"sdafljk\"]}"));
    }
}
