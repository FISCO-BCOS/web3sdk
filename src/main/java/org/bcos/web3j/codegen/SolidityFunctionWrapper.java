package org.bcos.web3j.codegen;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import javax.lang.model.element.Modifier;

import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.contract.source.AuthorityFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
//import com.sun.media.jfxmedia.logging.Logger;

import rx.functions.Func1;

import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.FunctionEncoder;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.DynamicArray;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.StaticArray;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.generated.AbiTypes;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.EncryptType;
import org.bcos.web3j.protocol.ObjectMapperFactory;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import org.bcos.web3j.utils.Collection;
import org.bcos.web3j.utils.Strings;
import org.bcos.web3j.utils.Version;

/**
 * Generate Java Classes based on generated Solidity bin and abi files.
 */
public class SolidityFunctionWrapper {

    private static final String BINARY = "BINARY";
    private static final String GUOMI_BINARY = "GUOMI_BINARY";
    private static final String ABI = "ABI";
    private static final String WEB3J = "web3j";
    private static final String CREDENTIALS = "credentials";
    private static final String TRANSACTION_MANAGER = "transactionManager";
    private static final String INITIAL_VALUE = "initialWeiValue";
    private static final String CONTRACT_ADDRESS = "contractAddress";
    private static final String CONTRACT_NAME = "contractName";
    private static final String GAS_PRICE = "gasPrice";
    private static final String GAS_LIMIT = "gasLimit";
    private static final String START_BLOCK = "startBlock";
    private static final String END_BLOCK = "endBlock";
    private static final String WEI_VALUE = "weiValue";
    private static final String IS_INITBYNAME = "isInitByName";
    
    private static final String CODEGEN_WARNING = "Auto generated code.<br>\n"
            + "<strong>Do not modify!</strong><br>\n"
            + "Please use the "
            + "<a href=\"https://docs.web3j.io/command_line.html\">web3j command line tools</a>, "
            + "or {@link " + SolidityFunctionWrapperGenerator.class.getName() + "} to update.\n";

    private static int encryptType = 0;
	// ======================
	/*
	 * add guomi compiler binary support
	 * 
	 * @ author: fisco-bcos-dev
	 */
	public void setEncryptType(int encryptType) {
		this.encryptType = encryptType;
	}

	private FieldSpec createGuomiBinaryDefinition(String guomi_binary) {
		return FieldSpec.builder(String.class, GUOMI_BINARY).addModifiers(Modifier.PRIVATE, Modifier.STATIC)
				.initializer("$S", guomi_binary).build();
	}

	private static MethodSpec buildSetBinary() {
		return MethodSpec.methodBuilder("setBinary").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(void.class)
				.addParameter(String.class, "binary").addStatement("BINARY = binary").build();
	}

	private static MethodSpec buildGetGuomiBinary() {
		return MethodSpec.methodBuilder("getGuomiBinary").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(String.class)
				.addStatement("return GUOMI_BINARY").build();
	}
	// ===========================
	public void generateJavaFiles(String contractName, String bin, String abi, String destinationDirLocation,
			String basePackageName, String guomiBinary) throws IOException, ClassNotFoundException {
		String className = Strings.capitaliseFirstLetter(contractName);
		TypeSpec.Builder classBuilder = createClassBuilder(className, bin, abi);
		if (classBuilder != null) {
			classBuilder.addField(createGuomiBinaryDefinition(guomiBinary)); // generate and set GUOMI_BINARY
			classBuilder.addMethod(buildSetBinary()); // generate method "setBinary"
			classBuilder.addMethod(buildGetGuomiBinary()); // generate method "getGuomiBinary"
			JavaFile javaFile = JavaFile.builder(basePackageName, classBuilder.build()).indent("    ")
					.skipJavaLangImports(true).build();
			javaFile.writeTo(new File(destinationDirLocation));
		} else {
			System.out.println("genearate java code failed, exit now");
			System.exit(1);
		}
	}

