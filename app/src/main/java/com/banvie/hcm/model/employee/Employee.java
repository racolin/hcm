package com.banvie.hcm.model.employee;

import com.banvie.hcm.model.summary.JobTitle;


import java.io.Serializable;

public class Employee implements Serializable {
    public String id = "";
    public String image = "";
    public String cif = "";
    public byte[] image_bytes = null;
    public JobTitle jobTitle = new JobTitle();
    public String fullName = "";
    public String username = "";
}
