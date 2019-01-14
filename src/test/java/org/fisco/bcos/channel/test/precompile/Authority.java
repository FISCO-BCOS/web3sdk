package org.fisco.bcos.channel.test.precompile;

public class Authority {

    private String table_name;
    private String address;
    private String enable_num;
    
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEnable_num() {
		return enable_num;
	}
	public void setEnable_num(String enable_num) {
		this.enable_num = enable_num;
	}
	@Override
	public String toString() {
		return "Authority [table_name=" + table_name + ", address=" + address + ", enable_num=" + enable_num + "]";
	}
    
}
