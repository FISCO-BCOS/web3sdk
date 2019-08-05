package org.fisco.bcos.channel.dto;

import java.math.BigInteger;

public class BcosBlkNotify {
    private String groupID;
    private BigInteger BlockNumber;

    public BigInteger getBlockNumber() {
        return BlockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        BlockNumber = blockNumber;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
