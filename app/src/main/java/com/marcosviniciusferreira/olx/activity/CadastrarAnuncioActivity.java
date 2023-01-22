package com.marcosviniciusferreira.olx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.marcosviniciusferreira.olx.R;

import java.util.Locale;

public class CadastrarAnuncioActivity extends AppCompatActivity {

    private EditText campoTitulo, campoDescricao;
    private CurrencyEditText campoValor;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncio);

        inicializarComponentes();


    }

    private void inicializarComponentes() {
        campoTitulo = findViewById(R.id.editTitulo);
        campoValor = findViewById(R.id.editValor);
        campoDescricao = findViewById(R.id.editDescricao);
        buttonCadastrar = findViewById(R.id.buttonCadastrarAnuncio);

        //Configurar localidade para pt-BR
        Locale locale = new Locale("pt", "BR");
        campoValor.setLocale(locale);
    }

}
