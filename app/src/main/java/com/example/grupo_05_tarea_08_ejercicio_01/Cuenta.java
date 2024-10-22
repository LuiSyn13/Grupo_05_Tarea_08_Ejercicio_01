package com.example.grupo_05_tarea_08_ejercicio_01;

import java.io.Serializable;
import java.util.ArrayList;

public class Cuenta implements Serializable {
    private String Numero;
    private Double Saldo;
    private ArrayList<Operacion> Operaciones;
    private String Estado;

    public Cuenta(){}

    public Cuenta(String numero, Double saldo, ArrayList<Operacion> operaciones, String estado) {
        Numero = numero;
        Saldo = saldo;
        Operaciones = operaciones;
        Estado = estado;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

    public ArrayList<Operacion> getOperaciones() {
        return Operaciones;
    }

    public void setOperaciones(ArrayList<Operacion> operaciones) {
        Operaciones = operaciones;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
