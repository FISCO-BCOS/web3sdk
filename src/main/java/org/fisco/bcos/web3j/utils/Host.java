package org.fisco.bcos.web3j.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Host {
    /**
     * @param IP
     * @return true if IP valid IP string otherwise false
     */
    public static boolean validIP(String IP) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(IP);
        return matcher.matches();
    }

    /**
     * @param port
     * @return true if port valid IP port otherwise false
     */
    public static boolean validPort(String port) {
        try {
            Integer p = Integer.parseInt(port);
            return p > 0 && p <= 65535;
        } catch (Exception e) {
            return false;
        }
    }
}
