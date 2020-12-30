package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

public class BcosBlockHeader extends Response<BcosBlockHeader.BlockHeader> {

    @Override
    public void setResult(BlockHeader result) {
        super.setResult(result);
    }

    public BlockHeader getBlockHeader() {
        return getResult();
    }

    public static class Signature {
        private String index;
        private String signature;

        public Signature() {}

        public Signature(String index, String signature) {
            this.index = index;
            this.signature = signature;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }

    public static class BlockHeader {
        private String dbHash;
        private List<String> extraData;
        private String gasLimit;
        private String gasUsed;
        private String hash;
        private String logsBloom;
        private String number;
        private String parentHash;
        private String transactionsRoot;
        private String sealer;
        private List<String> sealerList;
        private List<Signature> signatureList;
        private String stateRoot;
        private String receiptsRoot;
        private String timestamp;

        public BlockHeader() {}

        public String getDbHash() {
            return dbHash;
        }

        public void setDbHash(String dbHash) {
            this.dbHash = dbHash;
        }

        public List<String> getExtraData() {
            return extraData;
        }

        public void setExtraData(List<String> extraData) {
            this.extraData = extraData;
        }

        public String getGasLimit() {
            return gasLimit;
        }

        public void setGasLimit(String gasLimit) {
            this.gasLimit = gasLimit;
        }

        public String getGasUsed() {
            return gasUsed;
        }

        public void setGasUsed(String gasUsed) {
            this.gasUsed = gasUsed;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getLogsBloom() {
            return logsBloom;
        }

        public void setLogsBloom(String logsBloom) {
            this.logsBloom = logsBloom;
        }

        public BigInteger getNumber() {
            return Numeric.decodeQuantity(number);
        }

        public String getNumberRaw() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getParentHash() {
            return parentHash;
        }

        public void setParentHash(String parentHash) {
            this.parentHash = parentHash;
        }

        public String getTransactionsRoot() {
            return transactionsRoot;
        }

        public void setTransactionsRoot(String transactionsRoot) {
            this.transactionsRoot = transactionsRoot;
        }

        public String getSealer() {
            return sealer;
        }

        public void setSealer(String sealer) {
            this.sealer = sealer;
        }

        public List<String> getSealerList() {
            return sealerList;
        }

        public void setSealerList(List<String> sealerList) {
            this.sealerList = sealerList;
        }

        public String getStateRoot() {
            return stateRoot;
        }

        public void setStateRoot(String stateRoot) {
            this.stateRoot = stateRoot;
        }

        public String getReceiptsRoot() {
            return receiptsRoot;
        }

        public void setReceiptsRoot(String receiptsRoot) {
            this.receiptsRoot = receiptsRoot;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public List<Signature> getSignatureList() {
            return signatureList;
        }

        public void setSignatureList(List<Signature> signatureList) {
            this.signatureList = signatureList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlockHeader that = (BlockHeader) o;
            return Objects.equals(dbHash, that.dbHash)
                    && Objects.equals(extraData, that.extraData)
                    && Objects.equals(gasLimit, that.gasLimit)
                    && Objects.equals(gasUsed, that.gasUsed)
                    && Objects.equals(hash, that.hash)
                    && Objects.equals(logsBloom, that.logsBloom)
                    && Objects.equals(number, that.number)
                    && Objects.equals(parentHash, that.parentHash)
                    && Objects.equals(transactionsRoot, that.transactionsRoot)
                    && Objects.equals(sealer, that.sealer)
                    && Objects.equals(sealerList, that.sealerList)
                    && Objects.equals(signatureList, that.signatureList)
                    && Objects.equals(stateRoot, that.stateRoot)
                    && Objects.equals(receiptsRoot, that.receiptsRoot)
                    && Objects.equals(timestamp, that.timestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    dbHash,
                    extraData,
                    gasLimit,
                    gasUsed,
                    hash,
                    logsBloom,
                    number,
                    parentHash,
                    transactionsRoot,
                    sealer,
                    sealerList,
                    signatureList,
                    stateRoot,
                    receiptsRoot,
                    timestamp);
        }

        @Override
        public String toString() {
            return "BlockHeader{"
                    + "dbHash='"
                    + dbHash
                    + '\''
                    + ", extraData="
                    + extraData
                    + ", gasLimit='"
                    + gasLimit
                    + '\''
                    + ", gasUsed='"
                    + gasUsed
                    + '\''
                    + ", hash='"
                    + hash
                    + '\''
                    + ", logsBloom='"
                    + logsBloom
                    + '\''
                    + ", number="
                    + number
                    + ", parentHash='"
                    + parentHash
                    + '\''
                    + ", transactionsRoot='"
                    + transactionsRoot
                    + '\''
                    + ", sealer='"
                    + sealer
                    + '\''
                    + ", sealerList="
                    + sealerList
                    + ", signatureList="
                    + signatureList
                    + ", stateRoot='"
                    + stateRoot
                    + '\''
                    + ", receiptsRoot='"
                    + receiptsRoot
                    + '\''
                    + ", timestamp='"
                    + timestamp
                    + '\''
                    + '}';
        }
    }
}
