package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.CarView;
import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.service.model.UserHolder;
import com.myapps.tc_android.view.activities.AddCarUserActivity;
import com.myapps.tc_android.view.activities.UsersCarActivity;
import com.myapps.tc_android.viewmodel.ListCarsViewModel;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    @BindView(R.id.textview_name)
    TextView textviewName;
    @BindView(R.id.textview_username)
    TextView textviewUsername;
    @BindView(R.id.textview_username_title)
    TextView textviewUsernameTitle;
    @BindView(R.id.textview_age)
    TextView textviewAge;
    @BindView(R.id.textview_age_title)
    TextView textviewAgeTitle;
    @BindView(R.id.textview_email)
    TextView textviewEmail;
    @BindView(R.id.textview_email_title)
    TextView textviewEmailTitle;
    @BindView(R.id.recyclerView_profile_cars)
    PlaceHolderView recyclerViewProfileCars;
    Unbinder unbinder;
    private List<Car> cars;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        ListCarsViewModel.Factory factory = new ListCarsViewModel.Factory(true);
        final ListCarsViewModel viewModel = ViewModelProviders.of(this, factory).get(ListCarsViewModel.class);
        observeViewModel(viewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerViewProfileCars.getBuilder()
                .setItemViewCacheSize(2);
        fillUserInfo();
        return view;
    }

    private void observeViewModel(ListCarsViewModel viewModel) {
        viewModel.getListCarsObservableData().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> result) {
                if (result != null) {
                    cars = result;
                    for (int i = 0; i < 2 && i < result.size(); i++) {
                        Car c = result.get(i);
                        recyclerViewProfileCars.addView(new CarView(recyclerViewProfileCars, getActivity(), c));
                    }
                }
            }
        });
    }

    private void fillUserInfo() {
        User user = UserHolder.Instance().getUser();
        if (user != null) {
            textviewAge.setText(String.valueOf(user.getAge()));
            textviewName.setText(user.getLastName() + ", " + user.getName());
            textviewEmail.setText(user.getEmail());
            textviewUsername.setText(user.getUsername());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.button_profile_add_car, R.id.button_all_user_cars})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_profile_add_car:
                startActivity(new Intent(getActivity(), AddCarUserActivity.class));
                break;
            case R.id.button_all_user_cars:
                Intent intent = new Intent(getActivity(), UsersCarActivity.class);
                intent.putExtra(UsersCarActivity.USERS_LIST_OF_CARS, (Serializable) cars);
                startActivity(intent);
                break;
        }
    }
}
