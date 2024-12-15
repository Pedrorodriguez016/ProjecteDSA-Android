package com.example.proyecto.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.widget.ProgressBar;

import com.example.proyecto.R;
import com.example.proyecto.models.Datos;
import com.example.proyecto.models.Item;
import com.example.proyecto.services.ShopService;


public class Inventario extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InventarioAdapter adapter;
    private List<Item> items = new ArrayList<>();
    private ProgressBar progressBar;
    private Datos datos;
    int id;
    public static final String BASE_URI = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InventarioAdapter(this, items);
        recyclerView.setAdapter(adapter);

        CargarInventario();

    }


    private void CargarInventario() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        int id= prefs.getInt("id",-1);
        Log.i("id", "id usuario"+ id);
        ShopService lista = retrofit.create(ShopService.class);
        progressBar.setVisibility(View.VISIBLE);
        lista.inventario(id).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {


                if (response.isSuccessful() && response.body() != null) {
                    items.clear();
                    items.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.e("Error", "Error al cargar los items: " + response.code());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error", "Error de conexión: " + t.getMessage());
            }

        });
    }
    public void VolverOnClick(View v){
        Intent intent = new Intent (Inventario.this, MenuUsuario.class);
        startActivity(intent);
        finish();
    }
}
