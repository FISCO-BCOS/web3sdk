package org.fisco.bcos.channel.protocol.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.channel.dto.BcosBlkNotify;
import org.fisco.bcos.channel.protocol.Version;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

public class BlkNotifyParser {

    public Version version;

    public BlkNotifyParser(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public BcosBlkNotify decode(byte[] data)
            throws JsonParseException, JsonMappingException, IOException {
        BcosBlkNotify bcosBlkNotify = new BcosBlkNotify();

        switch (getVersion()) {
            case VERSION_2:
                {
                    bcosBlkNotify =
                            ObjectMapperFactory.getObjectMapper()
                                    .readValue(data, BcosBlkNotify.class);
                }
                break;
            case VERSION_1:
                {
                    String data0 = new String(data);

                    String[] split = data0.split(",");
                    if (split.length != 2) {
                        throw new IllegalArgumentException(
                                " invalid block notify message , data is " + data0);
                    }

                    bcosBlkNotify.setGroupID(split[0]);
                    bcosBlkNotify.setBlockNumber(new BigInteger(split[1]));
                } // break;
            default:
                break;
        }

        return bcosBlkNotify;
    }
}
