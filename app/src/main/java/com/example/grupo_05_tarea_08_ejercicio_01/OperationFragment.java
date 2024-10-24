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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OperationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OperationFragment extends Fragment implements View.OnClickListener {
    private Banco objBanco;
    private Operacion objOperacion;
    private ActivityResultLauncher<Intent> launcher_operation_activity;
    private TableLayout tl_operation;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OperationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OperationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OperationFragment newInstance(String param1, String param2) {
        OperationFragment fragment = new OperationFragment();
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
        //return inflater.inflate(R.layout.fragment_operation, container, false);
        final View view = inflater.inflate(R.layout.fragment_operation, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            objBanco = mainActivity.List_Banco();
        }
        view.findViewById(R.id.btn_addOperation).setOnClickListener(this);
        tl_operation = view.findViewById(R.id.tl_operation);

        launcher_operation_activity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
           if (result.getResultCode() == RESULT_OK) {
               Intent data = result.getData();
               Bundle contenedor = data.getExtras();
               objOperacion = (Operacion) contenedor.getSerializable("objOperacion");
               int i = (int) contenedor.getSerializable("pdni");
               int j = (int) contenedor.getSerializable("pnumero");
               double x = objBanco.getListaCliente().get(i).getObjCuentas().get(j).getSaldo();
               objBanco.getListaCliente().get(i).getObjCuentas().get(j).getOperaciones().add(new Operacion(objOperacion.getMonto(), objOperacion.getTipo()));
               if (objOperacion.getTipo().equals("Depósito")) {
                   objBanco.getListaCliente().get(i).getObjCuentas().get(j).setSaldo(x + objOperacion.getMonto());
               } else if (objOperacion.getTipo().equals("Retiro")) {
                   objBanco.getListaCliente().get(i).getObjCuentas().get(j).setSaldo(x - objOperacion.getMonto());
               }

               Toast.makeText(mainActivity, i + " JP", Toast.LENGTH_SHORT).show();
           } else if (result.getResultCode() == RESULT_CANCELED) {
           }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListOperations();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addOperation) {
            Bundle contenedor = new Bundle();
            Intent intent = new Intent(v.getContext(), OperationActivity.class);
            contenedor.putSerializable("listClient", objBanco.getListaCliente());
            intent.putExtras(contenedor);
            launcher_operation_activity.launch(intent);
        }
    }

    private void ListOperations() {
        tl_operation.removeAllViews();
        for (int i = 0; i < objBanco.getListaCliente().size(); i++) {
            for (int j = 0; j < objBanco.getListaCliente().get(i).getObjCuentas().size(); j++) {
                if (objBanco.getListaCliente().get(i).getObjCuentas().get(j).getEstado().equals("Cuenta Aperturada")) {
                    for (Operacion o: objBanco.getListaCliente().get(i).getObjCuentas().get(j).getOperaciones()) {

                        TableRow tr = new TableRow(getContext());

                        TextView tv_nume = new TextView(getContext());
                        TextView tv_mont = new TextView(getContext());
                        TextView tv_tipo = new TextView(getContext());

                        tv_nume.setText(objBanco.getListaCliente().get(i).getObjCuentas().get(j).getNumero());
                        tv_mont.setText(o.getMonto().toString());
                        tv_tipo.setText(o.getTipo());

                        tv_nume.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                        tv_mont.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                        tv_tipo.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        tr.addView(tv_nume);
                        tr.addView(tv_mont);
                        tr.addView(tv_tipo);

                        tl_operation.addView(tr);


                    }
                }
            }
        }
    }

}