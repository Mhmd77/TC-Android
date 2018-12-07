package com.myapps.tc_android.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.Car;
import com.myapps.tc_android.model.CarView;
import com.myapps.tc_android.model.User;
import com.myapps.tc_android.model.UserHolder;
import com.myapps.tc_android.view.activities.AddCarUserActivity;
import com.myapps.tc_android.view.activities.UsersCarActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements Callback<ApiResponse<List<Car>>> {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerViewProfileCars.getBuilder()
                .setItemViewCacheSize(2);
        Call<ApiResponse<List<Car>>> call = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class).getUserCar();
        call.enqueue(this);
        fillUserInfo();
        return view;
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

    @Override
    public void onResponse(Call<ApiResponse<List<Car>>> call, Response<ApiResponse<List<Car>>> response) {
        if (response.isSuccessful()) {
            if (response.body().getStatus().equals("OK")) {
                cars = response.body().getObject();
                for (int i = 0; i < 2; i++) {
                    Car c = response.body().getObject().get(i);
                    recyclerViewProfileCars.addView(new CarView(recyclerViewProfileCars, getActivity(), c));
                }
            } else {
                Log.e("Error", response.body().getStatus());
            }
        } else {
            Log.e("Error", response.code() + "");

        }
    }

    @Override
    public void onFailure(Call<ApiResponse<List<Car>>> call, Throwable t) {
        Log.e("Error", "something went wrong!...");
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
