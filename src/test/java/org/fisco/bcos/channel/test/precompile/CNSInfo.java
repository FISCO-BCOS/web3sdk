package org.fisco.bcos.channel.test.precompile;

public class CNSInfo {
	
	private String name;
	private String version;
	private String address;
	private String abi;
	
	
	public CNSInfo() {
		super();
	}


	public CNSInfo(String name, String version, String address, String abi) {
		super();
		this.name = name;
		this.version = version;
		this.address = address;
		this.abi = abi;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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

}
