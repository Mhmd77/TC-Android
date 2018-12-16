package com.myapps.tc_android.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.SignUpInfo;
import com.myapps.tc_android.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements Callback<ApiResponse<User>> {

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
                    SignUpInfo info = new SignUpInfo(edittextSignupUsername.getText().toString(), edittextSignupPassword.getText().toString(),
                            edittextSignupId.getText().toString(), edittextSignupEmail.getText().toString());
                    Call<ApiResponse<User>> call = ApiRepository.getRetrofitInstance().create(ApiService.class).signUpUser(info);
                    call.enqueue(this);
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
        }else if (username.length() > MAXIMUM_INPUT_LENGTH) {
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
        }else if (id.length() > 10) {
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

    @Override
    public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
        if (response.isSuccessful()) {
            if (response.body().getStatus().equals("OK")) {
                User user = response.body().getObject();
            }
            Toast.makeText(SignUpActivity.this, " " + response.body().getStatus(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SignUpActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
        Toast.makeText(SignUpActivity.this, "Signup failed!", Toast.LENGTH_SHORT).show();
    }
}
