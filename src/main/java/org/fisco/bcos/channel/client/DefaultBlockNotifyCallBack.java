package org.fisco.bcos.channel.client;

import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultBlockNotifyCallBack implements BlockNotifyCallBack {
    private static Logger logger = LoggerFactory.getLogger(DefaultBlockNotifyCallBack.class);

    @Override
    public void onBlockNotify(int groupID, BigInteger blockNumber) {
        logger.debug("  groupID {}, block number {} ", groupID, blockNumber);
    }
}
