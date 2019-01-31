package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.fisco.bcos.web3j.protocol.core.Response;

import java.util.List;

/**
 * eth_call.
 */
public class Call extends Response<Call.CallOutput> {

    public static class CallOutput {
        private String currentBlockNumber;
        private String output;

        public String getCurrentBlockNumber() {
            return currentBlockNumber;
        }

        public void setCurrentBlockNumber(String number) {
            this.currentBlockNumber = number;
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
