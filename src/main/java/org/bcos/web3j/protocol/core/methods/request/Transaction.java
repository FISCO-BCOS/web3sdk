package org.bcos.web3j.protocol.core.methods.request;

import java.math.BigInteger;

import org.bcos.web3j.tx.TransactionConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.bcos.web3j.utils.Numeric;

/**
 * Transaction request object used the below methods.
 * <ol>
 *     <li>eth_call</li>
 *     <li>eth_sendTransaction</li>
 *     <li>eth_estimateGas</li>
 * </ol>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    // default as per https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_sendtransaction
    public static final BigInteger DEFAULT_GAS = BigInteger.valueOf(9000);

    private String from;
    private String to;
    private BigInteger gas;
    private BigInteger gasPrice;
    private BigInteger value;
    private String data;
    private BigInteger nonce;  // nonce field is not present on eth_call/eth_estimateGas
    private BigInteger type;//0 new合约,1 call
    private BigInteger version = TransactionConstant.version;
    private String contractName;

    public Transaction(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                       String to, BigInteger value, String data, BigInteger type, boolean isInitByName) {
        this.from = from;
        if (isInitByName) {
            this.contractName = to;
        }
        else
        {
            this.to = to;
        }
        this.gas = gasLimit;
        this.gasPrice = gasPrice;
        this.value = value;

        if (data != null) {
            this.data = Numeric.prependHexPrefix(data);
        }

        this.nonce = nonce;
        this.type = type;
    }

    public static Transaction createContractTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
            BigInteger value, String init, BigInteger type, boolean isInitByName) {

        return new Transaction(from, nonce, gasPrice, gasLimit, null, value, init,type,isInitByName);
    }

    public static Transaction createContractTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, String init, BigInteger type, boolean isInitByName) {

        return createContractTransaction(from, nonce, gasPrice, null, null, init, type, isInitByName);
    }

    public static Transaction createEtherTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value,BigInteger type, boolean isInitByName) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, null, type, isInitByName);
    }

    public static Transaction createFunctionCallTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data,BigInteger type, boolean isInitByName) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, value, data, type, isInitByName);
    }

    public static Transaction createFunctionCallTransaction(
            String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger type, boolean isInitByName) {

        return new Transaction(from, nonce, gasPrice, gasLimit, to, null, data, type, isInitByName);
    }

    public static Transaction createEthCallTransaction(String from, String to, String data,BigInteger type, boolean isInitByName) {

        return new Transaction(from, null, null, null, to, null, data, type, isInitByName);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getGas() {
        return convert(gas);
    }

    public String getGasPrice() {
        return convert(gasPrice);
    }

    public String getValue() {
        return convert(value);
    }

    public String getData() {
        return data;
    }

    public String getNonce() {
        return convert(nonce);
    }

    public String getContractName() { return contractName; }

    public  String getType() {return  convert(type); }

    public  String getVersion() {return convert(version); }

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null;  // we don't want the field to be encoded if not present
        }
    }
}
