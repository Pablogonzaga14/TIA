package com.example.tia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil_Usuario extends AppCompatActivity {

    private CircleImageView foto_usuario;
    private TextView nome_usuario,email_usuario;
    private Button bt_editarPerfil;
    private String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponentes();
        bt_editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil_Usuario.this,Editar_Perfil.class);
                startActivity(intent);
            }
        });
    }
    //Metodo responsavel por iniciar uma conexão com o banco de dados para recuperar os dados do banco de dados
    protected void onStart(){
        super.onStart();

        //Abrindo o banco de dos
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Obtendo o usuario atual do banco de dados e recuperando o id
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //recuperando o email do usuario
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentereference = db.collection("Usuarios").document(usuarioID);
        documentereference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
               //caso tenha dados do banco de dados
                if(documentSnapshot != null){
                    //recuperando a imagem do banco de dados
                    Glide.with(getApplicationContext()).load(documentSnapshot.getString("foto")).into(foto_usuario);
                //recuperando o nome do usuario
                   nome_usuario.setText(documentSnapshot.getString("nome"));
                    //recuperando o email do usuario
                    email_usuario.setText(email);
                }
            }
        });
    }
    public void IniciarComponentes(){
        foto_usuario = findViewById(R.id.foto_usuario);
        nome_usuario = findViewById(R.id.nome_usuario);
        email_usuario = findViewById(R.id.email_usuario);
        bt_editarPerfil = findViewById(R.id.bt_editarPerfil);
    }
}