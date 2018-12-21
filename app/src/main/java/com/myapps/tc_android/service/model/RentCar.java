package com.myapps.tc_android.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class RentCar implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("car_id")
    private int carId;
    @SerializedName("start")
    private Date startDate;
    @SerializedName("end")
    private Date endDate;
    @SerializedName("source")
    private int srcLocation;
    @SerializedName("destination")
    private int desLocation;
    @SerializedName("cost")
    private int cost;
    @SerializedName("kilometer")
    private int kilometer;

    public RentCar(int id, int carId, Date startDate, Date endDate, int srcLocation, int desLocation, int cost, int kilometer) {
        this.id = id;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.srcLocation = srcLocation;
        this.desLocation = desLocation;
        this.cost = cost;
        this.kilometer = kilometer;
    }


    public int getId() {
        return id;
    }

    public int getCarId() {
        return carId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getSrcLocation() {
        return srcLocation;
    }

    public int getDesLocation() {
        return desLocation;
    }

    public int getCost() {
        return cost;
    }

    public int getKilometer() {
        return kilometer;
    }
}
