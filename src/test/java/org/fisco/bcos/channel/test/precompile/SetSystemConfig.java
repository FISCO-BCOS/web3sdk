package org.fisco.bcos.channel.test.precompile;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public class SetSystemConfig {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");
    private static String SystemConfigPrecompileAddress = "0x0000000000000000000000000000000000001000";

    public void call(String[] args, Web3j web3j, Credentials credentials, int groupId) throws Exception {
        /// get functions
        if (args.length < 3)
            Usage(args);
        String key = args[1];
        String value = args[2];
            
        System.out.println("==== setValueByKey " + key + " " + value + " of " + groupId);
        SetValueByKey(key, value, web3j, credentials);
        System.out.println("==== setValueByKey " + key + " " + value + " of " + groupId + " END ====");
        System.exit(0);
    }

    private void Usage(String[] args) {
        System.out.println("Usage:");
        System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager setSystemConfig ${key} ${value}");
        System.exit(0);
    }

    public String SetValueByKey(String key, String value, Web3j web3j, Credentials credentials) throws Exception {
        return SetValue(SystemConfigPrecompileAddress, web3j, credentials, key, value);
    }
	
    private String SetValue(String address, Web3j web3j, Credentials credentials, String key, String value) throws Exception {
        SystemConfigTable systemConfig = SystemConfigTable.load(address, web3j, credentials, gasPrice, gasLimit);
        TransactionReceipt receipt = systemConfig.setValueByKey(key, value).send();
        return Common.getJsonStr(receipt.getOutput());
    }
}