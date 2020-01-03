package org.fisco.bcos.web3j.crypto;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SignatureException;
import java.util.Arrays;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.math.ec.ECPoint;
import org.fisco.bcos.web3j.crypto.gm.sm2.crypto.asymmetric.SM2Algorithm;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.encoders.Hex;
import org.fisco.bcos.web3j.utils.Numeric;
import org.junit.Test;

public class SignTest {

    private static final byte[] TEST_MESSAGE = "A test message".getBytes();

    //    @Test
    //    public void testSignMessage() {
    //        Sign.SignatureData signatureData = Sign.signPrefixedMessage(TEST_MESSAGE,
    //                SampleKeys.KEY_PAIR);
    //
    //        Sign.SignatureData expected = new Sign.SignatureData(
    //                (byte) 28,
    //                Numeric.hexStringToByteArray(
    //                        "0x0464eee9e2fe1a10ffe48c78b80de1ed8dcf996f3f60955cb2e03cb21903d930"),
    //                Numeric.hexStringToByteArray(
    //                        "0x06624da478b3f862582e85b31c6a21c6cae2eee2bd50f55c93c4faad9d9c8d7f")
    //        );
    //
    //        assertThat(signatureData, is(expected));
    //    }

    //    @Test
    //    public void testSignedMessageToKey() throws SignatureException {
    //        Sign.SignatureData signatureData = Sign.signPrefixedMessage(TEST_MESSAGE,
    //                SampleKeys.KEY_PAIR);
    //        BigInteger key = Sign.signedPrefixedMessageToKey(TEST_MESSAGE, signatureData);
    //        assertThat(key, equalTo(SampleKeys.PUBLIC_KEY));
    //    }

    @Test
    public void testPublicKeyFromPrivateKey() {
        assertThat(
                Sign.publicKeyFromPrivate(SampleKeys.PRIVATE_KEY), equalTo(SampleKeys.PUBLIC_KEY));
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidSignature() throws SignatureException {
        Sign.signedMessageToKey(
                TEST_MESSAGE, new Sign.SignatureData((byte) 27, new byte[] {1}, new byte[] {0}));
    }

    @Test
    public void testPublicKeyFromPrivatePoint() {
        ECPoint point = Sign.publicPointFromPrivate(SampleKeys.PRIVATE_KEY);
        assertThat(Sign.publicFromPoint(point.getEncoded(false)), equalTo(SampleKeys.PUBLIC_KEY));
    }

    @Test
    public void testGmSignVerify() throws IOException {
        byte[] sourceData =
                Hex.decode("434477813974bf58f94bcf760833c2b40f77a5fc360485b0b9ed1bd9682edb45");
        String publicKey =
                "e8c670380cb220095268f40221fc748fa6ac39d6e930e63c30da68bad97f885da6e8c9ad722c3683ab859393220d1431eb1818ed44a942efb07b261a0fc769e7";
        String sign =
                "09628650676000c8d18bf43db68e7f66dfaed230d87e6391c29eb594b7b9cc3c8d370dbd29ce62bbcf3506adb57f041d8646ae4f70a26ea5179418e738fd4372e8c670380cb220095268f40221fc748fa6ac39d6e930e63c30da68bad97f885da6e8c9ad722c3683ab859393220d1431eb1818ed44a942efb07b261a0fc769e7";
        byte[] signatureBytes = Numeric.hexStringToByteArray("0x" + sign);

        ASN1Integer d_r =
                new ASN1Integer(new BigInteger(1, Arrays.copyOfRange(signatureBytes, 0, 32)));
        ASN1Integer d_s =
                new ASN1Integer(new BigInteger(1, Arrays.copyOfRange(signatureBytes, 32, 64)));
        ASN1EncodableVector v2 = new ASN1EncodableVector();
        v2.add(d_r);
        v2.add(d_s);
        DERSequence der = new DERSequence(v2);
        boolean b =
                SM2Algorithm.verify(
                        sourceData,
                        der.getEncoded(),
                        publicKey.substring(0, 64),
                        publicKey.substring(64, 128));
        assertTrue("Test sm2 verify", b);
    }
}
