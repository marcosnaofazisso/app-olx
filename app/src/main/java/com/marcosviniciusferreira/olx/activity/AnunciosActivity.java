package com.marcosviniciusferreira.olx.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.marcosviniciusferreira.olx.R;
import com.marcosviniciusferreira.olx.adapter.AdapterAnuncios;
import com.marcosviniciusferreira.olx.helper.ConfiguracaoFirebase;
import com.marcosviniciusferreira.olx.model.Anuncio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Button buttonRegiao, buttonCategoria, buttonLimparFiltros;
    private RecyclerView recyclerAnunciosPublicos;
    private AdapterAnuncios adapterAnuncios;
    private List<Anuncio> listaAnuncios = new ArrayList<>();

    private DatabaseReference anunciosPublicosRef;

    private AlertDialog dialog;
    private String filtroEstado = "";
    private String filtroCategoria = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        inicializarComponentes();

        //Configuracoes iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios");


        //Configurar adapter
        adapterAnuncios = new AdapterAnuncios(listaAnuncios, this);

        //Configurar recyclerview
        recyclerAnunciosPublicos.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnunciosPublicos.setHasFixedSize(true);
        recyclerAnunciosPublicos.setAdapter(adapterAnuncios);

        recuperarAnunciosPublicos();

        buttonLimparFiltros.setVisibility(View.GONE);


    }

    //Método que cria as opções do menu (ocorre apenas 1 vez)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Método que prepara as opções do menu
    //ocorre antes dos opções aparecerem no createOptionsMenu
    //E acontece cada vez que o menu aparece para o usuário
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (autenticacao.getCurrentUser() == null) {                    //usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado, true);
        } else {                                                        //usuario logado
            menu.setGroupVisible(R.id.group_logado, true);

        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sair:
                autenticacao.signOut();

                //Ao invalidar os itens de menu, e então ele prepara o menu novamente (onPrepateOptionsMenu)
                invalidateOptionsMenu();

                break;
            case R.id.menu_cadastrar:
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                break;

            case R.id.menu_anuncios:
                startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void limparFiltros(View view) {
        Log.i("CHURROS", "listaAnuncios ===>>> " + listaAnuncios);
        Log.i("CHURROS", "filtroEstado ===>>> " + filtroEstado);
        Log.i("CHURROS", "filtroCategoria ===>>> " + filtroCategoria);
        recuperarAnunciosPublicos();
        Log.i("CHURROS", "listaAnuncios ===>>> " + listaAnuncios);
        Log.i("CHURROS", "filtroEstado ===>>> " + filtroEstado);
        Log.i("CHURROS", "filtroCategoria ===>>> " + filtroCategoria);
    }

    public void inicializarComponentes() {

        recyclerAnunciosPublicos = findViewById(R.id.recyclerAnunciosPublicos);
        buttonCategoria = findViewById(R.id.buttonCategoria);
        buttonRegiao = findViewById(R.id.buttonRegiao);
        buttonLimparFiltros = findViewById(R.id.buttonLimparFiltros);

    }

    public void filtrarPorEstado(View view) {

        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
        dialogEstado.setTitle("Selecione o estado desejado");

        //Configurar spinner dentro do dialog
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        final Spinner spinnerEstado = viewSpinner.findViewById(R.id.spinnerFiltro);

        //Configurar spinner de Estados
        String[] estados = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                estados
        );
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterEstados);

        dialogEstado.setView(viewSpinner);

        dialogEstado.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                filtroEstado = spinnerEstado.getSelectedItem().toString();
                recuperarAnunciosPorEstado();

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog dialog = dialogEstado.create();
        dialog.show();

    }

    private void recuperarAnunciosPorEstado() {

        buttonLimparFiltros.setVisibility(View.VISIBLE);

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando anúncios")
                .setCancelable(false)
                .build();

        dialog.show();

        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios")
                .child(filtroEstado);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaAnuncios.clear();

                for (DataSnapshot categorias : dataSnapshot.getChildren()) {
                    for (DataSnapshot anuncios : categorias.getChildren()) {

                        Anuncio anuncio = anuncios.getValue(Anuncio.class);
                        listaAnuncios.add(anuncio);

                    }
                }

                Collections.reverse(listaAnuncios);
                adapterAnuncios.notifyDataSetChanged();

                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void filtrarPorCategoria(View view) {

        AlertDialog.Builder dialogCategoria = new AlertDialog.Builder(this);
        dialogCategoria.setTitle("Selecione a categoria desejada");

        //Configurar spinner dentro do dialog
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        final Spinner spinnerCategoria = viewSpinner.findViewById(R.id.spinnerFiltro);

        //Configurar spinner de Estados
        String[] categorias = getResources().getStringArray(R.array.categoria);
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                categorias
        );
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorias);

        dialogCategoria.setView(viewSpinner);

        dialogCategoria.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                filtroCategoria = spinnerCategoria.getSelectedItem().toString();
                buttonLimparFiltros.setVisibility(View.VISIBLE);
                recuperarAnunciosPorCategoria();

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog dialog = dialogCategoria.create();
        dialog.show();


    }

    private void recuperarAnunciosPorCategoria() {

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando anúncios")
                .setCancelable(false)
                .build();

        dialog.show();

        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios");

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaAnuncios.clear();

                for (DataSnapshot estados : dataSnapshot.getChildren()) {
                    for (DataSnapshot categoria : estados.getChildren()) {

                        if (categoria.getKey().equals(filtroCategoria)) {

                            for (DataSnapshot anuncios : categoria.getChildren()) {

                                Anuncio anuncio = anuncios.getValue(Anuncio.class);
                                listaAnuncios.add(anuncio);


                            }

                        }

                    }

                }


                Collections.reverse(listaAnuncios);
                adapterAnuncios.notifyDataSetChanged();

                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void recuperarAnunciosPublicos() {

        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios");

        buttonLimparFiltros.setVisibility(View.GONE);

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando anúncios")
                .setCancelable(false)
                .build();

        dialog.show();

        listaAnuncios.clear();

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot estados : dataSnapshot.getChildren()) {
                    for (DataSnapshot categorias : estados.getChildren()) {
                        for (DataSnapshot anuncios : categorias.getChildren()) {

                            Anuncio anuncio = anuncios.getValue(Anuncio.class);
                            listaAnuncios.add(anuncio);


                        }
                    }

                }
                Collections.reverse(listaAnuncios);
                adapterAnuncios.notifyDataSetChanged();

                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
