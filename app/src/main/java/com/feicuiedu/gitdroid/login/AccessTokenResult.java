package com.feicuiedu.gitdroid.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 2016/7/29.
 */
public class AccessTokenResult {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("scope")
    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}
