package org.fisco.bcos.contract;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;

public class OkTest extends TestBase {

  public static Class<?> contractClass;
  public String contractName;
  public RemoteCall<?> remoteCall;
  public String contractAddress;
  public java.math.BigInteger gasPrice = new BigInteger("300000000");
  public java.math.BigInteger gasLimit = new BigInteger("300000000");

  @Test
  public void testOkContract() throws Exception {

    Ok ok = Ok.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();

    if (ok != null) {
      System.out.println("####contract address is: " + ok.getContractAddress());
      TransactionReceipt receipt = ok.trans(new BigInteger("4")).send();
      assertTrue(receipt.getBlockNumber().intValue() > 0);
      assertTrue(receipt.getTransactionIndex().intValue() >= 0);
      assertTrue(receipt.getGasUsed().intValue() > 0);
      BigInteger oldBalance = ok.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
      ok.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
      BigInteger newBalance = ok.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
      System.out.println("####newBalance is: " + oldBalance.intValue());
      assertTrue(newBalance.intValue() == oldBalance.intValue() + 4);
    }
  }
}
