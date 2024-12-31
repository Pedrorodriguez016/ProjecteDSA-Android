package com.example.proyecto.util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.models.Datos;
import com.example.proyecto.models.FAQ;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.proyecto.R;
import com.example.proyecto.models.Item;
import com.example.proyecto.services.FAQService;
import com.example.proyecto.services.ShopService;

public class FAQSActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FAQSAdapter adapter;
    private List<FAQ> questions = new ArrayList<>();
    private ProgressBar progressBar;
    private Datos datos;
    int id;
    public static final String BASE_URI = "http://10.0.2.2:8080/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerViewFAQ);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FAQSAdapter(this, questions);
        recyclerView.setAdapter(adapter);
        CargarFAQ();
    }

    private void CargarFAQ() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FAQService lista = retrofit.create(FAQService.class);
        progressBar.setVisibility(View.VISIBLE);
        lista.getFAQs().enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    questions.clear();
                    questions.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.e("Error", "Error al cargar los items: " + response.code());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<FAQ>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error", "Error de conexión: " + t.getMessage());
            }

        });
    }

    public void VolverOnClick(View v){
        Intent intent = new Intent (FAQSActivity.this, MenuUsuario.class);
        startActivity(intent);
        finish();
    }
}
