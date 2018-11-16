package org.bcos.channel.test;

import org.bcos.channel.test.contract.Ok;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class BasicTest extends TestBase {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");

    @Test
    public void  basicTest() throws Exception {
        try {

            testDeployContract(web3j, credentials);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Execute basic test failed");
        }
    }


    private void testDeployContract(Web3j web3j, Credentials credentials) throws Exception {
        Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).send();
        if (okDemo != null) {
            System.out.println("####get nonce from Block: " + web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("0")), true).send().getBlock().getNonce());
            System.out.println("####get block number by index from Block: " + web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("1")), true).send().getBlock().getNumber());

            System.out.println("####contract address is: " + okDemo.getContractAddress());
            TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
            System.out.println("###callback trans success");

            System.out.println("####get block number from TransactionReceipt: " + receipt.getBlockNumber());
            System.out.println("####get transaction index from TransactionReceipt: " + receipt.getTransactionIndex());
            System.out.println("####get gas used from TransactionReceipt: " + receipt.getGasUsed());
       //   System.out.println("####get cumulative gas used from TransactionReceipt: " + receipt.getCumulativeGasUsed());

            BigInteger toBalance = okDemo.get().send();
            System.out.println("============to balance:" + toBalance.intValue());
        }
    }
}
