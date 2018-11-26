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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.frienonline.Principal.Contenedor;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PantallaPrincipal extends AppCompatActivity {

    EditText numero, nombre, gmail;
    Dialog loginCorrecto;
    CountDownTimer countDownTimer;
    Spinner dia, mesSpinner, anioSpinner;
    ArrayList numeroDia, mes, anio;
    private StringRequest stringRequest;
    private RequestQueue request;
    private String fechaNacimiento, varDia = "0", varMes = "0", varAnio = "0";
    private Dialog dialogoCargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        request = Volley.newRequestQueue(getApplicationContext());
        dialogoCargando = new Dialog(this);
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
                if (position != 0) {
                    varDia = numeroDia.get(position).toString();
                } else {
                    varDia = "0";
                }
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

        mesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    varMes = mes.get(position).toString();
                } else {
                    varMes = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        year = year - 10;

        anio.add("Año");
        for (int i = 1979; i < year; i++) {
            anio.add(i);
        }

        ArrayAdapter<CharSequence> adapterAnio = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_nacimiento, anio);
        anioSpinner.setAdapter(adapterAnio);

        anioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    varAnio = anio.get(position).toString();
                } else {
                    varAnio = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void Registrar(View view) {

        if (nombre.getText().toString().equalsIgnoreCase("") || numero.getText().toString().equalsIgnoreCase("") || gmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
            ventana(2);
        } else {
            if (varDia.equalsIgnoreCase("0") || varMes.equalsIgnoreCase("0") || varAnio.equalsIgnoreCase("0")) {
                Toast.makeText(this, "Ingrese la fecha de nacimiento", Toast.LENGTH_SHORT).show();
            } else {
                dialogoCargando();
                String url;
                url = "https://empresapp.000webhostapp.com/registro.php?nombre=" + nombre.getText().toString() + "&telefono=" + numero.getText().toString() + "&gmail=" + gmail.getText().toString() + "&nacimiento=" + varDia + "-" + varMes + "-" + varAnio;
                stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ventana(1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "No se pudo registrar verifique los datos", Toast.LENGTH_LONG).show();
                        ventana(2);
                    }

                });

                request.add(stringRequest);
            }
        }
    }
    private void dialogoCargando() {
        dialogoCargando.setContentView(R.layout.ventana_cargando);
        Objects.requireNonNull(dialogoCargando.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoCargando.show();
    }

    private void ventana(int tipo) {
        dialogoCargando.dismiss();
        ImageView imagenVentana;
        loginCorrecto.setContentView(R.layout.ventana_confirmacion);
        imagenVentana = loginCorrecto.findViewById(R.id.imagenVentana);
        TextView respuesta = loginCorrecto.findViewById(R.id.mensajeEmergente);
        if (tipo == 1) {
            imagenVentana.setImageDrawable(getResources().getDrawable(R.drawable.check));
            respuesta.setText("Registro Exitoso");
            tiempo(1);
        } else {
            imagenVentana.setImageDrawable(getResources().getDrawable(R.drawable.check_mal));
            respuesta.setText("Registro No Exitoso");
            tiempo(2);
        }


        Objects.requireNonNull(loginCorrecto.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginCorrecto.show();
    }

    private void tiempo(final int tipo) {
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (tipo == 1) {
                    loginCorrecto.dismiss();
                    limpiar();
                    Intent miActivity = new Intent(getApplicationContext(), Contenedor.class);
                    startActivity(miActivity);
                } else {
                    loginCorrecto.dismiss();
                }
            }
        };
        countDownTimer.start();
    }

    private void limpiar() {
        gmail.setText(null);
        nombre.setText(null);
        numero.setText(null);
        varMes = "0";
        varAnio = "0";
        varDia = "0";
    }

}
