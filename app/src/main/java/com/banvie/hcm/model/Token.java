package com.banvie.hcm.model;

public class Token {
    public String access_token = "";
    public String token_type = "";
    public long expire_in = 0;
    public long receive = 0;
    public String refresh_token = "";

    @Override
    public String toString() {
        return "Token{" +
                ", access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expire_in=" + expire_in +
                ", receive=" + receive +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
