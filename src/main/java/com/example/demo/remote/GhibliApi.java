package com.example.demo.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

// https://ghibliapi.herokuapp.com/#section/Studio-Ghibli-API
// https://ghibliapi.herokuapp.com/people
public interface GhibliApi {

    @GET("people/{id}")
    @Headers("Content-Type: application/json")
    Call<Person> getPerson(@Path("id") String id);

    @GET("species/{id}")
    @Headers("Content-Type: application/json")
    Call<Species> getSpecies(@Path("id") String id);
}
