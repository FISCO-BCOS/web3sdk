package org.fisco.bcos.channel.test.solidity;

import org.apache.commons.io.FileUtils;
import org.fisco.bcos.channel.test.solidity.compiler.CompilationResult;
import org.fisco.bcos.channel.test.solidity.compiler.SolidityCompiler;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.fisco.bcos.channel.test.solidity.compiler.SolidityCompiler.Options.*;

public class SolidityFunctionWrapperGeneratorTest {

    private String binFile =  new File("src/test/resources/solidity/Ok.bin").getAbsolutePath();
    private String abiFile =  new File("src/test/resources/solidity/Ok.abi").getAbsolutePath();
//     private String binFile =  new ClassPathResource("solidity/Ok.bin").getPath();
//     private String abiFile =  new ClassPathResource("solidity/Ok.abi").getPath();
    protected String tempDirPath =  new File("src/test/java/").getAbsolutePath();
    protected String packageName =  "org.fisco.bcos.temp";


    @Test
    public void generateClassFromABIForHelloWorld() throws Exception {

        String binFile1 =  new File("src/test/resources/solidity/HelloWorld.bin").getAbsolutePath();
        String abiFile1 =  new File("src/test/resources/solidity/HelloWorld.abi").getAbsolutePath();
        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "-a",  abiFile1,
                "-b",  binFile1,
                "-p", packageName,
                "-o", tempDirPath
        ).toArray(new String[0]));
        System.out.println("generate successfully");
    }

    @Test
    public void generateClassFromABIAndBIN() throws Exception {
    File  fileList = new File("src/test/resources/solidity");
    File[] files = fileList.listFiles();
    for(File file : files) {
        String filename = file.getName();
        String commonName = filename.split("\\.")[0];
        abiFile = "src/test/resources/solidity/"+commonName+ ".abi";
        binFile =  "src/test/resources/solidity/"+commonName+".bin";
        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "-a", abiFile,
                "-b", binFile,
                "-p", packageName,
                "-o", tempDirPath
        ).toArray(new String[0]));
    }
        System.out.println("generate successfully");
    }

    public SolidityFunctionWrapperGeneratorTest() throws IOException {
    }

    @Test
    public void compileSolFilesToJavaTest() throws IOException {

        File solFileList = new File("src/test/resources/contract");
        File[] solFiles = solFileList.listFiles();

        for (File solFile : solFiles) {

            SolidityCompiler.Result res = SolidityCompiler.compile(solFile, true, ABI, BIN, INTERFACE, METADATA);
            System.out.println("Out: '" + res.output + "'");
            System.out.println("Err: '" + res.errors + "'");
            CompilationResult result = CompilationResult.parse(res.output);
            //   Assert.assertEquals(solFile.getName(), result.getContractName()+".sol");
            //     Assert.assertEquals(solFile.getAbsolutePath(), result.getContractPath());
            System.out.println("contractname  " + solFile.getName());
            Path source = Paths.get(solFile.getPath());
            // todo
            String contractname = solFile.getName().split("\\.")[0];
            CompilationResult.ContractMetadata a = result.getContract(source, solFile.getName().split("\\.")[0]);
            System.out.println("abi   " + a.abi);
            System.out.println("bin   " + a.bin);
            FileUtils.writeStringToFile(new File("src/test/resources/solidity/" + contractname + ".abi"), a.abi);
            FileUtils.writeStringToFile(new File("src/test/resources/solidity/" + contractname + ".bin"), a.bin);
            String binFile;
            String abiFile;
            String tempDirPath = new File("src/test/java/").getAbsolutePath();
            String packageName = "org.fisco.bcos.temp";
            String filename = contractname;
            abiFile = "src/test/resources/solidity/" + filename + ".abi";
            binFile = "src/test/resources/solidity/" + filename + ".bin";
            SolidityFunctionWrapperGenerator.main(Arrays.asList(
                    "-a", abiFile,
                    "-b", binFile,
                    "-p", packageName,
                    "-o", tempDirPath
            ).toArray(new String[0]));
        }
        System.out.println("generate successfully");
    }
}
