package com.myapps.tc_android.model;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.Placeholder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.view.activities.CarProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @View(R.id.viewFlipper_car)
    ViewFlipper flipper;
    @View(R.id.imageview_car_logo)
    ImageView imageViewCarLogo;
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
        txtKilometer.setText(String.valueOf(car.getKilometer()));
        txtPrice.setText(String.valueOf(car.getPrice()));
        if (car.getImageUrl() != null) {
            Picasso.get()
                    .load(RetrofitClientInstance.getBaseUrl() + ApiService.imageApi + car.getImageUrl())
                    .error(R.drawable.sample)
                    .into(imageViewCarLogo);
        }
        flipper.setInAnimation(context, R.anim.grow_from_midle);
        flipper.setOutAnimation(context, R.anim.shrink_to_midle);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(placeholder);
    }

    @Click(R.id.button_see_car_profile)
    public void flipCardToBack() {
        flipper.showNext();
    }

    @Click(R.id.button_see_car)
    public void flipCardToFront() {
        flipper.showNext();
    }

    @Click(R.id.viewFlipper_car)
    public void cardOnClick() {
        Intent intent = new Intent(context, CarProfileActivity.class);
        intent.putExtra("carId", car.getId());
        context.startActivity(intent);
    }
}
