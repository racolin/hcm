package com.banvie.hcm.model.summary;

import java.io.Serializable;

public class Organization implements Serializable {
    public String id = "";
    public String name = "";
    public String tenantId = "";
    public String orgName = "";

    public Organization() {

    }

    public Organization(String id, String name, String tenantId, String orgName) {
        this.id = id;
        this.name = name;
        this.tenantId = tenantId;
        this.orgName = orgName;
    }
}
