package org.fisco.bcos.web3j.crypto;

import java.math.BigInteger;
import org.fisco.bcos.web3j.rlp.RlpDecoder;
import org.fisco.bcos.web3j.rlp.RlpList;
import org.fisco.bcos.web3j.rlp.RlpString;
import org.fisco.bcos.web3j.utils.Numeric;

public class TransactionDecoder {

  public static RawTransaction decode(String hexTransaction) {
    byte[] transaction = Numeric.hexStringToByteArray(hexTransaction);
    RlpList rlpList = RlpDecoder.decode(transaction);
    RlpList values = (RlpList) rlpList.getValues().get(0);
    BigInteger randomid = ((RlpString) values.getValues().get(0)).asPositiveBigInteger();
    BigInteger gasPrice = ((RlpString) values.getValues().get(1)).asPositiveBigInteger();
    BigInteger gasLimit = ((RlpString) values.getValues().get(2)).asPositiveBigInteger();
    BigInteger blockLimit = ((RlpString) values.getValues().get(3)).asPositiveBigInteger();
    String to = ((RlpString) values.getValues().get(4)).asString();
    BigInteger value = ((RlpString) values.getValues().get(5)).asPositiveBigInteger();
    String data = ((RlpString) values.getValues().get(6)).asString();
    if (values.getValues().size() > 7) {
      byte v = ((RlpString) values.getValues().get(7)).getBytes()[0];
      byte[] r =
          Numeric.toBytesPadded(
              Numeric.toBigInt(((RlpString) values.getValues().get(8)).getBytes()), 32);
      byte[] s =
          Numeric.toBytesPadded(
              Numeric.toBigInt(((RlpString) values.getValues().get(9)).getBytes()), 32);
      Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
      return new SignedRawTransaction(
          randomid, gasPrice, gasLimit, blockLimit, to, value, data, signatureData);
    } else {
      return RawTransaction.createTransaction(
          randomid, gasPrice, gasLimit, blockLimit, to, value, data);
    }
  }
}
