package org.fisco.bcos.web3j.abi.datatypes;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.stream.IntStream;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.junit.Test;

public class StaticArrayTest {

    @Test
    public void canBeInstantiatedWithLessThan32Elements() {
        final StaticArray<Uint> array = new StaticArray<>(arrayOfUints(32));

        assertThat(array.getValue().size(), equalTo(32));
    }

    @Test
    public void canBeInstantiatedWithSizeMatchingType() {
        final StaticArray<Uint> array = new StaticArray3<>(arrayOfUints(3));

        assertThat(array.getValue().size(), equalTo(3));
    }

    @Test
    public void throwsIfSizeDoesntMatchType() {
        try {
            new StaticArray3<>(arrayOfUints(4));
            fail();
        } catch (UnsupportedOperationException e) {
            assertThat(
                    e.getMessage(),
                    equalTo("Expected array of type [StaticArray3] to have [3] elements."));
        }
    }

    @Test
    public void throwsIfSizeIsAboveMaxOf32() {
        try {
            new StaticArray<>(arrayOfUints(1025));
        } catch (UnsupportedOperationException e) {
            assertThat(
                    e.getMessage(),
                    equalTo(
                            "Bitsize must be 8 bit aligned, and in range 0 < bitSize <= 256, and in valid range."));
        }
    }

    private Uint[] arrayOfUints(int length) {
        return IntStream.rangeClosed(1, length).mapToObj(Uint8::new).toArray(Uint[]::new);
    }
}
