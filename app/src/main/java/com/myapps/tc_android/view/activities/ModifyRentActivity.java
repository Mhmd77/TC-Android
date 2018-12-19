package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myapps.tc_android.R;
import com.myapps.tc_android.model.Car;
import com.myapps.tc_android.model.RentCarObject;

public class ModifyRentActivity extends AppCompatActivity {
    Car car;
    RentCarObject rentCarObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_rent);
        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("Car");
        rentCarObject = (RentCarObject) i.getSerializableExtra("Rent");
    }
}
