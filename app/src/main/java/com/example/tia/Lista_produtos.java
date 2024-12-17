package com.example.tia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.tia.RecyclerViewItemClickList.RecyclerViewItemClickList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Lista_produtos extends AppCompatActivity {
    private RecyclerView recyclerViewProdutos;
    private AdapterProduto adapterProduto;
    private List <Produto> produtoList;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        produtoList = new ArrayList<>();
        adapterProduto = new AdapterProduto(getApplicationContext(),produtoList);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewProdutos.setHasFixedSize(true);
        recyclerViewProdutos.setAdapter(adapterProduto);

        //Evento de click no recyclerview

        recyclerViewProdutos.addOnItemTouchListener(
                new RecyclerViewItemClickList(
                        getApplicationContext(),
                        recyclerViewProdutos,
                        new RecyclerViewItemClickList.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                 Intent intent = new Intent(Lista_produtos.this,Detalhes_Produtos.class);
                                 intent.putExtra("foto",produtoList.get(position).getFoto());
                                 intent.putExtra("nome",produtoList.get(position).getNome());
                                 //intent.putExtra("descricao",produtoList.get(position).getDescricao());
                                 intent.putExtra("preco",produtoList.get(position).getPreco());
                                 startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        db = FirebaseFirestore.getInstance();
        db.collection("Produtos").orderBy("nome")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                                Produto produto = queryDocumentSnapshot.toObject(Produto.class);
                                produtoList.add(produto);
                                adapterProduto.notifyDataSetChanged();
                            }
                        }
                    }
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