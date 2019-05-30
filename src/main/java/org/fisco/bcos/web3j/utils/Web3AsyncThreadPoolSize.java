package org.fisco.bcos.web3j.utils;

/** Created by mingzhenliu on 2018/8/24. */
public class Web3AsyncThreadPoolSize {
  public static Integer web3AsyncPoolSize = 2000;
  public static Integer web3AsyncCorePoolSize = 2000;

  public Web3AsyncThreadPoolSize(int web3AsyncPoolSize, int web3AsyncCorePoolSize) {
    this.web3AsyncPoolSize = web3AsyncPoolSize;
    this.web3AsyncCorePoolSize = web3AsyncCorePoolSize;
  }
}
