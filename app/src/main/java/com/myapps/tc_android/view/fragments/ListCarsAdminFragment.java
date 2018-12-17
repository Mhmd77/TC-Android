package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myapps.tc_android.R;

import com.myapps.tc_android.view.adapter.CarsRecyclerView;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.view.activities.CarProfileActivity;
import com.myapps.tc_android.viewmodel.ListCarsViewModel;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCarsAdminFragment extends Fragment implements CarsRecyclerView.UserOnItemClickListener {
    @BindView(R.id.recyclerView_main_cars)
    RecyclerView recyclerViewMainCars;

//    @BindView(R.id.actionButton_main_add_car)
    // private FloatingActionButton actionButtonMainAddCar = (FloatingActionButton) getActivity().findViewById(R.id.actionButton_main_add_car);

    private CarsRecyclerView adapter;
    private ApiService service;

    private Unbinder unbinder;
    private ListCarsViewModel viewModel;

    public ListCarsAdminFragment() {
    }

    public static ListCarsAdminFragment newInstance() {
        ListCarsAdminFragment fragment = new ListCarsAdminFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListCarsViewModel.class);
        observeViewModel(viewModel);
    }

    private void observeViewModel(ListCarsViewModel viewModel) {
        viewModel.getListCarsObservableData().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                generateDataList(cars);
            }
        });
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

        return view;
    }

    private void generateDataList(final List<Car> cars) {
        adapter = new CarsRecyclerView(getActivity(), cars, this);
        recyclerViewMainCars.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMainCars.setAdapter(adapter);
    }

    @Override
    public void cardOnClick(View view, int position) {
        int carId = adapter.getList().get(position).getId();
        Intent intent = new Intent(getActivity(), CarProfileActivity.class);
        intent.putExtra("carId", carId);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
