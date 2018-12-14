package org.fisco.bcos.channel.test.contract;

import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;

public class Erc20Test extends TestBase {

    @Test
    public void testOkContract() throws Exception {

        java.math.BigInteger gasPrice = new BigInteger("300000000");
        java.math.BigInteger gasLimit = new BigInteger("300000000");
        java.math.BigInteger initialWeiValue = new BigInteger("0");
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        NewSolTest erc20 = NewSolTest.deploy(web3j, credentials, contractGasProvider).send();

        if (erc20 != null) {
            System.out.println("####contract address is: " + erc20.getContractAddress());
            TransactionReceipt receipt = erc20.transfer("0x0f49a17d17f82da2a7d92ecf19268274150eaf5e", new BigInteger("100")).send();
            assertTrue(receipt.getBlockNumber().intValue() > 0);
            assertTrue(receipt.getTransactionIndex().intValue() >= 0);
            assertTrue(receipt.getGasUsed().intValue() > 0);

            BigInteger oldBalance = erc20.balanceOf("0x0f49a17d17f82da2a7d92ecf19268274150eaf5e").send();
            System.out.println("0x0f49a17d17f82da2a7d92ecf19268274150eaf5e balance" + oldBalance.intValue());
            assertTrue(oldBalance.intValue() == 100);
        }
    }
}