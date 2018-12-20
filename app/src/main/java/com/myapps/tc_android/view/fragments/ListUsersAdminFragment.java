package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.view.adapter.UserRecyclerView;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.service.model.ApiResponse;
import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.viewmodel.ListUserViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUsersAdminFragment extends Fragment implements UserRecyclerView.OnItemClickListener {
    @BindView(R.id.recyclerView_main_users)
    RecyclerView recyclerViewMainUsers;


    private UserRecyclerView adapter;
    private Unbinder unbinder;

    public ListUsersAdminFragment() {
    }

    public static ListUsersAdminFragment newInstance() {
        ListUsersAdminFragment fragment = new ListUsersAdminFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListUserViewModel viewModel = ViewModelProviders.of(this).get(ListUserViewModel.class);
        observeListUserViewModel(viewModel);
    }

    private void observeListUserViewModel(ListUserViewModel viewModel) {
        viewModel.getListCarsObservableData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                generateDataList(users);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_users_admin, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void generateDataList(final List<User> users) {
        adapter = new UserRecyclerView(getActivity(), users, this);
        recyclerViewMainUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMainUsers.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void deleteOnclick(final int layoutPosition) {
        /*ApiService service = ApiRepository.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<Object>> call = service.deleteUser(adapter.getList().get(layoutPosition).getId());
        call.enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "User : " + adapter.getList().get(layoutPosition).getName() + " deleted", Toast.LENGTH_SHORT).show();
                    Log.i("Connection", "User " + adapter.getList().get(layoutPosition).getId() + " deleted");
                    updateList();
                } else {
                    Log.e("Connection", "Deleting User Failed : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                Log.e("Connection", "Deleting User Failed : " + t.getMessage());
            }
        });*/
    }
}
