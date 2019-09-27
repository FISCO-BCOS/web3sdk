package org.fisco.bcos.web3j.codegen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.AbiTypes;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.fisco.bcos.web3j.utils.Collection;
import org.fisco.bcos.web3j.utils.Strings;
import org.fisco.bcos.web3j.utils.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import com.sun.media.jfxmedia.logging.Logger;

/** Generate Java Classes based on generated Solidity bin and abi files. */
public class SolidityFunctionWrapper extends Generator {
    private static final int maxSolidityBinSize = 0x40000;
    private static final String BINARY = "BINARY";
    private static final String WEB3J = "web3j";
    private static final String CREDENTIALS = "credentials";
    private static final String CONTRACT_GAS_PROVIDER = "contractGasProvider";
    private static final String TRANSACTION_MANAGER = "transactionManager";
    private static final String TRANSACTION_DECODER = "transactionDecoder";
    private static final String INITIAL_VALUE = "initialWeiValue";
    private static final String CONTRACT_ADDRESS = "contractAddress";
    private static final String GAS_PRICE = "gasPrice";
    private static final String GAS_LIMIT = "gasLimit";
    private static final String FILTER = "filter";
    private static final String FROM_BLOCK = "fromBlock";
    private static final String TO_BLOCK = "toBlock";
    private static final String WEI_VALUE = "weiValue";
    private static final String CALLBACK_VALUE = "callback";
    private static final String OTHER_TOPICS = "otherTopcs";
    private static final String FUNC_NAME_PREFIX = "FUNC_";
    private String abiContent;
    private static final ClassName LOG = ClassName.get(Log.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(SolidityFunctionWrapper.class);

    private static final String CODEGEN_WARNING =
            "<p>Auto generated code.\n"
                    + "<p><strong>Do not modify!</strong>\n"
                    + "<p>Please use the "
                    + "<a href=\"https://docs.web3j.io/command_line.html\">web3j command line tools</a>,\n"
                    + "or the "
                    + SolidityFunctionWrapperGenerator.class.getName()
                    + " in the \n"
                    + "<a href=\"https://github.com/web3j/web3j/tree/master/codegen\">"
                    + "codegen module</a> to update.\n";
    private final boolean useNativeJavaTypes;
    private static final String regex = "(\\w+)(?:\\[(.*?)\\])(?:\\[(.*?)\\])?";
    private static final Pattern pattern = Pattern.compile(regex);
    private final GenerationReporter reporter;

    public SolidityFunctionWrapper(boolean useNativeJavaTypes) {
        this(useNativeJavaTypes, new LogGenerationReporter(LOGGER));
    }

    SolidityFunctionWrapper(boolean useNativeJavaTypes, GenerationReporter reporter) {
        this.useNativeJavaTypes = useNativeJavaTypes;
        this.reporter = reporter;
    }

    @SuppressWarnings("unchecked")
    public void generateJavaFiles(
            String contractName,
            String bin,
            String abi,
            String destinationDir,
            String basePackageName)
            throws IOException, ClassNotFoundException {
        abiContent = abi;
        generateJavaFiles(
                contractName,
                bin,
                loadContractDefinition(abi),
                destinationDir,
                basePackageName,
                null);
    }

    void generateJavaFiles(
            String contractName,
            String bin,
            List<AbiDefinition> abi,
            String destinationDir,
            String basePackageName,
            Map<String, String> addresses)
            throws IOException, ClassNotFoundException, UnsupportedOperationException {
        String className = Strings.capitaliseFirstLetter(contractName);

        if (bin.length() > maxSolidityBinSize) {
            throw new UnsupportedOperationException(
                    " contract binary too long, max support is 256k, now is "
                            + Integer.valueOf(bin.length()));
        }

        TypeSpec.Builder classBuilder = createClassBuilder(className, bin, abi);

        classBuilder.addMethod(buildTransactionDecoderGet());
        classBuilder.addMethod(buildConstructor(Credentials.class, CREDENTIALS, false));
        classBuilder.addMethod(buildConstructor(Credentials.class, CREDENTIALS, true));
        classBuilder.addMethod(
                buildConstructor(TransactionManager.class, TRANSACTION_MANAGER, false));
        classBuilder.addMethod(
                buildConstructor(TransactionManager.class, TRANSACTION_MANAGER, true));
        classBuilder.addFields(buildFuncNameConstants(abi));
        classBuilder.addMethods(buildFunctionDefinitions(className, classBuilder, abi));
        classBuilder.addMethod(buildLoad(className, Credentials.class, CREDENTIALS, false));
        classBuilder.addMethod(
                buildLoad(className, TransactionManager.class, TRANSACTION_MANAGER, false));
        classBuilder.addMethod(buildLoad(className, Credentials.class, CREDENTIALS, true));
        classBuilder.addMethod(
                buildLoad(className, TransactionManager.class, TRANSACTION_MANAGER, true));
        if (!bin.equals(Contract.BIN_NOT_PROVIDED)) {
            classBuilder.addMethods(buildDeployMethods(className, classBuilder, abi));
        }

        write(basePackageName, classBuilder.build(), destinationDir);
    }

    private TypeSpec.Builder createClassBuilder(
            String className, String binary, List<AbiDefinition> abi) {

        String javadoc = CODEGEN_WARNING + getWeb3jVersion();

        return TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(javadoc)
                .superclass(Contract.class)
                .addAnnotation(
                        AnnotationSpec.builder(SuppressWarnings.class)
                                .addMember("value", "$S", "unchecked")
                                .build())
                .addField(createBinaryDefinition(binary))
                .addField(createABIDefinition(abi))
                .addField(createTransactionDecoderDefinition());
    }

    private String getWeb3jVersion() {
        String version;

        try {
            // This only works if run as part of the web3j command line tools which contains
            // a version.properties file
            version = Version.getVersion();
        } catch (IOException | NullPointerException e) {
            version = Version.DEFAULT;
        }
        return "\n<p>Generated with web3j version " + version + ".\n";
    }

    private FieldSpec createTransactionDecoderDefinition() {

        return FieldSpec.builder(TransactionDecoder.class, TRANSACTION_DECODER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T($N, $N)", TransactionDecoder.class, "ABI", "BINARY")
                .build();
    }

    private FieldSpec createBinaryDefinition(String binary) {

        return FieldSpec.builder(String.class, BINARY)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                // .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer("$S", binary)
                .build();
    }

    private FieldSpec createABIDefinition(List<AbiDefinition> abi) {
        return FieldSpec.builder(String.class, "ABI")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer("$S", abiContent)
                .build();
    }

    private FieldSpec createEventDefinition(String name, List<NamedTypeName> parameters) {

        CodeBlock initializer = buildVariableLengthEventInitializer(name, parameters);

        return FieldSpec.builder(Event.class, buildEventDefinitionName(name))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(initializer)
                .build();
    }

    private String buildEventDefinitionName(String eventName) {
        return eventName.toUpperCase() + "_EVENT";
    }

    private boolean isOverLoadFunction(String name, List<AbiDefinition> functionDefinitions) {
        int count = 0;
        for (AbiDefinition functionDefinition : functionDefinitions) {
            if (!functionDefinition.getType().equals("function")) {
                continue;
            }

            if (functionDefinition.getName().equals(name)) {
                count += 1;
            }
        }

        return count > 1;
    }

    private List<MethodSpec> buildFunctionDefinitions(
            String className,
            TypeSpec.Builder classBuilder,
            List<AbiDefinition> functionDefinitions)
            throws ClassNotFoundException {

        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (AbiDefinition functionDefinition : functionDefinitions) {
            if (functionDefinition.getType().equals("function")) {
                MethodSpec ms = buildFunction(functionDefinition);
                methodSpecs.add(ms);

                if (!functionDefinition.isConstant()) {
                    MethodSpec msCallback = buildFunctionWithCallback(functionDefinition);
                    methodSpecs.add(msCallback);

                    MethodSpec msSeq = buildFunctionSeq(functionDefinition);
                    methodSpecs.add(msSeq);

                    boolean isOverLoad =
                            isOverLoadFunction(functionDefinition.getName(), functionDefinitions);
                    if (!functionDefinition.getInputs().isEmpty()) {
                        MethodSpec inputDecoder =
                                buildFunctionWithInputDecoder(functionDefinition, isOverLoad);
                        methodSpecs.add(inputDecoder);
                    }

                    if (!functionDefinition.getOutputs().isEmpty()) {
                        MethodSpec outputDecoder =
                                buildFunctionWithOutputDecoder(functionDefinition, isOverLoad);
                        methodSpecs.add(outputDecoder);
                    }
                }
            } else if (functionDefinition.getType().equals("event")) {
                methodSpecs.addAll(buildEventFunctions(functionDefinition, classBuilder));
            }
        }

        return methodSpecs;
    }

    List<MethodSpec> buildDeployMethods(
            String className,
            TypeSpec.Builder classBuilder,
            List<AbiDefinition> functionDefinitions) {
        boolean constructor = false;
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (AbiDefinition functionDefinition : functionDefinitions) {
            if (functionDefinition.getType().equals("constructor")) {
                constructor = true;
                methodSpecs.add(
                        buildDeploy(
                                className,
                                functionDefinition,
                                Credentials.class,
                                CREDENTIALS,
                                true));
                methodSpecs.add(
                        buildDeploy(
                                className,
                                functionDefinition,
                                TransactionManager.class,
                                TRANSACTION_MANAGER,
                                true));
                methodSpecs.add(
                        buildDeploy(
                                className,
                                functionDefinition,
                                Credentials.class,
                                CREDENTIALS,
                                false));
                methodSpecs.add(
                        buildDeploy(
                                className,
                                functionDefinition,
                                TransactionManager.class,
                                TRANSACTION_MANAGER,
                                false));
            }
        }

        // constructor will not be specified in ABI file if its empty
        if (!constructor) {
            MethodSpec.Builder credentialsMethodBuilder =
                    getDeployMethodSpec(className, Credentials.class, CREDENTIALS, false, true);
            methodSpecs.add(
                    buildDeployNoParams(
                            credentialsMethodBuilder, className, CREDENTIALS, false, true));

            MethodSpec.Builder credentialsMethodBuilderNoGasProvider =
                    getDeployMethodSpec(className, Credentials.class, CREDENTIALS, false, false);
            methodSpecs.add(
                    buildDeployNoParams(
                            credentialsMethodBuilderNoGasProvider,
                            className,
                            CREDENTIALS,
                            false,
                            false));

            MethodSpec.Builder transactionManagerMethodBuilder =
                    getDeployMethodSpec(
                            className, TransactionManager.class, TRANSACTION_MANAGER, false, true);
            methodSpecs.add(
                    buildDeployNoParams(
                            transactionManagerMethodBuilder,
                            className,
                            TRANSACTION_MANAGER,
                            false,
                            true));

            MethodSpec.Builder transactionManagerMethodBuilderNoGasProvider =
                    getDeployMethodSpec(
                            className, TransactionManager.class, TRANSACTION_MANAGER, false, false);
            methodSpecs.add(
                    buildDeployNoParams(
                            transactionManagerMethodBuilderNoGasProvider,
                            className,
                            TRANSACTION_MANAGER,
                            false,
                            false));
        }

        return methodSpecs;
    }

    Iterable<FieldSpec> buildFuncNameConstants(List<AbiDefinition> functionDefinitions) {
        List<FieldSpec> fields = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>();
        fieldNames.add(Contract.FUNC_DEPLOY);

        for (AbiDefinition functionDefinition : functionDefinitions) {
            if (functionDefinition.getType().equals("function")) {
                String funcName = functionDefinition.getName();

                if (!fieldNames.contains(funcName)) {
                    FieldSpec field =
                            FieldSpec.builder(
                                            String.class,
                                            funcNameToConst(funcName),
                                            Modifier.PUBLIC,
                                            Modifier.STATIC,
                                            Modifier.FINAL)
                                    .initializer("$S", funcName)
                                    .build();
                    fields.add(field);
                    fieldNames.add(funcName);
                }
            }
        }
        return fields;
    }

    private static MethodSpec buildTransactionDecoderGet() {

        MethodSpec.Builder toReturn =
                MethodSpec.methodBuilder("getTransactionDecoder")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(TransactionDecoder.class);

        toReturn.addStatement("return $N", TRANSACTION_DECODER);

        return toReturn.build();
    }

    private static MethodSpec buildConstructor(
            Class authType, String authName, boolean withGasProvider) {
        MethodSpec.Builder toReturn =
                MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PROTECTED)
                        .addParameter(String.class, CONTRACT_ADDRESS)
                        .addParameter(Web3j.class, WEB3J)
                        .addParameter(authType, authName);

        if (withGasProvider) {
            toReturn.addParameter(ContractGasProvider.class, CONTRACT_GAS_PROVIDER)
                    .addStatement(
                            "super($N, $N, $N, $N, $N)",
                            BINARY,
                            CONTRACT_ADDRESS,
                            WEB3J,
                            authName,
                            CONTRACT_GAS_PROVIDER);
        } else {
            toReturn.addParameter(BigInteger.class, GAS_PRICE)
                    .addParameter(BigInteger.class, GAS_LIMIT)
                    .addStatement(
                            "super($N, $N, $N, $N, $N, $N)",
                            BINARY,
                            CONTRACT_ADDRESS,
                            WEB3J,
                            authName,
                            GAS_PRICE,
                            GAS_LIMIT)
                    .addAnnotation(Deprecated.class);
        }

        return toReturn.build();
    }

    private MethodSpec buildDeploy(
            String className,
            AbiDefinition functionDefinition,
            Class authType,
            String authName,
            boolean withGasProvider) {

        boolean isPayable = functionDefinition.isPayable();

        MethodSpec.Builder methodBuilder =
                getDeployMethodSpec(className, authType, authName, isPayable, withGasProvider);
        String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());

        if (!inputParams.isEmpty()) {
            return buildDeployWithParams(
                    methodBuilder, className, inputParams, authName, isPayable, withGasProvider);
        } else {
            return buildDeployNoParams(
                    methodBuilder, className, authName, isPayable, withGasProvider);
        }
    }

