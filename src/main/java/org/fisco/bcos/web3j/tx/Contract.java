package org.fisco.bcos.web3j.tx;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.channel.event.filter.EventLogUserParams;
import org.fisco.bcos.fisco.EnumNodeVersion;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.EventValues;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.Web3jService;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.channel.StatusCode;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.JsonRpc2_0Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.Call;
import org.fisco.bcos.web3j.protocol.core.methods.response.Code;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.NodeVersion;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tx.exceptions.ContractCallException;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Solidity contract type abstraction for interacting with smart contracts via native Java types.
 */
public abstract class Contract extends ManagedTransaction {

    private static final Logger logger = LoggerFactory.getLogger(Contract.class);

    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    public static final String BIN_NOT_PROVIDED = "Bin file was not provided";
    public static final String FUNC_DEPLOY = "deploy";

    protected final String contractBinary;
    protected String contractAddress;
    protected ContractGasProvider gasProvider;
    protected TransactionReceipt transactionReceipt;
    protected DefaultBlockParameter defaultBlockParameter = DefaultBlockParameterName.LATEST;

    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider gasProvider) {
        super(web3j, transactionManager);
        this.contractAddress = cnsService.getAddressByContractNameAndVersion(contractAddress);
        this.contractBinary = contractBinary;
        this.gasProvider = gasProvider;
    }

    // ************
    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider gasProvider) {
        this(
                contractBinary,
                contractAddress,
                web3j,
                getTheTransactionManager(web3j, credentials),
                gasProvider);
    }

    public static TransactionManager getTheTransactionManager(
            Web3j web3j, Credentials credentials) {
        JsonRpc2_0Web3j jsonRpc2_0Web3j = (JsonRpc2_0Web3j) web3j;
        int groupId = jsonRpc2_0Web3j.getGroupId();
        String chainId = "1";
        String version = "";
        String supportedVersion = "";
        NodeVersion.Version nodeVersion = null;
        try {
            nodeVersion = web3j.getNodeVersion().send().getNodeVersion();
            version = nodeVersion.getVersion();
            supportedVersion = nodeVersion.getSupportedVersion();

            if (EnumNodeVersion.BCOS_2_0_0_RC1.getVersion().equals(version)
                    || EnumNodeVersion.BCOS_2_0_0_RC1.getVersion().equals(supportedVersion)) {
                version = EnumNodeVersion.BCOS_2_0_0_RC1.getVersion();
                logger.debug("fisco-bcos version:{}", version);
            } else {
                chainId = nodeVersion.getChainID();
                logger.debug(
                        "fisco-bcos version:{}, supported version:{}", version, supportedVersion);
            }
        } catch (IOException e) {
            logger.error("Query fisco-bcos version failed", e);
        }

        TransactionManager transactionManager =
                EnumNodeVersion.BCOS_2_0_0_RC1.getVersion().equals(version)
                        ? new RawTransactionManager(web3j, credentials)
                        : new ExtendedRawTransactionManager(
                                web3j,
                                credentials,
                                BigInteger.valueOf(groupId),
                                new BigInteger(chainId));

        transactionManager.setNodeVersion(nodeVersion);

        return transactionManager;
    }

    @Deprecated
    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this(
                contractBinary,
                contractAddress,
                web3j,
                transactionManager,
                new StaticGasProvider(gasPrice, gasLimit));
    }

    @Deprecated
    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this(
                contractBinary,
                contractAddress,
                web3j,
                getTheTransactionManager(web3j, credentials),
                gasPrice,
                gasLimit);
    }

    @Deprecated
    protected Contract(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this("", contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @Deprecated
    protected Contract(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this(
                "",
                contractAddress,
                web3j,
                getTheTransactionManager(web3j, credentials),
                gasPrice,
                gasLimit);
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    public String getContractBinary() {
        return contractBinary;
    }

    public void setGasProvider(ContractGasProvider gasProvider) {
        this.gasProvider = gasProvider;
    }

    /**
     * Allow {@code gasPrice} to be set.
     *
     * @param newPrice gas price to use for subsequent transactions
     * @deprecated use ContractGasProvider
     */
    public void setGasPrice(BigInteger newPrice) {
        this.gasProvider = new StaticGasProvider(newPrice, gasProvider.getGasLimit());
    }

    /**
     * Get the current {@code gasPrice} value this contract uses when executing transactions.
     *
     * @return the gas price set on this contract
     * @deprecated use ContractGasProvider
     */
    public BigInteger getGasPrice() {
        return gasProvider.getGasPrice();
    }

    /**
     * Check that the contract deployed at the address associated with this smart contract wrapper
     * is in fact the contract you believe it is.
     *
     * <p>This method uses the <a href=
     * "https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getcode">eth_getCode</a> method to get
     * the contract byte code and validates it against the byte code stored in this smart contract
     * wrapper.
     *
     * @return true if the contract is valid
     * @throws IOException if unable to connect to web3j node
     */
    public boolean isValid() throws IOException {
        if (contractBinary.equals(BIN_NOT_PROVIDED)) {
            throw new UnsupportedOperationException(
                    "Contract binary not present in contract wrapper, "
                            + "please generate your wrapper using -abiFile=<file>");
        }

        if (contractAddress.equals("")) {
            throw new UnsupportedOperationException(
                    "Contract binary not present, you will need to regenerate your smart "
                            + "contract wrapper with web3j v2.2.0+");
        }

        Code gcode = web3j.getCode(contractAddress, DefaultBlockParameterName.LATEST).send();
        if (gcode.hasError()) {
            return false;
        }

        String code = Numeric.cleanHexPrefix(gcode.getCode());
        // There may be multiple contracts in the Solidity bytecode, hence we only
        // check for a match with a subset
        return !code.isEmpty() && contractBinary.contains(code);
    }

    /**
     * If this Contract instance was created at deployment, the TransactionReceipt associated with
     * the initial creation will be provided, e.g. via a <em>deploy</em> method. This will not
     * persist for Contracts instances constructed via a <em>load</em> method.
     *
     * @return the TransactionReceipt generated at contract deployment
     */
    public Optional<TransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(transactionReceipt);
    }

    /**
     * Sets the default block parameter. This use useful if one wants to query historical state of a
     * contract.
     *
     * @param defaultBlockParameter the default block parameter
     */
    public void setDefaultBlockParameter(DefaultBlockParameter defaultBlockParameter) {
        this.defaultBlockParameter = defaultBlockParameter;
    }

    /**
     * Execute constant function call - i.e. a call that does not change state of the contract
     *
     * @param function to call
     * @return {@link List} of values returned by function call
     */
    private List<Type> executeCall(Function function) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        Call ethCall =
                web3j.call(
                                Transaction.createEthCallTransaction(
                                        transactionManager.getFromAddress(),
                                        contractAddress,
                                        encodedFunction),
                                defaultBlockParameter)
                        .send();

        String value = ethCall.getValue().getOutput();
        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type> T executeCallSingleValueReturn(Function function)
            throws IOException {
        List<Type> values = executeCall(function);
        if (!values.isEmpty()) {
            return (T) values.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type, R> R executeCallSingleValueReturn(
            Function function, Class<R> returnType) throws IOException {
        T result = executeCallSingleValueReturn(function);
        if (result == null) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }

        Object value = result.getValue();
        if (returnType.isAssignableFrom(value.getClass())) {
            return (R) value;
        } else if (result.getClass().equals(Address.class) && returnType.equals(String.class)) {
            return (R) result.toString(); // cast isn't necessary
        } else {
            throw new ContractCallException(
                    "Unable to convert response: "
                            + value
                            + " to expected type: "
                            + returnType.getSimpleName());
        }
    }

    protected List<Type> executeCallMultipleValueReturn(Function function) throws IOException {
        return executeCall(function);
    }

    class Callback extends TransactionSucCallback {
        Callback() {
            try {
                semaphore.acquire(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void onResponse(TransactionReceipt receipt) {
            this.receipt = receipt;
            semaphore.release();
        }

        public TransactionReceipt receipt;
        public Semaphore semaphore = new Semaphore(1, true);
    };

    protected TransactionReceipt executeTransaction(Function function)
            throws IOException, TransactionException {

        Callback callback = new Callback();

        asyncExecuteTransaction(FunctionEncoder.encode(function), function.getName(), callback);
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return callback.receipt;
    }

    /**
     * Given the duration required to execute a transaction.
     *
     * @param data to send in transaction
     * @param weiValue in Wei to send in transaction
     * @return {@link Optional} containing our transaction receipt
     * @throws IOException if the call to the node fails
     * @throws TransactionException if the transaction was not mined while waiting
     */
    protected TransactionReceipt executeTransaction(
            String data, BigInteger weiValue, String funcName)
            throws TransactionException, IOException {

        Callback callback = new Callback();

        asyncExecuteTransaction(data, funcName, callback);
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        TransactionReceipt receipt = callback.receipt;
        if (!receipt.isStatusOK()) {
            String status = receipt.getStatus();
            BigInteger gasUsed = receipt.getGasUsed();

            /* String message =
            String.format(
                    "Transaction has failed with status: %s. "
                            + "Gas used: %d. (not-enough gas?)",
                    status, gasUsed);*/
            String message =
                    StatusCode.getStatusMessage(receipt.getStatus(), receipt.getMessage())
                            + " .gas used: "
                            + gasUsed.toString();

            logger.trace(
                    " execute transaction not successfully, hash: {}, status: {}, message: {}, gasUsed: {}",
                    receipt.getTransactionHash(),
                    receipt.getStatus(),
                    receipt.getMessage(),
                    receipt.getGasUsed());

            throw new TransactionException(message, status, gasUsed, receipt.getTransactionHash());
        }

        return receipt;
    }

    protected void asyncExecuteTransaction(Function function, TransactionSucCallback callback) {

        try {
            asyncExecuteTransaction(FunctionEncoder.encode(function), function.getName(), callback);
        } catch (IOException e) {
            logger.error(
                    " IOException, contractAddress:{}, exception:{} ",
                    getContractAddress(),
                    e.getMessage());
        } catch (TransactionException e) {
            logger.error(
                    " TransactionException, contractAddress:{}, transactionHash: {}, transactionStatus:{}, exception:{} ",
                    getContractAddress(),
                    e.getTransactionHash(),
                    e.getStatus(),
                    e.getMessage());
        }

        // asyncExecuteTransaction(FunctionEncoder.encode(function), function.getName(), callback);
    }

    protected void asyncExecuteTransaction(
            String data, String funName, TransactionSucCallback callback)
            throws IOException, TransactionException {
        sendOnly(
                contractAddress,
                data,
                BigInteger.ZERO,
                gasProvider.getGasPrice(funName),
                gasProvider.getGasLimit(funName),
                callback);
    }

    protected String createTransactionSeq(Function function) {
        try {
            String signedTransaction =
                    createSeq(
                            contractAddress,
                            FunctionEncoder.encode(function),
                            BigInteger.ZERO,
                            gasProvider.getGasPrice(function.getName()),
                            gasProvider.getGasLimit(function.getName()));
            return signedTransaction;
        } catch (IOException e) {
            // e.print_Stack_Trace();
            logger.error(" IOException, message:{}", e.getMessage());
            return "";
        }
    }

    protected <T extends Type> RemoteCall<T> executeRemoteCallSingleValueReturn(Function function) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function));
    }

    protected <T> RemoteCall<T> executeRemoteCallSingleValueReturn(
            Function function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function, returnType));
    }

    protected RemoteCall<List<Type>> executeRemoteCallMultipleValueReturn(Function function) {
        return new RemoteCall<>(() -> executeCallMultipleValueReturn(function));
    }

    protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(Function function) {
        return new RemoteCall<>(() -> executeTransaction(function));
    }

    private static <T extends Contract> T create(
            T contract, String binary, String encodedConstructor, BigInteger value)
            throws IOException, TransactionException {

        TransactionReceipt transactionReceipt =
                contract.executeTransaction(binary + encodedConstructor, value, FUNC_DEPLOY);

        String contractAddress = transactionReceipt.getContractAddress();
        if (contractAddress == null) {
            throw new RuntimeException("Empty contract address returned");
        }
        contract.setContractAddress(contractAddress);
        contract.setTransactionReceipt(transactionReceipt);

        return contract;
    }

    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        try {
            Constructor<T> constructor =
                    type.getDeclaredConstructor(
                            String.class,
                            Web3j.class,
                            Credentials.class,
                            ContractGasProvider.class);
            constructor.setAccessible(true);

            // we want to use null here to ensure that "to" parameter on message is
            // not populated
            T contract = constructor.newInstance(null, web3j, credentials, contractGasProvider);

            return create(contract, binary, encodedConstructor, value);
        } catch (TransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        try {
            Constructor<T> constructor =
                    type.getDeclaredConstructor(
                            String.class,
                            Web3j.class,
                            TransactionManager.class,
                            ContractGasProvider.class);
            constructor.setAccessible(true);

            // we want to use null here to ensure that "to" parameter on message is
            // not populated
            T contract =
                    constructor.newInstance(null, web3j, transactionManager, contractGasProvider);
            return create(contract, binary, encodedConstructor, value);
        } catch (TransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        return deploy(
                type,
                web3j,
                credentials,
                new StaticGasProvider(gasPrice, gasLimit),
                binary,
                encodedConstructor,
                value);
    }

    @Deprecated
    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        return deploy(
                type,
                web3j,
                transactionManager,
                new StaticGasProvider(gasPrice, gasLimit),
                binary,
                encodedConstructor,
                value);
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                gasPrice,
                                gasLimit,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor) {
        return deployRemoteCall(
                type,
                web3j,
                credentials,
                gasPrice,
                gasLimit,
                binary,
                encodedConstructor,
                BigInteger.ZERO);
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                BigInteger.ZERO));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                transactionManager,
                                gasPrice,
                                gasLimit,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor) {
        return deployRemoteCall(
                type,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                binary,
                encodedConstructor,
                BigInteger.ZERO);
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                transactionManager,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                transactionManager,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                BigInteger.ZERO));
    }

    public void registerEventLogPushFilter(
            TransactionDecoder decoder,
            EventLogUserParams params,
            EventLogPushWithDecodeCallback callback) {

        Web3jService service = ((JsonRpc2_0Web3j) web3j).web3jService();

        ChannelEthereumService channelEthereumService = (ChannelEthereumService) service;
        // set timeout
        // filter.setTimeout(channelEthereumService.getTimeout());
        callback.setDecoder(decoder);

        channelEthereumService.getChannelService().registerEventLogFilter(params, callback);
    }

    public void registerEventLogPushFilter(
            String abi, String bin, String topic0, EventLogPushWithDecodeCallback callback) {
        registerEventLogPushFilter(
                abi,
                bin,
                topic0,
                new String(DefaultBlockParameterName.LATEST.getValue()),
                new String(DefaultBlockParameterName.LATEST.getValue()),
                new ArrayList<String>(),
                callback);
    }

    public void registerEventLogPushFilter(
            String abi,
            String bin,
            String topic0,
            String fromBlock,
            String toBlock,
            List<String> otherTopics,
            EventLogPushWithDecodeCallback callback) {

        EventLogUserParams filter = new EventLogUserParams();
        filter.setFromBlock(fromBlock);
        filter.setToBlock(toBlock);

        List<String> addresses = new ArrayList<String>();
        addresses.add(getContractAddress());
        filter.setAddresses(addresses);

        List<Object> topics = new ArrayList<Object>();
        topics.add(topic0);
        if (otherTopics != null) {
            for (Object obj : otherTopics) {
                topics.add(obj);
            }
        }

        filter.setTopics(topics);

        TransactionDecoder decoder = new TransactionDecoder(abi, bin);
        this.registerEventLogPushFilter(decoder, filter, callback);
    }

    public static EventValues staticExtractEventParameters(Event event, Log log) {

        List<String> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (!topics.get(0).equals(encodedEventSignature)) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues =
                FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value =
                    FunctionReturnDecoder.decodeIndexedValue(
                            topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }

    protected EventValues extractEventParameters(Event event, Log log) {
        return staticExtractEventParameters(event, log);
    }

    protected List<EventValues> extractEventParameters(
            Event event, TransactionReceipt transactionReceipt) {
        return transactionReceipt
                .getLogs()
                .stream()
                .map(log -> extractEventParameters(event, log))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected EventValuesWithLog extractEventParametersWithLog(Event event, Log log) {
        final EventValues eventValues = staticExtractEventParameters(event, log);
        return (eventValues == null) ? null : new EventValuesWithLog(eventValues, log);
    }

    protected List<EventValuesWithLog> extractEventParametersWithLog(
            Event event, TransactionReceipt transactionReceipt) {
        return transactionReceipt
                .getLogs()
                .stream()
                .map(log -> extractEventParametersWithLog(event, log))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected List<EventValuesWithLog> extractEventParametersWithLog(Event event, List<Log> logs) {
        return logs.stream()
                .map(log -> extractEventParametersWithLog(event, log))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /** Adds a log field to {@link EventValues}. */
    public static class EventValuesWithLog {
        private final EventValues eventValues;
        private final Log log;

        private EventValuesWithLog(EventValues eventValues, Log log) {
            this.eventValues = eventValues;
            this.log = log;
        }

        public List<Type> getIndexedValues() {
            return eventValues.getIndexedValues();
        }

        public List<Type> getNonIndexedValues() {
            return eventValues.getNonIndexedValues();
        }

        public Log getLog() {
            return log;
        }
    }

    @SuppressWarnings("unchecked")
    public static <S extends Type, T> List<T> convertToNative(List<S> arr) {
        List<T> out = new ArrayList<T>();
        for (Iterator<S> it = arr.iterator(); it.hasNext(); ) {
            out.add((T) it.next().getValue());
        }
        return out;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
