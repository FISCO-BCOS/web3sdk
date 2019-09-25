package org.fisco.bcos.channel.client;

import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.Utils;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.methods.request.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.Call;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class CallContract {

    private static Logger logger = LoggerFactory.getLogger(ChannelResponseCallback.class);
    protected Credentials credentials;
    protected Web3j web3j;

    public CallContract(Credentials credentials, Web3j web3j) {
        this.credentials = credentials;
        this.web3j = web3j;
    }

    public String call(String contractAddress, String funcName, Type... args) throws IOException {
        final Function function = new Function(funcName, Arrays.<Type>asList(args), Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);
        Call ethCall =
                web3j.call(
                        Transaction.createEthCallTransaction(
                                credentials.getAddress(),
                                contractAddress,
                                data),
                        DefaultBlockParameterName.LATEST)
                        .send();
        return ethCall.getValue().getOutput();
    }

    public String sendTransaction(BigInteger gasPrice,
                                  BigInteger gasLimit,
                                  String contractAddress,
                                  String funcName,
                                  Type... args) {
        final Function function = new Function(funcName, Arrays.<Type>asList(args), Collections.<TypeReference<?>>emptyList());
        SendTransaction sendTransaction =
                new SendTransaction(
                        contractAddress,
                        web3j,
                        credentials,
                        gasPrice, gasLimit);
        TransactionCallback callback = new TransactionCallback();
        try {
            sendTransaction.send(function, callback);
        } catch (Exception e) {
            logger.error("send transaction failed:", e);
        }

        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("error:", e);
        }

        TransactionReceipt receipt = callback.receipt;
        return receipt.getOutput();
    }

    public List<Type> decode(String data, TypeReference<?>... typeReferences) {
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