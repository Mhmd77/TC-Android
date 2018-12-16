package com.myapps.tc_android.service.model;

public class UserHolder {
    private static UserHolder instance;
    private User user;
    //no outer class can initialize this class's object
    private UserHolder() {}

    public static UserHolder Instance()
    {
        if (instance == null)
        {
            instance = new UserHolder();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
