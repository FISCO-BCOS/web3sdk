package org.fisco.bcos.channel.event.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.fisco.bcos.web3j.utils.Strings;

public class EventLogFilterParams {

    private String groupID;
    private String fromBlock;
    private String toBlock;
    private List<String> addresses;
    private List<Object> topics;
    // timeout
    private int timeout;
    private String filterID;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

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

    public void setTopics(List<Object> topics) {
        this.topics = topics;
    }

    public String getFilterID() {
        return filterID;
    }

    public void setFilterID(String filterID) {
        this.filterID = filterID;
    }

    public boolean validParams() { // check if EventLogFilterParams valid
        if (Strings.isEmpty(getGroupID())
                || Strings.isEmpty(getFromBlock())
                || Strings.isEmpty(getToBlock())) {
            return false;
        }

        if ((getTopics() == null) || (getAddresses() == null)) {
            return false;
        }

        if (getTopics().size() > TopicTools.MAX_NUM_TOPIC_EVENT_LOG) {
            return false;
        }

        if (getToBlock().equals("latest")) {
            return true;
        } else if (getFromBlock().equals("latest")) {
            return true;
        }

        long fromBlock = Long.valueOf(getFromBlock());
        long toBlock = Long.valueOf(getToBlock());
        return fromBlock <= toBlock;
    }

    @JsonIgnore
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "EventLogFilterParams [groupID="
                + groupID
                + ", filterID="
                + filterID
                + ", fromBlock="
                + fromBlock
                + ", toBlock="
                + toBlock
                + ", address="
                + addresses
                + ", topics="
                + topics
                + ", timeout="
                + timeout
                + "]";
    }
}
