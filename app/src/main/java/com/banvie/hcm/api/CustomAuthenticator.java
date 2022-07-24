package com.banvie.hcm.api;


import com.banvie.hcm.model.RefreshToken;
import com.banvie.hcm.model.Token;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class CustomAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (Constant.REFRESH_TOKEN.equals("")) return null;
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.refreshToken = Constant.REFRESH_TOKEN;

        retrofit2.Response<Token> res = ApiService.apiService.refreshToken(refreshToken).execute();
        if (!res.isSuccessful()) {
            Constant.ACCESS_TOKEN = "";
            return null;
        }

        Token token = res.body();

        Constant.ACCESS_TOKEN = token.access_token;
        Constant.REFRESH_TOKEN = token.refresh_token;
        Constant.EXPIRE_IN = token.expire_in;

        return response.request().newBuilder()
                .header("Authorization", Constant.TOKEN_TYPE + " " + Constant.ACCESS_TOKEN)
                .build();
    }
}
