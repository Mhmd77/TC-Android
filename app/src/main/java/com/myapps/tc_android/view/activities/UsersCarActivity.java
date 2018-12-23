package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.view.adapter.CarViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsersCarActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_users_cars)
    PlaceHolderView recyclerViewUsersCars;
    @BindView(R.id.floatingActionButton_returnTop)
    FloatingActionButton floatingActionButtonReturnTop;
    private List<Car> cars;
    public static final String USERS_LIST_OF_CARS = "UsersCar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_car);
        ButterKnife.bind(this);
        initCars();
        initRecyclerView();
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewUsersCars.getBuilder().setLayoutManager(manager);
        recyclerViewUsersCars.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void initCars() {
        cars = (List<Car>) getIntent().getSerializableExtra(USERS_LIST_OF_CARS);
        for (Car c :
                cars) {
            recyclerViewUsersCars.addView(new CarViewAdapter(recyclerViewUsersCars, this, c));
        }
    }

    @OnClick(R.id.floatingActionButton_returnTop)
    public void onViewClicked() {
        recyclerViewUsersCars.smoothScrollToPosition(0);
    }
}
