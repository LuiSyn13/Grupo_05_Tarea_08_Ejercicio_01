package com.example.grupo_05_tarea_08_ejercicio_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAccountFragment extends Fragment {

    private TextView tvNumeroCuenta, tvSaldo, tvEstado, tvNombreCliente, tvCantidadDepositos, tvCantidadRetiros;
    private Button btnRealizarOperacion;
    private ListView lvOperaciones;
    private Cuenta cuentaSeleccionada;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditAccountFragment newInstance(String param1, String param2) {
        EditAccountFragment fragment = new EditAccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_account, container, false);

        tvNumeroCuenta = view.findViewById(R.id.tv_num_account);
        tvSaldo = view.findViewById(R.id.tv_saldo_account);
        tvEstado = view.findViewById(R.id.tv_estado_acc);
        tvNombreCliente = view.findViewById(R.id.tv_date_client);
        tvCantidadDepositos = view.findViewById(R.id.tv_cant_deposito);
        tvCantidadRetiros = view.findViewById(R.id.tv_cant_retiro);
        btnRealizarOperacion = view.findViewById(R.id.btn_register_oper_acc);
        lvOperaciones = view.findViewById(R.id.lv_list_oper);

        cuentaSeleccionada = (Cuenta) getArguments().getSerializable("cuentaselect");

        return view;
    }

    private void mostrarDatosCuenta(){
        if (cuentaSeleccionada != null) {

            tvNumeroCuenta.setText(cuentaSeleccionada.getNumero());
            tvSaldo.setText(String.valueOf(cuentaSeleccionada.getSaldo()));
            tvEstado.setText(cuentaSeleccionada.getEstado());

            Cliente cliente = obtenerClientePorCuenta(cuentaSeleccionada);
            if (cliente != null) {
                tvNombreCliente.setText(cliente.getNombres() + " " + cliente.getApellidos());
            }


            int cantidadDepositos = 0;
            int cantidadRetiros = 0;
            for (Operacion oper : cuentaSeleccionada.getOperaciones()) {
                if (oper.getTipo().equals("Deposito")) {
                    cantidadDepositos++;
                } else if (oper.getTipo().equals("Retiro")) {
                    cantidadRetiros++;
                }
            }

            // Mostrar cantidad de operaciones
            tvCantidadDepositos.setText(String.valueOf(cantidadDepositos));
            tvCantidadRetiros.setText(String.valueOf(cantidadRetiros));

            // Habilitar o deshabilitar botón de operación según el estado de la cuenta
            if (cuentaSeleccionada.getEstado().equals("No Aperturada")) {
                btnRealizarOperacion.setEnabled(false);
            } else if (cuentaSeleccionada.getEstado().equals("Aperturada")) {
                btnRealizarOperacion.setEnabled(true);
            }

            // Cargar operaciones en el ListView
            cargarOperacionesEnListView();
        }
    }

    private Cliente obtenerClientePorCuenta(Cuenta cuenta) {
        // Simular búsqueda de cliente por cuenta
        // Aquí deberías implementar la lógica real para obtener el cliente
    }

    private void cargarOperacionesEnListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        for (Operacion oper : cuentaSeleccionada.getOperaciones()) {
            adapter.add(oper.getTipo() + " - " + oper.getMonto());
        }
        lvOperaciones.setAdapter(adapter);
    }
}