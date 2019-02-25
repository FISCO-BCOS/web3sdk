package org.fisco.bcos.web3j.utils;

import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.ABI;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.BIN;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.INTERFACE;
import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.METADATA;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.bcos.web3j.solidity.compiler.CompilationResult;
import org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler;

public class SolToJavaUtil {

  public static void convert() throws IOException {
    File solFileList = new File("src/test/resources/contract");
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
      CompilationResult.ContractMetadata a =
          result.getContract(source, solFile.getName().split("\\.")[0]);
      System.out.println("abi   " + a.abi);
      System.out.println("bin   " + a.bin);
      FileUtils.writeStringToFile(
          new File("src/test/resources/solidity/" + contractname + ".abi"), a.abi);
      FileUtils.writeStringToFile(
          new File("src/test/resources/solidity/" + contractname + ".bin"), a.bin);
      String binFile;
      String abiFile;
      String tempDirPath = new File("src/test/java/").getAbsolutePath();
      String packageName = "org.fisco.bcos.temp";
      String filename = contractname;
      abiFile = "src/test/resources/solidity/" + filename + ".abi";
      binFile = "src/test/resources/solidity/" + filename + ".bin";
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

  public static void convert(String solDir, String abiAndBinDir, String tempDir, String packageName)
      throws IOException {
    File solFileList = new File(solDir);
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
      CompilationResult.ContractMetadata a =
          result.getContract(source, solFile.getName().split("\\.")[0]);
      System.out.println("abi   " + a.abi);
      System.out.println("bin   " + a.bin);
      FileUtils.writeStringToFile(new File(abiAndBinDir + contractname + ".abi"), a.abi);
      FileUtils.writeStringToFile(new File(abiAndBinDir + contractname + ".bin"), a.bin);
      String binFile;
      String abiFile;
      String tempDirPath = new File(tempDir).getAbsolutePath();
      String filename = contractname;
      abiFile = "src/test/resources/solidity/" + filename + ".abi";
      binFile = "src/test/resources/solidity/" + filename + ".bin";
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
