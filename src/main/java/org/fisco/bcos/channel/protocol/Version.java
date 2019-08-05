package org.fisco.bcos.channel.protocol;

public enum Version {
    VERSION_1(1), // default version
    VERSION_2(2);

    private int versionNumber;

    private Version(int versionNumber) {
        this.setVersionNumber(versionNumber);
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public static Version getHighestSupported() {
        // get highest version number supported
        Version[] versions = Version.values();
        if (0 == versions.length) {
            return Version.VERSION_1;
        }

        return versions[versions.length - 1];
    }

    public static Version convert(int v) {
    	for(Version ver:Version.values()) {
    		if(ver.getVersionNumber() == v) {
    			return ver;
    		}
    	}
    	
    	return Version.VERSION_1;
    }
}
