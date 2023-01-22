package com.marcosviniciusferreira.olx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.marcosviniciusferreira.olx.R;

public class MainActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;
    private Button botaoAcessar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        //Verificar o estado do switch
                        if (tipoAcesso.isChecked()) {//Cadastro

                        } else {//Login

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
