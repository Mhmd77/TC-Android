package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.myapps.tc_android.R;
import com.myapps.tc_android.view.fragments.HomeFragment;
import com.myapps.tc_android.view.fragments.RentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frame_layout_container)
    FrameLayout frameLayoutContainer;
    @BindView(R.id.bottom_navigation_menu_home_home)
    BottomNavigationView bottomNavigationMenuHomeHome;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.buttonSortYear)
    public Button buttonSortYear;
    @BindView(R.id.buttonSortCost)
    public Button buttonSortCost;
    private Fragment homeFragment, rentFragment, profileFragment, active;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initFragmnets();
        bottomNavigationMenuHomeHome.setOnNavigationItemSelectedListener(this);
        bottomNavigationMenuHomeHome.setSelectedItemId(R.id.navigation_home);
    }

    private void initFragmnets() {
        fm = getSupportFragmentManager();
        homeFragment = HomeFragment.newInstance();
        rentFragment = RentFragment.newInstance();
        profileFragment = RentFragment.newInstance();
        active = homeFragment;
        fm.beginTransaction().add(R.id.frame_layout_container, profileFragment, "3").hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout_container, rentFragment, "2").hide(rentFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout_container, homeFragment, "1").commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                buttonSortYear.setVisibility(View.VISIBLE);
                buttonSortCost.setVisibility(View.VISIBLE);
                return true;
            case R.id.navigation_rent:
                fm.beginTransaction().hide(active).show(rentFragment).commit();
                active = rentFragment;
                buttonSortYear.setVisibility(View.INVISIBLE);
                buttonSortCost.setVisibility(View.INVISIBLE);
                return true;
            case R.id.navigation_profile:
                fm.beginTransaction().hide(active).show(rentFragment).commit();
                active = rentFragment;
                buttonSortYear.setVisibility(View.INVISIBLE);
                buttonSortCost.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }
}
