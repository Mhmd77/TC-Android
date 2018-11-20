package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.myapps.tc_android.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_signup, R.id.button_signin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_signup:
                if (validate()) {

                }
                break;
            case R.id.button_signin:
                finish();
                break;
        }
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
        if (password.isEmpty()) {
            edittextSignupPassword.setError("Password is empty");
            requestFocus(edittextSignupPassword);
            valid = false;
        } else {
            edittextSignupPassword.setError(null);
        }
        if (username.isEmpty()) {
            edittextSignupUsername.setError("Username is empty");
            requestFocus(edittextSignupUsername);
            valid = false;
        } else {
            edittextSignupUsername.setError(null);
        }
        if (id.isEmpty()) {
            edittextSignupId.setError("ID is empty");
            requestFocus(edittextSignupId);
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
