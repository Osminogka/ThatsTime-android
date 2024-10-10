package com.example.thatstime;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String jwtToken) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(jwtToken)) // Add the AuthInterceptor
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5001")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
