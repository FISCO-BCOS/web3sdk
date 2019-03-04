package org.fisco.bcos.channel.test.contract;

import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OkTest extends TestBase {

  public static Class<?> contractClass;
  public String contractName;
  public RemoteCall<?> remoteCall;
  public String contractAddress;
  public java.math.BigInteger gasPrice = new BigInteger("300000000");
  public java.math.BigInteger gasLimit = new BigInteger("300000000");

  @Test
  public void testOkContract() throws Exception {

    Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit).send();

    if (okDemo != null) {
      System.out.println("####contract address is: " + okDemo.getContractAddress());
      TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
      assertTrue(receipt.getBlockNumber().intValue() > 0);
      assertTrue(receipt.getTransactionIndex().intValue() >= 0);
      assertTrue(receipt.getGasUsed().intValue() > 0);
      BigInteger oldBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
      okDemo.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
      BigInteger newBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
      System.out.println("####newBalance is: " + oldBalance.intValue());
      assertTrue(newBalance.intValue() == oldBalance.intValue() + 4);
    }
  }

  @Test
  public void testLoadClass() throws Exception {

    contractName = "org.fisco.bcos.channel.test.contract." + "Ok";
    contractClass = Class.forName(contractName);
    Method deploy =
        contractClass.getMethod(
            "deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
    remoteCall = (RemoteCall<?>) deploy.invoke(null, web3j, credentials, gasPrice, gasLimit);
    Contract contract = (Contract) remoteCall.send();
    contractAddress = contract.getContractAddress();
    System.out.println(contractAddress);

    Method load =
        contractClass.getMethod(
            "load",
            String.class,
            Web3j.class,
            Credentials.class,
            BigInteger.class,
            BigInteger.class);
    Object contractObject =
        load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);
    assertNotNull(contractObject);
    System.out.println(contractObject.toString());
  }
}
