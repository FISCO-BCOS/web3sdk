package org.fisco.bcos.channel.protocol;

public enum EnumChannelProtocolVersion {
    VERSION_1(1), // default version
    VERSION_2(2),
    VERSION_3(3);

    private int versionNumber;

    private EnumChannelProtocolVersion(int versionNumber) {
        this.setVersionNumber(versionNumber);
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public static EnumChannelProtocolVersion getMinimumProtocol() {

        // get minimum version number supported
        EnumChannelProtocolVersion[] versions = EnumChannelProtocolVersion.values();
        if (0 == versions.length) {
            return EnumChannelProtocolVersion.VERSION_1;
        }

        return versions[0];
    }

    public static EnumChannelProtocolVersion getMaximumProtocol() {
        // get highest version number supported
        EnumChannelProtocolVersion[] versions = EnumChannelProtocolVersion.values();
        if (0 == versions.length) {
            return EnumChannelProtocolVersion.VERSION_1;
        }

        return versions[versions.length - 1];
    }

    public static EnumChannelProtocolVersion toEnum(int v) throws ChannelPrococolExceiption {

        for (EnumChannelProtocolVersion enumVersion : EnumChannelProtocolVersion.values()) {
            if (enumVersion.getVersionNumber() == v) {
                return enumVersion;
            }
        }

        throw new ChannelPrococolExceiption(
                " not support channel protocol, version " + String.valueOf(v));
    }
}
