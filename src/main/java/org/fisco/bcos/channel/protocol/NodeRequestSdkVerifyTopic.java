package org.fisco.bcos.channel.protocol;

public class NodeRequestSdkVerifyTopic {
    private String topic;
    private String topicForCert;
    private String nodeId;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicForCert() {
        return topicForCert;
    }

    public void setTopicForCert(String topicForCert) {
        this.topicForCert = topicForCert;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
