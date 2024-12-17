package com.example.tia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Editar_Perfil extends AppCompatActivity {
    private CircleImageView fotoUsuario;
    private EditText edit_nome;
    private Button bt_AtualizarDados,bt_Selecionar_foto;
    private Uri mSlectUri;
    private String usuraioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        IniciarComponentes();
        bt_Selecionar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecionarFotoGaleria();
            }
        });
        bt_AtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pegando os dados que o usuario digitar na caixa nome e convertendo para string
                String name = edit_nome.getText().toString();
                //if para verificar se o campo está negativo e caso estiver mandando uma mensagem para ele preencher
                if(name.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,"Preencha todos os campos!",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    //caso nao esteja vazio chamara a função de atualizar os dados do usuario
                }else {
                    AtualizarDadosPerfil(v);
                }
            }
        });
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        mSlectUri = data.getData();
                        try {
                            fotoUsuario.setImageURI(mSlectUri);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public void SelecionarFotoGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }
    public void AtualizarDadosPerfil(View view){
        String nomeArquivo = UUID.randomUUID().toString();
        //criando uma pasta no storage
        final StorageReference reference = FirebaseStorage.getInstance().getReference("/imagens/"+nomeArquivo);
        reference.putFile(mSlectUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String foto = uri.toString();
                                String nome = edit_nome.getText().toString();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Map<String,Object> usuarios = new HashMap<>();
                                usuarios.put("nome",nome);
                                usuarios.put("foto",foto);
//esse metodo e oque diferencia de salvar os dados no banco de dados
//com ele atualizamos os itens do banco de dados
                                usuraioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                db.collection("Usuarios").document(usuraioID)
                                        .update("nome",nome,"foto",foto)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Snackbar snackbar = Snackbar.make(view,"Sucesso ao atualizar os dados",Snackbar.LENGTH_INDEFINITE)
                                                        .setAction("OK", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                finish();
                                                            }
                                                        });
                                                snackbar.show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public void IniciarComponentes(){
        fotoUsuario = findViewById(R.id.fotoUsuario);
        edit_nome = findViewById(R.id.edit_nome);
        bt_AtualizarDados = findViewById(R.id.bt_AtualizarDados);
        bt_Selecionar_foto = findViewById(R.id.bt_selecionar_foto);
    }
}