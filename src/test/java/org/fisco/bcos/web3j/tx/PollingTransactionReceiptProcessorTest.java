package org.fisco.bcos.web3j.tx;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.junit.Before;
import org.junit.Test;

public class PollingTransactionReceiptProcessorTest {
  private static final String TRANSACTION_HASH = "0x00";
  private Web3j web3j;
  private long sleepDuration;
  private int attempts;
  private PollingTransactionReceiptProcessor processor;

  @Before
  public void setUp() {
    web3j = mock(Web3j.class);
    sleepDuration = 100;
    attempts = 3;
    processor = new PollingTransactionReceiptProcessor(web3j, sleepDuration, attempts);
  }

  @Test
  public void returnsTransactionReceiptWhenItIsAvailableInstantly() throws Exception {
    TransactionReceipt transactionReceipt = new TransactionReceipt();
    doReturn(requestReturning(response(transactionReceipt)))
        .when(web3j)
        .getTransactionReceipt(TRANSACTION_HASH);

    TransactionReceipt receipt = processor.waitForTransactionReceipt(TRANSACTION_HASH);

    assertThat(receipt, sameInstance(transactionReceipt));
  }

  @Test
  public void throwsTransactionExceptionWhenReceiptIsNotAvailableInTime() throws Exception {
    doReturn(requestReturning(response(null))).when(web3j).getTransactionReceipt(TRANSACTION_HASH);

    try {
      processor.waitForTransactionReceipt(TRANSACTION_HASH);
      fail("call should fail with TransactionException");
    } catch (TransactionException e) {
      assertTrue(e.getTransactionHash().isPresent());
      assertEquals(e.getTransactionHash().get(), TRANSACTION_HASH);
    }
  }

  private static <T extends Response<?>> Request<String, T> requestReturning(T response) {
    Request<String, T> request = mock(Request.class);
    try {
      when(request.send()).thenReturn(response);
    } catch (IOException e) {
      // this will never happen
    }
    return request;
  }

  private static BcosTransactionReceipt response(TransactionReceipt transactionReceipt) {
    BcosTransactionReceipt response = new BcosTransactionReceipt();
    response.setResult(transactionReceipt);
    return response;
  }
}
