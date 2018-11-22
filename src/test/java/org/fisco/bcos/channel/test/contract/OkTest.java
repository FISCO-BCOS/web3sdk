package org.fisco.bcos.channel.test.contract;

import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;


public class OkTest extends TestBase {

    @Test
    public void testOkContract() throws Exception {

        java.math.BigInteger gasPrice = new BigInteger("300000000");
        java.math.BigInteger gasLimit = new BigInteger("300000000");
        java.math.BigInteger initialWeiValue = new BigInteger("0");

        Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).send();

        if (okDemo != null) {
                System.out.println("####contract address is: " + okDemo.getContractAddress());
                TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
            assertTrue( receipt.getBlockNumber().intValue()>0);
            assertTrue( receipt.getTransactionIndex().intValue()>=0);
            assertTrue( receipt.getGasUsed().intValue()>0);
            BigInteger oldBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
            okDemo.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
            BigInteger newBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
//           assertTrue(newBalance.intValue() == oldBalance.intValue() + 4);
            }
        }
}





