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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Cliente> listClient;
    private Banco objBanco;
    private ArrayList<Cuenta> listCuentas = new ArrayList<>();
    private Cuenta objCuenta;
    private ActivityResultLauncher<Intent> launcher_cuenta_activity;
    private TableLayout tl_cuentas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        final View vista = inflater.inflate(R.layout.fragment_account, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null){
            objBanco = mainActivity.List_Banco();
        }
        listClient = objBanco.getListaCliente();
        vista.findViewById(R.id.btn_addAccount).setOnClickListener(this);
        tl_cuentas = vista.findViewById(R.id.tl_accounts);
        launcher_cuenta_activity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Bundle contenedor = data.getExtras();
                objCuenta = (Cuenta) contenedor.getSerializable("objCuenta");
                listCuentas.add(objCuenta);
            } else if (result.getResultCode() == RESULT_CANCELED) {
            }
        });
        return vista;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addAccount){
            Bundle contenedor = new Bundle();
            Intent intent_add_account = new Intent(v.getContext(), AccountActivity.class);
            contenedor.putSerializable("listClient", objBanco.getListaCliente());
            contenedor.putSerializable("listAccount",listCuentas);
            intent_add_account.putExtras(contenedor);
            launcher_cuenta_activity.launch(intent_add_account);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CargarTabla();
    }

    private void ListAccount(){
        for(Cliente C:listClient){
            ArrayList<Cuenta> cuentasCliente = C.getObjCuentas();
            if (cuentasCliente != null && !cuentasCliente.isEmpty()) {
                listCuentas.addAll(cuentasCliente);
            }
        }
    }

    private void CargarTabla(){
        tl_cuentas.removeAllViews();
        ListAccount();
        for (Cuenta c : listCuentas) {
            TableRow tr = new TableRow(getContext());

            TextView tv_01 = new TextView(getContext());
            TextView tv_02 = new TextView(getContext());
            TextView tv_03 = new TextView(getContext());

            tv_01.setText(c.getNumero());
            tv_02.setText(c.getSaldo().toString());
            tv_03.setText(c.getEstado());

            tv_01.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT, 1));
            tv_02.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT, 1));
            tv_03.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT, 1));

            tr.addView(tv_01);
            tr.addView(tv_02);
            tr.addView(tv_03);

            tl_cuentas.addView(tr);
        }
    }

}