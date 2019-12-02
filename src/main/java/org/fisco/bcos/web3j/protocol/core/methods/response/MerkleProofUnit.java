package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;
import java.util.Objects;

/**
 * MerkleProofUnit object used by both {@link TransactionReceiptWithProof} and {@link
 * TransactionWithProof}.
 */
public class MerkleProofUnit {
    private List<String> left;
    private List<String> right;

    public MerkleProofUnit() {}

    public MerkleProofUnit(List<String> left, List<String> right) {
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
        if (!(o instanceof MerkleProofUnit)) {
            return false;
        }
        MerkleProofUnit that = (MerkleProofUnit) o;
        return Objects.equals(getLeft(), that.getLeft())
                && Objects.equals(getRight(), that.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeft(), getRight());
    }

    @Override
    public String toString() {
        return "MerkleProofUnit{" + "left=" + left + ", right=" + right + '}';
    }
}
