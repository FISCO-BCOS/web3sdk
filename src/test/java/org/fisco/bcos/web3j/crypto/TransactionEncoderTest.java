package org.fisco.bcos.web3j.crypto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.rlp.RlpType;
import org.fisco.bcos.web3j.utils.Numeric;
import org.junit.Ignore;
import org.junit.Test;

public class TransactionEncoderTest {

  static BigInteger gasPrice = BigInteger.ONE;
  static BigInteger gasLimit = BigInteger.TEN;

  @Test
  public void testSignMessage() {
    byte[] signedMessage =
        TransactionEncoder.signMessage(createEtherTransaction(), SampleKeys.CREDENTIALS);
    String hexMessage = Numeric.toHexString(signedMessage);
    assertThat(
        hexMessage,
        is(
            "0xf85a8201f4010a8201f5840add5355887fffffffffffffff801ba01cf44d4680e1ecaf11a9a997b08055ae84c5d417b1fc7c2bdbaffc3fd4a7659aa07a424ef2ad019c599a24309c97f4cd10d0e4293a51d8c1abb095052bf54a7ba7"));
  }

  @Ignore
  @Test
  public void testGMSignMessage() {
    EncryptType encryptType = new EncryptType(1);

    Credentials credentials =
        GenCredential.create("a392604efc2fad9c0b3da43b5f698a2e3f270f170d859912be0d54742275c5f6");
    System.out.println(credentials.getEcKeyPair().getPublicKey().toString(16));

    Credentials credentials1 =
        Credentials.create("a392604efc2fad9c0b3da43b5f698a2e3f270f170d859912be0d54742275c5f6");
    System.out.println(credentials1.getEcKeyPair().getPublicKey().toString(16));

    byte[] signedMessage = TransactionEncoder.signMessage(createContractTransaction(), credentials);
    String hexMessage = Numeric.toHexString(signedMessage);
    assertThat(
        hexMessage,
        is(
            "0xf8948201f4010a8201f5800a850000000000b8408234c544a9f3ce3b401a92cc7175602ce2a1e29b1ec135381c7d2a9e8f78f3edc9c06ee55252857c9a4560cb39e9d70d40f4331cace4d2b3121b967fa7a829f0a03d8627050f6688f27e2b5b89c9c141d3a48603029849e088486d1c7ea079ea7fa037024ed35d2c099d7eb68fb133e57735b03605ec32ded39ab305c3b56e5d99e7"));
  }

  @Test
  public void testEtherTransactionAsRlpValues() {
    List<RlpType> rlpStrings =
        TransactionEncoder.asRlpValues(
            createEtherTransaction(), new Sign.SignatureData((byte) 0, new byte[32], new byte[32]));
    assertThat(rlpStrings.size(), is(10));
    //     assertThat(rlpStrings.get(3), equalTo(RlpString.create(new BigInteger("10"))));
  }

  @Test
  public void testContractAsRlpValues() {
    List<RlpType> rlpStrings = TransactionEncoder.asRlpValues(createContractTransaction(), null);
    assertThat(rlpStrings.size(), is(7));
    //      assertThat(rlpStrings.get(3), is(RlpString.create("")));
  }

  //    @Test
  //    public void testEip155Encode() {
  //        assertThat(TransactionEncoder.encode(createEip155RawTransaction(), (byte) 1),
  //                is(Numeric.hexStringToByteArray(
  //
  // "0xec098504a817c800825208943535353535353535353535353535353535353535880de0"
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
  //
  // "0xf86c098504a817c800825208943535353535353535353535353535353535353535880"
  //                                +
  // "de0b6b3a76400008025a028ef61340bd939bc2195fe537567866003e1a15d"
  //                                +
  // "3c71ff63e1590620aa636276a067cbe9d8997f761aecb703304b3800ccf55"
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
        randomid, gasPrice, gasLimit, blockLimit, BigInteger.TEN, "0x0000000000");
  }
}
