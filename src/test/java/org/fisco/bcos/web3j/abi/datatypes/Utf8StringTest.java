package org.fisco.bcos.web3j.abi.datatypes;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Utf8StringTest {

    @Test
    public void testToString() {
        assertThat(new Utf8String("").toString(), is(""));
        assertThat(new Utf8String("string").toString(), is("string"));
    }
}
