package org.fisco.bcos.web3j.protocol.core.methods.response;

import org.fisco.bcos.web3j.protocol.core.Response;

public class RecoverGroup extends Response<RecoverGroup.Status> {

    public Status getStatus() {
        return getResult();
    }

    public static class Status {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Status{" + "code='" + code + '\'' + ", message='" + message + '\'' + '}';
        }
    }
}
