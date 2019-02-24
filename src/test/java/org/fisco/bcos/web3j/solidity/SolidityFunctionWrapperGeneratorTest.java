package org.fisco.bcos.web3j.solidity;

import static org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler.Options.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.bcos.web3j.solidity.compiler.CompilationResult;
import org.fisco.bcos.web3j.solidity.compiler.SolidityCompiler;
import org.junit.Test;

public class SolidityFunctionWrapperGeneratorTest {

  protected String tempDirPath = new File("src/test/java/").getAbsolutePath();
  protected String packageName = "org.fisco.bcos.temp";

  @Test
  public void generateClassFromABIAndBIN() throws Exception {
    File fileList = new File("src/test/resources/solidity");
    File[] files = fileList.listFiles();
    String abiFile = "";
    String binFile = "";
    for (File file : files) {
      String filename = file.getName();
      String commonName = filename.split("\\.")[0];
      abiFile = "src/test/resources/solidity/" + commonName + ".abi";
      binFile = "src/test/resources/solidity/" + commonName + ".bin";
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

  public SolidityFunctionWrapperGeneratorTest() throws IOException {}

  @Test
  public void compileSolFilesToJavaTest() throws IOException {
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
      CompilationResult.ContractMetadata a = result.getContract(solFile.getName().split("\\.")[0]);
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
}
