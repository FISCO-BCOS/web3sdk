package org.fisco.bcos.web3j.tx.txdecode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int8;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint8;
import org.junit.Test;

public class ContractTypeUtilTest {
    @Test
    public void validIntTest() {
        assertThat(ContractTypeUtil.invalidInt("int"), is(true));
        assertThat(ContractTypeUtil.invalidInt("int1"), is(true));
        assertThat(ContractTypeUtil.invalidInt("int256"), is(true));
        assertThat(ContractTypeUtil.invalidInt("int8"), is(true));
        assertThat(ContractTypeUtil.invalidInt("int0"), is(false));
        assertThat(ContractTypeUtil.invalidInt("int257"), is(false));
        assertThat(ContractTypeUtil.invalidInt("inta"), is(false));
        assertThat(ContractTypeUtil.invalidInt("aa"), is(false));
        assertThat(ContractTypeUtil.invalidInt(""), is(false));
        assertThat(ContractTypeUtil.invalidInt("uint"), is(false));
    }

    @Test
    public void validUintTest() {
        assertThat(ContractTypeUtil.invalidUint("uint"), is(true));
        assertThat(ContractTypeUtil.invalidUint("uint1"), is(true));
        assertThat(ContractTypeUtil.invalidUint("uint256"), is(true));
        assertThat(ContractTypeUtil.invalidUint("uint8"), is(true));
        assertThat(ContractTypeUtil.invalidUint("uint0"), is(false));
        assertThat(ContractTypeUtil.invalidUint("uint257"), is(false));
        assertThat(ContractTypeUtil.invalidUint(""), is(false));
        assertThat(ContractTypeUtil.invalidUint("int"), is(false));
    }

    @Test
    public void getTypeTest() throws BaseException {
        assertThat(ContractTypeUtil.getType("bytes").getName(), is(DynamicBytes.class.getName()));
        assertThat(ContractTypeUtil.getType("address").getName(), is(Address.class.getName()));
        assertThat(ContractTypeUtil.getType("string").getName(), is(Utf8String.class.getName()));
        assertThat(ContractTypeUtil.getType("int").getName(), is(Int256.class.getName()));
        assertThat(ContractTypeUtil.getType("uint").getName(), is(Uint256.class.getName()));
        assertThat(ContractTypeUtil.getType("int256").getName(), is(Int256.class.getName()));
        assertThat(ContractTypeUtil.getType("uint256").getName(), is(Uint256.class.getName()));
        assertThat(ContractTypeUtil.getType("int8").getName(), is(Int8.class.getName()));
        assertThat(ContractTypeUtil.getType("uint8").getName(), is(Uint8.class.getName()));
        assertThat(ContractTypeUtil.getType("bool").getName(), is(Bool.class.getName()));
        assertThat(ContractTypeUtil.getType("bytes1").getName(), is(Bytes1.class.getName()));
        assertThat(ContractTypeUtil.getType("bytes32").getName(), is(Bytes32.class.getName()));
    }
}
