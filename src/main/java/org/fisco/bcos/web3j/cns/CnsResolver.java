package org.fisco.bcos.web3j.cns;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.WalletUtils;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthSyncing;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.ClientTransactionManager;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.DefaultGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Resolution logic for contract addresses.
 */
public class CnsResolver {
    static Logger logger = LoggerFactory.getLogger(CnsResolver.class);
    static final long DEFAULT_SYNC_THRESHOLD = 1000 * 60 * 3;
    static final String REVERSE_NAME_SUFFIX = ".addr.reverse";

    private final Web3j web3j;
    private final TransactionManager transactionManager;
    private long syncThreshold;  // non-final in case this value needs to be tweaked
    private static String registryContract = "0x0000000000000000000000000000000000001004";

    private Cns cnsRegistry;
    public CnsResolver(Web3j web3j, long syncThreshold, Credentials credentials) {
        this.web3j = web3j;
        transactionManager = new ClientTransactionManager(web3j, credentials);  // don't use empty string
        this.syncThreshold = syncThreshold;
    }

    public CnsResolver(Web3j web3j,Credentials credentials) {
        this(web3j, DEFAULT_SYNC_THRESHOLD,credentials);
    }

    public void setSyncThreshold(long syncThreshold) {
        this.syncThreshold = syncThreshold;
    }

    public long getSyncThreshold() {
        return syncThreshold;
    }

//    /**
//     * Provides an access to a valid public resolver in order to access other API methods.
//     * @param ensName our user input ENS name
//     * @return PublicResolver
//     */
//    public PublicResolver obtainPublicResolver(String ensName) {
//        if (isValidCnsName(ensName)) {
//            try {
//                if (!isSynced()) {
//                    throw new CnsResolutionException("Node is not currently synced");
//                } else {
//                    return lookupResolver(ensName);
//                }
//            } catch (Exception e) {
//                throw new CnsResolutionException("Unable to determine sync status of node", e);
//            }
//
//        } else {
//            throw new CnsResolutionException("EnsName is invalid: " + ensName);
//        }
//    }

    public String resolve(String contractNameAndVersion) {

        if (contractNameAndVersion!= null &&WalletUtils.isValidAddress(contractNameAndVersion)) {
            return contractNameAndVersion;
        } else {
            if (contractNameAndVersion != null && (contractNameAndVersion.contains(":"))) {
                String contractName = contractNameAndVersion.split(":")[0];
                String contractVersion = contractNameAndVersion.split(":")[1];
                String contractAddressInfo = null;
                String address;
                Cns cns;
                try {
                    cns = lookupResolver();
                    contractAddressInfo = cns.selectByName(contractName).send();
                    logger.debug("get contractName ", contractAddressInfo);
                    List<Contracts> contracts = jsonToContracts(contractAddressInfo);
                   Contracts c = contracts.stream().filter(x -> x.getVersion().equals(contractVersion)).findFirst().get();
                    address = c.getAddress();
                } catch (Exception e) {
                    throw new RuntimeException("Unable to execute Ethereum request", e);
                }

                if (!WalletUtils.isValidAddress(address)) {
                    throw new RuntimeException("Unable to resolve address for name: " + contractNameAndVersion);
                } else {
                    return address;
                }
            }
            else
                return null;
        }
    }

    public TransactionReceipt registerCns(String name, String version, String addr, String abi) throws Exception {
        Cns cns = lookupResolver();
        TransactionReceipt receipt = cns.insert(name, version, addr, abi).send();
        if(receipt.getOutput()== "0") {
            throw new CnsResolutionException("Cannot register: " + name + ":" + version );
        }
        return receipt;
    }


    private List<Contracts> jsonToContracts(String contractAddressInfo) throws IOException {

        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        List<Contracts> contracts = objectMapper.readValue(contractAddressInfo, objectMapper.getTypeFactory().constructCollectionType(List.class, Contracts.class));
        return contracts;
    }

//    /**
//     * Reverse name resolution as documented in the
//     * <a href="https://docs.ens.domains/en/latest/userguide.html#reverse-name-resolution">specification</a>.
//     * @param address an ethereum address, example: "0x314159265dd8dbb310642f98f50c066173c1259b"
//     * @return a EnsName registered for provided address
//     */
//    public String reverseResolve(String address) {
//        if (WalletUtils.isValidAddress(address)) {
//            String reverseName = Numeric.cleanHexPrefix(address) + REVERSE_NAME_SUFFIX;
//            PublicResolver resolver = obtainPublicResolver(reverseName);
//
//            byte[] nameHash = NameHash.nameHashAsBytes(reverseName);
//            String name = null;
//            try {
//                name = resolver.name(nameHash).send();
//            } catch (Exception e) {
//                throw new RuntimeException("Unable to execute Ethereum request", e);
//            }
//
//            if (!isValidCnsName(name)) {
//                throw new RuntimeException("Unable to resolve name for address: " + address);
//            } else {
//                return name;
//            }
//        } else {
//            throw new CnsResolutionException("Address is invalid: " + address);
//        }
//    }

    public Cns lookupResolver() {

        if (this.cnsRegistry == null) {
            Cns cnsRegistry = Cns.load(
                    registryContract, web3j, transactionManager,
                    new StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT));
            this.cnsRegistry = cnsRegistry;
        }
        return this.cnsRegistry;
    }

    boolean isSynced() throws Exception {
        EthSyncing ethSyncing = web3j.ethSyncing().send();
        if (ethSyncing.isSyncing()) {
            return false;
        } else {
            EthBlock ethBlock =
                    web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            long timestamp = ethBlock.getBlock().getTimestamp().longValueExact() * 1000;

            return System.currentTimeMillis() - syncThreshold < timestamp;
        }
    }

    public static boolean isValidCnsName(String input) {
        return input != null  // will be set to null on new Contract creation
                && (input.contains(":") || !WalletUtils.isValidAddress(input));
    }
}
