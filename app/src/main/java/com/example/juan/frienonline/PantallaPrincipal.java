package com.example.juan.frienonline;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.frienonline.Principal.Contenedor;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class PantallaPrincipal extends AppCompatActivity {

    EditText numero, nombre, gmail;
    Dialog loginCorrecto;
    CountDownTimer countDownTimer;
    Spinner dia, mesSpinner, anioSpinner;
    ArrayList numeroDia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        loginCorrecto = new Dialog(this);
        numero = findViewById(R.id.numero);
        nombre = findViewById(R.id.nombre);
        gmail = findViewById(R.id.gmail);
        dia = findViewById(R.id.dia);
        mesSpinner = findViewById(R.id.mes);
        anioSpinner = findViewById(R.id.anio);
        numeroDia = new ArrayList();
        mes = new ArrayList();
        anio = new ArrayList();

        llenarArray();

    }

    private void llenarArray() {
        numeroDia.add("Dia");
        for (int i = 1; i < 32; i++) {
            numeroDia.add(i);
        }
        ArrayAdapter<CharSequence> adapterDia = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_nacimiento, numeroDia);
        dia.setAdapter(adapterDia);

        dia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mes.add("Mes");
        mes.add("Ene");
        mes.add("Feb");
        mes.add("Mar");
        mes.add("Abr");
        mes.add("May");
        mes.add("Jun");
        mes.add("Jul");
        mes.add("Ago");
        mes.add("Sep");
        mes.add("Oct");
        mes.add("Nov");
        mes.add("Dic");

        ArrayAdapter<CharSequence> adapterMes = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_nacimiento, mes);
        mesSpinner.setAdapter(adapterMes);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        year = year - 10;

        anio.add("Año");
        for (int i = 1979; i < year; i++) {
            anio.add(i);
        }

        ArrayAdapter<CharSequence> adapterAnio = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_nacimiento, anio);
        anioSpinner.setAdapter(adapterAnio);
    }

    public void Registrar(View view) {
        ventana();
    }


    private void ventana() {
        tiempo();
        TextView txtRetroBuena;
        loginCorrecto.setContentView(R.layout.ventana_confirmacion);
        TextView respuesta = loginCorrecto.findViewById(R.id.mensajeEmergente);
        respuesta.setText("Registro Exitoso");

        Objects.requireNonNull(loginCorrecto.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginCorrecto.show();
    }

    private void tiempo() {
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                loginCorrecto.dismiss();
                limpiar();
                Intent miActivity=new Intent(getApplicationContext(),Contenedor.class);
                startActivity(miActivity);
            }
        };
        countDownTimer.start();
    }

    private void limpiar() {
        gmail.setText(null);
        nombre.setText(null);
        numero.setText(null);
    }
}
