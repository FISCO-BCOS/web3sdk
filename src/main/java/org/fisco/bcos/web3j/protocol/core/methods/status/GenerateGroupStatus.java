package org.fisco.bcos.web3j.protocol.core.methods.status;

/** return status for rpc interface: generateGroup */
public class GenerateGroupStatus {
    // success
    public static final String SUCCESS = "0x0";
    // group already exist
    public static final String GROUP_EXIST = "0x1";
    // group genesis file already exist
    public static final String GROUP_GENESIS_FILE_EXIST = "0x2";
    // group config file already exist
    public static final String GROUP_CONFIG_FILE_EXIST = "0x3";
    // invalid parameters
    public static final String INVALID_PARAMS = "0x4";
    // node inner error
    public static final String OTHER_ERROR = "0x5";
}
