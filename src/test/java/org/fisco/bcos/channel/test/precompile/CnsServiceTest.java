package org.fisco.bcos.channel.test.precompile;

import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.channel.test.contract.Ok;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.cns.CnsService;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CnsServiceTest extends TestBase {

    public Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

    CnsService cnsService = new CnsService(web3j, credentials);

    java.math.BigInteger gasPrice = new BigInteger("300000000");
    java.math.BigInteger gasLimit = new BigInteger("300000000");
    java.math.BigInteger initialWeiValue = new BigInteger("0");

    @Test
    public void getContractAddressFromNameAndVersion() throws Exception {

        Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).send();
        System.out.println("okdemo contract address " + okDemo.getContractAddress());
        int random = new Random().nextInt(1000);
        String name = "hello world" + random;
        String result = cnsService.registerCns(name , "10.0", okDemo.getContractAddress(),"[\n" +
            "\t{\n" +
            "\t\t\"constant\": false,\n" +
            "\t\t\"inputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"name\": \"num\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"name\": \"trans\",\n" +
            "\t\t\"outputs\": [],\n" +
            "\t\t\"payable\": false,\n" +
            "\t\t\"type\": \"function\",\n" +
            "\t\t\"stateMutability\": \"nonpayable\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"constant\": true,\n" +
            "\t\t\"inputs\": [],\n" +
            "\t\t\"name\": \"get\",\n" +
            "\t\t\"outputs\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"name\": \"\",\n" +
            "\t\t\t\t\"type\": \"uint256\"\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"payable\": false,\n" +
            "\t\t\"type\": \"function\",\n" +
            "\t\t\"stateMutability\": \"view\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"inputs\": [],\n" +
            "\t\t\"type\": \"constructor\",\n" +
            "\t\t\"payable\": true,\n" +
            "\t\t\"stateMutability\": \"payable\"\n" +
            "\t}\n" +
            "]");
        System.out.println("result:" +result);
        System.out.println("CNS NAME   " + name+":9.0" );
        System.out.println("CNS register SUCCESSFULLY");
        System.out.println( "cnsResolver address" + cnsService.getAddressByContractNameAndVersion(name +":10.0"));
        System.out.println( "cnsResolver address" + cnsService.getAddressByContractNameAndVersion(name ));
        System.out.println( "cnsResolver address" + cnsService.getAddressByContractNameAndVersion(okDemo.getContractAddress() ));

        Ok okLoaded =  Ok.load(name,web3j, credentials, gasPrice, gasLimit);
        System.out.println(okLoaded.isValid());
        BigInteger balance = okLoaded.get().send();
        System.out.println("balance = "  + balance );
        assertEquals(cnsService.getAddressByContractNameAndVersion(name +":10.0"),okDemo.getContractAddress());

    }


}
