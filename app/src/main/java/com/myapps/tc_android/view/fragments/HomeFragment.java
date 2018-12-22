package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.view.adapter.CarViewAdapter;
import com.myapps.tc_android.viewmodel.ListCarsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
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
        viewModel = ViewModelProviders.of(this).get(ListCarsViewModel.class);
        observeViewModel(viewModel);
        viewModel.getCars();
    }

    private void observeViewModel(ListCarsViewModel viewModel) {
        viewModel.getListCarsObservableData().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                if (cars != null) {
                    generateDataList(cars);
                    hideSortDetails();
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
        swipeLayoutMainCars.setOnRefreshListener(this);
        initSortBar();
        initSpinner();
        return view;
    }

    private void initSpinner() {
        Sprite doubleBounce = new DoubleBounce();
        spinnerLoading.setIndeterminateDrawable(doubleBounce);
    }

    private void initSortBar() {
        ((RadioGroup) sortBar.findViewById(R.id.radioGroupSortBar)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        swipeLayoutMainCars.setRefreshing(false);
    }

    @OnClick({R.id.buttonSortCars, R.id.buttonSortYear, R.id.buttonSortCost})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSortYear:
                showSortDetails();
                field = "year";
                break;
            case R.id.buttonSortCost:
                showSortDetails();
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

    @Override
    public void onRefresh() {
        viewModel.getCars();
    }

    private void showSortDetails() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(getActivity(), R.layout.layout_sort_details);
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateInterpolator(1.0f));
        transition.setDuration(600);
        TransitionManager.beginDelayedTransition((ConstraintLayout) sortBar, transition);
        constraintSet.applyTo((ConstraintLayout) sortBar);
    }

    private void hideSortDetails() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(getActivity(), R.layout.layout_sort);
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateInterpolator(1.0f));
        transition.setDuration(600);
        TransitionManager.beginDelayedTransition((ConstraintLayout) sortBar, transition);
        constraintSet.applyTo((ConstraintLayout) sortBar);
    }
}
