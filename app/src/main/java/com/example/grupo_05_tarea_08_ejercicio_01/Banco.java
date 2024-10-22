package com.example.grupo_05_tarea_08_ejercicio_01;

import java.io.Serializable;
import java.util.ArrayList;

public class Banco implements Serializable {
    private ArrayList<Cliente> ListaCliente = new ArrayList<>();


    public ArrayList<Cliente> getListaCliente() {
        return ListaCliente;
    }

<<<<<<< HEAD
    private void RegistrarCliente (Cliente elemento) {
        ListaCliente.add(elemento);
=======
    public void setListaCliente(ArrayList<Cliente> listaCliente) {
        ListaCliente = listaCliente;
>>>>>>> 1f37612d2f5c67515874f1d484de4eba67467b39
    }
}
