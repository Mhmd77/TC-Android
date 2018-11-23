package com.myapps.tc_android.controller.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myapps.tc_android.R;
import com.myapps.tc_android.model.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarsRecyclerView extends RecyclerView.Adapter<CarsRecyclerView.ViewHolder> {


    @BindView(R.id.cart_onclick)
    CardView cartOnclick;
    private Context context;
    private List<Car> list;
    private OnItemClickListener onItemClickListener;
    private boolean isAdmin = true;

    public CarsRecyclerView(Context context, List<Car> list,
                            OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    public CarsRecyclerView(Context context, List<Car> list) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = null;
        isAdmin = false;
    }

    public void setList(List<Car> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
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
        @BindView(R.id.button_car_delete)
        Button buttonCarDelete;
        @BindView(R.id.button_car_update)
        Button buttonCarUpdate;
        @BindView(R.id.button_car_profile)
        Button buttonCarProfile;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            buttonCarUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.updateOnClick(v, getAdapterPosition());
                }
            });
            buttonCarDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.deleteOnClick(v, getAdapterPosition());
                }
            });
            cartOnclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.cartOnClick(v, getAdapterPosition());
                }
            });


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
        holder.buttonCarProfile.setVisibility(View.VISIBLE);
        if (!isAdmin) {
            holder.buttonCarDelete.setVisibility(View.GONE);
            holder.buttonCarUpdate.setVisibility(View.GONE);
        } else {
            holder.buttonCarDelete.setVisibility(View.VISIBLE);
            holder.buttonCarUpdate.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void deleteOnClick(View view, int position);

        void updateOnClick(View view, int position);

        void cartOnClick(View view, int position);


    }

}