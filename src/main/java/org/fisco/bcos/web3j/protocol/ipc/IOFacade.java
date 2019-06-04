package org.fisco.bcos.web3j.protocol.ipc;

import java.io.IOException;

/** Simple IO facade for the &#42;nix and Windows IPC implementations. */
public interface IOFacade {
  void write(String payload) throws IOException;

  String read() throws IOException;
}
