package org.fisco.bcos.web3j.protocol.channel;

import java.io.IOException;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.FiscoRequest;
import org.fisco.bcos.channel.dto.FiscoResponse;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.protocol.exceptions.MessageDecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Channel implementation of our services API. */
public class ChannelEthereumService extends org.fisco.bcos.web3j.protocol.Service {
  private static Logger logger = LoggerFactory.getLogger(ChannelEthereumService.class);

  private Service channelService;

  public ChannelEthereumService(boolean includeRawResponses) {
    super(includeRawResponses);
  }
  public ChannelEthereumService() {
    super(false);
  }
    private Integer timeout=0;

  @Override
  public <T extends Response> T send(Request request, Class<T> responseType) throws IOException {
    byte[] payload = objectMapper.writeValueAsBytes(request);

    FiscoRequest fiscoRequest = new FiscoRequest();
    fiscoRequest.setKeyID(channelService.getOrgID());
    fiscoRequest.setBankNO("");
    fiscoRequest.setContent(new String(payload));
    fiscoRequest.setMessageID(channelService.newSeq());

    if (timeout != 0) {
      fiscoRequest.setTimeout(timeout);
    }

    FiscoResponse response;
    if (!request.isNeedTransCallback()) {
      response = channelService.sendEthereumMessage(fiscoRequest);
    } else {
      response =
          channelService.sendEthereumMessage(fiscoRequest, request.getTransactionSucCallback());
    }

    logger.debug(
        "fisco Request:{} {}",
        fiscoRequest.getMessageID(),
        objectMapper.writeValueAsString(request));
    logger.debug(
        "fisco Response:{} {} {}",
        fiscoRequest.getMessageID(),
        response.getErrorCode(),
        response.getContent());
    if (response.getErrorCode() == 0) {
      try {
        T t = objectMapper.readValue(response.getContent(), responseType);
        if (t.getError() != null) {
          throw new IOException(t.getError().getMessage());
        }
        return t;
      } catch (Exception e) {
        throw new MessageDecodingException(response.getContent());
      }
    } else {
      throw new IOException(response.getErrorMessage());
    }
  }

  public String sendSpecial(Request request) throws IOException {
    byte[] payload = objectMapper.writeValueAsBytes(request);

    FiscoRequest fiscoRequest = new FiscoRequest();
    fiscoRequest.setKeyID(channelService.getOrgID());
    fiscoRequest.setBankNO("");
    fiscoRequest.setContent(new String(payload));
    fiscoRequest.setMessageID(channelService.newSeq());

    if (timeout != 0) {
      fiscoRequest.setTimeout(timeout);
    }

    FiscoResponse response;
    if (!request.isNeedTransCallback()) {
      response = channelService.sendEthereumMessage(fiscoRequest);
    } else {
      response =
          channelService.sendEthereumMessage(fiscoRequest, request.getTransactionSucCallback());
    }

    logger.debug(
        "fisco Request:{} {}",
        fiscoRequest.getMessageID(),
        objectMapper.writeValueAsString(request));
    logger.debug(
        "fisco Response:{} {} {}",
        fiscoRequest.getMessageID(),
        response.getErrorCode(),
        response.getContent());
    if (response.getErrorCode() == 0) {
      if (response.getContent().contains("error")) {
        Response t = objectMapper.readValue(response.getContent(), Response.class);
        throw new ResponseExcepiton(t.getError().getCode(), t.getError().getMessage());
      } else {
        String[] resultArray = response.getContent().split("result");
        String resultStr = resultArray[1];
        if ("\"".equals(resultStr.substring(2, 3)))
          return resultStr.substring(3, resultStr.length() - 3);
        else return resultStr.substring(2, resultStr.length() - 2);
      }
    } else {
      throw new IOException(response.getErrorMessage());
    }
  }

  public Service getChannelService() {
    return channelService;
  }

  public void setChannelService(Service channelService) {
    this.channelService = channelService;
  }

  public Integer getTimeout() {
    return timeout;
  }

  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }

  @Override
  public void close() throws IOException {}
}
