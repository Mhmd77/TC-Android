package com.myapps.tc_android.utils;

public class TokenAccess {
    private static final TokenAccess ourInstance = new TokenAccess();
    private String token;

    public static TokenAccess getInstance() {
        return ourInstance;
    }

    private TokenAccess() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
