package com.myapps.tc_android.service.repository;

import com.myapps.tc_android.utils.TokenAccess;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        if (TokenAccess.getInstance().getToken() != null) {
            Headers headers = request.headers().newBuilder().add("Access-Token", TokenAccess.getInstance().getToken()).build();
            request = request.newBuilder().headers(headers).build();
        }
        return chain.proceed(request);
    }
}
