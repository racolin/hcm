package com.banvie.hcm.model;

public class RefreshToken {
    private String refreshToken;

    public RefreshToken() {}

    public RefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
