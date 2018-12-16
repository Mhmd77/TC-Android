package com.myapps.tc_android.view.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.myapps.tc_android.R;
import com.myapps.tc_android.view.adapter.DrawerItemCustomAdapter;
import com.myapps.tc_android.service.model.FragmentAdminData;
import com.myapps.tc_android.view.fragments.ListCarsAdminFragment;
import com.myapps.tc_android.view.fragments.ListUsersAdminFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomePageAdminActivity extends AppCompatActivity {
    @BindView(R.id.actionButton_main_add_car)
    Button actionButtonMainAddCar;

    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.left_drawer)
    ListView leftDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_admin)
    Toolbar toolbarAdmin;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    private Fragment listCarsAdminFragment, listUsersAdminFragment, active;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin);
        ButterKnife.bind(this);
        mTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();
        initFragmnets();

        FragmentAdminData[] drawerItem = new FragmentAdminData[2];

        drawerItem[0] = new FragmentAdminData(R.drawable.ic_directions_car_black_24dp, "List Cars");
        drawerItem[1] = new FragmentAdminData(R.drawable.ic_account_box_black_24dp, "List Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

    }

    private void initFragmnets() {
        fragmentManager = getSupportFragmentManager();
        listCarsAdminFragment = ListCarsAdminFragment.newInstance();
        listUsersAdminFragment = ListUsersAdminFragment.newInstance();
        active = listCarsAdminFragment;

        fragmentManager.beginTransaction().add(R.id.content_frame, listUsersAdminFragment).hide(listUsersAdminFragment).commit();
        fragmentManager.beginTransaction().add(R.id.content_frame, listCarsAdminFragment).commit();
    }

    @OnClick(R.id.actionButton_main_add_car)
    public void onViewClicked() {
        startActivity(new Intent(HomePageAdminActivity.this, AddCarAdminActivity.class));
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    @SuppressLint("RestrictedApi")
    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = listCarsAdminFragment;
                actionButtonMainAddCar.setVisibility(View.VISIBLE);
                break;

            case 1:
                fragment = listUsersAdminFragment;
                actionButtonMainAddCar.setVisibility(View.GONE);
                break;

            default:
                break;
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().hide(active).show(fragment).commit();
            active = fragment;

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        setSupportActionBar(toolbarAdmin);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbarAdmin, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
}
