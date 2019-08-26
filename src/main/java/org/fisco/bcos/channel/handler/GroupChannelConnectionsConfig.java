package org.fisco.bcos.channel.handler;

import java.util.List;
import org.springframework.core.io.Resource;

public class GroupChannelConnectionsConfig {
    private List<ChannelConnections> allChannelConnections;

    private Resource caCert;
    private Resource sslCert;
    private Resource sslKey;

    public List<ChannelConnections> getAllChannelConnections() {
        return allChannelConnections;
    }

    public void setAllChannelConnections(List<ChannelConnections> allChannelConnections) {
        this.allChannelConnections = allChannelConnections;
    }

    public Resource getCaCert() {
        return caCert;
    }

    public void setCaCert(Resource caCert) {
        this.caCert = caCert;
    }

    public Resource getSslCert() {
        return sslCert;
    }

    public void setSslCert(Resource sslCert) {
        this.sslCert = sslCert;
    }

    public Resource getSslKey() {
        return sslKey;
    }

    public void setSslKey(Resource sslKey) {
        this.sslKey = sslKey;
    }
}
