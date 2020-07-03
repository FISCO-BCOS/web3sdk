package org.fisco.bcos.web3j.utils;

import static org.fisco.solc.compiler.SolidityCompiler.Options.ABI;
import static org.fisco.solc.compiler.SolidityCompiler.Options.BIN;
import static org.fisco.solc.compiler.SolidityCompiler.Options.INTERFACE;
import static org.fisco.solc.compiler.SolidityCompiler.Options.METADATA;

import java.io.File;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.solc.compiler.CompilationResult;
import org.fisco.solc.compiler.SolidityCompiler;

public class CompileSolToJava {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Please provide a package name.");
            return;
        }

        File solFileList = new File("contracts");
        File[] solFiles = solFileList.listFiles();
        if (solFiles.length == 0) {
            System.out.println("The contracts directory is empty.");
            return;
        }
        for (File solFile : solFiles) {
            if (!solFile.getName().endsWith(".sol") || solFile.getName().contains("Lib")) {
                continue;
            }
            SolidityCompiler.Result res =
                    SolidityCompiler.compile(solFile, false, true, ABI, BIN, INTERFACE, METADATA);
            if ("".equals(res.getOutput())) {
                System.out.println("Compile error: " + res.getErrors());
                return;
            }

            /** sm compile */
            SolidityCompiler.Result smRes =
                    SolidityCompiler.compile(solFile, true, true, ABI, BIN, INTERFACE, METADATA);
            if (smRes.isFailed() || "".equals(smRes.getOutput())) {
                System.out.println("Compile SM error: " + res.getErrors());
                return;
            }

            CompilationResult result = CompilationResult.parse(res.getOutput());
            CompilationResult smResult = CompilationResult.parse(smRes.getOutput());

            String contractname = solFile.getName().split("\\.")[0];
            CompilationResult.ContractMetadata meta =
                    result.getContract(solFile.getName().split("\\.")[0]);

            CompilationResult.ContractMetadata smMeta =
                    smResult.getContract(solFile.getName().split("\\.")[0]);

            FileUtils.writeStringToFile(new File("abi/" + contractname + ".abi"), meta.abi);
            FileUtils.writeStringToFile(new File("bin/" + contractname + ".bin"), meta.bin);

            FileUtils.writeStringToFile(
                    new File("abi/" + "/sm/" + contractname + ".abi"), smMeta.abi);
            FileUtils.writeStringToFile(
                    new File("bin/" + "/sm/" + contractname + ".bin"), smMeta.bin);

            String binFile;
            String smBinFile;
            String abiFile;
            String tempDirPath = new File("java").getAbsolutePath();
            String packageName = args[0];
            String filename = contractname;
            abiFile = "abi/" + filename + ".abi";
            binFile = "bin/" + filename + ".bin";
            smBinFile = "sm" + "/bin" + filename + ".bin";
            SolidityFunctionWrapperGenerator.main(
                    Arrays.asList(
                                    "-a", abiFile,
                                    "-b", binFile,
                                    "-s", smBinFile,
                                    "-p", packageName,
                                    "-o", tempDirPath)
                            .toArray(new String[0]));
        }
        System.out.println(
                "\nCompile solidity contract files to java contract files successfully!");
    }
}
