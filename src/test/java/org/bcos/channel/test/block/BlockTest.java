package org.bcos.channel.test.block;

import org.bcos.channel.test.Ok;
import org.bcos.channel.test.TestBase;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.response.EthBlock;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BlockTest extends TestBase {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");

    @Test
    public void getBlockNumber() throws IOException {
        EthBlock.Block block=  web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("1")), true).send().getBlock();
       assertEquals( block.getNonce(),new BigInteger("0"));
        assertTrue( block.getNumber().intValue()>0);
    }
    @Test
    public void testDeployAndInvokeContract() throws Exception {
        Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).sendAsync().get(60000, TimeUnit.MILLISECONDS);
        if (okDemo != null) {
            System.out.println("####contract address is: " + okDemo.getContractAddress());
            TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);

            assertTrue( receipt.getBlockNumber().intValue()>0);
            assertTrue( receipt.getTransactionIndex().intValue()>=0);
            assertTrue( receipt.getGasUsed().intValue()>0);

            BigInteger oldBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
             okDemo.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
            BigInteger newBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
            assertTrue(newBalance.intValue() == oldBalance.intValue() + 4);
            Thread.sleep(1000);
        }
    }
}
