package org.fisco.bcos.web3j.abi.datatypes;

/** Dynamically allocated sequence of bytes. */
public class DynamicBytes extends BytesType {

  public static final String TYPE_NAME = "bytes";
  public static final DynamicBytes DEFAULT = new DynamicBytes(new byte[] {});

  public DynamicBytes(byte[] value) {
    super(value, TYPE_NAME);
  }
}
