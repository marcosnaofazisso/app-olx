package com.marcosviniciusferreira.olx.activity;

import android.content.pm.ConfigurationInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.marcosviniciusferreira.olx.R;
import com.marcosviniciusferreira.olx.helper.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;
    private Button botaoAcessar;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        //Verificar o estado do switch
                        if (tipoAcesso.isChecked()) {//Cadastro

                            autenticacao.createUserWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                                    } else {

                                        Toast.makeText(MainActivity.this, "Erro ao cadastrar!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {//Login

                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Login realizado com sucesso!", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(MainActivity.this, "Erro ao logar", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Preencha a senha!", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Preencha o email!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void inicializarComponentes() {
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);

    }
}
