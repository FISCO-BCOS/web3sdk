package org.fisco.bcos.channel.test.protocol;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.fisco.bcos.channel.protocol.ChannelPrococolExceiption;
import org.fisco.bcos.fisco.EnumNodeVersion;
import org.junit.Test;

public class EnumNodeVersionTest {

    @Test
    public void EnumNodeVersionTestFunc0() {
        try {
            assertThat(
                    EnumNodeVersion.channelProtocolHandleShakeSupport(
                            EnumNodeVersion.BCOS_2_0_0_RC1.getVersion()),
                    is(false));
            assertThat(
                    EnumNodeVersion.channelProtocolHandleShakeSupport(
                            EnumNodeVersion.BCOS_2_0_0_RC2.getVersion()),
                    is(false));
            assertThat(
                    EnumNodeVersion.channelProtocolHandleShakeSupport(
                            EnumNodeVersion.BCOS_2_0_0_RC3.getVersion()),
                    is(false));
            assertThat(
                    EnumNodeVersion.channelProtocolHandleShakeSupport(
                            EnumNodeVersion.BCOS_2_0_0.getVersion()),
                    is(false));
            assertThat(
                    EnumNodeVersion.channelProtocolHandleShakeSupport(
                            EnumNodeVersion.BCOS_2_0_1.getVersion()),
                    is(false));
            assertThat(
                    EnumNodeVersion.channelProtocolHandleShakeSupport(
                            EnumNodeVersion.BCOS_2_1_0.getVersion()),
                    is(true));
        } catch (ChannelPrococolExceiption e) {
            assertThat(true, is(false));
        }
    }

    @Test
    public void EnumNodeVersionTestFunc1() {
        try {
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("1.1.0"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("1.2.3"), is(false));

            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.0-rc1"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.0-rc2"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.0-rc3"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.0-rc4"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.0-rc5"), is(false));

            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.0"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.1"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.2"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.3"), is(false));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.0.4"), is(false));

            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.1.0"), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.1.1"), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.1.2"), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.1.12"), is(true));

            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.2.0."), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.2.0."), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.2.0"), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.3.1-aaa"), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.4.2"), is(true));
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.4.3-rc1"), is(true));

        } catch (ChannelPrococolExceiption e) {
            assertThat(true, is(false));
        }
    }

    @Test
    public void EnumNodeVersionTestFunc2() {
        boolean excp0 = false;
        try {
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport(""), is(false));
        } catch (ChannelPrococolExceiption e) {
            excp0 = true;
        }

        assertThat(excp0, is(true));

        boolean excp1 = false;
        try {
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("aaaa"), is(false));
        } catch (ChannelPrococolExceiption e) {
            excp1 = true;
        }

        assertThat(excp1, is(true));

        boolean excp2 = false;
        try {
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.4.2.aa"), is(true));
        } catch (ChannelPrococolExceiption e) {
            excp2 = true;
        }

        assertThat(excp2, is(false));

        boolean excp3 = false;
        try {
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("a.b.c"), is(true));
        } catch (ChannelPrococolExceiption e) {
            excp3 = true;
        }

        assertThat(excp3, is(true));

        boolean excp4 = false;
        try {
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2"), is(true));
        } catch (ChannelPrococolExceiption e) {
            excp4 = true;
        }

        assertThat(excp4, is(true));

        boolean excp5 = false;
        try {
            assertThat(EnumNodeVersion.channelProtocolHandleShakeSupport("2.1"), is(true));
        } catch (ChannelPrococolExceiption e) {
            excp5 = true;
        }

        assertThat(excp5, is(true));
    }
}
