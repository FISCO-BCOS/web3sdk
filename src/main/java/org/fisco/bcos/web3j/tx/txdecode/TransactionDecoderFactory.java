package org.fisco.bcos.web3j.tx.txdecode;

import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.ABI;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.BIN;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.INTERFACE;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.METADATA;

import java.io.File;
import java.io.IOException;
import org.fisco.bcos.web3j.solidity.compiler.CompilationResult;
import org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler;

public class TransactionDecoderFactory {

    public static String SOLIDITY_PATH = "solidity";
    public static String SOL_POSTFIX = ".sol";
    public static String PREFIX_LIB = "Lib";

    /**
     * @param abi
     * @param bin
     * @return TransactionDecoder
     */
    public static TransactionDecoder buildTransactionDecoder(String abi, String bin) {
        return new TransactionDecoder(abi, bin);
    }

    /**
     * @param contractName
     * @return TransactionDecoder
     * @throws IOException
     */
    public static TransactionDecoder buildTransactionDecoder(String contractName)
            throws IOException {
        if (contractName.startsWith(PREFIX_LIB)) {
            throw new IOException("Please don't provide a library solidity file.");
        }
        if (!contractName.endsWith(SOL_POSTFIX)) {
            contractName = contractName + SOL_POSTFIX;
        }
        File solFileList = new File(SOLIDITY_PATH);
        if (!solFileList.exists()) {
            throw new IOException("Please checkout the directory " + SOLIDITY_PATH + " is exist.");
        }
        File destSol = new File(SOLIDITY_PATH + File.separator + contractName);
        if (!destSol.exists()) {
            throw new IOException(
                    "There is no " + contractName + " in the directory of " + SOLIDITY_PATH);
        }
        contractName = contractName.substring(0, contractName.length() - SOL_POSTFIX.length());
        return compileContract(contractName, solFileList);
    }

    private static TransactionDecoder compileContract(String contractName, File solFileList)
            throws IOException, CompileSolidityException {
        File[] solFiles = solFileList.listFiles();
        String abi = "";
        String bin = "";
        for (File solFile : solFiles) {
            if (!solFile.getName().endsWith(SOL_POSTFIX)
                    || solFile.getName().startsWith(PREFIX_LIB)) {
                continue;
            }
            SolidityCompiler.Result res =
                    SolidityCompiler.compile(solFile, true, ABI, BIN, INTERFACE, METADATA);
            if ("".equals(res.output)) {
                throw new CompileSolidityException("Compile error: " + res.errors);
            }
            CompilationResult result = CompilationResult.parse(res.output);
            String name = solFile.getName().split("\\.")[0];
            CompilationResult.ContractMetadata contractMetadata = result.getContract(name);
            if (contractName.equals(name)) {
                abi = contractMetadata.abi;
                bin = contractMetadata.bin;
                break;
            }
        }
        return new TransactionDecoder(abi, bin);
    }
}
