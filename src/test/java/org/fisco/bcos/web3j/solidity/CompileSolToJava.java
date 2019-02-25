package org.fisco.bcos.web3j.solidity;

import org.apache.commons.io.FileUtils;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.bcos.web3j.solidity.compiler.CompilationResult;
import org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.*;

public class CompileSolToJava {

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("please input the package");
      return;
    }

    File solFileList = new File("solidity/contracts");
    File[] solFiles = solFileList.listFiles();

    for (File solFile : solFiles) {

      SolidityCompiler.Result res =
              SolidityCompiler.compile(solFile, true, ABI, BIN, INTERFACE, METADATA);
      System.out.println("Out: '" + res.output + "'");
      System.out.println("Err: '" + res.errors + "'");
      CompilationResult result = CompilationResult.parse(res.output);
      System.out.println("contractname  " + solFile.getName());
      Path source = Paths.get(solFile.getPath());
      String contractname = solFile.getName().split("\\.")[0];
      CompilationResult.ContractMetadata a = result.getContract(solFile.getName().split("\\.")[0]);
      System.out.println("abi   " + a.abi);
      System.out.println("bin   " + a.bin);
      FileUtils.writeStringToFile(
              new File("solidity/abi/" + contractname + ".abi"), a.abi);
      FileUtils.writeStringToFile(
              new File("solidity/bin/" + contractname + ".bin"), a.bin);
      String binFile;
      String abiFile;
      String tempDirPath = new File("solidity/java").getAbsolutePath();
      String packageName = args[0];
      String filename = contractname;
      abiFile = "solidity/abi/" + filename + ".abi";
      binFile = "solidity/bin/" + filename + ".bin";
      SolidityFunctionWrapperGenerator.main(
              Arrays.asList(
                      "-a", abiFile,
                      "-b", binFile,
                      "-p", packageName,
                      "-o", tempDirPath)
                      .toArray(new String[0]));
    }
    System.out.println("generate successfully");
  }


}
