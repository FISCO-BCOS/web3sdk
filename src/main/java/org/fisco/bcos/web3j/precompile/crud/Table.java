package org.fisco.bcos.web3j.precompile.crud;

public class Table {

    private String tableName;
    private String key;
    private String valueFields;
    private String optional = "";

    public Table() {}

    public Table(String tableName, String key) {
        this.tableName = tableName;
        this.key = key;
    }

    public Table(String tableName, String key, String valueFields) {
        this.tableName = tableName;
        this.key = key;
        this.valueFields = valueFields;
    }

    public Table(String tableName, String key, String valueFields, String optional) {
        super();
        this.tableName = tableName;
        this.key = key;
        this.valueFields = valueFields;
        this.optional = optional;
    }

    public String getTableName() {
        return tableName;
    }

    public String getKey() {
        return key;
    }

    public String getValueFields() {
        return valueFields;
    }

    public String getOptional() {
        return optional;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValueFields(String valueFields) {
        this.valueFields = valueFields;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public Entry getEntry() {
        return new Entry();
    }

    public Condition getCondition() {
        return new Condition();
    }
}
