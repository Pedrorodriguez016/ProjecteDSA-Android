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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {
    @POST("game/user/login")
    @Headers("Content-Type: application/json")
    Call<Datos> loginUser(@Body Datos d);
    @POST("game/user")
    @Headers("Content-Type: application/json")
    Call<DatosRegistro> newUser(@Body DatosRegistro d);
    @GET("game/user/{username}")
    Call<Datos> getUser(@Path("username")String username);
    @POST("game/user/{username}")
    @Headers("Content-Type: application/json")
    Call<Void> DeleteUser(@Path("username")String username, @Body Datos datos);
    @PUT("game/user/{username}")
    @Headers("Content-Type: application/json")
    Call<Datos> UpdateUser(@Path("username") String username, @Query("chgpass") boolean changePass, @Query("newpass") String newPassword, @Body Datos d);
}
