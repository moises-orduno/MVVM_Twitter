package com.moisesorduno.twittermvvm.model;

import com.google.gson.annotations.SerializedName;

public class TwitterTokenType {



    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("access_token")
    private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
