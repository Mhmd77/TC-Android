package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.RentCarObject;
import com.myapps.tc_android.service.model.UserHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyRentActivity extends AppCompatActivity {
    Car car;
    RentCarObject rentCarObject;
    List<String> locations;
    @BindView(R.id.res_text)
    TextView resText;
    @BindView(R.id.car_name_rent)
    TextView carNameRent;
    @BindView(R.id.username_rent)
    TextView usernameRent;
    @BindView(R.id.first_line)
    TextView firstLine;
    @BindView(R.id.starting_date_rent_text)
    TextView startingDateRentText;
    @BindView(R.id.get_starting_date)
    TextView getStartingDate;
    @BindView(R.id.end_date_rent_text)
    TextView endDateRentText;
    @BindView(R.id.get_end_date)
    TextView getEndDate;
    @BindView(R.id.second_line)
    TextView secondLine;
    @BindView(R.id.src_location_text)
    TextView srcLocationText;
    @BindView(R.id.get_src_location)
    TextView getSrcLocation;
    @BindView(R.id.des_location_text)
    TextView desLocationText;
    @BindView(R.id.get_des_location)
    TextView getDesLocation;
    @BindView(R.id.accept_reserve)
    Button acceptReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_rent);
        ButterKnife.bind(this);
        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("Car");
        rentCarObject = (RentCarObject) i.getSerializableExtra("Rent");
        locations = i.getStringArrayListExtra("Locations");
        setVariables();
    }

    private void setVariables() {
        carNameRent.setText(car.getName());
        usernameRent.setText(UserHolder.Instance().getUser().getName());
        startingDateRentText.setText(rentCarObject.getStartDate().toString());
        endDateRentText.setText(rentCarObject.getEndDate().toString());
        srcLocationText.setText(locations.get(rentCarObject.getSrcLocation()));
        desLocationText.setText(locations.get(rentCarObject.getDesLocation()));

    }

    @OnClick(R.id.accept_reserve)
    public void onViewClicked() {
        //TODO
    }
}
