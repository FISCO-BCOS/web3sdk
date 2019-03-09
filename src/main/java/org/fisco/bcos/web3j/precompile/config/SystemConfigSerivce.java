package org.fisco.bcos.web3j.precompile.config;

import java.math.BigInteger;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

public class SystemConfigSerivce {
  private static BigInteger gasPrice = new BigInteger("300000000");
  private static BigInteger gasLimit = new BigInteger("300000000");
  private static String systemConfigPrecompileAddress =
      "0x0000000000000000000000000000000000001000";
  private SystemConfig systemConfig;

  public SystemConfigSerivce(Web3j web3j, Credentials credentials) {
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    systemConfig =
        SystemConfig.load(systemConfigPrecompileAddress, web3j, credentials, contractGasProvider);
  }

  public String setValueByKey(String key, String value) throws Exception {
    TransactionReceipt receipt = systemConfig.setValueByKey(key, value).send();
    return PrecompiledCommon.handleTransactionReceipt(receipt);
  }
}