	public void generateJavaFiles(String contractName, String bin, String abi, String destinationDirLocation,
			String basePackageName) throws IOException, ClassNotFoundException {
		String className = Strings.capitaliseFirstLetter(contractName);
		TypeSpec.Builder classBuilder = createClassBuilder(className, bin, abi);
		if (classBuilder != null) {
			JavaFile javaFile = JavaFile.builder(basePackageName, classBuilder.build()).indent("    ")
					.skipJavaLangImports(true).build();
			javaFile.writeTo(new File(destinationDirLocation));
		} else {
			System.out.println("genearate java code failed, exit now");
			System.exit(1);
		}
	}
    

	private TypeSpec.Builder createClassBuilder(String className, String binary, String abi) {

		String javadoc = CODEGEN_WARNING ;
		try {
			TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addJavadoc(javadoc).superclass(Contract.class)
					.addField(createBinaryDefinition(binary)).addField(createABIDefinition(abi));
			classBuilder.addMethod(buildConstructor(Credentials.class, CREDENTIALS));
			classBuilder.addMethod(buildConstructor(TransactionManager.class, TRANSACTION_MANAGER));
			classBuilder.addMethod(buildConstructorWithDefaultParam(Credentials.class, CREDENTIALS));
			classBuilder.addMethod(buildConstructorWithDefaultParam(TransactionManager.class, TRANSACTION_MANAGER));

			classBuilder.addMethods(buildFunctionDefinitions(className, classBuilder, loadContractDefinition(abi)));
			classBuilder.addMethod(buildLoad(className, Credentials.class, CREDENTIALS));
			classBuilder.addMethod(buildLoad(className, TransactionManager.class, TRANSACTION_MANAGER));
			classBuilder.addMethod(buildLoadByName(className, Credentials.class, CREDENTIALS));
			classBuilder.addMethod(buildLoadByName(className, TransactionManager.class, TRANSACTION_MANAGER));
			return classBuilder;
		} catch (Exception e) {
			System.out.println("create class builder failed, error msg:" + e.getMessage());
			return null;
		}
	}



    private FieldSpec createBinaryDefinition(String binary) {
        return FieldSpec.builder(String.class, BINARY)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .initializer("$S", binary)
                .build();
    }
    

    private FieldSpec createABIDefinition(String abi) {
        return FieldSpec.builder(String.class, ABI)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer("$S", abi)
                .build();
    }

    private static List<MethodSpec> buildFunctionDefinitions(
            String className,
            TypeSpec.Builder classBuilder,
            List<AbiDefinition> functionDefinitions) throws ClassNotFoundException {

        List<MethodSpec> methodSpecs = new ArrayList<>();
        boolean constructor = false;

        for (AbiDefinition functionDefinition : functionDefinitions) {
            if (functionDefinition.getType().equals("function")) {
                methodSpecs.add(buildFunction(functionDefinition));
                //添加带有callback的方法
                if (!functionDefinition.isConstant()) {
                    methodSpecs.add(buildFunctionWithCallback(functionDefinition));
                }

            } else if (functionDefinition.getType().equals("event")) {
                buildEventFunctions(functionDefinition, classBuilder);

            } else if (functionDefinition.getType().equals("constructor")) {
                constructor = true;
                methodSpecs.add(buildDeploy(
                        className, functionDefinition, Credentials.class, CREDENTIALS));
                methodSpecs.add(buildDeploy(
                        className, functionDefinition, TransactionManager.class,
                        TRANSACTION_MANAGER));
            }
        }

        // constructor will not be specified in ABI file if its empty
        if (!constructor) {
            MethodSpec.Builder credentialsMethodBuilder =
                    getDeployMethodSpec(className, Credentials.class, CREDENTIALS);
            methodSpecs.add(buildDeployAsyncNoParams(
                    credentialsMethodBuilder, className, CREDENTIALS));

            MethodSpec.Builder transactionManagerMethodBuilder =
                    getDeployMethodSpec(className, TransactionManager.class, TRANSACTION_MANAGER);
            methodSpecs.add(buildDeployAsyncNoParams(
                    transactionManagerMethodBuilder, className, TRANSACTION_MANAGER));
        }
        return methodSpecs;
    }

