package org.fisco.bcos.channel.protocol;

public class NodeVersion {
	
	private int HighestSupported;
	private String NodeVersion;

	public String getNodeVersion() {
		return NodeVersion;
	}

	public void setNodeVersion(String nodeVersion) {
		NodeVersion = nodeVersion;
	}

	public int getHighestSupported() {
		return HighestSupported;
	}

	public void setHighestSupported(int highestSupported) {
		HighestSupported = highestSupported;
	}
	
	@Override
	public String toString() {
		return "NodeVersion [HighestSupported=" + HighestSupported + ", NodeVersion=" + NodeVersion + "]";
	}
}


