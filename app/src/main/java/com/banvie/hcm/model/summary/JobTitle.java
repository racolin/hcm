package com.banvie.hcm.model.summary;

import java.io.Serializable;

public class JobTitle implements Serializable {
    public String id = "";
    public String name = "";

    public JobTitle() {}

    public JobTitle(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
