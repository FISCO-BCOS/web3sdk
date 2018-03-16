package org.bcos.web3j.protocol.core.methods.request;

/**
 * Created by suyuhui on 17/9/28.
 */
public class ProofMerkle {
    private String blockHash;
    private String transactionIndex;

    public ProofMerkle(String blockHash, String transactionIndex) {
        this.blockHash = blockHash;
        this.transactionIndex = transactionIndex;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }
}
