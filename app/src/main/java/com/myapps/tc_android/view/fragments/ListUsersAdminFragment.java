package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.view.adapter.UserRecyclerView;
import com.myapps.tc_android.viewmodel.ListUserViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListUsersAdminFragment extends Fragment implements UserRecyclerView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView_main_users)
    RecyclerView recyclerViewMainUsers;
    @BindView(R.id.swipeLayout_main_users)
    SwipeRefreshLayout swipeLayoutMainUsers;


    private UserRecyclerView adapter;
    private Unbinder unbinder;
    private ListUserViewModel viewModel;

    public ListUsersAdminFragment() {
    }

    public static ListUsersAdminFragment newInstance() {
        ListUsersAdminFragment fragment = new ListUsersAdminFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeLayoutMainUsers.setOnRefreshListener(this);
        viewModel = ViewModelProviders.of(this).get(ListUserViewModel.class);
        observeListUserViewModel(viewModel);
        viewModel.getListUser();
    }

    private void observeListUserViewModel(ListUserViewModel viewModel) {
        viewModel.getListUsersObservableData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null)
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
        swipeLayoutMainUsers.setRefreshing(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void deleteOnclick(final int layoutPosition) {
        LiveData<Boolean> liveData = ApiRepository.getInstance().deleteUser(adapter.getList().get(layoutPosition).getId());
        obserDeleteRequest(liveData);
    }

    private void obserDeleteRequest(LiveData<Boolean> liveData) {
        liveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean result) {
                if (result) {
                    Toast.makeText(getActivity(), "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                    observeListUserViewModel(viewModel);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong ... please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        viewModel.getListUser();
    }
}
