package com.example.juan.frienonline.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.juan.frienonline.Clases.Preguntas;
import com.example.juan.frienonline.R;

import java.util.ArrayList;

public class AdapterModelo extends RecyclerView.Adapter<AdapterModelo.AdapterHolderView> implements View.OnClickListener {

    ArrayList<Preguntas> listaPreguntas;
    View.OnClickListener listener;

    public AdapterModelo(ArrayList<Preguntas> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    @NonNull
    @Override
    public AdapterHolderView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_multiple, null, false);
        view.setOnClickListener(this);
        return new AdapterHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterHolderView adapterHolderView, int i) {
        adapterHolderView.pregunta.setText(i + 1 + " . " + listaPreguntas.get(i).getPregunta());
        adapterHolderView.pregunta.setOnCheckedChangeListener(null);
        adapterHolderView.pregunta.setChecked(listaPreguntas.get(i).isCheck());
        adapterHolderView.pregunta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listaPreguntas.get(adapterHolderView.getAdapterPosition()).setCheck(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPreguntas.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class AdapterHolderView extends RecyclerView.ViewHolder {
        CheckBox pregunta;

        public AdapterHolderView(@NonNull View itemView) {
            super(itemView);

            pregunta = itemView.findViewById(R.id.pregunta);
        }
    }
}
