package org.fisco.bcos.web3j.precompile.frozen;

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

public class FrozenService {
    private static BigInteger gasPrice = new BigInteger("30000000000");
    private static BigInteger gasLimit = new BigInteger("30000000000");
    private static String ContractFrozenPrecompileAddress =
            "0x0000000000000000000000000000000000001007";
    private Web3j web3j;
    private Frozen frozen;

    public FrozenService(Web3j web3j, Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        this.web3j = web3j;
        this.frozen =
                Frozen.load(
                        ContractFrozenPrecompileAddress, web3j, credentials, contractGasProvider);
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
        if (!(send.getValue1().intValue() == PrecompiledCommon.Success)) {
            return PrecompiledCommon.transferToJson(send.getValue1().intValue());
        }
        return send.getValue2();
    }

    public String queryAuthority(String addr) throws Exception {
        if (!WalletUtils.isValidAddress(addr)) {
            return PrecompiledCommon.transferToJson(PrecompiledCommon.InvalidAddress);
        }

        Tuple2<BigInteger, List<String>> send = frozen.queryAuthority(addr).send();

        if (!(send.getValue1().intValue() == PrecompiledCommon.Success)) {
            return PrecompiledCommon.transferToJson(send.getValue1().intValue());
        }

        return ObjectMapperFactory.getObjectMapper().writeValueAsString(send.getValue2());
    }
}
