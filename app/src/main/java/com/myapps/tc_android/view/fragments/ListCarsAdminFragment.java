package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.view.activities.CarProfileActivity;
import com.myapps.tc_android.view.adapter.CarsRecyclerView;
import com.myapps.tc_android.viewmodel.ListCarsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ListCarsAdminFragment extends Fragment implements CarsRecyclerView.UserOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView_main_cars)
    RecyclerView recyclerViewMainCars;
    @BindView(R.id.floatingActionButton_returnTop)
    FloatingActionButton floatingActionButtonReturnTop;
    @BindView(R.id.swipeLayout_main_cars)
    SwipeRefreshLayout swipeLayoutMainCars;

    private CarsRecyclerView adapter;

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
        observeViewModel();
        viewModel.getCars();
    }

    private void observeViewModel() {
        viewModel.getListCarsObservableData().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                if (cars != null)
                    generateDataList(cars);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_cars_admin, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerViewMainCars.setLayoutManager(manager);
        swipeLayoutMainCars.setOnRefreshListener(this);
        recyclerViewMainCars.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                if (lastItemPosition > 1) {
                    floatingActionButtonReturnTop.show();
                } else {
                    floatingActionButtonReturnTop.hide();
                }
            }
        });
    }

    private void generateDataList(final List<Car> cars) {
        adapter = new CarsRecyclerView(getActivity(), cars, this);
        recyclerViewMainCars.setAdapter(adapter);
        swipeLayoutMainCars.setRefreshing(false);
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

    @OnClick(R.id.floatingActionButton_returnTop)
    public void onViewClicked() {
        recyclerViewMainCars.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        viewModel.getCars();
    }
}
