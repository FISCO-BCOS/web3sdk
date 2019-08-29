package org.fisco.bcos.channel.event.filter;

public class ExtendEventLogFilterParams extends EventLogFilterParams {

    private String groupID;
    private String filterID;
    private int timeout = 0;

    public ExtendEventLogFilterParams(EventLogFilterParams params) {
        this.setFromBlock(params.getFromBlock());
        this.setToBlock(params.getToBlock());
        this.setAddresses(params.getAddresses());
        this.setTopics(params.getTopics());
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
