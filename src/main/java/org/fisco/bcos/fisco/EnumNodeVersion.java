package org.fisco.bcos.fisco;

import org.fisco.bcos.channel.protocol.ChannelPrococolExceiption;

public enum EnumNodeVersion {
    BCOS_2_0_0_RC1("2.0.0-rc1"),
    BCOS_2_0_0_RC2("2.0.0-rc2"),
    BCOS_2_0_0_RC3("2.0.0-rc3"),
    BCOS_2_0_0("2.0.0"),
    BCOS_2_0_1("2.0.1"),
    BCOS_2_1_0("2.1.0");

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

    class Version {
        private int major;
        private int child;
        private int phase;
        private String ext;

        @Override
        public String toString() {
            return "Version [major="
                    + major
                    + ", child="
                    + child
                    + ", phase="
                    + phase
                    + ", ext="
                    + ext
                    + "]";
        }

        public int getMajor() {
            return major;
        }

        public void setMajor(int major) {
            this.major = major;
        }

        public int getChild() {
            return child;
        }

        public void setChild(int child) {
            this.child = child;
        }

        public int getPhase() {
            return phase;
        }

        public void setPhase(int phase) {
            this.phase = phase;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }
    }

    private static Version getVersion(String version) throws ChannelPrococolExceiption {
        try {
            String[] s0 = version.trim().split("-");

            Version v = EnumNodeVersion.BCOS_2_0_0.new Version();
            if (s0.length > 1) {
                v.setExt(s0[1]);
            }

            String[] s1 = s0[0].split("\\.");
            if (s1.length >= 3) {
                v.setMajor(Integer.parseInt(s1[0].trim()));
                v.setChild(Integer.parseInt(s1[1].trim()));
                v.setPhase(Integer.parseInt(s1[2].trim()));
            } else {
                throw new ChannelPrococolExceiption(
                        " invalid node version format, version: " + version);
            }

            return v;
        } catch (Exception e) {
            throw new ChannelPrococolExceiption(
                    " invalid node version format, version: " + version);
        }
    }

    public static boolean channelProtocolHandleShakeSupport(String version)
            throws ChannelPrococolExceiption {
        Version v = getVersion(version);
        // 2.1.0
        return (v.getMajor() == 2) && (v.getChild() >= 1);
    }
}
