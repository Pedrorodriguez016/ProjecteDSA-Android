package com.example.proyecto.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class UpdateUsername extends AppCompatActivity {
    private ProgressBar progressBar;
    public static final String BASE_URI = "http://10.0.2.2:8080/";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextnewPassword;
    private CheckBox checkboxEmail;
    private CheckBox checkboxpassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateusername_activity);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextnewPassword = findViewById(R.id.editTextNewPassword);
        progressBar = findViewById(R.id.progressBar3);
        checkboxpassword = findViewById(R.id.checkBoxpassword);
        checkboxEmail= findViewById(R.id.checkBoxEmail);
        checkboxEmail.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editTextEmail.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
        checkboxpassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editTextPassword.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextnewPassword.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
        progressBar.setVisibility(View.GONE);

    }

    public void PerfilOnClick(View v) {
        Intent intent = new Intent(UpdateUsername.this, PerfilUsuario.class);
        startActivity(intent);
        finish();
    }
    public void ActualizarDatos(View v){
        boolean changeemail = checkboxEmail.isChecked();
        boolean changepassword = checkboxpassword.isChecked();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        progressBar.setVisibility(View.VISIBLE);
        String email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();
        String newPassword = editTextnewPassword.getText().toString();
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Datos datos = new Datos();
        datos.setUsername(prefs.getString("username",""));
        datos.setPassword(prefs.getString("password", ""));
        datos.setId(prefs.getInt("id", 0));
        if (changepassword && (Password.isEmpty() || newPassword.isEmpty())) {
            Toast.makeText(this, "Introduce la contraseña actual y la nueva", Toast.LENGTH_SHORT).show();
            return;
        }
        if (changeemail && email.isEmpty()) {
            Toast.makeText(this, "Introduce un email válido", Toast.LENGTH_SHORT).show();
            return;
        }
        if(changeemail)
            datos.setEmail(email);
        else
            datos.setEmail(prefs.getString("email",""));

        LoginService lista = retrofit.create(LoginService.class);
        lista.UpdateUser(datos.username,changepassword, newPassword, datos).enqueue(new Callback<Datos>() {
            @Override
            public void onResponse(Call<Datos> call, Response<Datos> response) {
                if (response.isSuccessful()) {
                Datos datosactualizados = response.body();
                SharedPreferences.Editor editor = prefs.edit();
                progressBar.setVisibility(View.GONE);
                if (changeemail) {
                    editor.putString("email", datosactualizados.getEmail());
                }
                if (changepassword) {
                    editor.putString("password", datosactualizados.getPassword());
                }
                editor.apply();
                Toast.makeText(UpdateUsername.this, "Usuario actualizado con éxito", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(UpdateUsername.this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Error al actualizar los datos: " + response.code());
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

}
