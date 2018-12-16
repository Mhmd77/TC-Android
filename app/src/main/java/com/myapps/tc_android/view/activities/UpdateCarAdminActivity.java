package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.CarBuilder;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCarAdminActivity extends AppCompatActivity implements Callback<ApiResponse<Car>> {

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


    Car carOld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_admin);
        ButterKnife.bind(this);
        Intent i = getIntent();
        carOld = (Car) i.getSerializableExtra("Car");
        setVariables(carOld);
    }

    private void setVariables(Car car) {
        editTextUpdateCarName.setText(car.getName());
        editTextUpdateCarFactory.setText(car.getFactory());
        editTextUpdateCarYear.setText(String.valueOf(car.getYear()));
        editTextUpdateCarKilometer.setText(String.valueOf(car.getKilometer()));
        editTextUpdateCarColor.setText(car.getColor());
        checkboxUpdateCarAutomate.setChecked(car.isAutomate());
        editTextUpdateCarPrice.setText(String.valueOf(car.getPrice()));
    }

    @OnClick(R.id.button_updateCar)
    public void onViewClicked() {
        if (validate()) {
            CarBuilder builder = new CarBuilder()
                    .setName(editTextUpdateCarName.getText().toString())
                    .setColor(editTextUpdateCarColor.getText().toString())
                    .setFactory(editTextUpdateCarFactory.getText().toString())
                    .setKilometer(Integer.parseInt(editTextUpdateCarKilometer.getText().toString()))
                    .setPrice(Integer.parseInt(editTextUpdateCarPrice.getText().toString()))
                    .setYear(Integer.parseInt(editTextUpdateCarYear.getText().toString()))
                    .setAutomate(checkboxUpdateCarAutomate.isChecked());
            updateCar(builder.createCar());
        }
    }

    private boolean validate() {
        boolean valid = true;
        String name = editTextUpdateCarName.getText().toString();
        String color = editTextUpdateCarColor.getText().toString();
        String factory = editTextUpdateCarFactory.getText().toString();
        String kilometer = editTextUpdateCarKilometer.getText().toString();
        String price = editTextUpdateCarPrice.getText().toString();
        String year = editTextUpdateCarYear.getText().toString();
        if (name.isEmpty() || name.length() > 20) {
            editTextUpdateCarName.setError("enter a valid name");
            requestFocus(editTextUpdateCarName);
            valid = false;
        } else {
            editTextUpdateCarName.setError(null);
        }
        if (color.isEmpty() || color.length() > 20) {
            editTextUpdateCarColor.setError("enter a valid color");
            requestFocus(editTextUpdateCarColor);
            valid = false;
        } else {
            editTextUpdateCarColor.setError(null);
        }
        if (factory.isEmpty() || factory.length() > 20) {
            editTextUpdateCarFactory.setError("enter a valid factory");
            requestFocus(editTextUpdateCarFactory);
            valid = false;
        } else {
            editTextUpdateCarFactory.setError(null);
        }
        if (kilometer.isEmpty() || kilometer.length() > 6) {
            editTextUpdateCarKilometer.setError("enter a valid kilometer");
            requestFocus(editTextUpdateCarKilometer);
            valid = false;
        } else {
            editTextUpdateCarKilometer.setError(null);
        }
        if (price.isEmpty() || price.length() > 9) {
            editTextUpdateCarPrice.setError("enter a valid price");
            requestFocus(editTextUpdateCarPrice);
            valid = false;
        } else {
            editTextUpdateCarPrice.setError(null);
        }
        if (year.isEmpty() || year.length() > 4) {
            editTextUpdateCarPrice.setError("enter a valid year");
            requestFocus(editTextUpdateCarPrice);
            valid = false;
        } else {
            editTextUpdateCarPrice.setError(null);
        }
        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void updateCar(Car car) {
        ApiService service = ApiRepository.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<Car>> call = service.updateCar(car, this.carOld.getId());
        call.enqueue(this);
    }


    @Override
    public void onResponse(@NonNull Call<ApiResponse<Car>> call, @NonNull Response<ApiResponse<Car>> response) {
        if (response.isSuccessful()) {
            if (response.body().getStatus().equals("OK")) {
                Toast.makeText(UpdateCarAdminActivity.this, "Car Updated Successfully ", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(UpdateCarAdminActivity.this, HomePageAdminActivity.class));

//                Intent intent = new Intent(UpdateCarAdminActivity.this, ListCarsAdminActivity.class);
//                startActivity(intent);
            } else {
                Log.e("Update Car Error", " status : " + response.body().getStatus());
            }
        } else {
            Log.e("Connection", "Failed To Add Car : " + response.message());
        }
    }

    @Override
    public void onFailure(@NonNull Call<ApiResponse<Car>> call, @NonNull Throwable t) {
        Log.e("Connection", "Failed To Connect : " + t.getMessage());

    }
}
