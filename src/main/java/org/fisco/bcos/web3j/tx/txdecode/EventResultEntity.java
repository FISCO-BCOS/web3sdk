package org.fisco.bcos.web3j.tx.txdecode;

import org.fisco.bcos.web3j.abi.datatypes.Type;

public class EventResultEntity extends ResultEntity {

    public EventResultEntity(String name, String type, boolean indexed, Type data) {
        super(name, type, data);

        this.setIndexed(indexed);
    }

    private boolean indexed;

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }
}
