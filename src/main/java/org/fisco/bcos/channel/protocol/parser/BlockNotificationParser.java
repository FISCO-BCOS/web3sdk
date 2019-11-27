package org.fisco.bcos.channel.protocol.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.dto.BcosBlockNotification;
import org.fisco.bcos.channel.protocol.EnumChannelProtocolVersion;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

public class BlockNotificationParser {

    public EnumChannelProtocolVersion version;

    public BlockNotificationParser(EnumChannelProtocolVersion version) {
        this.version = version;
    }

    public EnumChannelProtocolVersion getVersion() {
        return version;
    }

    public void setVersion(EnumChannelProtocolVersion version) {
        this.version = version;
    }

    public BcosBlockNotification decode(String data)
            throws JsonParseException, JsonMappingException, IOException {
        BcosBlockNotification bcosBlkNotify = new BcosBlockNotification();

        switch (getVersion()) {
            case VERSION_1:
                {
                    String[] split = data.split(",");
                    if (split.length != 2) {
                        throw new IllegalArgumentException(
                                " invalid block notify message, data: " + data);
                    }

                    bcosBlkNotify.setGroupID(split[0]);
                    bcosBlkNotify.setBlockNumber(new BigInteger(split[1]));
                }
                break;
            default:
                {
                    bcosBlkNotify =
                            ObjectMapperFactory.getObjectMapper()
                                    .readValue(data, BcosBlockNotification.class);
                }
                break;
        }

        return bcosBlkNotify;
    }
}
