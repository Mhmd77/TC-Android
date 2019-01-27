package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.view.adapter.CarViewAdapter;
import com.myapps.tc_android.view.adapter.RentViewAdapter;
import com.myapps.tc_android.viewmodel.ListRentViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.placeHolder_main_rents)
    PlaceHolderView placeHolderMainRents;
    @BindView(R.id.swipeLayout_main_rents)
    SwipeRefreshLayout swipeLayoutMainRents;
    @BindView(R.id.spinnerLoading)
    SpinKitView spinnerLoading;
    @BindView(R.id.floatingActionButton_returnTop)
    FloatingActionButton floatingActionButtonReturnTop;
    Unbinder unbinder;
    private ListRentViewModel viewModel;

    public RentFragment() {
    }

    public static RentFragment newInstance() {
        RentFragment fragment = new RentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListRentViewModel.class);
        observeViewModel(viewModel);
        viewModel.getRentUser();
    }

    private void observeViewModel(ListRentViewModel viewModel) {
        viewModel.getRentObservableData().observe(this, new Observer<List<RentCar>>() {
            @Override
            public void onChanged(@Nullable List<RentCar> rentCars) {
                Log.e("hi",  "hi ");
                if (rentCars != null ) {
                    Log.e("hi", "s ");
                    generateDataList(rentCars);
                }
            }
        });
    }

    private void generateDataList(List<RentCar> rentCars) {
        placeHolderMainRents.removeAllViews();
        for (RentCar c :
                rentCars) {
            placeHolderMainRents.addView(new RentViewAdapter(placeHolderMainRents, getActivity(), c));
        }
        placeHolderMainRents.refresh();
        swipeLayoutMainRents.setRefreshing(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        viewModel.getRentUser();
    }
    @OnClick(R.id.floatingActionButton_returnTop)
    public void onViewClicked() {
        placeHolderMainRents.smoothScrollToPosition(0);
    }
}
