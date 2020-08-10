package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.util.List;

import org.fisco.bcos.web3j.protocol.core.Response;

public class BlockTransactionReceipts
        extends Response<BlockTransactionReceipts.BlockTransactionReceiptsInfo> {

    public BlockTransactionReceiptsInfo getBlockTransactionReceipts() {
        return getResult();
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

    public static class BlockInfo {
        private String blockHash;
        private String blockNumber;
        private String receiptRoot;
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
