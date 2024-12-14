package com.example.proyecto.services;

import com.example.proyecto.models.Datos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    @POST("game/user/login")
    @Headers("Content-Type: application/json")
    Call<Datos> loginUser(@Body Datos d);
}
