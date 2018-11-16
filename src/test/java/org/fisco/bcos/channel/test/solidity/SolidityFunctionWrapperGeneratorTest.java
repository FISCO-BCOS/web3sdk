package org.fisco.bcos.channel.test.solidity;

import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
    }

    @Test
    public void generateClassFromABIForGreeter() throws Exception {
        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "-a", abiFile ,
                "-b", binFile,
                "-p", packageName,
                "-o", tempDirPath
        ).toArray(new String[0]));
    }

    public SolidityFunctionWrapperGeneratorTest() throws IOException {
    }

}
