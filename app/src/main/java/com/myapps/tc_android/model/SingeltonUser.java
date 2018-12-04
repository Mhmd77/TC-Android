package com.myapps.tc_android.model;

public class SingeltonUser {
    private static SingeltonUser instance;
    private User user;
    //no outer class can initialize this class's object
    private SingeltonUser() {}

    public static SingeltonUser Instance()
    {
        if (instance == null)
        {
            instance = new SingeltonUser();
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
