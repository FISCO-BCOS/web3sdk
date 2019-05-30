package org.fisco.bcos.web3j.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosLog;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;

/** Log filter handler. */
public class LogFilter extends Filter<Log> {

  private final org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter ethFilter;

  public LogFilter(
      Web3j web3j,
      Callback<Log> callback,
      org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter ethFilter) {
    super(web3j, callback);
    this.ethFilter = ethFilter;
  }

  @Override
  org.fisco.bcos.web3j.protocol.core.methods.response.BcosFilter sendRequest() throws IOException {
    return web3j.newFilter(ethFilter).send();
  }

  @Override
  void process(List<BcosLog.LogResult> logResults) {
    for (BcosLog.LogResult logResult : logResults) {
      if (logResult instanceof BcosLog.LogObject) {
        Log log = ((BcosLog.LogObject) logResult).get();
        callback.onEvent(log);
      } else {
        throw new FilterException(
            "Unexpected result type: " + logResult.get() + " required LogObject");
      }
    }
  }

  @Override
  protected Optional<Request<?, BcosLog>> getFilterLogs(BigInteger filterId) {
    return Optional.empty();
  }
}
