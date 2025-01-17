package com.example.proyecto.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.models.ForumMessage;
import com.example.proyecto.models.Thread;
import com.example.proyecto.models.ThreadMessageWrapper;
import com.example.proyecto.services.ForumService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewThreadActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextMessage;
    private ProgressBar progressBar;
    private Button submitButton;
    public static final String BASE_URI = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thread);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextMessage = findViewById(R.id.editTextMessage);
        progressBar = findViewById(R.id.progressBar);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> createNewThread());
    }

    public void createNewThread() {
        String title = editTextTitle.getText().toString().trim();
        String messageText = editTextMessage.getText().toString().trim();

        if (title.isEmpty() || messageText.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        submitButton.setEnabled(false);

        // Obtener ID del usuario desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        Integer userId = prefs.getInt("id", 0);

        if (userId == 0) {  // Comprobamos si es el valor por defecto
            Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            submitButton.setEnabled(true);
            return;
        }

        // Convertir el ID a Integer para el modelo

        // Crear el thread y el mensaje
        Thread newThread = new Thread();
        newThread.setTitle(title);
        newThread.setCreator(userId);
        newThread.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        ForumMessage newMessage = new ForumMessage();
        newMessage.setSender(userId);
        newMessage.setMessage(messageText);
        newMessage.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        ThreadMessageWrapper wrapper = new ThreadMessageWrapper(newThread, newMessage);

        // Configurar Retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ForumService service = retrofit.create(ForumService.class);

        service.createThread(wrapper).enqueue(new Callback<Thread>() {
            @Override
            public void onResponse(Call<Thread> call, Response<Thread> response) {
                progressBar.setVisibility(View.GONE);
                submitButton.setEnabled(true);

                if (response.isSuccessful()) {
                    Toast.makeText(NewThreadActivity.this, "Thread creado con éxito", Toast.LENGTH_SHORT).show();
                    finish(); // Volver a la actividad anterior
                } else {
                    Toast.makeText(NewThreadActivity.this, "Error al crear el thread: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Thread> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                submitButton.setEnabled(true);
                Toast.makeText(NewThreadActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error al crear thread: " + t.getMessage());
            }
        });
    }

    public void VolverOnClick(View v) {
        finish();
    }
}