package org.fisco.bcos.web3j.protocol.ipc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

/** Unix domain socket IO implementation for IPC. */
public class UnixDomainSocket implements IOFacade {

  private static final int DEFAULT_BUFFER_SIZE = 1024;

  private final int bufferSize;

  private final InputStreamReader reader;
  private final PrintWriter writer;

  public UnixDomainSocket(String ipcSocketPath) {
    this(ipcSocketPath, DEFAULT_BUFFER_SIZE);
  }

  public UnixDomainSocket(String ipcSocketPath, int bufferSize) {
    this.bufferSize = bufferSize;

    try {
      UnixSocketAddress address = new UnixSocketAddress(ipcSocketPath);
      UnixSocketChannel channel = UnixSocketChannel.open(address);

      reader = new InputStreamReader(Channels.newInputStream(channel));
      writer = new PrintWriter(Channels.newOutputStream(channel));

    } catch (IOException e) {
      throw new RuntimeException("Provided file socket cannot be opened: " + ipcSocketPath, e);
    }
  }

  UnixDomainSocket(InputStreamReader reader, PrintWriter writer, int bufferSize) {
    this.bufferSize = bufferSize;
    this.writer = writer;
    this.reader = reader;
  }

  @Override
  public void write(String payload) throws IOException {
    writer.write(payload);
    writer.flush();
  }

  @Override
  public String read() throws IOException {
    CharBuffer response = CharBuffer.allocate(bufferSize);
    String result = "";

    do {
      response.clear();
      reader.read(response);
      result += new String(response.array(), response.arrayOffset(), response.position());
    } while (response.position() == response.limit() && response.get(response.limit() - 1) != '\n');

    return result;
  }
}
