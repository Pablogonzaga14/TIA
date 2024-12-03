package com.example.tia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Lista_produtos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
//metodo responsavel por criar um menu novo para gente usar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
//Metodo responsavel por passar funcionalidade para os icones do menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int  itemID = item.getItemId();
        
        if (itemID == R.id.perfil){
            Intent intent = new Intent (Lista_produtos.this, Perfil_Usuario.class);
            startActivity(intent);
            finish();
        } else if (itemID == R.id.pedidos) {

        } else if (itemID == R.id.desogar) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Lista_produtos.this,"Usuario Deslogado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (Lista_produtos.this, Form_login.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}