package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.Car;
import com.myapps.tc_android.model.UserHolder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarProfileActivity extends AppCompatActivity implements Callback<ApiResponse<Object>> {

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
    @BindView(R.id.button_delete_car_profile)
    ImageButton buttonDeleteCarProfile;
    @BindView(R.id.imageview_car_logo)
    CircleImageView imageviewCarLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_profile);
        ButterKnife.bind(this);
        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("Car");
        setVariables(car);
        if (UserHolder.Instance().getUser().getRole().equals("admin") == false) {
            buttonEditCarprofile.setVisibility(View.GONE);
            buttonDeleteCarProfile.setVisibility(View.GONE);
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
        //setting image
        Log.i(CarProfileActivity.class.getSimpleName(), RetrofitClientInstance.getBaseUrl() + ApiService.imageApi + car.getImageUrl());
        Picasso.get()
                .load(RetrofitClientInstance.getBaseUrl() + ApiService.imageApi + car.getImageUrl())
                .into(imageviewCarLogo);
    }


    @OnClick({R.id.button_edit_carprofile, R.id.button_delete_car_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_edit_carprofile:
                Intent intent = new Intent(CarProfileActivity.this, UpdateCarAdminActivity.class);
                finish();
                intent.putExtra("Car", car);
                startActivity(intent);
                break;
            case R.id.button_delete_car_profile:
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                Call<ApiResponse<Object>> call = service.deleteCar(car.getId());
                call.enqueue(this);
                break;
        }
    }


    @Override
    public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
        if (response.isSuccessful()) {
            Toast.makeText(CarProfileActivity.this, "Car : " + car.getName() + " deleted", Toast.LENGTH_SHORT).show();
            Log.i("Connection", "Car " + car.getId() + " deleted");
            finish();
        } else {
            Log.e("Connection", "Deleting Car Failed : " + response.message());
        }
    }

    @Override
    public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
        Log.e("Connection", "Deleting Car Failed : " + t.getMessage());
    }
}
