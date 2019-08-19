package org.fisco.bcos.channel.event;

public enum EventFilterStatus {

	SUCCESS(0), 
	PUSH_COMPLETED(1), 
	NOT_SUPPORT_SERVICE(-41000), 
	INVALID_REQUEST(-41001), 
	GROUP_NOT_EXIST(-41002), 
	INVALID_RANGE(-41003),
	INVALID_RESPONSE(-41004), 
	UNKOWN_ERROR(-41005),;

	private int status;

	private EventFilterStatus(int status) {
		this.setStatus(status);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static EventFilterStatus fromIntStatus(int status) {
		for (EventFilterStatus e : EventFilterStatus.values()) {
			if (e.getStatus() == status) {
				return e;
			}
		}
		return EventFilterStatus.UNKOWN_ERROR;
	}

	public static String getDescMessage(int status) {
		 return getDescMessage(fromIntStatus(status));
	}

	public static String getDescMessage(EventFilterStatus status) {

		String desc;

		switch (status) {
		case SUCCESS:
			desc = "success";
			break;
		case PUSH_COMPLETED:
			desc = "push completed";
			break;
		case NOT_SUPPORT_SERVICE:
			desc = "sdk not support event filter opr, when service is not channelservice.";
			break;
		case INVALID_REQUEST:
			desc = "invalid request json format";
			break;
		case GROUP_NOT_EXIST:
			desc = "request groupid not exist";
			break;
		case INVALID_RANGE:
			desc = "invalid request block range, check \"startBlock\" permisson range";
			break;
		case INVALID_RESPONSE:
			desc = "invalid response json format, parser failed";
			break;
		default:
			desc = "other unkown error";

		}

		return desc;
	}
}
