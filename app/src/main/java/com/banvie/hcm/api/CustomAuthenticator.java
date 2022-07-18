package com.banvie.hcm.api;

import android.util.Log;

import com.banvie.hcm.model.RefreshToken;
import com.banvie.hcm.model.Token;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class CustomAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (Constant.REFRESH_TOKEN.equals("")) return null;
        Token token = ApiService.apiService.refreshToken(new RefreshToken(Constant.REFRESH_TOKEN)).execute().body();
        Constant.ACCESS_TOKEN = token.getAccess_token();
        Constant.REFRESH_TOKEN = token.getRefresh_token();
        Constant.EXPIRE_IN = token.getExpire_in();
        return response.request().newBuilder()
                .header("Authorization", Constant.TOKEN_TYPE + " " + Constant.ACCESS_TOKEN)
                .build();
    }
}
