/*
 * Copyright (c) [2016] [ <ether.camp> ]
 * This file is part of the ethereumJ library.
 *
 * The ethereumJ library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ethereumJ library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ethereumJ library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fisco.bcos.web3j.solidity;

import static org.fisco.solc.compiler.SolidityCompiler.Options.ABI;
import static org.fisco.solc.compiler.SolidityCompiler.Options.BIN;
import static org.fisco.solc.compiler.SolidityCompiler.Options.INTERFACE;
import static org.fisco.solc.compiler.SolidityCompiler.Options.METADATA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.solc.compiler.CompilationResult;
import org.fisco.solc.compiler.SolidityCompiler;
import org.junit.Assert;
import org.junit.Test;

public class CompilerTest {

    @Test
    public void solc_getVersion_shouldWork() throws IOException {
        final String version = SolidityCompiler.runGetVersionOutput(false);

        // ##### May produce 2 lines:
        // solc, the solidity compiler commandline interface
        // Version: 0.4.7+commit.822622cf.mod.Darwin.appleclang
        System.out.println(version);

        assertThat(version, containsString("Version:"));
    }

    @Test
    public void sm_solc_getVersion_shouldWork() throws IOException {
        final String version = SolidityCompiler.runGetVersionOutput(true);

        // ##### May produce 2 lines:
        // solc, the solidity compiler commandline interface
        // Version: 0.4.7+commit.822622cf.mod.Darwin.appleclang
        System.out.println(version);

        assertThat(version, containsString("Gm version:"));
    }

    @Test
    public void simpleTest() throws IOException {
        String contract =
                ""
                        + "pragma solidity >=0.4.24 <0.6.10;\n"
                        + "pragma experimental ABIEncoderV2;\n"
                        + "contract TestContract\n"
                        + "{\n"
                        + "    event TestEventSimpleParams(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string _s,bytes _bs);\n"
                        + "\tevent TestEventDArrayParams(uint256[] _u,int256[] _i,bool[] _b,address[] _addr,bytes32[] _bs32, string[] _s,bytes[] _bs);\n"
                        + "\tevent TestEventSArrayParams(uint256[4] _u,int256[4] _i,bool[4] _b,address[4] _addr,bytes32[4] _bs32, string[4] _s,bytes[4] _bs);\n"
                        + "\n"
                        + "\tfunction test(uint256 _u,int256 _i,bool _b,address _addr,bytes32 _bs32, string memory _s,bytes memory _bs) public view returns (uint256,int256,bool,address,bytes32,string memory,bytes memory) {\n"
                        + "\n"
                        + "\t}\n"
                        + "\n"
                        + "\tfunction test(uint256[] memory _u,int256[] memory _i,bool[] memory _b,address[] memory _addr,bytes32[] memory _bs32,string[] memory _s,bytes[] memory _bs) public view returns (uint256[] memory,int256[] memory,bool[] memory, address[] memory,bytes32[] memory,string[] memory,bytes[] memory ) {\n"
                        + "\n"
                        + "\t}\n"
                        + "\n"
                        + "\tfunction test(uint256[4] memory _u,int256[4] memory _i,bool[4] memory _b,address[4]memory _addr,bytes32[4] memory _bs32,string[4] memory _s,bytes[4] memory _bs) public view returns (uint256[2] memory ,int256[2] memory ,bool[2] memory ,address[2] memory ,bytes32[2] memory ,string[2] memory ,bytes[2] memory) {\n"
                        + "\n"
                        + "    }\n"
                        + "}";

        SolidityCompiler.Result res =
                SolidityCompiler.compile(
                        contract.getBytes(), false, true, ABI, BIN, INTERFACE, METADATA);
        Assert.assertTrue(!res.isFailed() && !res.getOutput().isEmpty());
        CompilationResult.ContractMetadata meta =
                CompilationResult.parse(res.getOutput()).getContract("TestContract");
        Assert.assertTrue(!meta.abi.isEmpty());
        Assert.assertTrue(!meta.bin.isEmpty());

        SolidityCompiler.Result smRes =
                SolidityCompiler.compile(
                        contract.getBytes(), true, true, ABI, BIN, INTERFACE, METADATA);
        Assert.assertTrue(!smRes.isFailed() && !smRes.getOutput().isEmpty());
        CompilationResult.ContractMetadata smMeta =
                CompilationResult.parse(smRes.getOutput()).getContract("TestContract");
        Assert.assertTrue(!smMeta.abi.isEmpty());
        Assert.assertTrue(!smMeta.bin.isEmpty());

        Assert.assertTrue(!meta.bin.equals(smMeta.bin));
    }

    @Test
    public void defaultFuncTest() throws IOException {
        String contractSrc =
                "pragma solidity >=0.4.24 <0.6.10;\n"
                        + "\n"
                        + "contract a {\n"
                        + "    function() \n"
                        + "    {\n"
                        + "        throw;\n"
                        + "        \n"
                        + "    }\n"
                        + "}\n";

        SolidityCompiler.Result res =
                SolidityCompiler.compile(contractSrc.getBytes(), false, true, BIN);
        CompilationResult result = CompilationResult.parse(res.getOutput());

        CompilationResult.ContractMetadata a = result.getContract("a");

        // Assert.assertTrue(!a.abi.isEmpty());
        Assert.assertTrue(!a.bin.isEmpty());

        SolidityCompiler.Result smRes =
                SolidityCompiler.compile(contractSrc.getBytes(), true, true, BIN);
        CompilationResult smResult = CompilationResult.parse(smRes.getOutput());

        CompilationResult.ContractMetadata smA = smResult.getContract("a");

        // Assert.assertTrue(!smA.abi.isEmpty());
        Assert.assertTrue(!smA.bin.isEmpty());

        Assert.assertTrue(!a.bin.equals(smA.bin));
    }

    @Test
    public void compileFilesTest() throws IOException {

        File solFileList = new File("src/test/resources/contract");
        File[] solFiles = solFileList.listFiles();

        for (File solFile : solFiles) {
            if (!solFile.getName().endsWith(".sol") || solFile.getName().contains("Lib")) {
                continue;
            }
            SolidityCompiler.Result res =
                    SolidityCompiler.compile(solFile, false, true, ABI, BIN, INTERFACE, METADATA);
            CompilationResult result = CompilationResult.parse(res.getOutput());
            System.out.println("contractname  " + solFile.getName());
            Path source = Paths.get(solFile.getPath());
            String contractname = solFile.getName().split("\\.")[0];
            CompilationResult.ContractMetadata a =
                    result.getContract(source, solFile.getName().split("\\.")[0]);
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
                                    "-a",
                                    abiFile,
                                    "-b",
                                    binFile,
                                    "-p",
                                    packageName,
                                    "-o",
                                    tempDirPath)
                            .toArray(new String[0]));
        }
    }

    @Test
    public void compileFilesWithImportTest() throws IOException {

        Path source = Paths.get("src", "test", "resources", "contract", "test2.sol");

        SolidityCompiler.Result res =
                SolidityCompiler.compile(
                        source.toFile(), false, true, ABI, BIN, INTERFACE, METADATA);
        CompilationResult result = CompilationResult.parse(res.getOutput());

        CompilationResult.ContractMetadata a = result.getContract(source, "test2");
    }

    @Test
    public void compileFilesWithImportFromParentFileTest() throws IOException {

        Path source = Paths.get("src", "test", "resources", "contract", "test3.sol");

        SolidityCompiler.Option allowPathsOption =
                new SolidityCompiler.Options.AllowPaths(
                        Collections.singletonList(source.getParent().getParent().toFile()));
        SolidityCompiler.Result res =
                SolidityCompiler.compile(
                        source.toFile(),
                        false,
                        true,
                        ABI,
                        BIN,
                        INTERFACE,
                        METADATA,
                        allowPathsOption);
        CompilationResult result = CompilationResult.parse(res.getOutput());

        Assert.assertEquals(2, result.getContractKeys().size());
        Assert.assertEquals(result.getContract("test3"), result.getContract(source, "test3"));
        Assert.assertNotNull(result.getContract("test1"));

        CompilationResult.ContractMetadata a = result.getContract(source, "test3");
    }

    @Test
    public void compileFilesWithImportFromParentStringTest() throws IOException {

        Path source = Paths.get("src", "test", "resources", "contract", "test3.sol");

        SolidityCompiler.Option allowPathsOption =
                new SolidityCompiler.Options.AllowPaths(
                        Collections.singletonList(
                                source.getParent().getParent().toAbsolutePath().toString()));
        SolidityCompiler.Result res =
                SolidityCompiler.compile(
                        source.toFile(),
                        false,
                        true,
                        ABI,
                        BIN,
                        INTERFACE,
                        METADATA,
                        allowPathsOption);
        CompilationResult result = CompilationResult.parse(res.getOutput());

        CompilationResult.ContractMetadata a = result.getContract(source, "test3");
    }

    @Test
    public void compileFilesWithImportFromParentPathTest() throws IOException {

        Path source = Paths.get("src", "test", "resources", "contract", "test3.sol");

        SolidityCompiler.Option allowPathsOption =
                new SolidityCompiler.Options.AllowPaths(
                        Collections.singletonList(source.getParent().getParent()));
        SolidityCompiler.Result res =
                SolidityCompiler.compile(
                        source.toFile(),
                        false,
                        true,
                        ABI,
                        BIN,
                        INTERFACE,
                        METADATA,
                        allowPathsOption);
        CompilationResult result = CompilationResult.parse(res.getOutput());

        CompilationResult.ContractMetadata a = result.getContract("test3");
    }
}
