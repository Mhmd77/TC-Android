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

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
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
    @BindView(R.id.spinnerLoading)
    SpinKitView spinnerLoading;
    private CarsRecyclerView adapter;
    private ApiService service;
    @BindView(R.id.sortbar)
    View sortBar;
    private String field;
    private int ascending = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);
        ButterKnife.bind(this);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        updateList();
        initSortBar();
        initSpinner();
    }

    private void initSpinner() {
        Sprite doubleBounce = new DoubleBounce();
        spinnerLoading.setIndeterminateDrawable(doubleBounce);
    }

    private void initSortBar() {
        Utils.collapse(sortBar);
        ((RadioGroup) sortBar).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonSortDescending:
                        ascending = 0;
                        break;
                    case R.id.radioButtonSortAscending:
                        ascending = 1;
                        break;
                }
            }
        });
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
                field = "year";
                break;
            case R.id.buttonSortCost:
                Utils.expand(sortBar);
                field = "price";
                break;
            case R.id.buttonSortCars:
                spinnerLoading.setVisibility(View.VISIBLE);
                Call<ApiResponse<List<Car>>> call = service.sortCars(field, ascending);
                call.enqueue(new Callback<ApiResponse<List<Car>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<Car>>> call, Response<ApiResponse<List<Car>>> response) {
                        spinnerLoading.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals("OK")) {
                                adapter.setList(response.body().getObject());
                                Utils.collapse(sortBar);
                            } else {
                                Log.e("Sort Error", " status : " + response.body().getStatus());
                            }
                        } else {
                            Log.e("Sort Error", " error code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<Car>>> call, Throwable t) {
                        Toast.makeText(ListCarsActivity.this, "Something went wrong. please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


}
