package org.fisco.bcos.web3j.precompile.permission;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vote {
    @JsonProperty("blcok_limit")
    private String blockLimit;

    private String origin;

    public String getBlockLimit() {
        return blockLimit;
    }

    public void setBlockLimit(String blockLimit) {
        this.blockLimit = blockLimit;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
