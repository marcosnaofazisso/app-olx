package com.marcosviniciusferreira.olx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcosviniciusferreira.olx.R;
import com.marcosviniciusferreira.olx.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterAnuncios extends RecyclerView.Adapter<AdapterAnuncios.MyViewHolder> {

    private List<Anuncio> anuncios = new ArrayList<>();
    private Context context;


    public AdapterAnuncios(List<Anuncio> listaAnuncios, Context c) {
        this.anuncios = listaAnuncios;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_anuncio, viewGroup, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Anuncio anuncio = anuncios.get(position);
        myViewHolder.titulo.setText(anuncio.getTitulo());
        myViewHolder.valor.setText(anuncio.getValor());
        myViewHolder.descricao.setText(anuncio.getDescricao());
        myViewHolder.uf.setText(anuncio.getEstado());

        //Carregar a imagem utilizando a lib Picasso
        List<String> urlFotos = anuncio.getFotos();

        String urlCapa = urlFotos.get(0);

        Picasso.get().load(urlCapa).into(myViewHolder.foto);


    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView valor;
        TextView descricao;
        TextView uf;
        ImageView foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            valor = itemView.findViewById(R.id.textPreco);
            descricao = itemView.findViewById(R.id.textDescricao);
            foto = itemView.findViewById(R.id.imageAnuncio);
            uf = itemView.findViewById(R.id.textUf);
        }
    }
}
