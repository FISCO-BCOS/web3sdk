package org.fisco.bcos.web3j.crypto;

import org.fisco.bcos.channel.test.TestBase;
import org.junit.Test;
import org.fisco.bcos.web3j.rlp.RlpString;
import org.fisco.bcos.web3j.rlp.RlpType;
import org.fisco.bcos.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TransactionEncoderTest  {

   static BigInteger gasPrice = BigInteger.ONE;
   static BigInteger gasLimit = BigInteger.TEN;

    @Test
    public void testSignMessage() {
        byte[] signedMessage = TransactionEncoder.signMessage(
                createEtherTransaction(), SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);
        assertThat(hexMessage,
                is("0xf85a8201f4010a8201f5840add5355887fffffffffffffff801ba01cf44d4680e1ecaf11a9a997b08055ae84c5d417b1fc7c2bdbaffc3fd4a7659aa07a424ef2ad019c599a24309c97f4cd10d0e4293a51d8c1abb095052bf54a7ba7"));
    }

    @Test
    public void testEtherTransactionAsRlpValues() {
        List<RlpType> rlpStrings = TransactionEncoder.asRlpValues(createEtherTransaction(),
                new Sign.SignatureData((byte) 0, new byte[32], new byte[32]));
        assertThat(rlpStrings.size(), is(10));
  //     assertThat(rlpStrings.get(3), equalTo(RlpString.create(new BigInteger("10"))));
    }

    @Test
    public void testContractAsRlpValues() {
        List<RlpType> rlpStrings = TransactionEncoder.asRlpValues(
                createContractTransaction(), null);
        assertThat(rlpStrings.size(), is(7));
  //      assertThat(rlpStrings.get(3), is(RlpString.create("")));
    }

//    @Test
//    public void testEip155Encode() {
//        assertThat(TransactionEncoder.encode(createEip155RawTransaction(), (byte) 1),
//                is(Numeric.hexStringToByteArray(
//                        "0xec098504a817c800825208943535353535353535353535353535353535353535880de0"
//                                + "b6b3a764000080018080")));
//    }
//
//    @Test
//    public void testEip155Transaction() {
//        // https://github.com/ethereum/EIPs/issues/155
//        Credentials credentials = Credentials.create(
//                "0x4646464646464646464646464646464646464646464646464646464646464646");
//
//        assertThat(TransactionEncoder.signMessage(
//                createEip155RawTransaction(), (byte) 1, credentials),
//                is(Numeric.hexStringToByteArray(
//                        "0xf86c098504a817c800825208943535353535353535353535353535353535353535880"
//                                + "de0b6b3a76400008025a028ef61340bd939bc2195fe537567866003e1a15d"
//                                + "3c71ff63e1590620aa636276a067cbe9d8997f761aecb703304b3800ccf55"
//                                + "5c9f3dc64214b297fb1966a3b6d83")));
//    }

    private static RawTransaction createEtherTransaction() {

        BigInteger randomid = new BigInteger("500");
        BigInteger blockLimit = new BigInteger("501");

        return RawTransaction.createEtherTransaction(
                randomid, gasPrice, gasLimit, blockLimit, "0xadd5355", BigInteger.valueOf(Long.MAX_VALUE));
    }

    static RawTransaction createContractTransaction() {

        BigInteger randomid = new BigInteger("500");
        BigInteger blockLimit = new BigInteger("501");

        return RawTransaction.createContractTransaction(
                randomid, gasPrice, gasLimit,blockLimit, BigInteger.TEN, "0x0000000000");
    }

}
