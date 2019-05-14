package org.fisco.bcos.web3j.precompile.exception;

import java.io.IOException;

public class PrecompileMessageException extends IOException {

    private static final long serialVersionUID = 1L;

    public PrecompileMessageException() {
        super();
    }

    public PrecompileMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrecompileMessageException(String message) {
        super(message);
    }

    public PrecompileMessageException(Throwable cause) {
        super(cause);
    }
}
