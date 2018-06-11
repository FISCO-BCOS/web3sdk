package org.bcos.contract.tools;

/**
 * Created by mingzhenliu on 2018/3/6.
 */
public class NodeInfo {
    private String id;
    private String name;
    private String agency;
    private String caHash;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getCaHash() {
		return caHash;
	}

	public void setCaHash(String caHash) {
		this.caHash = caHash;
	}

	@Override
    public String toString() {
        return "NodeInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", agency=" + agency +
                ", caHash=" + caHash +
                '}';
    }
}
