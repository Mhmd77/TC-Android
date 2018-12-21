package com.myapps.tc_android.view.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.model.UserHolder;
import com.myapps.tc_android.viewmodel.AcceptRentViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AcceptRentActivity extends AppCompatActivity {
    Car car;
    RentCar rentCar;
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
    @BindView(R.id.get_cost)
    TextView getCost;
    @BindView(R.id.get_kilometer)
    TextView getKilometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_rent);
        ButterKnife.bind(this);
        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("Car");
        rentCar = (RentCar) i.getSerializableExtra("Rent");
        locations = i.getStringArrayListExtra("Locations");
        setVariables();
    }

    private void setVariables() {
        carNameRent.setText(car.getName());
        usernameRent.setText(UserHolder.Instance().getUser().getName());
        startingDateRentText.setText(rentCar.getStartDate().toString());
        endDateRentText.setText(rentCar.getEndDate().toString());
        srcLocationText.setText(locations.get(rentCar.getSrcLocation()));
        desLocationText.setText(locations.get(rentCar.getDesLocation()));
        getCost.setText(rentCar.getCost());
        getKilometer.setText(rentCar.getKilometer());
    }

    @OnClick(R.id.accept_reserve)
    public void onViewClicked() {
        AcceptRentViewModel.Factory factory = new AcceptRentViewModel.Factory(rentCar);
        final AcceptRentViewModel viewModel = ViewModelProviders.of(this, factory).get(AcceptRentViewModel.class);
        observeRentResponse(viewModel);
        Toast.makeText(this, "You Rent " + car.getName(), Toast.LENGTH_LONG).show();
        finish();
    }

    private void observeRentResponse(AcceptRentViewModel viewModel) {
        viewModel.getRentObservableData().observe(this, new Observer<RentCar>() {
            @Override
            public void onChanged(@Nullable RentCar rentCar) {
                if (rentCar == null)
                    Log.e("Rent", "Rent did not add successfully");
            }
        });
    }
}
