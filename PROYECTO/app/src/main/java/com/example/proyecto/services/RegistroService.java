package com.example.proyecto.services;

import com.example.proyecto.models.DatosRegistro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegistroService {
    @POST("game/user")
    @Headers("Content-Type: application/json")
    Call<DatosRegistro> newUser(@Body DatosRegistro d);
}
