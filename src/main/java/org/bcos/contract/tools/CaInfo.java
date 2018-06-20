package org.bcos.contract.tools;

/**
 * Created by mingzhenliu on 2018/3/6.
 */
public class CaInfo {
    private String serial;
    private String pubkey;
    private String name;

    public String toString() {
        return "CaInfo{" +
                "serial='" + serial + '\'' +
                ", pubkey=" + pubkey +
                ", name=" + name +
                '}';
    }

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPubkey() {
		return pubkey;
	}

	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    


}
