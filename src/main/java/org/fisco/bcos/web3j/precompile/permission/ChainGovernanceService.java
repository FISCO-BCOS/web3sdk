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

public class ChainGovernanceService {
    private static BigInteger gasPrice = new BigInteger("30000000000");
    private static BigInteger gasLimit = new BigInteger("30000000000");

    private static String chainGovernanceAddress = "0x0000000000000000000000000000000000001008";

    private ChainGovernance chainGovernance;
    private Web3j web3j;
    private Credentials credentials;

    public ChainGovernanceService(Web3j web3j, Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        chainGovernance =
                ChainGovernance.load(
                        chainGovernanceAddress, web3j, credentials, contractGasProvider);
        this.web3j = web3j;
        this.credentials = credentials;
    }

    public List<PermissionInfo> listOperators() throws Exception {
        String operatorsInfo = chainGovernance.listOperators().send();
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        return objectMapper.readValue(
                operatorsInfo,
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, PermissionInfo.class));
    }

    public String updateCommitteeMemberWeight(String account, int weight) throws Exception {
        TransactionReceipt transactionReceipt =
                chainGovernance
                        .updateCommitteeMemberWeight(account, BigInteger.valueOf(weight))
                        .send();
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public BigInteger queryThreshold() throws Exception {
        return chainGovernance.queryThreshold().send();
    }

    public BigInteger queryCommitteeMemberWeight(String account) throws Exception {
        return chainGovernance.queryCommitteeMemberWeight(account).send();
    }

    public String grantCommitteeMember(String account) throws Exception {
        TransactionReceipt transactionReceipt =
                chainGovernance.grantCommitteeMember(account).send();
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public List<PermissionInfo> listCommitteeMembers() throws Exception {
        String committeeMembersInfo = chainGovernance.listCommitteeMembers().send();
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        return objectMapper.readValue(
                committeeMembersInfo,
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, PermissionInfo.class));
    }

    public String updateThreshold(int threshold) throws Exception {
        TransactionReceipt transactionReceipt =
                chainGovernance.updateThreshold(BigInteger.valueOf(threshold)).send();
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public String revokeCommitteeMember(String account) throws Exception {
        TransactionReceipt transactionReceipt =
                chainGovernance.revokeCommitteeMember(account).send();
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public String grantOperator(String account) throws Exception {
        TransactionReceipt transactionReceipt = chainGovernance.grantOperator(account).send();
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public String revokeOperator(String account) throws Exception {
        TransactionReceipt transactionReceipt = chainGovernance.revokeOperator(account).send();
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }
}
