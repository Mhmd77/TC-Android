package com.myapps.tc_android.view.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.User;
import com.myapps.tc_android.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    private static final int MAXIMUM_INPUT_LENGTH = 20;
    @BindView(R.id.edittext_signup_email)
    AppCompatEditText edittextSignupEmail;
    @BindView(R.id.edittext_signup_username)
    AppCompatEditText edittextSignupUsername;
    @BindView(R.id.edittext_signup_password)
    AppCompatEditText edittextSignupPassword;
    @BindView(R.id.edittext_signup_id)
    AppCompatEditText edittextSignupId;
    @BindView(R.id.button_signup)
    Button buttonSignup;
    @BindView(R.id.button_signin)
    Button buttonSignin;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        observeViewModel();
    }

    @OnClick({R.id.button_signup, R.id.button_signin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_signup:
                if (validate()) {
                    viewModel.signUpUser(edittextSignupUsername.getText().toString(), edittextSignupPassword.getText().toString(),
                            edittextSignupId.getText().toString(), edittextSignupEmail.getText().toString());
                }
                break;
            case R.id.button_signin:
                finish();
                break;
        }
    }

    private void observeViewModel() {
        viewModel.getObservableUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    Toast.makeText(SignUpActivity.this, "User " + user.getUsername() + " Signed Up Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String email = edittextSignupEmail.getText().toString();
        String password = edittextSignupPassword.getText().toString();
        String username = edittextSignupUsername.getText().toString();
        String id = edittextSignupId.getText().toString();
        if (email.isEmpty()) {
            edittextSignupEmail.setError("Email is empty");
            requestFocus(edittextSignupEmail);
            valid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edittextSignupEmail.setError("enter a valid email address");
            requestFocus(edittextSignupEmail);
            valid = false;
        } else {
            edittextSignupEmail.setError(null);
        }
        if (password.length() < 5) {
            edittextSignupPassword.setError("Password is less than 5 character");
            requestFocus(edittextSignupPassword);
            valid = false;
        } else if (password.length() > MAXIMUM_INPUT_LENGTH) {
            edittextSignupPassword.setError("Password is too long");
            requestFocus(edittextSignupPassword);
            valid = false;
        } else {
            edittextSignupPassword.setError(null);
        }
        if (username.isEmpty()) {
            edittextSignupUsername.setError("Username is empty");
            requestFocus(edittextSignupUsername);
            valid = false;
        } else if (username.length() > MAXIMUM_INPUT_LENGTH) {
            edittextSignupUsername.setError("Username is too long");
            requestFocus(edittextSignupPassword);
            valid = false;
        } else {
            edittextSignupUsername.setError(null);
        }
        if (id.length() < 5) {
            edittextSignupId.setError("ID is less than 5 digit");
            requestFocus(edittextSignupId);
            valid = false;
        } else if (id.length() > 10) {
            edittextSignupId.setError("ID is too long");
            requestFocus(edittextSignupPassword);
            valid = false;
        } else {
            edittextSignupId.setError(null);
        }
        return valid;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
