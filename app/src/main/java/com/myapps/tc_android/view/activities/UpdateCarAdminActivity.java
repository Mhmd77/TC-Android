package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.CarBuilder;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.Car;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCarAdminActivity extends AppCompatActivity implements Callback {

    @BindView(R.id.editText_updateCar_name)
    EditText editTextUpdateCarName;
    @BindView(R.id.editText_updateCar_factory)
    EditText editTextUpdateCarFactory;
    @BindView(R.id.editText_updateCar_year)
    EditText editTextUpdateCarYear;
    @BindView(R.id.editText_updateCar_kilometer)
    EditText editTextUpdateCarKilometer;
    @BindView(R.id.editText_updateCar_color)
    EditText editTextUpdateCarColor;
    @BindView(R.id.checkbox_updateCar_automate)
    CheckBox checkboxUpdateCarAutomate;
    @BindView(R.id.editText_updateCar_price)
    EditText editTextUpdateCarPrice;
    @BindView(R.id.button_updateCar)
    Button button_update_car;



    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_admin);
        ButterKnife.bind(this);
        Intent i = getIntent();
        car = (Car) i.getSerializableExtra("Car");
        setVariables(car);
    }

    private void setVariables(Car car) {
        editTextUpdateCarName.setText(car.getName());
        editTextUpdateCarFactory.setText(car.getFactory());
        editTextUpdateCarYear.setText(car.getYear());
        editTextUpdateCarKilometer.setText(car.getKilometer());
        editTextUpdateCarColor.setText(car.getColor());
        checkboxUpdateCarAutomate.setChecked(car.isAutomate());
        editTextUpdateCarPrice.setText(car.getPrice());


    }

    @OnClick(R.id.button_updateCar)
    public void onViewClicked() {
        CarBuilder builder = new CarBuilder()
                .setName(editTextUpdateCarName.getText().toString())
                .setColor(editTextUpdateCarColor.getText().toString())
                .setFactory( editTextUpdateCarFactory.getText().toString())
                .setKilometer(Integer.parseInt(editTextUpdateCarKilometer.getText().toString()))
                .setPrice(Integer.parseInt(editTextUpdateCarPrice.getText().toString()))
                .setYear(Integer.parseInt(editTextUpdateCarYear.getText().toString()))
                .setAutomate(checkboxUpdateCarAutomate.isChecked());
        updateCar(builder.createCar());
    }

    private void updateCar(Car car) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call call = service.updateCar(car,car.getId());
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        if (response.isSuccessful()) {
            Log.i("Connection", "Car Updated Successfully with id " + car.getId());
            startActivity(new Intent(UpdateCarAdminActivity.this,ListCarsAdminActivity.class));
            finish();
        } else {
            Log.e("Connection", "Failed To Updated Car : " + response.message());
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        Log.e("Connection", "Failed To Connect : " + t.getMessage());


    }
}