    private static MethodSpec buildDeployWithParams(
            MethodSpec.Builder methodBuilder,
            String className,
            String inputParams,
            String authName,
            boolean isPayable,
            boolean withGasProvider) {

        methodBuilder.addStatement(
                "$T encodedConstructor = $T.encodeConstructor(" + "$T.<$T>asList($L)" + ")",
                String.class,
                FunctionEncoder.class,
                Arrays.class,
                Type.class,
                inputParams);
        if (isPayable && !withGasProvider) {
            methodBuilder.addStatement(
                    "return deployRemoteCall("
                            + "$L.class, $L, $L, $L, $L, $L, encodedConstructor, $L)",
                    className,
                    WEB3J,
                    authName,
                    GAS_PRICE,
                    GAS_LIMIT,
                    BINARY,
                    INITIAL_VALUE);
            methodBuilder.addAnnotation(Deprecated.class);
        } else if (isPayable && withGasProvider) {
            methodBuilder.addStatement(
                    "return deployRemoteCall("
                            + "$L.class, $L, $L, $L, $L, encodedConstructor, $L)",
                    className,
                    WEB3J,
                    authName,
                    CONTRACT_GAS_PROVIDER,
                    BINARY,
                    INITIAL_VALUE);
        } else if (!isPayable && !withGasProvider) {
            methodBuilder.addStatement(
                    "return deployRemoteCall($L.class, $L, $L, $L, $L, $L, encodedConstructor)",
                    className,
                    WEB3J,
                    authName,
                    GAS_PRICE,
                    GAS_LIMIT,
                    BINARY);
            methodBuilder.addAnnotation(Deprecated.class);
        } else {
            methodBuilder.addStatement(
                    "return deployRemoteCall($L.class, $L, $L, $L, $L, encodedConstructor)",
                    className,
                    WEB3J,
                    authName,
                    CONTRACT_GAS_PROVIDER,
                    BINARY);
        }

        return methodBuilder.build();
    }

