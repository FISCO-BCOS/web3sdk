package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.math.BigInteger;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceiptWithProof;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionWithProof;
import org.fisco.bcos.web3j.rlp.RlpEncoder;
import org.fisco.bcos.web3j.rlp.RlpString;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionResource {
    private static final Logger logger = LoggerFactory.getLogger(TransactionResource.class);

    private Web3j web3j;

    public TransactionResource(Web3j web3j) {
        this.web3j = web3j;
    }

    public TransactionWithProof getTransactionWithProof(String transactionHash, String rootHash)
            throws IOException {
        TransactionWithProof transactionWithProof =
                web3j.getTransactionByHashWithProof(transactionHash).send();
        if (transactionWithProof.getTransactionWithProof() == null) {
            return null;
        }

        Transaction transaction = transactionWithProof.getTransactionWithProof().getTransaction();
        logger.debug("Transaction:{}", transaction);

        // transaction index
        String index = transaction.getTransactionIndexRaw();
        BigInteger indexValue = Numeric.toBigInt(index);
        byte[] byteIndex = RlpEncoder.encode(RlpString.create(indexValue));
        String input = Numeric.toHexString(byteIndex) + transactionHash.substring(2);
        logger.info("TransWithIndex:{}", input);

        String proof =
                Merkle.calculateMerkleRoot(
                        transactionWithProof.getTransactionWithProof().getTxProof(), input);
        //        System.out.println("MerkleRoot: " + proof);

        if (!proof.equals(rootHash)) {
            logger.debug("MerkleRoot:{}", proof);
            logger.debug("TransRoot :{}", rootHash);
            return null;
        }
        return transactionWithProof;
    }

    public TransactionReceiptWithProof getTransactionReceiptWithProof(
            String transactionHash, String rootHash) throws IOException {
        TransactionReceiptWithProof transactionReceiptWithProof =
                web3j.getTransactionReceiptByHashWithProof(transactionHash).send();

        if (transactionReceiptWithProof.getTransactionReceiptWithProof() == null) {
            return null;
        }
        TransactionReceipt transactionReceipt =
                transactionReceiptWithProof
                        .getTransactionReceiptWithProof()
                        .getTransactionReceipt();
        logger.debug("Receipt {}", transactionReceipt.toString());

        // transaction index
        String index = transactionReceipt.getTransactionIndexRaw();
        BigInteger indexValue = Numeric.toBigInt(index);
        byte[] byteIndex = RlpEncoder.encode(RlpString.create(indexValue));

        String receiptRlp = ReceiptEncoder.encode(transactionReceipt);
        logger.debug("ReceiptRlp:{}", receiptRlp);

        String rlpHash = Hash.sha3(receiptRlp);
        logger.debug("ReceiptRlpHash:{}", rlpHash);

        String input = Numeric.toHexString(byteIndex) + rlpHash.substring(2);
        logger.info("ReceiptWithIndex:{}", input);

        String proof =
                Merkle.calculateMerkleRoot(
                        transactionReceiptWithProof
                                .getTransactionReceiptWithProof()
                                .getReceiptProof(),
                        input);

        //        System.out.println("MerkleRoot: " + proof);
        if (!proof.equals(rootHash)) {
            logger.debug("MerkleRoot:{}", proof);
            logger.debug("TransRoot :{}", rootHash);
            return null;
        }
        return transactionReceiptWithProof;
    }

    public ImmutablePair<TransactionWithProof, TransactionReceiptWithProof>
            getTransactionAndReceiptWithProof(
                    String transactionHash, String transactionsRoot, String receiptsRoot)
                    throws IOException {
        TransactionWithProof transactionWithProof =
                getTransactionWithProof(transactionHash, transactionsRoot);
        if (transactionWithProof == null) {
            return null;
        }

        TransactionReceiptWithProof transactionReceiptWithProof =
                getTransactionReceiptWithProof(transactionHash, receiptsRoot);
        if (transactionReceiptWithProof == null) {
            return null;
        }

        String indexFromTransaction =
                transactionWithProof
                        .getTransactionWithProof()
                        .getTransaction()
                        .getTransactionIndexRaw();

        String indexFromReceipt =
                transactionReceiptWithProof
                        .getTransactionReceiptWithProof()
                        .getTransactionReceipt()
                        .getTransactionIndexRaw();

        logger.debug(
                "indexFromTransaction:{}, indexFromReceipt:{}",
                indexFromTransaction,
                indexFromReceipt);
        if (!indexFromTransaction.equals(indexFromReceipt)) {
            return null;
        }

        return new ImmutablePair<>(transactionWithProof, transactionReceiptWithProof);
    }
}
