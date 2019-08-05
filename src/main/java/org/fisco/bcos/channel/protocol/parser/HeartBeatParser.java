package org.fisco.bcos.channel.protocol.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import org.fisco.bcos.channel.dto.BcosHeartbeat;
import org.fisco.bcos.channel.protocol.EnumChannelProtocolVersion;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

public class HeartBeatParser {

    public EnumChannelProtocolVersion version;

    public HeartBeatParser(EnumChannelProtocolVersion version) {
        this.version = version;
    }

    public EnumChannelProtocolVersion getVersion() {
        return version;
    }

    public void setVersion(EnumChannelProtocolVersion version) {
        this.version = version;
    }

    public byte[] encode(String value) throws JsonProcessingException {

        byte[] result = null;

        switch (getVersion()) {
            case VERSION_2:
                {
                    BcosHeartbeat bcosHeartbeat = new BcosHeartbeat();
                    bcosHeartbeat.setHeartBeat(Integer.parseInt(value));
                    result = ObjectMapperFactory.getObjectMapper().writeValueAsBytes(bcosHeartbeat);
                }
                break;
            case VERSION_1:
                {
                } // break;
            default:
                result = value.getBytes();
                break;
        }

        return result;
    }

    public BcosHeartbeat decode(String data)
            throws JsonParseException, JsonMappingException, IOException {
        BcosHeartbeat bcosHeartbeat = new BcosHeartbeat();

        switch (getVersion()) {
            case VERSION_2:
                {
                    bcosHeartbeat =
                            ObjectMapperFactory.getObjectMapper()
                                    .readValue(data, BcosHeartbeat.class);
                }
                break;
            case VERSION_1:
                {
                } // break;
            default:
                bcosHeartbeat.setHeartBeat(Integer.parseInt(data));
                break;
        }

        return bcosHeartbeat;
    }
}
