package com.myapps.tc_android.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myapps.tc_android.R;

import com.myapps.tc_android.controller.adapter.CarsRecyclerView;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.Car;
import com.myapps.tc_android.view.activities.CarProfileActivity;



import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ListCarsAdminFragment extends Fragment implements Callback<ApiResponse<List<Car>>>, CarsRecyclerView.UserOnItemClickListener {
    @BindView(R.id.recyclerView_main_cars)
    RecyclerView recyclerViewMainCars;

//    @BindView(R.id.actionButton_main_add_car)
   // private FloatingActionButton actionButtonMainAddCar = (FloatingActionButton) getActivity().findViewById(R.id.actionButton_main_add_car);

    private CarsRecyclerView adapter;
    private ApiService service;

    private Unbinder unbinder;

    public ListCarsAdminFragment() {
    }

    public static ListCarsAdminFragment newInstance() {
        ListCarsAdminFragment fragment = new ListCarsAdminFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_cars_admin, container, false);
        unbinder = ButterKnife.bind(this, view);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        updateList();

        return view;
    }

    private void updateList() {
        Call<ApiResponse<List<Car>>> call = service.getAllCars();
        call.enqueue(this);
    }

    private void generateDataList(final List<Car> cars) {
        adapter = new CarsRecyclerView(getActivity(), cars, this);
        recyclerViewMainCars.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
    }


//    @OnClick(R.id.actionButton_main_add_car)
//    public void onViewClicked() {
//        startActivity(new Intent(getActivity(), AddCarAdminActivity.class));
//    }

    @Override
    public void cardOnClick(View view, int position) {
        Car car = adapter.getList().get(position);
        Intent intent = new Intent(getActivity(), CarProfileActivity.class);
        intent.putExtra("Car", car);
        startActivity(intent);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
