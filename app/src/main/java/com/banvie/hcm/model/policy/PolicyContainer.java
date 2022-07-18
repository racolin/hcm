package com.banvie.hcm.model.policy;

import java.util.List;

public class PolicyContainer {
    private String code;
    private DataPolicy data;

    public PolicyContainer() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataPolicy getData() {
        return data;
    }

    public void setData(DataPolicy data) {
        this.data = data;
    }
}
