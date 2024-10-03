package com.example.retrofit_android;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//AL FINAL NO HE FET SERVIR AQUESTA CLASSE
public class Controller implements Callback<List<Repo>> {

    static final String BASE_URL = "https://api.github.com";
    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GitHub gerritAPI = retrofit.create(GitHub.class);

        Call<List<Repo>> call = gerritAPI.repos("FalcoLluc");
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
        if(response.isSuccessful()) {
            List<Repo> changesList = response.body();
            changesList.forEach(repo -> System.out.println(repo.name));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Repo>> call, Throwable t) {
        t.printStackTrace();
    }
}
