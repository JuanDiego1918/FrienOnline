package com.example.juan.frienonline.Preguntas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juan.frienonline.Adapter.AdapterModelo;
import com.example.juan.frienonline.Clases.Global;
import com.example.juan.frienonline.Clases.Preguntas;
import com.example.juan.frienonline.Clases.Puente;
import com.example.juan.frienonline.R;
import com.example.juan.frienonline.Resuktados.ResultadosActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreguntasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreguntasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreguntasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PreguntasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreguntasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreguntasFragment newInstance(String param1, String param2) {
        PreguntasFragment fragment = new PreguntasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Puente miPuente;
    Activity activity;
    View view;
    ArrayList<Preguntas> preguntas;
    TextView txtpregunta;
    Button btnSiguiente;
    RecyclerView recyclerView;
    private RequestQueue request;
    private ArrayList<Preguntas> listasPreguntas;
    private Dialog dialogoCargando;
    private int cantidadSeleccionado = 0, tipo = 1;
    private HashMap<Integer, Integer> resultados = new HashMap();
    ArrayList<Preguntas> lista = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_preguntas, container, false);
        txtpregunta = view.findViewById(R.id.textoPregunta);
        recyclerView = view.findViewById(R.id.recyclerview);
        btnSiguiente = view.findViewById(R.id.btnSiguiente);
        dialogoCargando = new Dialog(getContext());


        txtpregunta.setText("Seleccione los casos que le interesan");

        request = Volley.newRequestQueue(getContext());
        cargarWebService();

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Preguntas preguntasVo : lista) {
                    if (preguntasVo.isCheck()) {
                        cantidadSeleccionado++;
                    }
                }
                resultados.put(tipo, cantidadSeleccionado);
                cantidadSeleccionado = 0;
                tipo++;
                if (tipo >= 7) {
                    Global.resultados = resultados;
                    Global.nombreCategoria = new ArrayList<>();
                    for (int i = 0; i < listasPreguntas.size(); i++) {
                        if (Global.nombreCategoria.contains(listasPreguntas.get(i).getNombreCategoria()) == false) {
                            Global.nombreCategoria.add(listasPreguntas.get(i).getNombreCategoria());
                        }
                    }
                    miPuente.pantallas();
                } else {
                    mostrarDatos(tipo);
                }
            }
        });


        return view;
    }


    private void cargarWebService() {
        dialogoCargando();
        String url = "https://empresapp.000webhostapp.com/consultaPreguntas.php";
        // String url = "http://10.71.133.49/Vocacional/consultaPreguntas.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    private void dialogoCargando() {
        dialogoCargando.setContentView(R.layout.ventana_cargando);
        Objects.requireNonNull(dialogoCargando.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoCargando.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
            miPuente = (Puente) activity;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se pudo Consultar:" + error.toString(), Toast.LENGTH_LONG).show();
        Log.v("***************",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        listasPreguntas = new ArrayList<>();
        JSONArray json = response.optJSONArray("pregunta");
        try {
            JSONObject jsonObject;
            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                Preguntas preguntas = new Preguntas();
                preguntas.setCategoria(jsonObject.optInt("idCategoria"));
                preguntas.setPregunta(jsonObject.optString("pregunta"));
                preguntas.setNombreCategoria(jsonObject.getString("nombre"));
                listasPreguntas.add(preguntas);
            }

            mostrarDatos(1);

        } catch (JSONException e) {
            cargarWebService();
            Log.v("*1**************",e.toString());
            Toast.makeText(getContext(), "No se ha podido establecer conexión" +
                    " " + response, Toast.LENGTH_LONG).show();
        }
        dialogoCargando.dismiss();
    }

    private void mostrarDatos(int i) {
        Preguntas miPregunta;
        lista.clear();
        for (int s = 0; s < listasPreguntas.size(); s++) {
            if (listasPreguntas.get(s).getCategoria() == i) {
                miPregunta = new Preguntas();
                miPregunta.setPregunta(listasPreguntas.get(s).getPregunta());
                miPregunta.setCategoria(listasPreguntas.get(s).getCategoria());
                lista.add(miPregunta);
            }
        }

        AdapterModelo miAdapterModelo = new AdapterModelo(lista);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(miAdapterModelo);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
