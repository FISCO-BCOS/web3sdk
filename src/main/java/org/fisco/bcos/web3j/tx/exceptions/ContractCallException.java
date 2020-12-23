package org.fisco.bcos.web3j.tx.exceptions;

import org.fisco.bcos.web3j.protocol.core.methods.response.Call;

/** Exception resulting from issues calling methods on Smart Contracts. */
public class ContractCallException extends RuntimeException {

    private Call.CallOutput callOutput;

    public Call.CallOutput getCallOutput() {
        return callOutput;
    }

    public void setCallOutput(Call.CallOutput callOutput) {
        this.callOutput = callOutput;
    }

    public ContractCallException(String message) {
        super(message);
    }

    public ContractCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
