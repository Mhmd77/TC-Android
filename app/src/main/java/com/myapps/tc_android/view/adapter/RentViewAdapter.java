package com.myapps.tc_android.view.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.RentCar;
import com.myapps.tc_android.service.model.UserHolder;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.view.fragments.RentFragment;
import com.myapps.tc_android.view.fragments.RentFragment_ViewBinding;
import com.myapps.tc_android.viewmodel.CarViewModel;

import butterknife.BindView;
import butterknife.OnClick;

@NonReusable
@Layout(R.layout.item_layout_rent)
public class RentViewAdapter {
    @BindView(R.id.car_name_rent)
    TextView carNameRent;
    @BindView(R.id.get_car_name)
    TextView getCarName;
    @BindView(R.id.username_rent)
    TextView usernameRent;
    @BindView(R.id.get_username)
    TextView getUsername;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.get_cost)
    TextView getCost;
    @BindView(R.id.kilometer)
    TextView kilometer;
    @BindView(R.id.get_kilometer)
    TextView getKilometer;
    @BindView(R.id.rent_layout)
    ConstraintLayout rentLayout;
    @BindView(R.id.viewFlipper_Rent)
    ViewFlipper viewFlipperRent;
    @BindView(R.id.rent_card)
    CardView rentCard;
    private RentCar rentCar;
    private Context context;
    private PlaceHolderView placeholder;
    private int carId;
    private CarViewModel viewModel;
    private Car mycar;


    public RentViewAdapter(PlaceHolderView placeholder, Context context, RentCar rentCar) {
        this.rentCar = rentCar;
        this.context = context;
        this.placeholder = placeholder;
        carId = rentCar.getCarId();
        viewModel = ViewModelProviders.of((FragmentActivity)context).get(CarViewModel.class);
        observeViewModel();
        viewModel.getCar(carId);

    }

    private void observeViewModel() {
        viewModel.getCarObservableData().observe((LifecycleOwner) this, new Observer<Car>() {
            @Override
            public void onChanged(@Nullable Car car) {
                if(car != null)
                    mycar = car;
            }
        });
    }

    @Resolve
    private void onResolved() {
        getUsername.setText(UserHolder.Instance().getUser().getUsername());
        getCarName.setText(mycar.getName());
        getCost.setText(rentCar.getCost());
        getKilometer.setText(rentCar.getKilometer());

    }

    @OnClick(R.id.rent_card)
    public void onViewClicked() {
    }
}
