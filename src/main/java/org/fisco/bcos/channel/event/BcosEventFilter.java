package org.fisco.bcos.channel.event;

import java.util.List;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BcosEventFilter {

	private String groupId;
	private String fromBlock;
	private String toBlock;
	private List<String> address;
	private List<Object> topics;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getFromBlock() {
		return fromBlock;
	}

	public void setFromBlock(String fromBlock) {
		this.fromBlock = fromBlock;
	}

	public String getToBlock() {
		return toBlock;
	}

	public void setToBlock(String toBlock) {
		this.toBlock = toBlock;
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public List<Object> getTopics() {
		return topics;
	}

	public void setTopics(List<Object> topics) {
		this.topics = topics;
	}

	@Override
	public String toString() {
		return "BcosEventFilter [groupId=" + groupId + ", fromBlock=" + fromBlock + ", toBlock=" + toBlock
				+ ", address=" + address + ", topics=" + topics + "]";
	}

	public String toJsonString() throws JsonProcessingException {
		return ObjectMapperFactory.getObjectMapper().writeValueAsString(this);

	}
}
