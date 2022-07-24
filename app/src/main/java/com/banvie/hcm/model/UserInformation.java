package com.banvie.hcm.model;

import retrofit2.http.PUT;

public class UserInformation {
    public String userId;
    public String family_name;
    public String given_name;

    public String getFullName() {
        return family_name + " " + given_name;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "userId='" + userId + '\'' +
                ", family_name='" + family_name + '\'' +
                ", given_name='" + given_name + '\'' +
                '}';
    }
}
