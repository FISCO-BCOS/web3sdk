package org.fisco.bcos.web3j.crypto;

import static org.fisco.bcos.web3j.crypto.TransactionUtils.generateTransactionHashHexEncoded;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.fisco.bcos.channel.test.TestBase;
import org.junit.Ignore;
import org.junit.Test;

public class TransactionUtilsTest extends TestBase {

  @Ignore
  @Test
  public void testGenerateTransactionHash() {
    assertThat(
        generateTransactionHashHexEncoded(
            TransactionEncoderTest.createContractTransaction(), SampleKeys.CREDENTIALS),
        is("0xc3a0f520404c8cd0cb1c98be6b8e17ee32bf134ac1697d078e90422525c2d902"));
  }

  @Ignore
  @Test
  public void testGenerateEip155TransactionHash() {
    assertThat(
        generateTransactionHashHexEncoded(
            TransactionEncoderTest.createContractTransaction(), (byte) 1, SampleKeys.CREDENTIALS),
        is("0x568c7f6920c1cee8332e245c473657b9c53044eb96ed7532f5550f1139861e9e"));
  }
}
