package org.fisco.bcos.channel.client;

import java.math.BigInteger;

public abstract class BlockNotifyCallBack {

    public abstract void onBlockNotify(int groupID, BigInteger blockNumber);
}
