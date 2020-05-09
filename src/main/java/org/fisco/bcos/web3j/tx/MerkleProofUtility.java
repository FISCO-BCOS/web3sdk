package org.fisco.bcos.web3j.tx;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.channel.client.Merkle;
import org.fisco.bcos.channel.client.ReceiptEncoder;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.protocol.core.methods.response.MerkleProofUnit;
import org.fisco.bcos.web3j.protocol.core.methods.response.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceiptWithProof;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionWithProof;
import org.fisco.bcos.web3j.rlp.RlpEncoder;
import org.fisco.bcos.web3j.rlp.RlpString;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerkleProofUtility {

    private static final Logger logger = LoggerFactory.getLogger(MerkleProofUtility.class);

    /**
     * Verify transaction merkle proof
     *
     * @param transactionRoot
     * @param transAndProof
     * @return
     */
    public static boolean verifyTransaction(
            String transactionRoot, TransactionWithProof.TransAndProof transAndProof) {
        // transaction index
        Transaction transaction = transAndProof.getTransaction();
        BigInteger index = transaction.getTransactionIndex();
        String input =
                Numeric.toHexString(RlpEncoder.encode(RlpString.create(index)))
                        + transaction.getHash().substring(2);
        String proof = Merkle.calculateMerkleRoot(transAndProof.getTxProof(), input);

        logger.debug(
                " transaction hash: {}, transaction index: {}, root: {}, proof: {}",
                transaction.getHash(),
                transaction.getTransactionIndex(),
                transactionRoot,
                proof);

        return proof.equals(transactionRoot);
    }

    /**
     * Verify transaction receipt merkle proof
     *
     * @param receiptRoot
     * @param receiptAndProof
     * @return
     */
    public static boolean verifyTransactionReceipt(
            String receiptRoot, TransactionReceiptWithProof.ReceiptAndProof receiptAndProof) {

        TransactionReceipt transactionReceipt = receiptAndProof.getTransactionReceipt();

        // transaction index
        byte[] byteIndex =
                RlpEncoder.encode(RlpString.create(transactionReceipt.getTransactionIndex()));

        if (!transactionReceipt.getGasUsedRaw().startsWith("0x")) {
            transactionReceipt.setGasUsed("0x" + transactionReceipt.getGasUsed().toString(16));
        }

        String receiptRlp = ReceiptEncoder.encode(transactionReceipt);
        String rlpHash = Hash.sha3(receiptRlp);
        String input = Numeric.toHexString(byteIndex) + rlpHash.substring(2);

        String proof = Merkle.calculateMerkleRoot(receiptAndProof.getReceiptProof(), input);

        logger.debug(
                " transaction hash: {}, receipt index: {}, root: {}, proof: {}, receipt: {}",
                transactionReceipt.getTransactionHash(),
                transactionReceipt.getTransactionIndex(),
                receiptRoot,
                proof,
                receiptAndProof.getTransactionReceipt());

        return proof.equals(receiptRoot);
    }

    /**
     * Verify transaction merkle proof
     *
     * @param transactionHash
     * @param index
     * @param transactionRoot
     * @param txProof
     * @return
     */
    public static boolean verifyTransaction(
            String transactionHash,
            BigInteger index,
            String transactionRoot,
            List<MerkleProofUnit> txProof) {
        String input =
                Numeric.toHexString(RlpEncoder.encode(RlpString.create(index)))
                        + transactionHash.substring(2);
        String proof = Merkle.calculateMerkleRoot(txProof, input);

        logger.debug(
                " transaction hash: {}, transaction index: {}, txProof: {}, transactionRoot: {}, proof: {}",
                transactionHash,
                index,
                txProof,
                transactionRoot,
                proof);

        return proof.equals(transactionRoot);
    }

    /**
     * Verify transaction receipt merkle proof
     *
     * @param receiptRoot
     * @param transactionReceipt
     * @param receiptProof
     * @return
     */
    public static boolean verifyTransactionReceipt(
            String receiptRoot,
            TransactionReceipt transactionReceipt,
            List<MerkleProofUnit> receiptProof) {

        if (!transactionReceipt.getGasUsedRaw().startsWith("0x")) {
            transactionReceipt.setGasUsed("0x" + transactionReceipt.getGasUsed().toString(16));
        }

        // transaction index
        byte[] byteIndex =
                RlpEncoder.encode(RlpString.create(transactionReceipt.getTransactionIndex()));

        String receiptRlp = ReceiptEncoder.encode(transactionReceipt);
        String rlpHash = Hash.sha3(receiptRlp);
        String input = Numeric.toHexString(byteIndex) + rlpHash.substring(2);

        String proof = Merkle.calculateMerkleRoot(receiptProof, input);

        logger.debug(
                " transaction hash: {}, transactionReceipt: {}, receiptProof: {}, receiptRoot: {}, proof: {}",
                transactionReceipt.getTransactionHash(),
                transactionReceipt,
                receiptProof,
                receiptRoot,
                proof);

        return proof.equals(receiptRoot);
    }
}
