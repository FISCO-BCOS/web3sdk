package org.fisco.bcos.web3j.tx.txdecode;

import java.io.IOException;

public class CompileSolidityException extends IOException {

    private static final long serialVersionUID = 1L;

    public CompileSolidityException() {
        super();
    }

    public CompileSolidityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileSolidityException(String message) {
        super(message);
    }

    public CompileSolidityException(Throwable cause) {
        super(cause);
    }
}
