package org.bcos.channel.test;

import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;


public class TestOk extends TestBase {

    @Test
    public void testOkContract() throws Exception {

        java.math.BigInteger gasPrice = new BigInteger("300000000");
        java.math.BigInteger gasLimit = new BigInteger("300000000");
        java.math.BigInteger initialWeiValue = new BigInteger("0");

        Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).send();

        if (okDemo != null) {
                System.out.println("####contract address is: " + okDemo.getContractAddress());
                TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
                System.out.println("###callback trans success");
                BigInteger toBalance = okDemo.get().send();
                assertTrue( toBalance.intValue()==4);
            }
        }
}