    private static MethodSpec buildDeployNoParams(
            MethodSpec.Builder methodBuilder,
            String className,
            String authName,
            boolean isPayable,
            boolean withGasPRovider) {
        if (isPayable && !withGasPRovider) {
            methodBuilder.addStatement(
                    "return deployRemoteCall($L.class, $L, $L, $L, $L, $L, \"\", $L)",
                    className,
                    WEB3J,
                    authName,
                    GAS_PRICE,
                    GAS_LIMIT,
                    BINARY,
                    INITIAL_VALUE);
            methodBuilder.addAnnotation(Deprecated.class);
        } else if (isPayable && withGasPRovider) {
            methodBuilder.addStatement(
                    "return deployRemoteCall($L.class, $L, $L, $L, $L, \"\", $L)",
                    className,
                    WEB3J,
                    authName,
                    CONTRACT_GAS_PROVIDER,
                    BINARY,
                    INITIAL_VALUE);
        } else if (!isPayable && !withGasPRovider) {
            methodBuilder.addStatement(
                    "return deployRemoteCall($L.class, $L, $L, $L, $L, $L, \"\")",
                    className,
                    WEB3J,
                    authName,
                    GAS_PRICE,
                    GAS_LIMIT,
                    BINARY);
            methodBuilder.addAnnotation(Deprecated.class);
        } else {
            methodBuilder.addStatement(
                    "return deployRemoteCall($L.class, $L, $L, $L, $L, \"\")",
                    className,
                    WEB3J,
                    authName,
                    CONTRACT_GAS_PROVIDER,
                    BINARY);
        }

        return methodBuilder.build();
    }

    private static MethodSpec.Builder getDeployMethodSpec(
            String className,
            Class authType,
            String authName,
            boolean isPayable,
            boolean withGasProvider) {
        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("deploy")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(buildRemoteCall(TypeVariableName.get(className, Type.class)))
                        .addParameter(Web3j.class, WEB3J)
                        .addParameter(authType, authName);
        if (isPayable && !withGasProvider) {
            return builder.addParameter(BigInteger.class, GAS_PRICE)
                    .addParameter(BigInteger.class, GAS_LIMIT)
                    .addParameter(BigInteger.class, INITIAL_VALUE);
        } else if (isPayable && withGasProvider) {
            return builder.addParameter(ContractGasProvider.class, CONTRACT_GAS_PROVIDER)
                    .addParameter(BigInteger.class, INITIAL_VALUE);
        } else if (!isPayable && withGasProvider) {
            return builder.addParameter(ContractGasProvider.class, CONTRACT_GAS_PROVIDER);
        } else {
            return builder.addParameter(BigInteger.class, GAS_PRICE)
                    .addParameter(BigInteger.class, GAS_LIMIT);
        }
    }

    private static MethodSpec buildLoad(
            String className, Class authType, String authName, boolean withGasProvider) {
        MethodSpec.Builder toReturn =
                MethodSpec.methodBuilder("load")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(TypeVariableName.get(className, Type.class))
                        .addParameter(String.class, CONTRACT_ADDRESS)
                        .addParameter(Web3j.class, WEB3J)
                        .addParameter(authType, authName);

        if (withGasProvider) {
            toReturn.addParameter(ContractGasProvider.class, CONTRACT_GAS_PROVIDER)
                    .addStatement(
                            "return new $L($L, $L, $L, $L)",
                            className,
                            CONTRACT_ADDRESS,
                            WEB3J,
                            authName,
                            CONTRACT_GAS_PROVIDER);
        } else {
            toReturn.addParameter(BigInteger.class, GAS_PRICE)
                    .addParameter(BigInteger.class, GAS_LIMIT)
                    .addStatement(
                            "return new $L($L, $L, $L, $L, $L)",
                            className,
                            CONTRACT_ADDRESS,
                            WEB3J,
                            authName,
                            GAS_PRICE,
                            GAS_LIMIT)
                    .addAnnotation(Deprecated.class);
        }

        return toReturn.build();
    }

    private MethodSpec.Builder addParameter(
            MethodSpec.Builder methodBuilder, String type, String name) {

        ParameterSpec parameterSpec = buildParameterType(type, name);

        TypeName typeName = getWrapperType(parameterSpec.type);

        ParameterSpec inputParameter = ParameterSpec.builder(typeName, parameterSpec.name).build();

        methodBuilder.addParameter(inputParameter);

        return methodBuilder;
    }

    private String addParameters(
            MethodSpec.Builder methodBuilder, List<AbiDefinition.NamedType> namedTypes) {

        List<ParameterSpec> inputParameterTypes = buildParameterTypes(namedTypes);

        List<ParameterSpec> nativeInputParameterTypes = new ArrayList<>(inputParameterTypes.size());
        for (ParameterSpec parameterSpec : inputParameterTypes) {
            TypeName typeName = getWrapperType(parameterSpec.type);
            nativeInputParameterTypes.add(
                    ParameterSpec.builder(typeName, parameterSpec.name).build());
        }

        methodBuilder.addParameters(nativeInputParameterTypes);

        if (useNativeJavaTypes) {
            return Collection.join(
                    namedTypes,
                    ", \n",
                    // this results in fully qualified names being generated
                    this::createMappedParameterTypes);
        } else {
            return Collection.join(inputParameterTypes, ", ", parameterSpec -> parameterSpec.name);
        }
    }

