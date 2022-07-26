package com.banvie.hcm.model.employee;

import com.banvie.hcm.model.summary.JobTitle;
import com.banvie.hcm.model.summary.Organization;

import java.io.Serializable;

public class EmployeeInformation implements Serializable {
    public String id = "";
    public String cif = "";
    public String username = "";
    public String image = "";
    public byte[] image_bytes = null;
    public JobTitle jobTitle = new JobTitle();
    public Organization organization = new Organization();
    public String fullName = "";
    public String phone = "";
    public String companyEmail = "";
    public String gender = "";
    public String birthday = "";
    public String nationality = "";
    public String maritalStatus = "";
    public String stringOnboardDate = "";
}
