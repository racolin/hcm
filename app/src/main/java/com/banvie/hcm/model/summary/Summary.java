package com.banvie.hcm.model.summary;

import java.util.ArrayList;
import java.util.List;

public class Summary {
    public String id = "";
    public String cif = "";
    public String userType = "";
    public String registerType = "";
    public String profileType = "";
    public long registration = 0;
    public String username = "";
    public String firstName = "";
    public String lastName = "";
    public int status = 0;
    public Organization organization = new Organization();
    public List<Role> roles = new ArrayList<>();
    public JobTitle jobTitle = new JobTitle();
    public DirectReport directReport = new DirectReport();
    public String fullName = "";
    public String phone = "";
    public String permanentAddress = "";
    public String temporaryAddress = "";
    public String tenantId = "";
    public String companyEmail = "";
    public String personalEmail = "";
    public String certificates = "";
    public String gender = "";
    public String birthDay = "";
    public String university = "";
    public String major = "";
    public String maritalStatus = "";
    public String nationality = "";
    public boolean syncLDAPDirectReport = false;
    public String ldapEntryDn = "";
    public boolean isDeletable = false;
//    List<public String> userMultipleReportMethod;
    public boolean isSkipCheckInOutNormal = false;
    public long lastModifiedDate = 0;
    public String lastModifiedBy = "";
    public String stringOnboardDate = "";

}
