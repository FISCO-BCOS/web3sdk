package org.fisco.bcos.web3j.precompile.crud;

import java.util.HashMap;
import java.util.Map;

public class Entry {
    private Map<String, String> fields;

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public Entry() {
        fields = new HashMap<>();
    }

    public void put(String key, String value) {
        fields.put(key, value);
    }

    public String get(String key) {
        return fields.get(key);
    }
}
