package com.example.proyecto.services;

import com.example.proyecto.models.Datos;
import com.example.proyecto.models.DatosRegistro;
import com.example.proyecto.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {
    @POST("game/user/login")
    @Headers("Content-Type: application/json")
    Call<Datos> loginUser(@Body Datos d);
    @POST("game/user")
    @Headers("Content-Type: application/json")
    Call<DatosRegistro> newUser(@Body DatosRegistro d);
    @GET("game/user/{id}")
    Call<Datos> getUser(@Path("id")int userId);
}
