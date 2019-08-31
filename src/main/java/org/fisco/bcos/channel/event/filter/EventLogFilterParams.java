package org.fisco.bcos.channel.event.filter;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.WalletUtils;
import org.fisco.bcos.web3j.utils.Strings;

public class EventLogFilterParams {

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
     * @param blockNumber: block number of blockchain
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean checkParams(BigInteger blockNumber) {

        do {
            try {
                if (Strings.isEmpty(getFromBlock())
                        || Strings.isEmpty(getToBlock())
                        || getAddresses() == null
                        || getTopics() == null) {
                    break;
                }

                // fromBlock, toBlock,
                if (getFromBlock().equals("latest") && !getToBlock().equals("latest")) {
                    // fromBlock="latest" but toBlock is not
                    break;
                } else if (!getFromBlock().equals("latest") && getToBlock().equals("latest")) {
                    // toBlock="latest" but fromBlock is not
                    BigInteger fromBlock = new BigInteger(getFromBlock());
                    // fromBlock is bigger than block number of the blockchain
                    if (fromBlock.compareTo(blockNumber) > 0) {
                        break;
                    }

                    // fromBlock is bigger than block number
                    if (fromBlock.compareTo(BigInteger.ZERO) <= 0) {
                        break;
                    }
                } else if (!getFromBlock().equals("latest") && !getToBlock().equals("latest")) {
                    // fromBlock and toBlock none is "lateπst"
                    BigInteger fromBlock = new BigInteger(getFromBlock());
                    BigInteger toBlock = new BigInteger(getToBlock());
                    // fromBlock is bigger than toBlock
                    if (fromBlock.compareTo(toBlock) > 0) {
                        break;
                    }

                    // fromBlock is bigger than block number
                    if (fromBlock.compareTo(blockNumber) > 0) {
                        break;
                    }

                    // fromBlock is bigger than block number
                    if (fromBlock.compareTo(BigInteger.ZERO) <= 0) {
                        break;
                    }
                }

                // addresses field
                for (String address : getAddresses()) {
                    // check if address valid
                    if (!WalletUtils.isValidAddress(address)) {
                        break;
                    }
                }

                // topics
                if (getTopics().size() > TopicTools.MAX_NUM_TOPIC_EVENT_LOG) {
                    break;
                }

                for (Object topic : getTopics()) {
                    if (topic instanceof String) {
                        // if valid topic
                        if (((String) topic).isEmpty()) {
                            break;
                        }
                    } else if (topic instanceof List) {
                        for (Object o : (List<String>) topic) {
                            // if valid topic
                            if (((String) o).isEmpty()) {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }

                return true;

            } catch (Exception e) {
                break;
            }
        } while (false);

        return false;
    }
}
