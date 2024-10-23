package com.example.grupo_05_tarea_08_ejercicio_01;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragment extends Fragment implements View.OnClickListener {

    private Banco objBanco;
    private ArrayList<Cliente> listClient;
    private Cliente objCliente;
    private ActivityResultLauncher<Intent> launcher_client_activity;
    private TableLayout tl_client;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientFragment newInstance(String param1, String param2) {
        ClientFragment fragment = new ClientFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_client, container, false);
        final View view = inflater.inflate(R.layout.fragment_client, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null) {
            objBanco = mainActivity.List_Banco();
        }
        view.findViewById(R.id.btn_addClient).setOnClickListener(this);
        tl_client = view.findViewById(R.id.tl_client);

        launcher_client_activity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Bundle contenedor = data.getExtras();
                objCliente = (Cliente) contenedor.getSerializable("objCliente");
                objBanco.getListaCliente().add(objCliente);
            } else if (result.getResultCode() == RESULT_CANCELED) {
            }
        });
        //ListClient();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addClient) {
            Intent intent = new Intent(v.getContext(), ClientActivity.class);
            launcher_client_activity.launch(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ListClient();
        //Toast.makeText(getView().getContext(), "LOL", Toast.LENGTH_SHORT).show();
    }

    private void ListClient() {
        tl_client.removeAllViews();
        for (Cliente c: objBanco.getListaCliente()) {
            TableRow tr = new TableRow(getContext());

            TextView tv_01 = new TextView(getContext());
            TextView tv_02 = new TextView(getContext());
            TextView tv_03 = new TextView(getContext());
            TextView tv_04 = new TextView(getContext());
            TextView tv_05 = new TextView(getContext());

            tv_01.setText(c.getDni());
            tv_02.setText(c.getApellidos());
            tv_03.setText(c.getNombres());
            tv_04.setText(c.getDireccion());
            tv_05.setText(c.getTelefono());

            tv_01.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            tv_02.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            tv_03.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            tv_04.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            tv_05.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tr.addView(tv_01);
            tr.addView(tv_02);
            tr.addView(tv_03);
            tr.addView(tv_04);
            tr.addView(tv_05);

            tl_client.addView(tr);
        }
    }
}