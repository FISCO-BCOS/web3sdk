package org.fisco.bcos.channel.test.contract;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;

public class TransTest extends TestBase {
  @Test
  public void testOkContract() throws Exception {

    BigInteger gasPrice = new BigInteger("300000000");
    BigInteger gasLimit = new BigInteger("300000000");

    Trans okDemo = Trans.deploy(web3j, credentials, gasPrice, gasLimit).send();

    if (okDemo != null) {
      System.out.println("####contract address is: " + okDemo.getContractAddress());
      TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();

      List<Trans.LogAddEventResponse> ilist = okDemo.getLogAddEvents(receipt);
      List<Log> log = ilist.stream().map(x -> x.log).collect(Collectors.toList());
      System.out.println("event log data is " + log.get(0).getData());
      BigInteger oldBalance = okDemo.get().send();

      System.out.println("####oldBalance is: " + oldBalance.intValue());

      okDemo.trans(new BigInteger("4")).send();

      BigInteger newBalance = okDemo.get().send();
      System.out.println("####newBalance is: " + newBalance.intValue());
      assertTrue(newBalance.intValue() == oldBalance.intValue() + 4);
    }
  }
}
