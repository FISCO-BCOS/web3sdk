package org.fisco.bcos.web3j.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosLog;

/** Handler for working with transaction filter requests. */
public class PendingTransactionFilter extends Filter<String> {

  public PendingTransactionFilter(Web3j web3j, Callback<String> callback) {
    super(web3j, callback);
  }

  @Override
  BcosFilter sendRequest() throws IOException {
    return web3j.newPendingTransactionFilter().send();
  }

  @Override
  void process(List<BcosLog.LogResult> logResults) {
    for (BcosLog.LogResult logResult : logResults) {
      if (logResult instanceof BcosLog.Hash) {
        String blockHash = ((BcosLog.Hash) logResult).get();
        callback.onEvent(blockHash);
      } else {
        throw new FilterException("Unexpected result type: " + logResult.get() + ", required Hash");
      }
    }
  }

  /**
   * Since the pending transaction filter does not support historic filters, the filterId is ignored
   * and an empty optional is returned
   *
   * @param filterId Id of the filter for which the historic log should be retrieved
   * @return Optional.empty()
   */
  @Override
  protected Optional<Request<?, BcosLog>> getFilterLogs(BigInteger filterId) {
    return Optional.empty();
  }
}
