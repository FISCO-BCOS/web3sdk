package org.fisco.bcos.web3j.precompile.cns;

import java.io.IOException;
import java.util.List;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.WalletUtils;
import org.fisco.bcos.web3j.precompile.common.PrecompiledCommon;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock;
import org.fisco.bcos.web3j.protocol.core.methods.response.SyncStatus;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.RawTransactionManager;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.DefaultGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/** Resolution logic for contract addresses. */
public class CnsService {
  private static Logger logger = LoggerFactory.getLogger(CnsService.class);
  private static final long DEFAULT_SYNC_THRESHOLD = 1000 * 60 * 3L;
  public static final int MAX_VERSION_LENGTH = 40;

  private final Web3j web3j;
  private final TransactionManager transactionManager;
  private long syncThreshold; // non-final in case this value needs to be tweaked
  private static String registryContract = "0x0000000000000000000000000000000000001004";

  private CNS cnsRegistry;

  public CnsService(Web3j web3j, long syncThreshold, Credentials credentials) {
    this.web3j = web3j;
    transactionManager = new RawTransactionManager(web3j, credentials);
    this.syncThreshold = syncThreshold;
  }

  public CnsService(Web3j web3j, Credentials credentials) {
    this(web3j, DEFAULT_SYNC_THRESHOLD, credentials);
  }

  public void setSyncThreshold(long syncThreshold) {
    this.syncThreshold = syncThreshold;
  }

  public long getSyncThreshold() {
    return syncThreshold;
  }

  public String getAddressByContractNameAndVersion(String contractNameAndVersion) {

    if (!isValidCnsName(contractNameAndVersion)) {
      return contractNameAndVersion;
    }
    CNS cns;
    cns = lookupResolver();
    String contractAddressInfo;
    String address;

    try {
      // if has version
      if (contractNameAndVersion.contains(":")) {
        String contractName = contractNameAndVersion.split(":")[0];
        String contractVersion = contractNameAndVersion.split(":")[1];

        contractAddressInfo = cns.selectByNameAndVersion(contractName, contractVersion).send();
        logger.debug("get contractName ", contractAddressInfo);
        List<CnsInfo> cNSInfos = jsonToCNSInfos(contractAddressInfo);
        address = cNSInfos.get(0).getAddress();
      } else {
        // only contract name
        contractAddressInfo = cns.selectByName(contractNameAndVersion).send();
        logger.debug("get contractName ", contractAddressInfo);
        List<CnsInfo> CNSInfos = jsonToCNSInfos(contractAddressInfo);
        CnsInfo c = CNSInfos.get(CNSInfos.size() - 1);
        address = c.getAddress();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    if (!WalletUtils.isValidAddress(address)) {
      throw new RuntimeException("Unable to resolve address for name: " + contractNameAndVersion);
    } else {
      return address;
    }
  }

  public String registerCns(String name, String version, String address, String abi)
      throws Exception {
    CNS cns = lookupResolver();
    if (version.length() > MAX_VERSION_LENGTH) {
      return PrecompiledCommon.transferToJson(PrecompiledCommon.VersionExceeds);
    }
    TransactionReceipt receipt = cns.insert(name, version, address, abi).send();
    return PrecompiledCommon.handleTransactionReceipt(receipt);
  }

  public List<CnsInfo> queryCnsByName(String name) throws Exception {
    CNS cns = lookupResolver();
    String cnsInfo = cns.selectByName(name).send();
    ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    return objectMapper.readValue(
        cnsInfo, objectMapper.getTypeFactory().constructCollectionType(List.class, CnsInfo.class));
  }

  public List<CnsInfo> queryCnsByNameAndVersion(String name, String version) throws Exception {
    CNS cns = lookupResolver();
    String cnsInfo = cns.selectByNameAndVersion(name, version).send();
    ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    return objectMapper.readValue(
        cnsInfo, objectMapper.getTypeFactory().constructCollectionType(List.class, CnsInfo.class));
  }

  private List<CnsInfo> jsonToCNSInfos(String contractAddressInfo) throws IOException {

    ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    List<CnsInfo> cnsInfo =
        objectMapper.readValue(
            contractAddressInfo,
            objectMapper.getTypeFactory().constructCollectionType(List.class, CnsInfo.class));
    return cnsInfo;
  }

  public CNS lookupResolver() {

    if (this.cnsRegistry == null) {
      CNS cnsRegistry =
          CNS.load(
              registryContract,
              web3j,
              transactionManager,
              new StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT));
      this.cnsRegistry = cnsRegistry;
    }
    return this.cnsRegistry;
  }

  boolean isSynced() throws Exception {
    SyncStatus ethSyncing = web3j.getSyncStatus().send();
    if (ethSyncing.isSyncing()) {
      return false;
    } else {
      BcosBlock block = web3j.getBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
      long timestamp = block.getBlock().getTimestamp().longValueExact() * 1000;

      return System.currentTimeMillis() - syncThreshold < timestamp;
    }
  }

  public static boolean isValidCnsName(String input) {
    return input != null // will be set to null on new Contract creation
        && (input.contains(":") || !WalletUtils.isValidAddress(input));
  }
}
