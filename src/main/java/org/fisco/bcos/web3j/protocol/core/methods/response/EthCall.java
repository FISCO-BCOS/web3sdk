package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.fisco.bcos.web3j.protocol.core.Response;

import java.util.List;

/**
 * eth_call.
 */
public class EthCall extends Response<EthCall.CallOutput> {

    public static class CallOutput {
        private String number;
        private String output;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }
    }
    public  CallOutput getValue() {
        return getResult();
    }

    public void setResult(CallOutput result) {
        super.setResult(result);
    }

}
