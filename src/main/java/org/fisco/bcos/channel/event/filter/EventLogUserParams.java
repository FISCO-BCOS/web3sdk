package org.fisco.bcos.channel.event.filter;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.WalletUtils;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.utils.Strings;

/**
 * This class is used by the user to set the required event log filter conditions, please refer to
 * the user manual for details
 */
public class EventLogUserParams {

    private String fromBlock;
    private String toBlock;
    private List<String> addresses;
    private List<Object> topics;

    public String getFromBlock() {
        return fromBlock;
    }

    public void setFromBlock(String fromBlock) {
        this.fromBlock = fromBlock;
    }

    public String getToBlock() {
        return toBlock;
    }

    public void setToBlock(String toBlock) {
        this.toBlock = toBlock;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<Object> getTopics() {
        return topics;
    }

    @Override
    public String toString() {
        return "EventLogFilterParams [fromBlock="
                + fromBlock
                + ", toBlock="
                + toBlock
                + ", addresses="
                + addresses
                + ", topics="
                + topics
                + "]";
    }

    public void setTopics(List<Object> topics) {
        this.topics = topics;
    }

    /**
     * check if addresses valid
     *
     * @return boolean
     */
    private boolean validAddresses() {

        if (getAddresses() == null) {
            return false;
        }
        // addresses field
        for (String address : getAddresses()) {
            // check if address valid
            if (!WalletUtils.isValidAddress(address)) {
                return false;
            }
        }

        return true;
    }

    /**
     * check if topics valid
     *
     * @return
     */
    private boolean validTopics() {

        // topics
        if ((getTopics() == null) || (getTopics().size() > TopicTools.MAX_NUM_TOPIC_EVENT_LOG)) {
            return false;
        }

        for (Object topic : getTopics()) {
            if (topic instanceof String) {
                // if valid topic
                if (((String) topic).isEmpty()) {
                    return false;
                }
            } else if (topic instanceof List) {
                for (Object o : (List<String>) topic) {
                    // if valid topic
                    if (((String) o).isEmpty()) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }

        return true;
    }

    private boolean validFromToBlock(BigInteger blockNumber) throws NumberFormatException {

        // fromBlock and toBlock none is "latest"
        BigInteger fromBlock = new BigInteger(getFromBlock());
        BigInteger toBlock = new BigInteger(getToBlock());

        return !((fromBlock.compareTo(toBlock) > 0)
                || (blockNumber.compareTo(BigInteger.ONE) > 0
                        && fromBlock.compareTo(blockNumber) > 0)
                || (fromBlock.compareTo(BigInteger.ZERO) <= 0));
    }

    /**
     * check if valid fromBlock and toBlock
     *
     * @param blockNumber
     * @return
     */
    private boolean validBlockRange(BigInteger blockNumber) {

        if (Strings.isEmpty(getFromBlock()) || Strings.isEmpty(getToBlock())) {
            return false;
        }

        boolean isValidBlockRange = true;

        try {
            // fromBlock, toBlock,
            if (getFromBlock().equals(DefaultBlockParameterName.LATEST.getValue())
                    && !getToBlock().equals(DefaultBlockParameterName.LATEST.getValue())) {
                // fromBlock="latest" but toBlock is not
                isValidBlockRange = false;
            } else if (!getFromBlock().equals(DefaultBlockParameterName.LATEST.getValue())
                    && getToBlock().equals(DefaultBlockParameterName.LATEST.getValue())) {
                // toBlock="latest" but fromBlock is not
                BigInteger fromBlock = new BigInteger(getFromBlock());
                // fromBlock is bigger than block number of the blockchain
                if ((blockNumber.compareTo(BigInteger.ONE) > 0
                                && fromBlock.compareTo(blockNumber) > 0)
                        || (fromBlock.compareTo(BigInteger.ZERO) <= 0)) {
                    isValidBlockRange = false;
                }
            } else if (!getFromBlock().equals(DefaultBlockParameterName.LATEST.getValue())
                    && !getToBlock().equals(DefaultBlockParameterName.LATEST.getValue())) {
                isValidBlockRange = validFromToBlock(blockNumber);
            }
        } catch (Exception e) {
            // invalid blockNumber format string
            isValidBlockRange = false;
        }

        return isValidBlockRange;
    }

    /**
     * @param blockNumber: block number of blockchain
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean checkParams(BigInteger blockNumber) {
        return validBlockRange(blockNumber) && validAddresses() && validTopics();
    }
}
