package com.example.grupo_05_tarea_08_ejercicio_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private Cuenta objCuenta = new Cuenta();
    private ArrayList<Cuenta> listCuentas;
    private TextInputEditText tie_saldo;
    private RadioButton forma_consaldo, forma_sinsalso;
    private RadioGroup rdgp_forma;
    private double saldo_acc = 0.0;
    private int pdni = 0;
    private int pnumero = 0;
    private int pestado = 0;
    private ArrayList<Cliente> listCliente;
    private AutoCompleteTextView at_cuenta_acc;
    private ArrayList<String> listNCuentas = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_acept_acc).setOnClickListener(this);
        findViewById(R.id.btn_cancel_acc).setOnClickListener(this);
        forma_consaldo = findViewById(R.id.rbtn_consaldo);
        forma_sinsalso = findViewById(R.id.rbtn_sinsaldo);
        at_cuenta_acc = findViewById(R.id.tie_numero_cuenta);
        tie_saldo = findViewById(R.id.tie_saldo);
        rdgp_forma = findViewById(R.id.rdgp_forma);

        forma_consaldo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tie_saldo.setEnabled(true);
                    pestado = 1;
                }
            }
        });

        forma_sinsalso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tie_saldo.setEnabled(false);
                    tie_saldo.setText("");
                }
            }
        });




        listCliente = (ArrayList<Cliente>) getIntent().getExtras().getSerializable("listClient");
        List_NCuenta();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        at_cuenta_acc.setAdapter(adapter);
        at_cuenta_acc.setThreshold(1);
        ((TextView) findViewById(R.id.tv_count_acc)).setText(listNCuentas.size() + " REG.");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {finish();}

        };
        getOnBackPressedDispatcher().addCallback(this,callback);
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
        if(v.getId() == R.id.btn_acept_acc){
            RegitrarCuenta();
            Bundle contenedor_acc = new Bundle();
            contenedor_acc.putSerializable("objCuenta", objCuenta);
            contenedor_acc.putSerializable("pdni", pdni);
            contenedor_acc.putSerializable("pnumero", pnumero);
            Intent data_acc = new Intent();
            data_acc.putExtras(contenedor_acc);
            setResult(RESULT_OK, data_acc);
            finish();
        } else if (v.getId() == R.id.btn_cancel_acc){
            finish();
        }
    }

    private void RegitrarCuenta(){
        ArrayList<Operacion> lop = new ArrayList<>();
        objCuenta.setNumero(at_cuenta_acc.getText().toString());
        objCuenta.setEstado("Cuenta Aperturada");
        objCuenta.setOperaciones(lop);
        int selectID = rdgp_forma.getCheckedRadioButtonId();
        if (selectID != -1) {
            RadioButton rdb = findViewById(selectID);
            String idRdb = rdb.getText().toString();
            if (idRdb.contentEquals("Con Saldo")) {
                if (tie_saldo.getText().toString().isEmpty()) {
                    tie_saldo.setError("Campo obligatorio");
                    objCuenta.setSaldo(0.0);
                } else {
                    objCuenta.setSaldo(Double.parseDouble(tie_saldo.getText().toString()));
                }
            } else {
                objCuenta.setSaldo(0.0);
            }
        }
        for (int i = 0; i < listCliente.size(); i++) {
            for (int j = 0; j < listCliente.get(i).getObjCuentas().size(); j++) {
                if (listCliente.get(i).getObjCuentas().get(j).getNumero().contentEquals(at_cuenta_acc.getText())) {
                    pdni = i;
                    pnumero = j;
                    return;
                }
            }
        }
    }
    private String CargarCuentaSeleccion(){
        String cuenta = "INK" + listCliente.size() + 1;
        return cuenta;
    }

    private void List_NCuenta() {
        for (Cliente c: listCliente) {
            for (Cuenta n: c.getObjCuentas()) {
                if (n.getEstado().equals("No Aperturada")) {
                    listNCuentas.add(n.getNumero());
                }
            }
        }
    }

}