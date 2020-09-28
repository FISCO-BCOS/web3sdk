package org.fisco.bcos.web3j.abi.wrapper;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.fisco.bcos.web3j.abi.TypeDecoder;
import org.fisco.bcos.web3j.abi.TypeEncoder;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Bytes;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.NumericType;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ABIObject {

    private static final Logger logger = LoggerFactory.getLogger(ABIObject.class);

    public enum ObjectType {
        VALUE, // uint, int, bool, address, bytes<M>, bytes, string
        STRUCT, // tuple
        LIST // T[], T[M]
    }

    public enum ValueType {
        BOOL, // bool
        UINT, // uint<M>
        INT, // int<M>
        BYTES, // byteN
        ADDRESS, // address
        STRING, // string
        DBYTES, // bytes
        FIXED, // fixed<M>x<N>
        UFIXED, // ufixed<M>x<N>
    }

    public enum ListType {
        DYNAMIC, // T[]
        FIXED, // T[M]
    }

    private String name; // field name

    private ObjectType type; // for value
    private ValueType valueType;

    private NumericType numericValue;
    private Bytes bytesValue;
    private Address addressValue;
    private Bool boolValue;
    private DynamicBytes dynamicBytesValue;
    private Utf8String stringValue;

    private ListType listType;
    private List<ABIObject> listValues; // for list
    private int listLength; // for list
    private ABIObject listValueType; // for list

    private List<ABIObject> structFields; // for struct

    public ABIObject(ObjectType type) {
        this.type = type;

        switch (type) {
            case VALUE:
                {
                    break;
                }
            case STRUCT:
                {
                    structFields = new LinkedList<ABIObject>();
                    break;
                }
            case LIST:
                {
                    listValues = new LinkedList<ABIObject>();
                    break;
                }
        }
    }

    public ABIObject(ValueType valueType) {
        this.type = ObjectType.VALUE;
        this.valueType = valueType;
    }

    public ABIObject(ListType listType) {
        this.type = ObjectType.LIST;
        this.listType = listType;
        this.listValues = new LinkedList<ABIObject>();
    }

    public ABIObject(Uint256 uintValue) {
        this(ValueType.UINT);
        this.numericValue = uintValue;
    }

    public ABIObject(Int256 intValue) {
        this(ValueType.INT);
        this.numericValue = intValue;
    }

    public ABIObject(Address addressValue) {
        this(ValueType.ADDRESS);
        this.addressValue = addressValue;
    }

    public ABIObject(Bool boolValue) {
        this(ValueType.BOOL);
        this.boolValue = boolValue;
    }

    public ABIObject(Utf8String stringValue) {
        this(ValueType.STRING);
        this.stringValue = stringValue;
    }

    public ABIObject(DynamicBytes dynamicBytesValue) {
        this(ValueType.DBYTES);
        this.dynamicBytesValue = dynamicBytesValue;
    }

    public ABIObject(Bytes bytesValue) {
        this(ValueType.BYTES);
        this.bytesValue = bytesValue;
    }

    public ABIObject newObjectWithoutValue() {
        ABIObject abiObject = new ABIObject(this.type);
        // value
        abiObject.setValueType(this.getValueType());
        abiObject.setName(this.getName());

        // list
        abiObject.setListType(this.getListType());
        abiObject.setListLength(this.getListLength());

        if (this.getListValueType() != null) {
            abiObject.setListValueType(this.getListValueType().newObjectWithoutValue());
        }

        if (this.listValues != null) {
            for (ABIObject obj : this.listValues) {
                abiObject.listValues.add(obj.newObjectWithoutValue());
            }
        }

        // tuple
        if (this.structFields != null) {
            for (ABIObject obj : this.structFields) {
                abiObject.structFields.add(obj.newObjectWithoutValue());
            }
        }

        return abiObject;
    }

    // clone itself
    public ABIObject newObject() {

        ABIObject abiObject = new ABIObject(this.type);

        // value
        abiObject.setValueType(this.getValueType());
        abiObject.setName(this.getName());

        if (this.getNumericValue() != null) {
            abiObject.setNumericValue(
                    new NumericType(
                            this.getNumericValue().getTypeAsString(),
                            this.getNumericValue().getValue()) {
                        @Override
                        public boolean dynamicType() {
                            return false;
                        }

                        @Override
                        public int offset() {
                            return 1;
                        }
                    });
        }

        if (this.getBoolValue() != null) {
            abiObject.setBoolValue(new Bool(this.getBoolValue().getValue()));
        }

        if (this.getStringValue() != null) {
            abiObject.setStringValue(new Utf8String(this.getStringValue().getValue()));
        }

        if (this.getDynamicBytesValue() != null) {
            abiObject.setDynamicBytesValue(
                    new DynamicBytes(this.getDynamicBytesValue().getValue()));
        }

        if (this.getAddressValue() != null) {
            abiObject.setAddressValue(new Address(this.getAddressValue().toUint160()));
        }

        if (this.getBytesValue() != null) {
            abiObject.setBytesValue(
                    new Bytes(
                            this.getBytesValue().getValue().length,
                            this.getBytesValue().getValue()));
        }

        // list
        abiObject.setListType(this.getListType());
        abiObject.setListLength(this.getListLength());

        if (this.getListValueType() != null) {
            abiObject.setListValueType(this.getListValueType().newObject());
        }

        if (this.listValues != null) {
            for (ABIObject obj : this.listValues) {
                abiObject.listValues.add(obj.newObject());
            }
        }

        // tuple
        if (this.structFields != null) {
            for (ABIObject obj : this.structFields) {
                abiObject.structFields.add(obj.newObject());
            }
        }

        return abiObject;
    }

    /**
     * Checks to see if the current type is dynamic
     *
     * @return
     */
    public boolean isDynamic() {
        switch (type) {
            case VALUE:
                {
                    switch (valueType) {
                        case DBYTES: // bytes
                        case STRING: // string
                            return true;
                        default:
                            return false;
                    }
                    // break;
                }
            case LIST:
                {
                    switch (listType) {
                        case FIXED: // T[M]
                            {
                                return listValueType.isDynamic();
                            }
                        case DYNAMIC: // T[]
                            {
                                return true;
                            }
                    }
                    break;
                }
            case STRUCT:
                {
                    for (ABIObject abiObject : structFields) {
                        if (abiObject.isDynamic()) {
                            return true;
                        }
                    }
                    return false;
                }
        }

        return false;
    }

    /**
     * dynamic offset of this object
     *
     * @return
     */
    public int offset() {
        if (isDynamic()) { // dynamic
            return 1;
        }

        int offset = 0;
        if (type == ObjectType.VALUE) { // basic type
            offset = 1;
        } else if (type == ObjectType.STRUCT) { // tuple
            int l = 0;
            for (ABIObject abiObject : structFields) {
                l += abiObject.offset();
            }
            offset = l;
        } else { // T[M]
            int length = listLength;
            int basicOffset = listValueType.offset();
            offset = length * basicOffset;
        }

        return offset;
    }

    public int offsetAsByteLength() {
        return offset() * Type.MAX_BYTE_LENGTH;
    }

    public int offsetAsHexLength() {
        return offset() * (Type.MAX_BYTE_LENGTH << 1);
    }

    /**
     * encode this object
     *
     * @return
     */
    public String encode() {

        StringBuffer stringBuffer = new StringBuffer();
        switch (type) {
            case VALUE:
                {
                    switch (valueType) {
                        case UINT:
                        case INT:
                            {
                                stringBuffer.append(TypeEncoder.encode(numericValue));
                                break;
                            }
                        case BOOL:
                            {
                                stringBuffer.append(TypeEncoder.encode(boolValue));
                                break;
                            }
                        case FIXED:
                        case UFIXED:
                            {
                                throw new UnsupportedOperationException(
                                        " Unsupported fixed/unfixed type. ");
                                // break;
                            }
                        case BYTES:
                            {
                                if (bytesValue.getValue().length > 32) {
                                    throw new InvalidParameterException(
                                            "the length of bytesN must be equal or less than 32");
                                }
                                stringBuffer.append(TypeEncoder.encode(bytesValue));
                                break;
                            }
                        case ADDRESS:
                            {
                                stringBuffer.append(TypeEncoder.encode(addressValue));
                                break;
                            }
                        case DBYTES:
                            {
                                stringBuffer.append(TypeEncoder.encode(dynamicBytesValue));
                                break;
                            }
                        case STRING:
                            {
                                stringBuffer.append(TypeEncoder.encode(stringValue));
                                break;
                            }
                        default:
                            {
                                throw new UnsupportedOperationException(
                                        " Unrecognized valueType: " + valueType);
                            }
                    }
                    break;
                }
            case STRUCT:
                {
                    long dynamicOffset = 0;
                    for (ABIObject abiObject : structFields) {
                        dynamicOffset += abiObject.offsetAsByteLength();
                    }

                    StringBuffer fixedBuffer = new StringBuffer();
                    StringBuffer dynamicBuffer = new StringBuffer();

                    for (ABIObject abiObject : structFields) {
                        String encodeValue = abiObject.encode();
                        if (abiObject.isDynamic()) {
                            fixedBuffer.append(TypeEncoder.encode(new Uint256(dynamicOffset)));
                            dynamicBuffer.append(encodeValue);
                            dynamicOffset += (encodeValue.length() >> 1);
                        } else {
                            fixedBuffer.append(encodeValue);
                        }
                    }

                    stringBuffer.append(fixedBuffer).append(dynamicBuffer);
                    break;
                }
            case LIST:
                {
                    StringBuffer lengthBuffer = new StringBuffer();
                    StringBuffer listValueBuffer = new StringBuffer();
                    StringBuffer offsetBuffer = new StringBuffer();

                    if (listType == ListType.DYNAMIC) {
                        lengthBuffer.append(TypeEncoder.encode(new Uint256(listValues.size())));
                    }

                    int dynamicOffset = listValues.size() * Type.MAX_BYTE_LENGTH;

                    for (ABIObject abiObject : listValues) {
                        String listValueEncode = abiObject.encode();
                        listValueBuffer.append(abiObject.encode());
                        if (abiObject.isDynamic()) {
                            offsetBuffer.append(TypeEncoder.encode(new Uint256(dynamicOffset)));
                            dynamicOffset += (listValueEncode.length() >> 1);
                        }
                    }

                    stringBuffer.append(lengthBuffer).append(offsetBuffer).append(listValueBuffer);
                    break;
                }
        }

        if (logger.isTraceEnabled()) {
            logger.trace("ABI: {}", stringBuffer.toString());
        }

        return stringBuffer.toString();
    }

    /**
     * decode this object
     *
     * @return
     */
    public ABIObject decode(String input) {
        return decode(input, 0);
    }

    /**
     * decode this object
     *
     * @return
     */
    private ABIObject decode(String input, int offset) {

        ABIObject abiObject = newObject();

        switch (type) {
            case VALUE:
                {
                    switch (valueType) {
                        case BOOL:
                            {
                                abiObject.setBoolValue(
                                        TypeDecoder.decode(input, offset, Bool.class));
                                break;
                            }
                        case UINT:
                            {
                                abiObject.setNumericValue(
                                        TypeDecoder.decode(input, offset, Uint256.class));
                                break;
                            }
                        case INT:
                            {
                                abiObject.setNumericValue(
                                        TypeDecoder.decode(input, offset, Int256.class));
                                break;
                            }
                        case FIXED:
                        case UFIXED:
                            {
                                throw new UnsupportedOperationException(
                                        " Unsupported fixed/unfixed type. ");
                                // break;
                            }
                        case BYTES:
                            {
                                abiObject.setBytesValue(
                                        TypeDecoder.decode(input, offset, Bytes32.class));
                                break;
                            }
                        case ADDRESS:
                            {
                                abiObject.setAddressValue(
                                        TypeDecoder.decode(input, offset, Address.class));
                                break;
                            }
                        case DBYTES:
                            {
                                abiObject.setDynamicBytesValue(
                                        TypeDecoder.decode(input, offset, DynamicBytes.class));
                                break;
                            }
                        case STRING:
                            {
                                abiObject.setStringValue(
                                        TypeDecoder.decode(input, offset, Utf8String.class));
                                break;
                            }
                    }
                    break;
                }
            case STRUCT:
                {
                    int structOffset = offset;
                    int initialOffset = offset;

                    for (int i = 0; i < structFields.size(); ++i) {
                        ABIObject structObject = abiObject.structFields.get(i);
                        ABIObject itemObject = null;
                        if (structObject.isDynamic()) {
                            int structValueOffset =
                                    TypeDecoder.decode(input, structOffset, Uint256.class)
                                            .getValue()
                                            .intValue();
                            itemObject =
                                    structObject.decode(
                                            input, initialOffset + (structValueOffset << 1));

                        } else {
                            itemObject = structObject.decode(input, structOffset);
                        }

                        abiObject.structFields.set(i, itemObject);
                        structOffset += structObject.offsetAsHexLength();
                    }
                    break;
                }
            case LIST:
                {
                    int listOffset = offset;
                    int initialOffset = offset;

                    int listLength = 0;
                    if (listType == ListType.DYNAMIC) {
                        // dynamic list length
                        listLength =
                                TypeDecoder.decode(input, listOffset, Uint256.class)
                                        .getValue()
                                        .intValue();
                        listOffset += (Type.MAX_BYTE_LENGTH << 1);
                        initialOffset += (Type.MAX_BYTE_LENGTH << 1);
                    } else {
                        // fixed list length
                        listLength = abiObject.getListLength();
                    }

                    if (logger.isTraceEnabled()) {
                        logger.trace(" listType: {}, listLength: {}", listType, listLength);
                    }

                    ABIObject listValueObject = abiObject.getListValueType();

                    for (int i = 0; i < listLength; i++) {
                        ABIObject itemABIObject = null;

                        if (listValueObject.isDynamic()) {
                            int listValueOffset =
                                    TypeDecoder.decode(input, listOffset, Uint256.class)
                                            .getValue()
                                            .intValue();
                            itemABIObject =
                                    abiObject
                                            .getListValueType()
                                            .decode(input, initialOffset + (listValueOffset << 1));
                        } else {
                            itemABIObject = abiObject.getListValueType().decode(input, listOffset);
                        }

                        listOffset += listValueObject.offsetAsHexLength();

                        abiObject.getListValues().add(itemABIObject);
                    }
                    break;
                }
        }

        return abiObject;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public NumericType getNumericValue() {
        return numericValue;
    }

    public Bool getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Bool boolValue) {
        this.type = ObjectType.VALUE;
        this.valueType = ValueType.BOOL;
        this.boolValue = boolValue;
    }

    public void setNumericValue(NumericType numericValue) {
        this.type = ObjectType.VALUE;
        this.valueType = ValueType.UINT;
        this.numericValue = numericValue;
    }

    public Bytes getBytesValue() {
        return bytesValue;
    }

    public void setBytesValue(Bytes bytesValue) {
        this.type = ObjectType.VALUE;
        this.valueType = ValueType.BYTES;
        this.bytesValue = bytesValue;
    }

    public Address getAddressValue() {
        return addressValue;
    }

    public void setAddressValue(Address addressValue) {
        this.type = ObjectType.VALUE;
        this.valueType = ValueType.ADDRESS;
        this.addressValue = addressValue;
    }

    public List<ABIObject> getStructFields() {
        return structFields;
    }

    public void setStructFields(List<ABIObject> structFields) {
        this.type = ObjectType.STRUCT;
        this.structFields = structFields;
    }

    public ListType getListType() {
        return listType;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }

    public List<ABIObject> getListValues() {
        return listValues;
    }

    public void setListValues(List<ABIObject> listValues) {
        this.type = ObjectType.LIST;
        this.listValues = listValues;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public DynamicBytes getDynamicBytesValue() {
        return dynamicBytesValue;
    }

    public void setDynamicBytesValue(DynamicBytes dynamicBytesValue) {
        this.dynamicBytesValue = dynamicBytesValue;
    }

    public Utf8String getStringValue() {
        return stringValue;
    }

    public void setStringValue(Utf8String stringValue) {
        this.stringValue = stringValue;
    }

    public ABIObject getListValueType() {
        return listValueType;
    }

    public void setListValueType(ABIObject listValueType) {
        this.listValueType = listValueType;
    }

    public int getListLength() {
        return listLength;
    }

    public void setListLength(int listLength) {
        this.listLength = listLength;
    }

    @Override
    public String toString() {

        String str = "ABIObject{" + "name='" + name + '\'' + ", type=" + type;

        if (type == ObjectType.VALUE) {
            str += ", valueType=" + valueType;
            switch (valueType) {
                case BOOL:
                    str += ", booValueType=";
                    str += Objects.isNull(boolValue) ? "null" : boolValue.getValue();
                    break;
                case UINT:
                case INT:
                    str += ", numericValue=";
                    str += Objects.isNull(numericValue) ? "null" : numericValue.getValue();
                    break;
                case ADDRESS:
                    str += ", addressValue=";
                    str += Objects.isNull(addressValue) ? "null" : addressValue.getValue();
                    break;
                case BYTES:
                    str += ", bytesValue=";
                    str += Objects.isNull(bytesValue) ? "null" : bytesValue.getValue();
                    break;
                case DBYTES:
                    str += ", dynamicBytesValue=";
                    str +=
                            Objects.isNull(dynamicBytesValue)
                                    ? "null"
                                    : dynamicBytesValue.getValue();
                case STRING:
                    str += ", stringValue=";
                    str += Objects.isNull(stringValue) ? "null" : stringValue.getValue();
            }
        } else if (type == ObjectType.LIST) {
            str += ", listType=" + listType;
            str += ", listValues=" + listValues + ", listLength=" + listLength;
        } else if (type == ObjectType.STRUCT) {
            str += ", structFields=" + structFields;
        }

        str += '}';
        return str;
    }
}
