package org.fisco.bcos.web3j.precompile.csm;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.WalletUtils;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

public class ContractStatusService {
    private static BigInteger gasPrice = new BigInteger("30000000000");
    private static BigInteger gasLimit = new BigInteger("30000000000");
    private static String ContractLifeCyclePrecompiledAddress =
            "0x0000000000000000000000000000000000001007";
    private Web3j web3j;
    private ContractLifeCyclePrecompiled contractLifeCycle;

    public ContractStatusService(Web3j web3j, Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        this.web3j = web3j;
        this.contractLifeCycle =
                ContractLifeCyclePrecompiled.load(
                        ContractLifeCyclePrecompiledAddress,
                        web3j,
                        credentials,
                        contractGasProvider);
    }

    public String freeze(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        TransactionReceipt receipt = contractLifeCycle.freeze(addr).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public String unfreeze(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        TransactionReceipt receipt = contractLifeCycle.unfreeze(addr).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public String grantManager(String addr, String user) throws Exception {
        if (!WalletUtils.isValidAddress(addr) || !WalletUtils.isValidAddress(user)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        TransactionReceipt receipt = contractLifeCycle.grantManager(addr, user).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public String getStatus(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        Tuple2<BigInteger, String> send = contractLifeCycle.getStatus(addr).send();
        if (!(send.getValue1().intValue() == PrecompiledCommon.Success)) {
            return PrecompiledCommon.transferToJson(send.getValue1().intValue());
        }
        return send.getValue2();
    }

    public String listManager(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        Tuple2<BigInteger, List<String>> send = contractLifeCycle.listManager(addr).send();

        if (!(send.getValue1().intValue() == PrecompiledCommon.Success)) {
            return PrecompiledCommon.transferToJson(send.getValue1().intValue());
        }

        return ObjectMapperFactory.getObjectMapper().writeValueAsString(send.getValue2());
    }
}
