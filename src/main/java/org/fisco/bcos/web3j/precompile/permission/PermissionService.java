package org.fisco.bcos.web3j.precompile.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.precompile.crud.CRUDService;
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

    public String grantUserTableManager(String tableName, String grantress) throws Exception {
        CRUDService crudSerivce = new CRUDService(web3j, credentials);
        crudSerivce.desc(tableName);
        return grant(tableName, grantress);
    }

    public String revokeUserTableManager(String tableName, String grantress) throws Exception {
        return revoke(tableName, grantress);
    }

    public List<PermissionInfo> listUserTableManager(String tableName) throws Exception {
        return list(tableName);
    }

    public String grantDeployAndCreateManager(String grantress) throws Exception {
        return grant(PrecompiledCommon.SYS_TABLE, grantress);
    }

    public String revokeDeployAndCreateManager(String grantress) throws Exception {
        return revoke(PrecompiledCommon.SYS_TABLE, grantress);
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

    public List<PermissionInfo> listSysConfigManager() throws Exception {
        return list(PrecompiledCommon.SYS_CONFIG);
    }

    private String grant(String tableName, String grantress) throws Exception {
        TransactionReceipt receipt = permission.insert(tableName, grantress).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    private String revoke(String tableName, String address) throws Exception {
        TransactionReceipt receipt = permission.remove(tableName, address).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    private List<PermissionInfo> list(String tableName) throws Exception {
        String permissionyInfo = permission.queryByName(tableName).send();
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        return objectMapper.readValue(
                permissionyInfo,
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, PermissionInfo.class));
    }
}
