package org.fisco.bcos.channel.client;

public class AmopCertRandValueItem
{
	private	String	randValue;
	private	long	createTime;
	public String getRandValue() {
		return randValue;
	}
	public void setRandValue(String randValue) {
		this.randValue = randValue;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}