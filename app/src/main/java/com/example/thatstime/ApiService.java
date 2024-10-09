package com.example.thatstime;

import com.example.thatstime.models.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/auth/signin")
    Call<LoginResponse> loginUser(@Body SigningCredentials loginRequest);
}
