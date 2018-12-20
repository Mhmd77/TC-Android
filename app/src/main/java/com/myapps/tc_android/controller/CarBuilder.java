package com.myapps.tc_android.controller;


import com.myapps.tc_android.model.Car;

public class CarBuilder {
    private int id;
    private String name;
    private String factory;
    private int year;
    private int kilometer;
    private String color;
    private String description;
    private boolean automate;
    private int price;
    private String imageUrl;

    public CarBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CarBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CarBuilder setFactory(String factory) {
        this.factory = factory;
        return this;
    }

    public CarBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public CarBuilder setKilometer(int kilometer) {
        this.kilometer = kilometer;
        return this;
    }

    public CarBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public CarBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CarBuilder setAutomate(boolean automate) {
        this.automate = automate;
        return this;
    }

    public CarBuilder setPrice(int price) {
        this.price = price;
        return this;
    }

    public CarBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Car createCar() {
        return new Car(id, name, factory, year, kilometer, color, description, automate, price,imageUrl);
    }
}