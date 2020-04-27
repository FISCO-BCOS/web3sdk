package org.fisco.bcos.web3j.protocol.core.methods.request;

import java.util.List;

public class GenerateGroupParams {
    private String timestamp;
    private List<String> sealers;
    private boolean enable_free_storage;

    public GenerateGroupParams(
            String timestamp, boolean enable_free_storage, List<String> sealers) {
        this.timestamp = timestamp;
        this.enable_free_storage = enable_free_storage;
        this.sealers = sealers;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getSealers() {
        return sealers;
    }

    public void setSealers(List<String> sealers) {
        this.sealers = sealers;
    }

    public boolean isEnable_free_storage() {
        return enable_free_storage;
    }

    public void setEnable_free_storage(boolean enable_free_storage) {
        this.enable_free_storage = enable_free_storage;
    }
}
