package com.example.juan.frienonline.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.frienonline.Clases.Preguntas;
import com.example.juan.frienonline.R;

import java.util.ArrayList;

public class AdapterResultados extends RecyclerView.Adapter<AdapterResultados.AdapterHolder> implements View.OnClickListener {

    ArrayList<Preguntas> lista;
    View.OnClickListener listener;

    public AdapterResultados(ArrayList<Preguntas> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modelo_resultados, null, false);
        view.setOnClickListener(this);
        return new AdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder adapterHolder, final int i) {
        adapterHolder.nombre.setText(lista.get(i).getNombreCategoria());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        Button nombre;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.botonResultados);
        }
    }
}
