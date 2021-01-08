package org.fisco.bcos.web3j.precompile.gaschargemgr;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionResponse;

public class GasChargeManageService {
    private final Web3j web3j;
    private final GasChargeManagePrecompiled gasChargeManagePrecompiled;
    private static String GasChargeManagePrecompileAddress =
            "0x0000000000000000000000000000000000001009";
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");

    public GasChargeManageService(Web3j web3j, Credentials credentials) {
        this.web3j = web3j;
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        this.gasChargeManagePrecompiled =
                GasChargeManagePrecompiled.load(
                        GasChargeManagePrecompileAddress, web3j, credentials, contractGasProvider);
    }

    public TransactionResponse decodeReceipt(
            TransactionReceipt receipt, String functionName, String errorMessage) throws Exception {
        TransactionResponse transactionResponse =
                receipt.parseReceipt(gasChargeManagePrecompiled.ABI, functionName);
        if (transactionResponse.getReturnABIObject() == null
                || transactionResponse.getReturnObject().isEmpty()) {
            return transactionResponse;
        }
        List<Object> returnObject = transactionResponse.getReturnObject();
        BigInteger returnCode = (BigInteger) (returnObject.get(0));
        transactionResponse.setReturnCode(returnCode.intValue());
        if (returnCode.intValue() == PrecompiledCommon.Success) {
            return transactionResponse;
        }
        String returnMessage = PrecompiledCommon.transferToJson(returnCode.intValue());
        transactionResponse.setReturnMessage(returnMessage);
        return transactionResponse;
    }

    public TransactionResponse charge(String userAccount, BigInteger gasValue) throws Exception {
        return decodeReceipt(
                gasChargeManagePrecompiled.charge(userAccount, gasValue).send(),
                gasChargeManagePrecompiled.FUNC_CHARGE,
                "GasChargeManageService: failed to call charge");
    }

    public TransactionResponse deduct(String userAccount, BigInteger gasValue) throws Exception {
        return decodeReceipt(
                gasChargeManagePrecompiled.deduct(userAccount, gasValue).send(),
                gasChargeManagePrecompiled.FUNC_DEDUCT,
                "GasChargeManageService: failed to call deduct");
    }

    public BigInteger queryRemainGas(String userAccount) throws Exception {
        Tuple2<BigInteger, BigInteger> result =
                gasChargeManagePrecompiled.queryRemainGas(userAccount).send();
        if (result.getValue1().intValue() != PrecompiledCommon.Success) {
            String errorMessage = PrecompiledCommon.transferToJson(result.getValue1().intValue());
            throw new TransactionException(errorMessage, result.getValue1().intValue());
        }
        return result.getValue2();
    }

    public String grantCharger(String chargerAccount) throws Exception {
        return PrecompiledCommon.handleTransactionReceipt(
                gasChargeManagePrecompiled.grantCharger(chargerAccount).send(), web3j);
    }

    public String revokeCharger(String chargerAccount) throws Exception {
        return PrecompiledCommon.handleTransactionReceipt(
                gasChargeManagePrecompiled.revokeCharger(chargerAccount).send(), web3j);
    }

    public List<String> listChargers() throws Exception {
        return gasChargeManagePrecompiled.listChargers().send();
    }
}
