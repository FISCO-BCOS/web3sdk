package org.fisco.bcos.web3j.precompile.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

public class ChainGovernanceService {
    private static BigInteger gasPrice = new BigInteger("30000000000");
    private static BigInteger gasLimit = new BigInteger("30000000000");

    private static final String chainGovernanceAddress =
            "0x0000000000000000000000000000000000001008";

    private ChainGovernance chainGovernance;
    private Web3j web3j;

    public static String getChainGovernanceAddress() {
        return chainGovernanceAddress;
    }

    public ChainGovernance getChainGovernance() {
        return chainGovernance;
    }

    public void setChainGovernance(ChainGovernance chainGovernance) {
        this.chainGovernance = chainGovernance;
    }

    public ChainGovernanceService(Web3j web3j, Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        chainGovernance =
                ChainGovernance.load(
                        chainGovernanceAddress, web3j, credentials, contractGasProvider);
        this.web3j = web3j;
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
                updateCommitteeMemberWeightAndRetReceipt(account, weight);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt updateCommitteeMemberWeightAndRetReceipt(String account, int weight)
            throws Exception {
        return chainGovernance
                .updateCommitteeMemberWeight(account, BigInteger.valueOf(weight))
                .send();
    }

    public BigInteger queryThreshold() throws Exception {
        return chainGovernance.queryThreshold().send();
    }

    public Tuple2<Boolean, BigInteger> queryCommitteeMemberWeight(String account) throws Exception {
        return chainGovernance.queryCommitteeMemberWeight(account).send();
    }

    public String grantCommitteeMember(String account) throws Exception {
        TransactionReceipt transactionReceipt = grantCommitteeMemberAndRetReceipt(account);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt grantCommitteeMemberAndRetReceipt(String account) throws Exception {
        return chainGovernance.grantCommitteeMember(account).send();
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
        TransactionReceipt transactionReceipt = updateThresholdAndRetReceipt(threshold);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt updateThresholdAndRetReceipt(int threshold) throws Exception {
        return chainGovernance.updateThreshold(BigInteger.valueOf(threshold)).send();
    }

    public String revokeCommitteeMember(String account) throws Exception {
        TransactionReceipt transactionReceipt = revokeCommitteeMemberAndRetReceipt(account);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt revokeCommitteeMemberAndRetReceipt(String account) throws Exception {
        return chainGovernance.revokeCommitteeMember(account).send();
    }

    public String grantOperator(String account) throws Exception {
        TransactionReceipt transactionReceipt = grantOperatorAndRetReceipt(account);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt grantOperatorAndRetReceipt(String account) throws Exception {
        return chainGovernance.grantOperator(account).send();
    }

    public String revokeOperator(String account) throws Exception {
        TransactionReceipt transactionReceipt = revokeOperatorAndRetReceipt(account);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt revokeOperatorAndRetReceipt(String account) throws Exception {
        return chainGovernance.revokeOperator(account).send();
    }

    public String freezeAccount(String account) throws Exception {
        TransactionReceipt transactionReceipt = freezeAccountAndRetReceipt(account);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt freezeAccountAndRetReceipt(String account) throws Exception {
        return chainGovernance.freezeAccount(account).send();
    }

    public String unfreezeAccount(String account) throws Exception {
        TransactionReceipt transactionReceipt = unfreezeAccountAndRetReceipt(account);
        return PrecompiledCommon.handleTransactionReceipt(transactionReceipt, web3j);
    }

    public TransactionReceipt unfreezeAccountAndRetReceipt(String account) throws Exception {
        return chainGovernance.unfreezeAccount(account).send();
    }

    public String getAccountStatus(String account) throws Exception {
        return chainGovernance.getAccountStatus(account).send();
    }
}
