package com.example.thatstime;

import com.example.thatstime.models.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/auth/signin")
    Call<LoginResponse> loginUser(@Body SigningCredentials loginRequest);

    @POST("/api/auth/signup")
    Call<LoginResponse> registerUser(@Body SingupCredentials singupCredentials);

    @POST("/api/records/newrecord")
    Call<RecordResponse> submitNewRecord(@Body RecordFromFrontEnd record);
}
