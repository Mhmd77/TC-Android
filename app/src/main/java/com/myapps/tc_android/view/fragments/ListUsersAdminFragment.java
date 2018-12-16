package com.myapps.tc_android.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.adapter.UserRecyclerView;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.service.repository.RetrofitClientInstance;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUsersAdminFragment extends Fragment implements Callback<ApiResponse<List<User>>>,UserRecyclerView.OnItemClickListener {
    @BindView(R.id.recyclerView_main_users)
    RecyclerView recyclerViewMainUsers;


    private UserRecyclerView adapter;
    private ApiService service;

    private Unbinder unbinder;

    public ListUsersAdminFragment() {
    }

    public static ListUsersAdminFragment newInstance() {
        ListUsersAdminFragment fragment = new ListUsersAdminFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_users_admin, container, false);
        unbinder = ButterKnife.bind(this, view);
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        updateList();

        return view;
    }

    private void updateList() {
        Call<ApiResponse<List<User>>> call = service.getAllUsers();
        call.enqueue(this);
    }

    private void generateDataList(final List<User> users) {
        adapter = new UserRecyclerView(getActivity(), users, this);
        recyclerViewMainUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMainUsers.setAdapter(adapter);
    }

    @Override
    public void onResponse(@NonNull Call<ApiResponse<List<User>>> call, @NonNull Response<ApiResponse<List<User>>> response) {
        if (response.body() != null) {
            generateDataList(response.body().getObject());
        } else {
            Log.e("CarList", "response body is null");
        }
    }

    @Override
    public void onFailure(@NonNull Call<ApiResponse<List<User>>> call, @NonNull Throwable t) {
        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void deleteOnclick(final int layoutPosition) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<Object>> call = service.deleteUser(adapter.getList().get(layoutPosition).getId());
        call.enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "User : " + adapter.getList().get(layoutPosition).getName() + " deleted", Toast.LENGTH_SHORT).show();
                    Log.i("Connection", "User " + adapter.getList().get(layoutPosition).getId()+ " deleted");
                    updateList();
                } else {
                    Log.e("Connection", "Deleting User Failed : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                Log.e("Connection", "Deleting User Failed : " + t.getMessage());
            }
        });
    }
}
