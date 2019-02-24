package org.fisco.bcos.web3j.protocol.core.methods.request;

import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;

/**
 * Filter implementation as per <a
 * href="https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_newfilter">docs</a>.
 */
public class BcosFilter extends Filter<BcosFilter> {
  private DefaultBlockParameter fromBlock; // optional, params - defaults to latest for both
  private DefaultBlockParameter toBlock;
  private List<String> address; // spec. implies this can be single address as string or list

  public BcosFilter() {
    super();
  }

  public BcosFilter(
      DefaultBlockParameter fromBlock, DefaultBlockParameter toBlock, List<String> address) {
    super();
    this.fromBlock = fromBlock;
    this.toBlock = toBlock;
    this.address = address;
  }

  public BcosFilter(
      DefaultBlockParameter fromBlock, DefaultBlockParameter toBlock, String address) {
    this(fromBlock, toBlock, Arrays.asList(address));
  }

  public DefaultBlockParameter getFromBlock() {
    return fromBlock;
  }

  public DefaultBlockParameter getToBlock() {
    return toBlock;
  }

  public List<String> getAddress() {
    return address;
  }

  @Override
  Filter getThis() {
    return this;
  }
}
