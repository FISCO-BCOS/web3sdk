package org.bcos.channel.test.block;

import org.bcos.channel.test.TestBase;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.response.EthBlock;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

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

}
