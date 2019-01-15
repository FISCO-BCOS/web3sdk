package org.fisco.bcos.web3j.precompile.config;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public class SystemConfigSerivce {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");
    private static String SystemConfigPrecompileAddress = "0x0000000000000000000000000000000000001000";


    public String SetValueByKey(String key, String value, Web3j web3j, Credentials credentials) throws Exception {
        return SetValue(SystemConfigPrecompileAddress, web3j, credentials, key, value);
    }
	
    private String SetValue(String address, Web3j web3j, Credentials credentials, String key, String value) throws Exception {
        SystemConfigTable systemConfig = SystemConfigTable.load(address, web3j, credentials, gasPrice, gasLimit);
        TransactionReceipt receipt = systemConfig.setValueByKey(key, value).send();
        return PrecompiledCommon.getJsonStr(receipt.getOutput());
    }
}