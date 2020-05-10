package org.fisco.bcos.channel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.RevertResolver;
import org.junit.Test;

public class RevertResolverTest {

    private Function newFunction(String name, String message) {
        return new Function(
                name,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(message)),
                Collections.singletonList(new TypeReference<Utf8String>() {}));
    }

    @Test
    public void hasRevertMessageTest() throws IOException {
        String revertMessage = "RevertMessage";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        assertFalse(RevertResolver.hasRevertMessage(null, null));
        assertFalse(RevertResolver.hasRevertMessage("", null));
        assertFalse(RevertResolver.hasRevertMessage(null, ""));
        assertFalse(RevertResolver.hasRevertMessage("", ""));
        assertFalse(RevertResolver.hasRevertMessage("0x0", ""));
        assertFalse(RevertResolver.hasRevertMessage("0x0", ""));
        assertFalse(RevertResolver.hasRevertMessage("0x0", revertABI));
        assertTrue(RevertResolver.hasRevertMessage("0x1", revertABI));
        assertFalse(RevertResolver.hasRevertMessage(null, revertABI));

        assertFalse(RevertResolver.hasRevertMessage("0x0", testABI));
        assertFalse(RevertResolver.hasRevertMessage("0x1", testABI));
        assertFalse(RevertResolver.hasRevertMessage(null, testABI));
    }

    @Test
    public void hasRevertMessageSMTest() throws IOException {
        EncryptType.setEncryptType(EncryptType.SM2_TYPE);
        String revertMessage = "RevertMessage";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        assertFalse(RevertResolver.hasRevertMessage(null, null));
        assertFalse(RevertResolver.hasRevertMessage("", null));
        assertFalse(RevertResolver.hasRevertMessage(null, ""));
        assertFalse(RevertResolver.hasRevertMessage("", ""));
        assertFalse(RevertResolver.hasRevertMessage("0x0", ""));
        assertFalse(RevertResolver.hasRevertMessage("0x0", ""));
        assertFalse(RevertResolver.hasRevertMessage("0x0", revertABI));
        assertTrue(RevertResolver.hasRevertMessage("0x1", revertABI));
        assertFalse(RevertResolver.hasRevertMessage(null, revertABI));

        assertFalse(RevertResolver.hasRevertMessage("0x0", testABI));
        assertFalse(RevertResolver.hasRevertMessage("0x1", testABI));
        assertFalse(RevertResolver.hasRevertMessage(null, testABI));
        EncryptType.setEncryptType(EncryptType.ECDSA_TYPE);
    }

    @Test
    public void isOutputStartWithRevertMethodTest() {
        String revertMessage = "isOutputStartWithRevertMethodTest";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        assertTrue(RevertResolver.isOutputStartWithRevertMethod(revertABI));
        assertFalse(RevertResolver.isOutputStartWithRevertMethod(testABI));
        assertTrue(RevertResolver.isOutputStartWithRevertMethod(revertABI));
        assertFalse(RevertResolver.isOutputStartWithRevertMethod(testABI));
    }

    @Test
    public void isOutputStartWithRevertMethodSMTest() {
        EncryptType.setEncryptType(EncryptType.SM2_TYPE);
        String revertMessage = "isOutputStartWithRevertMethodTest";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        assertTrue(RevertResolver.isOutputStartWithRevertMethod(revertABI));
        assertFalse(RevertResolver.isOutputStartWithRevertMethod(testABI));
        assertTrue(RevertResolver.isOutputStartWithRevertMethod(revertABI));
        assertFalse(RevertResolver.isOutputStartWithRevertMethod(testABI));
        EncryptType.setEncryptType(EncryptType.ECDSA_TYPE);
    }

    @Test
    public void tryResolveRevertMessageTest() throws IOException {
        String revertMessage = "RevertMessage";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        Tuple2<Boolean, String> booleanStringTuple2 =
                RevertResolver.tryResolveRevertMessage("", "");
        assertTrue(!booleanStringTuple2.getValue1());

        Tuple2<Boolean, String> booleanStringTuple20 =
                RevertResolver.tryResolveRevertMessage("0x0", revertABI);
        assertFalse(booleanStringTuple20.getValue1());

        Tuple2<Boolean, String> booleanStringTuple21 =
                RevertResolver.tryResolveRevertMessage("0x0", testABI);
        assertFalse(booleanStringTuple21.getValue1());

        Tuple2<Boolean, String> booleanStringTuple22 =
                RevertResolver.tryResolveRevertMessage("0x1", testABI);
        assertFalse(booleanStringTuple22.getValue1());

        Tuple2<Boolean, String> booleanStringTuple23 =
                RevertResolver.tryResolveRevertMessage("0x1", revertABI);
        assertTrue(booleanStringTuple23.getValue1());
        assertEquals(booleanStringTuple23.getValue2(), revertMessage);
    }

    @Test
    public void tryResolveRevertMessageSMTest() throws IOException {
        EncryptType.setEncryptType(EncryptType.SM2_TYPE);
        String revertMessage = "RevertMessage";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        Tuple2<Boolean, String> booleanStringTuple2 =
                RevertResolver.tryResolveRevertMessage("", "");
        assertFalse(booleanStringTuple2.getValue1());

        Tuple2<Boolean, String> booleanStringTuple20 =
                RevertResolver.tryResolveRevertMessage("0x0", revertABI);
        assertFalse(booleanStringTuple20.getValue1());

        Tuple2<Boolean, String> booleanStringTuple21 =
                RevertResolver.tryResolveRevertMessage("0x0", testABI);
        assertFalse(booleanStringTuple21.getValue1());

        Tuple2<Boolean, String> booleanStringTuple22 =
                RevertResolver.tryResolveRevertMessage("0x1", testABI);
        assertFalse(booleanStringTuple22.getValue1());

        Tuple2<Boolean, String> booleanStringTuple23 =
                RevertResolver.tryResolveRevertMessage("0x1", revertABI);
        assertTrue(booleanStringTuple23.getValue1());
        assertEquals(booleanStringTuple23.getValue2(), revertMessage);
        EncryptType.setEncryptType(EncryptType.ECDSA_TYPE);
    }

    @Test
    public void tryResolveRevertMessageTest0() throws IOException {
        String revertMessage = "";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        Tuple2<Boolean, String> booleanStringTuple2 =
                RevertResolver.tryResolveRevertMessage("", "");
        assertFalse(booleanStringTuple2.getValue1());

        Tuple2<Boolean, String> booleanStringTuple20 =
                RevertResolver.tryResolveRevertMessage("0x0", revertABI);
        assertFalse(booleanStringTuple20.getValue1());

        Tuple2<Boolean, String> booleanStringTuple21 =
                RevertResolver.tryResolveRevertMessage("0x0", testABI);
        assertFalse(booleanStringTuple21.getValue1());

        Tuple2<Boolean, String> booleanStringTuple22 =
                RevertResolver.tryResolveRevertMessage("0x1", testABI);
        assertFalse(booleanStringTuple22.getValue1());

        Tuple2<Boolean, String> booleanStringTuple23 =
                RevertResolver.tryResolveRevertMessage("0x1", revertABI);
        assertTrue(booleanStringTuple23.getValue1());
        assertEquals(booleanStringTuple23.getValue2(), revertMessage);
    }

    @Test
    public void tryResolveRevertMessageSMTest0() throws IOException {
        EncryptType.setEncryptType(EncryptType.SM2_TYPE);
        String revertMessage = "";
        Function revertFunction = newFunction("Error", revertMessage);
        String revertABI = FunctionEncoder.encode(revertFunction);

        Function testFunction = newFunction("testFunc", revertMessage);
        String testABI = FunctionEncoder.encode(testFunction);

        Tuple2<Boolean, String> booleanStringTuple2 =
                RevertResolver.tryResolveRevertMessage("", "");
        assertFalse(booleanStringTuple2.getValue1());

        Tuple2<Boolean, String> booleanStringTuple20 =
                RevertResolver.tryResolveRevertMessage("0x0", revertABI);
        assertFalse(booleanStringTuple20.getValue1());

        Tuple2<Boolean, String> booleanStringTuple21 =
                RevertResolver.tryResolveRevertMessage("0x0", testABI);
        assertFalse(booleanStringTuple21.getValue1());

        Tuple2<Boolean, String> booleanStringTuple22 =
                RevertResolver.tryResolveRevertMessage("0x1", testABI);
        assertFalse(booleanStringTuple22.getValue1());

        Tuple2<Boolean, String> booleanStringTuple23 =
                RevertResolver.tryResolveRevertMessage("0x1", revertABI);
        assertTrue(booleanStringTuple23.getValue1());
        assertEquals(booleanStringTuple23.getValue2(), revertMessage);
        EncryptType.setEncryptType(EncryptType.ECDSA_TYPE);
    }
}
