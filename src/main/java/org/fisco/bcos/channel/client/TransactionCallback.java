package org.fisco.bcos.channel.client;

import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.concurrent.Semaphore;

public class TransactionCallback extends TransactionSucCallback {
    TransactionCallback() {
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onResponse(TransactionReceipt receipt) {
        this.receipt = receipt;
        semaphore.release();
    }

    public TransactionReceipt receipt;
    public Semaphore semaphore = new Semaphore(1, true);
}
