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
        MAX_SIZE_OF_STATIC_ARRAY = 32;
        if (expectedSize == null && value.size() > MAX_SIZE_OF_STATIC_ARRAY) {
            throw new UnsupportedOperationException(
                    "Static arrays with a length greater than 32 are not supported.");
        } else if (expectedSize != null && value.size() != expectedSize) {
            throw new UnsupportedOperationException(
                    "Expected array of type ["
                            + getClass().getSimpleName()
                            + "] to have ["
                            + expectedSize
                            + "] elements.");
        }
    }
}
