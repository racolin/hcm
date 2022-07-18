package com.banvie.hcm.model;

public class Token {
    private String access_token;
    private String token_type;
    private long expire_in;
    private long receive;
    private String refresh_token;

    public long getReceive() {
        return receive;
    }

    public void setReceive(long receive) {
        this.receive = receive;
    }

    public long getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(long expire_in) {
        this.expire_in = expire_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

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
