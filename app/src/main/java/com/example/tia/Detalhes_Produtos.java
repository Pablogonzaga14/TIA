package com.example.tia;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class Detalhes_Produtos extends AppCompatActivity {

    private ImageView dtFotoProduto;
    private TextView dtNomeProduto,dtPrecoProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_produtos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponentes();
        String foto = getIntent().getExtras().getString("foto");
        String nome = getIntent().getExtras().getString("nome");
       // String descricao = getIntent().getExtras().getString("descricao");
        String preco = getIntent().getExtras().getString("preco");

        Glide.with(getApplicationContext()).load(foto).into(dtFotoProduto);
        dtNomeProduto.setText(nome);
       //dtDescricaoProduto.setText(descricao);
        dtPrecoProduto.setText(preco);

    }
    public void IniciarComponentes(){
        dtFotoProduto = findViewById(R.id.dtFotoProduto);
        dtNomeProduto = findViewById(R.id.dtNomeProduto);
        //dtDescricaoProduto = findViewById(R.id.dtDescricaoProduto);
        dtPrecoProduto = findViewById(R.id.dtPrecoProduto);
    }
}