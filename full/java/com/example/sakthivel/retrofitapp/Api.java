package com.example.sakthivel.retrofitapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";
    String a = "albums";
    @GET(a)
    Call<List<Albums>> getMovies();

}