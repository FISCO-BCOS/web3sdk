package org.fisco.bcos.web3j.cns;

import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;

import java.util.List;

public class Contracts {
    private String name;
    private String version;
    private String address;
    private String abi;

    public String getAbi() {
        return abi;
    }

    public void setAbi(String abi) {
        this.abi = abi;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