	private static MethodSpec buildConstructor(Class authType, String authName) {
		if (encryptType == 1) {
			return MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE)
					.addParameter(String.class, CONTRACT_ADDRESS).addParameter(Web3j.class, WEB3J)
					.addParameter(authType, authName).addParameter(BigInteger.class, GAS_PRICE)
					.addParameter(BigInteger.class, GAS_LIMIT).addParameter(Boolean.class, IS_INITBYNAME)
					.addStatement("super($N, $N, $N, $N, $N, $N, $N)", BINARY, CONTRACT_ADDRESS, WEB3J, authName,
							GAS_PRICE, GAS_LIMIT, IS_INITBYNAME)
					.addStatement("if($T.encryptType == 1) super.setContractBinary(GUOMI_BINARY)", EncryptType.class)
					.build();
		} else {
			return MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE)
					.addParameter(String.class, CONTRACT_ADDRESS).addParameter(Web3j.class, WEB3J)
					.addParameter(authType, authName).addParameter(BigInteger.class, GAS_PRICE)
					.addParameter(BigInteger.class, GAS_LIMIT).addParameter(Boolean.class, IS_INITBYNAME)
					.addStatement("super($N, $N, $N, $N, $N, $N, $N)", BINARY, CONTRACT_ADDRESS, WEB3J, authName,
							GAS_PRICE, GAS_LIMIT, IS_INITBYNAME)
					.build();
		}
	}

	private static MethodSpec buildConstructorWithDefaultParam(Class authType, String authName) {
		if (encryptType == 1) {
			return MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE)
					.addParameter(String.class, CONTRACT_ADDRESS).addParameter(Web3j.class, WEB3J)
					.addParameter(authType, authName).addParameter(BigInteger.class, GAS_PRICE)
					.addParameter(BigInteger.class, GAS_LIMIT)
					.addStatement("super($N, $N, $N, $N, $N, $N, false)", BINARY, CONTRACT_ADDRESS, WEB3J, authName,
							GAS_PRICE, GAS_LIMIT)
					.addStatement("if($T.encryptType == 1) super.setContractBinary(GUOMI_BINARY)", EncryptType.class)
					.build();
		} else {
			return MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE)
					.addParameter(String.class, CONTRACT_ADDRESS).addParameter(Web3j.class, WEB3J)
					.addParameter(authType, authName).addParameter(BigInteger.class, GAS_PRICE)
					.addParameter(BigInteger.class, GAS_LIMIT).addStatement("super($N, $N, $N, $N, $N, $N, false)",
							BINARY, CONTRACT_ADDRESS, WEB3J, authName, GAS_PRICE, GAS_LIMIT)
					.build();
		}
	}

    private static MethodSpec buildDeploy(
            String className, AbiDefinition functionDefinition,
            Class authType, String authName) {

        MethodSpec.Builder methodBuilder = getDeployMethodSpec(className, authType, authName);
        String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());

        if (!inputParams.isEmpty()) {
            return buildDeployAsyncWithParams(methodBuilder, className, inputParams, authName);
        } else {
            return buildDeployAsyncNoParams(methodBuilder, className, authName);
        }
    }

	private static MethodSpec buildDeployAsyncWithParams(
            MethodSpec.Builder methodBuilder, String className, String inputParams,
            String authName) {
      
        methodBuilder.addStatement("$T encodedConstructor = $T.encodeConstructor("
                        + "$T.<$T>asList($L)"
                        + ")",
                String.class, FunctionEncoder.class, Arrays.class, Type.class, inputParams);
        if(encryptType ==1) {
        	methodBuilder.addStatement("if($T.encryptType == 1) setBinary(getGuomiBinary())", EncryptType.class).build();
        }
        methodBuilder.addStatement(
                "return deployAsync($L.class, $L, $L, $L, $L, $L, encodedConstructor, $L)",
                className, WEB3J, authName, GAS_PRICE, GAS_LIMIT, BINARY, INITIAL_VALUE);
        return methodBuilder.build();
    }

    private static MethodSpec buildDeployAsyncNoParams(
            MethodSpec.Builder methodBuilder, String className,
            String authName) {
    	  if(encryptType ==1) {
          	methodBuilder.addStatement("if($T.encryptType == 1) setBinary(getGuomiBinary())", EncryptType.class).build();
          }
        methodBuilder.addStatement("return deployAsync($L.class, $L, $L, $L, $L, $L, \"\", $L)",
                className, WEB3J, authName, GAS_PRICE, GAS_LIMIT, BINARY, INITIAL_VALUE);
        return methodBuilder.build();
    }

    private static MethodSpec.Builder getDeployMethodSpec(
            String className, Class authType, String authName) {
        return MethodSpec.methodBuilder("deploy")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(
                        ClassName.get(Future.class), TypeVariableName.get(className, Type.class)))
                .addParameter(Web3j.class, WEB3J)
                .addParameter(authType, authName)
                .addParameter(BigInteger.class, GAS_PRICE)
                .addParameter(BigInteger.class, GAS_LIMIT)
                .addParameter(BigInteger.class, INITIAL_VALUE);
    }


    private static MethodSpec buildLoad(
            String className, Class authType, String authName) {
        return MethodSpec.methodBuilder("load")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeVariableName.get(className, Type.class))
                .addParameter(String.class, CONTRACT_ADDRESS)
                .addParameter(Web3j.class, WEB3J)
                .addParameter(authType, authName)
                .addParameter(BigInteger.class, GAS_PRICE)
                .addParameter(BigInteger.class, GAS_LIMIT)
                .addStatement("return new $L($L, $L, $L, $L, $L, false)", className,
                        CONTRACT_ADDRESS, WEB3J, authName, GAS_PRICE, GAS_LIMIT)
                .build();
    }

    private static MethodSpec buildLoadByName(
            String className, Class authType, String authName) {
        return MethodSpec.methodBuilder("loadByName")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeVariableName.get(className, Type.class))
                .addParameter(String.class, CONTRACT_NAME)
                .addParameter(Web3j.class, WEB3J)
                .addParameter(authType, authName)
                .addParameter(BigInteger.class, GAS_PRICE)
                .addParameter(BigInteger.class, GAS_LIMIT)
                .addStatement("return new $L($L, $L, $L, $L, $L, true)", className,
                        CONTRACT_NAME, WEB3J, authName, GAS_PRICE, GAS_LIMIT)
                .build();
    }


    static String addParameters(
            MethodSpec.Builder methodBuilder, List<AbiDefinition.NamedType> namedTypes) {

        List<ParameterSpec> inputParameterTypes = buildParameterTypes(namedTypes);
        methodBuilder.addParameters(inputParameterTypes);

        return Collection.join(
                inputParameterTypes,
                ", ",
                parameterSpec -> parameterSpec.name);
    }

    static List<ParameterSpec> buildParameterTypes(List<AbiDefinition.NamedType> namedTypes) {
        List<ParameterSpec> result = new ArrayList<>(namedTypes.size());
        for (int i = 0; i < namedTypes.size(); i++) {
            AbiDefinition.NamedType namedType = namedTypes.get(i);

            String name = createValidParamName(namedType.getName(), i);
            String type = namedTypes.get(i).getType();

            result.add(ParameterSpec.builder(buildTypeName(type), name).build());
        }
        return result;
    }

    /**
     * Public Solidity arrays and maps require an unnamed input parameter - multiple if they
     * require a struct type.
     *
     * @param name parameter name
     * @param idx parameter index
     * @return non-empty parameter name
     */
    static String createValidParamName(String name, int idx) {
        if (name.equals("")) {
            return "param" + idx;
        } else {
            return name;
        }
    }

    static List<TypeName> buildTypeNames(List<AbiDefinition.NamedType> namedTypes) {
        List<TypeName> result = new ArrayList<>(namedTypes.size());
        for (AbiDefinition.NamedType namedType : namedTypes) {
            result.add(buildTypeName(namedType.getType()));
        }
        return result;
    }

    static MethodSpec buildFunction(
            AbiDefinition functionDefinition) throws ClassNotFoundException {
        String functionName = functionDefinition.getName();

        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder(functionName)
                        .addModifiers(Modifier.PUBLIC);

        String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());

        List<TypeName> outputParameterTypes = buildTypeNames(functionDefinition.getOutputs());
        if (functionDefinition.isConstant()) {
            buildConstantFunction(
                    functionDefinition, methodBuilder, outputParameterTypes, inputParams);
        } else {
            buildTransactionFunction(
                    functionDefinition, methodBuilder, inputParams);
        }

        return methodBuilder.build();
    }

    /**
     * @desc 创建带有callback的方法
     * @param functionDefinition
     * @return
     * @throws ClassNotFoundException
     */
    static MethodSpec buildFunctionWithCallback(AbiDefinition functionDefinition) throws ClassNotFoundException {
        String functionName = functionDefinition.getName();

        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder(functionName)
                        .addModifiers(Modifier.PUBLIC);

        String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());
        methodBuilder.addParameter(ParameterSpec.builder(buildTypeName("TransactionSucCallback"), "callback").build());
        List<TypeName> outputParameterTypes = buildTypeNames(functionDefinition.getOutputs());
        if (functionDefinition.isConstant()) {
            buildConstantFunction(
                    functionDefinition, methodBuilder, outputParameterTypes, inputParams);
        } else {
            buildTransactionFunctionWithCallback(functionDefinition, methodBuilder, inputParams);
        }

        return methodBuilder.build();
    }

    private static void buildConstantFunction(
            AbiDefinition functionDefinition,
            MethodSpec.Builder methodBuilder,
            List<TypeName> outputParameterTypes,
            String inputParams) throws ClassNotFoundException {

        String functionName = functionDefinition.getName();

        if (outputParameterTypes.isEmpty()) {
            throw new RuntimeException("Only transactional methods should have void return types");
        } else if (outputParameterTypes.size() == 1) {
            methodBuilder.returns(ParameterizedTypeName.get(
                    ClassName.get(Future.class), outputParameterTypes.get(0)));

            TypeName typeName = outputParameterTypes.get(0);
            methodBuilder.addStatement("$T function = "
                            + "new $T($S, \n$T.<$T>asList($L), "
                            + "\n$T.<$T<?>>asList(new $T<$T>() {}))",
                    Function.class, Function.class, functionName,
                    Arrays.class, Type.class, inputParams,
                    Arrays.class, TypeReference.class,
                    TypeReference.class, typeName);
            methodBuilder.addStatement("return executeCallSingleValueReturnAsync(function)");

        } else {
            methodBuilder.returns(ParameterizedTypeName.get(
                    ClassName.get(Future.class),
                    ParameterizedTypeName.get(
                            ClassName.get(List.class), ClassName.get(Type.class))));

            buildVariableLengthReturnFunctionConstructor(
                    methodBuilder, functionName, inputParams, outputParameterTypes);

            methodBuilder.addStatement("return executeCallMultipleValueReturnAsync(function)");
        }
    }

    private static void buildTransactionFunction(
            AbiDefinition functionDefinition,
            MethodSpec.Builder methodBuilder,
            String inputParams) throws ClassNotFoundException {

        if (functionDefinition.isPayable()) {
            methodBuilder.addParameter(BigInteger.class, WEI_VALUE);
        }

        String functionName = functionDefinition.getName();

        methodBuilder.returns(ParameterizedTypeName.get(Future.class, TransactionReceipt.class));

        methodBuilder.addStatement("$T function = new $T($S, $T.<$T>asList($L), $T"
                        + ".<$T<?>>emptyList())",
                Function.class, Function.class, functionName,
                Arrays.class, Type.class, inputParams, Collections.class,
                TypeReference.class);
        if (functionDefinition.isPayable()) {
            methodBuilder.addStatement("return executeTransactionAsync(function, $N)", WEI_VALUE);
        } else {
            methodBuilder.addStatement("return executeTransactionAsync(function)");
        }
    }

    private static void buildTransactionFunctionWithCallback(
            AbiDefinition functionDefinition,
            MethodSpec.Builder methodBuilder,
            String inputParams) throws ClassNotFoundException {

        String functionName = functionDefinition.getName();

        //methodBuilder.returns(ParameterizedTypeName.get(Future.class, TransactionReceipt.class));

        methodBuilder.addStatement("$T function = new $T($S, $T.<$T>asList($L), $T"
                        + ".<$T<?>>emptyList())",
                Function.class, Function.class, functionName,
                Arrays.class, Type.class, inputParams, Collections.class,
                TypeReference.class);
        methodBuilder.addStatement("executeTransactionAsync(function, callback)");
    }

    static TypeSpec buildEventResponseObject(String className,
                                             List<NamedTypeName> indexedParameters,
                                             List<NamedTypeName> nonIndexedParameters) {

        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);

        for (NamedTypeName namedType : indexedParameters) {
            builder.addField(namedType.getTypeName(), namedType.getName(), Modifier.PUBLIC);
        }

        for (NamedTypeName namedType : nonIndexedParameters) {
            builder.addField(namedType.getTypeName(), namedType.getName(), Modifier.PUBLIC);
        }

        return builder.build();
    }

    static MethodSpec buildEventObservableFunction(String responseClassName,
                                                   String functionName,
                                                   List<NamedTypeName> indexedParameters,
                                                   List<NamedTypeName> nonIndexedParameters)
            throws ClassNotFoundException {

        String generatedFunctionName =
                Strings.lowercaseFirstLetter(functionName) + "EventObservable";
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get(rx
                .Observable.class), ClassName.get("", responseClassName));

        MethodSpec.Builder observableMethodBuilder = MethodSpec.methodBuilder(generatedFunctionName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(DefaultBlockParameter.class, START_BLOCK)
                .addParameter(DefaultBlockParameter.class, END_BLOCK)
                .returns(parameterizedTypeName);

        buildVariableLengthEventConstructor(
                observableMethodBuilder, functionName, indexedParameters, nonIndexedParameters);

        TypeSpec converter = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(
                        ClassName.get(Func1.class),
                        ClassName.get(Log.class),
                        ClassName.get("", responseClassName)))
                .addMethod(MethodSpec.methodBuilder("call")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Log.class, "log")
                        .returns(ClassName.get("", responseClassName))
                        .addStatement("$T eventValues = extractEventParameters(event, log)",
                                EventValues.class)
                        .addStatement("$1T typedResponse = new $1T()",
                                ClassName.get("", responseClassName))
                        .addCode(buildTypedResponse("typedResponse", indexedParameters,
                                nonIndexedParameters))
                        .addStatement("return typedResponse")
                        .build())
                .build();

        observableMethodBuilder.addStatement("$1T filter = new $1T($2L, $3L, "
                + "getContractAddress())", EthFilter.class, START_BLOCK, END_BLOCK)
                .addStatement("filter.addSingleTopic($T.encode(event))", EventEncoder.class)
                .addStatement("return web3j.ethLogObservable(filter).map($L)", converter);

        return observableMethodBuilder
                .build();
    }

    static MethodSpec buildEventTransactionReceiptFunction(String responseClassName, String
            functionName, List<NamedTypeName> indexedParameters, List<NamedTypeName>
                                                                   nonIndexedParameters) throws
            ClassNotFoundException {

        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                ClassName.get(List.class), ClassName.get("", responseClassName));

        String generatedFunctionName = "get" + Strings.capitaliseFirstLetter(functionName)
                + "Events";
        MethodSpec.Builder transactionMethodBuilder = MethodSpec
                .methodBuilder(generatedFunctionName)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addParameter(TransactionReceipt.class, "transactionReceipt")
                .returns(parameterizedTypeName);

        buildVariableLengthEventConstructor(
                transactionMethodBuilder, functionName, indexedParameters, nonIndexedParameters);

        transactionMethodBuilder.addStatement("$T valueList = extractEventParameters(event, "
                + "transactionReceipt)", ParameterizedTypeName.get(List.class, EventValues.class))
                .addStatement("$1T responses = new $1T(valueList.size())",
                        ParameterizedTypeName.get(ClassName.get(ArrayList.class),
                                ClassName.get("", responseClassName)))
                .beginControlFlow("for ($T eventValues : valueList)", EventValues.class)
                .addStatement("$1T typedResponse = new $1T()",
                        ClassName.get("", responseClassName))
                .addCode(buildTypedResponse("typedResponse", indexedParameters,
                        nonIndexedParameters))
                .addStatement("responses.add(typedResponse)")
                .endControlFlow();


        transactionMethodBuilder.addStatement("return responses");
        return transactionMethodBuilder.build();
    }

    static void buildEventFunctions(
            AbiDefinition functionDefinition,
            TypeSpec.Builder classBuilder) throws ClassNotFoundException {

        String functionName = functionDefinition.getName();
        List<AbiDefinition.NamedType> inputs = functionDefinition.getInputs();
        String responseClassName = Strings.capitaliseFirstLetter(functionName) + "EventResponse";

        List<NamedTypeName> indexedParameters = new ArrayList<>();
        List<NamedTypeName> nonIndexedParameters = new ArrayList<>();
        for (AbiDefinition.NamedType namedType : inputs) {

            if (namedType.isIndexed()) {
                indexedParameters.add(
                        new NamedTypeName(namedType.getName(), buildTypeName(namedType.getType())));
            } else {
                nonIndexedParameters.add(
                        new NamedTypeName(namedType.getName(), buildTypeName(namedType.getType())));
            }
        }

        classBuilder.addType(buildEventResponseObject(responseClassName, indexedParameters,
                nonIndexedParameters));

        classBuilder.addMethod(buildEventTransactionReceiptFunction(responseClassName,
                functionName, indexedParameters, nonIndexedParameters));
        classBuilder.addMethod(buildEventObservableFunction(responseClassName, functionName,
                indexedParameters, nonIndexedParameters));
    }

    static CodeBlock buildTypedResponse(String objectName,
                                        List<NamedTypeName> indexedParameters,
                                        List<NamedTypeName> nonIndexedParameters) {
        CodeBlock.Builder builder = CodeBlock.builder();
        for (int i = 0; i < indexedParameters.size(); i++) {
            builder.addStatement("$L.$L = ($T) eventValues.getIndexedValues().get($L)",
                    objectName,
                    indexedParameters.get(i).getName(),
                    indexedParameters.get(i).getTypeName(),
                    i);
        }

        for (int i = 0; i < nonIndexedParameters.size(); i++) {
            builder.addStatement("$L.$L = ($T) eventValues.getNonIndexedValues().get($L)",
                    objectName,
                    nonIndexedParameters.get(i).getName(),
                    nonIndexedParameters.get(i).getTypeName(),
                    i);
        }
        return builder.build();
    }

    static TypeName buildTypeName(String typeDeclaration) {
        String type = trimStorageDeclaration(typeDeclaration);

        if (type.endsWith("]")) {
            String[] splitType = type.split("\\[");
            Class<?> baseType = AbiTypes.getType(splitType[0]);

            TypeName typeName;
            if (splitType[1].length() == 1) {
                typeName = ParameterizedTypeName.get(DynamicArray.class, baseType);
            } else {
                // Unfortunately we can't encode it's length as a type
                typeName = ParameterizedTypeName.get(StaticArray.class, baseType);
            }
            return typeName;
        } else {
            Class<?> cls = AbiTypes.getType(type);
            return ClassName.get(cls);
        }
    }

    private static String trimStorageDeclaration(String type) {
        if (type.endsWith(" storage") || type.endsWith(" memory")) {
            return type.split(" ")[0];
        } else {
            return type;
        }
    }

    private static void buildVariableLengthReturnFunctionConstructor(
            MethodSpec.Builder methodBuilder, String functionName, String inputParameters,
            List<TypeName> outputParameterTypes) throws ClassNotFoundException {

        List<Object> objects = new ArrayList<>();
        objects.add(Function.class);
        objects.add(Function.class);
        objects.add(functionName);

        objects.add(Arrays.class);
        objects.add(Type.class);
        objects.add(inputParameters);

        objects.add(Arrays.class);
        objects.add(TypeReference.class);
        for (TypeName outputParameterType : outputParameterTypes) {
            objects.add(TypeReference.class);
            objects.add(outputParameterType);
        }

        String asListParams = Collection.join(
                outputParameterTypes,
                ", ",
                typeName -> "new $T<$T>() {}");

        methodBuilder.addStatement("$T function = new $T($S, \n$T.<$T>asList($L), \n$T"
                + ".<$T<?>>asList("
                + asListParams + "))", objects.toArray());
    }

    private static void buildVariableLengthEventConstructor(
            MethodSpec.Builder methodBuilder, String eventName, List<NamedTypeName>
            indexedParameterTypes,
            List<NamedTypeName> nonIndexedParameterTypes) throws ClassNotFoundException {

        List<Object> objects = new ArrayList<>();
        objects.add(Event.class);
        objects.add(Event.class);
        objects.add(eventName);

        objects.add(Arrays.class);
        objects.add(TypeReference.class);
        for (NamedTypeName indexedParameterType : indexedParameterTypes) {
            objects.add(TypeReference.class);
            objects.add(indexedParameterType.getTypeName());
        }

        objects.add(Arrays.class);
        objects.add(TypeReference.class);
        for (NamedTypeName indexedParameterType : nonIndexedParameterTypes) {
            objects.add(TypeReference.class);
            objects.add(indexedParameterType.getTypeName());
        }

        String indexedAsListParams = Collection.join(
                indexedParameterTypes,
                ", ",
                typeName -> "new $T<$T>() {}");

        String nonIndexedAsListParams = Collection.join(
                nonIndexedParameterTypes,
                ", ",
                typeName -> "new $T<$T>() {}");

        methodBuilder.addStatement("final $T event = new $T($S, \n"
                + "$T.<$T<?>>asList(" + indexedAsListParams + "),\n"
                + "$T.<$T<?>>asList(" + nonIndexedAsListParams + "))", objects.toArray());
    }

    private List<AbiDefinition> loadContractDefinition(String abi) throws IOException {
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        AbiDefinition[] abiDefinition = objectMapper.readValue(abi, AbiDefinition[].class);
        return Arrays.asList(abiDefinition);
    }

    private static class NamedTypeName {
        private final TypeName typeName;
        private final String name;

        NamedTypeName(String name, TypeName typeName) {
            this.name = name;
            this.typeName = typeName;
        }

        public String getName() {
            return name;
        }

        public TypeName getTypeName() {
            return typeName;
        }
    }

}
