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
            // Prueba de mostrar los datos
            // En un metodo listar los datos de los clientes, a partir del objBanco, que sigue de la inicialiacion mainActivity
            ((TextView) view.findViewById(R.id.tv_col01_cli)).setText(objBanco.getListaCliente().get(0).getDni());
            ((TextView) view.findViewById(R.id.tv_col02_cli)).setText(objBanco.getListaCliente().get(0).getApellidos());
            ((TextView) view.findViewById(R.id.tv_col03_cli)).setText(objBanco.getListaCliente().get(0).getTelefono());
        }
        view.findViewById(R.id.btn_addClient).setOnClickListener(this);

        launcher_client_activity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Bundle contenedor = data.getExtras();
                //listClient = (ArrayList<Cliente>) contenedor.getSerializable("objCliente");
                objCliente = (Cliente) contenedor.getSerializable("objCliente");
                objBanco.getListaCliente().add(objCliente);
                Toast.makeText(mainActivity, objBanco.getListaCliente().size() + " LOL", Toast.LENGTH_SHORT).show();
            } else if (result.getResultCode() == RESULT_CANCELED) {
                Toast.makeText(mainActivity, "No se guardaron los datos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addClient) {
            Intent intent = new Intent(v.getContext(), ClientActivity.class);
            launcher_client_activity.launch(intent);
        }
    }
}