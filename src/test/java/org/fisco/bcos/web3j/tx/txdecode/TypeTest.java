package org.fisco.bcos.web3j.tx.txdecode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.junit.Test;

public class TypeTest {

    @Test
    public void typeTest0() {
        AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type("int");

        assertThat(type.getBaseName(), is("int"));
        assertThat(type.getName(), is("int"));
        assertThat(type.getDepth().size(), is(0));
        assertThat(type.arrayType(), is(false));
        assertThat(type.dynamicArray(), is(false));
        assertThat(type.staticArray(), is(false));
    }

    @Test
    public void typeTest1() {
        AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type("int[]");

        assertThat(type.getBaseName(), is("int"));
        assertThat(type.getName(), is("int[]"));
        assertThat(type.getDepth().size(), is(1));
        assertThat(type.arrayType(), is(true));
        assertThat(type.dynamicArray(), is(true));
        assertThat(type.staticArray(), is(false));
    }

    @Test
    public void typeTest2() {
        AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type("int[3]");

        assertThat(type.getBaseName(), is("int"));
        assertThat(type.getName(), is("int[3]"));
        assertThat(type.getDepth().size(), is(1));
        assertThat(type.arrayType(), is(true));
        assertThat(type.dynamicArray(), is(false));
        assertThat(type.staticArray(), is(true));
    }

    @Test
    public void typeTest3() {
        AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type("string[][3]");

        assertThat(type.getBaseName(), is("string"));
        assertThat(type.getName(), is("string[][3]"));
        assertThat(type.getDepth().size(), is(2));
        assertThat(type.arrayType(), is(true));
        assertThat(type.dynamicArray(), is(false));
        assertThat(type.staticArray(), is(true));

        List<Integer> depths = type.getDepth();

        assertThat(depths.size(), is(2));
        assertThat(depths.get(0), is(0));
        assertThat(depths.get(1), is(3));
    }

    @Test
    public void typeTest4() {
        AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type("string[3][]");

        assertThat(type.getBaseName(), is("string"));
        assertThat(type.getName(), is("string[3][]"));
        assertThat(type.getDepth().size(), is(2));
        assertThat(type.arrayType(), is(true));
        assertThat(type.dynamicArray(), is(true));
        assertThat(type.staticArray(), is(false));

        List<Integer> depths = type.getDepth();

        assertThat(depths.size(), is(2));
        assertThat(depths.get(0), is(3));
        assertThat(depths.get(1), is(0));
    }

    @Test
    public void typeTest5() {
        AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type("string[][][][]");

        assertThat(type.getBaseName(), is("string"));
        assertThat(type.getName(), is("string[][][][]"));
        assertThat(type.getDepth().size(), is(4));
        assertThat(type.arrayType(), is(true));
        assertThat(type.dynamicArray(), is(true));
        assertThat(type.staticArray(), is(false));

        List<Integer> depths = type.getDepth();

        assertThat(depths.size(), is(4));
        assertThat(depths.get(0), is(0));
        assertThat(depths.get(1), is(0));
        assertThat(depths.get(2), is(0));
        assertThat(depths.get(3), is(0));
    }

    @Test
    public void typeTest6() {
        AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type("string[1][2][3][4]");

        assertThat(type.getBaseName(), is("string"));
        assertThat(type.getName(), is("string[1][2][3][4]"));
        assertThat(type.getDepth().size(), is(4));
        assertThat(type.arrayType(), is(true));
        assertThat(type.dynamicArray(), is(false));
        assertThat(type.staticArray(), is(true));

        List<Integer> depths = type.getDepth();

        assertThat(depths.size(), is(4));
        assertThat(depths.get(0), is(1));
        assertThat(depths.get(1), is(2));
        assertThat(depths.get(2), is(3));
        assertThat(depths.get(3), is(4));
    }
}
