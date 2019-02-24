package org.fisco.bcos.channel.test.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Ignore;
import org.junit.Test;

public class PressureTest extends TestBase {

  @Ignore
  @Test
  public void pressureTest() throws InterruptedException, ExecutionException {
    int threadNum = 500;
    System.out.println("####create credential succ, begin deploy contract");
    java.math.BigInteger gasPrice = new BigInteger("300000000");
    java.math.BigInteger gasLimit = new BigInteger("300000000");
    final Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit).sendAsync().get();
    if (okDemo != null) {
      ArrayList<Thread> threadArray = new ArrayList<Thread>();
      for (int threadIndex = 0; threadIndex < threadNum; threadIndex++) {
        threadArray.add(
            new Thread("" + threadIndex) {
              public void run() {

                try {
                  // System.out.println("####contract address is: " + okDemo.getContractAddress());
                  TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).sendAsync().get();
                  System.out.println("###callback trans success");
                  BigInteger toBalance = okDemo.get().sendAsync().get();
                  System.out.println("============to balance:" + toBalance.intValue());
                  Thread.sleep(1000); // sleep 1s
                } catch (Exception e) {
                  System.out.println("callback trans failed, error message:" + e.getMessage());
                }
              }
            });
        threadArray.get(threadIndex).start();
      }
      // join all theads
      for (int threadIndex = 0; threadIndex < threadNum; threadIndex++)
        threadArray.get(threadIndex).join();
    }
  }
}
