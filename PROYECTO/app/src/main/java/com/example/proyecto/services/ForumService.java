package com.example.proyecto.services;

import com.example.proyecto.models.Thread;
import com.example.proyecto.models.ForumMessage;
import com.example.proyecto.models.ThreadMessageWrapper;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Headers;

public interface ForumService {
    @GET("game/forums")
    Call<List<Thread>> getThreads();

    @POST("game/forums")
    @Headers("Content-Type: application/json")
    Call<Thread> createThread(@Body ThreadMessageWrapper threadMessage);

    @GET("game/forums/{threadId}")
    Call<List<ForumMessage>> getThreadMessages(@Path("threadId") Integer threadId);
}