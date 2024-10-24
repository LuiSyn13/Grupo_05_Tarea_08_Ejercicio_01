package com.example.grupo_05_tarea_08_ejercicio_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class OperationActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Cliente> objClient;
    private ActivityResultLauncher<Intent> launcher_operacion_activity_dep;
    private ActivityResultLauncher<Intent> launcher_operacion_activity_ret;
    private ActivityResultLauncher<Intent> launcher_operacion_activity_tcu;
    private ActivityResultLauncher<Intent> launcher_operacion_activity_tte;
    private Operacion objOperacion;
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
        findViewById(R.id.btn_retiro_op).setOnClickListener(this);
        findViewById(R.id.btn_transfCuenta_op).setOnClickListener(this);
        findViewById(R.id.btn_transfTercero_op).setOnClickListener(this);

        launcher_operacion_activity_dep = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
           if (result.getResultCode() == RESULT_OK) {
               Intent data = result.getData();
               Bundle contenedor = data.getExtras();
               Intent salida = new Intent();
               Bundle csalida = new Bundle();
               csalida.putSerializable("objOperacion", contenedor.getSerializable("objOperacion"));
               csalida.putSerializable("pdni", contenedor.getSerializable("pdni"));
               csalida.putSerializable("pnumero", contenedor.getSerializable("pnumero"));
               salida.putExtras(csalida);
               setResult(RESULT_OK, salida);
               finish();
           } else if (result.getResultCode() == RESULT_CANCELED){
               Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show();
           }
        });
        launcher_operacion_activity_ret = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
           if (result.getResultCode() == RESULT_OK) {
               Intent data = result.getData();
               Bundle contenedor = data.getExtras();
               Intent salida = new Intent();
               Bundle csalida = new Bundle();
               csalida.putSerializable("objOperacion", contenedor.getSerializable("objOperacion"));
               csalida.putSerializable("pdni", contenedor.getSerializable("pdni"));
               csalida.putSerializable("pnumero", contenedor.getSerializable("pnumero"));
               salida.putExtras(csalida);
               setResult(RESULT_OK, salida);
               finish();
           } else if (result.getResultCode() == RESULT_CANCELED){
               Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show();
           }
        });
        launcher_operacion_activity_tcu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
           if (result.getResultCode() == RESULT_OK) {
               Intent data = result.getData();
               Bundle contenedor = data.getExtras();
               Intent salida = new Intent();
               Bundle csalida = new Bundle();
               csalida.putSerializable("objOperacion", contenedor.getSerializable("objOperacion"));
               csalida.putSerializable("pdni", contenedor.getSerializable("pdni"));
               csalida.putSerializable("pnumero", contenedor.getSerializable("pnumero"));
               salida.putExtras(csalida);
               setResult(RESULT_OK, salida);
               finish();
           } else if (result.getResultCode() == RESULT_CANCELED){
               Toast.makeText(this, "Fallo", Toast.LENGTH_SHORT).show();
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
        if (v.getId() == R.id.btn_deposito_op) {
            Bundle contenedor = new Bundle();
            Intent intent = new Intent(v.getContext(), Operation_DepRet_Activity.class);
            contenedor.putSerializable("listClient", objClient);
            contenedor.putSerializable("tipo_op", "Depósito");
            intent.putExtras(contenedor);
            Toast.makeText(this, "Depósito", Toast.LENGTH_SHORT).show();
            launcher_operacion_activity_dep.launch(intent);
        } else if (v.getId() == R.id.btn_retiro_op) {
            Bundle contenedor = new Bundle();
            Intent intent = new Intent(v.getContext(), Operation_DepRet_Activity.class);
            contenedor.putSerializable("listClient", objClient);
            contenedor.putSerializable("tipo_op", "Retiro");
            intent.putExtras(contenedor);
            Toast.makeText(this, "Retiro", Toast.LENGTH_SHORT).show();
            launcher_operacion_activity_ret.launch(intent);
        } else if (v.getId() == R.id.btn_transfCuenta_op) {
            Bundle contenedor = new Bundle();
            Intent intent = new Intent(v.getContext(), Operation_Transfer_Activity.class);
            contenedor.putSerializable("listClient", objClient);
            contenedor.putSerializable("tipo_op", "Transferencia entre cuenta");
            intent.putExtras(contenedor);
            Toast.makeText(this, "Transferencia entre cuenta", Toast.LENGTH_SHORT).show();
            launcher_operacion_activity_tcu.launch(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "JOJOJOJO", Toast.LENGTH_SHORT).show();
    }
}