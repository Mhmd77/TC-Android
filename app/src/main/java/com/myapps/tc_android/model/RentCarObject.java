package com.myapps.tc_android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class RentCarObject implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("car_id")
    private int carId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("end_date")
    private Date endDate;
    @SerializedName("source_location")
    private int srcLocation;
    @SerializedName("destination_location")
    private int desLocation;


    public RentCarObject(int id, int carId, int userId, Date startDate, Date endDate, int srcLocation, int desLocation) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.srcLocation = srcLocation;
        this.desLocation = desLocation;
    }




}
