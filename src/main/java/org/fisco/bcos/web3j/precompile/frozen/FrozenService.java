package org.fisco.bcos.web3j.precompile.frozen;

import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.WalletUtils;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

public class FrozenService {
    private static BigInteger gasPrice = new BigInteger("30000000000");
    private static BigInteger gasLimit = new BigInteger("30000000000");
    private static String ConsensusPrecompileAddress = "0x0000000000000000000000000000000000001007";
    private Web3j web3j;
    private Frozen frozen;

    public FrozenService(Web3j web3j, Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        this.web3j = web3j;
        this.frozen =
                Frozen.load(ConsensusPrecompileAddress, web3j, credentials, contractGasProvider);
    }

    public String frozen(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        TransactionReceipt receipt = frozen.freeze(addr).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public String unfrozen(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        TransactionReceipt receipt = frozen.unfreeze(addr).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public String kill(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        TransactionReceipt receipt = frozen.kill(addr).send();
        return PrecompiledCommon.handleTransactionReceipt(receipt, web3j);
    }

    public String queryStatus(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        Tuple2<BigInteger, String> send = frozen.queryStatus(addr).send();
        return ObjectMapperFactory.getObjectMapper().writeValueAsString(send);
    }
}
