package org.bcos.web3j.protocol.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.bcos.channel.client.Service;
import org.bcos.channel.dto.EthereumRequest;
import org.bcos.channel.dto.EthereumResponse;
import org.bcos.web3j.protocol.Service;
import org.bcos.web3j.protocol.core.Request;
import org.bcos.web3j.protocol.core.Response;

import java.io.IOException;

/**
 * Channel implementation of our services API.
 */
public class ChannelEthereumService extends Service {
	private static Logger logger = LoggerFactory.getLogger(ChannelEthereumService.class);

	private org.bcos.channel.client.Service channelService;
	
    public ChannelEthereumService( boolean includeRawResponses) {
        super(includeRawResponses);
    }
	
	public ChannelEthereumService() {
        super(false);
    }

    @Override
    public <T extends Response> T send(
            Request request, Class<T> responseType) throws IOException {
        byte[] payload = objectMapper.writeValueAsBytes(request);

        EthereumRequest ethereumRequest = new EthereumRequest();
        ethereumRequest.setKeyID(channelService.getOrgID());
        ethereumRequest.setBankNO("");
        ethereumRequest.setContent(new String(payload));
        ethereumRequest.setMessageID(channelService.newSeq());

        if(timeout != 0) {
        	ethereumRequest.setTimeout(timeout);
        }

        EthereumResponse response;
        if (!request.isNeedTransCallback()) {
            response = channelService.sendEthereumMessage(ethereumRequest);
        } else {
            response = channelService.sendEthereumMessage(ethereumRequest, request.getTransactionSucCallback());
        }

        logger.debug("Ethereum Request:{} {}", ethereumRequest.getMessageID(), objectMapper.writeValueAsString(request));
        logger.debug("Ethereum Response:{} {} {}", ethereumRequest.getMessageID(), response.getErrorCode(), response.getContent());
        if(response.getErrorCode() == 0)
        {
            try {
                T t = objectMapper.readValue(response.getContent(), responseType);
                if (t.getError() != null)
                {
                    throw new IOException(t.getError().getMessage());
                }
                return t;
            }
            catch(Exception e) {
                throw new IOException(response.getContent());
            }
        }
        else {
            throw new IOException(response.getErrorMessage());
        }
    }

	public org.bcos.channel.client.Service getChannelService() {
		return channelService;
	}

	public void setChannelService(org.bcos.channel.client.Service channelService) {
		this.channelService = channelService;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	private Integer timeout = 0;
}
