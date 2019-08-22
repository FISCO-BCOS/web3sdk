package org.fisco.bcos.channel.event.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class EventLogFilterParams {

    private String groupID;
    private String filterID;
    private String fromBlock;
    private String toBlock;
    private List<String> address;
    private List<Object> topics;
    // timeout
    private int timeout;

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

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
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
                + address
                + ", topics="
                + topics
                + ", timeout="
                + timeout
                + "]";
    }
}
