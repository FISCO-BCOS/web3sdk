package org.fisco.bcos.channel.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AMOPVerifyUtil {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    private ConcurrentHashMap<String, PrivateKey> topic2PrivateKey =
            new ConcurrentHashMap<String, PrivateKey>();
    private ConcurrentHashMap<String, List<PublicKey>> topic2PublicKey =
            new ConcurrentHashMap<String, List<PublicKey>>();
    private transient ECDSAUtil ecdsaUtil = new ECDSAUtil();

    public ConcurrentHashMap<String, PrivateKey> getTopic2PrivateKey() {
        return topic2PrivateKey;
    }

    public void setTopic2PrivateKey(ConcurrentHashMap<String, PrivateKey> topic2PrivateKey) {
        this.topic2PrivateKey = topic2PrivateKey;
    }

    public ConcurrentHashMap<String, List<PublicKey>> getTopic2PublicKey() {
        return topic2PublicKey;
    }

    public void setTopic2PublicKey(ConcurrentHashMap<String, List<PublicKey>> topic2PublicKey) {
        this.topic2PublicKey = topic2PublicKey;
    }

    public String signatureForRandValue(String topic, String randValue) {
        try {
            PrivateKey privateKey = topic2PrivateKey.get(topic);
            if (privateKey == null) {
                logger.error("topic:{} has no private key please check", topic);
                throw new AmopException(
                        "private key of topic:" + topic + " not exist please check");
            }
            byte[] privateSignature = ecdsaUtil.privateEncrypt(randValue.getBytes(), privateKey);
            return ecdsaUtil.byte2Base64(privateSignature);
        } catch (AmopException e) {
            logger.error("signatureForRandValue for:{} exception:{}", randValue, e.getMessage());
            return "";
        } catch (Exception e) {
            logger.error("signatureForRandValue for:{} exception:{}", randValue, e.getMessage());
            return "";
        }
    }

    public boolean checkSignature(PublicKey publicKey, String signature, String originalValue) {

        try {
            byte[] base642Byte = ecdsaUtil.base642Byte(signature);
            return ecdsaUtil.publicDecrypt(base642Byte, originalValue.getBytes(), publicKey);
        } catch (Exception e) {
            return false;
        }
    }

    public int checkSignatureValidate(String topic, String signature, String randValue) {
        try {

            List<PublicKey> pukSet = topic2PublicKey.get(topic);
            if (pukSet == null) {
                logger.error("topic:{} has no private key please check", topic);
                throw new AmopException("public key of topic:" + topic + " not exist please check");
            }
            Iterator<PublicKey> it = pukSet.iterator();
            while (it.hasNext()) {
                boolean result = checkSignature(it.next(), signature, randValue);
                if (result) {
                    return 0;
                }
            }
        } catch (AmopException e) {
            logger.error("checkSignatureValidate for:{} exception:{}", signature, e.getMessage());
            return 1;
        } catch (Exception e) {
            logger.error("checkSignatureValidate for:{} exception:{}", signature, e.getMessage());
            return 1;
        }
        return 1;
    }

    public String parseDataFromPush(Integer length, byte[] data) {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer(length);
        byteBuf.writeBytes(data);
        int topicLen = byteBuf.readUnsignedByte() - 1;
        logger.info("topic length:{}", topicLen);
        byte[] topicBytes = new byte[topicLen];
        byteBuf.readBytes(topicBytes, 0, topicLen);
        String topic = new String(topicBytes);
        logger.info("topic len:{} topic:{}", topicLen, topic);

        int contentLen = (byteBuf.readShort());
        logger.info("read unsigned contentlen:{}", contentLen);
        byte[] contentBytes = new byte[contentLen];
        byteBuf.readBytes(contentBytes, 0, contentLen);
        String content = new String(contentBytes);
        logger.info("content len:{} topic:{}", contentLen, content);
        return content;
    }

    public byte[] getByteBuffByString(String topic, String content) {

        ByteBuf outBuf = PooledByteBufAllocator.DEFAULT.buffer();
        try {
            byte topicLen = (byte) (topic.length());
            outBuf.writeByte(topicLen + 1);
            outBuf.writeBytes(topic.getBytes());

            logger.info("topic to send:{}", topic);
            int jsonLen = content.length();
            outBuf.writeShort(jsonLen);
            outBuf.writeBytes(content.getBytes());
            logger.info("write buf len:{} msg:{}", jsonLen, content);

            byte[] bytes;

            if (outBuf.hasArray()) {
                bytes = outBuf.array();
            } else {
                int length = outBuf.readableBytes();
                bytes = new byte[length];
                outBuf.getBytes(outBuf.readerIndex(), bytes);
            }
            logger.info(
                    "write buf len:{} total len:{} msg:{} bytes:{}",
                    jsonLen,
                    bytes.length,
                    content,
                    Arrays.toString(bytes));
            return bytes;
        } finally {
            outBuf.release();
        }
    }
}
