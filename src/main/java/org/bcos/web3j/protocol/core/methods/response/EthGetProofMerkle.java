package org.bcos.web3j.protocol.core.methods.response;

import org.bcos.web3j.protocol.ObjectMapperFactory;
import org.bcos.web3j.protocol.core.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.List;

/**
 * Created by suyuhui on 17/9/28.
 */
public class EthGetProofMerkle extends Response<EthGetProofMerkle.Proof> {

    @Override
    @JsonDeserialize(using = EthGetProofMerkle.ResponseDeserialiser.class)
    public void setResult(Proof proof) {
        super.setResult(proof);
    }

    public static class Proof {
        private String key;
        private String root;
        private String hash;
        private List<String> proofs;
        private List<String> pubs;
        private List<ProofSign> signs;
        private String value;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public List<ProofSign> getSigns() {
            return signs;
        }

        public void setSigns(List<ProofSign> signs) {
            this.signs = signs;
        }

        public List<String> getPubs() {
            return pubs;
        }

        public void setPubs(List<String> pubs) {
            this.pubs = pubs;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }

        public List<String> getProofs() {
            return proofs;
        }

        public void setProofs(List<String> proofs) {
            this.proofs = proofs;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ProofSign {
        private int idx;
        private String sign;

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<Proof> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Proof deserialize(
                JsonParser jsonParser,
                DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Proof.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
