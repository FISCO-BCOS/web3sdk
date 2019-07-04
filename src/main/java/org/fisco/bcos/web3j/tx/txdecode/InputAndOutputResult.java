package org.fisco.bcos.web3j.tx.txdecode;

import java.util.List;

public class InputAndOutputResult {

    private String function;
    private String methodID;
    private List<ResultEntity> result;

    public InputAndOutputResult() {
        super();
    }

    public InputAndOutputResult(String function, String methodID, List<ResultEntity> result) {
        super();
        this.function = function;
        this.methodID = methodID;
        this.result = result;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getMethodID() {
        return methodID;
    }

    public void setMethodID(String methodID) {
        this.methodID = methodID;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "InputAndOutputResult [function="
                + function
                + ", methodID="
                + methodID
                + ", result="
                + result
                + "]";
    }
}
