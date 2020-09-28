package org.fisco.bcos.web3j.precompile.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

public class PermissionService {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static String PermissionPrecompileAddress =
            "0x0000000000000000000000000000000000001005";
    private Permission permission;
    private Web3j web3j;
    private Credentials credentials;

    public PermissionService(Web3j web3j, Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        permission =
                Permission.load(
                        PermissionPrecompileAddress, web3j, credentials, contractGasProvider);
        this.web3j = web3j;
        this.credentials = credentials;
    }

    public List<PermissionInfo> queryPermission(String address) throws Exception {
        String permissionInfo = permission.queryPermission(address).send();
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        return objectMapper.readValue(
                permissionInfo,
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, PermissionInfo.class));
    }

    public String grantWrite(String address, String user) throws Exception {
        TransactionReceipt transactionReceipt = grantWriteAndRetReceipt(address, user);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt grantWriteAndRetReceipt(String address, String user)
            throws Exception {
        return permission.grantWrite(address, user).send();
    }

    public String revokeWrite(String address, String user) throws Exception {
        TransactionReceipt receipt = revokeWriteAndRetReceipt(address, user);
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public TransactionReceipt revokeWriteAndRetReceipt(String address, String user)
            throws Exception {
        return permission.revokeWrite(address, user).send();
    }

    public String grantUserTableManager(String tableName, String grantress) throws Exception {
        return grant(tableName, grantress);
    }

    public String revokeUserTableManager(String tableName, String grantress) throws Exception {
        return revoke(tableName, grantress);
    }

    public List<PermissionInfo> listUserTableManager(String tableName) throws Exception {
        return list(tableName);
    }

    public String grantDeployAndCreateManager(String grantees) throws Exception {
        return grant(PrecompiledCommon.SYS_TABLE, grantees);
    }

    public String revokeDeployAndCreateManager(String grantees) throws Exception {
        return revoke(PrecompiledCommon.SYS_TABLE, grantees);
    }

    public List<PermissionInfo> listDeployAndCreateManager() throws Exception {
        return list(PrecompiledCommon.SYS_TABLE);
    }

    public String grantPermissionManager(String grantress) throws Exception {
        return grant(PrecompiledCommon.SYS_TABLE_ACCESS, grantress);
    }

    public String revokePermissionManager(String grantress) throws Exception {
        return revoke(PrecompiledCommon.SYS_TABLE_ACCESS, grantress);
    }

    public List<PermissionInfo> listPermissionManager() throws Exception {
        return list(PrecompiledCommon.SYS_TABLE_ACCESS);
    }

    public String grantNodeManager(String grantress) throws Exception {
        return grant(PrecompiledCommon.SYS_CONSENSUS, grantress);
    }

    public String revokeNodeManager(String grantress) throws Exception {
        return revoke(PrecompiledCommon.SYS_CONSENSUS, grantress);
    }

    public List<PermissionInfo> listNodeManager() throws Exception {
        return list(PrecompiledCommon.SYS_CONSENSUS);
    }

    public String grantCNSManager(String grantress) throws Exception {
        return grant(PrecompiledCommon.SYS_CNS, grantress);
    }

    public String revokeCNSManager(String grantress) throws Exception {
        return revoke(PrecompiledCommon.SYS_CNS, grantress);
    }

    public List<PermissionInfo> listCNSManager() throws Exception {
        return list(PrecompiledCommon.SYS_CNS);
    }

    public String grantSysConfigManager(String grantress) throws Exception {
        return grant(PrecompiledCommon.SYS_CONFIG, grantress);
    }

    public String revokeSysConfigManager(String grantress) throws Exception {
        return revoke(PrecompiledCommon.SYS_CONFIG, grantress);
    }

    public TransactionReceipt revokeSysConfigManagerAndRetReceipt(String grantress)
            throws Exception {
        return revokeAndRetReceipt(PrecompiledCommon.SYS_CONFIG, grantress);
    }

    public List<PermissionInfo> listSysConfigManager() throws Exception {
        return list(PrecompiledCommon.SYS_CONFIG);
    }

    public String grant(String tableName, String grandness) throws Exception {
        TransactionReceipt receipt = grantAndRetReceipt(tableName, grandness);
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public TransactionReceipt grantAndRetReceipt(String tableName, String grantress)
            throws Exception {
        return permission.insert(tableName, grantress).send();
    }

    public String revoke(String tableName, String address) throws Exception {
        TransactionReceipt receipt = revokeAndRetReceipt(tableName, address);
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public TransactionReceipt revokeAndRetReceipt(String tableName, String address)
            throws Exception {
        return permission.remove(tableName, address).send();
    }

    private List<PermissionInfo> list(String tableName) throws Exception {
        String permissionInfo = permission.queryByName(tableName).send();
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        return objectMapper.readValue(
                permissionInfo,
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, PermissionInfo.class));
    }
}
