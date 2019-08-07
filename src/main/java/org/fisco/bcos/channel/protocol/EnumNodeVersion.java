package org.fisco.bcos.channel.protocol;

public enum EnumNodeVersion {
    BCOS_RC1("2.0.0-rc1"),
    BCOS_RC2("2.0.0-rc2"),
    BCOS_RC3("2.0.0-rc3"),
    BCOS_20("2.0.0"),
    BCOS_21("2.1.0");

    private String version;

    private EnumNodeVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static boolean channelProtocolHandleShakeSupport(String version) {
        return !(version.equals(BCOS_RC1.getVersion())
                || version.equals(BCOS_RC2.getVersion())
                || version.equals(BCOS_RC3.getVersion())
                || version .equals(BCOS_20.getVersion()));
    }
}
