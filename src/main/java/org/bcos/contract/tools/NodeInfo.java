package org.bcos.contract.tools;

/**
 * Created by mingzhenliu on 2018/3/6.
 */
public class NodeInfo {
    private String id;
    private String ip;
    private Integer port;
    private Integer category;
    private String desc;
    private String CAhash = "";
    private String agencyinfo;
    private Integer idx;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCAhash() {
        return CAhash;
    }

    public void setCAhash(String CAhash) {
        this.CAhash = CAhash;
    }

    public String getAgencyinfo() {
        return agencyinfo;
    }

    public void setAgencyinfo(String agencyinfo) {
        this.agencyinfo = agencyinfo;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }
    @Override
    public String toString() {
        return "NodeInfo{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", category=" + category +
                ", desc='" + desc + '\'' +
                ", CAhash='" + desc + '\'' +
                ", agencyinfo='" + agencyinfo + '\'' +
                ", idx=" + idx +
                '}';
    }
}
