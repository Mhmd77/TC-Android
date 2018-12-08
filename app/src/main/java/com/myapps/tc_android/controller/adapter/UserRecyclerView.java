package com.myapps.tc_android.controller.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myapps.tc_android.R;
import com.myapps.tc_android.model.User;
import com.myapps.tc_android.model.UserHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRecyclerView extends RecyclerView.Adapter<UserRecyclerView.ViewHolder> {

    private Context context;
    private List<User> list;
    private UserRecyclerView.OnItemClickListener onItemClickListener;

    public UserRecyclerView(Context context, List<User> list,
                            UserRecyclerView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    public void setList(List<User> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<User> getList() {
        return list;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_user)
        CardView cartOnclick;
        @BindView(R.id.textview_username)
        TextView textviewUsername;
        @BindView(R.id.textview_name)
        TextView textviewName;
        @BindView(R.id.textview_email)
        TextView textviewEmail;
        @BindView(R.id.textview_last_name)
        TextView textviewLastName;
        @BindView(R.id.delete_user)
        Button deleteUser;
        @BindView(R.id.cons_lay_user)
        ConstraintLayout consLayUser;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            deleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.deleteOnclick(getLayoutPosition());
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_user, parent, false);
        ButterKnife.bind(this, view);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(UserRecyclerView.ViewHolder holder, final int position) {
        User item = list.get(position);
        if(item.getId() == UserHolder.Instance().getUser().getId()){
            holder.deleteUser.setVisibility(View.GONE);
        }
        if(item.getRole().equals("super_admin")) {
            holder.deleteUser.setVisibility(View.GONE);
        }
        holder.textviewName.setText("Name : " + item.getName());
        holder.textviewUsername.setText("Username : " + item.getUsername());
        holder.textviewEmail.setText("Email : " + item.getEmail());
        holder.textviewLastName.setText("Lastname : " + item.getLastName());

    }

    public interface OnItemClickListener  {

        void deleteOnclick(int layoutPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

