package com.example.proyecto.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.models.Thread;
import com.example.proyecto.services.ForumService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForumActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ForumAdapter adapter;
    private List<Thread> threads = new ArrayList<>();
    private ProgressBar progressBar;
    public static final String BASE_URI = "http://147.83.7.208:80/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerViewForum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ForumAdapter(this, threads, this::onThreadClick);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabNewThread = findViewById(R.id.fabNewThread);
        fabNewThread.setOnClickListener(v -> {
            Intent intent = new Intent(ForumActivity.this, NewThreadActivity.class);
            startActivity(intent);
        });

        loadThreads();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadThreads(); // Recargar threads cuando volvemos a la actividad
    }

    private void loadThreads() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ForumService service = retrofit.create(ForumService.class);
        progressBar.setVisibility(View.VISIBLE);

        service.getThreads().enqueue(new Callback<List<Thread>>() {
            @Override
            public void onResponse(Call<List<Thread>> call, Response<List<Thread>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    threads.clear();
                    threads.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("Error", "Error al cargar los threads: " + response.code());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Thread>> call, Throwable t) {
                Log.e("Error", "Error de conexión: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onThreadClick(Thread thread) {
        Intent intent = new Intent(ForumActivity.this, ThreadDetailActivity.class);
        intent.putExtra("threadId", thread.getId());
        intent.putExtra("threadTitle", thread.getTitle());
        startActivity(intent);
    }

    public void VolverOnClick(View v) {
        Intent intent = new Intent(ForumActivity.this, MenuUsuario.class);
        startActivity(intent);
        finish();
    }
}