package com.example.juan.frienonline.Resuktados;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.juan.frienonline.Clases.Implements;
import com.example.juan.frienonline.Preguntas.PreguntasFragment;
import com.example.juan.frienonline.Preguntas.ResultadosFragment;
import com.example.juan.frienonline.R;

import java.util.Objects;

public class ResultadosActivity extends AppCompatActivity implements Implements {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);


        Fragment fragment = new ResultadosFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorResultado, fragment).commit();

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
}
