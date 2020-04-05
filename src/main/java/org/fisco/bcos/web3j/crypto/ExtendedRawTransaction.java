package org.fisco.bcos.web3j.crypto;

import java.io.Serializable;
import java.math.BigInteger;
import org.fisco.bcos.web3j.utils.Numeric;

/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">yellow
 * paper</a>.
 */
public class ExtendedRawTransaction implements Serializable {

    private static final long serialVersionUID = -5580814755985097996L;
    private BigInteger randomid;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private BigInteger blockLimit;
    private String to;
    private BigInteger value;
    private String data;
    private BigInteger fiscoChainId;
    private BigInteger groupId;
    private String extraData;
    @Deprecated private BigInteger version;

    protected ExtendedRawTransaction(
            BigInteger randomid,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger blockLimit,
            String to,
            BigInteger value,
            String data,
            BigInteger fiscoChainId,
            BigInteger groupId,
            String extraData) {
        this.randomid = randomid;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.blockLimit = blockLimit;
        this.fiscoChainId = fiscoChainId;
        this.groupId = groupId;
        this.extraData = extraData;
        this.to = to;

        this.value = value;

        if (data != null) {
            this.data = Numeric.cleanHexPrefix(data);
        }
    }

    public static ExtendedRawTransaction createContractTransaction(
            BigInteger randomid,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger blockLimit,
            BigInteger value,
            String init,
            BigInteger chainId,
            BigInteger groupId,
            String extraData) {

        return new ExtendedRawTransaction(
                randomid,
                gasPrice,
                gasLimit,
                blockLimit,
                "",
                value,
                init,
                chainId,
                groupId,
                extraData);
    }
    //
    //
    //    public static ExtendedRawTransaction createTransaction(
    //            BigInteger randomid,
    //            BigInteger gasPrice,
    //            BigInteger gasLimit,
    //            BigInteger blockLimit,
    //            String to,
    //            String data) {
    //        return createTransaction(randomid, gasPrice, gasLimit, blockLimit, to,
    // BigInteger.ZERO, data);
    //    }

    public static ExtendedRawTransaction createTransaction(
            BigInteger randomid,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger blockLimit,
            String to,
            BigInteger value,
            String data,
            BigInteger chainId,
            BigInteger groupId,
            String extraData) {

        return new ExtendedRawTransaction(
                randomid,
                gasPrice,
                gasLimit,
                blockLimit,
                to,
                value,
                data,
                chainId,
                groupId,
                extraData);
    }

    public BigInteger getRandomid() {
        return randomid;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public BigInteger getBlockLimit() {
        return blockLimit;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    @Deprecated
    public BigInteger getVersion() {
        return version;
    }

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public BigInteger getFiscoChainId() {
        return fiscoChainId;
    }

    public void setFiscoChainId(BigInteger fiscoChainId) {
        this.fiscoChainId = fiscoChainId;
    }
}
