package com.myapps.tc_android.model;

import android.content.Context;
import android.support.constraint.Placeholder;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.myapps.tc_android.R;

import java.util.List;

@NonReusable
@Layout(R.layout.item_layout_car)
public class CarView {
    @View(R.id.textview_car_name)
    private TextView txtName;
    @View(R.id.textview_car_factory)
    private TextView txtFactory;
    @View(R.id.textview_car_kilometer)
    private TextView txtKilometer;
    @View(R.id.textview_car_price)
    private TextView txtPrice;
    private Car car;
    private Context context;
    private PlaceHolderView placeholder;

    public CarView(PlaceHolderView placeholder, Context context, Car car) {
        this.car = car;
        this.context = context;
        this.placeholder = placeholder;
    }

    @Resolve
    private void onResolved() {
        txtName.setText(car.getName());
        txtFactory.setText(car.getFactory());
        txtKilometer.setText(car.getKilometer());
        txtPrice.setText(car.getPrice());
    }

}
