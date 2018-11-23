package com.example.juan.frienonline.Preguntas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.juan.frienonline.Adapter.AdapterResultados;
import com.example.juan.frienonline.Clases.Global;
import com.example.juan.frienonline.Clases.Preguntas;
import com.example.juan.frienonline.Clases.Puente;
import com.example.juan.frienonline.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultadosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ResultadosFragment() {
        // Required empty public constructor
    }

    Puente miPuente;
    Activity activity;
    View view;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultadosFragment newInstance(String param1, String param2) {
        ResultadosFragment fragment = new ResultadosFragment();
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

    private PieChart pieChart;
    private ArrayList<Integer> sale = new ArrayList();
    //private int[] sale = new int[]{9, 5, 3, 3, 2, 7};
    private ArrayList<String> ambiente = Global.nombreCategoria;
    private int[] colores = new int[]{Color.rgb(205, 92, 205),
            Color.rgb(255, 192, 203),
            Color.rgb(255, 215, 0),
            Color.rgb(230, 230, 250),
            Color.rgb(173, 255, 47),
            Color.rgb(245, 222, 179)};
    private RecyclerView recyclerResultados;
    Preguntas preguntas;
    private Dialog dialogoProfesion;
    ArrayList<Preguntas> listaCategoria;
    AdapterResultados adapterModeloResultados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resultados, container, false);

        pieChart = view.findViewById(R.id.graficaCircular);
        recyclerResultados = view.findViewById(R.id.resultados);
        dialogoProfesion = new Dialog(getContext());

        datos();
        crearGraficas();
        llenarAdapter();

        adapterModeloResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "holi"+listaCategoria.get(recyclerResultados.getChildAdapterPosition(view)).getNombreCategoria(), Toast.LENGTH_SHORT).show();
                //ventanaCargo(1);
            }
        });

        return view;
    }


    private void datos() {
        for (int i = 1; i < Global.resultados.size() + 1; i++) {
            sale.add(Global.resultados.get(i));
        }

    }

    private void llenarAdapter() {
        listaCategoria = new ArrayList<>();

        for (int i = 0; i < ambiente.size(); i++) {
            preguntas = new Preguntas();
            preguntas.setNombreCategoria(ambiente.get(i));
            listaCategoria.add(preguntas);
        }

        adapterModeloResultados = new AdapterResultados(listaCategoria);

        recyclerResultados.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //recyclerResultados.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayout.VERTICAL,false));
        recyclerResultados.setAdapter(adapterModeloResultados);

    }

    private void ventanaCargo(int i) {
        dialogoProfesion.setContentView(R.layout.ventana_cargando);
        Objects.requireNonNull(dialogoProfesion.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogoProfesion.show();
    }


    private Chart getSameChart(Chart chart, String descip, int textColor, int background, int animate) {
        chart.getDescription().setEnabled(false);
        //chart.getDescription().setText(descip);
        //chart.getDescription().setTextSize(15);
        //chart.getDescription().setTextColor(textColor);
        //chart.setBackgroundColor(background);
        chart.animateY(animate);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart) {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < ambiente.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colores[i];
            entry.label = ambiente.get(i);
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<PieEntry> getPieEntries() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < sale.size(); i++)
            entries.add(new PieEntry(sale.get(i)));
        return entries;
    }

    public void crearGraficas() {
        pieChart = (PieChart) getSameChart(pieChart, "Vocación", Color.BLACK, Color.TRANSPARENT, 3000);
        pieChart.setHoleRadius(35);
        //pieChart.setCenterText("Vocación");
        //pieChart.setCenterTextSize(18);
        //pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(0);
        pieChart.setData(getPieData());
        pieChart.invalidate();
    }

    private DataSet getData(DataSet dataSet) {
        dataSet.setColors(colores);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private PieData getPieData() {
        PieDataSet pieDataSet = (PieDataSet) getData(new PieDataSet(getPieEntries(), ""));

        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());

        return new PieData(pieDataSet);
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
