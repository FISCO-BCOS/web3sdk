package org.fisco.bcos.channel.client;

import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.protocol.core.methods.response.MerkleProof;

public class MerkleRoot {
    public static String calculateMerkleRoot(List<MerkleProof> merkleProofs, String hash) {
        if (merkleProofs == null) return hash;
        String result = hash;
        for (MerkleProof merkleProof : merkleProofs) {
            String left = splicing(merkleProof.getLeft());
            String right = splicing(merkleProof.getRight());
            String input = "0x" + splicing(left, result.substring(2), right);
            result = Hash.sha3(input);
        }
        return result;
    }

    private static String splicing(List<String> stringList) {
        StringBuilder result = new StringBuilder();
        for (String eachString : stringList) {
            result.append(eachString);
        }
        return result.toString();
    }

    private static String splicing(String... stringList) {
        return splicing(Arrays.<String>asList(stringList));
    }
}
