package org.bcos.web3j.protocol.core.methods.request;

import java.math.BigInteger;

import org.bcos.web3j.tx.TransactionConstant;
import org.bcos.web3j.utils.Numeric;

/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">
 * yellow paper</a>.
 */
public class RawTransaction {

    private BigInteger randomid;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private BigInteger blockLimit;
    private String to;
    private BigInteger value;
    private String data;
    private String contractName;
    private BigInteger version = TransactionConstant.version;
    private BigInteger type;//0 new合约,1 call

    private RawTransaction(BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, BigInteger blockLimit, String to,
                           BigInteger value, String data, BigInteger type, boolean isInitByName) {
        this.randomid = randomid;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.blockLimit = blockLimit;
        this.type = type;
        if (isInitByName)
        {
            this.contractName = to;
        }
        else
        {
            this.to = to;
        }
        this.value = value;

        if (data != null) {
            this.data = Numeric.cleanHexPrefix(data);
        }
    }

    public static RawTransaction createContractTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, BigInteger blockLimit, BigInteger value,
            String init, BigInteger type, boolean isInitByName) {

        return new RawTransaction(randomid, gasPrice, gasLimit, blockLimit,"", value, init, type, isInitByName);
    }

    public static RawTransaction createEtherTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, BigInteger blockLimit, String to,
            BigInteger value, BigInteger type, boolean isInitByName) {

        return new RawTransaction(randomid, gasPrice, gasLimit, blockLimit, to, value, "", type, isInitByName);

    }

    public static RawTransaction createTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, BigInteger blockLimit, String to,
            String data, BigInteger type, boolean isInitByName) {
        return createTransaction(randomid, gasPrice, gasLimit, blockLimit, to, BigInteger.ZERO, data, type, isInitByName);
    }

    public static RawTransaction createTransaction(
            BigInteger randomid, BigInteger gasPrice, BigInteger gasLimit, BigInteger blockLimit, String to,
            BigInteger value, String data, BigInteger type, boolean isInitByName) {

        return new RawTransaction(randomid, gasPrice, gasLimit, blockLimit, to, value, data, type, isInitByName);
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

    public BigInteger getType() { return type; }

    public BigInteger getVersion() { return version; }

    public String getContractName() { return contractName; }
}
