package org.bcos.web3j.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bcos.web3j.protocol.core.methods.request.ProofMerkle;
import org.bcos.web3j.protocol.core.methods.response.*;
import org.bcos.web3j.utils.BlockLimit;
import org.bcos.web3j.utils.Web3AsyncThreadPoolSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.Web3jService;
import org.bcos.web3j.protocol.core.methods.request.ShhFilter;
import org.bcos.web3j.protocol.core.methods.request.ShhPost;
import org.bcos.web3j.protocol.core.methods.request.Transaction;
import org.bcos.web3j.protocol.core.methods.response.DbGetHex;
import org.bcos.web3j.protocol.core.methods.response.DbGetString;
import org.bcos.web3j.protocol.core.methods.response.DbPutHex;
import org.bcos.web3j.protocol.core.methods.response.DbPutString;
import org.bcos.web3j.protocol.core.methods.response.EthAccounts;
import org.bcos.web3j.protocol.core.methods.response.EthBlock;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;

//增加eth_pbftView接口
import org.bcos.web3j.protocol.core.methods.response.EthPbftView;
import org.bcos.web3j.protocol.core.methods.response.EthCall;
import org.bcos.web3j.protocol.core.methods.response.EthCoinbase;
import org.bcos.web3j.protocol.core.methods.response.EthCompileLLL;
import org.bcos.web3j.protocol.core.methods.response.EthCompileSerpent;
import org.bcos.web3j.protocol.core.methods.response.EthCompileSolidity;
import org.bcos.web3j.protocol.core.methods.response.EthEstimateGas;
import org.bcos.web3j.protocol.core.methods.response.EthFilter;
import org.bcos.web3j.protocol.core.methods.response.EthGasPrice;
import org.bcos.web3j.protocol.core.methods.response.EthGetBalance;
import org.bcos.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.bcos.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.bcos.web3j.protocol.core.methods.response.EthGetCode;
import org.bcos.web3j.protocol.core.methods.response.EthGetCompilers;
import org.bcos.web3j.protocol.core.methods.response.EthGetStorageAt;
import org.bcos.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.bcos.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.bcos.web3j.protocol.core.methods.response.EthGetUncleCountByBlockHash;
import org.bcos.web3j.protocol.core.methods.response.EthGetUncleCountByBlockNumber;
import org.bcos.web3j.protocol.core.methods.response.EthGetWork;
import org.bcos.web3j.protocol.core.methods.response.EthHashrate;
import org.bcos.web3j.protocol.core.methods.response.EthLog;
import org.bcos.web3j.protocol.core.methods.response.EthMining;
import org.bcos.web3j.protocol.core.methods.response.EthProtocolVersion;
import org.bcos.web3j.protocol.core.methods.response.EthSign;
import org.bcos.web3j.protocol.core.methods.response.EthSubmitHashrate;
import org.bcos.web3j.protocol.core.methods.response.EthSubmitWork;
import org.bcos.web3j.protocol.core.methods.response.EthSyncing;
import org.bcos.web3j.protocol.core.methods.response.EthTransaction;
import org.bcos.web3j.protocol.core.methods.response.EthUninstallFilter;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.NetListening;
import org.bcos.web3j.protocol.core.methods.response.NetPeerCount;
import org.bcos.web3j.protocol.core.methods.response.NetVersion;
import org.bcos.web3j.protocol.core.methods.response.ShhAddToGroup;
import org.bcos.web3j.protocol.core.methods.response.ShhHasIdentity;
import org.bcos.web3j.protocol.core.methods.response.ShhMessages;
import org.bcos.web3j.protocol.core.methods.response.ShhNewFilter;
import org.bcos.web3j.protocol.core.methods.response.ShhNewGroup;
import org.bcos.web3j.protocol.core.methods.response.ShhNewIdentity;
import org.bcos.web3j.protocol.core.methods.response.ShhUninstallFilter;
import org.bcos.web3j.protocol.core.methods.response.ShhVersion;
import org.bcos.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.bcos.web3j.protocol.core.methods.response.Web3Sha3;
import org.bcos.web3j.protocol.rx.JsonRpc2_0Rx;
import org.bcos.web3j.utils.Async;
import org.bcos.web3j.utils.Numeric;

