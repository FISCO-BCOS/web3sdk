package org.fisco.bcos.web3j.utils;

/** Created by mingzhenliu on 2018/8/24. */
public class BlockLimit {
  public static Integer blockLimit = 500;

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
