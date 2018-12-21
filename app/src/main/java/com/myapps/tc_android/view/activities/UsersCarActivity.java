package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.view.adapter.CarViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersCarActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_users_cars)
    PlaceHolderView recyclerViewUsersCars;
    private List<Car> cars;
    public static final String USERS_LIST_OF_CARS = "UsersCar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_car);
        ButterKnife.bind(this);
        initCars();
        recyclerViewUsersCars.getBuilder()
                .setItemViewCacheSize(2);
    }

    private void initCars() {
        cars = (List<Car>) getIntent().getSerializableExtra(USERS_LIST_OF_CARS);
        for (Car c :
                cars) {
            recyclerViewUsersCars.addView(new CarViewAdapter(recyclerViewUsersCars, this, c));
        }
    }
}
