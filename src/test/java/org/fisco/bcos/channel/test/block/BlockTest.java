package org.fisco.bcos.channel.test.block;

import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthBlock;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BlockTest extends TestBase {

    @Test
    public void getBlockNumber() throws IOException {
        EthBlock.Block block=  web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("1")), true).send().getBlock();
       assertEquals( block.getNonce(),new BigInteger("0"));
        System.out.println("getBlockNumber" + block.getNumber());
        System.out.println("***** current block number is  ***** " + (web3j.getBlockNumberCache().intValue()-500));
        assertTrue( block.getNumber().intValue()>0);
    }

}
