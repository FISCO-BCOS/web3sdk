package org.fisco.bcos.web3j.precompile.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.fisco.bcos.fisco.EnumNodeVersion;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.precompile.exception.PrecompileMessageException;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRUDService {

    private static final Logger logger = LoggerFactory.getLogger(CRUDService.class);

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static final String TableFactoryPrecompileAddress =
            "0x0000000000000000000000000000000000001001";
    private static final String CRUDPrecompileAddress =
            "0x0000000000000000000000000000000000001002";
    private TableFactory tableFactory;
    private CRUD crud;

    public CRUDService(Web3j web3j, Credentials credentials) {

        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        tableFactory =
                TableFactory.load(
                        TableFactoryPrecompileAddress, web3j, credentials, contractGasProvider);
        crud = CRUD.load(CRUDPrecompileAddress, web3j, credentials, contractGasProvider);
    }

    public int createTable(Table table) throws Exception {

        TransactionReceipt receipt =
                tableFactory
                        .createTable(table.getTableName(), table.getKey(), table.getValueFields())
                        .send();
        return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
    }

    public int insert(Table table, Entry entry) throws Exception {

        if (table.getKey().length() > PrecompiledCommon.TABLE_KEY_MAX_LENGTH) {
            throw new PrecompileMessageException(
                    "The value of the table key exceeds the maximum limit("
                            + PrecompiledCommon.TABLE_KEY_MAX_LENGTH
                            + ").");
        }
        String entryJsonStr =
                ObjectMapperFactory.getObjectMapper().writeValueAsString(entry.getFields());

        TransactionReceipt receipt =
                crud.insert(table.getTableName(), table.getKey(), entryJsonStr, table.getOptional())
                        .send();
        return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
    }

    public int update(Table table, Entry entry, Condition condition) throws Exception {

        if (table.getKey().length() > PrecompiledCommon.TABLE_KEY_MAX_LENGTH) {
            throw new PrecompileMessageException(
                    "The value of the table key exceeds the maximum limit("
                            + PrecompiledCommon.TABLE_KEY_MAX_LENGTH
                            + ").");
        }
        String entryJsonStr =
                ObjectMapperFactory.getObjectMapper().writeValueAsString(entry.getFields());
        String conditionStr =
                ObjectMapperFactory.getObjectMapper().writeValueAsString(condition.getConditions());
        TransactionReceipt receipt =
                crud.update(
                                table.getTableName(),
                                table.getKey(),
                                entryJsonStr,
                                conditionStr,
                                table.getOptional())
                        .send();
        return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
    }

    public int remove(Table table, Condition condition) throws Exception {

        if (table.getKey().length() > PrecompiledCommon.TABLE_KEY_MAX_LENGTH) {
            throw new PrecompileMessageException(
                    "The value of the table key exceeds the maximum limit("
                            + PrecompiledCommon.TABLE_KEY_MAX_LENGTH
                            + ").");
        }
        String conditionStr =
                ObjectMapperFactory.getObjectMapper().writeValueAsString(condition.getConditions());
        TransactionReceipt receipt =
                crud.remove(table.getTableName(), table.getKey(), conditionStr, table.getOptional())
                        .send();
        return PrecompiledCommon.handleTransactionReceiptForCRUD(receipt);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, String>> select(Table table, Condition condition) throws Exception {

        if (table.getKey().length() > PrecompiledCommon.TABLE_KEY_MAX_LENGTH) {
            throw new PrecompileMessageException(
                    "The value of the table key exceeds the maximum limit("
                            + PrecompiledCommon.TABLE_KEY_MAX_LENGTH
                            + ").");
        }
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        String conditionJsonStr = objectMapper.writeValueAsString(condition.getConditions());
        String resultStr =
                crud.select(
                                table.getTableName(),
                                table.getKey(),
                                conditionJsonStr,
                                table.getOptional())
                        .send();
        List<Map<String, String>> result =
                (List<Map<String, String>>)
                        objectMapper.readValue(
                                resultStr,
                                objectMapper
                                        .getTypeFactory()
                                        .constructCollectionType(List.class, Map.class));
        return result;
    }

    public Table desc(String tableName) throws Exception {
        Table table = new Table();
        table.setTableName(PrecompiledCommon.SYS_TABLE);

        /** Compatible with node version 2.2.0 */
        try {
            String supportedVersion =
                    crud.getTransactionManager().getNodeVersion().getSupportedVersion();
            EnumNodeVersion.Version version = EnumNodeVersion.getClassVersion(supportedVersion);

            logger.debug("desc, table: {}, node version: {}", tableName, version);

            // less than 2.2.0
            if ((version.getMajor() == 2) && (version.getMinor() < 2)) {
                table.setKey(PrecompiledCommon.USER_TABLE_PREFIX + tableName);
            } else {
                table.setKey(PrecompiledCommon.USER_TABLE_PREFIX_2_2_0_VERSION + tableName);
            }
        } catch (Exception e) {
            throw new PrecompileMessageException(
                    " Cannot query node version, maybe disconnect to all nodes.");
        }

        Condition condition = table.getCondition();
        List<Map<String, String>> userTable = select(table, condition);
        Table tableInfo = new Table();
        if ((userTable != null)
                && (userTable.size() != 0)
                && (null != userTable.get(0))
                && !userTable.get(0).isEmpty()) {
            tableInfo.setTableName(tableName);
            tableInfo.setKey(userTable.get(0).get("key_field"));
            tableInfo.setValueFields(userTable.get(0).get("value_field"));
        } else {
            throw new PrecompileMessageException("The table '" + tableName + "' does not exist.");
        }
        return tableInfo;
    }
}
