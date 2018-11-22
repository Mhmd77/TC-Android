package com.myapps.tc_android.model;

public class SignUpInfo {
    private String username;
    private String password;
    private String identificationId;
    private String email;

    public SignUpInfo(String username, String password, String identificationId, String email) {
        this.username = username;
        this.password = password;
        this.identificationId = identificationId;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public String getEmail() {
        return email;
    }
}
