package com.example.grupo_05_tarea_08_ejercicio_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {
    private Cliente objCliente = new Cliente();
    private TextInputEditText tie_dni_cli, tie_apel_cli, tie_nomb_cli, tie_dire_cli, tie_telf_cli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_register_cli).setOnClickListener(this);
        findViewById(R.id.btn_cancel_cli).setOnClickListener(this);

        tie_dni_cli = findViewById(R.id.tie_dni_cli);
        tie_apel_cli = findViewById(R.id.tie_apel_cli);
        tie_nomb_cli = findViewById(R.id.tie_nomb_cli);
        tie_dire_cli = findViewById(R.id.tie_dire_cli);
        tie_telf_cli = findViewById(R.id.tie_telf_cli);

        //listClient = (ArrayList<Cliente>) getIntent().getExtras().getSerializable("banco");
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
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
        if (v.getId() == R.id.btn_register_cli) {
            RegisterDate();
            finish();
        } else if (v.getId() == R.id.btn_cancel_cli) {
            finish();
        }
    }

    private void RegisterDate() {
        ArrayList<Cuenta> lcu = new ArrayList<>();
        objCliente.setDni(tie_dni_cli.getText().toString());
        objCliente.setApellidos(tie_apel_cli.getText().toString());
        objCliente.setNombres(tie_nomb_cli.getText().toString());
        objCliente.setDireccion(tie_dire_cli.getText().toString());
        objCliente.setTelefono(tie_telf_cli.getText().toString());
        objCliente.setObjCuentas(lcu);
        Bundle contenedor = new Bundle();
        contenedor.putSerializable("objCliente", objCliente);
        Intent data = new Intent();
        data.putExtras(contenedor);
        setResult(RESULT_OK, data);
    }
}