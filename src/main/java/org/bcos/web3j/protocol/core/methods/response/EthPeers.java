package org.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bcos.web3j.protocol.ObjectMapperFactory;
import org.bcos.web3j.protocol.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EthPeers extends Response<List<EthPeers.Peers>> {

    public List<Peers> getAdminPeers() {
        return getResult();
    }

    public void setResult(List<EthPeers.Peers> result) {
        super.setResult(result);
    }

    public static class Peers {
        private List<String> caps;
        private String height;
        private String id;
        private String name;
        private Map<String, String> network;

        public Peers() {
        }


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

        public Map<String, String> getNetwork() {
            return network;
        }

        public void setNetwork(Map<String, String> network) {
            this.network = network;
        }

        class Network {
            private String remoteAddress;

            public String getRemoteAddress() {
                return remoteAddress;
            }

//  public static class PeerResultDeserialiser extends JsonDeserializer<List<EthPeers.Peers>> {
//
//            private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();
//
//            @Override
//            public List<EthPeers.Peers> deserialize(
//                    JsonParser jsonParser,
//                    DeserializationContext deserializationContext) throws IOException {
//
//                List<EthPeers.Peers> logResults = new ArrayList<>();
//                JsonToken nextToken = jsonParser.nextToken();
//
//                if (nextToken == JsonToken.START_OBJECT) {
//                    Iterator<EthPeers.Peers> logObjectIterator =
//                            objectReader.readValues(jsonParser, EthPeers.Peers.class);
//                    while (logObjectIterator.hasNext()) {
//                        logResults.add(logObjectIterator.next());
//                    }
//                }
//                return logResults;
//            }
//        }

        }
    }
}

