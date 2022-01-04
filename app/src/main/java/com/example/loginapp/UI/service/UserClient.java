package com.example.loginapp.UI.service;

import com.example.loginapp.UI.model.Login;
import com.example.loginapp.UI.model.Register;
import com.example.loginapp.UI.model.User;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {
    @POST("/authenticate")
    Call<User> login(@Body Login login);
    @POST("/register")
    Call<ResponseBody> register(@Body Register register);
    @POST("/user/getinfo")
    Call<User> getUser(@Header("Authorization") String authToken);
}