/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0Web3j implements Web3j {
    static Logger logger = LoggerFactory.getLogger(JsonRpc2_0Web3j.class);
    protected static final long ID = 1;
    static final int BLOCK_TIME = 15 * 100;

    protected final Web3jService web3jService;
    private final JsonRpc2_0Rx web3jRx;
    private final long blockTime;

    synchronized public BigInteger getBlockNumber() {
        return blockNumber;
    }

    synchronized public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    private BigInteger blockNumber = new BigInteger("1");

    public JsonRpc2_0Web3j(Web3jService web3jService) {
        this(web3jService, BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0Web3j(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.web3jService = web3jService;
        this.web3jRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(Web3AsyncThreadPoolSize.web3AsyncPoolSize);

        cachedThreadPool.execute(new Runnable() {
            public void run() {
                while (true) {
                    try {
                    	Thread.sleep(10000);
                        EthBlockNumber ethBlockNumber = ethBlockNumber().sendAsync().get(10000, TimeUnit.MILLISECONDS);
                        setBlockNumber(ethBlockNumber.getBlockNumber());
                    } catch (Exception e) {
                        logger.error("Exception: " + e);
                    }
                }
            }
        });
    }

    @Override
    public BigInteger getBlockNumberCache() {
        if (getBlockNumber().intValue() == 1)
        {
            try {
                EthBlockNumber ethBlockNumber = ethBlockNumber().sendAsync().get();
                setBlockNumber(ethBlockNumber.getBlockNumber());
            } catch (Exception e) {
                logger.error("Exception: " + e);
            }
        }
        return getBlockNumber().add(new BigInteger(BlockLimit.blockLimit.toString()));
    }

    @Override
    public Request<?, EthGetProofMerkle> ethGetProofMerkle(ProofMerkle proofMerkle) {
        return new Request<>(
                "eth_getProofMerkle",
                Arrays.asList(proofMerkle.getBlockHash(), proofMerkle.getTransactionIndex()),
                ID,
                web3jService,
                EthGetProofMerkle.class);
    }

    @Override
    public Request<?, Web3ClientVersion> web3ClientVersion() {
        return new Request<>(
                "web3_clientVersion",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                Web3ClientVersion.class);
    }

    @Override
    public Request<?, Web3Sha3> web3Sha3(String data) {
        return new Request<>(
                "web3_sha3",
                Arrays.asList(data),
                ID,
                web3jService,
                Web3Sha3.class);
    }

    @Override
    public Request<?, EthPeers> getAdminPeers() {
        return new Request<>(
                "admin_peers",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthPeers.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<>(
                "net_version",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                NetVersion.class);
    }

    @Override
    public Request<?, NetListening> netListening() {
        return new Request<>(
                "net_listening",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                NetListening.class);
    }

    @Override
    public Request<?, NetPeerCount> netPeerCount() {
        return new Request<>(
                "net_peerCount",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                NetPeerCount.class);
    }

    @Override
    public Request<?, EthProtocolVersion> ethProtocolVersion() {
        return new Request<>(
                "eth_protocolVersion",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthProtocolVersion.class);
    }

    @Override
    public Request<?, EthCoinbase> ethCoinbase() {
        return new Request<>(
                "eth_coinbase",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthCoinbase.class);
    }

    @Override
    public Request<?, EthSyncing> ethSyncing() {
        return new Request<>(
                "eth_syncing",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthSyncing.class);
    }

    @Override
    public Request<?, EthMining> ethMining() {
        return new Request<>(
                "eth_mining",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthMining.class);
    }

    @Override
    public Request<?, EthHashrate> ethHashrate() {
        return new Request<>(
                "eth_hashrate",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthHashrate.class);
    }

    @Override
    public Request<?, EthGasPrice> ethGasPrice() {
        return new Request<>(
                "eth_gasPrice",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthGasPrice.class);
    }

    @Override
    public Request<?, EthAccounts> ethAccounts() {
        return new Request<>(
                "eth_accounts",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthAccounts.class);
    }

    @Override
    public Request<?, EthBlockNumber> ethBlockNumber() {
        return new Request<>(
                "eth_blockNumber",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthBlockNumber.class);
    }
    
    //增加eth_pbftView接口
    @Override
    public Request<?, EthPbftView> ethPbftView() {
        return new Request<>(
                "eth_pbftView",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthPbftView.class);
    }

    @Override
    public Request<?, EthGetBalance> ethGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getBalance",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetBalance.class);
    }

    @Override
    public Request<?, EthGetBalance> ethGetBalanceCNS(
            String contractName, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getBalanceCNS",
                Arrays.asList(contractName, defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetBalance.class);
    }

    @Override
    public Request<?, EthGetStorageAt> ethGetStorageAt(
            String address, BigInteger position, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getStorageAt",
                Arrays.asList(
                        address,
                        Numeric.encodeQuantity(position),
                        defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetStorageAt.class);
    }

    @Override
    public Request<?, EthGetStorageAt> ethGetStorageAtCNS(
            String contractName, BigInteger position, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getStorageAtCNS",
                Arrays.asList(
                        contractName,
                        Numeric.encodeQuantity(position),
                        defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetStorageAt.class);
    }

    @Override
    public Request<?, EthGetTransactionCount> ethGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetTransactionCount.class);
    }

    @Override
    public Request<?, EthGetTransactionCount> ethGetTransactionCountCNS(
            String contractName, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getTransactionCountCNS",
                Arrays.asList(contractName, defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetTransactionCount.class);
    }

    @Override
    public Request<?, EthGetBlockTransactionCountByHash> ethGetBlockTransactionCountByHash(
            String blockHash) {
        return new Request<>(
                "eth_getBlockTransactionCountByHash",
                Arrays.asList(blockHash),
                ID,
                web3jService,
                EthGetBlockTransactionCountByHash.class);
    }

    @Override
    public Request<?, EthGetBlockTransactionCountByNumber> ethGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getBlockTransactionCountByNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetBlockTransactionCountByNumber.class);
    }

    @Override
    public Request<?, EthGetUncleCountByBlockHash> ethGetUncleCountByBlockHash(String blockHash) {
        return new Request<>(
                "eth_getUncleCountByBlockHash",
                Arrays.asList(blockHash),
                ID,
                web3jService,
                EthGetUncleCountByBlockHash.class);
    }

    @Override
    public Request<?, EthGetUncleCountByBlockNumber> ethGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getUncleCountByBlockNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetUncleCountByBlockNumber.class);
    }

    @Override
    public Request<?, EthGetCode> ethGetCode(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getCode",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetCode.class);
    }

    @Override
    public Request<?, EthGetCode> ethGetCodeCNS(
            String contractName, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "eth_getCodeCNS",
                Arrays.asList(contractName, defaultBlockParameter.getValue()),
                ID,
                web3jService,
                EthGetCode.class);
    }

    @Override
    public Request<?, EthSign> ethSign(String address, String sha3HashOfDataToSign) {
        return new Request<>(
                "eth_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                ID,
                web3jService,
                EthSign.class);
    }

    @Override
    public Request<?, org.bcos.web3j.protocol.core.methods.response.EthSendTransaction>
            ethSendTransaction(
            Transaction transaction) {
        return new Request<>(
                "eth_sendTransaction",
                Arrays.asList(transaction),
                ID,
                web3jService,
                org.bcos.web3j.protocol.core.methods.response.EthSendTransaction.class);
    }

    @Override
    public Request<?, org.bcos.web3j.protocol.core.methods.response.EthSendTransaction>
    ethSendTransactionCNS(String contractName,
            Transaction transaction) {
        return new Request<>(
                "eth_sendTransactionCNS",
                Arrays.asList(contractName,transaction),
                ID,
                web3jService,
                org.bcos.web3j.protocol.core.methods.response.EthSendTransaction.class);
    }

    @Override
    public Request<?, org.bcos.web3j.protocol.core.methods.response.EthSendTransaction>
            ethSendRawTransaction(
            String signedTransactionData) {
        return new Request<>(
                "eth_sendRawTransaction",
                Arrays.asList(signedTransactionData),
                ID,
                web3jService,
                org.bcos.web3j.protocol.core.methods.response.EthSendTransaction.class);
    }

    @Override
    public Request<?, org.bcos.web3j.protocol.core.methods.response.EthCall> ethCall(
            Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<Object, EthCall>(
                "eth_call",
                Arrays.asList(transaction, defaultBlockParameter),
                ID,
                web3jService,
                org.bcos.web3j.protocol.core.methods.response.EthCall.class);
    }

    @Override
    public Request<?, org.bcos.web3j.protocol.core.methods.response.EthCall> ethCallCNS(
            String contractName,
            Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<Object, EthCall>(
                "eth_callCNS",
                Arrays.asList(contractName,transaction, defaultBlockParameter),
                ID,
                web3jService,
                org.bcos.web3j.protocol.core.methods.response.EthCall.class);
    }

    @Override
    public Request<?, EthEstimateGas> ethEstimateGas(Transaction transaction) {
        return new Request<>(
                "eth_estimateGas",
                Arrays.asList(transaction),
                ID,
                web3jService,
                EthEstimateGas.class);
    }

    @Override
    public Request<?, EthBlock> ethGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "eth_getBlockByHash",
                Arrays.asList(
                        blockHash,
                        returnFullTransactionObjects),
                ID,
                web3jService,
                EthBlock.class);
    }

    @Override
    public Request<?, EthBlock> ethGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) {
        return new Request<>(
                "eth_getBlockByNumber",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        returnFullTransactionObjects),
                ID,
                web3jService,
                EthBlock.class);
    }

    @Override
    public Request<?, EthTransaction> ethGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "eth_getTransactionByHash",
                Arrays.asList(transactionHash),
                ID,
                web3jService,
                EthTransaction.class);
    }

    @Override
    public Request<?, EthTransaction> ethGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "eth_getTransactionByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                ID,
                web3jService,
                EthTransaction.class);
    }

    @Override
    public Request<?, EthTransaction> ethGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<>(
                "eth_getTransactionByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex)),
                ID,
                web3jService,
                EthTransaction.class);
    }

    @Override
    public Request<?, EthGetTransactionReceipt> ethGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "eth_getTransactionReceipt",
                Arrays.asList(transactionHash),
                ID,
                web3jService,
                EthGetTransactionReceipt.class);
    }

    @Override
    public Request<?, EthBlock> ethGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "eth_getUncleByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                ID,
                web3jService,
                EthBlock.class);
    }

    @Override
    public Request<?, EthBlock> ethGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger uncleIndex) {
        return new Request<>(
                "eth_getUncleByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(uncleIndex)),
                ID,
                web3jService,
                EthBlock.class);
    }

    @Override
    public Request<?, EthGetCompilers> ethGetCompilers() {
        return new Request<>(
                "eth_getCompilers",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthGetCompilers.class);
    }

    @Override
    public Request<?, EthCompileLLL> ethCompileLLL(String sourceCode) {
        return new Request<>(
                "eth_compileLLL",
                Arrays.asList(sourceCode),
                ID,
                web3jService,
                EthCompileLLL.class);
    }

    @Override
    public Request<?, EthCompileSolidity> ethCompileSolidity(String sourceCode) {
        return new Request<>(
                "eth_compileSolidity",
                Arrays.asList(sourceCode),
                ID,
                web3jService,
                EthCompileSolidity.class);
    }

    @Override
    public Request<?, EthCompileSerpent> ethCompileSerpent(String sourceCode) {
        return new Request<>(
                "eth_compileSerpent",
                Arrays.asList(sourceCode),
                ID,
                web3jService,
                EthCompileSerpent.class);
    }

    @Override
    public Request<?, EthFilter> ethNewFilter(
            org.bcos.web3j.protocol.core.methods.request.EthFilter ethFilter) {
        return new Request<>(
                "eth_newFilter",
                Arrays.asList(ethFilter),
                ID,
                web3jService,
                EthFilter.class);
    }

    @Override
    public Request<?, EthFilter> ethNewBlockFilter() {
        return new Request<>(
                "eth_newBlockFilter",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthFilter.class);
    }

    @Override
    public Request<?, EthFilter> ethNewPendingTransactionFilter() {
        return new Request<>(
                "eth_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthFilter.class);
    }

    @Override
    public Request<?, EthUninstallFilter> ethUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "eth_uninstallFilter",
                Arrays.asList(Numeric.encodeQuantity(filterId)),
                ID,
                web3jService,
                EthUninstallFilter.class);
    }

    @Override
    public Request<?, EthLog> ethGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "eth_getFilterChanges",
                Arrays.asList(Numeric.encodeQuantity(filterId)),
                ID,
                web3jService,
                EthLog.class);
    }

    @Override
    public Request<?, EthLog> ethGetFilterLogs(BigInteger filterId) {
        return new Request<>(
                "eth_getFilterLogs",
                Arrays.asList(Numeric.encodeQuantity(filterId)),
                ID,
                web3jService,
                EthLog.class);
    }

    @Override
    public Request<?, EthLog> ethGetLogs(
            org.bcos.web3j.protocol.core.methods.request.EthFilter ethFilter) {
        return new Request<>(
                "eth_getLogs",
                Arrays.asList(ethFilter),
                ID,
                web3jService,
                EthLog.class);
    }

    @Override
    public Request<?, EthGetWork> ethGetWork() {
        return new Request<>(
                "eth_getWork",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                EthGetWork.class);
    }

    @Override
    public Request<?, EthSubmitWork> ethSubmitWork(
            String nonce, String headerPowHash, String mixDigest) {
        return new Request<>(
                "eth_submitWork",
                Arrays.asList(nonce, headerPowHash, mixDigest),
                ID,
                web3jService,
                EthSubmitWork.class);
    }

    @Override
    public Request<?, EthSubmitHashrate> ethSubmitHashrate(String hashrate, String clientId) {
        return new Request<>(
                "eth_submitHashrate",
                Arrays.asList(hashrate, clientId),
                ID,
                web3jService,
                EthSubmitHashrate.class);
    }

    @Override
    public Request<?, DbPutString> dbPutString(
            String databaseName, String keyName, String stringToStore) {
        return new Request<>(
                "db_putString",
                Arrays.asList(databaseName, keyName, stringToStore),
                ID,
                web3jService,
                DbPutString.class);
    }

    @Override
    public Request<?, DbGetString> dbGetString(String databaseName, String keyName) {
        return new Request<>(
                "db_getString",
                Arrays.asList(databaseName, keyName),
                ID,
                web3jService,
                DbGetString.class);
    }

    @Override
    public Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore) {
        return new Request<>(
                "db_putHex",
                Arrays.asList(databaseName, keyName, dataToStore),
                ID,
                web3jService,
                DbPutHex.class);
    }

    @Override
    public Request<?, DbGetHex> dbGetHex(String databaseName, String keyName) {
        return new Request<>(
                "db_getHex",
                Arrays.asList(databaseName, keyName),
                ID,
                web3jService,
                DbGetHex.class);
    }

    @Override
    public Request<?, org.bcos.web3j.protocol.core.methods.response.ShhPost> shhPost(ShhPost shhPost) {
        return new Request<>(
                "shh_post",
                Arrays.asList(shhPost),
                ID,
                web3jService,
                org.bcos.web3j.protocol.core.methods.response.ShhPost.class);
    }

    @Override
    public Request<?, ShhVersion> shhVersion() {
        return new Request<>(
                "shh_version",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                ShhVersion.class);
    }

    @Override
    public Request<?, ShhNewIdentity> shhNewIdentity() {
        return new Request<>(
                "shh_newIdentity",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                ShhNewIdentity.class);
    }

    @Override
    public Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress) {
        return new Request<>(
                "shh_hasIdentity",
                Arrays.asList(identityAddress),
                ID,
                web3jService,
                ShhHasIdentity.class);
    }

    @Override
    public Request<?, ShhNewGroup> shhNewGroup() {
        return new Request<>(
                "shh_newGroup",
                Collections.<String>emptyList(),
                ID,
                web3jService,
                ShhNewGroup.class);
    }

    @Override
    public Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress) {
        return new Request<>(
                "shh_addToGroup",
                Arrays.asList(identityAddress),
                ID,
                web3jService,
                ShhAddToGroup.class);
    }

    @Override
    public Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter) {
        return new Request<>(
                "shh_newFilter",
                Arrays.asList(shhFilter),
                ID,
                web3jService,
                ShhNewFilter.class);
    }

    @Override
    public Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "shh_uninstallFilter",
                Arrays.asList(Numeric.encodeQuantity(filterId)),
                ID,
                web3jService,
                ShhUninstallFilter.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "shh_getFilterChanges",
                Arrays.asList(Numeric.encodeQuantity(filterId)),
                ID,
                web3jService,
                ShhMessages.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetMessages(BigInteger filterId) {
        return new Request<>(
                "shh_getMessages",
                Arrays.asList(Numeric.encodeQuantity(filterId)),
                ID,
                web3jService,
                ShhMessages.class);
    }

    @Override
    public Observable<String> ethBlockHashObservable() {
        return web3jRx.ethBlockHashObservable(blockTime);
    }

    @Override
    public Observable<String> ethPendingTransactionHashObservable() {
        return web3jRx.ethPendingTransactionHashObservable(blockTime);
    }

    @Override
    public Observable<Log> ethLogObservable(
            org.bcos.web3j.protocol.core.methods.request.EthFilter ethFilter) {
        return web3jRx.ethLogObservable(ethFilter, blockTime);
    }

    @Override
    public Observable<org.bcos.web3j.protocol.core.methods.response.Transaction>
            transactionObservable() {
        return web3jRx.transactionObservable(blockTime);
    }

    @Override
    public Observable<org.bcos.web3j.protocol.core.methods.response.Transaction>
            pendingTransactionObservable() {
        return web3jRx.pendingTransactionObservable(blockTime);
    }

    @Override
    public Observable<EthBlock> blockObservable(boolean fullTransactionObjects) {
        return web3jRx.blockObservable(fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<EthBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return web3jRx.replayBlocksObservable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Observable<org.bcos.web3j.protocol.core.methods.response.Transaction>
            replayTransactionsObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return web3jRx.replayTransactionsObservable(startBlock, endBlock);
    }

    @Override
    public Observable<EthBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Observable<EthBlock> onCompleteObservable) {
        return web3jRx.catchUpToLatestBlockObservable(
                startBlock, fullTransactionObjects, onCompleteObservable);
    }

    @Override
    public Observable<EthBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestBlockObservable(startBlock, fullTransactionObjects);
    }

    @Override
    public Observable<org.bcos.web3j.protocol.core.methods.response.Transaction>
            catchUpToLatestTransactionObservable(DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestTransactionObservable(startBlock);
    }

    @Override
    public Observable<EthBlock> catchUpToLatestAndSubscribeToNewBlocksObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestAndSubscribeToNewBlocksObservable(
                startBlock, fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<org.bcos.web3j.protocol.core.methods.response.Transaction>
            catchUpToLatestAndSubscribeToNewTransactionsObservable(
            DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestAndSubscribeToNewTransactionsObservable(
                startBlock, blockTime);
    }
}
