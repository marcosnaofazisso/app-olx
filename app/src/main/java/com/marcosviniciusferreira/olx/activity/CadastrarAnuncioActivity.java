package com.marcosviniciusferreira.olx.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.marcosviniciusferreira.olx.R;
import com.marcosviniciusferreira.olx.helper.Permissoes;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastrarAnuncioActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText campoTitulo, campoDescricao;
    private CurrencyEditText campoValor;
    private Button buttonCadastrar;
    private MaskEditText campoTelefone;
    private ImageView imagem1, imagem2, imagem3;

    private Spinner campoEstado, campoCategoria;


    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private List<String> listaFotosRecuperadas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncio);

        //Validar as permissoes
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializarComponentes();
        carregarDadosSpinner();


    }

    private void carregarDadosSpinner() {

        //String[] estados = new String[] {"AC", "SP"};

        //Configurar spinner de Estados
        String[] estados = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                estados
        );
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoEstado.setAdapter(adapterEstados);

        //Configurar spinner de Categorias
        String[] categorias = getResources().getStringArray(R.array.categoria);
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                categorias
        );
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoCategoria.setAdapter(adapterCategorias);

    }

    private void inicializarComponentes() {
        campoTitulo = findViewById(R.id.editTitulo);
        campoValor = findViewById(R.id.editValor);
        campoDescricao = findViewById(R.id.editDescricao);
        campoTelefone = findViewById(R.id.editTelefone);
        buttonCadastrar = findViewById(R.id.buttonCadastrarAnuncio);

        imagem1 = findViewById(R.id.imageCadastro1);
        imagem2 = findViewById(R.id.imageCadastro2);
        imagem3 = findViewById(R.id.imageCadastro3);

        imagem1.setOnClickListener(this);
        imagem2.setOnClickListener(this);
        imagem3.setOnClickListener(this);

        campoEstado = findViewById(R.id.spinnerEstado);
        campoCategoria = findViewById(R.id.spinnerCategoria);


        //Configurar localidade para pt-BR
        Locale locale = new Locale("pt", "BR");
        campoValor.setLocale(locale);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageCadastro1:
                escolherImagem(1);
                break;
            case R.id.imageCadastro2:
                escolherImagem(2);
                break;
            case R.id.imageCadastro3:
                escolherImagem(3);
                break;
        }
    }

    public void escolherImagem(int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            //Recuperar imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            //Configurar imagem no imageview
            if (requestCode == 1) {
                imagem1.setImageURI(imagemSelecionada);
            } else if (requestCode == 2) {
                imagem2.setImageURI(imagemSelecionada);

            } else if (requestCode == 3) {
                imagem3.setImageURI(imagemSelecionada);

            }
            listaFotosRecuperadas.add(caminhoImagem);

        }

    }
}
