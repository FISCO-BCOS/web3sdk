/*
 * Copyright 2014-2020  [fisco-dev]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package org.fisco.bcos.web3j.tx.txdecode;

import java.util.List;
import org.fisco.bcos.web3j.abi.wrapper.ABIObject;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * TransactionResponse @Description: TransactionResponse
 *
 * @author maojiayu
 */
public class TransactionResponse {
    private TransactionReceipt transactionReceipt;
    private String contractAddress;
    private String values;
    private String events;
    private String receiptMessages;
    private List<Object> returnObject;
    private List<ABIObject> returnABIObject;
    private int returnCode = 0;
    private String returnMessage = "";

    public TransactionResponse() {
        super();
    }
    /** @return the bcosTransactionReceipt */
    public TransactionReceipt getTransactionReceipt() {
        return transactionReceipt;
    }

    /** @param transactionReceipt the transactionReceipt to set */
    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    /** @return the contractAddress */
    public String getContractAddress() {
        return contractAddress;
    }

    /** @param contractAddress the contractAddress to set */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /** @return the values */
    public String getValues() {
        return values;
    }

    /** @param values the values to set */
    public void setValues(String values) {
        this.values = values;
    }

    /** @return the events */
    public String getEvents() {
        return events;
    }

    /** @param events the events to set */
    public void setEvents(String events) {
        this.events = events;
    }

    /** @return the receiptMessages */
    public String getReceiptMessages() {
        return receiptMessages;
    }

    /** @param receiptMessages the receiptMessages to set */
    public void setReceiptMessages(String receiptMessages) {
        this.receiptMessages = receiptMessages;
    }

    public List<Object> getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(List<Object> returnObject) {
        this.returnObject = returnObject;
    }

    public List<ABIObject> getReturnABIObject() {
        return returnABIObject;
    }

    public void setReturnABIObject(List<ABIObject> returnABIObject) {
        this.returnABIObject = returnABIObject;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public String toString() {
        return "TransactionResponse{"
                + "transactionReceipt="
                + transactionReceipt
                + ", contractAddress='"
                + contractAddress
                + '\''
                + ", values='"
                + values
                + '\''
                + ", events='"
                + events
                + '\''
                + ", receiptMessages='"
                + receiptMessages
                + '\''
                + ", returnObject="
                + returnObject
                + ", returnABIObject="
                + returnABIObject
                + '}';
    }
}
