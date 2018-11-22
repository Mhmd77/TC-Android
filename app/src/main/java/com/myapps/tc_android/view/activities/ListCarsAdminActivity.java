package com.myapps.tc_android.view.activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.adapter.CarsRecyclerView;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCarsAdminActivity extends AppCompatActivity implements Callback<ApiResponse<List<Car>>>  {

    @BindView(R.id.recyclerView_main_cars)
    RecyclerView recyclerViewMainCars;
    @BindView(R.id.actionButton_main_add_car)
    FloatingActionButton actionButtonMainAddCar;
    private CarsRecyclerView adapter;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);
        ButterKnife.bind(this);

        service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        updateList();
    }

    private void updateList() {
        Call<ApiResponse<List<Car>>> call = service.getAllCars();
        call.enqueue(this);
    }

    private void generateDataList(final List<Car> cars) {
        adapter = new CarsRecyclerView(this, cars, new CarsRecyclerView.OnItemClickListener() {


            @Override
            public void deleteOnClick(View view , final int position) {
                Call<ApiResponse<Object>> call = service.deleteCar(cars.get(position).getId());
                call.enqueue(new Callback<ApiResponse<Object>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<Object>> call, @NonNull Response<ApiResponse<Object>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ListCarsAdminActivity.this, "Car : " + cars.get(position).getName() + " deleted", Toast.LENGTH_SHORT).show();
                            Log.i("Connection", "Car " + cars.get(position).getId() + " deleted");
                            updateList();
                        } else {
                            Log.e("Connection", "Deleting Car Failed : " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<Object>> call, @NonNull Throwable t) {
                        Log.e("Connection", "Deleting Car Failed : " + t.getMessage());
                    }
                });

            }

            @Override
            public void updateOnClick(View view,int position) {
                Car car =  cars.get(position);
                Intent intent = new Intent(ListCarsAdminActivity.this,UpdateCarAdminActivity.class);
                intent.putExtra("Car",car);
                startActivity(intent);

            }


        });
        recyclerViewMainCars.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMainCars.setAdapter(adapter);
    }

    @OnClick(R.id.actionButton_main_add_car)
    public void onViewClicked() {
        Intent intent = new Intent(ListCarsAdminActivity.this, AddCarAdminActivity.class);
        startActivity(intent);

    }

    @Override
    public void onResponse(@NonNull Call<ApiResponse<List<Car>>> call, @NonNull Response<ApiResponse<List<Car>>> response) {
        if (response.body() != null) {
            generateDataList(response.body().getObject());
        } else {
            Log.e("CarList", "response body is null");
        }
    }

    @Override
    public void onFailure(@NonNull Call<ApiResponse<List<Car>>> call, @NonNull Throwable t) {
        Toast.makeText(ListCarsAdminActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }


}
