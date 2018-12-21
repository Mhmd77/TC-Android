package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.view.adapter.CarViewAdapter;
import com.myapps.tc_android.utils.AnimationUtils;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.view.activities.CarProfileActivity;
import com.myapps.tc_android.view.activities.HomePageActivity;
import com.myapps.tc_android.view.adapter.CarsRecyclerView;
import com.myapps.tc_android.viewmodel.ListCarsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements CarsRecyclerView.UserOnItemClickListener, View.OnClickListener {
    @BindView(R.id.placeHolder_main_cars)
    PlaceHolderView placeHolderMainCars;
    @BindView(R.id.radioButtonSortAscending)
    RadioButton radioButtonSortAscending;
    @BindView(R.id.radioButtonSortDescending)
    RadioButton radioButtonSortDescending;
    @BindView(R.id.spinnerLoading)
    SpinKitView spinnerLoading;
    @BindView(R.id.sortbar)
    View sortBar;
    private String field;
    private int ascending = 1;
    private Unbinder unbinder;
    private ListCarsViewModel viewModel;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListCarsViewModel.Factory factory = new ListCarsViewModel.Factory(false);
        viewModel = ViewModelProviders.of(this, factory).get(ListCarsViewModel.class);
        observeViewModel(viewModel);
    }

    private void observeViewModel(ListCarsViewModel viewModel) {
        viewModel.getListCarsObservableData().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                if (cars != null) {
                    generateDataList(cars);
                }
                spinnerLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        placeHolderMainCars.getBuilder().setLayoutManager(new LinearLayoutManager(getActivity()));
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
        AnimationUtils.collapse(sortBar);
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

    private void generateDataList(final List<Car> cars) {
        placeHolderMainCars.removeAllViews();
        for (Car c :
                cars) {
            placeHolderMainCars.addView(new CarViewAdapter(placeHolderMainCars, getActivity(), c));
        }
        placeHolderMainCars.refresh();
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
                AnimationUtils.expand(sortBar);
                field = "year";
                break;
            case R.id.buttonSortCost:
                AnimationUtils.expand(sortBar);
                field = "price";
                break;
            case R.id.buttonSortCars:
                spinnerLoading.setVisibility(View.VISIBLE);
                viewModel.sortListOfCars(field, ascending);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
