package org.fisco.bcos.channel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;

public class BcosBlockNotification {
    @JsonProperty("GroupID")
    private String GroupID;

    @JsonProperty("BlockNumber")
    private BigInteger BlockNumber;

    @JsonIgnore
    public BigInteger getBlockNumber() {
        return BlockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        BlockNumber = blockNumber;
    }

    @JsonIgnore
    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        this.GroupID = groupID;
    }

    @Override
    public String toString() {
        return "BcosBlkNotify [GroupID=" + GroupID + ", BlockNumber=" + BlockNumber + "]";
    }
}
