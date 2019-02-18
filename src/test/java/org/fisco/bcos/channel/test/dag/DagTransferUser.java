package org.fisco.bcos.channel.test.dag;

import java.math.BigInteger;

public class DagTransferUser {
	private String user;
	private BigInteger amount;
	
	@Override
	public String toString() {
		return "DagTransferUser [user=" + user + ", amount=" + amount + "]";
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public BigInteger getAmount() {
		return amount;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
}