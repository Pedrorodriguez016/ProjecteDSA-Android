package com.example.proyecto.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.models.ForumMessage;
import com.example.proyecto.services.ForumService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThreadDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ForumMessageAdapter adapter;
    private List<ForumMessage> forumMessages = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView threadTitleView;
    private EditText newMessageEditText;
    private Integer threadId;
    public static final String BASE_URI = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_detail);

        // Obtener el ID y título del thread desde el intent
        threadId = getIntent().getIntExtra("threadId", -1);
        String threadTitle = getIntent().getStringExtra("threadTitle");

        // Inicializar vistas
        threadTitleView = findViewById(R.id.threadTitle);
        threadTitleView.setText(threadTitle);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerViewMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ForumMessageAdapter(this, forumMessages);
        recyclerView.setAdapter(adapter);

        newMessageEditText = findViewById(R.id.newMessageEditText);
        Button sendButton = findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(v -> sendMessage());

        // Cargar mensajes
        loadMessages();
    }

    private void loadMessages() {
        if (threadId == -1) {
            Toast.makeText(this, "Error: ID de thread no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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

        service.getThreadMessages(threadId).enqueue(new Callback<List<ForumMessage>>() {
            @Override
            public void onResponse(Call<List<ForumMessage>> call, Response<List<ForumMessage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    forumMessages.clear();
                    forumMessages.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(forumMessages.size() - 1);
                } else {
                    Log.e("Error", "Error al cargar los mensajes: " + response.code());
                    Toast.makeText(ThreadDetailActivity.this,
                            "Error al cargar los mensajes", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ForumMessage>> call, Throwable t) {
                Log.e("Error", "Error de conexión: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ThreadDetailActivity.this,
                        "Error al cargar los mensajes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = newMessageEditText.getText().toString().trim();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "El mensaje no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implementar el envío del mensaje cuando se agregue el endpoint correspondiente
        // Por ahora solo limpiamos el campo de texto
        newMessageEditText.setText("");
        Toast.makeText(this, "Funcionalidad en desarrollo", Toast.LENGTH_SHORT).show();
    }

    public void VolverOnClick(View v) {
        finish();
    }
}
