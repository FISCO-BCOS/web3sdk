package org.fisco.bcos.web3j.utils;

import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.ABI;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.BIN;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.INTERFACE;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.METADATA;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.bcos.web3j.solidity.compiler.CompilationResult;
import org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler;

public class CompileSolToJava {

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("Please provide a package name.");
      return;
    }

    File solFileList = new File("contracts");
    File[] solFiles = solFileList.listFiles();
    if(solFiles.length == 0)
    {
    	System.out.println("The contracts directory is empty.");
    	return;
    }
    for (File solFile : solFiles) {
      if(!solFile.getName().endsWith(".sol"))
			{
				continue;
			}
      SolidityCompiler.Result res =
          SolidityCompiler.compile(solFile, true, ABI, BIN, INTERFACE, METADATA);
      if("".equals(res.output))
      {
      	System.out.println("Compile error: " + res.errors);
      	return;
      }
      CompilationResult result = CompilationResult.parse(res.output);
      String contractname = solFile.getName().split("\\.")[0];
      CompilationResult.ContractMetadata a = result.getContract(solFile.getName().split("\\.")[0]);
      FileUtils.writeStringToFile(new File("abi/" + contractname + ".abi"), a.abi);
      FileUtils.writeStringToFile(new File("bin/" + contractname + ".bin"), a.bin);
      String binFile;
      String abiFile;
      String tempDirPath = new File("java").getAbsolutePath();
      String packageName = args[0];
      String filename = contractname;
      abiFile = "abi/" + filename + ".abi";
      binFile = "bin/" + filename + ".bin";
      SolidityFunctionWrapperGenerator.main(
          Arrays.asList(
                  "-a", abiFile,
                  "-b", binFile,
                  "-p", packageName,
                  "-o", tempDirPath)
              .toArray(new String[0]));
    }
    System.out.println("\nCompile solidity contract files to java contract files successfully!");
  }
}
