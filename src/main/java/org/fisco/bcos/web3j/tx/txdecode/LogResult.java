package org.fisco.bcos.web3j.tx.txdecode;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;

public class LogResult {
    private List<EventResultEntity> logParams;
    private Log log;

    public List<EventResultEntity> getLogParams() {
        return logParams;
    }

    public void setLogParams(List<EventResultEntity> logParams) {
        this.logParams = logParams;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "LogResult [logParams=" + logParams + ", log=" + log + "]";
    }
}
