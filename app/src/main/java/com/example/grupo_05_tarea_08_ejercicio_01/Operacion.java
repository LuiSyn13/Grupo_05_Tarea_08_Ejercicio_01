package com.example.grupo_05_tarea_08_ejercicio_01;

import java.io.Serializable;

public class Operacion implements Serializable {
    private Double Monto;
    private String Tipo;

    public Operacion(){}

    public Operacion(Double monto, String tipo) {
        Monto = monto;
        Tipo = tipo;
    }

    public Double getMonto() {
        return Monto;
    }

    public void setMonto(Double monto) {
        Monto = monto;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
