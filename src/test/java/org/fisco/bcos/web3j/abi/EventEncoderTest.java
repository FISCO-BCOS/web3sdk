package org.fisco.bcos.web3j.abi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Uint;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes10;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray6;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint32;
import org.junit.Test;

public class EventEncoderTest {

    @Test
    public void testBuildEventSignature() {
        assertThat(
                EventEncoder.buildEventSignature("Deposit(address,hash256,uint256)"),
                is("0x50cb9fe53daa9737b786ab3646f04d0150dc50ef4e75f59509d83667ad5adb20"));

        assertThat(
                EventEncoder.buildEventSignature("Notify(uint256,uint256)"),
                is("0x71e71a8458267085d5ab16980fd5f114d2d37f232479c245d523ce8d23ca40ed"));
    }

    @Test
    public void testEventbuildMethodSignature0() {
        Event event =
                new Event(
                        "g",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<DynamicArray<Uint256>>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("g(uint256[][],string[])"));

        assertThat(
                EventEncoder.encode(event),
                is("0x2289b18cd8c6e198648b35d3bcf2ff8668984543f01927711c161bcf7b5e1bba"));

        assertThat(
                EventEncoder.buildEventSignature("g(uint256[][],string[])"),
                is("0x2289b18cd8c6e198648b35d3bcf2ff8668984543f01927711c161bcf7b5e1bba"));
    }

    @Test
    public void testEventbuildMethodSignature1() {
        Event event =
                new Event(
                        "f",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Uint>() {},
                                new TypeReference<DynamicArray<Uint32>>() {},
                                new TypeReference<Bytes10>() {},
                                new TypeReference<DynamicBytes>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("f(uint256,uint32[],bytes10,bytes)"));

        assertThat(
                EventEncoder.encode(event),
                is("0x8be652465888a0c5d65fc9d0a7e898b9ca98de97185c53a54ec408fd2fd5d45d"));

        assertThat(
                EventEncoder.buildEventSignature("f(uint256,uint32[],bytes10,bytes)"),
                is("0x8be652465888a0c5d65fc9d0a7e898b9ca98de97185c53a54ec408fd2fd5d45d"));
    }

    @Test
    public void testEventbuildMethodSignature2() {
        Event event =
                new Event(
                        "f",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicBytes>() {},
                                new TypeReference<Bool>() {},
                                new TypeReference<DynamicArray<Uint256>>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("f(bytes,bool,uint256[])"));

        assertThat(
                EventEncoder.encode(event),
                is("0xa83b3f0112fa8ecc02937d734929bbaa30731fe27b20195418852fb64ac2837d"));

        assertThat(
                EventEncoder.buildEventSignature("f(bytes,bool,uint256[])"),
                is("0xa83b3f0112fa8ecc02937d734929bbaa30731fe27b20195418852fb64ac2837d"));
    }

