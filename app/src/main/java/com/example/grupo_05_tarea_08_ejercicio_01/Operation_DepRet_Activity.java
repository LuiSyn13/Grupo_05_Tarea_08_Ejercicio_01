package com.example.grupo_05_tarea_08_ejercicio_01;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

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
    private AutoCompleteTextView at_corigen_op;
    private Spinner spr_corigen_op;
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
        listClient = (ArrayList<Cliente>) getIntent().getExtras().getSerializable("listClient");

        getOnBackPressedDispatcher().addCallback(callback);
        at_corigen_op = findViewById(R.id.at_corigen_op);
        spr_corigen_op = findViewById(R.id.spr_corigen_op);
        List_spr_NCuenta();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listNCuentas);
        at_corigen_op.setAdapter(adapter);
        at_corigen_op.setThreshold(1);
        spr_corigen_op.setAdapter(adapter1);

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

    private void List_spr_NCuenta() {
        for (Cliente c: listClient) {
            for (Cuenta n: c.getObjCuentas()) {
                listNCuentas.add(n.getNumero());
            }
        }
    }
}