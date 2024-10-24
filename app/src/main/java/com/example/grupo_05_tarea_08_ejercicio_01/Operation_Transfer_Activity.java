package com.example.grupo_05_tarea_08_ejercicio_01;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class Operation_Transfer_Activity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Cliente> listClient;
    private ArrayAdapter<String> adapterO1, adapterO2, adapterD1, adapterD2;
    private ArrayList<String> listNCuentas = new ArrayList<>();
    private ArrayList<String> listNCuentasDestino = new ArrayList<>();
    private ArrayList<Double> listNSaldo = new ArrayList<>();
    private String Tipo = "";
    private AutoCompleteTextView at_corigen_transf_op, at_cdestino_transf_op;
    private Spinner spr_corigen_transf_op, spr_cdestino_transf_op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operation_transfer);
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

        findViewById(R.id.btn_register_transf_op).setOnClickListener(this);
        findViewById(R.id.btn_cancel_transf_op).setOnClickListener(this);
        listClient = (ArrayList<Cliente>) getIntent().getExtras().getSerializable("listClient");
        Tipo = (String) getIntent().getExtras().getSerializable("tipo_op");
        ((TextView) findViewById(R.id.tv_titulo_transf_op)).setText(Tipo);

        at_corigen_transf_op = findViewById(R.id.at_corigen_transf_op);
        at_cdestino_transf_op = findViewById(R.id.at_cdestino_transf_op);
        spr_corigen_transf_op = findViewById(R.id.spr_corigen_transf_op);
        spr_cdestino_transf_op = findViewById(R.id.spr_cdestino_transf_op);
        List_spr_NCuentaOrigen();

        adapterO1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        adapterO2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        adapterD1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        adapterD2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);

        at_corigen_transf_op.setAdapter(adapterO1);
        at_corigen_transf_op.setThreshold(1);
        spr_corigen_transf_op.setAdapter(adapterO2);
        at_cdestino_transf_op.setAdapter(adapterD1);
        at_cdestino_transf_op.setThreshold(1);
        spr_cdestino_transf_op.setAdapter(adapterD2);

        spr_corigen_transf_op.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sItem = parent.getItemAtPosition(position).toString();
                int pItem = parent.getSelectedItemPosition();
                ((TextView) findViewById(R.id.tv_saldoAct_transf_op)).setText("Saldo actual (S/): " + listNSaldo.get(pItem).toString());
                at_corigen_transf_op.setText(sItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        at_corigen_transf_op.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 5) {
                    ejecutarFuncionCuandoHayTexto();
                } else {
                }
            }
        });
    }

    private void ejecutarFuncionCuandoHayTexto() {
        // Aquí puedes hacer cualquier cosa, como activar un botón, hacer una búsqueda, etc.
        Toast.makeText(this, "Texto ingresado", Toast.LENGTH_SHORT).show();
    }

    private void ejecutarFuncionCuandoNoHayTexto() {
        // Aquí puedes manejar el caso en que no hay texto
        Toast.makeText(this, "No hay texto", Toast.LENGTH_SHORT).show();
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

    }

    private void List_spr_NCuentaOrigen() {
        for (Cliente c: listClient) {
            for (Cuenta n: c.getObjCuentas()) {
                if (n.getEstado().equals("Cuenta Aperturada")) {
                    listNCuentas.add(n.getNumero());
                    listNSaldo.add(n.getSaldo());
                }
            }
        }
    }
    private void List_spr_NCuentaDestino(String numero) {
        for (Cliente c: listClient) {
            for (Cuenta n: c.getObjCuentas()) {
                if (n.getEstado().equals("Cuenta Aperturada")) {
                    listNCuentasDestino.add(n.getNumero());
                    listNSaldo.add(n.getSaldo());
                }
            }
        }
    }
}