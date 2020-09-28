package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.HashInterface;
import org.fisco.bcos.web3j.crypto.SHA3Digest;
import org.fisco.bcos.web3j.crypto.gm.sm2.util.encoders.Hex;
import org.fisco.bcos.web3j.crypto.gm.sm3.SM3Digest;
import org.fisco.bcos.web3j.rlp.RlpEncoder;
import org.fisco.bcos.web3j.rlp.RlpList;
import org.fisco.bcos.web3j.rlp.RlpString;
import org.fisco.bcos.web3j.rlp.RlpType;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Transaction object used by both {@link BcosTransaction} and {@link BcosBlock}. */
public class Transaction {
    private static Logger logger = LoggerFactory.getLogger(Transaction.class);
    private String hash;
    private String nonce;
    private String blockHash;
    private String blockNumber;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gasPrice;
    private String gas;
    private String input;
    private String creates;
    private String publicKey;
    private String raw;
    private String r;
    private String s;
    private int v; // see https://github.com/web3j/web3j/issues/44
    private String blockLimit;
    private String chainId;
    private String groupId;
    private String extraData;
    private SignatureResponse signature;

    public class SignatureResponse {
        private String r;
        private String s;
        private String v;
        private String signature;

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        @Override
        public String toString() {
            return "SignatureResponse{"
                    + "r='"
                    + r
                    + '\''
                    + ", s='"
                    + s
                    + '\''
                    + ", v='"
                    + v
                    + '\''
                    + ", signature='"
                    + signature
                    + '\''
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SignatureResponse that = (SignatureResponse) o;
            return Objects.equals(r, that.r)
                    && Objects.equals(s, that.s)
                    && Objects.equals(v, that.v)
                    && Objects.equals(signature, that.signature);
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, s, v, signature);
        }
    }

    public Transaction() {}

    public Transaction(
            String hash,
            String nonce,
            String blockHash,
            String blockNumber,
            String transactionIndex,
            String from,
            String to,
            String value,
            String gas,
            String gasPrice,
            String input,
            String creates,
            String publicKey,
            String raw,
            String r,
            String s,
            int v) {
        this.hash = hash;
        this.nonce = nonce;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.transactionIndex = transactionIndex;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gas = gas;
        this.input = input;
        this.creates = creates;
        this.publicKey = publicKey;
        this.raw = raw;
        this.r = r;
        this.s = s;
        this.v = v;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BigInteger getNonce() {
        return Numeric.decodeQuantity(nonce);
    }

    public String getNonceRaw() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(blockNumber);
    }

    public String getBlockNumberRaw() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getTransactionIndex() {
        return Numeric.decodeQuantity(transactionIndex);
    }

    public String getTransactionIndexRaw() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return Numeric.decodeQuantity(value);
    }

    public String getValueRaw() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(gasPrice);
    }

    public String getGasPriceRaw() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGas() {
        return Numeric.decodeQuantity(gas);
    }

    public String getGasRaw() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getCreates() {
        return creates;
    }

    public void setCreates(String creates) {
        this.creates = creates;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getV() {
        return v;
    }

    // public void setV(byte v) {
    //     this.v = v;
    // }

    // Workaround until Geth & Parity return consistent values. At present
    // Parity returns a byte value, Geth returns a hex-encoded string
    // https://github.com/ethereum/go-ethereum/issues/3339
    public void setV(Object v) {
        if (v instanceof String) {
            this.v = Numeric.toBigInt((String) v).intValueExact();
        } else {
            this.v = ((Integer) v);
        }
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getBlockLimit() {
        return blockLimit;
    }

    public void setBlockLimit(String blockLimit) {
        this.blockLimit = blockLimit;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public SignatureResponse getSignature() {
        return signature;
    }

    public void setSignature(SignatureResponse signature) {
        this.signature = signature;
    }

    private List<RlpType> encodeTransactionResponse(int cryptoType) throws RuntimeException {
        if (blockLimit == null
                || chainId == null
                || groupId == null
                || extraData == null
                || signature == null) {
            throw new RuntimeException(
                    "calculate hash for the transaction failed for missing fields! Please make sure FISCO BCOS version >= v2.7.0");
        }
        List<RlpType> result = new ArrayList<>();
        // nonce
        result.add(RlpString.create(Numeric.decodeQuantity(nonce)));
        // gasPrice
        result.add(RlpString.create(Numeric.decodeQuantity(gasPrice)));
        // gas
        result.add(RlpString.create(Numeric.decodeQuantity(gas)));
        // blockLimit
        result.add(RlpString.create(Numeric.decodeQuantity(blockLimit)));
        // to
        result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        // value
        result.add(RlpString.create(Numeric.decodeQuantity(value)));
        // input
        result.add(RlpString.create(Numeric.hexStringToByteArray(input)));
        // chainId
        result.add(RlpString.create(Numeric.decodeQuantity(chainId)));
        // groupId
        result.add(RlpString.create(Numeric.decodeQuantity(groupId)));
        // extraData
        if (extraData.equals("0x")) {
            result.add(RlpString.create(""));
        } else {
            result.add(RlpString.create(Numeric.hexStringToByteArray(extraData)));
        }
        int startIndex = 0;
        if (signature.getSignature().startsWith("0x")) {
            startIndex = 2;
        }
        // signature
        if (cryptoType == 1) {
            // Note: shouldn't trimLeadingZeroes here for the Pub must be with the length of 64
            // Bytes
            result.add(RlpString.create(Numeric.hexStringToByteArray(signature.getV())));
            result.add(RlpString.create(Numeric.hexStringToByteArray(signature.getR())));
            result.add(RlpString.create(Numeric.hexStringToByteArray(signature.getS())));
        } else {
            // the v must add vBase
            int vWithVBase = Numeric.decodeQuantity(signature.getV()).intValue() + 27;
            result.add(RlpString.create((byte) vWithVBase));
            result.add(RlpString.create(Numeric.hexStringToByteArray(signature.getR())));
            result.add(RlpString.create(Numeric.hexStringToByteArray(signature.getS())));
        }
        return result;
    }

    // calculate the hash for the transaction
    public String calculateHash(int cryptoType) throws RuntimeException {
        try {
            List<RlpType> encodedTransaction = encodeTransactionResponse(cryptoType);
            RlpList rlpList = new RlpList(encodedTransaction);
            HashInterface hashInterface;
            if (cryptoType == EncryptType.ECDSA_TYPE) {
                hashInterface = new SHA3Digest();
            } else {
                hashInterface = new SM3Digest();
            }
            return "0x" + Hex.toHexString(hashInterface.hash(RlpEncoder.encode(rlpList)));
        } catch (Exception e) {
            logger.warn(
                    "calculate hash for the transaction failed, blockHash: {}, blockNumber: {}, transactionHash: {}, error info: {}",
                    blockHash,
                    blockNumber,
                    hash,
                    e);
            throw new RuntimeException(
                    "calculate hash for transaction " + hash + " failed for " + e.getMessage(), e);
        }
    }

    public String calculateHash() throws RuntimeException {
        return calculateHash(EncryptType.getEncryptType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }

        Transaction that = (Transaction) o;

        if (getV() != that.getV()) {
            return false;
        }
        if (getHash() != null ? !getHash().equals(that.getHash()) : that.getHash() != null) {
            return false;
        }
        if (getNonceRaw() != null
                ? !getNonceRaw().equals(that.getNonceRaw())
                : that.getNonceRaw() != null) {
            return false;
        }
        if (getBlockHash() != null
                ? !getBlockHash().equals(that.getBlockHash())
                : that.getBlockHash() != null) {
            return false;
        }
        if (getBlockNumberRaw() != null
                ? !getBlockNumberRaw().equals(that.getBlockNumberRaw())
                : that.getBlockNumberRaw() != null) {
            return false;
        }
        if (getTransactionIndexRaw() != null
                ? !getTransactionIndexRaw().equals(that.getTransactionIndexRaw())
                : that.getTransactionIndexRaw() != null) {
            return false;
        }
        if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) {
            return false;
        }
        if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) {
            return false;
        }
        if (getValueRaw() != null
                ? !getValueRaw().equals(that.getValueRaw())
                : that.getValueRaw() != null) {
            return false;
        }
        if (getGasPriceRaw() != null
                ? !getGasPriceRaw().equals(that.getGasPriceRaw())
                : that.getGasPriceRaw() != null) {
            return false;
        }
        if (getGasRaw() != null
                ? !getGasRaw().equals(that.getGasRaw())
                : that.getGasRaw() != null) {
            return false;
        }
        if (getInput() != null ? !getInput().equals(that.getInput()) : that.getInput() != null) {
            return false;
        }
        if (getCreates() != null
                ? !getCreates().equals(that.getCreates())
                : that.getCreates() != null) {
            return false;
        }
        if (getPublicKey() != null
                ? !getPublicKey().equals(that.getPublicKey())
                : that.getPublicKey() != null) {
            return false;
        }
        if (getRaw() != null ? !getRaw().equals(that.getRaw()) : that.getRaw() != null) {
            return false;
        }
        if (getR() != null ? !getR().equals(that.getR()) : that.getR() != null) {
            return false;
        }
        return getS() != null ? getS().equals(that.getS()) : that.getS() == null;
    }

    @Override
    public int hashCode() {
        int result = getHash() != null ? getHash().hashCode() : 0;
        result = 31 * result + (getNonceRaw() != null ? getNonceRaw().hashCode() : 0);
        result = 31 * result + (getBlockHash() != null ? getBlockHash().hashCode() : 0);
        result = 31 * result + (getBlockNumberRaw() != null ? getBlockNumberRaw().hashCode() : 0);
        result =
                31 * result
                        + (getTransactionIndexRaw() != null
                                ? getTransactionIndexRaw().hashCode()
                                : 0);
        result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
        result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
        result = 31 * result + (getValueRaw() != null ? getValueRaw().hashCode() : 0);
        result = 31 * result + (getGasPriceRaw() != null ? getGasPriceRaw().hashCode() : 0);
        result = 31 * result + (getGasRaw() != null ? getGasRaw().hashCode() : 0);
        result = 31 * result + (getInput() != null ? getInput().hashCode() : 0);
        result = 31 * result + (getCreates() != null ? getCreates().hashCode() : 0);
        result = 31 * result + (getPublicKey() != null ? getPublicKey().hashCode() : 0);
        result = 31 * result + (getRaw() != null ? getRaw().hashCode() : 0);
        result = 31 * result + (getR() != null ? getR().hashCode() : 0);
        result = 31 * result + (getS() != null ? getS().hashCode() : 0);
        result = 31 * result + getV();
        return result;
    }
}
