package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.model.UserHolder;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.viewmodel.CarViewModel;
import com.myapps.tc_android.viewmodel.GetReceiptViewModel;
import com.myapps.tc_android.viewmodel.RentViewModel;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.res_text)
    TextView resText;
    @BindView(R.id.car_name_rent)
    TextView carNameRent;
    @BindView(R.id.get_car_name)
    TextView getCarName;
    @BindView(R.id.username_rent)
    TextView usernameRent;
    @BindView(R.id.get_user_name)
    TextView getUserName;
    @BindView(R.id.first_line)
    TextView firstLine;
    @BindView(R.id.starting_date_rent_text)
    TextView startingDateRentText;
    @BindView(R.id.get_starting_date)
    TextView getStartingDate;
    @BindView(R.id.end_date_rent_text)
    TextView endDateRentText;
    @BindView(R.id.get_end_date)
    TextView getEndDate;
    @BindView(R.id.second_line)
    TextView secondLine;
    @BindView(R.id.src_location_text)
    TextView srcLocationText;
    @BindView(R.id.get_src_location)
    TextView getSrcLocation;
    @BindView(R.id.des_location_text)
    TextView desLocationText;
    @BindView(R.id.get_des_location)
    TextView getDesLocation;
    @BindView(R.id.third_line)
    TextView thirdLine;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.get_cost)
    TextView getCost;
    @BindView(R.id.kilometer)
    TextView kilometer;
    @BindView(R.id.get_kilometer)
    TextView getKilometer;
    @BindView(R.id.res_card)
    ConstraintLayout resCard;
    @BindView(R.id.swipeLayout_rent)
    SwipeRefreshLayout swipeRefreshLayout;
    private GetReceiptViewModel viewModel;
    private RentViewModel viewModel2;
    private List<String> locations;
    private RentCar rent;
    private CarViewModel viewModel3;
    String carName;

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
        viewModel = ViewModelProviders.of(this).get(GetReceiptViewModel.class);
        viewModel2 = ViewModelProviders.of(this).get(RentViewModel.class);
        viewModel3 = ViewModelProviders.of(this).get(CarViewModel.class);
        observeViewModelRentViewModel(viewModel);
        observelocationResponse();
        obserViewModel();
        viewModel.getRentUser();
    }

    private void obserViewModel() {
        viewModel3.getCarObservableData().observe(this, new Observer<Car>() {
            @Override
            public void onChanged(@Nullable Car car) {
                if (car != null) {
                    carName = car.getName();
                    setVariables();
                }
            }
        });
    }

    private void observelocationResponse() {
        viewModel2.getLocationsObservableData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> location) {
                if (location != null) {
                    locations = location;
                    viewModel3.getCar(rent.getCarId());
                }
            }
        });
    }

    private void observeViewModelRentViewModel(GetReceiptViewModel viewModel) {
        viewModel.getRentObservableData().observe(this, new Observer<RentCar>() {
            @Override
            public void onChanged(@Nullable RentCar rentCar) {
                if (rentCar != null) {
                    rent = rentCar;
                    viewModel2.getLocations();
                } else {
                    resCard.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });
    }

    private void setVariables() {
        getCarName.setText(carName);
        resCard.setVisibility(View.VISIBLE);
        getUserName.setText(UserHolder.Instance().getUser().getUsername());
        getStartingDate.setText(rent.getStartDate().toString());
        getEndDate.setText(rent.getEndDate().toString());
        getSrcLocation.setText(locations.get(rent.getSrcLocation()));
        getDesLocation.setText(locations.get(rent.getDesLocation()));
        getCost.setText(String.valueOf(rent.getCost()));
        getKilometer.setText(String.valueOf(rent.getKilometer()));
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
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
        Log.e("rent", "hello");
    }


}
