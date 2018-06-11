package org.bcos.contract.tools;

/**
 * 
 */
public class CNSEle {
	
	private String contract;
	private String version;
	private String time;
	private String address;
	private String bk;
	private String abi;
	
	public CNSEle(String abi, String address, String contract, String version, String bk, String time) {
		this.contract = contract;
		this.version = version;
		this.time = time;
		this.address = address;
		this.abi = abi;
		this.bk = bk;
	}
	
	@Override
	public String toString() {
		return "CNSEle [contract=" + contract + ", version=" + version + ", time=" + time + ", address=" + address
				+ ", bk=" + bk + ", abi=" + abi + "]";
	}

	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAbi() {
		return abi;
	}
	public void setAbi(String abi) {
		this.abi = abi;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBk() {
		return bk;
	}
	public void setBk(String bk) {
		this.bk = bk;
	}
}
