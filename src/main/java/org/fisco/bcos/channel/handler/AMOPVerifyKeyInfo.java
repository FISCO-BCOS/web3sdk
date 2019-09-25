package org.fisco.bcos.channel.handler;

import java.util.List;
import org.springframework.core.io.Resource;

public class AMOPVerifyKeyInfo {
    private List<Resource> publicKey;
    private Resource privateKey;

    public List<Resource> getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(List<Resource> publicKey) {
        this.publicKey = publicKey;
    }

    public Resource getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(Resource privateKey) {
        this.privateKey = privateKey;
    }
}
