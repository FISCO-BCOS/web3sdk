package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getTransactionWithProof. */
public class TransactionWithProof extends Response<TransactionWithProof.TransAndProof> {

    public TransAndProof getTransactionWithProof() {
        return getResult();
    }

    public static class TransAndProof {
        @JsonProperty("transaction")
        private Transaction transaction;

        @JsonProperty("txProof")
        private List<MerkleProof> txProof;

        public TransAndProof() {
            super();
        }

        public TransAndProof(Transaction transaction, List<MerkleProof> txProof) {
            super();
            this.transaction = transaction;
            this.txProof = txProof;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

        public List<MerkleProof> getTxProof() {
            return txProof;
        }

        public void setTxProof(List<MerkleProof> txProof) {
            this.txProof = txProof;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TransAndProof)) {
                return false;
            }
            TransAndProof tranProof = (TransAndProof) o;
            return Objects.equals(getTransaction(), tranProof.getTransaction())
                    && Objects.equals(getTxProof(), tranProof.getTxProof());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTransaction(), getTxProof());
        }

        @Override
        public String toString() {
            return "TransAndProof{" + "transaction=" + transaction + ", txProof=" + txProof + '}';
        }
    }
}
