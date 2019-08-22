package org.fisco.bcos.channel.event.filter;

import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.utils.Numeric;

public class TopicTools {
    public static final int MAX_NUM_TOPIC_EVENT_LOG = 4;

    public static String integerToTopic(BigInteger i) {
        return Numeric.toHexStringWithPrefixZeroPadded(i, 64);
    }

    public static String boolToTopic(boolean b) {
        if (b) {
            return Numeric.toHexStringWithPrefixZeroPadded(BigInteger.ONE, 64);
        } else {
            return Numeric.toHexStringWithPrefixZeroPadded(BigInteger.ZERO, 64);
        }
    }

    public static String addressToTopic(String s) {
        return s;
    }

    public static String stringToTopic(String s) {
        byte[] hash = Hash.sha3(s.getBytes());
        return Numeric.toHexString(hash);
    }

    public static String bytesToTopic(byte[] b) {
        byte[] hash = Hash.sha3(b);
        return Numeric.toHexString(hash);
    }

    public static String byteNToTopic(byte[] b) {
        return Numeric.toHexString(b);
    }
}
