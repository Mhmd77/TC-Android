package com.myapps.tc_android.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.service.model.Car;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarsRecyclerView extends RecyclerView.Adapter<CarsRecyclerView.ViewHolder> {

    private Context context;
    private List<Car> list;
    private UserOnItemClickListener onItemClickListener;

    public CarsRecyclerView(Context context, List<Car> list,
                            AdminOnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    public CarsRecyclerView(Context context, List<Car> list, UserOnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<Car> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<Car> getList() {
        return list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_car_name)
        TextView textviewCarName;
        @BindView(R.id.textview_car_factory)
        TextView textviewCarFactory;
        @BindView(R.id.textview_car_kilometer)
        TextView textviewCarKilometer;
        @BindView(R.id.textview_car_price)
        TextView textviewCarPrice;
        @BindView(R.id.imageview_car_logo)
        ImageView imageViewCarLogo;
        @BindView(R.id.viewFlipper_car)
        ViewFlipper flipper;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            flipper.setInAnimation(context,R.anim.grow_from_midle);
            flipper.setOutAnimation(context,R.anim.shrink_to_midle);
            if (onItemClickListener instanceof AdminOnItemClickListener) {
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.cardOnClick(itemView, getLayoutPosition());
                }
            });
        }
        @OnClick({R.id.button_see_car_profile, R.id.button_see_car})
        public void flipCards(View view) {
            switch (view.getId()) {
                case R.id.button_see_car_profile:
                    flipper.showNext();
                    break;
                case R.id.button_see_car:
                    flipper.showNext();
                    break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_layout_car, parent, false);
        ButterKnife.bind(this, view);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Car item = list.get(position);
        holder.textviewCarName.setText(item.getName());
        holder.textviewCarFactory.setText(item.getFactory());
        holder.textviewCarKilometer.setText(String.valueOf(item.getKilometer()));
        holder.textviewCarPrice.setText(String.valueOf(item.getPrice()));
        Picasso.get()
                .load(ApiRepository.getBaseUrl() + ApiService.imageApi + item.getImageUrl())
                .error(R.drawable.sample)
                .into(holder.imageViewCarLogo);
        holder.textviewCarPrice.setText('$' + String.valueOf(item.getPrice()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface AdminOnItemClickListener extends UserOnItemClickListener {
        void deleteOnClick(View view, int position);

        void updateOnClick(View view, int position);
    }

    public interface UserOnItemClickListener {
        void cardOnClick(View view, int position);
    }

}