package com.myapps.tc_android.service.model;


import java.sql.Date;

public class RentBuilder {
    private int id;
    private int carId;
    private Date startDate;
    private Date endDate;
    private int srcLocation;
    private int desLocation;
    private int cost;
    private int kilometer;

    public RentBuilder setCost(int cost){
        this.cost = cost;
        return this;
    }
    public RentBuilder setKilometer(int kilometer){
        this.kilometer = kilometer;
        return this;
    }

    public RentBuilder setCarId(int carId) {
        this.carId = carId;
        return this;
    }

    public RentBuilder setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public RentBuilder setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public RentBuilder setSrcLocation(int srcLocation) {
        this.srcLocation = srcLocation;
        return this;
    }

    public RentBuilder setDesLocation(int desLocation) {
        this.desLocation = desLocation;
        return this;
    }

    public RentCar createRent() {
        return new RentCar(id, carId, startDate, endDate, srcLocation, desLocation,cost,kilometer);
    }


}
