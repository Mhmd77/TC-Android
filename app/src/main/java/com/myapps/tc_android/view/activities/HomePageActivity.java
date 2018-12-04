package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.myapps.tc_android.R;

import butterknife.ButterKnife;

public class HomePageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
    }

}
