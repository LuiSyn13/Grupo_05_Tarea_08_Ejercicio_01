package com.example.grupo_05_tarea_08_ejercicio_01.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.grupo_05_tarea_08_ejercicio_01.R;
import com.example.grupo_05_tarea_08_ejercicio_01.databinding.FragmentHomeBinding;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView tv_prueba;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textoAs = root.findViewById(R.id.tv_prueba);
        Bundle operacion = getArguments();
        if (operacion != null) {
            String mit = operacion.getString("titulo");
            textoAs.setText(mit);
        }
        //textoAs.setText("HOLA MUNDO, MARTIN");

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        Spinner descubre = binding.spnDescubrelo;
        List<String> descubrelo = Arrays.asList("Conoce sobre Plin","Plinea en POS", "Promociones","¿Tienes dudas?");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, descubrelo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        descubre.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}