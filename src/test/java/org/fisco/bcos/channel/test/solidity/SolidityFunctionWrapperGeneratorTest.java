package org.fisco.bcos.channel.test.solidity;

import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SolidityFunctionWrapperGeneratorTest {

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
    public void generateClassFromABIForGreeter() throws Exception {
        File  fileList = new File("src/test/resources/solidity");
        File[] files = fileList.listFiles();
        String abiFile = "";
        String binFile = "";
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

}
