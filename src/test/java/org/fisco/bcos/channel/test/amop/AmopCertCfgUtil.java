package org.fisco.bcos.channel.test.amop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 本文件主要是用于加载公私钥配置文件
*/

public class AmopCertCfgUtil {
	public	static Logger logger = LoggerFactory.getLogger(AmopCertCfgUtil.class);
	
	public static ConcurrentHashMap<String, Set<String>> loadPukFromCfg(String filePath) {
		ConcurrentHashMap<String, Set<String>> topic2PublicKey = new ConcurrentHashMap<>();
		File file = new File(filePath);
		if (!file.exists()) {
			logger.info("filepath:{} not exist just return", filePath);
			return topic2PublicKey;
		}

		Properties props = new Properties();
		try {

			InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), "utf-8");
			props.load(reader);
			String[] topicArray = props.getProperty("topic_list").split(";");
			for (int index = 0; index < topicArray.length; index++) {
				String pukList = props.getProperty(topicArray[index]);
				String[] pukArray = pukList.split(";");
				Set<String> set = new HashSet<>();
				for (int pukIndex = 0; pukIndex < pukArray.length; pukIndex++) {
					set.add(pukArray[pukIndex]);
					logger.info("topic:{} puk:{}", topicArray[index], pukArray[pukIndex]);
				}
				topic2PublicKey.put(topicArray[index], set);
			}
			return topic2PublicKey;

		} catch (IOException e) {
			logger.error("loadPukFromCfg failed {}", e.getMessage());
			return topic2PublicKey;
		}

		catch (Exception e) {
			logger.error("loadPrkFromCfg failed {}", e.getMessage());
			return topic2PublicKey;
		}
	}

	public static ConcurrentHashMap<String, String> loadPrkFromCfg(String filePath) {
		ConcurrentHashMap<String, String> topic2PrivateKey = new ConcurrentHashMap<>();
		File file = new File(filePath);
		if (!file.exists()) {
			logger.info("filepath:{} not exist just return", filePath);
			return topic2PrivateKey;
		}

		Properties props = new Properties();
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), "utf-8");
			props.load(reader);
			String[] topicArray = props.getProperty("topic_list").split(";");
			for (int index = 0; index < topicArray.length; index++) {
				String prk = props.getProperty(topicArray[index]);
				topic2PrivateKey.put(topicArray[index], prk);
				logger.info("topic:{} puk:{}", topicArray[index], prk);
			}
			return topic2PrivateKey;
		} catch (IOException e) {
			logger.error("loadPrkFromCfg failed {}", e.getMessage());
			return topic2PrivateKey;
		} catch (Exception e) {
			logger.error("loadPrkFromCfg failed {}", e.getMessage());
			return topic2PrivateKey;
		}
	}

	public static Set<String> getTopicNeed2Send(ConcurrentHashMap<String, String> topic2PrivateKey,
			ConcurrentHashMap<String, Set<String>> topic2ublicKey) {
		Set<String> set = new HashSet<>();

		for (String key : topic2PrivateKey.keySet()) {
			String keyPrefiex = "has_prk_";
			keyPrefiex = keyPrefiex + key;
			set.add(keyPrefiex);
		}
		
		for(String key : topic2ublicKey.keySet())
		{
			String keyPrefiex = "has_puk_";
			keyPrefiex = keyPrefiex + key;
			set.add(keyPrefiex);
		}
		return set;
	}
}
