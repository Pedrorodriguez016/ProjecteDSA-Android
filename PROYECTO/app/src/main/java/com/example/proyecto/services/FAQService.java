package com.example.proyecto.services;

import com.example.proyecto.models.FAQ;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FAQService {
    @GET("game/faqs")
    Call<List<FAQ>> getFAQs();
}
