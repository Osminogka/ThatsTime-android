package com.example.thatstime;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {

    private String jwtToken;

    public AuthInterceptor(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Get the original request
        Request originalRequest = chain.request();

        // Add the JWT token to the request headers
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + jwtToken)
                .build();

        // Proceed with the modified request
        return chain.proceed(newRequest);
    }
}
