package com.myapps.tc_android.controller;

import com.myapps.tc_android.model.RentCarObject;

import java.sql.Date;

public class RentBuilder {
    private int id;
    private int carId;
    private int userId;
    private Date startDate;
    private Date endDate;
    private int srcLocation;
    private int desLocation;

    public RentBuilder setRentId(int rentId) {
        this.id = rentId;
        return this;
    }

    public RentBuilder setCarId(int carId) {
        this.carId = carId;
        return this;
    }

    public RentBuilder setUserId(int userId) {
        this.userId = userId;
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

    public RentCarObject createRent() {
        return new RentCarObject(id, carId, userId, startDate, endDate, srcLocation, desLocation);
    }


}
