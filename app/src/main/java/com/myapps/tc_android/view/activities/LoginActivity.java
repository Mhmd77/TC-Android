package com.myapps.tc_android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.controller.network.ApiService;
import com.myapps.tc_android.controller.network.RetrofitClientInstance;
import com.myapps.tc_android.model.LoginInfo;
import com.myapps.tc_android.model.ApiResponse;
import com.myapps.tc_android.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @OnClick({R.id.button_signin, R.id.button_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_signin:
                if (validate()) {
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    Call<ApiResponse<User>> call = service.loginUser(new LoginInfo(edittextsigninUsername.getText().toString(),
                            edittextsigninPassword.getText().toString()));
                    call.enqueue(new Callback<ApiResponse<User>>() {
                        @Override
                        public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                if (response.body().getStatus().equals("OK")) {
                                    if (response.body().getObject().getRole().equals("admin")) {
                                        //TODO Implement Admin
                                        Log.i("TAAAG", "" + response.body().getObject().getName());
                                    } else {
                                        //TODO Implement Home Activity

                                    }
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
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
