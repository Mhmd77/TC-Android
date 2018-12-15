package com.myapps.tc_android.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.CarBuilder;
import com.myapps.tc_android.controller.Utils;
import com.myapps.tc_android.controller.adapter.CarsRecyclerView;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.Car;
import com.myapps.tc_android.model.CarView;
import com.myapps.tc_android.view.activities.CarProfileActivity;
import com.myapps.tc_android.view.activities.HomePageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements Callback<ApiResponse<List<Car>>>, CarsRecyclerView.UserOnItemClickListener, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.placeHolder_main_cars)
    PlaceHolderView placeHolderMainCars;
    @BindView(R.id.radioButtonSortAscending)
    RadioButton radioButtonSortAscending;
    @BindView(R.id.radioButtonSortDescending)
    RadioButton radioButtonSortDescending;
    @BindView(R.id.spinnerLoading)
    SpinKitView spinnerLoading;
    @BindView(R.id.swipeLayout_main_cars)
    SwipeRefreshLayout swipeLayoutMainCars;
    private ApiService service;
    @BindView(R.id.sortbar)
    View sortBar;
    private String field;
    private int ascending = 1;
    private Unbinder unbinder;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        placeHolderMainCars.getBuilder().setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeLayoutMainCars.setOnRefreshListener(this);
        updateList();
        initSortBar();
        initSpinner();
        return view;
    }

    private void initSpinner() {
        Sprite doubleBounce = new DoubleBounce();
        spinnerLoading.setIndeterminateDrawable(doubleBounce);
    }

    private void initSortBar() {
        ((HomePageActivity) getActivity()).buttonSortCost.setOnClickListener(this);
        ((HomePageActivity) getActivity()).buttonSortYear.setOnClickListener(this);
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
        placeHolderMainCars.removeAllViews();
        for (Car c :
                cars) {
            placeHolderMainCars.addView(new CarView(placeHolderMainCars, getActivity(), c));
        }
        placeHolderMainCars.refresh();
        swipeLayoutMainCars.setRefreshing(false);
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
        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cardOnClick(View view, int position) {
        Car car = (Car) placeHolderMainCars.getViewResolverAtPosition(position);
        Intent intent = new Intent(getActivity(), CarProfileActivity.class);
        intent.putExtra("Car", car);
        startActivity(intent);
    }

    @OnClick(R.id.buttonSortCars)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
//                                placeHolderMainCars.removeAllViews();
                                generateDataList(response.body().getObject());
//                                placeHolderMainCars.refresh();
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
                        Toast.makeText(getActivity(), "Something went wrong. please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        updateList();
    }
}
