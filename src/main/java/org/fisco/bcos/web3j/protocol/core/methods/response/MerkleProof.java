package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;
import java.util.Objects;

/**
 * MerkleProof object used by both {@link TransactionReceiptWithProof} and {@link
 * TransactionWithProof}.
 */
public class MerkleProof {
    private List<String> left;
    private List<String> right;

    public MerkleProof() {}

    public MerkleProof(List<String> left, List<String> right) {
        this.left = left;
        this.right = right;
    }

    public List<String> getLeft() {
        return left;
    }

    public void setLeft(List<String> left) {
        this.left = left;
    }

    public List<String> getRight() {
        return right;
    }

    public void setRight(List<String> right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MerkleProof)) {
            return false;
        }
        MerkleProof that = (MerkleProof) o;
        return Objects.equals(getLeft(), that.getLeft())
                && Objects.equals(getRight(), that.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeft(), getRight());
    }

    @Override
    public String toString() {
        return "MerkleRoot{" + "left=" + left + ", right=" + right + '}';
    }
}
