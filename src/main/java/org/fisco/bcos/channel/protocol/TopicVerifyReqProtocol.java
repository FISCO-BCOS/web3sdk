package org.fisco.bcos.channel.protocol;

public class TopicVerifyReqProtocol {

    // sdk->node->node->sdk
    private String randValue;
    private String topic;

    public String getRandValue() {
        return randValue;
    }

    public void setRandValue(String randValue) {
        this.randValue = randValue;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
