package org.fisco.bcos.web3j.utils;

public class BlockLimit {
    public static Integer blockLimit = 600;

    public BlockLimit(int blockLimit) {
        this.blockLimit = blockLimit;
    }

    public int getBlockLimit() {
        return blockLimit;
    }

    public void setBlockLimit(int blockLimit) {
        BlockLimit.blockLimit = blockLimit;
    }
}
