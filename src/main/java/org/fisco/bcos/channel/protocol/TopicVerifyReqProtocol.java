package org.fisco.bcos.channel.protocol;

public class TopicVerifyReqProtocol {
    private String randValue;
    public String getRandValue() {
        return randValue;
    }
    public void setRandValue(String randValue) {
        this.randValue = randValue;
    }
    private String topic;
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
