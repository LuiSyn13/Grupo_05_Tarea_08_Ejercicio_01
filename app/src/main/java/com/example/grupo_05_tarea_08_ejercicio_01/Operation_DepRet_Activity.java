package com.example.grupo_05_tarea_08_ejercicio_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Operation_DepRet_Activity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Cliente> listClient;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private ArrayList<String> listNCuentas = new ArrayList<>();
    private ArrayList<Double> listNSaldo = new ArrayList<>();
    private AutoCompleteTextView at_corigen_op;
    private Spinner spr_corigen_op;
    private int pdni = 0;
    private int pnumero = 0;
    private Operacion objOperacion = new Operacion();
    private String Tipo = "";
    private EditText et_monto_dr_op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operation_dep_ret);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);

        findViewById(R.id.btn_register_dr_op).setOnClickListener(this);
        findViewById(R.id.btn_cancel_dr_op).setOnClickListener(this);
        et_monto_dr_op = findViewById(R.id.et_monto_dr_op);

        listClient = (ArrayList<Cliente>) getIntent().getExtras().getSerializable("listClient");
        Tipo = (String) getIntent().getExtras().getSerializable("tipo_op");
        ((TextView) findViewById(R.id.tv_titulo_dr_op)).setText(Tipo);

        at_corigen_op = findViewById(R.id.at_corigen_op);
        spr_corigen_op = findViewById(R.id.spr_corigen_op);
        List_spr_NCuenta();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        at_corigen_op.setAdapter(adapter);
        at_corigen_op.setThreshold(1);
        spr_corigen_op.setAdapter(adapter1);

        spr_corigen_op.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sItem = parent.getItemAtPosition(position).toString();
                int pItem = parent.getSelectedItemPosition();
                ((TextView) findViewById(R.id.tv_saldoAct_dr_op)).setText("Saldo actual (S/): " + listNSaldo.get(pItem).toString());
                //Toast.makeText(Operation_DepRet_Activity.this, listNSaldo.get(pItem).toString(), Toast.LENGTH_SHORT).show();
                at_corigen_op.setText(sItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register_dr_op) {
            Cargar_Datos();
            Bundle contenedor = new Bundle();
            Intent data = new Intent();
            contenedor.putSerializable("objOperacion", objOperacion);
            contenedor.putSerializable("pdni", pdni);
            contenedor.putSerializable("pnumero", pnumero);
            data.putExtras(contenedor);
            setResult(RESULT_OK, data);
            finish();
        } else if (v.getId() == R.id.btn_cancel_dr_op) {
            finish();
        }
    }

    private void List_spr_NCuenta() {
        for (Cliente c: listClient) {
            for (Cuenta n: c.getObjCuentas()) {
                if (n.getEstado().equals("Cuenta Aperturada")) {
                    listNCuentas.add(n.getNumero());
                    listNSaldo.add(n.getSaldo());
                }
            }
        }
    }

    private void Cargar_Datos() {
        objOperacion.setMonto(Double.parseDouble(et_monto_dr_op.getText().toString()));
        objOperacion.setTipo(Tipo);
        for (int i = 0; i < listClient.size(); i++) {
            for (int j = 0; j < listClient.get(i).getObjCuentas().size(); j++) {
                if (listClient.get(i).getObjCuentas().get(j).getNumero().contentEquals(at_corigen_op.getText().toString())) {
                    pdni = i;
                    pnumero = j;
                    return;
                }
            }
        }
    }
}