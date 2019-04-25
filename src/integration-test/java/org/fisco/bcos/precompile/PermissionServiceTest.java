package org.fisco.bcos.precompile;

import static org.junit.Assert.assertEquals;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.precompile.permission.PermissionService;
import org.junit.Test;

public class PermissionServiceTest extends TestBase {


	PermissionService permissionService = new PermissionService(web3j, credentials);

  @Test
  public void userTableManager() throws Exception {
  	
  	String grantUserTableManagerResult = permissionService.grantUserTableManager(Common.TABLE_NAME, Common.TX_ORIGIN);
  	assertEquals(grantUserTableManagerResult, Common.SUSSCESS);
  	
  	String revokeUserTableManagerResult = permissionService.revokeUserTableManager(Common.TABLE_NAME, Common.TX_ORIGIN);
  	assertEquals(revokeUserTableManagerResult, Common.SUSSCESS);
  }
  
  @Test
  public void deployAndCreateManager() throws Exception {
  	
  	String grantDeployAndCreateManagerResult = permissionService.grantDeployAndCreateManager(Common.TX_ORIGIN);
  	assertEquals(grantDeployAndCreateManagerResult, Common.SUSSCESS);
  	
  	String revokeDeployAndCreateManagerResult = permissionService.revokeDeployAndCreateManager(Common.TX_ORIGIN);
  	assertEquals(revokeDeployAndCreateManagerResult, Common.SUSSCESS);
  	
  }
  
  @Test
  public void permissionManager() throws Exception {
  	
  	String grantPermissionManagerResult = permissionService.grantPermissionManager(Common.TX_ORIGIN);
  	assertEquals(grantPermissionManagerResult, Common.SUSSCESS);
  	
  	String revokePermissionManagerResult = permissionService.revokePermissionManager(Common.TX_ORIGIN);
  	assertEquals(revokePermissionManagerResult, Common.SUSSCESS);
  }
  
  @Test
  public void nodeManager() throws Exception {
  	
  	String grantNodeManagerResult = permissionService.grantNodeManager(Common.TX_ORIGIN);
  	assertEquals(grantNodeManagerResult, Common.SUSSCESS);
  	
  	String revokeNodeManagerResult = permissionService.revokeNodeManager(Common.TX_ORIGIN);
  	assertEquals(revokeNodeManagerResult, Common.SUSSCESS);
  }
  
  @Test
  public void cnsManager() throws Exception {
  	
  	String grantCNSManagerResult = permissionService.grantCNSManager(Common.TX_ORIGIN);
  	assertEquals(grantCNSManagerResult, Common.SUSSCESS);
  	
  	String revokeCNSManagerResult = permissionService.revokeCNSManager(Common.TX_ORIGIN);
  	assertEquals(revokeCNSManagerResult, Common.SUSSCESS);
  }
  
  @Test
  public void sysConfigManager() throws Exception {
  	
  	String grantSysConfigManagerResult = permissionService.grantSysConfigManager(Common.TX_ORIGIN);
  	assertEquals(grantSysConfigManagerResult, Common.SUSSCESS);
  	
  	String revokeSysConfigManagerResult = permissionService.revokeSysConfigManager(Common.TX_ORIGIN);
  	assertEquals(revokeSysConfigManagerResult, Common.SUSSCESS);

  }
}
