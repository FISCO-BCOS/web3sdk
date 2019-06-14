package org.fisco.bcos.web3j.tx.txdecode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Array;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Bytes;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.NumericType;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

public class ResultEntity {

    private String name;
    private String type;
    private Object data;
    @JsonIgnore private Type typeObject;

    @SuppressWarnings("rawtypes")
    public ResultEntity(String name, String type, Type data) {
        this.name = name;
        this.type = type;
        this.data = typeToObject(data);
        this.typeObject = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toJson() throws JsonProcessingException {
        return ObjectMapperFactory.getObjectMapper().writeValueAsString(this);
    }

    public Object getData() {
        return data;
    }

    public Type getTypeObject() {
        return typeObject;
    }

    public void setData(Type data) {
        this.data = data;
    }

    public static Object typeToObject(Type type) {
        Object obj = null;
        if (type instanceof NumericType) { // uint int
            obj = ((NumericType) type).getValue();
        } else if (type instanceof Bool) { // bool
            obj = ((Bool) type).getValue();
        } else if (type instanceof Address) { // address
            obj = type.toString();
        } else if (type instanceof Bytes) { // bytes32
            obj = new String(((Bytes) type).getValue()).trim();
        } else if (type instanceof DynamicBytes) { // bytes
            obj = new String(((DynamicBytes) type).getValue()).trim();
        } else if (type instanceof Utf8String) { // string
            obj = ((Utf8String) type).getValue();
        } else if (type instanceof Array) { // T[] T[k]
            List<Object> r = new ArrayList<Object>();
            List l = ((Array) type).getValue();
            for (int i = 0; i < l.size(); ++i) {
                r.add(typeToObject((Type) l.get(i)));
            }

            obj = (Object) r;
        } else {
            obj = (Object) obj;
        }

        return obj;
    }

    @Override
    public String toString() {
        return "ResultEntity [name=" + name + ", type=" + type + ", data=" + data + "]";
    }
}
