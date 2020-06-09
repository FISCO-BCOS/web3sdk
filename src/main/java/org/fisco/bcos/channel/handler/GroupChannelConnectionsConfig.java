package org.fisco.bcos.channel.handler;

import java.util.List;
import org.springframework.core.io.Resource;

public class GroupChannelConnectionsConfig {
    private List<ChannelConnections> allChannelConnections;

    private Resource caCert;
    private Resource sslCert;
    private Resource sslKey;

    private Resource gmCaCert;
    private Resource gmSslCert;
    private Resource gmSslKey;
    private Resource gmEnSslCert;
    private Resource gmEnSslKey;

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

    public Resource getGmCaCert() {
        return gmCaCert;
    }

    public void setGmCaCert(Resource gmCaCert) {
        this.gmCaCert = gmCaCert;
    }

    public Resource getGmSslCert() {
        return gmSslCert;
    }

    public void setGmSslCert(Resource gmSslCert) {
        this.gmSslCert = gmSslCert;
    }

    public Resource getGmSslKey() {
        return gmSslKey;
    }

    public void setGmSslKey(Resource gmSslKey) {
        this.gmSslKey = gmSslKey;
    }

    public Resource getGmEnSslCert() {
        return gmEnSslCert;
    }

    public void setGmEnSslCert(Resource gmEnSslCert) {
        this.gmEnSslCert = gmEnSslCert;
    }

    public Resource getGmEnSslKey() {
        return gmEnSslKey;
    }

    public void setGmEnSslKey(Resource gmEnSslKey) {
        this.gmEnSslKey = gmEnSslKey;
    }
}
