package org.fisco.bcos.channel.event;

@SuppressWarnings("serial")
public class EventFilterException extends Exception {
	
	public EventFilterException(int status, String message) {
		super(message);
		this.status = status;
	}
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}