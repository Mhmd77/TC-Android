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
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.Car;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCarAdminActivity extends AppCompatActivity implements Callback<Car> {

    @BindView(R.id.editText_addCar_name)
    EditText editTextAddCarName;
    @BindView(R.id.editText_addCar_factory)
    EditText editTextAddCarFactory;
    @BindView(R.id.editText_addCar_year)
    EditText editTextAddCarYear;
    @BindView(R.id.editText_addCar_kilometer)
    EditText editTextAddCarKilometer;
    @BindView(R.id.editText_addCar_color)
    EditText editTextAddCarColor;
    @BindView(R.id.checkbox_addCar_automate)
    CheckBox editTextAddCarAutomate;
    @BindView(R.id.editText_addCar_price)
    EditText editTextAddCarPrice;
    @BindView(R.id.button_addCar)
    Button buttonAddCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_admin);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_addCar)
    public void onViewClicked() {
        if (validate()) {
            CarBuilder builder = new CarBuilder()
                    .setName(editTextAddCarName.getText().toString())
                    .setColor(editTextAddCarColor.getText().toString())
                    .setFactory(editTextAddCarFactory.getText().toString())
                    .setKilometer(Integer.parseInt(editTextAddCarKilometer.getText().toString()))
                    .setPrice(Integer.parseInt(editTextAddCarPrice.getText().toString()))
                    .setYear(Integer.parseInt(editTextAddCarYear.getText().toString()))
                    .setAutomate(editTextAddCarAutomate.isChecked());
            addCar(builder.createCar());
        }
    }

    private boolean validate() {
        boolean valid = true;
        String name = editTextAddCarName.getText().toString();
        String color = editTextAddCarColor.getText().toString();
        String factory = editTextAddCarFactory.getText().toString();
        String kilometer =editTextAddCarKilometer.getText().toString();
        String price = editTextAddCarPrice.getText().toString();
        String year = editTextAddCarYear.getText().toString();


        if (name.isEmpty() || name.length() > 15) {
            editTextAddCarName.setError("enter a valid name");
            requestFocus(editTextAddCarName);
            valid = false;
        } else {
            editTextAddCarName.setError(null);
        }


        if (color.isEmpty() || color.length() > 9) {
            editTextAddCarColor.setError("enter a valid color");
            requestFocus(editTextAddCarColor);
            valid = false;
        } else {
            editTextAddCarColor.setError(null);
        }


        if (factory.isEmpty() || factory.length() > 9) {
            editTextAddCarFactory.setError("enter a valid factory");
            requestFocus(editTextAddCarFactory);
            valid = false;
        } else {
            editTextAddCarFactory.setError(null);
        }


        if (kilometer.isEmpty() || kilometer.length() > 9) {
            editTextAddCarKilometer.setError("enter a valid kilometer");
            requestFocus(editTextAddCarKilometer);
            valid = false;
        } else {
            editTextAddCarKilometer.setError(null);
        }

        if (price.isEmpty() || price.length() > 9) {
            editTextAddCarPrice.setError("enter a valid price");
            requestFocus(editTextAddCarPrice);
            valid = false;
        } else {
            editTextAddCarPrice.setError(null);
        }

        if (year.isEmpty() || year.length() > 9) {
            editTextAddCarYear.setError("enter a valid year");
            requestFocus(editTextAddCarYear);
            valid = false;
        } else {
            editTextAddCarYear.setError(null);
        }

        return valid;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void addCar(Car car) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Car> call = service.addCar(car);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<Car> call, @NonNull Response<Car> response) {
        if (response.isSuccessful()) {
            Toast.makeText(AddCarAdminActivity.this, "Car Added Successfully ", Toast.LENGTH_SHORT).show();
            Log.i("Connection", "Car Added Successfully with id " + response.body().getId());
            finish();
            startActivity(new Intent(AddCarAdminActivity.this, ListCarsAdminActivity.class));
        } else {
            Log.e("Connection", "Failed To Add Car : " + response.message());
        }
    }

    @Override
    public void onFailure(@NonNull Call<Car> call, @NonNull Throwable t) {
        Log.e("Connection", "Failed To Connect : " + t.getMessage());

    }
}
