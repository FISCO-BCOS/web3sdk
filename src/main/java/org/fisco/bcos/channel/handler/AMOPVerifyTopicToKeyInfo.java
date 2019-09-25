package org.fisco.bcos.channel.handler;

import java.util.concurrent.ConcurrentHashMap;

public class AMOPVerifyTopicToKeyInfo {
    private ConcurrentHashMap<String, AMOPVerifyKeyInfo> topicToKeyInfo =
            new ConcurrentHashMap<String, AMOPVerifyKeyInfo>();

    public ConcurrentHashMap<String, AMOPVerifyKeyInfo> getTopicToKeyInfo() {
        return topicToKeyInfo;
    }

    public void setTopicToKeyInfo(ConcurrentHashMap<String, AMOPVerifyKeyInfo> topicToKeyInfo) {
        this.topicToKeyInfo = topicToKeyInfo;
    }
}
