package com.myapps.tc_android.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.model.User;
import com.myapps.tc_android.model.UserHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
        fillUserInfo();
        return view;
    }

    private void fillUserInfo() {
        User user = UserHolder.Instance().getUser();
        textviewAge.setText(String.valueOf(user.getAge()));
        textviewName.setText(user.getLastName() + ", " + user.getName());
        textviewEmail.setText(user.getEmail());
        textviewUsername.setText(user.getUsername());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_all_user_cars)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.button_all_user_cars:
                //TODO Goto all users car page
                break;
        }
    }
}
