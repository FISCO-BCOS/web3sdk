package org.fisco.bcos.channel.protocol.parser;

import java.io.IOException;
import java.math.BigInteger;

import org.fisco.bcos.channel.dto.BcosBlkNotify;
import org.fisco.bcos.channel.dto.BcosHeartbeat;
import org.fisco.bcos.channel.protocol.NodeVersion;
import org.fisco.bcos.channel.protocol.Version;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class HeartBeatParser {
	
	public Version version;
	
	public HeartBeatParser(Version version) {
		this.version = version;
	}
	
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public byte[] encode(String value) throws JsonProcessingException {

		byte[] result = null;

		switch (getVersion()) {

		case VERSION_2: {
			BcosHeartbeat bcosHeartbeat = new BcosHeartbeat();
			bcosHeartbeat.setHeartbeat(value);
			result = ObjectMapperFactory.getObjectMapper().writeValueAsBytes(bcosHeartbeat);
		}
			break;
		case VERSION_1: {

		} // break;
		default:
			result = value.getBytes();
			break;
		}

		return result;
	}
	
	public BcosHeartbeat decode(byte[] data) throws JsonParseException, JsonMappingException, IOException {
		BcosHeartbeat  bcosHeartbeat = new BcosHeartbeat();

		switch (getVersion()) {

		case VERSION_2: {
			bcosHeartbeat = ObjectMapperFactory.getObjectMapper().readValue(data, BcosHeartbeat.class);
		}
			break;
		case VERSION_1: {

		} // break;
		default:
			bcosHeartbeat.setHeartbeat(new String(data, "utf-8"));
			break;
		}

		return bcosHeartbeat;
	}
}
