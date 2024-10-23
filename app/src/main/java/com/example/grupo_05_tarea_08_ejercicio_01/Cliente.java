package com.example.grupo_05_tarea_08_ejercicio_01;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable {

    private String Dni;
    private String Apellidos;
    private String Nombres;
    private String Direccion;
    private String Telefono;
    private ArrayList<Cuenta> objCuentas;

    public  Cliente () {}

    public Cliente(String dni, String apellidos, String nombres, String direccion, String telefono, ArrayList<Cuenta> objCuentas) {
        Dni = dni;
        Apellidos = apellidos;
        Nombres = nombres;
        Direccion = direccion;
        Telefono = telefono;
        this.objCuentas = objCuentas;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String dni) {
        Dni = dni;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public ArrayList<Cuenta> getObjCuentas() {
        return objCuentas;
    }

    public void setObjCuentas(ArrayList<Cuenta> objCuentas) {
        this.objCuentas = objCuentas;
    }
}