    private String addParameters(List<AbiDefinition.NamedType> namedTypes) {

        List<ParameterSpec> inputParameterTypes = buildParameterTypes(namedTypes);

        List<ParameterSpec> nativeInputParameterTypes = new ArrayList<>(inputParameterTypes.size());
        for (ParameterSpec parameterSpec : inputParameterTypes) {
            TypeName typeName = getWrapperType(parameterSpec.type);
            nativeInputParameterTypes.add(
                    ParameterSpec.builder(typeName, parameterSpec.name).build());
        }

        if (useNativeJavaTypes) {
            return Collection.join(
                    namedTypes,
                    ", \n",
                    // this results in fully qualified names being generated
                    this::createMappedParameterTypes);
        } else {
            return Collection.join(inputParameterTypes, ", ", parameterSpec -> parameterSpec.name);
        }
    }

    private String createMappedParameterTypes(AbiDefinition.NamedType namedType) {

        String name = namedType.getName();
        String type = namedType.getType();
        AbiDefinition.NamedType.Type innerType = new AbiDefinition.NamedType.Type(type);

        ParameterSpec parameterSpec = ParameterSpec.builder(buildTypeName(type), name).build();

        if (parameterSpec.type instanceof ParameterizedTypeName) {
            List<TypeName> typeNames = ((ParameterizedTypeName) parameterSpec.type).typeArguments;
            if (typeNames.size() != 1) {
                throw new UnsupportedOperationException(
                        "Only a single parameterized type is supported");
            } else {
                String parameterSpecType = parameterSpec.type.toString();
                TypeName typeName = typeNames.get(0);
                String typeMapInput = typeName + ".class";

                if (typeName instanceof ParameterizedTypeName) {
                    List<TypeName> typeArguments = ((ParameterizedTypeName) typeName).typeArguments;
                    if (typeArguments.size() != 1) {
                        throw new UnsupportedOperationException(
                                "Only a single parameterized type is supported");
                    }

                    TypeName innerTypeName = typeArguments.get(0);
                    parameterSpecType =
                            ((ParameterizedTypeName) parameterSpec.type).rawType.toString();

                    typeMapInput =
                            ((ParameterizedTypeName) typeName).rawType
                                    + ".class, "
                                    + innerTypeName
                                    + ".class";
                }

                if (innerType.dynamicArray()) { // dynamic array
                    return parameterSpec.name
                            + ".isEmpty()?org.fisco.bcos.web3j.abi.datatypes.DynamicArray.empty"
                            + "(\""
                            + type
                            + "\"):"
                            + "new "
                            + parameterSpecType
                            + "(\n"
                            + "        org.fisco.bcos.web3j.abi.Utils.typeMap("
                            + parameterSpec.name
                            + ", "
                            + typeMapInput
                            + "))";
                } else { // static array
                    return "new "
                            + parameterSpecType
                            + "(\n"
                            + "        org.fisco.bcos.web3j.abi.Utils.typeMap("
                            + parameterSpec.name
                            + ", "
                            + typeMapInput
                            + "))";
                }
            }
        } else {
            return "new " + parameterSpec.type + "(" + parameterSpec.name + ")";
        }
    }

    private TypeName getWrapperType(TypeName typeName) {
        if (useNativeJavaTypes) {
            return getNativeType(typeName);
        } else {
            return typeName;
        }
    }

    private TypeName getWrapperRawType(TypeName typeName) {
        if (useNativeJavaTypes) {
            if (typeName instanceof ParameterizedTypeName) {
                return ClassName.get(List.class);
            }
            return getNativeType(typeName);
        } else {
            return typeName;
        }
    }

    private TypeName getIndexedEventWrapperType(TypeName typeName) {
        if (useNativeJavaTypes) {
            return getEventNativeType(typeName);
        } else {
            return typeName;
        }
    }

    static TypeName getNativeType(TypeName typeName) {

        if (typeName instanceof ParameterizedTypeName) {
            return getNativeType((ParameterizedTypeName) typeName);
        }

        String simpleName = ((ClassName) typeName).simpleName();

        if (simpleName.startsWith(Address.class.getSimpleName())) {
            return TypeName.get(String.class);
        } else if (simpleName.startsWith("Uint")) {
            return TypeName.get(BigInteger.class);
        } else if (simpleName.startsWith("Int")) {
            return TypeName.get(BigInteger.class);
        } else if (simpleName.startsWith(Utf8String.class.getSimpleName())) {
            return TypeName.get(String.class);
        } else if (simpleName.startsWith("Bytes")) {
            return TypeName.get(byte[].class);
        } else if (simpleName.startsWith(DynamicBytes.class.getSimpleName())) {
            return TypeName.get(byte[].class);
        } else if (simpleName.startsWith(Bool.class.getSimpleName())) {
            return TypeName.get(Boolean.class); // boolean cannot be a parameterized type
        } else {
            throw new UnsupportedOperationException(
                    "Unsupported type: " + typeName + ", no native type mapping exists.");
        }
    }

    static TypeName getNativeType(ParameterizedTypeName parameterizedTypeName) {
        List<TypeName> typeNames = parameterizedTypeName.typeArguments;
        List<TypeName> nativeTypeNames = new ArrayList<>(typeNames.size());
        for (TypeName enclosedTypeName : typeNames) {
            nativeTypeNames.add(getNativeType(enclosedTypeName));
        }
        return ParameterizedTypeName.get(
                ClassName.get(List.class),
                nativeTypeNames.toArray(new TypeName[nativeTypeNames.size()]));
    }

    static TypeName getEventNativeType(TypeName typeName) {
        if (typeName instanceof ParameterizedTypeName) {
            return TypeName.get(byte[].class);
        }

        String simpleName = ((ClassName) typeName).simpleName();
        if ("Utf8String".equals(simpleName)) {
            return TypeName.get(byte[].class);
        } else {
            return getNativeType(typeName);
        }
    }

    private ParameterSpec buildParameterType(String type, String name) {

        return ParameterSpec.builder(buildTypeName(type), name).build();
    }

    static List<ParameterSpec> buildParameterTypes(List<AbiDefinition.NamedType> namedTypes) {
        List<ParameterSpec> result = new ArrayList<>(namedTypes.size());
        for (int i = 0; i < namedTypes.size(); i++) {
            AbiDefinition.NamedType namedType = namedTypes.get(i);

            String name = createValidParamName(namedType.getName(), i);
            String type = namedTypes.get(i).getType();
            namedType.setName(name);

            result.add(ParameterSpec.builder(buildTypeName(type), name).build());
        }
        return result;
    }

