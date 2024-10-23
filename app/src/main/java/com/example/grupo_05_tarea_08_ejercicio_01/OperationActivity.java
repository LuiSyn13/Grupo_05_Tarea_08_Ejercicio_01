package com.example.grupo_05_tarea_08_ejercicio_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class OperationActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Cliente> objClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        objClient = (ArrayList<Cliente>) getIntent().getExtras().getSerializable("listClient");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);

        findViewById(R.id.btn_deposito_op).setOnClickListener(this);
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
        if (v.getId() == R.id.btn_deposito_op) {
            Bundle contenedor = new Bundle();
            Intent intent = new Intent(v.getContext(), Operation_DepRet_Activity.class);
            contenedor.putSerializable("listClient", objClient);
            intent.putExtras(contenedor);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, objClient.size() + " AGUU", Toast.LENGTH_SHORT).show();
    }
}