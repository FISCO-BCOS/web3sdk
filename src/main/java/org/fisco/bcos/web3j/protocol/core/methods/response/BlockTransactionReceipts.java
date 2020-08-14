package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.Arrays;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockTransactionReceipts extends Response<String> {

    private static Logger logger = LoggerFactory.getLogger(BlockTransactionReceipts.class);

    public byte[] compress(String data) {

        Deflater compress = new Deflater();

        compress.setInput(data.getBytes());

        byte[] compressedData = new byte[data.length()];
        compress.finish();

        int compressLength = compress.deflate(compressedData, 0, compressedData.length);
        return Arrays.copyOfRange(compressedData, 0, compressLength);
    }

    public byte[] uncompress(byte[] compressedData) throws IOException, DataFormatException {

        Inflater decompressor = new Inflater();
        decompressor.setInput(compressedData);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length)) {
            byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }

            return bos.toByteArray();
        }
    }

    public BlockTransactionReceiptsInfo getBlockTransactionReceipts()
            throws IOException, DataFormatException {

        String base64Data = getResult();
        /** Base64 encoding data */
        byte[] zipData = Base64.getDecoder().decode(base64Data);

        /** zip compression data */
        byte[] jsonData = uncompress(zipData);

        BlockTransactionReceiptsInfo blockTransactionReceiptsInfo =
                ObjectMapperFactory.getObjectMapper()
                        .readValue(jsonData, BlockTransactionReceiptsInfo.class);

        if (logger.isTraceEnabled()) {
            logger.trace(
                    " base64 pack size: {}, unzip size: {}, json size: {}",
                    base64Data.length(),
                    zipData.length,
                    jsonData.length);
            logger.trace(" block receipts: {}", blockTransactionReceiptsInfo);
        }

        return blockTransactionReceiptsInfo;
    }

    public static class BlockTransactionReceiptsInfo {
        private BlockInfo blockInfo;
        private List<TransactionReceipt> transactionReceipts;

        public BlockInfo getBlockInfo() {
            return blockInfo;
        }

        public void setBlockInfo(BlockInfo blockInfo) {
            this.blockInfo = blockInfo;
        }

        public List<TransactionReceipt> getTransactionReceipts() {
            return transactionReceipts;
        }

        public void setTransactionReceipts(List<TransactionReceipt> transactionReceipts) {
            this.transactionReceipts = transactionReceipts;
        }

        @Override
        public String toString() {
            return "ReceiptsInfo{"
                    + "blockInfo="
                    + blockInfo
                    + ", transactionReceipts="
                    + transactionReceipts
                    + '}';
        }
    }

    /** The transaction receipts common fields of the block */
    public static class BlockInfo {
        /** Block Hash */
        private String blockHash;
        /** BlockNumber */
        private String blockNumber;
        /** Transaction Receipt Root */
        private String receiptRoot;
        /** The total number of transaction receipts contained in the block */
        private String receiptsCount;

        public String getBlockHash() {
            return blockHash;
        }

        public void setBlockHash(String blockHash) {
            this.blockHash = blockHash;
        }

        public String getBlockNumber() {
            return blockNumber;
        }

        public void setBlockNumber(String blockNumber) {
            this.blockNumber = blockNumber;
        }

        public String getReceiptRoot() {
            return receiptRoot;
        }

        public void setReceiptRoot(String receiptRoot) {
            this.receiptRoot = receiptRoot;
        }

        public String getReceiptsCount() {
            return receiptsCount;
        }

        public void setReceiptsCount(String receiptsCount) {
            this.receiptsCount = receiptsCount;
        }

        @Override
        public String toString() {
            return "BlockInfo{"
                    + "blockHash='"
                    + blockHash
                    + '\''
                    + ", blockNumber='"
                    + blockNumber
                    + '\''
                    + ", receiptRoot='"
                    + receiptRoot
                    + '\''
                    + ", receiptsCount='"
                    + receiptsCount
                    + '\''
                    + '}';
        }
    }
}
