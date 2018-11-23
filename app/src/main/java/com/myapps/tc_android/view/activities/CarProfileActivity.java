package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.myapps.tc_android.R;
import com.myapps.tc_android.model.Car;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarProfileActivity extends AppCompatActivity {

    @BindView(R.id.txtCarName)
    TextView txtCarName;
    @BindView(R.id.txtFactory)
    TextView txtFactory;
    @BindView(R.id.txtKilometer)
    TextView txtKilometer;
    @BindView(R.id.txtColor)
    TextView txtColor;
    @BindView(R.id.txtYear)
    TextView txtYear;
    @BindView(R.id.isAutomate)
    CheckBox isAutomate;
    @BindView(R.id.button_photos)
    Button buttonPhotos;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_profile);
        ButterKnife.bind(this);
        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("Car");
        setVariables(car);
    }

    private void setVariables(Car car) {
        txtCarName.setText(car.getName());
        txtFactory.setText(car.getFactory());
        txtYear.setText(String.valueOf(car.getYear()));
        txtKilometer.setText(String.valueOf(car.getKilometer()));
        txtColor.setText(car.getColor());
        isAutomate.setChecked(car.isAutomate());
        txtPrice.setText(String.valueOf(car.getPrice()));
    }

    @OnClick(R.id.button_photos)
    public void onViewClicked() {
        startActivity(new Intent(CarProfileActivity.this,PhotoActivity.class));
    }
}
