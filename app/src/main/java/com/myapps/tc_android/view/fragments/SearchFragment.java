package com.myapps.tc_android.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.mindorks.placeholderview.PlaceHolderView;
import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.view.adapter.CarViewAdapter;
import com.myapps.tc_android.viewmodel.SearchViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements EditText.OnEditorActionListener, View.OnFocusChangeListener
        , TextWatcher {
    @BindView(R.id.search_editText)
    EditText searchEditText;
    @BindView(R.id.placeHolder_search_results)
    PlaceHolderView placeHolderSearchResults;
    Unbinder unbinder;
    @BindView(R.id.spinnerLoading)
    SpinKitView spinnerLoading;

    private SearchViewModel viewModel;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        initSpinner();
        observeViewModel();
        initEditText();
        return view;
    }

    private void initEditText() {
        searchEditText.addTextChangedListener(this);
    }

    private void initSpinner() {
        Sprite doubleBounce = new DoubleBounce();
        spinnerLoading.setIndeterminateDrawable(doubleBounce);
    }

    private void observeViewModel() {
        viewModel.getListSearchObservableData().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                if (cars != null) {
                    generateDataList(cars);
                }
                spinnerLoading.setVisibility(View.GONE);
            }
        });
    }

    private void generateDataList(final List<Car> cars) {
        placeHolderSearchResults.removeAllViews();
        for (Car c :
                cars) {
            placeHolderSearchResults.addView(new CarViewAdapter(placeHolderSearchResults, getActivity(), c));
        }
        placeHolderSearchResults.refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (event == null || !event.isShiftPressed()) {
                viewModel.searchCars(searchEditText.getText().toString());
                spinnerLoading.setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus && searchEditText.getText().length() > 0) {
            viewModel.searchCars(searchEditText.getText().toString());
        }
    }

    @OnClick(R.id.button_search)
    public void onViewClicked() {
        if (searchEditText.getText().length() > 0) {
            viewModel.searchCars(searchEditText.getText().toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        viewModel.searchCars(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (searchEditText.getText().length() > 0) {
            viewModel.searchCars(searchEditText.getText().toString());
        }
    }
}
