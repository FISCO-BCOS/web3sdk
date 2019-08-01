package org.fisco.bcos.channel.dto;

public class BcosHeartbeat {
	public String Heartbeat;

	public String getHeartbeat() {
		return Heartbeat;
	}

	public void setHeartbeat(String heartbeat) {
		Heartbeat = heartbeat;
	}

	@Override
	public String toString() {
		return "BcosHeartbeat [Heartbeat=" + Heartbeat + "]";
	}
}
