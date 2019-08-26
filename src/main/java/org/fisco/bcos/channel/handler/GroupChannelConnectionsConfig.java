package org.fisco.bcos.channel.handler;

import java.util.List;

import org.springframework.core.io.Resource;

public class GroupChannelConnectionsConfig {
    private List<ChannelConnections> allChannelConnections;
    //private String caCert = "classpath:ca.crt";
    //private String sslCert = "classpath:node.crt";
    //private String sslKey = "classpath:node.key";
    
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

    /*
    public void setSslKey(String sslKey) {
        this.sslKey = sslKey;
    }

    public void setSslCert(String sslCert) {
        this.sslCert = sslCert;
    }

    public void setCaCert(String caCert) {
        this.caCert = caCert;
    }

    public String getCaCert() {
        return caCert;
    }

    public String getSslCert() {
        return sslCert;
    }

    public String getSslKey() {
        return sslKey;
    }
    */
}
