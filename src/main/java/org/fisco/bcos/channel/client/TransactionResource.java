package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.protocol.Web3j;
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
        if (transactionWithProof.getTransactionWithProof() == null) return null;

        // transaction index
        String index =
                transactionWithProof
                        .getTransactionWithProof()
                        .getTransaction()
                        .getTransactionIndexRaw();
        BigInteger indexValue = Numeric.toBigInt(index);
        byte[] byteIndex = RlpEncoder.encode(RlpString.create(indexValue));
        String input = Numeric.toHexString(byteIndex) + transactionHash.substring(2);
        logger.info("Trans:{}", input);

        String thisHash = Hash.sha3(input);
        logger.info("ThisHash:{}", thisHash);

        String proof =
                MerkleRoot.calculateMerkleRoot(
                        transactionWithProof.getTransactionWithProof().getTxProof(), thisHash);

        if (!proof.equals(rootHash)) return null;
        return transactionWithProof;
    }

    public TransactionReceiptWithProof getTransactionReceiptWithProof(
            String transactionHash, String rootHash) throws IOException {
        TransactionReceiptWithProof transactionReceiptWithProof =
                web3j.getReceiptByHashWithProof(transactionHash).send();

        if (transactionReceiptWithProof.getTransactionReceiptWithProof() == null) return null;
        TransactionReceipt transactionReceipt =
                transactionReceiptWithProof
                        .getTransactionReceiptWithProof()
                        .getTransactionReceipt();
        logger.info(transactionReceipt.toString());

        // transaction index
        String index = transactionReceipt.getTransactionIndexRaw();
        BigInteger indexValue = Numeric.toBigInt(index);
        byte[] byteIndex = RlpEncoder.encode(RlpString.create(indexValue));

        String receiptRlp = ReceiptRlp.encode(transactionReceipt);
        logger.info("ReceiptRlp:{}", receiptRlp);

        String rlpHash = Hash.sha3(receiptRlp);
        logger.info("ReceiptHash:{}", rlpHash);

        String input = Numeric.toHexString(byteIndex) + rlpHash.substring(2);
        String thistHash = Hash.sha3(input);
        logger.info("thisHash:{}", thistHash);

        String proof =
                MerkleRoot.calculateMerkleRoot(
                        transactionReceiptWithProof
                                .getTransactionReceiptWithProof()
                                .getReceiptProof(),
                        thistHash);
        logger.info("MerkleProof:{}", proof);

        if (!proof.equals(rootHash)) return null;
        return transactionReceiptWithProof;
    }
}
