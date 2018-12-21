package com.myapps.tc_android.service.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Car implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    private String name;
    @SerializedName("factory")
    private String factory;
    @SerializedName("year")
    private int year;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("kilometer")
    private int kilometer;
    @SerializedName("color")
    private String color;
    @SerializedName("description")
    private String description;
    @SerializedName("automate")
    private boolean automate;
    @SerializedName("price")
    private int price;
    @SerializedName("image_url")
    private String imageUrl;

    public Car(int id,int user_id, String name, String factory, int year, int kilometer, String color, String description, boolean automate, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.factory = factory;
        this.year = year;
        this.kilometer = kilometer;
        this.color = color;
        this.description = description;
        this.automate = automate;
        this.price = price;
        this.user_id = user_id;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFactory() {
        return factory;
    }

    public int getYear() {
        return year;
    }

    public int getKilometer() {
        return kilometer;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAutomate() {
        return automate;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
