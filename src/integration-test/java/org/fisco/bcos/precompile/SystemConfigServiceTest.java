package org.fisco.bcos.precompile;

import static org.junit.Assert.assertEquals;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.precompile.config.EnumKey;
import org.fisco.bcos.web3j.precompile.config.SystemConfigService;
import org.junit.Test;

public class SystemConfigServiceTest extends TestBase {


  private SystemConfigService systemConfigService = new SystemConfigService(web3j, credentials);

  @Test
  public void setValueByKey() throws Exception {

  	String txCountLimit1 = "5000";
	systemConfigService.setValueByKey(EnumKey.tx_count_limit.toString(), txCountLimit1);
  	String txCountLimit2 = web3j.getSystemConfigByKey(EnumKey.tx_count_limit.toString()).send().getSystemConfigByKey();
  	assertEquals(txCountLimit1, txCountLimit2);
  	
  	String txGasLimit1 = "50000000";
  	systemConfigService.setValueByKey(EnumKey.tx_gas_limit.toString(), txGasLimit1);
  	String txGasLimit2 = web3j.getSystemConfigByKey(EnumKey.tx_gas_limit.toString()).send().getSystemConfigByKey();
  	assertEquals(txGasLimit1, txGasLimit2);
  	
  }
  
}
