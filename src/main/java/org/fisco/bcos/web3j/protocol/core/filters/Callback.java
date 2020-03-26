package org.fisco.bcos.web3j.protocol.core.filters;

/** Filter callback interface. */
@Deprecated
public interface Callback<T> {
    void onEvent(T value);
}
