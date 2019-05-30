package org.fisco.bcos.web3j.protocol;

import java.util.concurrent.ScheduledExecutorService;
import org.fisco.bcos.web3j.protocol.core.Ethereum;
import org.fisco.bcos.web3j.protocol.core.JsonRpc2_0Web3j;
import org.fisco.bcos.web3j.protocol.rx.Web3jRx;

/** JSON-RPC Request object building factory. */
public interface Web3j extends Ethereum, Web3jRx {
  static Web3j build(Web3jService web3jService) {
    return new JsonRpc2_0Web3j(web3jService);
  }

  static Web3j build(
      Web3jService web3jService,
      long pollingInterval,
      ScheduledExecutorService scheduledExecutorService,
      int groupId) {
    return new JsonRpc2_0Web3j(web3jService, pollingInterval, scheduledExecutorService, groupId);
  }

  static Web3j build(Web3jService web3jService, int groupId) {
    return new JsonRpc2_0Web3j(web3jService, groupId);
  }
}