    /**
     * Public Solidity arrays and maps require an unnamed input parameter - multiple if they require
     * a struct type.
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

    private MethodSpec buildFunction(AbiDefinition functionDefinition)
            throws ClassNotFoundException {
        String functionName = functionDefinition.getName();

        if (!SourceVersion.isName(functionName)) {
            functionName = "_" + functionName;
        }

        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder(functionName).addModifiers(Modifier.PUBLIC);

        String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());

        List<TypeName> outputParameterTypes = buildTypeNames(functionDefinition.getOutputs());
        if (functionDefinition.isConstant()) {
            buildConstantFunction(
                    functionDefinition, methodBuilder, outputParameterTypes, inputParams);
        } else {
            buildTransactionFunction(functionDefinition, methodBuilder, inputParams);
        }

        return methodBuilder.build();
    }

    MethodSpec buildFunctionSeq(AbiDefinition functionDefinition) throws ClassNotFoundException {
        String functionName = functionDefinition.getName();
        functionName += "Seq";

        if (!SourceVersion.isName(functionName)) {
            functionName = "_" + functionName;
        }

        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder(functionName).addModifiers(Modifier.PUBLIC);

        String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());

        buildTransactionFunctionSeq(functionDefinition, methodBuilder, inputParams);

        return methodBuilder.build();
    }

    MethodSpec buildFunctionWithCallback(AbiDefinition functionDefinition)
            throws ClassNotFoundException {
        String functionName = functionDefinition.getName();

        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder(functionName).addModifiers(Modifier.PUBLIC);

        List<TypeName> outputParameterTypes = buildTypeNames(functionDefinition.getOutputs());

        if (functionDefinition.isConstant()) {
            String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());
            buildConstantFunction(
                    functionDefinition, methodBuilder, outputParameterTypes, inputParams);
        } else {
            // functionDefinition.getInputs().add(new NamedType("callback",
            // "org.fisco.bcos.channel.dto.FiscoResponse.TransactionSucCallback"));
            String inputParams = addParameters(methodBuilder, functionDefinition.getInputs());
            methodBuilder.addParameter(
                    ParameterSpec.builder(buildTypeName("TransactionSucCallback"), "callback")
                            .build());

            buildTransactionFunctionWithCallback(functionDefinition, methodBuilder, inputParams);
        }

        return methodBuilder.build();
    }

    private String getInputOutputFunctionName(
            AbiDefinition functionDefinition, boolean input, boolean isOverLoad) {
        if (!isOverLoad) {
            return functionDefinition.getName();
        }

        List<NamedType> nameTypes =
                (input ? functionDefinition.getInputs() : functionDefinition.getOutputs());

        String name = functionDefinition.getName();
        for (int i = 0; i < nameTypes.size(); i++) {
            AbiDefinition.NamedType.Type type =
                    new AbiDefinition.NamedType.Type(nameTypes.get(i).getType());
            List<Integer> depths = type.getDepth();
            name += Strings.capitaliseFirstLetter(type.getBaseName());
            for (int j = 0; j < depths.size(); j++) {
                name += "Array";
                if (0 != depths.get(j)) {
                    name += String.valueOf(depths.get(j));
                }
            }
        }

        return name;
    }

    private MethodSpec buildFunctionWithInputDecoder(
            AbiDefinition functionDefinition, boolean isOverLoad) throws ClassNotFoundException {

        String functionName = getInputOutputFunctionName(functionDefinition, true, isOverLoad);

        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder(
                                "get" + Strings.capitaliseFirstLetter(functionName) + "Input")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(TransactionReceipt.class, "transactionReceipt");

        List<TypeName> returnTypes =
                buildReturnTypes(buildTypeNames(functionDefinition.getInputs()));

        ParameterizedTypeName parameterizedTupleType =
                ParameterizedTypeName.get(
                        ClassName.get(
                                "org.fisco.bcos.web3j.tuples.generated",
                                "Tuple" + returnTypes.size()),
                        returnTypes.toArray(new TypeName[returnTypes.size()]));

        methodBuilder.returns(parameterizedTupleType);
        methodBuilder.addStatement("String data = transactionReceipt.getInput().substring(10)");

        buildVariableLengthReturnFunctionConstructor(
                methodBuilder, functionName, "", buildTypeNames(functionDefinition.getInputs()));

        methodBuilder.addStatement(
                "$T<Type> results = $T.decode(data, function.getOutputParameters());",
                List.class,
                FunctionReturnDecoder.class);

        buildTupleResultContainer0(
                methodBuilder,
                parameterizedTupleType,
                buildTypeNames(functionDefinition.getInputs()));

        return methodBuilder.build();
    }

    private MethodSpec buildFunctionWithOutputDecoder(
            AbiDefinition functionDefinition, boolean isOverLoad) throws ClassNotFoundException {

        String functionName = getInputOutputFunctionName(functionDefinition, false, isOverLoad);

        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder(
                                "get" + Strings.capitaliseFirstLetter(functionName) + "Output")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(TransactionReceipt.class, "transactionReceipt");

        List<TypeName> returnTypes =
                buildReturnTypes(buildTypeNames(functionDefinition.getOutputs()));

        ParameterizedTypeName parameterizedTupleType =
                ParameterizedTypeName.get(
                        ClassName.get(
                                "org.fisco.bcos.web3j.tuples.generated",
                                "Tuple" + returnTypes.size()),
                        returnTypes.toArray(new TypeName[returnTypes.size()]));

        methodBuilder.returns(parameterizedTupleType);
        methodBuilder.addStatement("String data = transactionReceipt.getOutput()");

        buildVariableLengthReturnFunctionConstructor(
                methodBuilder, functionName, "", buildTypeNames(functionDefinition.getOutputs()));

        methodBuilder.addStatement(
                "$T<Type> results = $T.decode(data, function.getOutputParameters());",
                List.class,
                FunctionReturnDecoder.class);

        buildTupleResultContainer0(
                methodBuilder,
                parameterizedTupleType,
                buildTypeNames(functionDefinition.getOutputs()));

        return methodBuilder.build();
    }

    private void buildConstantFunction(
            AbiDefinition functionDefinition,
            MethodSpec.Builder methodBuilder,
            List<TypeName> outputParameterTypes,
            String inputParams)
            throws ClassNotFoundException {

        String functionName = functionDefinition.getName();

        if (outputParameterTypes.isEmpty()) {
            methodBuilder.addStatement(
                    "throw new RuntimeException"
                            + "(\"cannot call constant function with void return type\")");
        } else if (outputParameterTypes.size() == 1) {

            TypeName typeName = outputParameterTypes.get(0);
            TypeName nativeReturnTypeName;
            if (useNativeJavaTypes) {
                nativeReturnTypeName = getWrapperRawType(typeName);
            } else {
                nativeReturnTypeName = getWrapperType(typeName);
            }
            methodBuilder.returns(buildRemoteCall(nativeReturnTypeName));

            methodBuilder.addStatement(
                    "final $T function = "
                            + "new $T($N, \n$T.<$T>asList($L), "
                            + "\n$T.<$T<?>>asList(new $T<$T>() {}))",
                    Function.class,
                    Function.class,
                    funcNameToConst(functionName),
                    Arrays.class,
                    Type.class,
                    inputParams,
                    Arrays.class,
                    TypeReference.class,
                    TypeReference.class,
                    typeName);

            if (useNativeJavaTypes) {
                if (nativeReturnTypeName.equals(ClassName.get(List.class))) {
                    // We return list. So all the list elements should
                    // also be converted to native types
                    TypeName listType = ParameterizedTypeName.get(List.class, Type.class);

                    CodeBlock.Builder callCode = CodeBlock.builder();
                    callCode.addStatement(
                            "$T result = "
                                    + "($T) executeCallSingleValueReturn(function, $T.class)",
                            listType,
                            listType,
                            nativeReturnTypeName);
                    callCode.addStatement("return convertToNative(result)");

                    TypeSpec callableType =
                            TypeSpec.anonymousClassBuilder("")
                                    .addSuperinterface(
                                            ParameterizedTypeName.get(
                                                    ClassName.get(Callable.class),
                                                    nativeReturnTypeName))
                                    .addMethod(
                                            MethodSpec.methodBuilder("call")
                                                    .addAnnotation(Override.class)
                                                    .addAnnotation(
                                                            AnnotationSpec.builder(
                                                                            SuppressWarnings.class)
                                                                    .addMember(
                                                                            "value",
                                                                            "$S",
                                                                            "unchecked")
                                                                    .build())
                                                    .addModifiers(Modifier.PUBLIC)
                                                    .addException(Exception.class)
                                                    .returns(nativeReturnTypeName)
                                                    .addCode(callCode.build())
                                                    .build())
                                    .build();

                    methodBuilder.addStatement(
                            "return new $T(\n$L)",
                            buildRemoteCall(nativeReturnTypeName),
                            callableType);
                } else {
                    methodBuilder.addStatement(
                            "return executeRemoteCallSingleValueReturn(function, $T.class)",
                            nativeReturnTypeName);
                }
            } else {
                methodBuilder.addStatement("return executeRemoteCallSingleValueReturn(function)");
            }
        } else {
            List<TypeName> returnTypes = buildReturnTypes(outputParameterTypes);

            ParameterizedTypeName parameterizedTupleType =
                    ParameterizedTypeName.get(
                            ClassName.get(
                                    "org.fisco.bcos.web3j.tuples.generated",
                                    "Tuple" + returnTypes.size()),
                            returnTypes.toArray(new TypeName[returnTypes.size()]));

            methodBuilder.returns(buildRemoteCall(parameterizedTupleType));

            buildVariableLengthReturnFunctionConstructor(
                    methodBuilder, functionName, inputParams, outputParameterTypes);

            buildTupleResultContainer(methodBuilder, parameterizedTupleType, outputParameterTypes);
        }
    }

    private static ParameterizedTypeName buildRemoteCall(TypeName typeName) {
        return ParameterizedTypeName.get(ClassName.get(RemoteCall.class), typeName);
    }

    private void buildTransactionFunction(
            AbiDefinition functionDefinition, MethodSpec.Builder methodBuilder, String inputParams)
            throws ClassNotFoundException {

        if (functionDefinition.hasOutputs()) {
            // CHECKSTYLE:OFF
            reporter.report(
                    String.format(
                            "Definition of the function %s returns a value but is not defined as a view function. "
                                    + "Please ensure it contains the view modifier if you want to read the return value",
                            functionDefinition.getName()));
            // CHECKSTYLE:ON
        }

        if (functionDefinition.isPayable()) {
            methodBuilder.addParameter(BigInteger.class, WEI_VALUE);
        }

        String functionName = functionDefinition.getName();

        methodBuilder.returns(buildRemoteCall(TypeName.get(TransactionReceipt.class)));

        methodBuilder.addStatement(
                "final $T function = new $T(\n$N, \n$T.<$T>asList($L), \n$T"
                        + ".<$T<?>>emptyList())",
                Function.class,
                Function.class,
                funcNameToConst(functionName),
                Arrays.class,
                Type.class,
                inputParams,
                Collections.class,
                TypeReference.class);
        if (functionDefinition.isPayable()) {
            methodBuilder.addStatement(
                    "return executeRemoteCallTransaction(function, $N)", WEI_VALUE);
        } else {
            methodBuilder.addStatement("return executeRemoteCallTransaction(function)");
        }
    }

    private void buildTransactionFunctionWithCallback(
            AbiDefinition functionDefinition, MethodSpec.Builder methodBuilder, String inputParams)
            throws ClassNotFoundException {

        if (functionDefinition.hasOutputs()) {
            // CHECKSTYLE:OFF
            reporter.report(
                    String.format(
                            "Definition of the function %s returns a value but is not defined as a view function. "
                                    + "Please ensure it contains the view modifier if you want to read the return value",
                            functionDefinition.getName()));
            // CHECKSTYLE:ON
        }

        if (functionDefinition.isPayable()) {
            methodBuilder.addParameter(BigInteger.class, WEI_VALUE);
        }
        // methodBuilder.addParameter(TransactionSucCallback.class, "callback");

        String functionName = functionDefinition.getName();

        methodBuilder.returns(TypeName.VOID);

        methodBuilder.addStatement(
                "final $T function = new $T(\n$N, \n$T.<$T>asList($L), \n$T"
                        + ".<$T<?>>emptyList())",
                Function.class,
                Function.class,
                funcNameToConst(functionName),
                Arrays.class,
                Type.class,
                inputParams,
                Collections.class,
                TypeReference.class);
        methodBuilder.addStatement("asyncExecuteTransaction(function, callback)");
    }

    private void buildTransactionFunctionSeq(
            AbiDefinition functionDefinition, MethodSpec.Builder methodBuilder, String inputParams)
            throws ClassNotFoundException {

        if (functionDefinition.hasOutputs()) {
            // CHECKSTYLE:OFF
            reporter.report(
                    String.format(
                            "Definition of the function %s returns a value but is not defined as a view function. "
                                    + "Please ensure it contains the view modifier if you want to read the return value",
                            functionDefinition.getName()));
            // CHECKSTYLE:ON
        }

        if (functionDefinition.isPayable()) {
            methodBuilder.addParameter(BigInteger.class, WEI_VALUE);
        }

        String functionName = functionDefinition.getName();

        TypeName returnType = TypeName.get(String.class);
        methodBuilder.returns(returnType);

        methodBuilder.addStatement(
                "final $T function = new $T(\n$N, \n$T.<$T>asList($L), \n$T"
                        + ".<$T<?>>emptyList())",
                Function.class,
                Function.class,
                funcNameToConst(functionName),
                Arrays.class,
                Type.class,
                inputParams,
                Collections.class,
                TypeReference.class);

        methodBuilder.addStatement("return createTransactionSeq(function)");
    }

    TypeSpec buildEventResponseObject(
            String className,
            List<SolidityFunctionWrapper.NamedTypeName> indexedParameters,
            List<SolidityFunctionWrapper.NamedTypeName> nonIndexedParameters) {

        TypeSpec.Builder builder =
                TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC, Modifier.STATIC);

        builder.addField(LOG, "log", Modifier.PUBLIC);
        for (SolidityFunctionWrapper.NamedTypeName namedType : indexedParameters) {
            TypeName typeName = getIndexedEventWrapperType(namedType.typeName);
            builder.addField(typeName, namedType.getName(), Modifier.PUBLIC);
        }

        for (SolidityFunctionWrapper.NamedTypeName namedType : nonIndexedParameters) {
            TypeName typeName = getWrapperType(namedType.typeName);
            builder.addField(typeName, namedType.getName(), Modifier.PUBLIC);
        }

        return builder.build();
    }

    private MethodSpec buildRegisterEventLogPushFunction(String eventName)
            throws ClassNotFoundException {

        String generatedFunctionName = "register" + eventName + "EventLogFilter";

        MethodSpec.Builder getEventMethodBuilder =
                MethodSpec.methodBuilder(generatedFunctionName)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(String.class, FROM_BLOCK)
                        .addParameter(String.class, TO_BLOCK);

        addParameter(getEventMethodBuilder, "string[]", OTHER_TOPICS)
                .addParameter(AbiTypes.getType("EventLogPushCallback"), CALLBACK_VALUE);

        getEventMethodBuilder.addStatement(
                "String topic0 = $T.encode(" + buildEventDefinitionName(eventName) + ")",
                EventEncoder.class);

        getEventMethodBuilder.addStatement(
                "registerEventLogPushFilter(ABI,BINARY"
                        + ","
                        + "topic0"
                        + ","
                        + FROM_BLOCK
                        + ","
                        + TO_BLOCK
                        + ","
                        + OTHER_TOPICS
                        + ","
                        + CALLBACK_VALUE
                        + ")");

        return getEventMethodBuilder.build();
    }

    private MethodSpec buildDefaultRegisterEventLogPushFunction(String eventName)
            throws ClassNotFoundException {

        String generatedFunctionName = "register" + eventName + "EventLogFilter";

        MethodSpec.Builder getEventMethodBuilder =
                MethodSpec.methodBuilder(generatedFunctionName)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(AbiTypes.getType("EventLogPushCallback"), CALLBACK_VALUE);

        getEventMethodBuilder.addStatement(
                "String topic0 = $T.encode(" + buildEventDefinitionName(eventName) + ")",
                EventEncoder.class);

        getEventMethodBuilder.addStatement(
                "registerEventLogPushFilter(ABI,BINARY" + ",topic0" + "," + CALLBACK_VALUE + ")");

        return getEventMethodBuilder.build();
    }

    MethodSpec buildEventTransactionReceiptFunction(
            String responseClassName,
            String functionName,
            List<NamedTypeName> indexedParameters,
            List<NamedTypeName> nonIndexedParameters) {

        ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(
                        ClassName.get(List.class), ClassName.get("", responseClassName));

        String generatedFunctionName =
                "get" + Strings.capitaliseFirstLetter(functionName) + "Events";
        MethodSpec.Builder transactionMethodBuilder =
                MethodSpec.methodBuilder(generatedFunctionName)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(TransactionReceipt.class, "transactionReceipt")
                        .returns(parameterizedTypeName);

        transactionMethodBuilder
                .addStatement(
                        "$T valueList = extractEventParametersWithLog("
                                + buildEventDefinitionName(functionName)
                                + ", "
                                + "transactionReceipt)",
                        ParameterizedTypeName.get(List.class, Contract.EventValuesWithLog.class))
                .addStatement(
                        "$1T responses = new $1T(valueList.size())",
                        ParameterizedTypeName.get(
                                ClassName.get(ArrayList.class),
                                ClassName.get("", responseClassName)))
                .beginControlFlow(
                        "for ($T eventValues : valueList)", Contract.EventValuesWithLog.class)
                .addStatement("$1T typedResponse = new $1T()", ClassName.get("", responseClassName))
                .addCode(
                        buildTypedResponse(
                                "typedResponse", indexedParameters, nonIndexedParameters, false))
                .addStatement("responses.add(typedResponse)")
                .endControlFlow();

        transactionMethodBuilder.addStatement("return responses");
        return transactionMethodBuilder.build();
    }

    List<MethodSpec> buildEventFunctions(
            AbiDefinition functionDefinition, TypeSpec.Builder classBuilder)
            throws ClassNotFoundException {
        String functionName = functionDefinition.getName();
        List<AbiDefinition.NamedType> inputs = functionDefinition.getInputs();
        String responseClassName = Strings.capitaliseFirstLetter(functionName) + "EventResponse";

        List<NamedTypeName> parameters = new ArrayList<>();
        List<NamedTypeName> indexedParameters = new ArrayList<>();
        List<NamedTypeName> nonIndexedParameters = new ArrayList<>();

        for (AbiDefinition.NamedType namedType : inputs) {
            NamedTypeName parameter =
                    new NamedTypeName(
                            namedType.getName(),
                            buildTypeName(namedType.getType()),
                            namedType.isIndexed());
            if (namedType.isIndexed()) {
                indexedParameters.add(parameter);
            } else {
                nonIndexedParameters.add(parameter);
            }
            parameters.add(parameter);
        }

        classBuilder.addField(createEventDefinition(functionName, parameters));

        classBuilder.addType(
                buildEventResponseObject(
                        responseClassName, indexedParameters, nonIndexedParameters));

        List<MethodSpec> methods = new ArrayList<>();
        methods.add(
                buildEventTransactionReceiptFunction(
                        responseClassName, functionName, indexedParameters, nonIndexedParameters));

        methods.add(buildRegisterEventLogPushFunction(functionName));
        methods.add(buildDefaultRegisterEventLogPushFunction(functionName));

        return methods;
    }

    CodeBlock buildTypedResponse(
            String objectName,
            List<SolidityFunctionWrapper.NamedTypeName> indexedParameters,
            List<SolidityFunctionWrapper.NamedTypeName> nonIndexedParameters,
            boolean flowable) {
        String nativeConversion;

        if (useNativeJavaTypes) {
            nativeConversion = ".getValue()";
        } else {
            nativeConversion = "";
        }

        CodeBlock.Builder builder = CodeBlock.builder();
        if (flowable) {
            builder.addStatement("$L.log = log", objectName);
        } else {
            builder.addStatement("$L.log = eventValues.getLog()", objectName);
        }
        for (int i = 0; i < indexedParameters.size(); i++) {
            builder.addStatement(
                    "$L.$L = ($T) eventValues.getIndexedValues().get($L)" + nativeConversion,
                    objectName,
                    indexedParameters.get(i).getName(),
                    getIndexedEventWrapperType(indexedParameters.get(i).getTypeName()),
                    i);
        }

        for (int i = 0; i < nonIndexedParameters.size(); i++) {
            builder.addStatement(
                    "$L.$L = ($T) eventValues.getNonIndexedValues().get($L)" + nativeConversion,
                    objectName,
                    nonIndexedParameters.get(i).getName(),
                    getWrapperType(nonIndexedParameters.get(i).getTypeName()),
                    i);
        }
        return builder.build();
    }

    public static TypeName buildTypeName(String typeDeclaration) {

        AbiDefinition.NamedType.Type type =
                new AbiDefinition.NamedType.Type(trimStorageDeclaration(typeDeclaration));

        Class<?> baseType = AbiTypes.getType(type.getBaseName());

        if (!type.arrayType()) {
            return ClassName.get(baseType);
        }

        TypeName typeName;
        List<Integer> depths = type.getDepth();

        if (depths.get(0) == 0) {
            typeName = ParameterizedTypeName.get(DynamicArray.class, baseType);
        } else {
            Class<?> rawType = getStaticArrayTypeReferenceClass(String.valueOf(depths.get(0)));
            typeName = ParameterizedTypeName.get(rawType, baseType);
        }

        for (int i = 1; i < depths.size(); i++) {
            if (depths.get(i) == 0) {
                typeName = ParameterizedTypeName.get(ClassName.get(DynamicArray.class), typeName);
            } else {
                Class<?> rawType = getStaticArrayTypeReferenceClass(String.valueOf(depths.get(i)));
                typeName = ParameterizedTypeName.get(ClassName.get(rawType), typeName);
            }
        }

        return typeName;
    }

    private static Class<?> getStaticArrayTypeReferenceClass(String type) {
        try {
            return Class.forName("org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray" + type);
        } catch (ClassNotFoundException e) {
            // Unfortunately we can't encode it's length as a type if it's > 32.
            return StaticArray.class;
        }
    }

    private static String trimStorageDeclaration(String type) {
        if (type.endsWith(" storage") || type.endsWith(" memory")) {
            return type.split(" ")[0];
        } else {
            return type;
        }
    }

    private List<TypeName> buildReturnTypes(List<TypeName> outputParameterTypes) {
        List<TypeName> result = new ArrayList<>(outputParameterTypes.size());
        for (TypeName typeName : outputParameterTypes) {
            result.add(getWrapperType(typeName));
        }
        return result;
    }

    private static void buildVariableLengthReturnFunctionConstructor(
            MethodSpec.Builder methodBuilder,
            String functionName,
            String inputParameters,
            List<TypeName> outputParameterTypes)
            throws ClassNotFoundException {

        List<Object> objects = new ArrayList<>();
        objects.add(Function.class);
        objects.add(Function.class);
        objects.add(funcNameToConst(functionName));

        objects.add(Arrays.class);
        objects.add(Type.class);
        objects.add(inputParameters);

        objects.add(Arrays.class);
        objects.add(TypeReference.class);
        for (TypeName outputParameterType : outputParameterTypes) {
            objects.add(TypeReference.class);
            objects.add(outputParameterType);
        }

        String asListParams =
                Collection.join(outputParameterTypes, ", ", typeName -> "new $T<$T>() {}");

        methodBuilder.addStatement(
                "final $T function = new $T($N, \n$T.<$T>asList($L), \n$T"
                        + ".<$T<?>>asList("
                        + asListParams
                        + "))",
                objects.toArray());
    }

    private void buildTupleResultContainer(
            MethodSpec.Builder methodBuilder,
            ParameterizedTypeName tupleType,
            List<TypeName> outputParameterTypes)
            throws ClassNotFoundException {

        List<TypeName> typeArguments = tupleType.typeArguments;

        CodeBlock.Builder tupleConstructor = CodeBlock.builder();
        tupleConstructor
                .addStatement(
                        "$T results = executeCallMultipleValueReturn(function)",
                        ParameterizedTypeName.get(List.class, Type.class))
                .add("return new $T(", tupleType)
                .add("$>$>");

        String resultStringSimple = "\n($T) results.get($L)";
        if (useNativeJavaTypes) {
            resultStringSimple += ".getValue()";
        }

        String resultStringNativeList = "\nconvertToNative(($T) results.get($L).getValue())";

        int size = typeArguments.size();
        ClassName classList = ClassName.get(List.class);

        for (int i = 0; i < size; i++) {
            TypeName param = outputParameterTypes.get(i);
            TypeName convertTo = typeArguments.get(i);

            String resultString = resultStringSimple;

            // If we use native java types we need to convert
            // elements of arrays to native java types too
            if (useNativeJavaTypes && param instanceof ParameterizedTypeName) {
                ParameterizedTypeName oldContainer = (ParameterizedTypeName) param;
                ParameterizedTypeName newContainer = (ParameterizedTypeName) convertTo;
                if (newContainer.rawType.compareTo(classList) == 0
                        && newContainer.typeArguments.size() == 1) {
                    convertTo =
                            ParameterizedTypeName.get(classList, oldContainer.typeArguments.get(0));
                    resultString = resultStringNativeList;
                }
            }

            tupleConstructor.add(resultString, convertTo, i);
            tupleConstructor.add(i < size - 1 ? ", " : ");\n");
        }
        tupleConstructor.add("$<$<");

        TypeSpec callableType =
                TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(
                                ParameterizedTypeName.get(ClassName.get(Callable.class), tupleType))
                        .addMethod(
                                MethodSpec.methodBuilder("call")
                                        .addAnnotation(Override.class)
                                        .addModifiers(Modifier.PUBLIC)
                                        .addException(Exception.class)
                                        .returns(tupleType)
                                        .addCode(tupleConstructor.build())
                                        .build())
                        .build();

        methodBuilder.addStatement("return new $T(\n$L)", buildRemoteCall(tupleType), callableType);
    }

    private void buildTupleResultContainer0(
            MethodSpec.Builder methodBuilder,
            ParameterizedTypeName tupleType,
            List<TypeName> outputParameterTypes)
            throws ClassNotFoundException {

        List<TypeName> typeArguments = tupleType.typeArguments;

        CodeBlock.Builder codeBuilder = CodeBlock.builder();

        String resultStringSimple = "\n($T) results.get($L)";
        if (useNativeJavaTypes) {
            resultStringSimple += ".getValue()";
        }

        String resultStringNativeList = "\nconvertToNative(($T) results.get($L).getValue())";

        int size = typeArguments.size();
        ClassName classList = ClassName.get(List.class);

        for (int i = 0; i < size; i++) {
            TypeName param = outputParameterTypes.get(i);
            TypeName convertTo = typeArguments.get(i);

            String resultString = resultStringSimple;

            // If we use native java types we need to convert
            // elements of arrays to native java types too
            if (useNativeJavaTypes && param instanceof ParameterizedTypeName) {
                ParameterizedTypeName oldContainer = (ParameterizedTypeName) param;
                ParameterizedTypeName newContainer = (ParameterizedTypeName) convertTo;
                if (newContainer.rawType.compareTo(classList) == 0
                        && newContainer.typeArguments.size() == 1) {
                    convertTo =
                            ParameterizedTypeName.get(classList, oldContainer.typeArguments.get(0));
                    resultString = resultStringNativeList;
                }
            }

            codeBuilder.add(resultString, convertTo, i);
            codeBuilder.add(i < size - 1 ? ", " : "\n");
        }

        methodBuilder.addStatement("return new $T(\n$L)", tupleType, codeBuilder.build());
    }

    private static CodeBlock buildVariableLengthEventInitializer(
            String eventName, List<NamedTypeName> parameterTypes) {

        List<Object> objects = new ArrayList<>();
        objects.add(Event.class);
        objects.add(eventName);

        objects.add(Arrays.class);
        objects.add(TypeReference.class);
        for (NamedTypeName parameterType : parameterTypes) {
            objects.add(TypeReference.class);
            objects.add(parameterType.getTypeName());
        }

        String asListParams =
                parameterTypes
                        .stream()
                        .map(
                                type -> {
                                    if (type.isIndexed()) {
                                        return "new $T<$T>(true) {}";
                                    } else {
                                        return "new $T<$T>() {}";
                                    }
                                })
                        .collect(Collectors.joining(", "));

        return CodeBlock.builder()
                .addStatement(
                        "new $T($S, \n" + "$T.<$T<?>>asList(" + asListParams + "))",
                        objects.toArray())
                .build();
    }

    private List<AbiDefinition> loadContractDefinition(String abi) throws IOException {
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        AbiDefinition[] abiDefinition = objectMapper.readValue(abi, AbiDefinition[].class);
        return Arrays.asList(abiDefinition);
    }

    private static String funcNameToConst(String funcName) {
        return FUNC_NAME_PREFIX + funcName.toUpperCase();
    }

    private static class NamedTypeName {
        private final TypeName typeName;
        private final String name;
        private final boolean indexed;

        NamedTypeName(String name, TypeName typeName, boolean indexed) {
            this.name = name;
            this.typeName = typeName;
            this.indexed = indexed;
        }

        public String getName() {
            return name;
        }

        public TypeName getTypeName() {
            return typeName;
        }

        public boolean isIndexed() {
            return indexed;
        }
    }
}
