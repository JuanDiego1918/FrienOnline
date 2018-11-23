package com.example.juan.frienonline.Principal;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.juan.frienonline.Clases.Implements;
import com.example.juan.frienonline.Clases.Puente;
import com.example.juan.frienonline.Preguntas.PreguntasFragment;
import com.example.juan.frienonline.R;
import com.example.juan.frienonline.Resuktados.ResultadosActivity;

import java.util.Objects;

public class Contenedor extends AppCompatActivity implements Implements ,Puente {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);

        Fragment fragment = new PreguntasFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragment).commit();

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void pantallas() {
        Intent miIntent = new Intent(Contenedor.this, ResultadosActivity.class);
        startActivity(miIntent);
        finish();
    }
}
