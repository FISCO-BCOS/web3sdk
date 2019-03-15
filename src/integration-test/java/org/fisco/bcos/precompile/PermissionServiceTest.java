package org.fisco.bcos.precompile;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.precompile.permission.PermissionInfo;
import org.fisco.bcos.web3j.precompile.permission.PermissionService;
import org.junit.Test;

public class PermissionServiceTest extends TestBase {


	PermissionService permissionService = new PermissionService(web3j, credentials);

  @Test
  public void userTableManager() throws Exception {
  	
  	String grantUserTableManagerResult = permissionService.grantUserTableManager(Common.TABLE_NAME, Common.TX_ORIGIN);
  	assertEquals(grantUserTableManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listUserTableManagerResult1 = permissionService.listUserTableManager(Common.TABLE_NAME);
  	assertEquals(listUserTableManagerResult1.size(), 1);
  	
  	String revokeUserTableManagerResult = permissionService.revokeUserTableManager(Common.TABLE_NAME, Common.TX_ORIGIN);
  	assertEquals(revokeUserTableManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listUserTableManagerResult2 = permissionService.listUserTableManager(Common.TABLE_NAME);
  	assertEquals(listUserTableManagerResult2.size(), 0);
  }
  
  @Test
  public void deployAndCreateManager() throws Exception {
  	
  	String grantDeployAndCreateManagerResult = permissionService.grantDeployAndCreateManager(Common.TX_ORIGIN);
  	assertEquals(grantDeployAndCreateManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listDeployAndCreateManagerResult1 = permissionService.listDeployAndCreateManager();
  	assertEquals(listDeployAndCreateManagerResult1.size(), 1);
  	
  	String revokeDeployAndCreateManagerResult = permissionService.revokeDeployAndCreateManager(Common.TX_ORIGIN);
  	assertEquals(revokeDeployAndCreateManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listDeployAndCreateManagerResult2 = permissionService.listDeployAndCreateManager();
  	assertEquals(listDeployAndCreateManagerResult2.size(), 0);
  }
  
  @Test
  public void permissionManager() throws Exception {
  	
  	String grantPermissionManagerResult = permissionService.grantPermissionManager(Common.TX_ORIGIN);
  	assertEquals(grantPermissionManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listPermissionManagerResult1 = permissionService.listPermissionManager();
  	assertEquals(listPermissionManagerResult1.size(), 1);
  	
  	String revokePermissionManagerResult = permissionService.revokePermissionManager(Common.TX_ORIGIN);
  	assertEquals(revokePermissionManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listPermissionManagerResult2 = permissionService.listPermissionManager();
  	assertEquals(listPermissionManagerResult2.size(), 0);
  }
  
  @Test
  public void nodeManager() throws Exception {
  	
  	String grantNodeManagerResult = permissionService.grantNodeManager(Common.TX_ORIGIN);
  	assertEquals(grantNodeManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listNodeManagerResult1 = permissionService.listNodeManager();
  	assertEquals(listNodeManagerResult1.size(), 1);
  	
  	String revokeNodeManagerResult = permissionService.revokeNodeManager(Common.TX_ORIGIN);
  	assertEquals(revokeNodeManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listNodeManagerResult2 = permissionService.listNodeManager();
  	assertEquals(listNodeManagerResult2.size(), 0);
  }
  
  @Test
  public void cnsManager() throws Exception {
  	
  	String grantCNSManagerResult = permissionService.grantCNSManager(Common.TX_ORIGIN);
  	assertEquals(grantCNSManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listCNSManagerResult1 = permissionService.listCNSManager();
  	assertEquals(listCNSManagerResult1.size(), 1);
  	
  	String revokeCNSManagerResult = permissionService.revokeCNSManager(Common.TX_ORIGIN);
  	assertEquals(revokeCNSManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listCNSManagerResult2 = permissionService.listCNSManager();
  	assertEquals(listCNSManagerResult2.size(), 0);
  }
  
  @Test
  public void sysConfigManager() throws Exception {
  	
  	String grantSysConfigManagerResult = permissionService.grantSysConfigManager(Common.TX_ORIGIN);
  	assertEquals(grantSysConfigManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listSysConfigManagerResult1 = permissionService.listSysConfigManager();
  	assertEquals(listSysConfigManagerResult1.size(), 1);
  	
  	String revokeSysConfigManagerResult = permissionService.revokeSysConfigManager(Common.TX_ORIGIN);
  	assertEquals(revokeSysConfigManagerResult, Common.SUSSCESS);
  	
  	List<PermissionInfo> listSysConfigManagerResult2 = permissionService.listSysConfigManager();
  	assertEquals(listSysConfigManagerResult2.size(), 0);
  }
}
