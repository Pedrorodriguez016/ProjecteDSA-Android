package com.example.proyecto.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;

public class MenuUsuario extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void TiendaOnClick(View v) {
        Intent intent = new Intent(MenuUsuario.this, ShopActivity.class);
        startActivity(intent);
        finish();
    }
    public void InventarioOnClick(View v) {
        Intent intent = new Intent(MenuUsuario.this, InventarioActivity.class);
        startActivity(intent);
        finish();
    }
    public void PerfilOnClick(View v) {
        Intent intent = new Intent(MenuUsuario.this, PerfilUsuario.class);
        startActivity(intent);
        finish();
    }
    public void FAQsOnClick(View v) {
        Intent intent = new Intent(MenuUsuario.this, FAQSActivity.class);
        startActivity(intent);
        finish();
    }
    public void GameOnClick(View v) {
        Intent intent = new Intent(MenuUsuario.this, UnityActivity.class);
        startActivity(intent);
        finish();
    }
    public void ForumOnClick(View v) {
        Intent intent = new Intent(MenuUsuario.this, ForumActivity.class);
        startActivity(intent);
        finish();
    }
    public void CerrarOnClick(View v) {
        Toast.makeText(MenuUsuario.this, "Cierre sesión", Toast.LENGTH_SHORT).show();
        Log.i("INFO", "Cerrando sesión");
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("username");
        editor.remove("password");
        editor.remove("id");  // Añadido para eliminar también el ID
        editor.clear();
        editor.commit();

        Intent intent = new Intent(MenuUsuario.this, LoginUsuario.class);
        startActivity(intent);
        finish();
    }
}
