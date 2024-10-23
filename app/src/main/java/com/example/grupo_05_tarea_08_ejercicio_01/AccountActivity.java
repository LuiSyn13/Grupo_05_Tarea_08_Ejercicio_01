package com.example.grupo_05_tarea_08_ejercicio_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

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
    private TextInputEditText tie_numero,tie_saldo;
    private RadioButton forma_consaldo, forma_sinsalso;
    private double saldo_acc = 0.0;
    private String dni = "76466";
    private ArrayList<Cliente> listCliente;

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
        tie_numero = findViewById(R.id.tie_numero_cuenta);
        tie_saldo = findViewById(R.id.tie_saldo);

        forma_consaldo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tie_saldo.setEnabled(true);
                }
            }
        });

        forma_sinsalso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tie_saldo.setEnabled(false);
                }
            }
        });


        listCliente = (ArrayList<Cliente>) getIntent().getExtras().getSerializable("listClient");
        tie_numero.setText(CargarCuentaSeleccion());

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
            RegitrarCuenta(v);
            finish();
        } else if (v.getId() == R.id.btn_cancel_acc){
            finish();
        }
    }

    private void RegitrarCuenta(View vi){
        for (Cliente C : listCliente) {
            if(dni == C.getDni()){
                ArrayList<Operacion> lop = new ArrayList<>();
                objCuenta.setNumero(tie_numero.getText().toString());
                objCuenta.setEstado("Cuenta Aperturada");
                objCuenta.setOperaciones(lop);
                if(vi.getId() == R.id.rbtn_consaldo){
                    saldo_acc = Double.parseDouble(tie_saldo.getText().toString());
                    objCuenta.setSaldo(saldo_acc);
                } else if (vi.getId() == R.id.rbtn_sinsaldo) {
                    objCuenta.setSaldo(saldo_acc);
                }
                listCuentas = C.getObjCuentas();
                listCuentas.add(objCuenta);
                Bundle contenedor_acc = new Bundle();
                contenedor_acc.putSerializable("objCuenta", objCuenta);
                Intent data_acc = new Intent();
                data_acc.putExtras(contenedor_acc);
                setResult(RESULT_OK, data_acc);
            }
        }


    }
    private String CargarCuentaSeleccion(){
        String cuenta = "INK" + listCliente.size() + 1;
        return cuenta;
    }
}