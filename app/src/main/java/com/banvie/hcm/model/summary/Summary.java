package com.banvie.hcm.model.summary;

import java.util.List;

public class Summary {
    public String id;
    public String cif;
    public String userType;
    public String registerType;
    public String profileType;
    public long registration;
    public String username;
    public String firstName;
    public String lastName;
    public int status;
    public Organization organization;
    public List<Role> roles;
    public JobTitle jobTitle;
    public DirectReport directReport;
    public String fullName;
    public String phone;
    public String permanentAddress;
    public String temporaryAddress;
    public String tenantId;
    public String companyEmail;
    public String personalEmail;
    public String certificates;
    public String gender;
    public String birthDay;
    public String university;
    public String major;
    public String maritalStatus;
    public String nationality;
    boolean syncLDAPDirectReport;
    public String ldapEntryDn;
    public boolean isDeletable;
//    List<public String> userMultipleReportMethod;
    public boolean isSkipCheckInOutNormal;
    public long lastModifiedDate;
    public String lastModifiedBy;
    public String stringOnboardDate;


}
