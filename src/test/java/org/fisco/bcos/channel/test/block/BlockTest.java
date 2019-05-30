package org.fisco.bcos.channel.test.block;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.utils.Numeric;
import org.junit.Test;

public class BlockTest extends TestBase {

  @Test
  public void getBlockNumber() throws IOException {
    final String blockNumber = web3j.getBlockNumber().sendForReturnString();
    System.out.println(blockNumber);
    assertTrue(Numeric.decodeQuantity(blockNumber).compareTo(new BigInteger("0")) >= 0);
  }
}
