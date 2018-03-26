package org.bcos.contract.tools;

/**
 * Created by mingzhenliu on 2018/3/6.
 */
public class CaInfo {
    private String hash;
    private String pubkey;
    private String orgname;
    private Integer notbefore;
    private Integer notafter;
    private Integer status;
    private String whitelist;
    private String blacklist;



    @Override
    public String toString() {
        return "CaInfo{" +
                "hash='" + hash + '\'' +
                ", blacklist=" + blacklist +
                '}';
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Integer getNotbefore() {
        return notbefore;
    }

    public void setNotbefore(Integer notbefore) {
        this.notbefore = notbefore;
    }

    public Integer getNotafter() {
        return notafter;
    }

    public void setNotafter(Integer notafter) {
        this.notafter = notafter;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(String whitelist) {
        this.whitelist = whitelist;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }
}
