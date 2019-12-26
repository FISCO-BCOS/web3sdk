package org.fisco.bcos.channel.client;

import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.rlp.RlpEncoder;
import org.fisco.bcos.web3j.rlp.RlpList;
import org.fisco.bcos.web3j.rlp.RlpString;
import org.fisco.bcos.web3j.rlp.RlpType;
import org.fisco.bcos.web3j.utils.Numeric;

// encode transaction receipt by Rlp into hex string
public class ReceiptEncoder {
    public static String encode(TransactionReceipt transactionReceipt) {
        List<RlpType> values = asRlpValues(transactionReceipt);
        RlpList rlpList = new RlpList(values);
        byte[] rlpBytes = RlpEncoder.encode(rlpList);
        return Numeric.toHexString(rlpBytes);
    }

    private static List<RlpType> asRlpValues(TransactionReceipt transactionReceipt) {
        List<RlpType> result = new ArrayList<>();
        // bytes
        result.add(RlpString.create(Numeric.hexStringToByteArray(transactionReceipt.getRoot())));

        // BigInteger
        result.add(RlpString.create(Numeric.toBigInt(transactionReceipt.getGasUsedRaw())));

        result.add(
                RlpString.create(
                        Numeric.hexStringToByteArray(transactionReceipt.getContractAddress())));

        result.add(
                RlpString.create(Numeric.hexStringToByteArray(transactionReceipt.getLogsBloom())));

        result.add(RlpString.create(Numeric.toBigInt(transactionReceipt.getStatus())));

        result.add(RlpString.create(Numeric.hexStringToByteArray(transactionReceipt.getOutput())));

        // List
        List<Log> logs = transactionReceipt.getLogs();
        List<RlpType> logList = new ArrayList<>();
        for (Log log : logs) {
            List<RlpType> logUnit = new ArrayList<>();
            logUnit.add(RlpString.create(Numeric.hexStringToByteArray(log.getAddress())));

            List<String> topics = log.getTopics();
            List<RlpType> topicList = new ArrayList<>();
            for (String topic : topics) {
                topicList.add(RlpString.create(Numeric.hexStringToByteArray(topic)));
            }
            RlpList topicRlpList = new RlpList(topicList);
            logUnit.add(topicRlpList);
            logUnit.add(RlpString.create(Numeric.hexStringToByteArray(log.getData())));
            logList.add(new RlpList(logUnit));
        }
        RlpList logRlpList = new RlpList(logList);
        result.add(logRlpList);
        return result;
    }
}
