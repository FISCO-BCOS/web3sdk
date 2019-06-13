package org.fisco.bcos.web3j.tx.txdecode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray1;
import org.junit.Test;

public class ArrayReferenceTest {

    public static void main(String[] args) throws BaseException, ClassNotFoundException {
        Object o = StaticArrayReference.create("int", 1);
        System.out.println(StaticArrayReference.create("int", 1).getClassType().getName());
    }

    @Test
    public void staticArrayRefTest() throws BaseException {
        assertThat(
                StaticArrayReference.create("int", 1).getType().getClass().getName(),
                equalTo(
                        new TypeReference<StaticArray1<Int256>>() {}.getType()
                                .getClass()
                                .getName()));
    }
}
