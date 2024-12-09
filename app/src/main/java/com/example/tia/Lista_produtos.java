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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tia.Adapter.AdapterProduto;
import com.example.tia.Model.Produto;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Lista_produtos extends AppCompatActivity {
    private RecyclerView recyclerView_produtos;
    private AdapterProduto adapterProduto;
    private List <Produto> produtoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView_produtos = findViewById(R.id.recycleView_produtos);
        produtoList = new ArrayList<>();
        adapterProduto = new AdapterProduto(getApplicationContext(),produtoList);
        recyclerView_produtos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_produtos.setHasFixedSize(true);
        recyclerView_produtos.setAdapter(adapterProduto);

        Produto produto = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto);

        Produto produto1 = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto1);

        Produto produto2 = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto2);

        Produto produto3 = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto3);

        Produto produto4 = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto4);

        Produto produto5 = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto5);

        Produto produto6 = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto6);

        Produto produto7 = new Produto(R.drawable.ic_launcher_background,"produto 1", "R$20");
        produtoList.add(produto7);
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