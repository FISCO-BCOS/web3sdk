package org.bcos.web3j.utils;

/**
 * Created by mingzhenliu on 2018/8/24.
 */
public class Web3AsyncThreadPoolSize {
    public static Integer web3AsyncPoolSize = 100;
    public Web3AsyncThreadPoolSize(int web3AsyncPoolSize){
        this.web3AsyncPoolSize = web3AsyncPoolSize;
    }
    public int getWeb3AsyncThreadPoolSize() {
        return web3AsyncPoolSize;
    }

    public void setWeb3AsyncThreadPoolSize(int web3AsyncPoolSize) {
        Web3AsyncThreadPoolSize.web3AsyncPoolSize = web3AsyncPoolSize;
    }
}

