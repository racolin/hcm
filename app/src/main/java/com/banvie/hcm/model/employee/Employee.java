package com.banvie.hcm.model.employee;

import com.banvie.hcm.model.summary.DirectReport;
import com.banvie.hcm.model.summary.JobTitle;
import com.banvie.hcm.model.summary.Organization;
import com.banvie.hcm.model.summary.ReportTo;
import com.banvie.hcm.model.summary.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employee implements Serializable {
    public String id = "";
    public String cif = "";
//    public String userType;
//    public String registerType;
//    public String profileType;
//    public long registration;
//    public String username;
    public String firstName = "";
//    public String lastName;
    public String image = "";
    public byte[] image_bytes = null;
//    public int status;
    public Organization organization = new Organization();
//    public List<Role> roles;
    public JobTitle jobTitle = new JobTitle();
//    public DirectReport directReport;
    public String fullName = "";
    public String phone = "";
//    public String permanentAddress;
//    public String temporaryAddress;
//    public String tenantId;
    public String companyEmail = "";
//    public String personalEmail;
//    public String certificates;
    public String gender = "";
    public String birthDay;
//    public String university;
//    public String major;
    public String maritalStatus = "";
//    public String nationality;
//    public boolean syncLDAPDirectReport;
//    public String ldapEntryDn;
//    public boolean isDeletable;
//    public List<String> userMultipleReportMethod;
//    public boolean isSkipCheckInOutNormal;
//    public String lastModifiedBy;
//    public ReportTo reportTo;
//    public String fullNameInVietnamese;
//    public String stringOnboardDate;
    public List<Employee> descendants = new ArrayList<>();

    public Employee getEmployeeLite() {
        return new Employee(id, null, null, image, null, null,
                jobTitle, fullName, phone, companyEmail, gender, birthDay, maritalStatus);
    }

    public Employee() {}

    public Employee(String id, String cif, String firstName, String image, byte[] image_bytes, Organization organization,
                    JobTitle jobTitle, String fullName, String phone, String companyEmail, String gender, String birthDay, String maritalStatus) {
        this.id = id;
        this.cif = cif;
        this.firstName = firstName;
        this.image = image;
        this.image_bytes = image_bytes;
        this.organization = organization;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.phone = phone;
        this.companyEmail = companyEmail;
        this.gender = gender;
        this.birthDay = birthDay;
        this.maritalStatus = maritalStatus;
    }
}
