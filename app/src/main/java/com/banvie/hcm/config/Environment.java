package com.banvie.hcm.config;

public class Environment {
    public static final String DEV = "https://dev-nexthcm-api.banvien.com.vn/";
    public static final String QA = "https://qa-nexthcm-api.banvien.com.vn/";
    public static final String UAT = "https://uat-nexthcm-api.banvien.com.vn/";
    public static final String PRODUCTION = "https://api-hcm.banvien.com.vn/";

    public static String getEnvironment() {
        return PRODUCTION;
    }
}
