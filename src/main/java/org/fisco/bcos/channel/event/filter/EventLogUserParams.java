package org.fisco.bcos.channel.event.filter;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.WalletUtils;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used by the user to set the required event log filter conditions, please refer to
 * the user manual for details
 */
public class EventLogUserParams {

    private static Logger logger = LoggerFactory.getLogger(EventLogUserParams.class);

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
            if (topic == null) {
                continue;
            }
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

    private boolean validToBlock(BigInteger blockNumber) {

        // fromBlock="latest" but toBlock is not
        BigInteger toBlock = new BigInteger(getToBlock());
        return (blockNumber.compareTo(BigInteger.ONE) <= 0)
                || (blockNumber.compareTo(BigInteger.ONE) > 0
                        && (toBlock.compareTo(blockNumber)) > 0);
    }

    private boolean validFromBlock(BigInteger blockNumber) {

        // toBlock="latest" but fromBlock is not
        BigInteger fromBlock = new BigInteger(getFromBlock());
        // fromBlock is bigger than block number of the blockchain
        if (fromBlock.compareTo(BigInteger.ZERO) <= 0) {
            return false;
        }

        if (blockNumber.compareTo(BigInteger.ONE) > 0 && (fromBlock.compareTo(blockNumber) > 0)) {
            logger.info(
                    " future block range request, from: {}, to: {}", getFromBlock(), getToBlock());
        }

        return true;
    }

    private boolean validFromToBlock(BigInteger blockNumber) throws NumberFormatException {

        // fromBlock and toBlock none is "latest"
        BigInteger fromBlock = new BigInteger(getFromBlock());
        BigInteger toBlock = new BigInteger(getToBlock());

        if ((fromBlock.compareTo(BigInteger.ZERO) <= 0) || (fromBlock.compareTo(toBlock) > 0)) {
            return false;
        } else {
            if (blockNumber.compareTo(BigInteger.ONE) > 0
                    && (fromBlock.compareTo(blockNumber) > 0)) {
                logger.info(
                        " future block range request, from: {}, to: {}",
                        getFromBlock(),
                        getToBlock());
            }
            return true;
        }
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
            if (getFromBlock().equals(DefaultBlockParameterName.LATEST.getValue())
                    && !getToBlock().equals(DefaultBlockParameterName.LATEST.getValue())) {
                // fromBlock="latest" but toBlock is not
                isValidBlockRange = validToBlock(blockNumber);
            } else if (!getFromBlock().equals(DefaultBlockParameterName.LATEST.getValue())
                    && getToBlock().equals(DefaultBlockParameterName.LATEST.getValue())) {
                // toBlock="latest" but fromBlock is not
                isValidBlockRange = validFromBlock(blockNumber);
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
