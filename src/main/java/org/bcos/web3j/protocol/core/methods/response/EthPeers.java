package org.bcos.web3j.protocol.core.methods.response;

import org.bcos.web3j.protocol.core.Response;

import java.util.List;

public class EthPeers extends Response<List<EthPeers.Peers>> {
    public List<Peers> getAdminPeers() {
        return getResult();
    }

    public static class Peers {
        private List<String> caps;
        private String height;
        private String id;
        private String name;
        private Network network;

        public List<String> getCaps() {
            return caps;
        }

        public void setCaps(List<String> caps) {
            this.caps = caps;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Network getNetwork() {
            return network;
        }

        public void setNetwork(Network network) {
            this.network = network;
        }

        class Network {
            private String remoteAddress;

            public String getRemoteAddress() {
                return remoteAddress;
            }

            public void setRemoteAddress(String remoteAddress) {
                this.remoteAddress = remoteAddress;
            }
        }

    }
}
