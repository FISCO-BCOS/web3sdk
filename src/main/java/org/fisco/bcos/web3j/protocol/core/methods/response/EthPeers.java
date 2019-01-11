package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.fisco.bcos.web3j.protocol.core.Response;

import java.util.List;

public class EthPeers extends Response<List<EthPeers.Peer>> {

    public  List<EthPeers.Peer> getValue() {
        return getResult();
    }

    public void setResult(List<EthPeers.Peer> result) {
        super.setResult(result);
    }


    public class Peer {
        @JsonProperty("IPAndPort")
        private String iPAndPort;
        @JsonProperty("NodeID")
        private String nodeID;
        @JsonProperty("Topic")
        private List<String> topic;

        public Peer() {
        }

        public Peer(String iPAndPort, String nodeID, List<String> topic) {
            this.iPAndPort = iPAndPort;
            this.nodeID = nodeID;
            this.topic = topic;

        }

        public String getIPAndPort() {
            return iPAndPort;
        }

        public void setIPAndPort(String iPAndPort) {
            this.iPAndPort = iPAndPort;
        }

        public String getNodeID() {
            return nodeID;
        }

        public void setNodeID(String nodeID) {
            this.nodeID = nodeID;
        }

        public List<String> getTopic() {
            return topic;
        }

        public void setTopic(List<String> topic) {
            this.topic = topic;
        }
    }
}