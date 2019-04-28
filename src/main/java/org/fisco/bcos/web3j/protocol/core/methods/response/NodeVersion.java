package org.fisco.bcos.web3j.protocol.core.methods.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fisco.bcos.web3j.protocol.core.Response;

/** getNodeVersion. */
public class NodeVersion extends Response<NodeVersion.Version> {

    public Version getNodeVersion() {
        return getResult();
    }

    public static class Version {
        @JsonProperty("Build Time")
        private String buildTime;

        @JsonProperty("Build Type")
        private String buildType;

        @JsonProperty("FISCO-BCOS Version")
        private String version;

        @JsonProperty("Git Branch")
        private String gitBranch;

        @JsonProperty("Git Commit Hash")
        private String gitCommit;

        public Version() {
            super();
        }

        public Version(
                String buildTime,
                String buildType,
                String version,
                String gitBranch,
                String gitCommit) {
            super();
            this.buildTime = buildTime;
            this.buildType = buildType;
            this.version = version;
            this.gitBranch = gitBranch;
            this.gitCommit = gitCommit;
        }

        public String getBuildTime() {
            return buildTime;
        }

        public void setBuildTime(String buildTime) {
            this.buildTime = buildTime;
        }

        public String getBuildType() {
            return buildType;
        }

        public void setBuildType(String buildType) {
            this.buildType = buildType;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getGitBranch() {
            return gitBranch;
        }

        public void setGitBranch(String gitBranch) {
            this.gitBranch = gitBranch;
        }

        public String getGitCommit() {
            return gitCommit;
        }

        public void setGitCommit(String gitCommit) {
            this.gitCommit = gitCommit;
        }
    }
}
