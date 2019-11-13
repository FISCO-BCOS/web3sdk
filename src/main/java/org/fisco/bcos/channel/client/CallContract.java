package org.fisco.bcos.channel.client;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.Utils;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.StatusCode;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.methods.request.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.Call;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

public class CallContract {

    private static final BigInteger gasPrice = new BigInteger("3000000000");
    private static final BigInteger gasLimit = new BigInteger("3000000000");

    private Credentials credentials;
    private Web3j web3j;

    public CallContract(Credentials credentials, Web3j web3j) {
        this.credentials = credentials;
        this.web3j = web3j;
    }

    public CallResult call(String contractAddress, String funcName, Type... args) {
        final Function function =
                new Function(
                        funcName,
                        Arrays.<Type>asList(args),
                        Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        Call ethCall;
        try {
            ethCall =
                    web3j.call(
                                    Transaction.createEthCallTransaction(
                                            credentials.getAddress(), contractAddress, data),
                                    DefaultBlockParameterName.LATEST)
                            .send();
        } catch (IOException e) {
            return new CallResult(StatusCode.IOExceptionCatched, e.getMessage(), "0x");
        }

        Call.CallOutput callOutput = ethCall.getValue();
        if (callOutput != null) {
            return new CallResult(
                    callOutput.getStatus(),
                    StatusCode.getStatusMessage(callOutput.getStatus()),
                    callOutput.getOutput());
        } else {
            return new CallResult(
                    StatusCode.ErrorInRPC,
                    StatusCode.getStatusMessage(StatusCode.ErrorInRPC),
                    "0x");
        }
    }

    public TransactionReceipt sendTransaction(
            String contractAddress, String funcName, Type... args) {
        final Function function =
                new Function(
                        funcName,
                        Arrays.<Type>asList(args),
                        Collections.<TypeReference<?>>emptyList());

        ExecuteTransaction executeTransaction =
                new ExecuteTransaction(contractAddress, web3j, credentials, gasPrice, gasLimit);

        TransactionReceipt transactionReceipt = executeTransaction.send(function);
        if (transactionReceipt != null) {
            String status = transactionReceipt.getStatus();
            transactionReceipt.setMessage(StatusCode.getStatusMessage(status));
        }
        return transactionReceipt;
    }

    public TransactionReceipt sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String contractAddress,
            String funcName,
            Type... args) {
        final Function function =
                new Function(
                        funcName,
                        Arrays.<Type>asList(args),
                        Collections.<TypeReference<?>>emptyList());

        ExecuteTransaction executeTransaction =
                new ExecuteTransaction(contractAddress, web3j, credentials, gasPrice, gasLimit);

        TransactionReceipt transactionReceipt = executeTransaction.send(function);
        if (transactionReceipt != null) {
            String status = transactionReceipt.getStatus();
            transactionReceipt.setMessage(StatusCode.getStatusMessage(status));
        }
        return transactionReceipt;
    }

    public void asyncSendTransaction(
            TransactionSucCallback callback,
            String contractAddress,
            String funcName,
            Type... args) {
        final Function function =
                new Function(
                        funcName,
                        Arrays.<Type>asList(args),
                        Collections.<TypeReference<?>>emptyList());

        ExecuteTransaction executeTransaction =
                new ExecuteTransaction(contractAddress, web3j, credentials, gasPrice, gasLimit);

        executeTransaction.asyncSend(function, callback);
    }

    public void asyncSendTransaction(
            TransactionSucCallback callback,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String contractAddress,
            String funcName,
            Type... args) {
        final Function function =
                new Function(
                        funcName,
                        Arrays.<Type>asList(args),
                        Collections.<TypeReference<?>>emptyList());

        ExecuteTransaction executeTransaction =
                new ExecuteTransaction(contractAddress, web3j, credentials, gasPrice, gasLimit);

        executeTransaction.asyncSend(function, callback);
    }

    public List<Type> decode(String data, TypeReference<?>... typeReferences) {
        if (data.isEmpty() || data.equals("0x")) return null;
        List<TypeReference<?>> typeReferencesList = Arrays.<TypeReference<?>>asList(typeReferences);
        return FunctionReturnDecoder.decode(data, Utils.convert(typeReferencesList));
    }

    @SuppressWarnings("unchecked")
    public <S extends Type, T> List<T> convertList(List<S> arr) {
        List<T> out = new ArrayList<T>();
        for (Iterator<S> it = arr.iterator(); it.hasNext(); ) {
            out.add((T) it.next().getValue());
        }
        return out;
    }

    public <S extends Type, T> List<List<T>> convertListList(List<StaticArray<S>> arrs) {
        List<List<T>> out = new ArrayList<List<T>>();
        for (StaticArray<S> arr : arrs) {
            List<T> temp = convertList(arr.getValue());
            out.add(temp);
        }
        return out;
    }
}
