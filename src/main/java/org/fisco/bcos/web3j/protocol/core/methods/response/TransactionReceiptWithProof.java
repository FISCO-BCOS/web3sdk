package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getTransactionReceiptWithProof. */
public class TransactionReceiptWithProof
        extends Response<TransactionReceiptWithProof.ReceiptAndProof> {

    public ReceiptAndProof getTransactionReceiptWithProof() {
        return getResult();
    }

    public static class ReceiptAndProof {
        @JsonProperty("transactionReceipt")
        private TransactionReceipt transactionReceipt;

        @JsonProperty("receiptProof")
        private List<MerkleProof> receiptProof;

        public ReceiptAndProof() {
            super();
        }

        public ReceiptAndProof(
                TransactionReceipt transactionReceipt, List<MerkleProof> receiptProof) {
            super();
            this.transactionReceipt = transactionReceipt;
            this.receiptProof = receiptProof;
        }

        public TransactionReceipt getTransactionReceipt() {
            return transactionReceipt;
        }

        public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
            this.transactionReceipt = transactionReceipt;
        }

        public List<MerkleProof> getReceiptProof() {
            return receiptProof;
        }

        public void setReceiptProof(List<MerkleProof> receiptProof) {
            this.receiptProof = receiptProof;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ReceiptAndProof)) {
                return false;
            }
            ReceiptAndProof that = (ReceiptAndProof) o;
            return getTransactionReceipt().equals(that.getTransactionReceipt())
                    && getReceiptProof().equals(that.getReceiptProof());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTransactionReceipt(), getReceiptProof());
        }

        @Override
        public String toString() {
            return "ReceiptProof{"
                    + "transactionReceipt="
                    + transactionReceipt
                    + ", receiptProof="
                    + receiptProof
                    + '}';
        }
    }
}
