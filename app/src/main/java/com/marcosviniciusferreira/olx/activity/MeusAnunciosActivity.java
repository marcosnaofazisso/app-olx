package com.marcosviniciusferreira.olx.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.marcosviniciusferreira.olx.R;
import com.marcosviniciusferreira.olx.adapter.AdapterAnuncios;
import com.marcosviniciusferreira.olx.helper.ConfiguracaoFirebase;
import com.marcosviniciusferreira.olx.helper.RecyclerItemClickListener;
import com.marcosviniciusferreira.olx.model.Anuncio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MeusAnunciosActivity extends AppCompatActivity {

    private RecyclerView recyclerAnuncios;
    private List<Anuncio> anuncios = new ArrayList<>();
    private AdapterAnuncios adapterAnuncios;
    private DatabaseReference anuncioUsuarioRef;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        //Configuracoes iniciais
        anuncioUsuarioRef = ConfiguracaoFirebase.getFirebase()
                .child("meus_anuncios")
                .child(ConfiguracaoFirebase.getIdUsuario());

        inicializarComponentes();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarAnuncioActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurar adapter
        adapterAnuncios = new AdapterAnuncios(anuncios, this);

        //Configurar recyclerview
        recyclerAnuncios.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnuncios.setHasFixedSize(true);
        recyclerAnuncios.setAdapter(adapterAnuncios);

        recuperarAnuncios();

        //Adicionar evento de clique no Recyclerview
        recyclerAnuncios.addOnItemTouchListener(new RecyclerItemClickListener(
                this,
                recyclerAnuncios,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        Anuncio anuncioSelecionado = anuncios.get(position);
                        anuncioSelecionado.remover();

                        adapterAnuncios.notifyDataSetChanged();

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));


    }

    private void recuperarAnuncios() {

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando anúncios")
                .setCancelable(false)
                .build();

        dialog.show();

        anuncioUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                anuncios.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    anuncios.add(ds.getValue(Anuncio.class));
                }

                Collections.reverse(anuncios);
                adapterAnuncios.notifyDataSetChanged();

                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes() {

        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);


    }

}
