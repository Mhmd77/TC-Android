package com.myapps.tc_android.service.model;

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
