package org.bcos.web3j.codegen;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bcos.web3j.protocol.ObjectMapperFactory;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.bcos.web3j.utils.Files;
import org.bcos.web3j.utils.Strings;

import static org.bcos.web3j.utils.Collection.tail;
import static org.bcos.web3j.utils.Console.exitError;

/**
 * Java wrapper source code generator for Solidity ABI format.
 */
public class SolidityFunctionWrapperGenerator {

    private static final String USAGE = "solidity generate "
            + "<input binary file>.bin <input abi file>.abi "
            + "-p|--package <base package name> "
            + "-o|--output <destination base directory>";

    private String binaryFileLocation;
    private String guomiBinaryFileLocation;
    private String absFileLocation;
    private File destinationDirLocation;
    private String basePackageName;
    
    private SolidityFunctionWrapperGenerator(
            String binaryFileLocation,
            String absFileLocation,
            String destinationDirLocation,
            String basePackageName) {
    	this.binaryFileLocation = binaryFileLocation;
        this.absFileLocation = absFileLocation;
        this.destinationDirLocation = new File(destinationDirLocation);
        this.basePackageName = basePackageName;
        this.guomiBinaryFileLocation = "";
    }
   
    public void setGuomiBinaryFileLocation(String guomiBinaryFileLocation)
    {
    	this.guomiBinaryFileLocation = guomiBinaryFileLocation;
    }
    
    public static void run(String[] args) throws Exception {
        if (args.length < 1 || !args[0].equals("generate")) {
            exitError(USAGE);
        } else {
            main(tail(args));
        }
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 6) {
            exitError(USAGE);
        }

        String binaryFileLocation = parsePositionalArg(args, 0);
        String absFileLocation = parsePositionalArg(args, 1);
        String destinationDirLocation = parseParameterArgument(args, "-o", "--outputDir");
        String basePackageName = parseParameterArgument(args, "-p", "--package");
        
        String guomiBinaryFileLocation = parseParameterArgument(args, "-g", "--guomi-binary");

        if (binaryFileLocation.equals("")
                || absFileLocation.equals("")
                || destinationDirLocation.equals("")
                || basePackageName.equals("")) {
            exitError(USAGE);
        }

        SolidityFunctionWrapperGenerator solidityFunctionGenerator = 
        		new SolidityFunctionWrapperGenerator(
                binaryFileLocation,
                absFileLocation,
                destinationDirLocation,
                basePackageName);
        if(guomiBinaryFileLocation.equals("") == false)
        	solidityFunctionGenerator.setGuomiBinaryFileLocation(guomiBinaryFileLocation);
        
        //generate java code of solidity function
        solidityFunctionGenerator.generate();
    }

    private static String parsePositionalArg(String[] args, int idx) {
        if (args != null && args.length > idx) {
            return args[idx];
        } else {
            return "";
        }
    }

    private static String parseParameterArgument(String[] args, String... parameters) {
        for (String parameter : parameters) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals(parameter)
                        && i + 1 < args.length) {
                    String parameterValue = args[i + 1];
                    if (!parameterValue.startsWith("-")) {
                        return parameterValue;
                    }
                }
            }
        }
        return "";
    }
    
    private String readBinFile(String binaryFileLocation) throws IOException, ClassNotFoundException{
    	File binaryFile = new File(binaryFileLocation);
        if (!binaryFile.exists()) {
            exitError("Invalid input binary file specified: " + binaryFileLocation);
        }
        byte[] bytes = Files.readBytes(new File(binaryFile.toURI()));
        String binary = new String(bytes);
        return binary;
    }

    private void generate() throws IOException, ClassNotFoundException {
    	String binary = readBinFile(binaryFileLocation);
        File absFile = new File(absFileLocation);
        if (!absFile.exists() || !absFile.canRead()) {
            exitError("Invalid input ABI file specified: " + absFileLocation);
        }
        String fileName = absFile.getName();
        String contractName = getFileNameNoExtension(fileName);
        byte[] bytes = Files.readBytes(new File(absFile.toURI()));
        String abi = new String(bytes);

        List<AbiDefinition> functionDefinitions = loadContractDefinition(absFile);

        if (functionDefinitions.isEmpty()) {
            exitError("Unable to parse input ABI file");
        } else {
            String className = Strings.capitaliseFirstLetter(contractName);
            System.out.printf("Generating " + basePackageName + "." + className + " ... ");
            SolidityFunctionWrapper solidityFunctionWrapper = new SolidityFunctionWrapper();
			if (guomiBinaryFileLocation.equals("") == true)
			{	
				solidityFunctionWrapper.setEncryptType(0);
				solidityFunctionWrapper.generateJavaFiles(contractName, binary, abi,
						destinationDirLocation.toString(), basePackageName);
			}
			else
			{
				String guomiBinary = readBinFile(guomiBinaryFileLocation);
				solidityFunctionWrapper.setEncryptType(1);
				solidityFunctionWrapper.generateJavaFiles(contractName, binary, abi,
						destinationDirLocation.toString(), basePackageName, guomiBinary);
			}
            System.out.println("File written to " + destinationDirLocation.toString() + "\n");
        }
    }

    private static List<AbiDefinition> loadContractDefinition(File absFile)
            throws IOException {
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        AbiDefinition[] abiDefinition = objectMapper.readValue(absFile, AbiDefinition[].class);
        return Arrays.asList(abiDefinition);
    }

    static String getFileNameNoExtension(String fileName) {
        String[] splitName = fileName.split("\\.(?=[^.]*$)");
        return splitName[0];
    }
}