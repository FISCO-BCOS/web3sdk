package org.fisco.bcos.web3j.precompile.authority;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authority {
	
	@JsonProperty("table_name")
    private String tableName;
    private String address;
	@JsonProperty("enable_num")
    private String enableNum;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEnableNum() {
		return enableNum;
	}
	public void setEnableNum(String enableNum) {
		this.enableNum = enableNum;
	}
    
	
    
}
