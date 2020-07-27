package org.fisco.bcos.web3j.precompile.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MemberVotes {

    private List<Vote> revoke;

    private List<Vote> grant;

    @JsonProperty("update_weight")
    private List<Vote> updateWeight;

    public List<Vote> getRevoke() {
        return revoke;
    }

    public void setRevoke(List<Vote> revoke) {
        this.revoke = revoke;
    }

    public List<Vote> getGrant() {
        return grant;
    }

    public void setGrant(List<Vote> grant) {
        this.grant = grant;
    }

    public List<Vote> getUpdateWeight() {
        return updateWeight;
    }

    public void setUpdateWeight(List<Vote> updateWeight) {
        this.updateWeight = updateWeight;
    }

    public boolean isEmpty() {
        return revoke.isEmpty() && grant.isEmpty() && updateWeight.isEmpty();
    }
}
