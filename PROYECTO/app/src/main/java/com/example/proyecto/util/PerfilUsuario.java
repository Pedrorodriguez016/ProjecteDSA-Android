package com.example.proyecto.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.models.Datos;
import com.example.proyecto.services.LoginService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilUsuario extends AppCompatActivity {

    private TextView Usuario;
    private TextView Email;
    private TextView Dinero;
    private ProgressBar progressBar;
    public static final String BASE_URI = "http://147.83.7.208:80/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Usuario = findViewById(R.id.UsuarioText);
        Email = findViewById(R.id.Emailtext);
        Dinero = findViewById(R.id.Dinerotext);
        progressBar = findViewById(R.id.progressPerfil);
        CargarDatos();
    }

    public void CargarDatos() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        progressBar.setVisibility(View.VISIBLE);
        LoginService lista = retrofit.create(LoginService.class);
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", "");
        lista.getUser(username).enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Datos datos = response.body();
                    Usuario.setText(datos.getUsername());
                    Email.setText(datos.getEmail());
                    Dinero.setText(String.valueOf(datos.getMoney()));
                    Log.i("datos", "username" + datos.getUsername() + "email" + datos.getEmail() + "dinero" + datos.getMoney());
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.e("Error", "Error al cargar los datos: " + response.code());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    public void EliminarCuenta(View v){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        progressBar.setVisibility(View.VISIBLE);
        LoginService lista = retrofit.create(LoginService.class);
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Datos datos = new Datos();
        datos.setUsername(prefs.getString("username", ""));
        datos.setPassword(prefs.getString("password",""));
        datos.setEmail(prefs.getString("email",""));
        datos.setId(prefs.getInt("id", 0));
        datos.setMoney(prefs.getInt("money", 0));
        lista.DeleteUser(datos.username, datos).enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if (response.isSuccessful()) {
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(PerfilUsuario.this, LoginUsuario.class);
                    startActivity(intent);
                    finish();
                    progressBar.setVisibility(View.GONE);

                } else {
                    Log.e("Error", "Error al cargar los datos: " + response.code());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Datos> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error", "Error de conexión: " + t.getMessage());
            }
        });

    }

    public void VolverOnClick(View v) {
        Intent intent = new Intent(PerfilUsuario.this, MenuUsuario.class);
        startActivity(intent);
        finish();
    }

    public void ActualizarCuenta(View v) {
        Intent intent = new Intent(PerfilUsuario.this, Update.class);
        startActivity(intent);
        finish();
    }

}