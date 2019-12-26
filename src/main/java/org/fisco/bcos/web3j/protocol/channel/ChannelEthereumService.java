package org.fisco.bcos.web3j.protocol.channel;

import java.io.IOException;
import org.fisco.bcos.channel.client.BcosResponseCallback;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.BcosRequest;
import org.fisco.bcos.channel.dto.BcosResponse;
import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.protocol.core.methods.response.Call.CallOutput;
import org.fisco.bcos.web3j.protocol.core.methods.response.SendTransaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.MessageDecodingException;
import org.fisco.bcos.web3j.tx.exceptions.ContractCallException;
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

    private Integer timeout = 0;

    @Override
    public <T extends Response> T send(Request request, Class<T> responseType) throws IOException {
        byte[] payload = objectMapper.writeValueAsBytes(request);

        BcosRequest bcosRequest = new BcosRequest();
        if (channelService.getOrgID() != null) {
            bcosRequest.setKeyID(channelService.getOrgID());
        } else {
            bcosRequest.setKeyID(channelService.getAgencyName());
        }
        bcosRequest.setBankNO("");
        bcosRequest.setContent(new String(payload));
        bcosRequest.setMessageID(channelService.newSeq());

        if (timeout != 0) {
            bcosRequest.setTimeout(timeout);
        }

        BcosResponse response;
        if (!request.isNeedTransCallback()) {
            response = channelService.sendEthereumMessage(bcosRequest);
        } else {
            response =
                    channelService.sendEthereumMessage(
                            bcosRequest, request.getTransactionSucCallback());
        }
        logger.debug(
                "bcos request, seq:{}, method:{}", bcosRequest.getMessageID(), request.getMethod());
        logger.debug(
                "bcos request:{} {}",
                bcosRequest.getMessageID(),
                objectMapper.writeValueAsString(request));
        logger.trace(
                "bcos request:{} {}",
                bcosRequest.getMessageID(),
                objectMapper.writeValueAsString(request));
        logger.trace(
                "bcos response:{} {} {}",
                bcosRequest.getMessageID(),
                response.getErrorCode(),
                response.getContent());
        if (response.getErrorCode() == 0) {
            try {
                T t = objectMapper.readValue(response.getContent(), responseType);
                if (t.getError() != null) {
                    throw new IOException(t.getError().getMessage());
                }
                if (t.getResult() instanceof CallOutput) {
                    CallOutput callResult = (CallOutput) t.getResult();
                    if (StatusCode.RevertInstruction.equals(callResult.getStatus())) {
                        throw new ContractCallException(
                                "The execution of the contract rolled back.");
                    }
                    if (StatusCode.CallAddressError.equals(callResult.getStatus())) {
                        throw new ContractCallException("The contract address is incorrect.");
                    }

                    if (!StatusCode.Success.equals(callResult.getStatus())) {
                        throw new ContractCallException(
                                StatusCode.getStatusMessage(callResult.getStatus()));
                    }
                }
                return t;
            } catch (ContractCallException e) {
                throw e;
            } catch (Exception e) {
                throw new MessageDecodingException(response.getContent());
            }
        } else {
            throw new IOException(response.getErrorMessage());
        }
    }

    @Override
    public void sendOnly(Request request) throws IOException {
        byte[] payload = objectMapper.writeValueAsBytes(request);

        BcosRequest fiscoRequest = new BcosRequest();
        fiscoRequest.setKeyID(channelService.getOrgID());
        fiscoRequest.setBankNO("");
        fiscoRequest.setContent(new String(payload));
        fiscoRequest.setMessageID(channelService.newSeq());

        if (timeout != 0) {
            fiscoRequest.setTimeout(timeout);
        }

        if (!request.isNeedTransCallback()) {
            channelService.asyncSendEthereumMessage(
                    fiscoRequest,
                    new BcosResponseCallback() {
                        @Override
                        public void onResponse(BcosResponse response) {
                            try {
                                logger.debug(
                                        "fisco Request:{} {}",
                                        fiscoRequest.getMessageID(),
                                        objectMapper.writeValueAsString(request));
                                logger.debug(
                                        "fisco Response:{} {} {}",
                                        fiscoRequest.getMessageID(),
                                        response.getErrorCode(),
                                        response.getContent());

                                if (response.getErrorCode() != 0) {
                                    logger.error("Error: " + response.getErrorCode());
                                }
                            } catch (Exception e) {
                                logger.error("Error: ", e);
                            }
                        }
                    });
        } else {
            channelService.asyncSendEthereumMessage(
                    fiscoRequest,
                    new BcosResponseCallback() {
                        @Override
                        public void onResponse(BcosResponse response) {
                            try {
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

                                    // SendTransaction
                                    SendTransaction sendTransaction =
                                            objectMapper.readValue(
                                                    response.getContent(), SendTransaction.class);

                                    if (sendTransaction.getError() == null) {
                                        logger.debug(
                                                "sendRawTransaction response ok, transaction hash: {} ",
                                                sendTransaction.getResult());
                                    } else {
                                        TransactionReceipt receipt = new TransactionReceipt();
                                        receipt.setStatus(
                                                String.valueOf(
                                                        sendTransaction.getError().getCode()));
                                        receipt.setMessage(sendTransaction.getError().getMessage());

                                        // optional code
                                        if (channelService.getThreadPool() == null) {
                                            channelService.onReceiveTransactionMessage(
                                                    fiscoRequest.getMessageID(), receipt);
                                        } else {
                                            // Execute the callback function in the thread pool
                                            channelService
                                                    .getThreadPool()
                                                    .execute(
                                                            new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    channelService
                                                                            .onReceiveTransactionMessage(
                                                                                    fiscoRequest
                                                                                            .getMessageID(),
                                                                                    receipt);
                                                                }
                                                            });
                                        }

                                        logger.debug(
                                                " sendRawTransaction response not ok, code: {}, message: {} ",
                                                receipt.getStatus(),
                                                receipt.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("Error: ", e);
                            }
                        }
                    },
                    request.getTransactionSucCallback());
        }
    }

    public String sendSpecial(Request request) throws IOException {
        byte[] payload = objectMapper.writeValueAsBytes(request);

        BcosRequest bcosRequest = new BcosRequest();
        if (channelService.getOrgID() != null) {
            bcosRequest.setKeyID(channelService.getOrgID());
        } else {
            bcosRequest.setKeyID(channelService.getAgencyName());
        }
        bcosRequest.setBankNO("");
        bcosRequest.setContent(new String(payload));
        bcosRequest.setMessageID(channelService.newSeq());

        if (timeout != 0) {
            bcosRequest.setTimeout(timeout);
        }

        BcosResponse response;
        if (!request.isNeedTransCallback()) {
            response = channelService.sendEthereumMessage(bcosRequest);
        } else {
            response =
                    channelService.sendEthereumMessage(
                            bcosRequest, request.getTransactionSucCallback());
        }
        logger.trace(
                "bcos request:{} {}",
                bcosRequest.getMessageID(),
                objectMapper.writeValueAsString(request));
        logger.trace(
                "bcos response:{} {} {}",
                bcosRequest.getMessageID(),
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