    @Test
    public void testEventbuildMethodSignature3() {
        Event event =
                new Event(
                        "test3",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<StaticArray2<Bytes3>>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test3(bytes3[2])"));

        assertThat(
                EventEncoder.encode(event),
                is("0x8e8ba55ef93e108e0902c0aed7709f74b477e2a1a98497d860d050eb5ac8e599"));

        assertThat(
                EventEncoder.buildEventSignature("test3(bytes3[2])"),
                is("0x8e8ba55ef93e108e0902c0aed7709f74b477e2a1a98497d860d050eb5ac8e599"));
    }

    @Test
    public void testEventbuildMethodSignature4() {
        Event event =
                new Event(
                        "test4",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Uint32>() {}, new TypeReference<Bool>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test4(uint32,bool)"));

        assertThat(
                EventEncoder.encode(event),
                is("0xd7ee7b8ce8fd8944f6c4fc1d3d8f656f855a5b9c130a876af272bdb06b056f9c"));

        assertThat(
                EventEncoder.buildEventSignature("test4(uint32,bool)"),
                is("0xd7ee7b8ce8fd8944f6c4fc1d3d8f656f855a5b9c130a876af272bdb06b056f9c"));
    }

    @Test
    public void testEventbuildMethodSignature5() {
        Event event =
                new Event(
                        "test5",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<
                                        DynamicArray<
                                                DynamicArray<
                                                        DynamicArray<
                                                                DynamicArray<Utf8String>>>>>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test5(string[][][][])"));

        assertThat(
                EventEncoder.encode(event),
                is("0x69f175db6ba77ff81ba5e31ca58ddfeb6b2ea420d1233fba18371dc63a12e9c1"));

        assertThat(
                EventEncoder.buildEventSignature("test5(string[][][][])"),
                is("0x69f175db6ba77ff81ba5e31ca58ddfeb6b2ea420d1233fba18371dc63a12e9c1"));
    }

    @Test
    public void testEventbuildMethodSignature6() {
        Event event =
                new Event(
                        "test6",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<StaticArray3<Utf8String>>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test6(uint256,int256,string,string[],string[3])"));

        assertThat(
                EventEncoder.encode(event),
                is("0x5199068657caa55d23ad866ba738a7f21e567e6f50aa8173ac108efdd5d3bb79"));

        assertThat(
                EventEncoder.buildEventSignature("test6(uint256,int256,string,string[],string[3])"),
                is("0x5199068657caa55d23ad866ba738a7f21e567e6f50aa8173ac108efdd5d3bb79"));
    }

    @Test
    public void testEventbuildMethodSignature7() {
        Event event =
                new Event(
                        "test7",
                        Arrays.<TypeReference<?>>asList(
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
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is(
                        "test7(uint256,address,string,uint256[],uint256[3],string[],string[3],uint256[][],uint256[3][])"));

        assertThat(
                EventEncoder.encode(event),
                is("0x63c45f0c8793f28acf7f800281aaf63198c09afdef34c785429628237221a648"));

        assertThat(
                EventEncoder.buildEventSignature(
                        "test7(uint256,address,string,uint256[],uint256[3],string[],string[3],uint256[][],uint256[3][])"),
                is("0x63c45f0c8793f28acf7f800281aaf63198c09afdef34c785429628237221a648"));
    }

    @Test
    public void testEventbuildMethodSignature8() {
        Event event =
                new Event(
                        "test8",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<Uint256>() {},
                                new TypeReference<Int256>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test8(string,address,uint256,int256)"));

        assertThat(
                EventEncoder.encode(event),
                is("0xfa3d7a982eb00eec003de55d3e9266fa4b9ccd6a3a067b75b24ef6b4cb9b12c4"));
        assertThat(
                EventEncoder.buildEventSignature("test8(string,address,uint256,int256)"),
                is("0xfa3d7a982eb00eec003de55d3e9266fa4b9ccd6a3a067b75b24ef6b4cb9b12c4"));
    }

    @Test
    public void testEventbuildMethodSignature9() {
        Event event =
                new Event(
                        "test9",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Uint256>() {},
                                new TypeReference<StaticArray6<Uint256>>() {},
                                new TypeReference<DynamicArray<Uint256>>() {},
                                new TypeReference<Bool>() {},
                                new TypeReference<Address>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test9(string,uint256,uint256[6],uint256[],bool,address)"));

        assertThat(
                EventEncoder.encode(event),
                is("0xa37d8a63087cf5837e0b9ff13d07d756479f8afdfe4b05ea3dfdc98154ef58ed"));

        assertThat(
                EventEncoder.buildEventSignature(
                        "test9(string,uint256,uint256[6],uint256[],bool,address)"),
                is("0xa37d8a63087cf5837e0b9ff13d07d756479f8afdfe4b05ea3dfdc98154ef58ed"));
    }

    @Test
    public void testEventbuildMethodSignature10() {
        Event event =
                new Event(
                        "test10",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test10(string[])"));

        assertThat(
                EventEncoder.encode(event),
                is("0xfb9fe4911c195fb1112aaa9c3ac5a54a0c521f8cb0f4ee9290a9bff28542d051"));

        assertThat(
                EventEncoder.buildEventSignature("test10(string[])"),
                is("0xfb9fe4911c195fb1112aaa9c3ac5a54a0c521f8cb0f4ee9290a9bff28542d051"));
    }

    @Test
    public void testEventbuildMethodSignature11() {
        Event event = new Event("test11", Arrays.<TypeReference<?>>asList());

        assertThat(
                EventEncoder.buildMethodSignature(event.getName(), event.getParameters()),
                is("test11()"));

        assertThat(
                EventEncoder.encode(event),
                is("0xfa8b8ea1bf7b2a8633a34bc7af9661842883700a6d001d980310b17cf602b71f"));

        assertThat(
                EventEncoder.buildEventSignature("test11()"),
                is("0xfa8b8ea1bf7b2a8633a34bc7af9661842883700a6d001d980310b17cf602b71f"));
    }
}
