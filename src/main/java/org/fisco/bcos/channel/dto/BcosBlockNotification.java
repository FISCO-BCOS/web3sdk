package org.fisco.bcos.channel.dto;

import java.math.BigInteger;

public class BcosBlockNotification {
    private String groupID;

    private BigInteger blockNumber;

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    @Override
    public String toString() {
        return "BcosBlkNotify [groupID=" + groupID + ", blockNumber=" + blockNumber + "]";
    }
}
