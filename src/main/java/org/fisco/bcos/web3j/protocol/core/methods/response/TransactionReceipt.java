package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.utils.Numeric;

/** TransactionReceipt object used by {@link BcosTransactionReceipt}. */
public class TransactionReceipt {
    private String transactionHash;
    private String transactionIndex;
    private String blockHash;
    private String blockNumber;
    private String gasUsed;
    private String contractAddress;
    private String root;
    // status is only present on Byzantium transactions onwards
    // see EIP 658 https://github.com/ethereum/EIPs/pull/658
    private String status;
    private String message;
    private String from;
    private String to;
    private String input;
    private String output;
    private List<Log> logs;
    private String logsBloom;

    public TransactionReceipt() {}

    public TransactionReceipt(
            String transactionHash,
            String transactionIndex,
            String blockHash,
            String blockNumber,
            String gasUsed,
            String contractAddress,
            String root,
            String status,
            String message,
            String from,
            String to,
            String input,
            String output,
            List<Log> logs,
            String logsBloom) {
        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.gasUsed = gasUsed;
        this.contractAddress = contractAddress;
        this.root = root;
        this.status = status;
        this.message = message;
        this.from = from;
        this.to = to;
        this.input = input;
        this.output = output;
        this.logs = logs;
        this.logsBloom = logsBloom;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
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

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getGasUsed() {
        return Numeric.decodeQuantity(gasUsed);
    }

    public String getGasUsedRaw() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatusOK() {
        if (null == status) {
            return true;
        }

        try {
            BigInteger statusQuantity = Numeric.decodeQuantity(status);
            return BigInteger.ZERO.equals(statusQuantity);
        } catch (Exception e) {
            return false;
        }
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

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionReceipt)) {
            return false;
        }

        TransactionReceipt that = (TransactionReceipt) o;

        if (getTransactionHash() != null
                ? !getTransactionHash().equals(that.getTransactionHash())
                : that.getTransactionHash() != null) {
            return false;
        }
        if (transactionIndex != null
                ? !transactionIndex.equals(that.transactionIndex)
                : that.transactionIndex != null) {
            return false;
        }
        if (getBlockHash() != null
                ? !getBlockHash().equals(that.getBlockHash())
                : that.getBlockHash() != null) {
            return false;
        }
        if (blockNumber != null
                ? !blockNumber.equals(that.blockNumber)
                : that.blockNumber != null) {
            return false;
        }
        if (gasUsed != null ? !gasUsed.equals(that.gasUsed) : that.gasUsed != null) {
            return false;
        }
        if (getContractAddress() != null
                ? !getContractAddress().equals(that.getContractAddress())
                : that.getContractAddress() != null) {
            return false;
        }
        if (getRoot() != null ? !getRoot().equals(that.getRoot()) : that.getRoot() != null) {
            return false;
        }
        if (getStatus() != null
                ? !getStatus().equals(that.getStatus())
                : that.getStatus() != null) {
            return false;
        }
        if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) {
            return false;
        }
        if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) {
            return false;
        }
        if (getLogs() != null ? !getLogs().equals(that.getLogs()) : that.getLogs() != null) {
            return false;
        }
        if (getOutput() != null
                ? !getOutput().equals(that.getOutput())
                : that.getOutput() != null) {
            return false;
        }
        if (getInput() != null ? !getInput().equals(that.getInput()) : that.getInput() != null) {
            return false;
        }
        return getLogsBloom() != null
                ? getLogsBloom().equals(that.getLogsBloom())
                : that.getLogsBloom() == null;
    }

    @Override
    public int hashCode() {
        int result = getTransactionHash() != null ? getTransactionHash().hashCode() : 0;
        result = 31 * result + (transactionIndex != null ? transactionIndex.hashCode() : 0);
        result = 31 * result + (getBlockHash() != null ? getBlockHash().hashCode() : 0);
        result = 31 * result + (blockNumber != null ? blockNumber.hashCode() : 0);
        result = 31 * result + (gasUsed != null ? gasUsed.hashCode() : 0);
        result = 31 * result + (getContractAddress() != null ? getContractAddress().hashCode() : 0);
        result = 31 * result + (getRoot() != null ? getRoot().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
        result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
        result = 31 * result + (getOutput() != null ? getOutput().hashCode() : 0);
        result = 31 * result + (getInput() != null ? getInput().hashCode() : 0);
        result = 31 * result + (getLogs() != null ? getLogs().hashCode() : 0);
        result = 31 * result + (getLogsBloom() != null ? getLogsBloom().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionReceipt{"
                + "transactionHash='"
                + transactionHash
                + '\''
                + ", transactionIndex='"
                + transactionIndex
                + '\''
                + ", blockHash='"
                + blockHash
                + '\''
                + ", blockNumber='"
                + blockNumber
                + '\''
                + ", gasUsed='"
                + gasUsed
                + '\''
                + ", contractAddress='"
                + contractAddress
                + '\''
                + ", root='"
                + root
                + '\''
                + ", status='"
                + status
                + '\''
                + ", from='"
                + from
                + '\''
                + ", to='"
                + to
                + '\''
                + ", input='"
                + input
                + '\''
                + ", output='"
                + output
                + '\''
                + ", logs="
                + logs
                + ", logsBloom='"
                + logsBloom
                + '\''
                + '}';
    }
}
