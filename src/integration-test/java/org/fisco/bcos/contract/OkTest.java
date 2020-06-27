package org.fisco.bcos.contract;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;

public class OkTest extends TestBase {

  @Test
  public void testOkContract() throws Exception {

    Ok ok = Ok.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();

    if (ok != null) {
      TransactionReceipt receipt = ok.trans(new BigInteger("4")).send();
      assertTrue(receipt.isStatusOK());
      assertTrue(receipt.getBlockNumber().intValue() > 0);
      assertTrue(receipt.getTransactionIndex().intValue() >= 0);
      assertTrue(receipt.getGasUsed().intValue() > 0);
      BigInteger oldBalance = ok.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
      ok.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
      BigInteger newBalance = ok.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
      assertTrue(newBalance.intValue() == oldBalance.intValue() + 4);
    }
  }
}
