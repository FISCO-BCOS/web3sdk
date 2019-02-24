package org.fisco.bcos.web3j.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Build version utility method. */
public class Version {

  private Version() {}

  public static final String DEFAULT = "none";

  private static final String TIMESTAMP = "timestamp";
  private static final String VERSION = "version";

  public static String getVersion() throws IOException {
    return loadProperties().getProperty(VERSION);
  }

  public static String getTimestamp() throws IOException {
    return loadProperties().getProperty(TIMESTAMP);
  }

  private static Properties loadProperties() throws IOException {
    Properties properties = new Properties();
    InputStream in = null;
    try {
      in = Version.class.getResourceAsStream("/version.properties");
      properties.load(in);
    } finally {
      if (in != null) {
        in.close();
      }
    }
    return properties;
  }
}
