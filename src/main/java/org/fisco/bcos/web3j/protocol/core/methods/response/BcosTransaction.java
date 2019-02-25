package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.util.Optional;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.Response;

/**
 * Transaction object returned by:
 *
 * <ul>
 *   <li>eth_getTransactionByHash
 *   <li>eth_getTransactionByBlockHashAndIndex
 *   <li>eth_getTransactionByBlockNumberAndIndex
 * </ul>
 *
 * <p>This differs slightly from the request {@link SendTransaction} Transaction object.</p>
 *
 * <p>See <a href="https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_gettransactionbyhash">docs</a>
 * for further details.
 */
public class BcosTransaction extends Response<Transaction> {

  public Optional<Transaction> getTransaction() {
    return Optional.ofNullable(getResult());
  }

  public static class ResponseDeserialiser extends JsonDeserializer<Transaction> {

    private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

    @Override
    public Transaction deserialize(
        JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
      if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
        return objectReader.readValue(jsonParser, Transaction.class);
      } else {
        return null; // null is wrapped by Optional in above getter
      }
    }
  }
}
