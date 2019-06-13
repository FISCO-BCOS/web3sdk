package org.fisco.bcos.web3j.precompile.crud;

import java.util.HashMap;
import java.util.Map;

public class Condition {

    private Map<String, Map<EnumOP, String>> conditions;

    public Condition() {
        conditions = new HashMap<>();
    }

    public void EQ(String key, String value) {
        HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
        map.put(EnumOP.eq, value);
        conditions.put(key, map);
    }

    public void NE(String key, String value) {
        HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
        map.put(EnumOP.ne, value);
        conditions.put(key, map);
    }

    public void GT(String key, String value) {
        HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
        map.put(EnumOP.gt, value);
        conditions.put(key, map);
    }

    public void GE(String key, String value) {
        HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
        map.put(EnumOP.ge, value);
        conditions.put(key, map);
    }

    public void LT(String key, String value) {
        HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
        map.put(EnumOP.lt, value);
        conditions.put(key, map);
    }

    public void LE(String key, String value) {
        HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
        map.put(EnumOP.le, value);
        conditions.put(key, map);
    }

    public void Limit(int count) {
        Limit(0, count);
    }

    public void Limit(int offset, int count) {
        HashMap<EnumOP, String> map = new HashMap<EnumOP, String>();
        if (offset < 0) {
            offset = 0;
        }
        if (count < 0) {
            count = 0;
        }
        map.put(EnumOP.limit, offset + "," + count);
        conditions.put("limit", map);
    }

    public Map<String, Map<EnumOP, String>> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Map<EnumOP, String>> conditions) {
        this.conditions = conditions;
    }
}
