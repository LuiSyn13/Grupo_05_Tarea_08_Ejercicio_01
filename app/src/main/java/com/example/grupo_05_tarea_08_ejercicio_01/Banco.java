package com.example.grupo_05_tarea_08_ejercicio_01;

import java.io.Serializable;
import java.util.ArrayList;

public class Banco implements Serializable {
    private ArrayList<Cliente> ListaCliente = new ArrayList<>();

    public ArrayList<Cliente> getListaCliente() {
        return ListaCliente;
    }

    public void setListaCliente(ArrayList<Cliente> listaCliente) {
        ListaCliente = listaCliente;
    }
}
