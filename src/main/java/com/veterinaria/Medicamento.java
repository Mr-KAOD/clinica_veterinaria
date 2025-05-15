package com.veterinaria;

public class Medicamento {
    private String nombre;
    private double costo;

    public Medicamento(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    public double getCosto() {
        return costo;
    }
    public String getNombre() {
        return nombre;
    }
}
