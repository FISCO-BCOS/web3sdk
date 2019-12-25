package org.fisco.bcos.web3j.protocol.core.methods.status;

/** return status for rpc interface: startGroup */
public class StartGroupStatus {
    // success
    public static final String SUCCESS = "0x0";
    // group already running
    public static final String GROUP_IS_RUNNING = "0x1";
    // group genesis file not exist
    public static final String GROUP_GENESIS_FILE_NOT_EXIST = "0x2";
    // group config file not exist
    public static final String GROUP_CONFIG_FILE_NOT_EXIST = "0x3";
    // group genesis file invalid
    public static final String GROUP_GENESIS_INVALID = "0x4";
    // group config file invalid
    public static final String GROUP_CONFIG_INVALID = "0x5";
    // invalid parameters
    public static final String INVALID_PARAMS = "0x6";
    // node inner error
    public static final String OTHER_ERROR = "0x7";
}
