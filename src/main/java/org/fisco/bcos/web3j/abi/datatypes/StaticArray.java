package org.fisco.bcos.web3j.abi.datatypes;

import java.util.List;

/** Static array type. */
public class StaticArray<T extends Type> extends Array<T> {
    /**
     * Warning: increasing this constant will cause more generated StaticArrayN types, see:
     * AbiTypesGenerator#generateStaticArrayTypes
     */
    public static int MAX_SIZE_OF_STATIC_ARRAY = 1024;

    private Integer expectedSize;

    @SafeVarargs
    public StaticArray(T... values) {
        super(values[0].getTypeAsString() + "[" + values.length + "]", values);
        isValid();
    }

    @SafeVarargs
    public StaticArray(int expectedSize, T... values) {
        super(values[0].getTypeAsString() + "[" + values.length + "]", values);
        this.expectedSize = expectedSize;
        isValid();
    }

    public StaticArray(List<T> values) {
        super(values.get(0).getTypeAsString() + "[" + values.size() + "]", values);
        isValid();
    }

    public StaticArray(int expectedSize, List<T> values) {
        super(values.get(0).getTypeAsString() + "[" + values.size() + "]", values);
        this.expectedSize = expectedSize;
        isValid();
    }

    private void isValid() {
        if (expectedSize == null && value.size() > MAX_SIZE_OF_STATIC_ARRAY) {
            throw new UnsupportedOperationException(
                    "Static arrays with a length greater than 1024 are not supported.");
        } else if (expectedSize != null && value.size() != expectedSize) {
            throw new UnsupportedOperationException(
                    "Expected array of type ["
                            + getClass().getSimpleName()
                            + "] to have ["
                            + expectedSize
                            + "] elements.");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean dynamicType() {
        Type obj = value.get(0);

        if (obj instanceof StaticArray) {
            return ((T) obj).dynamicType();
        } else if ((obj instanceof NumericType)
                || (obj instanceof Address)
                || (obj instanceof Bool)
                || (obj instanceof Bytes)) {
            return false;
        } else if ((obj instanceof DynamicBytes)
                || (obj instanceof Utf8String)
                || (obj instanceof DynamicArray)) {
            return true;
        } else {
            throw new UnsupportedOperationException("Type cannot be encoded: " + obj.getClass());
        }
    }

    @Override
    public int offset() {

        if (dynamicType()) {
            return 1;
        }

        Object obj = value.get(0);

        if (obj instanceof StaticArray) {
            return ((Type) obj).offset() * getValue().size();
        }

        return getValue().size();
    }
}
