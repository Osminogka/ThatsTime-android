package com.example.thatstime;

import com.example.thatstime.models.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/auth/signin")
    Call<LoginResponse> loginUser(@Body SigningCredentials loginRequest);

    @POST("/api/auth/signup")
    Call<LoginResponse> registerUser(@Body SingupCredentials singupCredentials);

    @POST("/api/records/newrecord")
    Call<RecordResponse> submitNewRecord(@Body RecordFromFrontEnd record);

    @GET("/api/records/certain")
    Call<RecordResponse> getRecords(
            @Query("relatedObject") String relatedObject,
            @Query("forYourSelf") boolean forYourSelf,
            @Query("isGroup") boolean isGroup,
            @Query("year") int year,
            @Query("month") int month,
            @Query("day") int day
    );
}
