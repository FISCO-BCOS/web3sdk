package org.fisco.bcos.web3j.precompile.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigInteger;
import org.fisco.bcos.fisco.EnumNodeVersion;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.StatusCode;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeVersion.Version;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;

public class PrecompiledCommon {

    public static final String BCOS_RC1 = "2.0.0-rc1";
    public static final String BCOS_RC2 = "2.0.0-rc2";
    public static final String BCOS_RC3 = "2.0.0-rc3";

    // system table for authority control
    public static final String USER_TABLE_PREFIX = "_user_";
    public static final String USER_TABLE_PREFIX_2_2_0_VERSION = "u_";

    public static final String SYS_TABLE = "_sys_tables_";
    public static final String SYS_TABLE_ACCESS = "_sys_table_access_";
    public static final String SYS_CONSENSUS = "_sys_consensus_";
    public static final String SYS_CNS = "_sys_cns_";
    public static final String SYS_CONFIG = "_sys_config_";

    public static final int Success = 0;
    public static final int PermissionDenied_RC1 = 80;
    public static final int PermissionDenied = 50000;
    public static final int PermissionDenied_RC3 = -50000;
    public static final int TableExist = 50001;
    public static final int TableExist_RC3 = -50001;
    public static final int TableNameAndAddressExist_RC1 = 56;
    public static final int TableNameAndAddressExist = 51000;
    public static final int TableNameAndAddressExist_RC3 = -51000;
    public static final int TableNameAndAddressNotExist_RC1 = 57;
    public static final int TableNameAndAddressNotExist = 51001;
    public static final int TableNameAndAddressNotExist_RC3 = -51001;
    public static final int InvalidNodeId = -51100;
    public static final int LastSealer_RC1 = 100;
    public static final int LastSealer = 51101;
    public static final int LastSealer_RC3 = -51101;
    public static final int P2pNetwork = -51102;
    public static final int GroupPeers = -51103;
    public static final int SealerList = -51104;
    public static final int ObserverList = -51105;
    public static final int ContractNameAndVersionExist = -51200;
    public static final int VersionExceeds = -51201;
    public static final int InvalidKey_RC1 = 157;
    public static final int InvalidKey = 51300;
    public static final int InvalidKey_RC3 = -51300;

    public static final int TABLE_KEY_MAX_LENGTH = 255;

    public static String BCOS_VERSION = "";

    public static String transferToJson(int code) throws IOException {
        // adapt fisco-bcos rc1 || rc2 || rc3
        String msg = "";
        if (BCOS_VERSION == null
                || EnumNodeVersion.BCOS_2_0_0_RC1.getVersion().equals(BCOS_VERSION)) {
            if (code == PermissionDenied_RC1) {
                msg = "permission denied";
            } else if (code == TableNameAndAddressExist_RC1) {
                msg = "table name and address already exist";
            } else if (code == TableNameAndAddressNotExist_RC1) {
                msg = "table name and address does not exist";
            } else if (code == LastSealer_RC1) {
                msg = "the last sealer cannot be removed";
            } else if (code == InvalidKey_RC1) {
                msg = "invalid configuration entry";
            }
        } else if (EnumNodeVersion.BCOS_2_0_0_RC2.getVersion().equals(BCOS_VERSION)) {
            if (code == PermissionDenied) {
                msg = "permission denied";
            } else if (code == TableNameAndAddressExist) {
                msg = "table name and address already exist";
            } else if (code == TableNameAndAddressNotExist) {
                msg = "table name and address does not exist";
            } else if (code == LastSealer) {
                msg = "the last sealer cannot be removed";
            } else if (code == TableExist) {
                msg = "table already exist";
            } else if (code == InvalidKey) {
                msg = "invalid configuration entry";
            }
        } else {
            if (code == PermissionDenied_RC3) {
                msg = "permission denied";
            } else if (code == TableNameAndAddressExist_RC3) {
                msg = "table name and address already exist";
            } else if (code == TableNameAndAddressNotExist_RC3) {
                msg = "table name and address does not exist";
            } else if (code == LastSealer_RC3) {
                msg = "the last sealer cannot be removed";
            } else if (code == TableExist_RC3) {
                msg = "table already exist";
            } else if (code == InvalidKey_RC3) {
                msg = "invalid configuration entry";
            }
        }
        if (code == Success) {
            msg = "success";
        } else if (code == InvalidNodeId) {
            msg = "invalid node ID";
        } else if (code == P2pNetwork) {
            msg = "the node is not reachable";
        } else if (code == GroupPeers) {
            msg = "the node is not a group peer";
        } else if (code == SealerList) {
            msg = "the node is already in the sealer list";
        } else if (code == ObserverList) {
            msg = "the node is already in the observer list";
        } else if (code == ContractNameAndVersionExist) {
            msg = "contract name and version already exist";
        } else if (code == VersionExceeds) {
            msg = "version string length exceeds the maximum limit";
        }
        ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
        return mapper.writeValueAsString(new PrecompiledResponse(code, msg));
    }

    public static String getJsonStr(String output, Web3j web3j) throws IOException {
        try {
            Version nodeVersion = web3j.getNodeVersion().send().getNodeVersion();
            BCOS_VERSION = nodeVersion.getSupportedVersion();
            int code = 0;
            code = new BigInteger(output.substring(2, output.length()), 16).intValue();
            if (code == 1) {
                code = Success;
            }
            return transferToJson(code);
        } catch (NumberFormatException e) {
            return "The call function does not exist.";
        }
    }

    public static int handleTransactionReceiptForCRUD(TransactionReceipt receipt)
            throws TransactionException {
        String status = receipt.getStatus();
        if (!"0x0".equals(status)) {
            throw new TransactionException(
                    StatusCode.getStatusMessage(status, receipt.getMessage()));
        }
        String output = receipt.getOutput();
        if (!"0x".equals(output)) {
            return new BigInteger(output.substring(2, output.length()), 16).intValue();
        } else {
            throw new TransactionException("Transaction is handled failure.");
        }
    }

    public static String handleTransactionReceipt(TransactionReceipt receipt, Web3j web3j)
            throws TransactionException, IOException {
        String status = receipt.getStatus();
        if (!"0x0".equals(status)) {
            throw new TransactionException(
                    StatusCode.getStatusMessage(receipt.getStatus(), receipt.getMessage()));
        } else {
            if (receipt.getOutput() != null) {
                return PrecompiledCommon.getJsonStr(receipt.getOutput(), web3j);
            } else {
                throw new TransactionException("Transaction is handled failure.");
            }
        }
    }
}
