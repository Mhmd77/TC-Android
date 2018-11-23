package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.Utils;
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

public class ListCarsActivity extends AppCompatActivity implements Callback<ApiResponse<List<Car>>> {

    @BindView(R.id.recyclerView_main_cars)
    RecyclerView recyclerViewMainCars;
    @BindView(R.id.radioButtonSortAscending)
    RadioButton radioButtonSortAscending;
    @BindView(R.id.radioButtonSortDescending)
    RadioButton radioButtonSortDescending;
    @BindView(R.id.buttonSortYear)
    Button buttonSortYear;
    @BindView(R.id.buttonSortCost)
    Button buttonSortCost;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.actionButton_main_add_car)
    FloatingActionButton actionButtonMainAddCar;
    RadioGroup radioGroupSortItems;
    private CarsRecyclerView adapter;
    private ApiService service;
    @BindView(R.id.sortbar)
    View sortBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);
        ButterKnife.bind(this);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        updateList();
        initSortBar();
    }

    private void initSortBar() {
        radioGroupSortItems = findViewById(R.id.radioGroupSortItems);
        Utils.collapse(sortBar);
    }

    private void updateList() {
        Call<ApiResponse<List<Car>>> call = service.getAllCars();
        call.enqueue(this);
    }

    private void generateDataList(final List<Car> cars) {
        adapter = new CarsRecyclerView(this, cars);
        recyclerViewMainCars.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMainCars.setAdapter(adapter);
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
        Toast.makeText(ListCarsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    @OnClick({R.id.buttonSortYear, R.id.buttonSortCost, R.id.buttonSortCars})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonSortYear:
                Utils.expand(sortBar);
                break;
            case R.id.buttonSortCost:
                Utils.expand(sortBar);
                break;
            case R.id.buttonSortCars:
                Utils.collapse(sortBar);
                break;
        }
    }


}
