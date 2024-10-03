package com.example.retrofit_android;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface GitHub {
    @GET("/users/{owner}/repos")
    Call<List<Repo>> repos(@Path("owner") String owner);
}
