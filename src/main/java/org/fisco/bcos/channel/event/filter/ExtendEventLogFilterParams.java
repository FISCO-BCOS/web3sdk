package org.fisco.bcos.channel.event.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

public class ExtendEventLogFilterParams extends EventLogFilterParams {

    private String groupID;
    private String filterID;
    private int timeout = 0;

    public ExtendEventLogFilterParams(
            EventLogFilterParams params, String groupID, String filterID) {
        this.setFromBlock(params.getFromBlock());
        this.setToBlock(params.getToBlock());
        this.setAddresses(params.getAddresses());
        this.setTopics(params.getTopics());
        this.setGroupID(groupID);
        this.setFilterID(filterID);
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
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

    public String toJsonString() throws JsonProcessingException {
        String content = ObjectMapperFactory.getObjectMapper().writeValueAsString(this);
        return content;
    }

    @Override
    public String toString() {
        return "ExtendEventLogFilterParams [groupID="
                + groupID
                + ", filterID="
                + filterID
                + ", timeout="
                + getTimeout()
                + ", fromBlock="
                + getFromBlock()
                + ", toBlock="
                + getToBlock()
                + ", addresses="
                + getAddresses()
                + ", topics="
                + getTopics()
                + "]";
    }
}
