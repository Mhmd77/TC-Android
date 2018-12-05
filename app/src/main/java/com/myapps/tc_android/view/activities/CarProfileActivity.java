package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myapps.tc_android.R;
import com.myapps.tc_android.model.Car;
import com.myapps.tc_android.model.UserHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarProfileActivity extends AppCompatActivity {

    Car car;
    @BindView(R.id.textview_carprofile_year)
    TextView textviewCarprofileYear;
    @BindView(R.id.textview_carprofile_name)
    TextView textviewCarprofileName;
    @BindView(R.id.textview_carprofile_factory)
    TextView textviewCarprofileFactory;
    @BindView(R.id.textview_carprofile_kilometer)
    TextView textviewCarprofileKilometer;
    @BindView(R.id.textview_carprofile_cost)
    TextView textviewCarprofileCost;
    @BindView(R.id.textview_carprofile_color)
    TextView textviewCarprofileColor;
    @BindView(R.id.textview_carprofile_description)
    TextView textviewCarprofileDescription;
    @BindView(R.id.button_edit_carprofile)
    ImageButton buttonEditCarprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_profile);
        ButterKnife.bind(this);
        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("Car");
        setVariables(car);
        if(UserHolder.Instance().getUser().getName().equals("admin") == false){
            buttonEditCarprofile.setVisibility(View.GONE);
        }
    }

    private void setVariables(Car car) {
        textviewCarprofileName.setText(car.getName());
        textviewCarprofileFactory.setText(car.getFactory());
        textviewCarprofileYear.setText(String.valueOf(car.getYear()));
        textviewCarprofileKilometer.setText(String.valueOf(car.getKilometer()));
        textviewCarprofileColor.setText(car.getColor());
        textviewCarprofileCost.setText(String.valueOf(car.getPrice()));
        if (car.isAutomate()) {
            textviewCarprofileDescription.setText("AUTOMATE");
        } else {
            textviewCarprofileDescription.setText("MANUAL");
        }

    }

    @OnClick(R.id.button_edit_carprofile)
    public void onViewClicked() {
        Intent intent = new Intent(CarProfileActivity.this,UpdateCarAdminActivity.class);
        finish();
        intent.putExtra("Car",car);
        startActivity(intent);

    }

}
