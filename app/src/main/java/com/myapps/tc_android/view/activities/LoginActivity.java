package com.myapps.tc_android.view.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edittext_signin_username)
    AppCompatEditText edittextsigninUsername;
    @BindView(R.id.edittext_signin_password)
    AppCompatEditText edittextsigninPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void observeViewModel(UserViewModel userViewModel) {
        userViewModel.getObservableUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    if (user.getRole().equals("admin")) {
                        startActivity(new Intent(LoginActivity.this, HomePageAdminActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                        finish();
                    }
                } else {
                    //TODO Handle failures
                }
            }
        });
    }

    @OnClick({R.id.button_signin, R.id.button_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_signin:
                if (validate()) {
                    UserViewModel.Factory factory = new UserViewModel.Factory(edittextsigninUsername.getText().toString(), edittextsigninPassword.getText().toString());
                    final UserViewModel userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
                    observeViewModel(userViewModel);
                }
                break;
            case R.id.button_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
        }
    }

    public boolean validate() {
        boolean valid = true;

        String email = edittextsigninUsername.getText().toString();
        String password = edittextsigninPassword.getText().toString();

        if (email.isEmpty()) {
            edittextsigninUsername.setError("enter a valid email address");
            requestFocus(edittextsigninUsername);
            valid = false;
        } else {
            edittextsigninUsername.setError(null);
        }

        if (password.isEmpty()) {
            edittextsigninPassword.setError("Password is empty");
            requestFocus(edittextsigninPassword);
            valid = false;
        } else {
            edittextsigninPassword.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
